/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：陈巧玲
 * 联系方式：chenqiaoling@gyyx.cn
 * 创建时间： 2014/8/4 
 * 版本号：v1.0

 * 内容： 档案管理页面 编辑雇佣信息

 * -------------------------------------------------------------------------*/
$(function () {
  
    //根据机构和岗位code确定岗位下拉框的值
    function setPostCode(PostTarget, PostCode, AgencyCode) {
        if (PostCode != 0) {
            $.ajax({
                url: "/post/agencyposts/" + "?r=" + Math.random(),
                type: "GET",
                dataType: "json",
                data: {
                    id: AgencyCode
                },
                success: function (d) {
                    if (d.Ret == 0) { /*获取岗位成功*/
                        if (d.Data.length != 0) {
                            /*有岗位数据*/
                            $(PostTarget).empty();
                            $.each(d.Data, function (i, item) {
                                var options = $('<option value="' + item.Code + '">' + item.Name + '</option>');
                                $(PostTarget).append(options);
                            });
                            $(PostTarget).val(PostCode);
                        }
                    } else {
                        alert(d.Message);
                    }
                }
            });
        }
    }

    //绑定时间控件
    //入职日期
    $("#HireDate").datepicker({
        autoclose: true,
        startView: 2,
        format: "yyyy-m-d",
        minView: 2
    }).on("changeDate", function (ev) {
        var startD = $(this).val();
        $("#RealConfirmationDate").datepicker("setStartDate", startD);
        $("#HireDate").val($(this).val()).blur();
    });
    //约定转正日期
    $("#PreConfirmationDate").datepicker({
        autoclose: true,
        startView: 2,
        format: "yyyy-m-d",
        minView: 2
    }).on("changeDate", function (ev) {
        $("#PreConfirmationDate").val($(this).val()).blur();
    });
    //实际转正日期
    $("#RealConfirmationDate").datepicker({
        autoclose: true,
        startView: 2,
        format: "yyyy-m-d",
        minView: 2
    }).on("changeDate", function () {
        var endD = $(this).val();
        $("#HireDate").datepicker("setEndDate", endD);
        $("#RealConfirmationDate").val($(this).val()).blur();
    });
    //异动日期
    $("#ChangeDate").datepicker({
        autoclose: true,
        startView: 2,
        format: "yyyy-m-d",
        minView: 2
    }).on("changeDate", function (ev) {
        $("#ChangeDate").val($(this).val()).blur();
    });

    //合同生效日期
    $("#BeginDate").datepicker({
        autoclose: true,
        startView: 2,
        format: "yyyy-m-d",
        minView: 2
    }).on("changeDate", function (ev) {
        var startD = $(this).val();
        $("#EndDate").datepicker("setStartDate", startD);
        $("#BeginDate").val($(this).val()).blur();
    });
    //合同到期日期
    $("#EndDate").datepicker({
        autoclose: true,
        startView: 2,
        format: "yyyy-m-d",
        minView: 2,
        onRender: function (date) {
            return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
        }
    }).on("changeDate", function () {
        var endD = $(this).val();
        $("#BeginDate").datepicker("setEndDate", endD);
        $("#EndDate").val($(this).val()).blur();
    });

    //页面机构、岗位、职级初始化
    $(".js_agencySelectD").agencySelect({
        hidCode: $("#AgencyCode"), /*隐藏inputID*/
        hidName: $("#AgencyName"), /*hidden*/
        hidDiv: ".js_agencySelectListD"
    });
    $(".js_agencySelectForPostD").agencySelect({
        hidCode: $("#AgencyCode"), /*隐藏inputID*/
        hidName: $("#AgencyNameForPostD"), /*hidden*/
        hidDiv: ".js_agencySelectListForPostD",
        getPostList: true,
        PostSelect: $("#PostCodeD"),
        isGetAllCode: true
    });
    setPostCode("#PostCodeD", $("#PostCodeDD").val(), $("#AgencyCode").val());
    getRanks("#RankCodeD", $("#RankCode").val());

    //异动信息弹框下机构、岗位、职级
    $(".js_agencySelect").agencySelect({
        hidCode: $("#EmploymentChange").find("input[name='CurrentAgencyCode']"), /*隐藏inputID*/
        hidName: $("#EmploymentChange").find("input[name='CurrentAgencyName']"), /*hidden*/
        isGetAllCode: true
    });
    $(".js_agencySelectForPost").agencySelect({
        hidCode: $("#EmploymentChange").find("input[name='CurrentAgencyCode']"), /*隐藏inputID*/
        hidName: $("#EmploymentChange").find("input[name='AgencyNameForPost']"), /*hidden*/
        hidDiv: ".js_agencySelectListForPost",
        getPostList: true,
        PostSelect: $("#EmploymentChange").find("select[name='CurrentPostCode']"),
        isGetAllCode: true
    });
    //获取职级和员工状态
    function getRanks(target,selectedVal) {
        $.ajax({
            url: "/Staff/Create",
            type: "GET",
            dataType: "json",
            success: function (d) {
                if (d.Ret == 0) {
                    if (d.Data.Ranks.length != 0) {
                        $(target).find("select[name='CurrentRankCode']").empty();
                        $.each(d.Data.Ranks, function (i, item) {
                            if (item.Selected == true) {
                                $(target).find("select[name='CurrentRankCode']").append('<option value="' + item.Value + '" selected>' + item.Text + '</option>');
                            } else {
                                $(target).find("select[name='CurrentRankCode']").append('<option value="' + item.Value + '" >' + item.Text + '</option>');
                            }
                        });
                        if (selectedVal && selectedVal!="") {
                            $(target).find("select[name='CurrentRankCode']").val(selectedVal);
                        }
                    }
                    if (d.Data.StatusArr.length != 0) {
                        $(target).find("select[name='StatusCode']").empty();
                        $.each(d.Data.StatusArr, function (i, item) {
                            if (item.Selected == true) {
                                $(target).find("select[name='StatusCode']").append('<option value="' + item.Value + '" selected>' + item.Text + '</option>');
                            } else {
                                $(target).find("select[name='StatusCode']").append('<option value="' + item.Value + '" >' + item.Text + '</option>');
                            }
                        });
                    }
                } else {
                    alert(d.Message);
                }
            }
        });
    }
    getRanks("#EmploymentChange");

    //初始化公司名称，入职渠道
    if ($("input[name='CompanyNameD']").val()) {
        $("select[name='CompanyName']").val($("input[name='CompanyNameD']").val());
    } 
    if ($("input[name='EntryChannelD']").val()) {
        $("select[name='EntryChannel']").val($("input[name='EntryChannelD']").val());
    }

    //验证下拉框选中
    jQuery.validator.addMethod("select", function (value, element, param) {
        var flag = (value == -1) ? false : true;
        return this.optional(element) || flag;
    });
    /*编号*/
    jQuery.validator.addMethod("isNo", function (value, element, param) {
        var reg = /^[A-Za-z0-9]+$/;
        return this.optional(element) || (reg.test(value));
    });
    /*中英文数字*/
    jQuery.validator.addMethod("isName", function (value, element, param) {
        var reg = /[^\u4E00-\u9FA5^a-z^A-Z^0-9]/g;
        return this.optional(element) || (!reg.test(value));
    });
    /*大于小于html脚本字符*/
    jQuery.validator.addMethod("Html", function (value, element, param) {
        var reg = /[<>]/;
        return this.optional(element) || (!reg.test(value));
    });


    $("#EmploymentChangeForm").validate({
        errorElement: "span",
        onblur: true,
        debug: true,
        ignore: "",
        errorPlacement: function (error, element) {
            if (element.attr("name") == "CurrentAgencyCode") {
                error.appendTo(element.parent().parent());
                error.addClass("text-danger");
            } else {
                error.appendTo(element.parent());
                error.addClass("text-danger");
            }
        },
        rules: {
            ChangeDate: {
                required: true
            },
            CurrentAgencyCode: {
                required: true
            },
            CurrentPostCode: {
                required: true,
                select: true
            },
            CurrentRankCode: {
                required: true,
                select: true
            },
            Processor: {                
                isName: true
            }
        },
        messages: {
            ChangeDate: {
                required: "异动日期为必填项"
            },
            CurrentAgencyCode: {
                required: "机构为必选项"
            },
            CurrentPostCode: {
                required: "岗位为必选项",
                select: "岗位为必选项"
            },
            CurrentRankCode: {
                required: "职级为必选项",
                select: "职级为必选项"
            },
            Processor: {                
                isName: "请输入中文、英文或数字"
            }
        }
    });

    /*异动信息数组*/
    var EmploymentChange = [];
    var EmployInfoList = [];
    var loadEmploys = {
        AgencyName: $("#AgencyName").val(),
        AgencyCode: $("#AgencyCode").val(),
        AgencyNameForPostD: $("#AgencyNameForPostD").val(),
        AgencyCodeForPostD: $("#AgencyCode").val(),
        PostCodeDD: $("#PostCodeDD").val(),
        RankCode: $("#RankCode").val()
    };
    function GetLastestEmployInfo() {
        EmployInfoList = [];
        if (EmploymentChange.length > 0) {
            var lastestEmployInfo = EmploymentChange[EmploymentChange.length - 1];
            var can = lastestEmployInfo.AgencyNameForPost.split("/");
            $("#AgencyName").val(can[can.length - 1]);
            $("#AgencyCode").val(lastestEmployInfo.CurrentAgencyCode);
            $("#AgencyNameForPostD").val(lastestEmployInfo.AgencyNameForPost);
            $("#AgencyCodeForPostD").val(lastestEmployInfo.CurrentAgencyCode);

            var AgencyCodeForPostLast = lastestEmployInfo.CurrentAgencyCode.split("/");
            var acfpl = AgencyCodeForPostLast[AgencyCodeForPostLast.length - 1];
            setPostCode("#PostCodeD", lastestEmployInfo.CurrentPostCode, acfpl);

            $("#RankCodeD").find("select[name='CurrentRankCode']").val(lastestEmployInfo.CurrentRankCode);
        } else {
            $("#AgencyName").val(loadEmploys.AgencyName);
            $("#AgencyCode").val(loadEmploys.AgencyCode);

            $("#AgencyNameForPostD").val(loadEmploys.AgencyNameForPostD);
            $("#AgencyCodeForPostD").val(loadEmploys.AgencyCodeForPostD);

            var acfpl = loadEmploys.AgencyCodeForPostD;
            setPostCode("#PostCodeD", loadEmploys.PostCodeDD, acfpl);

            $("#RankCodeD").find("select[name='CurrentRankCode']").val(loadEmploys.RankCode);
        }

        for (var i = 0; i < EmploymentChange.length; i++) {
            EmployInfoList[i] = {
                ChangeDate: EmploymentChange[i].ChangeDate,
                OriginAgencyCode: EmploymentChange[i].OriginAgencyCode,
                OriginPostCode: EmploymentChange[i].OriginPostCode,
                OriginRankCode: EmploymentChange[i].OriginRankCode,
                CurrentAgencyCode: EmploymentChange[i].CurrentAgencyCode,
                CurrentPostCode: EmploymentChange[i].CurrentPostCode,
                CurrentRankCode: EmploymentChange[i].CurrentRankCode,
                Processor: EmploymentChange[i].Processor,
            };
        }
    }

    var InitEmployInfo = function () {
        var initEmployInfoWrap = $("#EmployTabel tbody").find("tr");
        var initEmployInfoNum = $("#EmployTabel tbody").find("tr").length;
        for (var i = 0; i < initEmployInfoNum; i++) {
            var EmploymentObj = {
                ChangeDate: initEmployInfoWrap.find("td").eq(0).text(),

                OriginAgencyName: initEmployInfoWrap.eq(i).attr("data-OriginAgencyName"),
                OriginPostName: initEmployInfoWrap.eq(i).attr("data-OriginPostName"),
                OriginRankName: initEmployInfoWrap.eq(i).attr("data-OriginRankName"),
                OriginAgencyCode: initEmployInfoWrap.eq(i).attr("data-OriginAgencyCode"),
                OriginPostCode: initEmployInfoWrap.eq(i).attr("data-OriginPostCode"),
                OriginRankCode: initEmployInfoWrap.eq(i).attr("data-OriginRankCode"),

                CurrentAgencyName: initEmployInfoWrap.eq(i).attr("data-CurrentAgencyName"),
                CurrentPostName: initEmployInfoWrap.eq(i).attr("data-CurrentPostName"),
                CurrentRankName: initEmployInfoWrap.eq(i).attr("data-CurrentRankName"),
                CurrentAgencyCode: initEmployInfoWrap.eq(i).attr("data-CurrentAgencyCode"),
                CurrentPostCode: initEmployInfoWrap.eq(i).attr("data-CurrentPostCode"),
                CurrentRankCode: initEmployInfoWrap.eq(i).attr("data-CurrentRankCode"),

                Processor: initEmployInfoWrap.eq(i).find("td").eq(3).text(),

                AgencyNameForPost: initEmployInfoWrap.eq(i).attr("data-AgencyNameForPost"),
                CurrentAgencyCode: initEmployInfoWrap.eq(i).attr("data-AgencyCodeForPost")
            };
            EmploymentChange[i] = EmploymentObj;
        }

        for (var i = 0; i < EmploymentChange.length; i++) {
            EmployInfoList[i] = {
                ChangeDate: EmploymentChange[i].ChangeDate,
                OriginAgencyCode: EmploymentChange[i].OriginAgencyCode,
                OriginPostCode: EmploymentChange[i].OriginPostCode,
                OriginRankCode: EmploymentChange[i].OriginRankCode,
                CurrentAgencyCode: EmploymentChange[i].CurrentAgencyCode,
                CurrentPostCode: EmploymentChange[i].CurrentPostCode,
                CurrentRankCode: EmploymentChange[i].CurrentRankCode,
                Processor: EmploymentChange[i].Processor,
            };
        }

    }
    InitEmployInfo();


    /*添加异动信息*/
    $(".js_createEmploymentChange").click(function () {
        $("#EmploymentChange").find(".modal-title").text("添加异动信息");
        $("#EmploymentChangeID").val("");
    });
    /*保存异动信息*/
    $(".js_saveEmploymentChange").click(function () {
        if (!$("#EmploymentChangeForm").valid()) {
            return false;
        }

        var id;
        if (!$("#EmploymentChangeID").val()) {
            //添加异动信息
            id = EmploymentChange.length;
            var EmploymentObj;
            if (id == 0) {
                var CodeAll = $("#EmploymentChangeForm input[name=CurrentAgencyCode]").val();
                var CodeAlllist = CodeAll.split("/");
                var aCode = CodeAlllist[CodeAlllist.length - 1];
                EmploymentObj = {
                    ChangeDate: $("#ChangeDate").val(),

                    OriginAgencyName: "",
                    OriginPostName: "",
                    OriginRankName: "",
                    OriginAgencyCode: 0,
                    OriginPostCode: 0,
                    OriginRankCode: 0,

                    CurrentAgencyName: $("#EmploymentChangeForm input[name=AgencyNameForPost]").val(),
                    CurrentPostName: $("#EmploymentChangeForm select[name=CurrentPostCode] option:selected").text(),
                    CurrentRankName: $("#EmploymentChangeForm select[name=CurrentRankCode] option:selected").text(),
                    //CurrentAgencyCodeAll: $("input[name=CurrentAgencyCode]").val(),
                    CurrentAgencyCode: aCode,
                    CurrentPostCode: $("#EmploymentChangeForm select[name=CurrentPostCode] option:selected").val(),
                    CurrentRankCode: $("#EmploymentChangeForm select[name=CurrentRankCode] option:selected").val(),
                    Processor: $("#EmploymentChangeForm input[name=Processor]").val(),

                    AgencyNameForPost: $("#EmploymentChangeForm input[name=AgencyNameForPost]").val(),
                    CurrentAgencyCode: $("#EmploymentChangeForm input[name=CurrentAgencyCode]").val()
                };
            } else {
                var CodeAll = $("#EmploymentChangeForm input[name=CurrentAgencyCode]").val();
                var CodeAlllist = CodeAll.split("/");
                var aCode = CodeAlllist[CodeAlllist.length - 1];
                EmploymentObj = {
                    ChangeDate: $("#ChangeDate").val(),

                    OriginAgencyName: EmploymentChange[id - 1].CurrentAgencyName,
                    OriginPostName: EmploymentChange[id - 1].CurrentPostName,
                    OriginRankName: EmploymentChange[id - 1].CurrentRankName,
                    OriginAgencyCode: EmploymentChange[id - 1].CurrentAgencyCode,
                    OriginPostCode: EmploymentChange[id - 1].CurrentPostCode,
                    OriginRankCode: EmploymentChange[id - 1].CurrentRankCode,

                    CurrentAgencyName: $("#EmploymentChangeForm input[name=AgencyNameForPost]").val(),
                    CurrentPostName: $("#EmploymentChangeForm select[name=CurrentPostCode] option:selected").text(),
                    CurrentRankName: $("#EmploymentChangeForm select[name=CurrentRankCode] option:selected").text(),
                    //CurrentAgencyCodeAll: $("input[name=CurrentAgencyCode]").val(),
                    CurrentAgencyCode: aCode,
                    CurrentPostCode: $("#EmploymentChangeForm select[name=CurrentPostCode] option:selected").val(),
                    CurrentRankCode: $("#EmploymentChangeForm select[name=CurrentRankCode] option:selected").val(),
                    Processor: $("#EmploymentChangeForm input[name=Processor]").val(),

                    AgencyNameForPost: $("#EmploymentChangeForm input[name=AgencyNameForPost]").val(),
                    CurrentAgencyCode: $("#EmploymentChangeForm input[name=CurrentAgencyCode]").val()
                };
               
            }
            var Origin = "";
            if (EmploymentObj.OriginAgencyName != "") {
                Origin = EmploymentObj.OriginAgencyName + "/" + EmploymentObj.OriginPostName + "/" + EmploymentObj.OriginRankName;
            }
            var EmployInfoStr = "<tr><td>" + EmploymentObj.ChangeDate + "</td><td>" + Origin + "</td><td>" + EmploymentObj.CurrentAgencyName + "/" + EmploymentObj.CurrentPostName + "/" + EmploymentObj.CurrentRankName + "</td><td>" + EmploymentObj.Processor + "</td><td><a data-toggle='modal' title='编辑' href='#EmploymentChange' class='btn btn-xs btn-info js_editEmploymentChange'><i class='fa fa-edit'></i></a> <a data-toggle='tooltip' data-title='删除' href='javascript:;' class='btn btn-xs btn-danger js_deleteEmployInfo'><i class='fa fa-times'></i></a></td></tr>";

            $("#EmployTabel tbody").append(EmployInfoStr);
            EmploymentChange[id] = EmploymentObj;
            GetLastestEmployInfo();
        } else {
            var CodeAll = $("#EmploymentChangeForm input[name=CurrentAgencyCode]").val();
            var CodeAlllist = CodeAll.split("/");
            var aCode = CodeAlllist[CodeAlllist.length - 1];
            //编辑异动信息
            id = $("#EmploymentChangeID").val();
            EmploymentChange[id].ChangeDate = $("#ChangeDate").val();
            EmploymentChange[id].AgencyNameForPost = $("#EmploymentChangeForm input[name=AgencyNameForPost]").val();
            EmploymentChange[id].CurrentPostName = $("#EmploymentChangeForm select[name=CurrentPostCode] option:selected").text();
            EmploymentChange[id].CurrentRankName = $("#EmploymentChangeForm select[name=CurrentRankCode] option:selected").text();
            //EmploymentChange[id].CurrentAgencyCodeAll = $("input[name=CurrentAgencyCode]").val();
            EmploymentChange[id].CurrentAgencyCode = aCode;
            EmploymentChange[id].CurrentPostCode = $("#EmploymentChangeForm select[name=CurrentPostCode] option:selected").val();
            EmploymentChange[id].CurrentRankCode = $("#EmploymentChangeForm select[name=CurrentRankCode] option:selected").val();
            EmploymentChange[id].Processor = $("#EmploymentChangeForm input[name=Processor]").val();

            EmploymentChange[id].AgencyNameForPost = $("#EmploymentChangeForm input[name=AgencyNameForPost]").val();
            EmploymentChange[id].CurrentAgencyCode = $("#EmploymentChangeForm input[name=CurrentAgencyCode]").val();

            var targetTr = $("#EmployTabel tbody").find("tr").eq(id);
            targetTr.find("td").eq(0).text(EmploymentChange[id].ChangeDate);
            //targetTr.find("td").eq(1).text(EmploymentChange[id].OriginAgencyName + "/" + EmploymentChange[id].OriginPostName + "/" + EmploymentChange[id].OriginRankName);
            targetTr.find("td").eq(2).text(EmploymentChange[id].AgencyNameForPost + "/" + EmploymentChange[id].CurrentPostName + "/" + EmploymentChange[id].CurrentRankName);
            targetTr.find("td").eq(3).text(EmploymentChange[id].Processor);

            GetLastestEmployInfo();
        }

        //关闭弹出框
        $(".close_js_Msg").click();
        //编辑异动信息
        $(".js_editEmploymentChange").off().on("click", function () {
            $("#EmploymentChange").find(".modal-title").text("编辑异动信息");

            var id = $(this).parent().parent().index();
            $("#EmploymentChangeID").val(id);
            $("#ChangeDate").val(EmploymentChange[id].ChangeDate);
            $("input[name=CurrentAgencyName]").val(EmploymentChange[id].CurrentAgencyName);
            $("input[name='CurrentAgencyCode']").val(EmploymentChange[id].CurrentAgencyCode);

            $("input[name=AgencyNameForPost]").val(EmploymentChange[id].AgencyNameForPost);
            $("input[name='CurrentAgencyCode']").val(EmploymentChange[id].CurrentAgencyCode);

            //var AgencyCodeForPostLast = EmploymentChange[id].AgencyCodeForPost.split("/");
            //var acfpl = AgencyCodeForPostLast[AgencyCodeForPostLast.length - 1];
            //setPostCode("select[name='CurrentPostCode']", EmploymentChange[id].CurrentPostCode, acfpl);
            $("select[name='CurrentPostCode']").val(EmploymentChange[id].CurrentPostCode);

            $("select[name='CurrentRankCode']").val(EmploymentChange[id].CurrentRankCode);
            $("input[name='Processor']").val(EmploymentChange[id].Processor);
        });

        /*删除异动信息*/
        $(".js_deleteEmployInfo").off().on("click", function () {
            var id = $(this).parent().parent().index();
            EmploymentChange.splice(id, 1);
            $("#EmployTabel tbody").find("tr").eq(id).remove();
            GetLastestEmployInfo();
        });
    });

    //编辑异动信息
    $(".js_editEmploymentChange").off().on("click", function () {
        $("#EmploymentChange").find(".modal-title").text("编辑异动信息");
        var id = $(this).parent().parent().index();
        $("#EmploymentChangeID").val(id);
        $("#ChangeDate").val(EmploymentChange[id].ChangeDate);
        $("input[name=CurrentAgencyName]").val(EmploymentChange[id].CurrentAgencyName);
        $("input[name='CurrentAgencyCode']").val(EmploymentChange[id].CurrentAgencyCode);

        $("input[name=AgencyNameForPost]").val(EmploymentChange[id].AgencyNameForPost);
        $("input[name='CurrentAgencyCode']").val(EmploymentChange[id].CurrentAgencyCode);

        var CurrentAgencyCodeLast = EmploymentChange[id].CurrentAgencyCode.split("/");
        var acfpl = CurrentAgencyCodeLast[CurrentAgencyCodeLast.length - 1];
        setPostCode("select[name='CurrentPostCode']", EmploymentChange[id].CurrentPostCode, acfpl);

        $("select[name='CurrentRankCode']").val(EmploymentChange[id].CurrentRankCode);
        $("input[name='Processor']").val(EmploymentChange[id].Processor);
    });

    /*删除异动信息*/
    $(".js_deleteEmployInfo").off().on("click", function () {
        var id = $(this).parent().parent().index();
        EmploymentChange.splice(id, 1);
        $("#EmployTabel tbody").find("tr").eq(id).remove();
        GetLastestEmployInfo();
    });


    /*合同信息数组*/
    var ContractInfo = [];

    var InitContractInfo = function () {
        var initContractInfoWrap = $("#ContractInfoTabel tbody").find("tr");
        var initContractInfoNum = $("#ContractInfoTabel tbody").find("tr").length;
        for (var i = 0; i < initContractInfoNum; i++) {
            var initContractInfoObj = {
                ContractNo: initContractInfoWrap.eq(i).find("td").eq(0).text(),
                Name: initContractInfoWrap.eq(i).find("td").eq(1).text(),
                Company: initContractInfoWrap.eq(i).find("td").eq(2).text(),
                ContractStatus: initContractInfoWrap.eq(i).find("td").eq(3).text(),
                BeginDate: initContractInfoWrap.eq(i).find("td").eq(4).text(),
                EndDate: initContractInfoWrap.eq(i).find("td").eq(5).text()
            };

            ContractInfo[i] = initContractInfoObj;
        }
    }
    InitContractInfo();

    /*添加合同信息*/
    $(".js_createContractInfo").click(function () {
        $("#CreateContractInfo").find(".modal-title").text("添加合同信息");
        $("#ContractID").val("");
        $("#BeginDate").datepicker("setStartDate", "1900-1-1");
        $("#BeginDate").datepicker("setEndDate", "2900-1-1");
        $("#EndDate").datepicker("setStartDate", "1900-1-1");
        $("#EndDate").datepicker("setEndDate", "2900-1-1");
        $("input[name='ContractNo']").removeAttr("disabled");
    });

    $("#ContractInfoForm").validate({
        errorElement: "span",
        onblur: true,
        debug: true,
        ignore: "",
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
            error.addClass("text-danger");
        },
        rules: {
            ContractNo: {
                required: true,
                isNo: true
            },
            Name: {
                required: true,
                isName: true
            },
            Company: {
                required: true
            },
            ContractStatus: {
                required: true
            },
            BeginDate: {
                required: true
            },
            EndDate: {
                required: true
            }
        },
        messages: {
            ContractNo: {
                required: "合同编号为必填项",
                isNo: "请输入英文字母和数字"
            },
            Name: {
                required: "签约人姓名为必填项",
                isName: "请输入中文、英文或数字"
            },
            Company: {
                required: "签约公司为必选项"
            },
            ContractStatus: {
                required: "合同状态为必选项"
            },
            BeginDate: {
                required: "生效日期为必填项"
            },
            EndDate: {
                required: "到期日期为必填项"
            }
        }
    });
    /*保存合同信息*/
    $(".js_saveContractInfo").click(function () {
        if (!$("#ContractInfoForm").valid()) {
            return false;
        }

        var id;
        if (!$("#ContractID").val()) {
            //添加合同

            var isHasContractNo = false;
            var contractList = $("#ContractInfoTabel tbody").find("tr");
            if (contractList.length > 0) {
                for (var i = 0; i < contractList.length; i++) {
                    if (contractList.eq(i).find("td").eq(0).text() == $("input[name='ContractNo']").val()) {
                        isHasContractNo = isHasContractNo || true;
                    }
                }
            }
            if (isHasContractNo) {
                $("#CreateContractInfo").find(".js_Message").text("此合同编号已被使用！").show();
                return false;
            } else {
                var isH = false;
                $.ajax({
                    url: "http://oa.gyyx.cn/PersonalFile/IsEnabledContractNo/?contractno=" + $("input[name='ContractNo']").val() + "&id=0",
                    type: "GET",
                    dataType: "json",
                    async: false,
                    data: {
                        r: function () { return Math.random() }
                    },
                    success: function (d) {
                        if (d.Ret == 0) {
                            if (!d.Data) {
                                isH = true; return false;
                            }
                        } else {
                            alert(d.Message);
                            return false;
                        }
                    }
                });
                if (isH) {
                    $("#CreateContractInfo").find(".js_Message").text("此合同编号已被使用！").show();
                    return false;
                } else {
                    $("#CreateContractInfo").find(".js_Message").text("").hide();
                }
            }
            
            id = ContractInfo.length;
            var ContractInfoObj = {
                ContractNo: $("input[name='ContractNo']").val(),
                Name: $("input[name='Name']").val(),
                Company: $("select[name='Company']  option:selected").val(),
                ContractStatus: $("select[name='ContractStatus']  option:selected").val(),
                BeginDate: $("input[name='BeginDate']").val(),
                EndDate: $("input[name='EndDate']").val()
            };
            var ContractInfoStr = "<tr data-code='0'><td>" + ContractInfoObj.ContractNo + "</td><td>" + ContractInfoObj.Name + "</td><td>" + $("select[name='Company']  option:selected").text() + "</td><td>" + $("select[name='ContractStatus']  option:selected").text() + "</td><td>" + ContractInfoObj.BeginDate + "</td><td>" + ContractInfoObj.EndDate + "</td><td><a data-toggle='modal' title='编辑' href='#CreateContractInfo' class='btn btn-xs btn-info js_editContractInfo'><i class='fa fa-edit'></i></a> <a data-toggle='tooltip' data-title='删除' href='javascript:;' class='btn btn-xs btn-danger js_deleteContractInfo'><i class='fa fa-times'></i></a></td></tr>";

            $("#ContractInfoTabel tbody").append(ContractInfoStr);
            ContractInfo[id] = ContractInfoObj;
        } else {
            //编辑合同
            id = $("#ContractID").val();

            ContractInfo[id].ContractNo = $("input[name='ContractNo']").val();
            ContractInfo[id].Name = $("input[name='Name']").val();
            ContractInfo[id].Company = $("select[name='Company']  option:selected").val();
            ContractInfo[id].ContractStatus = $("select[name='ContractStatus']  option:selected").val();
            ContractInfo[id].BeginDate = $("input[name='BeginDate']").val();
            ContractInfo[id].EndDate = $("input[name='EndDate']").val();

            var targetTr = $("#ContractInfoTabel tbody").find("tr").eq(id);
            targetTr.find("td").eq(0).text(ContractInfo[id].ContractNo);
            targetTr.find("td").eq(1).text(ContractInfo[id].Name);
            targetTr.find("td").eq(2).text($("select[name='Company']  option:selected").text());
            targetTr.find("td").eq(3).text($("select[name='ContractStatus']  option:selected").text());
            targetTr.find("td").eq(4).text(ContractInfo[id].BeginDate);
            targetTr.find("td").eq(5).text(ContractInfo[id].EndDate);

        }

        //关闭弹出框
        $(".close_js_Msg").click();
        //编辑合同信息
        $(".js_editContractInfo").off().on("click", function () {
            $("#CreateContractInfo").find(".modal-title").text("编辑合同信息");
            $("#BeginDate").datepicker("setStartDate", "1900-1-1");
            $("#BeginDate").datepicker("setEndDate", "2900-1-1");
            $("#EndDate").datepicker("setStartDate", "1900-1-1");
            $("#EndDate").datepicker("setEndDate", "2900-1-1");
            $("input[name='ContractNo']").attr("disabled", "disabled");
            var id = $(this).parent().parent().index();
            $("#ContractID").val(id);
            $("input[name='ContractNo']").val(ContractInfo[id].ContractNo);
            $("input[name='Name']").val(ContractInfo[id].Name);
            $("select[name='Company']").val(ContractInfo[id].Company);
            $("select[name='ContractStatus']").val(ContractInfo[id].ContractStatus);
            $("input[name='BeginDate']").val(ContractInfo[id].BeginDate);
            $("input[name='EndDate']").val(ContractInfo[id].EndDate);
        });

        /*删除合同信息*/
        $(".js_deleteContractInfo").off().on("click", function () {
            var id = $(this).parent().parent().index();
            ContractInfo.splice(id, 1);
            $("#ContractInfoTabel tbody").find("tr").eq(id).remove();
        });
    });
    //编辑合同信息
    $(".js_editContractInfo").off().on("click", function () {
        $("#CreateContractInfo").find(".modal-title").text("编辑合同信息");
        $("#BeginDate").datepicker("setStartDate", "1900-1-1");
        $("#BeginDate").datepicker("setEndDate", "2900-1-1");
        $("#EndDate").datepicker("setStartDate", "1900-1-1");
        $("#EndDate").datepicker("setEndDate", "2900-1-1");
        $("input[name='ContractNo']").attr("disabled", "disabled");
        var id = $(this).parent().parent().index();
        $("#ContractID").val(id);
        $("input[name='ContractNo']").val(ContractInfo[id].ContractNo);
        $("input[name='Name']").val(ContractInfo[id].Name);
        $("select[name='Company']").val(ContractInfo[id].Company);
        $("select[name='ContractStatus']").val(ContractInfo[id].ContractStatus);
        $("input[name='BeginDate']").val(ContractInfo[id].BeginDate);
        $("input[name='EndDate']").val(ContractInfo[id].EndDate);
    });

    /*删除合同信息*/
    $(".js_deleteContractInfo").off().on("click", function () {
        var id = $(this).parent().parent().index();
        ContractInfo.splice(id, 1);
        $("#ContractInfoTabel tbody").find("tr").eq(id).remove();
    });


    /*创建档案第二步验证*/
    $("#CreateSecondStep").validate({
        errorElement: "span",
        onblur: true,
        onkeyup: false,
        debug: true,
        ignore: "",
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
            if (element.attr("name") == "AgencyCode") {
                error.addClass("text-danger parsley-error").css({"float":"left","marginTop":"9px","marginLeft":"5px"});
            } else if (element.attr("name") == "CurrentRankCode" || element.attr("name") == "PostCodeD") {
                error.addClass("text-danger valid_error  parsley-error").css({"left":"155px","width":"115px"});
            } else {
                error.addClass("text-danger valid_error parsley-error");
            }

            element.click(function () {
                $(this).siblings().remove();
                $(this).focus();
            });

            /*创建档案 获取错误提示输入框焦点*/
            $("span.valid_error").on("click", function () {
                var $this = $(this);
                $this.siblings().focus();
                $this.remove();
            });
        },
        rules: {
            CompanyName: {
                required: true
            },
            AgencyCode: {
                required: true
            },
            CurrentRankCode: {
                required: true,
                select: true
            },
            PostCodeD: {
                required: true,
                select: true
            },
            PreConfirmationDate: {
                //required: true
            },
            RealConfirmationDate: {
               // required: true
            },
            EntryPostStatus: {
                required: true
            },
            EntryChannel: {
                //required: true
            },
            CurrentPostStatus: {
                required: true
            },
            HireDate: {
                required: true
            }
        },
        messages: {
            CompanyName: {
                required: "公司名称为必选项"
            },
            AgencyCode: {
                required: "所属机构为必选项"
            },
            CurrentRankCode: {
                required: "岗位级别为必选项",
                select: "岗位级别为必选项"
            },
            PostCodeD: {
                required: "岗位为必选项",
                select: "岗位为必选项"
            },
            PreConfirmationDate: {
                //required: "约定转正日期为必填项"
            },
            RealConfirmationDate: {
                //required: "实际转正日期为必填项"
            },
            EntryPostStatus: {
                //required: "入职岗位状态为必选项"
            },
            EntryChannel: {
                required: "入职渠道为必选项"
            },
            CurrentPostStatus: {
                required: "当前岗位状态为必选项"
            },
            HireDate: {
                required: "入职日期为必填项"
            }
        }
    });
    $(".js_saveSecondStep").click(function () {

        if (!$("#CreateSecondStep").valid()) {
            return false;
        }

        var iscontractExist = false;
        var contractList = $("#ContractInfoTabel tbody").find("tr");
        if (contractList.length > 0) {
            for (var i = 0; i < contractList.length; i++) {
                $.ajax({
                    url: "http://oa.gyyx.cn/PersonalFile/IsEnabledContractNo/?contractno=" + contractList.eq(i).find("td").eq(0).text() + "&id=" + contractList.eq(i).attr("data-code"),
                    type: "GET",
                    dataType: "json",
                    async: false,
                    data: {
                        r: function () { return Math.random() }
                    },
                    success: function (d) {
                        if (d.Ret == 0) {
                            if (!d.Data) {
                                alert("合同编号" + contractList.eq(i).find("td").eq(0).text() + "已被使用！");
                                iscontractExist = iscontractExist || true;
                                return false;
                            }
                        } else {
                            alert(d.Message);
                            return false;
                        }
                    }
                });
            }
        }
        if (iscontractExist) {
            return false;
        }
        //有异动信息和合同信息
        var data = {
            StaffCode: $("input[name=StaffCode]").val(),
            Employment: {
                CompanyName: $("select[name=CompanyName] option:selected").val(),
                AgencyCode: ($("#AgencyCode").val() == "") ? 0 : $("#AgencyCode").val(),
                PostCode: ($("#PostCodeD option:selected").val() == -1) ? 0 : $("#PostCodeD option:selected").val(),
                RankCode: ($("#RankCodeD select[name='CurrentRankCode'] option:selected").val() == -1) ? 0 : $("#RankCodeD select[name='CurrentRankCode'] option:selected").val(),
                HireDate: $("input[name=HireDate]").val(),
                EntryChannel: $("select[name=EntryChannel] option:selected").val(),
                EntryPostStatus: $("select[name=EntryPostStatus] option:selected").val(),
                CurrentPostStatus: $("select[name=CurrentPostStatus] option:selected").val(),
                PreConfirmationDate: $("input[name=PreConfirmationDate]").val() == "" ? "0001-01-01" : $("input[name=PreConfirmationDate]").val(),
                RealConfirmationDate: $("input[name=RealConfirmationDate]").val() == "" ? "0001-01-01" : $("input[name=RealConfirmationDate]").val(),
                SecondPostName: $("input[name=SecondPostName]").val()
            },
            StaffContracts: ContractInfo,
            EmploymentChangeLogs: EmployInfoList
        }
        $.ajax({
            url: 'http://oa.gyyx.cn/PersonalFile/EditorEmploymentInfo',
            type: "post",
            //async: true,
            data: JSON.stringify(data),
            dataType: "json",
            success: function (d) {
                if (d.Ret == 0) {
                    alert(d.Message);
                    window.location.href = window.location.href;
                } else {
                    alert(d.Message);
                }
            }
        });
    });

});
