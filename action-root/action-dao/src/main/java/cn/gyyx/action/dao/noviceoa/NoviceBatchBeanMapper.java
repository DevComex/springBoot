package cn.gyyx.action.dao.noviceoa;

import cn.gyyx.action.beans.noviceoa.NoviceBatchBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoviceBatchBeanMapper {
    int insertSelective(NoviceBatchBean record);

    NoviceBatchBean selectByPrimaryKey(Integer code);

    NoviceBatchBean selectByBatchName(@Param("batchName") String batchName);

    int updateByPrimaryKeySelective(NoviceBatchBean record);

    int selectTotalCount(@Param("batchType")String batchType);

    List<NoviceBatchBean> selectPageList(@Param("game_id") int gameId, @Param("skip_count") int index, @Param("select_count") int pageCount, @Param("batch_type") String batchType);

    List<NoviceBatchBean> selectBatchList(@Param("game_id") int gameId, @Param("batch_type") String batchType);
}