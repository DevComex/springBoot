/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2016年-02月-18日
       Note:服务器关闭状态Controller
************************************************/
package cn.gyyx.action.oa.xwbcreditsluckydraw;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.xwbcreditsluckydraw.ServerStatusBean;
import cn.gyyx.action.bll.xwbcreditsluckydraw.ServerStatusBll;
import cn.gyyx.action.service.xuanwuba.ServerStatusService;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping("/serverStatus")
public class XWBServerStatusController {
	
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(XWBServerStatusController.class);
	private ServerStatusBll serverStatusBll = new ServerStatusBll();
	private ServerStatusService serverStatusService = new ServerStatusService();
	
	@RequestMapping("/index")
	public String goods(Integer page ,Model model) {
		if(page == null){
			page = 1;
		}
		serverStatusService.onlineCheck();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<ServerStatusBean> list = serverStatusBll.getAllServerStatusInfo();
		List<ServerStatusBean> listPage = serverStatusBll.getAllServerStatusInfoByPage(page);
		List<ServerStatusBean> live = serverStatusBll.getAllOpenServerStatusInfo();
		for (ServerStatusBean serverStatusBean : listPage) {
			serverStatusBean.setStartTimeStr(sdf.format(serverStatusBean.getStartTime()));
			serverStatusBean.setEndTimeStr(sdf.format(serverStatusBean.getEndTime()));
		}
		model.addAttribute("list", listPage);
		model.addAttribute("page", page);
		model.addAttribute("live", live.size());
		model.addAttribute("die", list.size()-live.size());
		model.addAttribute("count", list.size());
		model.addAttribute("listCount",
				list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1);
		return "xwbcreditsluckydraw/serverStatus";
	}
	
	/**
	 * 增加服务器关闭状态
	 * @param serverStatusBean
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/addServerStatusInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addServerStatusInfo(ServerStatusBean serverStatusBean,Model model){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(serverStatusBean.getStartTimeStr() == null || "".equals(serverStatusBean.getStartTimeStr())){
			return "开始时间不能为空！";
		}else if(serverStatusBean.getEndTimeStr() == null || "".equals(serverStatusBean.getEndTimeStr())){
			return "结束时间不能为空！";
		}else{
			try {
				serverStatusBean.setStartTime(sdf.parse(serverStatusBean.getStartTimeStr()));
				serverStatusBean.setEndTime(sdf.parse(serverStatusBean.getEndTimeStr()));
			} catch (ParseException e) {
				logger.info("日期转换异常:"+e.toString());
			}
			serverStatusBean.setCloseStatus(false);
			serverStatusBll.addServerStatusInfo(serverStatusBean);
		}
		return "添加成功！";
	}
	
	/**
	 * 修改服务器关闭状态
	 * @param serverStatusBean
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/modifyServerStatusInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String modifyServerStatusInfo(ServerStatusBean serverStatusBean,Model model){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(serverStatusBean.getStartTimeStr() == null || "".equals(serverStatusBean.getStartTimeStr())){
			return "开始时间不能为空！";
		}else if(serverStatusBean.getEndTimeStr() == null || "".equals(serverStatusBean.getEndTimeStr())){
			return "结束时间不能为空！";
		}else{
			try {
				serverStatusBean.setStartTime(sdf.parse(serverStatusBean.getStartTimeStr()));
				serverStatusBean.setEndTime(sdf.parse(serverStatusBean.getEndTimeStr()));
			} catch (ParseException e) {
				logger.info("日期转换异常:"+e.toString());
			}
			serverStatusBll.modifyServerStatusInfo(serverStatusBean);
		}
		return "修改成功！";
	}
	
	/**
	 * 增加服务器关闭状态
	 * @param serverStatusBean
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getServerStatusInfoByCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getServerStatusInfoByCode(int code,Model model){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ServerStatusBean serverStatusBean = serverStatusBll.getServerStatusInfoByCode(code);
		serverStatusBean.setStartTimeStr(sdf.format(serverStatusBean.getStartTime()));
		serverStatusBean.setEndTimeStr(sdf.format(serverStatusBean.getEndTime()));
		return DataFormat.objToJson(serverStatusBean);
	}
}
