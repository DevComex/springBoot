package cn.gyyx.playwd.ui.viewmodule;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
  * <p>
  *   上传小说model 描述
  * </p>
  *  
  * @author chenglong
  * @since 0.0.1
 */
public class NovelChapterUploadModel {
    @NotBlank(message = "invalid-name|小说名称不能为空")
    @Length(max=20,message="invalid-name|小说名称长度不能大于20个汉字")
    private String name;
    
    @NotBlank(message = "invalid-categoryId|小说类型不能为空")
    @Pattern(regexp="^(0|[1-9][0-9]*)$",message="invalid-categoryId|小说类型无效")
    private String categoryId;
    
    @NotBlank(message = "invalid-cover|封面不能为空")
    @Length(max=100,message="invalid-cover|封面不能大于100个汉字")
    @Pattern(regexp="^http://[^\\s]+",message="invalid-cover|图片地址格式不正确")
    private String cover;
    
    @NotBlank(message = "invalid-description|简介不能为空")
    @Length(max=300,message="invalid-description|简介不能大于300个汉字")
    private String description;
    
    @NotBlank(message = "invalid-title|章节名称不能为空")
    @Length(max=20,message="invalid-title|章节名称不能大于20个汉字")
    private String title;
    
    @NotBlank(message = "invalid-status|是否完结不能为空")
    @Pattern(regexp="\\b(finished|unfinished)\\b",message="invalid-status|无效的是否完结参数")
    private String status;
    
//    @NotBlank(message = "invalid-chapterNum|章节不能为空")
//    @Pattern(regexp="^(0|[1-9][0-9]*)$",message="invalid-chapterNum|章节参数无效")
    private String chapterNum;
    
    @NotBlank(message = "invalid-content|章节内容不能为空")
    @Length(min=500,message="invalid-description|章节内容最少500字")
    private String content;
    
    @NotBlank(message = "invalid-captcha|验证码不能为空")
    private String captcha;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(String chapterNum) {
        this.chapterNum = chapterNum;
    }
}
