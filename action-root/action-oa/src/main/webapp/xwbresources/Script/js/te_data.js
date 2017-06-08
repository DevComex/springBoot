var tetopstr = ' <div class="navbar-header aside-md">\
                <a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen" data-target="#nav">\
                    <i class="fa fa-bars"></i>\
                </a>\
                <a href="#" class="navbar-brand" data-toggle="fullscreen">\
                    <img src="http://oa.gyyx.cn/Contents/images/gyyxlogo.png" class="m-r-sm logo">办公系统\
                </a>\
                <a class="btn btn-link visible-xs" data-toggle="dropdown" data-target=".nav-user">\
                    <i class="fa fa-cog"></i>\
                </a>\
            </div>\
            <ul class="nav navbar-nav hidden-xs">\
                <li class="dropdown" id="hiddenUserInfo">\
                    <a href="#" class="dropdown-toggle dker" data-toggle="dropdown">\
                        <i class="fa fa-building-o"></i>\
                        <span class="font-bold">个人信息</span>\
                    </a>\
                </li>\
            </ul>\
            <div class="flleft">\
                <a class="btn btn-rounded btn-sm btn-twitter" href="http://oa.gyyx.cn/InfoCenter/Index"><i class="fa fa-fw fa-envelope"></i>信息中心</a>\
                <a class="btn btn-rounded btn-sm btn-facebook" href="http://oa.gyyx.cn/UserQuickMenu/Index"><i class="fa fa-fw fa-book"></i>我的办公桌</a>\
                <a class="btn btn-rounded btn-sm btn-gplus" href="http://docscenter.oa.gyyx.cn/Document/DocCenter"><i class="fa fa-fw fa-folder"></i>文档中心</a>\
                <a class="btn btn-rounded btn-sm btn-info" href="http://oa.gyyx.cn/HelpCenter/FunctionIntroduction/InfoCenter.html" target="_blank"><i class="fa fa-fw fa-question"></i>帮助中心</a>\
            </div>\
            <ul class="nav navbar-nav navbar-right hidden-xs nav-user">\
                <li class="hidden-xs">\
                    <a data-toggle="dropdown" class="dropdown-toggle dk" href="#">\
                        <i class="fa fa-bell"></i>\
                        <span class="badge badge-sm up bg-danger m-l-n-sm count" style="display: inline-block;">0</span>\
                    </a>\
                    <section class="dropdown-menu aside-xl">\
                        <section class="panel bg-white">\
                            <header class="panel-heading b-light bg-light">\
                                <strong>你有<span class="count" style="display: inline;">0</span>条未读消息</strong>\
                            </header>\
                            <div class="list-group list-group-alt animated fadeInRight">\
                            </div>\
                            <footer class="panel-footer text-sm">\
                                <a href="http://oa.gyyx.cn/Webletter/List">查看所有消息</a>\
                            </footer>\
                        </section>\
                    </section>\
                </li>\
                <li class="dropdown">\
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" id="LoginNames">\
                        <span class="thumb-sm avatar pull-left">\
                            <img src="http://oa.gyyx.cn/Contents/images/avatar.jpg">\
                        </span>\
                    </a>\
                    <ul class="dropdown-menu animated fadeInRight">\
                        <span class="arrow top"></span>\
                        <li><a data-toggle="modal" href="#editpassword"><i class="fa fa-pencil"></i>&nbsp;&nbsp;修改密码</a></li>\
                        <li><a href="http://oa.gyyx.cn/signout/"><i class="fa fa-sign-out"></i>&nbsp;&nbsp;注销本次登陆</a></li>\
                    </ul>\
</li>\
</ul>';

var teleftstr = '<section class="vbox">\
        <header class="header bg-primary lter text-center clearfix">\
            <div class="btn-group">\
                <button title="New project" class="btn btn-sm btn-dark btn-icon" type="button">\
                    <i class="fa fa-plus"></i>\
                </button>\
                <div class="btn-group hidden-nav-xs">\
                    <button data-toggle="dropdown" class="btn btn-sm btn-primary dropdown-toggle" type="button">\
                        添加应用\
                        <span class="caret"></span>\
                    </button>\
                    <ul class="dropdown-menu text-left">\
                        <li><a href="#" class="cd">添加内部应用</a></li>\
                        <li><a href="#" class="cd">添加外部应用</a></li>\
                    </ul>\
                </div>\
            </div>\
        </header>\
        <section class="w-f scrollable">\
            <div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="5px" data-color="#333333">\
                <!-- 左侧大导航 begin -->\
                <nav class="nav-primary hidden-xs">\
                    <ul class="nav" id="AppLeft">\
                    </ul>\
                </nav>\
            </div>\
        </section>\
        <footer class="footer lt hidden-xs b-t b-dark">\
            <nav class=" on aside-md m-l-n dropup" id="chat">\
                <ul class="dropdown-menu aside-md panel-body" id="userNavList">\
                </ul>\
            </nav>\
            <a href="#nav" data-toggle="class:nav-xs" class="pull-right btn btn-sm btn-dark btn-icon navvs">\
                <i class="fa fa-angle-left text"></i>\
                <i class="fa fa-angle-right text-active"></i>\
            </a>\
            <div class="btn-group hidden-nav-xs">\
                <button type="button" title="Chats" class="btn  btn-dark" data-toggle="dropdown" data-target="#chat">\
                    <i class="fa fa-laptop"></i>\
外部系统\
</button>\
</div>\
</footer>\
</section>';
var terightstr = '<aside id="notes" class="bg-light lter b-l aside-md hide">\
                    <!--欢迎使用OA办公系统信件-->\
                    <section class="vbox">\
                        <div class="wrapper">\
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>欢迎使用</strong>新版光宇办公系统，改版的目的是让OA更人性化，使用更方便，逐步增加新的功能，在体验的过程中，期待您的意见反馈。<span class="text-muted text-xs">2014-4-17</span>&nbsp;&nbsp;<span class="badge bg-success">9:30</span><br />\
                            <br />\
                            <br />\
                            <p><strong>OA成长过程</strong></p>\
                            <section class="comment-list block">\
                                <article class="comment-item" id="comment-id-1">\
                                    <span class="fa-stack pull-left m-l-xs"><i class="fa fa-circle text-info fa-stack-2x"></i><i class="fa fa-comments-o text-white fa-stack-1x"></i></span>\
                                    <section class="comment-body m-b-lg">\
                                        <header><a href="#"><strong>探讨</strong></a><span class="text-muted text-xs">&nbsp;The first</span></header>\
                                        <div>提高用户体验</div>\
                                    </section>\
                                </article>\
                                <article class="comment-item" id="comment-id-2">\
                                    <span class="fa-stack pull-left m-l-xs"><i class="fa fa-circle text-danger fa-stack-2x"></i><i class="fa fa-file-o text-white fa-stack-1x"></i></span>\
                                    <section class="comment-body m-b-lg">\
                                        <header><a href="#"><strong>设计</strong></a><span class="text-muted text-xs">&nbsp;Second</span></header>\
                                        <div>优化框架结构</div>\
                                    </section>\
                                </article>\
                                <article class="comment-item" id="comment-id-3">\
                                    <span class="fa-stack pull-left m-l-xs"><i class="fa fa-circle text-primary fa-stack-2x"></i><i class="fa fa-lightbulb-o text-white fa-stack-1x"></i></span>\
                                    <section class="comment-body m-b-lg">\
                                        <header><a href="#"><strong>创新</strong></a><span class="text-muted text-xs">&nbsp;Third</span></header>\
                                        <div>突破思维领域</div>\
                                    </section>\
                                </article>\
                                <article class="comment-item" id="comment-id-4">\
                                    <span class="fa-stack pull-left m-l-xs"><i class="fa fa-circle text-success fa-stack-2x"></i><i class="fa fa-check text-white fa-stack-1x"></i></span>\
                                    <section class="comment-body m-b-lg">\
                                        <header><a href="#"><strong>完善</strong></a><span class="text-muted text-xs">&nbsp;Repeat</span></header>\
                                        <div>重复前三步</div>\
                                    </section>\
                                </article>\
                            </section>\
                        </div>\
                    </section>\
                </aside>';
var tepasswordstr = ' <!--修改密码 弹层信息-->\
    <div class="modal fade" id="editpassword">\
        <div class="modal-dialog">\
            <div class="modal-content">\
                <div class="modal-header">\
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>\
                    <h4 class="modal-title">修改密码</h4>\
                </div>\
                <p class="js_Message text-center m-t-sm m-b-none text-danger"></p>\
                <form action="/Credential/EditPassword" id="editpwd" name="editpwd" class="form-horizontal" method="POST">\
                    <div class="modal-body">\
                        <div class="form-group">\
                            <label for="input-id-1" class="col-sm-2 control-label">原密码:</label>\
                            <div class="col-sm-10">\
                                <input type="password" id="oldPassword" name="oldPassword" class="form-control required">\
                            </div>\
                        </div>\
                        <div class="form-group">\
                            <label for="input-id-1" class="col-sm-2 control-label">新密码:</label>\
                            <div class="col-sm-10">\
                                <input type="password" id="newPassword" name="newPassword" class="form-control required">\
                            </div>\
                        </div>\
                        <div class="form-group">\
                            <label for="input-id-1" class="col-sm-2 control-label">确认密码:</label>\
                            <div class="col-sm-10">\
                                <input type="password" id="confrimPassword" name="confrimPassword" class="form-control required">\
                            </div>\
                        </div>\
                    </div>\
                    <div class="modal-footer">\
                        <button type="button" class="btn btn-default close_js_Msg" data-dismiss="modal">返回</button>\
                        <button type="button" class="btn btn-info js_editPwdSubmit">确定</button><!--data-loading-text="提交中..."-->\
                    </div>\
                </form>\
            </div>\
        </div>\
    </div>';
var teheadstr = '<link rel="stylesheet" href="http://oa.gyyx.cn/Contents/css/app.v2.css" type="text/css" />\
    <link href="http://oa.gyyx.cn/Script/datatables/datatables.css" rel="stylesheet" />\
    <link rel="stylesheet" href="http://oa.gyyx.cn/Script/nestable/nestable.css" type="text/css" />\
    <!--[if lt IE 9]>   \
        <script src="http://oa.gyyx.cn/Script/ie/html5shiv.js"></script>\
        <script src="http://oa.gyyx.cn/Script/ie/respond.min.js"></script>\
        <script src="http://oa.gyyx.cn/Script/ie/excanvas.js"></script> \
        <link href="http://oa.gyyx.cn/Contents/css/media.css" rel="stylesheet" />\
    <![endif]-->\
    <script type="text/javascript" src="http://oa.gyyx.cn/Script/js/template.js"></script>';
