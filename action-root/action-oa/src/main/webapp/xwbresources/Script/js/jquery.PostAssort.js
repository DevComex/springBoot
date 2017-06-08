/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：李根
 * 联系方式：ligen@gyyx.cn
 * 创建时间： 2014/12/1 
 * 版本号：v1.0

 * 功能： 岗位分类脚本

 * -------------------------------------------------------------------------*/

$(function () {
    //添加分类
    $(".js_addSort").click(function () {
        var subjipt = $(".js_sortjectipt").val();
        var reg = /^[\u4e00-\u9fa5]{1,5}$/i;
        if (!reg.test(subjipt)) {
            alert("专题名称只能小于等于5个汉字")
        } else {
            $.ajax({
                url: '/PostAssort/Create',
                type: "get",
                data: {
                    sortName: subjipt,
                    r: Math.random()
                },
                dataType: "json",
                success: function (d) {
                    if (d.Ret == 0) {
                        alert(d.Message);
                        window.location.href = window.location.href;
                    } else if (d.Ret == -4) {
                        alert(d.Message);
                    }
                }
            });
        }
    });
    //删除分类
    $(".js_delSort").click(function () {
        if (confirm('确认要删除吗？')) {
            $.ajax({
                url: "/PostAssort/Remove/" + "?sortCode=" + $(this).attr("data-code") + "&r=" + Math.random(),
                type: "GET",
                dataType: "json",
                success: function (d) {
                    if (d.Ret == 0) {                       
                        window.location.reload();
                    } else {
                        alert(d.Message);
                    }
                }
            });
        }
    });
    //查看详情
    $(".js_details").click(function () {
        var sortCode = $(this).attr("data-code");
        $.ajax({
            url: "/PostAssort/Details",
            type: "get",
            dataType: "json",
            data: {
                r: Math.random(),
                sortCode: sortCode
            },
            success: function (d) {
                if (d.Ret == 0) {
                    var vmlisted = '';
                    for (var i = 0; i < d.vm.length; i++) {
                        vmlisted += '<span style="width: 100%; display: block; padding: 3px 0 0 0;">' + d.vm[i].SortName + '</span>';
                        for (var j = 0; j < d.vm[i].Posts.length; j++) {
                            vmlisted += '<span class="label label-info" style="margin:2px 0 0 2px;" data-postcode="' + d.vm[i].Posts[j].PostCode + '">' + d.vm[i].Posts[j].Name + '</span>';
                        }
                        vmlisted += '<div class="line pull-in"></div>';
                    }
                    $(".js_vgdlist").html(vmlisted);
                }
            }
        });
    });
    //获取机构列表
    $(".js_agencySelect").agencySelect({
        hidCode: $("#CreatePost").find("input[name='AgencyCode']"), /*隐藏inputID*/
        hidName: $("#CreatePost").find("input[name='AgencyName']") /*hidden*/
    });
    //搜索岗位分类
    $(".js_searchPost").click(function () {
        var createPostId = $("#CreatePost");
        var agencyCode = createPostId.find("input[name='AgencyCode']").val();
        var postName = createPostId.find("input[name='Name']").val();
        noadd(agencyCode, postName);
        added();
    });
    //未添加到分类的岗位列表
    function noadd(agencyCode, postName) {
        $.ajax({
            url: "/PostAssort/GetUnsortedPost",
            type: "get",
            dataType: "json",
            data: {
                r: Math.random(),
                agencyCode: agencyCode,
                postName: postName
            },
            success: function (d) {
                if (d.Ret == 0) {
                    var vmlist = '<div class="line pull-in" style="float:left;width:100%;"></div><span style="width: 100%; display: block; padding: 3px 0 0 0;font-weight: bold;float:left;">未添加的岗位:</span>';
                    for (var i = 0; i < d.vm.length; i++) {
                        vmlist += '<span class="spantitle">' + d.vm[i].SortName + '</span>';
                        for (var j = 0; j < d.vm[i].Posts.length; j++) {
                            vmlist += '<span class="label label-info js_addPost" data-postcode="' + d.vm[i].Posts[j].Code + '">' + d.vm[i].Posts[j].Name + '</span>';
                        }
                    }
                    $(".js_AgencyList").html(vmlist);
                    //添加岗位
                    $(".js_addPost").on("click", function () {
                        var postCode = $(this).attr("data-postcode");
                        addpost(postCode);
                    });
                }
            }
        });
    };
    //已添加到分类的岗位列表
    function added() {
        $.ajax({
            url: "/PostAssort/Details",
            type: "get",
            dataType: "json",
            data: {
                r: Math.random(),
                sortCode: $(".js_hidcode").val()
            },
            success: function (d) {
                if (d.Ret == 0) {
                    var vmlists = '<div class="line pull-in" style="float:left;width:100%;"></div><span style="width: 100%; display: block; padding: 3px 0 0 0;font-weight: bold;float:left;">已添加的岗位:</span>';
                    for (var i = 0; i < d.vm.length; i++) {
                        vmlists += '<span class="spantitle">' + d.vm[i].SortName + '</span>';
                        for (var j = 0; j < d.vm[i].Posts.length; j++) {
                            vmlists += '<span class="label label-success js_delPost" data-postcode="' + d.vm[i].Posts[j].PostCode + '">' + d.vm[i].Posts[j].Name + '</span>';
                        }
                    }
                    $(".js_AgencyLists").html(vmlists);
                    //删除岗位
                    $(".js_delPost").on("click", function () {
                        var postCode = $(this).attr("data-postcode");
                        delpost(postCode);
                    });
                } else {
                    $(".js_AgencyLists").html('<div class="line pull-in" style="float:left;width:100%;"></div><span style="width: 100%; display: block; padding: 3px 0 0 0;font-weight: bold;float:left;">已添加的岗位:</span>');
                }
            }
        });
    };
    //记录当前点击的分类code
    $(".js_addpo").click(function () {
        $(".js_AgencyList, .js_AgencyLists").html("");
        $(".js_hidcode").val($(this).attr("data-code"));
        added();
    });
    //添加到已添加岗位类表
    function addpost(postCode) {
        //关闭弹层刷新
        $(".close_js_Msg , .close").bind("click", function () {
            window.location.reload();
        });
        $.ajax({
            url: "/PostAssort/AddPostToSort",
            type: "get",
            dataType: "json",
            data: {
                r: Math.random(),
                sortCode: $(".js_hidcode").val(),
                postCode: postCode
            },
            success: function (d) {
                if (d.Ret == 0) {
                    var createPostId = $("#CreatePost");
                    var agencyCode = createPostId.find("input[name='AgencyCode']").val();
                    var postName = createPostId.find("input[name='Name']").val();
                    noadd(agencyCode, postName);
                    added();
                }
            }
        });
    };
    //删除已添加岗位类表
    function delpost(postCode) {
        $.ajax({
            url: "/PostAssort/RemovePostFromSort",
            type: "get",
            dataType: "json",
            data: {
                r: Math.random(),
                sortCode: $(".js_hidcode").val(),
                postCode: postCode
            },
            success: function (d) {
                if (d.Ret == 0) {
                    var createPostId = $("#CreatePost");
                    var agencyCode = createPostId.find("input[name='AgencyCode']").val();
                    var postName = createPostId.find("input[name='Name']").val();
                    noadd(agencyCode, postName);
                    added();
                }
            }
        });
    };
});