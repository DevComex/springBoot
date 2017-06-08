/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日上午11:20:34
 * 版本号：v1.0
 * 本类主要用途叙述：机会更改记录的数据库接口
 */

package cn.gyyx.action.dao.lottery;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lottery.LuckyDrawLogBean;

public interface ILuckyDrawLog {
	/***
	 * 增加一个抽奖来源日志
	 * 
	 * @param logBean
	 */
	public void addLuckyDrawLog(LuckyDrawLogBean logBean);

	/***
	 * 获取一段时间内某人某一类型增加抽奖次的记录
	 * 
	 */
	public LuckyDrawLogBean getOneSourceluckyDrawLogInTime(@Param("userCode") int userCode,
			@Param("begin") String begin, @Param("end") String end,
			@Param("source") String source);

	public int getCountByAccountAndSourceInAction(@Param("username") String username,
			@Param("actionCode") int actionCode, @Param("source") String source);

	public int getCountTodayByAccount(@Param("account") String account, @Param("activityCode") int activityCode,@Param("source") String source);
}
