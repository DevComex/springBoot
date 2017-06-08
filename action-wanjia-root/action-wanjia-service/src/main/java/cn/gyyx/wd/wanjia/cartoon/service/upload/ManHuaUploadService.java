/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-6
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画上传用户作者信息的Service
 */
package cn.gyyx.wd.wanjia.cartoon.service.upload;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.wd.wanjia.ResultBean;
import cn.gyyx.wd.wanjia.ServerBean;
import cn.gyyx.wd.wanjia.cartoon.beans.UploadFormBean;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCategory;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;

public interface ManHuaUploadService {
	/**
	 * 获取服务器信息 1网通 2电信3双线
	 * 
	 * @param netType
	 * @return
	 */
	public ResultBean<ServerBean> serverlist(int netType);

	/**
	 * 查询当前登陆玩家的所有漫画列表
	 * 
	 * @param userInfo
	 * @return
	 */
	public List<WanwdManhua> getManHua(UserInfo userInfo);

	/**
	 * 验证漫画title是否合法
	 * 
	 * @param userInfo
	 * @param title
	 * @return
	 */
	public ResultBean<Map<String,Object>> checkManHuaTitle(UserInfo userInfo, String title);

	/**
	 * 获取漫画所有类型
	 * 
	 * @return
	 */
	public List<WanwdCategory> getManHuaAllType();

	/**
	 * 存储上传数据
	 * 
	 * @param form
	 * @param info
	 * @param pagename
	 * @param pageurl
	 * @param request
	 * @param response
	 * @return
	 */

	public ResultBean<Map<String, String>> uploadSave(UploadFormBean form, UserInfo info, List<String> pagename,
			List<String> pageurl, Boolean captchaCheck);

}
