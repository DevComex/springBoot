function query(currentPage){
			$("#commentTable tbody").empty();
			var url = "";
			var data = {};
			data.currentPage = currentPage;
			var type = $("#query-type").val();
			if(type == "all"){
				url = "getCommentList";
			}else{
				url = "findByStartTimeToEndTime";
				var startTime = $.trim($("#startTime").val());
				var endTime = $.trim($("#endTime").val());
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
						var $tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
									"</td><td>" + list[i].comment + 
									"</td></td><td>" + list[i].time + 
									"</td><td>" + list[i].state + 
									"</td></tr>";
						$("#commentTable tbody").append($tr);
					}
					$("#currentPage-span").text(page.currentPage);
					$("#totalPage-span").text(page.totalPage < 1 ? 1 : page.totalPage);
						if(page.hasNextPage){
							$("#nextPage-btn").attr("disabled", false);
							$("#lastPage-btn").attr("disabled",false);
						}else{
							$("#nextPage-btn").attr("disabled",true);
							$("#lastPage-btn").attr("disabled",true);
						}
						if(page.hasPrevPage){
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
			query(1);
			// 翻页
			$("#firstPage-btn").on("click",function(){
				query(1);
			});
			$("#lastPage-btn").on("click",function(){
				query($("#totalPage-span").text());
			});
			$("#prevPage-btn").on("click",function(){
				query(parseInt($("#currentPage-span").text()) - 1);
			});
			$("#nextPage-btn").on("click",function(){
				query(parseInt($("#currentPage-span").text()) + 1);
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
				
				$.ajax({
					url:"findByStartTimeToEndTime",
					type:"GET",
					data:{
						"currentPage": 1,
						"startTime":startTime,
						"endTime":endTime
						},
					cache:false,
					dataType:"json",
					success:function(data){
						$("#commentTable tbody").empty();
						if(!data.isSuccess){
							alert(data.message);
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
							var $tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
										"</td><td>" + list[i].comment + 
										"<td>" + list[i].time + 
										"</td><td>" + list[i].state + 
										"</td></tr>";
							$("#commentTable tbody").append($tr);
						}
						$("#currentPage-span").text(page.currentPage);
						$("#totalPage-span").text(page.totalPage < 1 ? 1 : page.totalPage);
						if(page.hasNextPage){
							$("#nextPage-btn").attr("disabled", false);
							$("#lastPage-btn").attr("disabled",false);
						}else{
							$("#nextPage-btn").attr("disabled",true);
							$("#lastPage-btn").attr("disabled",true);
						}
						if(page.hasPrevPage){
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
				
					return false;
			});
			
			//批量展示（batchShow）
			$("#batchShow").on("click",function(){
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
					url:"batchShow",
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
						$.ajax({
							url:"getCommentList",
							type:"post",
							data:{
								"currentPage": 1,
							},
							cache:false,
							dataType:"json",
							success:function(data){
								var page = data.data;
								var list = page.list;
								var $tr;
								for (var i = 0; i < list.length; i++){
									$tr += "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
												"</td><td>" + list[i].comment + 
												"</td><td>" + list[i].time + 
												"</td><td>" + list[i].state + 
												"</td><tr>";
									}
									$("#commentTable tbody").html($tr);
									$("#currentPage-span").text(page.currentPage);
									$("#totalPage-span").text(page.totalPage < 1 ? 1 : page.totalPage);
									if(page.hasNextPage){
										$("#nextPage-btn").attr("disabled", false);
										$("#lastPage-btn").attr("disabled",false);
									}else{
										$("#nextPage-btn").attr("disabled",true);
										$("#lastPage-btn").attr("disabled",true);
									}
									if(page.hasPrevPage){
										$("#firstPage-btn").attr("disabled", false);
										$("#prevPage-btn").attr("disabled",false);
									}else{
										$("#firstPage-btn").attr("disabled", true);
										$("#prevPage-btn").attr("disabled",true);
									}
							},
							error:function(data){
								
							}
						})
						
					},
					error:function(){
						//alert("网络错误");
					}
					
				});
				
				return false;
			})
			
			
			//批量隐藏（batchHide）
			$("#batchHide").on("click",function(){
				var checks = $('input[name="checkbtn"]:checked');
				var arrays=[] ;
				if(checks==null || checks.length==0){
					alert("请选择要隐藏的选项！");
					return false;
				}
				else{
					for(var i=0;i<checks.length;i++){
						arrays.push( checks.eq(i).attr("data-id") );
					}
				}
				$.ajax({
					url:"batchHide",
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
						$.ajax({
							url:"getCommentList",
							type:"post",
							data:{
								"currentPage": 1,
							},
							cache:false,
							dataType:"json",
							success:function(data){
								var page = data.data;
								var list = page.list;
								var $tr;
								for (var i = 0; i < list.length; i++){
									$tr += "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
												"</td><td>" + list[i].comment + 
												"</td><td>" + list[i].time + 
												"</td><td>" + list[i].state + 
												"</td></tr>";
									}
									$("#commentTable tbody").html($tr);
									$("#currentPage-span").text(page.currentPage);
									$("#totalPage-span").text(page.totalPage < 1 ? 1 : page.totalPage);
									if(page.hasNextPage){
										$("#nextPage-btn").attr("disabled", false);
										$("#lastPage-btn").attr("disabled",false);
									}else{
										$("#nextPage-btn").attr("disabled",true);
										$("#lastPage-btn").attr("disabled",true);
									}
									if(page.hasPrevPage){
										$("#firstPage-btn").attr("disabled", false);
										$("#prevPage-btn").attr("disabled",false);
									}else{
										$("#firstPage-btn").attr("disabled", true);
										$("#prevPage-btn").attr("disabled",true);
									}
							},
							error:function(data){
								
							}
						})
						
					},
					error:function(){
						//alert("网络错误");
					}
					
				});
				
				return false;
			})
			
			//批量删除（batchDelete）
			$("#batchDelete").on("click",function(){
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
					url:"batchDelete",
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
						$.ajax({
							url:"getCommentList",
							type:"post",
							data:{
								"currentPage": 1,
							},
							cache:false,
							dataType:"json",
							success:function(data){
								var page = data.data;
								var list = page.list;
								var $tr;
								for (var i = 0; i < list.length; i++){
									$tr += "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
												"</td><td>" + list[i].comment + 
												"</td><td>" + list[i].time + 
												"</td><td>" + list[i].state + 
												"</td></tr>";
									}
									$("#commentTable tbody").html($tr);
									$("#currentPage-span").text(page.currentPage);
									$("#totalPage-span").text(page.totalPage < 1 ? 1 : page.totalPage);
									if(page.hasNextPage){
										$("#nextPage-btn").attr("disabled", false);
										$("#lastPage-btn").attr("disabled",false);
									}else{
										$("#nextPage-btn").attr("disabled",true);
										$("#lastPage-btn").attr("disabled",true);
									}
									if(page.hasPrevPage){
										$("#firstPage-btn").attr("disabled", false);
										$("#prevPage-btn").attr("disabled",false);
									}else{
										$("#firstPage-btn").attr("disabled", true);
										$("#prevPage-btn").attr("disabled",true);
									}
							},
							error:function(data){
								
							}
						});
						
					},
					error:function(){
						//alert("网络错误");
					}
					
				});
				
				return false;
			});
			
		});