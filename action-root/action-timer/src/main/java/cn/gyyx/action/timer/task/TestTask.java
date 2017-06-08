package cn.gyyx.action.timer.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.gyyx.action.service.jswswxsign.JSWSWxSignService;
import cn.gyyx.action.service.xlsgwxsign.XLSGWxSignService;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年11月13日 下午6:32:00
 * 描        述： 测试，最好只测试read数据库
 */
@Component
public class TestTask { 

    private JSWSWxSignService jSWSWxSignService = new JSWSWxSignService();
    private XLSGWxSignService xLSGWxSignService = new XLSGWxSignService();
    
    private Logger logger = LoggerFactory.getLogger(TestTask.class);
    
    /**
     * 测试读取数据库
     */
    //@Scheduled(cron="*/30 * * * * ?")
    public void integralZero() throws Exception {
        logger.info("-------测试读取数据库开始--------");
        jSWSWxSignService.getUserScore("1");
        xLSGWxSignService.getUserScore("1");
        logger.info("-------测试读取数据库结束--------");
    }

}
