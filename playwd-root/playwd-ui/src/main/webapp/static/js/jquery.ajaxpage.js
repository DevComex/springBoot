/*-------------------------------------------------------------------------
* 作者：骆崇飞
* 邮箱：luochongfei@gyyx.cn
* 创建时间： 2014/08
* 版本号：v1.0
* 主要用途描述：ajax分页
-------------------------------------------------------------------------*/
$.fn.extend({
    ajaxPage: function (options) {
        var setting = {
            ajaxtype: "GET",
            ajaxdatatype: "JSON",
            curpage: 1,
            eachcount: 10,
            id: 3812,
            city:"",
            keyword:"",
            hd_curpage: "图片列表",
            pageobj: $("#js_pagecont"),
            paramobj: {
               // hdName:"rr"
                },
            dataFn: function (d,lihtml) {
                for (var i = 0; i < d.List.length; i++) {
                    lihtml += '' +
                        '<li>' +
                        '    <img src="' + d.List[i].VideoAddress + '" />' +
                        '    <h2>' + d.List[i].VideoTitle + '</h2>' +
                        '</li>';
                }
                return lihtml;
            },
            successFn: function (d) {
                //alert(d.toSource())
            }
        };
        $.extend(setting, options);
        var $this = $(this),
        sdata = {
            t: new Date().getTime(),
            PageIndex: setting.curpage,
            PageSize: setting.eachcount,
            PrimaryCategoryId: setting.PrimaryCategoryId,
            CategoryId: setting.CategoryId,
            Orderby: setting.Orderby
        };
        $.extend(sdata,setting.paramobj);
        $.ajax({
            url: setting.geturl,
            type: setting.ajaxtype,
            dataType: setting.ajaxdatatype,
            data: sdata,
            success: function (data) {
                if (data) {
                    if (setting.dataFn) {
                        lihtml = setting.dataFn(data);
                    }
                    if (!lihtml) {
                        return false;
                    }
                    var 
                        listlen = data.Data.Count,    //总数量
                        ecount = setting.eachcount,      //每页数量
                        p_allpage = Math.ceil(listlen / ecount),   //总页数
                        paraarr = setting.paraarr,
                        options = {
                            ecount: ecount,
                            p_allpage: p_allpage,
                            p_curpage: setting.curpage,
                            hd_curpage: setting.hd_curpage,
                            pageobj: setting.pageobj
                        }
                    // alert(listlen);
                    
                    //装载列表数据
                    $this.empty().html(lihtml);
                    //分页
                    $this.pagesFn(options, setting);

                    if (setting.successFn) {
                        setting.successFn(listlen, ecount, setting.curpage);
                    }
                }
            }
        });
    },
    pagesFn: function (options, setting) {
        var $this = $(this),
            hd_curpage = options.hd_curpage,
            p_curpage = parseInt(options.p_curpage),
            p_allpage = parseInt(options.p_allpage),
            pageobj = options.pageobj;

        //如果页面不存在存储当前页码的hidden则创建一个hidden
        if (!$("#" + hd_curpage).length) {
            $("body").append("<input type='hidden' id='" + hd_curpage + "' />");
        }

        //将当前页及末页存入hidden
        $("#" + hd_curpage).val(p_curpage + "|" + p_allpage);

        var pagehtml = '<a href="javascript:;" pgid="-4">上一页</a><a href="javascript:;" pgid="-3">首页</a>';
        
        //页面公用函数提取
        function pagenum(i) {
            if ((i + 1) == p_curpage) {
                pagehtml += '<a class="gy_pageon" href="javascript:;" pgid="' + (i + 1) + '">' + (i + 1) + '</a>';
            } else {
                pagehtml += '<a href="javascript:;" pgid="' + (i + 1) + '">' + (i + 1) + '</a>';
            }
        }

        if (p_allpage < 6) {//如果页面小于6页时，显示全部页码
            for (var i = 0; i < p_allpage; i++) {
                pagenum(i);
            }
        } else {//如果页面大于6页时：
            var isLast = (p_curpage + 2) < p_allpage,
                aDot = '<a class="nobg" href="javascript:;">...</a>';
            if (p_curpage < 4) {//如果当前页小于4时，可见数量最大为5然后加...
                for (var i = 0; i < 5; i++) {
                    pagenum(i);
                }
                pagehtml += aDot;
            } else if (p_curpage < 6) {//如果当前页大于4小于6时，显示0--总页数或当前页+2
                for (var i = 0; i < (isLast ? (p_curpage + 2) : p_allpage) ; i++) {
                    pagenum(i);
                }
                isLast ? pagehtml += aDot : '';
            } else {//如果当前页大于6时，显示1，2，...,当前页-3,--总页数或当前页加2
                for (var i = 0; i < 2 ; i++) {
                    pagehtml += '<a href="javascript:;" pgid="' + (i + 1) + '">' + (i + 1) + '</a>';
                }
                pagehtml += aDot;
                for (var i = (p_curpage - 3) ; i < (isLast ? (p_curpage + 2) : p_allpage) ; i++) {
                    pagenum(i);
                }
                isLast ? pagehtml += aDot : '';
            }
        }
        pagehtml += '<a href="javascript:;" pgid="-2">末页</a><a href="javascript:;" pgid="-1">下一页</a>';
        pageobj.show().empty().html(pagehtml);

        $this.hidcur = hd_curpage;
        $this.pagetype = options.pagetype;
        pageobj.find("a").pageBtnFn($this, setting);
    },
    pageBtnFn: function (that, setting) {
        $(this).click(function () {
            var pgid = $(this).attr("pgid"),
                curHidVal = $("#"+that.hidcur)[0].value.split("|"),
                isfirst = curHidVal[0] == 1,
                islast = curHidVal[0] == curHidVal[1],
                cur = null;

            if (parseInt(pgid)>0) {//如果是数字按钮
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
            var curobj = {
                curpage: cur
            }
            $.extend(setting, curobj)
            that.ajaxPage(setting);
            return false;
        });
    }
});
