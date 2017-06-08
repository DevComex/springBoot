/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-beans
 * @作者：guoyonggang
 * @联系方式：guoyonggang@gyyx.cn
 * @创建时间：2017年2月27日
 * @版本号：1.0
 * @本类主要用途描述：  
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wechatcharge;

import java.util.List;

/**
  * <p>
  *   分页查询结果展示Bean
  * </p>
  * 
  * @param <T> 
  * @author guoyonggang
  * @since 0.0.1
 */
public class PaginationResultBean<T> {
    private boolean isSuccess;
    private String message;
    private int pageIndex;
    private int pageSize;
    private int count;
    private List<T> data;
    public boolean isSuccess() {
        return isSuccess;
    }
    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getPageIndex() {
        return pageIndex;
    }
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }
}
