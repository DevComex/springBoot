/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月16日上午11:36:46
 * 版本号：v1.0
 * 本类主要用途叙述：关于投票的
 */

package cn.gyyx.action.bll.wdno1pet;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.gyyx.action.beans.wdno1pet.VoteBean;
import cn.gyyx.action.dao.wdno1pet.IVote;
import cn.gyyx.action.dao.wdno1pet.PetInfoDAO;
import cn.gyyx.action.dao.wdno1pet.VoteDAO;

public class VoteBLL {

	private VoteDAO voteDAO = new VoteDAO();

	/**
	 * 确定这个用户是否还有给此作品投票的机会
	 * 
	 * @param voteBean
	 * @return 是否具有boolean
	 */
	public boolean getVoteQualification(VoteBean voteBean) {
		boolean is = false;
		// 将日期分开以便数据库查询
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		// 进行业务判断
		IVote iVote = new VoteDAO();
		if (iVote.SelectVoteQualification(voteBean, year, month, day) == null) {
			is = true;
		}
		return is;

	}

	/**
	 * 获取最近的投票
	 * 
	 * @param pCode
	 *            宠物主键
	 * @param limit
	 *            限制输出条数
	 * @return 用户主键
	 */
	public List<Integer> getVoteUserCodes(String pCode, int limit) {
		List<VoteBean> voteBeans = voteDAO.getVoteUserCodes(pCode, limit);
		List<Integer> userCodes = new ArrayList<Integer>();
		for (VoteBean voteBean : voteBeans) {
			userCodes.add(voteBean.getUserCode());
		}
		return userCodes;
	}
	/**
	 * 插入一条投票记录
	 * @param voteBean
	 */
	public void setVote(VoteBean voteBean){
		IVote iVote= new VoteDAO();
		iVote.insertVote(voteBean);
	}
	/**
	 * 更改作品表的时间与票数
	 */
	public void setNumberOfVote(VoteBean voteBean){
		PetInfoDAO petInfoDAO=new PetInfoDAO();
		petInfoDAO.updateVote(voteBean.getPetCode(), voteBean.getVoteTime());
		
	
	}
/**
 * 通过投票记录的主键查找投票的日期
 * @param voteCode
 * @return 投票时间TimeStamp
 */
	public Timestamp getVoteTimeByVoteCode(int voteCode) {
		IVote iVote = new VoteDAO();
		Timestamp timestamp = iVote.selectTimestampByVoteCode(voteCode);
		return timestamp;
	}
	

}
