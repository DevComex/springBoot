function query(currentPage) {
	$("#nomanitionBackTable tbody").empty();
	var data = {
		accountName : $("#account").val(),
		pageNo : currentPage

	};
	$.ajax({
		url : "nomanitionBackList",
		type : "GET",
		data : data,
		cache : false,
		dataType : "json",
		success : function(data) {
			if (data.data == null || data.data.list == null
					|| data.data.list.length == 0) {
				alert("没有符合条件的记录");
				$("#firstPage-btn").attr("disabled", true);
				$("#prevPage-btn").attr("disabled", true);
				$("#nextPage-btn").attr("disabled", true);
				$("#lastPage-btn").attr("disabled", true);
				$("#totalPage-span").text(1);
				$("#currentPage-span").text(1);
				return false;
			}
			var page = data.data;
			var list = page.list;

			for (var i = 0; i < list.length; i++) {
				var $tr = "";
				$tr = "<tr><td>" + list[i].nominatedServerName
						+ "</td></td><td>" + list[i].nominatedRole
						+ "</td><td>" + list[i].accountName + "</td><td>"
						+ list[i].accountServerName + "</td><td>"
						+ list[i].accountRole + "</td><td>" + list[i].voteWhite
						+ "</td><td>" + list[i].whiteRanking + "</td><td>"
						+ list[i].voteBlack + "</td><td>"
						+ list[i].blackRanking + "</td></tr>";
				$("#nomanitionBackTable tbody").append($tr);
			}
			$("#currentPage-span").text(page.pageIndex);
			$("#totalPage-span")
					.text(page.totalPages < 1 ? 1 : page.totalPages);
			if (page.isHaveNextPage) {
				$("#nextPage-btn").attr("disabled", false);
				$("#lastPage-btn").attr("disabled", false);
			} else {
				$("#nextPage-btn").attr("disabled", true);
				$("#lastPage-btn").attr("disabled", true);
			}
			if (page.isHavePrePage) {
				$("#firstPage-btn").attr("disabled", false);
				$("#prevPage-btn").attr("disabled", false);
			} else {
				$("#firstPage-btn").attr("disabled", true);
				$("#prevPage-btn").attr("disabled", true);
			}

		},
		error : function() {
			alert("网络错误");
		}

	});
}

$(function() {
	query(1);
	// 翻页
	$("#firstPage-btn").on("click", function() {
		query(1);
	});
	$("#lastPage-btn").on("click", function() {
		query($("#totalPage-span").text());
	});
	$("#prevPage-btn").on("click", function() {
		query(parseInt($("#currentPage-span").text()) - 1);
	});
	$("#nextPage-btn").on("click", function() {
		query(parseInt($("#currentPage-span").text()) + 1);
	});

	// 查询
	$("#query-btn").on(
			"click",
			function() {
				var accountName = $.trim($("#account").val());
				if (accountName == null || accountName == "") {
					accountName == "";
				}

				$.ajax({
					url : "nomanitionBackList",
					type : "GET",
					data : {
						"pageNo" : 1,
						"accountName" : accountName
					},
					cache : false,
					dataType : "json",
					success : function(data) {
						$("#nomanitionBackTable tbody").empty();
						if (!data.isSuccess) {
							alert(data.message);
							$("#firstPage-btn").attr("disabled", true);
							$("#prevPage-btn").attr("disabled", true);
							$("#nextPage-btn").attr("disabled", true);
							$("#lastPage-btn").attr("disabled", true);
							$("#totalPage-span").text(1);
							$("#currentPage-span").text(1);
							return false;
						}
						var page = data.data;
						var list = page.list;

						for (var i = 0; i < list.length; i++) {
							var $tr = "";
							$tr = "<tr><td>" + list[i].nominatedServerName
									+ "</td></td><td>" + list[i].nominatedRole
									+ "</td><td>" + list[i].accountName
									+ "</td><td>" + list[i].accountServerName
									+ "</td><td>" + list[i].accountRole
									+ "</td><td>" + list[i].voteWhite
									+ "</td><td>" + list[i].whiteRanking
									+ "</td><td>" + list[i].voteBlack
									+ "</td><td>" + list[i].blackRanking
									+ "</td></tr>";
							$("#nomanitionBackTable tbody").append($tr);
						}
						$("#currentPage-span").text(page.pageIndex);
						$("#totalPage-span").text(
								page.totalPages < 1 ? 1 : page.totalPages);
						if (page.isHaveNextPage) {
							$("#nextPage-btn").attr("disabled", false);
							$("#lastPage-btn").attr("disabled", false);
						} else {
							$("#nextPage-btn").attr("disabled", true);
							$("#lastPage-btn").attr("disabled", true);
						}
						if (page.isHavePrePage) {
							$("#firstPage-btn").attr("disabled", false);
							$("#prevPage-btn").attr("disabled", false);
						} else {
							$("#firstPage-btn").attr("disabled", true);
							$("#prevPage-btn").attr("disabled", true);
						}
					},
					error : function() {
						alert("网络错误");
					}

				});

				return false;
			});

});