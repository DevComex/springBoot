/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年9月18日下午5:08:39
 * 版本号：v1.0
 * 本类主要用途叙述：投票的bll
 */

package cn.gyyx.action.bll.lottery;

import java.util.Date;
import java.util.List;

import com.ibm.icu.util.Calendar;

import cn.gyyx.action.beans.lottery.VoteBean;
import cn.gyyx.action.dao.lottery.IVote;
import cn.gyyx.action.dao.lottery.VoteDao;

public class VoteBll {
	private IVote vote = new VoteDao();

	/***
	 * 增加一条投票信息
	 * 
	 * @param voteBean
	 */
	public Integer addVoteInfo(VoteBean voteBean) {
		return vote.addVoteInfo(voteBean);
	}

	/**
	 * 根据主键移除一条投票信息
	 * 
	 * @param code
	 */
	public void removeVoteInfoByCode(int code) {
		vote.removeVoteInfoByCode(code);
	}

	/**
	 * 得到用户投票的种类数
	 * 
	 * @param userCode
	 * @return
	 */
	public List<Integer> getCountVoteGroupByWritingCode(int userCode) {
		return vote.getCountVoteGroupByWritingCode(userCode);

	}

	/**
	 * 得到该用户||ip当日对该作品的投票情况
	 * 
	 * @return
	 */
	public VoteBean getVoteBeanByIPAccountDate(int writingCode, String account,
			String ip) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);// 获取年份
		int month = cal.get(Calendar.MONTH)+1;// 获取月份
		int day = cal.get(Calendar.DATE);// 获取日
		return vote.getVoteBeanByIPAccountDate(ip, account, year, month, day,
				writingCode);
	}

	/**
	 * 得到某作品的投票详细情况
	 * 
	 * @param writingCode
	 * @return
	 */
	public List<VoteBean> getCountVoteByWritingCode(int writingCode) {
		return vote.getCountVoteByWritingCode(writingCode);
	}
	/**
	 * 根据用户主键获得投票信息
	 * @param userCode
	 * @return
	 */
	public List<VoteBean> getVoteBeanByUserCode(int userCode) {
		return vote.getVoteBeanByUserCode(userCode);
	}
}
