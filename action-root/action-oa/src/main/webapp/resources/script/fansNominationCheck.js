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
				var accountName = $.trim($("#accountName").val());
				data.startTime= startTime;
				data.endTime= endTime;
				data.accountName = accountName;
			}else{
				url = pageurl;
				startTime = $.trim($("#startTime").val());
				endTime = $.trim($("#endTime").val());
				accountName = $.trim($("#accountName").val());
				data.startTime= startTime;
				data.endTime= endTime;
				data.accountName = accountName;
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
						if(list[i].nominatedType == 0){
							if(list[i].auditStatus == 1){
								$tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
								"</td><td>" + list[i].accountName + 
								"</td><td>" + list[i].nominatedRole + 
								"</td></td><td>" + list[i].nominatedContent +
								"</td></td><td>" + "是" +
								"</td></td><td>" + list[i].date +
								"</td><td>" + "已通过" + 
								"</td></tr>";
							}else if(list[i].auditStatus == 0){
								$tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
								"</td><td>" + list[i].accountName + 
								"</td><td>" + list[i].nominatedRole + 
								"</td></td><td>" + list[i].nominatedContent + 
								"</td></td><td>" + "是" +
								"</td></td><td>" + list[i].date +
								"</td><td>" + "未审核" + 
								"</td></tr>";
							}else if(list[i].auditStatus == -1){
								$tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
								"</td><td>" + list[i].accountName + 
								"</td><td>" + list[i].nominatedRole + 
								"</td></td><td>" + list[i].nominatedContent + 
								"</td></td><td>" + "是" +
								"</td></td><td>" + list[i].date +
								"</td><td>" + "审核未通过" + 
								"</td></tr>";
							}else if(list[i].auditStatus == 2){
								$tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
								"</td><td>" + list[i].accountName + 
								"</td><td>" + list[i].nominatedRole + 
								"</td></td><td>" + list[i].nominatedContent + 
								"</td></td><td>" + "是" +
								"</td></td><td>" + list[i].date +
								"</td><td>" + "已删除" + 
								"</td></tr>";
							}else{
								$tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
								"</td><td>" + list[i].accountName + 
								"</td><td>" + list[i].nominatedRole + 
								"</td></td><td>" + list[i].nominatedContent +
								"</td></td><td>" + "是" +
								"</td></td><td>" + list[i].date +
								"</td><td>" + "未审核" + 
								"</td></tr>";
							}
						}else{
							if(list[i].auditStatus == 1){
								$tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
								"</td><td>" + list[i].accountName + 
								"</td><td>" + list[i].nominatedRole + 
								"</td></td><td>" + list[i].nominatedContent + 
								"</td></td><td>" + "否" +
								"</td></td><td>" + list[i].date +
								"</td><td>" + "已通过" + 
								"</td></tr>";
							}else if(list[i].auditStatus == 0){
								$tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
								"</td><td>" + list[i].accountName + 
								"</td><td>" + list[i].nominatedRole + 
								"</td></td><td>" + list[i].nominatedContent +
								"</td></td><td>" + "否" +
								"</td></td><td>" + list[i].date +
								"</td><td>" + "未审核" + 
								"</td></tr>";
							}else if(list[i].auditStatus == -1){
								$tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
								"</td><td>" + list[i].accountName + 
								"</td><td>" + list[i].nominatedRole + 
								"</td></td><td>" + list[i].nominatedContent + 
								"</td></td><td>" + "否" +
								"</td></td><td>" + list[i].date +
								"</td><td>" + "审核未通过" + 
								"</td></tr>";
							}else if(list[i].auditStatus == 2){
								$tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
								"</td><td>" + list[i].accountName + 
								"</td><td>" + list[i].nominatedRole + 
								"</td></td><td>" + list[i].nominatedContent + 
								"</td></td><td>" + "否" +
								"</td></td><td>" + list[i].date +
								"</td><td>" + "已删除" + 
								"</td></tr>";
							}else{
								$tr = "<tr><td><input type='checkbox' name='checkbtn' data-id='"+list[i].code+"'/>"  + 
								"</td><td>" + list[i].accountName + 
								"</td><td>" + list[i].nominatedRole + 
								"</td></td><td>" + list[i].nominatedContent + 
								"</td></td><td>" + "否" +
								"</td></td><td>" + list[i].date +
								"</td><td>" + "未审核" + 
								"</td></tr>";
							}
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
			query(1,"nominationList");
			// 翻页
			$("#firstPage-btn").on("click",function(){
				query(1,"nominationList");
			});
			$("#lastPage-btn").on("click",function(){
				query($("#totalPage-span").text(),"nominationList");
			});
			$("#prevPage-btn").on("click",function(){
				query((parseInt($("#currentPage-span").text()) - 1),"nominationList");
			});
			$("#nextPage-btn").on("click",function(){
				query((parseInt($("#currentPage-span").text()) + 1),"nominationList");
			});
			
			$(".js_btn").on("click",function(){
				$(this).addClass("js_on").siblings(".js_btn").removeClass("js_on");
			});
			
			// 时间查询
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
							query(1,"deleteNominationList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"deleteNominationList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"deleteNominationList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"deleteNominationList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"deleteNominationList");
							});
						}else if($(".js_btn").eq(i).text() == "已通过"){
							query(1,"passNominationList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"passNominationList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"passNominationList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"passNominationList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"passNominationList");
							});
						}else if($(".js_btn").eq(i).text() == "未通过"){
							query(1,"noPassNominationList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"noPassNominationList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"noPassNominationList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"noPassNominationList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"noPassNominationList");
							});
						}else if($(".js_btn").eq(i).text() == "审核中"){
							query(1,"ingNominationList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"ingNominationList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"ingNominationList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"ingNominationList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"ingNominationList");
							});
						}else if($(".js_btn").eq(i).text() == "全部"){
							query(1,"nominationList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"nominationList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"nominationList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"nominationList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"nominationList");
							});
						}
					}
				}
				if(isnull){
					query(1,"nominationList");
					// 翻页
					$("#firstPage-btn").off("click").on("click",function(){
						query(1,"nominationList");
					});
					$("#lastPage-btn").off("click").on("click",function(){
						query($("#totalPage-span").text(),"nominationList");
					});
					$("#prevPage-btn").off("click").on("click",function(){
						query((parseInt($("#currentPage-span").text()) - 1),"nominationList");
					});
					$("#nextPage-btn").off("click").on("click",function(){
						query((parseInt($("#currentPage-span").text()) + 1),"nominationList");
					});
				}
				
			});
			
			
			// 账号查询
			$("#query1-btn").on("click",function(){
				$("#query-type").val("part");
				var accountName = $.trim($("#accountName").val());
				if(accountName == null || accountName== "" || accountName.length ==0){
					alert("请输入账号");
					return false;
				}
				var isnull=true;
				for (var i = 0; i < $(".js_btn").length; i++){
					if($(".js_btn").eq(i).hasClass("js_on")){
						isnull=false;
						if($(".js_btn").eq(i).text() == "已删除"){
							query(1,"deleteNominationList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"deleteNominationList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"deleteNominationList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"deleteNominationList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"deleteNominationList");
							});
						}else if($(".js_btn").eq(i).text() == "已通过"){
							query(1,"passNominationList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"passNominationList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"passNominationList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"passNominationList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"passNominationList");
							});
						}else if($(".js_btn").eq(i).text() == "未通过"){
							query(1,"noPassNominationList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"noPassNominationList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"noPassNominationList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"noPassNominationList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"noPassNominationList");
							});
						}else if($(".js_btn").eq(i).text() == "审核中"){
							query(1,"ingNominationList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"ingNominationList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"ingNominationList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"ingNominationList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"ingNominationList");
							});
						}else if($(".js_btn").eq(i).text() == "全部"){
							query(1,"nominationList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"nominationList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"nominationList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"nominationList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"nominationList");
							});
						}
					}
				}
				if(isnull){
					query(1,"nominationList");
					// 翻页
					$("#firstPage-btn").off("click").on("click",function(){
						query(1,"nominationList");
					});
					$("#lastPage-btn").off("click").on("click",function(){
						query($("#totalPage-span").text(),"nominationList");
					});
					$("#prevPage-btn").off("click").on("click",function(){
						query((parseInt($("#currentPage-span").text()) - 1),"nominationList");
					});
					$("#nextPage-btn").off("click").on("click",function(){
						query((parseInt($("#currentPage-span").text()) + 1),"nominationList");
					});
				}
				
			});
			
			// 批量展示（batchShow）
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
					url:"nominationInfoBeanShow",
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
									query(currentPage,"deleteNominationList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"deleteNominationList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"deleteNominationList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"deleteNominationList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"deleteNominationList");
									});
								}else if($(".js_btn").eq(i).text() == "已通过"){
									query(currentPage,"passNominationList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"passNominationList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"passNominationList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"passNominationList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"passNominationList");
									});
								}else if($(".js_btn").eq(i).text() == "未通过"){
									query(currentPage,"noPassNominationList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"noPassNominationList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"noPassNominationList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"noPassNominationList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"noPassNominationList");
									});
								}else if($(".js_btn").eq(i).text() == "审核中"){
									query(currentPage,"ingNominationList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"ingNominationList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"ingNominationList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"ingNominationList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"ingNominationList");
									});
								}else if($(".js_btn").eq(i).text() == "全部"){
									query(currentPage,"nominationList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"nominationList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"nominationList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"nominationList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"nominationList");
									});
								}
							}
						}
						if(isnull){
							query(currentPage,"nominationList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"nominationList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"nominationList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"nominationList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"nominationList");
							});
						}
					},
					error:function(){
						// alert("网络错误");
					}
					
				});
				
				return false;
			});
			
			
			// 批量隐藏（batchHide）
			$("#batchHide").on("click",function(){
				var currentPage = $("#currentPage-span").text();
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
					url:"nominationInfoHide",
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
									query(currentPage,"deleteNominationList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"deleteNominationList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"deleteNominationList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"deleteNominationList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"deleteNominationList");
									});
								}else if($(".js_btn").eq(i).text() == "已通过"){
									query(currentPage,"passNominationList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"passNominationList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"passNominationList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"passNominationList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"passNominationList");
									});
								}else if($(".js_btn").eq(i).text() == "未通过"){
									query(currentPage,"noPassNominationList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"noPassNominationList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"noPassNominationList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"noPassNominationList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"noPassNominationList");
									});
								}else if($(".js_btn").eq(i).text() == "审核中"){
									query(currentPage,"ingNominationList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"ingNominationList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"ingNominationList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"ingNominationList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"ingNominationList");
									});
								}else if($(".js_btn").eq(i).text() == "全部"){
									query(currentPage,"nominationList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"nominationList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"nominationList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"nominationList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"nominationList");
									});
								}
							}
						}
						if(isnull){
							query(currentPage,"nominationList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"nominationList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"nominationList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"nominationList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"nominationList");
							});
						}
						
					},
					error:function(){
						// alert("网络错误");
					}
					
				});
				
				return false;
			});
			
			
			
			
			
			// 批量删除（batchShow）
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
					url:"nominationInfoDelete",
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
									query(currentPage,"deleteNominationList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"deleteNominationList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"deleteNominationList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"deleteNominationList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"deleteNominationList");
									});
								}else if($(".js_btn").eq(i).text() == "已通过"){
									query(currentPage,"passNominationList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"passNominationList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"passNominationList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"passNominationList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"passNominationList");
									});
								}else if($(".js_btn").eq(i).text() == "未通过"){
									query(currentPage,"noPassNominationList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"noPassNominationList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"noPassNominationList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"noPassNominationList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"noPassNominationList");
									});
								}else if($(".js_btn").eq(i).text() == "审核中"){
									query(currentPage,"ingNominationList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"ingNominationList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"ingNominationList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"ingNominationList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"ingNominationList");
									});
								}else if($(".js_btn").eq(i).text() == "全部"){
									query(currentPage,"nominationList");
									// 翻页
									$("#firstPage-btn").off("click").on("click",function(){
										query(1,"nominationList");
									});
									$("#lastPage-btn").off("click").on("click",function(){
										query($("#totalPage-span").text(),"nominationList");
									});
									$("#prevPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) - 1),"nominationList");
									});
									$("#nextPage-btn").off("click").on("click",function(){
										query((parseInt($("#currentPage-span").text()) + 1),"nominationList");
									});
								}
							}
						}
						if(isnull){
							query(currentPage,"nominationList");
							// 翻页
							$("#firstPage-btn").off("click").on("click",function(){
								query(1,"nominationList");
							});
							$("#lastPage-btn").off("click").on("click",function(){
								query($("#totalPage-span").text(),"nominationList");
							});
							$("#prevPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) - 1),"nominationList");
							});
							$("#nextPage-btn").off("click").on("click",function(){
								query((parseInt($("#currentPage-span").text()) + 1),"nominationList");
							});
						}
						
					},
					error:function(){
						// alert("网络错误");
					}
					
				});
				
				return false;
			});
			
			// 全部提名
			$("#batchAll").on("click",function(){
				
				query(1,"nominationList");
				// 翻页
				$("#firstPage-btn").off("click").on("click",function(){
					query(1,"nominationList");
				});
				$("#lastPage-btn").off("click").on("click",function(){
					query($("#totalPage-span").text(),"nominationList");
				});
				$("#prevPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) - 1),"nominationList");
				});
				$("#nextPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) + 1),"nominationList");
				});
			});
			
			// 已删除提名
			$("#batchBeDelete").on("click",function(){
				
				query(1,"deleteNominationList");
				// 翻页
				$("#firstPage-btn").off("click").on("click",function(){
					query(1,"deleteNominationList");
				});
				$("#lastPage-btn").off("click").on("click",function(){
					query($("#totalPage-span").text(),"deleteNominationList");
				});
				$("#prevPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) - 1),"deleteNominationList");
				});
				$("#nextPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) + 1),"deleteNominationList");
				});
			});
			
			// 已通过提名
			$("#batchPassed").on("click",function(){
				
				query(1,"passNominationList");
				// 翻页
				$("#firstPage-btn").off("click").on("click",function(){
					query(1,"passNominationList");
				});
				$("#lastPage-btn").off("click").on("click",function(){
					query($("#totalPage-span").text(),"passNominationList");
				});
				$("#prevPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) - 1),"passNominationList");
				});
				$("#nextPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) + 1),"passNominationList");
				});
			});
			
			// 未通过提名
			$("#batchNoPassed").on("click",function(){
				
				query(1,"noPassNominationList");
				// 翻页
				$("#firstPage-btn").off("click").on("click",function(){
					query(1,"noPassNominationList");
				});
				$("#lastPage-btn").off("click").on("click",function(){
					query($("#totalPage-span").text(),"noPassNominationList");
				});
				$("#prevPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) - 1),"noPassNominationList");
				});
				$("#nextPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) + 1),"noPassNominationList");
				});
			});
			
			// 审核中提名
			$("#batchIngPassed").on("click",function(){
				
				query(1,"ingNominationList");
				// 翻页
				$("#firstPage-btn").off("click").on("click",function(){
					query(1,"ingNominationList");
				});
				$("#lastPage-btn").off("click").on("click",function(){
					query($("#totalPage-span").text(),"ingNominationList");
				});
				$("#prevPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) - 1),"ingNominationList");
				});
				$("#nextPage-btn").off("click").on("click",function(){
					query((parseInt($("#currentPage-span").text()) + 1),"ingNominationList");
				});
			});
			
		});