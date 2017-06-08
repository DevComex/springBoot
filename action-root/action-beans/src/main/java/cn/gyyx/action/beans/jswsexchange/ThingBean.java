/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年1月25日下午2:59:59
 * 版本号：v1.0
 * 本类主要用途叙述：物品奖励的实体
 */

package cn.gyyx.action.beans.jswsexchange;

import java.util.HashMap;
import java.util.Map;

public class ThingBean {
	// 英文名
	private String english;
	// 中文名
	private String chinese;
	// 积分
	private int score;

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ThingBean(String english, String chinese, int score) {
		this.english = english;
		this.chinese = chinese;
		this.score = score;
	}

	public ThingBean() {
	}

	@Override
	public String toString() {
		return "ThingBean [english=" + english + ", chinese=" + chinese
				+ ", score=" + score + "]";
	}

	/***
	 * 获取积分信息
	 * 
	 * @param english
	 * @return
	 */
	public static ThingBean thingBeanGet(String english) {
		Map<String, ThingBean> map = new HashMap<String, ThingBean>();
		map.put("redBag", new ThingBean("redBag", "1元现金红包", 30));
		map.put("qqVip", new ThingBean("qqVip", "腾讯QQ超级会员", 100));
		map.put("mouseMat", new ThingBean("mouseMat", "大胸鼠标垫", 500));
		map.put("keyboard", new ThingBean("keyboard", "闪光游戏键盘", 2000));
		map.put("miNote", new ThingBean("miNote", "小米Note", 5000));
		return map.get(english);
	}
}
