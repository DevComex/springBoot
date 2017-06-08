
$(function () {
    //$().UItoTop({ scrollSpeed: 600 });

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

     //头部导航 根据URL来判断当前是哪个页面
    var  currUrl=window.location.href.toUpperCase();
    var currurlid=1;
    if (currUrl.indexOf("ARTICLE")>0) {
        currurlid=2;
    } else if (currUrl.indexOf("PICTURE") > 0) {
        currurlid=3;
    } else if (currUrl.indexOf("VIDEO") > 0) {
        currurlid=4;
    } else {
        currurlid=1;
    }

    //默认显示当前页面的蓝色标示
    function moren(){
        var $this=$(".nav li").eq(currurlid-1);
        for (var i = 1; i <= 4; i++) {
            $this.siblings().removeClass("on" + i);
        }
        $this.addClass("on" + currurlid);
    }

    moren();

    //鼠标划入划出效果
    $(".nav li").hover(function() {
        var overid=$(this).attr('alt');
        $(this).addClass("on" + overid);
    }, function() {
        var overid=$(this).attr('alt');
        if (overid!=currurlid) {
            $(this).removeClass("on" + overid);
        }
    });


    //获取url参数值
    function getQueryString(name) {
      var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r !== null) return unescape(r[2]); return null;
    }

    //处理搜索相关 20141229lcf
    var $keyWordIpt = $("[name='s_KeyWord']");
    $("#js_SearchForm").submit(function () {
        if (!$.trim($keyWordIpt.val())) {
            alert("请输入关键字搜索！");
            return false;
        }
        var sKeyWord=$.trim($keyWordIpt.val());
        $keyWordIpt.val(sKeyWord);
        openNewTab("/Home/Search?s_KeyWord=" + escape(sKeyWord));
        return false;
    });

});

//新窗口打开方法
function openNewTab(url) {
    var a = document.createElement("a");
    a.setAttribute("href", url);
    a.setAttribute("target", setSearchTarget());
    document.body.appendChild(a);
    a.click();
}

//设置指向
function setSearchTarget() {
    var thisHref = window.location.pathname.toUpperCase();
    if (thisHref.indexOf("SEARCH") > 0) {
        return "_self";
    } else {
        return "_blank";
    }
}

//收藏
function collectWorks(options) {
    var
        url = options.url || "/User/collects",
        code = options.code,
        collectType = options.collectType;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "JSON",
        data: {
            r: Math.random(),
            SourcesCode: code,
            SourcesType: collectType
        },
        success: function (data) {
            if (options.success) {
                options.success(data);
            } else {

            }
        },
        error: function (e) {
            options.error && options.error(e);
        }
    });
}

$.fn.extend({
	popw:function(option){
		var _this = $(this);
		if(_this.length==0){
			return false;
		}
		if(typeof option == "string"){
			if(option=="open"){
				_this.parents(".pop_wrap_w").show();
			}else if(option=="close"){
				_this.parents(".pop_wrap_w").hide();
			}
		}else{
			var defaults = {
					width:"800px",
					height:"644px"
				};
				$.extend(defaults,option);
				
				var wrap = document.createElement("div");
				var pop = document.createElement("div");
				var mask = document.createElement("div");
				var popCon = document.createElement("div");
				var close = document.createElement("a");
				
				wrap.className = "pop_wrap_w";
				pop.className = "pop_w";
				mask.className = "mask_w";
				popCon.className = "pop_con_w";
				close.className = "close_w";
				
				pop.style.width = parseInt(defaults.width)+"px";
				pop.style.height = parseInt(defaults.height)+"px";
				
				pop.style.marginLeft = -parseInt(defaults.width)/2+"px";
				pop.style.marginTop = -parseInt(defaults.height)/2+"px";
				
				popCon.appendChild(_this[0]);
				
				pop.appendChild(close);
				pop.appendChild(popCon);
				
				wrap.appendChild(pop);
				wrap.appendChild(mask);
				
				wrap.style.display = "none";
				document.body.appendChild(wrap);
				close.onclick = function(){
					wrap.style.display = "none";
				}
		}
		
		
	}
})
