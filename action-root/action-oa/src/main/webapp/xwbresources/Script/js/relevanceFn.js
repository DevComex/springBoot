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
    relevanceFn: function (options) { /*级联下拉异步*/
        var settings = {
            url: "/OaFunctionGroup/Index/", /*异步请求url*/
            selectedValue: "", /*有权限时，前端加载下拉值并设置当前选中值*/
            //firstSel: "#ApplicationCode", /*第一个下拉菜单*/
            secondSel: "#uniform-ModuleCode",/*第二个下拉菜单*/
            txt: "请选择所属模块" /*第二个下拉清空时默认文字*/

        };
        $.extend(settings, options);
        var $this = $(this);

        var thisval = $this.val();
        $(settings.secondSel).parent("div").find("span").eq(0).html(settings.txt); /*清空上次选择的值*/

        $.ajax({ //select下拉change时异步请求其下的选项，并插入到页面中
            url: settings.url + "?r=" + Math.random(),
            type: "GET",
            data: { id: thisval },
            dataType: "json",
            success: function (data) {
                //var data = { "Ret": 0, "Message": "成功", "Data": [{ "val": 0, "txt": "请选择" }, { "val": 1, "txt": "werwer" }, { "val": 2, "txt": "moudule2" }] }; //模拟数据
                if (data) {

                    if (data.Ret == 0) {
                        var str = "";
                        $.each(data.Data, function (i, item) {
                            var optionsobj = document.createElement("option");
                            //alert(item.Value + ";" + item.Text);
                            var dataval = item.Value;
                            var datatxt = item.Text;
                            str += "<option value='" + dataval + "'>" + datatxt + "</option>";
                        });
                        $(settings.secondSel).html(str);
                        //alert($(settings.secondSel).html());

                        if (settings.selectedValue != "") {
                            $(settings.secondSel).val(settings.selectedValue); /*根据selectedValue值设置当前选中项*/

                            //$.each($(settings.secondSel + " option"), function (i, item) {
                            //    //alert($(item).val());
                            //    if ($(item).val() == settings.selectedValue) {
                            //        $(this).attr("selected", "selected");
                            //        $(settings.secondSel).parent("div").find("span").html($(this).html());
                            //    }
                            //});

                            $(settings.secondSel).change(); /*自动调用第二级change事件*/
                        }

                    } else {
                        //$(settings.alertBoxId).dialog({ /*Ret不为0时，弹框提示错误信息*/
                        //    modal: true,
                        //    width: 450
                        //});
                        $(settings.alertBoxId).show();
                        $(settings.alertBoxId).find("p").html(data.Message);
                    }
                }
            }, error: function () {
                alert("请求失败");
            }
        });
    }
});