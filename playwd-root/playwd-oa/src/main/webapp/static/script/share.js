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
1������QQ�ռ䣨Qzone,qqzone����һ����������
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
        }, kaixin: function () { //������
            var _u = "http://www.kaixin001.com/repaste/share.php?rtitle=" + this.title() + "&rurl=" + this.url();
            openwin(_u);
        }, renmin: function () { //������
            var _u = "http://t.people.com.cn/toshareinfo.action?appkey=5554506767&url=" + this.url() + "&title=" + this.title() + this.content(this.setid());
            openwin(_u);
        }, fenghuang: function () { //�����
            var _u = "http://t.ifeng.com/interface.php?_c=share&_a=share&sourceUrl=" + this.url() + "&title=" + this.title();
            openwin(_u);
        }, renren: function () { //������

            var _u = "http://widget.renren.com/dialog/share?resourceUrl=" + this.url() + "&title=" + this.title() + '&description=' + this.content(this.setid());
            openwin(_u);
        }, sinaminiblog: function () { //������
            var _u = "http://v.t.sina.com.cn/share/share.php?url=" + this.url() + "&title=" + this.title() + this.content(this.setid()) + "&content=utf-8&source=&sourceUrl=&pic=";
            openwin(_u);
        }, baidusoucang: function () { //�ٶ��Ѳ�
            var _u = "http://cang.baidu.com/do/add?it=" + this.title() + "&iu=" + this.url() + "&dc=" + this.content(this.setid()) + "&fr=ien#nw=1";
            openwin(_u);
        }, wangyi: function () { //����
            var _u = "http://t.163.com/article/user/checkLogin.do?source=%E7%BD%91%E6%98%93%E6%96%B0%E9%97%BB%20%20%20&link=" + this.url() + "&info=" + this.content(this.setid()) + this.url();
            openwin(_u);
        }, fenghuangkuaibo: function () { //��˿첩
            var _u = "http://k.ifeng.com/interface/index?type=blog" + "&title=" + this.title() + "&desc=" + this.content(this.setid()) + "&sourceUrl" + this.url();
            openwin(_u);
        }, mogujie: function () { //Ģ����
            var _u = 'http://www.mogujie.com/mshare?url=' + encodeURIComponent(location.href) + '&content=' + this.content(this.setid());
            openwin(_u);
        }, douban: function () { //������
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
        sharetxt = $.trim(sharetxt); //ȥ��ǰ��ո�
        $("#fenxiang").append(sharetxt);
    //}
    $(".moreshare,.more-con").live("mouseover", function () {
        $(".more-con").show();
    });
    $(".moreshare,.more-con").live("mouseout", function () {
        $(".more-con").hide();
    });
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

function postToWb() { //��Ѷ΢���������
    var _t = encodeURI(document.title);
    var _url = encodeURI(document.location);
    var _appkey = encodeURI("appkey");
    var _summary = encodeURI($("#fenxiang").text());
    var _u = 'http://v.t.qq.com/share/share.php?title=' + _t + '&summary=' + _summary + '&url=' + _url + '&appkey=' + _appkey;
    openwin(_u);
    //window.showModalDialog(_u, ',', "dialogWidth=600px;dialogHeight=350px;resizable:yes;");
}