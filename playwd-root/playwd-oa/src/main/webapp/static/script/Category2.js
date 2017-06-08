$(function () {
    var $currTd; //当前点击的的一级分类table中的td
    var currId;//当前点击的的一级分类table中的td 的id
    var num=true;
    $("#twoCategorytab").hide();
    //Category 标签管理页 点击一级分类 保存功能
    $(".js_oneCategorySave").click(function () {
        $this=$(this);
        var parentid="";
        /*if ($this.hasClass('js_oneCate')) { 
            var str = $("#oneCategoryInput").val();
            parentid=""; num=true;
        }else{
            var str = $("#twoCategoryInput").val();
            parentid=currId; num=false;
        };*/
        var str = $("#twoCategoryInput").val();
        parentid=currId; num=false;
        
        var id=$("#categorysel").find("option:selected").val();
            
        if (checkVal(str)) {
            $.ajax({
                url: '/category/add',
                type: 'POST',
                dataType: 'json',
                data: {Name:str,ParentId:parentid,ContentModuleType:id,r:Math.random()},
                success:function(d){
                    if (d.Success) { 
                        if (num) { window.location.reload();}else{ showtwolist(currId);}
                    }
                    else{
                        alert(d.Message);
                    }
                }
            })
        } 
    });

    //一级分类点击查看二级分类
    $(".js_category_see").click(function(event) {
        currId=$(this).attr("data-code");
        $("#onecate").val(currId);
        $("#js_One_ChildList tr td").removeClass('on');
        $(this).parent().addClass('on');
        $(this).parent().siblings().addClass('on')

        showtwolist(currId);
    });


    //显示二级分类
    function showtwolist(id){
        $("#js_ChildList").html("");
        $.ajax({
            url: '/category/getsubcategorylist',
            type: 'GET',
            dataType: 'json',
            data: {PcId:id,r:Math.random()},
            success:function(d){
                if (d.Success) {
                    var listhtml="";
                    for (var i = 0; i < d.Data.length; i++) {
                        listhtml+=''+
                        '<tr>'+
                        '    <td data-id="'+d.Data[i].Id+'">'+d.Data[i].Name+'</td>'+
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
                    
        })//-

        $("#twoCategorytab").show();
    }

    //Category 标签管理页 点击分类 编辑功能
    $(".js_category_edit_one").click(function(event) {
        categoryClick($(this));

    });

    function categoryClick(obj){
        var $this=obj;
        // alert($this.hasClass('js_oneCate'));
        if ($this.hasClass('js_oneCate')) { 
            $("#edit_Category").find(".js_edit_title").text("编辑一级分类名");
            num=true;
        }else{
           $("#edit_Category").find(".js_edit_title").text("编辑二级分类名");
           num=false;
        };

        var $this_td=$this.parent().siblings();
        $currTd=$this_td;
        $("#edit_name").val($this_td.text());
    }


    $("#edit_ok").click(function(event) {
        var edit_name=$("#edit_name").val();
        $("#edit_name").val(edit_name);
        var currid=$currTd.attr('data-id');
        if (!checkVal(edit_name)) {
            return false;
        }

        $.ajax({
            url: '/category/edit',
            type: 'POST',
            dataType: 'json',
            data: {Name: edit_name,Id:currid,r:Math.random()},
            success:function(d){
               if (d.Success) { 
                    $(".close").trigger("click");
                    if (num) { window.location.reload();}else{showtwolist(currId);}
                }
                else{
                    alert(d.Message)
                }
            }
        })
       
    });


    //Category 标签管理页 点击分类 删除功能
    $(".js_category_del_one").click(function () {
        deltwoCate($(this));
    });

    function deltwoCate(obj){
        var $this=obj;
        var $this_td=$this.parent().siblings();
        $currTd=$this_td;
        if ($this.hasClass('js_oneCate')) { 
            $("#edit_Category").find(".js_edit_title").text("删除一级分类"); num=true;
        }else{
            $("#del_Category").find(".js_edit_title").text("删除二级分类");
            $("#del_Category").find(".twobox").text("确定要删除该二级分类? ");num=false;
        };
        $("#twocate").val($this_td.attr("data-id"));

    }


    $("#del_ok").click(function(event) {
        var currid=$("#twocate").val();
        $.ajax({
            url: '/category/remove',
            type: 'POST',
            dataType: 'json',
            data: {Id:currid,r:Math.random()},
            success:function(d){
               if (d.Success) { 
                    
                    $(".close").trigger("click");
                    if (num) { window.location.reload();}else{showtwolist(currId);}
                }
                else{
                    alert(d.Message)
                }
            }
        })
    });

    //验证输入字符不可为空和最大长度
    function checkVal(str) {
        if (str == "" || str.length > 10) {
            alert("分类不能为空,不可超过10个字！");
            return false;
        }
        return true;
    }



});