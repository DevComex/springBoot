
function renderHtml(){
    var allheight = $(window).scrollTop() + $(window).height();
    if(allheight > $(".synthetical").offset().top){
        loadArticle($(".synthetical .content"),{pageSize : 8,categoryId : 0});
    }
    if(allheight > $(".interview").offset().top){
        loadArticle($(".interview .content"),{pageSize : 4,categoryId : 9});      
        
    }
    if(allheight > $(".emotion").offset().top){
        loadArticle($(".emotion .content"),{pageSize : 4,categoryId : 1});        
    }
    if(allheight > $(".strategy").offset().top){
        loadArticle($(".strategy .content"),{pageSize : 4,categoryId : 7});
    }
    if(allheight > $(".raceHot").offset().top){
        loadArticle($(".raceHot .content"),{pageSize : 4,categoryId : 8});
    }
    if(allheight > $(".PicJoke").offset().top){
        loadArticle($(".PicJoke .content"),{pageSize : 4,categoryId : 2});
    }
}
//加载首页列表
function loadArticle(obj,data){
    if(obj.parent().attr("data-show") === "false"){
        var datas = {
            pageIndex : 1,
            pageSize : 4,
            categoryId : 0,
            sortType : "view",
            r:Math.random()
        } ;
        $.extend(datas,data);
        $.ajax({
            url:"/article/list",
            type:"get",
            data:datas,
            success:function(d){
                var html = "",Data = d.dataSet;
                if(d.isSuccess){
                    if(Data.length > 0){
                        for(var i = 0; i <Data.length; i++){
                            //是否有副标题
                            var Title = (Data[i].secondTitle === "") ? "" : '【' + Data[i].secondTitle + '】';
                            var crownBox = "",crownCount = "";
                            //判断星级
                            if(parseInt(Data[i].prizeId) > 0){
                                crownBox = '<div class="crownBox">' +
                                                '<p><span class="crownIcon"></span><span class="crownNum">' + Data[i].prizeId + '</span></p>'+
                                            '</div>';
                                for(var j = 0; j < Data[i].prizeId; j++){
                                    crownCount += '<span class="crownIconB"></span>';
                                }
                            }
                            html +=
                            '<div class="articleBox">' +
                                crownBox+
                                '<div class="headPic">'+
                                    '<img src="' + Data[i].cover + '" alt="" width="100%" height="100%">'+
                                '</div>'+
                                '<div class="articleInfo">'+
                                    '<p class="articleTitle"><a title="' + Title +  Data[i].title  + '" href="/article/view/' + Data[i].code + '" target="_blank"><span class="especialTitle">' + Title + '</span>' + Data[i].title + '</a></p>'+
                                    '<p class="articleAuthor"><span class="peopleIcon"></span>【' + Data[i].serverName + '】' + Data[i].author + '</p>'+
                                '</div>'+
                                '<div class="hideInfoBox">'+
                                	'<a href="/article/view/' + Data[i].code + '" target="_black">'+
                                    '<p>' + crownCount + '</p>'+
                                    '<p class="summary">文章简介：<span title="' + Data[i].summary + '">' + Data[i].summary +  '</span></p>'+
                                    '<p><span class="eyeIcon"></span><span class="eyeNum">' + Data[i].viewCountString + '</span><span class="heartIcon"></span><span class="heartNum">' + Data[i].likeCountString + '</span></p>'+
                                    '</a>'+
                                '</div>'+
                            '</div>';
                                
                        }
                        obj.empty().html(html);
                        
                    }else{
                        obj.empty().html('<p style="display:block;height:180px;line-height:180px;padding-left:40px;">数据暂无</p>');
                    }

                }
            }
        });
        obj.parent().attr("data-show","true");
    }
};
$(function(){
    //进入页面即可执行
        //渲染页面
        renderHtml();
        //获取轮播图
        $.get("/content",{slotId :1 , topNumber :6,r:Math.random()},function(d){
            if(d.isSuccess){
                var html = "",_html="",picNum ="";
                if(d.data.length <= 4){
                    for(var i = 0; i < d.data.length; i++){
                        html += '<div class="pic">' +
                                    '<a href="' + d.data[i].url + '" target="_blank" title="' + d.data[i].title + '">' +
                                        '<img src="' + d.data[i].cover + '"  />' +
                                        '</a></div>';
                        if(i === 0){
                            picNum += '<li class="active"></li>';
                        }else{
                            picNum += '<li></li>';
                        }
                    }

                }else{
                    for(var x = 0; x < 4; x++){
                        html += '<div class="pic">' +
                                '<a href="' + d.data[x].url + '" target="_blank" title="' + d.data[x].title + '">' +
                                    '<img src="' + d.data[x].cover + '"  />' +
                                    '</a></div>';
                        if(x === 0){
                            picNum += '<li class="active"></li>';
                        }else{
                            picNum += '<li></li>';
                        }
                    }   
                    for(var j = 0; j < (d.data.length - 4); j++){
                        _html += '<div>' +
                                '<a href="' + d.data[4+j].url + '" target="_blank" title="' + d.data[4+j].title + '">' +
                                    '<img src="' + d.data[4+j].cover + '"  width="100%" height="100%" />' +
                                '</a></div>';
                    }
                    $(".rightBanner").html(_html);
                }
                $(".imgTextscrollableitems").html(html);
                $(".picnum").html(picNum);
                //轮播图
                $(".imgTextscrollbody").scrollContinue({
                   scrollBody: ".imgTextscrollbody",
                   prevBtn: ".btn_pre",
                   nextBtn: ".btn_next",
                   navNum: ".picnum"
               });
            }
        });
        $(".title").each(function(){
            var obj = $(this);
            $.get("/category/getCategory",{categoryType : obj.data("type"),r:Math.random()},function(d){
                if(d.isSuccess){
                    var html = "";
                    if(d.data.length > 0){
                        for(var i = 0; i < d.data.length; i++){
                            html += '<li><a class="js_childType" data-code="' + d.data[i].code + '">' + d.data[i].title + '</a></li>';
                        }
                        obj.find("ul").html(html);
                    }
                }
            });
        });

    $(".title").on("click",".js_childType",function(){
        $(this).parents(".title").find("a").removeClass("on");
        $(this).parents(".title").parent().attr("data-show","false");
        $(this).addClass("on");
        var obj = $(this).parents(".title").siblings(".content");
        loadArticle(obj,{pageSize : 4,categoryId : $(this).attr("data-code")});
    });
    $(window).scroll(function(){
        renderHtml();
    });

    //缩略图鼠标移入事件
    $(".maiin_left").on("mouseleave",".hideInfoBox",function(){
        var obj = $(this);
        obj.stop(true);
        $(this).hide().siblings(".crownBox").show();
    });
    $(".maiin_left").on("mouseenter",".headPic",function(){
        var obj = $(this);
        obj.stop(true);
        $(this).siblings(".hideInfoBox").show().siblings(".crownBox").hide();
    });

    //点击一级标题情感世界
    $(".emotion h2").click(function(){
    	$(".emotion").attr("data-show",false);
    	loadArticle($(".emotion .content"),{pageSize : 4,categoryId : 1}); 
    });

    //点击一级标题采访汇聚
    $(".interview h2").click(function(){
    	$(".interview").attr("data-show",false);
    	loadArticle($(".interview .content"),{pageSize : 4,categoryId : 9});
    });
    
    //点击一级标题玩法攻略
    $(".strategy h2").click(function(){
    	$(".strategy").attr("data-show",false);
    	loadArticle($(".strategy .content"),{pageSize : 4,categoryId : 7});
    });
    
    //点击一级标题赛事热点
    $(".raceHot h2").click(function(){
    	$(".raceHot").attr("data-show",false);
    	loadArticle($(".raceHot .content"),{pageSize : 4,categoryId : 8});
    });
    
    //点击一级标题图文笑话
    $(".PicJoke h2").click(function(){
    	$(".PicJoke").attr("data-show",false);
    	loadArticle($(".PicJoke .content"),{pageSize : 4,categoryId : 2});
    });
});



