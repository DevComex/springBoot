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

public class InterfaceTongAgentBean {

    // 返回消息
    String msg;
    // 返回消息
    String status;
    
    public InterfaceTongAgentBean() {
        super();
    }
    
    public InterfaceTongAgentBean(String msg, String status) {
        this.msg = msg;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "InterfaceAgentBean [msg=" + msg + ", status=" + status + "]";
    }

}
