<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>无标题文档</title>
	<link type="text/css" href="http://static.cn114.cn/action/wdalone/css/wd151030.css" rel="stylesheet" />
	<link type="text/css" href="http://static.cn114.cn/action/wdalone/css/jquery-ui-1.8.13.custom.css" />
	<script src="${rc.contextPath}/commonSupporter/actionUtil?actionCode=283"></script>
	<script type="text/javascript" src="http://s.gyyx.cn/lib/jquery.js"></script>
	<script type="text/javascript" src="http://s.gyyx.cn/publictop/jquery.publictop.js?webname=action"></script>
	<script type="text/javascript" src="http://static.cn114.cn/action/wdalone/js/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript" src="http://static.cn114.cn/action/wdalone/js/Lottery.js"></script>
	<script type="text/javascript" src="http://static.cn114.cn/action/wdalone/js/handle.js"></script>
	<script type="text/javascript">
		console.info("\u65f6\u95f4\u592a\u7d27\uff0c\u4ee3\u7801\u5199\u7684\u60f3\u9a82\u8857");
		var len = 0, anwer = "", question = [
			{ title: "如果你身边有单身的朋友，你会怎么为他过光棍节呢？", a: "陪Ta一起过节", b: "给Ta介绍男/女朋友", c: "送Ta袋狗粮", d: "我就是单身狗" },
			{ title: "你觉得自己是因为什么原因才一直单身的？", a: "没钱", b: "太丑", c: "没钱+太丑", d: "想追的太多，选择障碍" },
			{ title: "一个妹子独自伤心，而她的周围只有你的时候……", a: "路过，我又不认识她", b: "上前安慰", c: "长得好去搭讪，长得不好", d: "等她哭的时候，拿出纸巾，然后，卖给她" },
			{ title: "如果你可以用身上10斤换100万美元、或者变帅（变美）、或者变高智商脑暴天才，你会选择哪个？", a: "100万美元", b: "帅（美）", c: "高智商脑暴天才", d: "我用30斤都换" },
			{ title: "如果一个异性约你一起过光棍节，你会……", a: "一男一女还过什么光棍节，Rujia走起。", b: "同意，嘻嘻哈哈过一天，没有然后。", c: "没工夫，窝在家里玩游戏。", d: "诶！会有异性主动找我说话么？" }
		];

		//问答功能实现
		function q(){
			var o = $(".part01_cont"), next_btn = $("#js_question_btn"), q_str = len < question.length ? question[len] : 0;
			var checkli = function(){
				if(o.find("li.on").length > 0) return o.find("li.on").find("a").attr("rel");
				else return;
			}, info = checkli();
			if(q_str){
				var i = 0;
				if(info) anwer += info;
				else if(len > 0) { console.info("没答题"); return }
				if(len > 0) {
					o.find("p").eq(0).empty();
					o.find("ul li").removeClass("on").empty();
				}
				for(var c in q_str){
					if(c === "title") o.find("p").eq(0).append(q_str[c]);
					else {
						//为动态创建的LI绑定点击事件
						var Upc = c.toUpperCase();
						o.find("ul li").eq(i).append(Upc + " . " + q_str[c] + "<a rel='" + Upc + "'>" + Upc + "</a>").click( function(){
							$("ul li").removeClass("on");
							$(this).addClass("on");
						});
						i++;
					}
				}
				len++;
			}else{
				if(info) { anwer += info; next_btn.unbind("click"); }
				else { console.info("没答题"); return }
				console.info(anwer);
			}
		}

		$(function () {
			var btn = document.getElementById("login_btn");
			//alertFn(null, null, true, true, callback);
			//btn.addEventListener("click", function(){ alertFn(null,null,true);console.log(2); }, false);
			function callback() {
				console.log("1");
				//btn.addEventListener("click", function(){ return; }, false);
			}
			$("#js_question_btn").bind("click", function () { q(); });
			q();//调用问答功能
		});
		



		
		
		
		
	</script>

</head>

<body>
<div class="wd_wrap">
	<div class="main">
		<div class="wd_header">
			<div class="wd_nav"><a href="http://wd.gyyx.cn">进入官网</a>|<a href="http://pay.gyyx.cn">充值中心</a>|<a href="http://account.gyyx.cn/Member/Register">账号注册</a>|<a href="http://wd.gyyx.cn/download/">下载中心</a></div>
			<span class="wd_time">11月10日~11月17日</span>
		</div>
		<div class="wd_d01 clear">
			<div class="wd_video"><embed src="http://player.youku.com/player.php/sid/XMTM4MTUwNDc0NA==/v.swf" allowFullScreen="true" quality="high" width="640" height="336" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash"></embed></div><!--视频-->
			<div class="wd_info">
				<!--登录、注册-->
				<div class="logn_tab">
					<ul class="logn_menu clear">
						<li class="on"><a href="javascript:;">登录</a></li>
						<li><a href="http://account.gyyx.cn/Member/Register">快速注册</a></li>
					</ul>
					<div class="logn_tablist">
						<!--登录-->
						<div class="logn_cont">
							<!--<p><input class="inp01" type="text" value="请输入邮箱或者手机号" /></p>
							<p><input class="inp02" type="text" value="请输入密码" /></p>-->
							<p class="logn_txt">一年一度的光棍节又到啦！回答5道关于“单身”的问题，就可参加中奖率100%的抽奖活动。还不快快登陆来答题！</p>
							<p><input id="login_btn" class="sub" type="button" value="立即登录" /></p>
						</div>
						<!--登录 end-->
					</div>
				</div>
				<!--登录、注册 end-->
				<!--登录后-->
				<div class="after_logn"  style="display:none;">
					<h2>cheese****，欢迎您</h2>
					<div class="after01" style="display:none;"><!--未参与活动-->
						<p class="af01_p01">您还有 <span>0</span> 次抽奖机会</p>
						<p class="af01_p02">参与回答问题后可获得抽奖资格！</p>
					</div><!--未参与活动 end-->
					<div class="after01" style="display:none;"><!--参与问答未抽奖-->
						<p class="af01_p01">您还有 <span>2</span> 次抽奖机会</p>
					</div><!--参与问答未抽奖 end-->
					<div class="after01" style="display:none;"><!--已参与抽奖-->
						<p class="af01_p02">您所获的奖品为</p>
						<p class="af01_p03">水女人偶，10元光宇币</p>
					</div><!--已参与抽奖 end-->
				</div>
				<!--登录后 end-->
			</div>
		</div>
		<div class="wd_part01">
			<h2 class="wd_h2"><strong>趣味</strong>问答</h2>
			<div class="part01_cont">
				<h3>第一题</h3>
				<p></p>
				<ul class="wd_ans clear">
					<li></li>
					<li></li>
					<li></li>
					<li></li>
				</ul>
				<p class="part01_next"><a href="javascript:;" id="js_question_btn">NEXT</a></p>
			</div>
		</div>
		<div class="wd_part02">
			<h2 class="wd_h2"><strong>答题</strong>抽奖</h2>
			<div class="wd_cj">
				<ul class="cj_list01 clear">
					<li class="js_cj cj0 cj0_0" id="cj0"></li>
					<li class="js_cj cj1" id="cj1"></li>
					<li class="js_cj cj2" id="cj2"></li>
					<li class="js_cj cj3" id="cj3"></li>
				</ul>
				<ul class="cj_list02 clear">
					<li class="js_cj cj4" id="cj9"></li>
					<li class="cj_start"><a href="javascript:;" class="js_btnStart" text="go"></a></li>
					<li class="js_cj cj5" id="cj4"></li>
				</ul>
				<ul class="cj_list01 clear">
					<li class="js_cj cj6" id="cj8"></li>
					<li class="js_cj cj7" id="cj7"></li>
					<li class="js_cj cj8" id="cj6"></li>
					<li class="js_cj cj9" id="cj5"></li>
				</ul>
			</div>
		</div>
		<div class="wd_part03">
			<h2 class="wd_h2"><strong>活动</strong>说明</h2>
			<div class="part03_txt">
				<p><span>1</span>活动时间为11月10日-11月17日</p>
				<p><span>2</span>用户登陆光宇社区账号后，回答问题即可参与抽奖活动</p>
				<p><span>3</span>参与抽奖100%获得奖品</p>
				<p><span>4</span>活动期间，每个光宇帐号最多获得1次抽奖机会<br />实物活动奖品，奖品邮寄地址一经提交后不可修改。实物奖品将由光宇游戏在活动结束后30天以内统一安</p>
				<p><span>5</span>排寄送，为了确保您的获奖资格，请务必填写正确的个人信息，以免影响发奖</p>
				<p><span>6</span>本次活动的解释权归光宇游戏共同所有</p>
			</div>
		</div>
	</div>
</div>
<div class="public_nav_bottom"><script type="text/javascript">ShowBottom();</script></div>


<!--tanceng-->
<div id="js_dialogbox" class="wd_tc wd_tc01">
	<a class="js_close close" href="#"></a>
	<h2>答完题啦，快去<span>抽奖</span>吧！</h2>
</div>
<div class="wd_tc wd_tc02">
	<a class="close" href="#"></a>
	<h2>恭喜您获得 <span>10元光宇币</span></h2>
	<p>序列号为：<em>DHUIA462482327489743546</em></p>
	<p>密码：<em>558efd</em></p>
</div>
<div class="wd_tc wd_tc03">
	<a class="close" href="#"></a>
	<h2>恭喜您获得 <span>新手礼包</span></h2>
	<p>2499新手礼包：<em>44332249e981</em></p>
	<p class="tc_btn"><input type="button" value="激活新手礼包" /></p>
</div>
<div class="wd_tc wd_tc04">
	<a class="close" href="#"></a>
	<h2>恭喜您获得 <span>卡西欧自拍神器</span></h2>
	<div class="tc_cont04">
		<p><label>姓名</label><input type="text" /></p>
		<p><label>电话</label><input type="text" /></p>
		<p><label>邮编</label><input type="text" /></p>
		<p><label>邮寄地址</label><textarea></textarea></p>
		<p class="tc_btn tc04_btn"><input type="submit" value="确认领取" /></p>
	</div>
</div>

</body>
</html>
