/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：陈巧玲
 * 联系方式：chenqiaoling@gyyx.cn
 * 创建时间： 2014/4/8 
 * 版本号：v1.0

 * 功能： 登录

 * -------------------------------------------------------------------------*/
$(function () {
    $(".navbar-nav").hide();//隐藏顶部个人信息和顶部右侧光宇人
    $(".flleft").hide();//隐藏顶部信息集合
    $(".btn-rounded").hide();//隐藏顶部信息集合
    $(".Signin").submit(function () {
        var localhref = 'http://oa.gyyx.cn';
        var $form = $(this);
        $.ajax({
            url: "http://oa.gyyx.cn/signin/?continue=" + localhref,
            type: 'POST',
            data: $form.serialize(),
            success: function (d) {
                if (d.Ret == 0) {
                    window.location.href = window.location.href;
                }
                else {
                    $(".js_errortip").empty().append(d.Message);
                }
            }
        });
        return false;
    });
});
