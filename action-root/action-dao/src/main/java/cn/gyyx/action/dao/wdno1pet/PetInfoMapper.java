/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：Chen 
 * 联系方式：chenpeng03@gyyx.cn 
 * 创建时间： 2014年12月15日 下午2:29:34
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * Mapper
-------------------------------------------------------------------------*/
package cn.gyyx.action.dao.wdno1pet;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdno1pet.PetsQuery;
import cn.gyyx.action.beans.wdno1pet.WDNo1PetInfoBean;

public interface PetInfoMapper {

	/**
	 * 根据主键获取PetInfo
	 * 
	 * @param pcode
	 * @return
	 */
	public WDNo1PetInfoBean getPetInfoByPetCode(int pcode);

	/**
	 * 上传一个宠物作品参赛
	 * 
	 * @param petInfo
	 * @return 是否加入成功的布尔类型
	 */

	public void uploadPetInfo(@Param("petInfo")WDNo1PetInfoBean petInfo);
	
	/**根据作品类别、查询策略、服务器id、角色名、宠物作品名、页面索引号返回符合条件的作品集合
	 * @param query 查询参数实体
	 * @return 满足条件的作品集合
	 */
	public List<WDNo1PetInfoBean> getBeanByStrategyAndKeys(@Param("query")PetsQuery query);

	/**
	 * 最热宠物列表，分类依据宠物类别
	 * 
	 * @param type
	 *            宠物类型:普通，变异，神兽，元灵，坐骑
	 * @param limit
	 *            限制输出条数，默认15
	 * @return
	 */
	public List<WDNo1PetInfoBean> getHottestPetsByType(
			@Param("type") String type, @Param("limit") Integer limit);

	/**
	 * 最强宠物列表，分类依据属性名称
	 * 
	 * @param qualityName
	 *            属性名称：pet_growth,pet_blood,pet_speed,pet_magic,pet_attack
	 * @param limit
	 *            限制输出条数，默认15
	 * @return
	 */
	public List<WDNo1PetInfoBean> getStrongestPetsByQuality(
			@Param("qualityName") String qualityName,
			@Param("limit") Integer limit);

	/**
	 * 向作品表中更新投票信息
	 * 
	 * @param petCode
	 * @param voteTime
	 */
	public void updateVote(@Param("petCode") int petCode,
			@Param("voteTime") Timestamp voteTime);
	
	/**根据查询实体返回参赛作品总数
	 * @return 代表作品总数的整型值
	 */
	public int getPetCountByStrategyAndKeys(@Param("query")PetsQuery query);
	
	/**根据用户主键返回该用户参赛作品数量
	 * @return 代表作品数的整型值
	 */
	public int getPetCountByUserCode(@Param("userCode")int userCode);
	public List<Integer> getServerIdListByExist();
	/**
	 * 通过imgCode查询作品实体
	 * @param imgCode
	 * @return WDNo1PetInfoBean 
	 */
	public WDNo1PetInfoBean  getPetInfoByImgCode(@Param("imgCode")int imgCode);
	
}
