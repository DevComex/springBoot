jQuery.fn.extend({
    changephone: function (options) {
        var Defaults = {
            AjaxCheck: rc + "/user/findpwdcheckacc",
            Ajaxcpsendfirst: rc + "/user/cpsendfirst",
            Ajaxcpsendsecond: rc + "/user/cpsendsecond",
            submitChangePhone:rc+"/user/submitFirstChangePhone",
            submitSecondChangePhone:rc+"/user/submitSecondChangePhone",
            DefaultMsgID: ".prompt",
            ErrorMsgID: ".error"
        };
        Defaults = $.extend(Defaults, options);

        var $Submit = $(this);
        var sendtimer = null;
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
var _SendRegisterSMS1 = function (that) {
            
            $.ajax({
                url: Defaults.Ajaxcpsendfirst,
                type: "post",
                data: {
                    r: Math.random()
                },
                beforeSend: function () {
                    $(that).attr("disabled", "false").removeClass("btn-danger").addClass("disabled").val("发送中...");
               
                },
                success: function (d) {
                    if (d.isSuccess) {
                        clearInterval(sendtimer);
                        $(that).parents(".item_p").find(".tip").css("display", "none");
                        $(that).parents(".item_p").find(Defaults.DefaultMsgID).eq(1).css({ "display": "inline", "z-index": "101" });
                        $(that).attr("disabled", true).removeClass("btn-danger").addClass("disabled").val("重新获取(60)");
                        _CountDownReg($(that), 60);
                    } else {
                        clearInterval(sendtimer);
                        $(that).parents(".item_p").find(".tip").css("display", "none");
                        $(that).parents(".item_p").find(".prompt").remove();
                        alert(d.message);
                        $(that).parents(".item_p").find(Defaults.DefaultMsgID).eq(1).css({ "display": "inline", "z-index": "101" });
                        $(that).removeAttr("disabled").removeClass("disabled").addClass("btn-danger").val("重新获取");
                    }
                }
            });
        };
        var _SendRegisterSMS2 = function () {
        	var isbool = true;
        	 if (!$("#password1")._VerIsEmpty()) {
                 isbool = false;
             } else {
                 if ($("#password1") && !$("#password1")._VerCheckCaptcha()) {
                     isbool = false;
                 }
             }
        	 return isbool;
        };
        $(".js_reg_sendsms1").click(function () {
            _SendRegisterSMS1(this);
        });
        $(".get_sm").click(function () {
        	 alertMsg('<div class="posr txtleft js_iscaptcha item_p pb10"><span class="ipt_box"><input class="ipt capthaipt yzm" id="password1" type="text" data-tip="验证码" name="captchaCode" placeholder="请输入验证码" value=""  maxlength="4"><img src="" class="js_capimg"></span><a href="javascript:;" class="kbq js_kbq" target="_blank">看不清？</a><span class="error tip"></span><div class="sure"> <input class="btn btnlater js_reg_sendsms2 btn-danger" type="button" value="获取验证码"></div></div>', '验证码');     
        	 $(".js_capimg").attr("src", rc + "/captcha/create?bid=cpssms&r=" + Math.random());
        	 $(".js_kbq").click(function () {
                 $(".js_capimg").attr("src", rc + "/captcha/create?bid=cpssms&r=" + Math.random());
             });
        	 $(".js_capimg").click(function () {
                 $(this).attr("src", rc + "/captcha/create?bid=cpssms&r=" + Math.random());
             });
        	 $(".js_reg_sendsms2").click(function () {
        		 if (_SendRegisterSMS2()) {
        			 var $this=$(this);
        			 $.ajax({
                         url: Defaults.Ajaxcpsendsecond,
                         type: "post",
                         data: {
                         	phone:$(".js_phone").val(),
                         	captchaCode:$("#password1").val(),
                             r: Math.random()
                         },
                         beforeSend: function () {
                        	 $this.attr("disabled", "false").removeClass("btn-danger").addClass("disabled").val("发送中...");
                        
                         },
                         success: function (d) {
                             if (d.isSuccess) {
                                 clearInterval(sendtimer);
                                 $this.parents(".item_p").find(".tip").css("display", "none");
                                 $this.parents(".item_p").find(Defaults.DefaultMsgID).eq(1).css({ "display": "inline", "z-index": "101" });
                                 $this.attr("disabled", true).removeClass("btn-danger").addClass("disabled").val("重新获取 (60)");
                                 _CountDownReg($this, 60);
                                 alert(d.message);
                                 $(".closeMsgimg").click();
                                 $(".js_capimg").attr("src", rc + "/captcha/create?bid=cpssms&r=" + Math.random());
                                 $(".get_sm").val("重新获取");
                             } else {
                            	 $(".js_capimg").attr("src", rc + "/captcha/create?bid=cpssms&r=" + Math.random());
                            	 $(".get_sm").val("重新获取");
                                 clearInterval(sendtimer);
                                 $this.parents(".item_p").find(".tip").css("display", "none");
                                 $this.parents(".item_p").find(".prompt").remove();
                                 $this.parents(".item_p").append('<span class="prompt tip tip1"><i></i><b>' + d.message+ '</b></span>');
                                 $this.parents(".item_p").find(Defaults.DefaultMsgID).eq(1).css({ "display": "inline", "z-index": "101" });
                                 $this.removeAttr("disabled").removeClass("disabled").addClass("btn-danger").val("重新获取");
                             }
                         }
                     });
        		 }
             });
        	 $("#password1").blur(function () {
                 if ($(this)._VerIsEmpty()) {
                     $(this)._VerOK();
                     if ($(this)._VerCheckCaptcha()) {
                      	$(this)._VerOK();
                      }
                 }
        	 }); 
        }); 
        $(".js_next").click(function(){
        	if (!$(".captchaCode01")._VerIsEmpty()) {
        	return false;
        	}else{
        		 $.ajax({
                     type: "post",
                     url: Defaults.submitChangePhone,
                     data: {
                         smsCode: $(".captchaCode01").val(),
                         r: Math.random()
                     },
                     success: function (d) {
                         if (d.isSuccess) {
                             $(".js_fir").addClass("dis");
                             $(".js_sed").removeClass("dis");
                         } else {
                             alert(d.message);
                             
                         }
                     },
                     error: function () {
                         console.log("第一步认证旧手机接口异常：/user/submitFirstChangePhone");
                     }
                 });
        	}
        	
        });
        
        $(".item_p :input").not(":input[type=button]").blur(function () {
            if ($(this)._VerIsEmpty()) {
                $(this)._VerOK();
                if ($(this).attr("name") == "phone" && $(this)._VerCheckPhone()) {
                	$(".get_sm").show();
                    $(this)._VerOK();
                }
                if ($(this).attr("name") == "captchaCode01" && _VerCheckPassword($(this))) {
                	 $(this)._VerOK();
                }
                if ($(this).attr("name") == "confirmPassword" && _VerCheckConfirmPassword($(this), $(":input[name=password]"))) {
                	$(this)._VerOK();
                }
                if ($("#password1") && $("#password1")._VerCheckCaptcha()) {
                	$("#password1")._VerOK();
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
                        console.log("检查用户接口异常：/user/findpwdcheckacc")
                    }
                });
            } else {
                return false;
            }
        });
        $(".js_phone").keyup(function () {
		    var val = $(this).val();
		    var pattern = new RegExp("[^0-9]+", "g");
		    $(this).val(val.replace(pattern, ''));
		            
		});
        $(".js_submit").click(function () {
        	
            if (_Verificationpsword()) {
                $.ajax({
                    type: "post",
                    url: Defaults.submitSecondChangePhone,
                    data: {
                        phone: $(".js_phone").val(),
                        smsCode:$(".captchaCode02").val(),
                        r: Math.random()
                    },
                    success: function (data) {
                        if (data.isSuccess) {
                            alert(data.message);
                            window.location.reload(true);
                        } else {
                            alert(data.message);
                        }
                    },
                    error: function () {
                        console.log("第二部修改新手机号接口异常：/user/submitSecondChangePhone");
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
            } else {
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
            $(".item_p :input").not($(":input[name=smsCode]")).each(function () {
                if (!$(this)._VerIsEmpty()) {
                    isbool = false;
                } else {
                    if ($(this).attr("name") == "phone" && !$(":input[name=phone]")._VerCheckPhone()) {
                        isbool = false;
                    }
                    if ($(this).attr("name") == "verifyCode" && !$(":input[name=verifyCode]")._VerCheckCaptcha()) {
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
            $(this).parents(".item_p").find(".error").css("display", "inline-block").html("<b>验证码格式不正确</b>");
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
    }
});