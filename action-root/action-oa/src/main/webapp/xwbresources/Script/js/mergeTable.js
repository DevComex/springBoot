/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：陈巧玲
 * 联系方式：chenqiaoling@gyyx.cn
 * 创建时间： 2014/4/10 
 * 版本号：v1.0
 * 代码说明：在http://oa.gyyx.cn/Scripts/files/jquery.functions.js 的 assigRolesFn 上有所更改

 * 功能： 权限管理-凭据列表-分配角色弹出层

 * -------------------------------------------------------------------------*/

$.fn.extend({
    mergeTable: function () { /*合并表格*/

        var trlength = $(".table tbody tr").length;
        var td0 = $(".table tbody tr td.oa-app");
        var obj = {}, arr = []; /* obj = [{"模块":{d:1,index:2}}]*/
        $.each(td0, function (i, ele) {
            var h = $.trim($(ele).attr("code"));
            if (obj[h]) {
                obj[h]["d"] += 1;
            } else {
                obj[h] = { d: 1, index: i };
            }
        });
        var htmlstr = "";
        var trAll = $(".table tbody tr");
        for (var i = 0; i < td0.length; i++) {
            htmlstr = $.trim(td0.eq(i).attr("code"));
            if (obj.hasOwnProperty(htmlstr)) { /*判断对象中有没有当前单元格的内容的对象*/
                trAll.eq(obj[htmlstr]["index"]).find("td:first").attr("rowspan", obj[htmlstr]["d"]); /*合并单元格*/
                trAll.slice(obj[htmlstr]["index"] + 1, obj[htmlstr]["d"] + obj[htmlstr]["index"]).find('td.oa-app').remove();
            }
        }


        var td1 = $(".table tbody tr td.oa-module");
        var obj1 = {}, arr1 = [];
        $.each(td1, function (i, ele) {
            var h = $.trim($(ele).attr("code"));
            if (obj1[h]) {
                obj1[h]["d"] += 1;
                //alert(obj[h]["d"]);
            } else {
                obj1[h] = { d: 1, index: i };
            }
        });
        var htmlstr1 = "";
        var trAll1 = $(".table tbody tr");
        for (var i = 0; i < td1.length; i++) {
            htmlstr1 = $.trim(td1.eq(i).attr("code"));
            if (obj1.hasOwnProperty(htmlstr1)) {
                trAll1.eq(obj1[htmlstr1]["index"]).find("td.oa-module").attr("rowspan", obj1[htmlstr1]["d"])
                trAll1.slice(obj1[htmlstr1]["index"] + 1, obj1[htmlstr1]["d"] + obj1[htmlstr1]["index"]).find('td.oa-module').remove();
            }
        }


        var td2 = $(".table tbody tr td.oa-func-group");

        var obj2 = {}, arr1 = [];
        $.each(td2, function (i, ele) {
            var h = $.trim($(ele).attr("code"));
            if (obj2[h]) {
                obj2[h]["d"] += 1;
            } else {
                obj2[h] = { d: 1, index: i };
            }
        });
        var htmlstr2 = "";
        var trAll2 = $(".table tbody tr");
        for (var i = 0; i < td2.length; i++) {
            htmlstr2 = $.trim(td2.eq(i).attr("code"));

            if (obj2.hasOwnProperty(htmlstr2)) {
                trAll2.eq(obj2[htmlstr2]["index"]).find("td.oa-func-group").attr("rowspan", obj2[htmlstr2]["d"])
                trAll2.slice(obj2[htmlstr2]["index"] + 1, obj2[htmlstr2]["d"] + obj2[htmlstr2]["index"]).find('td.oa-func-group').remove();
            }
        }

    }
});