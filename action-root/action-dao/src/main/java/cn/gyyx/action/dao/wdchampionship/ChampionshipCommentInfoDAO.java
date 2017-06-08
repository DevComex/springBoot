/*************************************************
       Copyright ©, 2015, GY Game
       Author: 柳佳琦
       Created: 2016年3月24日
       Note：名人争霸赛评论数据访问
************************************************/
package cn.gyyx.action.dao.wdchampionship;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wdchampionship.ChampionshipCommenttInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: ChampionshipCommentInfoDAO
 * @Description: TODO 名人争霸赛评论数据访问实现类.
 * @author 柳佳琦 liujiaqi@gyyx.cn
 * @date 2016年3月24日 下午3:15:21
 */
public class ChampionshipCommentInfoDAO implements IChampionshipCommentInfoMapper{
	private static final Logger LOG = 
			GYYXLoggerFactory.getLogger(ChampionshipCommentInfoDAO.class);
	

	@Override
	public int addComment(ChampionshipCommenttInfoBean comment) {
		int count = 0;
		SqlSession session = MyBatisConnectionFactory.
				getSqlActionDBV2SessionFactory().openSession();
		try {
			IChampionshipCommentInfoMapper mapper = session.getMapper(IChampionshipCommentInfoMapper.class);
			count = mapper.addComment(comment);
			session.commit();
		} catch (Exception e) {
			LOG.info(e.toString());
			LOG.info("selectComment failed with param: "+ comment);
		}finally{
			session.close();
		}
		return count;
	}

	@Override
	public List<ChampionshipCommenttInfoBean> selectComment(boolean isDel,
			int typeOfYear, int startIndex, int endStartIndex) {
		List<ChampionshipCommenttInfoBean> list = null;
		SqlSession session = MyBatisConnectionFactory.
				getSqlActionDBV2SessionFactory().openSession();
		try {
			IChampionshipCommentInfoMapper mapper = session.getMapper(IChampionshipCommentInfoMapper.class);
			list = mapper.selectComment(isDel, typeOfYear, startIndex, endStartIndex);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.info(e.toString());
			LOG.info("selectComment failed with param: "+ isDel + "," + typeOfYear+ 
					"," + startIndex + "," + endStartIndex);
		}finally{
			session.close();
		}
		return list;
	}

	@Override
	public int selectCommentCount(boolean isDel, int typeOfYear) {
		int count = 0;
		SqlSession session = MyBatisConnectionFactory.
				getSqlActionDBV2SessionFactory().openSession();
		try {
			IChampionshipCommentInfoMapper mapper = session.getMapper(IChampionshipCommentInfoMapper.class);
			count = mapper.selectCommentCount(isDel, typeOfYear);
		} catch (Exception e) {
			LOG.info(e.toString());
			LOG.info("selectComment failed with param: "+ isDel + "," + typeOfYear);
		}finally{
			session.close();
		}
		return count;
	}

	@Override
	public List<ChampionshipCommenttInfoBean> selectTopComment(int typeOfYear) {
		List<ChampionshipCommenttInfoBean> list = null;
		SqlSession session = MyBatisConnectionFactory.
				getSqlActionDBV2SessionFactory().openSession();
		try {
			IChampionshipCommentInfoMapper mapper = session.getMapper(IChampionshipCommentInfoMapper.class);
			list = mapper.selectTopComment(typeOfYear);
		} catch (Exception e) {
			LOG.info(e.toString());
			LOG.info("selectComment failed with param: "+ typeOfYear);
		}finally{
			session.close();
		}
		return list;
	}

}
