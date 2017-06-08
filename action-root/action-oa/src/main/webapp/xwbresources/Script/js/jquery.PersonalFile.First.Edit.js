/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：陈巧玲
 * 联系方式：chenqiaoling@gyyx.cn
 * 创建时间： 2014/7/29 
 * 版本号：v1.0

 * 内容： 档案管理页面 编辑基础信息

 * -------------------------------------------------------------------------*/
$(function () {

    /*上传图片*/
    UPLOADURL = "http://up.gyyx.cn/File/WebSiteSaveToReal.ashx?group=oa1";
    FLASHURL = "http://up.gyyx.cn/flash/swfupload_f9.swf";
    FILESIZE = "10240";
    FILETYPE = "*.jpg;*.gif";
    TYPESDESCRIPTION = "Image";
    UPLOADLIMIT = "0";
    QUEUELIMIT = 1;
    DEBUG = false;
    UPLOADTARGET = "divFileProgressContainer";
    SAVEELEMENT = "FileCode";
    Initialize();

    //绑定时间控件
    var dd = new Date();
    var nowY = dd.getFullYear();
    var nowM = dd.getMonth();
    var nowD = dd.getDate();
    $("#Birthdate").datepicker({
        autoclose: true,
        startView: 4,
        format: "yyyy-m-d",
        minView: 2,
        endDate: new Date(nowY, nowM, nowD)
    }).on("changeDate", function (ev) {
        $("#Birthdate").val($(this).val()).blur();
    });
    $("#Begindate").datepicker({
        autoclose: true,
        startView: 4,
        format: "yyyy-m-d",
        minView: 2
    }).on("changeDate", function (ev) {
        var startD = $(this).val();
        $("#Enddate").datepicker("setStartDate", startD);
        $("#Begindate").val($(this).val()).blur();
    });
    $("#Enddate").datepicker({
        autoclose: true,
        startView: 4,
        format: "yyyy-m",
        minView: 3,
        onRender: function (date) {
            return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
        }
    }).on("changeDate", function () {
        var endD = $(this).val();
        $("#Begindate").datepicker("setEndDate", endD);
        $("#Enddate").val($(this).val()).blur();
    });

    //籍贯
    initShengShi($("#Birthplace")[0], $("#BirthplaceD").val());
    setShengShiSelected($("#Birthplace")[0],$("#BirthplaceD").val());

    /*添加学历信息数组*/
    var createEduInfo = [];
    var IsHighestLearningPeriod = function () {
        var hasHighestLearningPeriod = false;
        var highestindex;
        for (var i = 0; i < createEduInfo.length; i++) {
            if (createEduInfo[i].IsHighest == "true") {
                //是最高学历
                highestindex = i;
                hasHighestLearningPeriod = hasHighestLearningPeriod || true;
            }
        }
        if (hasHighestLearningPeriod) {
            //如果已有最高学历，则无法再次选择最高学历
            $("select[name='IsHighest'] option[value='true']").attr("disabled", "disabled");
            $("#HighestLearningPeriod").text($("#EduInfoTabel tbody").find("tr").eq(highestindex).find("td").eq(3).text());
            $("#Diplomas").val(createEduInfo[highestindex].LearningPeriod);
        } else {
            $("select[name='IsHighest'] option[value='true']").removeAttr("disabled");
            $("#HighestLearningPeriod").text("");
            $("#Diplomas").val(-1);
        }
    }
    var InitEduInfo = function () {
        var initEduInfoWrap = $("#EduInfoTabel tbody").find("tr");
        var initEduInfoNum = $("#EduInfoTabel tbody").find("tr").length;
        for (var i = 0; i < initEduInfoNum; i++) {
            var p = initEduInfoWrap.eq(i).find("td").eq(3).text();
            var lperiod;
            switch (p) {
                case "小学":
                    lperiod = 0; break;
                case "初中":
                    lperiod = 1; break;
                case "中专":
                    lperiod = 2; break;
                case "高中":
                    lperiod = 3; break;
                case "职高":
                    lperiod = 4; break;
                case "专科":
                    lperiod = 5; break;
                case "本科":
                    lperiod = 6; break;
                case "硕士":
                    lperiod = 7; break;
                case "博士":
                    lperiod = 8; break;
                default:
                    lperiod = -1;
            }
            var highest = (initEduInfoWrap.eq(i).find("td").eq(6).text() == "是") ? "true" : "false";
            var initEduInfoObj = {
                BeginDate: initEduInfoWrap.eq(i).find("td").eq(0).text(),
                EndDate: initEduInfoWrap.eq(i).find("td").eq(1).text(),
                Institutions: initEduInfoWrap.eq(i).find("td").eq(2).text(),
                LearningPeriod: lperiod,
                Profession: initEduInfoWrap.eq(i).find("td").eq(4).text(),
                HonourInfo: initEduInfoWrap.eq(i).find("td").eq(5).text(),
                IsHighest: highest
            };
            createEduInfo[i] = initEduInfoObj;
        }
        IsHighestLearningPeriod();
    }
    InitEduInfo();

    /*添加学历信息*/
    $(".js_createEduInfo").click(function () {
        $("#CreadeEduInfo").find(".modal-title").text("添加学历信息");
        $("#EduInfoID").val("");
        $("#Begindate").datepicker("setStartDate", "1900-1-1");
        $("#Begindate").datepicker("setEndDate", "2900-1-1");
        $("#Enddate").datepicker("setStartDate", "1900-1-1");
        $("#Enddate").datepicker("setEndDate", "2900-1-1");
    });

    $("#EduInfo").validate({
        errorElement: "span",
        onblur: true,
        debug: true,
        ignore: "",
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
            error.addClass("text-danger");
        },
        rules: {           
            Enddate: {
                required: true
            },
            Institutions: {
                required: true,
                isName: true
            },
            Profession: {
                isName: true
            },
            HonourInfo: {                
                Html: true,
                maxlength: 300
            }
        },
        messages: {            
            Enddate: {
                required: "毕业时间为必填项"
            },
            Institutions: {
                required: "学校名称为必填项",
                isName: "请输入中文、英文或数字"
            },
            Profession: {
                isName: "请输入中文、英文或数字"
            },
            HonourInfo: {               
                Html: "请不要输入<或>字符",
                maxlength: "所获荣誉最多输入300字符"
            }
        }
    });
    
    /*保存学历信息*/
    $(".js_saveEduInfo").click(function () {
        if (!$("#EduInfo").valid()) {
            return false;
        }
        var id;
        if (!$("#EduInfoID").val()) {
            //添加学历
            id = createEduInfo.length;
            if ($("#Begindate").val() == "") {
                var eduInfoObj = {
                    BeginDate: "0001-01-01",
                    EndDate: $("#Enddate").val(),
                    Institutions: $("input[name='Institutions']").val(),
                    LearningPeriod: $("select[name='LearningPeriod']  option:selected").val(),
                    Profession: $("input[name='Profession']").val(),
                    HonourInfo: $("textarea[name='HonourInfo']").val(),
                    IsHighest: $("select[name='IsHighest'] option:selected").val()
                };
                var eduInfoStr = "<tr><td><span class='btime js_hid'>0001-01-01</span></td><td>" + $("#Enddate").val() + "</td><td>" + $("input[name='Institutions']").val() + "</td><td>" + $("select[name='LearningPeriod']  option:selected").text() + "</td><td>" + $("input[name='Profession']").val() + "</td><td>" + $("textarea[name='HonourInfo']").val() + "</td><td>" + $("select[name='IsHighest'] option:selected").text() + "</td><td><a data-toggle='modal' title='编辑' href='#CreadeEduInfo' class='btn btn-xs btn-info js_editEduInfo'><i class='fa fa-edit'></i></a> <a data-toggle='tooltip' data-title='删除' href='javascript:;' class='btn btn-xs btn-danger js_deleteEduInfo'><i class='fa fa-times'></i></a></td></tr>";
            } else {
                var eduInfoObj = {
                    BeginDate: $("#Begindate").val(),
                    EndDate: $("#Enddate").val(),
                    Institutions: $("input[name='Institutions']").val(),
                    LearningPeriod: $("select[name='LearningPeriod']  option:selected").val(),
                    Profession: $("input[name='Profession']").val(),
                    HonourInfo: $("textarea[name='HonourInfo']").val(),
                    IsHighest: $("select[name='IsHighest'] option:selected").val()
                };
                var eduInfoStr = "<tr><td><span class='btime'>" + $("#Begindate").val() + "</span></td><td>" + $("#Enddate").val() + "</td><td>" + $("input[name='Institutions']").val() + "</td><td>" + $("select[name='LearningPeriod']  option:selected").text() + "</td><td>" + $("input[name='Profession']").val() + "</td><td>" + $("textarea[name='HonourInfo']").val() + "</td><td>" + $("select[name='IsHighest'] option:selected").text() + "</td><td><a data-toggle='modal' title='编辑' href='#CreadeEduInfo' class='btn btn-xs btn-info js_editEduInfo'><i class='fa fa-edit'></i></a> <a data-toggle='tooltip' data-title='删除' href='javascript:;' class='btn btn-xs btn-danger js_deleteEduInfo'><i class='fa fa-times'></i></a></td></tr>";
            }
           

            $("#EduInfoTabel tbody").append(eduInfoStr);
            createEduInfo[id] = eduInfoObj;
            IsHighestLearningPeriod();
        } else {
            //alert(8888)
            //编辑学历
            id = $("#EduInfoID").val();

            createEduInfo[id].BeginDate = $("#Begindate").val() == "" ? "0001-01-01" : $("#Begindate").val();
            createEduInfo[id].EndDate = $("#Enddate").val();
            createEduInfo[id].Institutions = $("input[name='Institutions']").val();
            createEduInfo[id].LearningPeriod = $("select[name='LearningPeriod']  option:selected").val();
            createEduInfo[id].Profession = $("input[name='Profession']").val();
            createEduInfo[id].HonourInfo = $("textarea[name='HonourInfo']").val();
            createEduInfo[id].IsHighest = $("select[name='IsHighest'] option:selected").val();

            var targetTr = $("#EduInfoTabel tbody").find("tr").eq(id);
            if ($("#Begindate").val() == "") {
                targetTr.find("td").eq(0).find("span").text("0001-01-01");
            } else {
                targetTr.find("td").eq(0).find("span").text($("#Begindate").val());
                var timesdata = $(".btime");
                for (var i = 0; i < timesdata.length; i++) {
                    if (timesdata.eq(i).text() != "0001-01-01") {
                        timesdata.eq(i).removeClass("js_hid");
                    }
                }
            }
            targetTr.find("td").eq(1).text($("#Enddate").val());
            targetTr.find("td").eq(2).text($("input[name='Institutions']").val());
            targetTr.find("td").eq(3).text($("select[name='LearningPeriod']  option:selected").text());
            targetTr.find("td").eq(4).text($("input[name='Profession']").val());
            targetTr.find("td").eq(5).text($("textarea[name='HonourInfo']").val());
            targetTr.find("td").eq(6).text($("select[name='IsHighest'] option:selected").text());

            IsHighestLearningPeriod();
        }

        //关闭弹出框
        $(".close_js_Msg").click();

        //编辑学历信息
        $(".js_editEduInfo").off().on("click", function () {
            $("#CreadeEduInfo").find(".modal-title").text("编辑学历信息");
            $("#Begindate").datepicker("setStartDate", "1900-1-1");
            $("#Begindate").datepicker("setEndDate", "2900-1-1");
            $("#Enddate").datepicker("setStartDate", "1900-1-1");
            $("#Enddate").datepicker("setEndDate", "2900-1-1");
            var id = $(this).parent().parent().index();
            $("#EduInfoID").val(id);
            if (createEduInfo[id].BeginDate == "0001-01-01") {
                $("#Begindate").val("");
            } else {
                $("#Begindate").val(createEduInfo[id].BeginDate);
            }
            $("#Enddate").val(createEduInfo[id].EndDate);
            $("input[name='Institutions']").val(createEduInfo[id].Institutions);
            $("select[name='LearningPeriod']").val(createEduInfo[id].LearningPeriod);
            $("input[name='Profession']").val(createEduInfo[id].Profession);
            $("textarea[name='HonourInfo']").val(createEduInfo[id].HonourInfo);
            $("select[name='IsHighest']").val(createEduInfo[id].IsHighest);
        });

        /*删除学历信息*/
        $(".js_deleteEduInfo").off().on("click", function () {
            var id = $(this).parent().parent().index();
            createEduInfo.splice(id, 1);
            $("#EduInfoTabel tbody").find("tr").eq(id).remove();
            IsHighestLearningPeriod();
        });
    });
    //编辑学历信息
    $(".js_editEduInfo").off().on("click", function () {
        $("#CreadeEduInfo").find(".modal-title").text("编辑学历信息");
        $("#Begindate").datepicker("setStartDate", "1900-1-1");
        $("#Begindate").datepicker("setEndDate", "2900-1-1");
        $("#Enddate").datepicker("setStartDate", "1900-1-1");
        $("#Enddate").datepicker("setEndDate", "2900-1-1");
        var id = $(this).parent().parent().index();
        $("#EduInfoID").val(id);
        if (createEduInfo[id].BeginDate == "0001-01-01") {
            $("#Begindate").val("");
        } else {
            $("#Begindate").val(createEduInfo[id].BeginDate);
        }
        $("#Enddate").val(createEduInfo[id].EndDate);
        $("input[name='Institutions']").val(createEduInfo[id].Institutions);
        $("select[name='LearningPeriod']").val(createEduInfo[id].LearningPeriod);
        $("input[name='Profession']").val(createEduInfo[id].Profession);
        $("textarea[name='HonourInfo']").val(createEduInfo[id].HonourInfo);
        $("select[name='IsHighest']").val(createEduInfo[id].IsHighest);
    });

    /*删除学历信息*/
    $(".js_deleteEduInfo").off().on("click", function () {
        var id = $(this).parent().parent().index();
        createEduInfo.splice(id, 1);
        $("#EduInfoTabel tbody").find("tr").eq(id).remove();
        IsHighestLearningPeriod();
    });

    /*注册验证*/
    /* 移动电话 */
    jQuery.validator.addMethod("mobile", function (value, element, param) {
        var mobiletip = /^[1][0-9]{10}$/;
        return this.optional(element) || (mobiletip.test(value));
    });
    /* 身份证*/
    jQuery.validator.addMethod("isCardNo", function (value, element, param) {
        var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        return this.optional(element) || (reg.test(value));
    });
    /*固定电话*/
    jQuery.validator.addMethod("telephone", function (value, element, param) {
        var reg = /^0?\d{2,3}\-\d{7,8}$/;
        return this.optional(element) || (reg.test(value));
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
    /*账号*/
    jQuery.validator.addMethod("isAccount", function (value, element, param) {
        var reg = /^[a-z]+[a-z][0-9]*$/;
        return this.optional(element) || (reg.test(value));
    });
    /*创建档案第一步验证*/
    $("#CreateFirstStep").validate({
        errorElement: "span",
        onblur: true,
        onkeyup: false,
        debug: true,
        ignore: "",
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
            if (element.attr("name") == "Description") {
                error.addClass("text-danger parsley-error");
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
            FileNo: {
                required: true,
                isNo: true
            },
            RealName: {
                required: true,
                isName: true
            },
            AnotherName: {
                isAccount: true
            },
            Gender: {
                required: true
            },
            Nation: {
                required: true,
                isName: true
            },
            HouseholdRegisterType: {
                required: true
            },
            MaritalStatus: {
                required: true
            },
            IdcardNo: {                
                isCardNo: true
            },
            //Birthplace: {
            //    required: true
            //},
            RtxAccount: {
                isName: true
            },
            Email: {
                email: true
            },
            OfficeEmail: {
                email: true
            },
            MobilephoneNo: {                
                mobile: true
            },
            HouseholdRegister: {
                required: true,
                Html: true
            },
            FamilyAddress: {                
                Html: true
            },
            ContactName: {
               // required: true,
                isName: true
            },
            ContactRelations: {
               // required: true,
                isName: true
            },
            Mobilephone: {
               // required: true,
                mobile: true
            },
            Telephone: {
                //required: true,
                telephone: true
            },
            Address: {               
                Html: true
            },
            Description: {               
                maxlength: 500,
                Html: true
            }
        },
        messages: {
            FileNo: {
                required: "档案编号为必填项",
                isNo: "请输入英文字母和数字"
            },
            RealName: {
                required: "员工姓名为必填项",
                isName: "请输入中文、英文或数字"
            },
            AnotherName: {
                isAccount: "请输入字母开头小写字母或数字"
            },
            Gender: {
                required: "员工性别为必选项"
            },
            Nation: {
                required: "员工民族为必填项",
                isName: "请输入中文、英文或数字"
            },
            HouseholdRegisterType: {
                required: "户口性质为必选项"
            },
            MaritalStatus: {
                required: "婚姻状态为必选项"
            },
            IdcardNo: {               
                isCardNo: "请正确输入身份证号码"
            },
            //Birthplace: {
            //    required: "员工籍贯为必选项"
            //},
            RtxAccount: {
                isName: "请正确输入RTX账号"
            },
            Email: {
                email: "请正确输入个人邮箱"
            },
            OfficeEmail: {
                email: "请正确输入办公邮箱"
            },
            MobilephoneNo: {
                mobile: "请正确输入移动电话"
            },
            HouseholdRegister: {
                required: "户口所在地为必填项",
                Html: "请不要输入<或>字符"
            },
            FamilyAddress: {                
                Html: "请不要输入<或>字符"
            },
            ContactName: {
                //required: "联系人姓名为必填项",
                isName: "请输入中文、英文或数字"
            },
            ContactRelations: {
                //required: "联系人关系为必填项",
                isName: "请输入中文、英文或数字"
            },
            Mobilephone: {
                //required: "联系人移动电话为必填项",
                mobile: "请正确输入联系人移动电话"
            },
            Telephone: {
                //required: "联系人固定电话为必填项",
                telephone: "请正确输入联系人固定电话"
            },
            Address: {               
                Html: "请不要输入<或>字符"
            },
            Description: {               
                maxlength: "工作经历最多输入500字符",
                Html: "请不要输入<或>字符"
            }
        }
    });
    $(".js_saveFirstStep").click(function () {
        if ($("#CreateFirstStep").valid()) {
            var telnone = $("input[name='Telephone']").val();
            var phonenone = $("input[name='Mobilephone']").val();
            var lxname = $("input[name='ContactName']").val();
            var lxbx = $("input[name='ContactRelations']").val();
            //if ($("input[name='Birthdate']").val() == "") {
            //    $("input[name='Birthdate']").val("0001-01-01");
            //}
            function subinfo() {
                //有学历信息
                var data = {
                    StaffCode: $("input[name='StaffCode']").val(),
                    StaffInfo: {
                        FileNo: $("input[name=FileNo]").val(),
                        RealName: $("input[name=RealName]").val(),
                        Email: $("input[name=Email]").val(),
                        AnotherName: $("input[name=AnotherName]").val(),
                        OfficeEmail: $("input[name=OfficeEmail]").val(),
                        Diplomas: $("input[name=Diplomas]").val(),
                        MobilePhoneNo: $("input[name=MobilephoneNo]").val(),
                        RtxAccount: $("input[name=RtxAccount]").val(),
                        Gender: $("select[name=Gender] option:selected").val(),
                        BirthDate: $("input[name=Birthdate]").val() == "" ? "0001-01-01" : $("input[name=Birthdate]").val(),
                        BirthPlace: $("select[name=Birthplace] option:selected").val(),
                        HouseholdRegisterType: $("select[name=HouseholdRegisterType] option:selected").val(),
                        HouseholdRegister: $("input[name=HouseholdRegister]").val(),
                        FamilyAddress: $("input[name=FamilyAddress]").val(),
                        Nation: $("input[name=Nation]").val(),
                        IdcardNo: $("input[name=IdcardNo]").val(),
                        MaritalStatus: $("select[name=MaritalStatus] option:selected").val(),
                        PhotoUrl: $("#FileCode").val()
                    },
                    LearningExperiences: createEduInfo,
                    StaffContacts: [
                        {
                            ContactName: $("input[name=ContactName]").val(),
                            ContactRelations: $("input[name=ContactRelations]").val(),
                            Mobilephone: $("input[name=Mobilephone]").val(),
                            Telephone: $("input[name=Telephone]").val(),
                            Address: $("input[name=Address]").val()
                        }
                    ],
                    WorkExperience: {
                        Description: $("textarea[name=Description]").val()
                    }
                }
                $.ajax({
                    url: 'http://oa.gyyx.cn/PersonalFile/EditorStaffBasicInfo',
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
            };
            if (lxname == "" && lxbx == "" && telnone == "" && phonenone == "") {
                subinfo();
            } else {
                if (lxname == "") {
                    $(".js_lxname").append('<span for="Mobilephone" generated="true" class="error text-danger valid_error parsley-error js_errorclose">联系人姓名为必填项</span>');
                } else if (lxbx == "") {
                    $(".js_lxbx").append('<span for="Mobilephone" generated="true" class="error text-danger valid_error parsley-error js_errorclose">联系人关系为必填项</span>');
                } else if (telnone == "" && phonenone == "") {
                    $(".js_telephones").append('<span for="Mobilephone" generated="true" class="error text-danger valid_error parsley-error js_errorclose">请正确输入联系人固定电话</span>');
                    $(".js_mobilephones").append('<span for="Mobilephone" generated="true" class="error text-danger valid_error parsley-error js_errorclose">请正确输入联系人移动电话</span>');
                } else if (lxname != "" && lxbx != "" && telnone != "") {
                    subinfo();
                } else if (lxname != "" && lxbx != "" && phonenone != "") {
                    subinfo();
                }
            }
        }
        $(".js_errorclose").click(function () {
            $(this).remove();
        });
    });
});
