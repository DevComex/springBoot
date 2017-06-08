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
		auditStatus : 0
	});

	// 查询兑换信息
	$('#btn_query').click(function() {
		var statrTimeJY = $("#txtBeginDay").val();
		var endTimeJY = $("#txtEndDay").val();
		var auditStatus = $("#AuditStatus").val();
		var keyWord = $("#keyWord").val();
		// 查询参数
		var queryPrams = {
			auditStatus : auditStatus,
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
	// 批量审批
	$(".btn_batch_audit").click(function() {
		var auditValue = $(this).attr("auditValue");
		if (checkBoxCode($(this))) {
			var codes = $(this).attr("codes");
			// 发起请求审核留言
			$.ajax({
				url : rc + "/wd11thyearscommentwall/audit",
				type : "POST",
				data : {
					auditValue : auditValue,
					codes : codes
				},
				cache : false,
				dataType : "json",
				success : function(data) {
					alert("批量" + data.message);
					// 初始化
					var statrTimeJY = $("#txtBeginDay").val();
					var endTimeJY = $("#txtEndDay").val();
					var auditStatus = $("#AuditStatus").val();
					var keyWord = $("#keyWord").val();
					// 查询参数
					var queryPrams = {
						auditStatus : auditStatus,
						beginTime : statrTimeJY,
						endTime : endTimeJY,
						keyWord : keyWord
					};
					getPage(queryPrams);
				}
			});
		}
	});

	// 单条审批
	$("#tableBox").on("click", ".btn_audit", function() {
		var auditValue = $(this).attr("auditValue");
		var codes = $(this).attr("codes");

		// 发起请求审核留言
		$.ajax({
			url : rc + "/wd11thyearscommentwall/audit",
			type : "POST",
			data : {
				auditValue : auditValue,
				codes : codes
			},
			cache : false,
			dataType : "json",
			success : function(data) {
				alert(data.message);
				// 初始化
				var statrTimeJY = $("#txtBeginDay").val();
				var endTimeJY = $("#txtEndDay").val();
				var auditStatus = $("#AuditStatus").val();
				var keyWord = $("#keyWord").val();
				// 查询参数
				var queryPrams = {
					auditStatus : auditStatus,
					beginTime : statrTimeJY,
					endTime : endTimeJY,
					keyWord : keyWord
				};
				getPage(queryPrams);
			}
		});
	});

	// 分页列表获取
	function getPage(queryPrams) {
		$("#loadPop").hide();
		$("#htmlWrap").hide();

		$("#tableBox")
				.ajaxPage(
						{
							url : rc + "/wd11thyearscommentwall/query",
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

								var dataHtml = "", dCont = data.rows, statisticsRows = data.data, statisticsDetail = "";
								// 统计数据
								if (statisticsRows && statisticsRows.length
										&& statisticsRows[0]) {
									for (var i = 0; i < statisticsRows.length; i++) {
										statisticsDetail = statisticsDetail
												+ '<div class="statisticsDetail"><p><span>'
												+ statisticsRows[i].actionName
												+ '：</span><span style="color:red;">'
												+ statisticsRows[i].total
												+ '</span></p></div>';
									}
									$("#statisticsDetails").empty().html(
											statisticsDetail);
								} else {
									statisticsDetail = '<div class="statisticsDetail"><p><span>暂无活动留言数据！</span></p></div>';
									$("#statisticsDetails").empty().html(
											statisticsDetail);
								}
								// 详细记录
								if (dCont && dCont.length && dCont[0]) {

									for (var i = 0; i < dCont.length; i++) {
										var code = dCont[i].code;
										var actionName = dCont[i].actionName;
										var nickName = dCont[i].nickName;
										var message = dCont[i].message;

										var isAudit = dCont[i].isAudit;
										var auditor = dCont[i].auditor;
										var createTime = dCont[i].createTime;
										var auditTime = dCont[i].auditTime;

										var operateHtml = '';
										if (isAudit == 0) {
											isAudit = '未审核';
											operateHtml = '<a href="javascript:;" class="btn btn-info btn_audit" auditValue="1" codes='
													+ dCont[i].code
													+ '>通过</a>'
													+ '<a href="javascript:;" class="btn btn-danger btn_audit" auditValue="2" style="margin-left:5px" codes='
													+ dCont[i].code + '>拒绝</a>'
										} else if (isAudit == 1) {
											isAudit = '通过';
											operateHtml = '<a href="javascript:;" class="btn btn-danger btn_audit" auditValue="2" codes='
													+ dCont[i].code + '>拒绝</a>'
										} else {
											isAudit = '拒绝';
											operateHtml = '<a href="javascript:;" class="btn btn-info btn_audit" auditValue="1" codes='
													+ dCont[i].code + '>通过</a>'
										}
										if (auditTime == null) {
											auditTime = '';
										} else {
											auditTime = new Date(auditTime)
													.Format("yyyy-MM-dd hh:mm:ss");
										}
										if (createTime == null) {
											createTime = '';
										} else {
											createTime = new Date(createTime)
													.Format("yyyy-MM-dd hh:mm:ss");
										}
										if (auditor == null) {
											auditor = '';
										}
										dataHtml += '<tr>'
												+ '<td style="vertical-align:middle;" align="center"><input type="checkbox" value="'
												+ code
												+ '" name="key" /></td>'
												+ '<td>'
												+ dCont[i].code
												+ '</td>'
												+ '<td class="dt" title="'
												+ dCont[i].actionName
												+ '" actionName='
												+ dCont[i].actionName
												+ '>'
												+ actionName
												+ '</td>'
												+ '<td class="dt" title="'
												+ dCont[i].nickName
												+ '">'
												+ dCont[i].nickName
												+ '</td>'
												+ '<td class="dt" title="'
												+ dCont[i].message
												+ '">'
												+ dCont[i].message
												+ '</td>'
												+ '<td class="createTime">'
												+ createTime
												+ '</td>'
												+ '<td style="text-align:center">'
												+ isAudit
												+ '</td>'
												+ '<td>'
												+ auditor
												+ '</td>'
												+ '<td>'
												+ auditTime
												+ '</td>'
												+ '<td style="text-align:center">'
												+ operateHtml
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

	// 遍历checkBox获取选中code
	function checkBoxCode(id) {
		var ids = '';
		var flag = 0;
		id.attr("codes", ids);
		$("input[name='key']:checkbox").each(function() {
			if ($(this).is(':checked')) {
				ids += $(this).val() + ',';
				flag += 1;
			}

		});

		ids = ids.substring(0, ids.length - 1);
		if (flag > 0) {
			id.attr("codes", ids);
			return true;

		} else {
			alert('请至少选择一项！');
			return false;
		}
	}
});
