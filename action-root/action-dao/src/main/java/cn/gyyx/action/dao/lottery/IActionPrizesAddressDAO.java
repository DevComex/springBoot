package cn.gyyx.action.dao.lottery;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;

public interface IActionPrizesAddressDAO {

	// 查询
	List<ActionPrizesAddressPO> getDataList(ActionPrizesAddressPO po);
	// 创建
	void postData(ActionPrizesAddressPO po, SqlSession session) throws Exception;
	// 更新
	void putData(ActionPrizesAddressPO po, SqlSession session) throws Exception;
	
	// 查询
	List<ActionPrizesAddressPO> get(ActionPrizesAddressPO po);
	// 创建
	int post(ActionPrizesAddressPO po, SqlSession session) throws Exception;
	// 更新
	int put(ActionPrizesAddressPO po, SqlSession session) throws Exception;
	
	List<ActionPrizesAddressPO> getUserAddressPaging(int pageSize,int currentPage);
	
	List<ActionPrizesAddressPO> getUserAddress();
	Integer getCount();
	
}
