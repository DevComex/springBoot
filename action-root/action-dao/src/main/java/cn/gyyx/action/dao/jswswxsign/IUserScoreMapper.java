/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.jswswxsign;

import cn.gyyx.action.beans.jswswxsign.UserScore;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 * 描        述： 绝世武神微信签到功能-用户积分Mapper
 */
public interface IUserScoreMapper {
	public UserScore getUserScore(UserScore userScore);
	public int insertUserScore(UserScore userScore);
	public int updateUserScore(UserScore userScore);
	public void updateUserScoreZero();
}
