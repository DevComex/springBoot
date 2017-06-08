package cn.gyyx.playwd.beans.playwd;

import java.io.Serializable;
import java.util.Date;

/**
 * 
  * <p>
  *   小说章节表
  * </p>
  *  
  * @author chenglong
  * @since 0.0.1
 */
public class NovelChapterBean implements Serializable {
    private Integer code;
    //小说id
    private Integer novelId;
    //所属章节
    private Integer chapterNum;
    //章节标题
    private String title;
    //创建时间
    private Date createTime;
    //章节总数
    private Integer wordCount;
    //发布时间
    private Date publishTime;
    //状态
    private String status;
    //章节内容
    private String content;
    
    private Integer userId;
    private String name;
    //小编回复内容
    private String message;
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private static final long serialVersionUID = 1L;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getNovelId() {
        return novelId;
    }

    public void setNovelId(Integer novelId) {
        this.novelId = novelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(Integer chapterNum) {
        this.chapterNum = chapterNum;
    }

    /**
     * 文章状态
     */
    public enum Status {
        /**
         * 待审核
         */
        unreviewd("unreviewd"), // 待审核
        /**
         * 审核不通过
         */
        failed("failed"), // 审核不通过
         
        /**
         * 审核通过（展示）
         */
        passed("passed"); // 审核通过（展示）
         
        
        private String value;

        private Status(String value) {
            this.value = value;
        }

        public String Value() {
            return value;
        }
    }

}