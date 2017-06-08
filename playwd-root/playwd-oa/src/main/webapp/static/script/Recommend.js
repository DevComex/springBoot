(function () {

    window.Recommend = {
        /*
         * 初始化推荐操作。
         */
        initRecommendOperations: function ($obj) {
            var $self = $obj;

            //初始化一个推荐操作集合对象。
            var operationCollection = new recommendOperationCollection($self);

            //寻找表格中每行带有[data-action]标记的操作按钮，
            //根据属性[data-action]设置其click事件为operationCollection的同名方法。
            $self.find('[data-action]').click(function () {
                var actionName = $(this).attr('data-action');

                if (operationCollection[actionName]) {
                    operationCollection[actionName]();
                }
            });
        },

        /*
         * 保存推荐。
         */
        saveRecommend: function () {
            alert(2);
            var data = getData();

            if (!checkData(data)) {
                return;
            }

            if (!!data.Id && data.Id > 0) {
                postTo('update', data);
            } else {
                postTo('add', data);
            }
        }
    };


    /*
     * 推荐操作集合对象（包含上移，下移等各种操作）。
     */
    function recommendOperationCollection(obj) {
        var _this = this;
        var id = obj.attr('data-id');

        /*
         * 上移。
         */
        _this.moveUp = function () {
            postIdTo('moveup');
        };

        /*
         * 下移。
         */
        _this.moveDown = function () {
            postIdTo('movedown');
        };

        /*
         * 展示。
         */
        _this.show = function () {
            showAndHide({code:id,isDisplay: true});
        };

        /*
         * 隐藏。
         */
        _this.hide = function () {
            showAndHide({code:id,isDisplay: false});
        };

        /*
         * 取消推荐。
         */
        _this.cancel = function () {
            if (confirm("确定取消推荐吗？")) {
                postIdTo('cancel');
            }
        };

        /*
         * 编辑。
         */
        _this.edit = function () {
            $(".imgEditDiv").html(imgDIV);
            Initialize();
            $.ajax({
                url: '/recommend/content/update',
                type: 'POST',
                cache: false,
                dataType: 'json',
                data: { code: id},
                success: function (result) {
                    if (result.Success) {
                        setData(result.Data);
                        $('#layer-save').show();
                    }
                    else {
                        alert('此条推荐已不存在，请刷新页面。');
                    }
                }
            });
        };

        /*
         * 只提交Id到后台指定的action。
         */
        function postIdTo(actionPartOfUrl) {
            postTo(actionPartOfUrl, { id: id });
        }
    }

    /*
     * 提交数据到后台指定的action。
     */
    function postTo(actionPartOfUrl, data) {
        alert(1);
        $.post('/recommend/content/' + actionPartOfUrl, data, function (result) {
            if (result.Success) {
                location.reload();
            }
            else {
                alert(result.Message);
            }
        });
    }
    /*
        展示与隐藏
     */
    function showAndHide(data){
        $.post("/recommend/content/show",data,function(d){
            if(d.isSuccess){
                alert(d.message);
                window.location.reload();
            }
            else{
                alert(d.message);
            }
        });
    }
    /*
     * 验证数据。
     */
    function checkData(data) {
        var flag = false;

        if (!/^.{1,100}$/.test(data.Title)) {
            alert('标题长度1-100。');
        } else if (!/^http:\/\/([\w-]+\.)+[\w-]+(\/[\w- ./?%&=]*)?$/.test(data.Url)|| 500 < data.Url.length) {
            alert('Url长度1-500，且必须符合url格式。');
        } else {
            flag = true;
        }

        return flag;
    }

    /*
     * 设置弹出层中的数据。
     */
    function setData(data) {
        $('#rcmd-id').val(data.Id);
        $('#rcmd-title').val(data.Title);
        $('#rcmd-url').val(data.Url);
        $('#rcmd-pic').attr('data-pic', data.Picture);
        $('#rcmd-pic').attr('data-thumbnail', data.Thumbnail);
        // var imglistoa = '<span style="float:left;margin:2px;padding:0 2px;background:#89cc97;color:#fff;cursor:pointer;"class="delimgs" codeimg="' + fileName + '" data-oldname = "' + fileOldName + '"><i class="fa fa-times delimg"></i>' + fileOldName + '</span>';
        // $("#divFileProgressContainer").html(imglistoa);
    }

    /*
     * 获取弹出层中的数据。
     */
    function getData() {

        //收集数据。
        var id = $('#rcmd-id').val();
        var title = $('#rcmd-title').val();
        var url = $('#rcmd-url').val();
        var pic = $('#rcmd-pic').attr('data-pic');
        var thumbnail = $('#rcmd-pic').attr('data-thumbnail');
        var locId = $('#js-locid').val();

        var data = {
            title: title,
            url: url,
            cover: pic,
            Thumbnail: thumbnail,
            LocationId: locId
        };

        if (!!id && parseInt(id) > 0) {
            data.Id = id;
        }

        return data;
    }



})();
