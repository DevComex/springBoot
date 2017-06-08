/*------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
* 作者：姜晗
* 联系方式：jianghan@gyyx.cn 
* 创建时间：2014年12月9日 下午5:33:26 
* 版本号：v1.0 
* 本类主要用途描述： 
* 
-------------------------------------------------------------------------*/

package cn.gyyx.action.beans.novicecard;

/** 
* @ClassName: NoviceCardVirtualItem 
* @Description: TODO 
* 新手礼包，物品实体类
* @author jh 
* @date 2014年12月9日 下午5:35:49 
*  
*/
public class VirtualItemBean {
	/**
	 * 物品编号
	 */
	private int code;
	/**
	 * 物品名称
	 */
	private String itemName;
	/**
	 * 物品注释
	 */
	private String itemNote;
	/**
	 * 游戏服务器编号
	 */
	private int gameId;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemNote() {
		return itemNote;
	}
	public void setItemNote(String itemNote) {
		this.itemNote = itemNote;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
	

}
