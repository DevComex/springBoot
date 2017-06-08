package cn.gyyx.action.oa.wdtenyearfans;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/WDFansStage")
public class WDTenYearFans {
	
	@RequestMapping("/indexComments")
	public String indexComments(Model model) {
	
		return "wdtenyearfans/fansCommentCheck";
	}
	@RequestMapping("/indexNomination")
	public String indexNomination(Model model) {
	
		return "wdtenyearfans/fansNominationCheck";
	}
	@RequestMapping("/indexVote")
	public String indexVote(Model model) {
		
		return "wdtenyearfans/fansVoteCheck";
	}
	@RequestMapping("/indexCount")
	public String indexCount(Model model) {
		
		return "wdtenyearfans/fansCount";
	}
	@RequestMapping("/indexNominationBack")
	public String indexNominationBack(Model model) {

		return "wdtenyearfans/fansNominationBack";
	}
	
}
