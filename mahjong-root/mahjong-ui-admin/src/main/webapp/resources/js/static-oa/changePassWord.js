jQuery.fn.extend({
    ChangePassWord: function (options) {
        var Defaults = {
            AjaxSubcpwd: rc + "/user/submitChangePassword",
            DefaultMsgID: ".prompt",
            ErrorMsgID: ".error"
        };
        Defaults = $.extend(Defaults, options);
        var $Submit = $(this);
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
        $(".item_p :input").not(":input[type=button]").blur(function () {
            if ($(this)._VerIsEmpty() == true) {
                $(this)._VerOK();
                if ($(this).attr("name") == "password" && _VerCheckPassword($(this))) {
                	 $(this)._VerOK();
                }
                if ($(this).attr("name") == "confirmPassword") {
                    if (_VerCheckConfirmPassword($(this), $(":input[name=password]"))) {
                        $(this)._VerOK();
                    }
                }
            }
        });
        $(".js_submit").click(function () {
            if (_Verificationpsword()) {
                $.ajax({
                    type: "post",
                    url: Defaults.AjaxSubcpwd,
                    data: {
                    	password: $(":input[name=password]").val(),
                    	confirmPassword: $(":input[name=confirmPassword]").val()
                    },
                    success: function (data) {
                        if (data.isSuccess) {
                            alert(data.message);
                            window.location.href = rc + "/login/";
                        } else {
                            alert(data.message);
                            $(":input[name=password],:input[name=confirmPassword]").val("");
                            $(":input[name=password],:input[name=confirmPassword]").parents(".item_p").find(".error").css("display", "inline-block").html("<b>" + $(":input[name=password]").attr("data-tip") + "不能为空</b>");
                            return false;
                        }
                    },
                    error: function () {
                        console.log("修改密码提交接口异常：/user/submitChangePassword");
                    }
                });
            } else {
                return false;
            }
        });
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
            $(".item_p :input").each(function () {
                if (!$(this)._VerIsEmpty()) {
                    isbool = false;
                } else {
                    if ($(this).attr("name") == "password" && !_VerCheckPassword($(":input[name=password]"))) {
                        isbool = false;
                    }
                    if ($(this).attr("name") == "confirmPassword" && !_VerCheckConfirmPassword($(":input[name=confirmPassword]"), $(":input[name=password]"))) {
                        isbool = false;
                    }
                }
            });
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
            $(this).parents(".item_p").find(".error").css("display", "inline-block").html("<b>验证码不正确</b>");
            return false;
        } else {
            return true;
        }
    }
});