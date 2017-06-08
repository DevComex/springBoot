package cn.gyyx.action.oa.wdhalloffame;

import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.gyyx.action.bll.wdhalloffame.ImportExcelUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdhalloffame.WdHalloffameBean;
import cn.gyyx.action.bll.wdhalloffame.WdHalloffameBll;
import cn.gyyx.action.service.wdhalloffame.WdHalloffameService;

@Controller
@RequestMapping(value = "/halloffame")
public class WdHalloffameController {
	WdHalloffameBll wdHalloffameBll = new WdHalloffameBll();

	/***
	 * 主页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(Model model) {
		return "wdhalloffame/index";
	}

	/***
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectall")
	@ResponseBody
	public ResultBean<WdHalloffameBean> selectbypage(HttpServletRequest request, HttpServletResponse response) {
		ResultBean<WdHalloffameBean> resultBean = new ResultBean<>();
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int start = (pageNum - 1) * pageSize;
		resultBean = wdHalloffameBll.selectByPage(pageSize, start);
		return resultBean;

	}

	@RequestMapping(value = "/updateismark")
	@ResponseBody
	public void updateismark(HttpServletRequest request) {
		int code = Integer.parseInt(request.getParameter("codeno"));
		int isMark = Integer.parseInt(request.getParameter("isMark"));
		wdHalloffameBll.updateismark(code, isMark);

	}

	@RequestMapping(value = "/updateremark")
	@ResponseBody
	public void updateremark(HttpServletRequest request, String reMark) {

		int code = Integer.parseInt(request.getParameter("codeno"));
		wdHalloffameBll.updateremark(code, reMark);

	}

	/***
	 * 按QQ号查询用户信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectbyqq")
	@ResponseBody
	public ResultBean<WdHalloffameBean> selectbyqq(HttpServletRequest request, String qqno) {
		ResultBean<WdHalloffameBean> resultBean = new ResultBean<>();
		resultBean = wdHalloffameBll.selectByQq(qqno);
		return resultBean;
	}

	/***
	 * 按用户账号查询用户信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectbyuid")
	@ResponseBody
	public ResultBean<WdHalloffameBean> selectbyuid(HttpServletRequest request, String uid) {
		ResultBean<WdHalloffameBean> resultBean = new ResultBean<>();
		resultBean = wdHalloffameBll.selectByUserId(uid);
		return resultBean;

	}

	/***
	 * 按时间查询用户信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectbydate")
	@ResponseBody
	public ResultBean<WdHalloffameBean> selectbydate(HttpServletRequest request, Date stdate, Date endate) {
		ResultBean<WdHalloffameBean> resultBean = new ResultBean<>();

		resultBean = wdHalloffameBll.selectByTime(stdate, endate);
		return resultBean;

	}

	/**
	 * 描述：通过 jquery.form.js 插件提供的ajax方式上传文件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxUpload.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ResultBean<String> ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		WdHalloffameService wdHalloffameService = new WdHalloffameService();
		System.out.println("通过 jquery.form.js 提供的ajax方式上传文件！");
		ResultBean<String> result = new ResultBean<String>();
		InputStream in = null;
		List<List<Object>> listob = null;
		MultipartFile file = multipartRequest.getFile("upfile");
		if (file.isEmpty()) {
			throw new Exception("文件不存在！");
		}
		in = file.getInputStream();
		listob = new ImportExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
		// 该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
		result=wdHalloffameService.saveexcel(listob);
		
		response.setContentType("application/json");
		
		return result;
	}

}
