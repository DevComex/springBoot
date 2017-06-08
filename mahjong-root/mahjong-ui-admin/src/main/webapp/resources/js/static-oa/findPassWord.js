jQuery.fn.extend({
    RetrievalPassWord: function (options) {
        var Defaults = {
            AjaxCapachaType: rc + "/capacha/type",
            AjaxFindPws: rc + "/user/findpwdsubmit",
            AjaxCheck: rc + "/user/findpwdcheckacc",
            AjaxSendSms: rc + "/user/findpwdsendsms",
            AjaxGetca: rc + "/captcha/create",
            DefaultMsgID: ".prompt",
            ErrorMsgID: ".error",
            bid: "lgct",
            Capachasource: "lgct"
        };
        Defaults = $.extend(Defaults, options);

        var $Submit = $(this);
        var sendtimer = null;
        $(".js_capimg").attr("src", rc + "/captcha/create?bid=check&r=" + Math.random());
        $(".js_fwkbq").click(function () {
            $(".js_capimg").attr("src", rc + "/captcha/create?bid=check&r=" + Math.random());
        });
        $(".js_capimg").click(function () {
            $(this).attr("src", rc + "/captcha/create?bid=check&r=" + Math.random());
        });
        $(".js_prev").click(function () {
        	window.location.reload(true);
        });
        $(".item_p :input").focus(function () {
            $Submit.find(".tip").css("z-index", "0");
            $Submit.find(Defaults.DefaultMsgID).css("display", "none");
            if ($(this).val() == "") {
                $Submit.find(".item_p").css("z-index", "0");
                $(this).parents(".item_p").css("z-index", "1");
                $(this).parents(".item_p").find(Defaults.ErrorMsgID).css("display", "none");
                $(this).parents(".item_p").find(Defaults.DefaultMsgID).css("display", "none");
                $(this).parents(".item_p").find(Defaults.DefaultMsgID).eq(0).css({ "display": "inline-block", "z-index": "100" });
            }
            $Submit.find(".item_p").find("span").removeClass("ipt_box_foc");
            $(this).parent("span").removeClass("ipt_box_err").addClass("ipt_box_foc");

        });
        $Submit.find("input[name='account']").keyup(function () {
            var val = $(this).val();
            var pattern = new RegExp("[^A-Za-z0-9]+", "g");
            $(this).val(val.replace(pattern, ''));
        });
        $Submit.find("input[name='password']").keyup(function () {
            var val = $(this).val();
            var pattern = new RegExp("[^A-Za-z0-9~!@#$%^&*]+", "g");
            $(this).val(val.replace(pattern, ''));
        });
        $Submit.find("input[name='confirmPassword']").keyup(function () {
            var val = $(this).val();
            var pattern = new RegExp("[^A-Za-z0-9~!@#$%^&*]+", "g");
            $(this).val(val.replace(pattern, ''));
        });
        $(".js_reg_sendsms").click(function () {
            _SendRegisterSMS();
        });
        var _SendRegisterSMS = function () {
            clearInterval(sendtimer);
            $.ajax({
                url: Defaults.AjaxSendSms,
                type: "post",
                data: {
                    "account": $(":input[name=account]").val(),
                    r: Math.random()
                },
                beforeSend: function () {
                    $(".js_reg_sendsms").attr("disabled", "false").removeClass("btn-danger").addClass("disabled").val("发送中...");
                    var seconds = 10;
                    sendtimer = setInterval(function () {
                        if (seconds > 1) {
                            seconds = seconds - 1;
                        } else {
                            clearInterval(sendtimer);
                            $(".js_reg_sendsms").removeAttr("disabled").removeClass("disabled").addClass("btn-danger").val("获取验证码");
                            return false;
                        }
                    }, 1000);
                },
                success: function (d) {
                    if (d.isSuccess) {
                        clearInterval(sendtimer);
                        $(":input[name=smsCode]").parents(".item_p").find(".tip").css("display", "none");
                        $(":input[name=smsCode]").parents(".item_p").find(Defaults.DefaultMsgID).eq(1).css({ "display": "inline", "z-index": "101" });
                        $(".js_reg_sendsms").attr("disabled", true).removeClass("btn-danger").addClass("disabled").val("重新获取(60)");
                        _CountDownReg($(".js_reg_sendsms"), 60);
                    } else {
                        clearInterval(sendtimer);
                        $(":input[name=smsCode]").parents(".item_p").find(".tip").css("display", "none");
                        if ($(":input[name=smsCode]").parents(".item_p").find(".tip1").length == 0) {
                            $(":input[name=smsCode]").parents(".item_p").append('<span class="prompt tip tip1"><i></i><b>' + d.data.subMsg + '</b></span>');
                        }
                        $(":input[name=smsCode]").parents(".item_p").find(Defaults.DefaultMsgID).eq(1).css({ "display": "inline", "z-index": "101" });
                        $(".js_reg_sendsms").removeAttr("disabled").removeClass("disabled").addClass("btn-danger").val("重新获取");
                    }
                }
            });
        }
        $(".item_p :input").not(":input[type=button]").blur(function () {
            if ($(this)._VerIsEmpty()) {
                $(this)._VerOK();

                if ($(this).attr("name") == "password" && _VerCheckPassword($(this))) {
                	$(this)._VerOK();
                }
                if ($(this).attr("name") == "confirmPassword" && _VerCheckConfirmPassword($(this), $(":input[name=password]"))) {
                	 $(this)._VerOK();
                }
                if ($(this).attr("name") == "captchaCode" && $(this)._VerCheckCaptcha()) {
                	$(this)._VerOK();
                }
            }
        });
        $(".js_check").click(function () {
            $(".js_bindphone").empty().html("");
            if (_Verificationaccount()) {
                $.ajax({
                    type: "post",
                    url: Defaults.AjaxCheck,
                    data: {
                        account: $(":input[name=account]").val(),
                        captchaCode: $(":input[name=captchaCode]").val(),
                        r: Math.random()
                    },
                    success: function (d) {
                        if (d.isSuccess) {
                            $(".js_bindphone").empty().html(d.data);
                            $(".js_fir").addClass("dis");
                            $(".js_sed").removeClass("dis");
                            $(".js_reg_sendsms").removeAttr("disabled").removeClass("disabled").addClass("btn-danger").val("获取验证码");
                        } else {
                            alert(d.message);
                            $(":input[name=captchaCode]").val("");
                            $(":input[name=captchaCode]").parents(".item_p").find(".error").css("display", "inline-block").html("<b>" + $(":input[name=captchaCode]").attr("data-tip") + "不能为空</b>");
                            $(".js_capimg").attr("src", rc + "/captcha/create?bid=check&r=" + Math.random());
                            return false;
                        }
                    },
                    error: function () {
                        console.log("检查用户接口异常：/user/findpwdcheckacc");
                    }
                });
            } else {
                return false;
            }
        });
        $(".js_submit").click(function () {
            if (_Verificationpsword()) {
                $.ajax({
                    type: "post",
                    url: Defaults.AjaxFindPws,
                    data: {
                        account: $(":input[name=account]").val(),
                        password: $(":input[name=password]").val(),
                    	confirmPassword: $(":input[name=confirmPassword]").val(),
                        smsCode: $(":input[name=smsCode]").val(),
                        r: Math.random()
                    },
                    success: function (data) {
                        if (data.isSuccess) {
                            alert(data.message);
                            window.location.href = rc + "/login/";
                        } else {
                            alert(data.message);
                            $(":input[name=password]").val("");
                            $(":input[name=password]").parents(".item_p").find(".error").css("display", "inline-block").html("<b>" + $(":input[name=account]").attr("data-tip") + "不能为空</b>");
                            $(":input[name=confirmPassword]").val("");
                            $(":input[name=confirmPassword]").parents(".item_p").find(".error").css("display", "inline-block").html("<b>" + $(":input[name=account]").attr("data-tip") + "不能为空</b>");
                            return false;
                        }
                    },
                    error: function () {
                        console.log("修改密码接口异常：/user/findpwdsubmit");
                    }
                });
            } else {
                return false;
            }
        });
        var _Verificationaccount = function () {
            var isbool = true;
            $(".item_p :input").not($(":input[name=password]")).not($(":input[name=confirmPassword]")).not($(":input[name=smsCode]")).each(function () {
                if (!$(this)._VerIsEmpty()) {
                    isbool = false;
                } else {
                    if ($(this).attr("name") == "captchaCode" && !$(":input[name=captchaCode]")._VerCheckCaptcha()) {
                        isbool = false;
                    }
                }
            });
            return isbool;
        };
        var _VerCheckPassword = function (obj) {
            var exp1 = new RegExp("^[\u4e00-\u9fa5]+$");
            if ($.trim(obj.val()).length > 16 || $.trim(obj.val()).length < 6) {
                obj.parents(".item_p").find(".succ").hide();
                obj.parent("span").addClass("ipt_box_err");
                obj.parents(".item_p").find(".error").css("display", "inline-block").html("<i></i><b>密码过短，必须在6-16个非空格字符之间</b>");
                return false;
            }  else {
                return true;
            }
        };
        var _VerCheckConfirmPassword = function (obj2, obj1) {
            if (obj2.val() != obj1.val()) {
                obj2.parents(".item_p").find(".succ").hide();
                obj2.parent("span").addClass("ipt_box_err");
                obj2.parents(".item_p").find(".error").css("display", "inline-block").html("<i></i><b>两次输入的密码不一致，请重新输入</b>");
                return false;
            } else {
                return true;
            }
        };
        var _Verificationpsword = function () {
            var isbool = true;
            $(".item_p :input").not($(":input[name=account]")).not($(":input[name=captchaCode]")).each(function () {
                if (!$(this)._VerIsEmpty()) {
                    isbool = false;
                } else {
                    if ($(this).attr("name") == "password" && !_VerCheckPassword($(":input[name=password]"))) {
                        isbool = false;
                    }
                    if ($(this).attr("name") == "confirmPassword" && !_VerCheckConfirmPassword($(":input[name=confirmPassword]"), $(":input[name=password]"))) {
                        isbool = false;
                    }
                    if ($(this).attr("name") == "sms" && !$(":input[name=sms]")._VerCheckCaptcha()) {
                        isbool = false;
                    }
                }
            });
            return isbool;
        };
        var _CountDownReg = function (obj, seconds) {
           var timer = setInterval(function () {
                if (seconds > 1) {
                    seconds = seconds - 1;
                    obj.val("重新获取(" + seconds + ")");
                } else {
                    clearInterval(timer);
                    obj.removeAttr("disabled").removeClass("disabled").addClass("btn-danger").val("重新获取");
                }
            }, 1000);
        }
    },
    _VerOK: function () {
        $(this).parents(".item_p").find(".tip").hide();
        $(this).parent("span").removeClass("ipt_box_err");
        $(this).parents(".item_p").find(".succ").css("display", "inline-block");
        return true;
    },
    _VerIsEmpty: function () {
        if ($(this).val() == "") {
            $(this).parents(".item_p").find(".succ").hide();
            $(this).parent("span").addClass("ipt_box_err");
            $(this).parents(".item_p").find(".tip").css("display", "none");
            $(this).parents(".item_p").find(".error").css("display", "inline-block").html("<b>" + $(this).attr("data-tip") + "不能为空</b>");
            return false;
        } else {
            return true;
        }
    },
    _VerCheckCaptcha: function () {
        var exp = new RegExp("^\\w{4,5}$");
        if (!exp.test($(this).val())) {
            $(this).parents(".item_p").find(".succ").hide();
            $(this).parent("span").addClass("ipt_box_err");
            $(this).parents(".item_p").find(".error").css("display", "inline-block").html("<b>验证码格式不正确</b>");
            return false;
        } else {
            return true;
        }
    }
});