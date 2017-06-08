package cn.gyyx.action.oa.wdoldplayers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/WDOldPlayer")
public class WDOldPlayer {
	public String indexComments(Model model) {
	
		return "wdoldplayers/oldPlayCommentCheck";
	}
	@RequestMapping("/indexLooking")
	public String indexNomination(Model model) {
	
		return "wdoldplayers/oldPlayLookingCheck";
	}

}
