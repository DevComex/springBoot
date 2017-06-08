jQuery.fn.extend({
    MyData: function (options) {
        var Defaults = {
            AjaxCapachaType: rc + "/capacha/type",
            AjaxLogin: rc + "/login/index",
            AjaxLogOut: rc + "/user/logout",
            AjaxGetca: rc + "/captcha/create",
            getUserInfo:rc + "/user/profile",
            DefaultMsgID: ".prompt",
            ErrorMsgID: ".error",
            bid: "lgct",
            Capachasource: "lgct"
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
        $Submit.find("input[name='gameid']").keyup(function () {
            var val = $(this).val();
            var pattern = new RegExp("[^0-9]+", "g");
            $(this).val(val.replace(pattern, ''));
        });
        $(".item_p :input").not(":input[type=button]").not(":input[name='gameid']").not(":input[name='wxid']").blur(function () {
            if ($(this)._VerIsEmpty()) {
                $(this)._VerOK();

                if ($(this).attr("name") == "password" && _VerCheckPassword($(this))) {
                	$(this)._VerOK();
                }

                if ($(this).attr("name") == "confirmPassword" && _VerCheckConfirmPassword($(this), $(":input[name=password]"))) {
                	$(this)._VerOK();
                }

                if ($(this).attr("name") == "sms" && _VerCheckCaptcha($(this))) {
                	$(this)._VerOK();
                }
            }
        });
        
        function getMyDate(str){  
            var oDate = new Date(str),  
            oYear = oDate.getFullYear(),  
            oMonth = oDate.getMonth()+1,  
            oDay = oDate.getDate(),  
            oHour = oDate.getHours(),  
            oMin = oDate.getMinutes(),  
            oSen = oDate.getSeconds(),  
            oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间
            return oTime;  
        }
        function getzf(num){  
            if(parseInt(num) < 10){  
                num = '0'+num;  
            }  
            return num;  
        }
        function getUserInfo(){
        	 $.ajax({
                type: "get",
                url: rc + "/user/profile",
                data: { r: Math.random() },
                success: function (data) {
                    if (data.isSuccess) {
                        $(".js_GameId").html(data.data.gameUserId);
                        $(".js_wechat").html(data.data.wechatId);
                        $(".js_name").html(data.data.name);
                        $(".js_phone").html(data.data.phoneNumber);
                        $(".js_address").html(data.data.province+data.data.city);
                        $(".js_detailAdd").html(data.data.address);
                        $(".js_registerTime").html(getMyDate(data.data.registerTime));
                        $(".item_p input[name='gameid']").val(data.data.gameUserId);
			            $(".item_p input[name='wxid']").val(data.data.wechatId);
			            $(".item_p input[name='account']").val( data.data.name);
			            $(".item_p input[name='phone']").val(data.data.phoneNumber);
			            $(".item_p select[name='province'] option[value='"+data.data.province+"']").prop("selected",true);
			            $("#province").trigger("change");
			            $(".item_p select[name='city'] option[value='"+data.data.city+"']").prop("selected",true);
			            $(".item_p input[name='address']").val(data.data.address);
			            
                    } else {
                        return false;
                    }
                },
                error: function () {
                    console.log("获取详细信息接口异常：/user/profile");
                }
        });
        }
        getUserInfo();
        $(".js_edit").click(function () {
            $(".item_t").addClass("dis");
            $(".item_p").removeClass("dis");   
        });
        $(".js_submit").click(function () {
            if (_Verificationpsword()) {
                $.ajax({
                    type: "post",
                    url: Defaults.getUserInfo,
                    data:{
                    	gameUserId:$.trim($(".item_p input[name='gameid']").val()),
                    	wechatId:$.trim($(".item_p input[name='wxid']").val()),
                    	name:$.trim($(".item_p input[name='account']").val()),
                    	province:$(".item_p select[name='province'] option:selected").text(),
                    	city:$(".item_p select[name='city'] option:selected").text(),
                    	address:$.trim($(".item_p input[name='address']").val())
                    },
                    success: function (data) {
                        if (data.isSuccess) {
                            alert(data.message);
                            window.location.reload(true);
                            $(".js_fir").addClass("dis");
                            $(".js_sed").removeClass("dis");
                        } else {
                        	alert(data.message);
                            $(":input[name=account]").val("");
                            $(":input[name=account]").parents(".item_p").find(".error").css("display", "inline-block").html("<b>" + $(":input[name=account]").attr("data-tip") + "不能为空</b>");
                            return false;
                        }
                    },
                    error: function () {
                        alert("异常");
                    }
                });
            } else {
                return false;
            }
        });
        $(".js_cancel").click(function(){
        	 window.location.reload();
        })
        var _VerCheckPassword = function (obj) {
            var exp1 = new RegExp("^[\u4e00-\u9fa5]+$");
            if (obj.val().length > 16 || obj.val().length < 6) {
                obj.parents(".item_p").find(".succ").hide();
                obj.parent("span").addClass("ipt_box_err");
                obj.parents(".item_p").find(".error").css("display", "inline-block").html("<i></i><b>密码过短，必须在6-16个字符之间</b>");
                return false;
            } else if (obj.val().indexOf("<") != -1 || obj.val().indexOf(">") != -1 || exp1.test(obj.val())) {
                obj.parents(".item_p").find(".succ").hide();
                obj.parent("span").addClass("ipt_box_err");
                obj.parents(".item_p").find(".error").css("display", "inline-block").html("<i></i><b>密码需由数字或字符或符号（除>和<）组成</b>");
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
        var _Verificationpsword = function (obj) {
            var isbool = true;
            $(".item_p .ipt").not(":input[type=button]").each(function () {

                if ($(this)._VerIsEmpty() == false) {
                    isbool = false;
                } else {
                    if ($(this).attr("name") == "password" && _VerCheckPassword($(":input[name=password]")) == false) {
                        isbool = false;
                    }
                    if ($(this).attr("name") == "confirmPassword" && _VerCheckConfirmPassword($(":input[name=confirmPassword]"), $(":input[name=password]")) == false) {
                        isbool = false;
                    }
                    if ($(this).attr("name") == "sms" && _VerCheckCaptcha($(":input[name=sms]"), $(":input[name=sms]")) == false) {
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
    _VerSelectIsEmpty: function () {
        if ($(this).val() == "1") {
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