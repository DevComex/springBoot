/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年9月18日下午5:00:12
 * 版本号：v1.0
 * 本类主要用途叙述：投票的数据库处理层
 */



package cn.gyyx.action.dao.lottery;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.lottery.VoteBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class VoteDao implements IVote {
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
	public int addVoteInfo(VoteBean voteBean) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IVote iVote = sqlSession
					.getMapper(IVote.class);
			iVote.addVoteInfo(voteBean);
			sqlSession.commit();
			return voteBean.getCode();
		}
	}

	@Override
	public void removeVoteInfoByCode(int code) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IVote iVote = sqlSession
					.getMapper(IVote.class);
			iVote.removeVoteInfoByCode(code);
			sqlSession.commit();
		}
	}

	@Override
	public List<Integer> getCountVoteGroupByWritingCode(int userCode) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IVote iVote = sqlSession
					.getMapper(IVote.class);
			return iVote.getCountVoteGroupByWritingCode(userCode);
		}
	}

	@Override
	public VoteBean getVoteBeanByIPAccountDate(String ip, String account,
			int year, int month, int day,int writingCode) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IVote iVote = sqlSession
					.getMapper(IVote.class);
			return iVote.getVoteBeanByIPAccountDate(ip, account, year, month, day,writingCode);
		}
	}

	@Override
	public  List<VoteBean>  getCountVoteByWritingCode(int writingCode) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IVote iVote = sqlSession
					.getMapper(IVote.class);
			return iVote.getCountVoteByWritingCode(writingCode);
		}
	}

	@Override
	public List<VoteBean> getVoteBeanByUserCode(int userCode) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IVote iVote = sqlSession
					.getMapper(IVote.class);
			return iVote.getVoteBeanByUserCode(userCode);
		}
	}
	
	
	

}
