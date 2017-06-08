package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.xwbcreditsluckydraw.GoodsGetBean;

public interface IGoodsGetMapper {
	/**
	 * 添加中奖或兑换记录
	 * */
	void addGoodsGet(GoodsGetBean goodsGetBean);
	/**
	 * 分页查询goodsGetBean
	 * */
	List<GoodsGetBean> getGoodsGetByPage(Map<String, Object> paramMap);
	/**
	 * 查询中奖或兑换记录的总数量
	 * */
	int getGoodsGetCount(GoodsGetBean goodsGetBean);
	List<GoodsGetBean> getGoodsGetByUser(String account);
	List<GoodsGetBean> getGoodsRecord(GoodsGetBean goodsGetBean);
	List<GoodsGetBean> getGoodsUserRecord(String account);
	List<GoodsGetBean> getGoodsRecordTop();
}
