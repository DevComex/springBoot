/*------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
* 作者：姜晗
* 联系方式：jianghan@gyyx.cn 
* 创建时间：${date} ${time} 
* 版本号：v1.0 
* 本类主要用途描述： 
* 
-------------------------------------------------------------------------*/

package cn.gyyx.action.beans.wd9yearnovicebag;

import java.util.Date;


/** 
* @ClassName ActivityConfigBean 
* @Description: 活动配置的实体类
* 
* @author jh 
* @date 2014年12月8日 下午4:38:07 
*  
*/
public class BagActivityConfigBean {
	private int code;
	private String activityName;
	private Date activityStart;
	private Date activityEnd;
	private boolean isDelete;
	private int gameId;
	private String paras;
	private String activityLink;
	private String checkType;
	//物品名称
	private String prize;
	//物品类型
	private String prizeType;

	

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Date getActivityStart() {
		return activityStart;
	}

	public void setActivityStart(Date activityStart) {
		this.activityStart = activityStart;
	}

	public Date getActivityEnd() {
		return activityEnd;
	}

	public void setActivityEnd(Date activityEnd) {
		this.activityEnd = activityEnd;
	}


	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getParas() {
		return paras;
	}

	public void setParas(String paras) {
		this.paras = paras;
	}

	public String getActivityLink() {
		return activityLink;
	}

	public void setActivityLink(String activityLink) {
		this.activityLink = activityLink;
	}

}
