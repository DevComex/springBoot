/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：马文斌
 * 联系方式：mawenbin@gyyx.cn 
 * 创建时间：2015年3月11日下午5:52
 * 版本号：v1.0
 * 本类主要用途叙述：祝福信息Mapper
 */
package cn.gyyx.action.dao.wd9year;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wd9year.WishBean;
/**
 * 祝福信息Mapper
 * */
public interface IWishDAO {
	/**
	 * 上传祝福
	 * @param 参数祝福实体
	 * */
	public void uploadWish(WishBean wishBean);
	/**
	 * 通过用户id得到用户上传祝福的次数
	 * @param userId 用户Id
	 *		 date1  起始时间点
	 * 		date2  截止时间点
	 * */
	public int getWishCountByUserId(@Param("userId") int userId, @Param("date1")String date1, @Param("date2")String date2);
	
	/**
	 * 分页查询wendao_nineyear_wish_tb表
	 * @日期：2015年3月13日
	 * @Title: selectByPage 
	 * @param map 条件集合
	 * @return 对应的WishBean集合列表
	 * List<WishBean>
	 */
	public List<WishBean> selectByPage(Map<String, String> map);
	
	/**
	 * 根据code修改审核状态
	 * @日期：2015年3月13日
	 * @Title: updateWishStatus 
	 * @param wishBean WishBean实体类
	 * void
	 */
	public void updateWishStatus(WishBean wishBean);
	
	/**
	 * 查询最新20条数据
	 * @日期：2015年3月13日
	 * @Title: selectWishByNew 
	 * @return  最新20条数据集合
	 * List<WishBean>
	 */
	public List<WishBean> selectWishByNew();
	
	/**
	 * 根据状态查询数据总数
	 * @日期：2015年3月13日
	 * @Title: getCountByStatus 
	 * @param checkStatus 状态
	 * @return 该状态查询数据总数
	 * Integer
	 */
	public Integer getCountByStatus(@Param("checkStatus")String checkStatus);
	
	/**
	 * 通过用户id查询祝福
	 * @param code 祝福主键code
	 * @return WishBean
	 * */
	public WishBean getWishByCode(@Param("code")int code);
	/**
	 * 得到最大的祝福code值
	 * */
	public int getMaxCode();
	/**
	 * 得到wish的数量
	 * */
	public int getWishCount();
	/**
	 * 查询wish
	 * */
	public List<WishBean> getWish();
}
