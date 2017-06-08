jQuery.fn.extend({
    AddHead: function (options) {

        var Defaults = {
            AjaxAddHead: rc + "/head/new",
            AjaxSendSms: rc + "/head/newSms/send",
            DefaultMsgID: ".prompt",
            ErrorMsgID: ".error"
        };

        Defaults = $.extend(Defaults, options);

        var $Submit = $(this);
        var sendtimer = null;
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
        $Submit.find("input[name='gameUserId']").keyup(function () {
            var val = $(this).val();
            var pattern = new RegExp("[^0-9]+", "g");
            $(this).val(val.replace(pattern, ''));
        });
        $(".item_p :input").not(":input[type=button]").blur(function () {
            if ($(this)._VerIsEmpty()) {
                $(this)._VerOK();
                if ($(this).attr("name") == "phone") {
                    if ($(this)._VerCheckPhone() && $(".js_reg_sendsms").val().indexOf("(") == -1) {
                        if ($(".js_reg_sendsms").val().indexOf("重") == -1) {
                            $(".js_reg_sendsms").removeAttr("disabled").removeClass("disabled").addClass("btn-danger").val("获取验证码");
                        } else {
                            $(".js_reg_sendsms").removeAttr("disabled").removeClass("disabled").addClass("btn-danger").val("重新获取");
                        }
                        $(this)._VerOK();
                    } else if (!$(this)._VerCheckPhone() && $(".js_reg_sendsms").val().indexOf("(") == -1) {
                        if ($(".js_reg_sendsms").val().indexOf("重") == -1) {
                            $(".js_reg_sendsms").attr("disabled", true).removeClass("btn-danger").addClass("disabled").val("获取验证码");
                        } else {
                            $(".js_reg_sendsms").attr("disabled", true).removeClass("btn-danger").addClass("disabled").val("重新获取");
                        }

                    }
                }
                if ($(this).attr("name") == "verifyCode" && $(this)._VerCheckCaptcha()) {
                	  $(this)._VerOK();
                }
            }

        });
        $(".js_submit").click(function () {
            if (_Verificationrcrecord()) {
                $.ajax({
                    type: "post",
                    url: Defaults.AjaxAddHead,
                    data: {
                    	gameUserId:$.trim($(":input[name=gameUserId]").val()),
                    	wechatId:$.trim($(":input[name=wechatId]").val()),
                    	name:$.trim($(":input[name=name]").val()),
                    	phone:$.trim($(":input[name=phone]").val()),
                    	verifyCode:$.trim($(":input[name=verifyCode]").val()),
                    	province:$.trim($(":input[name=province]").val()),
                    	city:$.trim($(":input[name=city]").val()),
                    	address:$.trim($(":input[name=address]").val())
                    },
                    success: function (data) {
                        if (data.isSuccess) {
                            alert(data.message);
                            window.location.href = rc + "/head/";
                        } else {
                            alert(data.message);
                            return false;
                        }
                    },
                    error: function () {
                        console.log("添加局头接口异常：/head/new");
                    }
                });
            } else {
                return false;
            }
        });
        $(".js_view").click(function () {
            alertMsg('<ul class="headinfo"><li><span class="bfont hleft">账号：</span><span class="hright">134256487457</span></li><li><span class="bfont hleft">游戏ID：</span><span class="hright">65459848</span></li><li><span class="bfont hleft">微信号：</span><span class="hright">15481854155</span></li><li><span class="bfont hleft">昵称：</span><span class="hright">逍遥</span></li><li><span class="bfont hleft">手机：</span><span class="hright">134256548774</span></li><li><span class="bfont hleft">地址：</span><span class="hright">湖北省武汉市</span></li><li><span class="bfont hleft">详细地址：</span><span class="hright">东湖花园</span></li><li><span class="bfont hleft">添加时间：</span><span class="hright">2017-05-12 12:21:24</span></li></ul>', '局头资料');
        });
        $(".js_back").click(function(){
        	window.location.href = rc+"/head/";
        });
        var _SendRegisterSMS = function () {
            clearInterval(sendtimer);
            $.ajax({
                url: Defaults.AjaxSendSms,
                type: "post",
                data: {
                    "phone": $(":input[name=phone]").val(),
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
                        if ($(":input[name=smsCode]").parents(".item_p").find(".tip1").length == 0) {
                            $(":input[name=smsCode]").parents(".item_p").append('<span class="prompt tip tip1"><i></i><b>验证码已经发送，可能会有1分钟延迟，请注意查收短信</b></span>');
                        }
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
        $(".js_reg_sendsms").click(function () {
            _SendRegisterSMS();
        });
        var _Verificationrcrecord = function () {
            var isbool = true;
            $(".item_p :input").each(function () {
                if (!$(this)._VerIsEmpty()) {
                    isbool = false;
                } else {
                    if ($(this).attr("name") == "phone" && !$(":input[name=phone]")._VerCheckPhone()) {
                        isbool = false;
                    }
                    if ($(this).attr("name") == "verifyCode" && !$(":input[name=verifyCode]")._VerCheckCaptcha()) {
                        isbool = false;
                    }
                    if ($(this).attr("name") == "province" && !$(":input[name=province]")._VerCheckCity()) {
                        isbool = false;
                    }
                    if ($(this).attr("name") == "city" && !$(":input[name=city]")._VerCheckCity()) {
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
        var exp = new RegExp("^\\w{4,6}$");
        if (!exp.test($(this).val())) {
            $(this).parents(".item_p").find(".succ").hide();
            $(this).parent("span").addClass("ipt_box_err");
            $(this).parents(".item_p").find(".error").css("display", "inline-block").html("<b>验证码不正确</b>");
            return false;
        } else {
            return true;
        }
    },
    _VerCheckPhone: function () {
        var pattern = new RegExp("[^0-9]+");
        if (pattern.test($(this).val())) {
            $(this).parents(".item_p").find(".succ").hide();
            $(this).parent("span").addClass("ipt_box_err");
            $(this).parents(".item_p").find(".error").css("display", "inline-block").html("<i></i><b>手机号码只能为数字</b>");
            return false;
        } else if ($(this).val() == "" || $(this).val().length < 11) {
            $(this).parents(".item_p").find(".succ").hide();
            $(this).parent("span").addClass("ipt_box_err");
            $(this).parents(".item_p").find(".error").css("display", "inline-block").html("<i></i><b>请输入11位手机号</b>");
            return false;
        } else if (!/^1[34578]\d{9}$/.test($(this).val())) {
            $(this).parents(".item_p").find(".succ").hide();
            $(this).parent("span").addClass("ipt_box_err");
            $(this).parents(".item_p").find(".error").css("display", "inline-block").html("<i></i><b>手机号码格式不正确</b>");
            return false;
        } else {
            return true;
        }
    },
    _VerCheckCity: function () {
        if ($(this).val() == "0") {
            $(this).parents(".item_p").find(".succ").hide();
            $(this).parent("span").addClass("ipt_box_err");
            $(this).parents(".item_p").find(".error").css("display", "inline-block").html("<b>请选择地址</b>");
            return false;
        } else {
            return true;
        }
    }
});