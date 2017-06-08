/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：李根
 * 联系方式：ligen@gyyx.cn
 * 创建时间： 2014/11/28 
 * 版本号：v1.0

 * 功能： 行政公司统计脚本

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
    //行政公司统计饼图
    function rankpie(url, title) {
        $.ajax({
            url: url + Math.random(),
            type: "GET",
            dataType: "json",
            data: {
                agencyCode: urlcode
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
                        renderTo: 'containers'
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
    //职级统计柱状图
    function rankcum(url, title) {
        $.ajax({
            url: url + Math.random(),
            type: "GET",
            dataType: "json",
            data: {
                agencyCode: urlcode
            },
            success: function (d) {
                //获取数据
                var piex = { type: 'column', name: "人数" }
                piex.data = [];
                var xaxis = [];
                for (var i = 0; i < d.series[0].data.length; i++) {
                    var temp = [d.series[0].data[i]]
                    piex.data.push(temp);
                }
                var piedata = piex;
                //柱状图标数据
                var options = {
                    chart: {
                        renderTo: 'containers',
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
                for (var i = 0; i < d.series[0].data.length; i++) {
                    options.xAxis.categories.push(d.xAxis[i])
                }
                options.series.push(piedata)
                chart = new Highcharts.Chart(options);//创建图表
            }
        });
    };

    rankpie("/staffinfo/CompanyStat?r=", "行政公司统计饼图");
    //图标切换
    $(".js_pielab").click(function () {
        rankpie("/staffinfo/CompanyStat?r=", "行政公司统计饼图");
    });
    $(".js_zhulab").click(function () {
        rankcum("/staffinfo/CompanyStat?r=", "行政公司统计柱状图");
    });
    //菜单跳转
    $(".js_li0").click(function () {
        window.location.href = "/staffinfo/Fullstatistics?agencyCode=" + urlcode;
    });
    $(".js_li2").click(function () {
        window.location.href = "/staffinfo/DiplomasStatP?agencyCode=" + urlcode;
    });
    $(".js_li3").click(function () {
        window.location.href = "/staffinfo/EmploymentStatP?agencyCode=" + urlcode;
    });
    $(".js_li4").click(function () {
        window.location.href = "/staffinfo/BirthDateStatP?agencyCode=" + urlcode;
    });
    $(".js_li5").click(function () {
        window.location.href = "/staffinfo/GenderStatP?agencyCode=" + urlcode;
    });
    $(".js_li6").click(function () {
        window.location.href = "/staffinfo/MaritalStatusStatP?agencyCode=" + urlcode;
    });
});