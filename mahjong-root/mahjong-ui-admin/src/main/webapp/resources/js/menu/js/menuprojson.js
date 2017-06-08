$(function () {
    $.getScript("../resources/js/menu/data/Promotertxt.txt?r=" + Math.random(), function () {
        var obj = bottomdata;
        var bottommenulist = "", bmlyer = "";
        for (var i = 0; i < obj.length; i++) {
            if (i == 0) {
            	 bottommenulist += '<a href="javascript:;" class="s">' + obj[i].FirName + '</a>';
                 bmlyer += '<div class="overlay s s' + obj[i].Code + ' overlay' + obj[i].Code + '"><nav><ul>';
            } else {
            	bottommenulist += '<a href="javascript:;" class="leftline s">' + obj[i].FirName + '</a>';
                bmlyer += '<div class="overlay s s' + obj[i].Code + ' overlay' + obj[i].Code + '"><nav><ul>';
            }
            for (var j = 0; j < obj[i].Sedlist.length; j++) {
                if (obj[i].Sedlist[j].SedName == "退出登录") {
                    if (window.location.pathname.indexOf("findpwd") < 0) {
                        bmlyer += '<li><a href="javascript:;" class="js_loginout">' + obj[i].Sedlist[j].SedName + '</a></li>';
                    } else {
                        bmlyer += '<li><a href="javascript:;" class="js_loginouts">' + obj[i].Sedlist[j].SedName + '</a></li>';
                    }
                } else {
                    bmlyer += '<li><a href="'+ rc + obj[i].Sedlist[j].Sedurl + '">' + obj[i].Sedlist[j].SedName + '</a></li>';
                }

            }
            bmlyer += "</ul></nav></div>";
        }
        $(".js_bottommenu").empty().html(bottommenulist);
        $("body").append(bmlyer);
        $(".js_bottommenu a").click(function(){
        	if($(this).hasClass("on")){
        		$(".overlay"+($(this).index()+1)).hide();
        		$(this).removeClass("on");
        	}else{
        		$(this).addClass("on");
            	$(this).siblings("a").removeClass("on");
            	$(".overlay").hide();
            	$(".overlay"+($(this).index()+1)).fadeIn();
        	}
        });
        $(".container").click(function(){
        	$(".overlay").hide();
        	$(".js_bottommenu a").removeClass("on");
        });
        
        function loginSession() {
            $.ajax({
                type: "get",
                url: rc + "/login/session",
                data: { r: Math.random() },
                success: function (data) {
                    if (data.isSuccess) {
                        $(".js_username").empty().html(data.data.account);
                    } else {
                        window.location.href = rc + "/login/"
                        return false;
                    }
                },
                error: function () {
                    console.log("判断是否登录接口异常：/login/session");
                }
            });
        }
        if (window.location.pathname.indexOf("findpwd")<0) {
            loginSession();
        }
        function logout() {
            $.ajax({
                type: "get",
                url: rc + "/user/logout",
                data: { r: Math.random() },
                success: function (data) {
                    if (data.isSuccess) {
                        window.location.href = rc + "/login/"
                    } else {
                        return false;
                    }
                },
                error: function () {
                   console.log("注销登录接口异常：/user/logout");
                }
            });
        }
        $(".js_loginout").click(function () {
            logout();
        });
        $(".js_loginouts").click(function () {
            window.location.href = rc + "/login/";
        });
    });
});
