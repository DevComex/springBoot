//从地址中获取参数
function getQuery(name){
	var reg =new RegExp("(^|&)"+name+"=([^&]*)(&|$)"),
	r=window.location.search.substr(1).match(reg);
	if(r !== null){
		return r[2];
	}
	return null;
}
$(function(){
	var next = 0;
	var last = 0;
	var ImgUrl = [];  //存放所有图片地址的
	
	//漫画浏览	
	$.ajax({
		url: rc+"/review/showBookInfoAndPage",
		type:"get",
		dataTyep:"json",
		data:{
			r: Math.random(),
			bookCode:getQuery("bookCode")
		},
		success:function(data){
			if(data.success){
				var Data =data.data.pageList ;
				if(Data){	
					var ImgHtml='';
					if(next == 0){
						ImgHtml += '<img class="js_browseImg" pageNum="'+Data[0].page_picture_num+'"  src="'+Data[0].page_picture_url.replace("-small","")+'" />'
						$(".js_browseMain").html(ImgHtml);					
					};
				
					for(var i=0;i<Data.length;i++){
						ImgUrl.push(Data[i].page_picture_url);
					};
					//点击下一页
					$(".nextPage").click(function(){
						
						if(next < ImgUrl.length){
							next= parseInt($(".nextPage").attr("pageNum"))+1;
							last= parseInt($(".lastPage").attr("last_pageNum"))+1;
							$(".nextPage").attr("pageNum",next);
							$(".lastPage").attr("last_pageNum",last);
							$(".js_browseMain").html('<img class="js_browseImg" data-code=next src="'+ImgUrl[next].replace("-small","")+'" />')
						}else{
							alert("已是最后一页了");
						};
					});
					
					//点击上一页
					$(".lastPage").click(function(){
						$(this).attr("disabled");
						if(last <= 0){
							alert("已是第一页了");
							$(this).removeAttr("disabled");
						}else{
							last= parseInt($(".lastPage").attr("last_pageNum"))-1;
							next= parseInt($(".nextPage").attr("pageNum"))-1;
							$(".lastPage").attr("last_pageNum",last);
							$(".nextPage").attr("pageNum",next);
							$(".js_browseMain").html('<img class="js_browseImg" data-code=last src="'+ImgUrl[last].replace("-small","")+'" />')
							$(this).removeAttr("disabled");
						};
						
					});
					
	        	}
			}else{
				alert(data.message);
			}
		}
	});
	

});



