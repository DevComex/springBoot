/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午6:46:51
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.service.challenger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.challenger.ChallenterUserInfoBean;
import cn.gyyx.action.beans.challenger.OperationLogBean;
import cn.gyyx.action.beans.lottery.LuckyDrawLogBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.bll.challenger.ChallenterUserInfoBll;
import cn.gyyx.action.bll.challenger.OperationLogBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class UserInfoService {
	private ChallenterUserInfoBll challenterUserInfoBll = new ChallenterUserInfoBll();
	private OperationLogBll operationLogBll = new OperationLogBll();

	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(UserInfoService.class);

	/***
	 * 审核通过用户报名信息
	 * 
	 * @return
	 */
	public ResultBean<String> passUserInfo(int code, int opCode,
			String userName, int actionCode) {
		try (SqlSession sqlSession = getSession()) {
			try (DistributedLock lock = new DistributedLock("passUserInfo"
					+ code + "." + actionCode)) {
				lock.weakLock(30, 2);
				ChallenterUserInfoBean uncheckchallenterUserInfo = challenterUserInfoBll
						.getCodeChallenterUserInfo(code, "oncheck");
				ChallenterUserInfoBean passcheckchallenterUserInfo = challenterUserInfoBll
						.getCodeChallenterUserInfo(code, "pass");
				// 审核通过的内部实现方法
				return passUserInfoStory(actionCode, opCode, code, userName,
						uncheckchallenterUserInfo, passcheckchallenterUserInfo,
						sqlSession);
			} catch (Exception e) {
				logger.error("passUserInfo error" + e);
				sqlSession.rollback();
				return new ResultBean<>(false, "审核失败", "");

			}
		}
	}

	/***
	 * 审核通过用户报名信息的内部实现方法
	 * 
	 * @return
	 */
	private ResultBean<String> passUserInfoStory(int actionCode, int opcode,
			int code, String userName,
			ChallenterUserInfoBean uncheckchallenterUserInfo,
			ChallenterUserInfoBean passcheckchallenterUserInfo,
			SqlSession sqlSession) {
		// 你总得判断下吧
		if (passcheckchallenterUserInfo != null) {
			return new ResultBean<>(false, "该信息已经审核过了", "");
		}
		if (uncheckchallenterUserInfo == null) {
			return new ResultBean<>(false, "就没有待审核报名信息", "");
		}
		// 审核
		int status = challenterUserInfoBll.setUserInfoState(code, "pass",
				sqlSession);
		if (status == 1) {
			LuckyDrawLogBean logBean = new LuckyDrawLogBean();
			logBean.setAccount(uncheckchallenterUserInfo.getAccount());
			logBean.setActionCode(actionCode);
			logBean.setDrawCount(1);
			logBean.setSource("signUserInfo");
			logBean.setUserId(uncheckchallenterUserInfo.getUserId());
			// 这就得加抽奖次数了
			LotteryService.setLotteryTimesSqlSession(logBean, sqlSession);
			// 加入操作日志
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(userName);
			operateBean.setUserid(opcode);
			operateBean.setType("userInfoCheck");
			operateBean.setTid(code);
			operateBean.setActionCode(actionCode);
			operateBean.setDescription("报名信息审核通过");
			operationLogBll.addOperationLog(operateBean, sqlSession);
			sqlSession.commit(true);
			return new ResultBean<>(true, "审核通过", "");
		} else {
			sqlSession.rollback();
			return new ResultBean<>(false, "审核不正确", "");
		}
	}

	/***
	 * 拒绝用户报名信息
	 * 
	 * @return
	 */
	public ResultBean<String> refuseUserInfo(int code, int opcode,
			String userName, int actionCode) {
		try (SqlSession sqlSession = getSession()) {
			try (DistributedLock lock = new DistributedLock("refuseUserInfo"
					+ code + "." + "actionCode")) {
				lock.weakLock(30, 2);
				ChallenterUserInfoBean uncheckchallenterUserInfo = challenterUserInfoBll
						.getCodeChallenterUserInfo(code, "oncheck");
				ChallenterUserInfoBean passcheckchallenterUserInfo = challenterUserInfoBll
						.getCodeChallenterUserInfo(code, "pass");
				// 审核通过的内部实现方法
				return refuseUserInfoStory(actionCode, code, opcode, userName,
						uncheckchallenterUserInfo, passcheckchallenterUserInfo,
						sqlSession);
			} catch (Exception e) {
				logger.error("passUserInfo error" + e);
				sqlSession.rollback();
				return new ResultBean<>(false, "审核失败", "");

			}
		}
	}

	/***
	 * 审核通过用户报名信息的内部实现方法
	 * 
	 * @return
	 */
	private ResultBean<String> refuseUserInfoStory(int actionCode, int code,
			int opcode, String userName,
			ChallenterUserInfoBean uncheckchallenterUserInfo,
			ChallenterUserInfoBean passcheckchallenterUserInfo,
			SqlSession sqlSession) {
		// 你总得判断下吧
		if (passcheckchallenterUserInfo != null) {
			return new ResultBean<>(false, "已经有审核通过的报名信息了", "");
		}
		if (uncheckchallenterUserInfo == null) {
			return new ResultBean<>(false, "就没有待审核报名信息", "");
		}
		// 审核
		int status = challenterUserInfoBll.setUserInfoState(code, "unpass",
				sqlSession);

		// 加入操作日志
		if (status == 1) {
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(userName);
			operateBean.setUserid(opcode);
			operateBean.setType("userInfoCheck");
			operateBean.setTid(code);
			operateBean.setActionCode(actionCode);
			operateBean.setDescription("拒绝报名信息");
			operationLogBll.addOperationLog(operateBean, sqlSession);
			sqlSession.commit(true);
			return new ResultBean<>(true, "拒绝成功", "");
		} else {
			sqlSession.rollback();
			return new ResultBean<>(true, "审核不正确", "");
		}
	}

	/***
	 * 获取用户信息
	 */
	public ResultBean<Map<String, String>> getUserInfo(
			ChallenterUserInfoBean userInfo) {
		ResultBean<Map<String, String>> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "获取用户信息失败", null);
		Map<String, String> map = new HashMap<>();
		try {
			ChallenterUserInfoBean ub = challenterUserInfoBll
					.getUserInfoBean(userInfo);
			if (ub != null) {
				if (ChallengerConstant.userState.oncheck.toString().equals(
						ub.getState())) {
					resultBean.setMessage("报名审核中,请等待");
					return resultBean;
				}
				map.put("roleName", ub.getRoleName());
				map.put("contact", ub.getContact());
				map.put("contactType", ub.getContactType());
			}
			resultBean.setProperties(true, "获取用户信息成功", map);
		} catch (Exception e) {
			logger.error("问道新世界挑战赛[获取用户信息]", e);
		}
		return resultBean;
	}

	/**
	 * 根据id获取用户信息
	 */
	public ChallenterUserInfoBean getUserInfoBean(int userId) {
		ChallenterUserInfoBean s = new ChallenterUserInfoBean();
		s.setUserId(userId);
		return challenterUserInfoBll.getUserInfoBean(s);
	}

	/**
	 * 立即报名
	 */
	public ResultBean<String> entry(ChallenterUserInfoBean userInfoBean) {
		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "报名失败", "");
		try {
			if (userInfoBean.getUserId() == 0) {
				resultBean.setMessage("用户不能为空");
				return resultBean;
			}
			if (StringUtils.isBlank(userInfoBean.getRoleName())) {
				resultBean.setMessage("预建角色不能为空");
				return resultBean;
			}
			if (StringUtils.isBlank(userInfoBean.getContactType())) {
				resultBean.setMessage("联系方式类型不能为空");
				return resultBean;
			}
			if (StringUtils.isBlank(userInfoBean.getContact())) {
				resultBean.setMessage("联系方式不能为空");
				return resultBean;
			}
			try (DistributedLock lock = new DistributedLock(
					ChallengerConstant.MEM_KEY_PREFIX + "signUp_"
							+ userInfoBean.getUserId())) {
				lock.weakLock(30, 11);
				ChallenterUserInfoBean queryUserInfoBean = new ChallenterUserInfoBean();
				queryUserInfoBean.setUserId(userInfoBean.getUserId());
				ChallenterUserInfoBean ub = challenterUserInfoBll
						.getUserInfoBean(queryUserInfoBean);
				if (ub != null) {
					if (ChallengerConstant.userState.oncheck.toString().equals(
							ub.getState())) {
						resultBean.setMessage("报名审核中,请等待");
						return resultBean;
					}
					if (ChallengerConstant.userState.pass.toString().equals(
							ub.getState())) {
						resultBean.setMessage("报名已通过,不允许多次报名");
						return resultBean;
					}
				}

				queryUserInfoBean = new ChallenterUserInfoBean();
				queryUserInfoBean.setRoleName(userInfoBean.getRoleName());
				int count = challenterUserInfoBll
						.getUserInfoCount(queryUserInfoBean);
				if (count != 0) {
					resultBean.setMessage("角色重复,请重新填写");
					return resultBean;
				}

				userInfoBean.setCreateTime(new Date());
				userInfoBean.setState(ChallengerConstant.userState.oncheck
						.toString());
				challenterUserInfoBll.insert(userInfoBean);
				resultBean.setProperties(true, "报名成功", "");
			} catch (Exception e) {
				throw e;
			}
		} catch (Exception e) {
			logger.error("问道新世界挑战赛[报名失败]", e);
		}
		return resultBean;
	}

	/**
	 * 检查用户状态
	 */
	public ResultBean<String> checkUserInfo(ChallenterUserInfoBean userInfo) {
		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "检查用户状态失败", null);
		try {
			ChallenterUserInfoBean ub = challenterUserInfoBll
					.getUserInfoBean(userInfo);
			if (ub != null) {
				if (ChallengerConstant.userState.oncheck.toString().equals(
						ub.getState())) {
					resultBean.setProperties(false, "报名审核中,请等待", "oncheck");
					return resultBean;
				} else {
					resultBean.setProperties(false, "用户已成功报名", "signed");
					return resultBean;
				}
			}
			resultBean.setProperties(true, "用户未成功报名", "unsign");
		} catch (Exception e) {
			logger.error("问道新世界挑战赛[检查用户状态失败]", e);
		}
		return resultBean;
	}

	/***
	 * 分页获取所有未通过的信息
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public ResultBeanWithPage<List<ChallenterUserInfoBean>> getAllUnPassChallenterUserInfo(
			int pageSize, int pageNo) {
		try {
			List<ChallenterUserInfoBean> list = challenterUserInfoBll
					.getStatusChallenterUserInfo(pageNo, pageSize, "oncheck");
			int count = challenterUserInfoBll
					.getCountStatusChallenterUserInfo("oncheck");
			return new ResultBeanWithPage<>(true, "获取成功", list, count);
		} catch (Exception e) {
			logger.error("getAllUnPassChallenterUserInfo" + e);
			return new ResultBeanWithPage<>(false, "获取失败", null, 0);
		}

	}

}
