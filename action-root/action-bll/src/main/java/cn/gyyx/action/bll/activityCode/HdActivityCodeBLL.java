package cn.gyyx.action.bll.activityCode;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.activityCode.PresentMesBean;
import cn.gyyx.action.dao.activityCode.HdActivityCodeDAO;

public class HdActivityCodeBLL {
	private HdActivityCodeDAO hdActivityCodeDAO = new HdActivityCodeDAO();
	public PresentMesBean getPresent(int activityCode) {
		boolean isSuccess = false; 
		PresentMesBean result = hdActivityCodeDAO.getPresent(activityCode);
		if(result==null){
			return null;
		}
		isSuccess = deleteActivityCode(result.getCode());
		if(isSuccess){
			return result;
		}else{
			return null;
		}
	}
	public boolean deleteActivityCode(int code){
		int count = hdActivityCodeDAO.deleteActivityCode(code);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
	public ResultBean<String> getReceiveCount(String actionCode) {
		ResultBean<String> resultBean = new ResultBean<String>();
		Integer result = hdActivityCodeDAO.getReceiveCount(actionCode);
		if(result==null){
			resultBean.setProperties(false, "error", "出错啦..!!");
		}else{
			resultBean.setProperties(true, actionCode, result.toString());
		}
		return resultBean;
	}
}
