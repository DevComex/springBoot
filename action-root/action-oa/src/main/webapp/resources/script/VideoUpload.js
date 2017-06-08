//add by tianhaiting 2014-9-18
// change by chenqiaoling 2014-11-12

$(function () {
    //上传图片
    UPLOADURL = "http://up.gyyx.cn/Image/WebSiteSaveToReal.ashx?group=wd1&&width=200&height=200";
    FLASHURL = "http://up.gyyx.cn/flash/swfupload_f9.swf"
    FILESIZE = "1024";
    FILETYPE = "*.jpg;*.png;*.bmp;*.gif";
    TYPESDESCRIPTION = "JPG/GIF Images";
    UPLOADLIMIT = 0;
    QUEUELIMIT = 0;
    DEBUG = false;
    SAVEELEMENT = "FileCode";

    if ($("#btnUpload").length > 0) {
        Initialize();
    }
    if ($("#btnUploads").length > 0) {
        Initializes();
    }
});
