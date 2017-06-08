/*

OA系统添加新功能 version: v1.0.0.1
by wQing 2013/6/19

====================================== 函数说明 ======================================
sortFn:列表排序
loadTablelist:异步加载列表
relevanceFn:级联下拉异步
roleprivilegesFn:给角色分配权限异步生成
menuFn:左侧菜单异步加载，并通过记录cookie记录用户点击状态
mergeTable：权限查询合并单元格
========================================================================================

*/
$.fn.extend({
    sortFn: function (options) { /*列表排序*/
        var settings = {
            tableobj: ".list tbody",/*table 下的tr*/
            preClass: ".pre", /*上移class*/
            nextClass: ".next",/*下移class*/
            preUrl: "/OaApplication/ForwardOrderNo/", /*向上排序请求地址*/
            nextUrl: "/OaApplication/BackwardOrderNo/", /*向下排序请求地址*/
            alertBoxId: ".js_Message", /*错误提示语所在的弹框的ID*/
            selfDiverror: "#warning", /*错误提示语所在的本页面中的ID*/
            errorway: true /*排序返回错误Message时，是以何种方式表现给用户(0弹框，不是0则显示在本页面)*/
        }
        $.extend(settings, options);

        /*页面一加载列表中的第一行没有向上移按钮，最后一行没有向下移按钮 start*/
        var firstTr = $(settings.tableobj + " tr").first();
        firstTr.eq(0).find(settings.preClass).css("visibility", "hidden");
        var lastTr = $(settings.tableobj + " tr").last();
        lastTr.eq(0).find(settings.nextClass).css("visibility", "hidden");
        var trlength = $(settings.tableobj + " tr").length;

        var canSort = true;

        if (canSort) {
            $(settings.preClass).unbind().bind("click", function () {
                up($(this));
            });
        }
        if (canSort) {
            $(settings.nextClass).unbind().bind("click", function () { /*点击下移*/
                down($(this));
            });
        }

        function up(thislink) {
            var thisindex = thislink.index(settings.preClass);
            var preindex = thisindex - 1;
            var thisObj = thislink.parents("tr");
            var preObj = $(settings.tableobj + " tr").eq(preindex);
            var thishtml = thisObj.html();
            var prehtml = preObj.html();

            //alert(thisindex+",,,,,,,"+preObj+",,,,,,"+thishtml+",,,,,,,,,"+preObj.html());

            var thissn = thisObj.children("td").eq(0).html();/*序号*/
            var presn = preObj.children("td").eq(0).html();/*序号*/
            var thisid = thisObj.attr("appId");
            var preid = preObj.attr("appId");
            $.ajax({ /*点击上移请求地址，返回成功时，在页面上给用户显示调动tr*/
                url: settings.preUrl + "?r=" + Math.random(),
                type: "POST",
                data: { id: parseInt(thisid), isforward: true },
                dataType: "json",
                success: function (data) {
                    //var data = {"Ret":-2,"Message":"出错"} /*模拟数据*/
                    if (!data) {
                        canSort = false;
                    } else {
                        canSort = true;
                    }
                    if (data.Ret == 0) { /*返回成功时替换内容*/
                        thisObj.html(prehtml);
                        preObj.html(thishtml);
                        preObj.children("td").eq(0).html(presn);/*改变序号*/
                        thisObj.children("td").eq(0).html(thissn);/*改变序号*/
                        thisObj.attr("appId", preid);
                        preObj.attr("appId", thisid);

                        preObj.addClass("backcolor").hide().fadeIn();

                        preObj.removeClass("backcolor");

                        hidHT();
                        $(settings.preClass).unbind().bind("click", function () {
                            up($(this));
                        });
                        $(settings.nextClass).unbind().bind("click", function () {
                            down($(this));
                        });
                        return false;

                    } else if (data.Ret == -1) {
                        if (settings.errorway) {
                            alertMsg("错误信息", "请先登录，2秒后自动跳转到登录页");/*错误提示以弹框形式展现，判断用户状态（添加，添加成功）,根据状态控制弹框显示或不显示*/
                            setTimeout(function () { location.href = "http://oa.gyyx.cn/"; }, 2000);
                        } else {
                            alertMsg("错误信息", "请先登录，2秒后自动跳转到登录页");
                            //$(settings.selfDiverror).fadeIn().html("请先登录，2秒后自动跳转到登录页");
                            setTimeout(function () { location.href = "http://oa.gyyx.cn/"; }, 2000);
                        }

                    } else {
                        if (settings.errorway) {
                            alertMsg("错误信息", data.Message);
                        } else {
                            $(settings.selfDiverror).fadeOut().fadeIn().html(data.Message);
                        }
                    }
                }
            });
            return false;
        }

        function down(thislink) {
            var thisindex = thislink.index(settings.nextClass);
            var nextindex;
            if (thisindex + 1 == $(settings.tableobj + " tr").size()) {
                nextindex = 0;
            } else {
                nextindex = thisindex + 1;
            }
            var thisObj = thislink.parents("tr");
            var nextObj = $(settings.tableobj + " tr").eq(nextindex);

            var thishtml = thisObj.html();
            var nexthtml = nextObj.html();

            var thissn = thisObj.children("td").eq(0).html();/*序号*/
            var nextsn = nextObj.children("td").eq(0).html();/*序号*/
            var thisid = thisObj.attr("appId");
            var nextid = nextObj.attr("appId");
            $.ajax({ /*点击下移请求地址，返回成功时，在页面上给用户显示调动tr*/
                url: settings.preUrl + "?r=" + Math.random(),
                type: "POST",
                data: { id: parseInt(thisid), isforward: false },
                dataType: "json",
                success: function (data) {
                    if (!data) {
                        canSort = false;
                    } else {
                        canSort = true;
                    }
                    if (data.Ret == 0) { /*返回成功时替换内容*/
                        thisObj.html(nexthtml);
                        nextObj.html(thishtml);
                        nextObj.children("td").eq(0).html(nextsn); /*改变序号*/
                        thisObj.children("td").eq(0).html(thissn);/*改变序号*/
                        thisObj.attr("appId", nextid);
                        nextObj.attr("appId", thisid);

                        nextObj.addClass("backcolor").hide().fadeIn();

                        nextObj.removeClass("backcolor");
                        hidHT();
                        $(settings.preClass).unbind().bind("click", function () {
                            up($(this));
                        });
                        $(settings.nextClass).unbind().bind("click", function () {
                            down($(this));
                        });
                        return false;

                    } else if (data.Ret == -1) {
                        if (settings.errorway) {
                            $(settings.alertBoxId).dialog({ /*判断用户状态（添加，添加成功）,根据状态控制弹框显示或不显示*/
                                modal: true,
                                width: 450
                            });
                            $(settings.alertBoxId).find("p").html("请先登录，2秒后自动跳转到登录页");
                            setTimeout(function () { location.href = "http://oa.gyyx.cn/"; }, 2000);
                        } else {
                            $(settings.selfDiverror).fadeIn().html("请先登录，2秒后自动跳转到登录页");
                            setTimeout(function () { location.href = "http://oa.gyyx.cn/"; }, 2000);
                        }

                    } else {
                        if (settings.errorway) {
                            $(settings.alertBoxId).html(data.Message);
                        } else {
                            $(settings.selfDiverror).fadeOut().fadeIn().html(data.Message);
                        }
                    }
                }
            });
            return false;
        }

        function hidHT() { /*页面一加载列表中的第一行没有向上移按钮，最后一行没有向下移按钮 start*/
            $.each($(settings.preClass), function (i, item) {
                if (i == 0) {
                    $(this).css("visibility", "hidden");
                } else {
                    $(item).css("visibility", "visible");
                }
            });
            firstTr.eq(0).find(settings.preClass).css("visibility", "hidden");

            trlength = $(settings.tableobj + " tr").length;

            $.each($(settings.nextClass), function (i, item) {
                if (i == trlength - 1) {
                    $(this).css("visibility", "hidden");
                } else {
                    $(item).css("visibility", "visible");
                }
            });
            lastTr.eq(0).find(settings.nextClass).css("visibility", "hidden");
            return false;
        }
    },
    loadTablelist: function (options) { /*异步加载列表*/
        var settings = {
            loadUrl: "/OaModule/GetSortDataList/", /*列表json请求地址*/
            setBtnClass: ".sortformodule", /*设置当前应用下模块排序的按钮的class，用于获取对象*/
            tableobjl: ".loadtable tbody", /*table对象*/
            preClass: ".pren", /*往上排序class名*/
            nextClass: ".nextn", /*往下排序class名*/
            preUrl: "/OaModule/ForwardOrderNo/", /*往上排序请求地址*/
            nextUrl: "/OaModule/BackwardOrderNo/", /*往下排序请求地址*/
            alertBoxId: ".js_Message", /*错误提示语所在的弹框的ID*/
            hidDiv: "#widgetTableList", /*放加载列表的隐藏DIV对象*/
            holdtxt: "此应用还没有模块"/*加载列表时，没有数据时的提示语*/
        }
        $.extend(settings, options);
        $(settings.tableobjl + " tbody").html("");


        $(settings.setBtnClass).unbind().bind("click", function () { /*点击排序按钮进行排序*/
            var _this = $(this);
            $(settings.tableobjl).html("");
            $.ajax({ /*点击下移请求地址，返回成功时，在页面上给用户显示调动tr*/
                url: settings.loadUrl + "?r=" + Math.random(),
                type: "GET",
                data: { id: parseInt(_this.parents("tr").attr("appId")) },
                dataType: "json",
                success: function (data) {
                    if (data.Ret == 0) {
                        if (data.Data == null || data.Data.length == 0) {
                            $(settings.alertBoxId).html(settings.holdtxt);
                        } else {

                            var strhtml = "";
                            $.each(data.Data, function (i, item) { /*循环异步获取的数据，进行创建，并插入到页面中*/
                                var trObj = document.createElement("tr");
                                $(trObj).attr("appId", item.Id);
                                var trhtml = '\
                                <td>'+ (i + 1) + '</td>\
                                <td>'+ item.Name + '</td>\
                                <td>\
<a data-toggle="tooltip" data-title="向上" href="javascript:;" class="btn btn-xs btn-danger pren set_a_span" data-original-title="" title="">\
                                    <i class="fa fa-long-arrow-up"></i>\
                                </a>\
                                    <a data-toggle="tooltip" data-title="向下" href="javascript:;" class="btn btn-xs btn-info nextn set_a_span" data-original-title="" title="">\
                                    <i class="fa fa-long-arrow-down"></i>\
                                </a>\
                                </td>';
                                $(trObj).html(trhtml);
                                $(settings.tableobjl).append($(trObj));
                            });
                            /*页面一加载列表中的第一行没有向上移按钮，最后一行没有向下移按钮 start*/
                            if ($(settings.tableobjl + " tr").size() != 0) {
                                var firstTr = $(settings.tableobjl + " tr").first();
                                firstTr.eq(0).find(settings.preClass).css("visibility", "hidden");
                                var lastTr = $(settings.tableobjl + " tr").last();
                                lastTr.eq(0).find(settings.nextClass).css("visibility", "hidden");
                            }
                        }

                        $(settings.tableobjl).sortFn({ /*创建列表，调用排序方法*/
                            tableobj: settings.tableobjl,
                            preClass: settings.preClass,
                            nextClass: settings.nextClass,
                            preUrl: settings.preUrl,
                            nextUrl: settings.nextUrl,
                            errorway: true
                        });

                    } else {
                       
                        if (data.Message) {
                            $(settings.alertBoxId).html(data.Message);
                            //alert(data.Message);
                        } else {
                            alert("请求失败");
                        }
                    }

                }, error: function () {
                    alert("请求失败");
                }
            });
        });
    },
    relevanceFn: function (options) { /*级联下拉异步*/
        var settings = {
            url: "/OaFunctionGroup/Index/", /*异步请求url*/
            selectedValue: "", /*有权限时，前端加载下拉值并设置当前选中值*/
            //firstSel: "#ApplicationCode", /*第一个下拉菜单*/
            secondSel: "#uniform-ModuleCode",/*第二个下拉菜单*/
            txt: "请选择所属模块" /*第二个下拉清空时默认文字*/

        };
        $.extend(settings, options);
        var $this = $(this);

        var thisval = $this.val();
        //$(settings.secondSel).parent("div").find("span").eq(0).html(settings.txt); /*清空上次选择的值*/

        $.ajax({ //select下拉change时异步请求其下的选项，并插入到页面中
            url: settings.url + "?r=" + Math.random(),
            type: "GET",
            data: { id: thisval },
            dataType: "json",
            success: function (data) {
                if (data) {

                    if (data.Ret == 0) {
                        var str = "";
                        var selecedflag = false;
                        $.each(data.Data, function (i, item) {
                            var optionsobj = document.createElement("option");
                            var dataval = item.Value;
                            var datatxt = item.Text;
                            str += "<option value='" + dataval + "'>" + datatxt + "</option>";
                            if (settings.selectedValue == dataval) {
                                selecedflag = selecedflag || true;
                            } else {
                                selecedflag = selecedflag || false;
                            }
                        });
                        $(settings.secondSel).html(str);
                        if (selecedflag) {
                            $(settings.secondSel).val(settings.selectedValue);
                            $(settings.secondSel).change();
                        } else {
                            $(settings.secondSel).val(-1);
                            $(settings.secondSel).change();
                        }
                        //if (settings.selectedValue != "") {
                        //    $(settings.secondSel).val(-1); /*根据selectedValue值设置当前选中项*/
                        //    $(settings.secondSel).change(); /*自动调用第二级change事件*/
                        //}
                    } else {
                        //alertMsg("错误提示",data.Message);
                    }
                }
            }, error: function () {
            }
        });
    },
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

        var thisid = $this.parents("tr").attr("data-roleCode");
        var hiddenroleId = "<input name='roleid' type='hidden' value='" + thisid + "' />";
        $("form#roleform").append(hiddenroleId);

        /*
        var data = {
            "Ret": 0,
            "Data": {
                "Name": "应用测试名称",
                "Modules": [{
                    "Name": "模块1",
                    "FunctionGroups": [
                        { "Name": "FG1", "Functions": [{ "Name": "功能1", "Code": 0, "Assigned": true }, { "Name": "功能2", "Code": 1, "Assigned": true }, { "Name": "功能3", "Code": 2, "Assigned": true }] },
                        { "Name": "FG2", "Functions": [{ "Name": "功能4", "Code": 3, "Assigned": false }, { "Name": "功能5", "Code": 4, "Assigned": true }] },
                        { "Name": "432fd", "Functions": [{ "Name": "fdsfd4", "Code": 13, "Assigned": false }, { "Name": "vcxvcx", "Code": 14, "Assigned": true }, { "Name": "fcvxv", "Code": 24, "Assigned": true }] }
                    ]
                },
					{
						"Name": "模块2",
						"FunctionGroups": [
                            { "Name": "FG3", "Functions": [{ "Name": "功能6", "Code": 5, "Assigned": true }, { "Name": "功能7", "Code": 6, "Assigned": true }, { "Name": "功能8", "Code": 7, "Assigned": false }] },
                            { "Name": "FG4", "Functions": [{ "Name": "功能9", "Code": 8, "Assigned": false }, { "Name": "功能10", "Code": 9, "Assigned": false }] }
						]
					}
                ]
            }
        }
        */
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
                            if (!$(this).attr("checked")) {
                                $(this).parent("p").siblings(".functionscon").find("input").prop("checked", true);
                                $(item).prop("checked", true);
                                $(item).attr("checked", "checked");
                            } else {
                                $(this).parent("p").siblings(".functionscon").find("input").prop("checked", false);
                                $(item).prop("checked", false);
                                $(item).removeAttr("checked");
                            }
                        });
                    });

                    $.each($("#moduleul input"), function (i, item) { /*点击模块全选功能组及功能*/
                        $(item).click(function () {
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
                            if ($(item).find("input:checked").length > 0) {
                                $("input[name=moudule]").eq(i).parent().addClass("bg-primary");
                            } else {
                                $("input[name=moudule]").eq(i).parent().removeClass("bg-primary");
                            }
                        });
                        if ($(item).find("input:checked").length > 0) {
                            $("input[name=moudule]").eq(i).parent().addClass("bg-primary");
                        } else {
                            $("input[name=moudule]").eq(i).parent().removeClass("bg-primary");
                        }
                    });
                } else {
                    $(".js_Message").empty().append(d.Message);
                }
            }
        });

    },
    assigRolesFn: function (options) { /*给帐号分配角色*/
        var settings = {
            assUrl: "/credential/roles/", /*异步请求地址*/
            alertBox: "#widget", /*弹框ID*/
            actionUrl: "" /*form表单提交地址*/
        }
        $.extend(settings, options);
        var $this = $(this);

        $(settings.alertBox).find("form#roleform").remove(); /*清空弹框内容，重新进行以下创建*/
        var thisid = $this.parents("tr").attr("code");
        var staffcode = $this.parents("tr").attr("staff_code");
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
    },
    menuFn: function (options) { /*菜单异步加载,点击应用，加载模块和功能,以及当前页面的菜单当前状态cookie记录*/
        var settings = {
            ajaxUrl: "/OaApplication/GetApplication", /*点击应用，异步请求地址*/
            ulId: ".subNav", /*中间UL的ID*/
            appulId: $(this).find(".subNav")/*当前的二级菜单内容*/
        }
        $.extend(settings, options);

        var $this = $(this);
        var appId = $this.attr("code");
         
        setCookie("appcode", appId);

        $(settings.ulId).html(""); /**/

        function setCookie(name, value) { /*设置cookie*/

            var str = name + '=' + value + ";path=/;" + ";domain=oa.gyyx.cn";
            document.cookie = str;
        }
        function getCookie(name) { /*获取cookie*/
            var arr = document.cookie.split('; ');
            var i = 0;
            for (i = 0; i < arr.length; i++) {
                var arr1 = arr[i].split('=');
                if (arr1[0] == name) {
                    return arr1[1];
                }
            }
            return '';
        }
        $.ajax({ /*异步请求数据*/
            url: settings.ajaxUrl,
            type: "GET",
            data: { id: appId, r: function () { return Math.random(); } },
            dataType: "json",
            success: function (data) {
                if (data && data.Ret == 0) { /*请求数据成功进行操作*/
                    if (data.Data.length > 0) {
                        $.each(data.Data, function (i, item) { /*循环模块，插入到页面中*/
                            var firstLi = "<li id='firstli" + i + "'>" +
                                          "   <a href='#" + item.Name + "'>" +
                                          "     <i class='fa fa-angle-down text'></i>" +
                                          "     <i class='fa fa-angle-up text-active'></i>" +
                                          "     <span>" + item.Name + "</span>" +
                                          "   </a>" +
                                          "   <ul class='nav bg ulfun'>" +
                                          "   </ul>" +
                                          "</li>";
                            $(settings.appulId).append(firstLi);

                            var functionsstr = "";
                            $.each(item.Functions, function (j, itemj) { /*循环功能插入到页面中*/
                                functionsstr += "<li code='" + itemj.Id + "'>" +
                                                "   <a href='" + itemj.Url + "'>" +
                                                "       <i class='fa fa-angle-right'></i>" +
                                                "       <span>" + itemj.Name + "</span>" +
                                                "   </a>" +
                                                "</li>";
                            });
                            $(settings.ulId).find("li#firstli" + i).find("ul").append(functionsstr);
                            
                        });
                        $(settings.ulId + " li ul li a").click(function () { /*点击时设置cookie*/
                            var thisid = $(this).parent("li").attr("code");
                            //$(settings.ulId + " li ul li").removeClass("active");
                            //$(this).parent("li").addClass("active");
                            setCookie("menuid", thisid);
                        });

                        loadfinish = true;

                        if (loadfinish) {
                            var menuikcookie = getCookie("menuid");
                            var isincookie = false;
                            $.each($(".ulfun li"), function (i, item) {
                                if ($(item).attr("code") == menuikcookie) {
                                    isincookie = true;
                                    return false;
                                }
                            });

                            if (isincookie) { /*如果有cookie*/
                                $.each($(settings.ulId + " li ul li"), function (i, item) {
                                    //$(item).removeClass("active");
                                    //$(item).find("a").removeClass("active");
                                    if (getCookie("menuid") == $(item).attr("code")) {
                                        $(this).addClass("active");
                                        $(this).parents().parents().addClass("active");
                                        $(this).parent().parent().find("a").addClass("active");
                                        //$(this).parents("ul.ulfun").show();
                                        return false;

                                    }
                                });
                            } else { /*如果没有cookie时，默认第一个菜单是展开的*/
                                if ($("#hidCurrentAppCode").val() != "" && $("#hidCurrentAppCode").val() != undefined && isincookie) {
                                    $(".ulfun li[code=" + $("#hidCurrentAppCode").val() + "]").parents("ul.ulfun").show();
                                }
                            }
                        }
                    } else {
                        $(settings.ulId).append("<p>无数据</p>");
                    }
                } else {
                    alert(data.Message);
                }
            }
        });
    }, etabsFn: function (options) { /*头部选项卡,点击应用,以及当前页面的菜单当前状态cookie记录*/
        var settings = {
            tabId: ".etabs" /*中间UL的ID*/
        }
        $.extend(settings, options);
        var $this = $(this);
        function setCookie(name, value) { /*设置cookie*/
            var str = name + '=' + value + ";path=/;" + ";domain=oa.gyyx.cn";
            document.cookie = str;
        }
        function getCookie(name) { /*获取cookie*/
            var arr = document.cookie.split('; ');
            var i = 0;
            for (i = 0; i < arr.length; i++) {
                var arr1 = arr[i].split('=');
                if (arr1[0] == name) {
                    return arr1[1];
                }
            }
            return '';
        }
        $(settings.tabId + " li a ").click(function () { /*点击时设置cookie*/
            var thisid = $(this).parent("li").attr("code");
            $(settings.tabId + " li").removeClass("ui-tabs-selected ui-state-active");
            $(this).parent("li").addClass(" ui-state-active");

            setCookie("etabid", thisid);
        });

        loadfinish = true;
        if (loadfinish) {
            var tabikcookie = getCookie("etabid");
            var isincookies = false;
            $.each($(".etabs li"), function (i, item) {
                if ($(item).attr("code") == tabikcookie) {
                    isincookies = true;
                    return false;
                }
            });
            if (isincookies) { /*如果有cookie*/
                $.each($(".etabs li"), function (i, item) {
                    $(item).removeClass("ui-tabs-selected ui-state-active");
                    if (getCookie("etabid") == $(item).attr("code")) {
                        $(this).addClass("ui-state-active");
                        var codess = $(item).attr("code");
                        $(".etabs li[code=" + codess + "] a").trigger("click");
                        return false;
                    }
                });
            } else {
                $(".etabs li a").eq(0).trigger("click");
            }
        }
    },
    mergeTable: function () { /*合并表格*/

        var trlength = $(".table tbody tr").length;
        var td0 = $(".table tbody tr td.oa-app");
        var obj = {}, arr = []; /* obj = [{"模块":{d:1,index:2}}]*/
        $.each(td0, function (i, ele) {
            var h = $.trim($(ele).attr("code"));
            if (obj[h]) {
                obj[h]["d"] += 1;
            } else {
                obj[h] = { d: 1, index: i };
            }
        });
        var htmlstr = "";
        var trAll = $(".table tbody tr");
        for (var i = 0; i < td0.length; i++) {
            htmlstr = $.trim(td0.eq(i).attr("code"));
            if (obj.hasOwnProperty(htmlstr)) { /*判断对象中有没有当前单元格的内容的对象*/
                trAll.eq(obj[htmlstr]["index"]).find("td:first").attr("rowspan", obj[htmlstr]["d"]); /*合并单元格*/
                trAll.slice(obj[htmlstr]["index"] + 1, obj[htmlstr]["d"] + obj[htmlstr]["index"]).find('td.oa-app').remove();
            }
        }


        var td1 = $(".table tbody tr td.oa-module");
        var obj1 = {}, arr1 = [];
        $.each(td1, function (i, ele) {
            var h = $.trim($(ele).attr("code"));
            if (obj1[h]) {
                obj1[h]["d"] += 1;
                //alert(obj[h]["d"]);
            } else {
                obj1[h] = { d: 1, index: i };
            }
        });
        var htmlstr1 = "";
        var trAll1 = $(".table tbody tr");
        for (var i = 0; i < td1.length; i++) {
            htmlstr1 = $.trim(td1.eq(i).attr("code"));
            if (obj1.hasOwnProperty(htmlstr1)) {
                trAll1.eq(obj1[htmlstr1]["index"]).find("td.oa-module").attr("rowspan", obj1[htmlstr1]["d"])
                trAll1.slice(obj1[htmlstr1]["index"] + 1, obj1[htmlstr1]["d"] + obj1[htmlstr1]["index"]).find('td.oa-module').remove();
            }
        }


        var td2 = $(".table tbody tr td.oa-func-group");

        var obj2 = {}, arr1 = [];
        $.each(td2, function (i, ele) {
            var h = $.trim($(ele).attr("code"));
            if (obj2[h]) {
                obj2[h]["d"] += 1;
            } else {
                obj2[h] = { d: 1, index: i };
            }
        });
        var htmlstr2 = "";
        var trAll2 = $(".table tbody tr");
        for (var i = 0; i < td2.length; i++) {
            htmlstr2 = $.trim(td2.eq(i).attr("code"));

            if (obj2.hasOwnProperty(htmlstr2)) {
                trAll2.eq(obj2[htmlstr2]["index"]).find("td.oa-func-group").attr("rowspan", obj2[htmlstr2]["d"])
                trAll2.slice(obj2[htmlstr2]["index"] + 1, obj2[htmlstr2]["d"] + obj2[htmlstr2]["index"]).find('td.oa-func-group').remove();
            }
        }

    },
    organSelectFn: function (options) { /*机构选择*/
        var settings = {
            hidinputId: "#ParentAgencyCode", /*隐藏inputID*/
            hidAgencyNameInputId: "#ParentAgencyName", /*hidden*/
            clickBtn: "#selectagency", /*点击按钮*/
            clickBtntxt: "请选择父级机构", /*按钮文字*/
            hidDiv: "#widgetforjigou", /*隐藏弹框ID*/
            currentId: "-1", /*编辑时传的ID，前端通过传给后台此ID，后台控制数据的加载或不加载*/
            url: "/Agency/GetAgencyChildren", /*请求地址*/
            repost: false, /*是否请求岗位数据(员工列表里添加员工用此功能)*/
            repostSelId: "#PostCode", /*所属岗位下拉ID*/
            repostCur: "#AgencyCode", /*所属岗位下拉ID默认选中的*/
            repostUrl: "/post/agencyposts/" /*岗位请求数据地址*/
        };
        $.extend(settings, options);

        var $this = $(this);
        var thisid;
        if ($this.attr("code")) { /*把当前点击的code值通过ajax传给后端,如果没有ID号则ID为0*/
            thisid = $this.attr("code");
        } else {
            thisid = "0";
            if ($(settings.hidDiv).html() != "") {
                return false;
            }
        };

        //alert(thisid);
        $.ajax({ /*异步请求获取上级机构*/
            url: settings.url + "?r=" + Math.random(),
            type: "GET",
            data: { id: parseInt(thisid), currentid: settings.currentId },
            dataType: "json",
            success: function (data) {

                //var data = {
                //    "Ret": 0,
                //    "Data": [{ "Code": 0, "Name": "aaa" }, { "Code": 1, "Name": "bbb" }, { "Code": 2, "Name": "ccc" }, { "Code": 3, "Name": "ddd" }, { "Code": 4, "Name": "eee" }, { "Code": 5, "Name": "fff" }, { "Code": 6, "Name": "ggg" }, { "Code": 7, "Name": "hhh" }]
                //}

                if (data.Ret == 0) { /*请求成功时*/
                    var oDiv1 = document.createElement("div");
                    if (settings.id > 0) {
                        $(oDiv1).addClass("divb").attr("code", "div" + settings.id);
                    }
                    else {
                        $(oDiv1).addClass("divb").attr("code", "div0");
                    }
                    $.each(data.Data, function (i, item) {
                        var oP = document.createElement("a");
                        $(oP).html(item.Name).attr("code", item.Code).addClass("btn btn-default btn-sm w118");
                        $(oDiv1).append($(oP));

                        $(oP).on("click", function () { /**/
                            var thisparentId = $(this).parent("div").attr("code");
                            cid = parseInt(thisparentId.substring(3));
                            $(this).parent(".divb").find("a").removeClass("btn-success");
                            $(this).addClass("btn-success");
                            $.each($(".divb"), function (i, item) {
                                if (parseInt($(item).attr("code").substring(3)) > cid) {
                                    $(item).remove();
                                }
                            });

                            //alert(cid);                            
                            $(this).organSelectFn({
                                "repost": settings.repost,
                                "id": parseInt(cid + 1),
                                "currentId": settings.currentId,
                                'hidinputId': settings.hidinputId,
                                'hidAgencyNameInputId': settings.hidAgencyNameInputId,
                                'hidDiv': '.js_AgencyList'
                            });/*点击时重新调用一次函数本身，把当前点击的ID传过去*/
                        });
                    });
                    $(settings.hidDiv).append($(oDiv1));

                } else {
                    alert(data.Message);
                }
            }
        });
    },
    postSelectFn: function (options) { /*岗位选择*/
        var settings = {
            hidinputId: "#PostCode", /*隐藏inputID*/
            hidAgencyNameInputId: "#PostName", /*hidden*/
            clickBtn: "#selectpost", /*点击按钮*/
            clickBtntxt: "请选择岗位", /*按钮文字*/
            hidDiv: "#widgetforjigou2", /*隐藏弹框ID*/
            currentId: "-1", /*编辑时传的ID，前端通过传给后台此ID，后台控制数据的加载或不加载*/
            url: "/Agency/GetAgencyChildren", /*请求地址*/
            repost: false, /*是否请求岗位数据(员工列表里添加员工用此功能)*/
            repostSelId: "#PostCode", /*所属岗位下拉ID*/
            repostCur: "#AgencyCode", /*所属岗位下拉ID默认选中的*/
            repostUrl: "/post/agencyposts/" /*岗位请求数据地址*/
        };
        $.extend(settings, options);

        var $this = $(this);
        var thisid;
        if ($this.attr("code")) { /*把当前点击的code值通过ajax传给后端,如果没有ID号则ID为0*/
            thisid = $this.attr("code");
        } else {
            thisid = "0";
        };

        //alert(thisid);
        $.ajax({ /*异步请求获取上级机构*/
            url: settings.url + "?r=" + Math.random(),
            type: "GET",
            data: { id: parseInt(thisid), currentid: settings.currentId },
            dataType: "json",
            success: function (data) {

                //var data = {
                //    "Ret": 0,
                //    "Data": [{ "Code": 0, "Name": "aaa" }, { "Code": 1, "Name": "bbb" }, { "Code": 2, "Name": "ccc" }, { "Code": 3, "Name": "ddd" }, { "Code": 4, "Name": "eee" }, { "Code": 5, "Name": "fff" }, { "Code": 6, "Name": "ggg" }, { "Code": 7, "Name": "hhh" }]
                //}

                if (data.Ret == 0) { /*请求成功时*/
                    $(settings.hidDiv).dialog({
                        modal: true,
                        width: 550,
                        height: 400,
                        buttons: {
                            "保存": function () {
                                if ($(".postdiv").find(".activea").length > 0) {
                                    var apcode = $(".postdiv").find(".activea").attr("code");
                                    var aptxt = $(".postdiv").find(".activea").html();
                                    $(settings.hidAgencyNameInputId).val(aptxt);
                                    $(settings.hidinputId).val(apcode);
                                    $(settings.clickBtn).html(aptxt);
                                } else {
                                    $(settings.clickBtn).html(settings.clickBtntxt);
                                }
                                $(this).dialog("close");
                            }
                        },
                        close: function () {
                            $(settings.hidDiv).html("");
                        }
                    });

                    var postDiv = document.createElement("div");
                    $(postDiv).addClass("postdiv");
                    if ($(settings.hidDiv).find(".title").length == 0) {
                        $(settings.hidDiv).append('<div class="title">所属机构</div>');
                    }
                    if ($(settings.hidDiv).find(".postdiv").length == 0) {
                        $(settings.hidDiv).append($(postDiv));
                    }
                    $.ajax({ /*异步请求获取上级机构*/
                        url: settings.repostUrl + "?r=" + Math.random(),
                        type: "GET",
                        data: { id: parseInt(thisid) },
                        dataType: "json",
                        success: function (data) {
                            //var data = { "Ret": 0, "Data": [{"Code":-1,"Name":"请选择"},{ "Code": 0, "Name": "aaa" }, { "Code": 1, "Name": "bbb" }] };
                            if (data.Ret == 0) {
                                var poststr = ""
                                $.each(data.Data, function (i, item) {
                                    if (i > 0) {
                                        poststr += "<a href='' code = '" + item.Code + "'>" + item.Name + "</a>";
                                    }
                                });
                                $('.postdiv').empty().append('<div class="title2">所在岗位</div>').append(poststr);

                                $(".postdiv").find("a").click(function () {
                                    $(this).siblings().removeClass("activea");
                                    $(this).addClass("activea");
                                    return false;
                                });

                            } else {
                                alert(data.Message);
                            }
                        },
                        error: function () {
                            alert("请求失败");
                        }
                    });

                    var oDiv1 = document.createElement("div");
                    if (settings.id > 0) {
                        $(oDiv1).addClass("divb").attr("code", "div" + settings.id);
                    }
                    else {
                        $(oDiv1).addClass("divb").attr("code", "div0");
                    }

                    $.each(data.Data, function (i, item) {
                        var oP = document.createElement("a");
                        $(oP).html(item.Name).attr("code", item.Code);
                        $(oDiv1).append($(oP));

                        $(oP).click(function () { /**/

                            var thisparentId = $(this).parent("div").attr("code");
                            cid = parseInt(thisparentId.substring(3));
                            $.each($(".divb"), function (i, item) {
                                if (parseInt($(item).attr("code").substring(3)) > cid) {
                                    $(item).remove();
                                }
                            });
                            //alert(cid);                            
                            $(this).postSelectFn({
                                "repost": settings.repost,
                                "id": parseInt(cid + 1),
                                "currentId": settings.currentId,
                                'hidinputId': settings.hidinputId,
                                'hidAgencyNameInputId': settings.hidAgencyNameInputId
                            });/*点击时重新调用一次函数本身，把当前点击的ID传过去*/
                        });
                    });
                    if ($(settings.hidDiv).find(".divb").length >= 1) {
                        $(settings.hidDiv).find(".divb").last().after($(oDiv1));
                    } else {
                        $(settings.hidDiv).find(".title").after($(oDiv1));
                    }

                } else {
                    alert(data.Message);
                }
            }
        });


    }
});