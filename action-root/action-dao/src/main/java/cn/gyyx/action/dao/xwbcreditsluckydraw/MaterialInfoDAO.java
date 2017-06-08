/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月2日
 * @版本号：V1.214
 * @本类主要用途描述：素材信息数据处理类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.MaterialAuditBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MaterialInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class MaterialInfoDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(MaterialInfoDAO.class);
	IMaterialInfoMapper iMaterialInfoMapper;

	/**
	 * 
	 * @日期：2015年9月2日
	 * @Title: getSession
	 * @Description: TODO 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 增加素材信息
	 * @param materialInfoBean
	 */
	public Integer insertMaterialInfo(MaterialInfoBean materialInfoBean){
		Integer count = 0;
		SqlSession session = getSession();
		try {
			iMaterialInfoMapper = session
					.getMapper(IMaterialInfoMapper.class);
			count = iMaterialInfoMapper.insertMaterialInfo(materialInfoBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return count;
	}
	/**
	 * 查询素材信息
	 * @param materialInfoBean
	 * @return
	 */
	public MaterialInfoBean selectMaterialInfo(MaterialInfoBean materialInfoBean){
		MaterialInfoBean materialInfo = null;
		SqlSession session = getSession();
		try {
			iMaterialInfoMapper = session
					.getMapper(IMaterialInfoMapper.class);
			materialInfo = iMaterialInfoMapper.selectMaterialInfo(materialInfoBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return materialInfo;
	}
	
	/**
	 * 根据code查询素材
	 * @param code
	 * @return
	 */
	public MaterialInfoBean getMaterialInfoByCode(Integer code){
		MaterialInfoBean materialInfo = null;
		SqlSession session = getSession();
		try {
			iMaterialInfoMapper = session
					.getMapper(IMaterialInfoMapper.class);
			materialInfo = iMaterialInfoMapper.getMaterialInfoByCode(code);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return materialInfo;
	}
	
	/**
	 * 增加正式图片地址
	 * @param materialInfoBean
	 */
	public void setRealUrl(MaterialInfoBean materialInfoBean){
		SqlSession session = getSession();
		try {
			iMaterialInfoMapper = session
					.getMapper(IMaterialInfoMapper.class);
			iMaterialInfoMapper.setRealUrl(materialInfoBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}
	
}
