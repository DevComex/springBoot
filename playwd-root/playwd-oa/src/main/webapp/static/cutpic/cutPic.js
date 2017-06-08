/*-------------------------------------------------------------------------
* author:luochongfei
* date:2015-8
* email:luochongfei@126.com
* desc:前端图片裁切（IE10+）
-------------------------------------------------------------------------*/
; (function (window,$) {
    "use strict";
    var CutPic = function (options) {
        var options=this.options = options || {};
        //file控件
        var fileId = options.fileId;
        this.oFile = document.getElementById(fileId);

        //预览原图框
        //file控件
        var picBoxId = options.picBoxId;
        this.oriPicBox = document.getElementById(picBoxId);

        //canvas画布
        var canvasId = options.canvasId;
        this.oCanvas = document.getElementById(canvasId);

        //预览图ID
        this.cutImgId = options.cutImgId;

        //裁切出来的尺寸（裁切框最小尺寸，和可上传最小的图片会受此配置影响）
        this.cutSize = 96;
       // this.cutSizeH = 150;
        //准备加载文件
        this.loadFile();
        //修改预览图的宽高       
       // this.a=this.options.a || 429;
       // this.b=this.options.b || 300;
        
        //$(".uploadImgDiv").hide();
    	//$(editPortrait).show();
        this.uploadImgDiv = this.options.uploadImgDiv;
        this.editPortrait = this.options.editPortrait;
       
        
    };
    CutPic.prototype = {
        //成功上传文件
        loadFile: function () {
            var
                _this=this,
                //file控件
                oFile = this.oFile,
                //变化事件
                fileChangeFn = function () {
                    if (!oFile.files) {
                        alert('您使用的浏览器不支持此项功能，推荐使用IE10+ Chrome Firefox Edge  !');
                        return false;
                    }
                    if (/image\/(png|jpg|jpeg)/.test(oFile.files[0].type)) {
                        _this.readFile();
                    }else{
                        alert("请选择(png|jpg)格式的图片！");
                    }
                };

            //为file控件绑定变化事件
            if (window.addEventListener) {
                oFile.addEventListener("change", fileChangeFn);
            } else {
                oFile.attachEvent("onchange", fileChangeFn);
            }

        },

        //读取文件
        readFile: function () {
            var
                _this = this,
                //创建读取文件对象
                oReader = new FileReader();

            //预读取文件完成事件
            oReader.onload = function () {
                _this.createPrePic(this);
            };

            //将文件读取为Data URL形式
            oReader.readAsDataURL(this.oFile.files[0]);

        },

        //创建预览图片
        createPrePic: function (oReader) {
            var _this = this;
            //预览原图框top
            this.imgBoxTop = this.oriPicBox.offsetTop;
            //预览原图
            this.oriPic = document.createElement("img");
            //this.oriPic = new Image();
 
            //图片加载完成
            this.oriPic.onload = function () {
                //原始尺寸
                this.originW = this.width;
                this.originH = this.height;
             
                //判断图片是否ok
                var imgIsOk=_this.picSizeIsOk(this.width, this.height);

                if (!imgIsOk[0]) {
                    alert(imgIsOk[1]);
                    return false;
                } 


                //设置id
                this.setAttribute("id", _this.cutImgId);

                //重新选择图片时清空原图片
                _this.oriPicBox.innerHTML = '';

                //将图片载入到页面（预览）
                _this.oriPicBox.appendChild(this);
                //为了IE下不受宽高属性影响变形
                this.style.width = "100%";
                this.style.height = "auto";
                //调整图片大小
                //_this.setPrevPic(_this.a,_this.b);
              
                //图片加载完成之后执行事件
                _this.imgLoadFn();
                
            };

            //赋图片地址
            this.oriPic.src = oReader.result;

        },

        //判断图片是否符合大小
        picSizeIsOk: function (w, h) {
       
            if (w < this.cutSize || h < this.cutSize) {//太小
                return [0, '图片尺寸太小啦'];
            } else if (w / h < .5) {//太高
                return [0, '图片高比宽多太多啦，页面放不下。。'];
            } else if (w / h > 7.2) {//太宽
                return [0, '图片宽比高多太多啦，选区放不下。。'];
            } else {
                return [1];
            }
        },

        //调整预览图的大小
        setPrevPic: function () {
            var
                pic = this.oriPic,
                picBox=this.oriPicBox,
                picW = pic.originW,
                picH = pic.originH,
                boxW = picBox.offsetWidth,
                boxH = picBox.offsetHeight;

            //pic.setAttribute("width", '');
            //pic.setAttribute("height", '');

            //如果图片大于预览框
            //if (picW < boxW) {
            //    //picBox.style.width = picW+"px";
            //}

        },

        //图片加载完成后开始裁切图片
        imgLoadFn: function () {
          // $(this.uploadImgDiv).hide();
           $(this.editPortrait).show();
            var
                _this = this,
                //更新即时预览图
                updatePrev = function (s) {
                    if (s.w > 0 && s.h > 0) {
                        _this.getCutBoxAttr(s);
                        _this.cutPicToNewPic();
                    }
                };
            $.getScript("/static/cutpic/jquery.Jcrop.min.js", function () {
                $("#js_preCutImg").Jcrop({
                    //初始选区
                    setSelect: _this.setCutBoxInitPos(),
                    //选区宽高比
                    aspectRatio: 211/118,
                    //选区最小尺寸
                    minSize: [211, 118],
                    //选区变化 时
                    onChange: updatePrev
                });
                //裁切准备妥当
                _this.options.cutPicLoad && _this.options.cutPicLoad();
                _this.oCanvas.style.display = "block";
                document.getElementById("js_editPorSubmit").style.display = "inline-block";

            });
        },

        //设置裁切框初始位置
        setCutBoxInitPos: function () {
            var
                cutS=this.cutSize,
                picBox = this.oriPicBox,
                pW = picBox.offsetWidth,
                pH = picBox.offsetHeight,
                arrPos = [0, 0, cutS, cutS];

            if (pW > cutS) {
                var cL = (pW - cutS) / 2;
                var cT = (pH - cutS) / 4;
                arrPos[0] = cL;
                arrPos[1] = cT;
                arrPos[2] = cL + cutS;
                arrPos[3] = cT + cutS;
            }
            return arrPos;
        },


        //获取裁切框的位置及尺寸
        getCutBoxAttr: function (s) {
            var
            //裁切框属性
                cX = s.x,
                cY = s.y,
                cW = s.w,
                cH = s.h,

            //预览框属性
                picBox=this.oriPicBox,
                prevW = picBox.offsetWidth,
                prevH = picBox.offsetHeight,

            //原图属性
                pic=this.oriPic,
                picW = pic.originW,
                picH = pic.originH,

            //实际需要的属性
                trueX = cX / prevW * picW,
                trueY = cY / prevH * picH,
                trueW = cW / prevW * picW,
                trueH = cH / prevH * picH;

            return this.cutBoxSize={
                x: trueX,
                y: trueY,
                w: trueW,
                h: trueH
            };
        },

        //截图并绘制到新图中
        cutPicToNewPic: function () {
            var
                oSize = this.cutBoxSize,
                oCanvas = this.oCanvas,
                oCtx = oCanvas.getContext("2d");

            /*=================================
             pic:原图
             x:原图的x坐标
             y:原图的y坐标
             w:从xy确定的点开始，截取原图的宽度
             h:从xy确定的点开始，截取原图的高度

             x2:canvas画布上定义一个x坐标（用来作为截取的图的位置）
             y2:canvas画面上定义一个y坐标（用来作为截取的图的位置）
             w2:设定截取的图的宽度
             h2:设定截取的图的高度
             =================================*/
            var
                x = oSize.x,
                y = oSize.y,
                w = oSize.w,
                h = oSize.h,
                x2 = 0,
                y2 = 0,
                w2 =  this.cutSize,
                h2 = w2;

            if (w < w2) {
                w = w2;
            }
            if (h < h2) {
                h = h2;
            }
           // console.log(this.oriPic,x, y, w, h, x2, y2, w2, h2)
            //通过裁切绘制新图
          
            oCtx.drawImage(this.oriPic,x, y, w, h, x2, y2, 292, 163);
        },

        //创建二进制文件
        createBin: function () {
            //获取canvas的base64编码
            var DataObj = this.oCanvas.toDataURL();

            //获取base64编码中的内容体
            DataObj = DataObj.split(",")[1];

            //将base64编过码的内容进行解码
            DataObj = window.atob(DataObj);

            var
                buffer = new ArrayBuffer(DataObj.length),
                bytes = new DataView(buffer);

            for (var i = 0, n = DataObj.length; i < n; i++) {
                bytes.setUint8(i, DataObj.charCodeAt(i));
            }

            try {
                var blob = new Blob([bytes], { type: "image/png" });
            }
            catch (e) {
                window.BlobBuilder = window.BlobBuilder || window.WebKitBlobBuilder || window.MozBlobBuilder || window.MSBlobBuilder;
                if (e.name == 'TypeError' && window.BlobBuilder) {
                    var blb = new BlobBuilder();
                    blb.append([bytes.buffer]);
                    var blob = blb.getBlob("image/jpeg");
                }
                else if (e.name == "InvalidStateError") {
                    var blob = new Blob([bytes.buffer], { type: "image/png" });
                }
                else {
                    alert("浏览器不支持");
                }
            }


            var fd = new FormData();
            fd.append("imgFile", blob);

            return fd;
        }
    };
    window.CutPic = CutPic;
})(this,jQuery);