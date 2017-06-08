/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月13日 上午10:46:16
 * @版本号：
 * @本类主要用途描述：物品信息接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.GoodsInfoBean;

public interface IGoodsinfoMapper {

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getGoods
	 * @Description: TODO 获得所有物品信息
	 * @param page
	 * @return List<GoodsInfoBean>
	 */
	public List<GoodsInfoBean> getGoods(int page);

	/**
	 * 
	 * @日期：2015年7月14日
	 * @Title: getGoodsTotal
	 * @Description: TODO 查询所有物品数量
	 * @return int
	 */
	public int getGoodsTotal();

	/**
	 * 
	 * @日期：2015年7月13日
	 * @Title: addGoods
	 * @Description: TODO 添加物品信息
	 * @param good
	 * @return int
	 */
	public int addGoods(GoodsInfoBean good);
	public GoodsInfoBean getGoodsByCode(int code); 
	public List<GoodsInfoBean> getGoodsAll(); 
	public Integer updateGood(GoodsInfoBean good); 
	public Integer deleteGood(GoodsInfoBean good); 
	/**
	 * 修改道具数量
	 * @param code
	 */
	public void updatePriceNum(int code); 
	
}
