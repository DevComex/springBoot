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
import cn.gyyx.action.common.dictionary.DictionaryContainer;
import cn.gyyx.action.common.dictionary.QueryType;
import cn.gyyx.action.common.dictionary.UploadType;
import cn.gyyx.action.common.dictionary.UploadType.NamePair;
import cn.gyyx.action.common.util.JsonUtil;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class SubTbDAO {
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	public boolean addSubForm(Map<String, Object> subForm) {
		try(SqlSession session = getSession()) {
			ISubTb mapper = session.getMapper(ISubTb.class);
			mapper.addSubForm(subForm);
			session.commit();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public boolean updateSubForm(Map<String, Object> subForm) {
		try(SqlSession session = getSession()) {
			ISubTb mapper = session.getMapper(ISubTb.class);
			mapper.updateSubForm(subForm);
			session.commit();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public int querySubFormNum(InnerTransmissionData queryBean) {
		try(SqlSession session = getSession()) {
			ISubTb mapper = session.getMapper(ISubTb.class);
			return mapper.querySubFormNum(queryBean);
		}
	}
	public List<Map<String,Object>> querySubForm(InnerTransmissionData queryBean) {
		List<Map<String,Object>> result = new LinkedList<Map<String,Object>>();
		UploadType uType = queryBean.getUploadType();
		QueryType qType = queryBean.getQueryType();
		try(SqlSession session = getSession()) {
			ISubTb mapper = session.getMapper(ISubTb.class);
			List<Map<String,Object>> subForms = mapper.querySubForm(queryBean);
			if(subForms != null) {
				for(Map<String,Object> subForm:subForms) {
					Map<String,Object> map = JsonUtil.parseJSON2Map((String) subForm.get(DBColum.JSON_STR));
					map.put(Dictionary.getJNameByColumnName(DBColum.CODE), subForm.get(DBColum.CODE));
					//将开放元素放入map结果中 
					Set<String> outputSet = qType.getExtraOutput();
					for(String s:outputSet) {
						if(subForm.containsKey(s)) {
							map.put(Dictionary.getJNameByColumnName(s), subForm.get(s));
						}
					}
					//转换前后端数据名称
					List<NamePair> namePair = uType.getPairs();
					for(NamePair np:namePair) {
						if(DBColum.COLUM_SET.contains(np.getInnerName())) {
							map.put(np.getOuterName(),subForm.get(np.getInnerName()));
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
	
	public List<Map<String,Object>> getAllSubForm(int actionCode,int mainCode,String type) {
		List<Map<String,Object>> res = new LinkedList<Map<String,Object>>();
		Dictionary dic = DictionaryContainer.getDictionary(actionCode);
		UploadType uType = dic.getUploadType(type);
		try(SqlSession session = getSession()) {
			ISubTb mapper = session.getMapper(ISubTb.class);
			List<Map<String,Object>> dbRes = mapper.getAllSubForm(actionCode,mainCode,type);
			for(Map<String,Object> subForm:dbRes) {
				Map<String,Object> map = JsonUtil.parseJSON2Map((String) subForm.get(DBColum.JSON_STR));
				//转换前后端数据名称
				List<NamePair> namePair = uType.getPairs();
				for(NamePair np:namePair) {
					if(DBColum.COLUM_SET.contains(np.getInnerName())) {
						map.put(np.getOuterName(),subForm.get(np.getInnerName()));
					}
				}
				res.add(map);
			}
			return res;
		}
	}
}
