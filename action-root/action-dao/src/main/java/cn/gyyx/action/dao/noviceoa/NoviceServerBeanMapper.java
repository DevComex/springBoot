package cn.gyyx.action.dao.noviceoa;

import cn.gyyx.action.beans.noviceoa.NoviceServerBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoviceServerBeanMapper {
    int insertSelective(NoviceServerBean record);

    NoviceServerBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(NoviceServerBean record);

    List<NoviceServerBean> selectByBatchId(@Param("batchId")int batchId);

    NoviceServerBean selectByServerId(@Param("serverId")int batchId,@Param("gameId")int gameId);

    NoviceServerBean selectMaxServer(@Param("gameId")int gameId);

    List<Integer> selectServerIdsByGameId(@Param("gameId") int gameId);
}