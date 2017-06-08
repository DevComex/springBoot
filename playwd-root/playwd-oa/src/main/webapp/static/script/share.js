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
1、新增QQ空间（Qzone,qqzone）的一键分享设置
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
                return encodeURIComponent($("#" + b).text())
            } else {
                return ""
            }
        }, setid: function () {
            if (typeof (shareid) == "undefined") {
                return null
            } else {
                return shareid
            }
        }, qqzone: function () { //Qzone
            var _u = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?title=" + this.title() + "&url=" + this.url() + "&summary=" + this.content(this.setid());
            openwin(_u);
        }, kaixin: function () { //开心网
            var _u = "http://www.kaixin001.com/repaste/share.php?rtitle=" + this.title() + "&rurl=" + this.url();
            openwin(_u);
        }, renmin: function () { //人民网
            var _u = "http://t.people.com.cn/toshareinfo.action?appkey=5554506767&url=" + this.url() + "&title=" + this.title() + this.content(this.setid());
            openwin(_u);
        }, fenghuang: function () { //凤凰网
            var _u = "http://t.ifeng.com/interface.php?_c=share&_a=share&sourceUrl=" + this.url() + "&title=" + this.title();
            openwin(_u);
        }, renren: function () { //人人网

            var _u = "http://widget.renren.com/dialog/share?resourceUrl=" + this.url() + "&title=" + this.title() + '&description=' + this.content(this.setid());
            openwin(_u);
        }, sinaminiblog: function () { //新浪网
            var _u = "http://v.t.sina.com.cn/share/share.php?url=" + this.url() + "&title=" + this.title() + this.content(this.setid()) + "&content=utf-8&source=&sourceUrl=&pic=";
            openwin(_u);
        }, baidusoucang: function () { //百度搜藏
            var _u = "http://cang.baidu.com/do/add?it=" + this.title() + "&iu=" + this.url() + "&dc=" + this.content(this.setid()) + "&fr=ien#nw=1";
            openwin(_u);
        }, wangyi: function () { //网易
            var _u = "http://t.163.com/article/user/checkLogin.do?source=%E7%BD%91%E6%98%93%E6%96%B0%E9%97%BB%20%20%20&link=" + this.url() + "&info=" + this.content(this.setid()) + this.url();
            openwin(_u);
        }, fenghuangkuaibo: function () { //凤凰快博
            var _u = "http://k.ifeng.com/interface/index?type=blog" + "&title=" + this.title() + "&desc=" + this.content(this.setid()) + "&sourceUrl" + this.url();
            openwin(_u);
        }, mogujie: function () { //蘑菇街
            var _u = 'http://www.mogujie.com/mshare?url=' + encodeURIComponent(location.href) + '&content=' + this.content(this.setid());
            openwin(_u);
        }, douban: function () { //豆瓣网
            var _u = "http://www.douban.com/recommend/?url=" + this.url() + "&title=" + this.title() + "&v=1";
            openwin(_u);
        }
    };
    window.share = a
})();

$(function () {
    $(".renren").click(function () {
        share.renren();
    });
    $(".renmin").click(function () {
        share.renmin();
    });
    $(".xinlang").click(function () {
        share.sinaminiblog();
    });
    $(".fenghuang").click(function () {
        share.fenghuang();
    });
    $(".douban").click(function () {
        share.douban();
    });
    $(".kaixin").click(function () {
        share.kaixin();
    });
    $(".qqzone").click(function () {
        share.qqzone();
    });
    $(".fenghuangkuaibo").click(function () {
        share.fenghuangkuaibo();
    });
    $(".wangyi").click(function () {
        share.wangyi();
    });
    $(".baidusoucang").click(function () {
        share.baidusoucang();
    });
    $(".qqweibo").click(function () {
        share.qqweibo();
    });
    $(".mogujie").click(function () {
        share.mogujie();
    });
    var shareinfo = $(".share_info").text();
    var txtlth = shareinfo.length;
    //if (txtlth > 200) {
        var sharetxt = shareinfo.substr(0, 120);
        sharetxt = $.trim(sharetxt); //去掉前后空格！
        $("#fenxiang").append(sharetxt);
    //}
    $(".moreshare,.more-con").live("mouseover", function () {
        $(".more-con").show();
    });
    $(".moreshare,.more-con").live("mouseout", function () {
        $(".more-con").hide();
    });
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

function postToWb() { //腾讯微博特殊加载
    var _t = encodeURI(document.title);
    var _url = encodeURI(document.location);
    var _appkey = encodeURI("appkey");
    var _summary = encodeURI($("#fenxiang").text());
    var _u = 'http://v.t.qq.com/share/share.php?title=' + _t + '&summary=' + _summary + '&url=' + _url + '&appkey=' + _appkey;
    openwin(_u);
    //window.showModalDialog(_u, ',', "dialogWidth=600px;dialogHeight=350px;resizable:yes;");
}