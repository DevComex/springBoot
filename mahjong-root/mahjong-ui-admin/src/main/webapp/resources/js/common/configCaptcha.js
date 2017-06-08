; (function () {
    function ConfigCaptcha(Defaults) {
        var simHtml = '<p class="item_p">' +  
                        '<label>验证码：</label>' +
                        '<span class="ipt_box">'+
                        '<input type="text" value="" name="js_configCaptchaValue" />' +
                        '</span>'+
                        '<img src="" class="js_configCaptchaImg js_refreshConfigCaptcha" />' +
                        '<a class="js_refreshConfigCaptcha">看不清？</a>' +
                    '</p>';
        var comHtml = '<div class="configCaptchaCom">'+   
                        '<p class="configCaptchaContainer_p01">'+
                            '<label class="configCaptcha_text">验证码：</label>'+
                            '<span class="configCaptchaInput_container">'+
                                '<em class="configCaptchaInput js_configCaptchaInput"><i class="configCaptchaImg js_configCaptchaImg"></i></em>' +
                                '<em class="configCaptchaInput js_configCaptchaInput"><i class="configCaptchaImg js_configCaptchaImg"></i></em>' +
                                '<em class="configCaptchaInput js_configCaptchaInput"><i class="configCaptchaImg js_configCaptchaImg"></i></em>' +
                                '<em class="configCaptchaInput js_configCaptchaInput"><i class="configCaptchaImg js_configCaptchaImg"></i></em>' +
                                '<em class="configCaptchaIcon configCaptchaInput configCaptcha_DeleteBtn js_deleteConfigCaptcha"></em>' +
                            '</span>'+
                            '<i class="configCaptchaIcon configCaptcha_Tip02 js_checkChinaCaptchaTip default"></i>'+
                        '</p>'+
                        '<p class="configCaptchaContainer_p02">'+
                            '<span class="configCaptchaImg js_configCaptchaImg configCaptchaImg_yes js_refreshConfigCaptcha"></span>' +
                            '<a class="configCaptchaChange js_refreshConfigCaptcha">看不清？</a>' +
                        '</p>'+
                        '<p class="configCaptchaContainer_p03">点击框内文字输入上图中<b class="configCaptcha_Tip03">汉字</b>相应文字</p>'+
                        '<div class="configCaptchaContainer_p04">'+
                            '<ul class="configCaptchaSelectCon js_ChinaCaptchaSelect">'+
                                '<li class="configCaptchaImg js_configCaptchaImg configCaptchaImg_0 js_captchaCaptchaSelect_img" data-code="0"></li>' +
                                '<li class="configCaptchaImg js_configCaptchaImg configCaptchaImg_1 js_captchaCaptchaSelect_img" data-code="1"></li>' +
                                '<li class="configCaptchaImg js_configCaptchaImg configCaptchaImg_2 js_captchaCaptchaSelect_img" data-code="2"></li>' +
                                '<li class="configCaptchaImg js_configCaptchaImg configCaptchaImg_3 js_captchaCaptchaSelect_img" data-code="3"></li>' +
                                '<li class="configCaptchaImg js_configCaptchaImg configCaptchaImg_4 js_captchaCaptchaSelect_img" data-code="4"></li>' +
                                '<li class="configCaptchaImg js_configCaptchaImg configCaptchaImg_5 js_captchaCaptchaSelect_img" data-code="5"></li>' +
                                '<li class="configCaptchaImg js_configCaptchaImg configCaptchaImg_6 js_captchaCaptchaSelect_img" data-code="6"></li>' +
                                '<li class="configCaptchaImg js_configCaptchaImg configCaptchaImg_7 js_captchaCaptchaSelect_img" data-code="7"></li>' +
                                '<li class="configCaptchaImg js_configCaptchaImg configCaptchaImg_8 js_captchaCaptchaSelect_img" data-code="8"></li>' +
                            '</ul>'+
                        '</div>'+
                    '</div> ';

        this.Defaults = {
            configCatpchaTypeUrl: "http://account.gyyx.cn/captcha/CorsNeedCaptcha_New", 
            createCaptchaUrl: "http://account.gyyx.cn/captcha/create", 
            cssLinkSrc: "/static/js/common/chinaCaptcha/configCaptcha.min.css", 
            data: {      
                bid: "Vaqrk"  
                
            },
            simHtml: simHtml, 
            comHtml: comHtml, 
            inputName: "js_configCaptchaValue", 
            popDefaultTip: "请输入验证码完成登录", 
            popErrorTip: "验证码输入错误", 
            popWidth: "340px", 
            popHeight: "430px", 
            flexStatus: false, 
            comIn: false,       
            typeIsOnlyCom:false, 
            captchaInWrap: "#configCaptchaWrap", 
            subFn: function () { },        
            closeFn: function () { }, 
            errorTipFn: function (wrap) {  
                wrap.find(".js_checkChinaCaptchaTip").addClass("error");
            },
            succTipFn: function (wrap) {   
                wrap.find(".js_checkChinaCaptchaTip").removeClass("error").addClass("success");
            },
            initError: function () {   
                alert("验证码初始化失败！");
            }
        };

        
        var isIE6 = /msie 6/i.test(navigator.userAgent);
        if (isIE6) {
            try { document.execCommand('BackgroundImageCache', false, true); } catch (e) { }
        }
        
        
        if (Defaults && Defaults.data && !Defaults.data.bid) {

            $.extend(Defaults.data, this.Defaults.data);  
        }

        $.extend(this.Defaults, Defaults);  

    }

    ConfigCaptcha.prototype = {

        
        init: function (confData) {

            this.isNeedCaptcha(confData);

        },

        
        isNeedCaptcha: function (confData) {
            var _this = this;

            var datas = {
                r: Math.random()
            };
            $.extend(datas, _this.Defaults.data);  
            if (confData && confData instanceof Object) {
                $.extend(datas, confData); 
            }
            

            var configCaptchaWrap = $(_this.Defaults.captchaInWrap); 

            if (_this.Defaults.typeIsOnlyCom) { 
               
                _this.captcahSwitchOpen = 1;

                _this.comCaptchaInitFn(configCaptchaWrap); 

            } else {                    
                $.ajax({
                    url: this.Defaults.configCatpchaTypeUrl,
                    type: "GET",
                    dataType: "json",
                    data: datas,
                    success: function (data) {
                        if (toString(data)) {
                            
                            _this.captcahSwitchOpen = parseInt(data);
                            switch (parseInt(data)) {
                                case -1:
                                    configCaptchaWrap.html('<input type="hidden" name="js_configCaptchaValue"/>');  
                                    break;
                                case 0:
                                    _this.insertCaptchaHtmlFn(configCaptchaWrap, _this.Defaults.simHtml); 

                                    var srcs = _this.Defaults.createCaptchaUrl + "?bid=" + _this.Defaults.data.bid;  
                                    configCaptchaWrap.find(".js_configCaptchaImg").attr("src", srcs);

                                    _this.refreshCaptchaEvent(configCaptchaWrap);

                                    break;
                                case 1:

                                    _this.comCaptchaInitFn(configCaptchaWrap); 

                                    break;
                                default:
                                    _this.initError();
                                    break;
                            }
                            _this.replaceInputNameFn();  

                        }
                    },
                    error: function () {
                        _this.initError();
                    }
                });
            }
        },
       
        comCaptchaInitFn: function (wrap) {
            var _this = this;
          
            if (!document.getElementById("configCaptchaLink")) {  

                $("head").append('<link href="' + _this.Defaults.cssLinkSrc + '" rel="stylesheet" type="text/css" id="configCaptchaLink">'); //引入样式
            }

            if (_this.Defaults.comIn) {  

                _this.insertCaptchaHtmlFn(wrap, _this.Defaults.comHtml); 
                wrap.append('<input type="hidden" name="js_configCaptchaValue"/>');
                _this.addEventForCaptcha();

            } else {
                wrap.html('<input type="hidden" name="js_configCaptchaValue"/>');  
                _this.createCaptchaPop();
            }
        },
       
        insertCaptchaHtmlFn: function (wrap, innerHtml) {
            var _this = this;
            if (typeof innerHtml === "string") {
                wrap.html(innerHtml); 
            } else if (innerHtml instanceof $) {
                innerHtml = innerHtml.clone().removeAttr("id").show(); 
                wrap.html(innerHtml);
            } else {
                alert("传入的验证码html元素无效");
            }
        },
        
        replaceInputNameFn: function () {
            var _this = this;
            if (_this.Defaults.inputName) {
                $(_this.Defaults.captchaInWrap).find("input[name='js_configCaptchaValue']").attr("name", _this.Defaults.inputName);
            } else {
                _this.Defaults.inputName = "js_configCaptchaValue";
            }
            
        },
        
        createCaptchaPop: function () {
            var _this = this;
            var popSizeUnit;
           

            var outWrap = document.createElement("div");
            var wrap = document.createElement("div");

            var title = document.createElement("h3");
            var close = document.createElement("a");

            var tip = document.createElement("p");
            var callBackErrorTip = document.createElement("p");

            var pop = document.createElement("div");

            var btnWrap = document.createElement("p");
            var btn = document.createElement("span");

            var mask = document.createElement("div");

           
            outWrap.className = "configCaptchaPopOutWrap js_configCaptchaPop";
            wrap.className = "configCaptchaPopWrap";

            title.className = "configCaptcha_Title";
            close.className = "configCaptchaIcon configCaptcha_close js_configCaptcha_close";

            tip.className = "configCaptcha_Tip01 js_defaultTextTips";
            callBackErrorTip.className = "configCaptcha_Tip01_error js_errorTextTips";

            pop.className = "configCaptchaPop js_configCaptchaPopIn";

            btnWrap.className = "configCaptchaContainer_p05";
            btn.className = "configCaptchaSubmit_Btn js_chinaCaptchaBtn";

            mask.className = "configCaptchaMask";

           
            if (_this.Defaults.flexStatus) {
                popSizeUnit = "rem";
            } else {
                popSizeUnit = "px";
            }
            wrap.style.width = parseFloat(_this.Defaults.popWidth) + popSizeUnit;
            wrap.style.height = parseFloat(_this.Defaults.popHeight) + popSizeUnit;

            wrap.style.marginLeft = -parseFloat(_this.Defaults.popWidth) / 2 + popSizeUnit;
            wrap.style.marginTop = -parseFloat(_this.Defaults.popHeight) / 2 + popSizeUnit;

            
            title.innerHTML = "输入验证码";
            title.appendChild(close);

            tip.innerHTML = "";
            callBackErrorTip.innerHTML = "";

            btn.innerHTML = "确定";
            btnWrap.appendChild(btn);

            wrap.appendChild(title);
            wrap.appendChild(tip);
            wrap.appendChild(callBackErrorTip);
            wrap.appendChild(pop);
            wrap.appendChild(btnWrap);

            outWrap.appendChild(wrap);
            outWrap.appendChild(mask);

            outWrap.style.display = "none";

            
            close.onclick = function () {
                outWrap.style.display = "none";
            };

            
            var outWrapExistLen = $(".js_configCaptchaPop").length;
            if (outWrapExistLen === 0) {
                document.body.appendChild(outWrap);
            }

        },
       
        openCaptchaPop: function (subfn, closefn) {   
            var _this = this;
            _this.insertCaptchaHtmlFn($(".js_configCaptchaPop").find(".js_configCaptchaPopIn"), _this.Defaults.comHtml);
            _this.refreshCaptcha($(".js_configCaptchaPop"));
            _this.callbackCaptchaErrorFn(false); 
            $(".js_configCaptchaPop").show();
          
            _this.addEventForCaptcha();
           
            if (typeof subfn === "function") {
                _this.popSubmit(subfn);
            } else {
                _this.popSubmit(_this.Defaults.subFn);
            }
           
            if (typeof closefn === "function") {
                _this.closeCaptchaPopFn(closefn);
            } else {
                _this.closeCaptchaPopFn(_this.Defaults.closeFn);
            }

        },
      
        closeCaptchaPop: function () {
            $(".js_configCaptchaPop").hide();
        },
       
        closeCaptchaPopFn: function (fn) {
            $(".js_configCaptcha_close").unbind("click").bind("click", function () {
                $(".js_configCaptchaPop").hide();
                if (typeof fn === "function") {
                    fn();
                }
            });
        },
       
        popSubmit: function (fn) {
            var _this = this;
           
            $(".js_chinaCaptchaBtn").unbind("click").bind("click", function () {
                if (_this.configCaptchaCheck($(_this.Defaults.captchaInWrap).find("input[name='" + _this.Defaults.inputName + "']"), $(".js_configCaptchaPop"))) {
                    fn(_this.Defaults);
                }
                return false;
            });
        },
     
        addEventForCaptcha: function () {
            var _this = this;
            var selectCaptchaWrapObj = null; 
            
            if (_this.Defaults.comIn) {

                selectCaptchaWrapObj = $(_this.Defaults.captchaInWrap);

                _this.refreshCaptcha(selectCaptchaWrapObj); 

            } else {

                selectCaptchaWrapObj = $(".js_configCaptchaPop");

            }
            

            selectCaptchaWrapObj.find(".js_captchaCaptchaSelect_img").unbind("click").bind("click", function () {
                _this.selectCaptcha($(this), selectCaptchaWrapObj);
                return false;
            });
          
            selectCaptchaWrapObj.find(".js_deleteConfigCaptcha").unbind("click").bind("click", function () {
                _this.deleteCaptcha(selectCaptchaWrapObj);
                return false;
            });

            
            _this.refreshCaptchaEvent(selectCaptchaWrapObj);

        },
        
        selectCaptcha: function ($obj, wrap) {
            var _this = this;
            var
                    obj = $obj,
                    objAttrCode = obj.attr("data-code"),
                    codeLen = parseInt($(_this.Defaults.captchaInWrap).find("input[name='" + _this.Defaults.inputName + "']").val().length),
                    checkcodeStr = $(_this.Defaults.captchaInWrap).find("input[name='" + _this.Defaults.inputName + "']").val() + objAttrCode;

            if (codeLen < 4) {

             
                $(_this.Defaults.captchaInWrap).find("input[name='" + _this.Defaults.inputName + "']").val(checkcodeStr);
                wrap.find(".js_configCaptchaInput").eq(codeLen).find("i").addClass("configCaptchaImg_" + objAttrCode);

            }
            this.configCaptchaCheck($(_this.Defaults.captchaInWrap).find("input[name='" + _this.Defaults.inputName + "']"), wrap);
        },

     
        deleteCaptcha: function (wrap) {
            var _this = this;
            var
                    Len = parseInt($(_this.Defaults.captchaInWrap).find("input[name='" + _this.Defaults.inputName + "']").val().length),
                    checkcodeStr1 = $(_this.Defaults.captchaInWrap).find("input[name='" + _this.Defaults.inputName + "']").val();

            checkcodeStr1 = checkcodeStr1.substring(0, Len - 1);
            $(_this.Defaults.captchaInWrap).find("input[name='" + _this.Defaults.inputName + "']").val(checkcodeStr1);


            var deleteObj = wrap.find(".js_configCaptchaInput").eq(Len - 1).find("i");
            var classNames = deleteObj[0].className;
            classNames = classNames.replace(/configCaptchaImg_[0-8]/g, "");
            deleteObj[0].className = classNames;


            this.configCaptchaCheck($(_this.Defaults.captchaInWrap).find("input[name='" + _this.Defaults.inputName + "']"), wrap);
        },
      
        refreshCaptchaEvent: function (wrap) {
            var _this = this;
            wrap.find(".js_refreshConfigCaptcha").unbind("click").bind("click", function () {
                _this.refreshCaptcha(wrap);
                return false;
            });
        },
      
        refreshCaptcha: function (wrap) {
            var _this = this;

            switch (_this.captcahSwitchOpen) {
                case 0:
                   
                    wrap.find(".js_configCaptchaImg").attr("src", _this.Defaults.createCaptchaUrl + "?bid=" + _this.Defaults.data.bid + "&r=" + Math.random());

                    break;
                case 1:
                  
                    wrap.find(".js_configCaptchaImg").css("backgroundImage", "url(" + _this.Defaults.createCaptchaUrl + "?bid=" + _this.Defaults.data.bid + "&r=" + Math.random() + ")");

                    wrap.find(".js_configCaptchaInput i").each(function () {
                        var $this = $(this);
                        var classNames = $this[0].className;
                        classNames = classNames.replace(/configCaptchaImg_[0-8]/g, "");
                        $this[0].className = classNames;
                    });

                    $(_this.Defaults.captchaInWrap).find("input[name='" + _this.Defaults.inputName + "']").val(""); 
                    
                    wrap.find(".js_checkChinaCaptchaTip").removeClass("success error"); 

                    break;
                default:
                    break;
            }

        },
       
        callbackCaptchaErrorFn: function (status) {
            var _this = this;
            if (status) {
                $(".js_errorTextTips").show().html(_this.Defaults.popErrorTip);
                $(".js_defaultTextTips").hide();
            } else {
                $(".js_errorTextTips").hide();
                $(".js_defaultTextTips").show().html(_this.Defaults.popDefaultTip);
            }
            
        },

    
        configCaptchaCheck: function (obj, wrap) {
            var _this = this;
            if (_this.VerCheckConfigCaptcha(obj, wrap) === true) {
                _this.VerConfigCaptchaOK(obj, wrap);
                return true;
            } else {
                return false;
            }
        },

     
        VerCheckConfigCaptcha: function (obj, wrap) {
            var _this = this;
            var exp = new RegExp("^\\d{4}$");
            if (!exp.test(obj.val())) {
                _this.Defaults.errorTipFn(wrap);
                return false;
            } else {
                return true;
            }
        },

      
        VerConfigCaptchaOK: function (obj, wrap) {
            var _this = this;
            _this.Defaults.succTipFn(wrap);
            return true;
        }
    };

    window.configCaptcha = function(obj){
        return new ConfigCaptcha(obj);
    };
})();