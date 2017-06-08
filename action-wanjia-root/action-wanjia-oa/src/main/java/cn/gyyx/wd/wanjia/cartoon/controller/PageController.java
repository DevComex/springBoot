package cn.gyyx.wd.wanjia.cartoon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	//上传页
		@RequestMapping("/list")
		public String void11(){
			return "list";
		}
	
		@RequestMapping("/filedhistory")
		public String void2(){
			return "filedhistory";
		}
		/**/
		@RequestMapping("/filedmanage")
		public String void3(){
			return "filedmanage";
		}
		/**
		 * 预览页
		 * @return
		 */
		@RequestMapping("/preview")
		public String preview(){
			return "preview";
		}
		
}
