package cn.gyyx.action.dao.weixin.related;

import org.apache.ibatis.annotations.Param;

public interface ITokenConfigMapper {
	public String getTokenPara(@Param("keyName")String keyName,@Param("type")String type);
}
