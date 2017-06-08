/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月30日上午10:09:02
 * 版本号：v1.0
 * 本类主要用途叙述：粉丝榜的业务拼装层
 */

package cn.gyyx.action.service.wdtenyearfans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.wdtenyearfans.AccountVoteInfoBean;
import cn.gyyx.action.beans.wdtenyearfans.WdAccountScoreBean;
import cn.gyyx.action.beans.wdtenyearfans.WdCommentsBean;
import cn.gyyx.action.beans.wdtenyearfans.WdNominationInfoBean;
import cn.gyyx.action.beans.wdtenyearfans.WdNominationShowBean;
import cn.gyyx.action.beans.wdtenyearfans.WdVoteInfoBean;
import cn.gyyx.action.bll.wdtenyearfans.WDBackstage;
import cn.gyyx.action.bll.wdtenyearfans.WdCommentsBll;
import cn.gyyx.action.bll.wdtenyearfans.WdNominationInfoBll;
import cn.gyyx.action.bll.wdtenyearfans.WdVoteInfoBll;
import cn.gyyx.action.service.WdTenYearService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.UserInfo;

public class FansRollService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(FansRollService.class);
	private WdNominationInfoBll wdNominationInfoBll = new WdNominationInfoBll();
	private WdVoteInfoBll wdVoteInfoBll = new WdVoteInfoBll();
	private WdCommentsBll wdCommentsBll = new WdCommentsBll();
	private WDBackstage wDBackstage = new WDBackstage();

	/***
	 * 增加评论
	 * 
	 * @param nominationCode
	 * @param commentsContent
	 * @return
	 */
	public ResultBean<String> addComment(int nominationCode,
			String commentsContent, UserInfo userInfo) {
		ResultBean<String> resultBean = new ResultBean<>();
		try {
			WdCommentsBean wdCommentsBean = new WdCommentsBean();
			// 用户名
			if (userInfo != null) {
				wdCommentsBean.setAccountName(userInfo.getAccount());
			} else {
				wdCommentsBean.setAccountName("");
			}
			// 作品主键
			wdCommentsBean.setNominationCode(nominationCode);
			// 时间
			wdCommentsBean.setCommentsDate(new Date());
			// 评论
			wdCommentsBean.setCommentsContent(commentsContent);
			// 状态
			wdCommentsBean.setCheckFlag(0);
			wdCommentsBll.addtWdCommentsBean(wdCommentsBean);
			resultBean.setProperties(true, "评论成功", "");
		} catch (Exception e) {
			logger.warn("addComment" + e);
			resultBean.setProperties(false, "评论出现问题", "");
		}

		return resultBean;
	}

	/***
	 * 获取某账号下对作品的关注或拉黑情况
	 * 
	 * @param account
	 * @return
	 */
	public ResultBean<AccountVoteInfoBean> getAccountVoteInfoBean(String account) {
		ResultBean<AccountVoteInfoBean> resultBean = new ResultBean<>();
		AccountVoteInfoBean accountVoteInfoBean = new AccountVoteInfoBean();
		try {
			// 账号
			accountVoteInfoBean.setAccount(account);
			// 获取账户的投票信息
			List<WdVoteInfoBean> list = wdVoteInfoBll
					.getWdVoteInfoBeanByAccount(account);
			// 如果没有投票信息
			if (list == null || list.isEmpty()) {
				resultBean.setProperties(true, "获取成功啦", null);
				return resultBean;
			} else {
				// 黑白粉作品获取
				accountVoteInfoBean = getVoteList(accountVoteInfoBean, list);
				resultBean.setProperties(true, "获取信息成功", accountVoteInfoBean);
			}
		} catch (Exception e) {
			logger.warn("getAccountVoteInfoBean" + e);
			resultBean.setProperties(false, "获取失败", null);
		}

		return resultBean;
	}

	/***
	 * 黑白粉作品获取
	 * 
	 * @param accountVoteInfoBean
	 * @param list
	 * @return
	 */
	private AccountVoteInfoBean getVoteList(
			AccountVoteInfoBean accountVoteInfoBean, List<WdVoteInfoBean> list) {
		AccountVoteInfoBean temp = accountVoteInfoBean;
		// 关注作品
		List<WdNominationInfoBean> whitesBeans = new ArrayList<>();
		// 拉黑作品
		List<WdNominationInfoBean> blackBeans = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			// 获取作品信息
			WdNominationInfoBean wInfoBean = wdNominationInfoBll
					.getWdNominationInfoBeanByCode(list.get(i)
							.getNominationCode());
			// 白粉
			if (list.get(i).getVoteType() == 0) {
				whitesBeans.add(wInfoBean);
			}
			// 黑粉
			else if (list.get(i).getVoteType() == 1) {
				blackBeans.add(wInfoBean);

			}
			// 黑粉
			temp.setBlackBeans(blackBeans);
			// 白粉
			temp.setWhitesBeans(whitesBeans);
		}

		return temp;
	}

	/***
	 * 增加投票信息
	 * 
	 * @param nominationCode
	 * @param type
	 * @return
	 */
	public ResultBean<String> addVote(int nominationCode, int type,
			String accountName, String ip, int userId) {
		// 判断是否有资格投票
		ResultBean<String> resultBean = isCanVode(accountName, nominationCode,
				type);
		try {
			if (!resultBean.getIsSuccess()) {
				return resultBean;
			}
			// 进行拉黑或者关注操作
			// 增加提名表的票数
			wdNominationInfoBll.setVoteNum(type, nominationCode);
			// 设定投票信息
			WdVoteInfoBean wdVoteInfoBean = new WdVoteInfoBean();
			// 账号
			wdVoteInfoBean.setAccountName(accountName);
			// 时间
			wdVoteInfoBean.setDate(new Date());
			// 提名作品
			wdVoteInfoBean.setNominationCode(nominationCode);
			// ip 地址
			wdVoteInfoBean.setVoteIp(ip);
			// 投票类型
			wdVoteInfoBean.setVoteType(type);
			wdVoteInfoBll.insertWdVoteInfoBean(wdVoteInfoBean);
			WdAccountScoreBean wdAccountScoreBean = wDBackstage
					.selectWdAccountScoreBeanByAccount(accountName);
			wdAccountScoreBean.setScore(wdAccountScoreBean.getScore() + 3);
			wDBackstage.updateWdAccountScoreBean(wdAccountScoreBean);
			WdTenYearService.setWdtenYearScore(3, userId, 346, accountName, ip);
			resultBean.setProperties(true, "投票成功", null);
		} catch (Exception e) {
			logger.warn("addVote" + e);
			resultBean.setProperties(false, "投票失败", null);
		}
		return resultBean;
	}

	/****
	 * 判断是否可以进行投票
	 * 
	 * @param userInfo
	 * @param nominationCode
	 * @return
	 */
	private ResultBean<String> isCanVode(String accountName,
			int nominationCode, int type) {
		ResultBean<String> resultBean = new ResultBean<>(false, "初始化参数",
				"初始化参数");
		// 判断投票的类型
		if (type != 0 && type != 1) {
			return new ResultBean<>(false, "没有这种操作啊", null);
		}
		// 获取总投票数
		int allVoteCount = wdVoteInfoBll
				.getCountWdVoteInfoBeanByAccount(accountName);
		// 判断总投票数是否超过标准
		if (allVoteCount >= 10) {
			return new ResultBean<>(false, "您的拉黑和关注的次数综合已经大于10，不能进行任何相关操作",
					null);
		}
		// 观察这货是否已对该提名拉黑或者关注
		WdVoteInfoBean wdVoteInfoBean = wdVoteInfoBll
				.getWdVoteInfoBeanByAccountAndNominationCode(accountName,
						nominationCode);
		// 存在信息
		if (wdVoteInfoBean != null) {
			return new ResultBean<>(false, "您已经对该作品拉黑或者关注，谢谢", null);
		}
		resultBean.setProperties(true, "可以投票", null);
		return resultBean;
	}

	/***
	 * 根据角色名模糊筛选
	 * 
	 * @param roleName
	 * @return
	 */
	public ResultBeanWithPage<List<WdNominationShowBean>> getNominationShowByRole(
			String roleName, int pageSize, int pageNo, String accountName) {
		ResultBeanWithPage<List<WdNominationShowBean>> resultBeanWithPage = new ResultBeanWithPage<>();
		try {
			// 模糊筛选出该角色的提名
			List<WdNominationInfoBean> listWdNominationInfo = wdNominationInfoBll
					.getWdNominationInfoBeansByRoleName(roleName, pageSize,
							pageNo, 1);
			WdVoteInfoBean wdVoteInfoBean = new WdVoteInfoBean();
			wdVoteInfoBean.setAccountName(accountName);
			// 拼装组合出展示信息
			List<WdNominationShowBean> list = getWdNominationShowBeans(
					listWdNominationInfo, wdVoteInfoBean);
			// 获取根据角色和状态筛选所有信息的数量
			int allCount = wdNominationInfoBll
					.getCountWdNominationInfoBeansByRoleName(roleName, 1);
			resultBeanWithPage.setProperties(true, "获取成功啊", list, allCount);
		} catch (Exception e) {
			logger.warn(e + "getNominationShowByRole");
			resultBeanWithPage.setProperties(false, "信息不存在啊", null, 0);
		}
		return resultBeanWithPage;
	}

	/***
	 * 根据时间顺序排序
	 * 
	 * @param roleName
	 * @return
	 */
	public ResultBeanWithPage<List<WdNominationShowBean>> getNominationShowByOrder(
			int pageSize, int pageNo, String accountName) {
		ResultBeanWithPage<List<WdNominationShowBean>> resultBeanWithPage = new ResultBeanWithPage<>();
		try {
			// 模糊筛选出该角色的提名
			List<WdNominationInfoBean> listWdNominationInfo = wdNominationInfoBll
					.getWdNominationInfoBeansByOrder(pageSize, pageNo, 1);
			WdVoteInfoBean wdVoteInfoBean = new WdVoteInfoBean();
			wdVoteInfoBean.setAccountName(accountName);
			// 拼装组合出展示信息
			List<WdNominationShowBean> list = getWdNominationShowBeans(
					listWdNominationInfo, wdVoteInfoBean);
			// 获取根据角色和状态筛选所有信息的数量
			int allCount = wdNominationInfoBll.getCountWdNominationInfoBeans(1);
			resultBeanWithPage.setProperties(true, "获取信息成功", list, allCount);
		} catch (Exception e) {
			logger.warn(e + "getNominationShowByRole");
			resultBeanWithPage.setProperties(false, "信息沒查詢到", null, 0);
		}
		return resultBeanWithPage;
	}

	/***
	 * 根据白粉数排序
	 * 
	 * @param roleName
	 * @return
	 */
	public ResultBeanWithPage<List<WdNominationShowBean>> getWdNominationInfoBeansByWhite(
			int pageSize, int pageNo, String accountName) {
		ResultBeanWithPage<List<WdNominationShowBean>> resultBeanWithPage = new ResultBeanWithPage<>();
		try {
			// 模糊筛选出该角色的提名
			List<WdNominationInfoBean> listWdNominationInfo = wdNominationInfoBll
					.getWdNominationInfoBeansByWhite(pageSize, pageNo, 1);
			WdVoteInfoBean wdVoteInfoBean = new WdVoteInfoBean();
			wdVoteInfoBean.setAccountName(accountName);
			// 拼装组合出展示信息
			List<WdNominationShowBean> list = getWdNominationShowBeans(
					listWdNominationInfo, wdVoteInfoBean);
			// 获取根据角色和状态筛选所有信息的数量
			int allCount = wdNominationInfoBll.getCountWdNominationInfoBeans(1);
			if (allCount > 30) {
				allCount = 30;
			}
			resultBeanWithPage.setProperties(true, "获取成功", list, allCount);

		} catch (Exception e) {
			logger.warn(e + "getWdNominationInfoBeansByWhite");
			resultBeanWithPage.setProperties(false, "信息不存在", null, 0);
		}

		return resultBeanWithPage;
	}

	/***
	 * 根据黑粉数排序
	 * 
	 * @param roleName
	 * @return
	 */
	public ResultBeanWithPage<List<WdNominationShowBean>> getWdNominationInfoBeansByBlack(
			int pageSize, int pageNo, String accountName) {
		ResultBeanWithPage<List<WdNominationShowBean>> resultBeanWithPage = new ResultBeanWithPage<>();
		try {
			// 模糊筛选出该角色的提名
			List<WdNominationInfoBean> listWdNominationInfo = wdNominationInfoBll
					.getWdNominationInfoBeansByBlack(pageSize, pageNo, 1);
			WdVoteInfoBean wdVoteInfoBean = new WdVoteInfoBean();
			wdVoteInfoBean.setAccountName(accountName);
			// 拼装组合出展示信息
			List<WdNominationShowBean> list = getWdNominationShowBeans(
					listWdNominationInfo, wdVoteInfoBean);
			// 获取根据角色和状态筛选所有信息的数量
			int allCount = wdNominationInfoBll.getCountWdNominationInfoBeans(1);
			if (allCount > 30) {
				allCount = 30;
			}
			resultBeanWithPage.setProperties(true, "获取成功", list, allCount);
		} catch (Exception e) {
			logger.warn(e + "getWdNominationInfoBeansByBlack");
			resultBeanWithPage.setProperties(false, "信息不存在", null, 0);
		}
		return resultBeanWithPage;
	}

	/***
	 * 根据展示信息拼装展示信息
	 * 
	 * @param listWdNominationInfo
	 * @return
	 */
	private List<WdNominationShowBean> getWdNominationShowBeans(
			List<WdNominationInfoBean> listWdNominationInfo,
			WdVoteInfoBean wdVoteInfoBean) {
		List<WdNominationShowBean> list = new ArrayList<>();

		// 非空判断
		if (listWdNominationInfo != null && !listWdNominationInfo.isEmpty()) {
			// 遍历信息
			for (int i = 0; i < listWdNominationInfo.size(); i++) {
				// 取出实体
				WdNominationInfoBean wdNominationInfoBean = listWdNominationInfo
						.get(i);

				wdVoteInfoBean
						.setNominationCode(wdNominationInfoBean.getCode());
				// 声明展示实体
				WdNominationShowBean wdNominationShowBean = new WdNominationShowBean();
				wdNominationShowBean.setServerName(wdNominationInfoBean
						.getNominatedServerName());
				// 主键
				wdNominationShowBean.setNominationCode(wdNominationInfoBean
						.getCode());
				// 角色名
				wdNominationShowBean.setRoleName(wdNominationInfoBean
						.getNominatedRole());
				// 图片
				wdNominationShowBean.setPicNum(wdNominationInfoBean
						.getImageShow());
				// 白名单排名
				wdNominationShowBean.setWhiteRanking(wdNominationInfoBll
						.getWhiteOrderWhenSameOrder(
								wdNominationInfoBean.getVoteWhiteDate(),
								wdNominationInfoBean.getVoteWhite())
						+ wdNominationInfoBll
								.getWhiteNumCount(wdNominationInfoBean
										.getCode()) + 1);

				// 粉丝数
				wdNominationShowBean.setWhiteNum(wdNominationInfoBean
						.getVoteWhite());
				// 黑名单排名
				wdNominationShowBean.setBlackRanking(wdNominationInfoBll
						.getBlackOrderWhenSameOrder(
								wdNominationInfoBean.getVoteBlackDate(),
								wdNominationInfoBean.getVoteBlack())
						+ wdNominationInfoBll
								.getBlackNumCount(wdNominationInfoBean
										.getCode()) + 1);
				// 黑粉数
				wdNominationShowBean.setBlackNum(wdNominationInfoBean
						.getVoteBlack());
				// 投票情况
				if (wdVoteInfoBll.getVoteTypeByAccount(wdVoteInfoBean) == null) {
					wdNominationShowBean.setTypeCode(-1);
				} else {
					wdNominationShowBean
							.setTypeCode(wdVoteInfoBll.getVoteTypeByAccount(
									wdVoteInfoBean).getVoteType());
				}
				// 简介
				wdNominationShowBean.setNominatedContent(wdNominationInfoBll
						.getNominatedContentByCode(
								wdNominationInfoBean.getCode())
						.getNominatedContent());
				logger.debug(wdNominationShowBean.toString());
				list.add(wdNominationShowBean);
			}
		}
		return list;
	}

}
