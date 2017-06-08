/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：马文斌
 * 联系方式：mawenbin@gyyx.cn 
 * 创建时间：2015年3月11日下午5:52
 * 版本号：v1.0
 * 本类主要用途叙述：祝福Bll类
 */
package cn.gyyx.action.bll.wd9year;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.wd9year.WishBean;
import cn.gyyx.action.dao.wd9year.WishDAO;
/**
 * 祝福Bll类
 * */
public class WishBll {
	
	private WishDAO wishDao = new WishDAO();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 上传一个祝福
	 * 
	 * @param wishBean
	 *            参赛祝福实体
	 */
	public void uploadWish(WishBean wishBean){
		wishDao.uploadWish(wishBean);
	}
	/**
	 * 通过用户id得到用户上传祝福的次数
	 * @param userId 用户Id
	 * */
	public int getWishCountByUserId(int userId){
		Date date1 = DateUtil.setTodayTime(0, 0, 0);
		Date date2 = DateUtil.setTodayTime(23, 59, 59);
		return wishDao.getWishCountByUserId(userId, sdf.format(date1), sdf.format(date2));
	}
	
	/**
	 * 分页查询wendao_nineyear_wish_tb表
	 * @日期：2015年3月13日
	 * @Title: selectByPage 
	 * @param map 条件集合
	 * @return 对应的WishBean集合列表
	 * List<WishBean>
	 */
	public List<WishBean> selectByPage(Map<String, String> map){
		return wishDao.selectByPage(map);
	}
	
	/**
	 * 根据code修改审核状态
	 * @日期：2015年3月13日
	 * @Title: updateWishStatus 
	 * @param wishBean WishBean实体类
	 * void
	 */
	public void updateWishStatus(WishBean wishBean){
		wishDao.updateWishStatus(wishBean);
	}
	
	/**
	 * 查询最新20条数据
	 * @日期：2015年3月13日
	 * @Title: selectWishByNew 
	 * @return  最新20条数据集合
	 * List<WishBean>
	 */
	public List<WishBean> selectWishByNew(){
		return wishDao.selectWishByNew();
	}
	
	/**
	 * 根据状态查询数据总数
	 * @日期：2015年3月13日
	 * @Title: getCountByStatus 
	 * @param checkStatus 状态
	 * @return 该状态查询数据总数
	 * Integer
	 */
	public Integer getCountByStatus(String checkStatus){
		return wishDao.getCountByStatus(checkStatus);
	}
	
	/**
	 * 通过用户id查询祝福
	 * @param code 祝福主键code
	 * @return WishBean
	 * */
	public WishBean getWishByCode(int code){
		return wishDao.getWishByCode(code);
	}
	/**
	 * 得到最大的祝福code值
	 * */
	public int getMaxWishCode(){
		return wishDao.getMaxCode();
	}
	/**
	 * 得到wish的数量
	 * */
	public int getWishCount(){
		return wishDao.getWishCount();
	}
	/**
	 * 查询wish
	 * */
	public List<WishBean> getWish(){
		return wishDao.getWish();
	}
}
