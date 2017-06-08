jQuery.fn.extend({
    RechargeCard: function (options) {
        var Defaults = {
            Exchange: rc + "/recharge/recharge",
            GetUserInfo: rc + "/user/detail",
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
        $Submit.find("input[name='gift']").keyup(function () {
            var val = $(this).val();
            var pattern = new RegExp("[^0-9]+", "g");
            $(this).val(val.replace(pattern, ''));
        });
        $Submit.find("input[name='account']").keyup(function () {
            var val = $(this).val();
            var pattern = new RegExp("[^A-Za-z0-9]+", "g");
            $(this).val(val.replace(pattern, ''));
        });
        $Submit.find("input[name='gift']").keyup(function () {
            var val = $(this).val();
            var pattern = new RegExp("[^A-Za-z0-9]+", "g");
            $(this).val(val.replace(pattern, ''));
        });
        $(".item_p :input").not(":input[type=button]").blur(function () {
        	$(this).find(".item_p").find(".error b").html("");
            if ($(this)._VerIsEmpty()) {
                $(this)._VerOK();
                if ($(this).attr("name") == "amount" && _Verifisedform()) {
                	$(this)._VerOK();
                }
                if ($(this).attr("name") == "checkcode" && $(this)._VerCheckCaptcha()) {
                	 $(this)._VerOK();
                }
                if ($(this).attr("name") == "gift" && _Verifisedform()) {
                	$(this)._VerOK();
                }
            }
        	
        });
        $(".js_checkid").click(function () {
        	var head = "";
            if (_Verififirform()) {
                $.ajax({
                    type: "get",
                    url: Defaults.GetUserInfo,
                    data: {
                        account: $(":input[name=account]").val(),
                        r: Math.random()
                    },
                    success: function (d) {
                        if (d.isSuccess) {
                        	if(d.data.role=="head"){
                        		head ="局头";
                        	}
                            $(".js_userid").empty().html($(":input[name=account]").val());
                            $(".js_nickName").empty().html(d.data.name);
                            $(".js_phone").empty().html(d.data.phone);
                            $(".js_role").empty().html(head);
                            $(".js_balance").empty().html((parseInt(d.data.cardInventory)+parseInt(d.data.cardGiftInventory)) + " 张");
                          
                            $(".js_sed").removeClass("dis");
                        } else {
                            alert(d.message);
                            $(":input[name=account]").val("");
                            return false;
                        }
                    },
                    error: function () {
                        console.log("查看详情接口异常：/user/detail");
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
                    	account: $(".js_userid").html(),
                        amount: $(".js_cardNum").val()?$(".js_cardNum").val():0,
                        gift:$(".js_gift").val()?$(".js_gift").val():0
                    },
                    success: function (d) {
                        if (d.isSuccess) {
                        	if(d.message=="操作成功"){
                        		alert("充值成功");
                        		window.location.reload(true);
                        	}
                        } else {
                            alert(d.message);
                            $(":input[name=amount]").val("");
                            $(":input[name=amount]").parents(".item_p").find(".error").css("display", "inline-block").html("<b>" + $(":input[name=amount]").attr("data-tip") + "不能为空</b>");
                            return false;
                        }
                    },
                    error: function () {
                        console.log("充值 接口异常：/recharge/recharge");
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
                        $(".js_inventory").empty().html((parseInt(data.data.inventory)+parseInt(data.data.giftInventory)) + " 张");
                        $(".js_inventory").attr("data-blc", data.data.inventory);
                    } else {
                        $(".js_inventory").empty().html("0");
                        return false;
                    }
                },
                error: function () {
                    console.log("统计接口异常：/user/inventory");
                }
            });
        }
        $(".js_select").change(function(){
        	if($(this).val()=="0"){
        		$(".js_gifts").find(".js_gift").remove();
        		$(".js_gifts").hide();
        	}else{
        		$(".js_gifts").show();
        		$(".js_gifts").find(".ipt_box").append('<input class="ipt short js_gift" type="text" data-tip="赠送数量" name="gift" placeholder="赠送数量" value=""  maxlength="7"/>');
        	}
        })
        var _VerifiAmount = function (obj) {
        	
            var exp = /^[1-9]+\d*$/;
            if (parseInt(obj.val()) > parseInt($(".js_inventory").attr("data-blc"))) {
                obj.parents(".item_p").find(".succ").hide();
                obj.parent("span").addClass("ipt_box_err");
                obj.parents(".item_p").find(".error").css("display", "inline-block").html("<i></i><b>当前库存不足</b>");
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
        	$(".item_p :input").not(":input[type=button]").not(":input[name=account]").each(function () {
        		
        	if(!$(this)._VerIsEmpty()){
        		isbool = false;
        	}else {
        		if($(this).val()!="" && !_VerifiAmount($(this))){
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
    	if($(".js_cardNum").val()=="" && $(".js_gift").val()==""){
    		   if ($(this).val() == "") {
    	            $(this).parents(".item_p").find(".succ").hide();
    	            $(this).parent("span").addClass("ipt_box_err");
    	            $(this).parents(".item_p").find(".tip").css("display", "none");
    	            $(this).parents(".item_p").find(".error").css("display", "inline-block").html("<b>" + $(this).attr("data-tip") + "不能为空</b>");
    	            return false;
    	        } else {
    	            return true;
    	        }    		   
    	}
    	
    	return true;
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