$.fn.extend({
    //获取文章列表
    getArticle:function (options){
        var $this = $(this),
            defaults = {
                geturl: "/recommend/record/list",
            };
        $.extend(defaults, options);

        pagingFn({
            geturl:defaults.geturl,
            contobj: $("#js_ArticleList"),
            pageobj: $("#js_pagecont"),
            curclassname: "paginate_active",
            pushdataobj: defaults.dataobj,
            pagecount: 20,
            dataFn:function(d){
                var listhtml = "";
                var listhtml = '' +
                                '<tr>' +
                                '    <th>标题</th>' +
                                '    <th>服务器</th>' +
                                '    <th>上传账户</th>' +
                                '    <th>玩家昵称</th>' +
                                '    <th>查看量</th>' +
                                '    <th>推荐时间</th>' +
                                '    <th>地址</th>' +
                                '    <th>奖励等级</th>' +
                                '    <th>备注</th>' +
                                '    <th>操作</th>' +
                                '</tr>';
                //listhtml = +theadhtml;
                for (var i = 0; i < d.dataSet.length; i++) {
                    var UserName = (d.dataSet[i].account === null) ? "无" : d.dataSet[i].account;
                    var ServerName = (d.dataSet[i].ServerName === null) ? "官方" : d.dataSet[i].serverName;
                    var remark = (d.dataSet[i].Remark === null) ? "" : d.dataSet[i].Remark;
                    listhtml+=''+
                    '<tr class="curr">' +
                    '    <td>'  +d.dataSet[i].title+'</td>'+
                    '    <td>' + ServerName + '</td>' +
                    '    <td>' + UserName + '</td>' +
                    '    <td>' + d.dataSet[i].author + '</td>' +
                    '    <td>' + d.dataSet[i].viewCount + '</td>' +
                    '    <td>' + d.dataSet[i].recommmendTime + '</td>' +

                    '    <td>' + d.dataSet[i].url + '</td>' +
                    '    <td>' + d.dataSet[i].prizeName + '</td>' +
                    '    <td class="js_remark">' + d.dataSet[i].remark + '</td>' +
                    '    <td><a data-toggle="tooltip" data-title="修改" href="javascript:;" class="btn btn-xs btn-info js_edit" data-original-title="修改" title=""><i class="fa fa-edit"></i></a><a data-code="' + d.dataSet[i].code + '" data-toggle="tooltip" data-title="保存" href="javascript:;" class="btn btn-xs btn-success js_save" style="display:none;" data-original-title="" title=""><i class="fa fa-edit"></i></a></td>' +
                    '</tr>';
                }
                return listhtml;

            },
            successFn: function (allCount, eachCount, pgindex) {
                $("#DataTables_Table_0_info").empty().html("共" + allCount + "条 第" + pgindex + "/" + Math.ceil(allCount / 20) + "页");
                //修改备注
                //编辑
                $(".js_edit").off("click").on("click", function () {
                    var editTd = $(this).parents("tr").find(".js_remark");
                    var Str = "<input type='text' class='js_edit_input' value='" + editTd.text() + "'  />";
                    editTd.empty("").append(Str);
                    $(this).hide().siblings(".js_save").show();
                });
                //保存编辑
                $(".js_save").off("click").on("click", function () {
                    var editTd = $(this).parents("tr").find(".js_remark"),
                        editValue = editTd.find(".js_edit_input").val(),
                        that=$(this),
                        _code = $(this).attr("data-code");
                    if ($(document).charlength(editValue) > 20) { alert("备注字符过长，最多输入10个汉字"); return false; }
                    if (/[`~!@#\$%\^\&\*\(\)_\+<>\?:"\{\},\.\\\/;'\[\]]/.test(editValue)) { alert("备注不能输入特殊字符"); return false; }
                    $.ajax({
                        url: "/recommend/record/remark",
                        data: { contentId : _code, remark: editValue,r:Math.random(),contentType :"article" },
                        type: "post",
                        success: function (d) {
                            alert(d.message);
                            if (d.isSuccess) {
                                editTd.html("").text(editValue);
                                that.hide().siblings(".js_edit").show();
                            }
                        }
                    });
                });

            }

        });

    },

    alertBox:function(obj){
        $("#Msgmark").show();
        var $this=obj;
        $this.show();
        var winMsgwidth = $(window).width();
        var thisMsgleft = winMsgwidth / 2 - 300;
        var thisMsgtop = $(window).height() / 2 - $this.height() / 2 + $(window).scrollTop();
        var bodyMsgheight = Math.max($(document.body).outerHeight(true), $(window).height());
        $(".markMsg").css({"height":bodyMsgheight, position:"fixed"});
        $this.css({ 'left': thisMsgleft, 'top': thisMsgtop, position:"fixed" });

        $(".js_close").unbind().bind("click", function () {
            $("#Msgmark").hide();
            $this.hide();
        });

    },
    choosePrize:function(){
        $.get("/prize/list",{contentType :"article",r:Math.random()},function(data){
            if(data.status === "success"){
                var optionS = "<option value='0'>全部</option>";
                if(data.data.length > 0){
                    for(var x = 0; x < data.data.length; x++){
                        optionS += '<option value="' + data.data[x].code + '" data-prize="' + data.data[x].silverCoins+',' + data.data[x].rmb + '" >' + data.data[x].name  +  '</option>';
                    }
                    $("#js_choosePrize").html(optionS);

                }else{

                }

            }
        });
    },
    //计算字符长度
    charlength:function (value) {
        var length = value.length;
        for (var i = 0; i < value.length; i++) {
            if (value.charCodeAt(i) > 127) {
                length++;
            }
        }
        return length;
     },

    //时间转换
    changeTime:function  (time) {
        if (time !== null) {
            var date = new Date(parseInt(time.replace("/Date(", "").replace(")/", ""), 10));
            var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
            var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            var currentHour = date.getHours();
            var currentMin = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
            var currentSec = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
            return date.getFullYear() + "-" + month + "-" + currentDate + " " + currentHour + ":" + currentMin + ":" + currentSec;
        } else {
            return "";
        }
    },


    //隐藏提示
    checkre:function () {
        $("#editAc .js_error_title_err").hide(); $("#addAc .js_error_title_err").hide();
        $("#editAc .js_edit_onecate_err").hide(); $("#addAc .js_edit_onecate_err").hide();
        $("#editAc .js_edittwocate_err").hide(); $("#addAc .js_edittwocate_err").hide();
        $("#editAc .js_edit_AuthorType_err").hide(); $("#addAc .js_edit_AuthorType_err").hide();
        $("#editAc .js_edit_AuthorName_err").hide(); $("#addAc .js_edit_AuthorName_err").hide();
        $("#editAc .js_edit_des_err").hide(); $("#addAc .js_edit_des_err").hide();
        $("#editAc .js_edit_keywords_err").hide(); $("#addAc .js_edit_keywords_err").hide();
        $("#editAc .js_errtip1").hide();$("#editAc .js_errtip2").hide();
        $("#addAc .js_errtip3").hide();$("#addAc .js_errtip4").hide();
    }

});

//分页
function pagingFn(options) {
    var _defaults = {
        geturl: "http://p.cn/sss.txt",
        contobj: $("#js_ulcont"),
        pageobj: $("#js_pagecont"),
        hdName: "",
        lihtml: '<li><img src="{para1}" /><h2>{para2}</h2></li>',
        paraarr: ["VideoAddress", "VideoTitle"],
        cookiename: "ck_cur_page2",
        hidobjid: "hid_curpage1",
        curclassname: "on",
        pushdataobj: {},
        btnword: {
            first: "首页",
            last: "末页",
            prev: "上页",
            next: "下页"
        },
        ajaxtype: "GET",
        ajaxdatatype: "JSON",
        dataFn: function (dCont) {
            var lihtml = "";
            return lihtml;
        },
        successFn: function () {

        }
    };
    $.extend(_defaults, options);
    getPicListFn({
        pageIndex: 1
    });

    //获取cookie值
    function getCookie(name) {
        var str = document.cookie.split(";");
        for (var i = 0; i < str.length; i++) {
            var str2 = str[i].split("="), reg = new RegExp("\\b" + name + "\\b");
            return reg.test(str2[0]) ? str2[1] : "";
        }
        return false;
    }

    //为分页按钮绑定事件
    function pgbtnBind(options) {
        options.pagewrapobj.find("a").off("click").on("click", function () {
            var thistext = $(this).text(),
                curHidVal = $("#" + options.hidobjid)[0].value.split("|"),
                isfirst = curHidVal[0] == 1,
                islast = curHidVal[0] == curHidVal[1];
            if (parseInt(thistext)) {//如果是数字按钮

                if (curHidVal[0] == thistext) {
                    return false;
                } else {
                    getPicListFn({
                        pageIndex: thistext
                    });
                }
            } else {//如果是文字按钮
                switch (thistext) {
                    case options.btnword.prev://上一页
                        if (isfirst) {
                            return false;
                        } else {
                            getPicListFn({ pageIndex: parseInt(curHidVal[0]) - 1 });
                        }
                        break;
                    case options.btnword.next://下一页
                        if (islast) {
                            return false;
                        } else {
                            getPicListFn({ pageIndex: parseInt(curHidVal[0]) + 1 });
                        }
                        break;
                    case options.btnword.first://第一页
                        if (isfirst) {
                            return false;
                        } else {
                            getPicListFn({ pageIndex: 1 });
                        }
                        break;
                    case options.btnword.last://最后一页
                        if (islast) {
                            return false;
                        } else {
                            getPicListFn({ pageIndex: parseInt(curHidVal[1]) });
                        }
                        break;
                    default:
                        return false;
                }
            }
            return false;
        });
    }

    //绑定数据列表
    function getPicListFn(options) {
        var defaults = {
            getListUrl: _defaults.geturl,                               //获取数据列表接口
            pageIndex: 1,                                               //当前页，默认为第1页
            pageSize: _defaults.pagecount,                              //单页文选条数，默认为6条
            datalistWrapObj: _defaults.contobj,
            pageWrapObj: _defaults.pageobj,
            hdName: _defaults.hdName,
            cookIndex: _defaults.cookiename,
            hiddenIndex: _defaults.hidobjid
        };
        $.extend(defaults, options);

        var dataobj = {
            t: new Date().getTime(),
            pageIndex: defaults.pageIndex,
            pageSize: defaults.pageSize,
        };
        $.extend(dataobj, _defaults.pushdataobj);
        $.ajax({
            url: defaults.getListUrl,
            type: _defaults.ajaxtype,
            dataType: _defaults.ajaxdatatype,
            data: dataobj,
            success: function (data) {
                if (data) {
                    if (!data.isSuccess) {
                        defaults.datalistWrapObj.empty().html("<tr><td colspan='11' style='color:#f00'>无符合条件数据</td></tr>");
                        $("#DataTables_Table_0_info").empty();
                        $("#js_pagecont").empty();
                        return false;
                    }
                    var lihtml = "",                                         //数据html累积
                        allCount = data.count,                      //总数量
                        eachCount = defaults.pageSize,                       //每页数量
                        paraarr = _defaults.paraarr,
                        dCont = data;

                    //遍历数据
                    if (_defaults.dataFn) {
                        lihtml += _defaults.dataFn(dCont);
                    } else {
                        for (var i = 0; i <  data.Data.List.length; i++) {
                            var flag = _defaults.lihtml;
                            for (var j = 0; j < paraarr.length; j++) {
                                flag = flag.replace("{para" + (j + 1) + "}", data.Data.List[i][paraarr[j]]);
                            }
                            lihtml += flag;
                        }
                    }

                    //装载列表数据
                    defaults.datalistWrapObj.empty().html(lihtml);
                    //分页
                    var pagehtml = pageFn({
                        p_allcount: allCount,
                        p_eachcount: eachCount,
                        p_curpage: defaults.pageIndex,
                        ck_curpage: defaults.cookIndex,
                        hd_curpage: defaults.hiddenIndex
                    });
                    //装载分页
                    defaults.pageWrapObj.empty().html(pagehtml);

                    if (_defaults.successFn) {
                        _defaults.successFn(allCount, eachCount, defaults.pageIndex);

                        pgbtnBind({
                            pagewrapobj: _defaults.pageobj,
                            hidobjid: _defaults.hidobjid,
                            btnword: _defaults.btnword
                        });
                    }
                }
            }
        });
    }

    //分页
    function pageFn(options) {
        var p_allcount = parseInt(options.p_allcount),                                                                           //总数量
            p_eachcount = parseInt(options.p_eachcount),                                                                     //每页数量
            p_allpage = Math.ceil(p_allcount / p_eachcount),                                                                    //总页数
            p_curpage = parseInt(options.p_curpage),                                                                            //当前页
            pagehtml = '<a href="javascript:;">' + _defaults.btnword.prev + '</a><a href="javascript:;">' + _defaults.btnword.first + '</a>';

        if (!$("#" + options.hd_curpage).length) {//如果页面不存在存储当前页码的hidden则创建一个hidden
            $("body").append("<input type='hidden' id='" + options.hd_curpage + "' />");
        }
        //将当前页及末页存入hidden
        $("#" + options.hd_curpage).val(p_curpage + "|" + p_allpage);
        //将当前页存入cookie
        document.cookie = options.ck_curpage + "=" + p_curpage;

        //如果设置当前页超过总页数
        p_curpage = p_curpage > p_allpage ? p_allpage : p_curpage;
        //如果设置当前页小于0
        p_curpage = p_curpage < 0 ? 1 : p_curpage;

        //页面公用函数提取
        function pagenum(i) {
            if ((i + 1) == p_curpage) {
                pagehtml += '<a class="' + _defaults.curclassname + '" href="javascript:;">' + (i + 1) + '</a>';
            } else {
                pagehtml += '<a href="javascript:;">' + (i + 1) + '</a>';
            }
        }
        if (p_allpage < 6) {//如果页面小于6页时，显示全部页码
            for (var i = 0; i < p_allpage; i++) {
                pagenum(i);
            }
        } else {//如果页面大于6页时：
            //当前页的后两页是否最后一页
            var isLast = (p_curpage + 2) < p_allpage,
                aDot = '<a class="nobg" href="javascript:;">...</a>';
            if (p_curpage < 4) {//如果当前页小于4时，可见数量最大为5然后加...
                for (var i = 0; i < 5; i++) {
                    pagenum(i);
                }
                pagehtml += aDot;
            } else if (p_curpage < 6) {//如果当前页大于4小于6时，显示0--总页数或当前页+2
                for (var i = 0; i < (isLast ? (p_curpage + 2) : p_allpage) ; i++) {
                    pagenum(i);
                }
                isLast ? pagehtml += aDot : '';
            } else {//如果当前页大于6时，显示1，2，...,当前页-3,--总页数或当前页加2
                for (var i = 0; i < 2 ; i++) {
                    pagehtml += '<a href="javascript:;">' + (i + 1) + '</a>';
                }
                pagehtml += aDot;
                for (var i = (p_curpage - 3) ; i < (isLast ? (p_curpage + 2) : p_allpage) ; i++) {
                    pagenum(i);
                }
                isLast ? pagehtml += aDot : '';
            }
        }

        pagehtml += '<a href="javascript:;">' + _defaults.btnword.last + '</a><a href="javascript:;">' + _defaults.btnword.next + '</a>';

        return pagehtml;

    }

}
