/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月15日下午8:26:20
 * 版本号：v1.0
 * 本类主要用途叙述：投票的DAO层
 */

package cn.gyyx.action.dao.wdno1pet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wdno1pet.VoteBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class VoteDAO implements IVote {

	/**
	 * 获取session，
	 * 
	 * @throws IOException
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	@Override
	/**
	 * 查询当天该用户或者该IP是否对存在投票的纪录
	 */
	public VoteBean SelectVoteQualification(VoteBean voteBean, int voteYear,
			int voteMonth, int voteDay) {
		try (SqlSession sqlSession = getSession()) {

			IVote iVote = sqlSession.getMapper(IVote.class);
			VoteBean vote = iVote.SelectVoteQualification(voteBean, voteYear,
					voteMonth, voteDay);
			return vote;
		}

	}

	@Override
	/**
	 * 当天插入一条投票记录
	 */
	public void insertVote(VoteBean voteBean) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {

			IVote iVote = sqlSession.getMapper(IVote.class);
			iVote.insertVote(voteBean);
			sqlSession.commit();

		}
	}

	/**
	 * 获取最近的投票
	 * 
	 * @param pCode
	 *            宠物主键
	 * @param limit
	 *            限制输出条数
	 * @return 用户主键
	 */
	public List<VoteBean> getVoteUserCodes(String pCode, Integer limit) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {

			IVote iVote = sqlSession.getMapper(IVote.class);
			List<VoteBean> voteBeans = iVote.getVoteUserCodes(pCode, limit);
			return voteBeans;
		}
	}
/**
 * 
 */
	@Override
	public Timestamp selectTimestampByVoteCode(int voteCode) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {

			IVote iVote = sqlSession.getMapper(IVote.class);
			Timestamp voteTime=iVote.selectTimestampByVoteCode(voteCode);
			return voteTime;
		}
		
	}
}
