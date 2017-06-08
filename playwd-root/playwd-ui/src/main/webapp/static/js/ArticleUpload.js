/*-------------------------------------------------------------------------
 * 作者：骆崇飞
 * 邮箱：luochongfei@gyyx.cn
 * 创建时间： 2014/12
 * 版本号：v1.0
 * 作用域：问道玩家天地文章上传
 -------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------
 * update  by chenqiaoling  2015/06/17
 * 作用域：文章上传添加角色名表单项，页面加载即显示默认角色，更改区服可重新选择角色
 -------------------------------------------------------------------------*/
var isUpT=0;
var isUpA=0;
var isUpAll=0;
var isUpI=0;
var ARTICLE_CONFIG = {
    //上传封面图片处理程序
    uploadImgUrl: "http://up.gyyx.cn/Image/WebSiteSaveToReal.ashx",
    //获取服务器列表
    serverUrl: "/upload/serverlist",
    defaultRoleUrl: "/upload/userInfo",   // 2015/06/17
    getRoleList: "/User/GetSyncRole", // 2015/06/17
    //获取分类
    firstCateUrl: "/category/getCategory",
    //文章上传
    uploadArticle: "/upload/instertArticle",
    //默认封面
    defaultCoverPic: rc + "/static/images/testimg01.jpg",
    //图片裁切
    cutImgUrl:"/imageUpPost"
};
$.fn.extend({
    //上传页面加载
    uploadFn: function () {
        //表单对象
        ARTICLE_CONFIG.$Form = $("#js_FormArticle");
        //初始化表单
        $(document).resetForm();
        //初始化编辑器
        $(document).editorFn();
        //封面图片上传
        $(document).coverPic();
        //绑定服务器
        $("#netId").bindServer();
        //绑定分类
        $("#PrimaryCategoryId").bindCate({
            $bindObj: $("#categoryId")
        });
        //提示
        $(window).tipsShow();
        //提交表单
        $(document).submitForm();
        //判断登录
        $(document).isLogin();
        $(".loginAlertBox").click(function(){
            alertFn(null, null, true);
            $("#Account").focus();

        });
        $(window).titleCheck();
    },
    //编辑器
    editorFn: function () {
        //获取编辑器对象
        var editor = UE.getEditor('editor');
        var $hidCont = $("#validContent");
        //初始设置编辑器内容为空
        editor.ready(function () {
            $hidCont.val("");
            editor.setContent("");

            //手动重置表单
            $("#js_CancelUpload").click(function () {
                $(document).resetForm(true);
                return false;
            });
        });
        //验证编辑器内容是否为空
        editor.addListener("contentChange", function () {
            var editorNext = $("#editor").next();
            if (editorNext.length) {
                if (editor.getContent()) {
                    editorNext.hide();
                } else {
                    editorNext.show();
                }
            }
        });
    },
    //封面图片
    coverPic: function (options) {
    	//初始化剪切弹层
    	$("#cupImgPop").popw();
    	
        var defaults = {
            //基础参数配置
            upload_url: ARTICLE_CONFIG.uploadImgUrl,
            flash_url: "http://up.gyyx.cn/flash/swfupload_f9.swf",
            post_params: {
                group: "wd1",
                width: 200,
                height: 200
            },
            custom_settings: {
            },

            //上传限制设置
            file_size_limit: "2048",
            file_types: "*.jpg;*.png;*.bmp;*.gif",
            file_types_description: "JPG/PNG/BMP/GIF 格式图片",
            file_upload_limit: 0,
            file_queue_limit: 0,

            file_queue_error_handler: fileQueueError,
            file_dialog_complete_handler: fileDialogComplete,
            upload_progress_handler: uploadProgress,
            upload_error_handler: uploadError,
            upload_success_handler: uploadSuccess,
            upload_complete_handler: uploadComplete,

            //按钮设置
            button_image_url: rc + "/static/images/XPButtonNoText_106x30.gif",
            button_placeholder_id: "btnUpload",
            button_width: 106,
            button_height: 30,
            button_text: '<span class="button"></span>',
            button_text_style: '.button {}',
            button_text_top_padding: 1,
            button_text_left_padding: 5,

            //调试模式
            debug: false
        };

        $.extend(defaults, options);
        var swfu = new SWFUpload(defaults);
        //显示剪切弹层
        $(".js_popCutImgBtn").click(function(){
        	//弹层初始化--开始
        	$("#imgs,#imgsP").removeAttr("src");
        	$("#imgsP").removeAttr("style");
        	$("#imgs").removeAttr("width");
        	$("#imgs").removeAttr("height");
        	$("#imgs").removeAttr("data-width");
        	$("#imgs").removeAttr("data-height");

        	$(".imgareaselect-outer,.imgareaselect-selection,.imgareaselect-border1,.imgareaselect-border2,.imgareaselect-border3,.imgareaselect-border4,.imgareaselect-handle").hide();
        	$(".js_cutSubmit").removeData();  //裁切按钮数据
        	//弹层初始化--结束
        	$("#cupImgPop").popw("open");
        });
        //确定剪切按钮
        $(".js_cutSubmit").click(function(){
        	var _this = $(this);
        	var datas = _this.data();
        	
        	if(checkObjEmpt(_this.data())){
        		alert("亲，先选个封面呗！");
        		return false;
        	}else{
        		datas = $.extend(datas,{r:Math.random()});
        		$.ajax({
            		url:ARTICLE_CONFIG.cutImgUrl,
            		type:"post",
            		dataType:"json",
            		data:datas,
            		success:function(d){
            			if(d.isSuccess){
            		        $(".titleimgs_add").attr("src", d.data);
            		        $("#hid_addthumbImgUrl").val(d.data);
            		        $("#cupImgPop").popw("close");
            		        $("#hid_addImgUrl").val(d.data).siblings(".error").remove(); 
            		        
            			}else{
            				alert("程序妹子打盹儿啦，再试一下吧！");
            			}
            		}
            	});
        	}
        	
        });
        function checkObjEmpt(obj){  //检查对象是否为空
        	for(var i in obj){
        		return false;
        	}
        	return true;
        }
    },
    //绑定服务器
    bindServer: function (options) {
        var
            $this = $(this),
            defaults = {
                url: ARTICLE_CONFIG.serverUrl,
                defaultRoleUrl: ARTICLE_CONFIG.defaultRoleUrl,   // 2015/06/17
                getRoleList: ARTICLE_CONFIG.getRoleList,  // 2015/06/17
                $bindObj: $("#serverId")
            },
            netId ="" , serverId = "",role = "";

        $.extend(defaults, options);

        //获取默认角色  2015/06/17
        $.ajax({
            url: defaults.defaultRoleUrl,
            type: "get",
            dataType: "JSON",
            data: {
                r: Math.random()
            },
            success: function (d) {
                if (d.isSuccess) {
                    if(d.data !== null){
                        role = d.data.author;
                        $("#author").val(role).attr("disabled",true);

                    }

                }
                $this.val("").unbind("change").bind("change", function () {

                    var
                        thisVal = this.value,
                        optHtml = '<option value="">请选择服务器</option>';
                    if (!thisVal) {
                        defaults.$bindObj.html(optHtml);
                        return false;
                    }
                    defaults.$bindObj.html('<option value="">加载中...</option>');
                    $.ajax({
                        url: defaults.url,
                        type: "POST",
                        dataType: "JSON",
                        data: {
                            netType : thisVal,
                            gameId: 2,
                            r: Math.random()
                        },
                        success: function (data) {
                            if (data.isSuccess === true) {
                                var dCont = data.data.data;
                                for (var i = 0; l = dCont.length, i < l; i++) {
                                    optHtml += "<option value='" + dCont[i].Code + "'>" + dCont[i].ServerName + "</option>";
                                }

                                defaults.$bindObj.html(optHtml).unbind("change").bind("change", function () {
                                    var thisServerVal = $(this).find("option:selected").val();
                                    var thisTxt = $(this).find("option:selected").text();
                                    $("#ServerName").val(thisTxt);
                                    $("#AuthorName").val("");

                                    //获取角色列表 2015/06/17
                                    //$(document).getRoleList({ serverId: thisServerVal });

                                });
                            } else {
                                defaults.$bindObj.html('<option value="">:(服务器列表加载失败</option>');
                                alert(data.message);
                            }
                        }
                    });
                });
            }
        });




    },
    //获取角色列表   2015/06/17
    getRoleList: function (options) {
        var
            $this = $(this),
            defaults = {
                getRoleList: ARTICLE_CONFIG.getRoleList
            };
        $.extend(defaults, options);
        $.ajax({
            url: defaults.getRoleList,
            type: "GET",
            dataType: "JSON",
            data: {
                serverId: defaults.serverId,
                r: Math.random()
            },
            success: function (d) {
                if (d.IsSuccess) {
                    if (d.List.length > 0) {
                        var str = "";
                        for (var i = 0; i < d.List.length; i++) {
                            if (d.List[i].IsDefault) {
                                $("#AuthorName").val(d.List[i].NickName);
                            }
                            str += '<div class="wd_js_l">'+
                                '<span><img src="/Content/images/portrait/' + d.List[i].Picture + '.jpg"></span>' +
                                '<p>角色：' + d.List[i].NickName + '</p>' +
                                '<p>服务器：' + d.List[i].ServerName + '</p>' +
                                '<p class="rolebox_btn js_but"><a href="javascript:;" class="js_upbut js_SetRole" data-NickName="' + d.List[i].NickName + '">选择</a></p>' +
                                '</div>';
                        }
                        $("#popRoleList .pop_role_content").empty().html(str).show();
                        $(document).alertRoleBoxFn(defaults.serverId);

                    } else {

                    }
                } else {
                    $("#popRoleList .pop_role_content").empty().hide();
                    if (d.Values == -3) {
                        alertFn(null, null, true);
                        $("#Account").focus();
                    } else {
                        //$("#popRoleList .pop_role_txt a.torole").html(d.Message);
                        $(document).alertRoleBoxFn(defaults.serverId);
                    }

                }
            }
        });
    },
    //绑定分类
    bindCate: function (options) {
        var
            $this = $(this),
            defaults = {
                firstUrl: ARTICLE_CONFIG.firstCateUrl,
                $bindObj: ""
            };
        $.extend(defaults, options);

        $.ajax({
            url: defaults.firstUrl,
            type: "GET",
            dataType: "JSON",
            data: {
                categoryType :"parent",
                r: Math.random()
            },
            success: function (data) {
                if (data.isSuccess) {
                    var dCont = data.data;
                    var optHtml1 = '<option value="">请选择一级分类</option>';
                    for (var i = 0; l = dCont.length, i < l; i++) {
                        optHtml1 += "<option value='" + dCont[i].code + "'>" + dCont[i].title + "</option>";
                    }
                    $this.html(optHtml1).unbind("change").bind("change", function () {
                        var thisVal = this.value;
                        var opHtml2 = '<option value="">请选择二级分类</option>';
                        if (!thisVal) {
                            defaults.$bindObj.html(opHtml2);
                            return false;
                        }
                        $.ajax({
                            url: defaults.firstUrl,
                            type: "GET",
                            dataType: "JSON",
                            data: {
                                categoryType : thisVal,
                                r: Math.random()
                            },
                            success: function (data) {
                                if (data.isSuccess) {
                                    var dCont = data.data;
                                    if (dCont.length > 0) {
                                        for (var i = 0; l = dCont.length, i < l; i++) {
                                            opHtml2 += "<option value='" + dCont[i].code + "'>" + dCont[i].title + "</option>";
                                        }
                                        defaults.$bindObj.html(opHtml2);
                                    } else {
                                        defaults.$bindObj.html('<option value="0">暂无二级分类</option>');
                                    }


                                } else {
                                    data.message && alert(data.message)
                                }
                            }
                        });

                    });

                } else {
                    data.message && alert(data.message)
                }
            }
        });
    },
    //表单验证
    formValid: function () {

        $(this).validate({
            errorElement: "span",
            onblur: true,
            debug: true,
            ignore: "",
            errorPlacement: function (error, element) {
                error.appendTo(element.parent());
                error.addClass("errortips");
            },
            rules: {
                title: {
                    required: true,
                    maxlength: 20
                },
                validContent: {
                    required: true
                },
                summary: {
                    required: true,
                    maxlength: 200
                },
                author: {
                    required: true,
                    maxlength: 20
                },
                keywords: {
                    required: true,
                    maxlength: 20
                },
                serverId : {
                    required: true
                },
                categoryId: {
                    required: true,
                    min: 0
                },
                cover:{
                    required: true
                }
            },
            messages: {
                title: {
                    required: "请填写标题",
                    maxlength: "标题不能超过50字"
                },
                validContent: {
                    required: "请填写内容"
                },
                summary: {
                    required: "请填写描述",
                    maxlength: "描述不能超过200字"
                },
                author: {
                    required: "请填写角色名",
                    maxlength: "角色名不能超16字"
                },
                keywords: {
                    required: "请填写关键词",
                    maxlength: "关键字不能超过20字"
                },
                serverId : {
                    required: "请选择服务器"
                },
                categoryId: {
                    required: "请选择分类",
                    min: "请选择分类"
                },
                cover:{
                    required : "请上传封面"
                }
            }
        });
        return $(this).valid();
    },
    //上传文章
    uploadArticleFn: function () {
        var
            $this = $(this),
        //传送参数字符串容器
            sdata = "",
        //获取编辑器内容
            eCont = UE.getEditor('editor').getContent(),
        //编码内容
            eCont_html = encodeURI(eCont.replace(/&#39;/g, "’")),
        //表单对象集合
            ipts = $this.find("input,select,textarea").not("[name='editorValue']").not("[name='validContent']"),
        //表单元素数量
            n = ipts.length,
        //弹出层
            $error = $("#popError"),
        //弹出层icon
            $iconP = $error.children().eq(0),
        //弹出层文本
            $txtP = $error.find(".errorMsg"),
        //处理弹层显示内容
            handResult = function (msg, icon, refresh) {
                $iconP.removeClass().addClass("icon icon0" + icon);
                $txtP.html(msg);
                $(document).alertBoxFn((refresh ? refresh : ""));
            };

        for (var i = 0; i < n; i++) {
            var _name = $(ipts[i]).attr("name");
            var _value = $(ipts[i]).val();
            sdata += (i != n - 1 ? "\"" + _name + "\":\"" + _value.replace(/"/g, "”").replace(/\\/g, "╲").replace(/&/g, "＆") + "\"," : " \"" + _name + "\":\"" + _value.replace(/"/g, "”").replace(/\\/g, "╲").replace(/&/g, "＆") + "\"");
        }
        sdata += ",\"r\":" + Math.random();
        sdata += ", \"content\": \"" + eCont_html + "\"";
        sdata += ",\"AuthorType\":" + 1;
        sdata = $.parseJSON("{" + sdata + "}");
        sdata.serverName  = $("#serverId").find("option:selected").text();
        $.ajax({
            url: ARTICLE_CONFIG.uploadArticle,
            type: "POST",
            dataType: "JSON",
            data: sdata,
            success: function (data) {
                if (data.isSuccess) {
                    handResult(data.isSuccess, "1", "refresh");
                } else {
                    if (data.message == "请先登录") {
                        handResult("您账号已掉线，请自行保存好内容，登录后重试！", "2");
                    } else {
                        handResult(data.message, "2");
                    }

                }
            },
            error: function () {
                handResult(":( 程序出错了", "2");
            }
        });
    },
    //提交表单
    submitForm: function () {
        $("#js_UploadArticle").click(function () {
            var
                oForm = ARTICLE_CONFIG.$Form,
                eCont = UE.getEditor('editor').getContent(),
                eCont_html = encodeURI(eCont);
            $("#validContent").val(eCont_html);
            if (oForm.formValid()) {
                isUpAll=1;
            }
            if($(".input_w[name=title]").val()=="请输入20字以内的标题~"||$(".input_w[name=title]").val()==""){
                $(".input_w[name=title]").nextAll().remove();
                $(".input_w[name=title]").after('<span for="title" generated="true" class="error errortips">请填写标题</span>');
            }else{
                isUpT=1;
            }
            if($(".input_w[name=keywords]").val()=="多个关键词请用“,”隔开~每个关键词5个字哟~"){
                $(".input_w[name=keywords]").nextAll().remove();
                $(".input_w[name=keywords]").after('<span for="keywords" generated="true" class="error errortips">请填写关键词</span>');
            }else{
                isUpA=1;
            }
            if($(".input_w[name=author]").val()=="请输入20字以内的角色名~"){
                $(".input_w[name=author]").nextAll().remove();
                $(".input_w[name=author]").after('<span for="author" generated="true" class="error errortips">请填写角色名</span>');
            }else{
                isUpI=1;
            }
            if(!isUpT||!isUpA||!isUpAll||!isUpI){
                return false;
            }else{
                oForm.uploadArticleFn();
            }
        });
    },
    //弹层
    alertBoxFn: function (refresh) {
        var bodyWidth, bodyHeight, dialogLeft, dialogTop;
        var alertFn = function () { /*使弹出改变窗口大小时居中显示*/
            bodyWidth = $(window).width();
            bodyHeight = $(window).height();
            dialogLeft = (bodyWidth - $("#popError").width()) / 2;
            dialogTop = (($(window).height() - $("#popError").height()) / 2 + $(document).scrollTop());
            $("#popError").css({ "left": dialogLeft, "top": dialogTop });
            $("#Mark").css({ "width": bodyWidth, "height": bodyHeight + $(document).scrollTop() });
        };
        var showalertFn = function () {
            $("#popError").show();
            $("#Mark").show();
        };
        var hidealertFn = function () {
            $("#popError").hide();
            $("#Mark").hide();
            if (refresh == "refresh") {
                $(document).refreshPage();
            }
        };
        $("#Mark").on("click", function () {
            hidealertFn();
        });
        alertFn();
        showalertFn();
        setTimeout(hidealertFn, 3000);
        $(window).resize(function () { alertFn(); });
        $(window).scroll(function () { alertFn(); });
    },
    //角色列表弹层  2015/06/17

    //刷新页面防末尾"#"不刷新
    refreshPage: function () {
        var href = window.location.href;
        href = href.replace(/#*$/, '');
        window.location.href = href;
    },
    //html编码
    htmlEncode: function (s) {
        var div = document.createElement('div');
        div.appendChild(document.createTextNode(s));
        return div.innerHTML;
    },
    //重置表单
    resetForm: function (nAuto) {
        $("#addImg").attr("src", ARTICLE_CONFIG.defaultCoverPic);
        $("#ServerId").html('<option value="">请选择服务器</option>');
        $("#PrimaryCategoryId").val("");
        $("#summary").val("");
        if (nAuto) {
            var editor = UE.getEditor('editor');
            editor.setContent("");
        }
        $("input[name='title']").val("请输入20字以内的标题~").css("color","#ccc");
        if($("#author").attr("disabled") !== "disabled"){
            $("#author").val("请输入20字以内的角色名~").css("color","#ccc");
        }
        $("input[name='keywords']").val("多个关键词请用“,”隔开~每个关键词5个字哟~").css("color","#ccc");
    },
    //判断是否登录并弹出登录层
    isLogin: function () {
        $.ajax({
            url: "http://reg.gyyx.cn/Login/Status",
            type: "GET",
            dataType: "jsonp",
            jsonp: "jsoncallback",
            success: function (data) {
                if (!data.IsLogin) {
                    alertFn(null, null, true);
                    $("#Account").focus();
                }else{
                    $(".loginAlertBox").remove();
                }
            }
        });
    },
    titleCheck:function(){
        $("input[name = 'title']").blur(function(){
            $("input[name = 'title']").nextAll().remove();
            if($("input[name = 'title']").val()==""){
                $("input[name = 'title']").after('<span class="error errortips">请填写标题</span>');
            }
            if($("input[name = 'title']").val() !== ""){
                $.get("/upload/checktitle",{title : $(this).val(), r: Math.random()},function(d){
                    if(d.isSuccess === false){
                        $("input[name = 'title']").next().remove();
                        $("input[name = 'title']").after('<span class="error errortips">标题已重复，请修改</span>');
                        $("#js_UploadArticle").attr("disabled",true);
                    }else{
                        $("#js_UploadArticle").attr("disabled",false);
                    }
                });
            }
        });
        $("input[name = 'keywords']").blur(function(){
            $("input[name = 'keywords']").nextAll().remove();
            if($("input[name = 'keywords']").val()==""){
                $("input[name = 'keywords']").after('<span class="error errortips">请填写关键词</span>');
            }
        })
        $("input[name = 'author']").blur(function(){
            $("input[name = 'author']").nextAll().remove();
            if($("input[name = 'author']").val()==""){
                $("input[name = 'author']").after('<span class="error errortips">请填写角色名</span>');
            }
        })
    },
    tipsShow:function(){
        $("input[name='title']").focus(function(){
            $(this).val(($(this).val() === "请输入20字以内的标题~")? "" : $(this).val());
            $(this).css("color","#000");
        });
        $("input[name='author']").focus(function(){
            $(this).val(($(this).val() === "请输入20字以内的角色名~")? "" : $(this).val());
            $(this).css("color","#000");
        });
        $("input[name='keywords']").focus(function(){
            $(this).val(($(this).val() === "多个关键词请用“,”隔开~每个关键词5个字哟~")? "" : $(this).val());
            $(this).css("color","#000");
        });
    }


});