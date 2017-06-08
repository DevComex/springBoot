package cn.gyyx.action.dao.wdshenluowangxiang;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdshenluowangxiang.ShenLuoWangXiangBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

public class ShenLuoWangXiangDao extends MyBatisBaseDAO{

	public ShenLuoWangXiangBean getUserInfoByUserId(Integer userId) throws Exception {
	        try(SqlSession session = getSession(true);) {
	            session.getConnection().setReadOnly(true);
	            ShenLuoWangXiangMapper  mapper = session
	                    .getMapper(ShenLuoWangXiangMapper.class);
	            return mapper.getUserInfoByUserId(userId);
	        } 
	}

	public void instert(ShenLuoWangXiangBean bindBean) {
	        try (SqlSession session = getSession(true);){
	        	ShenLuoWangXiangMapper mapper = session
	                    .getMapper(ShenLuoWangXiangMapper.class);
	            mapper.insertSelective(bindBean);
	        } 
		
	}
	/**
	 * 修改已抽奖次数
	 * @param userId
	 */
	public void updateLuckNum(Integer userId, SqlSession session) {
	    ShenLuoWangXiangMapper mapper = session
                    .getMapper(ShenLuoWangXiangMapper.class);
            mapper.updateLuckNum(userId)  ;
	}
     
}