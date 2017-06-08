/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月26日 上午10:04:25
 * @版本号：
 * @本类主要用途描述：是否二维码新手卡
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.novicecard;

public enum NoviceCardMark {

	noviceCard(1), erWeiMa(2);
	private int value = 0;

	private NoviceCardMark(int value) {
		this.value = value;
	}

	public static NoviceCardMark valueOf(int value) {
		switch (value) {
		case 1:
			return noviceCard;
		case 2:
			return erWeiMa;
		default:
			return null;
		}
	}

	public int value() {
		return this.value;
	}
}
