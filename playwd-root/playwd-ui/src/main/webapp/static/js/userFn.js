/*-------------------------------------------------------------------------
* 作者：骆崇飞
* 邮箱：luochongfei@gyyx.cn
* 创建时间： 2015/1
* 版本号：v1.0
* 作用域：用户中心
* updata:hujunhe
-------------------------------------------------------------------------*/
var USER_CONFIG = {
    //首页获取收藏文章2个
    getIndexCollectArticleUrl: '/User/GetArticleCollect',
    //首页获取收藏图册2个
    getIndexCollectAlbumsUrl: '/User/GetAlbumsCollect',
    //首页获取收藏视频2个
    getIndexCollectVideoUrl: '/User/GetVideoCollect',
    //获取我的收藏列表
    getCollectUrl: '/User/GetCollects',
    //取消收藏
    cancelCollectUrl: '/User/CancelCollect',
    //获取我的消息
    getMessageUrl: '/User/GetMessage',
    //获取我的角色
    getRoleListUrl: '/User/GetUser',
    //角色区组获取
    getRoleServerListUrl: '/User/GetGameServerList',
    //同步角色
    syncRoleUrl: '/User/InsertUser',
    //设置默认角色
    setDefaultRoleUrl: '/User/UserPhoto',
    //获取我的上传列表
    getUploadUrl: '/User/GetUpload',
    //验证码
    captchaUrl: 'http://wanwd.gyyx.cn/user/Captcha',
    //退出登录
    Loginut: 'http://reg.gyyx.cn/Login/LogoutJsonp/',

    //首页文章描述截取长度
    indexArticleSubstrLen: 105,

    //我的收藏分页量
    collectArticlePageSize: 5,
    collectImagePageSize: 12,
    collectVideoPageSize: 12,
    collectCartoonPageSize: 4,
    //我的收藏无数据提示
    noCollectArticleTxt:
        '您未有收藏的文章，快去' +
        '<a target="_blank" href="/articlelist/Index?firstType=21&secondType=0&order=1&pageIndex=1&pagesize=5">' +
        '图文专区</a>看看吧~',
    noCollectImageTxt: '您未有收藏的图片，快去<a target="_blank" href="/Picture/Index">秀图专区</a>看看吧~',
    noCollectVideoTxt: '您未有收藏的视频，快去<a target="_blank" href="/Video/Index">火爆视频专区</a>看看吧~',
    noCollectCartoonTxt: '您未有收藏的视频，快去<a target="_blank" href="http://wanwdv2.gyyx.cn/home">漫画专区</a>看看吧~',
    //loading
    collectLoadHtml: '<img class="search_loadimg" />',
    //我的上传文章描述截取长度
    collectArticleSubstrLen: 105,


    //我的上传分页量
    uploadArticlePageSize: 5,
    uploadImagePageSize: 12,
    uploadVideoPageSize: 12,
    //我的上传无数据提示
    noUploadArticleTxt: '您未有上传的文章，快去<a target="_blank" href="/article/upload">投稿</a>吧~',
    noUploadImageTxt: '您未有上传的图片，快去<a target="_blank" href="/Picture/Upload">秀图专区上传</a>吧~',
    noUploadVideoTxt: '您未有上传的视频，快去<a target="_blank" href="/Video/Upload">上传</a>吧~',
    //loading
    uploadLoadHtml: '<img class="search_loadimg" />',
    //我的上传文章描述截取长度
    uploadArticleSubstrLen: 105,

    //审核状态className(默认.audit)
    "审核通过": '',
    "审核中": 'h_shz',
    "编辑推荐": 'h_edit',
    "审核未通过": 'h_shz',

    //未登录显示
    noLoginTxt: '<span class="sp_nologin">您尚未登录，请<a href="javascript:;" onclick="alertFn(null,null,true); return false;">登录</a>后再查看！</span>',

    //文章默认图片
    articleDefaultImg: '/Content/images/art_default_pic.jpg',
    //头像图片路径
    portraitPath: '/Content/images/portrait/'
};
var isIE6 = /msie 6/i.test(navigator.userAgent);
if (isIE6) {
    try { document.execCommand('BackgroundImageCache', false, true); } catch (e) { }
}

ChinaCaptcha.prototype.subFn = function () { };
var chinaCaptcha1 = new ChinaCaptcha($(".js_bindRole"), {
    bid: "jnajq"
});
console.log(chinaCaptcha1);
chinaCaptcha1.init();
$.fn.extend({
    //用户中心头像$()
    headPortrait: function () {

        var
            //角色名
            $roleName = $("#js_RoleName"),
            //头像
            $headPortrait = $("#js_HeadPortrait"),
            //我的角色页面
            $roleCont = $("#js_RoleList");

        $.ajax({
            url: USER_CONFIG.getRoleListUrl,
            type: "GET",
            dataType: "JSON",
            data: {
                r: Math.random()
            },
            success: function (data) {
                if (data.IsSuccess) {
                    var dCont = data.List;
                    if (dCont && dCont[0]) {
                        for (var i = 0; i < dCont.length; i++) {
                            if (dCont[i].IsDefault) {
                                $("#js_RoleName").html(dCont[i].NickName + "【服务器：" + dCont[i].ServerName + "】");
                                $("#js_HeadPortrait").find("img").attr("src", USER_CONFIG.portraitPath + dCont[i].Picture + ".jpg");
                            }
                        }
                    } else {
                        $("#js_RoleName").html("未有同步角色");
                        //点击重新同步按钮
                        $("#js_SynchroBtn").click(function () {
                            //显示表单
                            $().setSyncForm();
                            return false;
                        });
                    }
                } else {
                    if (data.Values == "-3") {//未登录
                        alertFn(null, null, true);
                        noLoginFn();
                    } else {
                        //点击重新同步按钮
                        $("#js_SynchroBtn").click(function () {
                            //显示表单
                            $().setSyncForm();
                            return false;
                        });
                    }
                }

                if ($("#js_HidRole").val() == "Role") {
                    $().getRoleList(data);
                }
            }
        });


        //未登录处理
        function noLoginFn() {
            $roleName.html(USER_CONFIG.noLoginTxt);
            $roleCont.html(USER_CONFIG.noLoginTxt);
            $(".top_bg_red").attr("data-status", false);
        }

    },

    //用户中心首页加载
    uIndexFn: function () {
        var substrL = USER_CONFIG.indexArticleSubstrLen;

        //加载收藏的文章
        getTxt();

        //加载收藏的图片
        getPic();

        //加载视频
        getVideo();

        //获取收藏的文章2篇
        function getTxt() {
            $().getPreWorks({
                url: USER_CONFIG.getIndexCollectArticleUrl,
                data: {
                    SourcesType: 1,
                    r: Math.random()
                },
                success: function (data) {
                    var dCont = data.List;
                    if (data.IsSuccess) {
                        var divHtml = "";
                        var listMax = dCont.length > 2 ? 2 : dCont.length;
                        for (var i = 0; i < listMax; i++) {
                            var s_brief = dCont[i].Brief.length > substrL ? dCont[i].Brief.substr(0, substrL) + '...' : dCont[i].Brief;
                            var s_cate = dCont[i].ParentName == null ? '<a href="javascript:;" title="一级分类">' + dCont[i].Name + '</a>' : '<a href="javascript:;" title="一级分类">' + dCont[i].ParentName + '</a><a href="javascript:;" title="二级分类">' + dCont[i].Name + '</a>';
                            divHtml += '' +
                                '<div class="graplist">' +
                                '    <h2 class="title"><a target="_blank" href="/article/' + dCont[i].SourcesCode + '">' + dCont[i].Title + '</a></h2>' +
                                '    <div class="graplist_l">' +
                                '       <a target="_blank" href="/article/' + dCont[i].SourcesCode + '">' +
                                '           <img src="' + (dCont[i].Picture != null ? dCont[i].Picture : USER_CONFIG.articleDefaultImg) + '">' +
                                '       </a>' +
                                '   </div>' +
                                '    <div class="graplist_r">' +
                                '        <div class="txt">' + s_brief +
                                '           <a target="_blank" class="xx" href="/article/' + dCont[i].SourcesCode + '">[详情]</a></div>' +
                                '       <p class="s_art_cate">' + s_cate + '</p>' +
                                '    </div>' +
                                '</div>';
                        }
                        $("#js_IndexArticle").html(divHtml);
                    } else {
                        if (!(dCont && dCont[0])) {
                            $("#js_IndexArticle").html('<div class="nocollect_tip">' + USER_CONFIG.noCollectArticleTxt + '</div>');
                        }
                        if (data.Values == "-3") {
                            $(".uIndexbox").html("<h3 class='no_data_h3'>" + USER_CONFIG.noLoginTxt + "</h3>");
                            $(".top_bg_red").attr("data-status", false);
                            alertFn(null, null, true);
                        }
                    }
                }
            });
        }

        //获取收藏的图片3个
        function getPic() {
            $().getPreWorks({
                url: USER_CONFIG.getIndexCollectAlbumsUrl,
                data: {
                    SourcesType: 2,
                    r: Math.random()
                },
                success: function (data) {
                    var dCont = data.List;
                    if (data.IsSuccess) {
                        var liHtml = "";
                        var listMax = dCont.length > 2 ? 3 : dCont.length;
                        for (var i = 0; i < listMax; i++) {
                            liHtml += '' +
                                '<li>' +
                                '    <a target="_blank" href="/picture/' + dCont[i].SourcesCode + '">' +
                                '        <img src="' + dCont[i].Picture + '">' +
                                '    </a>' +
                                '    <p class="p_s_text">' + dCont[i].Title + '</p>' +
                                '</li>';
                        }
                        $("#js_IndexImage").html(liHtml);
                    } else {
                        if (!(dCont && dCont[0])) {
                            $("#js_IndexImage").html('').after('<div class="nocollect_tip">' + USER_CONFIG.noCollectImageTxt + '</div>');
                        }
                        if (data.Values == "-3") {
                            $(".uIndexbox").html("<h3 class='no_data_h3'>" + USER_CONFIG.noLoginTxt + "</h3>");
                            $(".top_bg_red").attr("data-status", false);
                        }
                    }
                }
            });
        }

        //获取收藏的视频3个
        function getVideo() {
            $().getPreWorks({
                url: USER_CONFIG.getIndexCollectVideoUrl,
                data: {
                    SourcesType: 3,
                    r: Math.random()
                },
                success: function (data) {
                    var dCont = data.List;
                    if (data.IsSuccess) {
                        var liHtml = "";
                        var listMax = dCont.length > 2 ? 3 : dCont.length;
                        for (var i = 0; i < listMax; i++) {
                            liHtml += '' +
                                '<li>' +
                                '    <a target="_blank" href="/video/' + dCont[i].SourcesCode + '">' +
                                '        <img src="' + dCont[i].Picture + '">' +
                                '        <span class="h_shade pngbg">' +
                                '            <span class="video_but pngbg">' + dCont[i].Title + '</span>' +
                                '        </span>' +
                                '    </a>' +
                                '</li>';
                        }
                        $("#js_IndexVideo").html(liHtml);
                    } else {
                        if (!(dCont && dCont[0])) {
                            $("#js_IndexVideo").html('').after('<div class="nocollect_tip">' + USER_CONFIG.noCollectVideoTxt + '</div>');
                        }
                        if (data.Values == "-3") {
                            $(".uIndexbox").html("<h3 class='no_data_h3'>" + USER_CONFIG.noLoginTxt + "</h3>");
                            $(".top_bg_red").attr("data-status", false);
                        }
                    }
                }
            });
        }
    },

    //用户中心我的收藏加载
    uCollectFn: function () {
        var cType = $().getQuery("collectType");
        $().getCate(cType, function (setting) {
            $().selectLayoutByType(setting);
        });
        //显示或隐藏右侧信息
        $().displayRightCont(cType ? cType : 1);

        //通过地址栏数据选择种类
        if (location.href.indexOf("?type") != -1) {
            var this_type = window.location.search.split("=")[1];
            $().getCate(this_type, function (setting) {
                $(document).selectLayoutByTypeForUpload(setting);
            });
            $("#js_WorksCate li").each(function () {
                if ($(this).data("stype") == this_type) {
                    $(this).addClass("on").siblings().removeClass("on");
                }
            });
        }
        //类型切换
        $("#js_WorksCate").find("li").unbind("click").bind("click", function () {
            var thisType = $(this).attr("data-stype");
            if ($(this).hasClass("on")) {
                return false;
            }
            $().getCate(thisType, function (setting) {
                $(document).selectLayoutByType(setting);
            });
            //显示或隐藏右侧信息
            $().displayRightCont(thisType);

            return false;
        });
    },

    //用户中心我的角色
    uRoleFn: function () {
        //$().getRoleList();
        console.log(1);
    },

    //用户中心我的消息加载
    uMessageFn: function () {
        var
            //未登录处理
            noLoginFn = function () {
                $("#js_MsgContWrap").html("<h3 class='no_data_h3'>" + USER_CONFIG.noLoginTxt + "</h3>");
                $(".top_bg_red").attr("data-status", false);
            },
            //作品类型配置
            typeCfg = {
                1: '文章',
                2: '图片',
                3: '视频',
                4: '漫画'
            },
            //消息类型语句配置(1:审核通过，2:推荐)
            msgTypeCfg = {
                1: '已被通过审核在平台发布。',
                2: '十分优秀，已被官方推荐，将会获得奖励哟，请关注官方公告。'
            },
            //查看链接配置
            msgLinkCfg = {
                1: '/Article/',
                2: '/Picture/',
                3: '/Video/',
                4: '//'//漫画链接
            },
            //获取消息
            getMsgFn = function () {
                $("#js_MsgCont").ajaxPage({
                    url: USER_CONFIG.getMessageUrl,
                    pageObj: $(".search_page"),
                    pageSize: 3,
                    curPageCls: "gy_pageon",
                    pageData: "data.Values",
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
                    paramObj: {},
                    dataFn: function (data) {
                        var
                            dataHtml = "",
                             //dataHtml = '<a href="javascript:;" style="color:#1598dc;" >文章、图片、视频消息</a> | <a href="http://wanwdv2.gyyx.cn/userCenter/myMessage" class="cartoonMessage" >漫画消息</a>',
                            dCont = data.List;
                        if (!data.IsSuccess) {
                            if (data.Values == "-3") {
                                noLoginFn();
                            } else {
                                $("#js_MsgContWrap").html('<p>暂无消息！</p>');
                            }
                            return false;
                        }
                        if (dCont && dCont[0]) {
                            for (var i = 0; i < dCont.length; i++) {
                                var typeTxt = typeCfg[dCont[i].SourceType];
                                var msgTxt = msgTypeCfg[dCont[i].MessageType];
                                var msgLink = msgLinkCfg[dCont[i].SourceType] + dCont[i].SourceCode;
                                if (dCont[i].SourceType == 4) {
                                    dataHtml += '' +
                                           '<li>' +
                                           //'   <div class="del_ico"></div>' +
                                           '    <div class="message_cont">' +
                                           '        <h3>' +
                                           '            <span class="message_tit textover">' + dCont[i].SourceTitle + '</span>' +
                                           '            <span class="mess_time">' + $().changeTime(dCont[i].CreateTime, true, "-", ":") + '</span>' +
                                           '        </h3>' +
                                           '        <p class="wordbreak">恭喜，您的' + typeTxt + '"' + dCont[i].SourceTitle + '"' +
                                           '         ' +
                                           '        </p>' +
                                           '    </div>' +
                                           '</li>';
                                } else {
                                    dataHtml += '' +
                                            '<li>' +
                                            //'   <div class="del_ico"></div>' +
                                            '    <div class="message_cont">' +
                                            '        <h3>' +
                                            '            <span class="message_tit textover"><a target="_blank" href="' + msgLink + '">' + dCont[i].SourceTitle + '</a></span>' +
                                            '            <span class="mess_time">' + $().changeTime(dCont[i].CreateTime, true, "-", ":") + '</span>' +
                                            '        </h3>' +
                                            '        <p class="wordbreak">恭喜，您的' + typeTxt + '"' + dCont[i].SourceTitle + '"' +
                                            '         ' + msgTxt + '<a target="_blank" href="' + msgLink + '">查看' + typeTxt + '</a>' +
                                            '        </p>' +
                                            '    </div>' +
                                            '</li>';
                                }



                            }

                            return dataHtml;
                        } else {
                            return false;
                        }
                    },
                    debug: false
                });
            };
        getMsgFn();
    },



    //用户中心我的上传加载
    uUploadFn: function () {
        var uType = $().getQuery("uploadType");
        $().getCate(uType, function (setting) {
            $().selectLayoutByTypeForUpload(setting);
        });
        //显示或隐藏右侧信息
        $().displayRightCont(uType ? uType : 1);
        //类型切换
        $("#js_WorksCate").find("li").unbind("click").bind("click", function () {
            var thisType = $(this).attr("data-stype");
            if ($(this).hasClass("on")) {
                return false;
            }
            if (thisType == "1") {
                $("#state").css({ "right": "20px" });
            } else {
                $("#state").css({ "right": "-250px" });
            }
            //获取数据
            $().getCate(thisType, function (setting) {
                $(document).selectLayoutByTypeForUpload(setting);
            });
            //显示或隐藏右侧信息
            $().displayRightCont(thisType);
            return false;
        });

        //我的上传 筛选框
        $("#state").change(function () {
            var currType = 1;
            for (var i = 0; i < 3; i++) {
                if ($($("#js_WorksCate li")[i]).hasClass("on")) {
                    currType = $($("#js_WorksCate li")[i]).attr("data-stype");
                }
            }
            //获取数据
            $().getCate(currType, function (setting) {
                $(document).selectLayoutByTypeForUpload(setting);
            });
        });
        //通过地址栏数据选择种类
        if (location.href.indexOf("?type") != -1) {
            var this_type = window.location.search.split("=")[1];
            $().getCate(this_type, function (setting) {
                $(document).selectLayoutByTypeForUpload(setting);
            });
            $("#js_WorksCate li").each(function () {
                if ($(this).data("stype") == this_type) {
                    $(this).addClass("on").siblings().removeClass("on");
                }
            });
        }

    },

    //显示隐藏右侧消息
    displayRightCont: function (type) {
        if (type == 1) {
            $("#js_RightCont").show();
        } else {
            $("#js_RightCont").hide();
        }
    },

    //首页获取文章，图片，视频,漫画公用方法
    getPreWorks: function (options) {
        var defaults = {
            url: "",
            data: {
                r: Math.random()
            },
            success: function () { },
            error: function () { }
        };
        $.extend(defaults, options);
        var sdata = defaults.data;
        $.ajax({
            url: defaults.url,
            type: "GET",
            dataType: "JSON",
            data: sdata,
            success: function (data) {
                defaults.success && defaults.success(data);
            },
            error: function (e) {
                defaults.error && defaults.error(e);
            }
        });
    },

    //类型切换(我的收藏,我的上传)
    getCate: function (type, callback) {
        var
            type = type && parseInt(type) || 1,
            setObj = {},
            $this = $("#js_WorksCate").find("li[data-stype='" + type + "']");
        //高亮
        $this.addClass("on").siblings().removeClass("on");

        //防止类型无效
        if (type > 4 || type < 1) {
            type = 1;
        }

        //确定最终类型
        setObj.type = type;
        //根据类型选择布局
        callback && callback(setObj);
    },

    //根据类型选择页面布局--我的收藏
    selectLayoutByType: function (setObj) {
        var
            //布局类型
            lType = parseInt(setObj.type),

            //文章描述截取长度
            substrL = USER_CONFIG.collectArticleSubstrLen,
            //内容填充区
            contWrap = $("#js_CollectContainer"),
            //loading HTML
            loadHtml = USER_CONFIG.collectLoadHtml,
            //获取数据所需参数
            paramObj = { SourcesType: setObj.type },
            //分页容器
            pageHtml = '' +
                '<div class="mt30 s_page_wrap">' +
                '   <span class="gy_page search_page"></span>' +
                '   <span class="search_page_txt"></span>' +
                '</div>',
            //未登录处理
            noLoginFn = function () {
                contWrap.html("<h3 class='no_data_h3'>" + USER_CONFIG.noLoginTxt + "</h3>");
                $(".top_bg_red").attr("data-status", false);
            };

        //根据布局类型编号选择
        switch (lType) {
            case 1://文章
                contWrap.html('<div class="grapdiv" id="js_Cont_Article"></div>' + pageHtml);

                $("#js_Cont_Article").html(loadHtml).pageLayout({
                    pageObj: $(".search_page"),
                    paramObj: paramObj,
                    pageSize: USER_CONFIG.collectArticlePageSize,
                    dataFn: function (data, $this) {
                        var dataHtml = "", dCont = data.List;
                        if (!data.IsSuccess) {
                            if (data.Values == "-3") {
                                noLoginFn();
                            } else {
                                contWrap.html("<h3 class='no_data_h3'>" + USER_CONFIG.noCollectArticleTxt + "</h3>");
                            }
                            return false;
                        }
                        if (dCont && dCont[0]) {
                            for (var i = 0; i < dCont.length; i++) {
                                var s_brief = dCont[i].Brief.length > substrL ? dCont[i].Brief.substr(0, substrL) + '...' : dCont[i].Brief;
                                var s_cate = dCont[i].ParentName == null ? '<a href="javascript:;" title="一级分类">' + dCont[i].Name + '</a>' : '<a href="javascript:;" title="一级分类">' + dCont[i].ParentName + '</a><a href="javascript:;" title="二级分类">' + dCont[i].Name + '</a>';
                                dataHtml += '' +
                                    '<div class="graplist">' +
                                    '    <h2 class="title">' +
                                    '        <a target="_blank" href="/article/' + dCont[i].SourcesCode + '" title="' + dCont[i].Title + '">' +
                                    '       ' + dCont[i].Title + '</a>' +
                                    '        <span class="v_grap_f">' +
                                    '            <a href="javascript:;" class="v_grap_qx">取消收藏</a>' +
                                    '            <a href="javascript:;" class="v_grap_delete js_DeleteThisWork" data-cancelcode="' + dCont[i].SourcesCode + '"></a>' +
                                    '        </span>' +
                                    '    </h2>' +
                                    '    <div class="graplist_l">' +
                                    '        <a target="_blank" href="/article/' + dCont[i].SourcesCode + '" title="' + dCont[i].Title + '">' +
                                    '        <img src="' + (dCont[i].Picture != null ? dCont[i].Picture : USER_CONFIG.articleDefaultImg) + '">' +
                                    '</a>' +
                                    '    </div>' +
                                    '    <div class="graplist_r">' +
                                    '        <div class="txt">' +
                                    '            ' + s_brief +
                                    '            <a target="_blank" class="xx" href="/article/' + dCont[i].SourcesCode + '">[详情]</a>' +
                                    '        </div>' +
                                    '       <p class="s_art_cate">' + s_cate + '</p>' +
                                    '    </div>' +
                                    '</div>';
                            }
                        } else {
                            contWrap.html("<h3 class='no_data_h3'>" + USER_CONFIG.noCollectArticleTxt + "</h3>");
                            return false;
                        }

                        return dataHtml;
                    }
                });
                break;
            case 2://相册
                contWrap.html('<div class="box_inner playp full_width"><ul class="p_tvlist v_pic_list" id="js_Cont_NorPic"></ul>' + pageHtml + '</div>');

                $("#js_Cont_NorPic").html("<li>" + loadHtml + "<li>").pageLayout({
                    pageObj: $(".search_page"),
                    paramObj: paramObj,
                    pageSize: USER_CONFIG.collectImagePageSize,
                    dataFn: function (data, $this) {
                        var dataHtml = "", dCont = data.List;
                        if (!data.IsSuccess) {
                            if (data.Values == "-3") {
                                noLoginFn();
                            } else {
                                contWrap.html("<h3 class='no_data_h3'>" + USER_CONFIG.noCollectImageTxt + "</h3>");
                            }
                            return false;
                        }
                        if (dCont && dCont[0]) {
                            for (var i = 0; i < dCont.length; i++) {
                                dataHtml += '' +
                                    '<li data-code="' + dCont[i].SourcesCode + '" class="js_CollectImgLi">' +
                                    '    <a target="_blank" href="/Picture/' + dCont[i].SourcesCode + '">' +
                                    '        <img src="' + dCont[i].Picture + '" title="' + dCont[i].Title + '">' +
                                    '        <span class="v_delete js_DeleteThisWork" data-cancelcode="' + dCont[i].SourcesCode + '">删除</span>' +
                                    '        <span class="v_pic_l v_pic_b uc_picnum">共1张</span>' +
                                    '     </a>' +
                                    '    <p class="p_s_text">' + dCont[i].Title + '</p>' +
                                    '</li>';
                            }
                        } else {
                            contWrap.html("<h3 class='no_data_h3'>" + USER_CONFIG.noCollectImageTxt + "</h3>");
                            return false;
                        }
                        return dataHtml;
                    }
                });
                break;
            case 3://视频
                contWrap.html('<div class="box_inner playp full_width"><ul class="p_tvlist v_pic_list" id="js_Cont_Video"></ul>' + pageHtml + '</div>');

                $("#js_Cont_Video").html("<li>" + loadHtml + "<li>").pageLayout({
                    pageObj: $(".search_page"),
                    paramObj: paramObj,
                    pageSize: USER_CONFIG.collectVideoPageSize,
                    dataFn: function (data, $this) {
                        var dataHtml = "", dCont = data.List;
                        if (!data.IsSuccess) {
                            if (data.Values == "-3") {
                                noLoginFn();
                            } else {
                                contWrap.html("<h3 class='no_data_h3'>" + USER_CONFIG.noCollectVideoTxt + "</h3>");
                            }
                            return false;
                        }
                        if (dCont && dCont[0]) {
                            for (var i = 0; i < dCont.length; i++) {
                                dataHtml += '' +
                                    '<li>' +
                                    '    <a target="_blank" href="/Video/' + dCont[i].SourcesCode + '">' +
                                    '        <img src="' + dCont[i].Picture + '" title="' + dCont[i].Title + '">' +
                                    '        <span class="v_delete js_DeleteThisWork" data-cancelcode="' + dCont[i].SourcesCode + '">删除</span>' +
                                    '    </a>' +
                                    '    <p class="p_s_text">' + dCont[i].Title + '</p>' +
                                    '</li>';
                            }
                        } else {
                            contWrap.html("<h3 class='no_data_h3'>" + USER_CONFIG.noCollectVideoTxt + "</h3>");
                            return false;
                        }
                        return dataHtml;
                    }
                });
                break;
            case 4://漫画
                window.location.href = "http://wanwdv2.gyyx.cn/userCenter/myCollectCartoon";
                break;
            default:
                alert(":( 程序出错，请刷新页面重试！");
                break;
        }
    },

    //根据类型选择页面布局--我的上传
    selectLayoutByTypeForUpload: function (setObj) {
        var
            //布局类型
            lType = parseInt(setObj.type),
            //文章描述截取长度
            substrL = USER_CONFIG.uploadArticleSubstrLen,
            //内容填充区
            contWrap = $("#js_CollectContainer"),
            //loading HTML
            loadHtml = '<img class="search_loadimg" />',
            //获取数据所需参数
            paramObj = { SourcesType: setObj.type, State: $("#state").val() },
            //分页容器
            pageHtml = '' +
                '<div class="mt30 s_page_wrap">' +
                '   <span class="gy_page search_page"></span>' +
                '   <span class="search_page_txt"></span>' +
                '</div>',
            //未登录处理
            noLoginFn = function () {
                contWrap.html("<h3 class='no_data_h3'>" + USER_CONFIG.noLoginTxt + "</h3>");
                $(".top_bg_red").attr("data-status", false);
            };

        //根据布局类型编号选择
        switch (lType) {
            case 1://文章
                contWrap.html('<div class="grapdiv" id="js_Cont_Article"></div>' + pageHtml);

                $("#js_Cont_Article").html(loadHtml).pageLayout({
                    url: USER_CONFIG.getUploadUrl,
                    pageObj: $(".search_page"),
                    paramObj: paramObj,
                    pageSize: setObj.pageSize,
                    dataFn: function (data, $this) {
                        var dataHtml = "", dCont = data.List;
                        if (!data.IsSuccess) {
                            if (data.Values == "-3") {
                                noLoginFn();
                            } else {
                                contWrap.html("<h3 class='no_data_h3'>" + USER_CONFIG.noUploadArticleTxt + "</h3>");
                            }
                            return false;
                        }
                        if (dCont && dCont[0]) {
                            for (var i = 0; i < dCont.length; i++) {
                                var s_style = dCont[i].State;
                                var s_brief = dCont[i].Brief.length > substrL ? dCont[i].Brief.substr(0, substrL) + '...' : dCont[i].Brief;
                                var s_cate = dCont[i].ParentName == null ? '<a href="javascript:;" title="一级分类">' + dCont[i].Name + '</a>' : '<a href="javascript:;" title="一级分类">' + dCont[i].ParentName + '</a><a href="javascript:;" title="二级分类">' + dCont[i].Name + '</a>';
                                var a_link_tit, a_link_img, a_link_desc = "";
                                if (dCont[i].State == "审核中" || dCont[i].State == "审核未通过") {
                                    a_link_tit = dCont[i].Title;
                                    a_link_img = '<img src="' + (dCont[i].Picture ? dCont[i].Picture : USER_CONFIG.articleDefaultImg) + '" />';
                                } else {
                                    a_link_tit = '<a target="_blank" href="/article/' + dCont[i].SourcesCode + '" title="' + dCont[i].Title + '">' + dCont[i].Title + '</a>';
                                    a_link_img = '' +
                                        '<a target="_blank" href="/article/' + dCont[i].SourcesCode + '" title="' + dCont[i].Title + '">' +
                                        '           <img src="' + (dCont[i].Picture ? dCont[i].Picture : USER_CONFIG.articleDefaultImg) + '" />' +
                                        '</a>';
                                    a_link_desc = '<a target="_blank" class="xx" href="/article/' + dCont[i].SourcesCode + '">[详情]</a>';
                                }

                                dataHtml += '' +
                                    '<div class="graplist">' +
                                    '   <h2 class="title" style="font-size:24px;">' + a_link_tit + '</h2>' +
                                    '   <div class="clear">' +
                                    '   <div class="graplist_l">' + a_link_img +
                                    '       <span class="audit ' + USER_CONFIG[s_style] + '">' + s_style + '</span>' +
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
                            contWrap.html("<h3 class='no_data_h3'>" + USER_CONFIG.noUploadArticleTxt + "</h3>");
                            return false;
                        }
                        return dataHtml;
                    }
                }, "upload");

                break;
            case 2://相册
                contWrap.html('<div class="box_inner playp full_width"><ul class="p_tvlist v_pic_list" id="js_Cont_NorPic"></ul>' + pageHtml + '</div>');

                $("#js_Cont_NorPic").html("<li>" + loadHtml + "<li>").pageLayout({
                    url: USER_CONFIG.getUploadUrl,
                    pageObj: $(".search_page"),
                    paramObj: paramObj,
                    pageSize: setObj.pageSize,
                    dataFn: function (data, $this) {
                        var dataHtml = "", dCont = data.List;
                        if (!data.IsSuccess) {
                            if (data.Values == "-3") {
                                noLoginFn();
                            } else {
                                contWrap.html("<h3 class='no_data_h3'>" + USER_CONFIG.noUploadImageTxt + "</h3>");
                            }
                            return false;
                        }
                        if (dCont && dCont[0]) {
                            for (var i = 0; i < dCont.length; i++) {
                                var s_style = dCont[i].State;
                                var a_link_img = "javascript:;";
                                var a_link_target = '_self';
                                if (s_style == "审核中" || s_style == "审核未通过") {
                                } else {
                                    a_link_img = '/Picture/' + dCont[i].SourcesCode;
                                    a_link_target = '_blank';
                                }
                                dataHtml += '' +
                                    '<li data-code="' + dCont[i].SourcesCode + '" class="js_CollectImgLi">' +
                                    '    <a target="' + a_link_target + '" href="' + a_link_img + '" class="u_up_imga">' +
                                    '        <img src="' + dCont[i].Picture + '" title="' + dCont[i].Title + '">' +
                                    '        <span class="v_pic_l uc_picnum">共1张</span>' +
                                    '        <span class="audit ' + USER_CONFIG[s_style] + '">' + s_style + '</span>' +
                                    '    </a>' +
                                    '    <p class="p_s_text">' + dCont[i].Title + '</p>' +
                                    '</li>';
                            }
                        } else {
                            contWrap.html("<h3 class='no_data_h3'>" + USER_CONFIG.noUploadImageTxt + "</h3>");
                            return false;
                        }
                        return dataHtml;
                    }
                }, "upload");

                break;
            case 3://视频
                contWrap.html('<div class="box_inner playp full_width"><ul class="p_tvlist v_up_list" id="js_Cont_Video"></ul>' + pageHtml + '</div>');

                $("#js_Cont_Video").html("<li>" + loadHtml + "<li>").pageLayout({
                    url: USER_CONFIG.getUploadUrl,
                    pageObj: $(".search_page"),
                    paramObj: paramObj,
                    pageSize: setObj.pageSize,
                    dataFn: function (data, $this) {
                        var dataHtml = "", dCont = data.List;
                        if (!data.IsSuccess) {
                            if (data.Values == "-3") {
                                noLoginFn();
                            } else {
                                contWrap.html("<h3 class='no_data_h3'>" + USER_CONFIG.noUploadVideoTxt + "</h3>");
                            }
                            return false;
                        }
                        if (dCont && dCont[0]) {
                            for (var i = 0; i < dCont.length; i++) {
                                var s_style = dCont[i].State;
                                var a_link_img = "javascript:;";
                                var a_link_target = '_self';
                                if (s_style == "审核中" || s_style == "审核未通过") {
                                } else {
                                    a_link_img = '/Picture/' + dCont[i].SourcesCode;
                                    a_link_target = '_blank';
                                }
                                dataHtml += '' +
                                    '<li>' +
                                    '    <a target="' + a_link_target + '" href="' + a_link_img + '" class="u_up_imga">' +
                                    '        <img src="' + dCont[i].Picture + '" title="' + dCont[i].Title + '">' +
                                    '        <span class="audit ' + USER_CONFIG[s_style] + '">' + s_style + '</span>' +
                                    '        <span class="h_shade pngbg">' +
                                    '            <span class="video_but pngbg">' + dCont[i].Title + '</span>' +
                                    '        </span>' +
                                    '    </a>' +
                                    '</li>';
                            }
                        } else {
                            contWrap.html("<h3 class='no_data_h3'>" + USER_CONFIG.noUploadVideoTxt + "</h3>");
                            return false;
                        }
                        return dataHtml;
                    }
                }, "upload");

                break;
            case 4://漫画test
                window.location.href = 'http://wanwdv2.gyyx.cn/userCenter/myUpload';
                break;


            default:
                alert(":( 程序出错，请刷新页面重试！");
                break;
        }
    },

    //分页型布局(我的收藏,我的上传)
    pageLayout: function (options, upload) {
        var
            $this = $(this),
            //最终选择的布局类型(1:文章,2:图片,3:视频)
            curType = options.paramObj.SourcesType,
            //默认配置项（可以在调用处覆盖此配置）

            defaults = {
                url: USER_CONFIG.getCollectUrl,
                pageObj: "",
                pageSize: 12,
                curPageCls: "gy_pageon",
                pageData: "data.Values",
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
                paramObj: {},
                successFn: function (allCount) {
                    //如果是我的上传页面调用
                    if (upload == "upload") {

                    } else {//否则是我的收藏页面调用
                        //取消收藏
                        $(".js_DeleteThisWork").hover(function () {
                            $(this).parent().addClass("disp_tit");
                        }, function () {
                            $(this).parent().removeClass("disp_tit");
                        }).click(function () {
                            var code = $(this).attr("data-cancelcode");
                            var conf = confirm("您确定要删除这项内容吗？");
                            if (conf) {
                                $().cancelCollect(code, curType);
                            } else {
                                return false;
                            }
                            return false;
                        });
                    }
                    //显示图片数量
                    $(".js_CollectImgLi").getPicCount();

                },
                clickFn: function (set, cur) {
                    $('html, body').animate({
                        scrollTop: 234
                    }, 200);
                },
                debug: false
            };
        $.extend(defaults, options);
        $this.ajaxPage(defaults);
    },

    //取消收藏
    cancelCollect: function (code, type) {
        if (!code) {
            alert("取消收藏失败，请重试！");
            return false;
        }
        var lType = type && parseInt(type) || 1;
        $.ajax({
            url: USER_CONFIG.cancelCollectUrl,
            type: "GET",
            dataType: "JSON",
            data: {
                r: Math.random(),
                SourcesCode: code
            },
            success: function (data) {
                if (data) {
                    alert(data.Message);
                    if (data.IsSuccess) {
                        $().getCate(lType, function (setting) {
                            $().selectLayoutByType(setting);
                        });
                    }
                } else {
                    alert(":( 取消收藏失败，请重试！");
                }
            },
            error: function () {
                alert(":( 取消收藏程序接口出错，请重试！");
            }
        });
    },

    //获取图册里图片数量
    getPicCount: function ($disObj) {
        var nextSpan = "";
        var $disObj = $disObj || ".uc_picnum";
        $(this).hover(function () {
            nextSpan = $(this).find($disObj);
            $.ajax({
                url: "/Picture/GetPictureCount",
                type: "GET",
                dataType: "JSON",
                data: {
                    Code: $(this).attr("data-code"),
                    r: Math.random()
                },
                success: function (data) {
                    if (data.IsSuccess) {
                        nextSpan.html("共" + data.Values + "张").show();
                    }
                }
            });
        }, function () {
            nextSpan.hide();
        });
    },

    //获取我的角色列表
    getRoleList: function (data) {
        if (data.IsSuccess) {
            var
                dCont = data.List,
                divHtml = "";
            if (dCont && dCont[0]) {
                for (var i = 0; i < dCont.length; i++) {
                    var
                        //是否默认角色
                        isDefault = dCont[i].IsDefault,
                        //默与不默认标志
                        defBtnHtml = isDefault ?
                        ' class="js_qxbut role_defaultbtn">当前默认' :
                        ' class="js_upbut js_SetDefaultRole" data-code="' + dCont[i].Code + '">设为默认';

                    divHtml += '' +
                        '<div class="wd_js_l">' +
                        '    <span>' +
                        '        <img src="' + USER_CONFIG.portraitPath + dCont[i].Picture + '.jpg"></span>' +
                        '    <p>角色：' + dCont[i].NickName + '</p>' +
                        '    <p>服务器：' + dCont[i].ServerName + '</p>' +
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
                    $().setSyncForm();
                    return false;
                });

                //设置默认角色
                $(".js_SetDefaultRole").click(function () {
                    var code = $(this).attr("data-code");
                    $().setDefaultRole(code);
                    return false;
                });
            }
        }
    },
    //获取验证码类型
    getCaptchaType: function () {

    },

    //配置同步角色表单
    setSyncForm: function () {
        var
            $netSel = $("#js_Net"),
            $serverSel = $("#js_Server"),
            $qq = $("#js_QQ").val("");

        //刷新验证码
        $(".Captcha").attr("src", USER_CONFIG.captchaUrl + "?r=" + Math.random());
        $(".Captcha").click(function () {
            $(".Captcha").attr("src", USER_CONFIG.captchaUrl + "?r=" + Math.random());
        });
        //显示表单
        $("#js_RoleList").append($("#js_SyncForm").show());
        //绑定服务器
        $netSel.bindServer({
            url: USER_CONFIG.getRoleServerListUrl,
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
            if (validData && able == "true") {
                $(this).attr("data-able", false);
                var subFn = function () {

                    $().syncRole(validData,$(".js_insertCaptcha").val());
                };
                switch (chinaCaptcha1.captcahSwitchOpen) {
                    case -1://不需要验证码
                    case 0://需要普通验证码
                        subFn();
                        break;
                    case 1://需要中文验证码
                        chinaCaptcha1.subFn = subFn;
                        console.log(chinaCaptcha1.subFn);
                        chinaCaptcha1.createCaptchaPop();
                        break;
                    default:
                        break;
                }
            }
            return false;
        });
    },

    //验证同步角色表单
    validSyncForm: function () {
        var
            netId = $("#js_Net").val(),
            $serverSel = $("#js_Server"),
            serverId = $serverSel.val(),
            $serverOpt = $serverSel.find("option:selected"),
            serverName = $.trim($serverOpt.text()),
            serverIp = $serverOpt.attr("data-ip"),
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
            // else if (!captcha) {
            //     $error.html("请输入验证码！").show();
            //     return false;
            // }
        else {
            return {
                ServerName: serverName,
                ServerId: serverId,
                NetId: netId,
                QQ: qq,
                ServerIP: serverIp,
            };
        }
    },

    //同步角色
    syncRole: function (sdata,captcha) {
        var ddata = {
            r: Math.random()
        }
	var captcha1={ captcha: captcha }
        $.extend(ddata, sdata,captcha1);
        $.ajax({
            url: USER_CONFIG.syncRoleUrl,
            type: "GET",
            dataType: "JSON",
            timeout: 5000,
            data: ddata,
            success: function (data) {
                if (data.IsSuccess) {
                    if (data.Message == "1小时内同步次数过多,请重新登录") {
                        alert("1小时内同步次数过多,请重新登录");
                        $().loginout();
                    } else {
                        alert("同步成功！");
                        $().refreshPage();
                    }
                } else {
                    alert(data.Message);
                    if (data.Values == "-3") {
                        $().refreshPage();
                    } else if (data.Message == "1小时内同步次数过多,请重新登录") {
                        $().loginout();
                    } else if (data.Message == "验证码错误") {
                        $(".js_refreshChinaCaptcha").click();
                    } else {
                        $(".js_chinaCaptcha_Alert").remove();
                    }
                }
                $(".Captcha").attr("src", USER_CONFIG.captchaUrl + "?r=" + Math.random());
                $("#js_SyncRole").attr("data-able", true);
                $(".js_insertCaptcha").val("");
            },
            error: function (req, stau) {
                $("#js_SyncRole").attr("data-able", true);
                if (stau = "timeout") {
                    alert(" :(请求接口超时，请重试！");
                    return false;
                }
                alert(" :(程序接口出错，请刷新页面重试！");
            }
        });
    },

    //设置默认角色
    setDefaultRole: function (code) {
        $.ajax({
            url: USER_CONFIG.setDefaultRoleUrl,
            type: "GET",
            dataType: "JSON",
            data: {
                r: Math.random(),
                Code: code
            },
            success: function (data) {
                if (data.IsSuccess) {
                    alert("设置成功！");
                    $().refreshPage();
                } else {
                    if (data.Values == "-3") {
                        alert(data.Message);
                        $().refreshPage();
                    }
                    alert(":( 设置失败，请刷新页面重试！");
                }

            },
            error: function () {
                alert(":( 程序出错，请刷新页面重试！");
            }
        });
    },

    //当select改变时隐藏错误消息
    changeHideError: function ($error, defaultVal, errorTxt) {
        var $this = $(this)
        if ($this.val() != defaultVal && $error.html() == errorTxt) {
            $error.hide();
        }
    },

    //绑定服务器
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
                type: "POST",
                dataType: "JSON",
                data: {
                    netType: thisVal,
                    gameId: 2,
                    r: Math.random()
                },
                success: function (data) {
                    if (data.IsSuccess) {
                        var dCont = data.List;
                        for (var i = 0; l = dCont.length, i < l; i++) {
                            optHtml += "<option data-ip='" + dCont[i].ServerIP + "' value='" + dCont[i].ServerCode + "'>&nbsp;" + dCont[i].ServerName + "</option>"
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

    //获取指定url参数值
    getQuery: function (name) {
        var
            reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"),
            r = window.location.search.substr(1).match(reg);
        return null != r ? r[2] : null
    },

    //将毫秒转成年月日时分秒
    changeTime: function (time, needSec, dateSpstr, secSpstr) {
        if (time != null) {
            var dateArr = [];
            var secArr = [];
            var dateSpstr = dateSpstr ? dateSpstr : "-";
            var secSpstr = secSpstr ? secSpstr : ":";
            var date = new Date(parseInt(time.replace("/Date(", "").replace(")/", ""), 10));
            var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
            var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();

            dateArr.push(date.getFullYear(), month, currentDate);
            if (needSec) {
                var currentHour = date.getHours();
                var currentMin = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
                var currentSec = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
                secArr.push(currentHour, currentMin, currentSec);
                return dateArr.join(dateSpstr) + " " + secArr.join(secSpstr);
            } else {
                return dateArr.join(dateSpstr)
            }
        } else {
            return "";
        }
    },

    //刷新页面防末尾"#"不刷新
    refreshPage: function () {
        var href = window.location.href;
        href = href.replace(/#*$/, '');
        window.location.href = href;
    },
    //退出登录
    loginout: function () {
        $.ajax({
            url: USER_CONFIG.Loginut,
            type: "GET",
            dataType: "jsonp",
            jsonp: "jsoncallback",
            data: { r: Math.random() },
            success: function (d) {
                if (d.IsSuccess) { location.reload(); }
                else { alert(d.Message); }
            }
        });
    },
    //空数据处理
    emptySet: function (str, newStr) {
        if (!str || str == "null" || str == "" || str == undefined || str == null) {
            return newStr || "";
        } else {
            return str;
        }
    }
});

//用户中心公用加载
$(function () {
    $().headPortrait();
});
