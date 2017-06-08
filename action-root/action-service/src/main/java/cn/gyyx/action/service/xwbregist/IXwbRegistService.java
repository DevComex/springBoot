package cn.gyyx.action.service.xwbregist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gyyx.action.beans.ResultBean;

/**
 * 炫舞吧mobile注册
 * @ClassName: IXwbRegistService
 * @description IXwbRegistService
 * @author luozhenyu
 * @date 2016年11月29日
 */
public interface IXwbRegistService {

	/**
	 * 注册到社区
	 * @param phone
	 * @param password
	 * @param verifyCode
	 * @param request
	 * @param response
	 * @return
	 */
	public ResultBean<String> longinAction(String phone,String password,String verifyCode,HttpServletRequest request,HttpServletResponse response);
}
