package cn.gyyx.action.dao.noviceoa;

import cn.gyyx.action.beans.noviceoa.NoviceGiftBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoviceGiftBeanMapper {
    int insert(NoviceGiftBean record);

    int insertSelective(NoviceGiftBean record);

    NoviceGiftBean selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(NoviceGiftBean record);

    int updateByPrimaryKey(NoviceGiftBean record);

    List<NoviceGiftBean> selectListByBatchId(@Param("batchId") int batchId);

    NoviceGiftBean selectBeanByGiftName(@Param("batchId") int batchId, @Param("giftName") String giftName);

    NoviceGiftBean selectBeanByGiftCode(@Param("batchId") int batchId, @Param("giftCode") String giftCode);

    int deleteGiftBeanByCode(@Param("code")int code);
}