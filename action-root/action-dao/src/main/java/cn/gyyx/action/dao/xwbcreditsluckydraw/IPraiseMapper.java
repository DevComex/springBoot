/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月2日
 * @版本号：V1.214
 * @本类主要用途描述：点赞记录数据处理接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import cn.gyyx.action.beans.xwbcreditsluckydraw.PraiseBean;

public interface IPraiseMapper {
	/**
	 * 增加点赞记录
	 * @param praiseBean
	 */
	public void insertPraise(PraiseBean praiseBean);
	/**
	 * 根据素材Code更新点赞记录的是否取消is_delete
	 * @param materialInfo
	 */
	public void updatePraiseDelete(PraiseBean praiseBean);
	/**
	 * 条件查询点赞信息
	 * @param praiseBean
	 * @return
	 */
	public PraiseBean selectPraise(PraiseBean praiseBean);
	/**
	 * 根据素材Code查询有多少个赞
	 * @param materialInfo
	 * @return
	 */
	public Integer selectCountPraise(Integer materialInfo);
	/**
	 * 修改状态
	 * @param praiseBean
	 */
	public void updatePraiseStatus(PraiseBean praiseBean);
	
	public Integer getPraiseCountByUser(String account,Integer materialCode);
}
