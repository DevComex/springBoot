package cn.gyyx.action.beans.enums;

/**
  * <p>
  *   活动状态枚举： 
  *      无效活动； 活动尚未开始；活动已经结束
  *   
  * </p>
  *  
  *  bozhencheng 2017-03-04 14:34 修改枚举定义名称，将原有的@Deprecated了。 
  *  
  * @author GYYX-DEV
  * @since 0.0.1
  */
public enum ActivityStatus {
    
    // 修改名称
    IS_NORMAL(1, "活动正在进行中"),
    HAS_NOT_START(10001, "活动尚未开始"),
    IS_INVALID(10000, "无效活动"),
    IS_OVER(10002, "活动已经结束");

    private int code;
    private String msg;

    private ActivityStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
