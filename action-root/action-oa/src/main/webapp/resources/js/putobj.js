$(function(){
    var showobjlisturl = "/novice-oa/channel/list/page";//显示所有投放信息列表
    var updateputobjurl = "/novice-oa/channel/update";//更新当前投放对象信息
	var saveputobjurl = "/novice-oa/channel/add";//添加投放对象信息
	var b;
    //显示投放对象列表信息
	page(1);
    function page(pageon){
		$("#table4 tbody").ajaxPage({//列表展示区
	        url: showobjlisturl,
	        type:"post",
	        pageObj: $("#DataTables_Table_0_paginate"),
	        index: pageon,
	        pageCount: 10,//每页显示数量
	        curPageCls: "paginate_active",
	        pageInfo: {
	            obj: $("#DataTables_Table_0_info"),
	            content: "共{allCount}条  当前第{pageNo}/{allPage}页"
	        },
	        paramObj: {
	            //传递参数在这边写、
                r:Math.random(),
				putType:0,
				putName:""
	        },
	        dataFn: function (data, $this) {
	        	if(data.isSuccess){
	        		if ($(".js_pageipt").val() > Math.floor(b / 1 -0.1)+1) {
		                alert("页码超出范围");
		                page($(".paginate_active").attr("pgid"));
		                return false;
		            }
		            else {console.log( $("#ipage").val() )
		                var dataHtml = "", dCont = data.data;
		                if (dCont && dCont.length && dCont[0]) {
							var index;
		                    for (var i = 0; i < dCont.length; i++) {
								if(!$("#ipage").length || $("#ipage").val()==1){
									if(i==9){
										index = pageon + "0";
									}else{
										index = (i+1);
									}
								}else{
									if(i==9){
										index = $("#ipage").val() + "0";
									}else{
										index = ($("#ipage").val()-1) + "" +(i+1);
									}
								}
		                        dataHtml += '<tr><td>'+ index +'</td>'+
                                            '<td><select class="js_puttype" disabled="disabled">'+ (dCont[i].putType==1 ? "<option selected=\"selected\" value=\"1\">落地页</option><option value=\"2\">非落地页</option>" : "<option value=\"1\">落地页</option><option selected=\"selected\" value=\"0\">非落地页</option>") +'</select></td>'+
                                            '<td class="js_putname">'+ dCont[i].putName +'</td>'+
                                            '<td><input type="text" class="js_url" value="'+ dCont[i].putUrl +'" disabled="disabled"/></td>'+
                                            '<td><a class="js_edit" code="'+dCont[i].code+'" data_ifpage="'+ (dCont[i].putType==1 ? "1":"0") +'" style="color: '+ (dCont[i].putType==1 ? "#1e91ff" : "gray") +' " >编辑</a></td></tr>';
		                    }
		                    $("#table4 tbody").empty().html(dataHtml);
		                    b = data.totalPage;
		                    return dataHtml;
		                    
		                } else if (data.data == null || data.data.length==0) {
		
		                    $("#table4 tbody").empty().html('<tr><td colspan="5">暂无数据</td></tr>');
		
		                } else {
		                    $("#table4 tbody").empty().html('<tr><td colspan="5">'+d.message+'</td></tr>');
		                }
		                
		            }
		            
	        	}else{
	        		$("#table4 tbody").empty().html('<tr><td colspan="5">'+data.message+'</td></tr>');
	        	}
	            
	        },
	        errorFn:function(){
	        	
	        }
	    });
	}
    //更新信息
    function updateputobj(code,putName,putType,putUrl){
        $.ajax({
            url:updateputobjurl,
            type:"post",
            data:{r:Math.random(),code:code,putName:putName,putType:putType,putUrl:putUrl,description:""},
            success:function(d){
                if(d.isSuccess){
					alert(d.message);
					window.location.reload();
				}else{
					alert(d.message);
				}
            },
            error:function(){
                alert("数据获取错误");
            }
        })
    }
	//保存信息
    function saveputobj(putName,putType,putUrl){
        $.ajax({
            url:saveputobjurl,
            type:"post",
            data:{r:Math.random(),putName:putName,putType:putType,putUrl:putUrl,description:""},
            success:function(d){
				if(d.isSuccess){
					alert(d.message);
					window.location.reload();
				}else{
					alert(d.message);
				}
            },
            error:function(){
                alert("数据获取错误");
            }
        })
    }
	$("#putobjtype").on("change",function(){
		if($("#putobjtype option:selected").val()==1){
			$("#groundadd").removeAttr("disabled");
		}else{
			$("#groundadd").attr("disabled","disabled");
		}
	})
    $("#table4").on("click",".js_edit",function(){
        var def = $(this).attr("data_ifpage");//是否是落地页 0非 1是
        if(def==1){
            $(this).parents("tr").find(".js_url").removeAttr("disabled");
            $(this).html("保存").addClass("js_save").removeClass("js_edit");
			$(this).css("color","#1e91ff");
        }else{
			$(this).css("color","gray");
		}
    })
    $("#table4").on("click",".js_save",function(){
        var puttype = $(this).parents("tr").find(".js_puttype option:selected").val();
		var putname = $.trim($(this).parents("tr").find(".js_putname").html());
		var puturl = $.trim($(this).parents("tr").find(".js_url").val());
		var code = $(this).attr("code");
		var reg = /^(http:\/\/)(.*)$/;
		if(putname==""){
			alert("投放对象名称不能为空");
			return false;
		}
		if(putname.length>20){
			alert("投放对象名称需小于20个字");
			return false;
		}
		if(puturl!=""){
			if(!reg.test(puturl)){
				alert("请输入正确的地址");
				return false;
			}
		}
		updateputobj(code,putname,puttype,puturl)
    })
	$("#js_footer").on("click",".js_save",function(){
        var puttype = $("#putobjtype option:selected").val();
		var putname = $.trim($("#putobjname").val());
		var puturl = $.trim($("#groundadd").val());
		var reg = /^(http:\/\/)(.*)$/;
		if(putname==""){
			alert("投放对象名称不能为空");
			return false;
		}
		if(putname.length>20){
			alert("投放对象名称需小于20个字");
			return false;
		}
		if(puturl!=""){
			if(!reg.test(puturl)){
				alert("请输入正确的地址");
				return false;
			}
		}
		
		saveputobj(putname,puttype,puturl);
    })
})