var url={
			downLoadUrl: rc + "/message/down",//下载附件
			getRecommendUrl: rc + "/recommend/fieldsList",//获取推荐位
			saveRecommendUrl : rc + "/recommend/saveFields",//提交修改推荐位置
			editorUrl:rc + "/review/showAllBook",//编辑章节
			getBookPic:rc + "/review/showBookInfoAndPage",//显示本章节中的图片
			submitBookPicUrl:rc + "/review/reviewFinish",//提交修改的章节
			deleteThisBookUrl:rc+"/review/reviewFailed",//删除本张杰
	};
$(function(){

	
	//获取区组
	Block();	
	
    //获取漫画所有类型
	acquisitionType();
	
	//页面加载进来-table
	query();		
	
	//点击查询筛选
	$(".js_query").click(function(){
		query();
	});
	
	//点击漫画名称的全选框
	$("#js_whole").click(function(){   
	    if(this.checked){   
	        $("#js_table :checkbox").prop("checked", true);  
	    }else{   
	    	$("#js_table :checkbox").prop("checked", false);
	    };  
	});
	
	
	//点击批量展示的时候
	$("#js_exhibition").click(function () {
		bookCode();  //获取bookCode值

		if($("input[type='checkbox']").is(':checked')){			
			$.ajax({
				url: rc+"/review/showAll",
		        type: "GET",
		        dataType: "json",
		        data: {
		            r: Math.random,
		            bookCode:check_val
		        },
		        success: function (data) {
		        	if(data.success){
		        		alert(data.message);
		        		query();
		            }else{
		            	alert(data.message);
		            };
		        }
			});
		}else{
			alert("您尚未选中任何记录");
		};
	});
	
	//点击批量隐藏
	$("#js_batchHide").click(function () {
		bookCode();  //获取bookCode值
		
		if($("input[type='checkbox']").is(':checked')){
			$.ajax({
				url: rc+"/review/hideAll",
		        type: "GET",
		        dataType: "json",
		        data: {
		            r: Math.random,
		            bookCode:check_val
		        },
		        success: function (data) {
		        	if(data.success){
		        		alert(data.message);
		        		query(); 
		            }else{
		            	alert(data.message);
		            };
		        }
			});  
		}else{
			alert("您尚未选中任何记录");
		};
	});
	
	//点击批量删除
	$("#js_batchDelete").click(function () {
		bookCode();  //获取bookCode值
	    
		if($("input[type='checkbox']").is(':checked')){
			$.ajax({
				url: rc+"/review/statusFailAll",
		        type: "GET",
		        dataType: "json",
		        data: {
		            r: Math.random,
		            bookCode:check_val
		        },
		        success: function (data) {
		        	if(data.success){
		        		alert(data.message);
		        		query(); //成功后将删除的内容删掉
		        		
		            }else{
		            	alert(data.message);
		            };
		        }
			});
		}else{
			alert("您尚未选中任何记录");
		}
	});
	
	//下载zip
//	$("#js_table").on("click",".js_downLoadBtn",function(){
//		obj = $(this);
//		$.get( url.downLoadUrl,{bookCode : $(this).parent().data("code"),r:Math.random()},function(d){
//			var zip = d.data;
//			$("#downloadZip").attr("href",zip);
//			document.getElementById("downloadZip").click();
//
//		})
//	});
	//关闭推荐弹层
	$(".js_closeRe").click(function(){
		$(".recommendBox").fadeOut();
		$(".js_recommendBtn").removeClass("btn-info");
		$("#js_leavel").val(-1);
	})
	//切换是否选中状态
	$(document).on("click",".js_recommendBtn",function(){
		if($(this).attr("data-bool") == 0){
			$(this).attr("data-bool",1);
			$(this).addClass("btn-info");
			
		}else{
			$(this).attr("data-bool",0);
			$(this).removeClass("btn-info");
			
		}
	});
	//点击推荐提示弹层
	$("#js_table").on("click",".js_recommendTo",function(){
		$(".recommendBox").fadeIn();
		$(".js_submitAll").attr("data-code",$(this).parent().data("code"));
		$.get(url.getRecommendUrl,{manhuaCode:$(this).parent().data("code"),r:Math.random()},function(d){
			var html ='',a='';
			for( var i =0;i<d.data.length; i++){
				if(d.data[i].bool == true){
					a = 'disabled="disabled"';
				}else{
					a='';
				}
				html += '<a href="javascript:;" ' + a + ' class="btn btn-xs btn-success js_recommendBtn" data-bool="0"  data-type="' + d.data[i].index + '">' + d.data[i].name + '</a>';
			}
			$(".js_tuijianwei").html(html);
		});
		
	});
	//点击推荐为确定按钮
	$(".js_submitAll").click(function(){
		
		
		var a = [];
		if($("#js_leavel option:selected").val() == -1){
			alert("没选奖励等级！")
			return false;
		}
		$(".js_recommendBtn").each(function(){
			if($(this).attr("class") == "btn btn-xs btn-success js_recommendBtn btn-info"){
				a.push($(this).data("type"));	
			}
		})
		if(a.length == 0){
			alert("还没选推荐位！")
			return false;
		}
		$.get(url.saveRecommendUrl,{manhuaCode:$(this).attr("data-code"),rewarLevel:$("#js_leavel option:selected").val(),locationId:a,r:Math.random()},function(d){
			alert(d.message);
			$(".recommendBox").hide();
			$("#js_leavel").val(-1);
		});
	})
	//编辑按钮
	$("#js_table").on("click",".js_editor",function(){

	 $.ajax({
			url: url.editorUrl,
	        type: "GET",
	        dataType: "json",
	        data: {
	            r: Math.random,
	            bookCode:$(this).data("bookcode")
	        },
	        success: function (data) {
	        	if(data.success){
	        	
	        		$(".charapter_list").empty();
	        		var html="";
	        		for (var i = 0; i < data.data.length; i++) {
	        			html+=" <div class='charapter_view_oneCharapter_circle'  ><div class='charapter_view_oneCharapter_status_circle'>";
		          		if (data.data[i].review_status == "REVIEW_ING") {
		          			html+="<p class='charapter_view_oneCharapter_status'>待审核</p>";
		          		}
		          		html+="</div><div class='charapter_view_oneCharapter' href='javascript:;' bookCode='"+data.data[i].code+"' reviewStatus='"+data.data[i].review_status+"'><span>"+data.data[i].book_num+"</span></div></div>";
	        		}
	        		$(".charapter_list").append(html);
	        		
	            }else{
	            	alert(data.message);
	            };
	        }
		});
	 $("#editor_charapter_view").show();
 });
	
	//关闭漫画编辑弹层
	$(".charapter_view_backBtn").click(function(){
		$(this).parent().hide();
	})
	//打开章节
	$(document).on("click",".charapter_view_oneCharapter",function(){
		$("#editor_charapter_view").hide();
		var buttonReviewStatus=$(this).attr("reviewStatus");
		$.get(url.getBookPic,{bookCode:$(this).attr("bookCode"),r:Math.random()},function(d){
		
			var html ='';
			//名字
			$(".charapter_review_title").html(d.data.manhuaBook.bookName);
			//
			$(".js_finishTheBookEditor,.js_deleteThisBook").attr("data-code",d.data.manhuaBook.code);
			for(var i =0; i<d.data.pageList.length; i++){
				html +='<div class="manhuaPic"><img src="' + d.data.pageList[i].page_picture_url.replace("-small","") + '"></div>'
			}
			$(".charapter_review_pic").html(html);
			
			$("#editor_charapter_review_view").show();
			if(buttonReviewStatus != "REVIEW_ING"){
				$(".charapter_review_reply").hide();
				$(".js_deleteThisBook").hide();
				$(".js_finishTheBookEditor").click(function(){
					$("#editor_charapter_review_view").hide();
					$(".js_finishTheBookEditor").unbind("click");
				})
				
			}else{
				$(".charapter_review_reply").show();
				$(".js_deleteThisBook").show();
				$(".js_finishTheBookEditor").click(function(){
					
					var picArray = [],
						reply = $("#charapter_review_reply_context_input").val();
					if(reply.length>300){
						alert("请少于300字");
						return false;
					}
					$(".manhuaPic img").each(function(){
						picArray.push($(this).attr("src"));
					});
					$.get(url.submitBookPicUrl,{bookCode:$(this).attr("data-code"),pagesUrl:picArray,reply:reply,r:Math.random()},function(d){
						 alert(d.message)
						 if(d.success){
							 
							 $("#editor_charapter_review_view").hide();
						 }
						 
					 })
					$("#charapter_review_reply_context_input").val("");
					$(".js_finishTheBookEditor").unbind("click");
				})
				
			}
			
		})
	})
	$(document).on("click",".manhuaPic",function(){
		if($(this).attr("class") == "manhuaPic choose"){
			$(this).removeClass("choose");
		}else{
			$(this).addClass("choose").siblings().removeClass("choose");
		}
	})
	//点击移动图片位置
	$(".js_movePic").click(function(){
		var code=$(this).data("code");
		var obj = $(".choose"); 
		movePic(obj,code);
	});
	//删除图片
	$(".js_deletePic").click(function(){
		$(".choose").remove();
	});
	//删除本章
	$(".js_deleteThisBook").click(function(){
		$.get(url.deleteThisBookUrl,{bookCode:$(this).data("code")},function(d){
			alert(d.message)
			if(d.success){
				$("#editor_charapter_review_view").hide();
			}
		})
	})
	//关闭审核弹层
	$(".js_closeReviewPic").click(function(){
		$("#editor_charapter_review_view").hide();
	})

	
 
 
 
 
 
});
//移动图片位置
function movePic(obj,move){
	var _index = obj.index();
	
	//如果为正则为后移动
	if(move == 1){
		obj.parent().find(".manhuaPic").eq(_index+1).after(obj);
	}else{//如果为负则为前移
		obj.parent().find(".manhuaPic").eq(_index-1).before(obj);
	}
}
//提交审核中的章节


//获取区组
function Block(){
	$("#js_NetId").change(function (){
		var val = "";
		val = $(this).find("option:selected").attr("value");
		var thisval = this.value;
		if(val == 1 || val == 2 || val == 3){
		    var opthtml = '<option>请选择服务器</option>';
			$.ajax({
		        url: rc+"/review/serverlist",
		        type: "GET",
		        dataType: "JSON",
		        data: {	            
		            r: Math.random(),
		            netType: thisval
		        },
		        success: function (data) {
		        	var dCont = data.data;
		        	if(data.success){
		        		if (dCont.data && dCont.data.length) {
		                    for (var i = 0; i < dCont.data.length; i++) {
		                    	opthtml += '<option value="'+dCont.data[i].ServerName+'" code="'+dCont.data[i].Code+'">'+dCont.data[i].ServerName+'</option>'
		                    };
		                    $('#js_ServerIdAndName').html(opthtml);
		                    
		                    return opthtml;
		                };
		        	}else{
		        		alert(data.message);
		        	};
		            
		        }
		    });
		}else{
			$('#js_ServerIdAndName').html("<option>请选择区组</option>");
		}
		
	});
};


//获取漫画类型
function acquisitionType(){
	$.ajax({
		url: rc+"/review/getalltype",
        type: "GET",
        dataType: "json",
        data: {
            r: Math.random
        },
        success: function (data) {
        	if(data.success){
            	if(data.data.length && data.data){
            		var selectHtml = '<option>请选择漫画类型</option>';
            		for(var i = 0; i < data.data.length;i++){
            			selectHtml += '<option value="'+data.data[i].code+'">'+data.data[i].name+'</option>'
            		};
            		$("#js_cartoonType").html(selectHtml);
            		return selectHtml;
            	};
            };
        }
	});
}

//获取bookCode的值
function bookCode(){
	obj = document.getElementsByName("checkbox");
	check_val = [];
	for(k in obj){
	    if(obj[k].checked)
	    check_val.push(obj[k].value);
	};
};


//查询
function query(){

	var netType = $("#js_NetId").find("option:selected").attr("value");  //获取区组
	
	var areaCode = $("#js_ServerIdAndName").find("option:selected").attr("code");  //获取小区组
	
	var reviewStatus = $(".js_examineSelect").find("option:selected").attr("value");    //审核状态
	
	var categoryCode = $("#js_cartoonType").find("option:selected").attr("value");     //漫画类型的code
	
	var isShow  =   $(".js_currentSelect").find("option:selected").attr("value");       //展示状态
	
	var isClosed  = $(".js_stateSelect").find("option:selected").attr("value");         //是否完结
		
	if($(".js_nameInput").val() == ""){  //如果漫画名称没有填，置为空
		var manhuaTitle  = undefined;
	}else{
		var manhuaTitle  = $(".js_nameInput").val();          //漫画名称
	};
	
	if($("#startTime").val() == ""){//如果开始时间没有填，置为空
		var startTime = undefined;
	}else{
		var startTime  = new Date($("#startTime").val()); 
	};
	if($("#endTime").val() == ""){//如果结束时间没有填，置为空
		var endTime = undefined;
	}else{
		var endTime  = new Date($("#endTime").val());
	};
	$.ajax({ 
		url: rc+"/review/filter",
        type: "GET",
        dataType: "json",
        data: {
            r: Math.random, 
            netType:netType,  //服务器大区
            areaCode:areaCode,   //服务器小区组
            reviewStatus:reviewStatus,    //审核状态
            categoryCode:categoryCode,  //漫画类型的code
            isShow:isShow,              //展示状态
            manhuaTitle:manhuaTitle,   //漫画名称
            isClosed:isClosed ,     //是否完结
            startTime:startTime,   //开始时间	
            endTime:endTime      //结束时间
            
        },
        success: function (data) {
        	if(data.success){
        		var Data=data.data;
        		//点击预览的时候

            	if(Data.length && Data){
            		var trHtml ="";
            		   
            		for(var i=0;i<Data.length;i++){
            			if(Data[i].is_delete == "已展示"){  //0--展示
            				trHtml += '<tr>'+
					                        '<td><input type="checkbox"  class="js_check" name="checkbox" value="'+Data[i].book_code+'"/>'+Data[i].title+'</td>'+
					                        '<td>'+Data[i].book_name+'</td>'+
					                        '<td>'+Data[i].category_name+'</td>'+
					                        '<td>'+Data[i].server_name+'</td>'+
					                        '<td>'+Data[i].author_account+'</td>'+
					                        '<td>'+Data[i].author_name+'</td>'+
					                        '<td>'+Data[i].create_time+'</td>'+
					                        '<td><img src="'+Data[i].page_picture_url+'" style="width:50px;height:50px;"/></td>'+
					                        '<td>'+Data[i].read_count+'</td>'+
					                        '<td>'+Data[i].is_delete+'</td>'+
					                        '<td>'+Data[i].is_closed+'</td>'+
					                        '<td  data-code="' + Data[i].manhua_code + '">'+
												'<a title="" class="btn btn-xs btn-default js_downLoadBtn" target="_blank" href="http://wanwdv2.oa.gyyx.cn/message/down?bookCode=' + Data[i].book_code + '" data-title="下载" data-toggle="tooltip" data-original-title="下载">'+
					                                '<i class="fa fa-download"></i>'+
					                            '</a>'+
					                            '<a title="" class="btn btn-xs btn-info js_search" href="http://wanwdv2.oa.gyyx.cn/preview?bookCode=' + Data[i].book_code + '" data-title="搜索" data-toggle="tooltip" data-original-title="搜索" bookCode="'+Data[i].book_code+'" >'+
					                                '<i class="fa fa-search"></i>'+
					                            '</a>'+
												'<a title="" class="btn btn-xs btn-success js_editor" href="javascript:void(0)" data-title="编辑" data-toggle="tooltip" data-original-title="编辑" data-bookCode="'+Data[i].book_code+'">'+
					                                '<i class="fa fa-edit"></i>'+
					                            '</a>'+
					                            '<a title="" class="btn btn-xs btn-gplus js_recommendTo" href="javascript:void(0)" data-title="推荐" data-toggle="tooltip" data-original-title="推荐"> 推荐'+
					                               	'<i class="fa fa-level-up"></i>'+
					                            '</a>'+
											'</td>'+
				    					'</tr>'
                        }else{//1--未展示
                        	trHtml += '<tr>'+
					                        '<td><input type="checkbox"  class="js_check" name="checkbox" value="'+Data[i].book_code+'"/>'+Data[i].title+'</td>'+
					                        '<td>'+Data[i].book_name+'</td>'+
					                        '<td>'+Data[i].category_name+'</td>'+
					                        '<td>'+Data[i].server_name+'</td>'+
					                        '<td>'+Data[i].author_account+'</td>'+
					                        '<td>'+Data[i].author_name+'</td>'+
					                        '<td>'+Data[i].create_time+'</td>'+
					                        '<td><img src="'+Data[i].page_picture_url+'" style="width:50px;height:50px;"/></td>'+
					                        '<td>'+Data[i].read_count+'</td>'+
					                        '<td>'+Data[i].is_delete+'</td>'+
					                        '<td>'+Data[i].is_closed+'</td>'+
					                        '<td data-code="' + Data[i].manhua_code + '">'+
												'<a title="" class="btn btn-xs btn-default js_downLoadBtn" target="_blank" href="http://wanwdv2.oa.gyyx.cn/message/down?bookCode=' + Data[i].book_code + '" data-title="下载" data-toggle="tooltip" data-original-title="下载">'+
					                                '<i class="fa fa-download"></i>'+
					                            '</a>'+
					                            '<a title="" class="btn btn-xs btn-info js_search" href="http://wanwdv2.oa.gyyx.cn/preview?bookCode=' + Data[i].book_code + '" data-title="搜索" data-toggle="tooltip" data-original-title="搜索" bookCode="'+Data[i].book_code+'" data-title="搜索" data-toggle="tooltip" data-original-title="搜索" bookCode="'+Data[i].book_code+'" >'+
					                                '<i class="fa fa-search"></i>'+
					                            '</a>'+
												'<a title="" class="btn btn-xs btn-success js_editor" href="javascript:void(0)" data-title="编辑" data-toggle="tooltip" data-original-title="编辑" data-bookCode="'+Data[i].book_code+'">'+
					                                '<i class="fa fa-edit"></i>'+
					                            '</a>'+
											'</td>'+
				    					 '</tr>'
                        };
            			
									
            		};
            		$("#js_tbody").html(trHtml);
            		$("#js_whole").prop("checked",false);
            		
            		/*$("#js_table").on('click','.js_search',function (){
            			book_code = $(this).attr("bookCode");
            			//alert(book_code);

            		});*/
            		return trHtml;
            		
            		
            	}else{
            		$("#js_tbody").html('<td colspan ="12" style="font-size:24px;padding: 5% 65% 12% 5%;">查询的内容为空</td>');
            	};
            	
            }else{
        		alert(data.message);
        	};
        }
	});
	
};