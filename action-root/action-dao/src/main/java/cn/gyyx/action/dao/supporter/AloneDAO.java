package cn.gyyx.action.dao.supporter;

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
import cn.gyyx.action.common.dictionary.UploadType;
import cn.gyyx.action.common.dictionary.UploadType.NamePair;
import cn.gyyx.action.common.util.JsonUtil;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class AloneDAO {
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	public boolean addAloneForm(Map<String, Object> aloneForm) {
		try(SqlSession session = getSession()) {
			IAloneTb mapper = session.getMapper(IAloneTb.class);
			mapper.addAloneForm(aloneForm);
			session.commit();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public boolean updateAloneForm(Map<String, Object> aloneForm) {
		try(SqlSession session = getSession()) {
			IAloneTb mapper = session.getMapper(IAloneTb.class);
			mapper.updateAloneForm(aloneForm);
			session.commit();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public int queryAloneFormNum(InnerTransmissionData queryBean) {
		try(SqlSession session = getSession()) {
			IAloneTb mapper = session.getMapper(IAloneTb.class);
			return mapper.queryAloneFormNum(queryBean);
		}
	}
	public List<Map<String,Object>> queryAloneForm(InnerTransmissionData queryBean) {
		List<Map<String,Object>> result = new LinkedList<Map<String,Object>>();
		UploadType uType = queryBean.getUploadType();
		QueryType qType = queryBean.getQueryType();
		try(SqlSession session = getSession()) {
			IAloneTb mapper = session.getMapper(IAloneTb.class);
			List<Map<String,Object>> aloneForms = mapper.queryAloneForm(queryBean);
			if(aloneForms != null) {
				for(Map<String,Object> aloneForm:aloneForms) {
					Map<String,Object> map = JsonUtil.parseJSON2Map((String) aloneForm.get(DBColum.JSON_STR));
					map.put(Dictionary.getJNameByColumnName(DBColum.CODE), aloneForm.get(DBColum.CODE));
					List<NamePair> namePair = uType.getPairs();
					for(NamePair np:namePair) {
						if(DBColum.COLUM_SET.contains(np.getInnerName())) {
							map.put(np.getOuterName(),aloneForm.get(np.getInnerName()));
						}
					}
					//将开放元素放入map结果中 
					Set<String> outputSet = qType.getExtraOutput();
					for(String s:outputSet) {
						if(aloneForm.containsKey(s)) {
							map.put(Dictionary.getJNameByColumnName(s), aloneForm.get(s));
						}
					}
					//舍弃不该显示在前台的元素
					for(String s:qType.getRemovedOutput()) {
						map.remove(s);
					}
					result.add(map);
				}
			}
		}
		return result;
	}
}
