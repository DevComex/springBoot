/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：马涛
 * 联系方式：
 * 创建时间： 2014年12月15日 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 
-------------------------------------------------------------------------*/
package cn.gyyx.action.oa;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.wdno1pet.ImageBean;
import cn.gyyx.action.beans.wdno1pet.ImgCheckBean;
import cn.gyyx.action.beans.wdno1pet.ResultBean;
import cn.gyyx.action.beans.wdno1pet.WDNo1PetInfoBean;
import cn.gyyx.action.bll.wdno1pet.CheckInfoBLL;
import cn.gyyx.action.bll.wdno1pet.ImageBLL;
import cn.gyyx.action.bll.wdno1pet.PetInfoBLL;
import cn.gyyx.action.oa.back.info.BackServiceInfo;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.core.DataFormat;
import cn.gyyx.service.wdno1pet.WDNo1PetService;

@Controller
@RequestMapping("/checkImg")
public class CheckImgController {
	private static final Logger logger = LoggerFactory.getLogger(CheckImgController.class);
	private CheckInfoBLL checkImgInfo = null;
	private List<ImageBean> list = new ArrayList<ImageBean>();
	private int nowPage =1;
	private int maxImgNum = 1;
	

	
	/**
	 * 1.
	 * 默认进入的是查询出第一页的信息
	 * @param petInfoBean
	 * @param imgStatus
	 * @param model
	 * @return
	 */

	@RequestMapping("/checkImgInfo")
	public String checkImgInfo( @ModelAttribute WDNo1PetInfoBean petInfoBean,@RequestParam("imgStatus") String imgStatus ,Model model) {	
		List<Integer> listPage = new ArrayList<Integer>();
		checkImgInfo = new CheckInfoBLL(petInfoBean,imgStatus,1);
		//获取按要求查找的所有图片信息
		list = checkImgInfo.checkImgInfo();
		maxImgNum = checkImgInfo.checkImgPageNum();    //总共信息的数量
		int maxPage=0;    //总共的页数..
		if( maxImgNum%5==0 ) {
			maxPage=maxImgNum/5;	
		}else {
			maxPage=maxImgNum/5+1;	
		}
		for( int i=1;i<=maxPage;i++ ) {
			listPage.add(i);
		}		
		model.addAttribute("imgInfo", list);
		model.addAttribute("sumInfoNum", maxImgNum);    //总共有多少信息
		model.addAttribute("maxPage", maxPage);    //总共的页数
		model.addAttribute("serverID", petInfoBean.getServerID());
		model.addAttribute("channel", petInfoBean.getChannel());
		model.addAttribute("imgStatus", imgStatus);
		model.addAttribute("pageNum", listPage);
		model.addAttribute("nowPage", 1);
		return "page";
	}  
	/**
	 * 1.
	 * 通过传入的当前页参数直接查询出当页的信息
	 * @param nowpageNew
	 * @param petInfoBean
	 * @param imgStatus
	 * @param model
	 * @return
	 */
	@RequestMapping("/checkImgInfo2/{lip}")
	public String checkImgIngoPage ( @PathVariable("lip") String nowpageNew, WDNo1PetInfoBean petInfoBean,String imgStatus ,Model model) {
		List<Integer> listPage = new ArrayList<Integer>();
		checkImgInfo = new CheckInfoBLL(petInfoBean,imgStatus,Integer.parseInt(nowpageNew));
		//获取按要求查找的所有图片信息
		list = checkImgInfo.checkImgInfo();
		maxImgNum = checkImgInfo.checkImgPageNum();
		int maxPage=0;
		if( maxImgNum%5==0 ) {
			maxPage=maxImgNum/5;	
		}else {
			maxPage=maxImgNum/5+1;	
		}
		for( int i=1;i<=maxPage;i++ ) {
			listPage.add(i);
		}		
		model.addAttribute("imgInfo", list);
		model.addAttribute("sumInfoNum", maxImgNum);
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("serverID", petInfoBean.getServerID());
		model.addAttribute("channel", petInfoBean.getChannel());
		model.addAttribute("imgStatus", imgStatus);
		model.addAttribute("pageNum", listPage);
		model.addAttribute("nowPage", nowpageNew);
		return "page";
	}
	/**
	 * 4.
	 * 审核通过某图片
	 * @param imgCode
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/passImg/{imgCode}")
	public String passImgInfo( @PathVariable("imgCode") String imgCode, @ModelAttribute WDNo1PetInfoBean petInfoBean,@RequestParam("imgStatus") String imgStatus ,@RequestParam("nowpageNew") String nowpageNew,Model model) throws IOException {
		checkImgInfo = new CheckInfoBLL(petInfoBean,imgStatus,Integer.parseInt(nowpageNew));
		ImageBean imgInfo = checkImgInfo.selectByImgCode(imgCode);
		imgInfo.setImgType("wd1");
		//group 就是    ImageBean 中的imgType属性
		String oldServicUrl = "http://interface.up.gyyx.cn/Image/SaveToRealFromTempByProportional.ashx?group="
				+ imgInfo.getImgType()
				+ "&width="
				+ imgInfo.getImgWidth()
				+ "&height="
				+ imgInfo.getImgHeight() 
				+ "&Code=" 
				+ imgInfo.getImgFeature();
		String realUrl = BackServiceInfo.backServiceUrlInfo(oldServicUrl);
		ImgCheckBean img = DataFormat.jsonToObj(realUrl, ImgCheckBean.class);
		checkImgInfo.passImgInfo(imgCode, img.getUrl());		//进行审核通过
//		CheckInfoBLL checkImgInfo2 = new CheckInfoBLL(petInfoBean,imgStatus,Integer.parseInt(nowpageNew));
		//获取按要求查找的所有图片信息
		list = checkImgInfo.checkImgInfo();
		model.addAttribute("imgInfo", list);
		model.addAttribute("maxPage", maxImgNum);
		model.addAttribute("serverID", petInfoBean.getServerID());
		model.addAttribute("channel", petInfoBean.getChannel());
		model.addAttribute("imgStatus", imgStatus);
		model.addAttribute("nowPage", nowpageNew);
		return "page";
	}
	@RequestMapping(value = "/passImgSlience/{imgCode}",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Integer> passImgInfoInSlience( @PathVariable("imgCode") String imgCode) throws IOException {
		checkImgInfo = new CheckInfoBLL();
		ImageBean imgInfo = checkImgInfo.selectByImgCode(imgCode);
		imgInfo.setImgType("wd1");
		//group 就是    ImageBean 中的imgType属性
		String oldServicUrl = "http://interface.up.gyyx.cn/Image/SaveToRealFromTempByProportional.ashx?group="
				+ imgInfo.getImgType()
				+ "&width="
				+ imgInfo.getImgWidth()
				+ "&height="
				+ imgInfo.getImgHeight() 
				+ "&Code=" 
				+ imgInfo.getImgFeature();
		String realUrl = BackServiceInfo.backServiceUrlInfo(oldServicUrl);
		ImgCheckBean img = DataFormat.jsonToObj(realUrl, ImgCheckBean.class);
		checkImgInfo.passImgInfo(imgCode, img.getUrl());		//进行审核通过
		ResultBean<Integer> res = new ResultBean<Integer>();
		res.setIsSuccess(true);
		return res;
	}
	/**
	 * 6.
	 * 取消审核通过的图片
	 */
	@RequestMapping("/cancelImg/{imgCode}")
	public String cancelPassedImg( @PathVariable("imgCode") String imgCode,@ModelAttribute WDNo1PetInfoBean petInfoBean,@RequestParam("imgStatus") String imgStatus ,@RequestParam("nowpageNew") String nowpageNew,Model model ) {
		checkImgInfo = new CheckInfoBLL(petInfoBean,imgStatus,Integer.parseInt(nowpageNew));
		checkImgInfo.cancelPassedImg(imgCode);
		list = checkImgInfo.checkImgInfo();
		model.addAttribute("imgInfo", list);
		model.addAttribute("maxPage", maxImgNum);
		model.addAttribute("serverID", petInfoBean.getServerID());
		model.addAttribute("channel", petInfoBean.getChannel());
		model.addAttribute("imgStatus", imgStatus);
		model.addAttribute("nowPage", nowpageNew);
		return "page";
	}
	
	@RequestMapping(value="getServers/{typeCode}")
	public String getServers(@PathVariable("typeCode") String typeCode, Model model) {
		// Check Auth
		WDNo1PetService wdno1petService = new WDNo1PetService();
		model.addAttribute("crtServers",new CallWebApiAgent().getAllServerByNetTypeCode(typeCode));
		model.addAttribute("codeServer2", "static.cn114.cn");
		return "getServic";
	}
	
	/*
	 * 12-23修改  增加通过imgStatus查找数据
	 */
	@RequestMapping("/selectImgInfo/{nowPage}")
	public String selectImgInfoSta( @PathVariable("nowPage") String nowPage,String imgStatus,Model model ) {
		List<Integer> listPage = new ArrayList<Integer>();
		checkImgInfo = new CheckInfoBLL();
		list = checkImgInfo.selectImgInfoSta(imgStatus, nowPage);
		maxImgNum = checkImgInfo.checkImgPageNum2( imgStatus );    //总共信息的数量
		int maxPage=0;    //总共的页数..
		if( maxImgNum%15==0 ) {
			maxPage=maxImgNum/15;	
		}else {
			maxPage=maxImgNum/15+1;	
		}
		for( int i=1;i<=maxPage;i++ ) {
			listPage.add(i);
		}		
		model.addAttribute("imgInfo", list);
		model.addAttribute("sumInfoNum", maxImgNum);    //总共有多少信息
		model.addAttribute("maxPage", maxPage);    //总共的页数
		model.addAttribute("imgStatus", imgStatus);
		model.addAttribute("pageNum", listPage);
		model.addAttribute("nowPage", 1);
		return "page";
	}
	
	@RequestMapping("/selectImgInfo2/{nowPage}")
	public String selectImgInfoSta2( @PathVariable("nowPage") String nowPage,String imgStatus,Model model ) {
		imgStatus="uncheck";
		List<Integer> listPage = new ArrayList<Integer>();
		checkImgInfo = new CheckInfoBLL();
		list = checkImgInfo.selectImgInfoSta(imgStatus, nowPage);
		maxImgNum = checkImgInfo.checkImgPageNum2( imgStatus );    //总共信息的数量
		int maxPage=0;    //总共的页数..
		if( maxImgNum%15==0 ) {
			maxPage=maxImgNum/15;	
		}else {
			maxPage=maxImgNum/15+1;	
		}
		for( int i=1;i<=maxPage;i++ ) {
			listPage.add(i);
		}		
		model.addAttribute("imgInfo", list);
		model.addAttribute("sumInfoNum", maxImgNum);    //总共有多少信息
		model.addAttribute("maxPage", maxPage);    //总共的页数
		model.addAttribute("imgStatus", imgStatus);
		model.addAttribute("pageNum", listPage);
		model.addAttribute("nowPage", 1);
		return "checkImg";
	}
	
	@RequestMapping("/passImg2/{imgCode}")
	public String passImgInfo2( @PathVariable("imgCode") String imgCode,@RequestParam("imgStatus") String imgStatus ,@RequestParam("nowpageNew") String nowpageNew,Model model) throws IOException {
		checkImgInfo = new CheckInfoBLL();
		ImageBean imgInfo = checkImgInfo.selectByImgCode(imgCode);
		imgInfo.setImgType("wd1");
		//group 就是    ImageBean 中的imgType属性
		String oldServicUrl = "http://interface.up.gyyx.cn/Image/SaveToRealFromTempByProportional.ashx?group="
				+ imgInfo.getImgType()
				+ "&width="
				+ imgInfo.getImgWidth()
				+ "&height="
				+ imgInfo.getImgHeight() 
				+ "&Code=" 
				+ imgInfo.getImgFeature();
		String realUrl = BackServiceInfo.backServiceUrlInfo(oldServicUrl);
		JSONObject js = JSONObject.fromObject(realUrl); 
		String realUrl2 = js.getString("Url");
		checkImgInfo.passImgInfo(imgCode, realUrl2);		//进行审核通过
//		CheckInfoBLL checkImgInfo2 = new CheckInfoBLL(petInfoBean,imgStatus,Integer.parseInt(nowpageNew));
		//获取按要求查找的所有图片信息
		list = checkImgInfo.selectImgInfoSta(imgStatus,nowpageNew);
		model.addAttribute("imgInfo", list);
		model.addAttribute("maxPage", maxImgNum);
		model.addAttribute("imgStatus", imgStatus);
		model.addAttribute("nowPage", nowpageNew);
		return "page";
	}
	
	@RequestMapping("/cancelImg2/{imgCode}")
	public String cancelPassedImg2( @PathVariable("imgCode") String imgCode,@RequestParam("imgStatus") String imgStatus ,@RequestParam("nowpageNew") String nowpageNew,Model model ) {
		checkImgInfo = new CheckInfoBLL();
		checkImgInfo.cancelPassedImg(imgCode);
		list = checkImgInfo.selectImgInfoSta(imgStatus,nowpageNew);
		model.addAttribute("imgInfo", list);
		model.addAttribute("maxPage", maxImgNum);
		model.addAttribute("imgStatus", imgStatus);
		model.addAttribute("nowPage", nowpageNew);
		return "page";
	}

	
	/***********************************************************************************************/
	/**
	 * 批量通过..
	 */
	@RequestMapping("/passAll/{nowPage}")
	public String passAllImgInfo( @PathVariable("nowPage") String nowPage,WDNo1PetInfoBean petInfoBean,String imgStatus,String boxValue,Model model) {
		/*checkImgInfo = new CheckInfoBLL(petInfoBean,imgStatus,Integer.parseInt(nowPage));
		ImageBean imgInfo = checkImgInfo.selectByImgCode(imgCode);
		imgInfo.setImgType("wd1");
		//group 就是    ImageBean 中的imgType属性
		String oldServicUrl = "http://interface.up.gyyx.cn/Image/SaveToRealFromTempByProportional.ashx?group="
				+ imgInfo.getImgType()
				+ "&width="
				+ imgInfo.getImgWidth()
				+ "&height="
				+ imgInfo.getImgHeight() 
				+ "&Code=" 
				+ imgInfo.getImgFeature();
		String realUrl = BackServiceInfo.backServiceUrlInfo(oldServicUrl);
		checkImgInfo.passAllImgInfo(boxValue);*/
		return "page";
	}
	/**
	 * 审核图片的详情页面
	 * @param model
	 * @return 详情页面
	 */
	@RequestMapping(value = "/startCheck/{imgCode}")
	public String checkImgInfo(Model model,@PathVariable("imgCode") int imgCode){
		ImageBLL iBll = new ImageBLL();
		ImageBean imageBean=iBll.getImageByCode(imgCode);
		WDNo1PetInfoBean petBean = new PetInfoBLL().getPetInfoByImgCode(imgCode);
		
		CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
		ServerInfoBean serverInfoBean;
		try {
			serverInfoBean = callWebApiAgent.getServerStatusFromWebApi(petBean.getServerID());
			model.addAttribute("serverName",serverInfoBean.getValue().getServerName());
		} catch (Exception e) {
			model.addAttribute("serverName","暂无");
		}
		
		
		model.addAttribute("imageBean",imageBean);
		model.addAttribute("petBean",petBean);
		
		return "checkImgInfo";
	}
	/**
	 * 更改审核不通过的状 态
	 * @param imgCode
	 */
	@RequestMapping(value = "/failCheck/{imgCode}",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Integer>checkFailImg(@PathVariable("imgCode") int imgCode){
		ResultBean<Integer> resultBean=new ResultBean<Integer>();
		checkImgInfo = new CheckInfoBLL();
		String imgCodeString=""+imgCode;
		checkImgInfo.cancelPassedImg(imgCodeString);
		resultBean.setIsSuccess(true);
		return resultBean;
	}
	
}
