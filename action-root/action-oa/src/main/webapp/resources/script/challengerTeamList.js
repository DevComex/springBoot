/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：张志玲
 * 联系方式：zhangzhiling@gyyx.cn
 * 创建时间： 2016/7/13
 * 版本号：s

 * 功能：
 * -------------------------------------------------------------------------*/
$(function () {
	  //获取机构列表--结束
    var formatJsonDateStr = function(jsonDateStr) {
		var date = new Date(parseInt(jsonDateStr));
		var dateStr = [];
		dateStr.push(date.getFullYear());
		dateStr.push('-'
				+ (Number(date.getMonth()) + 1));
		dateStr.push('-' + date.getDate());
		dateStr.push(' ' + date.getHours());
		dateStr.push(':' + date.getMinutes());
		dateStr.push(':' + date.getSeconds());
		return dateStr.join('');
	};
	
	getlist();
    function getlist() {
        $("#js_tbSzList").ajaxPage({
            url: rc+"/challenteroa/getTeamListData",//接口
            pageObj: $("#DataTables_Table_0_paginate"),
            pageIndex: 1,
            pageSize: 10,
            curPageCls: "paginate_active",
            pageInfo: {
                obj: $("#DataTables_Table_0_info"),
                content: "共{allCount}条  当前第{pageIndex}/{allPage}页"
            },
            paramObj: {
            },
            dataFn: function (data, $this) {
                var dataHtml = "",dataHtmlmain ="", dCont = data.data;//获取返回数据
                if (dCont && dCont.length && dCont[0]) {
       
                     var isneedact = "";
                     
                    for (var i = 0; i < dCont.length; i++) {
                       
                        dataHtml += '' +
                            '<tr>' +
                             '  <td>' + dCont[i].teamName + '</td>' +
                             '  <td>' + dCont[i].contactType + '-' + dCont[i].contactName + '</td>' +
                             '  <td>' + dCont[i].target + '</td>' + 
                             '  <td>' + dCont[i].declaration + '</td>' +
                             '  <td>' + dCont[i].roleName + '</td>' +
                             '  <td>' + dCont[i].account + '</td>' +
                             '  <td>' + formatJsonDateStr(dCont[i].createTime) + '</td>' +//时间
                             '  <td code="'+dCont[i].code+'"><a href="javascript:;" style="margin: 0 0 0 0;" class="passCreateTeamInfo btn btn-xs btn-success">通过</a><span style="margin:0 10px;">|</span><a href="javascript:;" style="margin: 0 0 0 0;" class="refuseCreateTeamInfo btn btn-xs btn-danger">不通过</a></td>' +//操作
                             '</tr>';

                    }
                    $("#js_tbSzList").empty().html(dataHtml);//展示精选详情内容
                    $(".passCreateTeamInfo,.refuseCreateTeamInfo").live('click',function(e){
                    	var code =  $(this).parent("td").attr("code");
                    	var state= "";
                    	if($(e.target).hasClass("passCreateTeamInfo")){
                    		state = "pass";
                    	}else if($(e.target).hasClass("refuseCreateTeamInfo")){
                    		state = "unpass";
                    	}
                    	$.ajax({
                    		url: rc+"/challenteroa/reviewCreateTeamApply",
                            type: "POST",
                            data: { 
                            	r: Math.random(), 
                            	code:code,
                            	state:state
                            },
                            dataType: "json",

                            success: function (d) {
                                if (d.isSuccess) {
                                	location.reload() 
                                }
                            }
                    	})

                    })
					
                    return dataHtml;
                   
                } else {
                   // alert(data.message)
                    $("#js_tbSzList").empty()
                    $("#scrollable_one").hide();

                }
            }

        });
    }
   


});



