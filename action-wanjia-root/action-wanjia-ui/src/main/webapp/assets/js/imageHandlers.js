var ss,ssString,ssArr,imgUrlString,imgUrlStringArr,imgUrlArr=[],imgUrlArrHJH=[];
//上传图片页面统一效果处理
function imgUploadFn() {
    //判断是否登录
    isLogin();

    //离焦事件后
    //区组
    $("#js_NetId").change(function(){
    	var optionVal = $.trim($("#js_NetId").find("option:selected").attr("value"));
    	if(optionVal === ""){
    		$(".PromptSereve").show();
    	}else{
    		$(".PromptSereve").hide();
    	}
    });
    //服务器
    $("#js_ServerIdAndName").change(function(){
    	var optionVal = $.trim($("#js_ServerIdAndName").find("option:selected").attr("value"));
    	if(optionVal === ""){
    		$(".PromptSereve1").show();
    	}else{
    		$(".PromptSereve1").hide();
    	}
    });
    //漫画名称

    $("#js_CartoonName").blur(function(){
    	var optionVal = $.trim($("#js_CartoonName").val());
    	if(optionVal === ""){
    		$(".PromptName").show();
    	}else{
    		$(".PromptName").hide();
    	}
    });
    //
    $(".web_wrap").click(function(){
    	if($.trim($("#js_CartoonName").val())){
    		$(".PromptName").hide();
    	}
    });

    //章节名称
    $("#js_AlbumsName").blur(function(){
    	var optionVal = $.trim($("#js_AlbumsName").val());
    	if(optionVal === ""){
    		$(".PromptChapter").show();
    	}else{
    		$(".PromptChapter").hide();
    	}
    });

    //角色名
    $("#js_RoleName").blur(function(){
    	var optionVal = $.trim($("#js_RoleName").val());
    	if(optionVal === ""){
    		$(".PromptRole").show();
    	}else{
    		$(".PromptRole").hide();
    	}
    });

    //章数
    $("#js_ChapterNumber").blur(function(){
    	var optionVal = $.trim($("#js_ChapterNumber").val());
    	if(optionVal === ""){
    		$(".PromptNumber").show();
    	}else{
    		$(".PromptNumber").hide();
    	}
    });

    //漫画简介
    $("#js_CartoonIntroduction").blur(function(){
    	var optionVal = $.trim($("#js_CartoonIntroduction").val());
    	if(optionVal === ""){
    		$(".PromptIntroduction").show();
    	}else{
    		$(".PromptIntroduction").hide();
    	}
    });

    //漫画类型
    $("#js_cartoonType").change(function(){
    	var optionVal = $.trim($("#js_cartoonType").find("option:selected").attr("value"));
    	if(optionVal === 0){
    		$(".PromptType").show();
    	}else{
    		$(".PromptType").hide();
    	}
    });


    //漫画简介
    $("#js_yzm").blur(function(){
    	var optionVal = $.trim($("#js_yzm").val());
    	if(optionVal === ""){
    		$(".PromptYzm").show();
    	}else{
    		$(".PromptYzm").hide();
    	}
    });


    //上传图片--获取默认角色（区服选择）-- start
    var netId = "", serverId = "", role = "";


    //绑定分类
    bindCate($("#js_CategoryId"));

    //保存图册
    saveAlbumFn();
}

//判断是否登录并弹出登录层
function isLogin() {
    $.ajax({
        url: "http://reg.gyyx.cn/Login/Status",
        type: "GET",
        dataType: "jsonp",
        jsonp: "jsoncallback",
        success: function (data) {
            if (!data.IsLogin) {
                alertFn(null,null,true);
            }
        }
    });
}

//获取指定url参数值
function getQuery(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"),
        r = window.location.search.substr(1).match(reg);
    if (r !== null) {
        return unescape(r[2]);
    }
    return null;
}

//获取角色列表  2015/06/17
function getRoleList(serverId) {
    $.ajax({
        url: rc+"/User/GetSyncRole",
        type: "GET",
        dataType: "JSON",
        data: {
            serverId: serverId,
            r: Math.random()
        },
        success: function (d) {
            if (d.IsSuccess) {
                if (d.List.length > 0) {
                    var str = "";
                    for (var i = 0; i < d.List.length; i++) {
                        if (d.List[i].IsDefault) {
                            $("#js_RoleName").val(d.List[i].NickName);
                        }
                        str += '<div class="wd_js_l">' +
                            '<span><img src="/Content/images/portrait/' + d.List[i].Picture + '.jpg"></span>' +
                            '<p>角色：' + d.List[i].NickName + '</p>' +
                            '<p>服务器：' + d.List[i].ServerName + '</p>' +
                            '<p class="rolebox_btn js_but"><a href="javascript:;" class="js_upbut js_SetRole" data-NickName="' + d.List[i].NickName + '">选择</a></p>' +
                        '</div>';
                    }
                    $("#popRoleList .pop_role_content").empty().html(str).show();
                    alertRoleBoxFn(serverId);
                }
            } else {
                $("#popRoleList .pop_role_content").empty().hide();
                if (d.Values == -3) {
                    alertFn(null, null, true);
                } else {
                    //$("#popRoleList .pop_role_txt a.torole").html(d.Message);
                    alertRoleBoxFn(serverId);
                }

            }
        }
    });
}

//角色列表弹层  2015/06/17
function alertRoleBoxFn(serverId) {
    var bodyWidth, bodyHeight, dialogLeft, dialogTop;
    var alertFn = function () { /*使弹出改变窗口大小时居中显示*/
        bodyWidth = $(window).width();
        bodyHeight = $(window).height();
        dialogLeft = (bodyWidth - $("#popRoleList").width()) / 2;
        dialogTop = (($(window).height() - $("#popRoleList").height()) / 2 + $(document).scrollTop());
        $("#popRoleList").css({ "left": dialogLeft, "top": dialogTop });
        $("#Mark").css({ "width": bodyWidth, "height": bodyHeight + $(document).scrollTop() });
    };
    var showalertFn = function () {
        $("#popRoleList").show();
        $("#Mark").show();
    };
    var hidealertFn = function () {
        $("#popRoleList").hide();
        $("#Mark").hide();
    };
    $(".pop_role_close").off("click").on("click", function () {
        hidealertFn();
    });
    $(".js_reflash").off("click").on("click", function () {
        getRoleList(serverId);
    });
    $(".js_SetRole").off("click").on("click", function () {
        if (confirm("已经选择角色，继续上传")) {
            $("#js_RoleName").val($(this).attr("data-NickName"));
            hidealertFn();
        }
    });
    $("#Mark").on("click", function () {
        hidealertFn();
    });
    alertFn();
    showalertFn();
    $(window).resize(function () { alertFn(); });
    $(window).scroll(function () { alertFn(); });
}

//绑定漫画分类
function bindCate($obj) {
    //重置分类hidden值
    $("#js_HidCategoryId").val("");
    $.ajax({
        url: "",
        type: "GET",
        dataType: "JSON",
        data: {
            cmType: 2,
            r: Math.random()
        },
        success: function (data) {
            if (data.Data && data.Data[0]) {
                var ahtml = '';
                var dCont = data.Data;
                for (var i = 0; i < dCont.length; i++) {
                    ahtml += '<a href="javascript:;" data-id="' + dCont[i].Id + '">' + dCont[i].Name + '</a>';
                }
                $obj.html(ahtml).find("a").unbind("click").bind("click", function () {
                    $(this).addClass("cur").siblings().removeClass("cur");
                    $("#js_HidCategoryId").val($(this).attr("data-id"));
                    return false;
                });
            } else {
                $obj.html("");
            }
        }
    });
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
        '           <span class="js_spImgUrl" style="display:none;">图片地址集合</span>' +
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
        $(this).parents("li").remove();
        return false;
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
    return data === "" ? str : $.trim(data);
}

//上传
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
    };
    $sBtn.click(function () {

    	imgUrlArrHJH=[];
        var sdataArr = [];
        sdata = getAndRegParam();
        if (!sdata.Picture.length) {
            //showUploadMsg("请至少上传一张图片！",4);
        	alert("请至少上传一张图片！");
            return false;
        }else if (!sdata.NetId) {
            //showUploadMsg("必须选择区组！");
        	$(".PromptSereve").show();
            return false;
        }else if (!sdata.ServerName) {
            //showUploadMsg("必须选择服务器！");
            $(".PromptSereve1").show();
            return false;
        } else if (!sdata.Cartoon) {
            //showUploadMsg("必须选择漫画名称！");
            $(".PromptName").show();
            return false;
        }else if (!sdata.SectionName) {
            //showUploadMsg("必须选择章节名称！");
            //scrollTo(20,0);
            $(".PromptChapter").show();
            return false;
        } else if (!sdata.RoleName) {
            //showUploadMsg("角色名称不能为空！");
            $(".PromptRole").show();
            return false;
        } else if (!sdata.ChapterNumber) {
            //showUploadMsg("必须选择章数！");
            $(".PromptNumber").show();
            return false;
        } else if (!sdata.Introduction) {
            //showUploadMsg("必须选择漫画简介！");
        	$(".PromptIntroduction").show();
            return false;
        } else if (!sdata.IsOver) {
            //showUploadMsg("必须选择漫画是否完结！");
            $(".PromptIs").show();
            return false;
        } else if (!sdata.yzm) {
            //showUploadMsg("必须选择漫画验证码！");
            $(".PromptYzm").show();
            return false;
        }else {
            sdataArr.push(sdata.Picture);
            var otherParamArr = [];
            otherParamArr.push("\"NetId\":\"" + sdata.NetId + "\"");
            otherParamArr.push("\"ServerId\":\"" + sdata.ServerId + "\"");
            otherParamArr.push("\"ServerName\":\"" + sdata.ServerName + "\"");
            otherParamArr.push("\"RoleName\":\"" + sdata.RoleName + "\"");

            otherParamArr.push("\"CartoonName\":\"" + sdata.Cartoon + "\""); //漫画名称
            otherParamArr.push("\"ChapterIndexCn \":\"" + sdata.ChapterNumber + "\""); //章数
            otherParamArr.push("\"CategoryDisc\":\"" + sdata.Introduction + "\""); //漫画简介
            otherParamArr.push("\"yzm\":\"" + sdata.yzm + "\""); //验证码
            otherParamArr.push("\"CartoonType\":\"" + sdata.cartoonType + "\""); //漫画类型
            otherParamArr.push("\"IsEnd\":\"" + sdata.IsOver + "\""); //是否完结

            otherParamArr.join(",");

            sdataArr.push("{" + otherParamArr + "}");
            var authorName = $.trim($("#js_RoleName").val());  //角色名
            var AlbumsName = $.trim($("#js_AlbumsName").val());   //章节名称
            var CartoonName = $.trim($("#js_CartoonName").val());   //漫画名称
            var ChapterNumber = $.trim($("#js_ChapterNumber").val());   //章数
            var CartoonIntroduction = $.trim($("#js_CartoonIntroduction").val());   //漫画简介
            var js_yzm = $.trim($("#js_yzm").val());   //验证码
            var IsOver = $("input:radio[name = IsOver]:checked").val();     //是否完结
            var netType = $.trim($("#js_NetId").find("option:selected").attr("value"));           //服务器大区
            var cartoonCode=$.trim($("#js_cartoonType").find("option:selected").attr("value"));  //漫画类型code

            var ServerName = $.trim($('#js_ServerIdAndName option:selected').val());
            var ServerCode = $.trim($('#js_ServerIdAndName option:selected').attr("code"));  //服务器code
            $(".p_picms a img").each(function(){
            	imgUrlArrHJH.push($(this).attr("src"));
            });
            //console.log(imgUrlArrHJH);
            $.ajax({
                url: rc+"/upload/upload",  ///Picture/InsertPicture
                type: "POST",
                dataType: "JSON",
                data: {
                	r:Math.random(),
                	pagesUrl:imgUrlArrHJH,  //图片url
                    authorName:authorName,  //角色名称
                    netType:netType,   //服务大区
                    serverCode  : ServerCode,  //服务器code
                    title:CartoonName,  //漫画名
                    bookName:AlbumsName,   //章节名
                    bookNum:ChapterNumber, //章节数
                    context:CartoonIntroduction,   //简介
                    isClosed:IsOver,  //是否完结 0未完 1完结
                    categoryCode: cartoonCode ,  //漫画类型的code
                    captcha:js_yzm   //验证码

                },
                success: function (data) {
                	if (data.success) {
                		if(data.message == "上传成功"){
                    		alert("上传成功,请耐心等待官方审核!");
                    	}
                        //showUploadMsg("成功上传" + sdata.picLen + "张图片,请耐心等待官方审核!", 1);
                        reloadPage();

                    }else {
                    	if(data.message!=="请先登录"){
                    		if(data.data.authorName != "正确"){
                        		alert(data.data.authorName);
                        	}else if(data.data.bookName != "正确"){
                        		alert(data.data.bookName);
                        	}else if(data.data.bookNum != "正确"){
                        		alert(data.data.bookNum);
                        	}else if(data.data.captcha != "正确"){
                        		alert(data.data.captcha);
                        		$("#js_yzmBtn").click();
                        	}else if(data.data.categoryCode != "正确"){
                        		alert(data.data.categoryCode);
                        	}else if(data.data.context != "正确"){
                        		alert(data.data.context);
                        	}else if(data.data.isClosed != "正确"){
                        		alert(data.data.isClosed);
                        	}else if(data.data.pages != "正确"){
                        		alert(data.data.pages);
                        	}else if(data.data.server != "正确"){
                        		alert(data.data.server);
                        	}else if(data.data.title != "正确"){
                        		alert(data.data.title);
                        	}
                    	}else{
                    		alert(data.message);
                    	}


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
    //alert($iLi);
    var allLiParaArr = [];
    for (var i = 0; i < $iLi.length; i++) {
        var iLiArr = [];
        iLiArr.push("\"PictureUrl\":\"" + $iLi[i].getAttribute("data-PictureUrl") + "\"");
        iLiArr.push("\"PictureUrlS\":\"" + $iLi[i].getAttribute("data-PictureUrlS") + "\"");
        iLiArr.push("\"PictureName\":\"" + emptySet($iLi[i].getAttribute("data-PictureName"), "未命名") + "\"");
        iLiArr.join(",");
        allLiParaArr.push("{" + iLiArr + "}");
    }
    return sdata = {
        picLen:$iLi.length,
        //图册集合
        Picture: allLiParaArr,
        //区组ID
        NetId: $("#js_NetId").val(),
        //服务器ID
        ServerId: $("#js_ServerIdAndName").val(),
        //服务器名称
        ServerName: $("#js_ServerIdAndName").find("option:selected").val(),
        //角色名
        RoleName: $.trim($("#js_RoleName").val()),
        //章节名称
        SectionName: $.trim($("#js_AlbumsName").val()),
        //漫画名称
        Cartoon: $.trim($("#js_CartoonName").val()),
        //章数
        ChapterNumber: $.trim($("#js_ChapterNumber").val()),
        //漫画简介
        Introduction: $.trim($("#js_CartoonIntroduction").val()),
        //验证码
        yzm: $.trim($("#js_yzm").val()),
        //漫画类型

        //是否完结
        IsOver: $.trim($("input:radio[name=IsOver]:checked").val())
    };
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
                //showUploadMsg("\"" + file.name + "\"<br/>的大小已超出最大限制！", 4);
                alert("\"" + file.name + "\"的大小已超出最大限制。最大只能为2M哦！！");
                break;
            case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
                //showUploadMsg("无效的文件类型！", 4);
            	alert("上传的文件类型无效哦，请仔细阅读上方要求，按要求上传！");
                break;
            case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
                //showUploadMsg("最多只能上传30张图片！",4);
            	alert("最多只能上传30张图片！");
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
            "data-PictureUrl": serverData.Url,
            "data-PictureUrlS": serverData.AffixUrl,
            "data-PictureName": file.name,
            "data-prief": "",
            "data-width": serverData.Width,
            "data-height": serverData.Height

        }).find("img").attr("src", serverData.AffixUrl).end().find(".js_spImgIntro").html(file.name).siblings(".js_spImgUrl").html(serverData.Url);

        var ImgName = $(".js_spImgIntro").text();//图片名称
        var ImgUrl = $(".js_spImgUrl").text();//图片地址url



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
                break;
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
