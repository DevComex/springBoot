function getQuery(name){
	var reg =new RegExp("(^|&)"+name+"=([^&]*)(&|$)"),
	r=window.location.search.substr(1).match(reg);
	if(r !== null){
		return r[2];
	}
	return null;
}
//目录页评论页--title
function commentList(types,manhuaCode){
	$("#js_commentDiv").ajaxPage({
		url:rc+"/details/comment",
		type:"get",
		pageObj:$("#DataTables_Table_0_paginate_collection2") ,    //,
		pageIndex: 1,//默认起始页为第一页
		pageSize: 3,//一页个数
		curPageCls: "paginate_active",
		pageInfo: {
			obj: $("#DataTables_Table_0_info"),
	        content: ""
		},
		paramObj: {
			categoryCode:0,
			type:types,
			manhuaCode:manhuaCode
		},

	});
}
