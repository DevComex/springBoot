package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.xwbcreditsluckydraw.CollectTopShowBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MaterialAuditBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PageBean;
import cn.gyyx.action.dao.xwbcreditsluckydraw.MaterialAuditDAO;

public class MaterialAuditBll {
	private MaterialAuditDAO materialAuditDao = new MaterialAuditDAO();
	
	/**
	 * 新增素材
	 * */
	public Integer addMaterialAudit(MaterialAuditBean materialAuditBean){
		return materialAuditDao.addMaterialAudit(materialAuditBean);
	}
	/**
	 * 查询素材
	 * */
	public List<MaterialAuditBean> getMaterialAuditByPage(MaterialAuditBean materialAuditBean, PageBean pageBean){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("materialAudit", materialAuditBean);
		map.put("page", pageBean);
		return this.materialAuditDao.getMaterialAuditByPage(map);
	}
	/**
	 * 查询素材数量
	 * */
	public Integer getMaterialAuditCount(MaterialAuditBean materialAuditBean){
		return this.materialAuditDao.getMaterialAuditCount(materialAuditBean);
	}
	/**
	 * 查询素材显示
	 * @param materialAuditBean（is_show;materialType）
	 * @return
	 */
	public List<MaterialAuditBean> getMaterialShow(MaterialAuditBean materialAuditBean){
		return materialAuditDao.selectMaterialShow(materialAuditBean);
	}
	/**
	 * 查询个人素材显示
	 * @param account（is_show;account）
	 * @return
	 */
	public List<MaterialAuditBean> getMaterialUserShow(String account){
		return materialAuditDao.selectMaterialUserShow(account);
		
	}
	/**
	 * 修改审核状态
	 * */
	public void updateIsChecked(String isChecked, Integer code){
		materialAuditDao.updateIsChecked(isChecked, code);
	}
	/**
	 * 重置首页显示状态
	 * @author fanyongliang
	 */
	public void resetIsShow(){
		materialAuditDao.resetIsShow();
	}
	/**
	 * 修改首页显示状态
	 * @author fanyongliang 
	 */
	public void updateIsShow(Integer code){
		materialAuditDao.updateIsShow(code);
	}
	
	/**
	 * 征集首页显示
	 * @return
	 */
	public List<MaterialAuditBean> getCollectTopShow(CollectTopShowBean collectTopShowBean){
		return materialAuditDao.getCollectTopShow(collectTopShowBean);
	}
	
	
	/**
	 * 点赞 
	 * @param num
	 * @param code
	 */
	public void updatePraiseInfo(Integer num,Integer code){
		materialAuditDao.updatePraiseInfo(num,code);
	}

	/**
	 * 视频
	 * @return
	 */
	public List<MaterialAuditBean> getVideoListWithName(String account){
		return materialAuditDao.getVideoListWithName(account);
	}
	

	/**
	 * 服装
	 * @return
	 */
	public List<MaterialAuditBean> getPhotoListWithName(String account){
		return materialAuditDao.getPhotoListWithName(account);
	}
	
	/**
	 * 建议
	 * @return
	 */
	public List<MaterialAuditBean> getSuggestListWithName(String account){
		return materialAuditDao.getSuggestListWithName(account);
	}
	
	/**
	 * 素材信息
	 * @return
	 */
	public MaterialAuditBean getMaterialInfoByCode(Integer code){
		return materialAuditDao.getMaterialInfoByCode(code);
	}
	/**
	 * 修改评论数
	 * @param num
	 * @param materialCode
	 */
	public void updateCommentNum(Integer num,Integer materialCode){
		materialAuditDao.updateCommentNum(num,materialCode);
	}
	/**
	 * 修改积分数
	 * @param num
	 * @param code
	 */
	public void updateCreditsNum(Integer num,Integer code){
		materialAuditDao.updateCreditsNum(num,code);
	}
	/**
	 * 根据code查询显示首页状态 
	 * @param code
	 * @return
	 */
	public Integer getShowStatusByCode(Integer code){
		return materialAuditDao.getShowStatusByCode(code);
	}
	/**
	 * 查询显示素材信息
	 * @return
	 */
	public List<MaterialAuditBean> getShowMaterialInfo(){
		return materialAuditDao.getShowMaterialInfo();
	}
	/**
	 * 最佳评论存在标识
	 */
	public void setBestFlag(Integer materialCode){
		materialAuditDao.setBestFlag(materialCode);
	}
	/**
	 * 重置最佳评论存在标识
	 */
	public void resetBestFlag(Integer materialCode){
		materialAuditDao.resetBestFlag(materialCode);
	}
	
	/**
	 * 查询素材类型
	 * @param materialCode
	 * @return
	 */
	public String getMaterialTypeByMaterialCode(Integer materialCode){
		return materialAuditDao.getMaterialTypeByMaterialCode(materialCode);
	}
}
