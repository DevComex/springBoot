/*
    一键分享 v.1.0.0.2
	update: 2013/7/02
	by ligen
*/
/* ============================= 参数配置说明 ============================== */
/* a: 获取本页的title信息、href地址信息和描述信息						     */
/* showModalDialog: 弹出窗体显示分享地址和控制弹出窗体大小					 */
/* postToWb: 腾讯微博特殊弹层格式						           			 */
/* setid: 获取页面中id="fenxiang"的内容加载到分享页面的概要中		         */
/* sharetxt: 截取分享中的描述字符串默认140字                               	 */
/* openwin(url): 整理的分享地址参数，在新页面中打开                          */
/* .live: 显示更多分享的层                                  		         */
/* ========================================================================  */

/*---------------------------up data v1.0.0.1----------------------------
1、新增QQ空间（Qzone）的一键分享设置
2、修正了弹窗位置不居中问题
3、修正新浪、腾讯微博分享不显示摘要问题
4、修正了打开方式，采用新页面打开方式，不用窗体的方式打开
*/
//获取标题、路径、描述等信息
/*---------------------------up data v1.0.0.2----------------------------
1、去掉了分享内容的前后空格（&nbsp;）
2、修正了分享字符的限制为120个字符(解决百度不自动截取问题)
3、将描述的取值改为text(),避免分享内容出现&nbsp;
*/
var shareid = "fenxiang";
(function () {
    var a = {
        url: function () {
            return encodeURIComponent(window.location.href)
        }, title: function () {
            return encodeURIComponent(window.document.title)
        }, content: function (b) {
            if (b) {
                return encodeURIComponent(b);
                
                // return encodeURIComponent($("#" + b).text())
            } else {
                return ""
            }
        }, setid: function () {
            if (typeof (shareid) == "undefined") {
                return null
            } else {
                return shareid
            }
        }, Qzone: function (url, title, content) { //Qzone
            var _u = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?title=" + title + "&url=http://wanwd.gyyx.cn/article/" + url + "&summary=" + this.content(content);
            openwin(_u);
        }, kaixin: function (url, title) { //开心网
            var _u = "http://www.kaixin001.com/repaste/share.php?rtitle=" + title + "&rurl=http://wanwd.gyyx.cn/article/" + url;
            openwin(_u);
        }, renmin: function (url, title, content) { //人民网
            
            var _u = "http://t.people.com.cn/toshareinfo.action?appkey=5554506767&url=http://wanwd.gyyx.cn/article/" + url + "&title=" + title + this.content(content);
            openwin(_u);
        }, fenghuang: function (url, title) { //凤凰网
            var _u = "http://t.ifeng.com/interface.php?_c=share&_a=share&sourceUrl=http://wanwd.gyyx.cn/article/" + url + "&title=" + title;
            openwin(_u);
        }, renren: function (url, title, content) { //人人网
            // alert(content);
            var _u = "http://widget.renren.com/dialog/share?resourceUrl=http://wanwd.gyyx.cn/article/" + url + "&title=" + title + '&description=' + this.content(content);
            openwin(_u);
        }, sinaminiblog: function (url, title, content) { //新浪网
            var _u = "http://v.t.sina.com.cn/share/share.php?url=http://wanwd.gyyx.cn/article/" + url + "&title=" + title + this.content(content) + "&content=utf-8&source=&sourceUrl=&pic=";
            openwin(_u);
        }, baidusoucang: function (url, title, content) { //百度搜藏
            var _u = "http://cang.baidu.com/do/add?it=" + title + "&iu=http://wanwd.gyyx.cn/article/" + url + "&dc=" + this.content(content) + "&fr=ien#nw=1";
            openwin(_u);
        }, wangyi: function (url, title, content) { //网易
            var _u = "http://t.163.com/article/user/checkLogin.do?source=%E7%BD%91%E6%98%93%E6%96%B0%E9%97%BB%20%20%20&link=http://wanwd.gyyx.cn/article/" + url + "&info=" + this.content(content) + "http://wanwd.gyyx.cn/article/" + url;
            openwin(_u);
        }, fenghuangkuaibo: function (url, title, content) { //凤凰快博
            var _u = "http://k.ifeng.com/interface/index?type=blog" + "&title=" + title + "&desc=" + this.content(content) + "&sourceUrlhttp://wanwd.gyyx.cn/article/" + url;
            openwin(_u);
        }, mogujie: function (url,content) { //蘑菇街
            var _u = 'http://www.mogujie.com/mshare?url=' + encodeURIComponent("http://wanwd.gyyx.cn/article/" + url) + '&content=' + this.content(content);
            openwin(_u);
        }, douban: function (url, title) { //豆瓣网
            var _u = "http://www.douban.com/recommend/?url=http://wanwd.gyyx.cn/article/" + url + "&title=" + title + "&v=1";
            openwin(_u);
        }
    };
    window.share = a
})();

$(function () {
    $(".renren").live("click", function () {
        var title = encodeURIComponent($(this).parents(".graplist").attr("data-title"));
        var url = encodeURIComponent($(this).parents(".graplist").attr("data-url"));
        var content = $(this).parents(".graplist").find(".txt span").text();
        //alert(title + "|" + url);
        share.renren(url, title, content);
    });
    $(".renmin").live("click", function () {
        var title = encodeURIComponent($(this).parents(".graplist").attr("data-title"));
        var url = encodeURIComponent($(this).parents(".graplist").attr("data-url"));
        var content = $(this).parents(".graplist").find(".txt span").text();
        share.renmin(url, title, content);
    });
    $(".xinlang").live("click", function () {
        var title = encodeURIComponent($(this).parents(".graplist").attr("data-title"));
        var url = encodeURIComponent($(this).parents(".graplist").attr("data-url"));
        var content = $(this).parents(".graplist").find(".txt span").text();
        share.sinaminiblog(url, title, content);
    });
    $(".fenghuang").live("click", function () {
        var title = encodeURIComponent($(this).parents(".graplist").attr("data-title"));
        var url = encodeURIComponent($(this).parents(".graplist").attr("data-url"));
        
        share.fenghuang(url, title);
    });
    $(".douban").live("click", function () {
        var title = encodeURIComponent($(this).parents(".graplist").attr("data-title"));
        var url = encodeURIComponent($(this).parents(".graplist").attr("data-url"));
        share.douban(url, title);
    });
    $(".kaixin").live("click", function () {
        var title =encodeURIComponent( $(this).parents(".graplist").attr("data-title"));
        var url = encodeURIComponent($(this).parents(".graplist").attr("data-url"));
        share.kaixin(url, title);
    });
    $(".Qzone").live("click", function () {
        var title = encodeURIComponent($(this).parents(".graplist").attr("data-title"));
        var url = encodeURIComponent( $(this).parents(".graplist").attr("data-url"));
        var content = $(this).parents(".graplist").find(".txt span").text();
        share.Qzone(url, title, content);
    });
    $(".fenghuangkuaibo").live("click", function () {
        var title = encodeURIComponent( $(this).parents(".graplist").attr("data-title"));
        var url = encodeURIComponent( $(this).parents(".graplist").attr("data-url"));
        var content = $(this).parents(".graplist").find(".txt span").text();
        share.fenghuangkuaibo(url, title, content);
    });
    $(".wangyi").live("click", function () {
        var title = encodeURIComponent( $(this).parents(".graplist").attr("data-title"));
        var url = encodeURIComponent( $(this).parents(".graplist").attr("data-url"));
        var content = $(this).parents(".graplist").find(".txt span").text();
        share.wangyi(url, title, content);
    });
    $(".baidusoucang").live("click", function () {
        var title =encodeURIComponent(  $(this).parents(".graplist").attr("data-title"));
        var url =encodeURIComponent(  $(this).parents(".graplist").attr("data-url"));
        var content = $(this).parents(".graplist").find(".txt span").text();
        share.baidusoucang(url, title, content);
    });
    $(".qqweibo").live("click", function () {
        share.qqweibo();
    });
    $(".mogujie").live("click", function () {
        var url =encodeURIComponent(  $(this).parents(".graplist").attr("data-url"));
        var content = encodeURIComponent( $(this).parents(".graplist").find(".txt span").text());
        share.mogujie(url, content);
    });
    var shareinfo = $(".share_info").text();
    var txtlth = shareinfo.length;
    if (txtlth > 200) {
        var sharetxt = shareinfo.substr(0, 120);
        sharetxt = $.trim(sharetxt); //去掉前后空格！
        $("#fenxiang").append(sharetxt);
    }
    $(".moreshare").live("mouseover", function () {
        $(this).parents(".share-con ").find(".more-con").show();
        //$(".more-con").show();
    });
    $(".more-con").live("mouseover", function () {
        $(this).show();
    });
    $(".moreshare").live("mouseout", function () {
        $(this).parents(".share-con ").find(".more-con").hide();
       // $(".more-con").hide();
    });
    $(".more-con").live("mouseout", function () {
        $(this).hide();
    });
});

$(".tengxunweibo").live("click", function () {

    var title = encodeURIComponent($(this).parents(".graplist").attr("data-title"));
    var url = encodeURIComponent($(this).parents(".graplist").attr("data-url"));
    var content = $(this).parents(".graplist").find(".txt span").text();
    postToWb(title, url, content);
});

//新窗口打开方法
function openwin(url) {
    var a = document.createElement("a");
    a.setAttribute("href", url);
    a.setAttribute("target", "_blank");
    a.setAttribute("id", "openwin");
    document.body.appendChild(a);
    a.click();
}

function postToWb(title, url, content) { //腾讯微博特殊加载
    var _t = encodeURI(document.title);
    var _url = encodeURI(document.location);
    var _appkey = encodeURI("appkey");
    var _content = encodeURI(content);
    var _u = 'http://v.t.qq.com/share/share.php?title=' + title + '&summary=' + _content + '&url=http://wanwd.gyyx.cn/article/' + url + '&appkey=' + _appkey;
    openwin(_u);
    //window.showModalDialog(_u, ',', "dialogWidth=600px;dialogHeight=350px;resizable:yes;");
}