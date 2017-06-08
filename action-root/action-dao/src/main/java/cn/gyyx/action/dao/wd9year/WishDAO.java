/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：马文斌
 * 联系方式：mawenbin@gyyx.cn 
 * 创建时间：2015年3月11日下午5:52
 * 版本号：v1.0
 * 本类主要用途叙述：祝福DAO
 */
package cn.gyyx.action.dao.wd9year;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wd9year.WishBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
/**
 * 祝福信息Mapper
 * */
public class WishDAO {

	//获取session对象
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 上传祝福
	 * @param 参数祝福实体
	 * */
	public void uploadWish(WishBean wishBean) {
		SqlSession session = getSession();
		try {
			IWishDAO wishDao = session.getMapper(IWishDAO.class);
			wishDao.uploadWish(wishBean);
			session.commit();
		}finally{
			session.close();
		}
	}
	/**
	 * 通过用户id得到用户上传祝福的次数
	 * @param userId 用户Id
	 * 		date1 起始时间点
	 * 		date2 截止时间点
	 * */
	public int getWishCountByUserId(int userId, String date1, String date2) {
		SqlSession session = getSession();
		int count = 0;
		try {
			IWishDAO wishDao = session.getMapper(IWishDAO.class);
			count = wishDao.getWishCountByUserId(userId, date1, date2);
		}finally{
			session.close();
		}
		return count;
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
		SqlSession session = getSession();
		List<WishBean> wishBeanList = null;
		try {
			IWishDAO wishDao = session.getMapper(IWishDAO.class);
			wishBeanList = wishDao.selectByPage(map);
		}finally{
			session.close();
		}
		return wishBeanList;
	}
	
	/**
	 * 根据code修改审核状态
	 * @日期：2015年3月13日
	 * @Title: updateWishStatus 
	 * @param wishBean WishBean实体类
	 * void
	 */
	public void updateWishStatus(WishBean wishBean){
		SqlSession session = getSession();
		try {
			IWishDAO wishDao = session.getMapper(IWishDAO.class);
			wishDao.updateWishStatus(wishBean);
			session.commit();
		}finally{
			session.close();
		}
	}
	
	/**
	 * 查询最新20条数据
	 * @日期：2015年3月13日
	 * @Title: selectWishByNew 
	 * @return  最新20条数据集合
	 * List<WishBean>
	 */
	public List<WishBean> selectWishByNew(){
		SqlSession session = getSession();
		List<WishBean> wishBeanList = null;
		try {
			IWishDAO wishDao = session.getMapper(IWishDAO.class);
			wishBeanList = wishDao.selectWishByNew();
		}finally{
			session.close();
		}
		return wishBeanList;
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
		SqlSession session = getSession();
		Integer count = null;
		try {
			IWishDAO wishDao = session.getMapper(IWishDAO.class);
			count = wishDao.getCountByStatus(checkStatus);
		}finally{
			session.close();
		}
		return count;
	}
	/**
	 * 通过用户id查询祝福
	 * @param code 祝福主键code
	 * @return WishBean
	 * */
	public WishBean getWishByCode(int code){
		SqlSession session = getSession();
		try {
			IWishDAO wishDao = session.getMapper(IWishDAO.class);
			return wishDao.getWishByCode(code);
		}finally{
			session.close();
		}
	}
	/**
	 * 得到最大的祝福code值
	 * */
	public int getMaxCode(){
		SqlSession session = getSession();
		int result=-1;
		try {
			IWishDAO wishDao = session.getMapper(IWishDAO.class);
			result = wishDao.getMaxCode();
		}finally{
			session.close();
		}
		return result;
	}
	/**
	 * 得到wish的数量
	 * */
	public int getWishCount(){
		SqlSession session = getSession();
		int result=-1;
		try {
			IWishDAO wishDao = session.getMapper(IWishDAO.class);
			result = wishDao.getWishCount();
		}finally{
			session.close();
		}
		return result;
	}
	/**
	 * 查询wish
	 * */
	public List<WishBean> getWish(){
		SqlSession session = getSession();
		try {
			IWishDAO wishDao = session.getMapper(IWishDAO.class);
			return wishDao.getWish();
		}finally{
			session.close();
		}
	}
}
