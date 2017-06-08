/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月13日 上午11:23:02
 * @版本号：
 * @本类主要用途描述：物品逻辑类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.GoodsInfoBean;
import cn.gyyx.action.dao.xwbcreditsluckydraw.GoodsinfoDAO;

public class GoodsBLL {

	private GoodsinfoDAO goodsDao = new GoodsinfoDAO();

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getGoods
	 * @Description: TODO 获得所有物品信息
	 * @param page
	 * @return List<GoodsInfoBean>
	 */
	public List<GoodsInfoBean> getGoods(int page) {
		return goodsDao.getGoods(page);
	}

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getGoodsTotal
	 * @Description: TODO 获得所有物品数量
	 * @return int
	 */
	public int getGoodsTotal() {
		return goodsDao.getGoodsTotal();
	}

	/**
	 * 
	 * @日期：2015年7月13日
	 * @Title: addGood
	 * @Description: TODO 添加物品
	 * @param good
	 * @return int
	 */
	public int addGood(GoodsInfoBean good) {
		return goodsDao.addGoods(good);
	}
	public List<GoodsInfoBean> getGoodsAll() {
		return goodsDao.getGoodsAll();
		
	}
	public int updateGood(GoodsInfoBean good) {
		return goodsDao.updateGood(good);
		
	}
	public int deleteGood(GoodsInfoBean good) {
		return goodsDao.deleteGood(good);
		
	}
	public GoodsInfoBean getGoodsByCode(int code){
		
		return goodsDao.getGoodsByCode(code);
		
	}
	
	/**
	 * 修改道具数量
	 * @param code
	 */
	public void updatePriceNum(int code){
		goodsDao.updatePriceNum(code);
	}
}
