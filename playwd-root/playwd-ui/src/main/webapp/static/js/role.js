var USER_CONFIG = {
    //角色区组获取
    getRoleServerListUrl: 'upload/serverlist',
    //同步角色
    syncRoleUrl: '/User/InsertUser',
    //设置默认角色
    setDefaultRoleUrl: '/User/UserPhoto',
    //获取我的上传列表
    getUploadUrl: '/User/GetUpload',
    //验证码
    captchaUrl: 'http://wanwd.gyyx.cn/user/Captcha',
    //退出登录
    //Loginut: 'http://reg.gyyx.cn/Login/LogoutJsonp/',
};
var isIE6 = /msie 6/i.test(navigator.userAgent);
if (isIE6) {
    try { document.execCommand('BackgroundImageCache', false, true); } catch (e) { }
}

ChinaCaptcha.prototype.subFn = function () { };
var chinaCaptcha1 = new ChinaCaptcha($(".js_bindRole"), {
    bid: "jnajq"
});
console.log(chinaCaptcha1);
chinaCaptcha1.init();
$.fn.extend({
    //用户中心我的角色
    uRoleFn: function () {
        //$().getRoleList();
        console.log(1);
    },
    //获取我的角色列表
    getRoleList: function (data) {
        if (data.IsSuccess) {
            var
                dCont = data.List,
                divHtml = "";
            if (dCont && dCont[0]) {
                for (var i = 0; i < dCont.length; i++) {
                    var
                        //是否默认角色
                        isDefault = dCont[i].IsDefault,
                        //默与不默认标志
                        defBtnHtml = isDefault ?
                            ' class="js_qxbut role_defaultbtn">当前默认' :
                            ' class="js_upbut js_SetDefaultRole" data-code="' + dCont[i].Code + '">设为默认';

                    divHtml += '' +
                        '<div class="wd_js_l">' +
                        '    <span>' +
                        '        <img src="' + USER_CONFIG.portraitPath + dCont[i].Picture + '.jpg"></span>' +
                        '    <p>角色：' + dCont[i].NickName + '</p>' +
                        '    <p>服务器：' + dCont[i].ServerName + '</p>' +
                        '    <p class="rolebox_btn js_but">' +
                        '       <a href="javascript:;"' + defBtnHtml + '</a>' +
                        '    </p>' +
                        '</div>';
                }
                //如果有角色就追加重新同步按钮
                divHtml += '<div class="wd_js_c"><a href="javascript:;" class="js_SynchroBtn">重新同步</a></div>';

                $("#js_RoleList").html(divHtml);

                //点击重新同步按钮
                $(".js_SynchroBtn").click(function () {
                    $().setSyncForm();
                    return false;
                });

                //设置默认角色
                $(".js_SetDefaultRole").click(function () {
                    var code = $(this).attr("data-code");
                    $().setDefaultRole(code);
                    return false;
                });
            }
        }
    },
    //获取验证码类型
    getCaptchaType: function () {

    },

    //配置同步角色表单
    setSyncForm: function () {
        var
            $netSel = $("#js_Net"),
            $serverSel = $("#js_Server"),
            $qq = $("#js_QQ").val("");

        //刷新验证码
        $(".Captcha").attr("src", USER_CONFIG.captchaUrl + "?r=" + Math.random());
        $(".Captcha").click(function () {
            $(".Captcha").attr("src", USER_CONFIG.captchaUrl + "?r=" + Math.random());
        });
        //显示表单
        $("#js_RoleList").append($("#js_SyncForm").show());
        //绑定服务器
        $netSel.bindServer({
            url: USER_CONFIG.getRoleServerListUrl,
            $bindObj: $serverSel
        });
        //取消重新同步
        $("#js_CancelSync").click(function () {
            $("#js_SyncForm").hide();
            return false;
        });
        //QQ输入限制
        $("#js_QQ")[0].oninput = function () {
            this.value = this.value.replace(/\D/g, "");
        }
        if (window.attachEvent) {
            $qq[0].attachEvent("onpropertychange", function (e) {
                var _this = e.srcElement, oldVal = _this.value;
                if (e.propertyName == "value" && /\D/.test(oldVal)) {
                    _this.value = oldVal.replace(/\D/, '');
                }
            });
        }

        //同步角色
        $("#js_SyncRole").unbind("click").bind("click", function () {
            var validData = $().validSyncForm();
            var able = $(this).attr("data-able");
            if (validData && able == "true") {
                $(this).attr("data-able", false);
                var subFn = function () {

                    $().syncRole(validData,$(".js_insertCaptcha").val());
                };
                switch (chinaCaptcha1.captcahSwitchOpen) {
                    case -1://不需要验证码
                    case 0://需要普通验证码
                        subFn();
                        break;
                    case 1://需要中文验证码
                        chinaCaptcha1.subFn = subFn;
                        console.log(chinaCaptcha1.subFn);
                        chinaCaptcha1.createCaptchaPop();
                        break;
                    default:
                        break;
                }
            }
            return false;
        });
    },

    //验证同步角色表单
    validSyncForm: function () {
        var
            netId = $("#js_Net").val(),
            $serverSel = $("#js_Server"),
            serverId = $serverSel.val(),
            $serverOpt = $serverSel.find("option:selected"),
            serverName = $.trim($serverOpt.text()),
            serverIp = $serverOpt.attr("data-ip"),
            qq = $.trim($("#js_QQ").val()),
            $error = $("#js_Error");
        if (!netId) {
            $error.html("请选择区组！").show();
            return false;
        } else if (!serverId || !serverName) {
            $error.html("请选择服务器！").show();
            return false;
        } else if (qq && !/^\d{5,15}$/g.test(qq)) {
            $error.html("QQ格式不正确！").show();
            return false;
        }
        // else if (!captcha) {
        //     $error.html("请输入验证码！").show();
        //     return false;
        // }
        else {
            return {
                ServerName: serverName,
                ServerId: serverId,
                NetId: netId,
                QQ: qq,
                ServerIP: serverIp,
            };
        }
    },

    //同步角色
    syncRole: function (sdata,captcha) {
        var ddata = {
            r: Math.random()
        }
        var captcha1={ captcha: captcha }
        $.extend(ddata, sdata,captcha1);
        $.ajax({
            url: USER_CONFIG.syncRoleUrl,
            type: "GET",
            dataType: "JSON",
            timeout: 5000,
            data: ddata,
            success: function (data) {
                if (data.IsSuccess) {
                    if (data.Message == "1小时内同步次数过多,请重新登录") {
                        alert("1小时内同步次数过多,请重新登录");
                        $().loginout();
                    } else {
                        alert("同步成功！");
                        $().refreshPage();
                    }
                } else {
                    alert(data.Message);
                    if (data.Values == "-3") {
                        $().refreshPage();
                    } else if (data.Message == "1小时内同步次数过多,请重新登录") {
                        $().loginout();
                    } else if (data.Message == "验证码错误") {
                        $(".js_refreshChinaCaptcha").click();
                    } else {
                        $(".js_chinaCaptcha_Alert").remove();
                    }
                }
                $(".Captcha").attr("src", USER_CONFIG.captchaUrl + "?r=" + Math.random());
                $("#js_SyncRole").attr("data-able", true);
                $(".js_insertCaptcha").val("");
            },
            error: function (req, stau) {
                $("#js_SyncRole").attr("data-able", true);
                if (stau = "timeout") {
                    alert(" :(请求接口超时，请重试！");
                    return false;
                }
                alert(" :(程序接口出错，请刷新页面重试！");
            }
        });
    },

    //设置默认角色
    setDefaultRole: function (code) {
        $.ajax({
            url: USER_CONFIG.setDefaultRoleUrl,
            type: "GET",
            dataType: "JSON",
            data: {
                r: Math.random(),
                Code: code
            },
            success: function (data) {
                if (data.IsSuccess) {
                    alert("设置成功！");
                    $().refreshPage();
                } else {
                    if (data.Values == "-3") {
                        alert(data.Message);
                        $().refreshPage();
                    }
                    alert(":( 设置失败，请刷新页面重试！");
                }

            },
            error: function () {
                alert(":( 程序出错，请刷新页面重试！");
            }
        });
    },

    //当select改变时隐藏错误消息
    changeHideError: function ($error, defaultVal, errorTxt) {
        var $this = $(this)
        if ($this.val() != defaultVal && $error.html() == errorTxt) {
            $error.hide();
        }
    },

    //绑定服务器
    bindServer: function (options) {
        var
            $this = $(this),
            defaults = {
                url: "",
                $bindObj: $("#ServerId")
            };

        $.extend(defaults, options);

        $this.val("").unbind("change").bind("change", function () {
            var
                thisVal = this.value,
                optHtml = '<option value="">&nbsp;请选择服务器</option>';

            $(this).changeHideError($("#js_Error"), "", "请选择区组！");

            if (!thisVal) {
                defaults.$bindObj.html(optHtml);
                return false;
            }
            defaults.$bindObj.html('<option value="">加载中...</option>');
            $.ajax({
                url: defaults.url,
                type: "POST",
                dataType: "JSON",
                data: {
                    netType: thisVal,
                    gameId: 2,
                    r: Math.random()
                },
                success: function (data) {
                    if (data.IsSuccess) {
                        var dCont = data.List;
                        for (var i = 0; l = dCont.length, i < l; i++) {
                            optHtml += "<option data-ip='" + dCont[i].ServerIP + "' value='" + dCont[i].ServerCode + "'>&nbsp;" + dCont[i].ServerName + "</option>"
                        }
                        defaults.$bindObj.html(optHtml).unbind("change").bind("change", function () {
                            $(this).changeHideError($("#js_Error"), "", "请选择服务器！");
                        });
                    } else {
                        defaults.$bindObj.html('<option value="">:(服务器列表加载失败</option>');
                        alert(data.Message);
                    }
                }
            });
        });
    },

    //获取指定url参数值
    getQuery: function (name) {
        var
            reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"),
            r = window.location.search.substr(1).match(reg);
        return null != r ? r[2] : null
    },

    //将毫秒转成年月日时分秒
    changeTime: function (time, needSec, dateSpstr, secSpstr) {
        if (time != null) {
            var dateArr = [];
            var secArr = [];
            var dateSpstr = dateSpstr ? dateSpstr : "-";
            var secSpstr = secSpstr ? secSpstr : ":";
            var date = new Date(parseInt(time.replace("/Date(", "").replace(")/", ""), 10));
            var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
            var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();

            dateArr.push(date.getFullYear(), month, currentDate);
            if (needSec) {
                var currentHour = date.getHours();
                var currentMin = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
                var currentSec = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
                secArr.push(currentHour, currentMin, currentSec);
                return dateArr.join(dateSpstr) + " " + secArr.join(secSpstr);
            } else {
                return dateArr.join(dateSpstr)
            }
        } else {
            return "";
        }
    },

    //刷新页面防末尾"#"不刷新
    refreshPage: function () {
        var href = window.location.href;
        href = href.replace(/#*$/, '');
        window.location.href = href;
    },
    //退出登录
    loginout: function () {
        $.ajax({
            url: USER_CONFIG.Loginut,
            type: "GET",
            dataType: "jsonp",
            jsonp: "jsoncallback",
            data: { r: Math.random() },
            success: function (d) {
                if (d.IsSuccess) { location.reload(); }
                else { alert(d.Message); }
            }
        });
    },
    //空数据处理
    emptySet: function (str, newStr) {
        if (!str || str == "null" || str == "" || str == undefined || str == null) {
            return newStr || "";
        } else {
            return str;
        }
    }
});

//用户中心公用加载
$(function () {
    $().headPortrait();
});
