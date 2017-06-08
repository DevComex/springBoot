/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月7日
 * @版本号：1.214
 * @本类主要用途描述：签到奖励信息数据访问
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.SignPrizeBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class SignPrizeDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(SignPrizeDAO.class);

	/**
	 * 获取session
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 增加一条签到奖励信息
	 * @author fanyongliang
	 * @param signPrizeBean
	 */
	public void insertSignPrize(SignPrizeBean signPrizeBean) {
		SqlSession session = getSession();
		try {
			ISignPrizeMapper mapper = session.getMapper(ISignPrizeMapper.class);
			mapper.insertSignPrize(signPrizeBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("insertSignPrize" + e.toString());
		} finally {
			session.close();
		}
	}

	/**
	 * 修改签到奖励
	 * @author fanyongliang
	 * @param signPrizeBean
	 */
	public void updateSignPrize(SignPrizeBean signPrizeBean) {
		SqlSession session = getSession();
		try {
			ISignPrizeMapper mapper = session.getMapper(ISignPrizeMapper.class);
			mapper.updateSignPrize(signPrizeBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("updateSignPrize" + e.toString());
		} finally {
			session.close();
		}
	}

	/**
	 * 根据奖励编号查询签到奖励
	 * @author fanyongliang
	 * @param code
	 * @return
	 */
	public SignPrizeBean selectSignPrizeByCode(Integer code) {
		SqlSession session = getSession();
		SignPrizeBean signPrizeBean = new SignPrizeBean();
		try {
			ISignPrizeMapper mapper = session.getMapper(ISignPrizeMapper.class);
			signPrizeBean = mapper.selectSignPrizeByCode(code);
		} catch (Exception e) {
			logger.warn("selectSignPrizeByCode" + e.toString());
		} finally {
			session.close();
		}
		return signPrizeBean;
	}

	/**
	 * 删除签到奖励
	 * @author fanyongliang
	 * @param code
	 */
	public void deleteSignPrize(Integer code) {
		SqlSession session = getSession();
		try {
			ISignPrizeMapper mapper = session.getMapper(ISignPrizeMapper.class);
			mapper.deleteSignPrize(code);
			session.commit();
		} catch (Exception e) {
			logger.warn("deleteSignPrize" + e.toString());
		} finally {
			session.close();
		}
	}

	/**
	 * 查询全部签到奖励
	 * @author fanyongliang
	 * @return
	 */
	public List<SignPrizeBean> selectAllSignPrize() {
		SqlSession session = getSession();
		List<SignPrizeBean> list = new ArrayList<SignPrizeBean>();
		try {
			ISignPrizeMapper mapper = session.getMapper(ISignPrizeMapper.class);
			list = mapper.selectAllSignPrize();
		} catch (Exception e) {
			logger.warn("selectAllSignPrize" + e.toString());
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * 根据奖励类型查询签到奖励
	 * @author fanyongliang
	 * @param code
	 * @return
	 */
	public List<SignPrizeBean> selectSignPrizeByType(String prizeType, String prizeSex) {
		SqlSession session = getSession();
		List<SignPrizeBean> signPrizeBeanList = new ArrayList<SignPrizeBean>();
		try {
			ISignPrizeMapper mapper = session.getMapper(ISignPrizeMapper.class);
			signPrizeBeanList = mapper.selectSignPrizeByType(prizeType, prizeSex);
		} catch (Exception e) {
			logger.warn("selectSignPrizeByType" + e.toString());
		} finally {
			session.close();
		}
		return signPrizeBeanList;
	}
	
	/**
	 * 查询奖励数量
	 * @param prizeType
	 * @return
	 */
	public Integer getPrizeCountByType(String prizeType,String sex){
		SqlSession session = getSession();
		Integer count = 0;
		try {
			ISignPrizeMapper mapper = session.getMapper(ISignPrizeMapper.class);
			count = mapper.getPrizeCountByType(prizeType,sex);
		} catch (Exception e) {
			logger.warn("getPrizeCountByType" + e.toString());
		} finally {
			session.close();
		}
		return count;
	}
}
