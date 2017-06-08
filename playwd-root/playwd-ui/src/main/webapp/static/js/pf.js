$(function () {
    $('#testDiv2').slimScroll({
        height: '65px',
        width: '172px'
    });
    $("#testDiv2").css({
        "overflow": " hidden",
        "width": "172px",
        "height": "65px"
    })
    $(".slimScrollDiv").css({
        "position": " relative",
        "overflow": "hidden",
        "width": "172px",
        "height": "65px",
        "margin-top": " 22px",
        "padding": "0 10px"
    })

	//点击
	$(".js_mini_pf").click(function(){
	    // $(this).hide().prev(".pf").show();
	    $(this).hide().prev(".js_pf").show().animate({
	        'margin-left': "0px"
	    }, 300)
	});
	$(".closeBtn").on("click",function(){
	    //$(this).parent(".js_pf").hide("slow").next().show("slow");
	    var _this = $(this);
	    $(this).parent(".js_pf").animate({
	        'margin-left': "-307px"
	    },500,function(){
	        _this.parent(".js_pf").hide().next().show();

	    })
	    
	})
	$(".btn_report").on("click", function () {
	    ajaxDialog();
		$(".mask").show();
		$(".disC").show();
	})
	$(".pf_close").on("click",function(){
		$(".mask").hide();
		$(".disC").hide();
	})
	ajaxScroll();
	
	

       
})




        


//请求悬浮
        function ajaxScroll(){
            var html = "";
        	$.ajax({
            url: "http://wanwd.gyyx.cn/notice/getinfo",
            type: "get",
            dataType: "json",
            data: { r: Math.random(),noticeType:"Suspend"},
            success: function (d) {           
                if (d.IsSuccess) {
                    html = decodeURI(d.Data);
                    $("#testDiv2").html(html);
                   
                }
                else {
                    $("#testDiv2").html("<p>暂无信息</p>");
                }
            }
        });
        }
//请求弹窗的数据
        function ajaxDialog() {
            var dialoghtml = "";
            $.ajax({
                url: "http://wanwd.gyyx.cn/notice/getinfo",
                type: "get",
                dataType: "json",
                data: { r: Math.random(), noticeType: "Popup" },
                success: function (d) {
                    if (d.IsSuccess) {
                        dialoghtml = decodeURI(d.Data);
                        $(".pf_tcBox div").html(dialoghtml);
                       
                    }
                    else {
                       $("#scrollBoxUl li").html("<p>暂无信息</p>");
                    }
                }
            });
        }

