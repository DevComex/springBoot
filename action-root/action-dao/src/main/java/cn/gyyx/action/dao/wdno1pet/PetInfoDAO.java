/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：Chen 
 * 联系方式：chenpeng03@gyyx.cn 
 * 创建时间： 2014年12月15日 下午2:26:08
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 连接数据库，查询PetInfo
-------------------------------------------------------------------------*/
package cn.gyyx.action.dao.wdno1pet;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wdno1pet.PetsQuery;
import cn.gyyx.action.beans.wdno1pet.WDNo1PetInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class PetInfoDAO implements PetInfoMapper {

	public WDNo1PetInfoBean getPetInfoByPetCode(int pcode) {
		SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {
			PetInfoMapper petInfoDao = sqlsession
					.getMapper(PetInfoMapper.class);
			return petInfoDao.getPetInfoByPetCode(pcode);
		} finally {
			sqlsession.close();
		}
	}

	/**
	 * 上传一个参赛作品到数据库
	 * 
	 * @param petInfo
	 *            参赛作品实体
	 */
	public void uploadPetInfo(WDNo1PetInfoBean petInfo) {
		try (SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession()) {
			PetInfoMapper petInfoDao = sqlsession
					.getMapper(PetInfoMapper.class);
			petInfoDao.uploadPetInfo(petInfo);
			// 提交上传参赛作品
			sqlsession.commit();
		}
	}

	/**
	 * 增加一个票数的实现
	 * 
	 * @param petCode
	 */
	public void updateVote(int petCode, Timestamp voteTime) {
		try (SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession()) {
			PetInfoMapper petInfoDao = sqlsession
					.getMapper(PetInfoMapper.class);
			petInfoDao.updateVote(petCode, voteTime);
			// 提交上传参赛作品
			sqlsession.commit();
		}
	}

	/**
	 * 根据作品类别、查询策略、服务器id、角色名、宠物作品名、页面索引号返回符合条件的作品集合
	 * 
	 * @param query
	 *            查询参数实体
	 * @return 满足条件的作品集合
	 */
	public List<WDNo1PetInfoBean> getBeanByStrategyAndKeys(PetsQuery query) {
		try (SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession()) {
			PetInfoMapper petInfoDao = sqlsession
					.getMapper(PetInfoMapper.class);
			return petInfoDao.getBeanByStrategyAndKeys(query);
		}
	}

	@Override
	public List<WDNo1PetInfoBean> getHottestPetsByType(String type,
			Integer limit) {
		try (SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession()) {
			PetInfoMapper petInfoDao = sqlsession
					.getMapper(PetInfoMapper.class);
			return petInfoDao.getHottestPetsByType(type, limit);
		}
	}

	@Override
	public List<WDNo1PetInfoBean> getStrongestPetsByQuality(String qualityName,
			Integer limit) {
		try (SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession()) {
			PetInfoMapper petInfoDao = sqlsession
					.getMapper(PetInfoMapper.class);
			return petInfoDao.getStrongestPetsByQuality(qualityName, limit);
		}
	}
	
	@Override
	public int getPetCountByStrategyAndKeys(PetsQuery query){
		try (SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession()) {
			PetInfoMapper petInfoDao = sqlsession
					.getMapper(PetInfoMapper.class);
			return petInfoDao.getPetCountByStrategyAndKeys(query);
		}
	}

	@Override
	public int getPetCountByUserCode(int userCode) {
		try (SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession()) {
			PetInfoMapper petInfoDao = sqlsession
					.getMapper(PetInfoMapper.class);
			return petInfoDao.getPetCountByUserCode(userCode);
		}
	}

	@Override
	public List<Integer> getServerIdListByExist() {
		try (SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession()) {
			PetInfoMapper petInfoDao = sqlsession
					.getMapper(PetInfoMapper.class);
			return petInfoDao.getServerIdListByExist();
		}
	}
	/**
	 * 实现通过imgCode查询作品
	 */
	@Override
	public WDNo1PetInfoBean getPetInfoByImgCode(int imgCode) {
		// TODO Auto-generated method stub
		try (SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession()) {
			PetInfoMapper petInfoDao  = sqlsession
					.getMapper(PetInfoMapper.class);
			WDNo1PetInfoBean wNo1PetInfoBean=petInfoDao.getPetInfoByImgCode(imgCode);
			return wNo1PetInfoBean;
			
		}
	}


}
