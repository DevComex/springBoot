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
	
//展示单个具体详情
	getlist();
    function getlist() {
        $("#js_tbSzList").ajaxPage({
            url: rc+"/challenteroa/getChallenterLiveBeansPage",//接口
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
                             '  <td>' + dCont[i].account + '</td>' +//用户
                             '  <td>' + dCont[i].roleName + '</td>' +//角色
                             '  <td>' + dCont[i].player  + '</td>' + //联系类型
                             '  <td>' + dCont[i].liveRadio + '</td>' +//联系方式
                             '  <td>' + formatJsonDateStr(dCont[i].liveTime) + '</td>' +//时间
                             '  <td code="'+dCont[i].code+'"><a href="javascript:;" style="margin: 0 20px 0 0;" class="passUserInfo">通过</a><a href="javascript:;" style="margin: 0 20px 0 0;" class="refuseUserInfo">不通过</a></td>' +//操作
                             '</tr>';

                    }
                    $("#js_tbSzList").empty().html(dataHtml);//展示精选详情内容
                    $(".passUserInfo").live('click',function(){
                    	
                    	var code =  $(this).parent("td").attr("code");
                    	
                    	$.ajax({
                    		url: rc+"/challenteroa/passLiveInfo",
                            type: "POST",
                            data: { 
                            	r: Math.random(), 
                            	code:code
                            },
                            dataType: "json",

                            success: function (d) {
                                if (d.isSuccess) {
                                	location.reload() 
                                }
                            }
                    	})

                    })
                    $(".refuseUserInfo").live('click',function(){
                    	
                    	var code =  $(this).parent("td").attr("code");
                    	
                    	$.ajax({
                    		url: rc+"/challenteroa/refuseLiveInfo",
                            type: "POST",
                            data: { 
                            	r: Math.random(), 
                            	code:code
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
                    alert(data.message)
                    $("#js_tbSzList").empty()
                    $("#scrollable_one").hide();

                }
            }

        });
    }
   


});



