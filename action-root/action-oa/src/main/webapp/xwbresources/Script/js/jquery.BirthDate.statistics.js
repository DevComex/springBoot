/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：李根
 * 联系方式：ligen@gyyx.cn
 * 创建时间： 2014/11/28 
 * 版本号：v1.0

 * 功能： 生日统计脚本

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
    var mos = $.getUrlParam('mo');
    if (mos == "s") {
        var mohref = "/StaffInfo/StaffBirthMonthStatDetails?agencyCode=" + urlcode + "&mo=" + mos;
        $(".js_ismo").attr("href", mohref);
        //菜单跳转
        $(".js_li0").click(function () {
            window.location.href = "/staffinfo/Fullstatistics?agencyCode=" + urlcode + "&mo=" + mos;
        });
        $(".js_li1").click(function () {
            window.location.href = "/staffinfo/RankStatP?agencyCode=" + urlcode + "&mo=" + mos;
        });
        $(".js_li2").click(function () {
            window.location.href = "/staffinfo/DiplomasStatP?agencyCode=" + urlcode + "&mo=" + mos;
        });
        $(".js_li3").click(function () {
            window.location.href = "/staffinfo/EmploymentStatP?agencyCode=" + urlcode + "&mo=" + mos;
        });
        $(".js_li4").click(function () {
            window.location.href = "/staffinfo/BirthDateStatP?agencyCode=" + urlcode + "&mo=" + mos;
        });
        $(".js_li5").click(function () {
            window.location.href = "/staffinfo/GenderStatP?agencyCode=" + urlcode + "&mo=" + mos;
        });
        $(".js_li6").click(function () {
            window.location.href = "/staffinfo/MaritalStatusStatP?agencyCode=" + urlcode + "&mo=" + mos;
        });
        $(".js_li7").click(function () {
            window.location.href = "/staffinfo/CompanyStatP?agencyCode=" + urlcode + "&mo=" + mos;
        });
    } else {
        var mohref = "/StaffInfo/StaffBirthMonthStatDetails?agencyCode=" + urlcode;
        $(".js_ismo").attr("href", mohref);
        //菜单跳转
        $(".js_li0").click(function () {
            window.location.href = "/staffinfo/Fullstatistics?agencyCode=" + urlcode;
        });
        $(".js_li1").click(function () {
            window.location.href = "/staffinfo/RankStatP?agencyCode=" + urlcode;
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
        $(".js_li7").click(function () {
            window.location.href = "/staffinfo/CompanyStatP?agencyCode=" + urlcode;
        });
    }
    //机构总人数
    function getallpeople(code) {
        $.ajax({
            url: "/staffinfo/GetCurrentAgencyStaffCount?r=" + Math.random(),
            type: "GET",
            dataType: "json",
            data: {
                code: code
            },
            success: function (d) {
                $(".js_cutall").html(d.agencyStaffCount);
                $(".js_agyname").html(d.agencyName);
            }
        });
    };
    getallpeople(urlcode);
    //生日统计图
    function rankcums(url, title) {
        $.ajax({
            url: url + Math.random(),
            type: "GET",
            dataType: "json",
            data: {
                agencyCode: urlcode
            },
            success: function (d) {
                //柱状图表数据
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
                        plotLines: [{ value: 0, width: 1, color: '#808080' }],
                        allowDecimals: false
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
                d.series[0].name = "人数";
                options.xAxis.categories = d.xAxis;
                options.series = d.series;
                chart = new Highcharts.Chart(options);//创建图表
            }
        });
    };
    //年龄统计图
    function rankcum(url, title, startAge, endAge) {
        $.ajax({
            url: url + Math.random(),
            type: "GET",
            dataType: "json",
            data: {
                agencyCode: urlcode,
                startAge: startAge,
                endAge: endAge
            },
            success: function (d) {
                //柱状图表数据
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
                        plotLines: [{ value: 0, width: 1, color: '#808080' }],
                        allowDecimals:false
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
                d.series[0].name = "人数";
                options.xAxis.categories = d.xAxis;
                options.series = d.series;
                chart = new Highcharts.Chart(options);//创建图表
            }
        });
    };
    //初始化年龄
    var startAge = $(".js_startAge").val();
    var endAge = $(".js_endAge").val();
    rankcum("/staffinfo/StaffAgeStat?r=", "生日统计图", startAge, endAge);
    
    //统计年龄
    $(".js_getage").click(function () {
        //初始化日期
        var startAge = $(".js_startAge").val();
        var endAge = $(".js_endAge").val();
        var searchcount = startAge;
        var searchcounts = endAge;
        var reg = /^[0-9]\d*$/;
        var testinput = reg.test(searchcount);
        var testinputs = reg.test(searchcounts);
        if (testinput && testinputs && startAge >= 18 && endAge <= 75) {
            rankcum("/staffinfo/StaffAgeStat?r=", "生日统计图", startAge, endAge);
        } else {
            alert("年龄在18-75周岁之间")
        }
    });
    $(".js_pielab").click(function () {
        $(".js_startAge").hide();
        $(".js_endAge").hide();
        $(".js_getage").hide();
        rankcums("/staffinfo/StaffBirthMonthStat?r=", "生日统计图");
    });
    $(".js_zhelab").click(function () {
        $(".js_startAge").val("18");
        $(".js_endAge").val("75");
        rankcum("/staffinfo/StaffAgeStat?r=", "生日统计图", startAge, endAge);
        $(".js_startAge").show();
        $(".js_endAge").show();
        $(".js_getage").show();
    });
});