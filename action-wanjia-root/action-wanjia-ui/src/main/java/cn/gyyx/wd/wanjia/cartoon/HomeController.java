package cn.gyyx.wd.wanjia.cartoon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gyyx.wd.wanjia.cartoon.service.TestService;

@Controller
public class HomeController {

	@Autowired
	private TestService testService;

	// 上传页
	@RequestMapping("/upload")
	public String void11() {
		return "upload";
	}

	// 列表页
	@RequestMapping("/home")
	public String void1() {
		return "list";
	}

	// 目录页
	@RequestMapping("/catalog")
	public String void2() {
		return "catalog";
	}

	// 目浏览
	@RequestMapping("/browse")
	public String void3() {
		return "browse";
	}

	// 编辑回复-漫画
	@RequestMapping("/userCenter/editorAnswer")
	public String void4() {
		return "userCenter/editorAnswer";
	}

	// 我的消息-漫画
	@RequestMapping("/userCenter/myMessage")
	public String void5() {
		return "userCenter/myMessage";
	}

	// 我的上传-漫画
	@RequestMapping("/userCenter/myUpload")
	public String void6() {
		return "userCenter/myUpload";
	}

	// 我的收藏-漫画
	@RequestMapping("/userCenter/myCollectCartoon")
	public String void7() {
		return "userCenter/myCollectCartoon";
	}

}
