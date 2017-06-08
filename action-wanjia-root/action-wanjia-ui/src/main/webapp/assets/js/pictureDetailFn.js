/*-------------------------------------------------------------------------
* 作者：骆崇飞
* 邮箱：luochongfei@gyyx.cn
* 创建时间： 2014/12
* 版本号：v1.0
* 作用域：问道玩家天地查看图册详情
-------------------------------------------------------------------------*/
$(function () {
    //缩略图ul
    var $abbrUl = $("#js_AbbrUl");
    //缩略图左按钮
    var $abbrPrevBtn = $("#js_DetailPrevAbbrBtn");
    //缩略图右按钮
    var $abbrNextBtn = $("#js_DetailNextAbbrBtn");

    var abbrCurPage = 1;
    var imgPage = 0;

    //绑定操作事件
    function bindSetEvent() {
        //缩略图li
        var $abbrLi = $abbrUl.find("li");
        //单个缩略图li的宽度
        var $abbrLiW = $abbrLi.outerWidth(true);
        //缩略图页数
        var abbrPageCount = Math.ceil($abbrLi.length / 6);

        //大图左按钮
        var $imgPrevBtn = $("#js_DetailPrevBtn");
        //大图右按钮
        var $imgNextBtn = $("#js_DetailNextBtn");

        //点击各个缩略图
        $abbrLi.unbind("click").bind("click", function () {
            var thisIdx = $(this).index();
            imgPage = thisIdx;
            $(this).addClass("on").siblings().removeClass("on");
            showPicInfo(imgPage);
            return false;
        });

        //大图左按钮点击
        $imgPrevBtn.click(function () {
            --imgPage;
            if ((imgPage + 1) % 6 ==0 || imgPage == -1) {
                $abbrPrevBtn.trigger("click");
            }
            if (imgPage == -1) {
                imgPage = $abbrLi.length - 1;
            }
            $abbrLi.eq(imgPage).addClass("on").siblings().removeClass("on");
            showPicInfo(imgPage);
            return false;
        });


        //大图右按钮点击
        $imgNextBtn.click(function () {
            ++imgPage;
            if (imgPage % 6 == 0 || imgPage == $abbrLi.length) {
                $abbrNextBtn.trigger("click");
            }
            if (imgPage == $abbrLi.length) {
                imgPage = 0;
            }
            $abbrLi.eq(imgPage).addClass("on").siblings().removeClass("on");
            showPicInfo(imgPage);
            return false;
        });

        //根据情况显示缩略图按钮及绑定事件
        if ($abbrLi.length < 6) {//如果小于6张不显示缩略图左右按钮
            $abbrPrevBtn.hide();
            $abbrNextBtn.hide();
        } else {
            $abbrUl.css("width", abbrPageCount * 6 * $abbrLiW + "px");

            //缩略图左按钮点击
            $abbrPrevBtn.unbind("click").bind("click", function () {
                if ($abbrUl.is(":animated")) {
                    return false;
                }
                if (abbrCurPage <= 1) {//如果当前是第一组
                    $abbrUl.animate({
                        left: "-=" + 6 * $abbrLiW * (abbrPageCount - 1)
                    });
                    abbrCurPage = abbrPageCount;
                    $abbrLi.eq($abbrLi.length - 1).addClass("on").siblings().removeClass("on");
                    imgPage = $abbrLi.length - 1
                } else {
                    $abbrUl.animate({
                        left: "+=" + 6 * $abbrLiW
                    });
                    abbrCurPage--;
                    $abbrLi.eq(abbrCurPage * 6 - 1).addClass("on").siblings().removeClass("on");
                    imgPage = abbrCurPage * 6 - 1
                }

                showPicInfo(imgPage);
                return false;
            });

            //缩略图右按钮点击
            $abbrNextBtn.unbind("click").bind("click", function () {
                if ($abbrUl.is(":animated")) {
                    return false;
                }
                if (abbrCurPage >= abbrPageCount) {//如果当前是最后一组
                    $abbrUl.animate({
                        left: 0
                    });
                    abbrCurPage = 1;
                    $abbrLi.eq((abbrCurPage - 1) * 6).addClass("on").siblings().removeClass("on");
                    imgPage = (abbrCurPage - 1) * 6
                } else {
                    $abbrUl.animate({
                        left: "-=" + 6 * $abbrLiW
                    });
                    abbrCurPage++;
                    $abbrLi.eq((abbrCurPage - 1) * 6).addClass("on").siblings().removeClass("on");
                    imgPage = (abbrCurPage - 1) * 6
                }

                showPicInfo(imgPage);
                return false;
            });


        }
    }


    //获取图片数据
    function getPicInfo() {
        var dCont = DetailList;   //接口
        if (dCont && dCont[0]) {
            var lihtml = '';
            for (var i = 0; i < dCont.length; i++) {
                lihtml += '<li><img src="' + dCont[i].PictureSmall + '"></li>';
            }
            $abbrUl.html(lihtml).find("li").eq(0).addClass("on");
            showPicInfo(0);
            bindSetEvent();

            var ImgTitle=dCont[0].AlbumsName;
            $("#fenxiang").html(ImgTitle);
            $("#js_ImageTitle").html(ImgTitle);
        } else {
            window.location.href = "/Home/Index";
        }
    }

    getPicInfo();

    //展示图片
    function showPicInfo(idx) {
        var dCont = DetailList[idx];
        //大图
        $("#js_load").show();
        var Img = new Image();
        setImageSize($("#js_DetailImage"), Img);
        //防止IE从缓存中取图片，所以将路径放在image onload后放置
        Img.src = dCont.Picture;
        //简介
        $("#js_ThisImageIntro").html("<span class='intro_title'>描述：</span>" + (dCont.Brief != "null" ? dCont.Brief : " "));
        //页码
        $("#js_ViewPage").html('<span class="curr">' + (idx + 1) + '</span><i></i>' + DetailList.length);
        //绑定下载图片地址
        $("#js_DownloadImage").attr("href", dCont.Picture);
    }

    $(".js_CollectThisWorks").click(function () {
        var
            $thisTxt = $(this).find("b"),
            curCount = $thisTxt.text();

        collectWorks({
            code: DetailList[0].Code,
            collectType: 2,
            success: function (data) {
                if (data) {
                    alert(data.Message);
                    if (data.IsSuccess) {
                        $thisTxt.html(parseInt(curCount)+1);
                    }
                }
            },
            error: function () {
                alert(":( 程序接口出错，请重试！")
            }
        });
        return false;
    });

});

//设置图片大小及定位
function setImageSize($Img, Img) {
    var $Img = $Img;
    var maxW = 700;
    var maxH = 480;
    Img.onload = function () {
        var thisW = this.width;
        var thisH = this.height;
        if (thisW <= maxW && thisH <= maxH) {//宽小于或等于maxW和高小于或等于maxH时
            $Img.css({
                width: thisW,
                height: thisH
            });
            $Img.css({
                marginLeft: -Math.ceil(thisW / 2),
                marginTop: -Math.ceil(thisH / 2)
            });
        }
        else if (thisW > maxW && thisH > maxH) {//宽大于maxW和高大于maxH时
            if (thisW / thisH > 1.2) {
                $Img.css({
                    width: thisW / (thisH / maxH),
                    height: maxH
                });
                $Img.css({
                    marginLeft: -Math.ceil($Img.width()/2),
                    marginTop:-Math.ceil(maxH/2)
                });
            } else {
                $Img.css({
                    height: maxH,
                    width:thisW/(thisH/maxH)
                });

                $Img.css({
                    marginLeft: -Math.ceil($Img.width() / 2),
                    marginTop: -maxH/2
                });
            }
        } 
        else if (thisW > maxW && thisH < maxH) {//宽大于maxW和高小于maxH时
            $Img.css({
                width: maxW,
                height: thisH / (thisW / maxW)
            });
            $Img.css({
                marginLeft: -Math.ceil(maxW / 2),
                marginTop: -Math.ceil($Img.height() / 2)
            });
        }
        else if (thisW < maxW && thisH > maxH) {//宽小于maxW和高大于maxH时
            $Img.css({
                height: maxH,
                width: thisW / (thisH / maxH)
            });

            $Img.css({
                marginLeft: -Math.ceil($Img.width() / 2),
                marginTop: -maxH / 2
            });
        }

        $("#js_load").hide();
        $Img.attr("src", this.src).fadeIn();
    }
}
