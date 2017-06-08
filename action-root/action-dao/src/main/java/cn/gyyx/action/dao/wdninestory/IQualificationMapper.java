/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：lottery
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年3月19日 上午10:24:08
 * @版本号：
 * @本类主要用途描述：用户资格接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdninestory;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.common.ActionConfigBean;
import cn.gyyx.action.beans.lottery.QualificationBean;

/**
 * @modify bozch 2016-07-13
 *
 */
public interface IQualificationMapper {

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: selectQualification
	 * @Description: TODO 根据用户code查询用户资格信息
	 * @param userCode
	 * @return QualificationBean
	 */
	public QualificationBean selectQualification(int userCode);

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: updateLotteryTime
	 * @Description: TODO 根据用户code更新用户抽奖次数
	 * @param qualification
	 */
	public void updateLotteryTime(QualificationBean qualification);

	/**
	 * 根据用户编号和活动编号获取抽奖资格信息
	 * 
	 * @日期：2015年4月9日
	 * @Title: selectByUserAndAction
	 * @param map
	 *            条件集合，包括用户Code和活动Code
	 * @return QualificationBean 用户抽奖资格信息
	 */
	public QualificationBean selectByUserAndAction(Map<String, String> map);

	/**
	 * 查询抽奖资格靠用户Code和活动Code
	 * 
	 * @param userCode
	 * @param actionCode
	 * @return
	 */
	public QualificationBean selectLotteryInfoByUserAndAction(
			@Param("userCode") int userCode, @Param("actionCode") int actionCode);

	/**
	 * 查询抽奖时间靠用户Code和活动Code---问道IDO漫展需要
	 * 
	 * @param userCode
	 * @param actionCode
	 * @return clicktime
	 */
	public QualificationBean selectClickLikeByUserAndAction(
			@Param("userCode") int userCode, @Param("actionCode") int actionCode);

	/**
	 * 插入用户资格 ——问道康师傅V1.211活动需要
	 * 
	 * @param qualification
	 */
	public void insertQualification(QualificationBean qualification);

	/**
	 * 减少抽奖次数
	 * 
	 * @param userCode
	 * @param actionCode
	 */
	public void reduceTime(@Param("userCode") int userCode,
			@Param("actionCode") int actionCode);

	public List<QualificationBean> selectByAction(
			@Param("actionCode") int actionCode);

	/**
	 * 增加一次抽奖次数
	 * 
	 * @param userCode
	 * @param actionCode
	 */
	public void addOneLotteryTime(@Param("userCode") int userCode,
			@Param("actionCode") int actionCode);
	/**
	 * 增加两次抽奖次数----问道IDO漫展活动需要
	 * 
	 * @param userCode
	 * @param actionCode
	 */
	public void addTwoLotteryTime(@Param("userCode") int userCode,
			@Param("actionCode") int actionCode);
	
	
	/***
	 * 根据页数获取资格
	 * 
	 * @return
	 */
	public List<QualificationBean> selectByActionAndPage(
			@Param("actionCode") int actionCode,
			@Param("pageSize") int pageSize, @Param("pageNo") int pageNo);

	public void removeByActionCode(@Param("actionCode") int actionCode);

	public void setTimes(@Param("userCode") int userCode,
			@Param("actionCode") int actionCode, @Param("time") int time);

	/**
	 * 查询活动的信息
	 * @param actionCode 活动编码
	 * @return
	 */
	ActionConfigBean selectActionConfig(@Param("actionCode") int actionCode);

	/**
	 * 问道1.61需要，增加一条抽奖次数为0的数据
	 * @param userCode
	 * @param actionCode
	 * @return
	 */
	public int addOneLotteryData(@Param("userCode") int userCode, @Param("actionCode") int actionCode);
	/**
	 * 问道1.61需要，抽奖次数加1
	 * @param userCode
	 * @param actionCode
	 * @return
	 */
	public int addLotteryTime(@Param("userCode") int userCode, @Param("actionCode") int actionCode);

}
