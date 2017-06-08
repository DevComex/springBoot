/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：chenpeilan
 * 联系方式：chenpeilan@gyyx.cn
 * 创建时间： 2016年3月30日
 * 版本号：
 * 本类主要用途描述：问道十周年粉丝榜活动提名相关Service
-------------------------------------------------------------------------*/
package cn.gyyx.action.service.wdtenyearfans;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdtenyearfans.WdAccountInfoBean;
import cn.gyyx.action.beans.wdtenyearfans.WdCommentsBean;
import cn.gyyx.action.beans.wdtenyearfans.WdMajorImageBean;
import cn.gyyx.action.beans.wdtenyearfans.WdNominationCheckBean;
import cn.gyyx.action.beans.wdtenyearfans.WdNominationInfoBean;
import cn.gyyx.action.bll.wdoldplayers.WdOldWantedInfoBLL;
import cn.gyyx.action.bll.wdtenyearfans.NominatedBLL;
import cn.gyyx.action.bll.wdtenyearfans.WdCommentsBll;
import cn.gyyx.action.bll.wdtenyearfans.WdNominationInfoBll;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: NominatedService
 * @Description: TODO 问道十周年粉丝榜活动提名相关Service
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2016年3月30日 下午2:10:28
 *
 */
public class NominatedService {

	private NominatedBLL nominatedBLL = new NominatedBLL();
	private WdNominationInfoBll wdNominationInfoBll = new WdNominationInfoBll();
	private WdCommentsBll wdCommentsBll = new WdCommentsBll();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(NominatedService.class);

	/**
	 * 
	 * @Title: addRole
	 * @Description: TODO 绑定角色
	 * @param @param wdAccountInfoBean
	 * @param @return
	 * @return ResultBean<WdAccountInfoBean>
	 * @throws
	 */
	public ResultBean<WdAccountInfoBean> addRole(
			WdAccountInfoBean wdAccountInfoBean) {
		logger.info("wdAccountInfoBean:" + wdAccountInfoBean);
		ResultBean<WdAccountInfoBean> result = new ResultBean<WdAccountInfoBean>(
				false, "绑定失败", null);
		nominatedBLL.addRole(wdAccountInfoBean);
		result.setProperties(true, "绑定成功!", wdAccountInfoBean);
		logger.info("wdAccountInfoBean:" + wdAccountInfoBean);
		logger.info("result:" + result);
		return result;
	}

	/**
	 * 
	 * @Title: getWdAccountInfoBeanByAccountName
	 * @Description: TODO 查询是否绑定过角色
	 * @param @param accountName
	 * @param @return
	 * @return WdAccountInfoBean
	 * @throws
	 */
	public ResultBean<WdAccountInfoBean> getWdAccountInfoBeanByAccountName(
			String accountName) {
		logger.info("accountName:" + accountName);
		ResultBean<WdAccountInfoBean> result = new ResultBean<WdAccountInfoBean>(
				false, "未绑定过角色", null);
		if (nominatedBLL.getWdAccountInfoBeanByAccountName(accountName) == null) {
			logger.info("result:" + result);
			return result;
		} else {
			result.setProperties(true, "已绑定过角色",
					nominatedBLL.getWdAccountInfoBeanByAccountName(accountName));
			logger.info("result:" + result);
			return result;
		}
	}

	/**
	 * 
	 * @Title: getWdNominationInfoBeanByAccountName
	 * @Description: TODO 判断是否提名过
	 * @param @param accountName
	 * @param @return
	 * @return ResultBean<Boolean>
	 * @throws
	 */
	public ResultBean<WdNominationInfoBean> getWdNominationInfoBeanByAccountName(
			String accountName) {
		logger.info("accountName:" + accountName);
		ResultBean<WdNominationInfoBean> result = new ResultBean<WdNominationInfoBean>(
				false, "", null);
		if (nominatedBLL.getWdNominationInfoBeanByAccountName(accountName) != null) {
			if (nominatedBLL.getWdNominationInfoBeanByAccountName(accountName)
					.getAuditStatus() == 0) {
				result.setMessage("提名正在审核中");
				logger.info("result:" + result);
				return result;
			} else if (nominatedBLL.getWdNominationInfoBeanByAccountName(
					accountName).getAuditStatus() == 1) {
				result.setMessage("提名已成功");
				logger.info("result:" + result);
				return result;

			} else if (nominatedBLL.getWdNominationInfoBeanByAccountName(
					accountName).getAuditStatus() == -1) {
				result.setMessage("未通过审核");
				logger.info("result:" + result);
				return result;
			} else {
				logger.info("result:" + result);
				return result;
			}
		} else {
			result.setProperties(true, "未进行过提名", null);
			logger.info("result:" + result);
			return result;
		}
	}

	/**
	 * 
	 * @Title: addWdNominationInfoBean
	 * @Description: TODO 提名操作
	 * @param @param wdNominationInfoBean
	 * @param @return
	 * @return ResultBean<WdNominationInfoBean>
	 * @throws
	 */
	public ResultBean<WdNominationInfoBean> addWdNominationInfoBean(String accountName,
			WdNominationInfoBean wdNominationInfoBean) {
		logger.info("wdNominationInfoBean:" + wdNominationInfoBean);
		ResultBean<WdNominationInfoBean> result = new ResultBean<WdNominationInfoBean>(
				false, "提名失败", null);
		if (wdNominationInfoBean.getNominatedType() == 0) {
			wdNominationInfoBean.setNominatedArea(this
					.getWdAccountInfoBeanByAccountName(accountName)
					.getData().getArea());
			wdNominationInfoBean.setNominatedServerId(this
					.getWdAccountInfoBeanByAccountName(accountName)
					.getData().getServerId());
			wdNominationInfoBean.setNominatedServerName(this
					.getWdAccountInfoBeanByAccountName(accountName)
					.getData().getServerName());
		}
		wdNominationInfoBean.setNominatedDate(new Date());
		nominatedBLL.addWdNominationInfoBean(wdNominationInfoBean);
		result.setProperties(true, "提名成功!", wdNominationInfoBean);
		logger.info("wdNominationInfoBean:" + wdNominationInfoBean);
		logger.info("result:" + result);
		return result;

	}

	/**
	 * 
	 * @Title: getImageByMajor
	 * @Description: TODO 通过提名类型，职业，性别获取图片信息
	 * @param @param type
	 * @param @param major
	 * @param @param sex
	 * @param @return
	 * @return ResultBean<WdMajorImageBean>
	 * @throws
	 */
	public ResultBean<WdMajorImageBean> getImageByMajor(int type, int major,
			int sex) {
		logger.info("type:" + type);
		logger.info("major:" + major);
		logger.info("sex:" + sex);
		ResultBean<WdMajorImageBean> result = new ResultBean<WdMajorImageBean>(
				false, "获取图片失败", null);
		if (nominatedBLL.getImageByMajor(type, major, sex) != null) {
			result.setProperties(true, "获取图片成功!",
					nominatedBLL.getImageByMajor(type, major, sex));
			logger.info("data:"
					+ nominatedBLL.getImageByMajor(type, major, sex));
			logger.info("result:" + result);
			return result;
		} else {
			result.setMessage("没有对应的图片");
			return result;
		}

	}

	/**
	 * 
	 * @Title: getNominationCheck
	 * @Description: TODO 获得提名展示信息
	 * @param @param accountName
	 * @param @return
	 * @return ResultBean<WdNominationCheckBean>
	 * @throws
	 */
	public ResultBean<WdNominationCheckBean> getNominationCheck(
			String accountName) {
		ResultBean<WdNominationCheckBean> result = new ResultBean<WdNominationCheckBean>();
		try {
			// 根据账号筛选出提名
			WdNominationInfoBean WdNominationInfo = nominatedBLL
					.getWdNominationInfoBeanByAccountName(accountName);
			// 拼装组合出展示信息
			WdNominationCheckBean wdNominationCheckBean = getWdNominationCheckBean(WdNominationInfo);
			result.setProperties(true, "获取信息成功", wdNominationCheckBean);
		} catch (Exception e) {
			logger.warn(e + "getNominationCheck");
			result.setProperties(false, "信息沒查詢到", null);
		}
		return result;
	}

	/***
	 * 根据展示信息拼装展示信息
	 * 
	 * @param listWdNominationInfo
	 * @return
	 */
	public WdNominationCheckBean getWdNominationCheckBean(
			WdNominationInfoBean wdNominationInfoBean) {
		// 声明展示实体
		WdNominationCheckBean wdNominationCheckBean = new WdNominationCheckBean();
		// 非空判断
		if (wdNominationInfoBean != null && !"".equals(wdNominationInfoBean)) {
			// 主键
			wdNominationCheckBean.setNominationCode(wdNominationInfoBean
					.getCode());
			// 审核状态
			wdNominationCheckBean.setStatus(wdNominationInfoBean
					.getAuditStatus());
			// 大区
			wdNominationCheckBean.setArea(wdNominationInfoBean
					.getNominatedArea());
			// 区服ID
			wdNominationCheckBean.setServerId(wdNominationInfoBean
					.getNominatedServerId());
			// 区服名称
			wdNominationCheckBean.setServerName(wdNominationInfoBean
					.getNominatedServerName());
			// 角色名
			wdNominationCheckBean.setRoleName(wdNominationInfoBean
					.getNominatedRole());
			// 图片
			wdNominationCheckBean.setPicNum(wdNominationInfoBean.getImageNum());
			// 白名单排名
			wdNominationCheckBean.setWhiteRanking(wdNominationInfoBll
					.getWhiteOrderWhenSameOrder(
							wdNominationInfoBean.getVoteWhiteDate(),
							wdNominationInfoBean.getVoteWhite())
					+ wdNominationInfoBll
							.getWhiteNumCount(wdNominationInfoBean
									.getCode()) + 1);
			// 粉丝数
			wdNominationCheckBean.setWhiteNum(wdNominationInfoBean
					.getVoteWhite());
			// 黑名单排名
			wdNominationCheckBean.setBlackRanking(wdNominationInfoBll
					.getBlackOrderWhenSameOrder(
							wdNominationInfoBean.getVoteBlackDate(),
							wdNominationInfoBean.getVoteBlack())
					+ wdNominationInfoBll
							.getBlackNumCount(wdNominationInfoBean
									.getCode()) + 1);
			// 黑粉数
			wdNominationCheckBean.setBlackNum(wdNominationInfoBean
					.getVoteBlack());
			logger.debug(wdNominationCheckBean.toString());
		}
		return wdNominationCheckBean;
	}
	
	/**
	 * 
	* @Title: getWdCommentsBean
	* @Description: TODO 获取所有评论
	* @param @return    
	* @return ResultBean<List<WdCommentsBean>>    
	* @throws
	 */
	public ResultBean<List<WdCommentsBean>> getWdCommentsBean(int nominationCode) {
		ResultBean<List<WdCommentsBean>>  result = new ResultBean<List<WdCommentsBean>> ();
		if(wdCommentsBll.getWdCommentsBean(nominationCode).size() > 0){
			result.setProperties(true, "获取评论成功", wdCommentsBll.getWdCommentsBean(nominationCode));
			logger.info("result:"+result);
			return result;
		}else{
			result.setMessage("无通过评论");
			logger.info("result:"+result);
			return result;
		}
	}

}
