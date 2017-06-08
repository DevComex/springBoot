/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月17日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——保单参数OA后台管理控制器类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.oa.wdinsurance;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.insurance.MyOrderParameterBean;
import cn.gyyx.action.beans.insurance.OrderBean;
import cn.gyyx.action.beans.insurance.OrderParameterBean;
import cn.gyyx.action.beans.insurance.OrderTotalResultBean;
import cn.gyyx.action.beans.insurance.ReparationBean;
import cn.gyyx.action.bll.insurance.BlackListBLL;
import cn.gyyx.action.bll.insurance.OrderBLL;
import cn.gyyx.action.bll.insurance.OrderParameterBLL;
import cn.gyyx.action.bll.insurance.ReparationBLL;
import cn.gyyx.action.oa.wdfire.WDFireController;
import cn.gyyx.action.service.insurance.InsuranceService;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

import com.ibm.icu.text.SimpleDateFormat;

@Controller
@RequestMapping(value = "/wdinsurance")
public class WDInsuranceController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDFireController.class);
	private OrderParameterBLL orderParameterBLL = new OrderParameterBLL();
	private OrderBLL orderBLL = new OrderBLL();
	private ReparationBLL reparationBLL = new ReparationBLL();
	private BlackListBLL blackListBLL = new BlackListBLL();
	private InsuranceService insuranceService = new InsuranceService();

	/**
	 * 订单参数初始控制器
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/orderParameterIndex", method = RequestMethod.GET)
	public String orderParameterIndex(Model model) {
		model.addAttribute("parameterList", orderParameterBLL.getAllOrderParameterBeans());
		return "/wdinsurance/orderPara";
	}
	/**
	 * 订单参数增加控制器
	 * @param model
	 * @param orderParameterBean
	 * @return
	 */
	@RequestMapping(value = "/orderParameterAdd", method = RequestMethod.POST)
	public String orderParameterAdd(Model model,@ModelAttribute OrderParameterBean orderParameterBean) {
		//验证前台的数据不为空
		if(orderParameterBean.getCircle()==null || orderParameterBean.getCircleName()==null || orderParameterBean.getDefaultAmount() ==null|| orderParameterBean.getLower() ==null||orderParameterBean.getOdds()==null || orderParameterBean.getType()==null || orderParameterBean.getUpper()==null){
			return "redirect:orderParameterIndex";
		}
		orderParameterBLL.addOrderParameter(orderParameterBean);
		return "redirect:orderParameterIndex";
	}
	/**
	 * 订单参数删除控制器
	 * @param model
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/orderParameterDelete", method = RequestMethod.GET)
	public String orderParameterDelete(Model model,String code) {
		int paraCode = 0;
		try{
			paraCode = Integer.parseInt(code);
		}catch(Exception e){
			logger.error("将编号转变为int类型时出错...");
		}
		 orderParameterBLL.updateIsDelete(paraCode);
		return "redirect:orderParameterIndex";
	}
	/**
	 * 订单参数修改提交控制器
	 * @param model
	 * @param orderParameterBean
	 * @return
	 */
	@RequestMapping(value = "/orderParameterUpdateSubmit", method = RequestMethod.POST)
	public String orderParameterUpdateSubmit(Model model,OrderParameterBean orderParameterBean) {
		//验证前台的数据不为空
		if(orderParameterBean.getCircle()==null || orderParameterBean.getCircleName()==null || orderParameterBean.getDefaultAmount() ==null|| orderParameterBean.getLower() ==null||orderParameterBean.getOdds()==null || orderParameterBean.getType()==null || orderParameterBean.getUpper()==null){
			return "redirect:orderParameterIndex";
		}
		orderParameterBLL.updateByCode(orderParameterBean);
		return "redirect:orderParameterIndex";
	}
	/**
	 * 订单参数点击修改控制器
	 */
	@ResponseBody
	@RequestMapping(value = "/orderParameterUpdate", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String orderParameterUpdate(Model model,String code) {
		int paraCode = 0;
		try{
			paraCode = Integer.parseInt(code);
		}catch(Exception e){
			logger.error("将编号转变为int类型时出错...");
		}
		String temp = DataFormat.objToJson(orderParameterBLL.selectBycode(paraCode));
		return temp;
	}
	/**
	 * 订单总计查询控制器
	 * @param model
	 * @param myOrderParameterBean
	 * @return
	 */
	@RequestMapping(value = "/orderTotal", method = RequestMethod.GET)
	@ResponseBody
	public String orderTotalIndex(Model model,MyOrderParameterBean myOrderParameterBean) {
		myOrderParameterBean.setGameName("问道");	
		improveParameter(myOrderParameterBean);
		OrderTotalResultBean orderTotalResultBean = new OrderTotalResultBean();
		Map<String, Integer> map = orderBLL.getCountByTime(myOrderParameterBean).getData();
		orderTotalResultBean.setFailreparationCount(map.get("failreparation"));
		orderTotalResultBean.setOrderCount(map.get("count")-map.get("nopay")-map.get("failpay"));	
		orderTotalResultBean.setProtectionSUM(orderBLL.selectProtectionSUM(myOrderParameterBean));
		orderTotalResultBean.setReparationCount(map.get("succreparation"));
		orderTotalResultBean.setReparationSUM(reparationBLL.selectReparationSUM(myOrderParameterBean));
		return DataFormat.objToJson(orderTotalResultBean);
	}
	/**
	 * 订单初始页控制器
	 * @param model
	 * @param myOrderParameterBean
	 * @return
	 */
	@RequestMapping(value = "/orderIndex", method = RequestMethod.GET)
	public String orderIndex(Model model) {
		MyOrderParameterBean myOrderParameterBean = new MyOrderParameterBean();
		myOrderParameterBean.setGameName("问道");
		myOrderParameterBean.setPageNum(1);
		ResultBean<List<OrderBean>> getResult = orderBLL.getOrderByCondition(myOrderParameterBean);
		model.addAttribute("orderList",orderBLL.changeTimeStyle(getResult.getData()));	
		Map<String, Integer> map = orderBLL.getPageCountByCondition(myOrderParameterBean).getData();
		model.addAttribute("count", map.get("count"));
		model.addAttribute("page", myOrderParameterBean.getPageNum());
		return "wdinsurance/orderInfo";
	}
	@RequestMapping(value = "/orderSearch", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String orderSearch(Model model, MyOrderParameterBean myOrderParameterBean) {
		try {
			myOrderParameterBean.setAccount( new String(myOrderParameterBean.getAccount().getBytes("iso8859-1"),"utf-8"));
			myOrderParameterBean.setOrderNum( new String(myOrderParameterBean.getOrderNum().getBytes("iso8859-1"),"utf-8"));
			
		} catch (Exception e) {
			logger.info("理赔接口——参数编码转换失败或者参数为空");
			e.printStackTrace();
		}
		String endTimeStr = myOrderParameterBean.getEndTimeStr();
		String startTimeStr = myOrderParameterBean.getStartTimeStr();
		improveParameter(myOrderParameterBean);
		myOrderParameterBean.setGameName("问道");
		if(myOrderParameterBean.getOrderNum()!=null && !"".equals(myOrderParameterBean.getOrderNum())){
			List<OrderBean> orderList = new ArrayList<OrderBean>();
			OrderBean bean = orderBLL.selectByOrderNum(myOrderParameterBean.getOrderNum()).getData();
			//如果查询的信息为空不做处理直接传null
			if(bean == null){
				model.addAttribute("orderList", null);	
			}else{
				orderList.add(bean);
				model.addAttribute("orderList", orderBLL.changeTimeStyle(orderList));	
			}
			model.addAttribute("count", 1);
			model.addAttribute("page", 1);
			//在传给前台的时候要将后加的时分秒去掉，不然前台会显示错误
			myOrderParameterBean.setEndTimeStr(endTimeStr);
			myOrderParameterBean.setStartTimeStr(startTimeStr);
			model.addAttribute("contion", myOrderParameterBean);
		}else{
			ResultBean<List<OrderBean>> getResult = orderBLL.getOrderByCondition(myOrderParameterBean);
			model.addAttribute("orderList", orderBLL.changeTimeStyle(getResult.getData()));	
			Map<String, Integer> map = orderBLL.getPageCountByCondition(myOrderParameterBean).getData();
			model.addAttribute("count", map.get("count"));
			model.addAttribute("page", 1);
			//在传给前台的时候要将后加的时分秒去掉，不然前台会显示错误
			myOrderParameterBean.setEndTimeStr(endTimeStr);
			myOrderParameterBean.setStartTimeStr(startTimeStr);
			model.addAttribute("contion", myOrderParameterBean);
		}
		return "wdinsurance/orderInfo";
	}
	@RequestMapping(value = "/orderPageSearch", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String orderPageSearch(Model model, MyOrderParameterBean myOrderParameterBean) {
		try {
			myOrderParameterBean.setAccount( new String(myOrderParameterBean.getAccount().getBytes("iso8859-1"),"utf-8"));
			myOrderParameterBean.setOrderNum( new String(myOrderParameterBean.getOrderNum().getBytes("iso8859-1"),"utf-8"));
		} catch (Exception e) {
			logger.error("理赔接口——参数编码转换失败");
			e.printStackTrace();
		}
		String endTimeStr = myOrderParameterBean.getEndTimeStr();
		String startTimeStr = myOrderParameterBean.getStartTimeStr();
		improveParameter(myOrderParameterBean);
		myOrderParameterBean.setGameName("问道");
		if(myOrderParameterBean.getOrderNum()!=null && !"".equals(myOrderParameterBean.getOrderNum())){
			List<OrderBean> orderList = new ArrayList<OrderBean>();
			orderList.add(orderBLL.selectByOrderNum(myOrderParameterBean.getOrderNum()).getData());
			model.addAttribute("orderList", orderBLL.changeTimeStyle(orderList));	
			model.addAttribute("count", 1);
			model.addAttribute("page", myOrderParameterBean.getPageNum());
			//在传给前台的时候要将后加的时分秒去掉，不然前台会显示错误
			myOrderParameterBean.setEndTimeStr(endTimeStr);
			myOrderParameterBean.setStartTimeStr(startTimeStr);
			model.addAttribute("contion", myOrderParameterBean);
		}else{
			ResultBean<List<OrderBean>> getResult = orderBLL.getOrderByCondition(myOrderParameterBean);
			model.addAttribute("orderList", orderBLL.changeTimeStyle(getResult.getData()));	
			Map<String, Integer> map = orderBLL.getPageCountByCondition(myOrderParameterBean).getData();
			model.addAttribute("count", map.get("count"));
			model.addAttribute("page", myOrderParameterBean.getPageNum());
			//在传给前台的时候要将后加的时分秒去掉，不然前台会显示错误
			myOrderParameterBean.setEndTimeStr(endTimeStr);
			myOrderParameterBean.setStartTimeStr(startTimeStr);
			model.addAttribute("contion", myOrderParameterBean);
			System.out.println(myOrderParameterBean.getPageNum());
		}
		return "wdinsurance/orderInfo";
	}
	/**
	 * 理赔初始控制器
	 * @param model
	 * @param myOrderParameterBean
	 * @return
	 */
	@RequestMapping(value = "/reparationIndex", method = RequestMethod.GET)
	public String reparationIndex(Model model) {
		MyOrderParameterBean myOrderParameterBean = new MyOrderParameterBean();
		myOrderParameterBean.setGameName("问道");
		myOrderParameterBean.setPageNum(1);
		//每页显示条数
		int num = 5;
		myOrderParameterBean.setNum(num);
		List<ReparationBean> getResult = new ArrayList<ReparationBean>();
		//如果查询条件中订单号有值，则直接获取此订单号的信息，然后返回
		if(myOrderParameterBean.getOrderNum()!=null){	
			getResult.add(reparationBLL.selectReparationBeanByOrderNum(myOrderParameterBean.getOrderNum()));
			model.addAttribute("reparationList", getResult);
			//共多少页
			model.addAttribute("count", 1);
			//当前页数
			model.addAttribute("page", 1);
			return "wdinsurance/claimsOrder";
		}
		getResult = reparationBLL.selectPage(myOrderParameterBean);
		model.addAttribute("reparationList", getResult);	
		//共多少页
		model.addAttribute("count", reparationBLL.selectPageCount(myOrderParameterBean, num));
		//当前页数
		model.addAttribute("page", myOrderParameterBean.getPageNum());		
		return "wdinsurance/claimsOrder";
	}
	@RequestMapping(value = "/reparationSearch", method = RequestMethod.GET)
	public String reparationSearch(Model model,MyOrderParameterBean myOrderParameterBean) {
		try {
			myOrderParameterBean.setAccount( new String(myOrderParameterBean.getAccount().getBytes("iso8859-1"),"utf-8"));
			myOrderParameterBean.setOrderNum( new String(myOrderParameterBean.getOrderNum().getBytes("iso8859-1"),"utf-8"));
		} catch (Exception e) {
			logger.error("理赔接口——参数编码转换失败");
			e.printStackTrace();
		}
		String endTimeStr = myOrderParameterBean.getEndTimeStr();
		String startTimeStr = myOrderParameterBean.getStartTimeStr();
		improveParameter(myOrderParameterBean);
		myOrderParameterBean.setGameName("问道");
		//每页显示条数
		int num = 5;
		myOrderParameterBean.setNum(num);
		List<ReparationBean> getResult = new ArrayList<ReparationBean>();
		//如果查询条件中订单号有值，则直接获取此订单号的信息，然后返回
		if(myOrderParameterBean.getOrderNum()!=null&& !"".equals(myOrderParameterBean.getOrderNum())){	
			getResult.add(reparationBLL.selectReparationBeanByOrderNum(myOrderParameterBean.getOrderNum()));
			model.addAttribute("reparationList", getResult);
			//共多少页
			model.addAttribute("count", 1);
			//当前页数
			model.addAttribute("page", 1);
			//在传给前台的时候要将后加的时分秒去掉，不然前台会显示错误
			myOrderParameterBean.setEndTimeStr(endTimeStr);
			myOrderParameterBean.setStartTimeStr(startTimeStr);
			model.addAttribute("contion", myOrderParameterBean);
			return "wdinsurance/claimsOrder";
		}
		getResult = reparationBLL.selectPage(myOrderParameterBean);
		model.addAttribute("reparationList", getResult);	
		//共多少页
		model.addAttribute("count", reparationBLL.selectPageCount(myOrderParameterBean, num));
		//当前页数
		model.addAttribute("page", myOrderParameterBean.getPageNum());	
		//在传给前台的时候要将后加的时分秒去掉，不然前台会显示错误
		myOrderParameterBean.setEndTimeStr(endTimeStr);
		myOrderParameterBean.setStartTimeStr(startTimeStr);
		model.addAttribute("contion", myOrderParameterBean);
		return "wdinsurance/claimsOrder";
	}
	/**
	 * 导出订单列表控制器
	 * @param myOrderParameterBean
	 * @param httpResponse
	 */
	@RequestMapping(value = "/exportOrderExcel", method = RequestMethod.GET)
	public void exportOrderExcel(MyOrderParameterBean myOrderParameterBean, HttpServletResponse httpResponse) {
		improveParameter(myOrderParameterBean);
		myOrderParameterBean.setGameName("问道");
		List<OrderBean> list = orderBLL.getOrderByConditionExcel(myOrderParameterBean).getData();
		
		try {
			orderBLL.exportOrderExcel(httpResponse, orderBLL.changeTimeStyle(list));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 导出理赔表控制器
	 * @param myOrderParameterBean
	 * @param httpResponse
	 */
	@RequestMapping(value = "/exportReparationExcel", method = RequestMethod.GET)
	public void exportReparationExcel(MyOrderParameterBean myOrderParameterBean, HttpServletResponse httpResponse) {
		
		improveParameter(myOrderParameterBean);
		myOrderParameterBean.setGameName("问道");
		//
		myOrderParameterBean.setNum(5);
		List<ReparationBean> list = reparationBLL.selectExcel(myOrderParameterBean);
		
		try {
			reparationBLL.exportReparationExcel(httpResponse, list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 黑名单首页
	 * @param model
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/blackListIndex", method = RequestMethod.GET)
	public String blackListIndex(Model model) {
		model.addAttribute("blackList", blackListBLL.getBlackLisForPage( 1));
		model.addAttribute("count",blackListBLL.getPage());
		System.out.println(blackListBLL.getPage());
		model.addAttribute("pageNum", 1);
		return "wdinsurance/blackList";
	}
	/**
	 * 黑名单首页
	 * @param model
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/blackListPage", method = RequestMethod.GET)
	public String blackListPage(Model model,Integer pageNum) {
		model.addAttribute("blackList", blackListBLL.getBlackLisForPage( pageNum));
		model.addAttribute("count",blackListBLL.getPage());
		model.addAttribute("pageNum", pageNum);
		return "wdinsurance/blackList";
	}
	/**
	 * 将前台的传来的参数进行处理，包括时间和状态
	 * @param myOrderParameterBean
	 * @return
	 */
	public MyOrderParameterBean improveParameter(MyOrderParameterBean myOrderParameterBean){
		//如果传的时间参数不为空，则转为Date类型，
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ("null".equals(myOrderParameterBean.getStatus())  && "".equals(myOrderParameterBean.getStatus()) && null == myOrderParameterBean.getStatus()) {
			myOrderParameterBean.setStatus(null);
		}
		try {
			Date dateEnd = null;
			Date dateStart = null;
			if (!"null".equals(myOrderParameterBean.getEndTimeStr()) && !"".equals(myOrderParameterBean.getEndTimeStr()) && null != myOrderParameterBean.getEndTimeStr()) {
				myOrderParameterBean.setEndTimeStr(myOrderParameterBean.getEndTimeStr()+" 23:59:59");
				dateEnd = sdf.parse(myOrderParameterBean.getEndTimeStr());
			}
			if (!"null".equals(myOrderParameterBean.getStartTimeStr()) && !"".equals(myOrderParameterBean.getStartTimeStr()) && null != myOrderParameterBean.getStartTimeStr()) {
				myOrderParameterBean.setStartTimeStr(myOrderParameterBean.getStartTimeStr()+" 00:00:00");
				dateStart = sdf.parse(myOrderParameterBean.getStartTimeStr());
			}
			myOrderParameterBean.setEndTime(dateEnd);
			myOrderParameterBean.setStartTime(dateStart);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return myOrderParameterBean;
	}
}
