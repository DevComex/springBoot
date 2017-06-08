/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月2日
 * @版本号：V1.214
 * @本类主要用途描述：素材信息数据处理接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import cn.gyyx.action.beans.xwbcreditsluckydraw.MaterialAuditBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MaterialInfoBean;

public interface IMaterialInfoMapper {

	/**
	 * 增加素材信息
	 * @param materialInfoBean
	 */
	public Integer insertMaterialInfo(MaterialInfoBean materialInfoBean);
	/**
	 * 查询素材信息
	 * @param materialInfoBean
	 * @return
	 */
	public MaterialInfoBean selectMaterialInfo(MaterialInfoBean materialInfoBean);	
	
	/**
	 * 根据code查询素材
	 * @param code
	 * @return
	 */
	public MaterialInfoBean getMaterialInfoByCode(Integer code); 	
	/**
	 * 增加正式图片地址
	 * @param materialInfoBean
	 */
	public void setRealUrl(MaterialInfoBean materialInfoBean);
	
}
