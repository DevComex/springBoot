/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：李根
 * 联系方式：ligen@gyyx.cn
 * 创建时间： 2014/11/28 
 * 版本号：v1.0

 * 功能： 雇佣状态统计脚本

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
        var mohref = "/StaffInfo/StaffInductionStatDetail?agencyCode=" + urlcode + "&mo=" + mos;
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
        var mohref = "/StaffInfo/StaffInductionStatDetail?agencyCode=" + urlcode;
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
    //月入职统计图
    function rankcum(url, title, starttime, endtime) {
        $.ajax({
            url: url + Math.random(),
            type: "GET",
            dataType: "json",
            data: {
                agencyCode: urlcode,
                startTime: starttime,
                endTime: endtime
            },
            success: function (d) {
                //折线图表数据
                var options = {
                    chart: {
                        renderTo: 'containers',
                        type: 'line'
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
    //员工状态统计图
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
    //初始化日期
    timeselect(0, ".js_Endtime");
    timeselect(370, ".js_Starttime");
    var startTime = $(".js_Starttime").val();
    var endTime = $(".js_Endtime").val();
    rankcum("/staffinfo/StaffRZLStat?r=", "月入职统计图", startTime, endTime);
    //统计月入职
    $(".js_getstatistcs").click(function () {
        //初始化日期
        var startTime = $(".js_Starttime").val();
        var endTime = $(".js_Endtime").val();
        rankcum("/staffinfo/StaffRZLStat?r=", "月入职统计图", startTime, endTime);
    });
    $(".js_pielab").click(function () {
        rankpie("/staffinfo/StaffStatusStat?r=", "员工状态统计图");
        $(".js_Starttime").hide();
        $(".js_Endtime").hide();
        $(".js_getstatistcs").hide();
        if (mos == "s") {
            $(".js_ismo").attr("href", "/StaffInfo/StaffStatusStatDetails?agencyCode=" + urlcode + "&mo=" + mos);
        } else {
            $(".js_ismo").attr("href", "/StaffInfo/StaffStatusStatDetails?agencyCode=" + urlcode);
        }
        
    });
    $(".js_zhelab").click(function () {
        //初始化日期
        var startTime = $(".js_Starttime").val();
        var endTime = $(".js_Endtime").val();
        rankcum("/staffinfo/StaffRZLStat?r=", "月入职统计图", startTime, endTime);
        $(".js_Starttime").show();
        $(".js_Endtime").show();
        $(".js_getstatistcs").show();
        if (mos == "s") {
            $(".js_ismo").attr("href", "/StaffInfo/StaffInductionStatDetail?agencyCode=" + urlcode + "&mo=" + mos);
        } else {
            $(".js_ismo").attr("href", "/StaffInfo/StaffInductionStatDetail?agencyCode=" + urlcode);
        }
        
    });
    //时间控件
    $(".js_Starttime").datepicker({
        format: 'yyyy-mm',
        startView: 3,   //默认打开是月试图
        autoclose: true,  //选择后自动关闭
        todayBtn: true,  //选择今天的按钮
        minView: 3   //只能选到月试图  （0 代表能选到分钟、1 代表能选到小时、2 代表能选到日 ）
    }).on('changeDate', function (ev) {
        var AddEndTime = $(this).val();
        var today = new Date();
        var yy = today.getFullYear();
        var MM = today.getMonth() + 1;
        if (MM < 10) {
            MM = "0" + MM;
        }
        $(".js_Starttime").datepicker('setEndDate', yy + "-" + MM);
        $(".js_Endtime").datepicker('setStartDate', AddEndTime);
        $(".js_Endtime").datepicker('setEndDate', yy + "-" + MM);
        $(".js_Starttime").datepicker('hide');    //隐藏开始时间面板
        $(this).parent().parent().find(".js_Endtime").datepicker('show')   //显示结束时间面板
    });
    $(".js_Endtime").datepicker({
        format: 'yyyy-mm',
        startView: 3,   //默认打开是年试图
        autoclose: true,  //选择后自动关闭
        todayBtn: true,  //选择今天的按钮
        minView: 3   //只能选到年试图  （0 代表能选到分钟、1 代表能选到小时、2 代表能选到日 ）
    }).on('changeDate', function (ev) {
        var today = new Date();
        var yy = today.getFullYear();
        var MM = today.getMonth() + 1;
        if (MM < 10) {
            MM = "0" + MM;
        }
        $(".js_Starttime").datepicker('setEndDate', $(this).val());
        $(".js_Endtime").datepicker('setEndDate', yy+"-"+MM);
    });

    //系统日期
    function timeselect(day, timeday) {
        var dateselectTime = new Date();
        dateselectTime.setDate(dateselectTime.getDate() - day);
        var yyy = dateselectTime.getFullYear();
        var MMM = dateselectTime.getMonth() + 1;  //因为1月这个方法返回为0，所以加1
        if (MMM < 10) {
            MMM = "0" + MMM
        }
        $(timeday).val(yyy + "-" + MMM);
    };
});