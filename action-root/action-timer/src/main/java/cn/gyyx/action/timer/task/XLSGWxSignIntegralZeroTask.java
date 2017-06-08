package cn.gyyx.action.timer.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.gyyx.action.service.xlsgwxsign.XLSGWxSignService;

/* 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年11月13日 下午2:32:00
 * 描        述：驯龙三国微信签到功能-定时任务
 */
@Component
public class XLSGWxSignIntegralZeroTask {

    private XLSGWxSignService xLSGWxSignService = new XLSGWxSignService();
    
    private Logger logger = LoggerFactory.getLogger(XLSGWxSignIntegralZeroTask.class);
    
    /**
     * 积分每月1号清零
     */
    @Scheduled(cron="0 0 0 1 * ?")
    public void integralZero() {
        logger.info("-------驯龙三国微信签到定时任务（积分每月1号零点积分清零） 开始--------");
        xLSGWxSignService.updateZeroInMonthStart();
        logger.info("-------驯龙三国微信签到定时任务（积分每月1号零点积分清零）结束--------");
    }

}
