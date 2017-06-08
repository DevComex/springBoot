/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：李根
 * 联系方式：ligen@gyyx.cn
 * 创建时间： 2014/4/16 
 * 版本号：v1.0

 * 功能： 公共弹出层方法

 * -------------------------------------------------------------------------*/

/*创建弹出层*/
function alertMsg(js_Msg_title, js_Msg_info, fn) {
    var alertMsgHtml = '<a data-toggle="modal" href="#js_Msg" style="display:none;" class="js_Msg"></a>' +
                        '<div class="modal fade" id="js_Msg">' +
                        '  <div class="modal-dialog">' +
                        '      <div class="modal-content">' +
                        '          <div class="modal-header">' +
                        '             <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
                        '             <h4 class="modal-title js_Msg_title">' + js_Msg_title +
                        '             </h4>' +
                        '        </div>' +
                        '        <div class="modal-body">' +
                        '            <p class="js_Msg_info" style="color:red;">' + js_Msg_info +
                        '            </p>' +
                        '       </div>' +
                        '      <div class="modal-footer">' +
                        '          <button type="button" class="btn btn-default close_js_Msg" data-dismiss="modal">关闭</button>' +
                        '      </div>' +
                        '  </div>' +
                        ' </div>' +
                        '</div>';
    $("body").append(alertMsgHtml);
    $(".js_Msg").trigger("click");
    $(".close_js_Msg , .close").unbind().bind("click", function () {
        $("js_Msg_info").html("");
        if (fn) {
            fn();
        }
    });
};
//浏览器升级弹层
function alertupdateMsg(alertMsginfo, alertMsgtitle, fn) {
    var alertupdateMsgHtml = '<div class="alertMsg_alert">' +
                '    <div class="alertMsgBox" style="top: 0; width: 490px; position: absolute; z-index: 20001; background: #fff; border: 5px solid #c6c6c6; border-radius: 3px 3px 3px 3px; ">' +
                '           <div class="alertMsgBox_body">' +
                '               <div class="Msgtitle" style="background: #f6f6f6; border-bottom: 1px solid #DDDDDD; height: 30px; padding: 10px 0 0 10px; font-family: SimHei; font-size: 14px; color: #000; ">' + alertMsgtitle +
                '               </div>' +
                '               <div class="Msginfodiv" style=" width: 95%; height: 150px; padding: 15px 0 0 5%;">' + alertMsginfo +
                '               </div>' +
                '               <div class="bottomMsgbtn" style="width: 95%; height: 30px; padding: 0 5% 0 0; text-align: right;">' +
                '               <input type="button" class="iptMsgbtn" value="关闭" />' +
                '               </div>' +
                '           </div>' +
                '    </div>' +
                '    <div id="Msgmark" class="markMsg" style="background: none repeat scroll 0 0 #000000; left: 0; width: 100%; height: 100%; opacity: 0.5; filter: alpha(opacity=50); position: absolute; top: 0; z-index: 20000; "></div>' +
                '</div>';
    $("body").append(alertupdateMsgHtml);
    var winMsgwidth = $(window).width();
    var thisMsgleft = winMsgwidth / 2 - $(".alertMsgBox").width() / 2;
    var thisMsgtop = $(window).height() / 2 - $(".alertMsgBox").height() / 2 + $(window).scrollTop();
    var bodyMsgheight = Math.max($(document.body).outerHeight(true), $(window).height());
    $(".markMsg").css("height", bodyMsgheight);
    $(".alertMsgBox").css({ 'left': thisMsgleft, 'top': thisMsgtop });
    $(".iptMsgbtn").unbind().bind("click", function () {
        $(".alertMsgBox").remove();
        $(".markMsg").remove();
        if (fn) {
            fn();
        }
    });
    $(".closeMsgimg").unbind().bind("click", function () {
        $(".alertMsgBox").remove();
        $(".markMsg").remove();
        if (fn) {
            fn();
        }
    });
};
//取消或关闭弹层清空表单数据
$(function () {
    $(".close_js_Msg , .close").unbind().bind("click", function () {
        $(this).parent().parent().find(".form-control").val("");
        var allselects = $(this).parent().parent().find("select");//关闭弹层清空input
        //关闭弹层初始化select为第一项
        for (var i = 0; i < allselects.length; i++) {
            allselects[i].options[0].selected = true;
        }
        $(".js_Message").empty();
        $(".error").empty();
    });
    $(".closes").unbind().bind("click", function () {
        $(this).removeClass("closes");
    });
    //判断浏览器升级
    //判断浏览器
    if (navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE6.0") {
        //alert("IE 6.0");
        //直接调用弹层
        alertupdateMsg('浏览器版本过低导致部分功能不可用，建议&nbsp;<a href="http://oa.gyyx.cn/HelpCenter/update.html" target="_blank" style="color:#0094ff; text-decoration:underline;">点此升级</a>', '浏览器升级');
    }
    else if (navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE7.0") {
        //alert("IE 7.0");
        alertupdateMsg('浏览器版本过低导致部分功能不可用，建议&nbsp;<a href="http://oa.gyyx.cn/HelpCenter/update.html" target="_blank" style="color:#0094ff; text-decoration:underline;">点此升级</a>', '浏览器升级');
    }
});