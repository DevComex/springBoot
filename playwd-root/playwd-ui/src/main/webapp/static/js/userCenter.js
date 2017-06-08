/*
﻿ * 作者：胡钧赫
﻿ * 时间：2017-3-10
﻿ * 功能：用户中心
﻿ */
    var obj = {
        noLoginTxt: '<span class="sp_nologin">您尚未登录，请<a href="javascript:;" onclick="alertFn(null,null,true); return false;">登录</a>后再查看！</span>',
        myCollectArticleUrl:"/userCenter/collectlist",//获取用户收藏 文章
        myUploadUrl:"/userCenter/uploadArticle",//获取用户上传 文章
        myEditorMessageUrl : "/userCenter/editorList",//获取用户编辑回复
        myMessageUrl:"/userCenter/myMessageList",//获取用户信息
        cancelCollect:"/userCenter/cancelCollect", //取消收藏图文
        getDefaultRole:"/userCenter/defaultRole",//获取默认角色    
        getRoleServerListUrl: '/upload/serverlist',  //角色区组获取
        getRoleListUrl:'/userCenter/myRole', //获取我的角色列表
        setDefaultRole:'/userCenter/editRole',//设置默认角色
        syncRoleUrl: '/userCenter/instertRole',//同步角色      
        captchaUrl: 'http://wanwd.gyyx.cn/user/Captcha'//验证码
    };
  //未登录
    function nologin(obj){
		obj.html('<h2 style="text-align:left;">您还未登陆，请先<a href="javascript:;" onclick="alertFn(null,null,true); return false;" style="color:red;">登录</a>后再查看<h2>');
	}
$(function(){

    var contWrap = $("#js_CollectContainer");
    var loadHtml = '<img class="search_loadimg" />';
    var substrL = 105;
    //分页容器
    pageHtml = '' +
        '<div class="mt30 s_page_wrap">' +
        '   <span class="gy_page search_page"></span>' +
        '   <span class="search_page_txt"></span>' +
        '</div>';
    
    //封装收藏列表
    function collectList(){
        $("#js_Cont_Article").empty().ajaxPageS({
            url: obj.myCollectArticleUrl,
            pageObj: $(".search_page"),
            pageSize: 3,
            curPageCls: "gy_pageon",
            pageInfo: {
                obj: $(".search_page_txt"),
                content: "第{pageIndex}页  共{allPage}页"
            },
            pageBtn: {
                first: "首页",
                last: "末页",
                prev: "上一页",
                next: "下一页"
            },
            paramObj: {SourcesType:4,r:Math.random()},
            dataFn: function (data) {
    	            var dataHtml = "", dCont = data.dataSet;
    	            if (!data.isSuccess) {
    	            	if(data.message == '您还未登陆'){
    	            		nologin($("#js_Cont_Article"));
    	            	}else{
    	            		alert(data.message);
    	            	}
    	                return false;
    	            }
    	            if (dCont && dCont[0]) {
    	                for (var i = 0; i < dCont.length; i++) {
                            var s_brief = dCont[i].summary.length > substrL ? dCont[i].summary.substr(0, substrL) + '...' : dCont[i].summary;
                            var s_cate = dCont[i].parentTitle === null ? '<a href="javascript:;" title="一级分类">' + dCont[i].parentTitle + '</a>' : '<a href="javascript:;" title="一级分类">' + dCont[i].parentTitle + '</a><a href="javascript:;" title="二级分类">' + dCont[i].childTitle + '</a>';
                            dataHtml += '' +
                                '<div class="graplist">' +
                                '    <h2 class="title">' +
                                '        <a target="_blank" href="/article/view/' + dCont[i].code + '" title="' + dCont[i].title + '">' +
                                '       ' + dCont[i].title + '</a>' +
                                '        <span class="v_grap_f">' +
                                '            <a href="javascript:;" class="v_grap_qx">取消收藏</a>' +
                                '            <a href="javascript:;" class="v_grap_delete js_DeleteThisWork" data-cancelcode="' + dCont[i].code + '"></a>' +
                                '        </span>' +
                                '    </h2>' +
                                '    <div class="graplist_l">' +
                                '        <a target="_blank" href="/article/view/' + dCont[i].code + '" title="' + dCont[i].title + '">' +
                                '        <img src="' + (dCont[i].cover !== null ? dCont[i].cover : loadHtml) + '">' +
                                '</a>' +
                                '    </div>' +
                                '    <div class="graplist_r">' +
                                '        <div class="txt">' +
                                '            ' + s_brief +
                                '            <a target="_blank" class="xx" href="/article/view/' + dCont[i].code + '">[详情]</a>' +
                                '        </div>' +
                                '       <p class="s_art_cate">' + s_cate + '</p>' +
                                '    </div>' +
                                '</div>';
                            }
    	            } else {
    	            	$("#js_CollectContainer").html("<h3 class='no_data_h3'>暂无消息</h3>");
    	                return false;
    	            }
                    $("#js_Cont_Article").html(dataHtml);
    	            return dataHtml;
    	        }
        });
    }
        //用户中心-我的收藏
    if(location.href.indexOf("mycollect") !== -1){
        contWrap.html('<div id="js_Cont_Article"></div>' + pageHtml);
        
        collectList();

    }
    //我的上传页面
    if(location.href.indexOf("myupload") !== -1){
        contWrap.html('<div id="js_Cont_Article"></div>' + pageHtml);
            pageOut("");
        $("#state").change(function(){
            pageOut($(this).val());
        });
        function pageOut(status){
            $("#js_Cont_Article").html(loadHtml).ajaxPageS({
                url: obj.myUploadUrl,
                pageObj: $(".search_page"),
                pageSize: 3,
                curPageCls: "gy_pageon",
                pageInfo: {
                    obj: $(".search_page_txt"),
                    content: "第{pageIndex}页  共{allPage}页"
                },
                pageBtn: {
                    first: "首页",
                    last: "末页",
                    prev: "上一页",
                    next: "下一页"
                },
                paramObj: {State:status,r:Math.random()},
                dataFn: function (data) {
        	            var dataHtml = "", dCont = data.dataSet;
        	            if (!data.isSuccess) {
        	            	if(data.message == '您还未登陆'){
        	            		nologin($("#js_Cont_Article"));
        	            	}else{
        	            		alert(data.message);
        	            	}
        	                return false;
        	            }
        	            if (dCont && dCont[0]) {
                            for (var i = 0; i < dCont.length; i++) {
                                var s_style = dCont[i].status;
                                var s_brief = dCont[i].summary.length > substrL ? dCont[i].summary.substr(0, substrL) + '...' : dCont[i].summary;
                                var s_cate = dCont[i].ParentName === null ? '<a href="javascript:;" title="一级分类">' + dCont[i].firstCategoryName + '</a>' : '<a href="javascript:;" title="一级分类">' + dCont[i].firstCategoryName + '</a><a href="javascript:;" title="二级分类">' + dCont[i].secondCategoryName + '</a>';
                                var a_link_tit, a_link_img, a_link_desc = "";
                                if (dCont[i].status == "审核中" || dCont[i].status == "审核未通过") {
                                    a_link_tit = dCont[i].title;
                                    a_link_img = '<img src="' + (dCont[i].cover ? dCont[i].Picture : loadHtml) + '" />';
                                } else {
                                    a_link_tit = '<a target="_blank" href="/article/view/' + dCont[i].code + '" title="' + dCont[i].title + '">' + dCont[i].title + '</a>';
                                    a_link_img = '' +
                                        '<a target="_blank" href="/article/view/' + dCont[i].code + '" title="' + dCont[i].title + '">' +
                                        '           <img src="' + (dCont[i].cover ? dCont[i].cover : USER_CONFIG.articleDefaultImg) + '" />' +
                                        '</a>';
                                    a_link_desc = '<a target="_blank" class="xx" href="/article/view/' + dCont[i].code + '">[详情]</a>';
                                }

                                dataHtml += '' +
                                    '<div class="graplist">' +
                                    '   <h2 class="title" style="font-size:24px;">' + a_link_tit + '</h2>' +
                                    '   <div class="clear">' +
                                    '   <div class="graplist_l">' + a_link_img +
                                    '       <span class="audit ' +[s_style] + '">' + s_style + '</span>' +
                                    '   </div>' +

                                    '   <div class="graplist_r">' +
                                    '       <div class="txt">' +
                                    '           ' + s_brief + a_link_desc +
                                    '       </div>' +
                                    '       <p class="s_art_cate">' + s_cate + '</p>' +
                                    '   </div>' +
                                    '   </div>' +
                                    '</div>';
                            }
        	            } else {
        	            	$("#js_Cont_Article").html("<h3 class='no_data_h3'>暂无消息</h3>");
        	                return false;
        	            }
                        $("#js_Cont_Article").html(dataHtml);
        	            return dataHtml;
        	        }
            });
        }

    }
    //我的编辑回复
    if(location.href.indexOf("editorMessage") !== -1){
        $("#js_MsgContWrap").html('<ul class="message_list" id="js_editorAnswer"></ul>' + pageHtml);
        $("#js_editorAnswer").empty().ajaxPageS({
            url: obj.myEditorMessageUrl,
            pageObj: $(".search_page"),
            pageSize: 3,
            curPageCls: "gy_pageon",
            pageInfo: {
                obj: $(".search_page_txt"),
                content: "第{pageIndex}页  共{allPage}页"
            },
            pageBtn: {
                first: "首页",
                last: "末页",
                prev: "上一页",
                next: "下一页"
            },
            paramObj: {SourcesType:4,r:Math.random()},
            dataFn: function (data) {
                    var dataHtml = "", dCont = data.dataSet;
                    if (!data.isSuccess) {
                        if(data.message == '您还未登陆'){
                            nologin($("#js_MsgContWrap"));
                        }else{
                            alert(data.message);
                        }
                        return false;
                    }
                    if (dCont && dCont[0]) {
                        for (var i = 0; i < dCont.length; i++) {
                            dataHtml +=
                            '<li>' +
                                '<div class="answerTitle">' +
                                    '<h3>编辑回复：</h3><span>' + dCont[i].createDate + '</span>' +
                                '</div>' +
                                '<div>' + dCont[i].message + '</div>' +
                            '</li>';
                            }
                    } else {
                        $("#js_MsgContWrap").html("<h3 class='no_data_h3'>暂无消息</h3>");
                        return false;
                    }
                    $("#js_Cont_Article").html(dataHtml);
                    return dataHtml;
                }
        });

    }
    //我的消息
    if(location.href.indexOf("myMessage") !== -1){
        $("#js_MsgContWrap").html('<ul class="message_list" id="js_editorAnswer"></ul>' + pageHtml);
        $("#js_editorAnswer").empty().ajaxPageS({
            url: obj.myMessageUrl,
            pageObj: $(".search_page"),
            pageSize: 3,
            curPageCls: "gy_pageon",
            pageInfo: {
                obj: $(".search_page_txt"),
                content: "第{pageIndex}页  共{allPage}页"
            },
            pageBtn: {
                first: "首页",
                last: "末页",
                prev: "上一页",
                next: "下一页"
            },
            paramObj: {SourcesType:4,r:Math.random()},
            dataFn: function (data) {
                    var dataHtml = "", dCont = data.dataSet;
                    if (!data.isSuccess) {
                        if(data.message == '您还未登陆'){
                            nologin($("#js_MsgContWrap"));
                        }else{
                            alert(data.message);
                        }
                        return false;
                    }
                    if (dCont && dCont[0]) {
                        for (var i = 0; i < dCont.length; i++) {
                            var tmpHtml = (dCont[i].url === "") ? "" : '<a target="_blank" href="' + dCont[i].url + '">'+ dCont[i].contentType + '</a>';

                            dataHtml +=
                            '<li>' +
                                '' +
                                    '<div class="message_cont">' +
                                        '<h3><a target="_blank" href="' + dCont[i].url + '"><span class="message_tit">' + dCont[i].contentTitle +'</span></a><span class="mess_time">' + dCont[i].createDate + '</span></h3>' +
                                        '<p>' + dCont[i].message + tmpHtml + '</p>' +
                                    '</div>'+
                            '</li>';
                            }
                    } else {
                        $("#js_MsgContWrap").html("<h3 class='no_data_h3'>暂无消息</h3>");
                        return false;
                    }
                    $("#js_MsgCont").html(dataHtml);
                    return dataHtml;
                }
        });
    }
  
    //切换分类
    $("#js_WorksCate li").click(function(){
        if($(this).hasClass("on")){
            return false;
        }else{
            $(this).addClass("on").siblings().removeClass("on");
        }

    });
    //取消收藏的功能
    $("#js_CollectContainer").on("click",".js_DeleteThisWork",function(){
	    	//console.log($(this).parents(".graplist").find(".title a").text());
    	var code = $(this).data("cancelcode");
    	var cancelBox = $(this).parents(".graplist");
            $.ajax({
                type:"GET",
                url:obj.cancelCollect,
                dataType:"json",
                data:{
                    code:code,
                    r:Math.random()
                },
                success:function(data){
                    if(data.isSuccess){
                        alert(data.message);
                        collectList();
                    }
                }
            });
              
    	
    });
    //验证码
    ChinaCaptcha.prototype.subFn = function () { };
    var chinaCaptcha1 = new ChinaCaptcha($(".js_bindRole"), {
        bid: "jnajq"
    });
    console.log(chinaCaptcha1);
    chinaCaptcha1.init();
    //调取公用的登录
    
    $.ajax({
        url: "http://reg.gyyx.cn/Login/Status",
        type: "GET",
        dataType: "jsonp",
        jsonp: "jsoncallback",
        data: { r: Math.random() },
        success: function (d) {
            if (d.IsLogin) {
            	getDefaultRole();
            }
            else {
            	nologin($("#js_RoleList"));
            	$("#js_HeadPortrait img").attr("src",'/static/images/default_rolepic.jpg');
            	$("#js_RoleName").html("");
            	
            }
        }
    });
    //获取默认角色
    function getDefaultRole(){
    	$.ajax({
            type:"GET",
            url:obj.getDefaultRole,
            dataType:"json",
            data:{
                r:Math.random()
            },
            success:function(data){
                if(data.isSuccess){
                	if(data.data!=null){
                		if(data.data.picture!=""){
                        	$("#js_HeadPortrait img").attr("src",data.data.picture);
                        }else{
                        	$("#js_HeadPortrait img").attr("src","/static/images/default_rolepic.jpg");
                        }
                		$("#js_RoleName").html(data.data.nickName);
                	}else{
                		$("#js_HeadPortrait img").attr("src","/static/images/default_rolepic.jpg");
                		// 
                		$("#js_RoleName").html("未有同步角色");
                        //点击重新同步按钮
                        $("#js_SynchroBtn").click(function () {
                            //显示表单
                            setSyncForm();
                            return false;
                        });
                	}                                
                }else{
                	$("#js_HeadPortrait img").attr("src","/static/images/default_rolepic.jpg");
                }
            }
        });
    }
    
    
   //获取我的角色列表
    function getRoleList (){
    	$.ajax({
    		url: obj.getRoleListUrl,
            type: "GET",
            dataType: "JSON",
            data: {
                r: Math.random()
            },
            success:function(data){
            	if (data.isSuccess) {
                    var
                        dCont = data.data,
                        divHtml = "";
                    if (dCont && dCont[0]) {
                        for (var i = 0; i < dCont.length; i++) {
                            var
                                //是否默认角色
                                isDefault = dCont[i].isDefault,
                                //默与不默认标志
                                defBtnHtml = isDefault ?
                                    ' class="js_qxbut role_defaultbtn">当前默认' :
                                    ' class="js_upbut js_SetDefaultRole" data-code="' + dCont[i].code + '">设为默认';

                            divHtml += '' +
                                '<div class="wd_js_l">' +
                                '    <span>' +
                                '        <img src="' + dCont[i].picture + '"style="width: 100%;"></span>' +
                                '    <p>角色：' + dCont[i].nickName + '</p>' +
                                '    <p>服务器：' + dCont[i].serverName + '</p>' +
                                '    <p class="rolebox_btn js_but">' +
                                '       <a href="javascript:;"' + defBtnHtml + '</a>' +
                                '    </p>' +
                                '</div>';
                        }
                        //如果有角色就追加重新同步按钮
                        divHtml += '<div class="wd_js_c"><a href="javascript:;" class="js_SynchroBtn">重新同步</a></div>';

                        $("#js_RoleList").html(divHtml);

                        //点击重新同步按钮
                      $(".js_SynchroBtn").click(function () {
                           setSyncForm();
                            return false;
                        });
                      
                        
                        //设置默认角色
                        $(".js_SetDefaultRole").click(function () {
                            var code = $(this).attr("data-code");
                            setDefaultRole(code);
                            return false;
                        });
                    }
                }
            }
    	})
    }
    getRoleList();
    function setDefaultRole(code) {
        $.ajax({
            url: obj.setDefaultRole,
            type: "GET",
            dataType: "JSON",
            data: {
                r: Math.random(),
                code: code
            },
            success: function (data) {
                if (data.isSuccess) {
                    alert("设置成功！");
                    refreshPage();
                } else {
                	refreshPage();
                    alert(":( 设置失败，请刷新页面重试！");
                }

            }
        });
    }
  //刷新页面防末尾"#"不刷新
     function refreshPage() {
        var href = window.location.href;
        href = href.replace(/#*$/, '');
        window.location.href = href;
    };
    //配置同步角色表单
    function setSyncForm() {
        var
            $netSel = $("#js_Net"),
            $serverSel = $("#js_Server"),
            $qq = $("#js_QQ").val("");

        //刷新验证码
        /*$(".Captcha").attr("src", USER_CONFIG.captchaUrl + "?r=" + Math.random());
        $(".Captcha").click(function () {
            $(".Captcha").attr("src", USER_CONFIG.captchaUrl + "?r=" + Math.random());
        });*/
        //显示表单
        $("#js_RoleList").append($("#js_SyncForm").show());
        //绑定服务器
        $netSel.bindServer({
            url: obj.getRoleServerListUrl,
            $bindObj: $serverSel
        });
        //取消重新同步
        $("#js_CancelSync").click(function () {
            $("#js_SyncForm").hide();
            return false;
        });
        //QQ输入限制
        $("#js_QQ")[0].oninput = function () {
            this.value = this.value.replace(/\D/g, "");
        }
        if (window.attachEvent) {
            $qq[0].attachEvent("onpropertychange", function (e) {
                var _this = e.srcElement, oldVal = _this.value;
                if (e.propertyName == "value" && /\D/.test(oldVal)) {
                    _this.value = oldVal.replace(/\D/, '');
                }
            });
        }

        //同步角色
        $("#js_SyncRole").unbind("click").bind("click", function () {
            var validData = $().validSyncForm();
            var able = $(this).attr("data-able");
            if (validData) {
            	 var subFn = function () {
             		
                 $().syncRole(validData,$(".js_insertCaptcha").val());
            	 };
            	chinaCaptcha1.subFn = subFn;
                console.log(chinaCaptcha1.subFn);
                chinaCaptcha1.createCaptchaPop();
               
                
            }
            return false;
        });
    }
    $.fn.extend({
    	bindServer: function (options) {
            var
                $this = $(this),
                defaults = {
                    url: "",
                    $bindObj: $("#ServerId")
                };

            $.extend(defaults, options);

            $this.val("").unbind("change").bind("change", function () {
                var
                    thisVal = this.value,
                    optHtml = '<option value="">&nbsp;请选择服务器</option>';

                $(this).changeHideError($("#js_Error"), "", "请选择区组！");

                if (!thisVal) {
                    defaults.$bindObj.html(optHtml);
                    return false;
                }
                defaults.$bindObj.html('<option value="">加载中...</option>');
                $.ajax({
                    url: defaults.url,
                    type: "GET",
                    dataType: "JSON",
                    data: {
                        netType: thisVal,
                        r: Math.random()
                    },
                    success: function (data) {
                        if (data.isSuccess) {
                            var dCont = data.data.data;
                            for (var i = 0; l = dCont.length, i < l; i++) {
                                optHtml += "<option value='" + dCont[i].Code + "'>&nbsp;" + dCont[i].ServerName + "</option>"
                            }
                            defaults.$bindObj.html(optHtml).unbind("change").bind("change", function () {
                                $(this).changeHideError($("#js_Error"), "", "请选择服务器！");
                            });
                        } else {
                            defaults.$bindObj.html('<option value="">:(服务器列表加载失败</option>');
                            alert(data.Message);
                        }
                    }
                });
            });
        },
        changeHideError: function ($error, defaultVal, errorTxt) {
            var $this = $(this)
            if ($this.val() != defaultVal && $error.html() == errorTxt) {
                $error.hide();
            }
        },
        //验证同步角色列表
        validSyncForm: function () {
            var
                netId = $("#js_Net").val(),
                $serverSel = $("#js_Server"),
                serverId = $serverSel.val(),
                $serverOpt = $serverSel.find("option:selected"),
                serverName = $.trim($serverOpt.text()),
                qq = $.trim($("#js_QQ").val()),
                $error = $("#js_Error");
            if (!netId) {
                $error.html("请选择区组！").show();
                return false;
            } else if (!serverId || !serverName) {
                $error.html("请选择服务器！").show();
                return false;
            } else if (qq && !/^\d{5,15}$/g.test(qq)) {
                $error.html("QQ格式不正确！").show();
                return false;
            }
            else {
                return {
                	serverName : serverName,
                    serverId : serverId,
                    netId : netId,
                    qq: qq,
                };
            }
        },
      //同步角色
        syncRole: function (sdata,captcha) {
        	console.log(sdata,captcha);
            var ddata = {
                r: Math.random()
            }
            var captcha1={ validCode: captcha }
            $.extend(ddata, sdata,captcha1); 
            console.log(ddata);
            $.ajax({
                url: obj.syncRoleUrl,
                type: "POST",
                dataType: "JSON",
                data: ddata,
                success: function (data) {
                	console.log(data)
                    if (data.isSuccess) {
                            alert("同步成功！");
                            refreshPage();
                        
                    } else {
                    	alert(data.message);
                    	if (data.message == "验证码不正确") {
                    		
                            $(".js_refreshChinaCaptcha").click();
                        }else{
                        	$(".js_chinaCaptcha_Alert").remove();s
                        }
                    	
                        /*if (data.Values == "-3") {
                            $().refreshPage();
                        } else if (data.Message == "1小时内同步次数过多,请重新登录") {
                            $().loginout();
                        } else if (data.Message == "验证码不正确") {
                            $(".js_refreshChinaCaptcha").click();
                        } else {
                        }*/
                    }
                    $(".Captcha").attr("src", obj.captchaUrl + "?r=" + Math.random());
                    //$("#js_SyncRole").attr("data-able", true);
                    $(".js_insertCaptcha").val("");
                },
                error: function (req, stau) {
                    //$("#js_SyncRole").attr("data-able", true);
                    if (stau = "timeout") {
                        alert(" :(请求接口超时，请重试！");
                        return false;
                    }
                    alert(" :(程序接口出错，请刷新页面重试！");
                }
            });
        }
    })
    

});
