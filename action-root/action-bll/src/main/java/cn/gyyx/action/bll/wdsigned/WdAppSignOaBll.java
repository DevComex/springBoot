package cn.gyyx.action.bll.wdsigned;

import cn.gyyx.action.beans.wdsigned.WdAppSignOaBean;
import cn.gyyx.action.dao.wdsigned.WdAppSignOaDao;

public class WdAppSignOaBll {
	
	WdAppSignOaDao wdAppSignOaDao=new WdAppSignOaDao();
	
	public int insert(WdAppSignOaBean bean) {
		
		return wdAppSignOaDao.insert(bean);
	}

	public WdAppSignOaBean getWdAppSignOaBean(String batch) {
		// TODO Auto-generated method stub
		return wdAppSignOaDao.getWdAppSignOaBean(batch);
	}
}
