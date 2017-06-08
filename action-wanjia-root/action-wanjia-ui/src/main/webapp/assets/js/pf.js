$(function () {
    $('#testDiv2').slimScroll({
        height: '65px',
        width: '172px'
    });
    $("#testDiv2").css({
        "overflow": " hidden",
        "width": "172px",
        "height": "65px"
    });
    $(".slimScrollDiv").css({
        "position": " relative",
        "overflow": "hidden",
        "width": "172px",
        "height": "65px",
        "margin-top": " 22px",
        "padding": "0 10px"
    });

	//点击
	$(".js_mini_pf").click(function(){
	    // $(this).hide().prev(".pf").show();
	    $(this).hide().prev(".js_pf").show().animate({
	        'margin-left': "0px"
	    }, 300);
	});
	$(".closeBtn").on("click",function(){
	    //$(this).parent(".js_pf").hide("slow").next().show("slow");
	    var _this = $(this);
	    $(this).parent(".js_pf").animate({
	        'margin-left': "-307px"
	    },500,function(){
	        _this.parent(".js_pf").hide().next().show();

	    });

	});
	$(".btn_report").on("click", function () {
	    ajaxDialog();
		$(".mask").show();
		$(".disC").show();
	});
	$(".pf_close").on("click",function(){
		$(".mask").hide();
		$(".disC").hide();
	});
	ajaxScroll();




});







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
            },
            error:function(d){
            	$("#testDiv2").html('<p><span id="_baidu_bookmark_start_19" style="display: none; line-height: 0px;">&zwj;</span><span style="color: rgb(255, 255, 255); font-size: 20px;"><strong>生存和赚钱主题活动开始啦~&nbsp;</strong></span></p><p><span id="_baidu_bookmark_start_29" style="display: none; line-height: 0px;">&zwj;</span><span style="color: rgb(255, 255, 255);">道友们，你们都是咋么赚钱哒！据说再过一个月就要开2017了，<span id="_baidu_bookmark_end_30" style="display: none; line-height: 0px;">&zwj;</span>兜里没钱有点慌啊~大家共享点赚钱的经验吧~~</span></p><p><span style="color: rgb(255, 255, 255);">攻略文（如果能分享一些人多服，比如年度服的赚钱攻略的特点就更好啦～）</span></p><p><span style="color: rgb(255, 255, 255);">活动时间：12月2日--12月31日</span></p><p><span style="color: rgb(255, 255, 255);">一推奖励为5000银元宝，二推奖励为8000银元宝</span></p><p><span style="color: rgb(255, 255, 255);">奖励在活动结束后统一发放~~</span></p><p><span style="color: rgb(255, 255, 255);">玩感受，PK过程，观战感想都不要错过！ 获得推荐的全民PK赛的稿件，基本奖励提升为：一推5000 双推8000的银元宝奖励！ <br>一起来加入吧~</span><span id="_baidu_bookmark_end_20" style="display: none; line-height: 0px;">&zwj;</span><br></p>');

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
                },
                error:function(d){
                	$(".pf_tcBox div").html('<p>道友你终于想当记者啦！</p><p>加问道官方新手记者群 479746808</p><p>认识更多相同志向的小伙伴，查看更多记者相关内容~</p><p><br></p><p>道友也可以直接戳下方的详情按钮查看记者说明哦~</p><p><br></p>');
                }
            });
        }
