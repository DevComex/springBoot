package cn.gyyx.action.dao.wdshenluowangxiang;

import org.apache.ibatis.session.SqlSession;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.wdshenluowangxiang.ShenLuoWangXiangAddressBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

public class ShenLuoWangXiangAddressDao extends MyBatisBaseDAO{
 
	/**
	 * 添加邀请地址
	 * @param userId
	 */
	public void insertAddress(ShenLuoWangXiangAddressBean addressBean) {
            try(SqlSession session = getSession(true);) {
                ShenLuoWangXiangAddressMapper mapper = session
                        .getMapper(ShenLuoWangXiangAddressMapper.class);
                mapper.insertSelective(addressBean) ;
            } catch (Exception e) {
                logger.error("insterAddress数据库异常:错误堆栈{}",
                    Throwables.getStackTraceAsString(e));
            } 
	}
	
	/**
	 * 
	  * <p>
	  *    查询地址by userId
	  * </p>
	  *
	  * @action
	  *    chenglong 2017年4月10日 下午2:30:11 查询地址by userId
	  *
	  * @param userId
	  * @return Integer
	 */
	public ShenLuoWangXiangAddressBean selectAddressByUserId(Integer userId) {
            try(SqlSession session = getSession(true);) {
                session.getConnection().setReadOnly(true);
                ShenLuoWangXiangAddressMapper  mapper = session
                        .getMapper(ShenLuoWangXiangAddressMapper.class);
                return mapper.selectByUserId(userId);
            } catch (Exception e) {
                logger.error("selectByUserIdError数据库异常:错误堆栈:{}",
                    Throwables.getStackTraceAsString(e));
            } 
            return null;
	}
	
	/**
	 * 
	  * <p>
	  *    更新地址
	  * </p>
	  *
	  * @action
	  *    chenglong 2017年4月10日 下午2:29:51 更新地址
	  *
	  * @param addressBean void
	 */
	public void updateAddress(ShenLuoWangXiangAddressBean addressBean) {
            try(SqlSession session = getSession(true);) {
            	ShenLuoWangXiangAddressMapper mapper = session
                        .getMapper(ShenLuoWangXiangAddressMapper.class);
                mapper.updateByPrimaryKeySelective(addressBean) ;
            } catch (Exception e) {
                logger.error("updateAddress数据库异常:错误堆栈:{}",
                    Throwables.getStackTraceAsString(e));
            } 
	}

	 
}