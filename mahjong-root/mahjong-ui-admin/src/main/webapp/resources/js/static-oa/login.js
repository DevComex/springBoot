jQuery.fn.extend({
    LoginValidate: function (options) {
        var Defaults = {
            AjaxLogin: rc + "/login/",
            IsNeedCaptcha: rc + "/login/isNeedCaptcha",
            DefaultMsgID: ".prompt",
            ErrorMsgID: ".error"
        };
        Defaults = $.extend(Defaults, options);
        var $Submit = $(this);
        var isneedca = 0;
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
        $(".item_p :input").not(":input[type=button]").blur(function () {
            if ($(this)._VerIsEmpty()) {
                $(this)._VerOK();
                if ($(this).attr("name") == "account") {
                    isNeedCaptcha($(":input[name=account]").val());
                }
                if ($(this).attr("name") == "password" && _VerCheckPassword($(this))) {
                	 $(this)._VerOK();
                }
                if (isneedca == 1 && $(this).attr("name") == "captchaCode" && $(this)._VerCheckCaptcha()) {
                	$(this)._VerOK();
                }

            }
        });
        $(".js_submitform").click(function () {
            if (_VerificationNoPhone()) {
                $.ajax({
                    type: "post",
                    url: Defaults.AjaxLogin,
                    data: $Submit.serialize(),
                    success: function (data) {
                        if (data.isSuccess) {
                            window.location.href = rc+ "/head/"
                        } else {
                            alert(data.message);
                            $(":input[name=password]").val("");
                            $(":input[name=captchaCode]").val("");
                            $(":input[name=password]").parents(".item_p").find(".tip").hide();
                            $(":input[name=password]").parents(".item_p").find("span").addClass("ipt_box_err");
                            $(":input[name=password]").parents(".item_p").find(".error").css("display", "inline-block").html("<b>" + $(":input[name=password]").attr("data-tip") + "不能为空</b>");
                            isNeedCaptcha($(":input[name=account]").val());
                            return false;
                        }
                    },
                    error: function () {
                        console.log("登录接口异常：/login/");
                    }
                });
            } else {
                $(":input[name=password]").val("");
                $(":input[name=password]").parents(".item_p").find(".tip").hide();
                $(":input[name=password]").parents(".item_p").find("span").addClass("ipt_box_err");
                $(":input[name=password]").parents(".item_p").find(".error").css("display", "inline-block").html("<b>" + $(":input[name=password]").attr("data-tip") + "不能为空</b>");
                return false;
            }
        });
        $("#password").focus(function () {
            $("#left_hand").animate({
                left: "133",
                top: " -38"
            }, {
                step: function () {
                    if (parseInt($("#left_hand").css("left")) > 115) {
                        $("#left_hand").attr("class", "left_hand");
                    }
                }
            }, 2000);
            $("#right_hand").animate({
                right: "-46",
                top: "-38px"
            }, {
                step: function () {
                    if (parseInt($("#right_hand").css("right")) > -70) {
                        $("#right_hand").attr("class", "right_hand");
                    }
                }
            }, 2000);
        });
        $("#password").blur(function () {
            $("#left_hand").attr("class", "initial_left_hand");
            $("#left_hand").attr("style", "left:85px;top:-12px;");
            $("#right_hand").attr("class", "initial_right_hand");
            $("#right_hand").attr("style", "right:-97px;top:-12px");
        });
        $(".js_kbq").click(function () {
            $(".js_capimg").attr("src", rc + "/captcha/create?bid=adminlg&r=" + Math.random());
        });
        $(".js_capimg").click(function () {
            $(this).attr("src", rc + "/captcha/create?bid=adminlg&r=" + Math.random());
        });
        $(".js_findpwd").click(function(){
        	window.location.href = rc + "/user/findpwd";
        });
        $(":input[name=agreement]").click(function(){
       	 if ($(this).is(':checked')) {
       		 $(this)._VerOK();
            } 
       });
        function isNeedCaptcha(acc) {
            $.ajax({
                type: "get",
                url: Defaults.IsNeedCaptcha,
                data: {
                    account: acc,
                    r: Math.random()
                },
                success: function (data) {
                    if (data.isSuccess) {
                        if (data.data) {
                            $(".js_capimg").attr("src", rc + "/captcha/create?bid=adminlg&r=" + Math.random());
                            $(".js_iscaptcha").removeClass("dis");
                            isneedca = 1;
                        } else {
                            $(".js_iscaptcha").addClass("dis");
                            isneedca = 0;
                        }
                    } else {
                        alert(data.message);
                        return false;
                    }
                },
                error: function () {
                    console.log("判断登录接口异常：/login/isNeedCaptcha");
                }
            });
        }
        var _VerCheckPassword = function (obj) {
            var exp1 = new RegExp("^[\u4e00-\u9fa5]+$");
            if (obj.val().length > 16 || obj.val().length < 6) {
                obj.parent("span").addClass("ipt_box_err");
                obj.parents(".item_p").find(".error").css("display", "inline-block").html("<b>密码过短，必须在6-16个字符之间</b>");
                return false;
            } else {
                return true;
            }
        };
        var _VerificationNoPhone = function () {
            var isbool = true;
            if (isneedca == 0) {
                $(".item_p :input").not(":input[name=captchaCode]").each(function () {
                    if (!$(this)._VerIsEmpty()) {
                        isbool = false;
                    } else {
                        if ($(this).attr("name") == "password" && !_VerCheckPassword($(":input[name=password]"))) {
                            isbool = false;
                        }
                        if ($(this).attr("name") == "agreement" && !$(":input[name=agreement]")._VerCheckaAgreement()) {
                            isbool = false;
                        }
                    }
                });
            } else {
                $(".item_p :input").each(function () {
                    if (!$(this)._VerIsEmpty()) {
                        isbool = false;
                    } else {
                        if ($(this).attr("name") == "password" && !_VerCheckPassword($(":input[name=password]"))) {
                            isbool = false;
                        }
                        if ($(this).attr("name") == "captchaCode" && !$(":input[name=captchaCode]")._VerCheckCaptcha()) {
                            isbool = false;
                        }
                        if ($(this).attr("name") == "agreement" && !$(":input[name=agreement]")._VerCheckaAgreement()) {
                            isbool = false;
                        }
                    }
                });
            }
            return isbool;
        };
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
    },
    _VerCheckaAgreement: function () {
        if (!$(this).is(':checked')) {
            $(this).parents(".item_p").find(".succ").hide();
            $(this).parent("span").addClass("ipt_box_err");
            $(this).parents(".item_p").find(".tip").css("display", "none");
            $(this).parents(".item_p").find(".error").css("display", "inline-block").html("<b>请阅读并勾选" + $(this).attr("data-tip") + "</b>");
            return false;
        } else {
            return true;
        }
    }
});