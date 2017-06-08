/*
 *by hujunhe 2017-3-14
 *文章推荐管理页
 */
$(function(){
	
    //进入页面时调用
    (function(){
    	function getRecommend(val){
    		 var html ="";
	       	 $.get("/slot/list",{contentType : "all",r:Math.random() },function(d){
	                if(d.isSuccess){
	                    for(var i = 0; i< d.data.length; i++){
	                      $.each(d.data[i].list,function(k,v){
	                   	  if(v.slotGroup==val){
	                   		  html+='<option value="' + v.code + '">' +  v.slot + '</option>';
	                   	  }
	                     })
	                       // html+='<option value="' + d.data[i].slotGroup + '">' +  d.data[i].slotGroup + '</option>';
	                    };
	                    $("#js_chooseRecommendCont").html(html);
	                }
	            });
    	}
    	//$("#js_chooseRecommendCont").empty().html("<option value='-1'>推荐位选择</option>")
        //获取推荐位置
        $.get("/slot/list",{contentType : "all",r:Math.random() },function(d){
            if(d.isSuccess){
              var html ="";
                for(var i = 0; i< d.data.length; i++){
                    html+='<option value="' + d.data[i].slotGroup + '">' +  d.data[i].slotGroup + '</option>';
                }
                $("#js_chooseRecommendType").html(html);
                getRecommend($("#js_chooseRecommendType").val());
                
            }else{
                alert(d.message);
            }
        });
        $("#js_chooseRecommendType").change(function(){
        	getRecommend($(this).val());
        })
        
	        pageLayout(1,0,"all","article");
    })();
    $("#js_check").click(function(){
    	if($("#js_chooseRecommendCont").val()!=6){
    		pageLayout($("#js_chooseRecommendCont").val(),0,"all","article");
    	}else{
    		pageLayout($("#js_chooseRecommendCont").val(),0,"all","novel");
    	}
    	
        
    });
    $("#js-save-cancel").click(function(){
        $(".close").trigger("click");
    });
    $("#js-save-confirm").click(function(){
        var code = $(this).attr("data-code"),arr=[];
        var contentType =$('#js_editToRecommend label.active').attr("data-id")==6?"novel":"article";
        $("label.active").each(function(){
            arr.push($(this).attr("data-id"));
        });
        if(arr.length === 0){
            alert("请选择推荐位置！");
            return false;
        }
        arr = arr.join(",");
        $.post("/recommend/management/slot",{contentType : contentType, contentId :code,r:Math.random(),recommendSlotId :arr },function(d){
            if(d.isSuccess){
                alert(d.message);
                $(".close").click();
            }else{
                alert(d.message);
            }
        });
    });

});

function pageLayout(recommendSlotId,prizeId,authorType,contentType){
	//获得年月日      得到日期oTime
    function getMyDate(str){  
        var oDate = new Date(str),  
        oYear = oDate.getFullYear(),  
        oMonth = oDate.getMonth()+1,  
        oDay = oDate.getDate(),  
        oHour = oDate.getHours(),  
        oMin = oDate.getMinutes(),  
        oSen = oDate.getSeconds(),  
        oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间
        return oTime;  
    };  
    //补0操作
    function getzf(num){  
        if(parseInt(num) < 10){  
            num = '0'+num;  
        }  
        return num;  
    };
    $("#js_One_ChildList").empty().ajaxPageS({
            url: "/recommend/management/list",
            pageObj: $(".search_page"),
            pageSize: 20,
            curPageCls: "gy_pageon",
            pageInfo: {
                obj: $(".search_page_txt"),
                content: "第{pageIndex}页  共{allPage}页"
            },
            pageBtn: {
                first: "首页",
                last: "末页",
                prev: "上一页",
                next: "下一页"
            },
            paramObj: {contentType:contentType,recommendSlotId:recommendSlotId,prizeId:prizeId,authorType:authorType,r:Math.random()},
            dataFn: function (data) {
                    var dataHtml ="";
                    if(data.isSuccess){
                        if(contentType=="article"){
                        	for(var i = 0; i < data.dataSet.length; i++){
                                dataHtml += '<tr data-code="' + data.dataSet[i].code + '">' +
                                                '<td>' + data.dataSet[i].title + '</td>'+
                                                '<td><img width="100px" src="' + data.dataSet[i].cover + '"/></td>'+
                                                '<td>' + data.dataSet[i].recommmendTime + '</td>'+
                                                '<td>' +
                                                    '<a data-toggle="modal" data-title="编辑"  data-make="article" class="btn btn-xs btn-info js_editTtheRecommend" href="#chooseRecommendType" title="编辑">'+
                                                        '<i class="fa fa-edit"></i>'+
                                                    '</a>' +
                                                '</td>'+
                                            '<tr>';
                            }
                        }else{
                        	for(var i = 0; i < data.dataSet.length; i++){
                                dataHtml += '<tr data-code="' + data.dataSet[i].code + '">' +
                                                '<td>' + data.dataSet[i].name + '</td>'+
                                                '<td><img width="100px" src="' + data.dataSet[i].cover + '"/></td>'+
                                                '<td>' + getMyDate(data.dataSet[i].create_time) + '</td>'+
                                                '<td>' +
                                                    '<a data-toggle="modal" data-title="编辑"  data-make="novel" class="btn btn-xs btn-info js_editTtheRecommend" href="#chooseRecommendType" title="编辑">'+
                                                        '<i class="fa fa-edit"></i>'+
                                                    '</a>' +
                                                '</td>'+
                                            '<tr>';
                            }
                        }
                        $("#js_One_ChildList").html(dataHtml);
                    }else{
                        alert(data.message);
                    }
                    $("#js_One_ChildList").off().on("click",".js_editTtheRecommend",function(){
                        var code = $(this).parents("tr").attr("data-code");
                        var contentType = $(this).attr("data-make");
                        $("#js-save-confirm").attr("data-code",code);
                        $.get("/recommend/management/slot",{contentType :contentType,contentId :code,r:Math.random()},function(obj){
                            if(obj.isSuccess){
                               if(contentType=="article"){
                            	   var childrenlist1 = '',childrenlist2='',childrenlist3='';
                            	   $(".recommend_tit").html("图文驿站推荐位");
                                   for(var  i = 0; i < obj.data.length; i++){
                                       if(obj.data[i].slotGroup === "玩家天地首页"){
                                           childrenlist1 += '' +
                                               '<label class="btn btn-sm js_tjwz btn-info ' + ((obj.data[i].select === true)?"active":"") +  ' " data-id="' + obj.data[i].code + '">' +
                                                   '    <input type="checkbox" name="options" ><i class="fa fa-check text-active"></i> ' + obj.data[i].slot + '' +
                                               '</label>';
                                       }else if(obj.data[i].slotGroup === "问道官网"){
                                           childrenlist2 += '' +
                                           '<label class="btn btn-sm js_tjwz btn-info ' + ((obj.data[i].select === true)?"active":"") + ' " data-id="' + obj.data[i].code + '">' +
                                               '    <input type="checkbox" name="options" ><i class="fa fa-check text-active"></i> ' + obj.data[i].slot + '' +
                                           '</label>';
                                       }else if(obj.data[i].slotGroup === "图文驿站"){
                                           childrenlist3 += '' +
                                           '<label class="btn btn-sm js_tjwz btn-info ' + ((obj.data[i].select === true)?"active":"") + ' " data-id="' + obj.data[i].code + '">' +
                                               '    <input type="checkbox" name="options" ><i class="fa fa-check text-active"></i> ' + obj.data[i].slot + '' +
                                           '</label>';
                                       }

                                   }
                                   var listhtml =  '<div class="form-group">' +
                                                       '<label for="input-id-2" class="col-sm-2 control-label pad_t_10 pad_r_0">' + "首页" + '</label>' +
                                                       '<div class="m-b-sm col-sm-10">' +
                                                           '<div class="btn-group" data-toggle="buttons">' +
                                                               childrenlist1 +
                                                           '</div>' +
                                                       '</div>' +
                                                   '</div>' +
                                                   '<div class="form-group">' +
                                                       '<label for="input-id-2" class="col-sm-2 control-label pad_t_10 pad_r_0">问道官网</label>' +
                                                       '<div class="m-b-sm col-sm-10">' +
                                                           '<div class="btn-group" data-toggle="buttons">' +
                                                               childrenlist2 +
                                                           '</div>' +
                                                       '</div>' +
                                                   '</div>'+
                                                   '<div class="form-group">' +
                                                   '<label for="input-id-2" class="col-sm-2 control-label pad_t_10 pad_r_0">图文驿站</label>' +
                                                   '<div class="m-b-sm col-sm-10">' +
                                                       '<div class="btn-group" data-toggle="buttons">' +
                                                           childrenlist3 +
                                                       '</div>' +
                                                   '</div>' +
                                               '</div>';
                                                 
                                                   $("#js_editToRecommend").html(listhtml);
                               }else{
                            	   var childrenlist1 = '';
                            	   $(".recommend_tit").html("小说模块推荐位");
                                   for(var  i = 0; i < obj.data.length; i++){
                                       if(obj.data[i].slotGroup === "小说模块"){
                                           childrenlist1 += '' +
                                               '<label class="btn btn-sm js_tjwz btn-info ' + ((obj.data[i].select === true)?"active":"") +  ' " data-id="' + obj.data[i].code + '">' +
                                                   '    <input type="checkbox" name="options" ><i class="fa fa-check text-active"></i> ' + obj.data[i].slot + '' +
                                               '</label>';
                                       }

                                   }
                                   var listhtml =  '<div class="form-group">' +
                                                       '<label for="input-id-2" class="col-sm-2 control-label pad_t_10 pad_r_0">' + "小说模块" + '</label>' +
                                                       '<div class="m-b-sm col-sm-10">' +
                                                           '<div class="btn-group" data-toggle="buttons">' +
                                                               childrenlist1 +
                                                           '</div>' +
                                                       '</div>' +
                                                   '</div>' +
                                               '</div>';
                                                 
                                                   $("#js_editToRecommend").html(listhtml);
                               }
                            }else{
                                alert(d.message);
                            }
                        });
                    });
    	            return dataHtml;
    	        }
        });
}
