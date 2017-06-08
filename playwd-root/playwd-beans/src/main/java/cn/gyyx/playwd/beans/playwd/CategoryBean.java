package cn.gyyx.playwd.beans.playwd;

import java.io.Serializable;

public class CategoryBean implements Serializable {
    private Integer code;

    private String contentType;

    private String title;

    private Integer parentId;

    private Integer gameId;

    private Boolean isDelete;

    private static final long serialVersionUID = 1L;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
    
    
    /**
	 * 分类类型
	 */
	public enum CategoryType{
		article("article","图文驿站"),//图文
		manhua("manhua",""),//漫画
		picture ("picture",""),//图片
		novel ("novel",""),//小说
		video ("video","");//视频
		
		private String value;
		private String desc;

		private CategoryType(String value,String desc) {
			this.value = value;
			this.desc = desc;
		}

		public String Value() {
			return value;
		}

		public String Desc() {
			return desc;
		}
		
	}
}