package cn.gyyx.action.ui.wdnirvana;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.text.SimpleDateFormat;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

/**
 * 类:WdNirvanaController
 * 叙述:问道2017浴火重生活动
 * 注释日期:2016年12月23日
 *
 * @author ZhangShaolei
 */
@Controller
@RequestMapping(value={"/2017/back","/2017back"})
public class WdNirvanaController {
    /**
     * 活动首页
     *
     * @return
     * @throws ParseException 
     */
    @RequestMapping(value = "/lottery", method = RequestMethod.GET)
    public String lottery(@RequestParam(required=false,value="gwqx")String gw) throws ParseException {
    	Calendar nowc = Calendar.getInstance();
    	Calendar startc = Calendar.getInstance();
    	Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-1-6 12:00:00");
    	startc.setTime(parse);
    	if(nowc.after(startc)){
    		return "wdnirvana/lottery";
    	}
		if(!StringUtils.isEmpty(gw)){
			return "wdnirvana/lottery";
		}

    	return "redirect:http://wd.gyyx.cn/huodong/wdxf/tf/2017/yhcs.html";
    	
    }

    /**
     * 获取用户活动信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAccountInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getAccountInfo(HttpServletRequest request) {
        ResultBean<Object> result = new ResultBean<Object>(false,"活动已结束，谢谢参与",null);
        UserInfo userInfo = null;
        try {
            userInfo = SignedUser.getUserInfo(request);
            if (userInfo == null) {
                return new ResultBean<>(false, "无法探测到您的用户信息，请重新登陆", null);
            }
        } catch (Exception e) {
            return new ResultBean<>(false, "无法探测到您的用户信息，请重新登陆", null);
        }
        return result;
    }

    /**
     * 抽奖
     *
     * @param request
     */
    @RequestMapping(value = "draw", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> draw(HttpServletRequest request) {
        ResultBean<Object> result = new ResultBean<Object>(false,"活动已结束，谢谢参与",null);
        UserInfo userInfo = null;
        try {
            userInfo = SignedUser.getUserInfo(request);
            if (userInfo == null) {
                return new ResultBean<>(false, "无法探测到您的用户信息，请重新登陆", null);
            }
        } catch (Exception e) {
            return new ResultBean<>(false, "无法探测到您的用户信息，请重新登陆", null);
        }
        return result;
    }

    /**
     * 修改收货地址
     *
     * @param address
     * @param request
     * @return
     */
    @RequestMapping(value = "/resetAddress", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> resetAddress(@ModelAttribute AddressBean address, HttpServletRequest request) {
        ResultBean<Object> result = new ResultBean<Object>(false,"活动已结束，谢谢参与",null);
        UserInfo userInfo = null;
        try {
            userInfo = SignedUser.getUserInfo(request);
            if (userInfo == null) {
                return new ResultBean<>(false, "无法探测到您的用户信息，请重新登陆", null);
            }
        } catch (Exception e) {
            return new ResultBean<>(false, "无法探测到您的用户信息，请重新登陆", null);
        }
        return result;
    }

    /**
     * 获取所有用户中奖信息接口
     */
    @RequestMapping(value = "/getSendPresentLog", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getSendPresentLog() {
        ResultBean<Object> result = new ResultBean<Object>(false,"活动已结束，谢谢参与",null);
        return result;
    }
}
