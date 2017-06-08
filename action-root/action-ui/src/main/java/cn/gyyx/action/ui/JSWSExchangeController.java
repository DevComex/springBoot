/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年1月25日下午2:53:33
 * 版本号：v1.0
 * 本类主要用途叙述：绝世武神积分兑换控制器
 */

package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

@Controller
@RequestMapping(value = "/JSWSExchange")
public class JSWSExchangeController {

    /**
     * 
     * @日期：2015年3月19日
     * @Title: getSession
     * @Description: TODO 获取session
     * @return SqlSession
     */
    private SqlSession getSession() {
        SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
                .getSqlActionDBV2SessionFactory();
        return sqlSessionFactory.openSession();
    }

    /**
     * 默认活动页面
     * 
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request) {

        return "jswsChangePrize/index";
    }

    /***
     * 获取用户的积分，并判断用户的积分是否可以兑换对应的奖品
     * 
     * @param model
     * @param request
     * @param openId
     * @param thing
     * @return
     */
    @RequestMapping("/getScore")
    @ResponseBody
    public ResultBean<Integer> getScore(HttpServletRequest request) {
        ResultBean<Integer> resultBean = new ResultBean<Integer>(false,
                "谢谢参与，活动已结束", null);
        return resultBean;
    }

    /***
     * 获取奖品
     * 
     * @param model
     * @param request
     * @param openId
     * @param thing
     * @param phone
     * @return
     */
    @RequestMapping(value = "/getThing", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> getThing(HttpServletRequest request) {
        ResultBean<String> resultBean = new ResultBean<String>(false,
                "谢谢参与，活动已结束", null);
        return resultBean;
    }

}
