
$(function () {

	if(getQueryString("AgencyNameForPost")!=null){
		$("input[name=AgencyNameForPost]").val(getQueryString("AgencyNameForPost"));//机构
		$("#AgencyCode").val(getQueryString("AgencyCode"));
		$("#finishStatus").val(getQueryString("finishStatus"));//手续办理状态
	}


$("input[name=AgencyNameForPost]").keyup(function(){
  if($(this).val()==""){
	  $("#AgencyCode").val("-1");
	  $("#AgencyCodeHidden").val("-1");
	  $("#PostSelect").val("-1");
	  $("#PostCode").val("-1");
	  $("#PostCodeHidden").val("-1");
  }
});

if($("input[name=AgencyNameForPost]").val()!=""){
	$.ajax({
        url: "http://oa.gyyx.cn/post/agencyposts",
        type: "GET",
        dataType: "jsonp",
        jsonp: "jsoncallback",
        data: {
        	r:Math.random(),
            id:getQueryString("AgencyCode")
        },
        success: function (d) {
            if (d.Ret == 0) { /*获取岗位成功*/
                if (d.Data.length != 0) {
                    /*有岗位数据*/
                	$("#PostSelect").empty();
                    $.each(d.Data, function (i, item) {
                        var options = $('<option value="' + item.Code + '">' + item.Name + '</option>');
                        $("#PostSelect").append(options);
                    });
                    $("#PostSelect").val(getQueryString("PostCode"));//岗位
                    $("#PostCode").val(getQueryString("PostCode"))
                }
            } else {
                alert(d.Message);
            }
        }
    });
	
}


function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
};


    /*中英文数字*/
    jQuery.validator.addMethod("isName", function (value, element, param) {
        var reg = /[^\u4E00-\u9FA5^a-z^A-Z^0-9]/g;
        return this.optional(element) || (!reg.test(value));
    });

    //选择机构
    $(".js_agencySelectForPost").agencySelect({
        hidCode: $("#PersonalFileList").find("input[name='AgencyCode']"), /*隐藏inputID*/
        hidName: $("#PersonalFileList").find("input[name='AgencyNameForPost']"), /*hidden*/
        hidDiv: ".js_agencySelectListForPost",
        getPostList: true,
        PostSelect: $("#PersonalFileList").find("select[name='PostCodeD']")
    });

    if ($("input[name='AgencyCode']").val() > 0) {
        $.ajax({
            url: "http://oa.gyyx.cn/post/agencyposts/" + "?r=" + Math.random(),
            type: "GET",
            dataType: "jsonp",
            jsonp: "jsoncallback",
            data: {
                id: $("input[name='AgencyCode']").val()
            },
            success: function (d) {
                if (d.Ret == 0) { /*获取岗位成功*/
                    if (d.Data.length != 0) {
                        /*有岗位数据*/
                        $("#PostSelect").empty();
                        $.each(d.Data, function (i, item) {
                            var options = $('<option value="' + item.Code + '">' + item.Name + '</option>');
                            $("#PostSelect").append(options);
                        });
                        $("#PostSelect").val($("input[name='PostCode']").val());
                    }
                } else {
                    alert(d.Message);
                }
            }
        });
    }

    //待发布档案查询列表页 搜索按钮
    $(".js_searchPersonalFile").click(function () {
        $("input[name='PostCode']").val($("select[name='PostCodeD'] option:selected").val());

        var reg = /[^\u4E00-\u9FA5^a-z^A-Z^0-9]/g;
        var staffname = $("#PersonalFileList").find("input[name='StaffName']");
        if (staffname.val() != "") {
            if (reg.test(staffname.val())) {
                if ($(".js_showerrortips").find(".text-danger").length > 0) {
                    $(".js_showerrortips").find(".text-danger").show();
                } else {
                    $(".js_showerrortips").append("<span class='input-sm form-control text-danger' style='border:none;'>请输入中文、英文或数字</span>");
                }
                return false;
            } else {
                if ($(".js_showerrortips").find(".text-danger").length > 0) {
                    $(".js_showerrortips").find(".text-danger").hide();
                }
            }
        }

    });

    //岗位 下拉框选择
    $("#PostSelect").change(function () {
        $("input[name='PostCode']").val($("select[name='PostCodeD'] option:selected").val());
    });

    //待发布档案查询列表页 中 单个发布事件
    $(".js_publishFile").click(function () {
    	var _this=$(this);
        $.ajax({
            url: "/staffEntry/published",
            type: "get",
            data: {
            	staffCode:$(this).attr("data-code"),
            	r: Math.random() },
            dataType: "json",
            beforeSend:function(){
            	var strhtml='<div class="public_login_div"> <div id="public_login_divTip" class="popuplayer">稍等，正在发布中...</div><div id="mark" class="markdiv" ></div></div>';
            	$("body").append(strhtml);
            	var winwidth = $(window).width();
                var thisleft = winwidth / 2 - $("#public_login_divTip").width() / 2;
                var thistop = $(window).height() / 2 - $("#public_login_divTip").height() / 2 + $(window).scrollTop();
                var bodyheight = Math.max($(document.body).outerHeight(true), $(window).height(true));
                $(".public_login_div .markdiv").css("height", bodyheight);
                $("#public_login_divTip").css({ 'left': thisleft, 'top': thistop });
            	//_this.unbind("click");
            },
            success: function (d) {
            	if(d.result=="1"){
            		window.location.href = window.location.href;
            	}else{
            		alert(d.message);
            		$(".public_login_div").remove();
            	}
            },
            error:function(){
            	alert("网络错误或服务器连接失败，请刷新页面重试或联系相关管理员")
            }
        });
        
    });

    
    //全选 反选
    $(".js_allPublishFileInp").click(function(){
    	if($(this).is(':checked')){
    		var flag=false;
    		for(var i=0;i<$(".js_allPublishFileInp").length;i++){
    			if(!$($(".js_allPublishFileInp").eq(i)).is(':checked')){
    				flag=true;
    			}
    		}
			if(!flag){
    			$(".js_allPublishFileTh").prop("checked", true);  
    		}
    	}else{
    		$(".js_allPublishFileTh").removeAttr("checked"); 
    	}
    	
    })
  

    //待发布档案查询列表页-批量发起
    $(".js_allPublishFile").click(function () {
        var allsels = [];
        var onlists = "";
        $(".js_allPublishFileInp:checked").each(function () {
            allsels.push($(this).parents("tr").attr("data-id"));
        });
        var onlists = allsels.join(",");
        if (onlists == "") {
            alert("请勾选发布内容");
        } else {
            $.ajax({
                url: "/staffEntry/publishedMany",
                type: "get",
                dataType: "json",
                data: {
                    r: Math.random(),
                    staffCode: onlists
                },
                beforeSend:function(){
                	var strhtml='<div class="public_login_div"> <div id="public_login_divTip" class="popuplayer">稍等，正在发布中...</div><div id="mark" class="markdiv" ></div></div>';
                	$("body").append(strhtml);
                	var winwidth = $(window).width();
                    var thisleft = winwidth / 2 - $("#public_login_divTip").width() / 2;
                    var thistop = $(window).height() / 2 - $("#public_login_divTip").height() / 2 + $(window).scrollTop();
                    var bodyheight = Math.max($(document.body).outerHeight(true), $(window).height(true));
                    $(".public_login_div .markdiv").css("height", bodyheight);
                    $("#public_login_divTip").css({ 'left': thisleft, 'top': thistop });
                	//$(".js_allPublishFile").unbind("click");
                },
                success: function (d) {
                	d=d.data;
                	var alertHtml="";
                	for(var i=0;i<d.length;i++){
                		alertHtml+=d[i].realName+" "+d[i].step+" "+d[i].message+"\n";
                	}
                	if(alertHtml!=""){
                		alert(alertHtml);
                		$(".public_login_div").remove();
                	}else{
                		window.location.href = window.location.href;
                	}
                },
                error:function(){
                	alert("网络错误或服务器连接失败，请刷新页面重试或联系相关管理员")
                }
            });
        }
    });



});