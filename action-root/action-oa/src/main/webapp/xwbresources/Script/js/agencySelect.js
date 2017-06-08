/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：陈巧玲
 * 联系方式：chenqiaoling@gyyx.cn
 * 创建时间： 2014/4/10 
 * 版本号：v1.0
 * 代码说明：在http://oa.gyyx.cn/Scripts/files/jquery.functions.js 的 assigRolesFn 上有所更改

 * 功能： 权限管理-凭据列表-分配角色弹出层


 *update by chenqiaoling 2014.8.6
 * 功能： 添加 是否获取选中项对应机构树中所有项 ( isGetAllCode ) 参数，默认为false。机构树最多三层
 *
 *
 *
 * -------------------------------------------------------------------------*/

$.fn.extend({
    agencySelect: function (options) { /*给帐号分配角色*/
        var settings = {
            url: "/Agency/GetAgencyChildren", /*请求地址*/
            hidCode: "#AgencyCode", /*隐藏inputID*/
            hidName: "#AgencyName", /*hidden*/
            hidDiv: ".js_agencySelectList",
            currentId: "-1",
            id: 0,
            getPostList: false,
            getPostUrl: "/post/agencyposts/",
            PostSelect: "#PostSelect",
            isGetAllCode: false
        }
        $.extend(settings, options);
        var $this = $(this);
        $.ajax({
            url: settings.url + "?r=" + Math.random(),
            type: "GET",
            dataType: "json",
            data: {
                id: settings.id,
                currentid: settings.currentId
            },
            success: function (d) {
                if (d.Ret == 0) {/*请求成功时*/
                    if (d.Data.length > 0) {
                        /*有数据*/
                        var listWrap = document.createElement("ul");
                        $(listWrap).attr('aria-labelledby', 'dropdownMenu').attr('role', 'menu').addClass("dropdown-menu");
                        $(listWrap).attr("data-levelcode", settings.id);
                        $.each(d.Data, function (i, item) {
                            var items = document.createElement("a");
                            $(items).html(item.Name).attr("data-code", item.Code).attr("data-parentCode", settings.id);
                            var list = document.createElement("li");
                            $(list).append($(items));
                            $(listWrap).append(list);
                            if (settings.id == 0) {
                                if ($(settings.hidDiv).find("ul").length == 0) {
                                    $(settings.hidDiv).append(listWrap);
                                }
                            } else {
                                if ($this.parent().find("ul").length == 0) {
                                    $this.parent().append(listWrap);
                                    $this.parent().addClass("dropdown-submenu");
                                }
                            }

                            //鼠标划过重新调用一次函数本身，获取下级菜单
                            $(items).on("mouseenter", function () {
                                var thisParentCode = $(this).attr("data-code");
                                //if ($(this).parent().hasClass("dropdown-submenu")) {
                                $(this).agencySelect({
                                    "id": thisParentCode,
                                    "currentId": settings.currentId,
                                    'hidCode': settings.hidCode,
                                    'hidName': settings.hidName,
                                    'hidDiv': settings.hidDiv,
                                    'getPostList': settings.getPostList,
                                    'PostSelect': settings.PostSelect,
                                    'isGetAllCode': settings.isGetAllCode
                                });
                                // }

                            });

                            //鼠标点击 选中菜单项
                            $(items).on("click", function () {
                                if (settings.isGetAllCode) {
                                    var hidCode, hidName;

                                    var parentCode = $(this).attr("data-parentcode");
                                    if (parentCode != 0) {
                                        var parent = $(settings.hidDiv).find("a[data-code=" + parentCode + "]");
                                        var parentName = parent.text();

                                        if (parent.attr("data-parentcode") != 0) {
                                            var ancCode = parent.attr("data-parentcode");
                                            var ancName = $(settings.hidDiv).find("a[data-code=" + ancCode + "]").text();

                                            hidCode = ancCode + "/" + parentCode + "/" + $(this).attr("data-code");
                                            hidName = ancName + "/" + parentName + "/" + $(this).text();
                                        } else {
                                            hidCode = parentCode + "/" + $(this).attr("data-code");
                                            hidName = parentName + "/" + $(this).text();
                                        }

                                    } else {
                                        hidCode =  $(this).attr("data-code");
                                        hidName =  $(this).text();
                                    }
                                    //只获取所属机构
                                    var hidCode = hidCode.split("/");
                                    $(settings.hidCode).val(hidCode[(hidCode.length - 1)]);
                                    $(settings.hidName).val(hidName);
                                } else {
                                    $(settings.hidCode).val($(this).attr("data-code"));
                                    $(settings.hidName).val($(this).text());
                                }

                                if (settings.getPostList) {
                                    $.ajax({
                                        url: settings.getPostUrl + "?r=" + Math.random(),
                                        type: "GET",
                                        dataType: "json",
                                        data: {
                                            id: $(this).attr("data-code")
                                        },
                                        success: function (d) {
                                            if (d.Ret == 0) { /*获取岗位成功*/
                                                if (d.Data.length != 0) {
                                                    /*有岗位数据*/
                                                    $(settings.PostSelect).empty();
                                                    $.each(d.Data, function (i, item) {
                                                        var options = $('<option value="' + item.Code + '">' + item.Name + '</option>');
                                                        $(settings.PostSelect).append(options);
                                                    });
                                                }
                                            } else {
                                                alert(d.Message);
                                            }
                                        }
                                    });
                                }
                            });

                        });
                    }                    
                } else {
                    alert(d.Message);
                }
            }
        });
    }
});