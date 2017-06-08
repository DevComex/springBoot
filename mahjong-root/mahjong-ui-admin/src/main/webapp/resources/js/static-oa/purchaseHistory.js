jQuery.fn.extend({
	PurchaseHistory: function (options) {
        var Defaults = {
            AjaxSaleList: rc + "/stat/purchase",
            DefaultMsgID: ".prompt",
            ErrorMsgID: ".error"
        };
        Defaults = $.extend(Defaults, options);
        var $Submit = $(this);
        var pIdx = 1;
        var myDate = new Date();
        var myyear = myDate.getFullYear();
        $(".js_month").val(myDate.getMonth() + 1);
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
        $(".item_p :input").not(":input[type=button]").blur(function () {
            if ($(this)._VerIsEmpty()) {
                $(this)._VerOK();
            }
        });
        $(".js_search").click(function () {
            if (_Verificationrcrecord()) {
                $(".js_pagetab").empty().html("");
                pIdx = 1;
                pagelist(pIdx);
                $(".js_page").html("下一页").removeClass("btn-danger").addClass("btn-success");
            } else {
                return false;
            }
        });
        var format = function (time, format) {
            var t = new Date(time);
            var tf = function (i) { return (i < 10 ? '0' : '') + i };
            return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function (a) {
                switch (a) {
                    case 'yyyy':
                        return tf(t.getFullYear());
                        break;
                    case 'MM':
                        return tf(t.getMonth() + 1);
                        break;
                    case 'mm':
                        return tf(t.getMinutes());
                        break;
                    case 'dd':
                        return tf(t.getDate());
                        break;
                    case 'HH':
                        return tf(t.getHours());
                        break;
                    case 'ss':
                        return tf(t.getSeconds());
                        break;
                }
            })
        };
        $(".js_page").click(function () {
            pagelist(pIdx);
        });
        function pagelist(pageidx) {
        	var m=$(".js_month").val();
        	var strtime = myyear+ '-' + m + "-1";
        	var endtime;
        	if(m != "12"){
        		 endtime= myyear + '-' + (parseInt(m)+1) +"-1";
        	}else{
        		 endtime= (parseInt(myyear)+1) + "-1-1";
        	}
            $.ajax({
                type: "get",
                url: Defaults.AjaxSaleList,
                data: {
                    start:strtime,
                    end: endtime,
                    pageSize: "10",
                    pageIndex: pageidx,
                    r: Math.random()
                },
                success: function (d) {
                    if (d.isSuccess) {
                        if (d.data.list == "") {
                        	 $(".js_page").html("已经到底了").removeClass("btn-success").addClass("btn-danger");
                             $(".js_next").removeClass("dis");
                        } else {
                            var listtab = '';
                            for (var i = 0; i < d.data.list.length; i++) {
                                listtab += '<tr><td>' + d.data.list[i].amount + '</td><td>' + d.data.list[i].gift + '</td><td>' + format(d.data.list[i].createTime, 'MM/dd') + '</td></tr>';
                            }
                            pIdx++;
                            $(".js_pagetab").append(listtab);
                            if(d.data.list.length == 10){
                            	$(".js_next").removeClass("dis");
                            }else{
                            	$(".js_page").html("已经到底了").removeClass("btn-success").addClass("btn-danger");
                            }
                        }
                        if (d.data.count.count == "0") {
                        	$(".js_sum").empty().html('总计：共  0  笔，进货量  0  张，赠送量  0 张');
                        }else{
                        	$(".js_sum").empty().html('总计：共 ' + d.data.count.count + ' 笔，进货量 ' + d.data.count.amountSum + ' 张，赠送量'+ d.data.count.giftSum+' 张');
                        }
                    } else {
                        alert(d.message);
                        $(".js_pagetab").empty().html('<tr><td colspan="4">暂无数据</td></tr>');
                        return false;
                    }
                },
                error: function () {
                    console.log("获取列表接口异常：/stat/purchase");
                }
            });
        }
        var _Verificationrcrecord = function () {
            var isbool = true;
            $(".item_p :input").each(function () {
                if (!$(this)._VerIsEmpty()) {
                    isbool = false;
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