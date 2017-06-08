/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：姜晗
 * 联系方式：jianghan@gyyx.cn 
 * 创建时间：2014年12月19日 上午9:54:55 
 * 版本号：v1.0 
 * 本类主要用途描述： 游戏分类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.beans.novicecard;

/**
 * @ClassName: GameCategory
 * @Description: TODO 游戏分类
 * @author jh
 * @date 2014年12月19日 上午9:55:03
 * 
 */
public enum GameCategory {

	Virtual(-1), WenDao(2);
	private int value = 0;

	private GameCategory(int value) {
		this.value = value;
	}

	public static GameCategory valueOf(int value) {
		switch (value) {
		case -1:
			return Virtual;
		case 2:
			return WenDao;
		default:
			return null;
		}
	}

	public int value() {
		return this.value;
	}

}
