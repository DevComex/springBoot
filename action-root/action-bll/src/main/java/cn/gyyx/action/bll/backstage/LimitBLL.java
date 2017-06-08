package cn.gyyx.action.bll.backstage;

import java.util.ArrayList;
import java.util.List;

import cn.gyyx.action.beans.backstage.ActionBean;
import cn.gyyx.action.beans.backstage.LimitBean;
import cn.gyyx.action.beans.backstage.LimitPeopleBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.NewPageBean;
import cn.gyyx.action.dao.backstage.ActionDAO;
import cn.gyyx.action.dao.backstage.LimitDAO;
import cn.gyyx.action.dao.backstage.LimitPeopleDAO;

public class LimitBLL {
	private LimitDAO limitDAO = new LimitDAO();
	private LimitPeopleDAO limitPeopleDAO = new LimitPeopleDAO();
	private ActionDAO actionDAO = new ActionDAO();
	public void inserActionLog(ActionBean actionBean){
		actionDAO.inserActionLog(actionBean);
	}
	public static List<LimitPeopleBean> stringToReportPersonList(
			String reportPersonInfo, int limitCode) {
		List<LimitPeopleBean> limitPeopleBeanList = new ArrayList<LimitPeopleBean>();
		int personId;
		String  personName;
		String[] nameArray;
		// 将String分隔为每一类的人员信息reportPersonArray
		nameArray = reportPersonInfo.split("、");
		
			// 将人员类别和人员分离出
				
				nameArray = reportPersonInfo.split("、");
				// 将人员Name和人员Id分离，并存进List
				for (int j = 0; j < nameArray.length; j++) {
					personName = nameArray[j].substring(0,
							nameArray[j].indexOf("("));
					personId = Integer.parseInt(nameArray[j].substring(
							nameArray[j].indexOf("(") + 1,
							nameArray[j].indexOf(")")));
					LimitPeopleBean limitPeopleBean = new LimitPeopleBean();
					limitPeopleBean.setDeleteFlag(0);
					limitPeopleBean.setLimitCode(limitCode);
					limitPeopleBean.setPersonId(personId);
					limitPeopleBean.setPersonName(personName);
					limitPeopleBeanList.add(limitPeopleBean);
				}
		
		
		return limitPeopleBeanList;
	}
	public void addLimit(LimitBean limitBean){
		Integer code = limitDAO.insertLimitBean(limitBean);
		List<LimitPeopleBean> limitPeopleList = stringToReportPersonList(limitBean.getNameString(),limitBean.getCode());
		for (LimitPeopleBean limitPeopleBean : limitPeopleList) {
			limitPeopleDAO.insertByActionCode(limitPeopleBean);
		}
	}
	public void updateLimit(LimitBean limitBean){
		limitDAO.updateLimitBean(limitBean);
		limitPeopleDAO.deleteLimitPeopleBean(limitBean.getCode());
		List<LimitPeopleBean> limitPeopleList = stringToReportPersonList(limitBean.getNameString(),limitBean.getCode());
		for (LimitPeopleBean limitPeopleBean : limitPeopleList) {
			limitPeopleDAO.insertByActionCode(limitPeopleBean);
		}
		
	}
	public void deleteLimit(Integer code){
		limitDAO.deleteLimitBean(code);
		limitPeopleDAO.deleteLimitPeopleBean(code);
	}
	public LimitBean getLimitBeanByCode(int code){
		LimitBean limitBean = limitDAO.selectLimitBeanByCode(code);
		return limitBean;
	}
	public List<LimitBean> getLimitBeanAll(NewPageBean newPageBean){
		return limitDAO.selectLimitBeanAll(newPageBean);
		
	}
	public Integer selectLimitBeanAllCount(){
		return limitDAO.selectLimitBeanAllCount();
		
	}
	public List<LimitBean> getLimitBeanByUser(NewPageBean newPageBean){
		return limitDAO.selectLimitBeanByUser(newPageBean);
	}
	public Integer selectLimitBeanByUserCount(int personId){
		return limitDAO.selectLimitBeanByUserCount(personId);
	}
}
