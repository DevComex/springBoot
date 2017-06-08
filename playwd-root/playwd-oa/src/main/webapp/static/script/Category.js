$(function () {  
    
    //页面加载
    $("#twoCategorytab").hide();
    classification("article")  //获取分类
    
    //一级分类点击查看二级分类
    var currId="";
    $("#js_One_ChildList").on("click",".js_category_see",function(){
    	currId=$(this).attr("data-code");
        $("#onecate").val(currId);
        $("#js_One_ChildList tr td").removeClass('on');
        $(this).parent().addClass('on');
        $(this).parent().siblings().addClass('on')
        showtwolist(currId);
    })  
    //内容名称切换
    $("#categorysel").change(function(){
    	var content = $("#categorysel").find("option:selected").attr("value");
    	
    	classification(content)
    });
    
    //一级分类
    function classification(types){
    	$("#js_One_ChildList").html("");
    	$("#twoCategorytab").hide();
	    $.ajax({
	    	url: '/category/getfirstcategorylist',
	        type: 'get',
	        dataType: 'json',
	        data: {r:Math.random(),type:types},
	        success:function(d){
	        	var Data = d.data;
	        	var trHtml ="";
	        	if(d.isSuccess){
	        		if(Data && Data.length){
	        			for(var i =0;i<Data.length;i++){
	        				trHtml +='<tr>'+
				                          '<td  data-id="'+Data[i].code+'" class="js_title">'+Data[i].title+'</td>'+
				                          '<td>'+                 
				                              '<a href="javascript:;" class="btn btn-xs btn-primary js_category_see" title="查看二级分类" data-code="'+Data[i].code+'">'+
				                                  '<i class="fa fa-search"></i>'+
				                              '</a>'+
				                          '</td>'+
				                      '</tr> '
	        				
	        			};
	        			$("#js_One_ChildList").html(trHtml);
	        			return trHtml;
	        		};
	        	}else{
                    alert(d.Message);
                };
	        }
	        
	    });
    };
    
    //显示二级分类
    function showtwolist(id){
        $("#js_ChildList").html("");
        $.ajax({
            url: '/category/getsubcategorylist',
            type: 'GET',
            dataType: 'json',
            data: {pid:id,r:Math.random()},
            success:function(d){
                if (d.isSuccess) {
                    var listhtml="";
                    var Data =d.data;
                    if(Data && Data.length){
	                    for (var i = 0; i < Data.length; i++) {
	                        listhtml+=''+
	                        '<tr>'+
	                        '    <td data-id="'+Data[i].code+'">'+Data[i].title+'</td>'+
	                        '    <td>'+
	                        '        <a data-toggle="modal" href="#edit_Category" class="btn btn-xs btn-info js_category_edit_one " title="编辑">'+
	                        '            <i class="fa fa-edit"></i>'+
	                        '        </a>'+
	                        '        <a data-toggle="modal" href="#del_Category" class="btn btn-xs btn-success js_category_del_one js_twodel " title="删除">'+
	                        '            <i class="fa fa-minus-square"></i>'+
	                        '        </a>'+
	                        '    </td>'+
	                        '</tr>';
	                    };
                    }
                    $(listhtml).appendTo('#js_ChildList');
                    $(".js_category_edit_one").unbind().bind('click',function(){
                        categoryClick($(this));
                    })
                    $(".js_category_del_one").unbind().bind('click',function(){
                        deltwoCate($(this));
                    })
                   
                }else{
                    alert(d.Message);
                }
            }
                    
        });
        $("#twoCategorytab").show();
    };
    
    //添加二级分类
    $(".js_oneCategorySave").click(function () {
        $this=$(this);
        var parentid="";       
        var str = $.trim($("#twoCategoryInput").val());
        parentid=currId;         	
        var id=$("#categorysel").find("option:selected").val();//下拉菜单的内容名称           
        if (checkVal(str)) {
            $.ajax({
                url: '/category/save',
                type: 'POST',
                dataType: 'json',
                data: {title:str,parentId:parentid,r:Math.random()},
                success:function(d){
                    if (d.isSuccess) { 
                    	showtwolist(currId);
                    	$("#twoCategoryInput").val("");
                    }else{
                        alert(d.Message);
                    }
                }
            })
        } 
    });
    
    function categoryClick(obj){
        var $this=obj;
        $("#edit_Category").find(".js_edit_title").text("编辑二级分类名");
        //num=false;
        var $this_td=$this.parent().siblings();
        $currTd=$this_td;
        $("#edit_name").val($this_td.text());
    };
    
    function deltwoCate(obj){
        var $this=obj;
        var $this_td=$this.parent().siblings();
        $currTd=$this_td;       
        $("#del_Category").find(".js_edit_title").text("删除二级分类");
        $("#del_Category").find(".twobox").text("确定要删除该二级分类? ");
        //num=false;
        $("#twocate").val($this_td.attr("data-id"));
    };
    
    //点击二级分类里的删除弹层--确定按钮
    $("#del_ok").click(function(event) {
        var currid=$("#twocate").val();
        $.ajax({
            url: '/category/delete',
            type: 'POST',
            dataType: 'json',
            data: {code:currid,r:Math.random()},
            success:function(d){
                if(d.isSuccess) {                     
                    $(".close").trigger("click");
                    showtwolist(currId);
                }else{
                    alert(d.Message)
                };
            }
        })
    });
    
    //二级分类中的编辑功能
    $("#edit_ok").click(function(event) {
        var edit_name=$("#edit_name").val();
        $("#edit_name").val(edit_name);
        var currid=$currTd.attr('data-id');
        if (!checkVal(edit_name)) {
            return false;
        };

        $.ajax({
            url: '/category/edit',
            type: 'POST',
            dataType: 'json',
            data: {title:edit_name,code:currid,r:Math.random()},
            success:function(d){
                if(d.isSuccess) { 
                    $(".close").trigger("click");
                    showtwolist(currId);
                }else{
                    alert(d.Message)
                };
            }
        });
       
    });
    //验证输入字符不可为空和最大长度
    function checkVal(str) {
        if (str == "" || str.length > 10) {
            alert("分类不能为空,不可超过10个字！");
            return false;
        }
        return true;
    };


});