package cn.gyyx.action.service.wdtoplevel;

import javax.servlet.http.HttpServletRequest;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdtoplevel.AddAppointmentBean;
import cn.gyyx.action.beans.wdtoplevel.AppointmentBean;
import cn.gyyx.action.dao.wdtoplevel.AppointmentDao;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

public class AppointmentService {
	AppointmentDao dao = new AppointmentDao();
	public ResultBean<String> addAppointment(AddAppointmentBean addBean,HttpServletRequest request){
		ResultBean<String> result = new ResultBean<String>();
		if(request.getCookies() == null){
			result.setMessage("请先登录");
			return result;
		}
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if(userInfo == null) {
			result.setMessage("请先登录");
			return result;
		}
		String account = userInfo.getAccount();
		if(dao.isAccountExists(account)){
			result.setMessage("每个账号只能申请1次，您已提交过冲级赛预约申请。");
			return result;
		}
		if(!addBean.check().equals(AddAppointmentBean.CHECK_SUCCESS)){
			result.setMessage(addBean.check());
			return result;
		}
		AppointmentBean app = new AppointmentBean(addBean);
		app.setAccount(account);
		result.setMessage("申请成功，感谢你的参与!");
		dao.addAppointment(app);
		return result;
	}
}
