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
  * @deprecated @see ActivityStatus 
  * @author GYYX-DEV
  * @since 0.0.1
  */
@Deprecated
public enum ErrorCodeEnums {
    
    ActivityIsInvalid(10000,"无效活动"),
    ActivityHasNotStart(10001, "活动未开始"),
    ActivityIsOver(10002, "活动已结束");

    private int code;
    private String msg;

    private ErrorCodeEnums(int code, String msg) {
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
