 jQuery.extend(jQuery.validator.messages, {
	    required: "<span class='error_valid_msg_modal_tip' xcon='必填项'></span>",
	    remote: "<span class='error_valid_msg_modal_tip' xcon='该用户名已存在，请更换用户名'></span>",
	    email: "<span class='error_valid_msg_modal_tip' xcon='请输入合法的电子邮件'></span>",
		url: "<span class='error_valid_msg_modal_tip' xcon='请输入合法的网址'></span>",
		digits: "<span class='error_valid_msg_modal_tip' xcon='请输入整数'></span>",
		max: "<span class='error_valid_msg_modal_tip' xcon='最大值为 {0}'></span>",
		min: "<span class='error_valid_msg_modal_tip' xcon='最小值为 {0}'></span>",
		maxlength: "<span class='error_valid_msg_modal_tip' xcon='最大长度为 {0}'></span>",
		minlength: "<span class='error_valid_msg_modal_tip' xcon='最大长度为 {0}'></span>"
	});
 
 
//绩效 扣分 测试人员 用例产出数目 之条数 格式：100,200,300
$.validator.addMethod("caseOutputScoreReg",function(value, element){
	return this.optional(element) || /^(\d*\.\d*|\d*)(,(\d*\.\d*|\d*))*$/.test(value);
}, "<span class='error_valid_msg_modal_tip' xcon='请输入以英文逗号隔开的数字'></span>");
 
//最多两位小数点
$.validator.addMethod("max2dot",function(value, element) {
	return this.optional(element) || /^-?([1-9]{1}[0-9]*|0)(\.[0-9]{1,2})?$/.test(value);
},"<span class='error_valid_msg_modal_tip' xcon='请输入最多两位小数的数字'></span>");