/*------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
* 作者：姜晗
* 联系方式：jianghan@gyyx.cn 
* 创建时间：2014年12月19日 上午11:36:56 
* 版本号：v1.0 
* 本类主要用途描述： 
* 
-------------------------------------------------------------------------*/

package cn.gyyx.action.beans.novicecard;

public enum ServerStatusEnum {
   
	/**
	 * 代表服务器不存在
	 */
	SERVERNOTEXIST(-2),
	/**
	 * 代表用户不存在
	 */
	ACCOUNTNOTEXIST(-1),
	/**
	 * 未激活
	 */
	NOTACTIVATION(0),
	
	/**
     * 激活
     */
    ACTIVATION(1),
	/**
	 * 激活，并激活天数大于30天,
	 */
	ACTIVATIONANDDAYGREATEIRTY(2);
	private int serverStatus;
	private ServerStatusEnum(int serverStatus){
		this.serverStatus = serverStatus;
	}
	@Override
    public String toString() {
        return String.valueOf ( this.serverStatus );
    }
}
