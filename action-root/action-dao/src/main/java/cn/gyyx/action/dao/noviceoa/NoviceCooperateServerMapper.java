package cn.gyyx.action.dao.noviceoa;

import cn.gyyx.action.beans.noviceoa.NoviceServerBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoviceCooperateServerMapper {
    int insertSelective(NoviceServerBean record);

    NoviceServerBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(NoviceServerBean record);

    List<NoviceServerBean> selectByBatchId(@Param("batchId") int batchId);

    NoviceServerBean selectByServerId(@Param("batchId")int batchId, @Param("serverId") int serverId);

    List<Integer> selectServerIdsByBatchIdGameId(@Param("batchId") int batchId, @Param("gameId") int gameId);
}