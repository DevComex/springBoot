/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年2月17日上午10:25:45
 * 版本号：v1.0
 * 本类主要用途叙述：添加申请信息的业务逻辑层
 */

package cn.gyyx.action.service.positionApply;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.positionApply.ApplyInfoBean;
import cn.gyyx.action.bll.positionApply.ApplyInfoBll;

public class ApplyInfoService {
	private ApplyInfoBll applyInfoBll = new ApplyInfoBll();

	/***
	 * 增加申请信息
	 * 
	 * @param applyInfoBean
	 * @param sqlSession
	 * @return
	 */
	public ResultBean<String> addApplyInfo(ApplyInfoBean applyInfoBean,
			SqlSession sqlSession) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if (!applyInfoBean.getSex().equals("男")
				&& !applyInfoBean.getSex().equals("女")) {
			resultBean.setProperties(false, "参数错误", "参数错误");
		}
		/*
		 * // 职位判断 applyInfoBean = applyInfoBean.positionGet(applyInfoBean); if
		 * (applyInfoBean.getPosition().equals("")) {
		 * resultBean.setProperties(false, "该职位暫時不能申請", "参数错误"); }
		 */
		ApplyInfoBean applyInfoBeanTempApplyInfoBean = applyInfoBll
				.getApplyInfoByPhoneAndName(applyInfoBean.getName(),
						applyInfoBean.getPhone(), applyInfoBean.getPosition(),
						sqlSession);
		if (applyInfoBeanTempApplyInfoBean != null) {
			return new ResultBean<String>(false, "职位已经申请，请耐心等待", "职位已经申请，请耐心等待");
		}
		try {
			applyInfoBll.addInfo(applyInfoBean, sqlSession);
			resultBean.setProperties(true, "职位申请成功", "职位申请成功");
		} catch (Exception e) {
			// TODO: handle exception
			resultBean.setProperties(false, "职位申请失败，请重试", "职位申请失败");
		}
		return resultBean;

	}

}
