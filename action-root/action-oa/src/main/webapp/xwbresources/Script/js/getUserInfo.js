/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：陈巧玲
 * 联系方式：chenqiaoling@gyyx.cn
 * 创建时间： 2014/4/8 
 * 版本号：v1.0

 * 功能： 获取登录用户信息 展示到公共顶部个人信息隐藏框内

 * -------------------------------------------------------------------------*/

function getUserInfo() {
    $.ajax({
        url: "http://oa.gyyx.cn/StaffApply/DetailsForUser?jsoncallback=?",
        type: "GET",
        dataType: "jsonp",
        data: { r: Math.random() },
        success: function (d) {
            if (d.Ret == 0 && d.Data != null ) {
                var RealName = d.Data.RealName ? d.Data.RealName : "";
                var AgencyName = d.Data.Agency.AgencyName ? d.Data.Agency.AgencyName : "";
                var Email = d.Data.Email ? d.Data.Email : "";
                var MobilePhoneNo = d.Data.MobilePhoneNo ? d.Data.MobilePhoneNo : "";
                var RtxNo = d.Data.RtxNo ? d.Data.RtxNo : "";
                var userInfo = '<section class="dropdown-menu aside-xl on animated fadeInLeft no-borders lt">'
                        + '<div class="wrapper lter m-t-n-xs">'
                            + '<a href="#" class="thumb pull-left m-r">'
                                + '<img src="http://oa.gyyx.cn/Contents/images/avatar.jpg" class="img-circle">'
                            + '</a>'
                            + '<div class="clear">'
                                + '<a href="#">'
                                    + '<span class="text-white font-bold">' + RealName + '</span>'
                                + '</a>'
                                + '<small class="block">' + AgencyName + '</small>'
                                + '<a href="#">'
                                    + '<span class="block text-white font-bold">' + Email + '</span>'
                                + '</a>'
                                + '<span class="block text-white font-bold">' + MobilePhoneNo + '</span>'
                                + '<span class="block text-white font-bold">' + RtxNo + '</span>'
                            + '</div>'
                        + '</div>'
                    + '</section>';
                $("#hiddenUserInfo").append(userInfo);
                var LoginName = '<span class="logna">'+ RealName + '</span>' + '<b class="caret"></b>';
                $("#LoginNames").append(LoginName);
            }
        }
    });
}
getUserInfo();