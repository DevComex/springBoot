package cn.gyyx.action.bll.userregistlog;

import org.slf4j.Logger;

import cn.gyyx.action.beans.userregistlog.UserRegistLogBean;
import cn.gyyx.action.dao.userregistlog.UserRegistLogDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class UserRegistLogBLL {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(UserRegistLogBLL.class);
	private UserRegistLogDAO userRegistLogDAO = new UserRegistLogDAO();
	public Integer insertRegiste(UserRegistLogBean para){
		return userRegistLogDAO.insert(para);
	}
}
