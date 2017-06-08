/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：李根
 * 联系方式：ligen@gyyx.cn
 * 创建时间： 2015/1/6 
 * 版本号：v1.0
 * 功能： 工资单管理
 * -------------------------------------------------------------------------*/
$(function () {
    //初始数据
    $(".js_idcode").val("");
    $(".js_hankcode").val("");
    $(".js_hankadd").val("");
    //判断是否有银行卡信息
    $.ajax({
        url: '/StaffBankCard/GetBankCardByStaffCode',
        type: "get",
        data: {
            r: Math.random()
        },
        dataType: "json",
        success: function (d) {
            if (d.Ret == 0) {
                $(".js_idcodeleb").html(d.Date.IdCardNo);
                $(".js_hankcodeleb").html(d.Date.BankCardNo);
                $(".js_hankaddleb").html(d.Date.OpeningBank);
                $(".js_cardcode").val(d.Date.Code);
                $(".js_submitedit").show();
            } else if (d.Ret == -4) {
                $(".js_submitcreate").show();
            }
        }
    });
    //编辑工资单
    $(".js_edit").click(function () {
        $(this).hide();
        $(".js_view").show();
        if ($(".js_lebel").html() == "无") {
            $(".js_idcode").val("");
            $(".js_hankcode").val("");
            $(".js_hankadd").val("");
            $(".js_lebel").hide();
            $(".js_input").show();
        } else {
            $(".js_idcode").val($(".js_idcodeleb").html());
            $(".js_hankcode").val($(".js_hankcodeleb").html());
            $(".js_hankadd").val($(".js_hankaddleb").html());
            $(".js_lebel").hide();
            $(".js_input").show();
        }
    });
    //确认信息
    $(".js_view").click(function () {
        var $this = $(this);
        if ($(".js_idcode").val() == "") {
            $this.parents().find(".js_error").show().html("请输入有效的身份证号码");
        } else {
            $this.parents().find(".js_error").hide().html("");
        }
        if ($(".js_hankcode").val() == "") {
            $this.parents().find(".js_error2").show().html("请输入银行卡号");
        } else {
            $this.parents().find(".js_error2").hide().html("");
        }
        if ($(".js_hankadd").val() == "") {
            $this.parents().find(".js_error3").show().html("请输入开户行地址");
        } else {
            $this.parents().find(".js_error3").hide().html("");
        }
       
        if ($(".js_idcode").val() != "" && $(".js_hankcode").val() != "" && $(".js_hankadd").val() != "" && _VerCheckIdCard($(".js_idcode")) == true) {
            $(".js_idcodediv").html($(".js_idcode").val());
            $(".js_hankcodediv").html($(".js_hankcode").val());
            $(".js_hankadddiv").html($(".js_hankadd").val());
            $(".js_viewhide").trigger("click");
            $(".js_error").html("").hide();
            $(".js_error2").html("").hide();
            $(".js_error3").html("").hide();
        }
    });
    //提交编辑信息
    $(".js_submitedit").click(function () {
        $.ajax({
            url: '/StaffBankCard/Edit',
            type: "post",
            data: {
                r: Math.random(),
                Code: $(".js_cardcode").val(),
                IdCardNo:$(".js_idcode").val(),
                BankCardNo:$(".js_hankcode").val(),
                OpeningBank:$(".js_hankadd").val()
            },
            dataType: "json",
            success: function (d) {
                if (d.Ret == 0) {
                    alert(d.Message);
                    window.location.href = window.location.href;
                } else if (d.Ret == -4) {
                    alert(d.Message)
                }
            }
        });
    });
    //提交创建信息
    $(".js_submitcreate").click(function () {
        $.ajax({
            url: '/StaffBankCard/Create',
            type: "post",
            data: {
                r: Math.random(),
                IdCardNo: $(".js_idcode").val(),
                BankCardNo: $(".js_hankcode").val(),
                OpeningBank: $(".js_hankadd").val()
            },
            dataType: "json",
            success: function (d) {
                if (d.Ret == 0) {
                    alert(d.Message);
                    window.location.href = window.location.href;
                } else if (d.Ret == -4) {
                    alert(d.Message)
                }
            }
        });
    });
    //时间控件--开始
    $(".js_startTimeCtr").datepicker({
        format: 'yyyy-mm-dd',
        startView: 2,   //默认打开是年试图
        autoclose: true,  //选择后自动关闭
        todayBtn: true,  //选择今天的按钮
        minView: 2   //只能选到年试图  （0 代表能选到分钟、1 代表能选到小时、2 代表能选到日 ）
    }).on('changeDate', function (ev) {
        var AddStartTime = $(this).val();
        $(".js_endTimeCtr").datepicker('setStartDate', AddStartTime);
        $(".js_endTimeCtr").datepicker('show')   //显示结束时间面板
    });
    $(".js_endTimeCtr").datepicker({
        format: 'yyyy-mm-dd',
        startView: 2,   //默认打开是年试图
        autoclose: true,  //选择后自动关闭
        todayBtn: true,  //选择今天的按钮
        minView: 2   //只能选到年试图  （0 代表能选到分钟、1 代表能选到小时、2 代表能选到日 ）
    }).on('changeDate', function (ev) {
        var AddEndTime = $(this).val();
        $(".js_startTimeCtr").datepicker('setEndDate', AddEndTime);

    });
    //时间控件--结束
    //初始化时间
    var datelastTimes = new Date();
    datelastTimes.setDate(datelastTimes.getDate());
    var yy = datelastTimes.getFullYear();
    var MM = datelastTimes.getMonth() + 1;  //因为1月这个方法返回为0，所以加1
    if (MM < 10) {
        MM = "0" + MM
    }
    var dd = datelastTimes.getDate();
    if (dd < 10) {
        dd = "0" + dd
    }
    $(".js_endTimeCtr").val(yy + "-" + MM + "-" + dd);
    //初始化前30天日期
    var datelastTime = new Date();
    datelastTime.setDate(datelastTime.getDate() - 30);
    var yy = datelastTime.getFullYear();
    var MM = datelastTime.getMonth() + 1;  //因为1月这个方法返回为0，所以加1
    if (MM < 10) {
        MM = "0" + MM
    }
    var dd = datelastTimes.getDate();
    if (dd < 10) {
        dd = "0" + dd
    }
    $(".js_startTimeCtr").val(yy + "-" + MM + "-" + dd);
    //记录查询后日期
    if ($(".js_hidstarttime").val() != "0001-01-01") {
        $(".js_startTimeCtr").val($(".js_hidstarttime").val());
    }
    if ($(".js_hidendtime").val() != "0001-01-01") {
        $(".js_endTimeCtr").val($(".js_hidendtime").val());
    }
    //身份证验证
    var _VerCheckIdCard = function (obj) {
        var icard = [];
        var icardval = obj.val();
        for (var i = 0; i < icardval.length; i++) {
            icard.push(icardval.substr(i, 1));
        }
        var year = icardval.length == 15 ? "19" + icardval.substr(6, 2) : icardval.substr(6, 4);
        var month = icardval.length == 15 ? icardval.substr(8, 2) : icardval.substr(10, 2);
        var myDate = new Date();
        var Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1];    // 加权因子   
        var ValideCode = [1, 0, 'x', 9, 8, 7, 6, 5, 4, 3, 2];            // 身份证验证位值.10代表X   
        var sum = 0;                             // 声明加权求和变量 
        myDate.getFullYear();
        myDate.getMonth();
        var months = (myDate.getFullYear() - year) * 12 + (myDate.getMonth() + 1 - month);
        var exp = new RegExp(/^(^\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/);
        if (!exp.test(obj.val())) {
            $(".js_error").show().html("您输入的身份证号码不正确或长度太短");
            return false;
        } else if (months < 18 * 12 || months > 80 * 12) {
            $(".js_error").show().html("未成年人不允许注册，请检查您的身份证号");
        } else if (icard.length != 15) {
            for (var i = 0; i < 17; i++) {
                sum += Wi[i] * icard[i];            // 加权求和   
            }
            valCodePosition = sum % 11;                // 得到验证码所位置   
            if (icard[17].toLowerCase() != ValideCode[valCodePosition]) {
                $(".js_error").show().html("您输入的身份证号码不正确或长度太短");
                return false;
            } else {
                return true;
            }
        }
    }

});