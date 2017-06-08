/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：李根
 * 联系方式：ligen@gyyx.cn
 * 创建时间： 2014/12/15
 * 版本号：v1.0
 * 内容： 用户组脚本
 * -------------------------------------------------------------------------*/
$(function () {
    //新增用户组
    $(".js_adduserg").click(function () {
        var groupName = $.trim($(".js_userg").val());
        if (groupName == "") {
            alert("请输入用户组！")
        } else {
            $.ajax({
                url: '/UserGroup/Create',
                type: "get",
                data: {
                    r: Math.random(),
                    groupName: groupName
                },
                dataType: "json",
                success: function (d) {
                    if (d.Ret == 0) {
                        $(".js_userg").val("");
                        alert(d.Message);
                        window.location.href = window.location.href;
                    } else {
                        alert(d.Message)
                    }
                }
            });
        }
    });
    //删除用户组
    $(".js_deluserg").click(function () {
        var code = $(this).attr("data-code");
        if (confirm("确定删除用户组么？")) {
            $.ajax({
                url: '/UserGroup/Remove',
                type: "get",
                data: {
                    r: Math.random(),
                    code: code
                },
                dataType: "json",
                success: function (d) {
                    if (d.Ret == 0) {
                        alert(d.Message);
                        window.location.href = window.location.href;
                    } else {
                        alert(d.Message)
                    }
                }
            });
        } return false;
    });
    //编辑用户组
    $(".js_usergedit").click(function () {
        $(this).hide();
        $(this).next("a").show();
        $(this).parent().parent().find(".js_gname").hide();
        $(this).parent().parent().find(".js_hideedit").show();
    });
    //编辑用户组提交
    $(".js_usergsave").click(function () {
        var code = $(this).attr("data-code");
        var groupName = $.trim($(this).parent().parent().find(".js_hideedit").val());
        if (groupName == "") {
            alert("请输入用户组！")
        } else {
            $.ajax({
                url: '/UserGroup/Edit',
                type: "get",
                data: {
                    r: Math.random(),
                    code:code,
                    groupName: groupName
                },
                dataType: "json",
                success: function (d) {
                    if (d.Ret == 0) {
                        alert(d.Message);
                        window.location.href = window.location.href;
                    } else {
                        alert(d.Message)
                    }
                }
            });
        }
    });
    //查看组内人员
    $(".js_usergview").click(function () {
        var code = $(this).attr("data-code");
        var usergtitle = $(this).parent().parent().find(".js_gname").text();
        $(".js_usergtitle").html(usergtitle);
        $.ajax({
            url: '/UserGroup/GetStaffs',
            type: "get",
            data: {
                r: Math.random(),
                code: code
            },
            dataType: "json",
            success: function (d) {
                var viewlist = "";
                if (d.length > 0) {
                    for (var i = 0; i < d.length; i++) {
                        if (i == 0) {
                            viewlist += '<span data-delangcode="' + d[0].Code + '" data-delcode="63" class="jgfzrs">' + d[0].Name + '</span>';
                        } else {
                            viewlist += '<span data-delangcode="' + d[i].Code + '" data-delcode="63" class="jgfzrs">' + d[i].Name + '</span>';
                        }
                    }
                } else {
                    viewlist += "无";
                }
                $(".js_viewlist").html(viewlist);
            }
        });
        });
    //配置用户组成员
    $(".js_usergset").click(function () {
        //关闭弹层刷新列表
        $(".js_usergclose").click(function () {
            window.location.href = window.location.href;
        });
        $(".close").click(function () {
            window.location.href = window.location.href;
        });
        var code = $(this).attr("data-code");
        $(".js_usergaddpeop").attr("data-code", code);
        var addagencys = '    <div id="OaSearchStaffEdit2" class="form-control" style="width: 100%; min-height: 38px;"><input type="text" class="js_oaSearch" value=""><input type="hidden" class="js_oaSearchResult" name="js_oaSearchResult" value="" /></div>';
        $(".js_addagens").html(addagencys);
        //添加机构负责人
        $("#OaSearchStaffEdit2").OaSearch({});
        var addspeop = '<div style="cursor: text; height: auto; min-height: 37px; overflow: hidden; border: 1px solid rgb(217, 217, 217);" class="form-control js_addeds"></div>';
        $(".js_addpeop").html(addspeop);
        getusergpeo(code)//获取已添加成员
    });
    //添加成员
    $(".js_usergaddpeop").click(function () {
        var code = $(this).attr("data-code");
        var staffCodes = $("input[name = 'js_oaSearchResult']").val();
        $.ajax({
            url: '/UserGroup/AddUserGroupStaffsRelative',
            type: "get",
            data: {
                r: Math.random(),
                code: code,
                staffCodes: staffCodes
            },
            dataType: "json",
            success: function (d) {
                if (d.Ret == 0) {
                    alert(d.Message);
                    getusergpeo(code);//获取已添加成员
                    var addagencys = '    <div id="OaSearchStaffEdit2" class="form-control" style="width: 100%; min-height: 38px;"><input type="text" class="js_oaSearch" value=""><input type="hidden" class="js_oaSearchResult" name="js_oaSearchResult" value="" /></div>';
                    $(".js_addagens").html(addagencys);
                    //添加机构负责人
                    $("#OaSearchStaffEdit2").OaSearch({});
                } else {
                    alert(d.Message)
                }
            }
        });
    });
    //获取已添加成员
    function getusergpeo(code) {
        $.ajax({
            url: '/UserGroup/GetStaffs',
            type: "get",
            data: {
                r: Math.random(),
                code: code
            },
            dataType: "json",
            success: function (d) {
                var viewlist = "";
                if (d.length > 0) {
                    for (var i = 0; i < d.length; i++) {
                        if (i == 0) {
                            viewlist += '<span data-delangcode="' + d[0].Code + '" class="jgfzr"><i class="fa fa-times"></i>' + d[0].Name + '</span>';
                        } else {
                            viewlist += '<span data-delangcode="' + d[i].Code + '" class="jgfzr"><i class="fa fa-times"></i>' + d[i].Name + '</span>';
                        }
                    }
                } else {
                    viewlist += "无";
                }
                $(".js_addpeop").html(viewlist);
                //删除已添加人员
                $(".jgfzr").click(function () {
                    var staffCodes = $(this).attr("data-delangcode");
                    delusergpeo(code, staffCodes)
                });
            }
        });
    };
    //删除已添加人员
    function delusergpeo(code, staffCodes) {
        $.ajax({
            url: '/UserGroup/RemoveUserGroupStaffsRelative',
            type: "get",
            data: {
                r: Math.random(),
                code: code,
                staffCode: staffCodes
            },
            dataType: "json",
            success: function (d) {
                if (d.Ret == 0) {
                    alert(d.Message);
                    getusergpeo(code);
                } else {
                    alert(d.Message)
                }
            }
        });
    };
});
