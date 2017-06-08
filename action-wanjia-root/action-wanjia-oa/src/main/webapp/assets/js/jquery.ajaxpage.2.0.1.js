/*-------------------------------------------------------------------------
* 作者：骆崇飞
* 邮箱：luochongfei@gyyx.cn
* 创建时间： 2014/09
* 版本号：v2.0
* 主要用途描述：ajax分页
-------------------------------------------------------------------------*/
$.fn.extend({
    //ajax分页初始化方法
    ajaxPageS: function (options) {
        //默认配置参数【GET，JSON，#js_pagecont，第1页为起始页，每页10条】
        var
            setting = {
                //ajax请求地址
                url: "http://www.gyyx.cn/",
                //ajax请求方式
                type: "GET",
                //ajax请求数据类型
                dataType: "JSON",
                //分页容器对象
                pageObj: $("#js_pagecont"),
                //第1页为起始页
                pageIndex: 1,
                //每页10条
                pageSize: 10,
                //当前页样式名
                curPageCls:"curpage",
                //定义拿总数对象位置
                pageData:null,
                //分页信息显示(传入容器对象与内容{allCount}, {pageSize}, {pageIndex})
                pageInfo: {
                    obj: "",
                    content: ""
                },
                //定义按钮文字
                pageBtn: {
                    first: "首页",
                    last: "末页",
                    prev: "上页",
                    next: "下页"
                },
                //参数配置对象
                paramObj: {
                },
                //数据组合函数(最后返回一个字符串变量)
                dataFn: function (data, $this, setting) {
                    return false;
                },
                //当数据遍历好并分页完成后执行函数
                successFn: function (allCount, pageSize, pageIndex) {
                },
                //点击分页按钮时处理函数
                clickFn: function (setting, cur) {
                },
                //ajax请求出错时执行函数
                errorFn: function (e) {
                },
                debug:true
            };

        //整合传入参数对象
        $.extend(setting, options);

        var
            //分页内容容器对象
            $this = $(this),
            //静态参数对象
            setObj = setting.paramObj,
            //传给ajax参数对象
            sendData = { "t": new Date().getTime().toString(36) },
            //当前页
            pageIndex = setting.pageIndex,
            //每页数量
            pageSize = setting.pageSize,
            //每页信息
            pageInfo = setting.pageInfo,
            //错误输出函数
            clog = function (str) {
                if (window.console) {
                    console.log(str);
                }
            },
            debug = setting.debug;

        //如果存在当前页与每页参数
        if (pageIndex && pageSize) {
            //如果当前页参数为对象
            if (typeof pageIndex == "object") {
                var pageIndexParam = pageIndex.param;
                pageIndex = parseInt(pageIndex.count);
                //如果当前页参数与值都存在
                if (pageIndex && pageIndexParam) {
                    setObj[pageIndexParam] = pageIndex;
                } else {
                    debug&&clog("当前页数参数不正确！");
                    return false;
                }
            } else if (typeof parseInt(pageIndex) == "number") {//如果当前页参数为准数字
                setObj.pageIndex = parseInt(pageIndex);
            } else {
                debug && clog("当前页数参数不正确！");
                return false;
            }
            //如果每页数参数为对象
            if (typeof pageSize == "object") {
                var pageSizeParam = pageSize.param;
                pageSize = parseInt(pageSize.count);
                if (pageSizeParam && pageSize) {
                    setObj[pageSizeParam] = pageSize;
                } else {
                    debug && clog("每页数参数不正确！");
                    return false;
                }
            } else if (typeof parseInt(pageSize) == "number") {//如果每页数参数为数字
                setObj.pageSize = parseInt(pageSize);
            } else {
                debug && clog("当前页数参数不正确！");
                return false;
            }
        } else {//否则不存在当前页或每页参数
            debug && clog("分页参数不正确！");
            return false;
        }


        //整合要传给Ajax的参数对象
        $.extend(sendData, setObj);

        var ajaxSuc = function (data) {
            if (data) {
                if (setting.dataFn) {
                    var dataHtml = setting.dataFn(data, $this);
                }
                if (!dataHtml) {
                    pageInfo.obj && pageInfo.obj.empty();
                    setting.pageObj.empty();
                    debug && clog("返回数据或组合数据出错，请检查dataFn里操作！\n" +
                    "1.可能是起始页超过数据最大分页，检查pageIndex\n" +
                    "2.可能是未传入必须参数，检查paramObj");
                    return false;
                }
                var
                    //总数量
                    allCount = setting.pageData ? eval(setting.pageData) : (data.data.list && data.data.Count),
                    //总页数
                    allPage = 0;

                //如果能取到总数量并且是大于0整数
                if (allCount && parseInt(allCount) > 0) {
                    allPage = Math.ceil(allCount / pageSize);
                } else {
                    debug && clog("总数量配置出错，请检查参数pageData和返回JSON格式！");
                    return false;
                }

                //装载列表数据
                $this.empty().html(dataHtml);

                //分页数据传递
                setting.allCount = allCount;
                setting.allPage = allPage;
                setting.pSize = pageSize;
                setting.pIndex = pageIndex;
                //分页
                $this.pagesFn(setting);

                if (pageInfo && pageInfo.obj && pageInfo.content) {
                    var pgInfo = pageInfo.content.
                        replace("{allCount}", allCount).
                        replace("{pageIndex}", pageIndex).
                        replace("{allPage}", allPage);
                    pageInfo.obj.empty().html(pgInfo);
                }

                //一切就绪后执行事件
                if (setting.successFn) {
                    setting.successFn(allCount, pageSize, pageIndex);
                }
            }
        };
        var ajaxErr = function (e) {
            if (setting.errorFn) {
                setting.errorFn(e);
            }
        };
        //ajax请求传入对象
        var ajaxObj = {
            url: setting.url,
            type: setting.type,
            dataType: setting.dataType,
            data: sendData,
            success: function (data) {
                ajaxSuc(data);
                $(".waitPlease").remove();
            },
            beforeSend:function(d){
            	var html ='<div class="waitPlease" style="position: fixed;width: 100%;height: 100%;background-color: black;top: 0;left: 0;z-index: 1000;opacity: 0.4;"><div style="margin:30% auto"><p style="font-size:25px; color:#fff; text-align:center;">正在查询，请稍等···</p></div></div>';
            	$("body").append(html);
            },
            error: function (e) {
                setting.pageObj.empty();
                pageInfo.obj && pageInfo.obj.empty();
                debug && clog("请检查参数配置\n1.ajax请求方式是否正确\n2.是否跨域配置\n3.hosts指向下是否有数据\n4.返回JSON数据格式是否正确");
                ajaxErr(e);
                return false;
            }
        }
        //如果是jsonp跨域请求
        if (/^jsonp$/ig.test(ajaxObj.dataType)) {
            ajaxObj[ajaxObj.dataType] = setting.ajaxCallBackStr ? setting.ajaxCallBackStr : "jsoncallback";
        }
        //发送ajax请求
        $.ajax(ajaxObj);
    },

    //ajax分页核心方法
    pagesFn: function (setting) {
        var
            //当前调用容器对象
            $this = $(this),
            //当前页
            pageIndex = parseInt(setting.pIndex),
            //总页数
            allPage = setting.allPage,
            //分页容器对象
            pageObj = setting.pageObj,
            //当前对象序列
            thisidx = $this.index(),
            //按钮文字
            btnTxt = setting.pageBtn,
            //构造一个存储当前页码的hidden id
            hidId = "hd_curpage_" + thisidx,
            //是否第一页
            isfirstpg = pageIndex == 1,
            //当前页样式名
            curPageCls=setting.curPageCls,
            //是否最后一页
            lastpg = pageIndex == allPage,
            //分页按钮html容器
            pagehtml = '<a ' + (isfirstpg ? 'class="pg_disabled"' : '') + ' href="javascript:;"  pgid="-4">' + btnTxt.prev + '</a>' +
                                  '<a ' + (isfirstpg ? 'class="pg_disabled"' : '') + ' href="javascript:;" pgid="-3">' + btnTxt.first + '</a>';

        //如果页面不存在存储当前页码的hidden则创建一个hidden
        if (!$("#" + hidId).length) {
            $("body").append("<input type='hidden' id='" + hidId + "' />");
        }

        //将当前页及末页存入hidden
        $("#" + hidId).val(pageIndex + "|" + allPage);
        //页面公用函数提取
        function pagenum(i) {
            var iscurpg = (i + 1) == pageIndex;
            pagehtml += '<a ' + (iscurpg ? 'class="' + curPageCls + '"' : '') + ' href="javascript:;" pgid="' + (i + 1) + '">' + (i + 1) + '</a>';
        }
        //console.log("pageIndex："+pageIndex+"\n allPage："+allPage);
        if (allPage < 6) {//如果页面小于6页时，显示全部页码
            for (var i = 0; i < allPage; i++) {
                pagenum(i);
            }
        } else {//如果页面大于6页时：
            var isLast = (pageIndex + 2) < allPage,
                aDot = '<a class="nobg" href="javascript:;">...</a>';

            if (pageIndex < 4) {//如果当前页小于4时，可见数量最大为5然后加...
                for (var i = 0; i < 5; i++) {
                    pagenum(i);
                }
                pagehtml += aDot;
            } else if (pageIndex < 6) {//如果当前页大于4小于6时，显示0--总页数或当前页+2
                for (var i = 0; i < (isLast ? (pageIndex + 2) : allPage) ; i++) {
                    pagenum(i);
                }
                isLast ? pagehtml += aDot : '';
            } else {//如果当前页大于6时，显示1，2，...,当前页-3,--总页数或当前页加2
                for (var i = 0; i < 2 ; i++) {
                    pagehtml += '<a href="javascript:;" pgid="' + (i + 1) + '">' + (i + 1) + '</a>';
                }
                pagehtml += aDot;
                for (var i = (pageIndex - 3) ; i < (isLast ? (pageIndex + 2) : allPage) ; i++) {
                    pagenum(i);
                }
                isLast ? pagehtml += aDot : '';
            }
        }

        pagehtml += '<a ' + (lastpg ? 'class="pg_disabled"' : '') + ' href="javascript:;" pgid="-2">' + btnTxt.last + '</a>' +
                                 '<a ' + (lastpg ? 'class="pg_disabled"' : '') + ' href="javascript:;" pgid="-1">' + btnTxt.next + '</a>';

        //装载分页按钮
        pageObj.show().empty().html(pagehtml);

        setting.hidcur = hidId;
        //为分页按钮绑定事件
        pageObj.find("a").pageBtnFn($this, setting);
    },

    //ajax分页按钮点击处理方法
    pageBtnFn: function (that, setting) {
        $(this).unbind("click").bind("click", function () {
            var pgid = $(this).attr("pgid"),
                curHidVal = $("#" + setting.hidcur)[0].value.split("|"),
                isfirst = curHidVal[0] == 1,
                islast = curHidVal[0] == curHidVal[1],
                cur = null;

            if (parseInt(pgid) > 0) {//如果是数字按钮
                if (curHidVal[0] == pgid) {
                    return false;
                } else {
                    cur = pgid;
                }
            } else {
                switch (pgid) {
                    case "-4"://上一页
                        if (isfirst) {
                            return false;
                        } else {
                            cur = parseInt(curHidVal[0]) - 1;
                        }
                        break;
                    case "-3"://第一页
                        if (isfirst) {
                            return false;
                        } else {
                            cur = 1;
                        }
                        break;
                    case "-2"://最后一页
                        if (islast) {
                            return false;
                        } else {
                            cur = parseInt(curHidVal[1]);
                        }
                        break;
                    case "-1"://下一页
                        if (islast) {
                            return false;
                        } else {
                            cur = parseInt(curHidVal[0]) + 1;
                        }
                        break;
                    default:
                        return false;
                        break;
                }
            }

            if (setting.clickFn) {
                setting.clickFn(setting, cur);
            }
            //如果是对象型的当前页
            if (setting.pageIndex.count) {
                setting.pageIndex.count = cur;
            } else {
                setting.pageIndex = cur;
            }
            that.ajaxPageS(setting);
            //阻止a链接默认跳转事件
            return false;
        });
    }
});
