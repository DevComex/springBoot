/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月15日下午7:56:39
 * 版本号：v1.0
 * 本类主要用途叙述：投票的DAO层接口
 */

package cn.gyyx.action.dao.wdno1pet;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdno1pet.VoteBean;

public interface IVote {
	
	/**
	 * 查找同一账户或同一IP是否在当天有过投票记录
	 * @param voteBean
	 * @param voteYear
	 * @param voteMonth
	 * @param voteDay
	 * @return
	 */
	public VoteBean SelectVoteQualification(@Param("voteBean")VoteBean voteBean,
			@Param("voteYear") int voteYear, @Param("voteMonth") int voteMonth,
			@Param("voteDay") int voteDay);

	/**
	 * 插入一条投票记录
	 * 
	 * @param voteBean
	 */
	public void insertVote(VoteBean voteBean);

	/**
	 * 获取最近的投票
	 * 
	 * @param pCode
	 *            宠物主键
	 * @param limit
	 *            限制输出条数
	 * @return 用户主键
	 */
	public List<VoteBean> getVoteUserCodes(@Param("pCode") String pCode,
			@Param("limit") Integer limit);

	/**
	 * 通过投票记录主键查询投票时间
	 * 
	 * @param voteCode
	 * @return 投票时间 TimeStamp
	 */
	public Timestamp selectTimestampByVoteCode (@Param("voteCode") int voteCode);

}
