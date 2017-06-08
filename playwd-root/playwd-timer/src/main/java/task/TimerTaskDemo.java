package task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年11月13日 下午6:32:00
 * 描        述： 测试
 */
@Component
public class TimerTaskDemo { 

    private Logger logger = LoggerFactory.getLogger(TimerTaskDemo.class);
    
    /**
     * 测试
     */
    //@Scheduled(cron="*/1 * * * * ?")
    public void integralZero() throws Exception {
        logger.info("-------测试开始--------");
    }

}
