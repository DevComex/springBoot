/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：李根
 * 联系方式：ligen@gyyx.cn
 * 创建时间： 2014/11/28 
 * 版本号：v1.0

 * 功能： 岗位分类统计脚本

 * -------------------------------------------------------------------------*/

$(function () {
    //获取机构code
    (function ($) {
        $.getUrlParam = function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
        }
    })(jQuery);
    var urlcode = $.getUrlParam('agencyCode');
    //机构总人数
    function getallpeople(code) {
        $.ajax({
            url: "/staffinfo/GetCurrentAgencyStaffCount?r=" + Math.random(),
            type: "GET",
            dataType: "json",
            data: {
                code: 1
            },
            success: function (d) {
                $(".js_cutall").html(d.agencyStaffCount);
                $(".js_agyname").html(d.agencyName);
            }
        });
    };
    getallpeople(urlcode);

    //岗位分类统计饼图
    function rankpie(url, title, divlayer) {
        $.ajax({
            url: url + Math.random(),
            type: "GET",
            dataType: "json",
            data: {
                agencyCode: urlcode
            },
            success: function (d) {
                var piex = { name: "人数" }
                piex.data = [];
                for (var i = 0; i < d.series[0].data.length; i++) {
                    var temp = { name: d.xAxis[i], y: d.series[0].data[i].y, extra: d.series[0].data[i].extra };
                    piex.data.push(temp);
                }
                var piedata = piex;
                //alert(piex.toSource());
                //饼图标数据
                var options = {
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false,
                        renderTo: divlayer,
                        type: 'pie'
                    },
                    title: {
                        text: title
                    },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.y} 人</b><br>占比：<b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: false
                            },
                            showInLegend: true,
                            events: {
                                click: function (event) {
                                    var names = event.point.name + "岗位分类统计员工的分布信息";
                                    rankpiesub("/staffinfo/SpePostSortStat?r=", names, "container", event.point.extra);
                                    //alert(event.point.name)
                                }
                            }
                        }
                    },
                    labels: {
                        items: [{
                            html: '',
                            style: {
                                left: '532px',
                                top: '160px',
                            }
                        }],
                        style: {
                            color: 'red',
                            fontSize: 45,
                            fontWeight: 'bold',
                            zIndex: 1000
                        }
                    },
                    series: []
                };
                //alert(piedata.toSource());
                //options.xAxis.categories = d.xAxis;
                options.series.push(piedata)
                chart = new Highcharts.Chart(options);//创建图表
            }
        });
    };
    //指定岗位分类统计员工的分布信息
    function rankpiesub(url, title, divlayer, sortCode) {
        $.ajax({
            url: url + Math.random(),
            type: "GET",
            dataType: "json",
            data: {
                sortCode: sortCode
            },
            success: function (d) {
                var piex = { type: 'pie', name: "人数" }
                piex.data = [];
                for (var i = 0; i < d.series[0].data.length; i++) {
                    var temp = [d.xAxis[i], d.series[0].data[i]]
                    piex.data.push(temp);
                }
                var piedata = piex;
                //饼图标数据
                var options = {
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false,
                        renderTo: divlayer,
                        type: 'pie'
                    },
                    title: {
                        text: title
                    },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.y} 人</b><br>占比：<b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: false
                            },
                            showInLegend: true
                        }
                    },
                    labels: {
                        items: [{
                            html: '',
                            style: {
                                left: '532px',
                                top: '160px',
                            }
                        }],
                        style: {
                            color: 'red',
                            fontSize: 45,
                            fontWeight: 'bold',
                            zIndex: 1000
                        }
                    },
                    series: []
                };
                options.series.push(piedata)
                chart = new Highcharts.Chart(options);//创建图表
            }
        });
    };
    //岗位分类统计柱状图
    function rankcum(url, title, divlayer) {
        $.ajax({
            url: url + Math.random(),
            type: "GET",
            dataType: "json",
            data: {
                agencyCode: urlcode
            },
            success: function (d) {
                var piex = { name: "人数" }
                piex.data = [];
                for (var i = 0; i < d.series[0].data.length; i++) {
                    var temp = { name: d.xAxis[i], y: d.series[0].data[i].y, extra: d.series[0].data[i].extra };
                    piex.data.push(temp);
                }
                var piedata = piex;
                //柱状图标数据
                d.series[0].name = "人数";
                var options = {
                    chart: {
                        renderTo: divlayer,
                        type: 'column'
                    },
                    title: {
                        text: title, x: -20
                    },
                    subtitle: {
                        text: '',
                        x: -20
                    },
                    xAxis: {
                        categories: []
                    },
                    yAxis: {
                        title: { text: '' },
                        plotLines: [{ value: 0, width: 1, color: '#808080' }]
                    },
                    tooltip: {
                        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                            '<td style="padding:0"><b>{point.y:.1f} 人</b></td></tr>',
                        footerFormat: '</table>',
                        shared: true,
                        useHTML: true
                    },
                    plotOptions: {
                        column: {
                            pointPadding: 0.2,
                            borderWidth: 0,
                            events: {
                                click: function (event) {
                                    var names = event.point.name + "岗位分类统计员工的分布信息";
                                    rankcumsub("/staffinfo/SpePostSortStat?r=", names, "container", event.point.extra);
                                   // alert(event.point.name)
                                }
                            }
                        }
                    },
                    series: []
                };
                //获取x轴坐标数据
                //alert(piedata.toSource());
                options.series.push(piedata)
                chart = new Highcharts.Chart(options);//创建图表
            }
        });
    };
    //该岗位分类下统计柱状图
    function rankcumsub(url, title, divlayer, sortCode) {
        $.ajax({
            url: url + Math.random(),
            type: "GET",
            dataType: "json",
            data: {
                sortCode: sortCode
            },
            success: function (d) {
                //柱状图标数据
                d.series[0].name = "人数";
                var options = {
                    chart: {
                        renderTo: divlayer,
                        type: 'column'
                    },
                    title: {
                        text: title, x: -20
                    },
                    subtitle: {
                        text: '',
                        x: -20
                    },
                    xAxis: {
                        categories: []
                    },
                    yAxis: {
                        title: { text: '' },
                        plotLines: [{ value: 0, width: 1, color: '#808080' }]
                    },
                    tooltip: {
                        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                            '<td style="padding:0"><b>{point.y:.1f} 人</b></td></tr>',
                        footerFormat: '</table>',
                        shared: true,
                        useHTML: true
                    },
                    plotOptions: {
                        column: {
                            pointPadding: 0.2,
                            borderWidth: 0
                        }
                    },
                    series: []
                };
                //获取x轴坐标数据
                options.xAxis.categories = d.xAxis;
                options.series = d.series;
                chart = new Highcharts.Chart(options);//创建图表
            }
        });
    };
    //初始化
    rankpie("/staffinfo/PostSortStat?r=", "岗位分类统计饼图", "containers");
    //图标切换
    $(".js_pielab").click(function () {
        $("#container").html("");
        rankpie("/staffinfo/PostSortStat?r=", "岗位分类统计饼图", "containers");
    });
    $(".js_zhulab").click(function () {
        $("#container").html("");
        rankcum("/staffinfo/PostSortStat?r=", "岗位分类统计柱状图", "containers");
    });
});