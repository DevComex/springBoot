package cn.gyyx.wd.wanjia.cartoon.beans;

public class WanwdPictureTb {
    private Integer code;

    private String pictureName;

    private String brief;

    private String picture;

    private String pictureSmall;

    private Integer albumsCode;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName == null ? null : pictureName.trim();
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getPictureSmall() {
        return pictureSmall;
    }

    public void setPictureSmall(String pictureSmall) {
        this.pictureSmall = pictureSmall == null ? null : pictureSmall.trim();
    }

    public Integer getAlbumsCode() {
        return albumsCode;
    }

    public void setAlbumsCode(Integer albumsCode) {
        this.albumsCode = albumsCode;
    }
}