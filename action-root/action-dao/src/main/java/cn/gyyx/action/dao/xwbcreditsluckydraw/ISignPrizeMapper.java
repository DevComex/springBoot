/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月7日
 * @版本号：1.214
 * @本类主要用途描述：签到奖励信息接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.SignPrizeBean;

public interface ISignPrizeMapper {
	/**
	 * 增加一条签到奖励信息
	 * @author fanyongliang
	 * @param signPrizeBean
	 */
	public void insertSignPrize(SignPrizeBean signPrizeBean);
	/**
	 * 修改签到奖励
	 * @author fanyongliang
	 * @param signPrizeBean
	 */
	public void updateSignPrize(SignPrizeBean signPrizeBean);
	/**
	 * 根据奖励编号查询签到奖励 
	 * @author fanyongliang
	 * @param code
	 * @return
	 */
	public SignPrizeBean selectSignPrizeByCode(Integer code);
	/**
	 * 删除签到奖励
	 * @author fanyongliang
	 * @param code
	 */
	public void deleteSignPrize(Integer code);
	/**
	 * 查询全部签到奖励
	 * @author fanyongliang 
	 * @return
	 */
	public List<SignPrizeBean> selectAllSignPrize();
	/**
	 * 根据奖励类型查询签到奖励
	 * @author fanyongliang
	 * @param code
	 * @return
	 */
	public List<SignPrizeBean> selectSignPrizeByType(String prizeType,String prizeSex);
	/**
	 * 查询奖励数量
	 * @param prizeType
	 * @return
	 */
	public Integer getPrizeCountByType(String prizeType,String sex);
}
