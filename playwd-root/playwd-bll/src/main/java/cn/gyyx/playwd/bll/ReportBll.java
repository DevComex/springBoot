package cn.gyyx.playwd.bll;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.ReportBean;
import cn.gyyx.playwd.dao.playwd.ReportDao;

/**
 * 举报bll
 * @author lidudi
 *
 */
@Component
public class ReportBll {

	@Autowired
	ReportDao reportDao;
	
	/**
	 * 插入
	 * @param commentId
	 * @param commentUserId
	 * @param commentUserAccount
	 * @param reportUserId
	 * @param reportUserAccount
	 * @param reason
	 * @return
	 */
	public int insert(Integer commentId,Integer commentUserId,String commentUserAccount
			,Integer reportUserId,String reportUserAccount,String reason) {
		ReportBean record = new ReportBean();
		record.setCommentId(commentId);
		record.setCommentUserAccount(commentUserAccount);
		record.setCommentUserId(commentUserId);
		record.setReportUserId(reportUserId);
		record.setReason(reason);
		record.setReportUserAccount(reportUserAccount);
		record.setCreateTime(new Date());
		record.setStatus(ReportBean.ReportStatus.processing.Value());
    	return reportDao.insert(record);
    }

	/**
	 * 更新bycode
	 * @param code
	 * @param operatorAccount
	 * @return
	 */
	public boolean changeReport(int code,String operatorAccount) {
		return reportDao.updateReport(code, operatorAccount)>0;
    }
	
	/**
	 * 获取举报管理列表数量
	 * @param startDate
	 * @param endDate
	 * @param contentType
	 * @param status
	 * @param commentAccount
	 * @param reportAccount
	 * @return
	 */
	public int getReportManagementCount(Date startDate,Date endDate,String contentType,String status,
			String commentAccount,String reportAccount) {
		return  reportDao.selectReportManagementCount(startDate, endDate, contentType, status, commentAccount, reportAccount);
	}
	
	/**
	 * 获取举报管理列表
	 * @param startDate
	 * @param endDate
	 * @param contentType
	 * @param status
	 * @param commentAccount
	 * @param reportAccount
	 * @return
	 */
	public List<ReportBean> getReportManagementList(Date startDate,Date endDate,String contentType,String status,
			String commentAccount,String reportAccount,int pageSize,int currentPage) {
		return reportDao.selectReportManagementList(startDate, endDate, contentType, status,
				commentAccount,reportAccount,(currentPage-1)*pageSize,pageSize);
	}
	
	/**
	 * 获取举报记录
	 * @param code
	 * @return
	 */
	public ReportBean getReportInfo(int code) {
		return reportDao.selectReportInfo( code);
	}
	/**
	 * 
	  * <p>
	  *    查询某人的举报次数 是否举报
	  * </p>
	  *
	  * @action
	  *    lihu 2017年4月19日 下午1:55:22 描述
	  *
	  * @param reportUserId
	  * @param commentId
	  * @return boolean
	 */
    public boolean selectUserReport(Integer reportUserId,
            Integer commentId) {
        return reportDao.selectByUserAndCommentId(reportUserId,commentId)>0;
    }
}
