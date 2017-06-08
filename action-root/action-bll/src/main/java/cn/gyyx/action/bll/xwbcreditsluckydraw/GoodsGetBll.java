package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.xwbcreditsluckydraw.GoodsGetBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PageBean;
import cn.gyyx.action.dao.xwbcreditsluckydraw.GoodsGetDAO;
import cn.gyyx.core.Text;

public class GoodsGetBll {
	private GoodsGetDAO goodsGetDao = new GoodsGetDAO();
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 添加中奖或积分兑换记录
	 * */
	public void addGoodsGet(GoodsGetBean goodsGetBean){
		goodsGetBean.setExchangeDate(goodsGetBean.getExchangeDate());
		goodsGetDao.addGoodsGet(goodsGetBean);
	}
	/**
	 * 查询中奖或积分兑换记录
	 * */
	public List<GoodsGetBean> getGoodsGetByPage(GoodsGetBean goodsGetBean, PageBean pageBean){
		try {
			if(!Text.isNullOrEmpty(goodsGetBean.getStrExchangeDate())){
				goodsGetBean.setExchangeDate(format.parse(goodsGetBean.getStrExchangeDate()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("goodsGet", goodsGetBean);
		map.put("page", pageBean);
		List<GoodsGetBean> list = goodsGetDao.getGoodsGetByPage(map);
		List<GoodsGetBean> newList = new ArrayList<GoodsGetBean>();
		/*转换时间格式*/
		for(GoodsGetBean temp:list){
			temp.setStrExchangeDate(format.format(temp.getExchangeDate()));
			newList.add(temp);
		}
		return newList;
	}
	/**
	 * 查询中奖或积分兑奖的总数
	 * */
	public int getGoodsGetCount(GoodsGetBean goodsGetBean){
		try {
			if(!Text.isNullOrEmpty(goodsGetBean.getStrExchangeDate())){
				goodsGetBean.setExchangeDate(format.parse(goodsGetBean.getStrExchangeDate()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return goodsGetDao.getGoodsGetCount(goodsGetBean);
	}
	
	public List<GoodsGetBean> getGoodsGetByUser(String account){
		return goodsGetDao.getGoodsGetByUser(account);
		
	}
	public List<GoodsGetBean> getGoodsRecord(GoodsGetBean goodsGetBean){
		return goodsGetDao.getGoodsRecord(goodsGetBean);
	}
	public List<GoodsGetBean> getGoodsUserRecord(String account){
		return goodsGetDao.getGoodsUserRecord(account);
		
	}
	public List<GoodsGetBean> getGoodsRecordTop(){
		return goodsGetDao.getGoodsRecordTop();
		
	}
}
