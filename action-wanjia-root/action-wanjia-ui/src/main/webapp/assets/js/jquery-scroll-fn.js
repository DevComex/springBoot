/*
    轮播效果
	add: 2014/8/21
	by tianhaiting
*/
(function ($) {
    $.fn.extend({
        "scrollContinue": function (options) {
            /*-----------参数说明--------------*/
            /*
              scrollBody:轮播容器，
              prevBtn：轮播左侧按钮，
              nextBtn：轮播右侧按钮，
              navNum：轮播焦点
            */
            /*-----------参数说明--------------*/
            var defaults = {
                scrollBody: ".scrollablebody",
                prevBtn: ".focus_pre",
                nextBtn: ".focus_next",
                navNum: ".focus_nav"
            };

            $.extend(defaults, options);

            $(defaults.scrollBody).hover(function () {
                
                $(defaults.prevBtn + "," + defaults.nextBtn).show();
            }, function () {
                $(defaults.prevBtn + "," + defaults.nextBtn).hide();
            });
            $(defaults.prevBtn + "," + defaults.nextBtn).hover(function () {
                $(defaults.prevBtn + "," + defaults.nextBtn).show();
            })

            $(defaults.scrollBody).scrollable({ circular: true }).navigator(defaults.navNum).autoscroll({ interval: 5000 });//调用轮播插件

        }
    
    });
    

})(jQuery);
