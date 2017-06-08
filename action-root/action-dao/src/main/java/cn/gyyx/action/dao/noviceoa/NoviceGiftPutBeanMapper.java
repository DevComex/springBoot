package cn.gyyx.action.dao.noviceoa;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.noviceoa.NoviceGiftPutBean;

public interface NoviceGiftPutBeanMapper {
	int deleteByPrimaryKey(Integer code);

	int insert(NoviceGiftPutBean record);

	int insertSelective(NoviceGiftPutBean record);

	NoviceGiftPutBean selectByPrimaryKey(Integer code);

	int updateByPrimaryKeySelective(NoviceGiftPutBean record);

	int updateByPrimaryKey(NoviceGiftPutBean record);

	List<NoviceGiftPutBean> selectPageList(@Param("put_type") int putType, @Param("put_name") String putName,
			@Param("skip_count") int index, @Param("select_count") int pageCount);

	List<NoviceGiftPutBean> selectByPutType(@Param("put_type")int putType);

	List<NoviceGiftPutBean> selectNoviceGiftPut(@Param("putName") String putName, @Param("putType") int putType);

	int selectTotalCount(@Param("put_type") int putType, @Param("put_name") String putName);
}