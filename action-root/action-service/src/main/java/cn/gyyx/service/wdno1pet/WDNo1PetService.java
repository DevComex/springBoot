/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：Chen 
 * 联系方式：chenpeng03@gyyx.cn 
 * 创建时间： 2014年12月15日 下午2:16:18
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 问道 “天下第一宠” 活动 Service 层
-------------------------------------------------------------------------*/
package cn.gyyx.service.wdno1pet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdno1pet.CommentBean;
import cn.gyyx.action.beans.wdno1pet.Pagination;
import cn.gyyx.action.beans.wdno1pet.WDNo1PetInfoBean;
import cn.gyyx.action.bll.wdno1pet.CommentBLL;
import cn.gyyx.action.bll.wdno1pet.PetInfoBLL;
import cn.gyyx.action.bll.wdno1pet.Validation;
import cn.gyyx.action.bll.wdno1pet.VoteBLL;
import cn.gyyx.action.service.agent.CallWebAPIUserInfoAgent;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

public class WDNo1PetService {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDNo1PetService.class);

	private PetInfoBLL petInfoBLL = new PetInfoBLL();
	private CommentBLL commentBLL = new CommentBLL();
	private VoteBLL voteBLL = new VoteBLL();

	/**
	 * 返回宠物实体
	 * 
	 * @param pCode
	 * @return
	 */
	public WDNo1PetInfoBean getPetInfoByPetCode(String pCode) {
		// TODO Auto-generated method stub
		int pcode = Integer.parseInt(pCode);
		return petInfoBLL.getPetInfoByPetCode(pcode);
	}

	/**
	 * 上传一个参赛作品到数据库
	 * 
	 * @param petInfo
	 *            参赛作品实体
	 */
	public ResultBean<Integer> uploadPetInfo(HttpServletRequest request,
			WDNo1PetInfoBean petInfo) {
		logger.debug("宠物作品信息", petInfo);
		ResultBean<Integer> msg = new ResultBean<Integer>();
		String checkInfo = Validation.checkBean(petInfo);
		if (Validation.BEAN_CHECK_SUCCESS.equals(checkInfo)) {
			UserInfo userInfo;
			try {
				userInfo = SignedUser.getUserInfo(request);
				logger.debug("" + userInfo.getUserId());
				int count = petInfoBLL.getPetCountByUserCode(userInfo
						.getUserId());
				if (count < 5) {
					petInfoBLL.uploadPetInfo(petInfo);
					msg.setIsSuccess(true);
					msg.setMessage("您已经成功提交，作品在审核后会展示在前台");
				} else {
					msg.setIsSuccess(false);
					msg.setMessage("您的作品提交数量已经达到上限，感谢您的参与");
				}
			} catch (NullPointerException e) {
				logger.warn("[uploadInfo]NullPointerException in geting userInfo");
				msg.setIsSuccess(false);
				msg.setMessage("请先登录！");
			}
		} else {
			msg.setIsSuccess(false);
			msg.setMessage(checkInfo);
		}
		logger.debug("上传信息的返回", msg);
		return msg;
	}

	public Pagination<CommentBean> getComments(String pCode, String page) {
		return commentBLL.getComments(pCode, page);
	}

	/**
	 * 根据宠物主键获得相应的投票账户列表
	 * 
	 * @param pCode
	 * @return
	 */
	public List<String> getVoteAccountsByPetId(String pCode) {
		logger.debug("宠物code", pCode);
		List<Integer> userCodes = voteBLL.getVoteUserCodes(pCode, 6);
		List<String> accounts = new ArrayList<String>();
		for (Integer userCode : userCodes) {
			JSONObject json = CallWebAPIUserInfoAgent
					.getUserBasicInfoByUserId(userCode);
			String account = null;
			try {
				account = json.get("Account").toString();
			} catch (Exception e) {
				logger.warn(e.getMessage());
				account = "";
				e.printStackTrace();
			}
			account = changeAccountWithStar(account);
			accounts.add(account);
		}
		logger.debug("根据宠物主键获得相应的投票账户列表", accounts);
		return accounts;
	}

	/**
	 * 对用户名进行加*处理
	 * 
	 * @param account
	 * @return
	 */
	private String changeAccountWithStar(String account) {
		logger.debug("账号信息", account);
		char[] c = account.toCharArray();
		int starNum = 0;
		String staredName = "";
		for (int i = 0; i < c.length; i++) {
			if (!(0 == i | 1 == i | c.length - 1 == i | c.length - 2 == i)) {
				c[i] = '*';
			} else {
				starNum++;
			}
		}
		staredName = new String(c);
		if (starNum > 13) {
			staredName = staredName.substring(0, 1) + "*************"
					+ staredName.substring(c.length - 2, c.length - 1);
		}
		logger.debug("加密后的姓名", staredName);
		return staredName;
	}

}
