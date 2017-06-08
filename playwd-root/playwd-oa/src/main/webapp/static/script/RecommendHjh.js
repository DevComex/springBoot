$(function(){
    $(".js_category_edit_one").click(function(){
        var parent = $(this).parents(".js-recommend-cmd");
        var url = "/recommend/content/",actionPartOfUrl='',code=parent.attr("data-id");
        var data= {code :code };

        var objType = $(this).attr("data-action");
        if(objType === "edit"){
            $(".imgEditDiv").html(imgDIV);
            Initialize();
            $(".js_createRoleSubmit").attr("data-code",code);
            $("#rcmd-title").val($(this).parent().siblings().eq(0).text());
            $("#rcmd-url").val($(this).parent().siblings().eq(2).text());
            $("#oldImgUrl").html($(this).parent().siblings().eq(1).find("img").attr("src"));
            
            $("#oldImgUrl").parent().find("img").remove();
            $("#oldImgUrl").parent().append("<img src='"+$("#oldImgUrl").html()+"' width=292 height=163 />");
            console.log($(this).parent().siblings().eq(1).find("img").attr("src"));
        }else if(objType === "hide" || objType === "show"){
            objType = (objType === "hide") ? false : true;
            $.post("/recommend/content/show",{code :code , isDisplay :objType ,r:Math.random()},function(d){
                if(d.isSuccess){
                    alert(d.message);
                    window.location.reload();
                }else{
                    alert(d.message);
                }
            });
        }else if(objType === "moveUp" || objType === "moveDown" || objType === "moveTop" || objType === "moveBottom"){
            $.post("/recommend/content/move",{code:code ,r:Math.random(),type : objType},function(d){
                if(d.isSuccess){
                    alert(d.message);
                    window.location.reload();
                }else{
                    alert(d.message);
                }
            });
        }
    });
    //推荐 确定键
    $("#js-save-confirm").click(function(){
        var imgurl ="";
        if($("#js_uploadimg").length > 0){
            imgurl = $("#js_uploadimg").attr("src");
        }else{
            imgurl = $("#oldImgUrl").text();
        }
        $.post("/recommend/content/update",{code:$(this).attr("data-code"), title: $("#rcmd-title").val(), url : $("#rcmd-url").val(), cover :imgurl ,r:Math.random()},function(d){
            if(d.isSuccess){
                alert(d.message);
                window.location.reload();
            }else{
                alert(d.message);
            }
        });
    });
    $('#js-save-cancel').click(function () {
        $(".close").trigger("click");

    });



});
