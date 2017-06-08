/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：陈巧玲
 * 联系方式：chenqiaoling@gyyx.cn
 * 创建时间： 2014/8/11
 * 版本号：v1.0
 * 修改：李根 at 2014-12-1
 * 内容： 档案管理页面 列表页

 * -------------------------------------------------------------------------*/
$(function () {

    /*中英文数字*/
    jQuery.validator.addMethod("isName", function (value, element, param) {
        var reg = /[^\u4E00-\u9FA5^a-z^A-Z^0-9]/g;
        return this.optional(element) || (!reg.test(value));
    });

    //机构
    //$(".js_agencySelect").agencySelect({
    //    hidCode: $("#PersonalFileList").find("input[name='AgencyCode']"), /*隐藏inputID*/
    //    hidName: $("#PersonalFileList").find("input[name='AgencyName']")/*hidden*/
    //});
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
            url: "/post/agencyposts/" + "?r=" + Math.random(),
            type: "GET",
            dataType: "json",
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
    //搜索按钮
    $(".js_searchPersonalFile").click(function () {
        $("input[name='PostCode']").val($("select[name='PostCodeD'] option:selected").val());

        var reg = /[^\u4E00-\u9FA5^a-z^A-Z^0-9]/g;
        var staffname = $("#PersonalFileList").find("input[name='StaffName']");
        if(staffname.val() != ""){
            if (reg.test(staffname.val())) {
                if (staffname.parent().find(".text-danger").length > 0) {
                    staffname.parent().find(".text-danger").show();
                } else {
                    staffname.parent().append("<span class='text-danger'>请输入中文、英文或数字</span>");
                }
                return false;
            } else {
                if (staffname.parent().find(".text-danger").length > 0) {
                    staffname.parent().find(".text-danger").hide();
                }
            }
        }

    });
    //离职按钮
    $(".js_delete").click(function () {
        if (confirm('确认要更新为离职状态吗？')) {
            $.ajax({
                url: "/personalfile/disabled/" + "?staffCode="+ $(this).parents("tr").attr("data-id") +"&r=" + Math.random(),
                type: "GET",
                dataType: "json",
                success: function (d) {
                    if (d.Ret == 0) {
                        alert(d.Message);
                        window.location.reload();
                    } else {
                        alert(d.Message);
                    }
                }
            });
        }
    });
    //获取机构列表
    $(".js_agencySelect").agencySelect({
        hidCode: $("#BatchConfirmation").find("input[name='BCAgencyCode']"), /*隐藏inputID*/
        hidName: $("#BatchConfirmation").find("input[name='BCAgencyName']") /*hidden*/
    });
    //搜索岗位分类
    $(".js_searchPeop").click(function () {
        var createPostId = $("#BatchConfirmation");
        var agencyCode = createPostId.find("input[name='BCAgencyCode']").val();
        var staffName = createPostId.find("input[name='BCName']").val();
        noadd(agencyCode, staffName);
    });
    $(".js_AgencyLists").hide();
    //未转正人员
    function noadd(agencyCode, staffName) {
        $.ajax({
            url: "/StaffInfo/GetNotRegularStaffs",
            type: "get",
            dataType: "json",
            data: {
                r: Math.random(),
                agencyCode: agencyCode,
                staffName: staffName
            },
            success: function (d) {
                if (d.Ret == 0) {
                    var vmlist = '<div class="line pull-in"></div><span style="width: 100%; display: block; padding: 3px 0 0 0;font-weight: bold;">未转正人员:</span>';
                        for (var i = 0; i < d.Data.length; i++) {
                            vmlist += '<span class="label label-info js_addpeopcode" style="margin:5px 0 0 2px;padding:6px 8px;cursor: pointer;font-size:1em;float:left;" data-peopcode="' + d.Data[i].Code + '">' + d.Data[i].RealName + '</span>';
                        }
                        $(".js_AgencyList").html(vmlist);
                        $(".js_AgencyLists").show();
                    //添加人员
                        $(".js_addpeopcode").on("click", function () {
                            //验证人员重复
                            var othername = $(this).attr("data-peopcode");
                            var onames = $(".js_codeed");
                            if (othername == "" || othername == "请输入别名") {
                                alert("请输入别名");
                            } else {
                                var reg1 = new RegExp("^" + othername + "$");
                                var num1 = 0;
                                for (var i = 0; i < onames.length; i++) {
                                    if (reg1.test(onames.eq(i).attr("data-peopcode"))) {
                                        num1++;
                                    }
                                }
                                if (num1 >= 1) {
                                    alert("人员重复！");
                                } else {
                                    var addpeoed = '<span class="label label-success js_codeed" style="margin:5px 0 0 2px;padding:6px 8px;cursor: pointer;font-size:1em;float:left;" data-peopcode="' + $(this).attr("data-peopcode") + '">' + $(this).text() + '</span>';
                                    $(".js_AgencyLists").append(addpeoed);
                                    $(this).remove();
                                }
                            };
                            //移除已添加人员
                            $(".js_codeed").on("click", function () {
                                $(this).remove();
                            });
                        });
                }
            }
        });
    };

    //提交添加
    $(".js_gerpeoples").on("click", function () {
        var getp = $(".js_codeed");
        var staffIds = "";
        var cut = [];
        for (var i = 0; i < getp.length; i++) {
            cut.push(getp.eq(i).attr("data-peopcode"));
        }
        staffIds = cut.join(",");
        $.ajax({
            url: "/StaffInfo/MakeRegular/",
            type: "POST",
            dataType: "json",
            data: {
                r: Math.random(),
                staffIds: staffIds,
            },
            success: function (d) {
                if (d.Ret == 0) {
                    alert(d.Data);
                    $(".js_AgencyLists").html('<div class="line pull-in"></div><div style="width: 100%; display: inline-block; padding: 3px 0 0 0;font-weight: bold;">已转正人员:</div>');
                } else {
                    alert(d.Message);
                }
            }
        });
    });
    //清空数据
    $(".js_plzz").click(function () {
        $(".js_AgencyList").html("");
        $(".js_AgencyLists").html('<div class="line pull-in"></div><div style="width: 100%; display: inline-block; padding: 3px 0 0 0;font-weight: bold;">已转正人员:</div>');
        $(".js_AgencyLists").hide();
    });
});
