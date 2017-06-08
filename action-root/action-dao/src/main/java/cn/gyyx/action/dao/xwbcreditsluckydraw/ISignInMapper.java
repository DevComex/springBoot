package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.xwbcreditsluckydraw.SignInBean;

public interface ISignInMapper {
	/**
	 * 插入签到新纪录
	 * */
	void addSignIn(SignInBean signInBean);

	/**
	 * 查询签到记录
	 * */
	List<SignInBean> getSignInByPage(Map<String, Object> paramMap);

	/**
	 * 查询记录数量
	 * */
	Integer getSignInCount(SignInBean signInBean);
	/**
	 * 查询用户某月签到信息
	 * @author fanyongliang
	 * @param account
	 * @param firstDay
	 * @param lastDay
	 * @return
	 */
	List<SignInBean> selectSignInInfoBetweenFLDay(String account,
			String firstDay, String lastDay);
}
