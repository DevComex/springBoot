
$(function(){

	//浏览页面加载进来(判断，如果是从开始阅读进来的，不需要传参数)
	if(getQuery("sign") == "sign"){
		browse();
	}else{
		browse(getQuery("bookNum"),1);
	}

	//点击上一页、章，下一页、章。
	$(".lastChapter").click(function(){
		scrollTo(0,450);
	});
	$(".lastPage").click(function(){
		scrollTo(0,450);
	});
	$(".nextChapter").click(function(){
		scrollTo(0,450);
	});
	$(".nextPage").click(function(){
		scrollTo(0,450);
	});


	//点击上一章
	$(".lastChapter").click(function(){
		var bookNum = parseInt($(this).parent().parent().children(".js_browseMain").children(".js_code").attr("upBookNum")); //章
		if(bookNum === 0){
			$(this).attr("disabled","true");
			alert("已经是第一章内容啦！！！");
		}else{
			browse(bookNum,1);
		}

	});

	//下一章
	$(".nextChapter").click(function(){
		var bookNum = $(this).parent().parent().children(".js_browseMain").children(".js_code").attr("downBookNum"); //章
		if(bookNum == 0){
			$(this).attr("disabled","true");
			alert("已经是最后一章内容啦！！！");
		}else{
			browse(bookNum,1);
		}
	});

	//下一页
	$(".nextPage").click(function(){
		var bookNum = $(this).parent().parent().children(".js_browseMain").children(".js_code").attr("booknum");
		var pageNum = parseInt($(this).parent().parent().children(".js_browseMain").children(".js_code").attr("pageNum"))+1;
		var pageSize = $(this).parent().parent().children(".js_browseMain").children(".js_code").attr("pageSize");
		if(pageNum > pageSize){
			$(this).attr("disabled","true");
			alert("已经是最后一页内容啦！！！");
		}else{
			browse(bookNum,pageNum);
		}
	});

	//上一页
	$(".lastPage").click(function(){
		var bookNum = $(this).parent().parent().children(".js_browseMain").children(".js_code").attr("booknum");
		var pageNum = parseInt($(this).parent().parent().children(".js_browseMain").children(".js_code").attr("pageNum"))-1;
		var pageSize = $(this).parent().parent().children(".js_browseMain").children(".js_code").attr("pageSize");
		if(pageNum<1){
			$(this).attr("disabled","true");
			alert("已经是第一页内容啦！！！");
		}else{
			browse(bookNum,pageNum);
		}
	});

	//最新章节
	$("#js_newChapter").click(function(){
		var bookNum = parseInt($(this).parent().parent().children(".js_browseMain").children(".js_code").attr("booknum"));
		var pageNum = parseInt($(this).parent().parent().children(".js_browseMain").children(".js_code").attr("pageNum"))-1;
		var pageSize = parseInt($(this).parent().parent().children(".js_browseMain").children(".js_code").attr("pageSize"));
		newNum();
	});

	//选择页数
	$(".js_selects").change(function(){
		var pageNum =parseInt($(".js_selects").find("option:selected").attr("value"));
		var bookNum = parseInt($(this).parent().parent().parent().children(".js_browseMain").children(".js_code").attr("booknum"));
		browse(bookNum,pageNum);
	});



	//搜藏
	$("#js_collection").click(function(){
		var code = $(this).parent().parent().children(".js_browseMain").children(".js_code").attr("code");
		var _this = $(this);
		$.ajax({
	        url: rc+ "/catelog/catalogbook",
	        type: "GET",
	        dataType: "json",
	        data: {
	        	r: Math.random(),
	        	code:getQuery("manhuaCode")
	        },
	        success: function (data) {
	        	if(data.message == '收藏成功'){
	        		_this.text('已收藏');
	        		_this.addClass('browseActive').siblings('a').removeClass('browseActive');
	        	}else{
	        		_this.text('收藏');
	        		_this.addClass('browseActive').siblings('a').removeClass('browseActive');
	        	}
	        	alert(data.message);


	        }
	    });
	});


});
//从地址中获取参数
function getQuery(name){
	var reg =new RegExp("(^|&)"+name+"=([^&]*)(&|$)"),
	r=window.location.search.substr(1).match(reg);
	if(r !== null){
		return r[2];
	}
	return null;
}

function newNum(){
	$.ajax({
        url: rc+"/browse/newestBook",
        type: "GET",
        dataType: "json",
        data: {
        	r: Math.random(),
        	manhuaCode:getQuery("manhuaCode")
        },
        success: function (data) {
        	if(data.success){
        		$("#js_newChapter").attr("disabled","true");
        		var Data = data.data;
           		var ImgHtmlNew='';
           			ImgHtmlNew +=  '<div class="js_code" code="'+data.data.code+'"  pageNum="'+Data.page_picture_num+'" bookNum="'+Data.book_num+'" upBookNum="'+Data.upBookNum+'" downBookNum="'+Data.downBookNum+'" pageSize="'+Data.pageSize+'">'+
				    	    			'<span class="catalog_style"></span>'+
				    	    			'<span><a href="home">漫画专区 ></a></span>'+
				    	    			'<span><a href="catalog?manhuaCode='+getQuery("manhuaCode")+'">'+Data.title+'></span>'+
				    	    			'<span>'+Data.book_name+'</span>'+
				    	    		'</div>'+
				    	    		'<div class="js_browseDiv"><img class="js_browseImg" src="'+Data.page_picture_url.replace("-small","")+'" /></div>';

           		 $(".js_browseMain").html(ImgHtmlNew);

            	 return ImgHtmlNew;
        	}else{
        		alert(data.message);
        	}

        }
    });
}


//漫画浏览
function browse(bookNum,pageNum){
	$.ajax({
		url: rc+"/browse/manhuaBrowse",
		type:"get",
		dataTyep:"json",
		data:{
			r: Math.random(),
			manhuaCode:getQuery("manhuaCode"),
			bookNum:bookNum,  //章
			pageNum:pageNum    //节
		},
		success:function(data){
			if(data.success){
				var Data =data.data;
				var option="";
				if(Data){
					//如果是第一章或者最后一章，禁止点击
					if(Data.upBookNum === 0){
						$(".lastChapter").attr('disabled',"true");

					}else if(Data.downBookNum === 0){
						$(".nextChapter").attr('disabled',"true");

					}else if(Data.upBookNum === 0){
						$(".lastPage").attr('disabled',"true");

					}else if(Data.downBookNum == Data.book_num){
						$(".nextPage").attr('disabled',"true");

					}
					if(Data.collect === false){
						$("#js_collection").text("收藏");
					}else{
						$("#js_collection").text("已收藏");
					}

					if(Data){
						option='<option>请选择</option>';
						for(var i=0;i<Data.pageSize;i++){
							option += '<option>'+(i+1)+'页</option>';
						}
						$(".js_selects").html(option);


						//生成目录select
		           		var ImgHtml='';

	       		    	ImgHtml += '<div class="js_code" code="'+Data.code+'"  pageNum="'+Data.page_picture_num+'" bookNum="'+Data.book_num+'" upBookNum="'+Data.upBookNum+'" downBookNum="'+Data.downBookNum+'" pageSize="'+Data.pageSize+'">'+
				    	    			'<span class="catalog_style"></span>'+
				    	    			'<span><a href="home">漫画专区 ></a></span>'+
				    	    			'<span><a href="catalog?manhuaCode='+getQuery("manhuaCode")+'">'+Data.title+' > </a></span>'+
				    	    			'<span>'+Data.book_name+'</span>'+
				    	    		'</div>'+
				    	    		'<div class="js_browseDiv"><img class="js_browseImg" src="'+Data.page_picture_url.replace("-small","")+'" /></div>';
		           		$(".js_browseMain").html(ImgHtml);
		           		return option;
					}


            	}else{
            		$(".js_browseMain").html('<a class="not_concerned">敬请期待！</a>');
            	}
			}else{

			}
		}
	});

}
