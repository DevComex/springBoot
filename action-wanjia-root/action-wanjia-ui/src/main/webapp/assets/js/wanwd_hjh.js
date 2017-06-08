$(function(){
	var url={
			myMessageCartoon: rc + "/user/getMessage",//我的漫画信息
			editorAnswerMsg: rc + "/user/getManagerReply",//编辑回复
			myUploadMsg:rc + "/user/getUpload",//我的上传-漫画
		    getCollectCartoonUrl: rc + "/user/getCollects",//我的收藏-漫画
		    cartoonLikeForCancelUrl: rc + "/catelog/catalogbook",//漫画取消收藏
		    getBingRoleUrl:rc+"/user/GetUser" //获取绑定角色信息
	};
	var pageHtml = '' +
    '<div class="mt30 s_page_wrap">' +
    '   <span class="gy_page search_page"></span>' +
    '   <span class="search_page_txt"></span>' +
    '</div>';
	var loadHtml ='<img class="search_loadimg" />';
	   //头部 上传按钮 划入划出效果
    var timer = null;
    $(".js_upload").hover(function () {
        $(".js_upload_ul").show();
    }, function () {
        $(".js_upload_ul").hide();
        timer = setTimeout(hover_upload, 50);
    });

    function hover_upload() {
        $(".js_upload_ul").hover(function () {
            clearTimeout(timer);
            $(this).show();
        }, function () {
            $(this).hide();
        });
    }
	getRoleInfo();//获取绑定角色信息
	//编辑回复-漫画
	 if (location.href.indexOf("userCenter/editorAnswer") != -1) {

		 //展开所有文字
		 $(".wd_message").on("click",".js_msgMore",function(){
			 var msg = $(this).attr("alt");
			 $(this).siblings('p').html(msg);
			 $(this).hide();
		 });
		 //编辑回复-漫画-分页
		 $("#js_editorAnswer").ajaxPageS({
			 url: url.editorAnswerMsg,
             pageObj: $(".search_page"),
             pageSize: 3,
             curPageCls: "gy_pageon",
             pageData: "",
             pageInfo: {
                 obj: $(".search_page_txt"),
                 content: "第{pageIndex}页  共{allPage}页"
             },
             pageBtn: {
                 first: "首页",
                 last: "末页",
                 prev: "上一页",
                 next: "下一页"
             },
             paramObj: {SourcesType:4,r:Math.random()},
             dataFn: function (data) {
                 var
                     dataHtml = "",
                     dCont = data.DataSet,
                     a = "";

                 if (!data.success) {
                	 if(data.message == '您还未登陆'){
		            		nologin($("#js_editorAnswer"));
		            	}else{
		            		alert(data.message);
		            	}
                     return false;
                 }
                 if (dCont && dCont[0]) {
                     for (var i = 0; i < dCont.length; i++) {
                    	 if(dCont[i].conntext.length >50){
                    		 a = '<a class="js_msgMore" alt="' + dCont[i].conntext + '">[展开]</a>';
                    	 }
                         dataHtml += '' +
                             '<li>' +
                             	'<div class="answerTitle">'+
                             		'<h3>编辑回复：</h3>'+
                             		'<span>' + dCont[i].create_time + '</span>'+
                             	'</div>'+
                             	'<div>'+
                             		'<p style="word-wrap: break-word;">' + enterLine(dCont[i].conntext) +'</p>'+a+
                             	'</div>'+
                             '</li>';
                     }

                     return dataHtml;
                 } else {
                	 $("#js_editorAnswer").html("<h3 class='no_data_h3'>暂无消息</h3>");
                     return false;
                 }
             }
		 });
	 }
	//我的上传-漫画
	if (location.href.indexOf("userCenter/myUpload") != -1) {
		uploadType("DEFAULT");
		$("#state").change(function(){
			var _type = $("#state option:selected").data("status");
			uploadType(_type);
		});
		$("#js_CollectContainer").on("click",".js_moreInfo",function(){
			var msg = $(this).attr("alt");
			 $(this).parent().siblings('p').html(msg);
			 $(this).hide();
		});
		function uploadType(type){


			$("#js_CollectContainer").html('<div class="box_inner playp full_width"><ul class="p_tvlist v_up_list" id="js_Cont_CartoonLi"></ul>' + pageHtml + '</div>');
			$("#js_Cont_CartoonLi").html("<li>" + loadHtml + "<li>").ajaxPageS({
				url: url.myUploadMsg,
		        dataType:"JSONP",
		        pageObj: $(".search_page"),
		        pageSize: 3,
		        curPageCls: "gy_pageon",
		        pageInfo: {
		            obj: $(".search_page_txt"),
		            content: "第{pageIndex}页  共{allPage}页"
		        },
		        pageBtn: {
		            first: "首页",
		            last: "末页",
		            prev: "上一页",
		            next: "下一页"
		        },
		        paramObj: {SourcesType:4,r:Math.random(),status : type},
		        dataFn: function (data) {
		            var
		                dataHtml = "",
		                dCont = data.DataSet,
		                a='';
		            var recommemd = '';
		            if (!data.success) {
		            	if(data.message == '您还未登陆'){
		            		nologin($("#js_Cont_CartoonLi"));
		            	}else{
		            		alert(data.message);
		            	}
		                return false;
		            }
		            if (dCont && dCont[0]) {
		                for (var i = 0; i < dCont.length; i++) {
		                	if(dCont[i].isRecommemd == 1){
		                		recommemd = '编辑推荐';
		                	}else{
		                		if(dCont[i].review_status == "SUCCESS"){
		                			recommemd = '审核通过';
		                		}else if(dCont[i].review_status == "REVIEW_ING"){
		                			recommemd = '审核中';
		                		}else if(dCont[i].review_status == "FAIL"){
		                			recommemd = '审核不通过';
		                		}
		                	}
		                	if(dCont[i].context.length >50){
	                    		 a = '<a class="js_msgMore" alt="' + dCont[i].conntext + '">[展开]</a>';
	                    	 }
		                    dataHtml += '' +
		                        '<li>' +
		                        	'<div class="cartoonUploadBox">'+
		                        		'<div class="cartoonUploadTitle" style="margin-bottom:5px;">'+
		                        			'<h3>' + dCont[i].title + '</h3>'+
		                        		'</div>' +
		                        		'<div class="cartoonUploadContent">'+
		                        			'<div class="cartoonLeftPic">' +
		                        				'<img src="' + dCont[i].page_picture_url + '" alt="封面" />' +
		                        				'<p>' + recommemd + '</p>'+
		                        			'</div>' +
		                        			'<div class="cartoonRightInfo">' +
		                        				'<p>' + enterLine(dCont[i].context) + '</p>' +
		                        				'<div><a class="js_moreInfo" alt="' + dCont[i].context + '">【详情】</a></div>' +
		                        			'</div>' +
		                        		'</div>' +
		                        	'</div>' +


		                        '</li>';
		                }

		                return dataHtml;
		            } else {
		            	$("#js_CollectContainer").html("<h3 class='no_data_h3'>暂无消息</h3>");
		                return false;
		            }
		        },
		        debug: false
			});
		}
	}
	//我的消息
if (location.href.indexOf("userCenter/myMessage") != -1) {
	$("#js_MsgCont").empty().ajaxPageS({
        url: url.myMessageCartoon,
        pageObj: $(".search_page"),
        pageSize: 3,
        curPageCls: "gy_pageon",
        pageInfo: {
            obj: $(".search_page_txt"),
            content: "第{pageIndex}页  共{allPage}页"
        },
        pageBtn: {
            first: "首页",
            last: "末页",
            prev: "上一页",
            next: "下一页"
        },
        paramObj: {SourcesType:4,r:Math.random()},
        dataFn: function (data) {
            var
                dataHtml = "<a href='http://wanwd.gyyx.cn/user/Message'>文章 | 图片 | 视频 |</a><a style='color:#1598dc' href='javascript:;'> 漫画</a>",
                dCont = data.DataSet,
                a='';
            if (!data.success) {
            	if(data.message == '您还未登陆'){
            		nologin($("#js_MsgCont"));
            	}else{
            		alert(data.message);
            	}
                return false;
            }
            if (dCont && dCont[0]) {
                for (var i = 0; i < dCont.length; i++) {
                	if(dCont[i].isShow === 0 && dCont[i].review_status =="SUCCESS"){
                		a='<a href="'+ dCont[i].url +'" target="_blank">查看</a></p>';
                	}else{
                		a='';


                	}
                    dataHtml += '' +
                        '<li>' +
                        //'   <div class="del_ico"></div>' +
                        '    <div class="message_cont">' +
                        '        <h3>' +
                        '            <span class="message_tit textover" style="color:#1598dc;">' + dCont[i].title + '</span>' +
                        '            <span class="mess_time">' + dCont[i].createTime + '</span>' +
                        '        </h3>' +
                        '        <p class="wordbreak">' + dCont[i].message + a +
                        '    </div>' +
                        '</li>';
                }

                return dataHtml;
            } else {
            	$("#js_MsgCont").html("<h3 class='no_data_h3'>暂无消息</h3>");
                return false;
            }
        },
        debug: false
    });
	}
//我的搜藏-漫画
if (location.href.indexOf("userCenter/myCollectCartoon") != -1) {
	$("#js_CollectContainer").html('<div class="box_inner playp full_width"><ul class="p_tvlist v_pic_list" id="js_Cont_Cartoon"></ul>' + pageHtml + '</div>');
	ajaxpage();
	function ajaxpage(){
		$("#js_Cont_Cartoon").html("<li>" + loadHtml + "<li>").ajaxPageS({
			url: url.getCollectCartoonUrl,
	        dataType:"JSONP",
	        pageObj: $(".search_page"),
	        pageSize: 4,
	        curPageCls: "gy_pageon",
	        pageInfo: {
	            obj: $(".search_page_txt"),
	            content: "第{pageIndex}页  共{allPage}页"
	        },
	        pageBtn: {
	            first: "首页",
	            last: "末页",
	            prev: "上一页",
	            next: "下一页"
	        },
	        paramObj: {SourcesType:4,r:Math.random()},
	        dataFn: function (data, $this) {

	            var dataHtml = "", dCont = data.DataSet;
	            if (!data.success) {
	            	if(data.message == '您还未登陆'){
	            		nologin($("#js_Cont_Cartoon"));
	            	}else{
	            		alert(data.message);
	            	}
	                return false;
	            }
	            if (dCont && dCont[0]) {
	                for (var i = 0; i < dCont.length; i++) {
	                    var href = 'http://wanwdv2.gyyx.cn/browse?manhuaCode=' + dCont[i].code;
	                    dataHtml += '' +
	                        '<li class="cartoonList" style="width:170px;height:290px;border:0;">' +
	                            '<div class="cartoonListBox">' +
	                                '<div class="titlePicBox">' +
	                                    '<a href="' + href + '"><img src="' + dCont[i].page_picture_url + '" alt="封面" style="width:100%; height:100%;" /></a>' +
	                                    '<div class="bookName" style="opacity: 0.7;"><h4>' + dCont[i].title + '</h4><p> 最新章节：<span>' + dCont[i].book_name + '</span></p></div>' +
	                                '</div>' +
	                                '<div class="buttonBox" data-code="' + dCont[i].code + '">' +
	                                    '<a href="javascript:;" class="cartoonButton js_collectCartoon" data-code="' + dCont[i].code + '">已收藏</a><a href="' + href + '" class="cartoonButton js_read">阅读</a>' +
	                                '</div>' +
	                            '</div>' +
	                        '</li>';
	                }
	            } else {
	            	$("#js_CollectContainer").html("<h3 class='no_data_h3'>暂无消息</h3>");
	                return false;
	            }
	            return dataHtml;
	        }
	    });
	}
	 $("#js_CollectContainer").on("click", ".js_collectCartoon", function () {
         obj = $(this);
         $.ajax({
             url: url.cartoonLikeForCancelUrl,
             type: "get",
             data: {
                 code: $(this).data("code"),
                 r:Math.random()
             },
             success: function (d) {
                 if(d.success === true){
                	 alert(d.message);
                     if (d.message == "已取消收藏！") {
                         obj.parents("li").remove();
                         ajaxpage();

                     }
                 }else{
                	alert(d.message);
                	return false;
                 }

             }
         });
     });
}
	//公用方法
	//超过100字节缩略加···
	function enterLine(msg){
		 if(msg.length >100){
			 msg = msg.substring(0,50)+"···";
			 return msg;
		 }else{
			 return msg;
		 }
	}
	//未登录
	function nologin(obj){
		obj.html('<h2>您还未登陆，请先<a href="javascript:;" onclick="alertFn(null,null,true); return false;" style="color:red;">登录</a>后再查看<h2>');
	}
	//获取绑定角色
	function getRoleInfo(){
		$.ajax({
			url:url.getBingRoleUrl,
			type:"post",
			dataType:"json",
			data:{
				r:Math.random()
			},
			success:function(d){
				if(d.IsSuccess){
					if (d.List && d.List[0]) {
						for(var i = 0,len =d.List.length;i<len;i++ ){
							if (d.List[i].isDefault) {
								$("#js_RoleName").html(d.List[i].nickName+"【服务器："+d.List[i].serverName+"】");
				                $("#js_HeadPortrait").find("img").attr("src", rc+"/assets/img/portrait/"+d.List[i].picture+".jpg");
							}

						}
					}else{
						$("#js_RoleName").html("未有同步角色");
					}

				}else{
					if (d.Values == "-3") {//未登录
                        alertFn(null, null, true);
						$("#js_RoleName").html('<span class="sp_nologin">您尚未登录，请<a href="javascript:;" onclick="alertFn(null,null,true); return false;">登录</a>后再查看！</span>');
						$(".top_bg_red").attr("data-status",false);
					} else {
                        alert(d.Message);
                    }
				}
			},
			error:function(){
				console.log("异常");
			}
		});

	}
});
