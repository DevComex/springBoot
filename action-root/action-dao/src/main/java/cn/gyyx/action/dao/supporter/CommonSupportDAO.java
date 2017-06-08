package cn.gyyx.action.dao.supporter;

import java.util.List;
import java.util.Map;

import cn.gyyx.action.common.beans.InnerTransmissionData;
import cn.gyyx.action.common.dictionary.DBColum;
import cn.gyyx.action.common.dictionary.Dictionary.FormType;

public class CommonSupportDAO {
	MainTbDAO mainTbDao = new MainTbDAO();
	SubTbDAO subTbDao = new SubTbDAO();
	AloneDAO aloneTbDao = new AloneDAO();
	EventDAO eventTbDao = new EventDAO();
	TableUpdateDAO tableUpdateDao = new TableUpdateDAO();

	public boolean uploadForm(InnerTransmissionData data) {
		data.getDataMap().put(DBColum.TYPE,data.getUploadType().getTypeName());
		if (data.getFormType() == FormType.MAIN_FORM) {
			return mainTbDao.addMainForm(data.getDataMap(),data.getSearchMap());
		} else if (data.getFormType() == FormType.SUB_FORM) {
			return subTbDao.addSubForm(data.getDataMap());
		} else if (data.getFormType() == FormType.ALONE_FORM) {
			return aloneTbDao.addAloneForm(data.getDataMap());
		} else if (data.getFormType() == FormType.EVENT_LOG) {
			return eventTbDao.addEventForm(data.getDataMap());
		}
		return false;
	}

	public int updateForm(InnerTransmissionData data) {
		return tableUpdateDao.updateTable(data);
	}

	public List<Map<String,Object>> queryForm(InnerTransmissionData queryBean) {
		FormType ft = queryBean.getUploadType().getFormType();
		if (ft == FormType.MAIN_FORM) {
			return mainTbDao.queryMainForm(queryBean);
		} else if (ft == FormType.SUB_FORM) {
			return subTbDao.querySubForm(queryBean);
		} else if (ft == FormType.ALONE_FORM) {
			return aloneTbDao.queryAloneForm(queryBean);
		} else if (ft == FormType.EVENT_LOG) {
			return eventTbDao.queryEventForm(queryBean);
		}
		return null;
	}

	public Integer queryFormNum(InnerTransmissionData queryBean) {
		FormType ft = queryBean.getUploadType().getFormType();
		if (ft == FormType.MAIN_FORM) {
			return mainTbDao.queryMainFormNum(queryBean);
		} else if (ft == FormType.SUB_FORM) {
			return subTbDao.querySubFormNum(queryBean);
		} else if (ft == FormType.ALONE_FORM) {
			return aloneTbDao.queryAloneFormNum(queryBean);
		} else if (ft == FormType.EVENT_LOG) {
			return eventTbDao.queryEventFormNum(queryBean);
		}
		return 0;
	}
	// public Integer orgQueryNum(DirectlyQuery query) {
	// return directlyDao.orgQueryNum(query);
	// }
	// public List<Map<String, Object>> orgQuery(DirectlyQuery query) {
	// return directlyDao.orgQuery(query);
	// }
	// public Integer orgUpdate(DirectlyUpdate update) {
	// return directlyDao.orgUpdate(update);
	// }
}
