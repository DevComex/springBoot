/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：陈巧玲
 * 联系方式：chenqiaoling@gyyx.cn
 * 创建时间： 2014/7/29 
 * 版本号：v1.0

 * 内容： 档案管理页面  办公信息编辑页

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
    $("#EditThirdStep").validate({
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
            MobilePhone: {
                //required: true,
                mobile:true
            },
            TelePhone: {
                //required: true,
                telephone:true
            },
            FaxNo: {
                //required: true,
                telephone: true
            },
            OfficeAddress: {
                Html: true,
                maxlength:80
            },
            PhotoUrl: {
                //required: true
            },
            JobDescription: {
                Html: true,
                maxlength:140
            },
            QQ: {
                //required: true,
                digits: true
            }
        },
        messages: {
            MobilePhone: {
                mobile: "请正确输入移动电话"
            },
            TelePhone: {
                telephone: "请正确输入座机电话"
            },
            FaxNo: {
                telephone: "请正确输入传真号码"
            },
            OfficeAddress: {
                Html: "请不要输入<或>字符",
                maxlength: "办公地点最多输入80字符"
            },
            PhotoUrl: {
                //required: true
            },
            JobDescription: {
                Html: "请不要输入<或>字符",
                maxlength: "岗位职责最多输入140字符"
            },
            QQ: {
                digits: "请正确输入QQ号码"
            }
        }
    });
    $(".js_saveThirdStep").click(function () {
        if ($("#EditThirdStep").valid()) {
            $.ajax({
                url: 'http://oa.gyyx.cn/PersonalFile/EditorOfficeInfo',
                type: "post",
                //async: true,
                data: {
                    StaffCode: $("input[name='StaffCode']").val(),
                    MobilePhone: $("input[name=MobilePhone]").val(),
                    TelePhone: $("input[name=TelePhone]").val(),
                    FaxNo: $("input[name=FaxNo]").val(),
                    OfficeAddress: $("input[name=OfficeAddress]").val(),
                    PhotoUrl: $("input[name=FileCode]").val(),
                    JobDescription: $("textarea[name=JobDescription]").val(),
                    QQ: $("input[name=QQ]").val()
                },
                dataType: "json",
                success: function (d) {
                    if (d.Ret == 0) {
                        alert(d.Message);
                        window.location.href = window.location.href;
                    } else {
                        alert(d.Message);
                    }
                }
            });
        }
    });

    

    

    
    


});
