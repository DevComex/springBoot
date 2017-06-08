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
 * 
  * <p>
  *   分页查询参数
  * </p>
  *  
  * @author guoyonggang
  * @since 0.0.1
 */
public class QueryConditionBean {
    private String channelName;
    private String timeType;
    private String beginTime;
    private String endTime;
    private String conditionType;
    private String conditionValue;
    private Integer pageIndex;
    private Integer pageSize;
    public String getChannelName() {
        return channelName;
    }
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    public String getTimeType() {
        return timeType;
    }
    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }
    public String getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getConditionType() {
        return conditionType;
    }
    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }
    public String getConditionValue() {
        return conditionValue;
    }
    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }
    public Integer getPageIndex() {
        return pageIndex;
    }
    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    @Override
    public String toString() {
        return "QueryConditionBean [channelName=" + channelName + ", timeType="
                + timeType + ", beginTime=" + beginTime + ", endTime=" + endTime
                + ", conditionType=" + conditionType + ", conditionValue="
                + conditionValue + ", pageIndex=" + pageIndex + ", pageSize="
                + pageSize + "]";
    }
    
}
