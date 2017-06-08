/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：陈巧玲
 * 修改：李根
 * 联系方式：ligen@gyyx.cn
 * 创建时间： 2014/4/16 
 * 版本号：v1.0

 * 功能： 公共弹出层方法

 * -------------------------------------------------------------------------*/
$(function () {
    /*右上角UCenter系统列表下拉菜单  by chenqiaoling 2014.1.14  start*/
    /* 创建系统权限加载进度指示条 */
    var CreateActionLine = function (id) {
        var sysName = $("#" + id).text();
        $('<div id="progressBox" class="widget">' +
        '    <div class="fileProcess">' +
        '        <img src="/Contents/images/10s.gif" alt="" class="loader">' +
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
            url: '/externalsystem/getguidaddr/' + parseInt(id),
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
            url: "/externalsystem/findall",
            type: 'GET',
            dataType: "json",
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
                    $("#userNavList a").bind("click",function () {
                        var $this = $(this);
                        var locationhref = $this.attr("href");
                        var sysid = parseInt($this.attr("id"));
                        loadSys(sysid, locationhref);
                        return false;

                    });
                } else if (d.Ret < 0) {
                    alertMsg("错误信息",d.Message);
                }
            }
        });
    }
    /*系统列表下拉菜单显示效果 */
    //var toggletimer = null;
    //$('li.js_showsubnav').hover(function () {
    //    $(this).find(".js_firstnav").addClass("cur");
    //    clearTimeout(toggletimer);
    //    toggletimer = setTimeout(function () {
    //        $('#userNavList').slideDown(200);
    //    }, 100);
    //}, function () {
    //    $(this).find(".js_firstnav").removeClass("cur");
    //    clearTimeout(toggletimer);
    //    toggletimer = setTimeout(function () {
    //        $('#userNavList').slideUp(200);
    //    }, 100);
    //});
    /*点击系统列表项，加载系统*/
   

    /*  end*/
});