
package cn.gyyx.action.dao.wd10yearcoser;

import java.util.List;

import cn.gyyx.action.beans.wd10yearcoser.CoserNovice;


public interface ICoserNovice {

	List<CoserNovice> getNoviceListPaging(
			CoserNovice bean);

	int insert(CoserNovice bean);

	int update(CoserNovice bean);

	int delete(CoserNovice bean);

	int getNoviceCount(CoserNovice bean);
	
	CoserNovice getCoserNovice(
			int code);

	List<CoserNovice> getNoviceFontListPaging(CoserNovice bean);

	CoserNovice getNoviceNew();

}
