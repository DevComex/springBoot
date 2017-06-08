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
  *   分页查询参数Bean
  * </p>
  *  
  * @author guoyonggang
  * @since 0.0.1
 */
public class WechatChargePaginationBean {
    private Integer pageSize;
    private Integer pageIndex;
    private String strWhere;
    private Integer forwardPage;
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public Integer getPageIndex() {
        return pageIndex;
    }
    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
    public String getStrWhere() {
        return strWhere;
    }
    public void setStrWhere(String strWhere) {
        this.strWhere = strWhere;
    }
    public Integer getForwardPage() {
        return forwardPage;
    }
    public void setForwardPage(Integer forwardPage) {
        this.forwardPage = forwardPage;
    }
    @Override
    public String toString() {
        return "WechatChargePaginationBean [pageSize=" + pageSize
                + ", pageIndex=" + pageIndex + ", strWhere=" + strWhere
                + ", forwardPage=" + forwardPage + "]";
    }
    
}
