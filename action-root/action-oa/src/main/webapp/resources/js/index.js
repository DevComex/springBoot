
$(function(){
    var getlistNameurl = "/novice-oa/batch/list";//获取批次名称
    var getnewlist = "/novice-oa/batch/list/page";//获取新手卡列表
    var checklisturl = "/novice-oa/batch/code";//查询该批次列表
    var getgameNameurl = "/novice-oa/game/list";//获取游戏名列表
    var getlibaourl = "/novice-oa/gift/list";//获取礼包管理礼包列表
    var addlibaourl = "/novice-oa/gift/add";//添加新礼包
    var updatelibaourl = "/novice-oa/gift/update";//更新礼包
    var deletelibaourl = "/novice-oa/gift/delete";//删除礼包
    var addnewbatchurl = "/novice-oa/batch/add";//添加批次
    var updatebatchurl = "/novice-oa/batch/update";//跟新批次
    var batchinfourl = "/novice-oa/batch/info";//当前批次信息
    var serverlisturl = "/novice-oa/server/list";//服务器列表显示
    var getqudaourl = "/novice-oa/channel/list";//获取渠道列表
    var shownewcardurl = "/novice-oa/channel/gift/generatelist";//显示新手卡信息
    var addnewcardurl = "/novice-oa/channel/gift/add";//新增新手卡渠道礼包
    var updatecardurl = "/novice-oa/channel/gift/update";//更新新手卡渠道礼包
    var isusedurl = "/novice-oa/gift/isused";//判断礼包是否已被新手卡使用
    var abandoncardurl = "/novice-oa/channel/gift/delete";//废弃新手卡接口
    var listurl = "/novice-oa/batch/type/list";//类别列表
    var TempArr=[];//存储option
    var gameName = [];//存储游戏名称
    var serverName = '';//存储服务器名称
    var quhtml = "";//存储渠道信息
    var libaohtml = "";//存储礼包信息
    var defaultQd = "";//存储默认渠道
    var puttypecode;//存储投放对象编号
    var b;
    var pcNum;
    function changeF(this_) {
        $(this_).prev("input").val($(this_).find("option:selected").text());
        $(this_).prev("input").attr("code",$(this_).find("option:selected").val());
        $("#typenum").css({"display":"none"});
    }
    function setfocus(this_){
        $("#typenum").css({"display":""});
        var select = $("#typenum");
        select.html("");
        $.each(TempArr,function(index,val){
            var option = '<option value="'+val[0]+'">'+val[1]+'</option>'
            select.append(option);
        })
    }

    function setinput(this_){
        var select = $("#typenum");
        select.html("");
        for(i=0;i<TempArr.length;i++){
            //若找到以txt的内容开头的，添option
            if(TempArr[i][1]==this_.value){console.log(TempArr[i][1]);console.log(this_.value)
                this_.setAttribute("code",TempArr[i][0]);
            }else{
                this_.setAttribute("code"," ");
            }
            if(TempArr[i][1].indexOf(this_.value)>=0){
                // var option = $("<option></option>").text(TempArr[i]);
                var option = '<option value="'+TempArr[i][0]+'">'+TempArr[i][1]+'</option>'
                select.append(option);
            }
        }

    }
    document.getElementById("makeupCo").onfocus=function(){
        setfocus();
    }
    document.getElementById("makeupCo").oninput=function(){
        var this_ = this;
        setinput(this_);
    }
    document.getElementById("typenum").onchange=function(){
        var this_ = this;
        changeF(this_);
    }

    //存储游戏名称
    function getgamename(){
        $.ajax({
            type:"get",
            url: getgameNameurl,//存储游戏名称
            success:function(d){
                if(d.isSuccess){
                    gameName = eval(d.data);
                    for(var i=0;i<gameName.length;i++){//获取首页游戏列表
                        $("#gameN").append('<option value="'+gameName[i].code+'">'+gameName[i].gameName+'</option>');
                        $("#js_game").append('<option value="'+gameName[i].code+'">'+gameName[i].gameName+'</option>');
                    }
                }else{

                }
            },
            async:true
        })
    }
    getgamename();



    //获取批次名称
    function getlistname(gameId){
        $.ajax({
            type:"get",
            url: getlistNameurl,//获取批次名称列表
            data:{
                r:Math.random(),
                gameId:gameId,
                batchType:"novice"
            },
            success:function(d){
                //var map = new Map();
                if(d.isSuccess){
                    $("#typenum").html("");//console.log(d.data.length)
                    for(var i=0;i<d.data.length;i++){//获取首页游戏列表
                        //$("#typenum").append('<option value="'+d.data[i].code+'">'+d.data[i].name+'</option>');
                        /*先将数据存入数组*/
                        var temp=[];
                        temp[0]=d.data[i].code;
                        temp[1]=d.data[i].name;
                        //TempArr[d.data[i].code] = d.data[i].name;
                        TempArr[i]=temp;
                        //console.log(d.data[i].code);


                        //map.set(d.data[i].code,d.data[i].name);
                    }
                    // map.forEach(function(value ,key) {
                    //     console.log(key,value);
                    // });

                    //console.log(TempArr);

                    //console.log(map.size)
                }else{
                    $("#typenum").html("");
                    $("#makeupCo").val("");
                    TempArr=[];
                    alert(d.message);
                }
            },
            async:false
        })
    }
    getlistname(2);
    $("#gameN").on("change",function(){
        var gameId = $("#gameN option:selected").val();
        getlistname(gameId);
    })
    $(document).bind('click', function(e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && (elem.id == 'typenum' || elem.id == "makeupCo")) {
                return;
            }
            elem = elem.parentNode;
        }
        $('#typenum').css('display', 'none'); //点击的不是div或其子元素
    });

    $("#switch").on("click",function(){
        $("#switch").hasClass("on") ? $("#switch").removeClass("on").find("b").text("已开启") : $("#switch").addClass("on").find("b").text("已关闭");
    })
    page(1,2);
    function page(pageon,gameId){
        $("#table tbody").ajaxPage({//列表展示区
            url: getnewlist,//获取新手卡列表
            type:"get",
            pageObj: $("#DataTables_Table_0_paginate"),
            index: pageon,
            pageCount: 10,//每页显示数量
            curPageCls: "paginate_active",
            pageInfo: {
                obj: $("#DataTables_Table_0_info"),
                content: "共{allCount}条  当前第{pageNo}/{allPage}页"
            },
            paramObj: {
                //传递参数在这边写、
                r:Math.random(),
                gameId : gameId,
                batchType:"novice"
            },
            dataFn: function (data, $this) {
                if(data.isSuccess){
                    if ($(".js_pageipt").val() > Math.floor(b / 1 -0.1)+1) {
                        alert("页码超出范围");
                        page($(".paginate_active").attr("pgid"));
                        return false;
                    }
                    else {
                        var dataHtml = "", dCont = data.data;
                        if (dCont && dCont.length && dCont[0]) {
                            for (var i = 0; i < dCont.length; i++) {
                                dataHtml += '<tr>'+
                                    '<td>'+dCont[i].code+'</td>'+
                                    '<td>'+dCont[i].gameName+'</td>'+
                                    '<td>'+dCont[i].name+'</td>'+
                                    '<td><a class="btn btn-info js_baseinfo" data_code="'+dCont[i].code+'"  data_name="'+dCont[i].name+'" href="#libaoTesk" data-toggle="modal" href="javascript:;">编辑</a><a class="btn btn-info js_manger" data_code="'+dCont[i].code+'"  data_name="'+dCont[i].name+'" href="#updateTask" data-toggle="modal" href="javascript:;">礼包管理</a><a class="btn btn-info js_create" data_code="'+dCont[i].code+'"  data_name="'+dCont[i].name+'" href="#newCardTask" data-toggle="modal">新手卡生成</a></td>'+
                                    '</tr>';

                            }
                            $("#table tbody").empty().html(dataHtml);
                            b = data.totalPage;
                            return dataHtml;

                        } else if (data.data == null || data.data.length==0) {

                            $("#table tbody").empty().html('<tr><td colspan="4">暂无数据</td></tr>');

                        } else {
                            $("#table tbody").empty().html('<tr><td colspan="4">'+d.message+'</td></tr>');
                        }

                    }

                }else{
                    $("#table tbody").empty().html('<tr><td colspan="4">'+data.message+'</td></tr>');
                }

            },
            errorFn:function(){

            }
        });
    }


    //查询该批次列表
    function checklist(code){
        $.ajax({
            type:"get",
            url: checklisturl,//查询该批次列表
            data:{
                r:Math.random(),
                batchCode:code
            },
            success:function(d){
                if(d.isSuccess){
                    var html = "";
                    $("#table tbody").empty().html('<tr>'+
                        '<td>'+d.data.code+'</td>'+
                        '<td>'+d.data.gameName+'</td>'+
                        '<td>'+d.data.name+'</td>'+
                        '<td><a class="btn btn-info js_baseinfo" data_code="'+d.data.code+'"  data_name="'+d.data.name+'" href="#libaoTesk" data-toggle="modal" href="javascript:;">编辑</a><a class="btn btn-info js_manger" data_code="'+d.data.code+'"  data_name="'+d.data.name+'" href="#updateTask" data-toggle="modal" href="javascript:;">礼包管理</a><a class="btn btn-info js_create" data_code="'+d.data.code+'"  data_name="'+d.data.name+'"  href="#newCardTask" data-toggle="modal">新手卡生成</a></td>'+
                        '</tr>');
                }else{
                    alert(d.message);
                }
            },
            error:function(){
                $("#table tbody").empty().html('<tr><td colspan="4">无数据，请重选批次</td></tr>');
            },
            async:true
        })
    }


    $(".js_search").on("click",function(){
        var code = $("#makeupCo").attr("code");
        var value = $("#makeupCo").val();
        if(value==""){
            page(1,2);
            return false;
        }
        $("#DataTables_Table_0_paginate").html("");
        checklist(code);
    })

    //礼包管理页面
    //获取批次下礼包列表
    function getlibao(code){
        $.ajax({
            type:"get",
            url: getlibaourl,//获取批次下礼包列表
            data:{
                r:Math.random(),
                batchCode:code
            },
            success:function(d){
                if(d.isSuccess){
                    var datahtml = '';
                    for(var i=0;i<d.data.length;i++){
                        datahtml += '<tr>'+
                            '<td class="num">'+(i+1)+'</td>'+
                            '<td><input class="b_inp" type="text" value="'+d.data[i].giftName+'" disabled="disabled"/></td>'+
                            '<td><input class="c_inp" type="text" value="'+d.data[i].giftCode+'" disabled="disabled"/></td>'+
                            '<td><input type="checkbox" '+ (d.data[i].isDefault ? "checked=\'checked\'" : "") +' name="checkbox" disabled="disabled" /></td>'+
                            '<td><a class="js_edit" code="'+d.data[i].code+'" href="javascript:;">编辑</a><a class=" js_delete" code="'+d.data[i].code+'" href="javascript:;">删除</a></td>'+
                            '</tr>';
                    }
                    datahtml +='<tr><td colspan="5"><a class="js_newlb" href="javascript:;">+ 新增礼包</a></td></tr>';

                    $("#table2 tbody").html(datahtml);
                }else{
                    $("#table2 tbody").html('<tr><td colspan="5"><a class="js_newlb" href="javascript:;">+ 新增礼包</a></td></tr>');
                }
            },
            async:false
        })
    }
    //添加礼包
    function addlibao(isDefault,batchId,giftName,giftCode){
        $.ajax({
            type:"post",
            url: addlibaourl,//增加新礼包
            data:{
                r:Math.random(),
                isDefault:isDefault,
                batchId:batchId,
                giftName : giftName,
                giftCode :giftCode
            },
            success:function(d){
                if(d.isSuccess){
                    getlibao(pcNum);
                }else{
                    //$("#table2 tbody").html('<tr><td colspan="4">'+d.message+'</td></tr>');
                    alert(d.message);
                }
            },
            async:false
        })
    }
    //更新礼包
    function updatelibao(isDefault,giftId,batchId,giftName,giftCode){
        $.ajax({
            type:"post",
            url: updatelibaourl,//增加新礼包
            data:{
                r:Math.random(),
                isDefault:isDefault,
                batchId:batchId,
                giftId:giftId,
                giftName : giftName,
                giftCode :giftCode
            },
            success:function(d){
                if(d.isSuccess){
                    alert(d.message);
                    getlibao(pcNum);
                }else{
                    alert(d.message);
                }
            },
            async:false
        })
    }
    //删除礼包
    function deletelibao(giftId){
        $.ajax({
            type:"post",
            url: deletelibaourl,//增加新礼包
            data:{
                r:Math.random(),
                code:giftId
            },
            success:function(d){
                if(d.isSuccess){
                    alert(d.message);
                    getlibao(pcNum);
                }else{
                    alert(d.message);
                }
            },
            async:false
        })
    }
    var serverA = [];
    //获取服务器列表信息
    function serverlist(gameId){
        serverName='';
        $.ajax({
            type:"get",
            url: serverlisturl,//增加新礼包
            data:{
                r:Math.random(),
                gameId:gameId
            },
            success:function(d){
                if(d.isSuccess){console.log(serverA.length);
                    $("#base_info .multiselect-native-select").remove();
                    serverName = '<select class="combobox" id="combox" multiple="multiple">'
                    if(serverA.length==0){
                        for(var i=0;i<d.data.length;i++){
                            serverName += '<option value="'+ d.data[i].code +'">'+ d.data[i].serverName +'</option>';
                        }
                    }else{
                        var sidA = [];
                        var snmA = [];
                        sidA = serverA[0].split(",");
                        snmA = serverA[1].split(",");
                        for(var i=0;i<sidA.length;i++){
                            serverName += '<option selected="selected" value="'+ sidA[i] +'">'+ snmA[i] +'</option>';
                        }
                        for(var i=0;i<d.data.length;i++){
                            serverName += '<option value="'+ d.data[i].code +'">'+ d.data[i].serverName +'</option>';
                        }
                    }
                    serverName += '</select>';
                    $('#sername').after(serverName);
                    $('#base_info .combobox').multiselect({
                        maxHeight: 300,
                        enableFiltering: true,
                        filterPlaceholder: 'Search for something...'
                    });
                }else{
                    alert(d.message);
                    $("#base_info .multiselect-native-select").remove();
                    $('#sername').after('<select class="combobox" id="combox" multiple="multiple"></select>');
                    $('#base_info .combobox').multiselect({
                        enableFiltering: true,
                        filterPlaceholder: 'Search for something...'
                    });
                }
            },
            async:false
        })
    }

    function formatDate(now)   {
        var date = new Date(now);
        var year=date.getFullYear();
        var month=date.getMonth()+1;
        var date=date.getDate();
        return   year+"-"+month+"-"+date;
    }
    //显示批次信息
    function batchinfo(batchCode){
        $.ajax({
            type:"get",
            url: batchinfourl,//增加新礼包
            data:{
                r:Math.random(),
                batchCode : batchCode
            },
            success:function(d){
                if(d.isSuccess){
                    for(var i=0;i<$("#js_game option").length;i++){
                        if($("#js_game option").eq(i).val()==d.data.gameId){
                            $("#js_game option").eq(i).attr("selected","selected");
                        }
                    }

                    $("#batchCode").val(d.data.batchName);
                    serverA[0] = d.data.serverId;
                    serverA[1] = d.data.serverName;
                    serverlist(d.data.gameId);
                    $(".js_yxdate").val(formatDate(d.data.startTime));
                    d.data.isOpen ? $("#switch").removeClass("on").find("b").text("已开启") : $("#switch").addClass("on").find("b").text("已关闭");
                }else{
                    alert(d.message);
                }
            },
            error:function(){
                alert("数据请求错误，请重试");
            },
            async:true
        })
    }
    //添加批次
    function addnewbatch(gameId,batchName,serverName,serverId,startTime,isOpen){
        $.ajax({
            type:"post",
            url: addnewbatchurl,//增加新礼包
            data:{
                r:Math.random(),
                gameId:gameId,
                batchName:batchName,
                serverName:serverName,
                serverId:serverId,
                startTime:startTime,
                isOpen:isOpen,
                batchType:"novice"
            },
            success:function(d){
                if(d.isSuccess){
                    alert(d.message);
                    window.location.reload();
                }else{
                    alert(d.message);
                }
            },
            async:false
        })
    }
    //更新批次
    function updatebatch(code,gameId,batchName,serverName,serverId,startTime,isOpen){
        $.ajax({
            type:"post",
            url: updatebatchurl,//增加新礼包
            data:{
                r:Math.random(),
                batchCode:code,
                gameId:gameId,
                batchName:batchName,
                serverName:serverName,
                serverId:serverId,
                startTime:startTime,
                isOpen:isOpen,
                batchType:"novice"
            },
            success:function(d){
                if(d.isSuccess){
                    alert(d.message);
                }else{
                    alert(d.message);
                }
            },
            async:false
        })
    }

    $("#table2").on("click",".js_newlb",function(){
        $(this).parents("tr").before('<tr>'+
            '<td class="num"></td>'+
            '<td><input class="b_inp" type="text"/></td>'+
            '<td><input class="c_inp" type="text"/></td>'+
            '<td><input type="checkbox"  name="checkbox" /></td>'+
            '<td><a class="js_save" href="javascript:;">保存</a><a class="js_adddelete" href="javascript:;">删除</a></td>'+
            '</tr>')
    })
    $("#table2").on("click",".js_save",function(){
        var giftName = $(this).parents("tr").find(".b_inp").val();
        var giftCode = $(this).parents("tr").find(".c_inp").val();
        var isDefault;
        if($(this).parents("tr").find("input[name='checkbox']").is(':checked')){
            isDefault=true;
        }else{
            isDefault=false;
        }

        if( giftName=="" || giftCode==""){
            alert("所有均为必填项");
            return false;
        }
        if(giftName.length>50){
            alert("礼包名称需小于50个字");
            return false;
        }
        if(giftCode.length>50){
            alert("礼包编码需小于50个字");
            return false;
        }
        if($("#table2 input[type='checkbox']:checked").length>=2){
            alert("已有默认发放礼包");
            return false;
        }
        addlibao(isDefault,pcNum,giftName,giftCode);
    })
    $("#js_new").on("click",function(){
        serverA=[];
        serverlist(2);
        // $("#base_info .multiselect-native-select").remove();
        // $('#sername').after(serverName);
        pcNum = "";
        $("#batchCode").val("");
        $('.combobox').multiselect('clearSelection');
        //$("#switch").removeClass("on").find("b").text("已开启");
        $("#switch").addClass("on").find("b").text("已关闭")
        $(".js_yxdate").val("");
    })
    $("#table").on("click",".js_manger",function(){
        pcNum = $(this).attr("data_code");
        getlibao(pcNum);
        //$("#black_font").html("批次名称："+$(this).attr("data_name"));
    })
    $("#table").on("click",".js_baseinfo",function(){
        pcNum = $(this).attr("data_code");
        batchinfo(pcNum);
    })
    $("#table2").on("click",".js_edit",function(){
        var code = $(this).attr("code");
        var _this = $(this);
        $.ajax({
            type:"get",
            url: isusedurl,//增加新礼包
            data:{
                r:Math.random(),
                code:code
            },
            success:function(d){
                if(d.isSuccess){
                    if(confirm("该礼包被新手卡使用，编辑保存后将直接更新已生成的新手卡对应礼包")){
                        _this.parent("td").siblings().find("input").removeAttr("disabled");
                        _this.addClass("js_update").removeClass("js_edit").html("保存");
                    }
                }else{
                    _this.parent("td").siblings().find("input").removeAttr("disabled");
                    _this.addClass("js_update").removeClass("js_edit").html("保存");
                }
            },
            async:false
        })
    })
    $("#table2").on("click",".js_update",function(){
        var giftId = $(this).attr("code");
        var giftName = $(this).parents("tr").find(".b_inp").val();
        var giftCode = $(this).parents("tr").find(".c_inp").val();
        var isDefault;
        if($(this).parents("tr").find("input[name='checkbox']").is(':checked')){
            isDefault=true;
        }else{
            isDefault=false;
        }
        if( giftName=="" || giftCode==""){
            alert("所有均为必填项");
            return false;
        }
        console.log($("#table2 input[type='checkbox']:checked").length)
        if($("#table2 input[type='checkbox']:checked").length>=2){
            alert("已有默认发放礼包");
            return false;
        }
        updatelibao(isDefault,giftId,pcNum,giftName,giftCode);
    })
    $("#table2").on("click",".js_delete",function(){
        var giftId = $(this).attr("code");
        deletelibao(giftId);
    })
    $("#table2").on("click",".js_adddelete",function(){
        $(this).parents("tr").remove();
    })
    $("#js_footer").on("click",".js_save",function(){
        var $id = $("#uc .base_d.on").attr("id");
        var gameId = $("#js_game option:selected").val();
        var batchName = $("#batchCode").val();
        var serverName = $("#base_info .multiselect").attr("title");
        var serverid = [];
        for(var i=0;i<$("#base_info .multiselect-container li.active").length;i++){
            serverid.push( $("#base_info .multiselect-container li.active").eq(i).find("input").val() );
        }
        var serverId = serverid.join();
        var time = $(".js_yxdate").val();
        var data = new Date(time);
        var startTime = data.getTime()/1000-3600*8;
        var isOpen = ($("#switch").hasClass("on") ? 0 : 1);
        if(batchName=="" || serverName=="None selected" || time==""){
            alert("请填写完整");
            return false;
        }
        if(batchName.length>50){
            alert("批次名需小于50个字");
            return false;
        }
        if(pcNum == ""){
            addnewbatch(gameId,batchName,serverName,serverId,startTime,isOpen);
        }else{
            updatebatch(pcNum,gameId,batchName,serverName,serverId,startTime,isOpen);
        }
    })
    $("#js_game").on("change",function(){
        var gameid = $("#js_game option:selected").val();
        if(pcNum==""){
            serverA=[];
        }
        if(gameid==2){
            serverlist(gameid);
        }else{
            alert("系统暂只开放问道新手卡管理");
            $("#base_info .multiselect-native-select").remove();
            $('#sername').after('<select class="combobox" id="combox" multiple="multiple"></select>');
            $('#base_info .combobox').multiselect({
                enableFiltering: true,
                filterPlaceholder: 'Search for something...'
            });
        }

    })
    $("#table3").on("click",".js_newcard",function(){
        var idate=$(this).parents("tr").prev().find(".num").html();
        if($("#table3 tbody tr").length>1){
            if($(this).parents("tr").prev().find("a")[0].className=="js_saveandplan"){
                alert("请先保存新手卡再添加");
                return false;
            }
        }
        getqudao("",3);
        getpclibao(pcNum,"");
        $(this).parents("tr").before('<tr>'+
            '<td class="num"></td>'+
            '<td><select class="lb_inp">'+libaohtml+'</select></td>'+
            '<td><input class="sl_inp" type="text"/></td>'+
            '<td>'+
            '<input style="display: inline-block;" class="form-control input-sm Wdate dinp st_inp" id="js_startdate'+idate+'"  value="" onClick="var d5222=$dp.$(\'js_enddate'+idate+'\'); WdatePicker({onpicked:function(){d5222.click();},maxDate:\'#F{$dp.$D(\\\'js_enddate'+idate+'\\\')}\'})" readonly="">'+
            '至'+
            '<input style="display: inline-block;" class="form-control input-sm Wdate dinp en_inp" id="js_enddate'+idate+'"  value="" onClick="WdatePicker({minDate:\'#F{$dp.$D(\\\'js_startdate'+idate+'\\\')}\'})" readonly="">'+
            '</td>'+
            '<td class="qudaobox"><select class="js_putobjC"><option value="0">请选择投放对象</option><option value="1">落地页</option><option value="2">非落地页</option></select>'+quhtml+'</td>'+
            '<td><input class="ms_inp" type="text"/></td>'+
            '<td><a class="js_saveandplan" txt="go" href="javascript:;">保存并生成</a></td>'+
            '</tr>');
        $('.qudaobox .cardbox').multiselect({
            maxHeight: 300,
            numberDisplayed:2,
            enableFiltering: true,
            filterPlaceholder: 'Search for something...'
        });
    })
    $("#table3").on("change",".js_putobjC",function(){
        var _this=$(this);
        var puttype = $(this).find("option:selected").val();
        var html="";
        _this.siblings(".multiselect-native-select").remove();
        html ='<select class="cardbox" multiple="multiple">';
        if(puttype==0){
            html +="";
        }else{
            $.ajax({
                type:"get",
                url: getqudaourl,//增加新礼包
                data:{
                    r:Math.random(),
                    putType:puttype
                },
                success:function(d){
                    if(d.isSuccess){

                        for(var i=0;i<d.data.length;i++){
                            html += '<option value="'+d.data[i].code+'">'+d.data[i].putName+'</option>';
                        }
                        console.log(html)

                    }else{
                        alert(d.message);
                    }
                },
                async:false
            })
        }

        html += '</select>';
        _this.after(html);
        _this.siblings('.cardbox').multiselect({
            maxHeight: 300,
            numberDisplayed:2,
            enableFiltering: true,
            filterPlaceholder: 'Search for something...'
        });
    })
    //新增新手卡渠道礼包 addnewcardurl
    function addnewcard(batchId,giftId,count,beginDate,endDate,channel,description){
        $.ajax({
            type:"post",
            url: addnewcardurl,//增加新礼包
            data:{
                r:Math.random(),
                batchId:batchId,
                giftId:giftId,
                count:count,
                beginDate:beginDate,
                endDate:endDate,
                channel:channel,
                description:description
            },
            beforeSend:function(){
                $(".js_saveandplan").attr("txt","no");
                $(".js_saveandplan").html("生成中...");
            },
            complete:function(){
                $(".js_saveandplan").attr("txt","go");
                $(".js_saveandplan").html("生成完成");
            },
            success:function(d){
                if(d.isSuccess){
                    alert(d.message);
                    window.location.reload();
                }else{
                    alert(d.message);
                }
            },
            async:true
        })
    }
    //获取批次下的礼包列表  ?batchCode=86
    function getpclibao(batchCode,def){
        libaohtml =  "";
        $.ajax({
            type:"get",
            url: getlibaourl,//获取批次下的礼包列表
            data:{
                r:Math.random(),
                batchCode:batchCode
            },
            success:function(d){
                if(d.isSuccess){
                    for(var i=0;i<d.data.length;i++){
                        if(def==d.data[i].code){
                            libaohtml += '<option value="'+d.data[i].code+'" selected="selected">'+d.data[i].giftName+'</option>';
                        }else{
                            libaohtml += '<option value="'+d.data[i].code+'">'+d.data[i].giftName+'</option>';
                        }
                    }
                }else{
                    //alert(d.message);
                }
            },
            async:false
        })
    }
    //获取渠道信息
    function getqudao(quStr,putType){
        var putType = ((typeof(putType)=="undefined") ? "0" : putType);
        quhtml = '';
        if(putType==3){
            if(quStr==""){
                quhtml ='<select class="cardbox" multiple="multiple">';
            }else{
                quhtml ='<select class="cardbox" multiple="multiple" disabled="disabled">';
            }
            quhtml += '</select>';
        }else{
            $.ajax({
                type:"get",
                url: getqudaourl,//增加渠道列表
                data:{
                    r:Math.random(),
                    putType:putType
                },
                success:function(d){
                    if(d.isSuccess){
                        if(quStr==""){
                            if(putType==0){
                                quhtml ='<select class="cardbox" multiple="multiple" disabled="disabled"></select>';
                                puttypecode=0;
                            }else{
                                quhtml ='<select class="cardbox" multiple="multiple">';
                                for(var i=0;i<d.data.length;i++){
                                    quhtml += '<option value="'+d.data[i].code+'">'+d.data[i].putName+'</option>';
                                    // if(d.data[i].putName=="《问道》官网活动"){
                                    //     defaultQd= d.data[i].code+':'+d.data[i].putName;
                                    // }
                                }
                                quhtml += '</select>';
                            }

                        }else{

                            var quAry = [];
                            var newquA = [];
                            quAry = quStr.split(";");
                            quhtml ='<select class="cardbox" multiple="multiple" disabled="disabled">';
                            for(var i=0;i<d.data.length;i++){
                                newquA.push(d.data[i].code+":"+d.data[i].putName);
                                // if(d.data[i].putName=="《问道》官网活动"){
                                //     defaultQd= d.data[i].code+':'+d.data[i].putName;
                                // }
                                //获取投放对象编号
                                if(putType=="0"){
                                    if(quAry[0].substring(0,quAry[0].indexOf(":"))==d.data[i].code){
                                        puttypecode = d.data[i].putType;
                                    }
                                }
                            }

                            for(var j=0;j<newquA.length;j++){
                                for(var k=0;k<quAry.length;k++){
                                    if(quAry[k]==newquA[j]){
                                        newquA.splice(j,1);
                                    }
                                }
                            }

                            for(var j=0;j<quAry.length;j++){
                                var elindex = quAry[j].substring(0,quAry[j].indexOf(":"));
                                var elqdname = quAry[j].substring(quAry[j].indexOf(":")+1);
                                quhtml += '<option value="'+elindex+'" selected="selected">'+elqdname+'</option>';
                            }
                            for(var j=0;j<newquA.length;j++){
                                var elindex = newquA[j].substring(0,newquA[j].indexOf(":"));
                                var elqdname = newquA[j].substring(newquA[j].indexOf(":")+1);
                                quhtml += '<option value="'+elindex+'">'+elqdname+'</option>';
                            }
                            quhtml += '</select>';

                        }

                    }else{
                        alert(d.message);
                    }
                },
                async:false
            })
        }

    }
    function shownewcard(code){
        $.ajax({
            type:"get",
            url: shownewcardurl,//增加新礼包
            data:{
                r:Math.random(),
                batchId:code
            },
            success:function(d){
                if(d.isSuccess){
                    var datahtml = '';
                    for(var i=0;i<d.data.length;i++){
                        $(".qudaobox").eq(i).find(".multiselect-native-select").remove();
                        getqudao(d.data[i].channel);
                        getpclibao(pcNum,d.data[i].giftId);
                        datahtml += '<tr>'+
                            '<td class="num">'+(i+1)+'</td>'+
                            '<td><select class="lb_inp" disabled="disabled">'+libaohtml+'</select></td>'+
                            '<td><input class="sl_inp" type="text" value="'+d.data[i].count+'" disabled="disabled"/></td>'+
                            '<td class="timeqj">'+ ((d.data[i].beginDate==null || d.data[i].endDate==null) ?  "永久" : '<input style="display: inline-block;" class="form-control input-sm Wdate dinp st_inp" id="js_startdate'+i+'"  value="'+ formatDate(d.data[i].beginDate)+'" onClick="var d5222=$dp.$(\'js_enddate'+i+'\'); WdatePicker({onpicked:function(){d5222.click();},maxDate:\'#F{$dp.$D(\\\'js_enddate'+i+'\\\')}\'})" readonly="" disabled="disabled">至<input style="display: inline-block;" class="form-control input-sm Wdate dinp en_inp" id="js_enddate'+i+'"  value="'+formatDate(d.data[i].endDate)+'" onClick="WdatePicker({minDate:\'#F{$dp.$D(\\\'js_startdate'+i+'\\\')}\'})" readonly="" disabled="disabled">')+
                            '</td>'+
                            '<td class="qudaobox"><select class="js_putobjC" disabled="disabled"><option '+ ((puttypecode==0) ? 'selected="selected"' : "") +' value="0">请选择投放对象</option><option '+ ((puttypecode==1) ? 'selected="selected"' : "") +' value="1">落地页</option><option '+ ((puttypecode==2) ? 'selected="selected"' : "") +' value="2">非落地页</option></select>'+quhtml+'</td>'+
                            '<td><input class="ms_inp" type="text" value="'+d.data[i].description+'" disabled="disabled"/></td>'+
                            '<td><a class="js_editcard" code="'+d.data[i].code+'" href="javascript:;">编辑</a>'+
                            ((puttypecode!=1) ? '<a class="js_export" batchid="'+d.data[i].batchId+'" code="'+d.data[i].code+'" href="/novice-oa/channel/gift/export?batchId='+d.data[i].batchId+'&taskId='+d.data[i].code+'">导出</a>' : "" ) + '<a class="js_abandoncard" code="'+d.data[i].code+'" href="javascript:;">废弃</a></td>'+
                            '</tr>';
                    }
                    datahtml +='<tr><td colspan="7"><a class="js_newcard" href="javascript:;">+ 新增</a></td></tr>';

                    $("#table3 tbody").html(datahtml);
                    $('.qudaobox .cardbox').multiselect({
                        maxHeight: 300,
                        numberDisplayed:2,
                        enableFiltering: true,
                        filterPlaceholder: 'Search for something...'
                    });
                }else{
                    $("#table3 tbody").html('<tr><td colspan="7"><a class="js_newcard" href="javascript:;">+ 新增</a></td></tr>');
                }
            },
            async:false
        })
    }

    $("#table").on("click",".js_create",function(){
        pcNum = $(this).attr("data_code");
        $("#pcname").html('业务组名称：'+$(this).attr("data_name"));
        shownewcard(pcNum);
    })
    //addnewcard(batchId,giftCode,count,beginDate,endDate,channel,description)
    $("#table3").on("click",".js_saveandplan",function(){
        var batchId = pcNum;
        var giftCode = $(this).parent().siblings().find(".lb_inp option:selected").val();
        var count = $(this).parent().siblings().find(".sl_inp").val();
        var beginDate = $(this).parent().siblings().find(".st_inp").val();
        var endDate = $(this).parent().siblings().find(".en_inp").val();
        var channel = $(this).parent().siblings(".qudaobox").find(".multiselect").attr("title");
        var description = $(this).parent().siblings().find(".ms_inp").val();
        var reg = /^[1-9]\d{0,6}$/;
        if(giftCode=="" || giftCode==null){
            alert("请选择礼包");
            return false;
        }
        if(!reg.test(count)){
            alert("请输入少于7位的数字");
            return false;
        }
        if(description.length>50){
            alert("请输入少于50字的描述");
            return false;
        }
        // if(channel=="None selected" && defaultQd==""){
        //     alert("无法获取默认渠道,请重试");
        //     return false;
        // }
        if(channel=="None selected"){
            channel = defaultQd;
        }else{
            channel="";
            var cAry = [],cStr = [];
            var acstr = $(this).parents("tr").find(".multiselect-container li.active");
            cAry = $(this).parent().siblings(".qudaobox").find(".multiselect").attr("title").split(",");
            for(var i=0;i<acstr.length;i++){
                cStr.push( acstr.eq(i).find("input").val() );
            }
            for(var i=0;i<cAry.length;i++){
                channel += cStr[i]+":"+$.trim(cAry[i]) + ";";
            }
            channel = channel.substring(0,channel.length-1);
        }
        if(beginDate=="" || endDate==""){
            beginDate="";
            endDate="";
        }else{
            var data1 = new Date(beginDate);
            beginDate = data1.getTime()/1000-3600*8;
            var data2 = new Date(endDate);
            endDate = data2.getTime()/1000-3600*8;
        }
        if($(this).attr("txt")=="go"){
            addnewcard(batchId,giftCode,count,beginDate,endDate,channel,description)
        }
    })
    function updatecard(code,batchId,giftId,count,beginDate,endDate,channel,description){
        $.ajax({
            type:"post",
            url: updatecardurl,//获取批次下的礼包列表
            data:{
                r:Math.random(),
                code:code,
                batchId:batchId,
                giftId:giftId,
                count:count,
                beginDate:beginDate,
                endDate:endDate,
                channel:channel,
                description:description
            },
            success:function(d){
                if(d.isSuccess){
                    alert(d.message);
                    shownewcard(pcNum);
                }else{
                    alert(d.message);
                }
            },
            async:false
        })
    }
    function abandoncard(code){
        $.ajax({
            type:"post",
            url: abandoncardurl,//获取批次下的礼包列表
            data:{
                r:Math.random(),
                code:code,
                isDelete:1
            },
            success:function(d){
                if(d.isSuccess){
                    alert(d.message);
                    shownewcard(pcNum);
                }else{
                    alert(d.message);
                }
            },
            async:false
        })
    }
    $("#table3").on("click",".js_editcard",function(){
        //$(this).parents("tr").find("input").removeAttr("disabled");timeqj
        $(this).parents("tr").find(".lb_inp").removeAttr("disabled");
        //$(this).parents("tr").find(".js_putobjC").removeAttr("disabled");
        var i = $(this).parents("tr").find(".num").html();
        if($(this).parents("tr").find(".timeqj").html()=="永久"){
            $(this).parents("tr").find(".timeqj").html('<input style="display: inline-block;" class="form-control input-sm Wdate dinp st_inp" id="js_startdate'+(i-1)+'"  value="" onClick="var d5222=$dp.$(\'js_enddate'+(i-1)+'\'); WdatePicker({onpicked:function(){d5222.click();},maxDate:\'#F{$dp.$D(\\\'js_enddate'+(i-1)+'\\\')}\'})" readonly="">至<input style="display: inline-block;" class="form-control input-sm Wdate dinp en_inp" id="js_enddate'+(i-1)+'"  value="" onClick="WdatePicker({minDate:\'#F{$dp.$D(\\\'js_startdate'+(i-1)+'\\\')}\'})" readonly="" >');
        }else{
            $(this).parents("tr").find(".st_inp").removeAttr("disabled");
            $(this).parents("tr").find(".en_inp").removeAttr("disabled");
        }
        $(this).parents("tr").find(".ms_inp").removeAttr("disabled");
        //$(this).parents("tr").find(".cardbox").removeAttr("disabled");
        //$(this).parents("tr").find('.cardbox').multiselect('enable');
        $(this).addClass("js_onlysave").html("保存").removeClass("js_editcard");
    })
    //废弃新手卡
    $("#table3").on("click",".js_abandoncard",function(){
        var code = $(this).attr("code");
        abandoncard(code);
    })
    $("#table3").on("click",".js_onlysave",function(){
        var code = $(this).attr("code");
        var batchId = pcNum;
        var giftCode = $(this).parent().siblings().find(".lb_inp option:selected").val();
        var count = $(this).parent().siblings().find(".sl_inp").val();
        var beginDate = $(this).parent().siblings().find(".st_inp").val();
        var endDate = $(this).parent().siblings().find(".en_inp").val();
        var channel = $(this).parent().siblings(".qudaobox").find(".multiselect").attr("title");
        var description = $(this).parent().siblings().find(".ms_inp").val();
        var reg = /^[1-9]\d{0,6}$/;
        if(!reg.test(count)){
            alert("请输入少于7位的数字");
            return false;
        }
        if(description.length>50){
            alert("请输入少于50字的描述");
            return false;
        }
        // if(channel=="None selected" && defaultQd==""){
        //     alert("无法获取默认渠道,请重试");
        //     return false;
        // }
        if(channel=="None selected"){
            channel = defaultQd;
        }else{
            channel="";
            var cAry = [],cStr = [];
            var acstr = $(this).parents("tr").find(".multiselect-container li.active");
            cAry = $(this).parent().siblings(".qudaobox").find(".multiselect").attr("title").split(",");
            for(var i=0;i<acstr.length;i++){
                cStr.push( acstr.eq(i).find("input").val() );
            }
            for(var i=0;i<cAry.length;i++){
                channel += cStr[i]+":"+$.trim(cAry[i]) + ";";
            }
            channel = channel.substring(0,channel.length-1);
        }
        if(beginDate=="" || endDate==""){
            beginDate="";
            endDate="";
        }else{
            var data1 = new Date(beginDate);
            beginDate = data1.getTime()/1000-3600*8;
            var data2 = new Date(endDate);
            endDate = data2.getTime()/1000-3600*8;
        }
        updatecard(code,batchId,giftCode,count,beginDate,endDate,channel,description)
    })

})