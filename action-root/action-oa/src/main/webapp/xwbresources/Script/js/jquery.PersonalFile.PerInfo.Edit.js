/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：陈巧玲
 * 联系方式：chenqiaoling@gyyx.cn
 * 创建时间： 2014/7/29 
 * 版本号：v1.0

 * 内容： 档案管理页面 个人资料修改页

 * -------------------------------------------------------------------------*/
$(function () {

    /*上传图片*/
    UPLOADURL = "http://up.gyyx.cn/File/WebSiteSaveToReal.ashx?group=oa1";
    FLASHURL = "http://up.gyyx.cn/flash/swfupload_f9.swf";
    FILESIZE = "10240";
    FILETYPE = "*.jpg;*.gif";
    TYPESDESCRIPTION = "Image";
    UPLOADLIMIT = "0";
    QUEUELIMIT = 1;
    DEBUG = false;
    UPLOADTARGET = "divFileProgressContainer";
    SAVEELEMENT = "FileCode";
    Initialize();

    //生日
    var dd = new Date();
    var nowY = dd.getFullYear();
    var nowM = dd.getMonth();
    var nowD = dd.getDate();
    $("#Birthdate").datepicker({
        autoclose: true,
        startView: 4,
        format: "yyyy-m-d",
        minView: 2,
        endDate: new Date(nowY, nowM, nowD)
    }).on("changeDate", function (ev) {
        $("#Birthdate").val($(this).val()).blur();
    });

    /*注册验证*/
    /* 移动电话 */
    jQuery.validator.addMethod("mobile", function (value, element, param) {
        var mobiletip = /^[1][0-9]{10}$/;
        return this.optional(element) || (mobiletip.test(value));
    });
    /*固定电话*/
    jQuery.validator.addMethod("telephone", function (value, element, param) {
        //var reg = /^(0\d{2,3})(\d{7,8})(\d{3,})?$/;
        var reg = /^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
        return this.optional(element) || (reg.test(value));
    });
    /*大于小于html脚本字符*/
    jQuery.validator.addMethod("Html", function (value, element, param) {
        var reg = /[<>]/;
        return this.optional(element) || (!reg.test(value));
    });
    /*创建档案第一步验证*/
    $("#EditPerInfo").validate({
        errorElement: "span",
        onblur: true,
        onkeyup: false,
        debug: true,
        ignore: "",
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
            error.addClass("text-danger parsley-error");
        },
        rules: {
            RealName: {
                //required: true
            },
            Gender: {
                //required: true
            },
            Birthdate: {
                //required: true
            },
            TelePhone: {
                telephone: true
            },
            RtxAccount: {
                digits: true
            },
            Email: {
                email: true
            },
            QQ: {
                digits: true
            },
            OfficeAddress: {
                required: true,
                Html: true,
                maxlength:80
            },
            Mobilephone: {
                mobile: true
            },
            JobDescription: {
                required: true,
                Html: true,
                maxlength: 140
            }
        },
        messages: {
            RealName: {
                //required: true
            },
            Gender: {
                //required: true
            },
            Birthdate: {
                //required: true
            },
            TelePhone: {
                telephone: "请正确输入办公电话"
            },
            RtxAccount: {
                digits: "请正确输入RTX号码"
            },
            Email: {
                email: "请正确输入办公邮箱"
            },
            QQ: {
                digits: "请正确输入QQ号码"
            },
            OfficeAddress: {
                required:"请填写办公地点",
                Html: "请不要输入<或>字符",
                maxlength: "办公地点最多输入80字符"
            },
            Mobilephone: {
                mobile: "请正确输入移动电话"
            },
            JobDescription: {
                required: "请填写岗位职责",
                Html: "请不要输入<或>字符",
                maxlength: "岗位职责最多输入140字符"
            }
        }
    });
    $(".js_savePerInfo").click(function () {
        if ($("#EditPerInfo").valid()) {
            $.ajax({
                url: 'http://oa.gyyx.cn/PersonalFile/EditorPerInformation',
                type: "post",
                //async: true,
                data: {
                    StaffCode: $("input[name='StaffCode']").val(),
                    RealName: $("input[name=RealName]").val(),
                    Gender: $("input[name=Gender]").val(),
                    Birthdate: $("input[name=Birthdate]").val(),
                    TelePhone: $("input[name=TelePhone]").val(),
                    RtxAccount: $("input[name=RtxAccount]").val(),
                    Email: $("input[name=Email]").val(),
                    QQ: $("input[name=QQ]").val(),
                    Mobilephone: $("input[name=Mobilephone]").val(),
                    OfficeAddress: $("input[name='OfficeAddress']").val(),
                    JobDescription: $("textarea[name=JobDescription]").val(),
                    PhotoUrl: $("#FileCode").val()
                },
                dataType: "json",
                success: function (d) {
                    alert(d.Message);
                }
            });
        }
    });

    

    

    
    


});
