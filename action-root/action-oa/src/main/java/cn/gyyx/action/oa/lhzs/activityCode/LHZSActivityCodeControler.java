package cn.gyyx.action.oa.lhzs.activityCode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.bll.activityCode.HdActivityCodeBLL;

@Controller
@RequestMapping("/LHZSOA")
public class LHZSActivityCodeControler {
	@ResponseBody
	@RequestMapping("/getCount")
	public ResultBean<String> getCount(String actionCode ){
		ResultBean<String> result = new ResultBean<String>();
		int actionCodeInt = 0;
		try{
			actionCodeInt = Integer.parseInt(actionCode);
		}catch(Exception e){
			result.setProperties(false, "errorActionCode", "请填写正确的活动号！！");
		}
		result = new HdActivityCodeBLL().getReceiveCount(String.valueOf(actionCodeInt));
		return result;
	}
	
	@RequestMapping("/index")
	public String index(){
		return "activityCode/getCount";
	}
}
