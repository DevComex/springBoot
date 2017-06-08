package cn.gyyx.wd.wanjia;

public class ResultBean<T> {
    private boolean success;
    private String message;
    private T data;
    
    public ResultBean() {
		super();
	}
	public ResultBean(boolean success, String message ) {
		super();
		this.success = success;
		this.message = message;
	}
	public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public void setProperties(boolean success,String message,T data) {
    	this.data = data;
    	this.message = message;
    	 this.success = success;
    }
    @Override
    public String toString() {
        return "[success:" + success + ",message:" + message + ",data:" + data + "]";
    }
}
