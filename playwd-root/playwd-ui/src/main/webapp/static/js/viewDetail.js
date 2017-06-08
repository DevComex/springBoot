//渲染回复
var _ueditor = '<script id="editor" type="text/plain" style="width: 772px; margin-left:50px; height: 40px;"></script><p><a class="js_submitPL">发表</a></p>';
var textarea = '<textarea id="editor" style="width: 860px; height: 400px;"></textarea>';
var defaultIcon = '/static/images/default_rolepic.jpg';

//初始化加载页数
var page = {
    range : 50,//距下边距的长度
    num:1,//初始当前页面
    html : "<p class='MoreCommet'><a class='js_moreCommet'  data-num='1'>查看更多回帖</a></p>"
};

var ue,ues;
var isTrue=0,dataP;
function replayList(obj,code){
    $.get("/comment/reply/list",{commentId:code,r:Math.random()},function(d){
        if(d.isSuccess){
            var html = '';
            for(var i = 0; i<d.data.length; i++){
                var icon = d.data[i].icon || defaultIcon;
                html +=
                '<div class="secondBoxBar">' +
                    '<div class="headPic">' +
                        '<img src="' + icon + '" alt="">'+
                    '</div>' +
                    '<div class="smallBox_Name">' +
                        '<p class="commenterInfo_Name">' +d.data[i].fromUserAccount + '</p>' +
                    '</div>' +
                    '<div class="content commenterInfo_Content">' +
                        d.data[i].content +
                    '</div>' +
                    '<p class="activeP">' +
                    '<span> ' + d.data[i].replyTime + '</span>    ' +
                    '<span class="reportSpan"> ··· <a class="js_report js_report2" data-userId="' + d.data[i].commentId + '"></a></span>' +
                    '<a class="js_answer">回复</a> ' +
                    '</p>' +
                    '<div class="ueditorBox" data-userId="' + d.data[i].commentId + '"></div>' +
                '</div>';
            }
            obj.parents(".topBar").siblings(".smallBox").html(html);
            
        }
    });
}
$(function(){
            //部署发表评论
            (function(){
                ues = UE.getEditor("editorA",{
                    toolbars: [
                        ['emotion']
                    ],
                    focus:false,
                    wordCount:false,
                    maximumWords:"",
                    wordCountMsg:'',
                    allowDivTransToP:false   
                    
                });
              
            })();
            (function(){
                $.get("/userCenter/userinfo/" + $("#userCode").val(),{},function(d){
                    if(d.isSuccess){
                        $(".peopleHead img").attr("src",d.data.icon || defaultIcon);
                        $(".getNum").html((d.data.rankNum === -1)?"暂无":"第" + d.data.rankNum + "名");
                        $(".getNickName").html((d.data.title === "")?"暂无":d.data.title);
                        var silverCount = d.data.rewardSum >= 10000 ? (d.data.rewardSum/10000).toFixed(1)+"万" : d.data.rewardSum;
                        $(".getPrize").html(silverCount + "银元宝");
                        $(".artucleNum").html(d.data.articleCount + "个");
                    }
                });
            })();
            //收起及展开
            $(".floatType6").click(function(){
                $(this).find("span").html("展开");
                if($(this).attr("data-show") === "true"){
                    for(var i = 1; i < 7; i++){
                        $(".floatType" + i).animate({top:0},"fast","linear");
                    }
                    $(this).attr("data-show","false");

                }else{
                    $(this).find("span").html("收起");
                    for(var i = 1; i < 7; i++){
                        var top = (i-1) * 55;
                        $(".floatType" + i).animate({top:top},"fast","linear");
                    }
                    $(this).attr("data-show","true");
                }
            });
            //二级
            $(".floatType5 .floatBox").mouseenter(function(){
                $(".secondTypeUl").show();
                $(this).addClass("on");
            });
            $(".secondTypeUl").mouseleave(function(){
                setTimeout(function(){
                    $(".secondTypeUl").hide();
                    $(".floatType5 .floatBox").removeClass("on");
                },500);
            });
            //显示微信二维码
            $(".floatType10 .secFloatBox .secIcon").hover(function(){
                $(".shareUrl").show();
            },function(){
                $(".shareUrl").hide();
            });
            
          
            //点击评论按钮
            $(".floatType1").click(function(){                           	
            	$.ajax({
                    url: "http://reg.gyyx.cn/Login/Status",
                    type: "GET",
                    dataType: "jsonp",
                    jsonp: "jsoncallback",
                    success: function (data) {
                    	//判断是否登录并弹出登录层
                        if (!data.IsLogin) { 
                            alertFn(null, null, true);
                        }else{//已登录的话页面滚动到最下边
                        	$(window).scrollTop($("body").height());
                        }
                    }
                });
            });
            
            //点赞
            $(".floatType3").click(function(){
                var obj = $(this);
                //如果没点击过
                if($(this).attr("data-click") === "true"){
                    $.post("/article/praise/" + $("#articleCode").val(),{},function(d){
                        if(d.isSuccess){
                            obj.find("span").html("已点赞");
                            obj.attr("data-click","false");
                            obj.find(".floatBox").addClass("on");
                        }else{
                            if(d.data === "nologin"){
                                alertFn(null,null,true);
                            }
                        }
                    });
                }else{
                    //alert("您已经点过赞了");
                }
            });
            //收藏
            $(".floatType2").click(function(){
                var obj = $(this);
                //如果没点击过
                if($(this).attr("data-click") === "true"){
                    $.post("/article/collect/" + $("#articleCode").val(),{},function(d){
                        if(d.isSuccess){
                            obj.find("span").html("已收藏");
                            obj.attr("data-click","false");
                            obj.find(".floatBox").addClass("on");
                        }else{
                            if(d.data === "nologin"){
                                alertFn(null,null,true);
                            }
                        }
                    });
                }else{
                   // alert("您已经收藏过了");
                }
            });
            //举报显示隐藏按钮
            $(".articleComment_content").on("mouseenter",".reportSpan",function(){
                $(this).find(".js_report").show();
            });
            $(".articleComment_content").on("mouseleave",".reportSpan",function(){
                $(this).find(".js_report").hide();
            });
            $(".articleComment_content").on("mouseleave",".js_report",function(){
                $(this).hide();
            });
            //点击举报
            $(".articleComment_content").on("click",".js_report1",function(){
            	$(this).parents(".topBar").find(".commenterInfo_Name").addClass("reportOn");
                $(".reportBox").show();
                $("#submitReport").attr("data-commentid",$(this).attr("data-userid"));
            });
          //点击举报
            $(".articleComment_content").on("click",".js_report2",function(){
            	$(this).parents(".secondBoxBar").find(".commenterInfo_Name").addClass("reportOn");
                $(".reportBox").show();
                $("#submitReport").attr("data-commentid",$(this).attr("data-userid"));
            });
            //关闭举报弹窗
            $("#popClose").unbind().bind("click",function(){
            	
                $(".reportBox").hide();
                $("#reportNote").val("");
                $(".reportNotebox span").html(200);
                //var sureId =$(this).parents(".reportBox").find("a.submitReport").attr("data-commentid");
                var sureId =$("#submitReport").attr("data-commentid");
                $(".js_report1").each(function(i,d){
                	if($(this).attr("data-userid")==sureId){
                		$(this).parents('.topBar').find(".commenterInfo_Name").removeClass("reportOn");
                	}
                })
                $(".js_report2").each(function(i,d){
                	if($(this).attr("data-userid")==sureId){
                		$(this).parents('.secondBoxBar').find(".commenterInfo_Name").removeClass("reportOn");
                	}
                })
            });
            //选择其他时才可填写描述
            $('input[type="radio"]').change(function(){
                if($(this).val() === "其他"){
                    $("#reportNote").removeAttr("disabled").focus();

                }else{
                    $("#reportNote").attr("disabled",true).val("");
                }
            });
            //动态显示剩余输入字数
            $("#reportNote").keyup(function(){
                var defaultLength = 200;
                if(200-$("#reportNote").val().length < 0){
                    alert("您超过200字了，请删减后重试");
                }else{
                    $(".reportNotebox span").html(200-$("#reportNote").val().length);
                }
            });
            //发送举报
            $("#submitReport").click(function(){
                var reason = $("#reportNote").val() || $('input[type="radio"]:checked').val();
                console.log(reason);
                if($("#reportNote").val().length > 200){
                    alert("您超过200字了，请删减后重试");
                }else{
                    $.post("/report",{
                        reason : reason,
                        commentId: $(this).attr("data-commentid"),
                        r: Math.random()
                    },function(d){
                        alert(d.message);
                        if(d.isSuccess){
                            $("#popClose").click();
                        }else{
                            if(d.data === "no-login"){
                                alertFn(null,null,true);
                            }
                        }
                    });
                }
            });
            //收起
            $(".articleComment_content").on("click",".showMessage",function(){
            	
                var obj = $(this);
                if($(this).attr("data-flag") == "true"){
                    $(this).attr("data-flag","false");
                    $(".ueditorBox").hide();
                    $(this).parents(".topBar").siblings(".smallBox").find(".secondBoxBar").hide();
                    $(this).text($(this).attr("data-html")).removeClass("on");
                }else{
                	
                    $(this).attr("data-flag","true");
                      replayList($(obj),$(obj).attr("data-code"));
                    $(this).parents(".topBar").siblings(".smallBox").find(".secondBoxBar").show();
                    $(this).text("收起回复").addClass("on");
                    $(this).parent().siblings(".ueditorBox").empty();

                }
            });
            //点击发表回复
            $(".articleComment_content").on("click",".js_submitPL",function(){
                var _obj = $(this);
                var $iframe=$(this).parents(".topBar").find("iframe").contents();
                var obj = _obj.parents(".smallBox").find(".secondBoxBar:last");
                var code = $(this).parents(".ueditorBox").attr("data-userId");
               
               var content =UE.getEditor(dataP).getContent().replace(/<p>/g,"").replace(/<\/\p>/g,"").replace(/<br\/\>/g,"").replace(/&nbsp;/g,"").replace(/<span>/g,"").replace(/<\/\span>/g,"");
               //var content =UE.getEditor(dataP).getContent();
               var _name ="回复<span style='color:#939ba2'>"+$(this).parent().parent().parent().find(".commenterInfo_Name").text()+"</span>:";
                //UE.getEditor(dataP).getContentTxt().length
//              
                if($.trim(content)==""){
                	alert("评论内容不能为空！");
                	return false;
                }
                var result =0;
                var c = "<img src="; // 要计算的字符
                var regex = new RegExp(c, 'g'); // 使用g表示整个字符串都要匹配
                if(content.match(regex)){
                	result = content.match(regex).length*93;
                }
               
                if($.trim(content).length-result>300){
                	alert("评论内容不能超过300个字！");
                	return false;
                }
                content=$.trim(_name+content);
                $.post("/comment",{
                    code : code,
                    contentType : "article",
                    content : content,
                    r : Math.random()
                },function(d){
                    if(d.isSuccess){
                    	$(".participationPerson").html(parseInt($(".participationPerson").html())+1);
                    	var obj = _obj.parents(".bigBox").find('.showMessage');
                       /*var html =
                        '<div class="secondBoxBar">' +
                            '<div class="headPic">' +
                                '<img src="' + d.data.icon + '" alt="">'+
                            '</div>' +
                            '<div class="smallBox_Name">' +
                                '<p class="commenterInfo_Name">' +d.data.account + '：</p>' +
                            '</div>' +
                            '<div class="content commenterInfo_Content" data-commentCode="'+ d.data.code+'">' +
                                d.data.content +
                            '</div>' +
                            '<p class="activeP">' +
                            '<span> ' + d.data.createTime + '</span>    ' +
                            '<span class="reportSpan"> ··· <a class="js_report js_report2" data-userId="' + d.data.code + '"></a></span>' +
                            '<a class="js_answer">回复</a> ' +
                            '</p>' +
                            '<div class="ueditorBox" data-userId="' + d.data.code + '"></div>' +
                        '</div>';*/
                        var tmp =_obj.parents(".ueditorBox").siblings(".activeP");
                        
                        //隐藏输入框
                        UE.getEditor(dataP).destroy();
                        _obj.parents(".ueditorBox").hide().empty();
                        //obj.after(html);
                        replayList($(obj),$(obj).attr("data-code"));
                        var count=parseInt($(obj).attr("data-html").split("条回复")[0])+1;
                        $(obj).find("i").html(count);
                        $(obj).attr("data-html",count+"条回复");
                        if(isTrue&&$(obj).attr("data-flag")=="false"){
                           $(obj).click();
                        }else{
                        	isTrue=0
                        }
                    }else{
                        if(d.message =="您还未登陆"){
                        	
                            alertFn(null,null,true);
                        }else{
                        	alert(d.message);

                        	UE.getEditor(dataP).setContent('');
                        }
                    }
                });
              
            });
            //点击发表评论
            
            $(".js_submitPLA").click(function(){
              var vauleC =UE.getEditor('editorA').getContent().replace(/<p>/g,"").replace(/<\/\p>/g,"").replace(/<br\/\>/g,"").replace(/&nbsp;/g,"").replace(/<span >/g,"").replace(/<\/\span>/g,"");
              //var vauleB=$.trim(UE.getEditor('editorA').getContentTxt()); 
               //var vauleC=$.trim(UE.getEditor('editorA').getContent());
            
              var result =0;
              var c = "<img src="; // 要计算的字符
              var regex = new RegExp(c, 'g'); // 使用g表示整个字符串都要匹配
              if(vauleC.match(regex)){
              	result = vauleC.match(regex).length*93;
              }
             
              if($.trim(vauleC).length-result>300){
              	alert("评论内容不能超过300个字！");
              	return false;
              }
               
                /*if(vauleB==""){
                	alert("评论内容不能为空！");
                	return false;
                }*/
                $.post("/comment",{
                    contentType : "article",
                    content : vauleC,
                    contentId:$("#articleCode").val(),
                    r : Math.random()
                },function(d){
                    if(d.isSuccess){
                    	if(d.message=="评论成功"){
                    		alert(d.message);
                    		UE.getEditor('editorA').setContent('');
                    		$(window).scrollTop($(".articleComment").offset().top);
                    		pageHtml(1);
                    		UE.getEditor('editorA').getContent("");
                    		$(".participationPerson").html(parseInt($(".participationPerson").html())+1);
                    		$(".comentPerson").html(parseInt($(".comentPerson").html())+1);
                    	}
                        /*var html =
                        '<div class="bigBox">'+
                            '<div class="topBar">' +
                                '<div class="headPic">' +
                                    '<img src="' + d.data.icon + '">' +
                                '</div>' +
                                '<div class="commenterInfo">' +
                                    '<p class="commenterInfo_Name">' + d.data.account + '</p>'+
                                    '<div class="commenterInfo_Content" data-commentCode="' + d.data.code + '">' + d.data.content + '</div>' +
                                '</div>' +
                                '<p class="activeP">' +
                                    '<span><i class="listNum">' + d.data.num + '</i>楼</span>' +
                                    '<span>' + d.data.createTime + '</span> ' +
                                    '<span class="reportSpan"> ··· <a class="js_report" data-userId="' + d.data.code + '"></a></span>' +
                                    '<a class="js_answer" >回复</a>' +
                                    '<a class="showMessage" data-code="' + d.data.code + '" data-html="0条回复" data-flag="false"><i>0</i>条回复</a>' +
                                    '<a class=""></a>' +
                                '</p>' +
                                '<div class="ueditorBox" data-userId="' + d.data.code + '" ></div>'+
                            '</div><div class="smallBox"></div>'+
                        '</div>';
                        UE.getEditor('editorA').setContent("");
                        $(".articleComment_content bigBox:frist").before(html);*/
                    }else{
                        if(d.message =="您还未登陆"){
                        	
                            alertFn(null,null,true);
                        }else{
                        	
                        	alert(d.message);
                        	
                        }
                    }
                });
            });
            //点击回复
            $(".articleComment_content").on("click",".js_answer",function(){
               
            	$(".ueditorBox").hide();
                $(".scriptEd").remove();
                var obj = $(this);
                var $iframe=$(this).parents(".bigBox").find("iframe").contents();
                if($(this).parent().parent().attr("class")=="topBar"){
                	$(".js_answer").removeAttr("soreceId");
                	$(this).attr("soreceId","answer");
	                	isTrue=1;
                }else{
                	$(".js_answer").removeAttr("soreceId");
                	isTrue=0;
                }
                dataP=Math.random()+""
                
                var _ueditor2='<script class="scriptEd" id="'+dataP+'" type="text/plain" style="width: 772px; margin-left:50px; height: 40px;"></script><p><a class="js_submitPL">发表</a></p>'
                $(this).parent().siblings(".ueditorBox").show().html(_ueditor2);
                //部署编辑器
                
                ue = UE.getEditor(dataP,{
                    toolbars: [
                        ['emotion']
                    ],
                    focus:true,
                    allowDivTransToP:false   
                });
             
                UE.getEditor(dataP).addListener("ready", function () {
                    var name = "";
                    if(obj.parent().parent().hasClass("secondBoxBar")){
                        name = obj.parent().siblings(".smallBox_Name").find(".commenterInfo_Name").text();
                   }else{
                        name = obj.parent().siblings(".commenterInfo").find(".commenterInfo_Name").text();
                    }
                   //UE.getEditor(dataP).setContent("回复 " + name.replace(/[\s\：]*/g,'') + "：");
                   
                  // UE.getEditor(dataP).setContent('<input type="text" disabled=true  style="border: none;background:transparent;float: left;width: 96px;" value="'+"回复 " + name.replace(/[\s\：]*/g,'') + "："+'"/>');
                   //$($iframe).find("p").hover(function(){console.log("dsada");if($($iframe).find("p").text()=="")$($iframe).find("p input").hide();})
                });
                
                //获取回复被人名称
            });
            //点击查看更多回帖
            $(".articleComment_content").on("click",".js_moreCommet",function(d){
                $(this).parent().hide();
                pageHtml("",true);
            });
            //

            //调用显示
            pageHtml();
        });
        function pageHtml(obj,type){
            obj = obj || page.num;
            $.ajax({
                url:"/comment/list",
                type:"GET",
                data:{
                    contentType : "article",
                    contentId : $("#articleCode").val(),
                    pageIndex : obj,
                    pageSize : 5,
                    r:Math.random()
                },
                async:false,
                success:function(d){
                    if(d.isSuccess){
                        if(d.data.length >0){
                            var html = "";
                            for(var i = 0; i < d.data.length; i++){
                                var icon = d.data[i].icon || defaultIcon;
                                html +=
                                '<div class="bigBox">'+
                                    '<div class="topBar">' +
                                        '<div class="headPic">' +
                                            '<img src="' + icon + '">' +
                                        '</div>' +
                                        '<div class="commenterInfo">' +
                                            '<p class="commenterInfo_Name">' + d.data[i].fromUserAccount + '</p>'+
                                            '<div class="commenterInfo_Content">' + d.data[i].content + '</div>' +
                                        '</div>' +
                                        '<p class="activeP">' +
                                            //'<span><i class="listNum"></i>楼</span>' +
                                            '<span>' + d.data[i].commentTime + '</span> ' +
                                            '<span class="reportSpan"> ··· <a class="js_report js_report1" data-userId="' + d.data[i].commentId + '"></a></span>' +
                                            '<a class="js_answer" data-userId="' + d.data[i].commentId + '">回复</a>' +
                                            '<a class="showMessage" data-code="' + d.data[i].commentId + '" data-html="' + d.data[i].replyCount + '条回复" data-flag="false"><i>' + d.data[i].replyCount + '</i>条回复</a>' +
                                        '</p>' +
                                        '<div class="ueditorBox" data-userId="' + d.data[i].commentId + '" ></div>'+
                                    '</div><div class="smallBox"></div>'+
                                '</div>';
                            } 
                            if(type){
                          	  $(".articleComment_content").append(html);
                          	  $(".web_main").css("background",'#fff');
                            }else{
                          	  $(".articleComment_content").html(html);
                            }
                            page.num +=1;
                            $(".articleComment_content .smallBox:last").after(page.html);  
                        }else{
                            $(".MoreCommet:last").text("没有更多数据了");
                        }
                    }else{
                        alert(d.message);
                    }

                }
            });
        }
