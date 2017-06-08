package cn.mahjong.beans;

/**
  * <p>
  *   AccountStatus描述
  * </p>
  *  
  * @author chenwen
  * @since 0.0.1
  */
public enum AccountStatus {
    NORMAL("normal"),BANNED("banned");
    
    private String value;
    
    private AccountStatus(String value){
        this.value = value;
    }
    
    public synchronized String getValue() {
        return value;
    }
    
}
