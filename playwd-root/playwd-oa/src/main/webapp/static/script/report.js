/**
 * Created by Administrator on 2017/4/18.
 */

var
    reportListUrl = "/report/list",     //评论管理页面展示
    viewDetailsUrl = "/comment/viewDetails",  //查看详情
    processUrl = "/report/process";  //展示隐藏

$(function () {
    var newTime = new Date(), startTime = '', end = "", startM = "", startY = "", startD = "";
    //开始时间
    startY = newTime.getFullYear(); //获取当前年份
    startM = newTime.getMonth() + 1; //获取当前月份
    startTime += startY + '-' + startM + '-' + 1;
    //结束时间
    var firstdate = startY + '-' + startM + '-01';
    var day = new Date(startY, startM, 0);
    var endTime = startY + '-' + startM + '-' + day.getDate();//获取当月最后一天日期
    //页面加载进来
    reportListPage(startTime, endTime);
    $(".js_startTime").val(startTime);
    $(".js_endTime").val(endTime);
    //点击查询：
    $(".js_query").click(function () {
        var startDate = $.trim($(".js_startTime").val());  //开始时间
        var endDate = $.trim($(".js_endTime").val());    //结束时间
        reportListPage(startDate, endDate);
    });

    //点击关闭按钮
    $(".js_close").click(function () {
        $(".js_maskBox").hide();
    });

    //点击查看详情
    $(".js_tbodyBox").on("click", ".js_details", function () {
        $(".js_maskBox").show();
        $.ajax({
            url: viewDetailsUrl,
            type: "get",
            dataType: "json",
            data: {
                r : Math.random(),
                code : $(this).attr("data-code")
            },
            success: function (data) {
                var Data = data.data, trHtml = "";
                if (data.isSuccess) {
                    if (Data && Data.length) {
                        for (var i =0;i<Data.length;i++){
                            trHtml += '<tr>'+
                                '<td style="width:14%;">'+Data[i].account+'</td>'+
                                '<td style="width:25%">'+Data[i].create_time+'</td>'+
                                '<td>'+Data[i].content  +'</td>'+
                                '</tr>'
                        };
                        $(".js_popTbody").html(trHtml);
                    };

                }else{
                    alert(data.message);
                };
            },
            error:function(){

            }
        });
    });

    //点击展示和隐藏
    $('.js_tbodyBox').on('click','.js_show, .js_hide',function (){
        $.ajax({
            url:processUrl,
            type:"post",
            dataType:"json",
            data:{
                r:Math.random(),
                reportId : $.trim($(this).attr("codeID")),
                commentStatus :$.trim($(this).attr("commentStatus"))
            },
            success:function(data){
                if (data.isSuccess) {
                    alert(data.message);
                    window.location.reload();//刷新当前页面.
                } else {
                    alert(data.message);
                };
            },
            error:function(){

            }
        });
    });


})

//评论管理页面展示
function reportListPage(startDate,endDate){
    $(".js_tbodyBox").ajaxPageS({
        url : reportListUrl,
        pageObj: $("#DataTables_Table_0_paginate"),
        pageSize: 20,//每页条数
        pageIndex: 1,//当前页
        curPageCls: "paginate_active",
        pageInfo: {
            obj: $("#DataTables_Table_0_info"),
            content: "共{allCount}条  当前第{pageIndex}页"
        },
        pageBtn: {
            first: "首页",
            last: "末页",
            prev: "上一页",
            next: "下一页"
        },
        paramObj: {
            r: Math.random(),
            contentType : $.trim($(".js_column").val()),    //所属栏目
            status : status=$.trim($(".js_processingState").val()),  //处理状态
            startDate : startDate,  //开始时间
            endDate : endDate,    //结束时间
            commentAccount : $.trim($(".js_reportedAccount").val()),   //被举报账号
            reportAccount : $.trim($(".js_reportAccount").val())    //举报账号
        },
        dataFn: function (data, $this) {
            var Data = data.dataSet;
            var trHtml ="";
            if(data.isSuccess){
                if(data.count > 0){
                    if(Data && Data.length){
                        for(var i =0;i<Data.length;i++){
                            '<a data-toggle="modal" href="javascript:;" class="btn btn-xs btn-primary js_details" data-code='+Data[i].commentId+' title="查看详情">'+
	                            '<i class="fa fa-search"></i>'+
	                        '</a>'
                            var btn = (Data[i].status == "已处理")?  '<td>'+
                            											'<a data-toggle="modal" href="#layer-topic" class="btn btn-xs btn-primary js_details" data-code='+Data[i].commentId+' title="查看详情">'+
								    	                            		'<i class="fa fa-search"></i>'+
								    	                            	'</a>'+
								    	                            '</td>':
										                            '<td>'+
											                            '<a  codeID='+Data[i].code+' commentStatus ="hidden" data-toggle="modal" href="javascript:;" class="btn btn-xs btn-danger js_hide" title="隐藏">'+
											                            	'<i class="fa fa-ban"></i>'+
											                            '</a>'+
											                            '<a codeID='+Data[i].code+' commentStatus ="show"  data-toggle="modal" href="javascript:;" class="btn btn-xs btn-info js_show" title="展示">'+
											                            	'<i class="fa fa-unlock"></i>'+
											                            '</a>'+
											                            '<a data-toggle="modal" href="#layer-topic" class="btn btn-xs btn-primary js_details" data-code='+Data[i].commentId+' title="查看详情">'+
								    	                            		'<i class="fa fa-search"></i>'+
								    	                            	'</a>'+
										                            '</td>'
                            trHtml += '<tr>'+
                                '<td>'+Data[i].code+'</td>'+
                                '<td>'+Data[i].content+'</td>'+
                                '<td>'+Data[i].reason+'</td>'+
                                '<td>'+Data[i].reportTime+'</td>'+
                                '<td>'+Data[i].commentAccount+'</td>'+
                                '<td>'+Data[i].commentAccountStatus+'</td>'+
                                '<td>'+Data[i].reportAccount+'</td>'+
                                '<td>'+Data[i].reportAccountStatus+'</td>'+
                                '<td>'+Data[i].status+'</td>'+
                                '<td>'+Data[i].result+'</td>'+
                                btn+
                                '</tr>'
                        };
                        $(".js_tbodyBox").html(trHtml);
                        return trHtml;

                    };
                } else {
                    $(".js_tbodyBox").html('<td style="text-align:center;font-size: 24px;padding-top:100px;"  colspan="11" rowSpan="3">暂无数据~</td>');
                };

            }else{
                alert(data.message);
            };

        }

    });

};
