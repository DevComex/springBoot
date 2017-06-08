/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：李根
 * 联系方式：ligen@gyyx.cn
 * 创建时间： 2014/4/16 
 * 版本号：v1.0

 * 功能： 公共弹出层方法

 * -------------------------------------------------------------------------*/

$.fn.extend({
    assigRolesFns: function (options) { /*给帐号分配角色*/
        var settings = {
            assUrl: "/credential/roles/", /*异步请求地址*/
            alertBox: "#widget", /*弹框ID*/
            actionUrl: "" /*form表单提交地址*/
        }
        $.extend(settings, options);
        var $this = $(this);

        $(settings.alertBox).find("form#roleform").remove(); /*清空弹框内容，重新进行以下创建*/
        var thisid = "1399";
        var staffcode = "389";
        var formObj = "<form id='roleform' action='" + settings.actionUrl + "' method='post'></form>";
        $(settings.alertBox).append(formObj);

        var hiddenroleId = "<input name='credentialCode' type='hidden' value='" + thisid + "' />" +
            "<input name='staffCode' type='hidden' value='" + staffcode + "' />";
        $("form#roleform").append(hiddenroleId);


        $.ajax({ /*异步请求数据*/
            url: settings.assUrl + "?r=" + Math.random(),
            type: "GET",
            data: { id: thisid },
            dataType: "json",
            success: function (data) {
                if (data.Ret == 0) {
                    $(".js_allocateSubmit").click(function () {
                        $("form#roleform").submit();
                    });
                    var str = "<ul id='moduleul'></ul>";
                    $(settings.alertBox).find("form").append(str);

                    $.each(data.Data, function (i, item) { /*循环应用*/
                        var oListr = "<li><input name='moudule' type='checkbox' value='' />" + item.Name + "</li>";
                        $("#moduleul").append(oListr); /*把应用插入到页面中*/

                        var oDivstr = "<div class='modules dis_none' id=modules" + i + "></div>";
                        $(settings.alertBox).find("form").append(oDivstr);

                        var rolestr = "";
                        $.each(item.Roles, function (j, itemj) { /*循环应用下的角色，插入到页面中*/
                            if (itemj.Selected) {
                                rolestr += "<span title='" + itemj.Name + "'><input name='role' type='checkbox' id='" + itemj.Code + "' value='" + itemj.Code + "' checked='" + itemj.Selected + "' />" + itemj.Name + "</span>";/*给span加title,防止功能名称过长显示不全，显示title查看lcf*/
                            } else {
                                rolestr += "<span title='" + itemj.Name + "'><input name='role' type='checkbox' id='" + itemj.Code + "' value='" + itemj.Code + "' />" + itemj.Name + "</span>";/*给span加title,防止功能名称过长显示不全，显示title查看lcf*/
                            }
                        });
                        $("#modules" + i).append(rolestr);

                    });
                    $("#moduleul li").eq(0).addClass("activeli");/*凭据管理弹出分配角色第一选项高亮lcf*/
                    $.each($("#moduleul li"), function (i, item) { /*点击应用显示当前下的角色DIV*/
                        $(item).click(function () {
                            $(".modules").hide();
                            $(".modules").eq($(this).index()).show();
                            $(this).addClass("activeli").siblings().removeClass("activeli");/*弹出分配角色点选项高亮其它普通lcf*/
                        });
                    });
                    $(".modules").eq(0).show(); /*默认第一个应用是打开状态*/

                    $.each($("#moduleul li input"), function (i, item) { /*点击功能checkbox时，全选当前下的所有角色*/
                        $(item).click(function () {
                            //alert($(item).attr("checked"));
                            if (!$(item).attr("checked")) {
                                $(".modules").eq(i).find("input").attr("checked", "checked");
                                $(".modules").eq(i).find("input").prop("checked", true);
                                $(item).attr("checked", "checked");
                                $(item).prop("checked", true);
                                $(item).parent().addClass("bg-primary");
                            } else {
                                $(item).prop("checked", false);
                                $(item).removeAttr("checked");
                                $(item).parent().removeClass("bg-primary");
                                $(".modules").eq(i).find("input").removeAttr("checked");
                                $(".modules").eq(i).find("input").prop("checked", false);
                            }
                        });
                    });

                    $.each($(".modules"), function (i, item) { /*点击角色时，判断是否全选，则应用为选中或不选中状态*/
                        $(item).find("input").click(function () {
                            var checkedleng = $(item).find("input:checked").length;
                            if (checkedleng != 0) {
                                $("input[name=moudule]").eq(i).parent().addClass("bg-primary");
                            } else {
                                $("input[name=moudule]").eq(i).parent().removeClass("bg-primary");
                            }
                            if (checkedleng != 0 && checkedleng == $(item).find("input").length) {
                                $("input[name=moudule]").eq(i).prop("checked", true);
                                $("input[name=moudule]").eq(i).attr("checked", "checked");
                            } else {
                                $("input[name=moudule]").eq(i).prop("checked", false);
                                $("input[name=moudule]").eq(i).removeAttr("checked");
                            }
                        });
                    });

                    $.each($(".modules"), function (i, item) { /*数据一加载，所有的角色选中时，应用为选中状态*/
                        if ($(item).find("input:checked").length > 0) {
                            $("input[name=moudule]").eq(i).parent().addClass("bg-primary");
                        }
                        if ($(item).find("input:checked").length > 0 && $(item).find("input:checked").length == $(item).find("input").length) {
                            $("input[name=moudule]").eq(i).attr("checked", "checked");
                            $("input[name=moudule]").eq(i).prop("checked", true);
                        }
                    });

                } else {
                    if (data.Message) {
                        alert(data.Message);
                    } else {
                        alert("请求错误");
                    }
                }
            }
        });
    }
});