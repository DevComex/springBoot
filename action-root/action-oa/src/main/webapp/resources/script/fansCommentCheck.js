function query(currentPage,pageurl){
			$("#commentTable tbody").empty();
			var url = "";
			var data = {};
			data.currentPage = currentPage;
			data.r = Math.random();
			var type = $("#query-type").val();
			if(type == "all"){
				url = pageurl;
				var startTime = $.trim($("#startTime").val());
				var endTime = $.trim($("#endTime").val());
				data.startTime= startTime;
				data.endTime= endTime;
			}else{
				url = pageurl;
				startTime = $.trim($("#startTime").val());
				endTime = $.trim($("#endTime").val());
				data.startTime= startTime;
				data.endTime= endTime;
			}
			
			$.ajax({
				url:url,
				type:"GET",
				data:data,
				cache:false,
				dataType:"json",
				success:function(data){
					if(data.data == null  || data.data.list == null || data.data.list.length==0){
						alert("没有符合条件的记录");
						$("#firstPage-btn").attr("disabled", true);
						$("#prevPage-btn").attr("disabled",true);
						$("#nextPage-btn").attr("disabled",true);
						$("#lastPage-btn").attr("disabled",true);
						$("#totalPage-span").text(1);
						$("#currentPage-span").text(1);
						return false;
					}
					var page = data.data;
					var list = page.list;
					
					for (var i = 0; i < list.length; i ++){
						var $tr="";
						if(list[i].checkFlag == 1){
							$tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
							"</td><td>" + list[i].commentsContent + 
							"</td></td><td>" + list[i].date + 
							"</td><td>" + "已通过" + 
							"</td></tr>";
						}else if(list[i].checkFlag == 0){
							$tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
							"</td><td>" + list[i].commentsContent + 
							"</td></td><td>" + list[i].date + 
							"</td><td>" + "未审核" + 
							"</td></tr>";
						}else if(list[i].checkFlag == -1){
							$tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
							"</td><td>" + list[i].commentsContent + 
							"</td></td><td>" + list[i].date + 
							"</td><td>" + "审核未通过" + 
							"</td></tr>";
						}else if(list[i].checkFlag == 2){
							$tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
							"</td><td>" + list[i].commentsContent + 
							"</td></td><td>" + list[i].date + 
							"</td><td>" + "已删除" + 
							"</td></tr>";
						}else{
							$tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
							"</td><td>" + list[i].commentsContent + 
							"</td></td><td>" + list[i].date + 
							"</td><td>" + "未审核" + 
							"</td></tr>";
						}
						
						$("#commentTable tbody").append($tr);
					}
					$("#currentPage-span").text(page.pageIndex);
					$("#totalPage-span").text(page.totalPages < 1 ? 1 : page.totalPages);
						if(page.isHaveNextPage){
							$("#nextPage-btn").attr("disabled", false);
							$("#lastPage-btn").attr("disabled",false);
						}else{
							$("#nextPage-btn").attr("disabled",true);
							$("#lastPage-btn").attr("disabled",true);
						}
						if(page.isHavePrePage){
							$("#firstPage-btn").attr("disabled", false);
							$("#prevPage-btn").attr("disabled",false);
						}else{
							$("#firstPage-btn").attr("disabled", true);
							$("#prevPage-btn").attr("disabled",true);
						}
				
				},
				error:function(){
					alert("网络错误");
				}
			
			});
    	}
    
    
		$(function(){
			query(1,"commentsList");
			// 翻页
			$("#firstPage-btn").off("click").on("click",function(){
				query(1,"commentsList");
			});
			$("#lastPage-btn").off("click").on("click",function(){
				query($("#totalPage-span").text(),"commentsList");
			});
			$("#prevPage-btn").off("click").on("click",function(){
				query((parseInt($("#currentPage-span").text()) - 1),"commentsList");
			});
			$("#nextPage-btn").off("click").on("click",function(){
				query((parseInt($("#currentPage-span").text()) + 1),"commentsList");
			});
			
			$(".js_btn").on("click",function(){
				$(this).addClass("js_on").siblings(".js_btn").removeClass("js_on");
			});
			
			//查询
			$("#query-btn").on("click",function(){
				$("#query-type").val("part");
				var startTime = $.trim($("#startTime").val());
				var endTime = $.trim($("#endTime").val());
				if(startTime == null || startTime== "" || startTime.length ==0){
					alert("请输入开始时间");
					return false;
				}
				if(endTime == null || endTime== "" || endTime.length ==0){
					alert("请输入结束时间");
					return false;
				}
				var isnull=true;
				for (var i = 0; i < $(".js_btn").length; i++){
					if($(".js_btn").eq(i).hasClass("js_on")){
						isnull=false;
						if($(".js_btn").eq(i).text() == "已删除"){
							query(1,"deleteCommentsList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"deleteCommentsList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"deleteCommentsList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"deleteCommentsList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"deleteCommentsList");
							});
						}else if($(".js_btn").eq(i).text() == "已通过"){
							query(1,"passCommentsList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"passCommentsList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"passCommentsList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"passCommentsList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"passCommentsList");
							});
						}else if($(".js_btn").eq(i).text() == "未通过"){
							query(1,"noPassCommentsList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"noPassCommentsList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"noPassCommentsList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"noPassCommentsList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"noPassCommentsList");
							});
						}else if($(".js_btn").eq(i).text() == "审核中"){
							query(1,"ingCommentsList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"ingCommentsList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"ingCommentsList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"ingCommentsList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"ingCommentsList");
							});
						}else if($(".js_btn").eq(i).text() == "全部"){
							query(1,"commentsList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"commentsList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"commentsList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"commentsList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"commentsList");
							});
						}
					}
				}
				if(isnull){
					query(1,"commentsList");
					// 翻页
					$("#firstPage-btn").off("click").on("click",function(){
						query(1,"commentsList");
					});
					$("#lastPage-btn").off("click").on("click",function(){
						query($("#totalPage-span").text(),"commentsList");
					});
					$("#prevPage-btn").off("click").on("click",function(){
						query((parseInt($("#currentPage-span").text()) - 1),"commentsList");
					});
					$("#nextPage-btn").off("click").on("click",function(){
						query((parseInt($("#currentPage-span").text()) + 1),"commentsList");
					});
				}

			});

			//批量展示（batchShow）
			$("#batchShow").on("click",function(){
				var currentPage = $("#currentPage-span").text();
				var checks = $('input[name="checkbtn"]:checked');
				var arrays=[] ;
				if(checks==null || checks.length==0){
					alert("请选择要展示的选项！");
					return false;
				}else{
					for(var i=0;i<checks.length;i++){
						arrays.push( checks.eq(i).attr("data-id") );
					}
				}
				
				$.ajax({
					url:"commentsShow",
					type:"post",
					data:{
						"codes[]": arrays,
						"r":Math.random()
						},
					cache:false,
					dataType:"json",
					success:function(data){
						$("#allCheck").prop("checked",false);
						$("#commentTable tbody").empty();
						alert(data.message);
						var isnull=true;
						for (var i = 0; i < $(".js_btn").length; i++){
							if($(".js_btn").eq(i).hasClass("js_on")){
								isnull=false;
								if($(".js_btn").eq(i).text() == "已删除"){
									query(currentPage,"deleteCommentsList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"deleteCommentsList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"deleteCommentsList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"deleteCommentsList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"deleteCommentsList");
									});
								}else if($(".js_btn").eq(i).text() == "已通过"){
									query(currentPage,"passCommentsList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"passCommentsList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"passCommentsList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"passCommentsList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"passCommentsList");
									});
								}else if($(".js_btn").eq(i).text() == "未通过"){
									query(currentPage,"noPassCommentsList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"noPassCommentsList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"noPassCommentsList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"noPassCommentsList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"noPassCommentsList");
									});
								}else if($(".js_btn").eq(i).text() == "审核中"){
									query(currentPage,"ingCommentsList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"ingCommentsList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"ingCommentsList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"ingCommentsList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"ingCommentsList");
									});
								}else if($(".js_btn").eq(i).text() == "全部"){
									query(currentPage,"commentsList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"commentsList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"commentsList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"commentsList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"commentsList");
									});
								}
							}
						}
						if(isnull){
							query(currentPage,"commentsList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"commentsList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"commentsList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"commentsList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"commentsList");
							});
						}
						
					},
					error:function(){
						//alert("网络错误");
					}
					
				});
				
				return false;
			});
			
			
			//批量隐藏（batchHide）
			$("#batchHide").on("click",function(){
			var currentPage = $("#currentPage-span").text();
			var checks = $('input[name="checkbtn"]:checked');
			var arrays=[] ;
			if(checks==null || checks.length==0){
				alert("请选择要隐藏的选项！");
				return false;
			}else{
				for(var i=0;i<checks.length;i++){
					arrays.push( checks.eq(i).attr("data-id") );
				}
			}
			$.ajax({
				url:"commentsHide",
				type:"post",
				data:{
					"codes[]": arrays
					},
				cache:false,
				dataType:"json",
				success:function(data){
					$("#allCheck").prop("checked",false);
					$("#commentTable tbody").empty();
					alert(data.message);
					var isnull=true;
					for (var i = 0; i < $(".js_btn").length; i++){
						if($(".js_btn").eq(i).hasClass("js_on")){
							isnull=false;
							if($(".js_btn").eq(i).text() == "已删除"){
								query(currentPage,"deleteCommentsList");
								// 翻页
								$("#firstPage-btn").off("click").on("click",function(){
									query(1,"deleteCommentsList");
								});
								$("#lastPage-btn").off("click").on("click",function(){
									query($("#totalPage-span").text(),"deleteCommentsList");
								});
								$("#prevPage-btn").off("click").on("click",function(){
									query((parseInt($("#currentPage-span").text()) - 1),"deleteCommentsList");
								});
								$("#nextPage-btn").off("click").on("click",function(){
									query((parseInt($("#currentPage-span").text()) + 1),"deleteCommentsList");
								});
							}else if($(".js_btn").eq(i).text() == "已通过"){
								query(currentPage,"passCommentsList");
								// 翻页
								$("#firstPage-btn").off("click").on("click",function(){
									query(1,"passCommentsList");
								});
								$("#lastPage-btn").off("click").on("click",function(){
									query($("#totalPage-span").text(),"passCommentsList");
								});
								$("#prevPage-btn").off("click").on("click",function(){
									query((parseInt($("#currentPage-span").text()) - 1),"passCommentsList");
								});
								$("#nextPage-btn").off("click").on("click",function(){
									query((parseInt($("#currentPage-span").text()) + 1),"passCommentsList");
								});
							}else if($(".js_btn").eq(i).text() == "未通过"){
								query(currentPage,"noPassCommentsList");
								// 翻页
								$("#firstPage-btn").off("click").on("click",function(){
									query(1,"noPassCommentsList");
								});
								$("#lastPage-btn").off("click").on("click",function(){
									query($("#totalPage-span").text(),"noPassCommentsList");
								});
								$("#prevPage-btn").off("click").on("click",function(){
									query((parseInt($("#currentPage-span").text()) - 1),"noPassCommentsList");
								});
								$("#nextPage-btn").off("click").on("click",function(){
									query((parseInt($("#currentPage-span").text()) + 1),"noPassCommentsList");
								});
							}else if($(".js_btn").eq(i).text() == "审核中"){
								query(currentPage,"ingCommentsList");
								// 翻页
								$("#firstPage-btn").off("click").on("click",function(){
									query(1,"ingCommentsList");
								});
								$("#lastPage-btn").off("click").on("click",function(){
									query($("#totalPage-span").text(),"ingCommentsList");
								});
								$("#prevPage-btn").off("click").on("click",function(){
									query((parseInt($("#currentPage-span").text()) - 1),"ingCommentsList");
								});
								$("#nextPage-btn").off("click").on("click",function(){
									query((parseInt($("#currentPage-span").text()) + 1),"ingCommentsList");
								});
							}else if($(".js_btn").eq(i).text() == "全部"){
								query(currentPage,"commentsList");
								// 翻页
								$("#firstPage-btn").off("click").on("click",function(){
									query(1,"commentsList");
								});
								$("#lastPage-btn").off("click").on("click",function(){
									query($("#totalPage-span").text(),"commentsList");
								});
								$("#prevPage-btn").off("click").on("click",function(){
									query((parseInt($("#currentPage-span").text()) - 1),"commentsList");
								});
								$("#nextPage-btn").off("click").on("click",function(){
									query((parseInt($("#currentPage-span").text()) + 1),"commentsList");
								});
							}
						}
					}
					if(isnull){
						query(currentPage,"commentsList");
						// 翻页
						$("#firstPage-btn").off("click").on("click",function(){
							query(1,"commentsList");
						});
						$("#lastPage-btn").off("click").on("click",function(){
							query($("#totalPage-span").text(),"commentsList");
						});
						$("#prevPage-btn").off("click").on("click",function(){
							query((parseInt($("#currentPage-span").text()) - 1),"commentsList");
						});
						$("#nextPage-btn").off("click").on("click",function(){
							query((parseInt($("#currentPage-span").text()) + 1),"commentsList");
						});
					}
					
				},
				error:function(){
					//alert("网络错误");
				}
				
			});
			
			return false;
		});
			
			
			
			
			
			//批量删除（batchShow）
			$("#batchDelete").on("click",function(){
					var currentPage = $("#currentPage-span").text();
				var checks = $('input[name="checkbtn"]:checked');
				var arrays=[] ;
				if(checks==null || checks.length==0){
					alert("请选择要删除的选项！");
					return false;
				}else{
					for(var i=0;i<checks.length;i++){
						arrays.push( checks.eq(i).attr("data-id") );
					}
				}
				$.ajax({
					url:"commentsDelete",
					type:"post",
					data:{
						"codes[]": arrays,
						"r":Math.random()
						},
					cache:false,
					dataType:"json",
					success:function(data){
						$("#allCheck").prop("checked",false);
						$("#commentTable tbody").empty();
						alert(data.message);
						var isnull=true;
						for (var i = 0; i < $(".js_btn").length; i++){
							if($(".js_btn").eq(i).hasClass("js_on")){
								isnull=false;
								if($(".js_btn").eq(i).text() == "已删除"){
									query(currentPage,"deleteCommentsList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"deleteCommentsList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"deleteCommentsList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"deleteCommentsList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"deleteCommentsList");
									});
								}else if($(".js_btn").eq(i).text() == "已通过"){
									query(currentPage,"passCommentsList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"passCommentsList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"passCommentsList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"passCommentsList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"passCommentsList");
									});
								}else if($(".js_btn").eq(i).text() == "未通过"){
									query(currentPage,"noPassCommentsList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"noPassCommentsList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"noPassCommentsList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"noPassCommentsList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"noPassCommentsList");
									});
								}else if($(".js_btn").eq(i).text() == "审核中"){
									query(currentPage,"ingCommentsList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"ingCommentsList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"ingCommentsList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"ingCommentsList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"ingCommentsList");
									});
								}else if($(".js_btn").eq(i).text() == "全部"){
									query(currentPage,"commentsList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"commentsList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"commentsList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"commentsList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"commentsList");
									});
								}
							}
						}
						if(isnull){
							query(currentPage,"commentsList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"commentsList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"commentsList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"commentsList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"commentsList");
							});
						}
						
					},
					error:function(){
						//alert("网络错误");
					}
					
				});
				
				return false;
			});
			
		
			//已删除评论
			$("#batchBeDelete").on("click",function(){
				
				query(1,"deleteCommentsList");
				// 翻页
				$("#firstPage-btn").off("click").on("click",function(){
					query(1,"deleteCommentsList");
				});
				$("#lastPage-btn").off("click").on("click",function(){
					query($("#totalPage-span").text(),"deleteCommentsList");
				});
				$("#prevPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) - 1),"deleteCommentsList");
				});
				$("#nextPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) + 1),"deleteCommentsList");
				});
			});
			
			//已通过评论
			$("#batchPassed").on("click",function(){
				
				query(1,"passCommentsList");
				// 翻页
				$("#firstPage-btn").off("click").on("click",function(){
					query(1,"passCommentsList");
				});
				$("#lastPage-btn").off("click").on("click",function(){
					query($("#totalPage-span").text(),"passCommentsList");
				});
				$("#prevPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) - 1),"passCommentsList");
				});
				$("#nextPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) + 1),"passCommentsList");
				});
			});
			
			//未通过评论
			$("#batchNoPassed").on("click",function(){
				
				query(1,"noPassCommentsList");
				// 翻页
				$("#firstPage-btn").off("click").on("click",function(){
					query(1,"noPassCommentsList");
				});
				$("#lastPage-btn").off("click").on("click",function(){
					query($("#totalPage-span").text(),"noPassCommentsList");
				});
				$("#prevPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) - 1),"noPassCommentsList");
				});
				$("#nextPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) + 1),"noPassCommentsList");
				});
			});
			
			//审核中评论
			$("#batchIngPassed").on("click",function(){
				
				query(1,"ingCommentsList");
				// 翻页
				$("#firstPage-btn").off("click").on("click",function(){
					query(1,"ingCommentsList");
				});
				$("#lastPage-btn").off("click").on("click",function(){
					query($("#totalPage-span").text(),"ingCommentsList");
				});
				$("#prevPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) - 1),"ingCommentsList");
				});
				$("#nextPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) + 1),"ingCommentsList");
				});
			});
			
			//全部评论
			$("#batchAll").on("click",function(){
				
				query(1,"commentsList");
				// 翻页
				$("#firstPage-btn").off("click").on("click",function(){
					query(1,"commentsList");
				});
				$("#lastPage-btn").off("click").on("click",function(){
					query($("#totalPage-span").text(),"commentsList");
				});
				$("#prevPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) - 1),"commentsList");
				});
				$("#nextPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) + 1),"commentsList");
				});
			});
			
			
			
			
		});
		
		
		
