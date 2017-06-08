var offical="";
$.fn.extend({
    //获取文章列表
    getNovel:function (options){
        var $this = $(this),
            defaults = {
                geturl: "/novel/list",
                contobj: $("#js_ArticleList")
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
                                    '    <th>小说名称</th>' +
                                    '    <th>分类</th>' +
                                    '    <th>账号</th>' +
                                    '    <th>小说封面</th>' +
                                    '    <th>更新时间</th>' +
                                    '    <th>章节数</th>' +
                                    '    <th>是否待审</th>' +
                                    '    <th>展示状态</th>' +
                                    '    <th>完结状态</th>' +
                                    '    <th>操作</th>' +
                                    '</tr>';
                    //listhtml = +theadhtml;
                   if(d.dataSet.length>0){
                	   for (var i = 0; i < d.dataSet.length; i++) {
                           var isdisplay = d.dataSet[i].statusName;
                           var UserName = (d.dataSet[i].author === null) ? "无" : d.dataSet[i].author;
                           var button = '';
                           var display = (d.dataSet[i].displayStatus === null) ? "未展示" : d.dataSet[i].displayStatus;
                           var isShowBtn = (d.dataSet[i].isShow=="未展示") ? "展示":"隐藏";
                               
                           if(d.dataSet[i].isShow=="展示" && d.dataSet[i].isPassed=="havePassed"){
                        	    if(d.dataSet[i].isRecommend ==-1){
                        	    	 button =
                                         '<a href="javascript:;" class="btn btn-xs btn-primary  js_category_edit_one" title="目录">目录' +
                                           
                                         '</a>'+
                                         '<a data-id="editAc" data-toggle="modal" href="#CreateRole" data-title="推荐"  class="btn btn-xs btn-info js_category_up_one" title="推荐">推荐'+
                                             
                                         '</a>' +
                                         '<a href="javascript:"  class="btn btn-xs btn-success js_download" data-type="pass" title="下载">下载'+
                                             
                                         '</a>' +
                                         '<a href="javascript:"  class="btn btn-xs btn-danger js_showHide">'+isShowBtn+
                                         '</a>';
                        	    }else{
                        	    	 button =
                                         '<a href="javascript:;" class="btn btn-xs btn-primary  js_category_edit_one" title="目录">目录' +
                                           
                                         '</a>'+
                                         '<a data-id="editAc"  data-title="推荐"  class="btn btn-xs btn-info" title="推荐" style="background:#ccc;border:none;cursor:default;">已推荐'+
                                             
                                         '</a>' +
                                         '<a href="javascript:"  class="btn btn-xs btn-success js_download" data-type="pass" title="下载">下载'+
                                             
                                         '</a>' +
                                         '<a href="javascript:"  class="btn btn-xs btn-danger js_showHide">'+isShowBtn+
                                         '</a>';
                        	    }
                        	  
                           }else{
                        	   button =
                                   '<a href="javascript:;" class="btn btn-xs btn-primary  js_category_edit_one" title="目录">目录' +
                                     
                                   '</a>'+
                                   '<a href="javascript:"  class="btn btn-xs btn-success js_download" data-type="pass" title="下载">下载'+
                                       
                                   '</a>' +
                                   '<a href="javascript:"  class="btn btn-xs btn-danger js_showHide">'+isShowBtn+
                                   '</a>';
                           }
                           
                           listhtml+=''+
                           '<tr class="curr" data-id="' + d.dataSet[i].code + '">' +
                           '    <td>'+
                           '        <input class=" js_ckcom" "data-val="true" type="checkbox" value="' + d.dataSet[i].code + '"></td>'+
                           '    <td>'+d.dataSet[i].name+'</td>'+
                           '    <td>'+d.dataSet[i].title+'</td>'+
                           '    <td class="isOffical" data-name="'+d.dataSet[i].account+'">' + d.dataSet[i].account + '</td>' +
                           '    <td><img src="'+d.dataSet[i].cover+'" alt="" width="100" height="70"></td>'+
                           '    <td>' + d.dataSet[i].latestPublishTime + '</td>' +
                           '    <td>' + d.dataSet[i].chapterCount+ '</td>' +
                           '    <td>' + d.dataSet[i].status + '</td>' +
                           '    <td>' + d.dataSet[i].isShow + '</td>' +
                           '    <td>' + d.dataSet[i].isFinished + '</td>' +
                           '    <td>'+
                                   button+
                           '    </td>'
                           '</tr>';
                       }
                   }else{
                	   listhtml="<tr><td colspan='11' style='color:#f00'>无符合条件数据</td></tr>";
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
                   
                    $("#listcode").val(code);
                    $.ajax({
                        url: '/slot/list',
                        type: 'GET',
                        dataType: 'json',
                        data: {contentType : "novel",r:Math.random()},
                        success:function(d){
                            if (d.status === "success") {
                                var childrenlist1 = '',childrenlist2='';childrenlist3='';
                                for(var  i = 0; i < d.data.length; i++){
                                    if(d.data[i].slotGroup === "小说模块"){
                                        childrenlist1 += '' +
                                            '<label class="btn btn-sm js_tjwz btn-info ' + ' " data-id="' + d.data[i].code + '">' +
                                                '    <input type="checkbox" name="options" ><i class="fa fa-check text-active"></i> ' + d.data[i].slot + '' +
                                            '</label>';
                                    }

                                }
                                var listhtml =  '<div class="form-group">' +
                                                    '<label for="input-id-2" class="col-sm-2 control-label pad_t_10 pad_r_0">' + "小说模块" + '</label>' +
                                                    '<div class="m-b-sm col-sm-10">' +
                                                        '<div class="btn-group" data-toggle="buttons">' +
                                                            childrenlist1 +
                                                        '</div>' +
                                                    '</div>' +
                                                '</div>';
                               
                                $("#js_index_tuij").empty().html(listhtml);

                            }else{
                                alert(d.message);
                            }

                        }
                    });

                });
                //点击目录
                $(".js_category_edit_one").unbind().bind("click",function(event) {
                    var code=$(this).parents("#js_ArticleList tr").attr('data-id');
                    var obj=$("#editAc");
                    var footHtml ='<div class="modal-footer"><button type="button" class="btn btn-info js_enterChapter" data-novelCode="'+code+'">进入章节列表</button></div>';
                    offical = $(this).parents("#js_ArticleList tr").find("td.isOffical").data("name");
                    $(document).alertBox(obj);
                    $.ajax({
                        url: '/novel/novelCatalogue',
                        type: 'POST',
                        dataType: 'json',
                        data: {novelCode : code,r:Math.random()},
                        success:function(d){
                            if(d.isSuccess){
                            	var categoryHtml ='';
                            	for (var i = 0; i < d.data.length; i++) {
                            		if(d.data[i].status=="通过"){
                            			categoryHtml+='<div class="btnDiv"><button data-bcode="'+d.data[i].bcode+'" class="btn btn-info btnClass">'+d.data[i].chapterNum+'</button></div>'
                                			
                            		}else{
                            			categoryHtml+='<div class="btnDiv"><button data-bcode="'+d.data[i].bcode+'" class="btn btn-info btnClass">'+d.data[i].chapterNum+'</button><span>'+d.data[i].status+'</span></div>'
                            			
                            		}
                            		
                            	}
                            	
                            	$(".js_index_edit_category").html(categoryHtml);
                            	$($(".js_index_edit_category").next()).remove();
                            	$(".js_index_edit_category").after(footHtml);
                            	categoryChapterList();
                            }else{
                                alert(d.message);
                            }

                        }
                    });
                });
              //点击进入章节列表
                $("#js_edit_Article").on("click",".js_enterChapter",function () {
                	var oo = offical;
                	 var code=$(this).attr('data-novelcode');
                   location.href ="/novel/chapter?code="+code+"&name="+oo;
                   
                });
                //小说章节列表
                function categoryChapterList(){
                	$('.js_index_edit_category .btnDiv').each(function(index,value){
        				if($(this).find("span").html()=="不通过"||$(this).find("span").html()=="待审核"){
        					$(this).nextAll().find('button').removeClass("btn-info");
        					$(this).nextAll().find('button').css("cursor","auto");
        				}
        			});
                	//点击每一个章节按钮时
                	  $(".js_index_edit_category .btnDiv").click(function(){
                      	if(!$(this).find('button').hasClass("btn-info")){
                      		return false;
                      	}
                      	if($(this).prev().find('span').html()=="待审核"){
                      		alert("不能跨章节审核，请按照章节顺序进行审核！")
                      	}
                      	var code=$(this).find("button").attr('data-bcode');
                      	location.href ="/novel/chapter/view/"+code;
                      	
                      })
                };
                
              
                //点击展示隐藏按钮
                $('#js_ArticleList .js_showHide').click(function(){
                	var code=$(this).parents("#js_ArticleList tr").attr('data-id');
                	$.ajax({
                        url: '/novel/showAndhidden',
                        type: 'POST',
                        dataType: 'json',
                        data: {novelCode : code,r:Math.random()},
                        success:function(d){
                            if(d.isSuccess){
                            window.location.href = window.location.href;
                            }else{
                                alert(d.message);
                            }

                        }
                    });
                	
                })
                //下载
                $(".js_download").click(function(event){
                    var code=$(this).parents("#js_ArticleList tr").attr('data-id');
                    location.href="/novel/download?novelCode="+code
                    
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
        $(".js_close").unbind("click").bind("click", function () {
            $("#Msgmark").hide();
            $this.hide();

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

    //获取小说分类
    getNovelList:function(contentType){
    	 //$(".twocate").empty().html("");
         $("<option></option>").val("").text("全部").appendTo('#onecate');
         $.ajax({
             url: '/category/getCategory',
             type: 'POST',
             dataType: 'json',
             data: {contentType: contentType,r:Math.random()},
             success:function(d){
                if (d.isSuccess) {
                     for (var i = 0; i < d.data.length; i++) {
                         $("<option></option>").val(d.data[i].code).text(d.data[i].title).appendTo('#onecate');
                     }
                    // if (isone) {$("#js_edittwocate").val(editid);}
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
