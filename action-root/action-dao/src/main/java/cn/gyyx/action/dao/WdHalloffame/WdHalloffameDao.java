package cn.gyyx.action.dao.WdHalloffame;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdhalloffame.WdHalloffameBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WdHalloffameDao {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WdHalloffameDao.class);

	/**
	 * 获取session对象
	 * 
	 * @return
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 分页查询实现
	 * 
	 * @param pageSize
	 * @param start
	 * @return
	 */
	public ResultBean<WdHalloffameBean> selectByPage(int pageSize, int start) {
		ResultBean<WdHalloffameBean> result = new ResultBean<WdHalloffameBean>(false, "未知错误", null);
		List<WdHalloffameBean> list = null;
		SqlSession session = getSession();
		try {
			IWdHalloffameMapper iWdHalloffameMapper = session.getMapper(IWdHalloffameMapper.class);
			list = iWdHalloffameMapper.selectbypage(pageSize, start);
			int total = iWdHalloffameMapper.selecttotal();
			result.setTotal(total);
			result.setRows(list);
			result.setIsSuccess(true);
			result.setMessage("chenggong");
		} catch (Exception e) {
			logger.error(e.toString());
			result.setProperties(false, e.toString(), null);
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 更新标记
	 * 
	 * @param code
	 * @param ismark
	 * @return
	 */
	public void updateismark(int code, int ismark) {
		ResultBean<String> result = new ResultBean<String>(false, "未知错误", null);
		try (SqlSession sqlSession = getSession()) {
			IWdHalloffameMapper iWdHalloffameMapper = sqlSession.getMapper(IWdHalloffameMapper.class);
			iWdHalloffameMapper.updateismark(code, ismark);
			result.setIsSuccess(true);
			result.setMessage("更新成功");
			sqlSession.commit();

		}
	}

	/**
	 * 更新备注
	 * 
	 * @param code
	 * @param remark
	 * @return
	 */
	public void updateremark(int code, String remark) {
		ResultBean<String> result = new ResultBean<String>(false, "未知错误", null);
		try (SqlSession sqlSession = getSession()) {
			IWdHalloffameMapper iWdHalloffameMapper = sqlSession.getMapper(IWdHalloffameMapper.class);
			iWdHalloffameMapper.updateremark(code, remark);
			result.setIsSuccess(true);
			result.setMessage("更新成功");
			sqlSession.commit();
		}

	}

	/**
	 * 按QQ号查询用户信息
	 * 
	 * @param qqNo
	 * @return
	 **/

	public ResultBean<WdHalloffameBean> selectByQq(String qqNo) {
		ResultBean<WdHalloffameBean> result = new ResultBean<WdHalloffameBean>(false, "未知错误", null);

		SqlSession session = getSession();
		try {
			IWdHalloffameMapper iWdHalloffameMapper = session.getMapper(IWdHalloffameMapper.class);
			List<WdHalloffameBean> qq = iWdHalloffameMapper.selectbyqq(qqNo);
			result.setRows(qq);
			result.setIsSuccess(true);
			result.setMessage("查询成功");
			return result;

		} catch (Exception e) {
			logger.error(e.toString());
			result.setProperties(false, e.toString(), null);
		} finally {
			session.close();
		}

		return result;
	}

	/**
	 * 按用户账户查询用户信息
	 * 
	 * @param userName
	 * @return
	 **/
	public ResultBean<WdHalloffameBean> selectByUserId(String userName) {
		ResultBean<WdHalloffameBean> result = new ResultBean<WdHalloffameBean>(false, "未知错误", null);

		SqlSession session = getSession();
		try {
			IWdHalloffameMapper iWdHalloffameMapper = session.getMapper(IWdHalloffameMapper.class);
			List<WdHalloffameBean> UN = iWdHalloffameMapper.selectbyuserid(userName);
			result.setRows(UN);
			result.setIsSuccess(true);
			result.setMessage("查询成功");
			return result;

		} catch (Exception e) {
			logger.error(e.toString());
			result.setProperties(false, e.toString(), null);
		} finally {
			session.close();
		}

		return result;
	}

	/**
	 * 选择日期时间查询
	 * 
	 * @param userName
	 * @return
	 **/
	public ResultBean<WdHalloffameBean> selectByTime(Date stdate, Date endate) {
		ResultBean<WdHalloffameBean> result = new ResultBean<WdHalloffameBean>(false, "未知错误", null);

		SqlSession session = getSession();
		try {
			IWdHalloffameMapper iWdHalloffameMapper = session.getMapper(IWdHalloffameMapper.class);
			List<WdHalloffameBean> time = iWdHalloffameMapper.selectbytime(stdate, endate);
			result.setRows(time);
			result.setIsSuccess(true);
			result.setMessage("查询成功");
			return result;

		} catch (Exception e) {
			logger.error(e.toString());
			result.setProperties(false, e.toString(), null);
		} finally {
			session.close();
		}

		return result;
	}

	/**
	 * 插入excel表
	 * 
	 * @param WdHalloffameBean
	 * @return
	 **/

	public int insertExcel(List<WdHalloffameBean> WdHalloffameBean) {
		SqlSession session = getSession();
		int result = 0;
		try {
			IWdHalloffameMapper iWdHalloffameMapper = session.getMapper(IWdHalloffameMapper.class);
			result = iWdHalloffameMapper.insertexcel(WdHalloffameBean);
			session.commit();
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 按用户账户查询用户信息
	 * 
	 * @param userName
	 * @return
	 **/
	public ResultBean<String> selectByUserName(String userName) {
		ResultBean<String> result = new ResultBean<String>(false, "未知错误", null);

		SqlSession session = getSession();
		try {
			IWdHalloffameMapper iWdHalloffameMapper = session.getMapper(IWdHalloffameMapper.class);
			String UN = iWdHalloffameMapper.selectbyusername(userName);
			result.setData(UN);
			return result;

		} catch (Exception e) {
			logger.error(e.toString());
			result.setProperties(false, e.toString(), null);
		} finally {
			session.close();
		}

		return result;
	}

	/**
	 * 逐条插入game_vip_qualification表
	 * 
	 * @param userName
	 * @return
	 */
	public void insertUserName(String username) {
		SqlSession session = getSession();

		try {
			IWdHalloffameMapper iWdHalloffameMapper = session.getMapper(IWdHalloffameMapper.class);
			 iWdHalloffameMapper.insertusername(username);
			session.commit();
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			session.close();
		}
	}

}
