package cn.gyyx.action.timer.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.gyyx.action.service.jswswxsign.JSWSWxSignService;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 绝世武神微信签到功能-定时任务
 */
@Component
public class JSWSWxSignIntegralZeroTask {

    private JSWSWxSignService jSWSWxSignService = new JSWSWxSignService();
    
    private Logger logger = LoggerFactory.getLogger(JSWSWxSignIntegralZeroTask.class);
    
    /**
     * 积分每月1号清零
     */
    @Scheduled(cron="0 0 0 1 * ?")
    public void integralZero() {
        logger.info("-------绝世武神微信签到定时任务（积分每月1号零点积分清零） 开始--------");
        jSWSWxSignService.updateZeroInMonthStart();
        logger.info("-------绝世武神微信签到定时任务（积分每月1号零点积分清零）结束--------");
    }

}
