/*js 工具类*/

Date.prototype.pattern = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, // 小时
		"H+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	var week = {
		"0" : "/u65e5",
		"1" : "/u4e00",
		"2" : "/u4e8c",
		"3" : "/u4e09",
		"4" : "/u56db",
		"5" : "/u4e94",
		"6" : "/u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt
				.replace(
						RegExp.$1,
						((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f"
								: "/u5468")
								: "")
								+ week[this.getDay() + ""]);
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
}

//包含左侧width
function _form_valid_altTab01() {
	$("body").delegate("span.error_valid_msg_modal_tip","mouseover",function(){
			var navWidth = $("#nav").width();
            var _val = $(this).attr('xcon');
            if (_val != null) {
                var _x = $(this).offset().left - navWidth;
                var _y = $(this).offset().top-100;
                _y = _y + 14;
                _x = _x - 22;
                $(".form_valid_mess").css({
                    display: 'block',
                    left: _x + 'px',
                    top:_y  + "px"
                });
                $(".form_valid_con01 p").html(_val);
            }
        }
	);
	
	$("body").delegate("span.error_valid_msg_modal_tip","mouseout",function(){
		$(".form_valid_mess").css('display', 'none');
	});	
}

//不包含左侧width
function _form_valid_altTab02() {
	$("body").delegate("span.error_valid_msg_modal_tip","mouseover",function(){
			var navWidth = $("#nav").width();
            var _val = $(this).attr('xcon');
            if (_val != null) {
                var _x = $(this).offset().left;
                var _y = $(this).offset().top-50;
                _y = _y + 14;
                _x = _x - 22;
                $(".form_valid_mess").css({
                    display: 'block',
                    left: _x + 'px',
                    top:_y  + "px"
                });
                $(".form_valid_con01 p").html(_val);
            }
        }
	);
	
	$("body").delegate("span.error_valid_msg_modal_tip","mouseout",function(){
		$(".form_valid_mess").css('display', 'none');
	});	
}

$(function(){
    	
      _form_valid_altTab01();
})

