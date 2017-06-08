/*
by chenqiaoling 2014/06/10
Version: v1.0


_init()，初始化搜索框
_showSearchPanel()，显示搜索结果面板
_hideSearchPanel()，隐藏搜索结果面板
_eventFocus()，搜索框获取焦点事件
_eventKeyUp()，搜索框keyup事件
_eventBlur()，搜索框失去焦点效果。取点击事件判断是否隐藏结果面板
_getResult()，ajax取得搜索结果并展示在结果面板
_choiceResult()，结果面板里鼠标点击事件
_showResultList()，将在结果面板里选择的项展示在输入框
_deleteChoice()，删除已选择的项   


*/
/*==============update  log  ===============*/
/*
by chenqiaoling 2014/7/16
添加键盘上下方向键效果、enter键效果
*/
/*
 by pengjia 2015/5/27
 增加 MaxNameLen 参数，来限制输入框中的人名数量
 */
/*===================================*/
(function ($) {
    $.fn.extend({
        OaSearch: function (options) {
            /*============================================*/
            /*  AjaxSearchUrl :  搜索接口地址*/
            /*  InputObj :  搜索输入框*/
            /*  InputType : 搜索输入框限制格式  可选 all(不限制)，zhw(中文)*/
            /*  listKey : 显示在下拉列表里每项的数据键值，默认为RealName*/
            /*  InputKey : 选择搜索结果后存入搜索框的数据键值，默认为RealName(Code)*/
            /*  ManuallyEnter : 是否允许手动输入，默认为允许true*/
            /*  MaxNameLen : ["0",""]限制输入框最多显示几个人名，默认不限制为0，第二个参数为限制时 的提示信息 eg:["1","只能输入1个人名"] */
            /*==================================================*/
            var Defaults = {
                AjaxSearchUrl: "http://oa.gyyx.cn/staffinfo/GetStaffName",
                InputObj: ".js_oaSearch",
                InputType: "all",
                listKey: "RealName",
                InputKey: ["RealName", "Code"],
                ManuallyEnter: true,
                MaxNameLen:["0",""]
            };
            Defaults = $.extend(Defaults, options);
            
            var $oaSearch = $(this);

            var resultFormatKey = []; //ajax请求返回的数据格式的键值

            //初始化搜索框
            var _init = function () {
                ////添加搜索结果面板
                //if ($(".js_searchPanel").length == 0) {
                //    $("body").append("<ul class='js_searchPanel' style='display:none;'></ul><style type='text/css'>.js_searchPanel li.cur{ background:#eee;}</style>");
                //}
                //var inputObjWidth = $oaSearch.width();

                //搜索外框和实际输入框样式及点击样式
                $oaSearch.find(Defaults.InputObj).wrap("<span style='float:left;'></span>");
                $oaSearch.css({ 'cursor': 'text', 'height': 'auto', 'overflow': 'hidden' });
                $(Defaults.InputObj).css({ "border": "none", "outline": "none", "background": "none", "width": "150px" });
                $oaSearch.click(function () {
                    $(this).find(Defaults.InputObj).focus();
                    $(this).css("border", "1px solid #65bd77");
                });

                //初始化输入框里的值
                var initVal = $.trim($oaSearch.find(".js_oaSearchResult").val());
                if (initVal) {
                    var initValList = initVal.split(" ");
                    if (initValList.length > 0) {
                    	// $oaSearch.find(Defaults.InputObj).parent().empty();
                        for (var i = 0; i < initValList.length; i++) {
  
                            $oaSearch.find(Defaults.InputObj).parent().before("<span style='float:left;margin:2px;padding:0 2px;background:#89cc97;color:#fff;cursor:pointer;'><i class='fa fa-times'></i>" + initValList[i] + "</span>");
                
                        }
                    }
                }
                _deleteChoice();
                
            }          

            //格式转换，将4px转换为4
            var _parsePx = function (str) {
                return parseInt(str.substring(0, str.length - 2), 10);
            }

            //显示搜索结果面板，定位在输入框下方
            var _showSearchPanel = function (obj) {

                //添加搜索结果面板
                if ($oaSearch.find($(".js_searchPanel")).length <=1) {
                $oaSearch.append("<ul class='js_searchPanel' style='display:none;'></ul><style type='text/css'>.js_searchPanel li.cur{ background:#eee;}</style>");
                }

                var inputW = obj.width();
                var inputH = obj.height();
                //var inputT = obj.offset().top;
                //var inputL = obj.offset().left;

                var top = inputH + _parsePx(obj.css("paddingTop")) + _parsePx(obj.css("paddingBottom")) + _parsePx(obj.css("borderTopWidth")) + _parsePx(obj.css("borderBottomWidth"));
                var width = inputW + _parsePx(obj.css("paddingLeft")) + _parsePx(obj.css("paddingRight")) + _parsePx(obj.css("borderLeftWidth")) + _parsePx(obj.css("borderRightWidth"));
                var left = _parsePx(obj.css("paddingLeft")) + _parsePx(obj.css("borderLeftWidth"))+2;

                $oaSearch.find(".js_searchPanel").css({ 'position': 'absolute', 'width': width, 'top': top, 'left': left, 'listStyle': 'none', 'margin': 0, 'padding': 0, 'backgroundColor': '#fff', 'zIndex': 9999 });
                $(".js_searchPanel").find("li.cur").css({ "backgroundColor": "#eee" });
            }

            //隐藏搜索结果面板
            var _hideSearchPanel = function () {
                $oaSearch.find(".js_searchPanel").hide();
            }

            //搜索框获取焦点事件
            var _eventFocus = function () {
                var $obj = $oaSearch.find(Defaults.InputObj);
                $obj.focus(function (e) {
                    $oaSearch.css("border", "1px solid #65bd77");
                    _showSearchPanel($oaSearch);
                    var _val = $.trim($(this).val());
                    if (_val.length != 0) {
                        _getResult(this,e);
                    } else {
                        _hideSearchPanel();
                    }
                });
            }

            //搜索框keyup事件
            var _eventKeyUp = function () {
                var $obj = $oaSearch.find(Defaults.InputObj);
                $obj.keyup(function (e) {
                    $oaSearch.css("border", "1px solid #65bd77");
                    
                    //手动输入时按空格后存入值
                    if ($(this).val().substring($(this).val().length - 1, $(this).val().length) == " ") {
                        var result = $(this).val().substring(0, $(this).val().length - 1);
                        result = $.trim(result);
                        if (result) {
                            if (Defaults.ManuallyEnter) {//允许手动输入
                                _showResultList(this, result);
                            } 
                        }
                    }

                    _showSearchPanel($oaSearch);
                    var _val = $.trim($(this).val());
                    if (_val.length != 0) {
                        _getResult(this,e);
                    } else {
                        _hideSearchPanel();
                    }
                });
            }

            //搜索框失去焦点效果。取点击事件判断是否隐藏结果面板
            var _eventBlur = function () {
                $(document).click(function (e) {
                    var inputId = $oaSearch.attr("id");
                    var inputObj = Defaults.InputObj.substring(1, Defaults.InputObj.length);
                    if (!$(e.target).hasClass(inputObj) && !$(e.target).parent().hasClass("js_searchPanel") && $(e.target).attr("id") != inputId) {
                        _hideSearchPanel();
                        $oaSearch.css("border", "1px solid #d9d9d9");
                    }
                });
            }

            // ajax取得搜索结果并展示在结果面板
            var _getResult = function (obj,e) {

                var $obj = $(obj);
                var val = "";
                //不限制输入格式
                if (Defaults.InputType == "all") {
                    val = $.trim($obj.val());
                    if (!/[u4e00-u9fa5]/.test($.trim(val))) {
                        val = encodeURI(val);
                    }
                } else if (Defaults.InputType == "zhw") {  //限制输入为中文
                    if (/[u4e00-u9fa5]/.test($.trim($obj.val()))) {
                        return false;
                    }
                    val = $.trim($obj.val());
                    val = encodeURI(val);
                }
               
                $.ajax({
                    url: Defaults.AjaxSearchUrl + "?name=" + val,
                    type: "get",
                    dataType: "jsonp",
                    jsonp: "jsoncallback",
                    data: {
                        r: Math.random()
                    },
                    success: function (d) {
                        var array = d;
                        var item = "";
                        if (array.length > 0) {
                            //存储数据格式里每项名称
                            for (var key in array[0]) {
                                resultFormatKey[resultFormatKey.length] = key;
                            }
                            for (var i = 0; i < array.length; i++) {
                                var liAttr = "";
                                var listKey = "";
                                for (var key in array[i]) {
                                    var keyval = "";
                                    if (array[i][key] != "" && array[i][key] != null) {
                                        keyval = array[i][key];
                                    }
                                    liAttr += ' data-' + key + '="' + keyval + '"';
                                    if (key == Defaults.listKey) {
                                        listKey = array[i][key];
                                    }
                                }
                                item += "<li " + liAttr + " style='padding-left:3px'>" + listKey + "</li>";
                            }
                        }
                        $oaSearch.find(".js_searchPanel").empty().append(item);
                        $oaSearch.find(".js_searchPanel").show();

                        /*   add by chenqiaoling  2014.7.16  */
                        //$(document).unbind().bind("keydown", function (e) {
                        //    var keycode = e.whith || e.keyCode;
                        //    if (keycode == 40) {
                        //        //down
                        //        var index = $oaSearch.find(".js_searchPanel li.cur").index();
                        //        if (index == $oaSearch.find(".js_searchPanel li").length - 1) {
                        //            $oaSearch.find(".js_searchPanel li").removeClass('cur').eq(0).addClass('cur');
                        //        } else { $oaSearch.find(".js_searchPanel li").removeClass('cur').eq(index + 1).addClass('cur'); }
                        //        $oaSearch.find(Defaults.InputObj).blur();
                        //        return false;
                        //    } else if (keycode == 38) {
                        //        //up
                        //        var index = $oaSearch.find(".js_searchPanel li.cur").index();
                        //        if (index == 0) {
                        //            $oaSearch.find(".js_searchPanel li").removeClass('cur').eq($oaSearch.find(".js_searchPanel li").length - 1).addClass('cur');
                        //        } else {
                        //            $oaSearch.find(".js_searchPanel li").removeClass('cur').eq(index - 1).addClass('cur');
                        //        }
                        //        $oaSearch.find(Defaults.InputObj).blur();
                        //        return false;
                        //    } else if (keycode == 13) {
                        //        //enter
                        //        if ($oaSearch.find(".js_searchPanel").css("display") == "block" && $oaSearch.find(".js_searchPanel li").length > 0 && $oaSearch.find(".js_searchPanel li").hasClass("cur")) {
                        //            var $curli = $oaSearch.find(".js_searchPanel li.cur");
                        //            var result = {};
                        //            if (resultFormatKey.length > 0) {
                        //                for (var i = 0; i < resultFormatKey.length; i++) {
                        //                    result[resultFormatKey[i]] = $curli.attr("data-" + resultFormatKey[i]);
                        //                }
                        //            }
                        //            var inputVal = [Defaults.InputKey.length];
                        //            var val = "";
                        //            for (var key in result) {
                        //                for (var j = 0; j < Defaults.InputKey.length; j++) {
                        //                    if (key == Defaults.InputKey[j]) {
                        //                        inputVal[j] = result[key];
                        //                    }
                        //                }
                        //            }

                        //            if (Defaults.InputKey.length == 2) {
                        //                val = inputVal[0] + "(" + inputVal[1] + ")";
                        //            } else {
                        //                val = inputVal[0];
                        //            }

                        //            _showResultList(obj, val);
                        //        }

                        //    }
                        //});
                        /* end */

                        _choiceResult(obj);
                    }
                });

            }

            //结果面板里鼠标事件。
            var _choiceResult = function (obj) {
                var $obj = $(obj);
                $oaSearch.find(".js_searchPanel").find("li").hover(function () {
                    $(this).addClass("cur").siblings().removeClass("cur");
                });               

                $oaSearch.find(".js_searchPanel").find("li").click(function () {
                    //取出当前选中项的各data属性，通过事先存储好的resultFormatKey取得每个data属性
                    var result = {};
                    if (resultFormatKey.length > 0) {
                        for (var i = 0; i < resultFormatKey.length; i++) {
                            result[resultFormatKey[i]] = $(this).attr("data-" + resultFormatKey[i]);
                        }
                    }
                    var inputVal = [Defaults.InputKey.length];
                    var val = "";
                    for (var key in result) {
                        for (var j = 0; j < Defaults.InputKey.length; j++) {
                            if (key == Defaults.InputKey[j]) {
                                inputVal[j] = result[key];
                            }
                        }
                    }

                    if (Defaults.InputKey.length == 2) {
                        val = inputVal[0] + "(" + inputVal[1] + ")";
                    } else {
                        val = inputVal[0];
                    }
                    
                    _showResultList(obj, val);
                                      
                });

            }

            //将在结果面板里选择的项展示在输入框
            var _showResultList = function (obj, val) {

                var top = _parsePx($oaSearch.css("paddingTop"));
                var bottom = _parsePx($oaSearch.css("paddingBottom"));
                var right = _parsePx($oaSearch.css("paddingRight"));

                var oldval = $(obj).parent().siblings(".js_oaSearchResult").val();
                var oldvalList = oldval.split(" ");
                var oldflag = true;
                for (var i = 0; i < oldvalList.length; i++) {
                    if (oldvalList[i] == val) {
                        oldflag = oldflag && false;
                    }
                }

                if(Defaults.MaxNameLen[0]!="0"){//限制长度
                    if($oaSearch.find("span:not(:last)").length>=Defaults.MaxNameLen[0]){
                        alert(Defaults.MaxNameLen[1]);return false;
                    }
                }

                if (!oldflag) {
                    alert("请不要重复选择！");
                    $(obj).val($.trim($(obj).val()));
                    return false;
                } else {
                    $(obj).val("");
                    _hideSearchPanel();

                    $oaSearch.find(Defaults.InputObj).parent().before("<span style='float:left;margin:2px;padding:0 2px;background:#89cc97;color:#fff;cursor:pointer;'><i class='fa fa-times'></i>" + val + "</span>");
                    
                    $(obj).parent().siblings(".js_oaSearchResult").val($.trim(oldval + " " + val));
                }
                _deleteChoice();
            }

            //删除已选择的项
            var _deleteChoice = function () {
                $oaSearch.find("span:not(:last)").on("click",function () {
                    $(this).remove();
                    var newval = "";
                    $oaSearch.find("span").each(function (i, item) {
                        if (i == 0) {
                            newval += $(this).text();
                        } else {
                            newval += " " + $(this).text();
                        }
                    });

                    $oaSearch.find(".js_oaSearchResult").val($.trim(newval));
                });
            }

            _init();
            _eventFocus();
            _eventKeyUp();
            _eventBlur();

        }
    });
})(jQuery);