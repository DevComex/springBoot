$.fn.extend({
    //获取文章列表
    getArticle:function (options){
        var $this = $(this),
            defaults = {
                geturl: "/article/list",
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
                if(d.isSuccess){
                    var listhtml = "";
                    var listhtml = '' +
                                    '<tr>' +
                                    '    <th>' +
                                    '        <input class=" " data-val="true" type="checkbox" value=""></th>' +
                                    '    <th>标题</th>' +
                                    '    <th>一级分类</th>' +
                                    '    <th>二级分类</th>' +
                                    '    <th>服务器</th>' +
                                    '    <th>账号</th>' +
                                    '    <th>昵称</th>' +
                                    '    <th>上传时间</th>' +
                                    '    <th>展示状态</th>' +
                                    '    <th>审核状态</th>' +
                                    '    <th>操作</th>' +
                                    '</tr>';
                    //listhtml = +theadhtml;
                    for (var i = 0; i < d.dataSet.length; i++) {
                        var isdisplay = d.dataSet[i].statusName;
                        var UserName = (d.dataSet[i].author === null) ? "无" : d.dataSet[i].author;
                        var button = '';
                        var display = (d.dataSet[i].displayStatus === null) ? "未展示" : d.dataSet[i].displayStatus;
                        if(d.dataSet[i].status === "unreviewd"){ //未审核
                            button =
                            '<a href="/article/view/' + d.dataSet[i].code + '" class="btn btn-xs btn-primary  js_category_ser_one" title="预览">' +
                                '<i class="fa fa-search"></i>'+
                            '</a>'+
                            '<a data-id="editAc" href="javascript:"  class="btn btn-xs btn-info js_category_edit_one" title="编辑">'+
                                '<i class="fa fa-edit"></i>'+
                            '</a>' +
                            '<a href="javascript:"  class="btn btn-xs btn-success js_checkSuccess" data-type="pass" title="审核通过">'+
                                '<i class="fa fa-check"></i>'+
                            '</a>' +
                            '<a href="javascript:"  class="btn btn-xs btn-danger js_checkSuccess" data-type="fail" title="审核不通过">'+
                                '<i class="fa fa-times"></i>'+
                            '</a>';
                        }else if(d.dataSet[i].status === "passed"){ //审核通过
                            button =
                            '<a href="/article/view/' + d.dataSet[i].code + '" class="btn btn-xs btn-primary  js_category_ser_one" title="预览">' +
                                '<i class="fa fa-search"></i>'+
                            '</a>'+
                            '<a data-toggle="modal" href="#CreateRole" data-title="推荐" class="btn btn-xs btn-danger js_category_up_one ' +(isdisplay ? "" :"dn")+ ' " title="推荐">'+
                                '<i class="fa fa-level-up"></i>'+
                            '</a>' ;
                        }else if(d.dataSet[i].status === "failed"){ //审核未通过
                            button =
                            '<a href="/article/view/' + d.dataSet[i].code + '" class="btn btn-xs btn-primary  js_category_ser_one" title="预览">' +
                                '<i class="fa fa-search"></i>'+
                            '</a>' ;
                        }else if(d.dataSet[i].status === "hidden"){ //通过但隐藏
                            button =
                            '<a href="/article/view/' + d.dataSet[i].code + '" class="btn btn-xs btn-primary  js_category_ser_one" title="预览">' +
                                '<i class="fa fa-search"></i>'+
                            '</a>'+
                            '<a data-id="editAc" href="javascript:"  class="btn btn-xs btn-info js_category_edit_one" title="编辑">'+
                                '<i class="fa fa-edit"></i>'+
                            '</a>' ;
                        }else if(d.dataSet[i].status === "recommended"){ //通过切推荐
                            button =
                            '<a  href="/article/view/' + d.dataSet[i].code + '" class="btn btn-xs btn-primary  js_category_ser_one" title="预览">' +
                                '<i class="fa fa-search"></i>'+
                            '</a>'+
                            '<a data-id="editAc" href="javascript:"  class="btn btn-xs btn-info js_category_edit_one" title="编辑">'+
                                '<i class="fa fa-edit"></i>'+
                            '</a>'  ;
                        }
                        listhtml+=''+
                        '<tr class="curr" data-id="' + d.dataSet[i].code + '">' +
                        '    <td>'+
                        '        <input class=" js_ckcom" "data-val="true" type="checkbox" value="' + d.dataSet[i].code + '"></td>'+
                        '    <td>'+d.dataSet[i].title+'</td>'+
                        '    <td>'+d.dataSet[i].firstName+'</td>'+
                        '    <td>' + d.dataSet[i].secondName + '</td>' +
                        '    <td>' + d.dataSet[i].serverName + '</td>' +
                        '    <td>' + d.dataSet[i].account + '</td>' +
                        '    <td>' + d.dataSet[i].author+ '</td>' +
                        '    <td>' + d.dataSet[i].createTime + '</td>' +
                        '    <td>' + display + '</td>' +
                        '    <td>' + d.dataSet[i].statusName + '</td>' +
                        '    <td>'+
                                button+
                        '    </td>'+
                        '</tr>';
                    }

                    return listhtml;
                }else{
                    alert(d.message);

                }

            },
            successFn: function (allCount, eachCount, pgindex) {
                $("#DataTables_Table_0_info").empty().html("共" + allCount + "条 第" + pgindex + "/" + Math.ceil(allCount / 20) + "页");

                //推荐
                $(".js_category_up_one").click(function(event) {
                    // alert("推荐");
                    var code=$(this).parents("#js_ArticleList tr").attr('data-id');
                    //console.log(code);
                    $("#listcode").val(code);
                    $.ajax({
                        url: '/slot/list',
                        type: 'GET',
                        dataType: 'json',
                        data: {contentType : "article",r:Math.random()},
                        success:function(d){
                            if (d.status === "success") {
                                var childrenlist1 = '',childrenlist2='';childrenlist3='';
                                for(var  i = 0; i < d.data.length; i++){
                                    if(d.data[i].slotGroup === "玩家天地首页"){
                                        childrenlist1 += '' +
                                            '<label class="btn btn-sm js_tjwz btn-info ' + ' " data-id="' + d.data[i].code + '">' +
                                                '    <input type="checkbox" name="options" ><i class="fa fa-check text-active"></i> ' + d.data[i].slot + '' +
                                            '</label>';
                                    }else if(d.data[i].slotGroup === "问道官网"){
                                        childrenlist2 += '' +
                                        '<label class="btn btn-sm js_tjwz btn-info ' + ' " data-id="' + d.data[i].code + '">' +
                                            '    <input type="checkbox" name="options" ><i class="fa fa-check text-active"></i> ' + d.data[i].slot + '' +
                                        '</label>';
                                    }else if(d.data[i].slotGroup === "图文驿站"){
                                        childrenlist3 += '' +
                                        '<label class="btn btn-sm js_tjwz btn-info ' + ' " data-id="' + d.data[i].code + '">' +
                                            '    <input type="checkbox" name="options" ><i class="fa fa-check text-active"></i> ' + d.data[i].slot + '' +
                                        '</label>';
                                    }

                                }
                                var listhtml =  '<div class="form-group">' +
                                                    '<label for="input-id-2" class="col-sm-2 control-label pad_t_10 pad_r_0">' + "首页" + '</label>' +
                                                    '<div class="m-b-sm col-sm-10">' +
                                                        '<div class="btn-group" data-toggle="buttons">' +
                                                            childrenlist1 +
                                                        '</div>' +
                                                    '</div>' +
                                                '</div>' +
                                                '<div class="form-group">' +
                                                    '<label for="input-id-2" class="col-sm-2 control-label pad_t_10 pad_r_0">问道官网</label>' +
                                                    '<div class="m-b-sm col-sm-10">' +
                                                        '<div class="btn-group" data-toggle="buttons">' +
                                                            childrenlist2 +
                                                        '</div>' +
                                                    '</div>' +
                                                '</div>'+
                                                '<div class="form-group">' +
                                                '<label for="input-id-2" class="col-sm-2 control-label pad_t_10 pad_r_0">图文驿站</label>' +
                                                '<div class="m-b-sm col-sm-10">' +
                                                    '<div class="btn-group" data-toggle="buttons">' +
                                                        childrenlist3 +
                                                    '</div>' +
                                                '</div>' +
                                            '</div>';
                                $.get("/prize/list",{contentType : "article",r:Math.random()},function(data){
                                    if(data.status === "success"){
                                        var optionS = "<option value=''>请选择奖励</option>";
                                        if(data.data.length > 0){
                                            for(var x = 0; x < data.data.length; x++){
                                                optionS += '<option value="' + data.data[x].code + '" data-prize="' + data.data[x].silverCoins+',' + data.data[x].rmb + '" >' + data.data[x].name + '(' +  data.data[x].title + ')' +  '</option>';
                                            }
                                            listhtml += '<div class="form-group">' +
                                                                '<label for="input-id-2" class="col-sm-2 control-label pad_t_10 pad_r_0">' + "奖励" + '</label>' +
                                                                '<div class="m-b-sm col-sm-10">' +
                                                                    '<select id="js_prizeType">' +
                                                                        optionS +
                                                                    '</selected>' +
                                                                '</div>' +
                                                            '</div>';

                                        }

                                    }
                                    $("#js_index_tuij").empty().html(listhtml);
                                    $("#js_prizeType").change(function(){
                                        if($("#js_prizeTypeChild") || $("#js_prizeTypeSpan")){
                                            $("#js_prizeTypeChild").remove();
                                            $("#js_prizeTypeSpan").remove();
                                        }
                                        var tmp = $("#js_prizeType option:selected").attr("data-prize").split(",");
                                        if($(this).val() === "4" || $(this).val() === "5"){
                                            var selectHtml = '<select id="js_prizeTypeChild"><option value="silverCoins">银元宝：' + tmp[0] + '</option>' + '<option value="rmb">人民币：' + tmp[1] + '</option></select>';
                                            $(this).after(selectHtml);
                                        }else{
                                            var selectHtml = "<span id='js_prizeTypeSpan'>银元宝：" + tmp[0] + "</span>";
                                            $(this).after(selectHtml);
                                        }
                                    });
                                });

                            }else{
                                alert(d.message);
                            }

                        }
                    });

                });
                //推荐 end


                //编辑
                $(".js_category_edit_one").click(function(event) {
                	$(".titleimgs_change").attr("src","");
                	$("#htpou2").html("");   
                    $("#htpou").append(imgCut);
                   // Initialize();
                    $(document).checkre();
                    var code=$(this).parents("#js_ArticleList tr").attr('data-id');
                    //console.log(code);
                    $(document).editArticle(code);
                    var obj=$("#editAc");
                    $(document).alertBox(obj);
                    oAEditPic($(".uploadImgDiv"),$("#js_editPorSubmit"),$('#editPortrait'),$("#addImg"),$(".titleimgs"),"/static/data/UpLoadPhoto.json",$(".close_js_Msg"),"file_uploadPortrait","js_oriImgBox","js_prevImg","js_preCutImg");
                });
                //编辑end
                //审核
                $(".js_checkSuccess").click(function(event){
                    $(document).checkre();
                    var code=$(this).parents("#js_ArticleList tr").attr('data-id');
                    var type = $(this).attr('data-type');
                    //console.log(code);
                    $(document).checkSuccess(code,type);
                });
                $("#js_tbComList").checkFn();

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
            $(".input-edit-bt").val("");
            $("#onecate_add").val("-1");
            $(".js_edittwocate").val("-1");
            $(".js_add_AuthorName").val("官方");
            $(".js_edit_des").val("");
            $(".js_edit_keywords").val("");
            UE.getEditor('editor1').setContent("");
            $("#Msgmark").hide();
            $this.hide();
            $(".uploadImgDiv").empty();
            $(".imgDIV").empty();

        });

    },
    //审核按钮
    checkSuccess:function(code,type){
        $.ajax({
            url: '/article/review',
            type: 'POST',
            dataType: 'json',
            data: {id: code,type: type,r:Math.random()},
            success:function(d){
                if(d.isSuccess){
                    alert("操作成功！");
                    window.location.reload();
                }else{
                    alert(d.message);
                }

            }
        });
    },
    //编辑当前文章详情详情页
    editArticle:function(code){
        // alert(code);
        $.ajax({
            url: '/article/save',
            type: 'GET',
            dataType: 'json',
            data: {code: code,r:Math.random()},
            success:function(d){
                if (d.isSuccess) {
                   
                    $("#input-edit-bt").val(d.data.title);
                    $("#js_edit_keywords").val(d.data.keywords);
                    $("#js_edit_des").val(d.data.summary);
                    $("#js_edit_AuthorName").html(d.data.author);
                    $("#js_edit_onecate").val(d.data.firstCategoryName);
                    $("#aaa").getChildList(d.data.PrimaryCategoryId,d.data.categoryId,true);
                    $("#addImg").attr("src",d.data.cover);
                    $("#addImg").parent().parent().siblings(".titleimgs_change").attr("src",d.data.cover)
                   /*if($(".editPicSrc").length){
                	   $(".editPicSrc").attr("src",d.data.cover);
                	   $("#addImg").hide();
                  }else{
                	   $("#addImg").attr("src",d.data.cover).show().parent().show();
                   }*/
             
                    if(d.data.authorType=='player'){
                    	$("#js_edit_AuthorType").html("玩家");
                    }else{
                    	$("#js_edit_AuthorType").html("官方");
                    }
                    $("#js_edit_onecate").val(d.data.parentId);
                    $("#js_ThisarticleImage").attr("src",d.data.cover);
                    /*百度编辑内容*/
                    UE.getEditor('editor').ready(function () {
                        var content = $("#content").val();
                        UE.getEditor('editor').setContent(decodeURI(d.data.content));
                    });
                    var str="";
                    str=code;
                    $("#listdata").val(str);
                }else{
                    alert(d.message);
                }

            }
        });

        $("#js_edit_onecate").off("change").change(function () {
            var id = $("#js_edit_onecate").val();
            $("#aaa").getChildList(id);
        });
    },

    //编辑当前文章详情详情页 post数据
    postEditArticle:function(options){
    		console.log(options)
        var defaults = {
            getListUrl: "/article/save",
            pushdataobj:{}
        };
        $.extend(defaults, options);
        var dataobj = {
            t: new Date().getTime()
        };
        $.extend(dataobj, defaults.pushdataobj);
        //验证
        //提交
        $.ajax({
            url: defaults.getListUrl,
            type: 'POST',
            dataType: 'json',
            data: dataobj,
            success:function(d){
                if (d.isSuccess) {
                    alert(d.message);
                    $(".close").trigger('click');
                    if ($("#js_pagecont .paginate_active").text() !== "") {
                        pageindex = parseInt($("#js_pagecont .paginate_active").text());
                        $(".paginate_active").trigger("click");
                    }
                    //重置表单
                    $(".input-edit-bt").val("");
                    $("#onecate_add").val("-1");
                    $(".js_edittwocate").val("-1");
                    $(".js_add_AuthorName").val("");
                    $(".js_edit_des").val("");
                    $(".js_edit_keywords").val("");
                    UE.getEditor('editor1').setContent("");
                    //window.location.href=window.location.href;
                }else{
                    alert(d.message);
                }
            },
            error:function(d){
                alert(d.message);
            }
        });

    },





    //表格中全(不)选
    checkFn: function (options) {
        var $this = $(this),
            thck = $this.find("input:checkbox").eq(0),
            tdcks = $this.find("input:checkbox").not(":first");
        thck.click(function () {
            if (this.checked) {
                for (var i = 0; i < tdcks.length; i++) {
                    tdcks[i].checked = true;
                }
            } else {
                for (var i = 0; i < tdcks.length; i++) {
                    tdcks[i].checked = false;
                }
            }
        });
        for (var i = 0; i < tdcks.length; i++) {
            tdcks[i].onclick = function () {
                var flag = true;
                for (var j = 0; j < tdcks.length; j++) {
                    if (!tdcks[j].checked) {
                        flag = false;
                    }
                }
                thck[0].checked = flag;
            };
        }
    },

    // 获取选中的项目
    check:function(){
        var checkbox=$("#js_ArticleList").find("input:checkbox");
        //console.log(checkbox);

    },

    //批量展示
    batchShow: function (options) {
        var str,incomingType,type,
            $this = $(this),
            defaults = {
                type: 'show',// show：批量展示  hide：批量隐藏   3：批量删除
                checkCls: ".js_ckcom"
            };
        $.extend(defaults, options);


        // var comArr = [defaults.type];
        var comArr = [];
        var comChecks = $this.find(defaults.checkCls);


        for (var i = 0; i < comChecks.length; i++) {
            if (comChecks.eq(i)[0].checked) {
                comArr.push(comChecks.eq(i).val());
            }
        }

        //console.log(comArr,comArr.length);

        if (comArr.length === 0) {
            alert("您尚未选择任何文章！");
            return false;
        }

        if (defaults.type=="show") { //展示
            incomingType="Operation"; type='show';
        }else if(defaults.type=="hide"){ //隐藏
            incomingType="Operation"; type='hide';
        }else{ //删除
            incomingType="dele"; type=1;
        }


        //str = comArr.join("|");

        //更新状态
        $.ajax({
            url: "/article/review/batchshow",
            type: "POST",
            dataType: "JSON",
            data: {
                r: Math.random(),
                ids: comArr,
                //IncomingType:incomingType,
                type:type
            },
            success: function (d) {
                if (d) {
                    alert(d.message);
                    if ($("#js_pagecont .paginate_active").text() !== "") {
                        pageindex = parseInt($("#js_pagecont .paginate_active").text());
                        $(".paginate_active").trigger("click");
                    }
                    //window.location.href = window.location.href;
                }else{
                    alert(d.message);
                }
            }
        });
    },




    //文章分类,获取子类
    getChildList:function(id,editid,isone){
        $(".twocate").empty().html("");
        $("<option></option>").val("-1").text("全部").appendTo('.twocate');
        $.ajax({
            url: '/category/getCategory',
            type: 'POST',
            dataType: 'json',
            data: {categoryType: id,r:Math.random()},
            success:function(d){
                // alert(d.toSource());
                if (d.isSuccess) {
                    for (var i = 0; i < d.data.length; i++) {
                        $("<option></option>").val(d.data[i].code).text(d.data[i].title).appendTo('.twocate');
                    }
                    if (isone) {$("#js_edittwocate").val(editid);}
                }else{
                    alert(d.message);
                }
            }
        });

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

    //展示状态转换
    changeDisplay: function (str){
        if (str) {return "已展示";}else{ return "未展示";}
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
                getPicListFn({
                    pageIndex: thistext
                });
                //if (curHidVal[0] == thistext) {
                //    return false;
                //} else {
                //    getPicListFn({
                //        pageIndex: thistext
                //    });
                //}
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
            pageSize: defaults.pageSize
        };

        $.extend(dataobj,_defaults.pushdataobj);

        $.ajax({
            url: defaults.getListUrl,
            type: _defaults.ajaxtype,
            dataType: _defaults.ajaxdatatype,
            data: dataobj,
            success: function (data) {
                if (data) {
                    if (data.isSuccess !== true) {
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
