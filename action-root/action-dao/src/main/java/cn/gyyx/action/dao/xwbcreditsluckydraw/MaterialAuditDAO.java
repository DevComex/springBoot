package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.CollectTopShowBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MaterialAuditBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class MaterialAuditDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(MaterialAuditDAO.class);

	/**
	 * 
	 * @日期：2015年7月8日
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
	 * 
	 * @日期：2015年10月15日
	 * @Title: addMaterialAudit
	 * @Description: TODO 新增素材
	 * @return void
	 */
	public Integer addMaterialAudit(MaterialAuditBean materialAuditBean) {
		Integer count = 0;
		SqlSession session = getSession();
		try {
			IMaterialAuditMapper mapper = session
					.getMapper(IMaterialAuditMapper.class);
			count = mapper.addMaterialAudit(materialAuditBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("addMaterialAudit" + e.toString());
		} finally {
			session.close();
		}
		return count;
	}

	/**
	 * 
	 * @日期：2015年7月13日
	 * @Title: getMaterialAuditByPage
	 * @Description: TODO 分页查询素材
	 * @return void
	 */
	public List<MaterialAuditBean> getMaterialAuditByPage(
			Map<String, Object> paramMap) {
		SqlSession session = getSession();
		try {
			IMaterialAuditMapper mapper = session
					.getMapper(IMaterialAuditMapper.class);
			return mapper.getMaterialAuditByPage(paramMap);
		} catch (Exception e) {
			logger.warn("getMaterialAuditByPage" + e.toString());
		} finally {
			session.close();
		}
		return null;
	}

	/**
	 * 
	 * @日期：2015年7月13日
	 * @Title: getMaterialAuditCount
	 * @Description: TODO 查询素材数量
	 * @return Integer
	 */
	public Integer getMaterialAuditCount(MaterialAuditBean materialAuditBean) {
		SqlSession session = getSession();
		try {
			IMaterialAuditMapper mapper = session
					.getMapper(IMaterialAuditMapper.class);
			return mapper.getMaterialAuditCount(materialAuditBean);
		} catch (Exception e) {
			logger.warn("getMaterialAuditCount" + e.toString());
		} finally {
			session.close();
		}
		return null;
	}

	/**
	 * 查询素材显示
	 * @param materialAuditBean（is_show;materialType）
	 * @return
	 */
	public List<MaterialAuditBean> selectMaterialShow(MaterialAuditBean materialAuditBean){
		SqlSession session = getSession();
		List<MaterialAuditBean> list = null;
		try{
			IMaterialAuditMapper mapper = session.getMapper(IMaterialAuditMapper.class);
			list = mapper.selectMaterialShow(materialAuditBean);
		}catch(Exception e){
			logger.warn("selectMaterialShow查询素材显示"+e.toString());
		}finally{
			session.close();
		}
		return list;
	}
	/**
	 * 查询个人素材显示
	 * @param account（is_show;account）
	 * @return
	 */
	public List<MaterialAuditBean> selectMaterialUserShow(String account){
		SqlSession session = getSession();
		List<MaterialAuditBean> list = null;
		try{
			IMaterialAuditMapper mapper = session.getMapper(IMaterialAuditMapper.class);
			list = mapper.selectMaterialUserShow(account);
		}catch(Exception e){
			logger.warn("selectMaterialShow查询素材显示"+e.toString());
		}finally{
			session.close();
		}
		return list;
	}
	/**
	 * @日期：2015年7月15日
	 * @Title: updateIsChecked
	 * @Description: TODO 修改审核状态
	 * @return void
	 * */
	public void updateIsChecked(String checkedStatus, Integer code) {
		SqlSession session = getSession();
		try {
			IMaterialAuditMapper mapper = session
					.getMapper(IMaterialAuditMapper.class);
			mapper.updateIsChecked(checkedStatus, code);
			session.commit();
		} catch (Exception e) {
			logger.warn("getMaterialAuditCount" + e.toString());
		} finally {
			session.close();
		}
	}

	/**
	 * 重置首页显示状态
	 * @author fanyongliang
	 */
	public void resetIsShow() {
		SqlSession session = getSession();
		try {
			IMaterialAuditMapper mapper = session
					.getMapper(IMaterialAuditMapper.class);
			mapper.resetIsShow();
			session.commit();
		} catch (Exception e) {
			logger.warn("resetIsShow" + e.toString());
		} finally {
			session.close();
		}
	}

	/**
	 * 修改首页显示状态
	 * @author fanyongliang
	 */
	public void updateIsShow(Integer code) {
		SqlSession session = getSession();
		try {
			IMaterialAuditMapper mapper = session
					.getMapper(IMaterialAuditMapper.class);
			mapper.updateIsShow(code);
			session.commit();
		} catch (Exception e) {
			logger.warn("updateIsShow" + e.toString());
		} finally {
			session.close();
		}
	}
	
	/**
	 * 征集首页显示
	 * @return
	 */
	public List<MaterialAuditBean> getCollectTopShow(CollectTopShowBean collectTopShowBean){
		SqlSession session = getSession();
		List<MaterialAuditBean> list = null;
		try{
			IMaterialAuditMapper mapper = session.getMapper(IMaterialAuditMapper.class);
			list = mapper.getCollectTopShow(collectTopShowBean);
		}catch(Exception e){
			logger.warn("getCollectTopShow"+e.toString());
		}finally{
			session.close();
		}
		return list;
	}
	
	/**
	 * 点赞
	 * @param num
	 * @param code
	 */
	public void updatePraiseInfo(Integer num,Integer code){
		SqlSession session = getSession();
		try {
			IMaterialAuditMapper mapper = session
					.getMapper(IMaterialAuditMapper.class);
			mapper.updatePraiseInfo(num,code);
			session.commit();
		} catch (Exception e) {
			logger.warn("updatePraiseInfo" + e.toString());
		} finally {
			session.close();
		}
	}

	/**
	 * 视频
	 * @return
	 */
	public List<MaterialAuditBean> getVideoListWithName(String account){
		SqlSession session = getSession();
		List<MaterialAuditBean> list = null;
		try{
			IMaterialAuditMapper mapper = session.getMapper(IMaterialAuditMapper.class);
			list = mapper.getVideoListWithName(account);
		}catch(Exception e){
			logger.warn("getVideoListWithName"+e.toString());
		}finally{
			session.close();
		}
		return list;
	}
	

	/**
	 * 服装
	 * @return
	 */
	public List<MaterialAuditBean> getPhotoListWithName(String account){
		SqlSession session = getSession();
		List<MaterialAuditBean> list = null;
		try{
			IMaterialAuditMapper mapper = session.getMapper(IMaterialAuditMapper.class);
			list = mapper.getPhotoListWithName(account);
		}catch(Exception e){
			logger.warn("getPhotoListWithName"+e.toString());
		}finally{
			session.close();
		}
		return list;
	}

	/**
	 * 建议
	 * @return
	 */
	public List<MaterialAuditBean> getSuggestListWithName(String account){
		SqlSession session = getSession();
		List<MaterialAuditBean> list = null;
		try{
			IMaterialAuditMapper mapper = session.getMapper(IMaterialAuditMapper.class);
			list = mapper.getSuggestListWithName(account);
		}catch(Exception e){
			logger.warn("getSuggestListWithName"+e.toString());
		}finally{
			session.close();
		}
		return list;
	}
	
	/**
	 * 素材信息
	 * @return
	 */
	public MaterialAuditBean getMaterialInfoByCode(Integer code){
		SqlSession session = getSession();
		MaterialAuditBean list = new MaterialAuditBean();
		try{
			IMaterialAuditMapper mapper = session.getMapper(IMaterialAuditMapper.class);
			list = mapper.getMaterialInfoByCode(code);
		}catch(Exception e){
			logger.warn("getMaterialInfoByCode"+e.toString());
		}finally{
			session.close();
		}
		return list;
	}
	/**
	 * 修改评论数
	 * @param num
	 * @param materialCode
	 */
	public void updateCommentNum(Integer num,Integer materialCode){
		SqlSession session = getSession();
		try {
			IMaterialAuditMapper mapper = session
					.getMapper(IMaterialAuditMapper.class);
			mapper.updateCommentNum(num,materialCode);
			session.commit();
		} catch (Exception e) {
			logger.warn("updatePraiseInfo" + e.toString());
		} finally {
			session.close();
		}
	}
	/**
	 * 修改积分数
	 * @param num
	 * @param code
	 */
	public void updateCreditsNum(Integer num,Integer code){
		SqlSession session = getSession();
		try {
			IMaterialAuditMapper mapper = session
					.getMapper(IMaterialAuditMapper.class);
			mapper.updateCreditsNum(num,code);
			session.commit();
		} catch (Exception e) {
			logger.warn("updatePraiseInfo" + e.toString());
		} finally {
			session.close();
		}
	}
	/**
	 * 根据code查询显示首页状态 
	 * @param code
	 * @return
	 */
	public Integer getShowStatusByCode(Integer code){
		Integer count = 0;
		SqlSession session = getSession();
		try {
			IMaterialAuditMapper mapper = session
					.getMapper(IMaterialAuditMapper.class);
			count = mapper.getShowStatusByCode(code);
		} catch (Exception e) {
			logger.warn("getShowStatusByCode" + e.toString());
		} finally {
			session.close();
		}
		return count;
	}
	/**
	 * 查询显示素材信息
	 * @return
	 */
	public List<MaterialAuditBean> getShowMaterialInfo(){
		SqlSession session = getSession();
		List<MaterialAuditBean> list = null;
		try{
			IMaterialAuditMapper mapper = session.getMapper(IMaterialAuditMapper.class);
			list = mapper.getShowMaterialInfo();
		}catch(Exception e){
			logger.warn("getShowMaterialInfo"+e.toString());
		}finally{
			session.close();
		}
		return list;
	}
	/**
	 * 最佳评论存在标识
	 */
	public void setBestFlag(Integer materialCode){
		SqlSession session = getSession();
		try{
			IMaterialAuditMapper mapper = session.getMapper(IMaterialAuditMapper.class);
			mapper.setBestFlag(materialCode);
			session.commit();
		}catch(Exception e){
			logger.warn("setBestFlag"+e.toString());
		}finally{
			session.close();
		}
	}
	/**
	 * 重置最佳评论存在标识
	 */
	public void resetBestFlag(Integer materialCode){
		SqlSession session = getSession();
		try{
			IMaterialAuditMapper mapper = session.getMapper(IMaterialAuditMapper.class);
			mapper.resetBestFlag(materialCode);
			session.commit();
		}catch(Exception e){
			logger.warn("resetBestFlag"+e.toString());
		}finally{
			session.close();
		}
	}
	
	/**
	 * 查询素材类型
	 * @param materialCode
	 * @return
	 */
	public String getMaterialTypeByMaterialCode(Integer materialCode){
		SqlSession session = getSession();
		try{
			IMaterialAuditMapper mapper = session.getMapper(IMaterialAuditMapper.class);
			return  mapper.getMaterialTypeByMaterialCode(materialCode);
		}catch(Exception e){
			logger.warn("getMaterialTypeByMaterialCode"+e.toString());
		}finally{
			session.close();
		}
		return "";
	}
}
