package cn.gyyx.action.dao.noviceoa;

import cn.gyyx.action.beans.noviceoa.NoviceBatchTypeBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoviceBatchTypeBeanMapper {
    int insertSelective(NoviceBatchTypeBean record);

    NoviceBatchTypeBean selectByPrimaryKey(Integer code);

    List<NoviceBatchTypeBean> selectList();

    NoviceBatchTypeBean selectBeanByBatchType(@Param("batchType") String batchType);
}