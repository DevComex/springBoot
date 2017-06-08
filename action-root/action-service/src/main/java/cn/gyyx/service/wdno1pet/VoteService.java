/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月16日下午5:43:17
 * 版本号：v1.0
 * 本类主要用途叙述：关于投票的service层
 */

package cn.gyyx.service.wdno1pet;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdno1pet.VoteBean;
import cn.gyyx.action.bll.wdno1pet.VoteBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class VoteService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(VoteService.class);

	/**
	 * 用户投一次票的业务集成
	 * 
	 * @param voteBean
	 */
	public ResultBean<Integer> addVoteService(VoteBean voteBean) {
		logger.debug("voteBean", voteBean);
		VoteBLL voteBLL = new VoteBLL();
		String msg = "";
		ResultBean<Integer> vMsg = new ResultBean<Integer>();
		boolean q = voteBLL.getVoteQualification(voteBean);
		logger.debug("getVoteQualification", q);

		if (q) {
			voteBLL.setVote(voteBean);
			voteBean.setVoteTime(voteBLL.getVoteTimeByVoteCode(voteBean
					.getVoteCode()));
			voteBLL.setNumberOfVote(voteBean);
			msg = "感谢您珍贵的一票";
			vMsg.setIsSuccess(true);
			vMsg.setMessage(msg);

		} else {
			msg = "谢谢您的支持，机会请留给他人";
			vMsg.setIsSuccess(false);
			vMsg.setMessage(msg);
		}
		logger.debug("vMsg", vMsg);
		return vMsg;
	}

}
