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

public class MainTbDAO {
	private SubTbDAO subDao = new SubTbDAO();
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	public boolean addMainForm(Map<String, Object> mainForm,Map<String,Object> searchMap) {
		try(SqlSession session = getSession()) {
			IMainTb mapper = session.getMapper(IMainTb.class);
			mapper.addMainForm(mainForm);
			if(searchMap.size() > 0) {
				searchMap.put(DBColum.MAIN_CODE, mainForm.get(DBColum.CODE));
				mapper.addSearchMap(searchMap);
			}
			session.commit();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	@Deprecated
	public boolean updateMainForm(Map<String, Object> mainForm,Map<String,Object> searchMap) {
		try(SqlSession session = getSession()) {
			IMainTb mapper = session.getMapper(IMainTb.class);
			mapper.updateMainForm(mainForm);
			if(searchMap.size() > 0) {
				searchMap.put(DBColum.MAIN_CODE,mainForm.get(DBColum.CODE));
				mapper.updateSearchForm(searchMap);
			}
			session.commit();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public int queryMainFormNum(InnerTransmissionData queryBean) {
		try(SqlSession session = getSession()) {
			IMainTb mapper = session.getMapper(IMainTb.class);
			return mapper.queryMainFormNum(queryBean);
		}
	}
	public List<Map<String,Object>> queryMainForm(InnerTransmissionData queryBean) {
		List<Map<String,Object>> result = new LinkedList<Map<String,Object>>();
		UploadType uType = queryBean.getUploadType();
		QueryType qType = queryBean.getQueryType();
		try(SqlSession session = getSession()) {
			IMainTb mapper = session.getMapper(IMainTb.class);
			List<Map<String,Object>> mainForms = mapper.queryMainForm(queryBean);
			if(mainForms != null) {
				for(Map<String,Object> mainForm:mainForms) {
					Map<String,Object> map = JsonUtil.parseJSON2Map((String) mainForm.get(DBColum.JSON_STR));
					map.put(Dictionary.getJNameByColumnName(DBColum.CODE), mainForm.get(DBColum.CODE));
					//进行关联设置
					for(QueryType type:qType.getJoinTypes()) {
						InnerTransmissionData subQuery = new InnerTransmissionData();
						subQuery.setData(DBColum.ACTION_CODE,mainForm.get(DBColum.ACTION_CODE));
						subQuery.setData(DBColum.TYPE, type.getUploadType().getTypeName());
						
						subQuery.setData(DBColum.ACCOUNT, mainForm.get(DBColum.ACCOUNT));
						subQuery.setData(DBColum.ACCOUNT_CODE, mainForm.get(DBColum.ACCOUNT_CODE));
						subQuery.setData(DBColum.UPLOAD_DATE, mainForm.get(DBColum.UPLOAD_DATE));
						subQuery.setData(DBColum.UPLOAD_SQLDATE, mainForm.get(DBColum.UPLOAD_SQLDATE));
						
						subQuery.setData(DBColum.MAIN_CODE,mainForm.get(DBColum.CODE));
						subQuery.setQueryType(type);
						subQuery.setUploadType(type.getUploadType());
						map.put(type.getQueryType(),subDao.querySubForm(subQuery));
					}
					//将开放元素放入map结果中 
					Set<String> outputSet = qType.getExtraOutput();
					for(String s:outputSet) {
						if(mainForm.containsKey(s)) {
							map.put(Dictionary.getJNameByColumnName(s), mainForm.get(s));
						}
					}
					List<NamePair> namePair = uType.getPairs();
					for(NamePair np:namePair) {
						if(DBColum.COLUM_SET.contains(np.getInnerName())) {
							map.put(np.getOuterName(),mainForm.get(np.getInnerName()));
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
