/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年9月18日上午10:27:27
 * 版本号：v1.0
 * 本类主要用途叙述：投票的数据库接口
 */



package cn.gyyx.action.dao.lottery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lottery.VoteBean;

public interface IVote {
	/**
	 * 增加投票信息
	 * @param voteBean
	 */
	public  int addVoteInfo(VoteBean voteBean);
	/**
	 * 根据主键移除一条投票信息
	 * @param code
	 */
	public  void  removeVoteInfoByCode(@Param("code") int code);
	/**
	 * 得到给多少种作品投过票
	 * @param userCode
	 * @return
	 */
	public  List<Integer> getCountVoteGroupByWritingCode(@Param("userCode") int userCode);
	/**
	 * 日期账户时间查询投票信息
	 * @return
	 */
	public VoteBean getVoteBeanByIPAccountDate(@Param("ip") String ip,@Param("account") String account,
			@Param("year") int year,@Param("month") int month,@Param("day") int day
			,@Param("writingCode") int writingCode);
	/**
	 * 得到给多少种作品投过票
	 * @param userCode
	 * @return
	 */
	public  List<VoteBean> getCountVoteByWritingCode(@Param("writingCode") int writingCode);
	
	/**
	 * 得到用户的投票信息
	 * @param userCode
	 * @return
	 */
	public List<VoteBean> getVoteBeanByUserCode(@Param("userCode") int userCode);

}
