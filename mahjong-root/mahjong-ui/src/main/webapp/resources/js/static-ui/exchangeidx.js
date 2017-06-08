jQuery.fn.extend({
    RechargeCard: function (options) {
        var Defaults = {
            Exchange: rc + "/exchange/exchange",
            GetGameUserInfo: rc + "/game/userInfo",
            GetInventory: rc + "/user/inventory",
            DefaultMsgID: ".prompt",
            ErrorMsgID: ".error"
        };
        Defaults = $.extend(Defaults, options);
        Getinventory();
        $(".js_prev").click(function () {
        	window.location.reload(true);
        });
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
        $Submit.find("input[name='amount']").keyup(function () {
            var val = $(this).val();
            var pattern = new RegExp("[^0-9]+", "g");
            $(this).val(val.replace(pattern, ''));
        });
        $Submit.find("input[name='account']").keyup(function () {
            var val = $(this).val();
            var pattern = new RegExp("[^0-9]+", "g");
            $(this).val(val.replace(pattern, ''));
        });
        $(".item_p :input").not(":input[type=button]").blur(function () {
            if ($(this)._VerIsEmpty()) {
                $(this)._VerOK();
                if ($(this).attr("name") == "amount" && _Verifisedform()) {
                	$(this)._VerOK();
                }
                if ($(this).attr("name") == "checkcode" && $(this)._VerCheckCaptcha()) {
                	 $(this)._VerOK();
                }
            }
        });
        $(".js_checkid").click(function () {
            if (_Verififirform()) {
                $.ajax({
                    type: "post",
                    url: Defaults.GetGameUserInfo,
                    data: {
                        gameUserId: $(":input[name=account]").val(),
                        r: Math.random()
                    },
                    success: function (d) {
                        if (d.isSuccess) {
                            $(".js_userid").empty().html($(":input[name=account]").val());
                            $(".js_nickName").empty().html(d.data.nickName);
                            $(".js_balance").empty().html(d.data.balance + " 张");
                            $(".js_fir").addClass("dis");
                            $(".js_sed").removeClass("dis");
                        } else {
                            alert(d.message);
                            $(":input[name=account]").val("");
                            $(":input[name=account]").parents(".item_p").find(".error").css("display", "inline-block").html("<b>" + $(":input[name=account]").attr("data-tip") + "不能为空</b>");
                            return false;
                        }
                    },
                    error: function () {
                        console.log("查询玩家信息接口异常：/game/userInfo");
                    }
                });
            } else {
                return false;
            }
        });
        $(".js_recharge").click(function () {
            if (_Verifisedform()) {
                $.ajax({
                    type: "post",
                    url: Defaults.Exchange,
                    data: {
                        gameUserId: $(":input[name=account]").val(),
                        amount: $.trim($(":input[name=amount]").val())
                    },
                    success: function (d) {
                        if (d.isSuccess) {
                            alert(d.message);
                            window.location.reload(true);
                        } else {
                            alert(d.message);
                            $(":input[name=amount]").val("");
                            $(":input[name=amount]").parents(".item_p").find(".error").css("display", "inline-block").html("<b>" + $(":input[name=amount]").attr("data-tip") + "不能为空</b>");
                            return false;
                        }
                    },
                    error: function () {
                        console.log("充值房卡接口异常：/exchange/exchange");
                    }
                });
            } else {
                return false;
            }
        });
        function Getinventory() {
            $.ajax({
                type: "get",
                url: Defaults.GetInventory,
                data: {
                    r: Math.random()
                },
                success: function (data) {
                    if (data.isSuccess) {
                    	var inty =parseInt(data.data.inventory) + parseInt(data.data.giftInventory);
                        $(".js_inventory").empty().html(inty + " 张");
                        $(".js_inventory").attr("data-blc", inty);
                    } else {
                        $(".js_inventory").empty().html("0");
                        return false;
                    }
                },
                error: function () {
                   console.log("获取局头库存接口异常：/user/inventory");
                }
            });
        }
        var _VerifiAmount = function (obj) {
            var exp = /^[1-9]+\d*$/;
            if (parseInt(obj.val()) > parseInt($(".js_inventory").attr("data-blc"))) {
                obj.parents(".item_p").find(".succ").hide();
                obj.parent("span").addClass("ipt_box_err");
                obj.parents(".item_p").find(".error").css("display", "inline-block").html("<i></i><b>不能大于当前库存</b>");
                return false;
            } else if (!exp.test(obj.val())) {
                obj.parents(".item_p").find(".succ").hide();
                obj.parents(".item_p").find(".error").addClass("tops2").css("display", "inline-block").html("<i></i><b>充卡数量必须为大于0的整数</b>");
                return false;
            } else {
                return true;
            }
        };
        var _Verififirform = function () {
            var isbool = true;
            if (!$(":input[name=account]")._VerIsEmpty()) {
                isbool = false;
            }
            return isbool;
        };
        var _Verifisedform = function () {
            var isbool = true;
            $(".item_p :input").each(function () {
                if (!$(this)._VerIsEmpty()) {
                    isbool = false;
                } else {
                    if ($(this).attr("name") == "amount" && !_VerifiAmount($(":input[name=amount]"))) {
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