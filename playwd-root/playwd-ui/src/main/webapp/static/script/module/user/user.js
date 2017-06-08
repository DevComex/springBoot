$(function(){
    		/*基本*/
    		$("#nav .navvs").click();
    		
			var y = $("#year").val();
			var m = $("#month").val();
			for(var i = 1; i <= 12; i ++){
                   var $option = "<option value='" + i +"'>" + i + "月</option>"
				$("#monthMes").append($option);
			}
			$("#yearMes").val(y);
			$("#monthMes").val(m);
			
			$("#yearMes,#monthMes,#scoreType").change(function(){
				listData();
			});
			
			/*功能*/
			
			//列表
			listData();
			
			function listData(){
				var trHtml = '<tr code="{{code}}">'
					+'<td>{{index}}</td>'
					+'<td>{{projectcollectionName}}</td>'
					+'<td>{{projectName}}</td>'
					+'<td>{{version}}</td>'
					+'<td>{{pmName}}</td>'
					+'<td>{{projectStartDateStr}}</td>'
					+'<td>{{projectEndDateStr}}</td>'
					+'<td style="text-align:left;">{{projectDescription}}</td>'
					+'<td class="td_personList">{{personList}}</td>'
					+'<td>'
						+'<p>'
							+'<a class="btn btn-xs btn-info convention_task_detail" data-content="" data-html="true" data-placement="bottom" >'
		                        +'<i class="fa fa-eye"></i>详情'
		                    +'</a>'
		                +'</p>'
					+'</td>'
		        +'</tr>';
		        
		        var trHtmlCopy = "";
				var pp1HtmlCopy = "";
				var y = $("#yearMes").val();
				var m = $("#monthMes").val();
		        var finishMonth = y + "-" + (m <10 ? ("0"+m) : m);
		        var scoreType = $("#scoreType").val();
		        var projectDescription = $("#projectDescription").val();
		        var table = $("#performance_convention_task_list");
				$.ajax({
					url:"/performance/conventionDataList",  
	                  type:'post',  
	                  dataType:'json',
	                  data:{finishMonth:finishMonth,scoreType:scoreType,projectDescription:projectDescription},
	                  success:function(data){  
                		  if(data && data.length > 0){
                			  $(table).find("tbody").empty();
                			  for(var i=0;i<data.length;i++){
                				  var pp1Html = $("#taskDetailDiv").html();
                				  var ddsd = data[i].typePersons;
                				  
                				  pp1HtmlCopy = pp1Html.replace("{{attributeCost}}",data[i].attributeCost);
                				  pp1HtmlCopy = pp1HtmlCopy.replace("{{demand}}",data[i].demand);
                				  pp1HtmlCopy = pp1HtmlCopy.replace("{{acceptance}}",data[i].acceptance);
                				  pp1HtmlCopy = pp1HtmlCopy.replace("{{projectDescription}}",data[i].projectDescription.replace(/\n/g,"<br/>"));
                				  pp1HtmlCopy = pp1HtmlCopy.replace("{{detailPersonList}}",displayPersonDetailsHtml(ddsd));
                				 
                				  trHtmlCopy = trHtml.replace("{{code}}",data[i].code);
                				  trHtmlCopy = trHtmlCopy.replace("{{index}}",(i+1));
                				  trHtmlCopy = trHtmlCopy.replace("{{projectcollectionName}}",data[i].projectcollectionName);
                				  trHtmlCopy = trHtmlCopy.replace("{{projectName}}",data[i].projectName);
                				  trHtmlCopy = trHtmlCopy.replace("{{version}}",data[i].version);
                				  trHtmlCopy = trHtmlCopy.replace("{{pmName}}",data[i].pmName);
                				  trHtmlCopy = trHtmlCopy.replace("{{projectStartDateStr}}",data[i].projectStartDateStr);
                				  trHtmlCopy = trHtmlCopy.replace("{{projectEndDateStr}}",data[i].projectEndDateStr);
                				  trHtmlCopy = trHtmlCopy.replace("{{projectDescription}}",data[i].projectDescription.replace(/\n/g,"<br/>"));
                				  
                				  if(ddsd){
                					  trHtmlCopy = trHtmlCopy.replace("{{personList}}",displayPersonScoreListHtml(ddsd,data[i].projectScore));
                				  }
                				  
                				  var obj = $(trHtmlCopy);
                				  $(obj).find(".convention_task_detail").attr("data-content",pp1HtmlCopy);
                				  $(table).find("tbody").append($(obj));
                				  $(".convention_task_detail").each(function(){
                		    			var _this = $(this);
                		    			_this.popover();
                		    	  });
                				 
                			  }
                		  }else{
                			  var html = "<tr><td colspan='"+$(table).find("thead th").length+"'>暂无数据!</td></tr>";
                			  $(table).find("tbody").html(html);
                		  }
                		  
	                  },  
	                  error:function(){  
	                	  alert("获取紧急任务列表失败！");
	                  }   
	            });
			}
			
			//搜索
			$("#search").click(function(){
				listData();
			});
			
    	});	  
    	
    	function displayPersonScoreListHtml(ddsd,score){
			var ddsdHtml = "";
			for(var key in ddsd){  
				  ddsdHtml += '<div class="down"><ul>';
				   for(var j=0,xxsd=ddsd[key];j<xxsd.length;j++){
				   		ddsdHtml+='<li data-id="'+xxsd[j].code+'">'+xxsd[j].personName+'(<b>'+xxsd[j].score+'</b>)</li>'
			  	   }
				   ddsdHtml += '</ul></div>';
			}  
			return ddsdHtml;
		}
    	
    	function displayPersonDetailsHtml(ddsd){
    		var ddsdHtml = "";
    		for(var key in ddsd){  
    			ddsdHtml += '<div><p>'+key+'</p><ul>';
    			for(var j=0,xxsd=ddsd[key];j<xxsd.length;j++){
    				ddsdHtml+='<li data-id="'+xxsd[j].code+'">'+xxsd[j].personName+'</li>'
    			}
    			ddsdHtml += '</ul></div>';
    		}  
    		return ddsdHtml;
    	}
    	
    	var scoreGlobalFlag = false;
    	function selectconventionScore (obj,btn){
    		var personTbCodes = [];
    		var taskScores = [];
    		var allData = {};
    		var code = $(btn).parents("tr:eq()").attr("code");
    		$(obj).each(function(){
    			var _this = this;
    			personTbCodes.push($(_this).parent().attr("data-id"));
    			taskScores.push(_this.value);
    			allData[$(_this).parent().attr("data-id")] = _this.value;
    		});
    		
    		if(scoreGlobalFlag){
    			console.log("不允许重复提交");
    			return;
    		}
    		$.ajax({
				type: 'POST',
				url: "/performance/conventionScore",
				data: {personTbCodes:personTbCodes,taskScores:taskScores,code:code},
				beforeSend: function(){
					markGlobalFlag = true;
			    },
			    complete: function(){
			    	markGlobalFlag = false;
			    },
				success: function(data){
					if(data.isSuccess){
						$(obj).parents("tr:eq(0)").find(".score_is").html('<span class="label bg-success">是</span>');
			    		$(obj).parents("td:eq(0)").find(".js_score_convention_task").click();
			    		for(var key in allData){
							$(btn).parents("tr:eq(0)").find("li[data-id="+key+"]>b").html(allData[key]);
						}
					}else{
						alert(data.message);
					}
				},error:function(){
					alert("网络异常");
				}
			});
    	}
    	
    	function btnSubmitconventionScore(obj){
    		var sc = $(obj).parent().find("input[type=text]");
    		var reg1 = /^[0-9]{1,2}([.][0-9]{1,2}){0,1}$/;
    		
    		var f = true;
    		$(obj).next().html("");
    		$(sc).each(function(){
    			var _this = this;
    			if(!$(this).val()){
    				$(obj).next().html("不能为空!");
        			f=false;
    			}
    			if(f && !reg1.test($(this).val())){
        			$(obj).next().html("格式错误,应为:0到99.99,且最多两位小数");
        			f=false;
        		}
    			if(!f){
    				$(this).focus().css({"border":"1px solid red"});
        			setTimeout(function(){
        				$(_this).css({"border":""});
        			},2000);
        			return false;
    			}
    		});
    		if(!f){
    			return false;
    		}
    		
    		selectconventionScore(sc,obj);
    	}