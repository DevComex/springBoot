/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年2月17日上午10:14:44
 * 版本号：v1.0
 * 本类主要用途叙述：职位申请数据库交互层
 */

package cn.gyyx.action.dao.positionApply;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.positionApply.ApplyInfoBean;

public class ApplyDao {
	/***
	 * 添加职位申请信息
	 * 
	 * @param applyInfoBean
	 * @param sqlSession
	 */
	public void addInfo(ApplyInfoBean applyInfoBean, SqlSession sqlSession) {
		// TODO Auto-generated method stub
		IApplyInfo iApplyInfo = sqlSession.getMapper(IApplyInfo.class);
		iApplyInfo.addInfo(applyInfoBean);
	}

	/***
	 * 查询所有职位种类
	 */
	public List<String> getApplyInfoPostion(SqlSession sqlSession) {
		IApplyInfo iApplyInfo = sqlSession.getMapper(IApplyInfo.class);
		return iApplyInfo.getApplyInfoPostion();
	}

	/***
	 * 查询出所有的申请信息
	 */
	public List<ApplyInfoBean> getAllApplyInfoBeans(SqlSession sqlSession) {
		IApplyInfo iApplyInfo = sqlSession.getMapper(IApplyInfo.class);
		return iApplyInfo.getAllApplyInfoBeans();
	}

	/**
	 * 根据主键查询申请信息
	 */
	public ApplyInfoBean getApplyInfoByCode(int code, SqlSession sqlSession) {
		IApplyInfo iApplyInfo = sqlSession.getMapper(IApplyInfo.class);
		return iApplyInfo.getApplyInfoByCode(code);
	}

	/**
	 * 根据主键删除一条信息
	 */
	public void removeApplyInfoByCode(int code, SqlSession sqlSession) {
		IApplyInfo iApplyInfo = sqlSession.getMapper(IApplyInfo.class);
		iApplyInfo.removeApplyInfoByCode(code);

	}
	
	public ApplyInfoBean getApplyInfoByPhoneAndName(String name,String phone,String position, SqlSession sqlSession) {
		IApplyInfo iApplyInfo = sqlSession.getMapper(IApplyInfo.class);
		return iApplyInfo.getApplyInfoByNameAndPhone(name, phone, position);
		
	}

}
