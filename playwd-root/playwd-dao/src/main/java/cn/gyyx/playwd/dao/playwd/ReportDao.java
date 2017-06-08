package cn.gyyx.playwd.dao.playwd;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.playwd.beans.playwd.ReportBean;

/**
 * 举报dao
 * @author chenglong
 *
 */
public interface ReportDao {

	/**
	 * 插入
	 * @param record
	 * @return
	 */
    int insert(ReportBean record);
    
    /**
     * 更新bycode
     * @param code
     * @param operatorAccount
     * @return
     */
    int updateReport(@Param("code")int code,@Param("operatorAccount")String operatorAccount);
    
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
    int selectReportManagementCount(@Param("startDate") Date startDate,@Param("endDate") Date endDate,
    		@Param("contentType") String contentType,@Param("status") String status,
    		@Param("commentAccount") String commentAccount,@Param("reportAccount") String reportAccount);
    
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
    List<ReportBean> selectReportManagementList(@Param("startDate") Date startDate,@Param("endDate") Date endDate,
    		@Param("contentType") String contentType,@Param("status") String status,
    		@Param("commentAccount") String commentAccount,@Param("reportAccount") String reportAccount,
    		@Param("startCode")int startCode,@Param("pageSize")int pageSize);
    
    /**
     * 获取举报记录
     * @param code
     * @return
     */
    ReportBean selectReportInfo(@Param("code")int code);

    int selectByUserAndCommentId(@Param("reportUserId")Integer reportUserId, @Param("commentId")Integer commentId);
}