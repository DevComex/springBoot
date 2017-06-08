/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月7日
 * @版本号：1.214
 * @本类主要用途描述：签到奖励信息业务逻辑
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.SignPrizeBean;
import cn.gyyx.action.dao.xwbcreditsluckydraw.SignPrizeDAO;

public class SignPrizeBll {
	private SignPrizeDAO signPrizeDAO = new SignPrizeDAO();
	/**
	 * 获取用户prizeType奖励类型的奖励
	 * @param prizeType
	 * @param sex
	 * @return
	 */
	public List<SignPrizeBean> getAccountPrize(String prizeType ,String sex){
		
		List<SignPrizeBean> list = signPrizeDAO.selectSignPrizeByType(prizeType, sex);
		List<SignPrizeBean> listAll = signPrizeDAO.selectSignPrizeByType(prizeType, "无");
		for (SignPrizeBean signPrizeBean : listAll) {
			list.add(signPrizeBean);
		}
		return list;
	}
	/**
	 * 增加一条签到奖励信息
	 * @author fanyongliang
	 * @param signPrizeBean
	 */
	public void addSignPrize(SignPrizeBean signPrizeBean) {
		signPrizeDAO.insertSignPrize(signPrizeBean);
	}
	/**
	 * 修改签到奖励
	 * @author fanyongliang
	 * @param signPrizeBean
	 */
	public void setSignPrize(SignPrizeBean signPrizeBean) {
		signPrizeDAO.updateSignPrize(signPrizeBean);
	}
	/**
	 * 根据奖励编号查询签到奖励
	 * @author fanyongliang
	 * @param code
	 * @return
	 */
	public SignPrizeBean getSignPrizeByCode(Integer code) {
		return signPrizeDAO.selectSignPrizeByCode(code);
	}
	/**
	 * 删除签到奖励
	 * @author fanyongliang
	 * @param code
	 */
	public void deleteSignPrize(Integer code) {
		signPrizeDAO.deleteSignPrize(code);
	}
	/**
	 * 查询全部签到奖励
	 * @author fanyongliang
	 * @return
	 */
	public List<SignPrizeBean> getAllSignPrize() {
		return signPrizeDAO.selectAllSignPrize();
	}
	/**
	 * 根据奖励类型查询签到奖励
	 * @author fanyongliang
	 * @param code
	 * @return
	 */
	public List<SignPrizeBean> getSignPrizeByType(String prizeType, String prizeSex) {
		return signPrizeDAO.selectSignPrizeByType(prizeType,prizeSex);
	}
	
	/**
	 * 查询奖励数量
	 * @param prizeType
	 * @return
	 */
	public Integer getPrizeCountByType(String prizeType,String sex){
		
		Integer man = signPrizeDAO.getPrizeCountByType(prizeType,"男");
		Integer woman = signPrizeDAO.getPrizeCountByType(prizeType,"女");
		if(sex.equals("女")){
			return woman;
		}else if(sex.equals("男")){
			return man;
		}else{
			if(man > woman){
				return man;
			}else{
				return woman;
			}
		}
	}
}
