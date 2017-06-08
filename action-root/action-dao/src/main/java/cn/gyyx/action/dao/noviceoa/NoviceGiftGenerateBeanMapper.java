package cn.gyyx.action.dao.noviceoa;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.noviceoa.NoviceGiftGenerateBean;
import cn.gyyx.action.beans.noviceoa.NoviceGiftGenerateInfoBean;

public interface NoviceGiftGenerateBeanMapper {
    int insert(NoviceGiftGenerateBean record);

    int insertSelective(NoviceGiftGenerateBean record);

    NoviceGiftGenerateBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(NoviceGiftGenerateBean record);
    
    List<NoviceGiftGenerateInfoBean> selectByBatchId(@Param("batchId")int batchId);
    
    List<NoviceGiftGenerateBean> selectByChannel(@Param("channel")String channel);
    
    List<NoviceGiftGenerateBean> selectByGiftId(@Param("giftId")int giftId);
}