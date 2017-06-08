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


	//页面加载进来的时候目录列表
	$.ajax({
        url: rc + "/upload/getalltype",
        type: "GET",
        dataType: "json",
        data: {
        	r: Math.random()
        },
        success: function (data) {
            if(data.success){
            	if(data.data.length && data.data){
            		var munList='<li><a href="javascript:;" data-code="0" class="active">默认</a><span>|</span></li>';
            		for(var i = 0;i<data.data.length;i++ ){
            			munList += '<li><a href="javascript:;" data-code="'+data.data[i].code+'">'+data.data[i].name+'</a><span>|</span></li>';
            		}

            		$(".js_listActive").html(munList);
            		//漫画列表-目录切换
            		$(".js_listActive li a").click(function(){

            			$(this).addClass("active").parent().siblings().find('a').removeClass("active");

            			var dataCode = $(this).attr('data-code');
            			$('.cartoomTitle').attr('data-code',dataCode);
            			var dataType = $('.cartoomTitle').attr('data-type');

            			showList($("#listCartoomMain"),dataCode,dataType);
            		});


            	}

            }

        }
    });


	//最新和最热切换
	$('.js_type a').click(function (){
		$(this).addClass('on').siblings('a').removeClass('on');
		var dataType = $(this).attr('data-type');
		$('.cartoomTitle').attr('data-type',dataType);
		var dataCode = $('.cartoomTitle').attr('data-code');

		showList($("#listCartoomMain"),dataCode,dataType);
	});


	//最新力作和浏览排行榜切换
	showRightList(0,35);
	$(".worksTitle a").click(function(){
		$(this).addClass('newOn').siblings('a').removeClass('newOn');
		var order =  $(this).attr('order');
		var type =  $(this).attr('data-type');
		showRightList(type,order);

	});


	//最新力作和浏览排行展开隐藏
	$('.js_NewestMain').on('click','.js_name',function (){
		$(this).parents('.js_Newestdiv').siblings().find('.js_brief').hide();
		$(this).next().show();
	});

	//道姐推荐点击展开隐藏
	$('.js_recommendMain').on('click','.js_name',function (){
		$(this).parents('.js_Newestdiv').siblings().find('.js_brief').hide();
		$(this).next().show();
	});
	//道姐推荐
	showRightListDao(0,37);
	$(".js_recommend a").click(function(){
		$(this).addClass('newOnBottom').siblings('a').removeClass('newOnBottom');
		var order =  $(this).attr('order');
		var type =  $(this).attr('data-type');
		showRightListDao(type,order);
	});
	//返回顶部按钮
	$(".js_topBtn").click(function(){
		scrollTo(20,0);
	});

	//检测登陆状态
    checkLogin({
        success: function (data) {
        	/*我的收藏--start*/
        	showCollectionList();
        	showList($("#listCartoomMain"),0,0);

        	//收藏
        	$("#listCartoomMain").on("click",".Collection",function(){
        		var code = $(this).attr('data-code');
        		var _this = $(this);
        		$.ajax({
        	        url: rc+"/catelog/catalogbook",
        	        type: "GET",
        	        dataType: "json",
        	        data: {
        	        	r: Math.random(),
        	        	code:code
        	        },
        	        success: function (data) {

        	        	if(data.message == '收藏成功'){
        	        		_this.html('已收藏');
        	        		showCollectionList();
        	        		alert(data.message);
        	        		return _this.html();

        	        	}else{
        	        		_this.html('收藏');
        	        		showCollectionList();
        	        		alert(data.message);
        	        		return _this.html();
        	        	}
        	        	/*showCollectionList();
        	        	alert(data.message)*/


        	        }
        	    });
        	});




        },
        failed: function (data) {

			/*我的收藏--start*/
        	showCollectionList();
        	//未登录点击我的搜藏的那条红字的时候弹出登陆框
        	$(".js_collection").on("click",".not_concerned",function(){
        		alertFn(null,null,true);
        	});

        	showList($("#listCartoomMain"),0,0);
        	//未登录点击阅读的时候弹出登陆框
        	$("#listCartoomMain").on("click",".Collection,.Read",function(){
        		alertFn(null,null,true);
        	});


        }
    });



});
