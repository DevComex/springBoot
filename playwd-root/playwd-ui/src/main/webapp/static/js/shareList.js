/*
    һ������ v.1.0.0.2
	update: 2013/7/02
	by ligen
*/
/* ============================= ��������˵�� ============================== */
/* a: ��ȡ��ҳ��title��Ϣ��href��ַ��Ϣ��������Ϣ						     */
/* showModalDialog: ����������ʾ�����ַ�Ϳ��Ƶ��������С					 */
/* postToWb: ��Ѷ΢�����ⵯ���ʽ						           			 */
/* setid: ��ȡҳ����id="fenxiang"�����ݼ��ص�����ҳ��ĸ�Ҫ��		         */
/* sharetxt: ��ȡ�����е������ַ���Ĭ��140��                               	 */
/* openwin(url): ����ķ����ַ����������ҳ���д�                          */
/* .live: ��ʾ�������Ĳ�                                  		         */
/* ========================================================================  */

/*---------------------------up data v1.0.0.1----------------------------
1������QQ�ռ䣨Qzone����һ����������
2�������˵���λ�ò���������
3���������ˡ���Ѷ΢��������ʾժҪ����
4�������˴򿪷�ʽ��������ҳ��򿪷�ʽ�����ô���ķ�ʽ��
*/
//��ȡ���⡢·������������Ϣ
/*---------------------------up data v1.0.0.2----------------------------
1��ȥ���˷������ݵ�ǰ��ո�&nbsp;��
2�������˷����ַ�������Ϊ120���ַ�(����ٶȲ��Զ���ȡ����)
3����������ȡֵ��Ϊtext(),����������ݳ���&nbsp;
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
        }, kaixin: function (url, title) { //������
            var _u = "http://www.kaixin001.com/repaste/share.php?rtitle=" + title + "&rurl=http://wanwd.gyyx.cn/article/" + url;
            openwin(_u);
        }, renmin: function (url, title, content) { //������
            
            var _u = "http://t.people.com.cn/toshareinfo.action?appkey=5554506767&url=http://wanwd.gyyx.cn/article/" + url + "&title=" + title + this.content(content);
            openwin(_u);
        }, fenghuang: function (url, title) { //�����
            var _u = "http://t.ifeng.com/interface.php?_c=share&_a=share&sourceUrl=http://wanwd.gyyx.cn/article/" + url + "&title=" + title;
            openwin(_u);
        }, renren: function (url, title, content) { //������
            // alert(content);
            var _u = "http://widget.renren.com/dialog/share?resourceUrl=http://wanwd.gyyx.cn/article/" + url + "&title=" + title + '&description=' + this.content(content);
            openwin(_u);
        }, sinaminiblog: function (url, title, content) { //������
            var _u = "http://v.t.sina.com.cn/share/share.php?url=http://wanwd.gyyx.cn/article/" + url + "&title=" + title + this.content(content) + "&content=utf-8&source=&sourceUrl=&pic=";
            openwin(_u);
        }, baidusoucang: function (url, title, content) { //�ٶ��Ѳ�
            var _u = "http://cang.baidu.com/do/add?it=" + title + "&iu=http://wanwd.gyyx.cn/article/" + url + "&dc=" + this.content(content) + "&fr=ien#nw=1";
            openwin(_u);
        }, wangyi: function (url, title, content) { //����
            var _u = "http://t.163.com/article/user/checkLogin.do?source=%E7%BD%91%E6%98%93%E6%96%B0%E9%97%BB%20%20%20&link=http://wanwd.gyyx.cn/article/" + url + "&info=" + this.content(content) + "http://wanwd.gyyx.cn/article/" + url;
            openwin(_u);
        }, fenghuangkuaibo: function (url, title, content) { //��˿첩
            var _u = "http://k.ifeng.com/interface/index?type=blog" + "&title=" + title + "&desc=" + this.content(content) + "&sourceUrlhttp://wanwd.gyyx.cn/article/" + url;
            openwin(_u);
        }, mogujie: function (url,content) { //Ģ����
            var _u = 'http://www.mogujie.com/mshare?url=' + encodeURIComponent("http://wanwd.gyyx.cn/article/" + url) + '&content=' + this.content(content);
            openwin(_u);
        }, douban: function (url, title) { //������
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
        sharetxt = $.trim(sharetxt); //ȥ��ǰ��ո�
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

//�´��ڴ򿪷���
function openwin(url) {
    var a = document.createElement("a");
    a.setAttribute("href", url);
    a.setAttribute("target", "_blank");
    a.setAttribute("id", "openwin");
    document.body.appendChild(a);
    a.click();
}

function postToWb(title, url, content) { //��Ѷ΢���������
    var _t = encodeURI(document.title);
    var _url = encodeURI(document.location);
    var _appkey = encodeURI("appkey");
    var _content = encodeURI(content);
    var _u = 'http://v.t.qq.com/share/share.php?title=' + title + '&summary=' + _content + '&url=http://wanwd.gyyx.cn/article/' + url + '&appkey=' + _appkey;
    openwin(_u);
    //window.showModalDialog(_u, ',', "dialogWidth=600px;dialogHeight=350px;resizable:yes;");
}