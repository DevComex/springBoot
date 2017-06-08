
package cn.gyyx.action.dao.challenger;

import java.util.List;

import cn.gyyx.action.beans.challenger.ChallenterLiveRadioBean;


public interface IChallenterLiveRadio {

	List<ChallenterLiveRadioBean> getChallenterLiveRadioListPaging(
			ChallenterLiveRadioBean bean);

	int insert(ChallenterLiveRadioBean bean);

	int update(ChallenterLiveRadioBean bean);

	int delete(ChallenterLiveRadioBean bean);

	int getChallenterLiveRadioCount(ChallenterLiveRadioBean bean);
	
	ChallenterLiveRadioBean getChallenterLiveRadioBean(
			int code);

	int getWebFrontChallenterLiveRadioCount(ChallenterLiveRadioBean bean);

	List<ChallenterLiveRadioBean> getWebFrontChallenterLiveRadioListPaging(
			ChallenterLiveRadioBean bean);
	


}
