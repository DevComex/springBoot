package cn.gyyx.wd.wanjia.cartoon.beans;

public class WanwdCategory {
    private Integer code;

    private Byte contentModuleType;

    private String name;

    private Integer parentId;

    private Boolean isDelete;

    private Integer gameid;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Byte getContentModuleType() {
        return contentModuleType;
    }

    public void setContentModuleType(Byte contentModuleType) {
        this.contentModuleType = contentModuleType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }
}