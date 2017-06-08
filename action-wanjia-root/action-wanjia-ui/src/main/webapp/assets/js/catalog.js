 function checkLogin(fn) {
    $.ajax({
        url: "http://reg.gyyx.cn/Login/Status",
	    type: "GET",
	    dataType: "jsonp",
	    jsonp: "jsoncallback",
	    data: {},
	    success: function (d) {
	        if (d.IsLogin) {
	            if (fn) {
	                fn.success(d);
	            }
	        }else {
	            if (fn) {
	                fn.failed(d);
	            }
	        }
	    }
    });
}


$(function(){
	//页面加载调用评论title
	commentList(1,getQuery("manhuaCode"));

	//页面加载调用章节列表
	ChapterList();

	//目录页面加载展示--漫画板块
	comicPlate();

	//他们还看过
	TheySee(0,39);
	//他们还看过点击内容
	$('.js_theyMain').on('click','.js_theyName',function (){
		$(this).parents('.js_theyDiv').siblings().find('.js_theyBrief').hide();
		$(this).next().show();
	});


	//众说纷纭作者有话说切换
	$(".commentTitleWord a").click(function(){
		//切换时调用评论title
		$(this).addClass('js_sayActive').siblings('a').removeClass('js_sayActive');
		var type=$(".js_sayActive").attr("type");
		commentList(type,getQuery("manhuaCode"));

	});

	//点赞收藏评论，hover时显示小手掌
	$(".catalog_kuang").hover(function(){
		$(this).css("cursor","pointer");
	});

	//我来说一句，显示影藏
	$('#js_commentDiv').on('click','.js_say',function (){
		$(".say_hidden").toggle();
	});

	//点击回复显示影藏  ---发表框
	$('#js_commentDiv').on('click','.js_Reply',function (){
		$(".say_hidden").toggle();
	});


	//检测登陆状态
    checkLogin({
        success: function (data) {
        	//收藏
    		$(".catalog").on("click",".js_shoucang",function(){
    			Collection();
        	});

    		//点赞
    		$(".catalog").on("click",".js_zan",function(){
    			fabulous();
    		});

    	   //评论
    	   $(".catalog").on("click",".js_pinglun",function(){
    		   scrollTo(0,1500);
    	   });

    	   //点击最下面的发表，调用接口
    	   $('.js_myPublish').on('click',function (){
    		   var lengthContext = 130;//限制字数
    		   if($.trim($(this).prev().val()).length>lengthContext){
		    		alert("输入最多不可超过130个文字和标点,包括空格！");
    		   }else if($.trim($(this).prev().val()) === ""){
    			   alert("请输入发表内容");
    		   }else{
    			   var context= $(this).prev().val();
    	    	   comment(context);
    	    	   $(this).prev().val(' ');
    		   }

    	   });

    	    //点击发表，调用接口
    		$('#js_commentDiv').on('click','.js_publish',function (){
    			var lengthContext = 130;//限制字数
		    	//var listContxt = $.trim($(".js_textarea").val());

		    	if($.trim($(this).prev().val()).length>lengthContext){
		    		alert("输入最多不可超过130个文字和标点,包括空格！");
		    	}else if($.trim($(this).prev().val()) === ""){
		    		alert("请输入评论内容");
		    	}else{
		    		var context= $(this).prev().val();
	    			var code = $(this).parents(".commentTitle").attr("code");
	    			comment(context,code);
	    			$(this).prev().val(' ');
		    	}

    		});

    		//点击回复，调用接口(可以登录以后放入上面)
    		$('.catalog').on('click','.js_replyA',function (){
    			var context= $(this).prev().val();
    			var code = $(this).parents(".commentTitle").attr("code");
    			comment(context,code);//回复调用的函数
    			$(this).prev().val(' ');
    		});


        },
        failed: function (data) {
        	//收藏
    		$(".catalog").on("click",".js_shoucang",function(){
    			alertFn(null,null,true);
        	});

    		//点赞
    		$(".catalog").on("click",".js_zan",function(){
    			alertFn(null,null,true);
    		});

    	   //评论
    	   $(".catalog").on("click",".js_pinglun",function(){
    		   alertFn(null,null,true);
    	   });

    	   //点击最下面的发表，调用接口
    	   $('.js_myPublish').on('click',function (){
    		   alertFn(null,null,true);
    	   });

    	    //点击发表，调用接口
    		$('#js_commentDiv').on('click','.js_publish',function (){
    			alertFn(null,null,true);
    		});

    		//点击回复，调用接口(可以登录以后放入上面)
    		$('#js_commentDiv').on('click','.js_replyA',function (){
    			alertFn(null,null,true);
    		});

    		//点击我来说一句
    		$('#js_commentDiv').on('click','.js_say',function (){
    			alertFn(null,null,true);
    		});



        }
    });


});




//收藏
function Collection(){
	$.ajax({
        url: rc+"/catelog/catalogbook",
        type: "GET",
        dataType: "json",
        data: {
        	r: Math.random(),
        	code:getQuery("manhuaCode")
        },
        success: function (data) {
    		if(data.message == '收藏成功'){
    			comicPlate();
        	}else if(data.message == '您还未登录，请点击登录按钮进行登录'){
        		//alertFn(null,null,true);
        		alert("登陆超时，请重新登陆");
        	}else{
        		comicPlate();
        	}

        }
    });
}

//目录页面加载展示--漫画板块
function comicPlate(){
	$.ajax({
        url: rc+"/browse/InfoAndStatus",
        type: "GET",
        dataType: "json",
        data: {
        	r: Math.random(),
        	manhuaCode:getQuery("manhuaCode")
        },
        success: function (data) {
        	if(data.success){
        		var Data = data.data;
        		if(Data){
        			var dhtml = '<div>'+
					    			'<div class="catalog_title">'+
					    				'<span class="catalog_style"></span>'+
					    				'<span><a href="home">漫画专区  ></a></span>'+
					    				'<span>'+Data.title+' </span>'+
					    				/*'<span>'+Data.author+'</span>'+*/
					    			'</div>'+
					    			'<div class="catalog_main">'+
					    				'<img class="catalog_img" src="'+Data.pic.replace("-small","")+'"  />'+
					    				'<div class="catalog_content">'+
					    					'<span class="catalog_name">'+Data.title+'</span>'+
					    					'<span>作者：'+Data.author+'</span>'+
					    					'<span class="js_word_zan">被赞：<span>'+Data.goodCount+'</span></span><br/>'+
					    					'<span class="js_word_content" maxlength=10>'+Data.context+'</span>'+
					    					'<div>'+
				    	    					'<a class="reading" href="browse?manhuaCode='+getQuery("manhuaCode")+'&sign=sign">开始阅读</a>'+
				    	    					'<div class="catalog_kuang">'+
				    	    						'<span class="js_shoucang"><img src="assets/img/start.png"  />收藏</span><span class="line">|</span>'+
				    	    						'<span class="js_zan"><img src="assets/img/zan.png" />点赞</span><span class="line">|</span>'+
				    	    						'<span class="js_pinglun"><img src="assets/img/pl.png"  />评论</span>'+
				    	    					'</div>'+
				    	    				'</div>'+
					    				'</div>'+
					    			'</div>'+
					    		 '</div>';

					   $(".catalog").html(dhtml);

					   //点赞
					   if(Data.goodStatus){//true以经点赞，按钮置灰
						   $(".js_zan").text("已点赞");
						   $(".js_zan").css("color","#aaa");
						   //$(".reading").text("'++'");

					   }


					   //收藏
					   if(Data.collectStatus){//以收藏，按钮置灰
						   $(".js_shoucang").text("已收藏");
						   $(".js_shoucang").css("color","#aaa");
						   //comicPlate();
					   }
					   //是否阅读
					   if(Data.readStatus){//以阅读，按钮置灰
						   $(".reading").text("继续阅读");
					   }

					   $(".reading").click(function(){
						   isReading();
					   });



        		}
        	}else{
        		alertFn(null,null,true);
        	}

        }
    });
}

//点赞
function fabulous(){
	$.ajax({
        url: rc+"/browse/addGoodStatus",
	    type: "GET",
	    dataType: "json",
	    data: {
	    	r: Math.random(),
	    	manhuaCode:getQuery("manhuaCode")
	    },
	    success: function (data) {
	        if(data.success){
	        	//$(".js_zan").css("color","#ddd");
	        	comicPlate();
	        }else{
	        	if(data.message == "您还未登录，请点击登录按钮进行登录"){
	        		//alertFn(null,null,true);
	        		alert("登陆超时，请重新登陆");
	        	}
	        }
	    }
    });
}

//页面加载进来的时候章节列表
function ChapterList(){
	$.ajax({
        url: rc+"/details/bookList",
        type: "GET",
        dataType: "json",
        data: {
        	r: Math.random(),
        	manhuaCode:getQuery("manhuaCode")
        },
        success: function (data) {
            if(data.success){
            	var Data =data.data;
                var chapterList="",select="";
            	if(Data.list.length && Data.list){
            		//目录列表
            		for(var i = 0;i<Data.list.length;i++ ){
            			if(Data.list[i].newly == "NEW"){
            				chapterList += '<li manhuCode="'+Data.list[i].manhuaCode+'"  bookNum="'+Data.list[i].bookNum+'"><img class="newImg" src="assets/img/newImg.png"><a href="browse?manhuaCode='+getQuery("manhuaCode")+'&bookNum='+Data.list[i].bookNum+'">'+Data.list[i].bookName+'</a></li>';
            			}else{
            				chapterList += '<li manhuCode="'+Data.list[i].manhuaCode+'"><a href="browse?manhuaCode='+getQuery("manhuaCode")+'&bookNum='+Data.list[i].bookNum+'">'+Data.list[i].bookName+'</a></li>';
            			}
            		}
            		$(".js_chapterUl").html(chapterList);

            	}
            	if(Data.group){
            		//生成目录select
	           		var json = Data.group;
	           		select='<option>请选择章节</option>';
           		    for(var key in json){
           		    	select += '<option key="'+key+'">'+json[key]+'</option>';
           		        //alert(key+':'+json[key]);
           		    }
	         		$(".js_catalogSelect").html(select);

	         		//选择不同的目录调用接口
	         		$(".js_catalogSelect").change(function(){//选择章节列表
	         			var key=$(".js_catalogSelect").find("option:selected").attr("key"); //获取Select选择的key
	         			$.ajax({
	         		        url: rc+"/details/bookGroupList",
	         		        type: "GET",
	         		        dataType: "json",
	         		        data: {
	         		        	r: Math.random(),
	         		        	manhuaCode:getQuery("manhuaCode"),
	         		        	groupKey:key
	         		        },
	         		        success: function (data) {
	         		            if(data.success){
	         		            	var Data =data.data;
	         		            	if(Data.length && Data){
	         		            		var chapterList="",select="";
	         		            		//目录列表
	         		            		for(var i = 0;i<Data.length;i++ ){
	         		            			if(Data[i].newly == "NEW"){
	         		            				chapterList += '<li manhuCode="'+Data[i].manhuaCode+'" bookNum="'+Data[i].bookNum+'"><img class="newImg" src="assets/img/newImg.png"><a href="browse?manhuaCode='+getQuery("manhuaCode")+'&bookNum='+Data[i].bookNum+'">'+Data[i].bookName+'</a></li>';
	         		            			}else{
	         		            				chapterList += '<li manhuCode="'+Data[i].manhuaCode+'" bookNum="'+Data[i].bookNum+'"><a href="browse?manhuaCode='+getQuery("manhuaCode")+'&bookNum='+Data[i].bookNum+'">'+Data[i].bookName+'</a></li>';
	         		            			}

	         		            		}
	         		            		$(".js_chapterUl").html(chapterList);
	         		            	}
	         		            }else{
	         		            	alert(data.message);
	         		            }
	         		        }
	         			});
	         		});
        		}

        		//标记阅读记录
        		$(".js_chapterUl li a").click(function(){
        			isReading();
        		});


            }

        }
    });
}

//从地址中获取参数
function getQuery(name){
	var reg =new RegExp("(^|&)"+name+"=([^&]*)(&|$)"),
	r=window.location.search.substr(1).match(reg);
	if(r !== null){
		return r[2];
	}
	return null;
}

//他们还看过
function TheySee(type,order){
	$.ajax({
		url:rc+'/catelog/bodylist',
		type:'get',
		data:{
			categoryCode:0,
			type:type,
			pageIndex:1,
			pageSize:8,
			order:order
		},
		success:function (data){
			var dHtmlB = '';
			var dCont = data.data.list;

			if(dCont && dCont.length){
				var redThey ="";
				for(var i=0; i<dCont.length; i++){
					if(i<3){
						redThey='redThey';
					}else{
						redThey='grayThey';
					}
					dHtmlB +=   '<div class="js_theyDiv">'+
									'<div class="js_theyName">'+
					                	'<span class="theyNumber">'+(i+1)+'</span>'+
					                	'<a class="js_theyNewName">'+dCont[i].title+'</a>'+
					                '</div>'+
					                '<div class="js_theyBrief">'+
					                    '<a href="catalog?manhuaCode='+dCont[i].code+'"><img src="'+dCont[i].page_picture_url+'" /></a>'+
					                    '<span class="js_theyContent">'+dCont[i].context+'</span>'+
					                '</div>'+
					                '<span class="'+redThey+'"></span>'+
				                '</div>';

				}

				$('.js_theyMain').html(dHtmlB);
				//alert(dHtmlB);
				$('.js_theyMain .js_theyDiv').eq(0).find('.js_theyName').click();
				return dHtmlB;

			}else{
				//$('.js_theyMain').html(data.message);
				$('.js_theyMain').html('<p style="font-size: 14px;text-align: center;padding-top: 50px;">没有查到相关信息哦~</p>');
			}

		}
	});
}

//评论页，发表和回复
function comment(context,code){
	if($.trim(context) !== ""){
		var type=$(".js_sayActive").attr("type");
		$.ajax({
	        url: rc+"/details/saveComment",
	        type: "post",
	        dataType: "json",
	        data: {
	        	r: Math.random(),
	        	type:type,
	        	manhuaCode:getQuery("manhuaCode"),
	        	context:context,
	        	parentCode:code
	        },
	        success: function (data) {
	        	if(data.success){
	        		alert(data.message);
	        		commentList(type,getQuery("manhuaCode"));//刷新一下评论处，可以实时更新上去
	        	}else{
	        		if(data.message == "您还未登录，请点击登录按钮进行登录"){
	        			//alertFn(null,null,true);
	        			alert("登陆超时，请重新登陆");

	        		}else{
	        			alert(data.message);
	        		}

	        	}

	        }
	    });
	}

}


//是否阅读的标记
function isReading(){
	$.ajax({
        url: rc+"/browse/readCountPlus",
        type: "get",
        dataType: "json",
        data: {
        	r: Math.random(),
        	manhuaCode:getQuery("manhuaCode")
        },
        success: function (data) {

        }
    });
}
