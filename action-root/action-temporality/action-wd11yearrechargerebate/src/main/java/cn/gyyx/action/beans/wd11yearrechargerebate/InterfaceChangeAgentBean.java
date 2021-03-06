/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：lihu
 * @联系方式：lihu@gyyx.cn
 * @创建时间： 2017年3月30日 
 * @版本号：V1.211
 * @本类主要用途描述：问道11周年充值返现活动调interface.tong.gyyx.cn域名的返回结果实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wd11yearrechargerebate;

public class InterfaceChangeAgentBean {

    // 返回消息
    String message;
    // 返回消息
    String status;
    // 返回结果
    String data;
    
    public InterfaceChangeAgentBean(String message, String status,
            String data) {
        super();
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public InterfaceChangeAgentBean() {
        super();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "InterfaceChangeAgentBean [message=" + message + ", status="
                + status + ", data=" + data + "]";
    }
}
