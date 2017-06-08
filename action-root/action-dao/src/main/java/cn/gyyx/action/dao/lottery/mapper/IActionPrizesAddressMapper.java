package cn.gyyx.action.dao.lottery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;

public interface IActionPrizesAddressMapper {
	
	// 查询
	List<ActionPrizesAddressPO> getDataList(ActionPrizesAddressPO po);
	
	// 创建
	void postData(ActionPrizesAddressPO po);
	
	// 更新
	void putData(ActionPrizesAddressPO po);
	
	// 查询
	List<ActionPrizesAddressPO> select(ActionPrizesAddressPO params);
	// 创建
	int insert(ActionPrizesAddressPO params);
	// 更新
	int update(ActionPrizesAddressPO params);
	//获取地址信息
	public List<ActionPrizesAddressPO> getUserAddress();
	
	//获取地址信息
	public List<ActionPrizesAddressPO> getUserAddressPaging(@Param("pageSize")int pageSize, @Param("currentPage")int currentPage);

	Integer getCount();
}
