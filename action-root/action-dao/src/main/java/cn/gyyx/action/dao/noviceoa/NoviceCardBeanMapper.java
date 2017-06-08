package cn.gyyx.action.dao.noviceoa;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.noviceoa.NoviceCardBean;

public interface NoviceCardBeanMapper {
    
    String existTable(String tableName);
    
    void insertBatchCodeSql(@Param("insertSql")String insertSql);
    
    String selectLastNoviceCardNo(@Param("tbName")String tbName);
    
    List<NoviceCardBean> getNoviceCardListByTaskId(@Param("tbName")String tbName,@Param("taskId")int taskId);
}