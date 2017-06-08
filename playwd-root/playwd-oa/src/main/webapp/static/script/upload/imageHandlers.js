//上传图片页面统一效果处理

function imgUploadFn() {





    //保存图册
    saveAlbumFn();
}


//获取指定url参数值
function getQuery(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"),
        r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}



//几秒后消失的层
var Timer_box = null;
function timeShowPop($obj, second) {
    clearTimeout(Timer_box);
    $obj.fadeIn();
    Timer_box = setTimeout(function () {
        $obj.fadeOut();
    }, second ? second * 1000 : 3000);
}


//显示上传提示语
/*(msg:要显示的文件
code:默认提醒橙色叹号
1：成功对号
2：失败叉号
3：服务器忙黄色叹号
4：即默认 橙色叹号
*/
function showUploadMsg(msg,code) {
    var
        $obj = $("#js_StatuPop"),
        $objTxt = $("#js_StatuPop_Txt"),
        $objIco = $("#js_StatuPop_Icon"),
        code = code ? parseInt(code) : 4;
    $objTxt.html(msg);
    $objIco.attr("class", "icon icon0"+code);
    timeShowPop($obj,3);
}

//创建图像占位
function createImgLi(index) {
    var lihtml = '' +
        '<li class="js_ReelNeedUpLi" id="UploadLi_' + index + '">' +
        '    <div class="p_picms">' +
        '        <a href="javascript:;">' +
        '            <img src="../Content/images/upimgbg.png">' +
        '        </a>' +
        '        <div  class="add_imgintro">' +
        '            <span class="i_mod"></span>' +
        '            <span class="i_txt">添加描述</span>' +
        '            <textarea  class="i_textms" onpropertychange="if(value.length>200) value=value.substr(0,200)" maxlength="200"></textarea>' +
        '        </div>' +
        '        <span class="set_img">' +
        '            <span class="i_mod"></span>' +
        '            <span class="i_icon">' +
        '                <a href="#" class="i_icon_del js_RemoveThisPic pngbg" title="删除这张"></a>' +
        '                <a href="#" class="i_icon_prev pngbg" title="(排序)往前移"></a>' +
        '                <a href="#" class="i_icon_next pngbg" title="(排序)往后移"></a>' +
        '            </span>' +
        '        </span>' +
        '    </div>' +
        '    <p class="pot_pbg">' +
        '           <span class="js_spImgIntro">准备上传中...</span>' +
        '           <input maxlength="30" class="js_IptImgIntro" type="text" value="准备上传中..." />' +
        '    </p>' +
        '</li>';
    $("#js_UpImageLi").before(lihtml);
}

//上传完图片LI加载事件
function imgLoadEvent() {
    //图片列表加载完成后执行事件


    //上传进度条隐藏
    $("#divFileProgressContainer").hide();

    //鼠标移上图片Li
    $("#js_ImageUl").find("li").hover(function () {
        $(this).addClass("cur");
    }, function () {
        if ($(this).find(".i_textms").val()) {

        } else {
            $(this).removeClass("cur");
        }
    });

    //删除图片
    $(".js_RemoveThisPic").unbind("click").bind("click", function () {
        $(this).parents("li").hide().removeClass("js_ReelNeedUpLi");
        return false;
    });

    //编辑图片名称
    $(".js_spImgIntro").unbind("click").bind("click", function () {
        $(this).hide().next().val($(this).text()).show().focus();
        return false;
    });
    $(".js_IptImgIntro").blur(function () {
        $(this).hide().prev().show().html(this.value);
        $(this).parents("li").attr("data-picturename", this.value);
    });


    //添加描述
    $(".add_imgintro").unbind("click").bind("click", function () {
        var $this = $(this);
        $this.find(".i_txt").hide();
        $(this).addClass("i_edit").find("textarea").css("border", "1px solid #fff").focus().unbind("blur").bind("blur", function () {
            $(this).css("border", "1px dashed transparent");
            if ($(this).val()) {
                $(this).parent().addClass("i_edit_cont").removeClass("i_edit");
            } else {
                $(this).parent().removeClass("i_edit");
                $this.find(".i_txt").show();
            }
            $(this).parents("li").attr("data-brief", this.value);
        });
    });
    var $imgUl = $("#js_ImageUl");

    //显示图片名称及描述title
    $("#js_ImageUl").find(".i_textms").hover(function () {
        this.title = this.value;
    }).end().find(".js_IptImgIntro").hover(function () {
        this.title = this.value;
    });

    //往前移一张
    $(".i_icon_prev").unbind("click").bind("click", function () {
        var parli = $(this).parents("li");
        if (parli.index() <= 0) {
            return false;
        } else {
            $imgUl.find("li").eq(parli.index() - 1).before(parli);
        }
        return false;
    });

    //往后移一张
    $(".i_icon_next").unbind("click").bind("click", function () {
        var parli = $(this).parents("li");
        if (parli.index() >= $imgUl.find("li").length - 2) {
            return false;
        } else {
            $imgUl.find("li").eq(parli.index() + 1).after(parli);
        }
        return false;
    });
}

//空处理函数
function emptySet(data, str) {
    var str = str?str:"null";
    return data == "" ? str : $.trim(data);
}

//保存图册
function saveAlbumFn() {
    var $sBtn = $("#js_SaveAlbum");
    var sdata = "";
    var reloadPage = function (second) {
        var second1 = second ? second : 3;
        $(".pop_pinfo").find("input").val("");
        setTimeout(function () {
            var lhref = window.location.href;
            lhref = lhref.replace(/#*$/, '');
            window.location.href = lhref;
        }, second1*1000);
    }
    $sBtn.click(function () {
        var sdataArr = [];
        sdata = getAndRegParam();
        if (!sdata.Picture||!sdata.AlbumsPicture||!sdata.AlbumsPictureSmall) {
            showUploadMsg("请至少上传一张图片！",4);
            return false;
        } else if (!sdata.AlbumsName) {
            showUploadMsg("图册名称不能为空！",4);
            return false;
        } else if (!sdata.NetId) {
            showUploadMsg("必须选择区组！");
            return false;
        } else if (!sdata.ServerId) {
            showUploadMsg("必须选择服务器！");
            return false;
        } else if (!sdata.ServerName) {
            showUploadMsg("必须选择服务器！");
            return false;
        } else if (!sdata.RoleName) {
            showUploadMsg("角色名称不能为空！");
            return false;
        } else if (!sdata.KeyWord) {
            showUploadMsg("关键字不能为空！");
            return false;
        } else if (!sdata.NickName) {
            showUploadMsg("玩家名称不能为空！");
            return false;
        } else if (!sdata.CategoryId) {
            showUploadMsg("请选择分类！");
            return false;
        } else {
            sdataArr.push(sdata.Picture);
            var otherParamArr = [];
            otherParamArr.push("\"AlbumsName\":\"" + sdata.AlbumsName + "\"");
            otherParamArr.push("\"NetId\":\"" + sdata.NetId + "\"");
            otherParamArr.push("\"ServerId\":\"" + sdata.ServerId + "\"");
            otherParamArr.push("\"ServerName\":\"" + sdata.ServerName + "\"");
            otherParamArr.push("\"RoleName\":\"" + sdata.RoleName + "\"");
            otherParamArr.push("\"KeyWord\":\"" + sdata.KeyWord + "\"");
            otherParamArr.push("\"NickName\":\"" + sdata.NickName + "\"");
            otherParamArr.push("\"CategoryId\":\"" + sdata.CategoryId + "\"");
            otherParamArr.push("\"AlbumsPicture\":\"" + sdata.AlbumsPicture + "\"");
            otherParamArr.push("\"AlbumsPictureSmall\":\"" + sdata.AlbumsPictureSmall + "\"");
            otherParamArr.push("\"Width\":\"" + sdata.Width + "\"");
            otherParamArr.push("\"Height\":\"" + sdata.Height + "\"");
            otherParamArr.join(",");

            sdataArr.push("{" + otherParamArr + "}");
            $.ajax({
                url: "/Picture/InsertPicture",
                type: "POST",
                dataType: "JSON",
                data: {
                    Picture: "[" + sdataArr.join(",") + "]",
                    r:Math.random()
                },
                success: function (data) {
                    if (data.IsSuccess) {
                        showUploadMsg("成功上传" + sdata.picLen + "张图片,请耐心等待官方审核!", 1);
                        reloadPage();
                    } else {
                        var msg=data.Message?data.Message:"上传出错，请刷新页面重试！";
                        showUploadMsg(msg, 2);
                        reloadPage(4);
                    }
                },
                error: function () {
                    showUploadMsg("程度接口出错！", 3);
                    reloadPage(4);
                }
            });
        }
        return false;
    });
}

//组装参数及验证
function getAndRegParam() {
    var $iUl = $("#js_ImageUl");
    var $iLi = $iUl.find("li.js_ReelNeedUpLi");
    var allLiParaArr = [];
    for (var i = 0; i < $iLi.length; i++) {
        var iLiArr = [];
        iLiArr.push("\"Picture\":\"" + $iLi[i].getAttribute("data-picture") + "\"");
        iLiArr.push("\"PictureSmall\":\"" + $iLi[i].getAttribute("data-picturesmall") + "\"");
        iLiArr.push("\"PictureName\":\"" + emptySet($iLi[i].getAttribute("data-picturename"), "未命名") + "\"");
        iLiArr.push("\"Brief\":\"" + emptySet($iLi[i].getAttribute("data-brief")) + "\"");
        iLiArr.join(",");
        allLiParaArr.push("{" + iLiArr + "}");
    }
    return sdata = {
        picLen:$iLi.length,
        //图册集合
        Picture: allLiParaArr,
        //相册名称
        AlbumsName: $.trim($("#js_AlbumsName").val()),
        //区组ID
        NetId: $("#js_NetId").val(),
        //服务器ID
        ServerId: $("#js_ServerIdAndName").val(),
        //服务器名称
        ServerName: $("#js_ServerIdAndName").find("option:selected").text(),
        //角色名
        RoleName: $.trim($("#js_RoleName").val()),
        //关键字
        KeyWord: $.trim($("#js_Keyword").val()),
        //玩家名称
        NickName: $.trim($("#js_NickName").val()),
        //分类ID
        CategoryId: $("#js_HidCategoryId").val(),
        //图册封面
        AlbumsPicture: $iLi.eq(0).attr("data-picture"),
        //图册封面缩略图
        AlbumsPictureSmall: $iLi.eq(0).attr("data-picturesmall"),
        //图册封面宽度
        Width: $iLi.eq(0).attr("data-width"),
        //图册封面高度
        Height: $iLi.eq(0).attr("data-height")
    }
}

//组成最终上传队列时执行函数
function fileQueued(file) {
    createImgLi(file.index);
}

//选择完文件时出错执行函数
function fileQueueError(file, errorCode, message) {
    //debugger;
    try {
        var imageName = "error.gif";
        var errorName = "";
        if (errorCode === SWFUpload.errorCode_QUEUE_LIMIT_EXCEEDED) {
            errorName = "You have attempted to queue too many files.";
        }

        if (errorName !== "") {
            alert(errorName);
            return;
        }
        switch (errorCode) {
            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
                imageName = "zerobyte.gif";
                showUploadMsg("\"" + file.name + "\"<br/>没有字节！", 4);
                break;
            case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
                imageName = "toobig.gif";
                showUploadMsg("\"" + file.name + "\"<br/>的大小已超出最大限制！", 4);
                break;
            case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
                showUploadMsg("无效的文件类型！", 4);
                break;
            case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
                showUploadMsg("最多只能上传100张图片！",4);
                break;
            default:
                showUploadMsg(message,4);
                break;
        }


    } catch (ex) {
        this.debug(ex);
    }
}

//确定选择框时执行函数
function fileDialogComplete(numFilesSelected, numFilesQueued, file) {
    try {
        if (numFilesQueued >0) {
            $("#divFileProgressContainer").show();
            this.startUpload();
            this.NeedUpImageCount = numFilesQueued ;
        }
    } catch (ex) {
        this.debug(ex);
    }
}



//上传过程中执行函数
function uploadProgress(file, bytesLoaded) {
    try {
        var percent = Math.ceil((bytesLoaded / file.size) * 100);
        var progress = new FileProgress(file, this.customSettings.upload_target);
        var upIndex = file.index;
        $("#UploadLi_" + upIndex).append($("#divFileProgressContainer"));
        progress.setProgress(percent);
        //原为===，搜狗下文件字节上传时会变大，会出现大于100的情况
        if (percent >= 100) {
            progress.setStatus("建立缩略图中...");
            progress.toggleCancel(false, this);
            if (this.NeedUpImageCount < 2) {
                $("#js_NeedUpImgNum").html("(全部图片已上传完成)");
            } else {
                $("#js_NeedUpImgNum").html("(" + (--this.NeedUpImageCount) + "张图片等待上传)");
            }

        } else {
            progress.setStatus("上传中...");
            progress.toggleCancel(true, this);
        }
    } catch (ex) {
        this.debug(ex);
    }
}

//上传成功后执行函数
function uploadSuccess(file, serverData) {
    try {
        serverData = eval('(' + serverData + ')');
        $("#UploadLi_" + file.index).attr({
            "data-picture": serverData.Url,
            "data-picturesmall":serverData.AffixUrl,
            "data-picturename": file.name,
            "data-prief": "",
            "data-width": serverData.Width,
            "data-height":serverData.Height
        })
            .find("img").attr("src", serverData.AffixUrl)
            .end().find(".js_spImgIntro").html(file.name);
        //debugger;
        var hidFileName = document.getElementById("ctl00_ContentPlaceHolder1_hidFileNames");
        var hidFileOldName = document.getElementById("ctl00_ContentPlaceHolder1_hidFileOldNames");

        var fileName = serverData.Code.toString();


        var fileOldName = file.name;

        hidFileName.value += fileName + "$";
        hidFileOldName.value += fileOldName + "$";

        //上传后的图片显示路径

        addImage(serverData.Url);
        var progress = new FileProgress(file, this.customSettings.upload_target);

        progress.setStatus("上传成功！");
        progress.toggleCancel(false);

    } catch (ex) {
        this.debug(ex);
    }
}

//所有文件上传完成后执行函数
function uploadComplete(file) {
    try {
        if (this.getStats().files_queued > 0) {
            this.startUpload();
        } else {

            //上传完图片LI加载事件
            imgLoadEvent();


            var progress = new FileProgress(file, this.customSettings.upload_target);
            progress.setComplete();
            progress.setStatus("上传成功.");
            var hidFileOldName = document.getElementById("ctl00_ContentPlaceHolder1_hidFileOldNames").value.split("$");

            progress.toggleCancel(false);
        }
    } catch (ex) {
        this.debug(ex);
    }
}

//上传出错执行函数
function uploadError(file, errorCode, message) {
    var imageName = "error.gif";
    var progress;
    try {
        switch (errorCode) {
            case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
                try {
                    progress = new FileProgress(file, this.customSettings.upload_target);
                    progress.setCancelled();
                    progress.setStatus("Cancelled");
                    progress.toggleCancel(false);
                }
                catch (ex1) {
                    this.debug(ex1);
                }
                break;
            case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
                                                try {
                                                    progress = new FileProgress(file, this.customSettings.upload_target);
                                                    progress.setCancelled();
                                                    progress.setStatus("Stopped");
                                                    progress.toggleCancel(true);
                                                }
                                                catch (ex2) {
                                                    this.debug(ex2);
                                                }
            case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
                imageName = "uploadlimit.gif";
                break;
            default:
                alert(message);
                break;
        }

        addImage("images/" + imageName);

    } catch (ex3) {
        this.debug(ex3);
    }

}

//出错时自定义一个图片
function addImage(src) {
    var newImg = document.createElement("img");
    newImg.style.margin = "5px";
    newImg.style.width = "200px";
    newImg.style.height = "200px";
    document.getElementById("thumbnails").appendChild(newImg);
    if (newImg.filters) {
        try {
            newImg.filters.item("DXImageTransform.Microsoft.Alpha").opacity = 0;
        } catch (e) {
            newImg.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + 0 + ')';
        }
    } else {
        newImg.style.opacity = 0;
    }

    newImg.onload = function () {
        http://wd.gyyx.cn/VideoImage/upload.aspx
            fadeIn(newImg, 0);
    };
    newImg.src = src;
    deleteImg();
}

//加载图片动画
function fadeIn(element, opacity) {
    var reduceOpacityBy = 5;
    var rate = 30; // 15 fps


    if (opacity < 100) {
        opacity += reduceOpacityBy;
        if (opacity > 100) {
            opacity = 100;
        }

        if (element.filters) {
            try {
                element.filters.item("DXImageTransform.Microsoft.Alpha").opacity = opacity;
            } catch (e) {
                element.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + opacity + ')';
            }
        } else {
            element.style.opacity = opacity / 100;
        }
    }

    if (opacity < 100) {
        setTimeout(function () {
            fadeIn(element, opacity);
        }, rate);
    }
}



/* ******************************************
*	FileProgress Object
*	Control object for displaying file info
* ****************************************** */

function FileProgress(file, targetID) {
    this.fileProgressID = "divFileProgress";
    this.fileProgressWrapper = document.getElementById(this.fileProgressID);
    if (!this.fileProgressWrapper) {
        this.fileProgressWrapper = document.createElement("div");
        this.fileProgressWrapper.className = "progressWrapper";
        this.fileProgressWrapper.id = this.fileProgressID;

        this.fileProgressElement = document.createElement("div");
        this.fileProgressElement.className = "progressContainer pop_progb";

        var progressCancel = document.createElement("a");
        progressCancel.className = "progressCancel";
        progressCancel.href = "#";
        progressCancel.style.visibility = "hidden";
        progressCancel.appendChild(document.createTextNode(" "));

        var progressText = document.createElement("div");
        progressText.className = "progressName";
        progressText.appendChild(document.createTextNode(file.name));

        var progressBar = document.createElement("div");
        progressBar.className = "progressBarInProgress pop_pbgon";

        var progressStatus = document.createElement("div");
        progressStatus.className = "progressBarStatus";
        progressStatus.innerHTML = "&nbsp;";

        this.fileProgressElement.appendChild(progressCancel);
        this.fileProgressElement.appendChild(progressText);
        this.fileProgressElement.appendChild(progressStatus);
        this.fileProgressElement.appendChild(progressBar);

        this.fileProgressWrapper.appendChild(this.fileProgressElement);

        document.getElementById(targetID).appendChild(this.fileProgressWrapper);
        fadeIn(this.fileProgressWrapper, 0);

    } else {
        this.fileProgressElement = this.fileProgressWrapper.firstChild;
        this.fileProgressElement.childNodes[1].firstChild.nodeValue = file.name;
    }

    this.height = this.fileProgressWrapper.offsetHeight;

}
FileProgress.prototype.setProgress = function (percentage) {
    this.fileProgressElement.className = "progressContainer green";
    this.fileProgressElement.childNodes[3].className = "progressBarInProgress";
    this.fileProgressElement.childNodes[3].style.width = percentage + "%";
};
FileProgress.prototype.setComplete = function () {
    this.fileProgressElement.className = "progressContainer blue";
    this.fileProgressElement.childNodes[3].className = "progressBarComplete";
    this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress.prototype.setError = function () {
    this.fileProgressElement.className = "progressContainer red";
    this.fileProgressElement.childNodes[3].className = "progressBarError";
    this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress.prototype.setCancelled = function () {
    this.fileProgressElement.className = "progressContainer";
    this.fileProgressElement.childNodes[3].className = "progressBarError";
    this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress.prototype.setStatus = function (status) {
    this.fileProgressElement.childNodes[2].innerHTML = status;
};

FileProgress.prototype.toggleCancel = function (show, swfuploadInstance) {
    this.fileProgressElement.childNodes[0].style.visibility = show ? "visible" : "hidden";
    if (swfuploadInstance) {
        var fileID = this.fileProgressID;
        this.fileProgressElement.childNodes[0].onclick = function () {
            swfuploadInstance.cancelUpload(fileID);
            return false;
        };
    }
};
