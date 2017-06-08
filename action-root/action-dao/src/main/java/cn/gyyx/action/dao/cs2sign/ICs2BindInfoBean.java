package cn.gyyx.action.dao.cs2sign;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.gyyx.action.beans.cs2sign.Cs2BindInfoBean;

public interface ICs2BindInfoBean {
	public void insertCs2BindInfoBean(Cs2BindInfoBean bean);
	public List<Cs2BindInfoBean> selectCs2BindInfoBeanByAccount(@Param("account")String account);
	public int selectCs2BindInfoCountByServerAndChar(@Param("server")String server,@Param("character")String character);
}