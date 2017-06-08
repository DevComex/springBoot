package cn.gyyx.action.dao.noviceoa;

import cn.gyyx.action.beans.noviceoa.NoviceGiftGenerateBean;
import cn.gyyx.action.beans.noviceoa.NoviceGiftGenerateInfoBean;
import cn.gyyx.action.dao.MyBatisMySQLConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class NoviceGiftGenerateDao {
	SqlSessionFactory factory = MyBatisMySQLConnectionFactory.getSqlActivityDBSessionFactory();

	public int insert(NoviceGiftGenerateBean record) {
		try (SqlSession session = factory.openSession(true)) {
			NoviceGiftGenerateBeanMapper mapper = session.getMapper(NoviceGiftGenerateBeanMapper.class);
			mapper.insert(record);
			return record.getCode() == null ? 0 : record.getCode();
		}
	}

	/**
	 * 新增并生成新手卡
	 * 
	 * @param record
	 * @return
	 */
	public int insertSelective(NoviceGiftGenerateBean record, List<String> batchCodeList) {
		try (SqlSession session = factory.openSession(true)) {
			NoviceGiftGenerateBeanMapper mapper = session.getMapper(NoviceGiftGenerateBeanMapper.class);
			mapper.insertSelective(record);
			if (record.getCode() > 0) {
				NoviceCardBeanMapper noviceCardMapper = session.getMapper(NoviceCardBeanMapper.class);
				for (String codeSql : batchCodeList) {
					codeSql = codeSql.replaceAll("##", record.getCode().toString());
					// 向新手卡表批量插入新手卡
					noviceCardMapper.insertBatchCodeSql(codeSql);
				}
			}
			return record.getCode();
		}
	}

	public NoviceGiftGenerateBean selectByPrimaryKey(Integer code) {
		try (SqlSession session = factory.openSession(true)) {
			NoviceGiftGenerateBeanMapper mapper = session.getMapper(NoviceGiftGenerateBeanMapper.class);
			return mapper.selectByPrimaryKey(code);
		}
	}

	public int updateByPrimaryKeySelective(NoviceGiftGenerateBean record) {
		try (SqlSession session = factory.openSession(true)) {
			NoviceGiftGenerateBeanMapper mapper = session.getMapper(NoviceGiftGenerateBeanMapper.class);
			return mapper.updateByPrimaryKeySelective(record);
		}
	}

	public List<NoviceGiftGenerateInfoBean> selectByBatchId(Integer batchId) {
		try (SqlSession session = factory.openSession(true)) {
			NoviceGiftGenerateBeanMapper mapper = session.getMapper(NoviceGiftGenerateBeanMapper.class);
			return mapper.selectByBatchId(batchId);
		}
	}

	public List<NoviceGiftGenerateBean> selectByChannel(String channel) {
		try (SqlSession session = factory.openSession(true)) {
			NoviceGiftGenerateBeanMapper mapper = session.getMapper(NoviceGiftGenerateBeanMapper.class);
			return mapper.selectByChannel(channel);
		}
	}

	public List<NoviceGiftGenerateBean> selectByGiftId(Integer giftId) {
		try (SqlSession session = factory.openSession(true)) {
			NoviceGiftGenerateBeanMapper mapper = session.getMapper(NoviceGiftGenerateBeanMapper.class);
			return mapper.selectByGiftId(giftId);
		}
	}

}
