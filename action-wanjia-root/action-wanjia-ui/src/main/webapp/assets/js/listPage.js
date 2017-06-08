
/*分页中的调用*/
function showList(obj,categoryCode,type,order){
	obj.html('');
	obj.ajaxPage({
		url:rc+"/catelog/bodylist",
		type:"get",
		pageObj:$("#DataTables_Table_0_paginate") ,    //,
		pageIndex: 1,//默认起始页为第一页
		pageSize: 12,//一页个数
		curPageCls: "paginate_active",
		pageInfo: {
			obj: $("#DataTables_Table_0_info"),
	        content: ""
		},
		//定义按钮文字
		paramObj: {
			categoryCode:categoryCode,
			type:type
		},
		dataFn:function(data,$this){

			if(data.success && data.data.total){
				var dCont = data.data.list;//数据列
				var dataHtml = "";

				var redClass= '';
				function isHasCode(str){
					var ucCont = data.data.uclist;//登陆后的数据列
					if(ucCont && ucCont.length){
						var arrs = [];
						for(var j =0; j < ucCont.length; j++){
							arrs.push(ucCont[j].manhuaCode);
						};
						var retule =  arrs.indexOf(str);   //indexOf()，方法，找不到子串返回-1，参数必填，对象或者文字，要在参数对象中查找子字符串
						if(retule == -1){
							return '收藏';
						}else{
							return '已收藏';
						}
					}else{
						return '收藏';
					}
				}

				for (var i = 0; i < dCont.length; i++) {
					if(dCont[i].rank <= 3){
						if(type == 0){
							redClass='redclass';
						}else{
							redClass='redclassHost';
						};
					}else{
						redClass='';
					};
					/*if(i<3){
						if(type == 0){
							redClass='redclass';
						}else{
							redClass='redclassHost';
						}

					}else{
						redClass='';
					}*/
					dataHtml += '<li>'+
				                    '<a  href="catalog?manhuaCode='+dCont[i].code+'">'+
				                        '<img src="'+dCont[i].page_picture_url+'" />'+
				                    '</a>'+
				                    '<div class="cartoonListFrame">'+
				                        '<span class="cartoonListName">'+dCont[i].title+'</span>'+
				                        '<span class="cartoonListChapter">'+dCont[i].book_name+'</span>'+
				                    '</div>'+
				                    '<a class="Collection" data-code="'+dCont[i].code+'" >'+isHasCode(dCont[i].code)+'</a>'+
				                    '<a class="Read" href="catalog?manhuaCode='+dCont[i].code+'">阅读</a>'+
				                    '<p class="'+redClass+'"></p>'+
				                 '</li>';

				}

				obj.html(dataHtml);


				return dataHtml;

			}else{

				obj.html('<li style="text-align:center;width:100%;">暂无数据</li>');
			}
		}
	});

}



/*我的收藏分页调用*/
function showCollectionList(){
	$(".js_collection").ajaxPage({
		url:rc+"/catelog/collectinfo",
		type:"get",
		pageObj:$("#DataTables_Table_0_paginate_collection") ,    //,
		pageIndex: 1,//默认起始页为第一页
		pageSize: 5,//一页个数
		curPageCls: "paginate_active",
		pageInfo: {
			obj: $("#DataTables_Table_0_info"),
	        content: ""
		},
		//定义按钮文字
		paramObj: {
			/*categoryCode:categoryCode,
			type:type*/
		},
		dataFn:function(data,$this){
			if(data.success){
				var dCont = data.data.list;//数据列
				var dataHtml = "";

				if (dCont && dCont.length) {
					for (var i = 0; i < dCont.length; i++) {

						dataHtml +='<li>'+
						                '<a href="catalog?manhuaCode='+dCont[i].code+'">'+
						                    '<img src="'+dCont[i].page_picture_url+'" />'+
						                '</a>'+
						                '<div class="collectionListFrame">'+
						                    '<span class="collectionListName">'+dCont[i].title+'</span>'+
						                    '<span class="collectionListChapter">'+dCont[i].book_name+'</span>'+
						                '</div>'+
						                '<a class="js_read" href="catalog?manhuaCode='+dCont[i].code+'">阅读</a>'+
						            '</li>';

					}
					$(".js_collection").html(dataHtml);

					return dataHtml;
				}
			}else{
				$(".js_collection").html('<div class="js_collection" style="border: 1px solid #ddd;width: 949px;height: 272px"><a class="not_concerned">'+ data.message +'</a><div>');

			}

		}
	});

}


//显示右侧列表
function showRightList(type,order){
	$.ajax({
		url:rc + '/catelog/bodylist',
		type:'get',
		data:{
			categoryCode:0,
			type:type,
			pageIndex:1,
			pageSize:8,
			order:order
		},
		success:function (data){
			var dHtml = '';
			var dCont = data.data.list;
			var See ="";

			if(dCont && dCont.length){
				var redkuang ="";
				for(var i=0; i<dCont.length; i++){
					if(i<3){
						redkuang='redkuang';
					}else{
						redkuang='graykuang';
					}
					dHtml+='<div class="js_Newestdiv">'+
			                    '<div class="js_name">'+
				                	'<span class="newesNumber">'+(i+1)+'</span>'+
				                	'<a class="js_newesName">'+dCont[i].title+'</a>'+

				                '</div>'+

				                '<div class="js_brief">'+
				                    '<a href="catalog?manhuaCode='+dCont[i].code+'"><img src="'+dCont[i].page_picture_url+'"></a>'+  //
				                    '<span class="js_content">'+dCont[i].context+'</span>'+
				                '</div>'+
				                '<span class="'+redkuang+'"></span>'+
				            '</div>';
				}

				$('.js_NewestMain').html(dHtml);
				$('.js_NewestMain .js_Newestdiv').eq(0).find('.js_name').click();


			}else{
				//$('.js_NewestMain').html(data.message);
				$('.js_NewestMain').html('<p style="font-size: 14px;text-align: center;padding-top: 50px;">没有查到相关信息哦~</p>');
			}

		}
	});
}
//道姐推荐
function showRightListDao(type,order){
	$.ajax({
		url:rc + '/catelog/bodylist',
		type:'get',
		data:{
			categoryCode:0,
			type:type,
			pageIndex:1,
			pageSize:8,
			order:order
		},
		success:function (data){
			var dHtml = '';
			var dCont = data.data.list;
			var See ="";

			if(dCont && dCont.length){
				var redkuang ="";
				for(var i=0; i<dCont.length; i++){
					if(i<3){
						redkuang='redkuang';
					}else{
						redkuang='graykuang';
					}
					dHtml+='<div class="js_Newestdiv">'+
			                    '<div class="js_name">'+
				                	'<span class="newesNumber">'+(i+1)+'</span>'+
				                	'<a class="js_newesName">'+dCont[i].title+'</a>'+

				                '</div>'+

				                '<div class="js_brief">'+
				                    '<a href="catalog?manhuaCode='+dCont[i].code+'"><img src="'+dCont[i].page_picture_url+'"></a>'+  //
				                    '<span class="js_content">'+dCont[i].context+'</span>'+
				                '</div>'+
				                '<span class="'+redkuang+'"></span>'+
				            '</div>';
				}

				$('.js_recommendMain').html(dHtml);
				$('.js_recommendMain .js_Newestdiv').eq(0).find('.js_name').click();

			}else{
				//$('.js_recommendMain').html(data.message);
				$('.js_recommendMain').html('<p style="font-size: 14px;text-align: center;padding-top: 50px;">暂无推荐哦~</p>');
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

//目录页评论页--title
function commentList(types,manhuaCode){
	$("#js_commentDiv").ajaxPage({
		url:rc + "/details/comment",
		type:"get",
		pageObj:$("#DataTables_Table_0_paginate_collection2") ,    //,
		pageIndex: 1,//默认起始页为第一页
		pageSize: 3,//一页个数
		curPageCls: "paginate_active",
		pageInfo: {
			obj: $("#DataTables_Table_0_info"),
	        content: ""
		},
		paramObj: {
			categoryCode:0,
			type:types,
			manhuaCode:manhuaCode
		},
		dataFn:function(data,$this){
			if(data.success){
		    	var Data =data.data.list;
		    	var commentTitle ="";
		    	var commentLi = "";
    			var commentLi2="";
    			var listArr=[];
    			var listArr2=[];
		        if(Data.length && Data){
		        	for(var i=0;i<Data.length;i++){
								var userName =Data[i].sourcesAccount;
								if(userName.length>4){
									userName = userName.substring(0,2)+"****"+userName.substring(userName.length-2,userName.length);
								};

		        		commentTitle += '<div class="commentTitle" code='+Data[i].code+'>'+
		        							'<div class="comment_img">'+
							    				'<div style="width:80px;">'+
													'<img src="assets/img/tx.png" />'+  //作者头像
													'<a>'+userName+'</a>'+  //作者账号
												'</div>'+
												'<div class="js_introduce">'+Data[i].context+'</div>'+   // 作者建楼内容
											'</div>'+
											'<div class="js_comment_div">'+
												'<a class="allCom">展开评论<img style="margin-bottom: 4px;margin-left: 4px;" src="assets/img/shou.png" /></a>'+
												'<span>'+Data[i].createTime+'</span>'+  // 时间
											'</div>'+
											'<div class="js_commentkuang">'+
												'<div class="js_commentList">'+
								    				'<ul class="js_commentUl"></ul>'+
												'</div>'+
												'<div style="width:100%;height: 30px;margin-top:15px;margin-bottom: 1%;"><a class="js_say" href="javascript:;">我来说一句<img src="assets/img/shou.png" /></a></div>'+
												'<div class="say_hidden">'+
								    				'<textarea class="js_textarea"></textarea>'+
								    				'<a class="js_publish">发表</a>'+
								    			'</div>'+
								    			'<span class="splitLine"></span>'+
											'</div>'+
										'</div>';



						if(Data[i].list && Data[i].list.length){
							var list=Data[i].list;
							$(".js_commentUl").css("display","block");
							commentLi="";
		        			commentLi2="";
		        			/*截取对应数段中的字符串*/
						    for(var j=0;j<list.length;j++){

						    	var lengthContext = 25;//限制字数
						    	var listContxt = list[j].context;
						    	var contestBox = '';
						    	if(listContxt.length > lengthContext){
						    		var listContxtSub = listContxt.substring(0,24) ;
						    		contestBox = listContxtSub+"...";
						    	}else{
						    		contestBox = list[j].context;
						    	};
						    /*截取对应数段中的字符串*/
						    	//用户名中间内容用***代替
						    	if(list[j].sourcesAccount.length>4){
						    		list[j].sourcesAccount = list[j].sourcesAccount.substring(0,2)+"****"+list[j].sourcesAccount.substring(list[j].sourcesAccount.length-2,list[j].sourcesAccount.length);
						    	};
						    	if(list[j].targetAccount.length>4){
						    		list[j].targetAccount = list[j].targetAccount.substring(0,2)+"****"+list[j].targetAccount.substring(list[j].targetAccount.length-2,list[j].targetAccount.length);
						    	};
						    	commentLi += '<li class="js_commentLi" style="margin-top:3%;">'+
										    	'<div>'+
													'<div style="display:inline;height: 35px;line-height: 35px;">'+
														'<span class="js_commentRead">'+list[j].sourcesAccount+'</span>'+
														'<span style="color:#555;">&nbsp;回复&nbsp;</span>'+
														'<span class="js_commentRead">'+list[j].targetAccount+'</span>说：'+
														'<span class="js_Ellipsis" content="'+ list[j].context+'">'+list[j].context+'</span>'+
													'</div>'+

												'</div>'+
												'<div class="js_Reply">'+
													'<span>'+list[j].createTime+'</span>'+
													'<a class="js_replyA" code='+list[j].code+'>回复</a>'+
												'</div>'+
											 '</li>';
						    	if(j<3){
						    		  commentLi2 += '<li class="js_commentLi" style="margin-top:1%;">'+
											    		'<div>'+
															'<div style="display: inline;height: 35px;line-height: 35px;"">'+
																'<span class="js_commentRead">'+list[j].sourcesAccount+'</span>'+
																'<span style="color:#555;">&nbsp;回复&nbsp;</span>'+
																'<span class="js_commentRead">'+list[j].targetAccount+'</span>说：'+
																'<span  class="js_Ellipsis" content="'+ list[j].context+'">'+contestBox+'</span>'+
															'</div>'+

														'</div>'+
														'<div class="js_Reply">'+
															'<span>'+list[j].createTime+'</span>'+
															'<a class="js_replyA" code='+list[j].code+'>回复</a>'+
														'</div>'+
													 '</li>';

						    	}
						    	if($(".js_commentUl").text === ""){
						    		$(this).css("display","block");
						    	}else{
						    		$(this).css("display","none");
						    	}


						     }



		        		}else{
		        			commentLi="";
		        			commentLi2="";
		        			$(".js_commentUl").eq(i).html(" ");
		        		}
		        		 listArr2.push(commentLi);
			        	 listArr.push(commentLi2);
		        	}
		        	setTimeout(function(){
		        		for(var sl=0;sl<$(".js_commentUl").length;sl++){

		        			$(".js_commentUl").eq(sl).html(listArr[sl]);


		        		}

		        		$(".allCom").toggle(function(){
		        			var _tInex=$(this).parents(".commentTitle").index();
		        			$(this).parents(".js_comment_div").siblings(".js_commentkuang").find(".js_commentUl").html(listArr2[_tInex]);
		        			$(this).text("收起评论");
		        			var removeDian =$(this).parent().parent(".commentTitle").children(".js_commentkuang").children(".js_commentList").children(".js_commentUl").children("li").children(".js_commentEllipsis");
		        			removeDian.removeClass("js_commentEllipsis");
		        		},function(){
		        			var _tInex=$(this).parents(".commentTitle").index();
		        			$(this).parents(".js_comment_div").siblings(".js_commentkuang").find(".js_commentUl").html(listArr[_tInex]);
		        			$(this).text("展开评论");

		        		});
		        	},500);

		    		return commentTitle;
		        }else{
		        	$("#js_commentDiv").html(" ");
		        }

			}

		}
	});


}
