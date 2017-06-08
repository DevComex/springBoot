/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年2月17日上午10:18:22
 * 版本号：v1.0
 * 本类主要用途叙述：职位申请信息业务层
 */

package cn.gyyx.action.bll.positionApply;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.positionApply.ApplyInfoBean;
import cn.gyyx.action.dao.positionApply.ApplyDao;

public class ApplyInfoBll {
	private ApplyDao applyDao = new ApplyDao();

	/***
	 * 添加职位申请信息
	 * 
	 * @param applyInfoBean
	 * @param sqlSession
	 */
	public void addInfo(ApplyInfoBean applyInfoBean, SqlSession sqlSession) {
		// TODO Auto-generated method stub
		applyDao.addInfo(applyInfoBean, sqlSession);
	}

	/***
	 * 查询所有职位种类
	 */
	public List<String> getApplyInfoPostion(SqlSession sqlSession) {
		return applyDao.getApplyInfoPostion(sqlSession);
	}

	/**
	 * 获取所有申请信息
	 * 
	 * @param sqlSession
	 * @return
	 */
	public List<ApplyInfoBean> getAllApplyInfoBeans(SqlSession sqlSession) {
		return applyDao.getAllApplyInfoBeans(sqlSession);
	}

	/**
	 * 根据主键查询申请信息
	 */
	public ApplyInfoBean getApplyInfoByCode(int code, SqlSession sqlSession) {
		return applyDao.getApplyInfoByCode(code, sqlSession);
	}

	/**
	 * 根据主键删除一条信息
	 */
	public void removeApplyInfoByCode(int code, SqlSession sqlSession) {
		applyDao.removeApplyInfoByCode(code, sqlSession);
	}

	/**
	 * 根据姓名的电话查询信息
	 * 
	 * @param name
	 * @param phone
	 * @param sqlSession
	 */
	public ApplyInfoBean getApplyInfoByPhoneAndName(String name, String phone,
			String position, SqlSession sqlSession) {
		return applyDao.getApplyInfoByPhoneAndName(name, phone, position,
				sqlSession);
	}

}
