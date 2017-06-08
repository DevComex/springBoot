/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月11日上午11:05:24
 * 版本号：v1.0
 * 本类主要用途叙述：问道十周年总分数接口
 */

package cn.gyyx.action.dao.wdtenyearpicture;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.tenyearpicture.ScoreBean;

public interface IScore {
	/***
	 * 根据用户主键获取总分信息
	 * 
	 * @param userCode
	 * @return
	 */
	public ScoreBean getScoreBean(@Param("userCode") int userCode);

	/***
	 * 根据主键设定相应的分数
	 * 
	 * @param userCode
	 * @param score
	 */
	public void setScoreBean(@Param("userCode") int userCode,
			@Param("score") int score);

	/***
	 * 增加十年记录
	 * 
	 * @param scoreBean
	 */
	public void addScoreBean(ScoreBean scoreBean);

	/***
	 * 获取积分绑定的总账户数
	 * 
	 * @return
	 */
	public Integer getCountByAccount();

	/***
	 * 获取所有积分信息
	 * 
	 * @return
	 */
	public List<ScoreBean> getScoreBeanas();

	/***
	 * 分页得到排名前一千玩家
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<ScoreBean> getTopScoreByPage(@Param("pageNo") int pageNo,
			@Param("pageSize") int pageSize);

	/***
	 * 得到排名前一千玩家
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<ScoreBean> getTopScore();

	/****
	 * 
	 * @param score
	 * @return
	 */
	public Integer getCountScore(@Param("score") int score);

}
