$(function(){
    var listurl = "/novice-oa/batch/type/list";//类别列表
    var addurl = "/novice-oa/batch/type/add";//新增列表
    function getlist(){
        $.ajax({
            url:listurl,
            type:"get",
            data:{r:Math.random()},
            success:function(d){
                if(d.isSuccess){
                    if(d.data.length==0){
                        $("#table tbody").html('<tr><td colspan="2">暂无数据</td></tr>');
                    }else{
                        var html = "";
                        for(var i=0;i<d.data.length;i++){
                            html += '<tr><td>'+d.data[i].code+'</td><td>'+d.data[i].typeName+'</td></tr>';
                        }
                        $("#table tbody").html(html);
                    }
                    
                }else{
                    $("#table tbody").html(d.message);
                }
            },
            error:function(){
                $("#table tbody").html("接口调取失败，请重试");
            }
        })
    }
    function getadd(typeName,abbreviate){
        $.ajax({
            url:addurl,
            type:"post",
            data:{r:Math.random(),typeName:typeName,abbreviate:abbreviate},
            success:function(d){
                if(d.isSuccess){
                    alert(d.message);
                    window.location.reload();
                }else{
                    alert(d.message);
                }
            },
            error:function(){
                alert("接口调取失败，请重试")
            }
        })
    }
    getlist();
    $("#newTypeTask").on("click",".js_save",function(){
        var pin = $.trim($("#js_typePin").val());
        var info = $.trim($("#js_typeInfo").val());
        if(info==""){
            alert("类别名不能为空");
            return false;
        }
        if(info.length>20){
            alert("类别名需小于20个字");
        }
        if(pin==""){
            alert("类别名缩写不能为空");
            return false;
        }
        if(pin.length>20){
            alert("类别名缩写需小于20个字");
            return false;
        }
        getadd(info,pin);
    })
})