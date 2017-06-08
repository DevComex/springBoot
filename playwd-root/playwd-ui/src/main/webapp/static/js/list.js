$(function(){
	//从地址中获取参数
	 function getQuery(name){
	      var reg =new RegExp("(^|&)"+name+"=([^&]*)(&|$)"),
	      r=window.location.search.substr(1).match(reg);
	      if(r != null){
	            return r[2];
	      };
	      return null;
	 };
	 var categoryId = getQuery("categoryId")
	 //页面从首页跳转进来
	 if(categoryId == 0){
		 $(".comprehensive_text").addClass("on li_zh").siblings().removeClass("on li_cf li_qg li_wf li_ss li_tw");		 
		 $(".js_hottest").addClass("li_host");  //最热
		 $(".title_text").text("综合");
		 		 
	 }else if(categoryId == 9){
		 $(".interview_text").addClass("on li_cf").siblings().removeClass("on li_zh li_qg li_wf li_ss li_tw");
		 $(".js_hottest").addClass("li_host");  //最热
		 $(".title_text").text("采访汇聚");
		 
	 }else if(categoryId == 1){
		 $(".emotion_text").addClass("on li_qg").siblings().removeClass("on li_zh li_cf li_wf li_ss li_tw");
		 $(".js_hottest").addClass("li_host");   //最热
		 $(".title_text").text("情感世界");
 		 
	 }else if(categoryId == 7){
		 $(".play_text").addClass("on li_wf").siblings().removeClass("on li_zh li_cf li_qg li_ss li_tw");
		 $(".js_hottest").addClass("li_host");  //最热
		 $(".title_text").text("玩法攻略");
		 
	 }else if(categoryId == 8){
		 $(".match_text").addClass("on li_ss").siblings().removeClass("on li_zh li_cf li_qg li_wf li_tw");		 
		 $(".js_hottest").addClass("li_host");  //最热
		 $(".title_text").text("赛事热点");
 		 
	 }else if(categoryId == 2){
		 $(".joke_text").addClass("on li_tw").siblings().removeClass("on li_zh li_qg li_cf li_wf li_ss");		 
		 $(".js_hottest").addClass("li_host");   //最热	
		 $(".title_text").text("图文笑话");
	 };
	
	 paging(categoryId,"view");
	 
	 //
	 $(".list_Main").on("mouseover",".js_animate",function(){
		 $(this).stop().animate({
			 width: "150%",
		    height: "150%",
		    top: "-25%",
		    left: "-25%",	
		 },"slow")
	 });

	 $(".list_Main").on("mouseout",".js_animate",function(){
		 $(this).stop().animate({
			 width: "100%",
		    height: "100%",
		    top: "0%",
		    left: "0%"
		 },"slow")
	 });
	 
	 //综合
	 $(".comprehensive_text").click(function(){		 
		$(".title_text").text("综合");
 		$(this).addClass("on li_zh").siblings().removeClass("on li_cf li_qg li_wf li_ss li_tw") //综合	
 		$(".js_hottest").addClass("onCli li_host").siblings().removeClass("onCli li_new");
 		paging(0,"view");	
	 });
	 
	 //采访汇聚	 
 	 $(".interview_text").click(function(){
 		$(".title_text").text("采访汇聚");
 		$(this).addClass("on li_cf").siblings().removeClass("on li_zh li_qg li_wf li_ss li_tw"); //综合
 		$(".js_hottest").addClass("onCli li_host").siblings().removeClass("onCli li_new");
 		paging(9,"view");	
	 }); 
	 
 	 //情感世界	 
 	 $(".emotion_text").click(function(){
 		$(".title_text").text("情感世界");
 		$(this).addClass("on li_qg").siblings().removeClass("on li_zh li_cf li_wf li_ss li_tw");  //综合
 		$(".js_hottest").addClass("onCli li_host").siblings().removeClass("onCli li_new");
 		paging(1,"view");	
	 }); 
 	
 	 //玩法攻略	 
	 $(".play_text").click(function(){
		 $(".js_hottest").addClass("onCli li_host").siblings().removeClass("onCli li_new");
		$(".title_text").text("玩法攻略");
		$(this).addClass("on li_wf").siblings().removeClass("on li_zh li_cf li_qg li_ss li_tw");
		paging(7,"view");	
	 });
 	 
	//赛事热点	 
	 $(".match_text").click(function(){
		 $(".js_hottest").addClass("onCli li_host").siblings().removeClass("onCli li_new");
		$(".title_text").text("赛事热点");
		$(this).addClass("on li_ss").siblings().removeClass("on li_zh li_cf li_qg li_wf li_tw");
		paging(8,"view");	
	 });
	
	//图文笑话	 
	 $(".joke_text").click(function(){
		$(".js_hottest").addClass("onCli li_host").siblings().removeClass("onCli li_new");
		$(".title_text").text("图文笑话");
		$(this).addClass("on li_tw").siblings().removeClass("on li_zh li_cf li_qg li_ss li_wf");
		paging(2,"view");	
	 });
 	 	
	var sortType= "";
	//最热
	$(".js_hottest").click(function(){
		var code = $(".on").attr("code");
		paging(code,"view");
		$(this).addClass("onCli li_host").siblings().removeClass("onCli li_new");
		
	});
	//最新
 	$(".js_new").click(function(){
 		var code = $(".on").attr("code");
		paging(code,"new");
 		$(this).addClass("onCli li_new").siblings().removeClass("onCli li_host");
		
	});


});


function paging(categoryId,sortType){
	$(".list_Main").ajaxPageS({
		url: rc+"/article/list",
		type:"get",
		pageObj:$("#DataTables_Table_0_paginate") ,    //,
		pageIndex: 1,//默认起始页为第一页
		pageSize: 10,//一页个数
		curPageCls: "paginate_active",
		pageInfo: {
	        obj: $("#DataTables_Table_0_info"),
	        content: "共{allCount}条  当前第{pageIndex}/{allPage}页"
	    },
        pageBtn: {
            first: "首页",
            last: "末页",
            prev: "上页",
            next: "下页"
        },
	    paramObj: {
	    	r: Math.random(),
	    	categoryId : categoryId,
	    	sortType : sortType
	    },
		dataFn: function (data, $this) {
	        var dCont = data.dataSet;
	        var dataHtml = "";
			if(data.isSuccess){
				if (dCont && dCont.length) {
		            for (var i = 0; i < dCont.length; i++) {
		            	var serverName = (dCont[i].serverName === "") ? '' : '<span class="money_number">【'+dCont[i].serverName+'】</span>';
		            	var summary ="";
		            	if(dCont[i].summary.length<70){
		            		summary = '<h2>'+dCont[i].summary+'</h2>'
		            	}else{
		            		var sum = dCont[i].summary.substr(0,69);
		            		summary = '<h2>'+sum+'...<a style="float:right;padding-right:9px;">详情</a></h2>'
		            	}
		            	if(dCont[i].prizeId>0 && dCont[i].prizeId<6){
		            		dataHtml += '<div class="box_list">'+
						    				'<div class="list_img">'+
						    					'<a href="/article/view/' + dCont[i].code + '" target="_block">'+
							    					'<img src ="'+dCont[i].cover+'" class="js_animate"  style="position:relative" />'+
													'<img src ="/static/images/public/anCrown'+dCont[i].prizeId+'.png" class="js_anCrown"/>'+
												'</a>'+
											'</div>'+
											'<div class="list_content">'+
												'<h1><a target="_blank" href="/article/view/' + dCont[i].code + '">'+dCont[i].title+'</a></h1>'+summary+
												'<span style="display: inline-flex;">'+
													'<span class="span_pepo"><img src="/static/images/public/pepo.png" />'+serverName+'<a>'+dCont[i].author+'</a></span>'+
													'<span class="spanTime"><img src="/static/images/public/sz.png" />'+dCont[i].createTime+'</span>'+
												'</span>'+
											'</div>'+
											'<div class="list_number">'+
												'<span class="zan"><i>'+dCont[i].likeCountString+'</i></span>'+
												'<span class="browse"><i>'+dCont[i].viewCountString+'</i></span>'+
											'</div>'+
										 '</div>';
    					}else{
    		            	dataHtml += '<div class="box_list">'+
						    				'<div class="list_img">'+
						    					'<a href="/article/view/' + dCont[i].code + '" target="_block">'+
						    						'<img src ="'+dCont[i].cover+'" class="js_animate" style="position:relative"/>'+
						    					'</a>'+
						    				'</div>'+
											'<div class="list_content">'+
												'<h1><a target="_blank" href="/article/view/' + dCont[i].code + '">'+dCont[i].title+'</a></h1>'+summary+
												'<span style="display: inline-flex;">'+
													'<span class="span_pepo"><img src="/static/images/public/pepo.png" />'+serverName+'<a>'+dCont[i].author+'</a></span>'+
													'<span class="spanTime"><img src="/static/images/public/sz.png" />'+dCont[i].createTime+'</span>'+
												'</span>'+
											'</div>'+
											'<div class="list_number">'+
												'<span class="zan"><i>'+dCont[i].likeCountString+'</i></span>'+
												'<span class="browse"><i>'+dCont[i].viewCountString+'</i></span>'+
											'</div>'+
										 '</div>';
				        }
		            }
					$(".list_Main").empty().html(dataHtml);
					
		            return dataHtml;
		        };
			}else{
				alert(data.message);
			}

	    },

	});
}