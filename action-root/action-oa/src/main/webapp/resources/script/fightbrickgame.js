/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：郭永岗
 * 联系方式：guoyonggang@gyyx.cn
 * 创建时间： 2017/03/12增删改查）
 * -------------------------------------------------------------------------*/
$(function() {

	// 时间格式转换
	Date.prototype.Format = function(fmt) {
		var o = {
			"M+" : this.getMonth() + 1, // 月份
			"d+" : this.getDate(), // 日
			"h+" : this.getHours(), // 小时
			"m+" : this.getMinutes(), // 分
			"s+" : this.getSeconds(), // 秒
			"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
			"S" : this.getMilliseconds()
		// 毫秒
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	};

	// 初始化
	getPage({
	});

	// 查询兑换信息
	$('#btn_query').click(function() {
		var statrTimeJY = $("#txtBeginDay").val();
		var endTimeJY = $("#txtEndDay").val();
		var keyWord = $("#keyWord").val();
		// 查询参数
		var queryPrams = {
			beginTime : statrTimeJY,
			endTime : endTimeJY,
			keyWord : keyWord
		};
		if (statrTimeJY > endTimeJY) {
			alert("开始时间不能大于结束时间");
		} else {
			getPage(queryPrams);
		}

	});

	// 分页列表获取
	function getPage(queryPrams) {
		$("#loadPop").hide();
		$("#htmlWrap").hide();

		$("#tableBox")
				.ajaxPage(
						{
							url : rc + "/fightbrickgame/list",
							pageObj : $("#DataTables_Table_0_paginate"),
							pageIndex : 1,
							pageSize : 10,
							type : "post",
							curPageCls : "paginate_active",
							pageInfo : {
								obj : $("#DataTables_Table_0_info"),
								content : "共{allCount}条  当前第{pageIndex}/{allPage}页"
							},
							paramObj : queryPrams,

							dataFn : function(data, $this) {
								
								if(!data.isSuccess){
									$("#tableBox").empty();
									$(".panel-default").hide();
									alert(data.message);
									return;
								}
								
								var dataHtml = "", dCont = data.rows;
								
								// 详细记录
								if (dCont && dCont.length && dCont[0]) {

									for (var i = 0; i < dCont.length; i++) {

										dataHtml += '<tr>'
												+ '<td>'
												+ dCont[i].code
												+ '</td>'
												+ '<td class="dt" title="'
												+ dCont[i].openid
												+ '">'
												+ dCont[i].openid
												+ '</td>'
												+ '<td class="dt" title="'
												+ dCont[i].nickName
												+ '">'
												+ dCont[i].nickName
												+ '</td>'
												+ '<td class="dt" title="'
												+ dCont[i].latestScore
												+ '">'
												+ dCont[i].latestScore
												+ '</td>'
												+ '<td>'
												+ dCont[i].highScore
												+ '</td>'
												+ '<td style="text-align:center">'
												+ dCont[i].rank
												+ '</td>'
												+ '<td>'
												+ dCont[i].historyScore
												+ '</td>'
												+ '<td>'
												+ dCont[i].updateTime
												+ '</td>'												
												+ '</tr>';
									}
									$(".panel-default").show();
									$("#tableBox").empty().html(dataHtml);

									return dataHtml;

								} else {
									$("#tableBox").empty();
									$(".panel-default").hide();
									alert("暂无记录");
								}
							}

						});
	}

});
