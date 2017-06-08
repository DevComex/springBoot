/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：Chen 
 * 联系方式：chenpeng03@gyyx.cn 
 * 创建时间： 2014年12月15日 下午2:13:14
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 宠物详细信息业务逻辑
-------------------------------------------------------------------------*/
package cn.gyyx.action.bll.wdno1pet;

import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.wdno1pet.PetsQuery;
import cn.gyyx.action.beans.wdno1pet.WDNo1PetInfoBean;
import cn.gyyx.action.dao.wdno1pet.PetInfoDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class PetInfoBLL {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(PetInfoBLL.class);
	private PetInfoDAO petInfoDAO = new PetInfoDAO();

	public WDNo1PetInfoBean getPetInfoByPetCode(int pcode) {
		return petInfoDAO.getPetInfoByPetCode(pcode);
	}

	/**
	 * 上传一个参赛作品到数据库
	 * 
	 * @param petInfo
	 *            参赛作品实体
	 */
	public void uploadPetInfo(WDNo1PetInfoBean petInfo) {
		petInfoDAO.uploadPetInfo(petInfo);
	}

	/**
	 * 根据作品类别、查询策略、服务器id、角色名、宠物作品名、页面索引号返回符合条件的作品集合
	 * 
	 * @param query
	 *            查询参数实体
	 * @return 满足条件的作品集合
	 */
	public List<WDNo1PetInfoBean> getBeanByStrategyAndKeys(PetsQuery query) {
		return petInfoDAO.getBeanByStrategyAndKeys(query);
	}

	/**
	 * 最热宠物列表，分类依据宠物类别
	 * 
	 * @param type
	 *            宠物类型:普通，变异，神兽，元灵，坐骑
	 * @param limit
	 *            限制输出条数，默认15
	 * @return
	 */
	public List<WDNo1PetInfoBean> getHottestPetsByType(String type,
			Integer limit) {
		logger.debug("type"+ type+"limit"+limit);
		if (Validation.isPetType(type)) {
			
			return petInfoDAO.getHottestPetsByType(type, limit);
		} else {
			return null;
		}

	}

	/**
	 * 最强宠物列表，分类依据属性值
	 * 
	 * @param type
	 *            宠物属性名称：pet_growth,pet_blood,pet_speed,pet_magic,pet_attack
	 * @param limit
	 *            限制输出条数，默认15
	 * @return
	 */
	public List<WDNo1PetInfoBean> getStrongestPetsByQuality(String qualityName,
			Integer limit) {
		logger.debug("qualityName"+ qualityName+"limit"+limit);
		if (Validation.isPetQualityName(qualityName)) {
			return petInfoDAO.getStrongestPetsByQuality(qualityName, limit);
		} else {
			return null;
		}
	}
	
	/**根据查询实体返回参赛作品总数
	 * @return 代表作品总数的整型值
	 */
	public int getPetCountByStrategyAndKeys(PetsQuery query){
		return petInfoDAO.getPetCountByStrategyAndKeys(query);
	}
	
	/**根据用户主键返回该用户参赛作品数量
	 * @return 代表作品数的整型值
	 */
	public int getPetCountByUserCode(int userCode){
		return petInfoDAO.getPetCountByUserCode(userCode);
	}
	
	public List<Integer> getServerIdListByExist(){
		return petInfoDAO.getServerIdListByExist();
	}
	
	public WDNo1PetInfoBean getPetInfoByImgCode(int imgCode){
		return petInfoDAO.getPetInfoByImgCode(imgCode);
	}
	

}
