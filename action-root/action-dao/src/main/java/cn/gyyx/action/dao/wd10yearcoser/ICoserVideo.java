
package cn.gyyx.action.dao.wd10yearcoser;

import java.util.List;

import cn.gyyx.action.beans.wd10yearcoser.CoserOfficialVideo;


public interface ICoserVideo {

	List<CoserOfficialVideo> getVideoListPaging(
			CoserOfficialVideo bean);

	int insert(CoserOfficialVideo bean);

	int update(CoserOfficialVideo bean);

	int delete(CoserOfficialVideo bean);

	int getVideoCount(CoserOfficialVideo bean);
	
	CoserOfficialVideo getCoserOfficialVideo(
			int code);

	void cancleLastTop();

	/**
	 * @param num
	 * @param type
	 */
	void cancleLastTopWorksByType(int num, String type);

	List<CoserOfficialVideo> videoFrontTopList(CoserOfficialVideo bean);

	int getVideoFrontCount(CoserOfficialVideo bean);

	List<CoserOfficialVideo> getVideoFrontListPaging(CoserOfficialVideo bean);

	


}
