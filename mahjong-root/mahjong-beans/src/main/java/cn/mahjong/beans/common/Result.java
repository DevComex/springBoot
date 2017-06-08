package cn.mahjong.beans.common;

/**
  * <p>
  *   Result描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
public class Result<T> {
    
    /**
     * 状态信息: 是否成功
     */
    String status;
    
    /**
     * 说明信息
     */
    String message;

    /**
     * 状态数据
     */
    T data;
    
    /**
     * 构造函数
     */
    public Result(){}
    
    /**
     * 带参数构造函数
     * @param isSuccess 
     * @param message
     * @param data
     */
    public Result(String status, String message, T data){
            this.message = message;
            this.data = data;
            this.status = status;
    }

    public synchronized String getStatus() {
        return status;
    }

    public synchronized void setStatus(String status) {
        this.status = status;
    }

    public synchronized String getMessage() {
        return message;
    }

    public synchronized void setMessage(String message) {
        this.message = message;
    }

    public synchronized T getData() {
        return data;
    }

    public synchronized void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result [status=" + status + ", message=" + message + ", data="
                + data + "]";
    }
}
