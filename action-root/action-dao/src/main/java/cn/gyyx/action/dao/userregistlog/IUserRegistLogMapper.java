package cn.gyyx.action.dao.userregistlog;

import cn.gyyx.action.beans.userregistlog.UserRegistLogBean;

public interface IUserRegistLogMapper {
	/**
	 * 插入注册信息
	 * @param para
	 * @return
	 */
	public Integer insert(UserRegistLogBean para);
}
