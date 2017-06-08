function fileQueueError(file, errorCode, message) {
    try {
        var imageName = "error.gif";
        var errorName = "";
        if (errorCode === SWFUpload.errorCode_QUEUE_LIMIT_EXCEEDED) {
            errorName = "You have attempted to queue too many files.";
        }

        if (errorName !== "") {
            alert(errorName);
            return;
        }

         switch (errorCode) {
            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
                alert("该文件0字节，不能上传");
                break;
            case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
                alert("您选择的文件的大小已超出最大限制");
                break;
             case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
                 alert("文件格式不正确");
                 break;
             case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
                 alert("每次最多只能上传1张图片！");

                 break;
            default:
                alert(message);
                break;
        }

        addImage("../images/" + imageName);

    } catch (ex) {
        this.debug(ex);
    }

}

function fileDialogComplete(numFilesSelected, numFilesQueued) {
    try {
        if (numFilesQueued > 0) {
            this.startUpload();
        }
    } catch (ex) {
        this.debug(ex);
    }
}

function uploadProgress(file, bytesLoaded) {
    try {
        var percent = Math.ceil((bytesLoaded / file.size) * 100);

        var progress = new FileProgress(file, this.customSettings.upload_target);
        progress.setProgress(percent);
        if (percent === 100) {
            progress.setStatus("Creating thumbnail...");
            progress.toggleCancel(false, this);
        } else {
            progress.setStatus("Uploading...");
            progress.toggleCancel(true, this);
        }
    } catch (ex) {
        this.debug(ex);
    }
}

function uploadSuccess(file, serverData) {
    try {

        serverData = eval('(' + serverData + ')');
        //var valArray = serverData.toString().split("$");

        var fileName = serverData.Url.toString();
var imgw = serverData.Width.toString();
var imgh = serverData.Height.toString();

var code=serverData.Code.toString();



        var fileOldName = file.name;


        var bigFileDiv = document.getElementById(this.customSettings.save_element);
        var oldFileName = document.getElementById("OldFileName");


        //if ($(".js_showImg").length == 0) {
        //    var imglistoa = '<img src="' + fileName + '" style="width:160px;height:224px;" class="js_showImg"  />';
        //    $(".defaultImg").hide();
        //    $(".progressName").append(imglistoa);
        //} else {
        //    $(".js_showImg").attr("src", fileName);
        //}

        var strhtml = '<div class="js_showImg "><img id="js_uploadimg"  data-width="' + imgw + '"  data-height="' + imgh + '"  data-code="' + code+ '" src="' + fileName + '" style="display: inline-block;" ><input type="hidden" name="tempUrl" value="' + fileName + '" /><input type="hidden" name="imgFeature" value="' + code + '" /><input type="hidden" name="imgWidth" value="' + imgw + '" /><input type="hidden" name="imgHeight" value="' + imgh + '" /></div>';
        $("#divFileProgressContainer").html(strhtml);
        $("input[name='imageUrl']").val(fileName);
        $(".upl").hide();

        bigFileDiv.value = fileName;
        oldFileName.value = fileOldName;

        var progress = new FileProgress(file, this.customSettings.upload_target);

        $(".js_delpic").click(function () {
            $(this).parents(".js_showImg").remove();
            //var stats = swfu.getStats();
            //stats.successful_uploads--;
            //swfu.setStats(stats);
        })

        progress.setStatus("Thumbnail Created.");
        progress.toggleCancel(false);


        //������ʾ��Ϣ
        document.getElementById("divphoto").style.display = "none";
    } catch (ex) {
        this.debug(ex);
    }
}

function uploadComplete(file) {
    try {
        /*  I want the next upload to continue automatically so I'll call startUpload here */
        if (this.getStats().files_queued > 0) {
            this.startUpload();
        } else {
            var progress = new FileProgress(file, this.customSettings.upload_target);
            //alert(file.name);
            //$(".progressName").text($(".progressName").text() + file.name+" ");
            progress.setComplete();
            progress.setStatus("All images received.");
            progress.toggleCancel(false);
        }
    } catch (ex) {
        this.debug(ex);
    }
}

function uploadError(file, errorCode, message) {
    var imageName = "error.gif";
    var progress;
    try {
        switch (errorCode) {
            case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
                try {
                    progress = new FileProgress(file, this.customSettings.upload_target);
                    progress.setCancelled();
                    progress.setStatus("Cancelled");
                    progress.toggleCancel(false);
                }
                catch (ex1) {
                    this.debug(ex1);
                }
                break;
            case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
                try {
                    progress = new FileProgress(file, this.customSettings.upload_target);
                    progress.setCancelled();
                    progress.setStatus("Stopped");
                    progress.toggleCancel(true);
                }
                catch (ex2) {
                    this.debug(ex2);
                }
            case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
                imageName = "uploadlimit.gif";
                break;
            default:
                alert(message);
                break;
        }

        addImage("images/" + imageName);

    } catch (ex3) {
        this.debug(ex3);
    }

}


function addImage(src) {
    var newImg = document.createElement("img");
    newImg.style.margin = "5px";

    document.getElementById("thumbnails").appendChild(newImg);
    if (newImg.filters) {
        try {
            newImg.filters.item("DXImageTransform.Microsoft.Alpha").opacity = 0;
        } catch (e) {
            // If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
            newImg.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + 0 + ')';
        }
    } else {
        newImg.style.opacity = 0;
    }

    newImg.onload = function() {
        fadeIn(newImg, 0);
    };
    newImg.src = src;
}

function fadeIn(element, opacity) {
    var reduceOpacityBy = 5;
    var rate = 30; // 15 fps


    if (opacity < 100) {
        opacity += reduceOpacityBy;
        if (opacity > 100) {
            opacity = 100;
        }

        if (element.filters) {
            try {
                element.filters.item("DXImageTransform.Microsoft.Alpha").opacity = opacity;
            } catch (e) {
                // If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
                element.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + opacity + ')';
            }
        } else {
            element.style.opacity = opacity / 100;
        }
    }

    if (opacity < 100) {
        setTimeout(function() {
            fadeIn(element, opacity);
        }, rate);
    }
}



/* ******************************************
*	FileProgress Object
*	Control object for displaying file info
* ****************************************** */
var fname = [];
function FileProgress(file, targetID) {
    this.fileProgressID = "divFileProgress";

    this.fileProgressWrapper = document.getElementById(this.fileProgressID);
    if (!this.fileProgressWrapper) {
        this.fileProgressWrapper = document.createElement("div");
        this.fileProgressWrapper.className = "progressWrapper";
        this.fileProgressWrapper.id = this.fileProgressID;

        var progressText = document.createElement("div");
        progressText.className = "progressName";

        this.fileProgressWrapper.appendChild(progressText);

        document.getElementById(targetID).appendChild(this.fileProgressWrapper);
        fadeIn(this.fileProgressWrapper, 0);

    }

    this.height = this.fileProgressWrapper.offsetHeight;
    var imgname = file.name;
}
