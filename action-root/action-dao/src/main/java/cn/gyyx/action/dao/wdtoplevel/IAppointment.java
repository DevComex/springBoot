package cn.gyyx.action.dao.wdtoplevel;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdtoplevel.AppointmentBean;

public interface IAppointment {
	public void addAppointment(AppointmentBean appointment);
	public boolean isAccountExists(@Param("account")String account);
}
