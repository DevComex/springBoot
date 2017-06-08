jQuery.fn.extend({
	HeadManage: function (options) {
        var Defaults = {
            AjaxSubList: rc + "/sub/list",
            AjaxUserDetail: rc + "/user/detail",
            DefaultMsgID: ".prompt",
            ErrorMsgID: ".error"
        };
        Defaults = $.extend(Defaults, options);
        var $Submit = $(this);
        var myyear = "";
        var pIdx = 1;
        var myDate = new Date();
        myyear = myDate.getFullYear();
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
        $Submit.find("input[name='account']").keyup(function () {
            var val = $(this).val();
            var pattern = new RegExp("[^A-Za-z0-9]+", "g");
            $(this).val(val.replace(pattern, ''));
        });
        $(".item_p :input").not(":input[type=button]").not(":input[name=account]").blur(function () {
            if ($(this)._VerIsEmpty()) {
                $(this)._VerOK();
            }
        });
        $(".js_search").click(function () {
          
                $(".js_pagetab").empty().html("");
                pIdx = 1;
                pagelist(pIdx);
                $(".js_page").html("下一页").removeClass("btn-danger").addClass("btn-success");
            
        });
        $(".js_page").click(function () {
            pagelist(pIdx);
        });
        $(".js_newhead").click(function(){
        	window.location.href = rc+"/sub/edit";
        });
        function pagelist(pageidx) {
            $.ajax({
                type: "get",
                url: Defaults.AjaxSubList,
                data: {
                	gameId:1,
                	account: $(":input[name=account]").val(),
                	role:$("select[name='agent'] option:selected").val(),
                	status:$("select[name='block'] option:selected").val(),
                    pageSize: "10",
                    pageIndex: pageidx,
                    r: Math.random()
                },
                success: function (d) {
                    if (d.isSuccess) {
                    	console.log(d);
                        if (d.data == "") {
                        	   $(".js_page").html("已经到底了").removeClass("btn-success").addClass("btn-danger");
                               $(".js_next").removeClass("dis");
                        } else {
                            var listtab = '';
                            var names = '';
                            var button = '<a href ="javascript:;">编辑</a><a href ="javascript:;">封停</a><a href ="javascript:;">封停历史</a>';
                            for (var i = 0; i < d.data.length; i++) {
                            	if(d.data[i].name==null){
                            		names="无";
                            	}else{
                            		names=d.data[i].name;
                            	}
                            	listtab += '<tr><td><a href="javascript:;" class="js_view" data-userid="' + d.data[i].account + '">' + d.data[i].account + '</a></td><td>' + names + '</td><td>' + (parseInt(d.data[i].cardInventory)+parseInt(d.data[i].cardGiftInventory)) + '</td><td>' + button + '</td></tr>';
                            }
                            pIdx++;
                            $(".js_pagetab").append(listtab);
                            if(d.data.length == 10){
                            	$(".js_next").removeClass("dis");
                            }else{
                            	$(".js_page").html("已经到底了").removeClass("btn-success").addClass("btn-danger");
                            }
                           
                            
                        }
                    } else {
                        alert(d.message+"ssss");
                        $(".js_pagetab").empty().html('<tr><td colspan="4">暂无数据</td></tr>');
                        $(":input[name=account]").val("");
                        $(":input[name=account]").parents(".item_p").find(".error").css("display", "inline-block").html("<b>" + $(":input[name=account]").attr("data-tip") + "不能为空</b>");
                        return false;
                    }
                },
                error: function () {
                   console.log("获取列表接口异常：/head/list");
                }
            });
        }
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
        $(".js_pagetab").on("click",".js_view",function () {
        	   alertMsg('<ul class="headinfo"><li><span class="bfont hleft">账号：</span><span class="hright">111</span></li><li><span class="bfont hleft">游戏ID：</span><span class="hright">222</span></li><li><span class="bfont hleft">微信号：</span><span class="hright">333</span></li><li><span class="bfont hleft">昵称：</span><span class="hright">4444</span></li><li><span class="bfont hleft">手机：</span><span class="hright">555</span></li><li><span class="bfont hleft">地址：</span><span class="hright">666</span></li><li><span class="bfont hleft">详细地址：</span><span class="hright">555</span></li><li><span class="bfont hleft">添加时间：</span><span class="hright">9999</span></li></ul>', '局头资料');
               
            //getdetail($(this).attr("data-userid"));
        });
        $(".js_pagetab").on("click",".js_block",function () {
            alertMsg('<div class="reasonBlock"><div><label>原因</label><textarea  class="reasonBox" rows="3" cols="20"></textarea></div><p class="formbtn"><a href="javascript:;" class="btn btn-success js_sure">确定</a><a href="javascript:;" class="btn btn-info js_cancel">取消</a></p></div>',"封停原因")
        });
        $(".js_pagetab").on("click",".js_unblock",function () {
            alertMsg('<div class="reasonBlock"><div><label>原因</label><textarea  class="reasonBox" rows="3" cols="20"></textarea></div><p class="formbtn"><a href="javascript:;" class="btn btn-success js_sure">确定</a><a href="javascript:;" class="btn btn-info js_cancel">取消</a></p></div>',"解封原因")
        });
        $(".js_pagetab").on("click",".js_blockHis",function () {
            alertMsg('<table class="result-tab"><thead><tr><th>账号</th><th>封停操作</th><th>封停人</th><th>原因</th></tr></thead><tbody class="js_pagetab"><tr><td><a href="javascript:;" class="js_view" data-userid="18618161929">18618161929</a></td><td>无</td><td>0</td><td>违规操作</td></tr></tbody></table>',"封停历史")
        });
        function getdetail(userid) {
        	var guid="",wechatid="",nikname="",phone="",city="",address="",province="";
            $.ajax({
                type: "get",
                url: Defaults.AjaxUserDetail,
                data: {
                    account: userid,
                    r: Math.random()
                },
                success: function (d) {
                    if (d.isSuccess) {
                    	var acc=d.data.account;
                    	var createtime=format(d.data.createTime, 'yyyy-MM-dd HH:mm:ss');
                    	if(d.data.address==null){
                    		address="无";
                    	}else{
                    		address=d.data.address;
                    	}
                    	if(d.data.province==null){
                    		province="&nbsp;";
                    	}else{
                    		province=d.data.province;
                    	}
                    	if(d.data.city==null){
                    		city="&nbsp;";
                    	}else{
                    		city=d.data.city;
                    	}
                    	if(d.data.gameUserId==null){
                    		guid="无";
                    	}else{
                    		guid=d.data.gameUserId;
                    	}
                    	if(d.data.name==null){
                    		nikname="无";
                    	}else{
                    		nikname=d.data.name;
                    	}
                    	if(d.data.phone==null){
                    		phone="无";
                    	}else{
                    		phone=d.data.phone;
                    	}
                    	if(d.data.wechatId==null){
                    		wechatid="无";
                    	}else{
                    		wechatid=d.data.wechatId;
                    	}
                    	var citys=province+city;
                        alertMsg('<ul class="headinfo"><li><span class="bfont hleft">账号：</span><span class="hright">'+ acc +'</span></li><li><span class="bfont hleft">游戏ID：</span><span class="hright">'+ guid +'</span></li><li><span class="bfont hleft">微信号：</span><span class="hright">'+ wechatid +'</span></li><li><span class="bfont hleft">昵称：</span><span class="hright">'+ nikname +'</span></li><li><span class="bfont hleft">手机：</span><span class="hright">'+ phone +'</span></li><li><span class="bfont hleft">地址：</span><span class="hright">' + citys +'</span></li><li><span class="bfont hleft">详细地址：</span><span class="hright">'+ address +'</span></li><li><span class="bfont hleft">添加时间：</span><span class="hright">'+ createtime +'</span></li></ul>', '局头资料');
                    } else {
                        alert(d.message);
                        return false;
                    }
                },
                error: function () {
                    console.log("获取详情接口异常：/user/detail");
                }
            });

        }
        var _Verificationrcrecord = function () {
            var isbool = true;
            $(".item_p :input").not(":input[name=account]").each(function () {
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