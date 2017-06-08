package cn.gyyx.action.dao.supporter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.common.beans.InnerTransmissionData;
import cn.gyyx.action.common.dictionary.DBColum;
import cn.gyyx.action.common.dictionary.Dictionary;
import cn.gyyx.action.common.dictionary.QueryType;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class EventDAO {
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	public boolean addEventForm(Map<String, Object> eventForm) {
		try(SqlSession session = getSession()) {
			IEventTb mapper = session.getMapper(IEventTb.class);
			mapper.addEventForm(eventForm);
			session.commit();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public int queryEventFormNum(InnerTransmissionData queryBean) {
		try(SqlSession session = getSession()) {
			IEventTb mapper = session.getMapper(IEventTb.class);
			return mapper.queryEventFormNum(queryBean);
		}
	}
	public List<Map<String,Object>> queryEventForm(InnerTransmissionData queryBean) {
		List<Map<String,Object>> result = new LinkedList<Map<String,Object>>();
		QueryType qType = queryBean.getQueryType();
		try(SqlSession session = getSession()) {
			IEventTb mapper = session.getMapper(IEventTb.class);
			List<Map<String,Object>> eventForms = mapper.queryEventForm(queryBean);
			if(eventForms != null) {
				for(Map<String,Object> eventForm:eventForms) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put(Dictionary.getJNameByColumnName(DBColum.CODE), eventForm.get(DBColum.CODE));
					//将开放元素放入map结果中 
					Set<String> outputSet = qType.getExtraOutput();
					for(String s:outputSet) {
						if(eventForm.containsKey(s)) {
							map.put(Dictionary.getJNameByColumnName(s), eventForm.get(s));
						}
					}
					result.add(map);
				}
			}
		}
		return result;
	}
}
