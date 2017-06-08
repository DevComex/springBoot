/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdshenluowangxiang
 * @作者：lihu
 * @联系方式：lihu@gyyx.cn
 * @创建时间：2017年4月8日 下午4:23:05
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.wdshenluowangxiang;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.joda.time.DateTime;
import org.joda.time.Days;

import cn.gyyx.action.beans.wdshenluowangxiang.Constants;
import cn.gyyx.action.beans.wdshenluowangxiang.ShenLuoWangXiangAddressBean;
import cn.gyyx.action.beans.wdshenluowangxiang.ShenLuoWangXiangBean;
import cn.gyyx.action.dao.wdshenluowangxiang.ShenLuoWangXiangAddressDao;
import cn.gyyx.action.dao.wdshenluowangxiang.ShenLuoWangXiangDao;

/**
 * <p>
 * 森罗万象活动页面信息ShenLuoWangXiangBll
 * </p>
 * 
 * @author lihu
 * @since 0.0.1
 */
public class ShenLuoWangXiangBll {
	private ShenLuoWangXiangDao dao = new ShenLuoWangXiangDao();
	private ShenLuoWangXiangAddressDao addressDao = new ShenLuoWangXiangAddressDao();

	/**
	 * 根据用户ID 获取活动报名信息
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public ShenLuoWangXiangBean getUserInfoByUserId(Integer userId) throws Exception {
		ShenLuoWangXiangBean bean = dao.getUserInfoByUserId(userId);
		if (bean == null) {
			return null;
		}
		if (bean.getMaxLevel() != null && bean.getMaxLevel() >= 20) {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//将日期转换为 年月日
			String format = dateFormat.format(bean.getUpdateTime() == null ? date :bean.getUpdateTime());
			DateTime dt1 = new DateTime(dateFormat.parse(format));
			DateTime dt2 = new DateTime(date);
			bean.setTotalNum(Days.daysBetween(dt1, dt2).getDays() * Constants.DAILY_TIMES + 3); // 总的获奖次数
			bean.setLastNum(bean.getTotalNum() - bean.getLuckyNum());
		} else {
			bean.setTotalNum(0);
			bean.setLastNum(0);
		}

		return bean;
	}

	/**
	 * 插入用户报名信息
	 * 
	 * @param bindBean
	 */
	public void instert(ShenLuoWangXiangBean bindBean) {
		dao.instert(bindBean);
	}

	/**
	 * 修改用户抽奖次数
	 * 
	 * @param userId
	 */
	public void updateLuckNum(Integer userId, SqlSession session) {
		dao.updateLuckNum(userId, session);
	}

	/**
	 * 添加邀请地址
	 * 
	 * @param userId
	 */
	public void insertAddress(ShenLuoWangXiangAddressBean addressBean) {
		addressDao.insertAddress(addressBean);
	}

	/**
	 * 
	 * <p>
	 * 查询地址by userId
	 * </p>
	 *
	 * @action chenglong 2017年4月10日 下午2:30:11 查询地址by userId
	 *
	 * @param userId
	 * @return Integer
	 */
	public ShenLuoWangXiangAddressBean selectAddressByUserId(Integer userId) {
		return addressDao.selectAddressByUserId(userId);
	}

	/**
	 * 
	 * <p>
	 * 更新地址
	 * </p>
	 *
	 * @action chenglong 2017年4月10日 下午2:29:51 更新地址
	 *
	 * @param addressBean
	 *            void
	 */
	public void updateAddress(ShenLuoWangXiangAddressBean addressBean) {
		addressDao.updateAddress(addressBean);
	}

	public ShenLuoWangXiangAddressBean getInviteAddress(Integer userId) {

		return addressDao.selectAddressByUserId(userId);
	}
}
