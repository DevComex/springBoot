/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：陈巧玲
 * 联系方式：chenqiaoling@gyyx.cn
 * 创建时间： 2014/4/10 
 * 版本号：v1.0
 * 代码说明：在http://oa.gyyx.cn/Scripts/files/jquery.functions.js 的 assigRolesFn 上有所更改

 * 功能： 权限管理-凭据列表-分配角色弹出层

 * -------------------------------------------------------------------------*/

$.fn.extend({
    roleprivilegesFn: function (options) { /*给角色分配权限*/
        var settings = {
            alertBox: "#widget", /*弹框ID*/
            roleUrl: "/Role/RoleFunctions/", /*异步请求地址*/
            actionUrl: "" /*表单提交地址*/
        };
        $.extend(settings, options);
        var $this = $(this);
        $(settings.alertBox).find("form#roleform").remove(); /*清空弹框内容，重新进行以下创建*/
        $("#errorwar").remove();

        var formObj = "<form id='roleform' action='" + settings.actionUrl + "' method='post'></form>";
        $(settings.alertBox).append(formObj);

        var thisid = $this.parents("tr").attr("rolecode");
        var hiddenroleId = "<input name='roleid' type='hidden' value='" + thisid + "' />";
        $("form#roleform").append(hiddenroleId);

        $.ajax({ /*异步请求数据*/
            url: settings.roleUrl + "?r=" + Math.random(),
            type: "GET",
            data: { id: thisid },
            dataType: "json",
            success: function (data) {

                if (data.Ret == 0) { /*加载数据成功*/
                    $(".js_allocateSubmit").click(function () {
                        $("form#roleform").submit();
                    });
                    var appName = "<h4>应用名称：" + data.Data.Name + "</h4>"; /*应用名称插入到页面中*/
                    $(settings.alertBox).find("form").append(appName);

                    if (!data.Data.Modules) {
                        $(settings.alertBox).find("form").append("<p style='color:#f00'>暂时无数据</p>");
                    }
                    var str = "<ul id='moduleul'></ul>";
                    $(settings.alertBox).find("form").append(str);
                    var oWarningP = "<p id='warningp'></p>";
                    //$(settings.alertBox).find("form").append(oWarningP);
                    var oDivstr = "";
                    var functionsstr = "";
                    var aid = -1; /*fgs的id*/
                    $.each(data.Data.Modules, function (i, item) { /*循环数据，先把模块名称插入到页面中*/

                        var warningstr = "";
                        var functionGroup = "";/*功能组*/
                        var moduleLi = "<li id='moduleLi" + i + "'><input name='moudule' type='checkbox' value='' />" + item.Name + "</li>";
                        $("#moduleul").append(moduleLi);

                        var oDiv = "<div class='modules dis_none' id='functionDiv" + i + "'></div>";
                        $(settings.alertBox).find("form").append(oDiv);

                        $.each(item.FunctionGroups, function (j, itemj) { /*功能组*/
                            aid++;
                            functionsstr = ""; /*功能*/
                            functionGroup = "<div class='fgs' id='" + aid + "'><p><input class='fl_left' name='functionGroup' type='checkbox' />" + itemj.Name + " <strong class='fl_right'>↓</strong></p><div class='functionscon'></div></div>";
                            $("#functionDiv" + i).append(functionGroup);

                            $.each(itemj.Functions, function (h, itemh) {
                                if (itemh.Assigned) {
                                    functionsstr += "<span title='" + itemh.Name + "'><input name='function' type='checkbox' id='" + itemh.Code + "' checked='" + itemh.Assigned + "' value='" + itemh.Code + "' />" + itemh.Name + "</span>";/*给span加title,防止功能名称过长显示不全，显示title查看lcf*/
                                } else {
                                    functionsstr += "<span title='" + itemh.Name + "'><input name='function' type='checkbox' id='" + itemh.Code + "' value='" + itemh.Code + "' />" + itemh.Name + "</span>";/*给span加title,防止功能名称过长显示不全，显示title查看lcf*/
                                }
                            });
                            $(".fgs").eq(aid).find(".functionscon").eq(0).append(functionsstr);

                        });


                    });
                    $(settings.alertBox).find("form").append(oDivstr);

                    $(".functionscon").eq(0).show(); /**/
                    $(".functionscon").eq(0).siblings("p").find("strong").html("↑");

                    $(".modules").eq(0).show(); /*第一个模块显示*/
                    $("#moduleul li").eq(0).addClass("activeli");   /*第一个模块选项高亮 lcf*/

                    $.each($(".modules"), function (i, item) { /*第个模块下的第一个功能组显示*/
                        $(item).find(".functionscon").eq(0).show();
                        $(item).find("p strong").eq(0).html("↑");
                    });

                    $(".fgs p strong").click(function () { /*点击功能组显示隐藏*/
                        if ($(this).html() == "↑") {
                            $(this).html("↓");
                            $(this).parent("p").siblings(".functionscon").hide();
                        } else {
                            $(this).html("↑");
                            $(this).parent("p").siblings(".functionscon").show();
                        }

                    });
                    $.each($("#moduleul li"), function (i, item) { /*点击模块，控制显示当前模块的显示隐藏*/
                        $(this).click(function () {
                            $(this).addClass("activeli").siblings("li").removeClass("activeli");    /*当前模块高亮其它变成普通 lcf*/
                            for (var j = 0; j < $("#moduleul li").length; j++) {
                                $(".modules").hide();
                            }
                            $(".modules").eq($(this).index()).show();
                        });
                    });

                    $.each($("input[name=functionGroup]"), function (i, item) { /*点击功能组全选功能*/
                        $(item).click(function () {
                            if ($(this).attr("checked")) {
                                $(this).parent("p").siblings(".functionscon").find("input").prop("checked", true);
                            } else {
                                $(this).parent("p").siblings(".functionscon").find("input").prop("checked", false);
                            }
                        });
                    });

                    $.each($("#moduleul input"), function (i, item) { /*点击模块全选功能组及功能*/
                        $(item).click(function () {
                            if ($(this).attr("checked")) {
                                $(".modules").eq(i).find("input").prop("checked", true);
                            } else {
                                $(".modules").eq(i).find("input").prop("checked", false);
                            }
                        });
                    });

                    $(".functionscon input").click(function () { /*点击功能全选时，功能组选中*/
                        var checkedleng = $(this).parents("div.functionscon").find($("input[name=function]:checked")).length;
                        if (checkedleng != 0 && checkedleng == $(this).parents(".functionscon").find("input").length) {
                            $(this).parents(".functionscon").siblings("p").find("input").attr("checked", "checked");
                            $(this).parents(".functionscon").siblings("p").find("input").prop("checked", true);
                        } else {
                            $(this).parents(".functionscon").siblings("p").find("input").removeAttr("checked");
                            $(this).parents(".functionscon").siblings("p").find("input").prop("checked", false);
                        }
                    });

                    $.each($(".fgs"), function (i, item) { /*数据一加载所有功能选中时，功能组选中*/
                        if ($(item).find("input[name=function]:checked").length > 0 && $(item).find("input[name=function]:checked").length == $(item).find(".functionscon input").length) {
                            $(item).find("input[name=functionGroup]").attr("checked", "checked");
                            $(item).find("input[name=functionGroup]").prop("checked", true);
                        }
                    });

                    $.each($(".modules"), function (i, item) { /*数据一加载，所有的功能及功能组选中时，模块为选中状态*/
                        if ($(item).find("input[name=function]:checked").length > 0 && $(item).find("input:checked").length == $(item).find("input").length) {
                            $("input[name=moudule]").eq(i).attr("checked", "checked");
                            $("input[name=moudule]").eq(i).prop("checked", true);
                        }
                    });

                    $.each($(".modules"), function (i, item) { /*点击功能和功能组时，判断是否所有的为选中状态，如果是则选中当前模块*/
                        $(item).find("input").click(function () {
                            var checkedleng = $(item).find("input:checked").length;
                            if (checkedleng != 0 && checkedleng == $(item).find("input").length) {
                                $("input[name=moudule]").eq(i).prop("checked", true);
                            } else {
                                $("input[name=moudule]").eq(i).prop("checked", false);
                            }
                        });
                    });
                } else {
                    $(settings.alertBox).dialog({ /*显示弹框*/
                        height: 200,
                        width: 550,
                        modal: true
                    });
                    $(settings.alertBox).html("<p id='errorwar' style='color:#f00'>" + data.Message + "</p>");
                }
            }
        });
    }
});