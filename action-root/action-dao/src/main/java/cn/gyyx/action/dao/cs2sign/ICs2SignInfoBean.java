package cn.gyyx.action.dao.cs2sign;

import org.apache.ibatis.annotations.Param;
import cn.gyyx.action.beans.cs2sign.Cs2SignInfoBean;
import java.util.Date;

public interface ICs2SignInfoBean {
	public void insertCs2SignInfoBean(Cs2SignInfoBean bean);
	public int selectCs2SignInfoCountByAccount(@Param("account")String account);
	public Cs2SignInfoBean selectCs2SignInfoBeanByAccount(@Param("account")String account);
	public void updateCs2SignInfo(@Param("date")Date date,@Param("continuity")int continuity,@Param("account")String account);
}