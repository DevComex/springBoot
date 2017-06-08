package cn.gyyx.action.dao.lottery.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.dao.lottery.IActionPrizesAddressDAO;
import cn.gyyx.action.dao.lottery.mapper.IActionPrizesAddressMapper;

public class ActionPrizesAddressDAOImpl extends MyBatisBaseDAO implements IActionPrizesAddressDAO {

	public List<ActionPrizesAddressPO> getDataList(ActionPrizesAddressPO po) {
		List<ActionPrizesAddressPO> result = null;
		SqlSession session = null;
		
		try {
			session = this.getSession();
			
			IActionPrizesAddressMapper mapper = session.getMapper(IActionPrizesAddressMapper.class);
			
			result = mapper.getDataList(po);
		}
		catch(Exception e) {
			logger.error("ActionPrizesAddressDAOImpl.getAddressByWeChat", e);
		}
		
		return result;
	}
	
	public void postData(ActionPrizesAddressPO po, SqlSession session) throws Exception {
		try {
			IActionPrizesAddressMapper mapper = session.getMapper(IActionPrizesAddressMapper.class);
			
			mapper.postData(po);
		}
		catch(Exception e) {
			logger.error("ActionPrizesAddressDAOImpl.getAddressByWeChat", e);
			throw new Exception(e.getCause());
		}
	}
	
	public void putData(ActionPrizesAddressPO po, SqlSession session) throws Exception {
		try {
			IActionPrizesAddressMapper mapper = session.getMapper(IActionPrizesAddressMapper.class);
			
			mapper.putData(po);
		}
		catch(Exception e) {
			logger.error("ActionPrizesAddressDAOImpl.getAddressByWeChat", e);
			throw new Exception(e.getCause());
		}
	}
	
	// 查询
	public List<ActionPrizesAddressPO> get(ActionPrizesAddressPO params) {
		List<ActionPrizesAddressPO> result = null;
		
		try(SqlSession session = this.getSession()) {
			IActionPrizesAddressMapper mapper = session.getMapper(IActionPrizesAddressMapper.class);
			
			result = mapper.select(params);
		}
		catch(Exception e) {
			logger.error("ActionPrizesAddressDAOImpl->get->Cause:" + e.getCause());
			logger.error("ActionPrizesAddressDAOImpl->get->Message:" + e.getMessage());
			logger.error("ActionPrizesAddressDAOImpl->get->StackTrace:" + e.getStackTrace());
		}
		
		return result;
	}
	
	// 创建
	public int post(ActionPrizesAddressPO params, SqlSession session) throws Exception {
		int result = -1;
		
		try {
			IActionPrizesAddressMapper mapper = session.getMapper(IActionPrizesAddressMapper.class);
			
			result = mapper.insert(params);
		}
		catch(Exception e) {
			logger.error("ActionPrizesAddressDAOImpl->post->Cause:" + e.getCause());
			logger.error("ActionPrizesAddressDAOImpl->post->Message:" + e.getMessage());
			logger.error("ActionPrizesAddressDAOImpl->post->StackTrace:" + e.getStackTrace());
			throw new Exception(e.getCause());
		}
		
		return result;
	}
	
	// 更新
	public int put(ActionPrizesAddressPO params, SqlSession session) throws Exception {
		int result = -1;
		
		try {
			IActionPrizesAddressMapper mapper = session.getMapper(IActionPrizesAddressMapper.class);
			
			result = mapper.update(params);
		}
		catch(Exception e) {
			logger.error("ActionPrizesAddressDAOImpl->put->Cause:" + e.getCause());
			logger.error("ActionPrizesAddressDAOImpl->put->Message:" + e.getMessage());
			logger.error("ActionPrizesAddressDAOImpl->put->StackTrace:" + e.getStackTrace());
			throw new Exception(e.getCause());
		}
		
		return result;
	}
	
	/**
	 * 获取用户中奖纪录加地址-所有
	 * @return
	 */
	public List<ActionPrizesAddressPO> getUserAddress(){
		try (SqlSession session = getSession()) {
			IActionPrizesAddressMapper addressMapper = session
					.getMapper(IActionPrizesAddressMapper.class);
			return addressMapper.getUserAddress();
		}
	}

	/**
	 * 获取全部记录条数
	 * @return
	 */
	public Integer getCount(){
		try (SqlSession session = getSession()) {
			IActionPrizesAddressMapper addressMapper = session
					.getMapper(IActionPrizesAddressMapper.class);
			return addressMapper.getCount();
		}
	}

	/**
	 * 获取用户中奖纪录加地址-分页
	 * @return
	 */
	@Override
	public List<ActionPrizesAddressPO> getUserAddressPaging(int pageSize,
			int currentPage) {
		try (SqlSession session = getSession()) {
			IActionPrizesAddressMapper addressMapper = session
					.getMapper(IActionPrizesAddressMapper.class);
			return addressMapper.getUserAddressPaging(pageSize,currentPage);
		}
	}
}
