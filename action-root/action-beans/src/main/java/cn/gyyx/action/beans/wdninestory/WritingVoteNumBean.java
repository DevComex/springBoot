/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月11日下午2:55:16
 * 版本号：v1.0
 * 本类主要用途叙述：每个作品的票数
 */



package cn.gyyx.action.beans.wdninestory;

public class WritingVoteNumBean {
	//作品code
	private int wrttingCode;
	//投票数
	private int voteNum;
	/**
	 * 作品主键get方法
	 * @return 作品主键
	 */
	public int getWrttingCode() {
		return wrttingCode;
	}
	/**
	 * |作品主键set方法
	 * @param wrttingCode
	 */
	public void setWrttingCode(int wrttingCode) {
		this.wrttingCode = wrttingCode;
	}
	/**
	 * 票数get方法
	 * @return
	 */
	public int getVoteNum() {
		return voteNum;
	}
	/**
	 * 票数set方法
	 * @param voteNum
	 */
	public void setVoteNum(int voteNum) {
		this.voteNum = voteNum;
	}
	

}
