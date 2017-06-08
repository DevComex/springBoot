/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：张志玲
 * 联系方式：zhangzhiling@gyyx.cn
 * 创建时间： 2017/2/27
 * 功能： oa兑换码查询页面（增删改查）
 * -------------------------------------------------------------------------*/
$(function () {
	
    //时间控件（页面中）
	    $(".js_startTimeCtr").datepicker({
	        format: 'yyyy-mm-dd ',
	        startView: 2,   //默认打开视图（ 0：分钟视图、 1：小时视图、2：日试图、3：月视图、4：年视图）
	        autoclose: true,  //选择后自动关闭
	        todayBtn: true,  //选择今天的按钮
	        minView: 2,  //只能选到日试图  （0 代表能选到分钟、1 代表能选到小时、2 代表能选到日 ）
	        // endDate: new Date(),       //今天之后不可选
	        
	    }).on('changeDate', function (ev) {
	        $(".js_startTimeCtr").datepicker('hide');    //隐藏开始时间面板
	        $(".js_EndTimeCtr").datepicker('show');  //显示结束时间面板
	    });
	    
	    $(".js_EndTimeCtr").datepicker({format: 'yyyy-mm-dd ',startView: 2, autoclose: true,todayBtn: true,minView: 2, 
	        //endDate: new Date() 
	    }).on('changeDate', function (ev) {
	        $(".js_EndTimeCtr").datepicker('hide'); 
	    });
	
    //时间控件(弹层)
    $(".js_startTimeCtr02").datepicker({format: 'yyyy-mm-dd ',startView: 2, autoclose: true,todayBtn: true, minView: 2,  
        //endDate: new Date() 
    }).on('changeDate', function (ev) {
        $(".js_startTimeCtr02").datepicker('hide');   
        $(".js_EndTimeCtr02").datepicker('show'); 
    });
    $(".js_EndTimeCtr02").datepicker({format: 'yyyy-mm-dd ',startView: 2,autoclose: true,todayBtn: true,minView: 2,
        // endDate: new Date() 
    }).on('changeDate', function (ev) {
        $(".js_EndTimeCtr02").datepicker('hide'); 
    });
    
	//时间格式转换
	Date.prototype.Format = function (fmt) {  
        var o = {
            "M+": this.getMonth() + 1, //月份 
            "d+": this.getDate(), //日 
            "h+": this.getHours(), //小时 
            "m+": this.getMinutes(), //分 
            "s+": this.getSeconds(), //秒 
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
            "S": this.getMilliseconds() //毫秒 
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };

    //-------------------------------查询页面---------------------------------------------//
	$(".emptyTime").click(function(){
		$(".js_startTimeCtr").val('');
		$(".js_EndTimeCtr").val('');
	});
    //验证是否为空
    function isNull(obj) {
        if ($.trim(obj.val()) == "") {
            obj.parent().next('.error').html('*必填').show();
            return false;
        } else {
            obj.parent().next('.error').hide();
            return true;
        }
    }

    function inputIsNull(obj) {
        obj.bind('keyup', function () {
            if ($.trim(obj.val()) == "") {
                obj.parent().next('.error').html('*必填').show();
                return false;
            } else {
                obj.parent().next('.error').hide();
                return true;
            };
        });

        obj.bind('blur', function () {
            if ($.trim(obj.val()) == "") {
                obj.parent().next('.error').html('*必填').show();
                return false;
            } else {
                obj.parent().next('.error').hide();
                return true;
            };
        });
    }
    
    //奖品类型选择change
    $("input[name='lucly']").change(function () {
        var v = $(this).val();
        if (v == 'RealPrize') {
            $(".cardNummber").hide();//.find("#cardNummber").val('');
            $(".cardPassword").hide();//.find("#cardPassword").val('');
            $(".courier_number").show(); 
        } else {
            $(".cardNummber").show();
            $(".cardPassword").show();
            $(".courier_number").hide();
        };
        return v;
    });
    verForm();
    function verForm() {
        valType = $("input[name='lucly']:checked").val();
        var userName = $("#userName");//昵称
        var luclyName = $("#luclyName");//奖品名
        var cardNummber = $("#cardNummber");//卡号
        var activtyName = $("#activtyName");//活动名称
        var js_startTimeCtr02 = $(".js_startTimeCtr02");//获奖时间
        var js_EndTimeCtr02 = $(".js_EndTimeCtr02");//结束时间

        inputIsNull(userName);
        inputIsNull(luclyName);
        inputIsNull(cardNummber);
        inputIsNull(activtyName);
        //inputIsNull(js_startTimeCtr02);
        //inputIsNull(js_EndTimeCtr02);


        if (!isNull(userName)) {
            return false;
        }
        if (!isNull(luclyName)) {
            return false;
        }
        if (valType == 'VirtualPrize') {

            if (!isNull(cardNummber)) {
                return false;
            }
        }

        if (!isNull(activtyName)) {
            return false;
        }
        if (!isNull(js_startTimeCtr02)) {
            return false;
        }
        if (!isNull(js_EndTimeCtr02)) {
            return false;
        }
        return true;

    }

    /*新增或者修改-保存兑换信息*/
    var flagresume = true;
    $("#js_update_task").click(function () {
    	var url='',courierNumberAfter='';
    	if($("#updateTask").find(".exchangeTitle").html()=='新增兑换信息'){
    		url='add';
    		courierNumberAfter='';
    		codeNummber='';
    	}else{
    		url="update";
    		courierNumberAfter=$("#courier_number").val();
    		codeNummber=$(".exchangeTitle").attr("updateCode");
    	};
        if (verForm()) {
            if (flagresume) {
                flagresume = false;//防止重复点击
                $.ajax({
                    url: rc + "/wechatcharge/"+url,
                    type: "post",
                    dataType: "JSON",
                    data: {
                        r: Math.random(),
                        channelName: $("#channel").val(),//渠道
                        nickName: $.trim($('#userName').val()),//昵称
                        chargeCode: $(".dhm").val(),//兑换码
                        prizeName: $.trim($('#luclyName').val()),//奖品名
                        prizeType: $("input[name='lucly']:checked").val(),//奖品类型
                        cardNo: $.trim($('#cardNummber').val()),//卡号
                        cardPwd: $.trim($('#cardPassword').val()),//密码
                        actionName: $.trim($('#activtyName').val()),//活动名称
                        awardTime: $.trim($('.js_startTimeCtr02').val()),//获奖时间
                        chargeEndTime: $.trim($('.js_EndTimeCtr02').val()),//兑换截止时间
                        expressNumber:$.trim(courierNumberAfter),//实物奖品快递单号
                        code:codeNummber
                       
                    },
                    success: function (d) {
                        if (d.isSuccess) {
                            alert(d.message);
                            flagresume = true;
                            $(".close_js_Msg").click();
                            getPage();//初始化
                        } else {
                        	alert(d.message);
                            flagresume = true;
                        }
                    }
                });

            }

        }
    });
    
    getPage();//初始化

    //查询兑换信息
    $('#PresInfo_btn_sea').click(function () {
    	var statrTimeJY = $(".js_startTimeCtr").val();
    	var endTimeJY = $(".js_EndTimeCtr").val();
    	if(statrTimeJY > endTimeJY){
    		alert("开始时间不能大于结束时间");
    	}else{
    		getPage();
    	}    
    	
    });

    // 分页列表获取
    function getPage() {
    	$("#loadPop").hide();
    	$("#htmlWrap").hide();
    	
    		 $("#tableBox").ajaxPage({
    	            url: rc + "/wechatcharge/query",
    	            pageObj: $("#DataTables_Table_0_paginate"),
    	            pageIndex: 1,
    	            pageSize: 10,
    	            type:"post",
    	            curPageCls: "paginate_active",
    	            pageInfo: {
    	                obj: $("#DataTables_Table_0_info"),
    	                content: "共{allCount}条  当前第{pageIndex}/{allPage}页"
    	            },
    	            paramObj: {
    	            	channelName: $("#PresInfo_way").val(),
    	            	timeType: $("#PresInfo_type_sel_sea").val(),
    	            	beginTime :$.trim($('.js_startTimeCtr').val()),
    	            	endTime: $.trim($('.js_EndTimeCtr').val()),
    	            	conditionType: $('#activityNameFind').val(),
    	            	conditionValue: $.trim($('#activityNameWord').val())
    	            },

    	            dataFn: function (data, $this) {

    	                var dataHtml = "", dCont = data.data;
    	                if (dCont && dCont.length && dCont[0]) {
    	                
    	                    for (var i = 0; i < dCont.length; i++) {
    	                    	var isWendao=dCont[i].isWendao;
    	                    	var awardTime = dCont[i].awardTime;
    	                    	var chargeEndTime = dCont[i].chargeEndTime;
    	                    	var chargeTime = dCont[i].chargeTime;
    	                    	
    	                    	var channelName = dCont[i].channelName;
    	                    	var prizeType = dCont[i].prizeType;
    	                    	var isCharge = dCont[i].isCharge;
    	                    	
    	                    	
    	                    	if(channelName=='WeiXin'){
    	                    		channelName='微信';
    	                    	}else if(channelName=='WeiBo'){
    	                    		channelName='微博';
    	                    	}else if(channelName=='TieBa'){
    	                    		channelName='贴吧';
    	                    	}else if(channelName=='Other'){
    	                    		channelName='其他';
    	                    	}
    	                    	if(prizeType=='VirtualPrize'){
    	                    		prizeType='虚拟';
    	                    	}else{
    	                    		prizeType='实物';
    	                    	}
    	                    	if(awardTime==null){
    	                        	awardTime='';
    	                        }else{
    	                        	awardTime=new Date(awardTime).Format("yyyy-MM-dd");
    	                        }
    	                    	if(chargeEndTime==null){
    	                    		chargeEndTime='';
    	                        }else{
    	                        	chargeEndTime=new Date(chargeEndTime).Format("yyyy-MM-dd");
    	                        }
    	                    	if(chargeTime==null){
    	                    		chargeTime='';
    	                        }else{
    	                        	chargeTime=new Date(chargeTime).Format("yyyy-MM-dd hh:mm:ss");
    	                        }
    	                        if(isWendao==null){
    	                        	isWendao='';
    	                        }else if(isWendao==false){
    	                        	isWendao='否';
    	                        }else if(isWendao==true){
    	                        	isWendao='是';
    	                        }
    	                        if(isCharge==false){
    	                        	isCharge='否';
    	                        }else{
    	                        	isCharge='是';
    	                        }
    	                    	dataHtml += '<tr>' +
    	                                         '<td>' + dCont[i].code + '</td>' +
    	                                         '<td class="channel" channelName='+dCont[i].channelName +'>' + channelName + '</td>' +
    	                                         '<td>' + dCont[i].nickName + '</td>' +
    	                                         '<td>' + dCont[i].chargeCode + '</td>' +
    	                                         '<td prizeType="'+dCont[i].prizeType+'" class="type">' + prizeType + '</td>' +
    	                                         '<td>' + dCont[i].prizeName + '</td>' +
    	                                         '<td>' + dCont[i].actionName + '</td>' +
    	                                         '<td>' +awardTime+ '</td>' +
    	                                         '<td>' +chargeEndTime + '</td>' +
    	                                         '<td>' + isCharge + '</td>' +
    	                                         '<td>' + chargeTime + '</td>' +
    	                                         '<td>' + isWendao + '</td>' +
    	                                         '<td>'+
    	                                              '<a href="#updateTask" data-toggle="modal" class="reviseBtn" code=' + dCont[i].code + '>修改</a>' +
    	                                              '<a href="javascript:;" class="deleteBtn" code=' + dCont[i].code + '>删除</a>' +
    	                                              '<a href="#updateTask" data-toggle="modal" class="detailsBtn" code=' + dCont[i].code + '>详情</a>' +
    	                                          '</td>'+
    	                                     '</tr>';
    	                    }
    	                    $(".panel-default").show();
    	                    $("#tableBox").empty().html(dataHtml);
    	                    return dataHtml;
    	                    

    	                } else {
    	                    $("#tableBox").empty();
    	                    $(".panel-default").hide();
    	                    alert("暂无记录");
    	                }
    	            }

    	        });
    	
       
    }



    //删除兑换信息
    $("#tableBox").on("click", ".deleteBtn", function () {
        var codeNum = $(this).attr("code");//获取删除code值
        if (confirm('确定要删除吗？')) {
            console.log(codeNum);
            $.ajax({
            	url: rc + "/wechatcharge/delete",
                type: "get",
                dataType: "JSON",
                data: {
                    r: Math.random(),
                    code: codeNum
                },
                success: function (d) {
                    if (d.isSuccess) {
                        getPage();
                    } else {
                        alert(d.message);
                    }
                }
            });
        }
    });

//弹出层公用（判断+ 赋值）
  //增加快递单号项
	var courierNumber ='<label class="col-sm-3 control-label">快递单号:</label>'+
	'<div class="col-sm-6">'+
		'<input type="text" id="courier_number" class="form-control " value="" placeholder="请在玩家兑换实物奖后填写" maxlength="20" >'+
	'</div>';
	
    //获取兑换码,调取新增弹层
    $(".addNewWay").click(function () {
        $(".error").hide();
        $(".exchangeTitle").html('新增兑换信息');
        $(".courier_number").empty();
        $("#formDetails").empty().hide();//详情隐藏
   	    $("#updateTaskForm").show();//修改增加显示
        $.ajax({
            url: rc + "/wechatcharge/getChargeCode",
            type: "get",
            dataType: "JSON",
            data: {
                r: Math.random(),
            },
            success: function (d) {
                if (d.isSuccess) {
                    $(".dhm").val(d.data);
                    $("input[title='emptyInput']").val('');//置空
                }
            }
        });
    });
    //修改兑换信息，调取修改弹层
    $("#tableBox").on("click", ".reviseBtn", function () {
    	$(".error").hide();
    	$(".exchangeTitle").html('修改兑换信息');
    	$(".courier_number").empty().html(courierNumber);
    	$("#formDetails").empty().hide();//详情隐藏
   	    $("#updateTaskForm").show();//修改增加显示
    	var codeNum = $(this).attr("code");//获取修改code值
    	var prizeTypeMess = $(this).parent().siblings(".type").attr("prizeType");//获取奖品种类
    	var channel = $(this).parent().siblings(".channel").attr("channelName") ;//获取渠道
    	
    	if(prizeTypeMess=='VirtualPrize'){
        	$("input[value='VirtualPrize']").prop('checked',true).trigger('change');
        }else{
        	$("input[value='RealPrize']").prop('checked',true).trigger('change');
        }
    	//回绑数据信息
    	$.ajax({
            url: rc + "/wechatcharge/get",
            type: "get",
            dataType: "JSON",
            data: {
                r: Math.random(),
                code: codeNum,//后面的要删除
            },
            success: function (d) {
                if (d.isSuccess) {
                	var dCont = d.data;
                	console.log(dCont);
                	var channelWay=dCont.channelName;//获取渠道
                		
                		//时间格式化处理
                    	var awardTime =dCont.awardTime;
                    	var chargeEndTime = dCont.chargeEndTime;
                    	
                    	if(awardTime==null){
                        	awardTime='';
                        }else{
                        	awardTime=new Date(awardTime).Format("yyyy-MM-dd");
                        }
                    	if(chargeEndTime==null){
                    		chargeEndTime='';
                        }else{
                        	chargeEndTime=new Date(chargeEndTime).Format("yyyy-MM-dd");
                        }
                    $("#channel").val(channelWay);
                   // $("#channel").find("option[value="+channelWay+"]").attr("selected",true);//渠道
                    $("#userName").val(dCont.nickName);//昵称
                    $("#dhm").val(dCont.chargeCode);//兑换码
                    $("#luclyName").val(dCont.prizeName);//奖品名称
                    $("#cardNummber").val(dCont.cardNo);//卡号
                    $("#cardPassword").val(dCont.cardPwd);//密码
                    $("#activtyName").val(dCont.actionName);//活动名称
                    $(".js_startTimeCtr02").val(awardTime);//获奖时间
                    $(".js_EndTimeCtr02").val(chargeEndTime);//结束时间
                    $("#courier_number").val(dCont.expressNumber);//快递单号
                    $(".exchangeTitle").attr("updateCode",codeNum);
                }
            }
        });

    });
   


    //导出功能exc
    $("#exportBtn").click(function () {
        if ($("#tableBox").find("tr").length > 0) {
            window.location = rc + '/wechatcharge/export?beginTime=' + $(".js_startTimeCtr").val() + '&endTime=' + $(".js_EndTimeCtr").val() + '&channelName=' + $("#PresInfo_way").val()+ '&timeType=' + $("#PresInfo_type_sel_sea").val()+ '&conditionType=' + $("#activityNameFind").val()+ '&conditionValue=' + $("#activityNameWord").val();
        }else {
            alert("没有列表可以导出，请正确查询后导出");
        }
    });


    //查看详情
    $("#tableBox").on("click",".detailsBtn",function(){
    	var codeNum = $(this).attr("code");//获取详情code值
    	$(".exchangeTitle").html('兑换信息详情');
    	$("#updateTaskForm").hide();//修改增加隐藏
    	//回绑数据信息
    	$.ajax({
            url: rc + "/wechatcharge/get",
            type: "get",
            dataType: "JSON",
            data: {
                r: Math.random(),
                code: codeNum,//后面的要删除
            },
            success: function (d) {
                if (d.isSuccess) {
                	var dCont = d.data;
                	console.log(dCont);
                	var channelWay=dCont.channelName;//获取渠道
                		//时间格式化处理
                    	var awardTime =dCont.awardTime;
                    	var chargeEndTime = dCont.chargeEndTime;
                    	var chargeTime = dCont.chargeTime;
                    	var isWendao=dCont.isWendao;
                    	var recipientName=dCont.recipientName;
                    	var recipientPhone=dCont.recipientPhone;
                    	var recipientAddress=dCont.recipientAddress;
                    	var channelName=dCont.channelName;
                    	var prizeType=dCont.prizeType;
                    	var isCharge = dCont.isCharge;
                    	 if(isWendao==null){
	                        	isWendao='';
	                        }else if(isWendao==false){
	                        	isWendao='否';
	                        }else if(isWendao==true){
	                        	isWendao='是';
	                        }
	                        if(isCharge==false){
	                        	isCharge='否';
	                        }else{
	                        	isCharge='是';
	                        }
                    	if(recipientName==null){
                    		recipientName='';
                    	}
                    	if(recipientPhone==null){
                    		recipientPhone='';
                    	}
                    	if(recipientAddress==null){
                    		recipientAddress='';
                    	}
                    	if(channelName=='WeiXin'){
                    		channelName='微信';
                    	}else if(channelName=='WeiBo'){
                    		channelName='微博';
                    	}else if(channelName=='TieBa'){
                    		channelName='贴吧';
                    	}else if(channelName=='Other'){
                    		channelName='其他';
                    	}
                    	if(prizeType=='VirtualPrize'){
                    		prizeType='虚拟';
                    	}else{
                    		prizeType='实物';
                    	}
                    	
                    	if(awardTime==null){
                        	awardTime='';
                        }else{
                        	awardTime=new Date(awardTime).Format("yyyy-MM-dd");
                        }
                    	if(chargeEndTime==null){
                    		chargeEndTime='';
                        }else{
                        	chargeEndTime=new Date(chargeEndTime).Format("yyyy-MM-dd");
                        }
                    	if(chargeTime==null){
                    		chargeTime='';
                        }else{
                        	chargeTime=new Date(chargeTime).Format("yyyy-MM-dd hh:mm:ss");
                        }
                    //绑定值
                    	 var details = '<div class="form-group" id="detailsBox">' +
                    	 '<div class="groupLine">' +
                    	 	'<label class="col-sm-2 control-label">序号:</label>' +
                    	 	'<div class="col-sm-4 borderRight" >' +
                    	 		'<span>'+dCont.code+'</span>' +
                    	 	'</div>' +
                    	     '<label class="col-sm-3 control-label">渠道:</label>' +
                    	 	'<div class="col-sm-3">' +
                    	 		'<span>'+channelName+'</span>' +
                    	 	'</div>' +
                    	 '</div>' +
                    	 '<div class="groupLine">' +
                    	 	'<label class="col-sm-2 control-label">昵称:</label>' +
                    	 	'<div class="col-sm-4 borderRight">' +
                    	 		'<span>'+dCont.nickName+'</span>' +
                    	 	'</div>' +
                    	     '<label class="col-sm-3 control-label">兑换码:</label>' +
                    	 	'<div class="col-sm-3">' +
                    	 		'<span>'+dCont.chargeCode+'</span>' +
                    	 	'</div>' +
                    	 '</div>' +
                    	 '<div class="groupLine">' +
                    	 	'<label class="col-sm-2 control-label">奖品名:</label>' +
                    	 	'<div class="col-sm-4 borderRight">' +
                    	 		'<span>'+dCont.prizeName+'</span>' +
                    	 	'</div>' +
                    	     '<label class="col-sm-3 control-label">奖品类型:</label> ' +
                    	 	'<label class="col-sm-3" style="margin-bottom: 0;">' +
                    	 		'<span class="prizeType" type="'+prizeType+'">'+prizeType+'</span> ' +
                    	 	'</label>' +
                    	 '</div>' +

                    	 '<div class="groupLine card2">' +
                    	 	'<label class="col-sm-2 control-label">卡号:</label>' +
                    	 	'<div class="col-sm-4 borderRight">' +
                    	 		'<span>'+dCont.cardNo+'</span>' +
                    	 	'</div>' +
                    	     '<label class="col-sm-3 control-label">密码:</label>' +
                    	 	'<div class="col-sm-3">' +
                    	 		'<span>'+dCont.cardPwd+'</span>' +
                    	 	'</div>' +
                    	 '</div>' +

                    	 '<div class="groupLine">' +
                    	 	'<label class="col-sm-2 control-label">活动名称:</label>' +
                    	 	'<div class="col-sm-4 borderRight">' +
                    	 		'<span>'+dCont.actionName+'</span>' +
                    	 	'</div>' +
                    	     '<label class="col-sm-3 control-label">是否为问道玩家:</label>' +
                    	 	'<div class="col-sm-3">' +
                    	 		'<span>'+isWendao+'</span>' +
                    	 	'</div>' +
                    	 '</div>' +
                    	 '<div class="groupLine">' +
                    	 	'<label class="col-sm-2 control-label">获奖时间:</label>' +
                    	 	'<div class="col-sm-4 borderRight">' +
                    	 		'<span>'+awardTime+'</span>' +
                    	 	'</div>' +
                    	     '<label class="col-sm-3 control-label">结束时间:</label>' +
                    	 	'<div class="col-sm-3">' +
                    	 		'<span>'+chargeEndTime+'</span>' +
                    	 	'</div>' +
                    	 '</div>' +
                    	 '<div class="groupLine">' +
                    	 	'<label class="col-sm-2 control-label">是否兑换:</label>' +
                    	 	'<div class="col-sm-4 borderRight">' +
                    	 		'<span>'+isCharge+'</span>' +
                    	 	'</div>' +
                    	     '<label class="col-sm-2 control-label">兑换时间:</label>' +
                    	 	'<div class="col-sm-4">' +
                    	 		'<span>'+chargeTime+'</span>' +
                    	 	'</div>' +
                    	 '</div>' +

                    	 '<div class="groupLine namePhone2">' +
                    	 	'<label class="col-sm-2 control-label ">姓名:</label>' +
                    	 	'<div class="col-sm-4 borderRight">' +
                    	 		'<span>'+recipientName+'</span>' +
                    	 	'</div>' +
                    	     '<label class="col-sm-3 control-label ">电话:</label>' +
                    	 	'<div class="col-sm-3">' +
                    	 		'<span>'+recipientPhone+'</span>' +
                    	 	'</div>' +
                    	 '</div>' +

                    	  '<div class="groupLine address2">' +
                    	 	'<label class="col-sm-2 control-label">地址:</label>' +
                    	 	'<div class="col-sm-8">' +
                    	 		'<span>'+recipientAddress+'</span>' +
                    	 	'</div>' +
                    	 '</div>' +
                    	 '<div class="groupLine courier_number2" >' +
                    	     '<label class="col-sm-2 control-label">快递单号:</label>' +
                    	 	'<div class="col-sm-8">' +
                    	 		'<span>'+dCont.expressNumber+'</span>' +
                    	 	'</div>' +
                    	 '</div>' +

                    	 '</div>';
                    	 //绑定值结束
                    	 $("#formDetails").empty().html(details);
                    	 $("#formDetails").show();//详情展示
                    	 var type=$(".prizeType").html();
                    	 if(type=='实物'){
                    		 $(".namePhone2").show();$(".address2").show();$(".courier_number2").show();
                    	     $(".card2").hide();
                    	 }else{
                    		 $(".namePhone2").hide();$(".address2").hide();$(".courier_number2").hide();
                    	     $(".card2").show();
                    		 
                    	 }
                }
            }
        });
       
    	
    });
























});