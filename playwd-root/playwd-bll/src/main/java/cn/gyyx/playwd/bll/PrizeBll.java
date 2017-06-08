 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月6日上午11:27:16
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.bll;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.PrizeBean;
import cn.gyyx.playwd.dao.playwd.PrizeDao;

/**
 * 奖励物品类
 * @author lidudi
 *
 */
@Component
public class PrizeBll {

	//奖励物品类
	@Autowired
	public PrizeDao prizeDao;
	
	/**
	 * 获取奖励物品列表
	 * @param contentType
	 * @return
	 */
	public List<PrizeBean> getPrizeListByContentType(String contentType) {
		return prizeDao.selectPrizeListByContentType(contentType);	
	}
	
	/**
	 * 获取奖励物品信息
	 * @param prizeCode
	 * @return
	 */
	public PrizeBean getPrizeByCode(int prizeCode) {
		return prizeDao.selectPrizeByPrizeCode(prizeCode);
	}
	
	/**
	 * 奖品描述(等级:XXXXXXX)
	 * @param bean 奖励奖品信息
	 * @return 奖品描述
	 */
	public String getPrizeInfo(PrizeBean bean,String prizeType) {
		String prizeInfo="等级:"+bean.getName();
		for (int i = 0; i < bean.getItemList().size(); i++) {
			if(bean.getItemList().get(i).getName().indexOf("称号")>=0){
				prizeInfo=prizeInfo+","+bean.getItemList().get(i).getName();
			}
			if(bean.getItemList().get(i).getName().indexOf("银元宝")>=0){
				if((bean.getName().equals("4星级")||bean.getName().equals("5星级"))
						&&prizeType.equals("silverCoins")){
					prizeInfo=prizeInfo+","+bean.getItemList().get(i).getName();
				}
				if(!bean.getName().equals("4星级")&&!bean.getName().equals("5星级")){
					prizeInfo=prizeInfo+","+bean.getItemList().get(i).getName();
				}
			}		
			if(bean.getItemList().get(i).getName().indexOf("现金")>=0){
				if((bean.getName().equals("4星级")||bean.getName().equals("5星级"))
						&&prizeType.equals("rmb")){
					prizeInfo=prizeInfo+","+bean.getItemList().get(i).getName();
				}
				if(!bean.getName().equals("4星级")&&!bean.getName().equals("5星级")) {
					prizeInfo=prizeInfo+","+bean.getItemList().get(i).getName();
				}
			}
			
		}
		return prizeInfo;
	}
}
