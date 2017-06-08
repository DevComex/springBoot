package cn.gyyx.action.beans.wd11yearrechargerebate;
/**
 * 
  * <p>
  *   操作日志实体BLOBsbean
  * </p>
  *  
  * @author lihu
  * @since 0.0.1
 */
public class RechargerebateAcessLogWithBLOBs extends RechargerebateAcessLog {
    private Integer code;
    
    private String bindappResult;

    private String recharge10Result;

    private String level60Result;

    private String historyRechargeResult;

    public String getBindappResult() {
        return bindappResult;
    }

    public void setBindappResult(String bindappResult) {
        this.bindappResult = bindappResult == null ? null : bindappResult.trim();
    }

    public String getRecharge10Result() {
        return recharge10Result;
    }

    public void setRecharge10Result(String recharge10Result) {
        this.recharge10Result = recharge10Result == null ? null : recharge10Result.trim();
    }

    public String getLevel60Result() {
        return level60Result;
    }

    public void setLevel60Result(String level60Result) {
        this.level60Result = level60Result == null ? null : level60Result.trim();
    }

    public String getHistoryRechargeResult() {
        return historyRechargeResult;
    }

    public void setHistoryRechargeResult(String historyRechargeResult) {
        this.historyRechargeResult = historyRechargeResult == null ? null : historyRechargeResult.trim();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
    
}