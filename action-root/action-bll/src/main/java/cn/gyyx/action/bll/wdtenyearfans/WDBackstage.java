package cn.gyyx.action.bll.wdtenyearfans;

import java.util.List;

import cn.gyyx.action.beans.wdtenyearfans.PageBean;
import cn.gyyx.action.beans.wdtenyearfans.WdAccountScoreBean;
import cn.gyyx.action.beans.wdtenyearfans.WdCommentsBean;
import cn.gyyx.action.beans.wdtenyearfans.WdNominationInfoBean;
import cn.gyyx.action.dao.wdtenyearfans.WdAccountScoreDAO;
import cn.gyyx.action.dao.wdtenyearfans.WdCommentsDAO;
import cn.gyyx.action.dao.wdtenyearfans.WdNominationInfoDAO;
import cn.gyyx.action.dao.wdtenyearfans.WdVoteInfoDAO;

public class WDBackstage {
	private WdCommentsDAO wdCommentsDAO = new WdCommentsDAO();
	private WdNominationInfoDAO wdNominationInfoDAO = new WdNominationInfoDAO();
	private WdAccountScoreDAO wdAccountScoreDAO = new WdAccountScoreDAO();
	private WdVoteInfoDAO wdVoteInfoDAO = new WdVoteInfoDAO();
	public List<WdCommentsBean> gettWdCommentsBeanList(PageBean pageBean){
		return wdCommentsDAO.selectWdCommentsBeanList(pageBean);
		
	}
	public List<WdCommentsBean> getDeleteWdCommentsBeanList(PageBean pageBean){
		return wdCommentsDAO.selectDeleteWdCommentsBeanList(pageBean);
		
	}
	public int gettWdCommentsCount(PageBean pageBean){
		return wdCommentsDAO.selectWdCommentsCount(pageBean);
		
	}
	public int getDeleteWdCommentsCount(PageBean pageBean){
		return wdCommentsDAO.selectDeleteWdCommentsCount(pageBean);
		
	}
	public int getWdNominationCount(PageBean pageBean){
		return wdNominationInfoDAO.selectWdNominationCount(pageBean);
	}
	public int getDeleteWdNominationCount(PageBean pageBean){
		return wdNominationInfoDAO.selectDeleteWdNominationCount(pageBean);
	}
	public WdCommentsBean getWdCommentsBeanBycode(int code){
		return wdCommentsDAO.selectWdCommentsBeanByCode(code);
		
	}
	public void updateFlag(WdCommentsBean bean) {
		wdCommentsDAO.updateFlag(bean);
	}
	public void updateAuditStatus(WdNominationInfoBean bean){
		wdNominationInfoDAO.updateAuditStatus(bean);
	}
	public List<WdNominationInfoBean> getWdNominationInfoList(PageBean pageBean){
		return wdNominationInfoDAO.selectWdNominationInfoList(pageBean);
	}
	public List<WdNominationInfoBean> getDeleteWdNominationInfoList(PageBean pageBean){
		return wdNominationInfoDAO.selectDeleteWdNominationInfoList(pageBean);
	}
	public WdNominationInfoBean getWdNominationInfoByCode(int code){
		return wdNominationInfoDAO.selectWdNominationInfoBeanByCode(code);
	}
	public void updateWdAccountScoreBean(WdAccountScoreBean wdAccountScoreBean) {
		wdAccountScoreDAO.updateWdAccountScoreBean(wdAccountScoreBean);
	}
	public WdAccountScoreBean selectWdAccountScoreBeanByAccount(String accountName){
		return wdAccountScoreDAO.selectWdAccountScoreBeanByAccount(accountName);
	}
	public int getAccountNumByWhite(PageBean pageBean){
		return wdVoteInfoDAO.getAccountNumByWhite(pageBean);
		
	}
	public int getAccountNumByBlack(PageBean pageBean){
		return wdVoteInfoDAO.getAccountNumByBlack(pageBean);
		
	}
	public int getAccountNumByVote(PageBean pageBean){
		return wdVoteInfoDAO.getAccountNumByVote(pageBean);
		
	}
	public int getNomanitionCountForMe(PageBean pageBean){
		return wdNominationInfoDAO.getNomanitionCountForMe(pageBean);
		
	}
	public int getNomanitionCountForHe(PageBean pageBean){
		return wdNominationInfoDAO.getNomanitionCountForHe(pageBean);
		
	}
	public int getWhiteNumByAccountDate(PageBean pageBean){
		return wdVoteInfoDAO.getWhiteNumByAccountDate(pageBean);
		
	}
	public int getBlackNumByAccountDate(PageBean pageBean){
		return wdVoteInfoDAO.getBlackNumByAccountDate(pageBean);
		
	}
	public List<String> selectAllVoteAccount(PageBean pageBean){
		return wdVoteInfoDAO.selectAllVoteAccount(pageBean);
		
	}
}
