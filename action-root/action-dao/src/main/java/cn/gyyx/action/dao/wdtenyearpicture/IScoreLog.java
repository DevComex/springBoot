/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月11日上午11:08:37
 * 版本号：v1.0
 * 本类主要用途叙述：日志分数的数据库接口
 */

package cn.gyyx.action.dao.wdtenyearpicture;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.tenyearpicture.ScoreLogBean;

public interface IScoreLog {
	/***
	 * 增加一条日志信息
	 * 
	 * @param scoreLogBean
	 */
	public void addScoreLog(ScoreLogBean scoreLogBean);

	/***
	 * 获取活动的名字
	 * 
	 * @return
	 */
	public List<String> getScoreLogHdName();

	/***
	 * 根据活动名和用户获取活动分数进出总和
	 * 
	 * @param hdName
	 * @return
	 */
	public Integer getSumScoreByHdNameAndAccount(@Param("hdName") String hdName,
			@Param("account") String account);
}
