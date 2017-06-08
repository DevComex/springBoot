function query(currentPage,pageurl){
			$("#countTable tbody").empty();
			var url = "";
			var data = {};
			data.currentPage = currentPage;
			var type = $("#query-type").val();
			if(type == "all"){
				url = pageurl;
				var startTime = $.trim($("#startTime").val());
				data.startTime= startTime;
			}else{
				url = pageurl;
				startTime = $.trim($("#startTime").val());
				data.startTime= startTime;
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
						$("#nomaForMe").val(0);
						$("#nomaForHe").val(0);
						$("#joinWhiteNum").val(0);
						$("#joinBlackNum").val(0);
						$("#joinVoteNum").val(0);
						return false;
					}
					var page = data.data;
					var list = page.list;
					
					for (var i = 0; i < list.length; i ++){
						var $tr="";
							$tr = "<tr><td>"+ list[i].accountName + 
							"</td><td>" + list[i].whiteNum + 
							"</td></td><td>" + list[i].blackNum + 
							"</td></tr>";
						
						$("#countTable tbody").append($tr);
					}
					$("#nomaForMe").val(list[0].nomaForMe);
					$("#nomaForHe").val(list[0].nomaForHe);
					$("#joinWhiteNum").val(list[0].joinWhiteNum);
					$("#joinBlackNum").val(list[0].joinBlackNum);
					$("#joinVoteNum").val(list[0].joinVoteNum);
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
			query(1,"CountList");
			// 翻页
			$("#firstPage-btn").off("click").on("click",function(){
				query(1,"CountList");
			});
			$("#lastPage-btn").off("click").on("click",function(){
				query($("#totalPage-span").text(),"CountList");
			});
			$("#prevPage-btn").off("click").on("click",function(){
				query((parseInt($("#currentPage-span").text()) - 1),"CountList");
			});
			$("#nextPage-btn").off("click").on("click",function(){
				query((parseInt($("#currentPage-span").text()) + 1),"CountList");
			});
			
			//查询
			$("#query-btn").on("click",function(){
				$("#query-type").val("part");
				var startTime = $.trim($("#startTime").val());
				if(startTime == null || startTime== "" || startTime.length ==0){
					alert("请输入查询日期");
					return false;
				}
					query(1,"CountList");
					// 翻页
					$("#firstPage-btn").off("click").on("click",function(){
						query(1,"CountList");
					});
					$("#lastPage-btn").off("click").on("click",function(){
						query($("#totalPage-span").text(),"CountList");
					});
					$("#prevPage-btn").off("click").on("click",function(){
						query((parseInt($("#currentPage-span").text()) - 1),"CountList");
					});
					$("#nextPage-btn").off("click").on("click",function(){
						query((parseInt($("#currentPage-span").text()) + 1),"CountList");
					});
				

			});

		});
		
		
		