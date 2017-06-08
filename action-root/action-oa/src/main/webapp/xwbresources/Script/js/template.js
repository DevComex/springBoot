
var loadfinish = false, istop = false, isleft = false;
var isApp = false;

var loadPopstr = '<div id="loadPop" style="position: absolute;top: 50%;left: 50%;margin:-50px 0 0 -150px;width:300px;height:100px;z-index:1001;color:#000;background:#efefef;padding:40px 0 0 0;text-align:center;">正在加载，请等待！</div><div id="htmlWrap" style="position:absolute;top:0;left:0;background:#000;opacity:0.3;filter:alpha(opacity=30);z-index:1000;width:3000px;height:1000px"></div>';
function loadPop() {
    if ($("body").length > 0) {
        if ($("#loadPop").length == 0) {
            $("body").append(loadPopstr);
        }
    }
}

var loadPopstrTime = window.setInterval(loadPop,50);

$(function () {	
    /*加载等待*/
    $(window).load(function () {
        window.clearInterval(loadPopstrTime);
        $("#htmlWrap").remove();
        $("#loadPop").remove();
    });

    $(".js_template_top").html(tetopstr);
    $(".js_template_left").html(teleftstr);
    $(".stretch").append(terightstr);
    $("body").append(tepasswordstr);
    
    $.ajax({
        url: "http://oa.gyyx.cn/Home/GetViewData?jsoncallback=?",
        type: "GET",
        data: { r: function () { return Math.random(); } },
        dataType: "json",
        success: function (data) {
            if (data.Ret == 0) {
                isApp = true;
                /*左侧应用异步start*/
                $("#AppLeft").html("");
                var appleftstr = "";
                $.each(data.Data.AppLeft, function (i, item) {
                    var oli = '<li code="' + item.Id + '">\
                                        <a href="#' + item.Name + '"  class="navon">\
                                            <i class="fa fa-desktop icon">\
                                                <b class="bg-success"></b>\
                                            </i>\
                                            <span>' + item.Name + '</span>\
                                        </a>\
                                        <ul class="subNav nav lt"></ul>\
                                    </li>';
                    appleftstr += oli;
                });
                $("#AppLeft").html(appleftstr);
                /*左侧应用异步end*/
                if (isApp) {
                    function setCookie(name, value) { /*设置cookie*/
                        var str = name + '=' + value + ";path=/;" + ";domain=oa.gyyx.cn";
                        document.cookie = str;
                    }
                    function getCookie(name) { /*获取cookie*/
                        var arr = document.cookie.split('; ');
                        var i = 0;
                        for (i = 0; i < arr.length; i++) {
                            var arr1 = arr[i].split('=');
                            if (arr1[0] == name) {
                                return arr1[1];
                            }
                        }
                        return '';
                    }
                    $("#AppLeft > li").bind(function () {
                        setCookie("appcode", $(this).attr("code"));
                        $(this).menuFn({ ajaxUrl: "http://oa.gyyx.cn/OaApplication/GetApplication?jsoncallback=?" });                        
                    });
                    if (getCookie("appcode") == "") {
                        $("#AppLeft li").eq(0).menuFn({ ajaxUrl: "http://oa.gyyx.cn/OaApplication/GetApplication?jsoncallback=?" });
                    } else {
                        $("#AppLeft li[code=" + getCookie("appcode") + "]").menuFn({ ajaxUrl: "http://oa.gyyx.cn/OaApplication/GetApplication?jsoncallback=?" });
                    }
                    //处理左侧导展开时点击加载，收缩时划过加载事件
                    var loadfinish = false;
                    $(".navon").click(function () {
                        //判断如果已经加载了菜单就不重复加载
                        var hclass = $(this).parent().find("li").hasClass("active");
                        if (!hclass) {
                            $(this).parent("li").unbind().bind().menuFn({ ajaxUrl: "http://oa.gyyx.cn/OaApplication/GetApplication?jsoncallback=?" });
                        }
                    });
                    var clkbtn = false;
                    $(".navvs").click(function () {
                        if (clkbtn == false) {
                            $(".navon").bind({
                                mouseenter: function (e) { $(this).trigger("click"); }, mouseleave: function (e) { }
                            });
                        } else {
                            $(".navon").unbind().click(function () {
                                $(this).parent("li").menuFn({ ajaxUrl: "http://oa.gyyx.cn/OaApplication/GetApplication?jsoncallback=?" });
                            });
                        }
                        clkbtn = !clkbtn;
                    });
                }
            } else {
                //alert(111111);
            }
        },
        error: function () {
            alert("error");
        }
    });
    
    /*右上角UCenter系统列表下拉菜单  by ligen 2014.5.07  start*/
    /* 创建系统权限加载进度指示条 */
        var CreateActionLine = function (id) {
            var sysName = $("#" + id).text();
            $('<div id="progressBox" class="widget">' +
            '    <div class="fileProcess">' +
            '        <img src="http://oa.gyyx.cn/Contents/images/10s.gif" alt="" class="loader">' +
            '        <strong>正在载入' + sysName + '</strong>' +
            '    </div>' +
            '    <div class="clear"></div>' +
            '    </div>' +
            '    <div class="markdivbox"></div>').appendTo(document.body);

            var thisleft = $(window).width() / 2 - $("#progressBox").width() / 2;
            var thistop = $(window).height() / 2 - $("#progressBox").height() / 2 + $(window).scrollTop();
            $("#progressBox").css({ "left": thisleft, "top": thistop });

            window.onscroll = function () {
                var thistop = $(window).height() / 2 - $("#progressBox").height() / 2 + $(window).scrollTop();
                $("#progressBox").css({ 'left': thisleft });
            }
        }
        var removeActionLine = function () {
            $(".markdivbox").remove();
            $("#progressBox").remove();
        }

    /*加载系统*/
        var loadSys = function (id, href) {
            CreateActionLine(id);
            $.ajax({
                url: 'http://oa.gyyx.cn/externalsystem/getguidaddr/' + parseInt(id) + "?jsoncallback=?",
                type: 'GET',
                dataType: "json",
                async: false,
                timeout: 50000,
                data: {
                    r: function () { return Math.random(); }
                },
                error: function () {
                    alert("请求失败，请重新操作");
                    removeActionLine();
                },
                success: function (d) {
                    if (d.Ret == 0) {
                        var sysurl = d.Data;
                        $.ajax({
                            url: sysurl + "&callback=?",
                            type: 'GET',
                            dataType: "json",
                            async: false,
                            timeout: 50000,
                            data: {
                                r: function () { return Math.random(); }
                            },
                            success: function (d) {
                                if (d.isSuccess) {
                                    setTimeout(function () {
                                        //window.location.href = href;
                                        window.open(href);
                                        removeActionLine();
                                    }, 1000);
                                } else {
                                    alert(d.message);
                                    //alertMsg("错误信息", d.message);
                                    removeActionLine();
                                }
                            },
                            error: function () {
                                alert("请求失败，请重新操作");
                                removeActionLine();
                            }

                        });
                    } else if (d.Ret < 0) {
                        alertMsg("错误信息", d.Message);
                        removeActionLine();
                    }
                }
            });
        }
    /*生成所有系统列表*/
        var signed = window.location.href;
        var signhref = signed.split("/");
        if (signhref[3] != "signin") {         //已登录
            $.ajax({
                url: "http://oa.gyyx.cn/externalsystem/findall?jsoncallback=?",
                type: 'GET',
                dataType: "jsonp",
                data: { r: function () { return Math.random(); } },
                error: function () {
                    alert("请求失败，请重新操作");
                },
                success: function (d) {
                    if (d.Ret == 0) {
                        var syslist = "";
                        if (d.Data.length > 7) {
                            $("#userNavList").addClass("userNav2");
                        }
                        for (var i = 0; i < d.Data.length; i++) {
                            syslist += '<li><a id="' + d.Data[i].id + '" href="' + d.Data[i].defaultUrl + '" title="" target="_blank"><i class="fa fa-external-link">&nbsp;&nbsp;&nbsp;&nbsp;</i>' + d.Data[i].showName + '</a></li>';
                        }
                        $("#userNavList").empty().append(syslist);
                        $("#userNavList a").bind("click", function () {
                            var $this = $(this);
                            var locationhref = $this.attr("href");
                            var sysid = parseInt($this.attr("id"));
                            loadSys(sysid, locationhref);
                            return false;

                        });
                    } else if (d.Ret < 0) {
                        alertMsg("错误信息", d.Message);
                    }
                }
            });
        }
        /*修改密码弹层验证 */
        $("#editpwd").validate({
            errorElement: "span",
            onblur: true,
            debug: true,
            ignore: "",
            errorPlacement: function (error, element) {
                error.appendTo(element.parent());
                error.addClass("text-danger");
            },
            messages: {
                oldPassword: {
                    required: "原密码为必填项"
                },
                newPassword: {
                    required: "新密码为必填项"
                },
                confrimPassword: {
                    required: "确认密码为必填项"
                }
            }
        });
        $(".js_editPwdSubmit").click(function () {
            var $this = $(this);
            if ($("#editpwd").valid()) {
                //document.forms.editpwd.submit();
                $.ajax({
                    url: "http://oa.gyyx.cn/Credential/EditPasswordNew?jsoncallback=?",
                    type: "GET",
                    dataType: "jsonp",
                    data: {
                        r: function () { return Math.random(); },
                        oldPassword: $("#oldPassword").val(),
                        newPassword: $("#newPassword").val(),
                        confrimPassword: $("#confrimPassword").val()
                    },
                    error: function (error) {
                        alert(error);
                    },
                    success: function (d) {
                        if (d.Ret == 0) {
                            $this.parent().find(".btn-default").click();
                            window.location.href = "http://oa.gyyx.cn/signout/";
                        } else {
                            $(".js_Message").empty().append(d.Message);

                        }
                    }
                });
            } else { }
        });
    
    /*彩蛋 start*/
        $(".cd").click(function () {
            alertMsg("暂未开放", "更多精彩体验，期待新OA~")
        });
    /*彩蛋 end*/
       
});
