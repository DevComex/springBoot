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


function Cartoon(){
	//点击验证码切换
	$("#js_yzmBtn").click(function(){
		$("#js_yzmImg").attr("src","http://api.gyyx.cn/Captcha/CreateVJ.ashx?r="+Math.random());
	});
	$("#js_yzmImg").click(function(){
		$("#js_yzmImg").attr("src","http://api.gyyx.cn/Captcha/CreateVJ.ashx?r="+Math.random());
	});

	//点击漫画名称，将所有的名称都展示出来
    $("#js_CartoonName").click(function () {
        $("#js_CartoonName_ul").show();//将隐藏的ul显示出来
        $.ajax({
            url: rc+"/upload/manhualist",
            type: "GET",
            dataType: "json",
            data: {
                r: Math.random
            },
            success: function (data) {
                var data = eval(data);
                if(data.success){
                	if (data.data.length > 0) {
                        var CartoonNameLi="";
                        for (var i = 0; i < data.data.length; i++) {
                            CartoonNameLi += '<li>' +
                                                '<a href="javascript:;" class="js_keyword" data-code="' + data.data[i].authorId + '" data-categoryCode="' + data.data[i].categoryCode + '">' + data.data[i].title + '</a>' +
                                             '</li>';

                        }
                        $("#js_CartoonName_ul").empty().append(CartoonNameLi);

                   }
                }else{
                	//alert(data.message);
                }

            }
        });
    });
}
$(function () {
	//检测登陆状态
    checkLogin({
        success: function (data) {
        	Cartoon();//获取漫画列表
        },
        failed: function (data) {
        	//Cartoon();//获取漫画列表
        	$('#CartoonName_div').click(function (){
        		alertFn(null,null,true);
        	});
        	$('#js_RoleName').click(function (){
        		alertFn(null,null,true);
        	});

        	$('#js_ChapterNumber').click(function (){
        		alertFn(null,null,true);
        	});
        	$('#js_CartoonIntroduction').click(function (){
        		alertFn(null,null,true);
        	});
        	$('#js_yzm').click(function (){
        		alertFn(null,null,true);
        	});
        	$('#js_SaveAlbum').click(function (){
        		alertFn(null,null,true);
        	});
        	$("#js_AlbumsName").click(function(){
        		alertFn(null,null,true);
        	});
        	$("#js_CartoonName").click(function(){
        		alertFn(null,null,true);
        	});

        }
    });


    //获取漫画所有类型
	$.ajax({
		url: rc+"/upload/getalltype",
        type: "GET",
        dataType: "json",
        data: {
            r: Math.random
        },
        success: function (data) {
        	if(data.success){
            	if(data.data.length && data.data){
            		var selectHtml = '<option value="0">请选择漫画类型</option>';
            		for(var i = 0; i < data.data.length;i++){
            			selectHtml += '<option value="'+data.data[i].code+'">'+data.data[i].name+'</option>';
            		}
            		$("#js_cartoonType").html(selectHtml);
            		return selectHtml;
            	}
            }
        }
	});


    $("#js_CartoonName").blur(function () {
    	var categorycode='';

    	$("#js_CartoonName_ul li").on("click",function () {

	        $("#js_CartoonName").val($.trim($(this).text())); //漫画名称的值绑给input 的val()上
	        $("#js_CartoonName_ul").hide();
	        categorycode=$(this).children().attr("data-categorycode");
	        return categorycode;
	    });

    	var CartoonNameCheck = $("#js_CartoonName").val();//手动输入的内容漫画名称
    	console.log($("#js_CartoonName").val());
    	for(var i = 0; i<$("#js_CartoonName_ul li a").length;i++){
    		CartoonNameText=$("#js_CartoonName_ul li a").eq(i).text();//所有的漫画名称text值
			 if(CartoonNameText==CartoonNameCheck){
				 categorycode=$("#js_CartoonName_ul li a").eq(i).attr("data-categorycode");

			 }
		 }
         setTimeout(function () {
             var tmp = $("#js_CartoonName").val();
             if (tmp.trim() === "") { //漫画名称为空
                 $("#js_CartoonName_ul").hide();
                 $("#js_RoleName").val(' ');
    			 $("#js_cartoonType").val('0');
    			 $("#js_CartoonIntroduction").val(' ');
    			 $("#js_AlbumsName").val(' ');
             } else {
                 $.ajax({
                     url: rc+"/upload/checktitle",
                     type: "GET",
                     dataType: "json",
                     data: {
                         r: Math.random,
                         title: tmp.trim()   //漫画名称
                     },
                     success: function (data) {
                    	 var categoryCode = "";
                    	 if(data.success){
                    		 var authorName='',context='',cartoonTypeVal='';
                    		 if(data.data){
                    			 if(data.data.manhuaInfo===null){
                    				 authorName= '';
                    				 context='';

                    				 $("#js_RoleName").removeAttr("readonly");
                        			 //$("#js_CartoonIntroduction").removeAttr("readonly");
                        			 $("#js_cartoonType").removeAttr("disabled");
                    			 }else{
                    				 authorName = data.data.manhuaInfo.authorName;
                    				 context = data.data.manhuaInfo.context;
                    				 $("#js_RoleName").attr("readonly","readonly");
                        			 //$("#js_CartoonIntroduction").attr("readonly","readonly");
                        			 $("#js_cartoonType").attr("disabled","disabled");
                    			 }
                    			 $("#js_CartoonName_ul").hide();
                    			 $("#js_RoleName").val(authorName);
                    			 $("#js_ChapterNumber").val(data.data.book_num);
                    			 $("#js_CartoonIntroduction").val(context);

                    			 //遍历漫画类型code值，相等赋值绑定
                    			 for(var i = 0; i<$("#js_cartoonType option").length;i++){
                    				 cartoonTypeVal=$("#js_cartoonType option").eq(i).attr("value");//漫画类型value值
                    				 if(cartoonTypeVal==categorycode){
                    					 $("#js_cartoonType").val(cartoonTypeVal);
                    				 }
                    			 }



                    		 }
                    	 }else{
                    		 alert(data.message);
                    	 }

                     }
                 });
             }
         }, 150);
    });


    $("#js_CartoonName").keydown(function () {
        $("#js_CartoonName_ul").hide();
    });

    $('#js_NetId').change(function (){
    	var thisval = this.value;
        var opthtml = '<option value="">请选择服务器</option>';
    	$.ajax({
            url: rc+"/upload/serverlist",
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
                        	opthtml += '<option value="'+dCont.data[i].ServerName+'" code="'+dCont.data[i].Code+'">'+dCont.data[i].ServerName+'</option>';
                        }
                        $('#js_ServerIdAndName').html(opthtml);


                        return opthtml;
                    }
            	}

            }
        });
    });
});
