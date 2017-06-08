package task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.playwd.beans.common.RetryTaskInfo;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2017年3月3日 上午10:31:33
 * 描        述：
 */
public abstract class OneProducerMultiConsumer<T> {
	private Logger logger = LoggerFactory.getLogger(getClass());
	//默认通知间隔时间
	public static final Map<Integer,Integer> defaultRetryTime = new HashMap<Integer,Integer>();
	//初始化间隔时间  第K次重试间隔V秒
	private void initDefaultRestryTime(){
		defaultRetryTime.put(1,10);
		defaultRetryTime.put(2,60);
		defaultRetryTime.put(3,2 * 60);
		defaultRetryTime.put(4,15 * 60);
		defaultRetryTime.put(5,30 * 60);
		defaultRetryTime.put(6,1 * 60 * 60);
		defaultRetryTime.put(7,2 * 60 * 60);
		defaultRetryTime.put(8,2* 60 * 60);
		defaultRetryTime.put(9,8 * 60 * 60);
		defaultRetryTime.put(10,8 * 60 * 60);
	}
	
	private String taskName = "OneProducerMultiConsumer";
	//生产者线程
	private Thread producer;
	
	private boolean isRunning = false;
	
	public abstract List<T> produce();
	
	public abstract boolean consume(T item);
	
	private AtomicInteger currentRunningTaskCount = new AtomicInteger();
	
	//生产者睡眠时间(毫秒)
	private long produceSleepMilliseconds = 1000;
	//生产者生产任务最大数目
	private int maxProduceTaskCount = 100;
	//消费者最大数目
	private long maxConsumeCount = 100;
	//生产者生成最小间隔时间(毫秒)
	private long minProduceInterval = 60 * 1000;
	
	public OneProducerMultiConsumer() {
		this.initDefaultRestryTime();
	}
	
	public void start() {
		logger.info("生产者Start...");
		isRunning = true;
		producer = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					//上次生产时间
					long lastProduceTime = 0;
					while(isRunning){
						//限制运行的最大任务数量
						if(currentRunningTaskCount.get() > maxConsumeCount){
							//logger.info("The current number of tasks has exceeded the limit , procuder sleep {} ms.",produceSleepMilliseconds);
							Thread.sleep(produceSleepMilliseconds);
							continue;
						}
						long interval = System.currentTimeMillis() - lastProduceTime;
						if(interval < minProduceInterval){
							Thread.sleep(minProduceInterval - interval);
						}
						lastProduceTime = System.currentTimeMillis();
						List<T> items = null;
						try{
							items = produce();
						}catch(Exception e){
							logger.error("produce method error.",e);
						}
						if(items == null || items.size() == 0){
							//logger.info("Number of tasks obtained from the producer is empty, procuder sleep {} ms.",produceSleepMilliseconds);
							Thread.sleep(produceSleepMilliseconds);
							continue;
						}
						currentRunningTaskCount.addAndGet(items.size());
						items.parallelStream().forEach(new Consumer<T>() {
							@Override
							public void accept(T t) {
								try{
                                    boolean isSuccess = consume(t);
                                    logger.info("taskName : "+taskName + " consume item : {}  finish run . The result is {}.", t ,isSuccess ? "success" : "failed");
                                }catch (Exception e){
                                    logger.error("taskName : "+taskName + " consume item : "+t+"  throw exception ." , e);
                                }finally{
                                	currentRunningTaskCount.decrementAndGet();
                                }
							}
						});
					}
				}catch(Exception e){
					logger.error("OneProducerMultiConsumer run method error.",e);
				}
			}
		});
		producer.start();
    }
	
	public void stop() {
		logger.info("生产者Stoping...");
		isRunning = false;
    }
	
	public void updateRetryTaskInfo(RetryTaskInfo retryTaskInfo){
		retryTaskInfo.setRetryCount(retryTaskInfo.getRetryCount()+1);
		retryTaskInfo.setRetryTime(new DateTime().plusSeconds(defaultRetryTime.get(retryTaskInfo.getRetryCount())).toDate());
    }

	public long getProduceSleepMilliseconds() {
		return produceSleepMilliseconds;
	}

	public void setProduceSleepMilliseconds(long produceSleepMilliseconds) {
		this.produceSleepMilliseconds = produceSleepMilliseconds;
	}

	public long getMaxConsumeCount() {
		return maxConsumeCount;
	}

	public void setMaxConsumeCount(long maxConsumeCount) {
		this.maxConsumeCount = maxConsumeCount;
	}

	public int getMaxProduceTaskCount() {
		return maxProduceTaskCount;
	}

	public void setMaxProduceTaskCount(int maxProduceTaskCount) {
		this.maxProduceTaskCount = maxProduceTaskCount;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setMinProduceInterval(long minProduceInterval) {
		this.minProduceInterval = minProduceInterval;
	}

}
