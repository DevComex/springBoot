/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：李根
 * 联系方式：ligen@gyyx.cn
 * 创建时间： 2014/11/28 
 * 版本号：v1.0

 * 功能： 全员统计脚本

 * -------------------------------------------------------------------------*/

$(function () {
    //获取机构code
    (function($){
        $.getUrlParam = function(name)
            {
                var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r!=null) return unescape(r[2]); return null;
                }
            })(jQuery);
    var urlcode = $.getUrlParam('agencyCode');
    var mos = $.getUrlParam('mo');
    if (mos == "s") {
        var mohref = "/StaffInfo/PostStatDetails?agencyCode=" + urlcode + "&mo=" + mos;
        $(".js_ismo").attr("href", mohref)
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
        var mohref = "/StaffInfo/PostStatDetails?agencyCode=" + urlcode;
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
    //向上比较机构总人数占父机构比例
    function uppie(url,title) {
        $.ajax({
            url: url + Math.random(),
            type: "GET",
            dataType: "json",
            data: {
                code: urlcode
            },
            success: function (d) {
                var piex = {type:'pie',name:"人数"}
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
    //向上比较占总公司机构比例
    function uppieall(url,title) {
        $.ajax({
            url: url + Math.random(),
            type: "GET",
            dataType: "json",
            data: {
                code: urlcode
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
                        renderTo: 'container'
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

    uppie("/staffinfo/DownFullStatisticsSub?r=", "当前机构下各子机构人数统计")
    uppieall("/staffinfo/DownFullStatisticsPost?r=", "当前机构下各岗位人数统计")
    $(".js_down").click(function () {
        uppie("/staffinfo/DownFullStatisticsSub?r=", "当前机构下各子机构人数统计")
        uppieall("/staffinfo/DownFullStatisticsPost?r=", "当前机构下各岗位人数统计")
    });
    $(".js_up").click(function () {
        uppie("/staffinfo/UpFullStatisticsParent?r=", "当前机构在父机构人数统计")
        uppieall("/staffinfo/UpFullStatisticsCompany?r=", "当前机构在总公司人数统计")
    });

});