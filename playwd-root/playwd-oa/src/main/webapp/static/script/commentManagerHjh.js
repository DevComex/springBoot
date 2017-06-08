
$(function(){
    (function(){
        htmlLayout();
    })();
    $(".js_query").click(function(){
        htmlLayout(1,$(".js_column").val(),$(".js_keyword").val(),$(".js_showStatus").val(),$(".js_Account").val(),$(".js_title").val(),$(".js_startTime").val(),$(".js_endTime").val());
    });
    $(".js_tbodyBox").on("click",".js_changeStatus",function(){
        var a = [];
        a.push($(this).attr("data-code"));
        $.post("/comment/review",{
            status:$(this).attr("data-show"),
            r:Math.random(),
            codeIteam :a
        },function(d){
            alert(d.message);
            if(d.isSuccess){
                htmlLayout();
            }
        });
    });
    $(".js_allStatus").click(function(){
        var a =[];
        $("input[type='checkbox']").each(function(){
            if($(this).prop("checked")){
                a.push($(this).attr("data-code"));
            }
        });
        $.post("/comment/review",{
            status:$(this).attr("data-show"),
            r:Math.random(),
            codeIteam :a
        },function(d){
            alert(d.message);
            if(d.isSuccess){
                htmlLayout();
            }
        });
    });
    $(".js_tbodyBox").on("click",".js_searchThis",function(){
        $.post("/comment/viewDetails",{
            code : $(this).attr("data-code"),
            r:Math.random()
        },function(d){
            if(d.isSuccess){
                var html ="";
                for (var i = 0; i < d.data.length; i++) {
                    html += '' +
                        '<div class="boxWord">'+
                            '<div class="userName">'+
                                '<p>' + d.data[i].account +' :</p>'+
                            '</div>'+
                            '<div class="content">'+
                                d.data[i].content +
                            '</div>'+
                            '<div class="creatTime">'+
                                '<p>' + d.data[i].create_time + '</p>'+
                            '</div>'+
                        '</div>';
                }
                $(".modalBody .form-group").empty().html(html);
            }
        });
    });
});
function htmlLayout (index,contentType,content,isShow,account,title,startTime,endTime){
    var pageindex = index|| $("#pageIndex").val() || 1;
    $(".js_tbodyBox").ajaxPageS({
        url:"/comment/list",
        pageObj: $("#DataTables_Table_0_paginate"),
        pageSize: 10,//每页条数
        pageIndex: pageindex,//当前页
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
            contentType : contentType,    //所属栏目
            content :content ,
            isShow :isShow ,
            account :account ,
            title :title ,
            startTime :startTime ,
            endTime :endTime
        },
        dataFn: function (data, $this) {
            var Data = data.dataSet;
            var trHtml ="";
            if(data.isSuccess){
            	if(data.count > 0){
            		if(Data && Data.length){
                        for(var i =0;i<Data.length;i++){
                            var btn =(Data[i].is_show)?'<a  href="javascript:;" data-code="' + Data[i].code + '" class="js_changeStatus btn btn-xs btn-danger" data-show="hidden" title="隐藏" data-original-title="隐藏"><i class="fa fa-times"></i></a>':'<a  href="javascript:;" data-original-title="展示" title="展示" class=" btn btn-xs btn-success js_changeStatus" data-code="' + Data[i].code + '" data-show="show"><i class="fa fa-check"></i></a>';
                            trHtml += '' +
                                '<tr>' +
                                    '<td><input type="checkbox" data-code="' + Data[i].code + '" /></td>' +
                                    '<td><div style="width:100%;height:100px;overflow:hidden">' + Data[i].content + '</div></td>' +
                                    '<td>' + Data[i].show_type + '</td>' +
                                    '<td>' + Data[i].account + '</td>' +
                                    '<td>正常</td>' +
                                    '<td>' + Data[i].create_time + '</td>' +
                                    '<td>' + Data[i].title + '</td>' +
                                    '<td>' + Data[i].content_type + '</td>' +
                                    '<td>' +
                                        btn +
                                        '<a data-toggle="modal" href="#layer-topic" data-code="' + Data[i].code + '" class="btn btn-xs btn-success js_searchThis" data-original-title="查看详情"><i class="fa fa-search"></i></a>' +
                                    '</td>' +
                                '</tr>';
                        }
                        $(".js_tbodyBox").html(trHtml);
                        $("#pageIndex").val(data.pageIndex);
                        return trHtml;
                    }
            	} else {
            		$(".js_tbodyBox").html('<td style="text-align:center;font-size: 24px;padding-top:100px;"  colspan="11" rowSpan="3">暂无数据~</td>');
            	}

            }else{
            	alert(data.message);
            }

        }

    });
}
