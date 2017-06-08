/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月6日
 * @版本号：V1.214
 * @本类主要用途描述：首页素材展示记录业务逻辑层
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.ShowTopBean;
import cn.gyyx.action.dao.xwbcreditsluckydraw.ShowTopDAO;

public class ShowTopBll {
	private ShowTopDAO showTopDAO = new ShowTopDAO();

	/**
	 * 增加一条记录
	 * @author fanyongliang
	 * @param showTopBean
	 */
	public void addOne(ShowTopBean ShowTopBean) {
		Integer num = showTopDAO
				.getCountByAuditCode(ShowTopBean.getAuditCode());
		if (num == 0) {
			showTopDAO.addOne(ShowTopBean);
		}
	}

	/**
	 * 根据审核code删除记录
	 * @author fanyongliang 
	 * @param auditCode
	 */
	public void deleteByAuditCode(Integer auditCode) {
		showTopDAO.deleteByAuditCode(auditCode);
	}

	/**
	 * 清空记录的数据
	 * @author fanyongliang 
	 */
	public void deleteAll() {
		showTopDAO.deleteAll();
	}

	/**
	 * 根据素材类型查询记录数量
	 * @author fanyongliang 
	 * @param materialType
	 * @return
	 */
	public Integer getCountByMaterialType(String materialType) {
		return showTopDAO.getCountByMaterialType(materialType);
	}

	/**
	 * 根据审核编号查询记录数量
	 * @author fanyongliang 
	 * @param auditCode
	 * @return
	 */
	public Integer getCountByAuditCode(Integer auditCode) {
		return showTopDAO.getCountByAuditCode(auditCode);
	}

	/**
	 * 查询总记录数
	 * @author fanyongliang 
	 */
	public Integer getCountAll() {
		return showTopDAO.getCountAll();
	}

	/**
	 * 查询所有记录
	 * @author fanyongliang 
	 */
	public List<ShowTopBean> getAllInfo() {
		return showTopDAO.getAllInfo();
	}
}
