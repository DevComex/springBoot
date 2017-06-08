/*
by xuyan 2013/12/26
Version:v1.16
官网-页游头部导航、底部信息
===============Upload log===================
Version:v1.22 by xuyingchao
2016/03/24
1.修改长按回车多次调用中文验证码构造函数问题

Version:v1.21 by shiyawei
2016/01/29
1.回滚到2016-1-25版本。
2.更新登录框第一次登录失败后，关闭登录框，再次登录无法登录（修改1205行和1300行添加事件）。

Version:v1.20 by shiyawei
2015/12/27
1.更新输入用户名离焦后请求验证码类型接口
Version:v1.1
2013/7/11
更新登陆框样式 by xuyan

Version:v1.2
2013.8.11 by xyanli
1.修复点击登录没有反应问题，修改593、664行“点击登录或回车提交表单时，登录成功后页面刷新js语句修改”;
1.点击退出没反应，修改223行“点击退出后页面刷新js语句修改”。

Version:v1.3
2013.8.12 by xuyan
1.Showtop方法添加参数AutomaticOpenLogin，值为true或者空。未登录情况，是否自动弹出登陆层， Showtop()不弹，Showtop(true)弹

Version:v1.4
2013.8.13 by xuyingchao
1.修复游戏图片切换而图片链接为切换Bug
2013.8.13 by xuyan
2.增加登陆层用户名输入框限制字数20

Version:v1.5
2013.8.14 by xuyan
1.解决登陆层搜狗记住密码的文字覆盖
2.修改样式，解决底部页面缩小，拉动横向滚动条，底部背景不全；修改样式和Showtop方法中html代码片段，添加class=top_body_bg的div，解决拉动横向滚动条，头部背景不全
3.增加public_bottom_data.txt文件，修改ShowBottom方法。访问底部信息数据文件，根据js参数，读取不同的底部信息
4.修复由于v1.4-1造成的‘更多精彩游戏’连接错误

Version:v1.6
2013.8.19 by xuyan
1.修复js在accont站点在退出不跳转
2.修复光宇乾坤、矩阵密保显示问题
3.修复登陆验证码输入框出现后，换账号，验证码输入框不消失的问题

Version:v1.7
2013.9.6 by xuyan
1.更换样式，使用新版样式
2.游戏列表分左右两栏处理

Version:v1.8
2013.9.16 by xuyan
1.ShowBottom添加参数;如果想把底部背景色改为#f4f4f4，只需在ShowBottom()中传入颜色值（不带#号），例如ShowBottom("f4f4f4")
2.添加注册层,本次针对落地页,按钮为'进入游戏','老用户登录'.
3.alertFn方法添加参数:isRefPage是否刷新页面,isNoRefPageFun登陆后不刷新页面，执行的方法,具体参看方法上注释 

Version:v1.9 2013.9.25 by xuyan
1.登陆层改成标签的，含有密码登陆和短信登陆
2.添加补全资料层

Version:v1.10 2013.10.10 by xuyan
1.重新改版样式

Version:v1.11 2013.10.22 by xuyan
1.删除登陆后弹出补填资料层的功能，保留层
2.添加设置密码层和对应功能

Version:v1.12 2013.10.30 by xuyan
1.添加点击进入游戏按钮，记录点击情况，成功与否没关系，不影响后面流程

Version:v1.13 2013.11.13 by xuyan
1.导入jquery.bgiframe.js，解决ie6下下拉框遮挡

Version:v1.14 2013.12.23 by xuyan
1.底部文件放置美编处。地址 http://static.gyyx.cn/Stage/public_bottom_data.txt

Version:v1.15 2013.12.26 by xuyan
1.添加http://reg.gyyx.cn/login/AjaxCompletePwd  设置密码接口，修改了alertPassWord（）方法
0失败 1成功 -1未登录

Version:v1.16 2014.6.4 by xuyingchao
1.将手游平台链接修改为：http://shouyou.gyyx.cn

Version:v1.17 2014.11.14 by xuyan
1.头部导航的html结构上，添加用户登陆标示，429行
2.alertFn方法更新，根据用户登陆标示判断是否弹出登陆层，或者执行回调函数isNoRefPageFun

Version:v1.18 2015.1.28 by pengjia
1.修改 获取页游服务器列表接口GetGameInfo 链接地址 为：api.officialsite.gyyx.cn/ToGame/GetNewGameInfoForTopNav
2.修改 头部导航，游戏服务器列表的连接ToGameUrl 链接地址 为：api.officialsite.gyyx.cn/Frame/GetFrmMainUrl
  并添加_toGameFrm函数,

  Version:v1.19 2015.5.19 by ligen
1.登陆验证时增加服务器其他错误返回信息显示

version:v1.2 2016-2-17 by Hu Junhe
增加导航栏手机认证四种状态

Version:v1.21 2016-2-24 by chenqiaoling
更改头部导航的数据文件和底部导航的数据文件，改为由CMS管理生成 

Version:v1.22 2016-7-28 by maoxiangmin
1.添加光宇官网与充值中心页面弹层悬浮窗功能
2.所有游戏(除问道)，增加左侧浮窗按钮

Version:v1.22 2016-8-04 by maoxiangmin
配合《绝世武神》手游活动悬浮窗功能关闭
alertTra(关闭此方法)；
============================================
*/


/*页游弹层广告延迟加载方法----start*/
var str = new Array("http://s.gyyx.cn/publictop/images/adsimg.jpg http://svip.gyyx.cn/rebate/index.html", "http://s.gyyx.cn/publictop/images/adsimg.jpg http://svip.gyyx.cn/rebate/index.html");
var aimg;
var bhref = "";
aimg = str[parseInt(Math.random() * (str.length))];
var advHtml = '<div class="js_ads" style="width:100%;height:290px;background: #7f1b4f;position:relative;display:none;"><a href="' + aimg.split(/\s+/)[1] + '" target="_blank" style="width:100%;height:290px;display:inline-block;overflow:hidden;text-align:center;"><img src="' + aimg.split(/\s+/)[0] + '"></a><span class="js_adsclose" style="position:absolute;right:5%;bottom:2%;cursor:pointer;color:#fff;font-size:12px;">关闭</span></div>';
function aa() {
    var adslen = $("body", parent.document).find(".js_ads").length;
    if (adslen == 2) {
        $("body", parent.document).find(".js_ads").eq(1).remove();
    }
}
/*页游弹层广告延迟加载方法----end*/

/*判断URL官网类型执行操作---start*/

var urlLocal = location.href;
//监测链接对应
/*
 官网首页：
http://news.gyyx.cn/Counter.ashx?pc=8136&sc=17792&mc=6309&gc=20028&ul=http%3a%2f%2fjs.gyyx.cn%2fhuodong%2flwj2016%2findex.html&mt=4062&et=1
光宇页游：
http://news.gyyx.cn/Counter.ashx?pc=8136&sc=17794&mc=6309&gc=20028&ul=http%3a%2f%2fjs.gyyx.cn%2fhuodong%2flwj2016%2findex.html&mt=4062&et=1
所有游戏：
http://news.gyyx.cn/Counter.ashx?pc=8136&sc=17795&mc=6309&gc=20028&ul=http%3a%2f%2fjs.gyyx.cn%2fhuodong%2flwj2016%2findex.html&mt=4062&et=1
支付中心：
http://news.gyyx.cn/Counter.ashx?pc=8136&sc=17793&mc=6309&gc=20028&ul=http%3a%2f%2fjs.gyyx.cn%2fhuodong%2flwj2016%2findex.html&mt=4062&et=1
*/
function supCon(suoNum) {
    var urlSup = "javascript:void(0)";
    if (suoNum == "gysy") {
        urlSup = "http://news.gyyx.cn/Counter.ashx?pc=8136&sc=17792&mc=6309&gc=20028&ul=http%3a%2f%2fjs.gyyx.cn%2fhuodong%2flwj2016%2findex.html&mt=4062&et=1"
    }
    if (suoNum == "zfzx") {
        urlSup = "http://news.gyyx.cn/Counter.ashx?pc=8136&sc=17793&mc=6309&gc=20028&ul=http%3a%2f%2fjs.gyyx.cn%2fhuodong%2flwj2016%2findex.html&mt=4062&et=1"
    }
    if (suoNum == "gyyy") {
        urlSup = "http://news.gyyx.cn/Counter.ashx?pc=8136&sc=17794&mc=6309&gc=20028&ul=http%3a%2f%2fjs.gyyx.cn%2fhuodong%2flwj2016%2findex.html&mt=4062&et=1"
    }
  //因光宇页游页含右侧小人弹层，引起删除.class名更改
    var alertTraMon = ' <div id="mark" class="markdiv"></div><div class="public_nav_top_alert_T" style="display:block;">' +
                      '<div class="alertBox_img"><i class="traMonClose"></i><a href="' + urlSup + '" target="_blank"><img style="display: inline-block;" src="http://s.gyyx.cn/publictop/images/alertTraMonImg.png"/></a></div>' +
                          ' <a class="traIn" style="display:none;" href="javascript:void(0)"></a>'+
                      '</div>';
    $("body").append(alertTraMon);
    //用于觉得加载计算弹层宽高时的BUG
    setTimeout(function () {
        var winwidth = $(window).width();
        var thisleft = winwidth / 2 - $(".alertBox_img").width() / 2;
        var thistop = $(window).height() / 2 - $(".alertBox_img").height() / 2;
        var bodyheight = Math.max($(document.body).outerHeight(true), $(window).height());
        $(".alertBox_img").css({ 'left': thisleft, 'top': thistop });
        $(".public_nav_top_alert_T").siblings("#mark").css("height", bodyheight + 'px');
    },1000)
}
//所有游戏除问道
var allGameName = ["http://cs2.gyyx.cn/Home/index.shtml", "http://7s.gyyx.cn/index.shtml","http://7s.gyyx.cn/index.shtml","http://sm.gyyx.cn/Home/index.shtml", "http://ss.gyyx.cn/index_ss.html", "http://ss.gyyx.cn/index_ss.html", "http://xwb.gyyx.cn/Home/index.shtml", "http://tmld.gyyx.cn/index.shtml", "http://zhange.gyyx.cn/index.shtml", "http://dpsc.gyyx.cn/index.shtml", "http://jyjx.gyyx.cn/index.shtml", "http://dzz.gyyx.cn/index.shtml", "http://fy.gyyx.cn/index.shtml", "http://xxd.gyyx.cn/", "http://fsgj.gyyx.cn/index.shtml", "http://xbjz.gyyx.cn/index.shtml", "http://dzs.gyyx.cn/index.shtml", "http://zt.gyyx.cn/index.shtml", "http://tssj.gyyx.cn/index.shtml", "http://qfz.gyyx.cn/index.shtml", "http://xyzg.gyyx.cn/index.shtml", "http://gdjh.gyyx.cn/index.shtml", "http://ws.gyyx.cn/", "http://7s.gyyx.cn/", "http://sxd.gyyx.cn/Index.shtml", "http://sg.gyyx.cn/Index.shtml", "http://gnshz.gyyx.cn/Home/index.shtml", "http://zjfk.gyyx.cn/", "http://zjkp.gyyx.cn/", "http://js.gyyx.cn/Home/index.shtml", "http://dne.gyyx.cn/Home/index.shtml", "http://fl.gyyx.cn/Home/index.shtml", "http://yxwg.gyyx.cn/Home/index.shtml", "http://fr.gyyx.cn/", "http://jyjx.gyyx.cn/Server/ServerList.shtml", "http://tmld.gyyx.cn/Server/ServerList.shtml", "http://zhange.gyyx.cn/Server/ServerList.shtml", "http://dpsc.gyyx.cn/Server/ServerList.shtml", "http://jyjx.gyyx.cn/Server/ServerList.shtml", "http://dzz.gyyx.cn/Server/ServerList.shtml", "http://fy.gyyx.cn/Server/ServerList.shtml", "http://xxd.gyyx.cn/Server/ServerList.shtml", "http://fsgj.gyyx.cn/Server/ServerList.shtml", "http://xbjz.gyyx.cn/Server/ServerList.shtml", "http://dzs.gyyx.cn/Server/ServerList.shtml", "http://zt.gyyx.cn/Server/ServerList.shtml", "http://tssj.gyyx.cn/Server/ServerList.shtml", "http://qfz.gyyx.cn/Server/ServerList.shtml", "http://xyzg.gyyx.cn/Server/ServerList.shtml", "http://gdjh.gyyx.cn/Server/ServerList.shtml", "http://ws.gyyx.cn/Server/ServerList.shtml", "http://sxd.gyyx.cn/Server/ServerList.aspx", "http://sg.gyyx.cn/Server/ServerList.aspx",""].toString();
function alertTra() {
    if (urlLocal =="http://www.gyyx.cn/") {
        supCon("gysy");
        
    }
    
    if (urlLocal == "http://pay.gyyx.cn/") {
        supCon("zfzx")

    }
    if ( urlLocal == "http://web.gyyx.cn/") {
        supCon("gyyy");
        $(".traIn").css({
            "left": "0",
            "margin-left": "35px"
        })
    }
    /*
    for (var i = 0; i < allGameName.length; i++)
    {
        if (urlLocal == allGameName[i])
        {
            $(".allGameLeft").remove();
            $("body").append('<a class="allGameLeft" href="http://news.gyyx.cn/Counter.ashx?pc=8136&sc=17795&mc=6309&gc=20028&ul=http%3a%2f%2fjs.gyyx.cn%2fhuodong%2flwj2016%2findex.html&mt=4062&et=1" target="_blank"></a>');
            if (urlLocal == "http://dne.gyyx.cn/Home/index.shtml") {
                $(".allGameLeft").css({
                    "right": "0",
                    "margin-right": "35px"
                })
            }
        }
    }
    */
   if (allGameName.indexOf(urlLocal) != -1) {
        $(".allGameLeft").remove();
        $("body").append('<a class="allGameLeft" href="http://news.gyyx.cn/Counter.ashx?pc=8136&sc=17795&mc=6309&gc=20028&ul=http%3a%2f%2fjs.gyyx.cn%2fhuodong%2flwj2016%2findex.html&mt=4062&et=1" target="_blank"></a>');
        if (urlLocal == "http://dne.gyyx.cn/Home/index.shtml") {
            $(".allGameLeft").css({
            "right": "0",
           "margin-right":"35px"
            })
        }
    }
    $(".traIn").click(function () {
        $(".markdiv").show();
        $(".alertBox_img").show();
        $(".traIn").hide();
    });
    $(".traMonClose,#mark").click(function () {
        $(".alertBox_img").hide();
        $(".markdiv").hide();
        $(".traIn").show();
    });

}

/*判断URL官网类型---end*/

/* 登陆框和遮罩层html */
var alertHtml = '<div class="public_nav_top_alert">' +
                '    <div class="alertBox">' +
                '           <div class="alertBox_body">' +
                '               <a href="javascript:void(0)" class="closeimg" ></a>' +
    '               <ul class="title js_logintitle ewm_title">' +
                '                   <li class="cur"><span class="sec">密码登录</span><b>&nbsp;</b></li>' +
                '                   <li><span class="msm">短信登录</span><b>&nbsp;</b></li>' +
    '                   <li class="login_ewm"><span class="login_ewmS">二维码</span></li>' +
                '               </ul>' +
                '               <div class="publogindiv">' +
                '               <form  method="post" class="js_login_form">' +
                '                   <div class="errorp visbid js_pass_err"><span class="error"><span class="icon_error">&nbsp;</span><span id="errortext2" class="errortext2"></span></span>&nbsp;</div>' +
                '                   <div class="input js_input">' +
                '                       <b>*</b>' +
                '                       <span class="icon1">&nbsp;</span>' +
                '                       <span class="word">账号</span>' +
                '                       <input type="text" class="inps js_Account" id="Account" value="" name="Account" autocomplete="off"  maxlength="18" data-olduser="" />' +
                '                   </div>' +
                '                   <div class="input js_input">' +
                '                       <b>*</b>' +
                '                       <span class="icon2">&nbsp;</span>' +
                '                       <span class="word">密码</span>' +
                '                       <input type="password" class="inps js_Password" id="Password" value=""  name="Password" autocomplete="off" />' +
                '                   </div>' +
               '                   <div class="inputcode clearfix ">' +
                '                       <div class="js_captcha disnone">' +
                '                           <div class="left">' +
                '                               <b>*</b>' +
                '                               <span class="word">验证码</span>' +
                '                               <input type="text" class="inpsCaptcha" name="Captcha" id="Captcha" autocomplete="off"/>' +
                '                           </div>' +
                '                           <img height="34" width="94" src="" alt="验证码" class="login_code js_captchaimg" id="captcha_image" />' +
                '                           <a class="changecode">&nbsp;</a>' +
                '                       </div>' +
                '                   </div>' +
                '                   <div class="btndiv"><input type="submit" class="siginBtn js_passbtn" id="sigin"  value="" /></div>' +
                '               </form>' +
                '               </div>' +
                '               <div class="publogindiv" style="display:none" >' +
                '                   <div class="js_loginphone_form">' +
                '                       <div class="errorp visbid js_phone_err"><span class="error"><span class="icon_error">&nbsp;</span><span id="errortext2" class="errortext2"></span></span>&nbsp;</div>' +
                  '                     <div class="input js_input">' +
                '                           <b>*</b>' +
                '                           <span class="icon1">&nbsp;</span>' +
                '                           <span class="word">账号</span>' +
                '                           <input type="text" class="inps js_Account_phone" id="account" value="" name="account" autocomplete="off"  maxlength="18" />' +
                '                       </div>' +
                '                       <div class="inputcode clearfix" style="margin-bottom: 73px;">' +
                '                           <b>*</b>' +
                '                           <span class="phoneleft js_input">' +
                '                               <span class="icon6">&nbsp;</span>' +
                '                               <span class="word">短信验证码</span>' +
                '                               <input id="verifyCode" class="yzm" type="text" name="verifyCode" value="">' +
                '                           </span>' +
                '                           <span class="phoneright">' +
                '                               <input class="disab getvercode js_getvercode" type="button" disabled="disabled" value="免费获取验证码">' +
                '                           </span>' +
                '                       </div>' +
                '                       <div class="btndiv"><input type="button" class="siginBtn js_phonebtn" id="js_phonebtn"  value="" /></div>' +
                '                   </div>' +
                '               </div>' +
                '               <div class="publogindiv" style="display:none"><div class="login_ewmBox">' +
    '                   <p class="ewmTit">使用<a href="http://wd.gyyx.cn/News/NewsDetail_New.aspx?NewsID=78899" target="_blank">光宇游戏APP</a>扫描二维码安全登录</p>' +
    '                   <div class="ewmImgB"><div id="js_ewm_box"data-status="1"></div>' +
                '                   <div class="ewmOverDue_box"><p class="ewmOverDue"></p>' +
                '                   <p class="ewmtxt">二维码失效<br>请点击刷新</p></div>' +
                '               </div>' +
                '               </div></div>' +
                '               <div class="publicregdiv">' +
                '                   <a href="http://account.gyyx.cn/Member/Register" target="_blank">注册</a>|<a href="http://account.gyyx.cn/Member/ForgetPassword" target="_blank">忘记密码</a>' +
                '               </div>' +
                '           </div>' +

                '    </div>' +
                '    <div id="mark" class="markdiv"></div>' +
                '</div>';

/*补全资料层*/
var complementHtml = '<div class="public_nav_top_alert">' +
                    '    <div class="alertBox alertbox_comp">' +
                    '           <div class="alertBox_body">' +
                    '               <a href="javascript:void(0)" class="closeimg" ></a>' +
                    '               <ul class="title js_com_title">' +
                    '                   <li><span class="sec">密码登录</span><b>&nbsp;</b></li>' +
                    '                   <li><span class="msm">短信登录</span><b>&nbsp;</b></li>' +
                    '               </ul>' +
                    '               <div class="publogindiv" >' +
                    '               <form  method="post" class="js_complementform_pass">' +
                    '               <div class="js_custominfo">' +
                    '                   <div class="infodiv">' +
                    '                       <span class="ts">请补充资料让您的账户更安全</span>' +
                    '                   </div>' +
                    '                   <div class="errorp visbid js_pass_err"><span class="error"><span class="icon_error">&nbsp;</span><span id="errortext2" class="errortext2"></span></span>&nbsp;</div>' +
                    '                   <div class="input js_input">' +
                    '                       <b>*</b>' +
                    '                       <span class="icon1">&nbsp;</span>' +
                    '                       <span class="word">真实姓名</span>' +
                    '                       <input type="text" class="inps js_truename" id="truename" value="" name="truename" autocomplete="off"  maxlength="8"/>' +
                    '                   </div>' +
                    '                   <div class="input js_input">' +
                    '                       <b>*</b>' +
                    '                       <span class="icon3">&nbsp;</span>' +
                    '                       <span class="word">身份证号码</span>' +
                    '                       <input type="text" class="inps js_idcard" id="idcard" value="" name="idcard" autocomplete="off"  />' +
                    '                   </div>' +
                    '                   <div class="input js_input">' +
                    '                       <b>*</b>' +
                    '                       <span class="icon4">&nbsp;</span>' +
                    '                       <span class="word">邮箱地址</span>' +
                    '                       <input type="text" class="inps js_email" id="email" value="" name="email" autocomplete="off"  />' +
                    '                   </div>' +
                    '                   <div class="btndiv"><input type="submit" class="complementBtn js_complementBtn_pass"  value="" /><a class="closecompdiv" href="javascript:void(0)">跳过</a></div>' +
                    '               </div>' +
                    '               </form>' +
                    '               </div>' +
                    '               <div class="publogindiv" style="display:none">' +
                    '               <form  method="post" class="js_complementform_phone">' +
                    '               <div class="js_custominfo">' +
                    '                   <div class="infodiv">' +
                    '                       <span class="ts">请补充资料让您的账户更安全</span>' +
                    '                   </div>' +
                    '                   <div class="errorp visbid js_pass_err"><span class="error"><span class="icon_error">&nbsp;</span><span id="errortext2" class="errortext2"></span></span>&nbsp;</div>' +
                    '                   <div class="input js_input">' +
                    '                       <b>*</b>' +
                    '                       <span class="icon1">&nbsp;</span>' +
                    '                       <span class="word">真实姓名</span>' +
                    '                       <input type="text" class="inps js_truename_p" id="truename" value="" name="truename" autocomplete="off"   maxlength="8"/>' +
                    '                   </div>' +
                    '                   <div class="input js_input">' +
                    '                       <b>*</b>' +
                    '                       <span class="icon3">&nbsp;</span>' +
                    '                       <span class="word">身份证号码</span>' +
                    '                       <input type="text" class="inps js_idcard_p" id="idcard" value="" name="idcard" autocomplete="off"  />' +
                    '                   </div>' +
                    '                   <div class="input js_input">' +
                    '                       <b>*</b>' +
                    '                       <span class="icon4">&nbsp;</span>' +
                    '                       <span class="word">邮箱地址</span>' +
                    '                       <input type="text" class="inps js_email_p" id="email" value="" name="email" autocomplete="off"  />' +
                    '                   </div>' +
                     '                   <div class="input js_input">' +
                    '                       <b>*</b>' +
                    '                       <span class="icon5">&nbsp;</span>' +
                    '                       <span class="word">设置登录密码</span>' +
                    '                       <input type="password" class="inps js_Password_p" id="password" value="" name="password" autocomplete="off"  maxlength="16" />' +
                    '                   </div>' +
                    '                   <div class="btndiv"><input type="submit" class="complementBtn js_complementBtn_phone"  value="" /><a class="closecompdiv" href="javascript:void(0)">跳过</a></div>' +
                    '               </div>' +
                    '               </form>' +
                    '               </div>' +
                    '               <div class="publicregdiv">' +
                    '               </div>' +
                    '           </div>' +
                    '    </div>' +
                    '    <div id="mark" class="markdiv"></div>' +
                    '</div>';;

/* 注册层和遮罩层html */
var quickregHtml = '<div class="public_nav_top_alert">' +
                    '    <div class="alertBox_quickreg">' +
                    '        <form class="js_qreg_form" method="post">' +
                    '        <div class="alertBox_body_quickreg">' +
                    '            <div class="title_quickreg">' +
                    '                <a class="closeimg" href="javascript:void(0)"></a>' +
                    '            </div>' +
                    '            <div class="quickregdiv">' +
                    '                <div class="errorp_quickreg visbid ">' +
                    '                    <span class="error_quickreg">' +
                    '                        <span class="icon_error">&nbsp;</span>' +
                    '                        <span class="errortext2" id="errortext_reg"></span>' +
                    '                    </span>' +
                    '                </div>' +
                    '                <div class="input js_quickreginput">' +
                    '                    <div class="inputtitle">用户名:</div>' +
                    '                    <span class="word">字母开头，6-16位的字母和数字组合</span>' +
                    '                    <input type="text" maxlength="16" autocomplete="off" name="UserName" value="" id="UserName" class="inps_quickreg js_userName"/>' +
                    '                </div>' +
                    '                <div class="input js_quickreginput">' +
                    '                    <div class="inputtitle">密码:</div>' +
                    '                    <span class="word">4-16字符（除 &lt;和&gt;）</span>' +
                    '                    <input type="password" autocomplete="off" name="Password" value="" id="Password" class="inps_quickreg js_Password"/>' +
                    '                </div>' +
                    '                <div class="inputcode clearfix  js_quickregcaptcha">' +
                    '                    <div class="left">' +
                    '                        <div class="inputtitle">验证码:</div>' +
                    '                        <span class="word">看不清可刷新</span>' +
                    '                        <input type="text" id="CheckCode" name="CheckCode" class="inpsCaptcha">' +
                    '                    </div>' +
                    '                    <img width="94" height="34" id="captcha_image" class="login_code js_quickregcheckcode" alt="验证码" src="">' +
                    '                    <a class="changecode">&nbsp;</a>' +
                    '                </div>' +
                    '                <div class="input js_input" >' +
                    '                    <div class="inputtitle">&nbsp;</div>' +
                    '                    <label class="xieyi">' +
                    '                    <input id="ckbox" class="chk" type="checkbox" checked="checked" name="checkbox">' +
                    '                    <a href="http://www.gyyx.cn/Resource/Agreement.html#Terms" target="_blank">同意《光宇游戏平台用户服务协议》</a>' +
                    '                    </label>' +
                    '                </div>' +
                    '                <div class="btndiv ">' +
                    '                    <input type="submit" value="" id="btn_gogame" class="btn_gogame">' +
                    '                    <input type="button" value="" id="btn_gologin" class="btn_gologin">' +
                    '                </div>' +
                    '            </div>' +
                    '        </div>' +
                    '        </form>' +
                    '    </div>' +
                    '    <div class="markdiv" id="mark" ></div>' +
                    '</div>';

/* 选择服务器层 */
var serverHtml = '<div class="public_nav_top_alert">' +
                '    <div class="alertBox_ssl">' +
                '        <div class="alertBox_body_ssl">' +
                '            <div class="title_ssl">' +
                '                <a class="closeimg" href="javascript:void(0)"></a>' +
                '            </div>' +
                '            <div class="ssldiv">' +
                '                <div class="js_myseverlists">' +
                '                </div>' +
                '                <div class="js_newseverlists">' +
                '               </div>' +
                '            </div>' +
                '            <div class="publicregdiv js_webseverlist">' +
                '            </div>' +
                '        </div>' +
                '    </div>' +
                '    <div class="markdiv" id="mark" ></div>' +
                '</div>';

var loadingHtml = '<div class="public_nav_top_alert">' +
                  '     <div class="alertBox_loading"><img src="http://s.gyyx.cn/publictop/images/loading.gif"/></div>' +
                  '     <div class="markdiv" id="mark"></div>' +
                  '</div>';

var addpasswordHtml = '<div class="public_nav_top_alert">' +
                    '   <div class="alertBox alertbox_addpass">' +
                    '       <div class="alertBox_body">' +
                    '           <a class="closeimg closeimgdef" href="javascript:void(0)"></a>' +
                    '           <ul class="title_pass js_com_title"></ul>' +

                    '           <div style="height:auto; display: block" class="publogindiv">' +
                    '               <form class="js_formpublic_password_add"  method="post">' +
                    '                   <div class="infodiv"><span class="ts">请填写光宇通行证密码</span></div>' +
                    '                   <div class="errorp visbid js_public_password_add_err"><span class="error"><span class="icon_error">&nbsp;</span><span class="errortext2"></span></span></div>' +
                    '                   <div class="input js_input" >' +
                    '                   <b>*</b><span class="icon5">&nbsp;</span><span class="word">填写光宇通行证密码</span>' +
                    '                   <input type="password" maxlength="16" autocomplete="off" name="js_public_pw_add_input" value="" id="js_public_pw_add_input" class="inps js_public_pw_add_input" />' +
                    '                   </div>' +
                    '                   <div class="inputcode clearfix disnone js_captcha_addpwd">' +
                    '                       <div>' +
                    '                           <div class="left">' +
                    '                               <b>*</b>' +
                    '                               <span class="word js_word_addpwd">验证码</span>' +
                    '                               <input type="text" class="inpsCaptcha inpsCaptcha_addpwd" name="Captcha" id="Captcha" />' +
                    '                           </div>' +
                    '                           <img height="34" width="94" src="" alt="验证码" class="login_code js_captchaimg" id="captcha_image" />' +
                    '                           <a class="changecode">&nbsp;</a>' +
                    '                       </div>' +
                    '                   </div>' +
                    '                   <div class="btndiv" style="margin-top: 30px;"><input type="submit" value="" class=" addreserveBtn" /></div>' +
                    '               </form>' +
                    '           </div>' +
                    '           <div style="height: 160px; display: none" class="publogindiv">' +
                    '               <form class="js_formpublic_password_write"  method="post">' +
                    '                   <div class="infodiv"><span class="ts">请设置光宇通行证密码</span></div>' +
                    '                   <div class="errorp visbid js_public_password_write_err"><span class="error"><span class="icon_error">&nbsp;</span><span class="errortext2"></span></span></div>' +
                    '                   <div class="input js_input" style="margin-bottom: 30px;">' +
                    '                   <b>*</b><span class="icon5">&nbsp;</span><span class="word">设置光宇通行证密码</span>' +
                    '                   <input type="password" maxlength="16" autocomplete="off" name="js_public_pw_write_input" value="" id="js_public_pw_write_input" class="inps js_public_pw_write_input" />' +
                    '                   </div>' +
                    '                   <div class="btndiv"><input type="submit" value="" class="addreserveBtn" /></div>' +
                    '               </form>' +
                    '           </div>' +
                    '           <div class="publicregdiv"></div>' +
                    '       </div>' +
                    '   </div>' +
                    '   <div class="markdiv"></div>' +
                    '</div>';

//var Staticfiles = "http://static.gyyx.cn/Stage/public_nav_data.txt"; /*头部导航的数据文件，在美编处*/
//var StaticfilesBottom = "http://static.gyyx.cn/Stage/public_bottom_data.txt";/*底部导航的数据文件，在s站*/

var Staticfiles = "http://static.gyyx.cn/Stage/public_nav_data_cms.txt"; /*头部导航的数据文件，在美编处，改为由CMS管理生成 change by chenqiaoling 2016-2-24*/
var StaticfilesBottom = "http://static.gyyx.cn/Stage/public_bottom_data_cms.txt";/*底部导航的数据文件，在s站，改为由CMS管理生成 change by chenqiaoling 2016-2-24*/

var LoginStatusAjaxUrl = "http://reg.gyyx.cn/Login/Status";/*判断是否登录接口*/
var ToGameUrl = "http://api.officialsite.gyyx.cn/Frame/GetFrmMainUrl";/*头部导航，游戏服务器列表的连接*/
var GetGameInfo = "http://api.officialsite.gyyx.cn/ToGame/GetNewGameInfoForTopNav?jsoncallback=?";/*获取页游服务器列表接口*/
var AsynUrl = "http://reg.gyyx.cn/login/AsyncOnlyForAccount";/*登陆接口*/
var DeployUrl = "http://account.gyyx.cn/captcha/create";/*登陆用验证码接口 edit by lcf 20150325*/
var AvailableCaptcha = "http://reg.gyyx.cn/Login/Available";/*是否需要打开验证码*/
var RegDeployUrl = "http://www.gyyx.cn/Captcha/Create";/*注册用验证码接口*/
var CheckNameRepeatUrl = "http://reg.gyyx.cn/register/AccountIsExist?jsoncallback=?";/*验证用户名是否已被注册*/
var QuRegUrl = "http://account.gyyx.cn/Member/WebSimpleRegisterJsonp?jsoncallback=?";/*注册接口*/
var GameServer = "http://api.webstage.gyyx.cn/gameserver/";/*最新页游服务器列表*/
var SendSmsLoginUrl = "http://account.gyyx.cn/mobilephone/SmsLoginSendJsonp";/*发送短信验证码*/
var SmsLoginUrl = "http://reg.gyyx.cn/login/SmsLoginJsonp";/*短信登陆接口*/
var CompleteInfoAsyncUrl = "http://reg.gyyx.cn/Member/CompleteInfoAsync";/*通用弹出层补全资料*/
var ValidAccountPassWord = "http://reg.gyyx.cn/login/ValidAccount";/*判断密码是否输入正确*/
var AjaxCompletePwd = "http://reg.gyyx.cn/login/AjaxCompletePwd";/*设置密码*/
var SourceUrl = "http://a.gyyx.cn/regclickhandler.ashx";/*记录点击情况，用在落地页点击‘开始游戏’按钮*/


loadjscssfile("http://s.gyyx.cn/publictop/css/public_nav.css", "css");
loadjscssfile("http://s.gyyx.cn/lib/md5-min.js", "js");
loadjscssfile("http://s.gyyx.cn/lib/jquery.bgiframe.js", "js");
loadjscssfile("http://s.gyyx.cn/lib/addChinaCaptcha.js", "js");
loadjscssfile("http://s.gyyx.cn/login/js/jquery.login.js", "js");

loadjscssfile("http://s.gyyx.cn/Lib/qrcode.js", "js");  //生成二维码库

/* 头部导航生成 */
/* AutomaticOpenLogin  未登录情况，是否自动弹出登陆层,true为是 */
function Showtop(AutomaticOpenLogin) {


    var Defaults = {
        /*==================参数说明=========================*/
        /* LoginStatusAjaxUrl 判断是否登录接口               */
        /* AsynUrl 登陆接口                                  */
        /* LogoutUrl 退出接口                                */
        /* MobilePhoneUrl 手机认证接口                       */
        /* MatrixUrl 矩阵密保接口                            */
        /* EkeyUrl 光宇乾坤锁接口                            */
        /* GetGameInfo 页游服务器列表*/
        /* ToGameUrl  网页游戏服务器跳转地址	*/
        /* DeployUrl 配置验证码url,如果没有配置则是一个默认的地址         */
        /* Css 所需css文件路径                            */
        /* LoadingImg Loading图片地址                               */
        /*===================================================*/
        LoginStatusAjaxUrl: "http://reg.gyyx.cn/Login/Status",
        AsynUrl: "http://reg.gyyx.cn/login/AsyncOnlyForAccount",
        LogoutUrl: "http://reg.gyyx.cn/Login/LogoutJsonp",
        MobilePhoneUrl: "http://security.gyyx.cn/mobilephone/AjaxGetAuthed/",
        MatrixUrl: "http://security.gyyx.cn/Matrix/AjaxIsMatrix",
        EkeyUrl: "http://security.gyyx.cn/EkeyV2/AjaxIsEkey",
        DeployUrl: "http://account.gyyx.cn/captcha/create",//edit by lcf 20150325
        ToGameUrl: "http://api.officialsite.gyyx.cn/Frame/GetFrmMainUrl",
        GetGameInfo: "http://api.officialsite.gyyx.cn/ToGame/GetNewGameInfoForTopNav?jsoncallback=?",
        LoadingImg: "http://s.gyyx.cn/publictop/images/loading.gif"
    };

    /* 加载对应css和js */

    /* 取得导航模板 */
    $.getScript(Staticfiles + "?r=" + Math.random(), function () {
        var obj = datainc;

        var html = '<div class="top_bg_red" data-status="false">';

        html += '<div class="top_body_bg">';
        html += '<div class="top_red clearfix">';

        /*  html += '<a class="top_logo_mask" href="http://www.gyyx.cn/" target="_blank"></a>'; 2013.12.18 圣诞添加。过后删除 */

        html += '<div class="top_logo">';
        html += '<a href="http://www.gyyx.cn/" target="_blank"><img src="http://s.gyyx.cn/publictop/images/toplogo.gif" /></a>';
        html += '</div>';

        html += '<div class="top_comm_menu">';
        html += '<a target="_blank" href="http://www.gyyx.cn/" class="js_gamelink">首页</a>';
        html += '<a target="_blank" href="http://web.gyyx.cn/" class="js_gamelink">页游</a>';/* by angki 2014/07/07 */
        html += '<a target="_blank" href="http://js.gyyx.cn/" class="js_gamelink">手游</a>';/* by shiyawei 2016/06/01 */
        html += '<a target="_blank" href="http://i.gyyx.cn/" class="js_gamelink">资讯</a>';
        html += '</div>';

        html += '<div class="top_info_menu ">';
        html += '<div class="info_mli js_customer"><a class="icon_customer">&nbsp;</a>';
        html += '</div>';
        html += '<span class="js_pubtoplogin_div" id="js_pubtoplogin_div"></span>';
        html += '</div>';

        html += '<div class="top_game_menu clearfix">';
        for (var i = 0; i < 3; i++) {
            html += '<div class="menu_li">';
            if (i != 2) {
                html += '<a class="menu_li_a_red">' + obj[i].title + '<i class="top_g_crr">&nbsp;</i></a>';
            } else {
                html += '<a class="menu_li_a_red" style="background:none">' + obj[i].title + '<i class="top_g_crr">&nbsp;</i></a>';
            }
            html += '</div>';
        }

        html += '<div class="clearfix">';
        for (var i = 0; i < 3; i++) {
            if (obj[i].type == "more") {
                html += '<div class="tip_menu tip_menu_' + i + ' gdiv' + i + ' clearfix">';

                html += '<i class="tip_menu_crr">&nbsp;</i>';
                html += '<div class="clearfix pt10">';
                html += '<div class="tip_left">';
                html += '<div style="border:1px solid #fff">';
                html += '<p class="tip_left_img"><a target="_blank" href="' + obj[i].data[0].gamelist[0].item[0].url + '" class="js_gamelink"><img src="' + obj[i].data[0].gamelist[0].item[0].image + '" class="js_gameimg" /></a></p>';
                html += '<p class="tip_left_word"><a target="_blank" href="' + obj[i].data[0].gamelist[0].item[0].url + '" class="js_imgword js_gamelink">' + obj[i].data[0].gamelist[0].item[0].word + '</a></p>';
                html += '</div>';
                if (obj[i].moreurl != "") {
                    html += '<div class="tip_left_more"><a target="_blank" href="' + obj[i].moreurl + '">更多精彩游戏</a><span class="icon_go">&nbsp;</span></div>';
                } else {
                    html += '<div class="tip_left_more"></div>';
                }
                html += '</div>';
                html += '<div class="tip_right" style="padding:10px 0 7px 12px; width:276px;">';
                for (var j = 0; j < obj[i].data.length; j++) {
                    if (j == obj[i].data.length - 1) {
                        html += '<div class="game_type_div clearfix no_border">';
                        html += '<div class="h2">' + obj[i].data[j].type + '</div>';

                        for (var k = 0; k < obj[i].data[j].gamelist.length; k++) {
                            html += '<ul class="game_ls">';

                            for (var l = 0; l < obj[i].data[j].gamelist[k].item.length; l++) {
                                if (obj[i].data[j].gamelist[k].item[l].status != "") {
                                    html += '<li><a target="_blank" href="' + obj[i].data[j].gamelist[k].item[l].url + '" class="js_gamelink">' + obj[i].data[j].gamelist[k].item[l].title + '<span class="icon_' + obj[i].data[j].gamelist[k].item[l].status + '" style="right:' + obj[i].data[j].gamelist[k].num + '">&nbsp;</span></a></li>';
                                } else {
                                    html += '<li><a target="_blank" href="' + obj[i].data[j].gamelist[k].item[l].url + '" class="js_gamelink">' + obj[i].data[j].gamelist[k].item[l].title + '</a></li>';
                                }
                            }
                            html += '</ul>';
                        }
                        html += '</div>';
                    } else {
                        html += '<div class="game_type_div clearfix ">';
                        html += '<div class="h2">' + obj[i].data[j].type + '</div>';

                        for (var k = 0; k < obj[i].data[j].gamelist.length; k++) {
                            html += '<ul class="game_ls">';
                            for (var l = 0; l < obj[i].data[j].gamelist[k].item.length; l++) {
                                if (obj[i].data[j].gamelist[k].item[l].status != "") {
                                    html += '<li><a target="_blank" href="' + obj[i].data[j].gamelist[k].item[l].url + '" class="js_gamelink"> ' + obj[i].data[j].gamelist[k].item[l].title + '<span class="icon_' + obj[i].data[j].gamelist[k].item[l].status + '" style="right:' + obj[i].data[j].gamelist[k].num + '">&nbsp;</span></a></li>';
                                } else {
                                    html += '<li><a target="_blank" href="' + obj[i].data[j].gamelist[k].item[l].url + '" class="js_gamelink">' + obj[i].data[j].gamelist[k].item[l].title + '</a></li>';
                                }
                            }
                            html += '</ul>';
                        }
                        html += '</div>';
                    }
                }
                html += '<div class="tip_right_word">';
                html += '<span><i class="icon_hot i">&nbsp;</i>热门</span> <span><i class="icon_new i">&nbsp;</i>最新</span><span><i class="icon_beta i">&nbsp;</i>测试</span>';
                html += '</div>';
                html += '</div>';
                html += '<div class="tip_r"></div>';
                html += '</div>';

                html += '</div>';
            } else if (obj[i].type != "more") {
                html += '<div class="tip_menu tip_menu_' + i + ' gdiv' + i + ' clearfix">';
                html += '<i class="tip_menu_crr">&nbsp;</i>';
                html += '<div class="clearfix pt10">';
                html += '<div class="tip_left">';
                html += '<div style="border:1px solid #fff">';
                html += '<p class="tip_left_img"><a target="_blank" href="' + obj[i].data[0].item[0].url + '" class="js_gamelink"><img src="' + obj[i].data[0].item[0].image + '" class="js_gameimg" /></a></p>';
                html += '<p class="tip_left_word"><a target="_blank" href="' + obj[i].data[0].item[0].url + '" class="js_imgword js_gamelink">' + obj[i].data[0].item[0].word + '</a></p>';

                html += '</div>';
                if (obj[i].moreurl != "") {
                    html += '<div class="tip_left_more"><a target="_blank" href="' + obj[i].moreurl + '">更多精彩游戏</a><span class="icon_go">&nbsp;</span></div>';
                } else {
                    html += '<div class="tip_left_more"></div>';
                }
                html += '</div>';


                html += '<div class="tip_right">';
                html += '<div class="clearfix">';

                for (var j = 0; j < obj[i].data.length; j++) {
                    html += '<ul class="tip_right_gamelist">';

                    for (var k = 0; k < obj[i].data[j].item.length; k++) {
                        if (obj[i].data[j].item[k].status != "") {
                            html += '<li><a target="_blank" href="' + obj[i].data[j].item[k].url + '" class="js_gamelink">' + obj[i].data[j].item[k].title + '<span class="icon_' + obj[i].data[j].item[k].status + '" style="right:' + obj[i].data[j].num + '">&nbsp;</span></a></li>';
                        } else {
                            html += '<li><a target="_blank" href="' + obj[i].data[j].item[k].url + '" class="js_gamelink">' + obj[i].data[j].item[k].title + '</a></li>';
                        }
                    }
                    html += '</ul>';
                }
                html += '</div>';

                html += '<div class="tip_right_word">';
                html += '<span><i class="icon_hot i">&nbsp;</i>热门</span> <span><i class="icon_new i">&nbsp;</i>最新</span><span><i class="icon_beta i">&nbsp;</i>测试</span>';
                html += '</div>';
                html += '</div>';
                html += '<div class="tip_r"></div>';
                html += '</div>';
                html += '</div>';
            }
        }
        html += '</div>';

        html += '</div>';
        html += '</div>';
        html += '</div>';




        html += '<div class="tip_gameser js_tip_gameser">';
        html += '<div class="tip_gameser_body">游戏服务：';
        for (var i = 0; i < obj[3].data.length; i++) {
            if (i == obj[3].data.length - 1) {
                html += '<a class="nor" target="_blank" href="' + obj[3].data[i].url + '">' + obj[3].data[i].title + '</a>';
            } else {
                html += '<a target="_blank" href="' + obj[3].data[i].url + '">' + obj[3].data[i].title + '</a>|';
            }
        }
        html += '<span class="js_haswebser haswebser">';
        html += '</span>';

        html += '</div>';
        html += '</div>';

        html += '<div class="top_bg_red_mask">&nbsp</div>';
        html += '</div>';

        $(".public_nav_top").append(html);

        /* 判断用户是否登录 */
        /*  获取到登陆信息 后显示登陆状态*/
        function succShowFn(IsLogin, account) {
            var s = '';
            if (IsLogin == false) {
                s = '<div class="info_mli info_mli_lore"><a class="info_mli_a js_alertlogin_a no_lo_a">登录</a></div><div class="info_mli_lore_line"></div><div class="info_mli info_mli_lore"><a class="info_mli_a no_lo_a" href="http://account.gyyx.cn/Member/Register" target="_blank">注册</a></div>';
                $(".top_bg_red").attr("data-status", "false");
                /* 是否自动弹出登陆层 */
                function subSomething() {
                    if (document.readyState == "complete") {
                        if (AutomaticOpenLogin == true) {
                            alertFn(Defaults.AsynUrl, Defaults.DeployUrl, false);
                        }
                    }
                }
                document.onreadystatechange = subSomething;
            }
            else {
                $(".top_bg_red").attr("data-status", "true");
                $(".gdiv0").removeClass("tip_menu_0").addClass("tip_menu_0_1");
                $(".gdiv1").removeClass("tip_menu_1").addClass("tip_menu_1_1");
                $(".gdiv2").removeClass("tip_menu_2").addClass("tip_menu_2_1");

                /* 判断用户名长度 */
                if (account.length <= 8) {
                    s = '<div class="info_mli js_loginzzz"><span class="info_mli_a ">您好，<a target="_blank" href="http://account.gyyx.cn/" class="name">' + account + '</a><i class="top_g_crr">&nbsp;</i></span>';
                } else {
                    var uname1 = account.substring(0, 2);
                    var uname2 = account.substring(account.length - 2, account.length);
                    var uname = uname1 + "****" + uname2;
                    s = '<div class="info_mli js_loginzzz"><span class="info_mli_a ">您好，<a target="_blank" href="http://account.gyyx.cn/" class="name">' + uname + '</a><i class="top_g_crr">&nbsp;</i></span>';
                }

                s += '<div class="tip_menu_info js_pubtoplogin_menu">';
                s += '<i class="tip_menu_crr">&nbsp;</i> ';
                s += '<div class="conn"><img src="' + Defaults.LoadingImg + '" /><span class="imgmid"></span></div>';

                s += '<div class="jd_operatediv">';
                s += '<p class="tip_ex"></p>';
                s += '<input type="hidden" id="hidphone" value="0" /><input type="hidden" id="hidEkey" value="0" /><input type="hidden" id="hidMatrix" value="0" />';
                s += '<div class="tip_operate  js_opphone disnone">';
                s += '</div>';
                s += '<div class="tip_operate js_opEkey disnone">';
                s += '</div>';
                s += '<div class="tip_operate nob js_opMatrix disnone">';
                s += '</div>';
                s += '</div>';

                s += '</div>';
                s += '</div>';
                s += '<div class="info_mli"><a class="info_mli_a js_Logout">退出</a></div>';

                /*这里判断用户是否登录了页游*/

                $.getJSON(Defaults.GetGameInfo, { game_id: 0, r: function () { return Math.random(); } }, function (d) {
                    if (d.Result && d.LoginGameSer != null && d.LoginGameSer != "") {
                        var str = '<span class="shangcid">上次登录：</span>';
                        $.each(d.LoginGameSer, function (i, item) {
                            str += '<a class="sername" href="javascript:;" onclick="_toGameFrm(' + item.GameID + ', ' + item.ServerID + ');return false;"  >' + item.GameName + "" + item.ServerName + '</a>';/*add by pengjia 2015.1.28*/

                        });

                        $(".js_haswebser").html(str);
                    } else {
                        $(".js_haswebser").html("");
                    }
                });
            }

            $(".js_pubtoplogin_div").append(s);
        }

        /*  ajax   方法---显示登陆信息*/
        function ajaxShowLoginInfo() {
            $.ajax({
                url: Defaults.LoginStatusAjaxUrl,
                type: "GET",
                dataType: "jsonp",
                jsonp: "jsoncallback",
                data: { r: Math.random() },
                success: function (d) {
                    succShowFn(d.IsLogin, d.Account)   /*  获取到登陆信息 后显示登陆状态*/
                },
                error: function (d, e) { }
            });
        }

        /*cookie 方法---显示登陆信息*/
        function cookieShowLoginInfo() {
            var sCookie = getCookie("GYYX_LOGINSTATUS_STAGE_V1");

            if (sCookie == "") {
                ajaxShowLoginInfo(); /*ajax方法显示登陆信息*/
            } else {
                var Account = sCookie.split("$")[0].split(":")[1];
                var IsLogin = sCookie.split("$")[1].split(":")[1];

                succShowFn(IsLogin, Account);  /*通过获取 cookie方法显示登陆信息*/
            }
        }
        cookieShowLoginInfo();


        /* 登陆按钮 */
        $(".js_alertlogin_a").live("click", function () {
            alertFn(Defaults.AsynUrl, Defaults.DeployUrl, false);
        });
        /* 退出按钮 */
        $(".js_Logout").live("click", function () {
            $.ajax({
                url: Defaults.LogoutUrl,
                type: "GET",
                dataType: "jsonp",
                jsonp: "jsoncallback",
                data: { r: Math.random() },
                success: function (d) {
                    if (d.IsSuccess) {
                        location.reload();
                    }
                    else {
                        alert(d.Message);
                    }
                },
                error: function (e) {
                }
            });
        });

        var time_login;
        var num = 0;

        /* 登录后，用户名下菜单效果，和接口处理 */
        $(".js_loginzzz").live("mouseenter", function () {
            $(this).addClass("on");
            $(".js_pubtoplogin_menu").show();
            clearTimeout(time_login)
            /*  如果手机认证隐藏域值为0，则调用接口查询。否则保留信息。下同  */
            if ($("#hidphone").val() == "0") {
                $.ajax({
                    url: Defaults.MobilePhoneUrl,
                    type: "GET",
                    dataType: "jsonp",
                    jsonp: "jsoncallback",
                    data: {
                        r: Math.random()
                    },
                    beforeSend: function (d) {
                        $("#hidphone").val("1");
                    },
                    success: function (d) {
                        $(".conn").hide();
                        $(".js_opphone").show();
                        $("#hidphone").val("1");
                        var str;
                        if (d.AuthPhoneStatus == "Unbind") {                        //未绑定
                            str = '<i class="icon_error"></i>';
                            str += '<span class="opername">手机认证：</span>';
                            str += '<span class="status">未绑定手机</span>';
                            str += '<span class="oper"><a href="http://security.gyyx.cn/MobilePhone/index" target="_blank">绑定</a></span>';
                            num++;
                        }
                        else if (d.AuthPhoneStatus == "Bind") {             //未认证
                            str = '<i class="icon_phone"></i>';
                            str += '<span class="opername">手机认证：</span>';
                            str += '<span class="status">尚未认证手机</span>';
                            str += '<span class="oper_lefts"><a href="http://security.gyyx.cn/MobilePhone/VerifyRegisterInfo" target="_blank">认证</a><span>';
                            str += '<span class="oper" style="right:40px"><a href="http://security.gyyx.cn/MobilePhone/Change" target="_blank">更换</a></span>';
                            str += '<span class="oper"><a href="http://security.gyyx.cn/MobilePhone/UnBindPhone " target="_blank">解绑</a></span>';
                        }
                        else if (d.AuthPhoneStatus =="Authing") {  //认证中
                            str = '<i class="icon_phone"></i>';
                            str += '<span class="opername">手机认证：</span>';
                            str += '<span class="status">处于验证期</span>';
                            str += '<span class="opers">还差<span class="c_red">' + (30 - d.AuthedDay) + '</span>天通过验证</span>';
                        }
                        else if (d.AuthPhoneStatus == "AuthFinish") {     //已认证
                            
                            str = '<i class="icon_phone"></i>';
                            str += '<span class="opername">手机认证：</span>';
                            str += '<span class="status">' + d.PhoneNo + '</span>';
                            
                            if (d.AccountType == "custom") {
                                str += '<span class="oper"><a href="http://security.gyyx.cn/MobilePhone/Change" target="_blank">更换手机</a></span>';
                            }
                            
                        }
                        $(".js_opphone").html("");
                        $(".js_opphone").html(str);
                        if (num == 0) {
                            $(".tip_ex").html('您的账号安全状态：');
                        } else if (num == 1) {
                            $(".tip_ex").html('您的账号存在<span class="num">1</span>项风险：');
                        } else if (num == 2) {
                            $(".tip_ex").html('您的账号存在<span class="num">2</span>项风险：');
                        } else {
                            $(".tip_ex").html('您的账号存在<span class="num">3</span>项风险：');
                        }

                    },
                    error: function (d) {
                        $(".js_opphone").hide();
                        $("#hidphone").val("0");
                        $(".js_opphone").hide();
                    }
                });
            }

            if ($("#hidEkey").val() == "0") {
                $.ajax({
                    url: Defaults.EkeyUrl,
                    type: "GET",
                    dataType: "jsonp",
                    jsonp: "jsoncallback",
                    data: {
                        r: Math.random()
                    },
                    beforeSend: function (d) {
                        $("#hidEkey").val("1");
                    },
                    success: function (d) {
                        $(".conn").hide();
                        $(".js_opEkey").show();
                        $("#hidEkey").val("1");
                        var str;
                        if (d == 1) {
                            str = '<i class="icon_lock"></i>';
                            str += '<span class="opername">光宇乾坤锁：</span>';
                            str += '<span class="status">已绑定</span>';
                            str += '<span class="oper"><a href="http://security.gyyx.cn/EkeyV2/Index/" target="_blank">乾坤锁管理</a></span>';
                        } else {
                            str = '<i class="icon_error"></i>';
                            str += '<span class="opername">光宇乾坤锁：</span>';
                            str += '<span class="status">尚未开通</span>';
                            str += '<span class="oper"><a href="http://security.gyyx.cn/EkeyV2/BindMain" target="_blank">绑定</a></span>';
                            num++;
                        }
                        $(".js_opEkey").html("");
                        $(".js_opEkey").html(str);
                        if (num == 0) {
                            $(".tip_ex").html('您的账号安全状态：');
                        } else if (num == 1) {
                            $(".tip_ex").html('您的账号存在<span class="num">1</span>项风险：');
                        } else if (num == 2) {
                            $(".tip_ex").html('您的账号存在<span class="num">2</span>项风险：');
                        } else {
                            $(".tip_ex").html('您的账号存在<span class="num">3</span>项风险：');
                        }
                    },
                    error: function (d) {
                        $("#hidEkey").val("0");
                        $(".js_opEkey").hide();
                    }
                });
            }
            if ($("#hidMatrix").val() == "0") {
                $.ajax({
                    url: Defaults.MatrixUrl,
                    type: "GET",
                    dataType: "jsonp",
                    jsonp: "jsoncallback",
                    data: {
                        r: Math.random()
                    },
                    beforeSend: function (d) {
                        $("#hidMatrix").val("1");
                    },
                    success: function (d) {
                        $(".conn").hide();
                        $(".js_opMatrix").show();
                        $("#hidMatrix").val("1");
                        var str;
                        if (d == 1) {
                            str = '<i class="icon_lock"></i>';
                            str += '<span class="opername">矩阵密保：</span>';
                            str += '<span class="status">已绑定</span>';
                            str += '<span class="oper"><a href="http://security.gyyx.cn/Matrix/Index/" target="_blank">矩阵密保管理</a></span>';
                        } else {
                            str = '<i class="icon_error"></i>';
                            str += '<span class="opername">矩阵密保：</span>';
                            str += '<span class="status">尚未开通</span>';
                            str += '<span class="oper"><a href="http://security.gyyx.cn/Matrix/Bind" target="_blank">绑定</a></span>';
                            num++;
                        }

                        $(".js_opMatrix").html("");
                        $(".js_opMatrix").html(str);
                        if (num == 0) {
                            $(".tip_ex").html('您的账号安全状态：');
                        } else if (num == 1) {
                            $(".tip_ex").html('您的账号存在<span class="num">1</span>项风险：');
                        } else if (num == 2) {
                            $(".tip_ex").html('您的账号存在<span class="num">2</span>项风险：');
                        } else {
                            $(".tip_ex").html('您的账号存在<span class="num">3</span>项风险：');
                        }
                    },
                    error: function (d, e) {
                        $("#hidMatrix").val("0");
                        $(".js_opMatrix").hide();
                    }
                });
            }
            //if ($(".js_opphone").html() == "") {
            //    $(".js_opphone").next("div").addClass("nob");
            //}


        });
        $(".js_loginzzz").live("mouseleave", function () {
            $(this).removeClass("on");
            time_login = setTimeout('$(".js_pubtoplogin_menu").hide()', 300)
        });

        /* 游戏菜单效果 */
        $(".menu_li").hover(function () {
            $(this).addClass("on");
            $(this).find("a").addClass("crr");
            var i = $(this).index();
            var _menu = $(".gdiv" + i);
            _menu.show();
            _menu.find(".js_gameimg").attr("src", obj[i].data[0].image);
            _menu.find(".js_imgword").text(obj[i].data[0].word);
            _menu.find(".tip_left_img").find("a").attr("href", obj[i].data[0].url);
            _menu.find(".js_imgword").attr("href", obj[i].data[0].url);
        }, function () {
            var i = $(this).index();
            $(this).removeClass("on");
            $(this).find("a").removeClass("crr");
            var _menu = $(".gdiv" + i);
            _menu.hide();
        });

        $(".tip_menu").hover(function () {
            $(this).show();
            var index = $(this).index();
            $(".top_game_menu .menu_li:eq(" + index + ")").addClass("on");
            $(".top_game_menu .menu_li:eq(" + index + ")").find("a").addClass("crr");

        }, function () {
            $(this).hide();
            var index = $(this).index();
            $(".top_game_menu .menu_li:eq(" + index + ")").removeClass("on");
            $(".top_game_menu .menu_li:eq(" + index + ")").find("a").removeClass("crr");
        });



        var timer_gameser;
        $(".js_customer").hover(function () {
            $(".js_tip_gameser").show();
            $(".top_bg_red_mask").hide();
            $(this).find("a").addClass("on");
        }, function () {
            timer_gameser = setTimeout('$(".js_tip_gameser").hide();$(".top_bg_red_mask").show();$(".js_customer").find("a").removeClass("on");', 300);
        });
        $(".js_tip_gameser").hover(function () {
            clearTimeout(timer_gameser);
            $(this).show();
            $(".top_bg_red_mask").hide();
            $(".js_customer").find("a").addClass("on");
        }, function () {
            $(this).hide();
            $(".top_bg_red_mask").show();
            $(".js_customer").find("a").removeClass("on");
        });

        /*光宇服务菜单内鼠标滑过效果*/
        $(".menu_customerlist li").hover(function () {
            $(this).addClass("on");
        }, function () {
            $(this).removeClass("on");
        });

        /*游戏列表左右两栏最后一项下划线的处理*/
        $(".tip_menu").each(function () {
            if ($(this).find(".tip_right_gamelist:eq(0) li").length > $(this).find(".tip_right_gamelist:eq(1) li").length) {
                $(this).find(".tip_right_gamelist:eq(0) li").last().addClass("no_border");
            } else if ($(this).find(".tip_right_gamelist:eq(0) li").length == $(this).find(".tip_right_gamelist:eq(1) li").length) {
                $(this).find(".tip_right_gamelist:eq(0) li").last().addClass("no_border");
                $(this).find(".tip_right_gamelist:eq(1) li").last().addClass("no_border");
            }
        });

        /* 游戏类型菜单内鼠标滑过效果*/
        $(".tip_right_gamelist li").hover(function () {
            var i = $(this).parents(".tip_menu").index();
            var j = $(this).parents(".tip_right_gamelist").index();
            var url = $(this).find("a").attr("href");
            $(this).addClass("on");
            $(this).parents(".tip_menu").find(".tip_left_img").find("a").attr("href", url);
            $(this).parents(".tip_menu").find(".js_imgword").attr("href", url);
            $(this).parents(".tip_menu").find(".js_gameimg").attr("src", obj[i].data[j].item[$(this).index()].image);
            $(this).parents(".tip_menu").find(".js_imgword").text(obj[i].data[j].item[$(this).index()].word);
        }, function () {
            $(this).removeClass("on");
        });

        $(".game_type_div li").hover(function () {
            var i = $(this).parents(".tip_menu").index();
            var j = $(this).parents(".game_type_div").index();
            var k = $(this).parent("ul").index() - 1;
            var url = $(this).find("a").attr("href");
            $(this).addClass("on");
            $(this).parents(".tip_menu").find(".tip_left_img").find("a").attr("href", url);
            $(this).parents(".tip_menu").find(".js_imgword").attr("href", url);
            $(this).parents(".tip_menu").find(".js_gameimg").attr("src", obj[i].data[j].gamelist[k].item[$(this).index()].image);
            $(this).parents(".tip_menu").find(".js_imgword").text(obj[i].data[j].gamelist[k].item[$(this).index()].word);
        }, function () {
            $(this).removeClass("on");
        });

        /*游戏点击统计*/
        $(".js_gamelink").die().live("click", function () {
            var $this = $(this);
            var pageUrl = location.href;
            var href = $this.attr("href");
            var target = $this.attr("target");
            $.ajax({
                url: "http://api.gyyx.cn/ClickInfo/capture_click_data.ashx?jsoncallback=?",
                type: "GET",
                dataType: "json",
                data: {
                    href: href,
                    pageUrl: pageUrl,
                    target: target,
                    type: "text",
                    r: function () { return Math.random(); }
                }
            });
        });


    });
}

/* 底部文件生成 */
function ShowBottom(color) {
    /* 取得js后参数值，判断使用哪个底部信息。参数名‘webname’ */
    var webname = GetJSQueryString("jquery.publictop.js");

    $.getScript(StaticfilesBottom + "?r=" + Math.random(), function () {
        var obj = bottomdata;
        var name = "default";
        if (webname[2] != null && webname[2].toString().length > 1) {
            for (var i = 0; i < obj.length; i++) {
                if (webname[2] == obj[i].id) {
                    name = webname[2];
                }
            }
        }
        for (var i = 0; i < obj.length; i++) {
            if (name == obj[i].id) {
                var html = "";
                if (color == "" || color == null) {
                    html += '<div class="bottompub_bg">';
                    html += '<div class="bottompub">';
                } else {
                    var html = '<div class="bottompub_bg" style="background-color: #' + color + ';background-image: none;">';
                    html += '<div class="bottompub" style="background-color: #' + color + ';background-image: none;">';
                }


                html += '<p class="bottompub_word_p1">';
                for (var j = 2; j < obj[i].data.length; j++) {
                    if (j != obj[i].data.length - 1) {
                        html += '<a  href="' + obj[i].data[j].url + '" target="_blank" title="' + obj[i].data[j].title + '">' + obj[i].data[j].title + '</a>|';
                    } else {
                        html += '<a  href="' + obj[i].data[j].url + '" target="_blank" title="' + obj[i].data[j].title + '">' + obj[i].data[j].title + '</a>';
                    }
                }
                html += '</p>';
                html += '<p class="boycottpub">' + obj[i].slogan + '</p>';
                html += '<p class="bottompub_word_p2">' + obj[i].reginfo + '</p>';
                html += '<div class="bottompub_img">';
                html += '<a href="' + obj[i].data[0].url + '" target="_blank" title="' + obj[i].data[0].title + '"><img src="' + obj[i].data[0].image + '" /></a>';
                html += '<a href="' + obj[i].data[1].url + '" target="_blank" title="' + obj[i].data[1].title + '"><img src="' + obj[i].data[1].image + '" /></a>';
                html += '</div>';
                html += '</div>';
                html += '</div>';
                $(".public_nav_bottom").append(html);
            }
        }
    });
}

/* 动态加载css和js文件 */
function loadjscssfile(filename, filetype) {
    if (filetype == "js") {
        var fileref = document.createElement('script');
        fileref.setAttribute("type", "text/javascript");
        fileref.setAttribute("src", filename);
    } else if (filetype == "css") {
        var fileref = document.createElement('link');
        fileref.setAttribute("rel", "stylesheet");
        fileref.setAttribute("type", "text/css");
        fileref.setAttribute("href", filename);
    }
    if (typeof fileref != "undefined") {
        document.getElementsByTagName("head")[0].appendChild(fileref);
    }

    //var bool = false;
    //if (filetype == "js") {
    //    var fileref = document.createElement('script');
    //    fileref.setAttribute("type", "text/javascript");
    //    fileref.setAttribute("src", filename);
    //} else if (filetype == "css") {
    //    var fileref = document.createElement('link');
    //    fileref.setAttribute("rel", "stylesheet");
    //    fileref.setAttribute("type", "text/css");
    //    fileref.setAttribute("href", filename);
    //}
    //if (typeof fileref != "undefined") {
    //    document.getElementsByTagName("head")[0].appendChild(fileref);
    //}

    //fileref.onload = fileref.onreadystatechange = function () {
    //    if (!this.readyState || this.readyState == 'loaded' || this.readyState == 'complete') {
    //        // alert('done');
    //        bool=true
    //    }
    //    fileref.onload = fileref.onreadystatechange = null;
    //}
    //return bool
}




/* 取得js文件后参数值 */
function GetJSQueryString(paramName) {
    var tt
    var jsFileName = paramName;
    var rName = new RegExp(jsFileName + "(\\?(.*))?$")
    var jss = document.getElementsByTagName('script');
    for (var i = 0; i < jss.length; i++) {
        var j = jss[i];
        if (j.src && j.src.match(rName)) {
            var oo = j.src.match(rName)[2];
            if (oo && (t = oo.match(/([^&=]+)=([^=&]+)/g))) {
                for (var l = 0; l < t.length; l++) {
                    r = t[l];
                    tt = r.match(/([^&=]+)=([^=&]+)/);
                    if (tt) {
                        return tt;
                    }
                    //  document.write('参数：' + tt[1] + '，参数值：' + tt[2] + '<br />');
                    // return tt[2];
                }
            }
        }
    }
    return "";


}

/*ESC关闭登陆弹出层*/
function escFn(ev) {
    var oEvent = window.event || ev;
    if (oEvent.keyCode == 27) {
        $("ul.title li").die("click");
        $(".public_nav_top_alert").remove();
        //$(".alertBox").remove();
        //$(".alertBox_quickreg").remove();
        //$(".alertBox_ssl").remove();
        //$(".markdiv").remove();
    }
}




/*
显示登陆层，加载输入框效果，加载提交事件
参数说明：
AsyncUrl：登陆接口；
DeployUrl：验证码接口；
isOpenbool：是否外部调用。
isRefPage:是否刷新页面
isNoRefPageFun:登陆后不刷新页面，执行的方法
Gameid:游戏id
weburl:游戏官网地址
severlistsurl：游戏服务器地址
示例：某个页面有‘登陆’文字，在onclick事件里面直接写alertFn(null,null,true)即可
*/
function alertFn(AsyncUrl, DeployUrl, isOpenbool, isRefPage, isNoRefPageFun, GameId, weburl, severlistsurl) {

    var prstatus = $(".top_bg_red").attr("data-status");

    if (prstatus != "true") {
        $(".public_nav_top_alert").remove();
        $("body").append(alertHtml);

        document.onkeydown = escFn;

        var winwidth = $(window).width();
        var thisleft = winwidth / 2 - $(".alertBox").width() / 2;
        var thistop = $(window).height() / 2 - $(".alertBox").height() / 2 + $(window).scrollTop();
        var bodyheight = Math.max($(document.body).outerHeight(true), $(window).height());
        $(".markdiv").css("height", bodyheight);
        $(".alertBox").css({ 'left': thisleft, 'top': thistop });

        $(".closeimg").hover(function () {
            $(this).addClass("closeimgactive").removeClass("closeimgdef");
        }, function () {
            $(this).addClass("closeimgdef").removeClass("closeimgactive");
        }).bind("click", function () {
            $(".alertBox").remove();
            $(".markdiv").remove();
            $("ul.js_logintitle li").die("click");
            $(".js_Account").die("click");
        });

        setTimeout(function () {
            $.each($(".js_input input"), function (i, item) {
                if ($(this).val() == "") {
                    $(this).parents(".js_input").find(".word").show();
                } else {
                    $(this).parents(".js_input").find(".word").hide();
                }
            });
        }, 1000);

        $(".js_input").find("input").live("keydown keyup", function () {
            var _this = $(this);
            if ($(this).val() != "") {
                $(_this.parent().find(".word")).hide();
            } else {
                $(_this.parent().find(".word")).show();
            }
        });

        $(".js_captcha").find("input").live("keydown keyup", function () {
            var _this = $(this);
            if ($(this).val() != "") {
                $(_this.parent().find(".word")).hide();
            } else {
                $(_this.parent().find(".word")).show();
            }
        });

        $(".js_input,.js_captcha").find("input").bind("paste", function (e) {
            var _this = $(this);
            setTimeout(function () {
                if (_this.val() != "") {
                    $(_this.parent().find(".word")).hide();
                } else {
                    $(_this.parent().find(".word")).show();
                }
            }, 100);
        });
        /*$.each($(".alertBox_body > .publogindiv"), function (i, item) {
            if ($(item).is(":visible"))
                $("ul.js_logintitle li").eq($(item).index()).addClass("cur");
            else
                $("ul.js_logintitle li").eq($(item).index()).removeClass("cur");
        });*/

        isEwmLogin($("ul.js_logintitle li"), $('#js_ewm_box'), true);
        $("ul.js_logintitle li").live("click", function () {
            var _index = $(this).index();

            $("ul.js_logintitle li").removeClass("cur");
            $(this).addClass("cur");
            $(".alertBox_body > .publogindiv").hide().eq(_index).show();
            isEwmLogin($("ul.js_logintitle li"), $('#js_ewm_box'), true);
        });

        $(".word").click(function () {
            $(this).parents(".js_input").find(".inps").focus();
            $(this).parents(".js_input").find(".yzm").focus();
            $(this).parents(".js_captcha").find(".inpsCaptcha").focus();
        });

        if (!isOpenbool) {
            AsyncUrl = AsyncUrl;
            DeployUrl = DeployUrl;
        } else {
            AsyncUrl = "http://reg.gyyx.cn/login/AsyncOnlyForAccount";
            DeployUrl = "http://account.gyyx.cn/captcha/create";
        }

        $("#Captcha").live("keyup paste focus blur", function () {
            var val = $(this).val();
            var pattern = new RegExp("[^A-Za-z0-9]+", "g");
            $(this).val(val.replace(pattern, ''));
        });


        //IE6下默认不缓存背景图片，CSS里每次更改图片的位置时都会重新发起请求，用这个方法告诉IE6缓存背景图片 
        var isIE6 = /msie 6/i.test(navigator.userAgent);
        if (isIE6) {
            try { document.execCommand('BackgroundImageCache', false, true); } catch (e) { }
        }

        var chinaCaptcha = new ChinaCaptcha($(".js_login_form"), {
            bid: "Vaqrk"
        });

        setTimeout(function () {
            if ($('.js_Account').val() != "") {
                chinaCaptcha.init($('.js_Account').val());
             }
        }, 300)

        //输入用户名离焦后获取验证码类型
        $('.js_Account').die('blur').live("blur", function () {
            var $this = $(this);
            if ($this.val() != $this.attr('data-olduser') && $this.val() !='') {
                chinaCaptcha.init($this.val());
            }
            $this.attr('data-olduser', $this.val());  
        })

        /* 密码登陆 */
        //提交表单
        $(".js_login_form").submit(function () {

            $(".js_pass_err").css("visibility", "hidden");
            if ($(".js_Account").val() == "" || $(".js_Password").val() == "") {
                $(".js_pass_err").css("visibility", "visible");
                $(".js_pass_err .errortext2").html("账号、密码不能为空");
                return false
            }
            else {
                var exp = new RegExp("^[\u4e00-\u9fa5]{0,}$");
                if (exp.test($(".js_Account").val())) {
                    $(".js_pass_err").css("visibility", "visible");
                    $(".js_pass_err .errortext2").html("账号格式错误，不允许包含中文");
                    return false
                } else {
                  
                    var subFn = function () {
                        var sdata = "";
                        var n = $(".js_login_form").find("input[type!='button']").size();
                        $.each($(".js_login_form").find("input[type!='button']"), function (i, d) {
                            var _val = $(d).val();
                            if ($(d).attr("type") == "password") {
                                _val = hex_md5(_val.trim());
                            }
                            sdata += (i != n - 1 ? "\"" + $(d).attr("name") + "\": \"" + _val + "\", " : " \"" + $(d).attr("name") + "\": \"" + _val + "\" ");
                        });
                        sdata += ", \"r\": " + Math.random();
                        $.ajax({
                            url: AsyncUrl,
                            type: "GET",
                            dataType: "jsonp",
                            jsonp: "jsoncallback",
                            data: $.parseJSON("{" + sdata + "}"),
                            beforeSend: function (d) {
                                $(".js_passbtn").attr("disabled", true);
                                $(".js_passbtn").addClass("siginBtnactive");
                            },
                            success: function (d, e) {
                                if (d.ToUrl) {
                                    window.location.href = d.ToUrl;
                                    return;
                                } else { 

                                    if (d.IsLogin) {
                                  
                                        if (isRefPage == false || isRefPage == "" || isRefPage == null) {
                                            top.location.reload();
                                        } else {
                                            var s = "";
                                            if (d.Account.length <= 8) {
                                                s = '<div class="info_mli js_loginzzz"><span class="info_mli_a ">您好，<a target="_blank" href="http://account.gyyx.cn/" class="name">' + d.Account + '</a><i class="top_g_crr">&nbsp;</i></span>';
                                            } else {
                                                var uname1 = d.Account.substring(0, 2);
                                                var uname2 = d.Account.substring(d.Account.length - 2, d.Account.length);
                                                var uname = uname1 + "****" + uname2;
                                                s = '<div class="info_mli js_loginzzz"><span class="info_mli_a ">您好，<a target="_blank" href="http://account.gyyx.cn/" class="name">' + uname + '</a><i class="top_g_crr">&nbsp;</i></span>';
                                            }
                                            s += '<div class="tip_menu_info js_pubtoplogin_menu">';
                                            s += '<i class="tip_menu_crr">&nbsp;</i> ';
                                            s += '<div class="conn"><img src="http://s.gyyx.cn/publictop/images/loading.gif" /><span class="imgmid"></span></div>';

                                            s += '<div class="jd_operatediv">';
                                            s += '<p class="tip_ex"></p>';
                                            s += '<input type="hidden" id="hidphone" value="0" /><input type="hidden" id="hidEkey" value="0" /><input type="hidden" id="hidMatrix" value="0" />';
                                            s += '<div class="tip_operate nob js_opphone disnone">';
                                            s += '</div>';
                                            s += '<div class="tip_operate js_opEkey disnone">';
                                            s += '</div>';
                                            s += '<div class="tip_operate  js_opMatrix disnone">';
                                            s += '</div>';
                                            s += '</div>';

                                            s += '</div>';
                                            s += '</div>';
                                            s += '<div class="info_mli c_yellow"><a class="info_mli_a js_Logout">退出</a></div>';

                                            //这里判断用户是否登录了页游


                                            $.getJSON(GetGameInfo, { game_id: 0, r: function () { return Math.random(); } }, function (d) {
                                                if (d.Result && d.LoginGameSer != null && d.LoginGameSer != "") {
                                                    var str = '<span class="shangcid">上次登录：</span>';
                                                    $.each(d.LoginGameSer, function (i, item) {

                                                        str += '<a class="sername" href="javascript:;" onclick="_toGameFrm(' + item.GameID + ', ' + item.ServerID + ');return false;"  >' + item.GameName + "" + item.ServerName + '</a>';//add by pengjia 2015.1.28

                                                    });

                                                    $(".js_haswebser").html(str);
                                                } else {
                                                    $(".js_haswebser").html("");
                                                }
                                            });


                                            var o = document.getElementById("js_pubtoplogin_div");
                                            if (o) {
                                                $(".js_pubtoplogin_div").html(s);
                                            }

                                            if (isNoRefPageFun != null) {
                                                isNoRefPageFun(GameId, weburl, severlistsurl);
                                            }
                                        }

                                    }
                                    else {
                                    

                                        $(".js_passbtn").attr("disabled", false);
                                        $(".js_passbtn").removeClass("siginBtnactive").addClass("siginBtn");

                                        if (d.Message == '帐号或密码错误') {
                                            $('.js_chinaCaptcha_Alert').remove();
                                        
                                        }

                                        if (chinaCaptcha.captcahSwitchOpen == 1) {//如果是中文验证码，提示错误信息并刷新验证码
                                            alert(d.Message);
                                            chinaCaptcha.refreshCaptcha();
                                        } else {
                                            if (d.CaptchaType == undefined) {//显示服务器其他错误
                                                $(".js_pass_err").css("visibility", "visible");
                                                $(".js_pass_err #errortext2").text(d.Message);
                                            } else {
                                                switch (d.CaptchaType) {
                                                    case 0:
                                                        $(".js_pass_err").css("visibility", "visible");
                                                        $(".js_pass_err .errortext2").text(d.Message);
                                                        break;
                                                    case 1:
                                                        if (d.Message == "发生错误gyyxuser-incorrect-captcha，请返回重试") {
                                                            $(".js_pass_err").css("visibility", "visible");
                                                            $(".js_pass_err #errortext2").text("验证码输入错误");
                                                        } else {
                                                            $(".js_pass_err").css("visibility", "visible");
                                                            $(".js_pass_err #errortext2").text(d.Message);
                                                        }
                                                        break;
                                                    case 2:
                                                        $(".js_pass_err").css("visibility", "visible");
                                                        $(".js_pass_err #errortext2").text(d.Message);
                                                        break;
                                                }
                                            }

                                            //只要出错就都刷新验证码 edit by lcf 20150325
                                            $(".js_captcha").show();
                                            var $loginform_captchaimg = $(".js_login_form .js_captchaimg");
                                            $loginform_captchaimg.attr("src", DeployUrl + "?bid=" + chinaCaptcha.bid + "&r=" + Math.random());
                                            $loginform_captchaimg.unbind("click").bind("click", function () {
                                                $loginform_captchaimg.attr("src", DeployUrl + "?bid=" + chinaCaptcha.bid + "&r=" + Math.random());
                                            });
                                            $(".changecode").unbind("click").bind("click", function () {
                                                $loginform_captchaimg.attr("src", DeployUrl + "?bid=" + chinaCaptcha.bid + "&r=" + Math.random());
                                            });
                                        }

                                    }
                                }
                            },
                            error: function (d, e) {
                                $(".js_passbtn").attr("disabled", false);
                                $(".js_passbtn").removeClass("siginBtnactive").addClass("siginBtn");
                                $(".js_pass_err").css("visibility", "visible");
                                $(".js_pass_err #errortext2").text("登陆失败");
                            }
                        });
                    }

                    switch (chinaCaptcha.captcahSwitchOpen) {
                        case -1://不需要验证码
                        case 0://需要普通验证码
                            subFn();
                            break;
                        case 1://需要中文验证码
                            ChinaCaptcha.prototype.subFn = subFn;
                            chinaCaptcha.createCaptchaPop();
                            break;
                        default:
                            break;
                    }

                }
                return false
            }

        });

        /* 短信登陆 */
        /* 发送验证码按钮效果 */
        $("#account").keyup(function () {
            $(".js_phone_err").css("visibility", "hidden");
            $(".js_phone_err .ser_err").html("");
            if ($(this).val().length != 0) {
                $(".js_getvercode").removeClass("disab").attr("disabled", false);
            } else {
                $(".js_getvercode").addClass("disab").attr("disabled", true);
            }
        });
        $("#account").bind("paste", function (e) {
            var _this = $(this);
            setTimeout(function () {
                $(".js_phone_err").css("visibility", "hidden");
                $(".js_phone_err .ser_err").html("");
                if (_this.val().length != 0) {
                    $(".js_getvercode").removeClass("disab").attr("disabled", false);
                } else {
                    $(".js_getvercode").addClass("disab").attr("disabled", true);
                }
            }, 300);
        });

        /*发送短信验证码*/
        $(".js_getvercode").click(function () {
            $(".js_phone_err").css("visibility", "hidden");
            $(".js_phone_err .ser_err").html("");
            if ($("#account").val() == "") {
                $(".js_phone_err").css("visibility", "visible");
                $(".js_phone_err .errortext2").html("账号不能为空");
            } else {
                $.ajax({
                    url: SendSmsLoginUrl,
                    type: "GET",
                    dataType: "jsonp",
                    jsonp: "jsoncallback",
                    data: { account: $("#account").val(), r: Math.random() },
                    beforeSend: function (d) {
                        $(".js_getvercode").addClass("disab").attr("disabled", true).val("发送中...");
                    },
                    success: function (data) {
                        var d = data.IsLogin;
                        if (d) {
                            if (data.IsSuccess || data.IsCountDown) {
                                $("#account").attr("disabled", true);
                                $(".js_getvercode").addClass("disab").attr("disabled", true).val(data.PushTime + "秒后可再次获取");
                                countDown($(".js_getvercode"), data.PushTime);
                            } else {
                                $("#account").attr("disabled", true);
                                $(".js_getvercode").addClass("disab").attr("disabled", true).val(data.PushTime + "秒后可再次获取");
                                countDown($(".js_getvercode"), data.PushTime);
                                $(".js_phone_err").css("visibility", "visible");
                                $(".js_phone_err .errortext2").html(data.Message);
                            }
                        } else {
                            $(".js_getvercode").removeClass("disab").attr("disabled", false).val("免费获取验证码");
                            $(".js_phone_err").css("visibility", "visible");
                            $(".js_phone_err .errortext2").html(data.Message);
                        }
                    },
                    error: function () { }
                })
            }
        });
        /*短信登陆*/
        $(".js_phonebtn").click(function () {
            $(".js_phone_err").css("visibility", "hidden");
            if ($("#account").val() == "" || $("#verifyCode").val() == "") {
                $(".js_phone_err").css("visibility", "visible");
                $(".js_phone_err .errortext2").html("账号、验证码不能为空");
            } else {
                var exp = new RegExp("^[\u4e00-\u9fa5]{0,}$");
                if (exp.test($("#account").val())) {
                    $(".js_phone_err").css("visibility", "visible");
                    $(".js_phone_err .errortext2").html("账号格式错误，不允许包含中文");
                    return false
                } else {
                    $.ajax({
                        url: SmsLoginUrl,
                        type: "GET",
                        dataType: "jsonp",
                        jsonp: "jsoncallback",
                        data: {
                            account: $("#account").val(), verifyCode: $("#verifyCode").val(), r: Math.random()
                        },
                        beforeSend: function (d) {
                            $(".js_phonebtn").attr("disabled", true);
                            $(".js_phonebtn").addClass("siginBtnactive");
                        },
                        success: function (data) {
                            if (data.IsLogin) {
                                if (isRefPage == false || isRefPage == "" || isRefPage == null) {
                                    top.location.reload();
                                } else {
                                    var s = "";

                                    var uname1 = data.Account.substring(0, 2);
                                    var uname2 = data.Account.substring(data.Account.length - 2, data.Account.length);
                                    var uname = uname1 + "****" + uname2;
                                    s = '<div class="info_mli js_loginzzz"><span class="info_mli_a ">您好，<a target="_blank" href="http://account.gyyx.cn/" class="name">' + uname + '</a><i class="top_g_crr">&nbsp;</i></span>';
                                    s += '<div class="tip_menu_info js_pubtoplogin_menu">';
                                    s += '<i class="tip_menu_crr">&nbsp;</i> ';
                                    s += '<div class="conn"><img src="http://s.gyyx.cn/publictop/images/loading.gif" /><span class="imgmid"></span></div>';

                                    s += '<div class="jd_operatediv">';
                                    s += '<p class="tip_ex"></p>';
                                    s += '<input type="hidden" id="hidphone" value="0" /><input type="hidden" id="hidEkey" value="0" /><input type="hidden" id="hidMatrix" value="0" />';
                                    s += '<div class="tip_operate nob js_opphone disnone">';
                                    s += '</div>';
                                    s += '<div class="tip_operate js_opEkey disnone">';
                                    s += '</div>';
                                    s += '<div class="tip_operate  js_opMatrix disnone">';
                                    s += '</div>';
                                    s += '</div>';

                                    s += '</div>';
                                    s += '</div>';
                                    s += '<div class="info_mli c_yellow"><a class="info_mli_a js_Logout">退出</a></div>';

                                    //这里判断用户是否登录了页游


                                    $.getJSON(GetGameInfo, { game_id: 0, r: function () { return Math.random(); } }, function (d) {
                                        if (d.Result && d.LoginGameSer != null && d.LoginGameSer != "") {
                                            var str = "";
                                            str += '<span class="shangcid">上次登录：</span>';
                                            $.each(d.LoginGameSer, function (i, item) {
                                                //str += '<a class="sername" href="' + ToGameUrl + '?game_id=' + item.GameID + '&server_id=' + item.ServerID + '" target="_blank" >' + item.GameName + "" + item.ServerName + '</a>';
                                                str += '<a class="sername" href="javascript:;" onclick="_toGameFrm(' + item.GameID + ', ' + item.ServerID + ')"  >' + item.GameName + "" + item.ServerName + '</a>';//add by pengjia 2015.1.28
                                                //if (item.GameID > 10015 && item.GameID < 20000) {
                                                //    str += '<a class="sername" href="' + Defaults.ToGameUrl + '?game_id=' + item.GameID + '&server_id=' + item.ServerID + '&cms_type=1" target="_blank" >' + item.GameName + "" + item.ServerName + '</a>';
                                                //} else if (item.GameID > 10000 && item.GameID <= 10015) {
                                                //    str += '<a class="sername" href="' + Defaults.ToGameUrl + '?game_id=' + item.GameID + '&server_id=' + item.ServerID + '" target="_blank" >' + item.GameName + "" + item.ServerName + '</a>';
                                                //}
                                            });

                                            $(".js_haswebser").html(str);
                                        } else {
                                            $(".js_haswebser").html("");
                                        }
                                    });
                                    var o = document.getElementById("js_pubtoplogin_div");
                                    if (o) {
                                        $(".js_pubtoplogin_div").html(s);
                                    }

                                    if (isNoRefPageFun != null) {
                                        isNoRefPageFun(GameId, weburl, severlistsurl);
                                    }
                                }
                            } else {
                                $(".js_phonebtn").attr("disabled", false);
                                $(".js_phonebtn").removeClass("siginBtnactive").addClass("siginBtn");
                                $(".js_phone_err").css("visibility", "visible");
                                $(".js_phone_err .errortext2").html(data.Message);
                            }
                        },
                        error: function (d, e) {
                            $(".js_phonebtn").attr("disabled", false);
                            $(".js_phonebtn").removeClass("siginBtnactive").addClass("siginBtn");
                            $(".js_phone_err").css("visibility", "visible");
                            $(".js_phone_err .errortext2").html("登陆失败");
                        }
                    });
                }

            }
        })

        $(".alertBox").bgiframe();
    } else if (prstatus == "true") {
        if (isNoRefPageFun != null) {
            isNoRefPageFun();
        }
    }
};

/* 短信定时器倒计时*/
function countDown(obj, seconds) {
    var timer = setInterval(function () {
        if (seconds > 1) {
            seconds = seconds - 1;
            obj.val(seconds + "秒后可再次获取");
        } else {
            clearInterval(timer);
            $(".js_phone_err").css("visibility", "hidden");
            $(".js_phone_err .ser_err").html("");
            $("#account").attr("disabled", false);
            $(".js_getvercode").attr("disabled", false).removeClass("disab").val("免费获取验证码");
        }
    }, 1000);
}

/* 补充资料 */
function alertComplement(type) {
    $(".public_nav_top_alert").remove();
    $("body").append(complementHtml);
    document.onkeydown = escFn;
    var winwidth = $(window).width();
    var thisleft = winwidth / 2 - $(".alertBox").width() / 2;
    var thistop = $(window).height() / 2 - $(".alertBox").height() / 2 + $(window).scrollTop();
    var bodyheight = Math.max($(document.body).outerHeight(true), $(window).height());
    $(".markdiv").css("height", bodyheight);
    $(".alertBox").css({ 'left': thisleft, 'top': thistop });

    $(".closeimg").hover(function () {
        $(this).addClass("closeimgactive").removeClass("closeimgdef");
    }, function () {
        $(this).addClass("closeimgdef").removeClass("closeimgactive");
    }).bind("click", function () {
        $(".alertBox").remove();
        $(".markdiv").remove();
        setCookie("IsNoSetUserInfo", "true");
        location.reload();
    });

    $(".closecompdiv").live("click", function () {
        $(".alertBox").remove();
        $(".markdiv").remove();
        setCookie("IsNoSetUserInfo", "true");
        location.reload();
    });
    setTimeout(function () {
        $.each($(".js_input input"), function (i, item) {
            if ($(this).val() == "") {
                $(this).parents(".js_input").find(".word").show();
            } else {
                $(this).parents(".js_input").find(".word").hide();
            }
        });
    }, 1000);
    $(".js_input").find("input").bind("keydown keyup", function () {
        var _this = $(this);
        if ($(this).val() != "") {
            $(_this.parent().find(".word")).hide();
        } else {
            $(_this.parent().find(".word")).show();
        }
    });

    $(".js_input").find("input").bind("paste", function (e) {
        var _this = $(this);
        setTimeout(function () {
            if (_this.val() != "") {
                $(_this.parent().find(".word")).hide();
            } else {
                $(_this.parent().find(".word")).show();
            }
        }, 100);
    });


    $(".publogindiv").hide();
    if (type == "pass") {
        $(".js_com_title li:eq(0)").addClass("cur");
        $(".publogindiv:eq(0)").show();
    } else if (type == "phone") {
        $(".js_com_title li:eq(1)").addClass("cur");
        $(".publogindiv:eq(1)").show();
    }
    $(".word").click(function () {
        $(this).parents(".js_input").find(".inps").focus();
    });

    $(".js_complementform_pass .js_input input").live("focus blur", function () {
        if (_VerIsEmptyPass($(this)) == true) {
            _VerOKPass($(this));
        }
        if ($(this).hasClass("js_truename")) {
            if (_VerCheckChinese($(this)) == true) {
                _VerOKPass($(this));
            }
        }
        if ($(this).hasClass("js_idcard")) {
            if (_VerCheckIdCard($(this)) == true) {
                _VerOKPass($(this));
            }
        }
        if ($(this).hasClass("js_email")) {
            if (_VerCheckEmail($(this)) == true) {
                _VerOKPass($(this));
            }
        }
    });

    $(".js_complementform_phone .js_input input").live("focus blur", function () {
        if (_VerIsEmptyPass($(this)) == true) {
            _VerOKPass($(this));
        }
        if ($(this).hasClass("js_truename_p")) {
            if (_VerCheckChinese($(this)) == true) {
                _VerOKPass($(this));
            }
        }
        if ($(this).hasClass("js_idcard_p")) {
            if (_VerCheckIdCard($(this)) == true) {
                _VerOKPass($(this));
            }
        }
        if ($(this).hasClass("js_email_p")) {
            if (_VerCheckEmail($(this)) == true) {
                _VerOKPass($(this));
            }
        }
        if ($(this).hasClass("js_Password_p")) {
            if (_VerCheckPasswordPhone($(this)) == true) {
                _VerOKPass($(this));
            }
        }
    });

    /* 密码补全资料 表单提交*/
    $(".js_complementform_pass").submit(function () {
        $(".js_pass_err").css("visibility", "hidden");
        $(".js_pass_err .errortext2").html("");
        if (_Verificationpass() == true) {
            var sdata = "";
            var n = $(".js_complementform_pass").find("input[type!='submit']").size();
            $.each($(".js_complementform_pass").find("input[type!='submit']"), function (i, d) {
                var _val = $(d).val();
                sdata += (i != n - 1 ? "\"" + $(d).attr("name") + "\": \"" + _val + "\", " : " \"" + $(d).attr("name") + "\": \"" + _val + "\" ");
            });
            sdata += ", \"r\": " + Math.random();
            $.ajax({
                url: CompleteInfoAsyncUrl,
                type: "GET",
                dataType: "jsonp",
                jsonp: "jsoncallback",
                data: $.parseJSON("{" + sdata + "}"),
                beforeSend: function (d) {
                    $(".js_complementBtn_pass").attr("disabled", true).addClass("complementBtnactive");
                },
                success: function (data) {
                    if (data.IsSuccess) {
                        location.reload();
                    } else {
                        $(".js_complementBtn_pass").attr("disabled", false).removeClass("siginBtnactive").addClass("siginBtn");
                        $(".js_pass_err").css("visibility", "visible");
                        $(".js_pass_err .errortext2").html(data.Message);
                    }
                },
                error: function () {
                    $(".js_complementBtn_pass").attr("disabled", false).removeClass("complementBtnactive").addClass("complementBtn");
                    $(".js_pass_err").css("visibility", "visible");
                    $(".js_phone_err .errortext2").html("补全失败");
                }
            })
        }
        return false;
    });

    /* 短信补全资料 表单提交*/
    $(".js_complementform_phone").submit(function () {
        $(".js_pass_err").css("visibility", "hidden");
        $(".js_pass_err .errortext2").html("");
        if (_Verificationphone() == true) {
            var sdata = "";
            var n = $(".js_complementform_phone").find("input[type!='submit']").size();
            $.each($(".js_complementform_phone").find("input[type!='submit']"), function (i, d) {
                var _val = $(d).val();
                sdata += (i != n - 1 ? "\"" + $(d).attr("name") + "\": \"" + _val + "\", " : " \"" + $(d).attr("name") + "\": \"" + _val + "\" ");
            });
            sdata += ", \"r\": " + Math.random();
            $.ajax({
                url: CompleteInfoAsyncUrl,
                type: "GET",
                dataType: "jsonp",
                jsonp: "jsoncallback",
                data: $.parseJSON("{" + sdata + "}"),
                beforeSend: function (d) {
                    $(".js_complementBtn_phone").attr("disabled", true).addClass("complementBtnactive");
                },
                success: function (data) {
                    if (data.IsSuccess) {
                        location.reload();
                    } else {
                        $(".js_complementBtn_phone").attr("disabled", false).removeClass("siginBtnactive").addClass("siginBtn");
                        $(".js_pass_err").css("visibility", "visible");
                        $(".js_pass_err .errortext2").html(data.Message);
                    }
                },
                error: function () {
                    $(".js_complementBtn_phone").attr("disabled", false).removeClass("complementBtnactive").addClass("complementBtn");
                    $(".js_pass_err").css("visibility", "visible");
                    $(".js_phone_err .errortext2").html("补全失败");
                }
            })
        }
        return false;
    });


}

function setCookie(name, value) { /*设置cookie*/
    var str = name + '=' + value;
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

/* 快速注册层 */
function alertQuickReg(GameId, weburl, severlistsurl) {
    $.ajax({
        url: LoginStatusAjaxUrl,
        type: "GET",
        dataType: "jsonp",
        jsonp: "jsoncallback",
        data: { r: Math.random() },
        beforeSend: function () {
            $("body").append(loadingHtml);
            var winwidth = $(window).width();
            var thisleft = winwidth / 2 - $(".alertBox_loading").width() / 2;
            var thistop = $(window).height() / 2 - $(".alertBox_loading").height() / 2 + $(window).scrollTop();
            var bodyheight = Math.max($(document.body).outerHeight(true), $(window).height());
            $(".markdiv").css("height", bodyheight);
            $(".alertBox_loading").css({ 'left': thisleft, 'top': thistop });
        },
        success: function (d) {
            if (d.IsLogin == true) {
                $.ajax({
                    url: GameServer + GameId + "/new?getCount=4",
                    type: "GET",
                    dataType: "jsonp",
                    jsonp: "jsoncallback",
                    data: { r: Math.random() },
                    success: function (d) {
                        if (d.IsSuccess) {
                            if (d.Data != null && d.Data != "" && d.Data.length != undefined) {
                                _toGameFrm(d.Data[0].GameID, d.Data[0].ServerID);//add by pengjia 2015.1.28
                                //var s = ToGameUrl + '?game_id=' + d.Data[0].GameID + '&server_id=' + d.Data[0].ServerID;
                                //window.location.href = s
                            }
                        }
                    }
                })
            } else {
                $(".public_nav_top_alert").remove();
                $("body").append(quickregHtml);


                document.onkeydown = escFn;
                var winwidth = $(window).width();
                var thisleft = winwidth / 2 - $(".alertBox_quickreg").width() / 2;
                var thistop = $(window).height() / 2 - $(".alertBox_quickreg").height() / 2 + $(window).scrollTop();
                var bodyheight = Math.max($(document.body).outerHeight(true), $(window).height());
                $(".markdiv").css("height", bodyheight);
                $(".alertBox_quickreg").css({ 'left': thisleft, 'top': thistop });
                $(".closeimg").hover(function () {
                    $(this).addClass("closeimgactive").removeClass("closeimgdef");
                }, function () {
                    $(this).addClass("closeimgdef").removeClass("closeimgactive");
                }).bind("click", function () {
                    $(".alertBox_quickreg").remove();
                    $(".markdiv").remove();
                });

                setTimeout(function () {
                    $.each($(".js_quickreginput .inps_quickreg , .inpsCaptcha"), function (i, item) {
                        if ($(this).val() == "") {
                            $(this).prev(".word").show();
                        } else {
                            $(this).prev(".word").hide();
                        }
                    });
                }, 1000);

                $(".js_quickreginput .word,.js_quickregcaptcha .word").live("click", function () {
                    $(this).next("input").focus();
                });
                $(".js_quickreginput input,.inpsCaptcha").live("keydown keyup", function () {
                    var _this = $(this);
                    if ($(this).val() != "") {
                        $(_this.prev(".word")).hide();
                    } else {
                        $(_this.prev(".word")).show();
                    }
                });
                $(".js_quickreginput").find("input").bind("paste", function (e) {
                    var _this = $(this);
                    setTimeout(function () {
                        if (_this.val() != "") {
                            $(_this.prev(".word")).hide();
                        } else {
                            $(_this.prev(".word")).show();
                        }
                    }, 100);
                });
                $(".inpsCaptcha").bind("paste", function (e) {
                    var _this = $(this);
                    setTimeout(function () {
                        if (_this.val() != "") {
                            $(_this.prev(".word")).hide();
                        } else {
                            $(_this.prev(".word")).show();
                        }
                    }, 100);
                });

                $(".js_quickregcheckcode").attr("src", RegDeployUrl + "?fileName=" + Math.random());

                $(".js_quickreginput .inps_quickreg , .inpsCaptcha").live("blur", function () {
                    if (_VerIsEmpty($(this)) == true) {
                        _VerOK($(this));
                        if ($(this).hasClass("js_userName")) {
                            if (_VerCheckEnglish($(this)) == true) {
                                if (_VerCheckNameRepeat($(this)) == true) {
                                    _VerOK($(this));
                                }
                            }
                        }
                        if ($(this).hasClass("js_Password")) {
                            if (_VerCheckPassword($(this)) == true) {
                                _VerOK($(this));
                            }
                        }

                        if ($(this).hasClass("inpsCaptcha")) {
                            if (_VerCheckCaptcha($(this)) == true) {
                                _VerOK($(this));
                            }
                        }
                    }
                });

                $(".changecode,.js_quickregcheckcode").live("click", function () {
                    $(".js_quickregcheckcode").attr("src", RegDeployUrl + "?fileName=" + Math.random() + ".png");
                });



                /* 进入游戏按钮   by angki 2013/9/16 */
                $("form.js_qreg_form").submit(function () {
                    /* 记录点击情况，成功与否没关系 */
                    $.ajax({
                        url: SourceUrl,
                        type: "GET",
                        dataType: "jsonp",
                        data: { r: document.referrer }
                    });

                    if (_Verification() == true) {
                        $.ajax({
                            url: CheckNameRepeatUrl,
                            type: "GET",
                            dataType: "jsonp",
                            data: $.parseJSON("{ \"userName\": \"" + $(".js_userName").val() + "\", \"r\": " + Math.random() + " }"),
                            success: function (d) {
                                if (d.result) {
                                    $(".errorp_quickreg").css("visibility", "hidden");
                                    $(".errortext2").html("");
                                    $(".js_userName").removeClass("inps_quickreg_err");

                                    var sdata = "";
                                    var n = $(".js_qreg_form").find("input[type!='button']").size();
                                    $.each($(".js_qreg_form").find("input[type!='button']"), function (i, d) {
                                        var _name = $(d).attr("name");
                                        var _value = $(d).val();
                                        sdata += (i != n - 1 ? "\"" + _name + "\": \"" + _value + "\", " : " \"" + _name + "\": \"" + _value + "\" ");
                                    });
                                    sdata += ", \"GameId\": \"" + GameId + "\"";
                                    sdata += ", \"r\": " + Math.random();
                                    sdata = $.parseJSON("{" + sdata + "}");
                                    $.ajax({
                                        type: "GET",
                                        dataType: "jsonp",
                                        jsonp: "jsoncallback",
                                        url: QuRegUrl,
                                        data: sdata,
                                        beforeSend: function (XMLHttpRequest) {
                                            $("#btn_gogame").attr("disabled", true);
                                            $("#btn_gogame").addClass("btn_gogameactive");
                                        },
                                        success: function (d) {
                                            if (d.IsSuccess) {
                                                $.ajax({
                                                    url: GameServer + GameId + "/new?getCount=4",
                                                    type: "GET",
                                                    dataType: "jsonp",
                                                    jsonp: "jsoncallback",
                                                    data: { r: Math.random() },
                                                    success: function (d) {
                                                        if (d.IsSuccess) {
                                                            if (d.Data != null && d.Data != "" && d.Data.length != undefined) {
                                                                _toGameFrm(d.Data[0].GameID, d.Data[0].ServerID);//add by pengjia 2015.1.28
                                                                //var s = ToGameUrl + '?game_id=' + d.Data[0].GameID + '&server_id=' + d.Data[0].ServerID;
                                                                //window.location.href = s
                                                            }
                                                        }
                                                    }
                                                })

                                            } else {
                                                if (d.Message == "验证码不正确，请您重新输入") {
                                                    $(".js_quickregcheckcode").attr("src", RegDeployUrl + "?fileName=" + Math.random() + ".png");
                                                }
                                                $("#btn_gogame").attr("disabled", false);
                                                $("#btn_gogame").removeClass("btn_gogameactive");
                                                $(".errorp_quickreg").css("visibility", "visible");
                                                $(".errortext2").html(d.Message);
                                                $(".inpsCaptcha").addClass("inps_quickreg_err");
                                            }
                                        },
                                        error: function () {

                                        }
                                    });

                                } else {
                                    $(".js_userName").addClass("inps_quickreg_err");
                                    $(".errorp_quickreg").css("visibility", "visible");
                                    $(".errortext2").html("该用户名已使用！");
                                    //  $(".js_userName").focus();
                                }
                            }
                        });
                    }
                    return false;
                });

                /* 老用户登陆 按钮，如果没有登陆，则先打开登陆层，如果登陆了，直接打开服务器列表层 */
                $("#btn_gologin").live("click", function () {
                    $(".public_nav_top_alert").remove();
                    $.ajax({
                        url: LoginStatusAjaxUrl,
                        type: "GET",
                        dataType: "jsonp",
                        jsonp: "jsoncallback",
                        data: { r: Math.random() },
                        success: function (d) {
                            if (d.IsLogin == false) {
                                alertFn("", "", true, true, alertServer, GameId, weburl, severlistsurl);
                            } else {
                                alertServer(GameId, weburl, severlistsurl);
                            }
                        }
                    })
                });

            }
        },
        error: function () {
            $(".public_nav_top_alert").remove();
        }


    })
}


/* 服务器列表层 */
function alertServer(GameId, weburl, severlistsurl) {
    $(".public_nav_top_alert").remove();
    $("body").append(serverHtml);
    document.onkeydown = escFn;
    var winwidth = $(window).width();
    var thisleft = winwidth / 2 - $(".alertBox_ssl").width() / 2;
    var thistop = $(window).height() / 2 - $(".alertBox_ssl").height() / 2 + $(window).scrollTop();
    var bodyheight = Math.max($(document.body).outerHeight(true), $(window).height());
    $(".markdiv").css("height", bodyheight);
    $(".alertBox_ssl").css({ 'left': thisleft, 'top': thistop });

    $(".closeimg").hover(function () {
        $(this).addClass("closeimgactive").removeClass("closeimgdef");
    }, function () {
        $(this).addClass("closeimgdef").removeClass("closeimgactive");
    }).bind("click", function () {
        $(".alertBox_ssl").remove();
        $(".markdiv").remove();
        location.reload();
    });
    $(".js_webseverlist").html('<a target="_blank" href="' + weburl + '">进入官网</a>|<a target="_blank"  href="' + severlistsurl + '">更多服务器</a></div>');
    $.ajax({
        url: GameServer + GameId + "?getCount=4",
        type: "GET",
        dataType: "jsonp",
        jsonp: "jsoncallback",
        data: { r: Math.random() },
        success: function (d) {
            if (d.IsSuccess) {
                if (d.Data != null && d.Data != "" && d.Data.length != undefined) {
                    var s = "";
                    s += '<div class="h2">最近登录的服务器</div><ul class="ssllists clearfix ">';
                    for (var i = 0; i < d.Data.length; i++) {
                        if (i < 2) {
                            s += '<li><a href="javascript:;" onclick="_toGameFrm(' + d.Data[i].GameID + ', ' + d.Data[i].ServerID + ');return false;"  >' + d.Data[i].GameName + "" + d.Data[i].ServerName + '</a>（<span>新服推荐</span>）</li>';//add by pengjia 2015.1.28
                            //s += '<li><a href="' + ToGameUrl + '?game_id=' + d.Data[i].GameID + '&server_id=' + d.Data[i].ServerID + '" target="_blank" >' + d.Data[i].GameName + "" + d.Data[i].ServerName + '</a>（<span>新服推荐</span>）</li>';
                        } else {
                            s += '<li><a href="javascript:;" onclick="_toGameFrm(' + d.Data[i].GameID + ', ' + d.Data[i].ServerID + ');return false;"  >' + d.Data[i].GameName + "" + d.Data[i].ServerName + '</a>（<span>火爆</span>）</li>';//add by pengjia 2015.1.28
                            //s += '<li><a href="' + ToGameUrl + '?game_id=' + d.Data[i].GameID + '&server_id=' + d.Data[i].ServerID + '" target="_blank" >' + d.Data[i].GameName + "" + d.Data[i].ServerName + '</a>（<span>火爆</span>）</li>';
                        }
                    }
                    s += '</ul></div>'
                    $(".js_myseverlists").html(s);
                }
            }
        }
    })
    $.ajax({
        url: GameServer + GameId + "/new?getCount=4",
        type: "GET",
        dataType: "jsonp",
        jsonp: "jsoncallback",
        data: { r: Math.random() },
        success: function (d) {
            if (d.IsSuccess) {
                if (d.Data != null && d.Data != "" && d.Data.length != undefined) {
                    var s = "";
                    s += '<div class="h2">推荐服务器</div><ul class="ssllists clearfix ">';
                    for (var i = 0; i < d.Data.length; i++) {
                        if (i < 2) {
                            //s += '<li><a href="' + ToGameUrl + '?game_id=' + d.Data[i].GameID + '&server_id=' + d.Data[i].ServerID + '" target="_blank" >' + d.Data[i].GameName + "" + d.Data[i].ServerName + '</a>（<span>新服推荐</span>）</li>';
                            s += '<li><a href="javascript:;" onclick="_toGameFrm(' + d.Data[i].GameID + ', ' + d.Data[i].ServerID + ');return false;"  >' + d.Data[i].GameName + "" + d.Data[i].ServerName + '</a>（<span>新服推荐</span>）</li>';//add by pengjia 2015.1.28
                        } else {
                            s += '<li><a href="javascript:;" onclick="_toGameFrm(' + d.Data[i].GameID + ', ' + d.Data[i].ServerID + ');return false;"  >' + d.Data[i].GameName + "" + d.Data[i].ServerName + '</a>（<span>火爆</span>）</li>';//add by pengjia 2015.1.28
                            //s += '<li><a href="' + ToGameUrl + '?game_id=' + d.Data[i].GameID + '&server_id=' + d.Data[i].ServerID + '" target="_blank" >' + d.Data[i].GameName + "" + d.Data[i].ServerName + '</a>（<span>火爆</span>）</li>';
                        }
                    }
                    s += '</ul></div>'
                    $(".js_newseverlists").html(s);
                }
            }
        }
    })
}

/* 填写、设置通行证密码层 type类型  funname填写、设置密码后需要执行的方法名 */
function alertPassWord(type, funname) {
    $(".public_nav_top_alert").remove();
    $("body").append(addpasswordHtml);
    document.onkeydown = escFn;
    var winwidth = $(window).width();
    var thisleft = winwidth / 2 - $(".alertbox_addpass").width() / 2;
    var thistop = $(window).height() / 2 - $(".alertbox_addpass").height() / 2 + $(window).scrollTop();
    var bodyheight = Math.max($(document.body).outerHeight(true), $(window).height());
    $(".markdiv").css("height", bodyheight);
    $(".alertbox_addpass").css({ 'left': thisleft, 'top': thistop });
    $(".closeimg").hover(function () {
        $(this).addClass("closeimgactive").removeClass("closeimgdef");
    }, function () {
        $(this).addClass("closeimgdef").removeClass("closeimgactive");
    }).bind("click", function () {
        $(".public_nav_top_alert").remove();
    });

    /* set设置新密码  add填写已有密码*/
    if (type == "set") {
        $(".alertbox_addpass").find(".publogindiv").hide();
        $(".alertbox_addpass").find(".publogindiv").eq(1).show();
    } else if (type = "add") {
        $(".alertbox_addpass").find(".publogindiv").hide();
        $(".alertbox_addpass").find(".publogindiv").eq(0).show();
    }

    $(".word,.js_word_addpwd").live("click", function () {
        $(this).next("input").focus(); $(this).next(".inpsCaptcha").focus();
    });

    $(".inps,.inpsCaptcha_addpwd").live("keydown keyup", function () {
        var _this = $(this);
        if ($(this).val() != "") {
            $(_this.prev(".word")).hide();
        } else {
            $(_this.prev(".word")).show();
        }
    });
    $(".inps,.inpsCaptcha_addpwd").live("paste", function (e) {
        var _this = $(this);
        setTimeout(function () {
            if (_this.val() != "") {
                $(_this.prev(".word")).hide();
            } else {
                $(_this.prev(".word")).show();
            }
        }, 100);
    })

    /* 设置密码输入框格式验证 */
    $(".js_public_pw_write_input").live("keyup", function () {
        if (_VerIsEmptyALL($(this), $(".js_public_password_write_err")) == true) {
            _VerOKALL($(this), $(".js_public_password_write_err"));
            if ($(this).hasClass("js_public_pw_write_input")) {
                if (_VerCheckPasswordALL($(this), $(".js_public_password_write_err")) == true) {
                    _VerOKALL($(this), $(".js_public_password_write_err"));
                }
            }
        }
    });
    /* 填写密码输入框格式验证 */
    $(".js_public_pw_add_input").live("keyup", function () {
        if (_VerIsEmptyALL($(this), $(".js_public_password_add_err")) == true) {
            _VerOKALL($(this), $(".js_public_password_add_err"));
        }
    });

    /* 设置密码提交 */
    //$(".js_formpublic_password_write").submit(function () {
    //    if (_VerificationWritePass() == true) {
    //        var _val = $(".js_public_pw_write_input").val().trim();
    //        funname(_val);
    //        $(".public_nav_top_alert").remove();
    //    }
    //    return false;
    //});
    $(".js_formpublic_password_write").submit(function () {
        var _val = $(".js_public_pw_write_input").val().trim();
        if (_VerificationWritePass() == true) {
            $.ajax({
                url: AjaxCompletePwd,
                type: "GET",
                dataType: "jsonp",
                data: {
                    r: Math.random(),
                    pwd: _val
                },
                beforeSend: function (d) {
                    $(".addreserveBtn").attr("disabled", true);
                    $(".addreserveBtn").addClass("addreserveBtnactive");
                },
                success: function (d) {
                    if (d == "0") {
                        $(".js_public_password_write_err").css("visibility", "visible");
                        $(".js_public_password_write_err .errortext2").html("设置失败！");
                    } else if (d == "1") {
                        $(".js_public_password_write_err").css("visibility", "hidden");
                        funname($(".js_public_pw_write_input").val());
                        $(".public_nav_top_alert").remove();
                    } else if (d == "-1") {
                        $(".js_public_password_write_err").css("visibility", "visible");
                        $(".js_public_password_write_err .errortext2").html("用户未登录！");
                    }
                },
                error: function () {
                    $(".addreserveBtn").attr("disabled", false);
                    $(".addreserveBtn").removeClass("addreserveBtnactive");
                    alert("设置失败！");
                }
            });
        }
        return false;
    });

    $(".js_captchaimg").live("click", function () {
        $(".js_captchaimg").attr("src", RegDeployUrl + "?r=" + Math.random());
    });

    $(".changecode").live("click", function () {
        $(".js_captchaimg").attr("src", RegDeployUrl + "?r=" + Math.random());
    });
    /* 填写密码提交 */
    $(".js_formpublic_password_add").submit(function () {
        var _val = $(".js_public_pw_add_input").val().trim();
        if (_VerificationAddPass() == true) {
            $.ajax({
                url: ValidAccountPassWord,
                type: "GET",
                dataType: "jsonp",
                data: {
                    r: Math.random(),
                    Password: _val,
                    Captcha: $(".inpsCaptcha_addpwd").val()
                },
                beforeSend: function (d) {
                    $(".addreserveBtn").attr("disabled", true);
                    $(".addreserveBtn").addClass("addreserveBtnactive");
                },
                success: function (d) {
                    $(".addreserveBtn").attr("disabled", false);
                    $(".addreserveBtn").removeClass("addreserveBtnactive");
                    $(".js_public_password_add_err").css("visibility", "hidden");
                    $(".js_captcha_addpwd").hide();
                    $(".inpsCaptcha_addpwd").val("");
                    $(".js_captcha_addpwd").find(".word").show();


                    if (d == "Required") {
                        $(".js_public_password_add_err").css("visibility", "visible");
                        $(".js_public_password_add_err .errortext2").html("密码或验证码错误！");
                        $(".js_captchaimg").attr("src", RegDeployUrl + "?fileName=" + Math.random());
                        $(".js_captcha_addpwd").show();
                        $(".inpsCaptcha_addpwd").val("");
                        $(".js_captcha_addpwd").find(".word").show();

                        $(".inpsCaptcha_addpwd").live("focus blur", function () {
                            var exp = new RegExp("^\\w{4,5}$");
                            if ($(this).val().length == 0) {
                                $(".js_public_password_add_err").css("visibility", "visible");
                                $(".js_public_password_add_err .errortext2").html("验证码不能为空！");

                            } else if (!exp.test($(".inpsCaptcha_addpwd").val())) {
                                $(".js_public_password_add_err").css("visibility", "visible");
                                $(".js_public_password_add_err .errortext2").html("验证码格式错误！");

                            }
                        })

                    } else if (d == "Forbidden") {

                        $(".js_captcha_addpwd").hide();
                        $(".inpsCaptcha_addpwd").val("");
                        $(".js_captcha_addpwd").find(".word").show();
                        $(".js_public_password_add_err").css("visibility", "visible");
                        $(".js_public_password_add_err .errortext2").html("登陆次数过多，请稍后再试！");

                    } else if (d == "Success") {

                        $(".js_captcha_addpwd").show();
                        $(".inpsCaptcha_addpwd").val("");
                        $(".js_captcha_addpwd").find(".word").show();
                        $(".js_public_password_add_err").css("visibility", "hidden");

                        funname($("#js_public_pw_add_input").val());
                        $(".public_nav_top_alert").remove();
                    }
                    else if (d == "Trust") {
                        $(".js_public_password_add_err").css("visibility", "visible");
                        $(".js_public_password_add_err .errortext2").html("密码错误！");
                    }
                    else if (d == "NotLogin") {
                        alert("登录超时，请重新登录！");
                        location.reload();
                    }
                },
                error: function () {
                    $(".addreserveBtn").attr("disabled", false);
                    $(".addreserveBtn").removeClass("addreserveBtnactive");
                    alert("填写失败");
                }
            })
        }
        return false;
    });
}

function _VerIsEmptyALL(obj, err) {
    if (obj.val() == "") {
        err.css("visibility", "visible");
        err.find(".errortext2").html("输入框不能为空");
        return false;
    } else {
        return true;
    }
}
function _VerOKALL(obj, err) {
    err.css("visibility", "hidden");
    err.find(".errortext2").html("");
    return true;
}
function _VerCheckPasswordALL(obj, err) {
    var exp = new RegExp("^(.){4,16}$");
    var exp1 = new RegExp("^[\u4e00-\u9fa5]+$");
    if (obj.val().indexOf("<") != -1 || obj.val().indexOf(">") != -1 || exp1.test(obj.val()) == true || exp.test(obj.val()) == false) {
        err.css("visibility", "visible");
        err.find(".errortext2").html("密码长度为4-16位，数字和字母或符号（除>和<）组成");
        return false;
    } else if (!_Verpassword1(obj.val())) {
        err.css("visibility", "visible");
        err.find(".errortext2").html("密码不安全，请不要使用同一个字符");
        return false;
    } else if (!_Verpassword2(obj.val())) {
        err.css("visibility", "visible");
        err.find(".errortext2").html("密码不安全，请不要使用连续字符");
        return false;
    } else {
        return true;
    }
}

function _VerificationWritePass() {
    var isbool = true;
    if (_VerIsEmptyALL($(".js_public_pw_write_input"), $(".js_public_password_write_err")) == false) {
        isbool = false;
    } else {
        if (_VerCheckPasswordALL($(".js_public_pw_write_input"), $(".js_public_password_write_err")) == false) {
            isbool = false;
        }
    }
    return isbool;
}
function _VerificationAddPass() {
    var isbool = true;
    if (_VerIsEmptyALL($(".js_public_pw_add_input"), $(".js_public_password_add_err")) == false) {
        isbool = false;
    }
    return isbool;
}


function _Verificationpass() {
    var isbool = true;
    $(".js_complementform_pass .inps").each(function () {
        if (_VerIsEmptyPass($(this)) == false) {
            isbool = false;
        } else {
            if (_VerCheckChinese($(".js_truename")) == false) {
                isbool = false;
            }
            if (_VerCheckIdCard($(".js_idcard")) == false) {
                isbool = false;
            }
            if (_VerCheckEmail($(".js_email")) == false) {
                isbool = false;
            }
        }
    });
    return isbool;
}

function _Verificationphone() {
    var isbool = true;
    $(".js_complementform_phone .inps").each(function () {
        if (_VerIsEmptyPass($(this)) == false) {
            isbool = false;
        } else {
            if (_VerCheckChinese($(".js_truename_p")) == false) {
                isbool = false;
            }
            if (_VerCheckIdCard($(".js_idcard_p")) == false) {
                isbool = false;
            }
            if (_VerCheckEmail($(".js_email_p")) == false) {
                isbool = false;
            }
            if (_VerCheckPasswordPhone($(".js_Password_p")) == false) {
                isbool = false;
            }
        }
    });
    return isbool;
}

/* 注册验证 */
function _Verification() {
    var isbool = true;
    $(".quickregdiv .inps_quickreg, .inpsCaptcha").each(function () {
        if (_VerIsEmpty($(this)) == false) {
            isbool = false;
        } else {
            if (_VerCheckEnglish($(".js_userName")) == false) {
                isbool = false;
            }
            if (_VerCheckPassword($(".js_Password")) == false) {
                isbool = false;
            }
            if (_VerCheckCaptcha($(".inpsCaptcha")) == false) {
                isbool = false;
            }
            if (_VerCheakeBox($(".chk")) == false) {
                isbool = false;
            }
        }
    });

    return isbool;
}

function _VerCheakeBox(obj) {
    if (!obj.is(":checked")) {
        $(".errorp_quickreg").css("visibility", "visible");
        obj.addClass("inps_quickreg_err");
        $(".errortext2").html("请阅读《光宇通行证用户协议》");
        return false;
    } else {
        return true;
    }
}
function isCardID(sId) {
    var exp = new RegExp(/^((1[1-5])|(2[1-3])|(3[1-7])|(4[1-6])|(5[0-4])|(6[1-5])|71|(8[12])|91)\d{4}((19\d{2}(0[13-9]|1[012])(0[1-9]|[12]\d|30))|(19\d{2}(0[13578]|1[02])31)|(19\d{2}02(0[1-9]|1\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\d{3}(\d|X|x)?$/);
    if (!exp.test(sId)) {
        return false;
    } else {
        return true;
    }
}
function _Verpassword1(value) {
    var flag = false;
    var vaus = value.substring(0, 1)
    for (var i = 1; i < value.length; i++) {
        var valsub = value.substring(i, i + 1);
        if (vaus != valsub) { flag = true; return flag; }
    }
    return flag;
}
function _Verpassword2(value) {
    var str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    var str2 = "abcdefghijklmnopqrstuvwxyz";
    var str3 = "0123456789";
    var flag = true;
    var exp = new RegExp("^[a-z]+$");
    var lower = exp.test(value);

    //如果是非小写字母
    if (isNaN(value) == true && lower == false) {
        for (var i = 0; i < str1.length; i++) {
            if (value.substring(0, value.length) == str1.substring(i, i + value.length)) {
                flag = false;
                return flag;
            }
        }
        return flag;
    }
    //如果是小写字母
    if (isNaN(value) == true && lower == true) {
        for (var i = 0; i < str2.length; i++) {
            if (value.substring(0, value.length) == str2.substring(i, i + value.length)) {
                flag = false;
                return flag;
            }
        }
        return flag;
    }
    //如果是数字
    if (isNaN(value) == false) {
        for (var i = 0; i < str3.length; i++) {
            if (value.substring(0, value.length) == str3.substring(i, i + value.length)) {
                flag = false; return flag;
            }
        }
        return flag;
    }
}
function _VerOK(obj) {
    $(".errorp_quickreg").css("visibility", "hidden");
    $(".errortext2").html("");
    obj.removeClass("inps_quickreg_err");
    return true;
}
function _VerIsEmpty(obj) {
    if (obj.val() == "") {
        $(".errorp_quickreg").css("visibility", "visible");
        obj.addClass("inps_quickreg_err");
        $(".errortext2").html("输入框不能为空");
        return false;
    } else {
        return true;
    }
}
function _VerCheckEnglish(obj) {
    var exp = new RegExp("^[a-zA-Z][A-Za-z0-9]+$");
    if (!exp.test(obj.val())) {
        obj.addClass("inps_quickreg_err");
        $(".errorp_quickreg").css("visibility", "visible");
        $(".errortext2").html("以英文字母开头，只允许字母和数字！");
        return false;
    } else if (obj.val().length > 20 || obj.val().length < 6) {
        obj.addClass("inps_quickreg_err");
        $(".errorp_quickreg").css("visibility", "visible");
        $(".errortext2").html("用户名长度为6-20个字符！");
        return false;
    } else {
        return true;
    }
}

function _VerCheckNameRepeat(obj) {
    $.ajax({
        url: CheckNameRepeatUrl,
        type: "GET",
        dataType: "jsonp",
        data: $.parseJSON("{ \"userName\": \"" + obj.val() + "\", \"r\": " + Math.random() + " }"),
        success: function (d) {
            if (d.result) {
                return true;
            } else {
                obj.addClass("inps_quickreg_err");
                $(".errorp_quickreg").css("visibility", "visible");
                $(".errortext2").html("该用户名已使用！");
                return false;
            }
        }
    });


}

function _VerCheckPassword(obj) {
    var exp = new RegExp("^(.){4,16}$");
    var exp1 = new RegExp("^[\u4e00-\u9fa5]+$");
    if (obj.val().indexOf("<") != -1 || obj.val().indexOf(">") != -1 || obj.val().indexOf(" ") != -1 || exp1.test(obj.val()) == true || exp.test(obj.val()) == false) {
        obj.addClass("inps_quickreg_err");
        $(".errorp_quickreg").css("visibility", "visible");
        $(".errortext2").html("密码长度为4-16位字符,禁用<和>！");
        return false;
    } else if (!_Verpassword1(obj.val())) {
        obj.addClass("inps_quickreg_err");
        $(".errorp_quickreg").css("visibility", "visible");
        $(".errortext2").html("请勿使用同一字符来设置密码！");
        return false;
    } else if (!_Verpassword2(obj.val())) {
        obj.addClass("inps_quickreg_err");
        $(".errorp_quickreg").css("visibility", "visible");
        $(".errortext2").html("请勿使用连续字符来设置密码！");
        return false;
    } else {
        return true;
    }
}

function _VerCheckCaptcha(obj) {
    var exp = new RegExp("^\\w{4,5}$");
    var exp1 = new RegExp("^[A-Za-z0-9]+$");
    if (!exp.test(obj.val())) {
        obj.addClass("inps_quickreg_err");
        $(".errorp_quickreg").css("visibility", "visible");
        $(".errortext2").html("验证码格式错误！");
        return false;
    } else if (!exp1.test(obj.val())) {
        obj.addClass("inps_quickreg_err");
        $(".errorp_quickreg").css("visibility", "visible");
        $(".errortext2").html("验证码格式错误！");
        return false;
    } else {
        return true;
    }
}

function _VerIsEmptyPass(obj) {
    if (obj.val() == "") {
        $(".js_pass_err").css("visibility", "visible");
        $(".errortext2").html("输入框不能为空");
        return false;
    } else {
        return true;
    }
}

function _VerOKPass(obj) {
    $(".js_pass_err").css("visibility", "hidden");
    $(".errortext2").html("");
    return true;
}

function _VerCheckChinese(obj) {
    var exp = new RegExp("^[\u4e00-\u9fa5]{2,8}$");
    if (!exp.test(obj.val())) {
        $(".js_pass_err").css("visibility", "visible");
        $(".errortext2").html("真实姓名必须是2-8个中文！");
        return false;
    } else {
        return true;
    }
}

function _VerCheckIdCard(obj) {
    if (!isCardID(obj.val())) {
        $(".js_pass_err").css("visibility", "visible");
        $(".errortext2").html("请填写正确的18位身份证号码！");
        return false;
    } else {
        return true;
    }
}

function _VerCheckEmail(obj) {
    var exp = new RegExp("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
    if (!exp.test(obj.val())) {
        $(".js_pass_err").css("visibility", "visible");
        $(".errortext2").html("电子邮件格式不正确");
        return false;
    } else {
        return true;
    }
}

function _VerCheckPasswordPhone(obj) {
    var exp = new RegExp("^(.){4,16}$");
    var exp1 = new RegExp("^[\u4e00-\u9fa5]+$");
    if (obj.val().indexOf("<") != -1 || obj.val().indexOf(">") != -1 || obj.val().indexOf(" ") != -1 || exp1.test(obj.val()) == true || exp.test(obj.val()) == false) {
        $(".js_pass_err").css("visibility", "visible");
        $(".errortext2").html("密码长度为4-16位字符,禁用<和>！");
        return false;
    } else if (!_Verpassword1(obj.val())) {
        $(".js_pass_err").css("visibility", "visible");
        $(".errortext2").html("请勿使用同一字符来设置密码！");
        return false;
    } else if (!_Verpassword2(obj.val())) {
        $(".js_pass_err").css("visibility", "visible");
        $(".errortext2").html("请勿使用连续字符来设置密码！");
        return false;
    } else {
        return true;
    }
}

//获取进入游戏的地址并进入游戏(gameId,serverId,cms:true|false)
function _toGameFrm(GameID, ServerId) {
    var isCms = "";
    if (GameID > 10000 && GameID < 10015) {
        //非cms开发
        isCms = false;
        //武易
        if (GameID == 10014) {
            isCms = true;
        }
    } else if (GameID > 10015 && GameID < 20000) {
        //cms开发
        isCms = true;
    }
    var getUrl = isCms ? ToGameUrl + "?cmsType=1" : ToGameUrl;
    $.ajax({
        url: getUrl,
        type: "GET",
        dataType: "jsonp",
        jsonp: "jsoncallback",
        data: {
            gameId: GameID,
            serverId: ServerId,
            r: Math.random()
        },
        success: function (d) {
            if (d && d.Values) {
                window.location.href = d.Values;
            }
        }
    });
}

String.prototype.trim = function () {
    return this.replace(/(^[\s]*)|([\s]*$)/g, "");
}
/*
    页游游戏界面广告弹框
	add: 2014/12/10
	by ligen
*/
$(function () {
    
    //获取url域名
    function change_url(web_url) {
        var durl = /http:\/\/([^\/]+)\//i;
        domain = web_url.match(durl);
        return domain[0];
    }
    var web_url = window.location.href;
    var weburl = change_url(web_url);
    //alert(weburl);
    var weburls = window.location.pathname;
    // alert(weburls);
    var today = new Date();
    var year = today.getFullYear();
    var month = today.getMonth() + 1;
    var day = today.getDate();
    if (weburls != "/Login/login.shtml" && weburls != "/login/login.shtml" && weburls != "/Login.aspx" && weburls != "/login.aspx" && weburls != "/frame/frmMain.shtml" && weburls != "/frame/frmMain.aspx") {
        if (weburl == "http://jzjt.gyyx.cn/" || weburl == "http://qm.gyyx.cn/" || weburl == "http://bztx.gyyx.cn/" || weburl == "http://zsglw.gyyx.cn/" || weburl == "http://kdxy.gyyx.cn/" || weburl == "http://xajt.gyyx.cn/" || weburl == "http://zwx.gyyx.cn/" || weburl == "http://fsgj.gyyx.cn/" || weburl == "http://san.gyyx.cn/" || weburl == "http://ha.gyyx.cn/" || weburl == "http://aj2.gyyx.cn/" || weburl == "http://wy.gyyx.cn/" || weburl == "http://xxd.gyyx.cn/" || weburl == "http://lw.gyyx.cn/" || weburl == "http://xfz.gyyx.cn/" || weburl == "http://fy.gyyx.cn/" || weburl == "http://chi.gyyx.cn/" || weburl == "http://ws.gyyx.cn/" || weburl == "http://7s.gyyx.cn/" || weburl == "http://sxd.gyyx.cn/" || weburl == "http://sg.gyyx.cn/" || weburl == "http://web.gyyx.cn/" || weburl == "http://phs.gyyx.cn/" || weburl == "http://gdjh.gyyx.cn/" || weburl == "http://xyzg.gyyx.cn/" || weburl == "http://lpjt.gyyx.cn/" || weburl == "http://qfz.gyyx.cn/" || weburl == "http://jslh.gyyx.cn/" || weburl == "http://9x.gyyx.cn/" || weburl == "http://tssj.gyyx.cn" || weburl == "http://zt.gyyx.cn" || weburl=="http://wj.gyyx.cn" || weburl=="http://7j.gyyx.cn/" || weburl=="http://wlsh.gyyx.cn" || weburl=="http://smsg.gyyx.cn/" || weburl=="http://jdj.gyyx.cn/") {
            if (year == "2014" && month == "12" && day == "12") {
                var advHtml = '<div id="thankgivingAdv" style="position:absolute;z-index:12000;bottom:0;right:0;width:290px;height:190px;"><span class="close" style="position:absolute;top:0;right:0;width:20px;height:20px;line-height:20px;text-indent:0;cursor:pointer;color:#fff;font-size:14px;font-weight:bold;">X</span><a href="http://action.gyyx.cn/ThankGiving/ganen.html" target="_blank"><img src="http://s.gyyx.cn/publictop/images/adsimg.gif" width="290px" height="190" /></a></div>';



                $("body").append(advHtml);

                advPosition("#thankgivingAdv");
            }
        }
    } else {
        if (year >= "2015") {
            setTimeout('$("body", parent.document).prepend(advHtml)', 1000);//1秒自动打开
            setTimeout('aa()', 1000);//去除多余广告层
            setTimeout('$(".js_ads", parent.document).show()', 4000);//3秒显示弹层
            setTimeout('$(".js_ads", parent.document).hide()', 13000);//10秒自动关闭
            $(".js_ads", parent.document).find(".js_adsclose").live("click", function () {
                $(".js_ads", parent.document).hide();
            });
        }
    }
    function advPosition(target) {
        var bodyWidth, bodyHeight, dialogLeft, dialogTop;
        var alertFn = function () {
            bodyWidth = $(window).width();
            bodyHeight = $(window).height();
            dialogLeft = bodyWidth - $(target).width();
            dialogTop = $(window).height() - $(target).height() + $(document).scrollTop();
            $(target).css({ "left": dialogLeft, "top": dialogTop });
        };
        var hidealertFn = function () {
            $(target).hide();
        }
        $(target).find(".close").live("click", function () {
            hidealertFn();
        });
        alertFn();
        $(window).resize(function () { alertFn() });
        $(window).scroll(function () { alertFn() });
    }

});

