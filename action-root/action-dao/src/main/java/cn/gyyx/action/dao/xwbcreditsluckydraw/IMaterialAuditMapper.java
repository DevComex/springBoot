package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.xwbcreditsluckydraw.CollectTopShowBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MaterialAuditBean;

public interface IMaterialAuditMapper {
	/**
	 * 增加素材
	 * */
	Integer addMaterialAudit(MaterialAuditBean materialAuditBean);
	/**
	 * 查询素材
	 * */
	List<MaterialAuditBean> getMaterialAuditByPage(Map<String, Object> paramMap);
	/**
	 * 查询素材的数量
	 * */
	Integer getMaterialAuditCount(MaterialAuditBean materialAuditBean);
	/**
	 * 查询素材显示
	 * @param materialAuditBean（is_show;materialType）
	 * @return
	 */
	List<MaterialAuditBean> selectMaterialShow(MaterialAuditBean materialAuditBean);
	/**
	 * 查询个人素材显示
	 * @param account（is_show;account）
	 * @return
	 */
	List<MaterialAuditBean> selectMaterialUserShow(String account);
	
	/**
	 * 修改审核状态
	 * */
	void updateIsChecked(@Param("checkedStatus")String checkedStatus, @Param("code")Integer code);
	/**
	 * 重置首页显示状态
	 * @author fanyongliang
	 */
	void resetIsShow();
	
	/**
	 * 修改首页显示状态
	 * @author fanyongliang
	 */
	void updateIsShow(Integer code);
	
	/**
	 * 征集首页显示
	 * @return
	 */
	List<MaterialAuditBean> getCollectTopShow(CollectTopShowBean collectTopShowBean);
	
	
	
	/**
	 * 点赞
	 * @param num
	 * @param code
	 */
	void updatePraiseInfo(Integer num,Integer code);
	
	
	
	/**
	 * 视频
	 * @return
	 */
	List<MaterialAuditBean> getVideoListWithName(String account);
	
	
	
	/**
	 * 服装
	 * @return
	 */
	List<MaterialAuditBean> getPhotoListWithName(String account);
	
	
	
	/**
	 * 建议
	 * @return
	 */
	List<MaterialAuditBean> getSuggestListWithName(String account);
	
	/**
	 * 素材信息
	 * @return
	 */
	MaterialAuditBean getMaterialInfoByCode(Integer code);
	
	/**
	 * 修改评论数
	 * @param num
	 * @param materialCode
	 */
	void updateCommentNum(Integer num,Integer materialCode);
	/**
	 * 修改积分数
	 * @param num
	 * @param code
	 */
	void updateCreditsNum(Integer num,Integer code);
	/**
	 * 根据code查询显示首页状态 
	 * @param code
	 * @return
	 */
	Integer getShowStatusByCode(Integer code);
	
	/**
	 * 查询显示素材信息
	 * @return
	 */
	List<MaterialAuditBean> getShowMaterialInfo();
	
	/**
	 * 最佳评论存在标识
	 */
	void setBestFlag(Integer materialCode);
	/**
	 * 重置最佳评论存在标识
	 */
	void resetBestFlag(Integer materialCode);
	/**
	 * 查询素材类型
	 * @param materialCode
	 * @return
	 */
	public String getMaterialTypeByMaterialCode(Integer materialCode);
}
