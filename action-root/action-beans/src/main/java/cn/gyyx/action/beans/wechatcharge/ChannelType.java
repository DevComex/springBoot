/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-bean
 * @作者：guoyonggang
 * @联系方式：guoyonggang@gyyx.cn
 * @创建时间：2017年2月27日
 * @版本号：1.0
 * @本类主要用途描述：  
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wechatcharge;
/**
  * <p>
  *   活动渠道类型
  * </p>
  *  
  * @author guoyonggang
  * @since 0.0.1
 */
public enum ChannelType {
    WeiXin("微信", 1), WeiBo("微博", 2), TieBa("贴吧", 3), Other("其他", 4);
    private String name ;
    private int index ;
     
    private ChannelType( String name , int index ){
        this.name = name ;
        this.index = index ;
    }
     
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
