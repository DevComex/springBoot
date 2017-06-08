package cn.mahjong.beans;

/**
  * <p>
  *   UserRole描述
  * </p>
  *  hq 总部, L1_manager一级, salesman 推广员, head 局头
  * @author chenwen
  * @since 0.0.1
  */
public enum UserRole {
    
    HQ("hq", 1), 
    L1_MANAGER("L1_manager", 2),
    //4
    //8
    //16
    SALESMAN("salesman", 32), 
    HEAD("head", 64), ;
    // sum = 127，不能再大了
    
    private String value;
    private Byte bitFlag;
    
    private UserRole(String name, Integer bitFlag){
        this.value = name;
        this.bitFlag = bitFlag.byteValue();
    }
    
    public synchronized String getValue() {
        return value;
    }
    
    public synchronized Byte getBitFlag() {
        return bitFlag;
    }
    
    public static Boolean contains(Byte bitFlag, UserRole role){
        return (bitFlag & role.getBitFlag()) != 0;
    }
    
    public static class Flags{
        public static final String SALESMAN = "32";
    }
}
