package cn.gyyx.action.oa;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.gyyx.action.beans.wdno1pet.CommentBean;
import cn.gyyx.action.bll.wdno1pet.CheckInfoBLL;

@Controller
@RequestMapping("/checkDiscuss")
public class CheckDiscussController {
	private List<CommentBean> listDis =new ArrayList<CommentBean>();
	private CheckInfoBLL checkDisInfo =null;
	private int nowPage =1;
	private int maxImgNum = 1;
	/**
	 * 通过评论类型查出10评论
	 * @author MT
	 * @param commentStatus
	 * @param model
	 * @return
	 */
	@RequestMapping("/checkDisInfo")
	public String checkDisInfo (  String commentStatus,Model model) {
		checkDisInfo = new CheckInfoBLL(commentStatus,nowPage);
		listDis = checkDisInfo.checkDisInfo();
		int disNum = checkDisInfo.selectDisPageNum(commentStatus);
		int disPageNum = 0;
		if( disNum%10==0 ) {
			disPageNum = disNum/10;
		}else {
			disPageNum = disNum/10+1;
		}
		List<Integer> list = new ArrayList<Integer>();
		for( int i=1;i<=disPageNum;i++ ) {
			list.add(i);
		}
		model.addAttribute("listDis", listDis);
		model.addAttribute("nowpageNew", nowPage);
		model.addAttribute("commentStatus", commentStatus);
		model.addAttribute("pageNum",list);
		model.addAttribute("sumDisNum",disNum);
		model.addAttribute("sumPageNum", disPageNum);
		return "pageDis";
	}
	/**
	 * 通过类型查出10评论
	 * @param commentStatus
	 * @param nowPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/checkDisInfo/{lip}")
	public String checkDisInfoBy ( @PathVariable("lip") String nowpageNew,String commentStatus ,Model model) {
		checkDisInfo = new CheckInfoBLL(commentStatus,Integer.parseInt(nowpageNew));
		listDis = checkDisInfo.checkDisInfo();
		int disNum = checkDisInfo.selectDisPageNum(commentStatus);
		int disPageNum = 0;
		if( disNum%10==0 ) {
			disPageNum = disNum/10;
		}else {
			disPageNum = disNum/10+1;
		}
		List<Integer> list = new ArrayList<Integer>();
		for( int i=1;i<=disPageNum;i++ ) {
			list.add(i);
		}
		model.addAttribute("listDis", listDis);
		model.addAttribute("nowpageNew", nowpageNew);
		model.addAttribute("commentStatus", commentStatus);
		model.addAttribute("pageNum",list);
		model.addAttribute("sumDisNum",disNum);
		model.addAttribute("sumPageNum", disPageNum);
		return "pageDis";
	}
	/**
	 * 通过评论审核
	 * @author MT
	 * @param commentCode
	 * @param pw
	 */
	@RequestMapping("/passDis/{commentCode}")
	public String passDisInfo ( @PathVariable("commentCode") String commentCode,@RequestParam String nowpageNew,@RequestParam String commentStatus ,Model model) {
		checkDisInfo = new CheckInfoBLL(commentStatus,Integer.parseInt(nowpageNew));
		checkDisInfo.passDisInfo(commentCode);
		listDis = checkDisInfo.checkDisInfo();
		int disNum = checkDisInfo.selectDisPageNum(commentStatus);
		int disPageNum = 0;
		if( disNum%10==0 ) {
			disPageNum = disNum/10;
		}else {
			disPageNum = disNum/10+1;
		}
		List<Integer> list = new ArrayList<Integer>();
		for( int i=1;i<=disPageNum;i++ ) {
			list.add(i);
		}
		model.addAttribute("listDis", listDis);
		model.addAttribute("nowpageNew", nowpageNew);
		model.addAttribute("commentStatus", commentStatus);
		model.addAttribute("pageNum",list);
		model.addAttribute("sumDisNum",disNum);
		model.addAttribute("sumPageNum", disPageNum);
		return "pageDis";

	}
	/**
	 * 取消审核通过，更改为审核未通过
	 * @param commentCode
	 * @param pw
	 */
	@RequestMapping("/cancelDis/{commentCode}")
	public String cancelPassedDis ( @PathVariable("commentCode") String commentCode ,@RequestParam String nowpageNew,@RequestParam String commentStatus,Model model ) {
		checkDisInfo = new CheckInfoBLL(commentStatus,Integer.parseInt(nowpageNew));
		checkDisInfo.cancelPassedDis(commentCode);
		listDis = checkDisInfo.checkDisInfo();
		int disNum = checkDisInfo.selectDisPageNum(commentStatus);
		int disPageNum = 0;
		if( disNum%10==0 ) {
			disPageNum = disNum/10;
		}else {
			disPageNum = disNum/10+1;
		}
		List<Integer> list = new ArrayList<Integer>();
		for( int i=1;i<=disPageNum;i++ ) {
			list.add(i);
		}
		model.addAttribute("listDis", listDis);
		model.addAttribute("nowpageNew", nowpageNew);
		model.addAttribute("commentStatus", commentStatus);
		model.addAttribute("pageNum",list);
		model.addAttribute("sumDisNum",disNum);
		model.addAttribute("sumPageNum", disPageNum);
		return "pageDis";
	}
	/***************************************************************************/
	@RequestMapping("/checkDisInfo2/{lip}")
	public String checkDisInfoBy2 ( @PathVariable("lip") String nowpageNew,String commentStatus ,Model model) {
		commentStatus = "uncheck";
		checkDisInfo = new CheckInfoBLL(commentStatus,Integer.parseInt(nowpageNew));
		listDis = checkDisInfo.checkDisInfo();
		int disNum = checkDisInfo.selectDisPageNum(commentStatus);
		int disPageNum = 0;
		if( disNum%10==0 ) {
			disPageNum = disNum/10;
		}else {
			disPageNum = disNum/10+1;
		}
		List<Integer> list = new ArrayList<Integer>();
		for( int i=1;i<=disPageNum;i++ ) {
			list.add(i);
		}
		model.addAttribute("listDis", listDis);
		model.addAttribute("nowpageNew", nowpageNew);
		model.addAttribute("commentStatus", commentStatus);
		model.addAttribute("pageNum",list);
		model.addAttribute("sumDisNum",disNum);
		model.addAttribute("sumPageNum", disPageNum);
		return "checkDiscuss";
	}
	 
	 
	 /**********************************************/
}
