package cn.gyyx.action.dao.activityCode;

import java.util.List;

import cn.gyyx.action.beans.wd9yeardatavideo.PresentLogBean;

public interface ISendPresentLogMapper {
	/**
	 * 判断同一活动下同IP个数
	 * @param ip
	 * @param activityId
	 * @return
	 */
	public int SameIPNum(String ip,int activityId);
	/**
	 * 是否存在account为searchPara的值
	 * @param activityId
	 * @param searchPara
	 * @return
	 */
	public List<PresentLogBean> isExist(int activityId,String searchPara);
	/**
	 * 向数据库中插入获奖信息
	 * @param para
	 * @return
	 */
	public boolean insertWinningMes(PresentLogBean para);
}
