/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：李根
 * 联系方式：ligen@gyyx.cn
 * 创建时间： 2014/4/9 
 * 版本号：v1.0

 * 功能： 异步加载左侧菜单，默认展开第一个

 * -------------------------------------------------------------------------*/

$.fn.extend({
    sortFn: function (options) { /*列表排序*/
        var settings = {
            tableobj: ".list tbody",/*table 下的tr*/
            preClass: ".pre", /*上移class*/
            nextClass: ".next",/*下移class*/
            preUrl: "/OaApplication/ForwardOrderNo/", /*向上排序请求地址*/
            nextUrl: "/OaApplication/BackwardOrderNo/", /*向下排序请求地址*/
            alertBoxId: "#widget", /*错误提示语所在的弹框的ID*/
            selfDiverror: "#warning", /*错误提示语所在的本页面中的ID*/
            errorway: true /*排序返回错误Message时，是以何种方式表现给用户(0弹框，不是0则显示在本页面)*/
        }
        $.extend(settings, options);

        /*页面一加载列表中的第一行没有向上移按钮，最后一行没有向下移按钮 start*/
        var firstTr = $(settings.tableobj + " tr").first();
        firstTr.eq(0).find("span" + settings.preClass).css("visibility", "hidden");
        var lastTr = $(settings.tableobj + " tr").last();
        lastTr.eq(0).find("span" + settings.nextClass).css("visibility", "hidden");
        var trlength = $(settings.tableobj + " tr").length;

        var canSort = true;

        if (canSort) {
            $(settings.preClass).die().live("click", function () { /*点击上移*/

                var thisindex = $(this).index("span" + settings.preClass);
                var preindex = thisindex - 1;
                var thisObj = $(this).parents("tr");
                var preObj = $(settings.tableobj + " tr").eq(preindex);
                var thishtml = thisObj.html();
                var prehtml = preObj.html();

                //alert(thisindex+",,,,,,,"+preObj+",,,,,,"+thishtml+",,,,,,,,,"+preObj.html());

                var thissn = thisObj.children("td").eq(0).html();/*序号*/
                var presn = preObj.children("td").eq(0).html();/*序号*/
                var thisid = thisObj.attr("appid");
                var preid = preObj.attr("appid");

                $.ajax({ /*点击上移请求地址，返回成功时，在页面上给用户显示调动tr*/
                    url: settings.preUrl + "?r=" + Math.random(),
                    type: "POST",
                    data: { id: parseInt(thisid), isforward: true },
                    dataType: "json",
                    success: function (data) {
                        //var data = {"Ret":-2,"Message":"出错"} /*模拟数据*/
                        if (!data) {
                            canSort = false;
                        } else {
                            canSort = true;
                        }
                        if (data.Ret == 0) { /*返回成功时替换内容*/

                            thisObj.html(prehtml);
                            preObj.html(thishtml);
                            preObj.children("td").eq(0).html(presn);/*改变序号*/
                            thisObj.children("td").eq(0).html(thissn);/*改变序号*/
                            thisObj.attr("appid", preid);
                            preObj.attr("appid", thisid);

                            preObj.addClass("backcolor").hide().fadeIn();

                            preObj.removeClass("backcolor");

                            hidHT();
                            return false;

                        } else if (data.Ret == -1) {
                            if (settings.errorway) {
                                $(settings.alertBoxId).dialog({ /*错误提示以弹框形式展现，判断用户状态（添加，添加成功）,根据状态控制弹框显示或不显示*/
                                    modal: true,
                                    width: 450
                                });
                                $(settings.alertBoxId).find("p").html("请先登录，2秒后自动跳转到登录页");
                                setTimeout(function () { location.href = "http://oa.gyyx.cn/"; }, 2000);
                            } else {
                                $(settings.selfDiverror).fadeIn().html("请先登录，2秒后自动跳转到登录页");
                                setTimeout(function () { location.href = "http://oa.gyyx.cn/"; }, 2000);
                            }

                        } else {
                            if (settings.errorway) {
                                $(settings.alertBoxId).dialog({ /*判断用户状态（添加，添加成功）,根据状态控制弹框显示或不显示*/
                                    modal: true,
                                    width: 450
                                });
                                $(settings.alertBoxId).find("p").html(data.Message);
                            } else {
                                $(settings.selfDiverror).fadeOut().fadeIn().html(data.Message);
                            }
                        }
                    }
                });
                return false;
            });
        }

        if (canSort) {
            $(settings.nextClass).die().live("click", function () { /*点击下移*/
                var thisindex = $(this).index("span" + settings.nextClass);
                var nextindex;
                if (thisindex + 1 == $(settings.tableobj + " tr").size()) {
                    nextindex = 0;
                } else {
                    nextindex = thisindex + 1;
                }
                var thisObj = $(this).parents("tr");
                var nextObj = $(settings.tableobj + " tr").eq(nextindex);

                var thishtml = thisObj.html();
                var nexthtml = nextObj.html();

                var thissn = thisObj.children("td").eq(0).html();/*序号*/
                var nextsn = nextObj.children("td").eq(0).html();/*序号*/
                var thisid = thisObj.attr("appid");
                var nextid = nextObj.attr("appid");



                $.ajax({ /*点击下移请求地址，返回成功时，在页面上给用户显示调动tr*/
                    url: settings.preUrl + "?r=" + Math.random(),
                    type: "POST",
                    data: { id: parseInt(thisid), isforward: false },
                    dataType: "json",
                    success: function (data) {
                        if (!data) {
                            canSort = false;
                        } else {
                            canSort = true;
                        }
                        if (data.Ret == 0) { /*返回成功时替换内容*/
                            thisObj.html(nexthtml);
                            nextObj.html(thishtml);
                            nextObj.children("td").eq(0).html(nextsn); /*改变序号*/
                            thisObj.children("td").eq(0).html(thissn);/*改变序号*/
                            thisObj.attr("appid", nextid);
                            nextObj.attr("appid", thisid);

                            nextObj.addClass("backcolor").hide().fadeIn();

                            nextObj.removeClass("backcolor");
                            hidHT();
                            return false;

                        } else if (data.Ret == -1) {
                            if (settings.errorway) {
                                $(settings.alertBoxId).dialog({ /*判断用户状态（添加，添加成功）,根据状态控制弹框显示或不显示*/
                                    modal: true,
                                    width: 450
                                });
                                $(settings.alertBoxId).find("p").html("请先登录，2秒后自动跳转到登录页");
                                setTimeout(function () { location.href = "http://oa.gyyx.cn/"; }, 2000);
                            } else {
                                $(settings.selfDiverror).fadeIn().html("请先登录，2秒后自动跳转到登录页");
                                setTimeout(function () { location.href = "http://oa.gyyx.cn/"; }, 2000);
                            }

                        } else {
                            if (settings.errorway) {
                                $(settings.alertBoxId).dialog({ /*判断用户状态（添加，添加成功）,根据状态控制弹框显示或不显示*/
                                    modal: true,
                                    width: 450
                                });
                                $(settings.alertBoxId).find("p").html(data.Message);
                            } else {
                                $(settings.selfDiverror).fadeOut().fadeIn().html(data.Message);
                            }
                        }
                    }
                });
                return false;
            });
        }

        function hidHT() { /*页面一加载列表中的第一行没有向上移按钮，最后一行没有向下移按钮 start*/
            $.each($(settings.preClass), function (i, item) {
                if (i == 0) {
                    $(this).css("visibility", "hidden");
                } else {
                    $(item).css("visibility", "visible");
                }
            });
            firstTr.eq(0).find("span" + settings.preClass).css("visibility", "hidden");

            trlength = $(settings.tableobj + " tr").length;

            $.each($(settings.nextClass), function (i, item) {
                if (i == trlength - 1) {
                    $(this).css("visibility", "hidden");
                } else {
                    $(item).css("visibility", "visible");
                }
            });
            lastTr.eq(0).find("span" + settings.nextClass).css("visibility", "hidden");
            return false;
        }
    },menuFn: function (options) { /*菜单异步加载,点击应用，加载模块和功能,以及当前页面的菜单当前状态cookie记录*/
        var settings = {
            ajaxUrl: "/OaApplication/GetApplication", /*点击应用，异步请求地址*/
            ulId: ".subNav" /*中间UL的ID*/
        }
        $.extend(settings, options);

        var $this = $(this);
        var appId = $this.attr("code");
        setCookie("appcode", appId);

        $(settings.ulId).html(""); /**/

        function setCookie(name, value) { /*设置cookie*/

            var str = name + '=' + value + ";path=/;" + ";domain=oa.gyyx.cn";
            document.cookie = str;
        }
        function getCookie(name) { /*获取cookie*/
            var arr = document.cookie.split('; ');
            var i = 0;
            for (i = 0; i < arr.length; i++) {
                var arr1 = arr[i].split('=');
                if (arr1[0] == name) {
                    return arr1[1];
                }
            }
            return '';
        }

        $.ajax({ /*异步请求数据*/
            url: settings.ajaxUrl,
            type: "GET",
            data: { id: appId, r: function () { return Math.random(); } },
            dataType: "json",
            success: function (data) {

                if (data && data.Ret == 0) { /*请求数据成功进行操作*/

                    if (data.Data.length > 0) {
                        $.each(data.Data, function (i, item) { /*循环模块，插入到页面中*/
                            var firstLi = "<li id='firstli" + i + "'>" +
                                          "   <a href='buttons.html'>"+
                                          "     <i class='fa fa-angle-down text'></i>"+
                                          "     <i class='fa fa-angle-up text-active'></i>"+
                                          "     <span>" + item.Name + "</span>" +
                                          "   </a>" +
                                          "   <ul class='nav bg'>" +
                                          "   </ul>" +
                                          "</li>";
                            $(settings.ulId).append(firstLi);

                            var functionsstr = "";
                            $.each(item.Functions, function (j, itemj) { /*循环功能插入到页面中*/
                                functionsstr += "<li code='" + itemj.Id + "'>"+
                                                "   <a href=" + itemj.Url + ">"+
                                                "       <i class='fa fa-angle-right'></i>"+
                                                "       <span>" + itemj.Name + "</span>"+
                                                "   </a>"+
                                                "</li>";
                            });
                            $(settings.ulId).find("li#firstli" + i).find("ul").append(functionsstr);

                        });

                        //$(settings.ulId + " li p").click(function () { /*点击菜单展开收缩*/
                        //    $(settings.ulId + " ul").hide();
                        //    $(this).parent("li").children("ul").show();
                        //});


                        $(settings.ulId + " li ul li a").click(function () { /*点击时设置cookie*/
                            var thisid = $(this).parent("li").attr("code");

                            $(settings.ulId + " li ul li").removeClass("activeli");
                            $(settings.ulId + " li ul li").parents("ul.ulfun").hide();

                            $(this).parent("li").addClass("activeli");
                            $(this).parents("ul.ulfun").show();
                            setCookie("menuid", thisid);
                        });

                        loadfinish = true;

                        if (loadfinish) {
                            var menuikcookie = getCookie("menuid");
                            var isincookie = false;
                            $.each($(".ulfun li"), function (i, item) {
                                if ($(item).attr("code") == menuikcookie) {
                                    isincookie = true;
                                    return false;
                                }
                            });

                            if (isincookie) { /*如果有cookie*/
                                $.each($(settings.ulId + " li ul li"), function (i, item) {

                                    $(item).removeClass("activeli");
                                    $(item).parents("ul.ulfun").hide();

                                    if (getCookie("menuid") == $(item).attr("code")) {
                                        $(this).addClass("activeli");
                                        $(this).parents("ul.ulfun").show();
                                        return false;

                                    }
                                });
                            } else { /*如果没有cookie时，默认第一个菜单是展开的*/
                                if ($("#hidCurrentAppCode").val() != "" && $("#hidCurrentAppCode").val() != undefined && isincookie) {
                                    $(".ulfun li[code=" + $("#hidCurrentAppCode").val() + "]").parents("ul.ulfun").show();
                                } else {
                                    $("ul.ulfun").eq(0).show();
                                }
                            }
                        }


                    } else {
                        $(settings.ulId).append("<p>无数据</p>");
                    }
                } else {
                    alert(data.Message);
                }
            }
        });
    }
});