function escMsg(ev) {
    var oEvent = window.event || ev;
    if (oEvent.keyCode == 27) {
        $(".alertMsg_alert").remove();
    }
}
function alertMsg(alertMsginfo, alertMsgtitle, fn) {
    var alertMsgHtml = '<div class="alertMsg_alert">' +
                '    <div class="alertMsgBox">' +
                '           <div class="alertMsgBox_body">' +
                '               <a href="javascript:void(0)" class="closeMsgimg" ></a>' +
                '               <div class="Msgtitle">' + alertMsgtitle +
                '               </div>' +
                '               <div class="Msginfodiv">' + alertMsginfo +
                '               </div>' +
                '           </div>' +
                '    </div>' +
                '    <div id="Msgmark" class="markMsg"></div>' +
                '</div>';
    $("body").append(alertMsgHtml);
    document.onkeydown = escMsg;
    var winMsgwidth = $(window).width();
    var thisMsgleft = (winMsgwidth / 2 - $(".alertMsgBox").width() / 2) - 5;
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
        $(".alertMsg_alert").remove();
        $(".markMsg").remove();
        if (fn) {
            fn();
        }
    });
}