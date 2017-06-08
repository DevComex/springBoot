/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——保单业务处理类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.bll.insurance;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.insurance.MyOrderParameterBean;
import cn.gyyx.action.beans.insurance.OrderBean;
import cn.gyyx.action.beans.insurance.OrderResultBean;
import cn.gyyx.action.beans.insurance.ReparationBean;
import cn.gyyx.action.dao.insurance.OrderDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;




public class OrderBLL {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(OrderBLL.class);
	private OrderDAO orderDAO = new OrderDAO();
	//分页显示时的每页条数
	private int num = 10;
	/**
	 * 根据用户名、区组、角色Id查询订单表中是否有重复的信息
	 * @param orderBean
	 * @return true:有重复信息    false：没有重复信息
	 */
	public boolean checkOrder(OrderBean orderBean){
		
		Integer count = orderDAO.selectByNameGroupRole(orderBean);
		logger.info("虚拟财产保险活动，检查账号+区组+角色ID是否有重复的。数据库中条件为："+orderBean +"的信息条数为："+count);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 将OrderBeanList中的时间格式改为yyyy-MM-dd HH：mm：ss ，并将相应的状态设置到Bean中
	 * @param orderList
	 * @return
	 */
	public List<OrderBean> changeTimeStyle(List<OrderBean> orderList){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String creatTimeStr = null;
		String validTimeStr = null;
		for(OrderBean orderBean: orderList){
			if(orderBean.getCreatTime()!=null){
				creatTimeStr = sdf.format(orderBean.getCreatTime());
				orderBean.setCreatTimeStr(creatTimeStr);
			}
			if(orderBean.getValidTime()!=null){
				validTimeStr = sdf.format(orderBean.getValidTime());
				orderBean.setValidTimeStr(validTimeStr);
			}
			if(orderBean.getCreatTime()==null){
				orderBean.setCreatTimeStr("");
			}
			if(orderBean.getValidTime()==null){
				orderBean.setValidTimeStr("");
			}
			switch(orderBean.getStatus()){	
			case "nopay" :  orderBean.setStatusShow("未付款");break;
			case "succpay" :  orderBean.setStatusShow("支付成功");break;
			case "failpay" :  orderBean.setStatusShow("支付失败");break;
			case "efforder" : orderBean.setStatusShow("有效保单") ;break;
			case "noefforder" : orderBean.setStatusShow("无效保单") ;break;
			case "reparation" :  orderBean.setStatusShow("理赔中") ;break;
			case "succreparation" :  orderBean.setStatusShow("理赔成功") ;break;
			case "failreparation" :  orderBean.setStatusShow("理赔失败") ;break;
			}
		
		}
		return orderList;
	}

	/**
	 * 计算保费总额
	 * @return
	 */
	public Float selectProtectionSUM(MyOrderParameterBean myOrderParameterBean){
		return orderDAO.selectProtectionSUM(myOrderParameterBean);
	}

	
	/**
	 * 新增账单
	 * @param orderBean
	 */
	public void addOrder(OrderBean orderBean){
		orderDAO.addOrder(orderBean);
		
	}
	/**
	 * 对比两个参数中Protection、Circle、Reparation是否一致
	 * @param data
	 * @param reparameterBean
	 * @return
	 */
	public boolean checkSame(OrderBean data, ReparationBean reparameterBean) {
		if(data.getProtection()==reparameterBean.getProtection() && data.getCircle()==reparameterBean.getCircle() ){
			return true;
		}
		return false;
	}
	/**
	 * 根据订单号更改订单状态
	 * @param orderNum
	 * @param status
	 */
	public boolean setOrderStatus(String orderNum ,String status){
		try{
			orderDAO.updateOrderStatus(orderNum, status);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * 按照订单号查询订单信息
	 * @param orderNum
	 * @return
	 */
	public ResultBean<OrderBean> selectByOrderNum(String orderNum){
		ResultBean<OrderBean> result = new ResultBean<OrderBean>(false, "获取订单信息未知错误", null);
		OrderBean orderBean = null;
		try{
			orderBean = orderDAO.selectByOrderNum(orderNum);
			result.setProperties(true, "获取订单信息获取成功", orderBean);
			return result;
		}catch(Exception e){
			result.setProperties(false, "获取订单信息获取失败", orderBean);
			return result;
		}
	}
	/**
	 * @Title: getOrderByCondition
	 * @Author:wanglei
	 * @date 2015年07月14日 
	 * @Description: TODO 条件查询保单信息
	 * @param String status:保单状态,  Date endTime：下单结束时间, Integer pageNum：页
	 * @param Date startTime：下单起始时间,
	 */
	public ResultBean<List<OrderBean>> getOrderByCondition(MyOrderParameterBean myOrderParameterBean) {
		ResultBean<List<OrderBean>> result = new ResultBean<List<OrderBean>>(false, "Order分页未知错误", null);
		List<OrderBean> orderBeanList = null;
		myOrderParameterBean.setNum(num);
		try{
			orderBeanList = orderDAO.selectOrderByCondition(myOrderParameterBean);
			result.setProperties(true, "我的订单分页信息获取成功", orderBeanList);
			return result;
		}catch(Exception e){
			result.setProperties(false, "我的订单分页信息获取失败", orderBeanList);
			return result;
		}
		
		
	}
	/**
	 * @Title: getOrderByCondition
	 * @Author:wanglei
	 * @date 2015年07月14日 
	 * @Description: TODO 条件查询保单信息为了Excel
	 * @param String status:保单状态,  Date endTime：下单结束时间, Integer pageNum：页
	 * @param Date startTime：下单起始时间,
	 */
	public ResultBean<List<OrderBean>> getOrderByConditionExcel(MyOrderParameterBean myOrderParameterBean) {
		ResultBean<List<OrderBean>> result = new ResultBean<List<OrderBean>>(false, "Order分页未知错误", null);
		List<OrderBean> orderBeanList = null;
		myOrderParameterBean.setNum(num);
		try{
			orderBeanList = orderDAO.selectOrderByConditionExcel(myOrderParameterBean);
			result.setProperties(true, "我的订单分页信息获取成功", orderBeanList);
			return result;
		}catch(Exception e){
			result.setProperties(false, "我的订单分页信息获取失败", orderBeanList);
			return result;
		}
		
		
	}
	/**
	 * @Title: selectCountByCondition
	 * @Author:wanglei
	 * @date 2015年07月15日 
	 * @Description: TODO 条件查询保单信息条数
	 * @param MyOrderParameterBean myOrderParameterBean
	 * 
	 */
	public ResultBean<Map<String ,Integer>> getPageCountByCondition(MyOrderParameterBean myOrderParameterBean) {
		String trueStatus = myOrderParameterBean.getStatus();
		ResultBean<Map<String ,Integer>> result = new ResultBean<Map<String ,Integer>>(false, "Order获取保单条数失败", null);
		Map<String ,Integer> countMap = new HashMap<String ,Integer>();
		//保单状态数组
		String [] statusArray = {"nopay","succpay","failpay","efforder","noefforder","reparation","succreparation","failreparation"};
		
		try{
			//获取分页的总页数
			Integer count = orderDAO.selectCountByCondition(myOrderParameterBean);
			countMap.put("count", count%num == 0 ? count/num : count/num+1);
			//将时间设置为0，来查询每个状态的条数
			myOrderParameterBean.setStartTime(null);
			myOrderParameterBean.setEndTime(null);
			//获取本用户每个状态的保单数量
			for(String status: statusArray){
				myOrderParameterBean.setStatus(status);
				Integer orderNum = orderDAO.selectCountByCondition(myOrderParameterBean);
				countMap.put(status, orderNum);
			}
			result.setProperties(true, "我的订单获取保单条数成功", countMap);
			myOrderParameterBean.setStatus(trueStatus);
			return result;
		}catch(Exception e){
			result.setProperties(false, "我的订单获取保单条数失败", countMap);
			myOrderParameterBean.setStatus(trueStatus);
			return result;
		}
	}
	/**
	 * @Title: selectCountByCondition
	 * @Author:wanglei
	 * @date 2015年07月15日 
	 * @Description: TODO 条件查询保单信息条数其中count为总数据条数
	 * @param MyOrderParameterBean myOrderParameterBean
	 * 
	 */
	public ResultBean<Map<String ,Integer>> getPageCount(MyOrderParameterBean myOrderParameterBean) {
		ResultBean<Map<String ,Integer>> result = new ResultBean<Map<String ,Integer>>(false, "Order获取保单条数失败", null);
		Map<String ,Integer> countMap = new HashMap<String ,Integer>();
		//保单状态数组
		String [] statusArray = {"nopay","succpay","failpay","efforder","noefforder","reparation","succreparation","failreparation"};
		
		try{
			//获取分页的总页数
			Integer count = orderDAO.selectCountByCondition(myOrderParameterBean);
			countMap.put("count", count);
			//将时间设置为0，来查询每个状态的条数
			myOrderParameterBean.setStartTime(null);
			myOrderParameterBean.setEndTime(null);
			//获取本用户每个状态的保单数量
			for(String status: statusArray){
				myOrderParameterBean.setStatus(status);
				Integer orderNum = orderDAO.selectCountByCondition(myOrderParameterBean);
				countMap.put(status, orderNum);
			}
			result.setProperties(true, "我的订单获取保单条数成功", countMap);
			return result;
		}catch(Exception e){
			result.setProperties(false, "我的订单获取保单条数失败", countMap);
			return result;
		}
	}
	/**
	 * @Title: selectCountByCondition
	 * @Author:wanglei
	 * @date 2015年07月15日 
	 * @Description: TODO 条件查询保单信息条数其中count为总数据条数
	 * @param MyOrderParameterBean myOrderParameterBean
	 * 
	 */
	public ResultBean<Map<String ,Integer>> getCountByTime(MyOrderParameterBean myOrderParameterBean) {
		ResultBean<Map<String ,Integer>> result = new ResultBean<Map<String ,Integer>>(false, "Order获取保单条数失败", null);
		Map<String ,Integer> countMap = new HashMap<String ,Integer>();
		//保单状态数组
		String [] statusArray = {"nopay","succpay","failpay","efforder","noefforder","reparation","succreparation","failreparation"};
		
		try{
			//获取分页的总页数
			Integer count = orderDAO.selectCountByCondition(myOrderParameterBean);
			countMap.put("count", count);
			//获取本用户每个状态的保单数量
			for(String status: statusArray){
				myOrderParameterBean.setStatus(status);
				Integer orderNum = orderDAO.selectCountByCondition(myOrderParameterBean);
				countMap.put(status, orderNum);
			}
			result.setProperties(true, "我的订单获取保单条数成功", countMap);
			return result;
		}catch(Exception e){
			result.setProperties(false, "我的订单获取保单条数失败", countMap);
			return result;
		}
	}
	/**
	 * 为我的订单返回数据设置保单数量以及分页总数的值
	 * @param orderResultBean
	 * @param countMap
	 * @return
	 */
	public OrderResultBean setOrderNumToResult(OrderResultBean orderResultBean, Map<String ,Integer> countMap){
		orderResultBean.setCount(countMap.get("count"));
		orderResultBean.setStatusCount(countMap.get("nopay"), countMap.get("succpay"), countMap.get("failpay"), 
								 	   countMap.get("efforder"), countMap.get("noefforder"), 
								 	   countMap.get("reparation"), countMap.get("succreparation"), 
								 	   countMap.get("failreparation"));
		return orderResultBean;
	}

	/**
	 * 检查接收信息是否为空  有空值返回true 
	 * @param status
	 * @param orderNum
	 * @param protection
	 * @param circle
	 * @param reparation
	 * @param other
	 * @param reparation_order_num
	 * @param reparation_result
	 * @param timestamp
	 * @param sign_type
	 * @param sign
	 * @return
	 */
	public boolean checkIsNull(String status, String orderNum, Float protection,
			Integer circle, Float reparation, String other,
			String reparation_order_num, String reparation_result,
			String timestamp, String sign_type, String sign) {
		if(status==null || orderNum==null || protection==null || circle==null || reparation==null || other==null 
				|| reparation_order_num==null || sign==null || timestamp==null || sign_type==null){
			return true;
		}else{
			//如果状态不是处理中并且理赔结果为空返回为false
			if((!"reparation".equals(status))&&reparation_result==null){
				return true;
			}
			return false;
		}
		
	}
	
	/**
	 * 获取保单数
	 * 
	 * @param myOrderParameterBean
	 * @return
	 */
	public Integer getCountOrder(MyOrderParameterBean myOrderParameterBean){
		return  orderDAO.selectCountByCondition(myOrderParameterBean);
	}
	/**
	 * @throws IOException 
	 * @作者：王雷
	 * @日期：2015年7月17日
	 * @描述：导出保险订单的Excel表格
	 * @参数： HttpServletRequest request 
	 */
	public void exportOrderExcel(HttpServletResponse httpResponse,
			List<OrderBean> list) throws IOException {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("保险订单信息");

		sheet.setColumnWidth(0, 16 * 256);
		sheet.setColumnWidth(1, 16 * 256);
		sheet.setColumnWidth(2, 16 * 256);
		sheet.setColumnWidth(3, 16 * 256);
		sheet.setColumnWidth(4, 16 * 256);
		sheet.setColumnWidth(5, 16 * 256);
		sheet.setColumnWidth(6, 16 * 256);
		sheet.setColumnWidth(7, 16 * 256);
		sheet.setColumnWidth(8, 16 * 256);
		sheet.setColumnWidth(9, 16 * 256);
		sheet.setColumnWidth(10, 16 * 256);
		sheet.setColumnWidth(11, 16 * 256);
		sheet.setColumnWidth(12, 16 * 256);
		sheet.setColumnWidth(13, 16 * 256);
		sheet.setColumnWidth(14, 16 * 256);
		sheet.setColumnWidth(15, 16 * 256);
		
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCellStyle styleContext = wb.createCellStyle();
		styleContext.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleContext.setFillForegroundColor(HSSFColor.RED.index);
		ArrayList<String> cellList = new ArrayList<String>();
		cellList.add("订单号");
		cellList.add("姓名");
		cellList.add("手机号");
		cellList.add("订单类型");
		cellList.add("图片");
		cellList.add("账号");
		cellList.add("区组");
		cellList.add("名称");
		cellList.add("等级");
		cellList.add("保费");
		cellList.add("保单周期");
		cellList.add("下单时间");
		cellList.add("有效时间");
		cellList.add("订单状态");
		cellList.add("保额上限");
		cellList.add("登陆IP");
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("订单号");
		cell.setCellStyle(style);
		for (int i = 1; i < cellList.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(cellList.get(i));
			cell.setCellStyle(style);
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		String GroupName = "";
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			OrderBean info = list.get(i);

			// 第四步，创建单元格，并设置值
			row.createCell(0).setCellValue(info.getOrderNum());
			row.createCell(1).setCellValue(info.getName());
			row.createCell(2).setCellValue(info.getPhone());
			row.createCell(3).setCellValue(info.getOrderType());
			row.createCell(4).setCellValue(info.getPictureUrl());
			row.createCell(5).setCellValue(info.getAccount());
			row.createCell(6).setCellValue(info.getServerGroup());
			row.createCell(7).setCellValue(info.getServerName());
			row.createCell(8).setCellValue(info.getLevel());
			row.createCell(9).setCellValue(info.getProtection());
			row.createCell(10).setCellValue(info.getCircle());
			row.createCell(11).setCellValue(info.getCreatTimeStr());
			if(info.getValidTime()==null){
				row.createCell(12).setCellValue("");
			}else{
				row.createCell(12).setCellValue(info.getValidTimeStr());
			}
			row.createCell(13).setCellValue(info.getStatusShow());
			row.createCell(14).setCellValue(info.getReparation());
			row.createCell(15).setCellValue(info.getIp());
		
		}
		// 第六步，将文件存到指定位置
		
			String fileName = "WDInsuranceOrderInfoTable.xls";
			String realPath = "";

			OutputStream os = httpResponse.getOutputStream();// 取得输出流
			httpResponse.reset();// 清空输出流
			httpResponse.setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String(fileName.getBytes("GB2312"),
									"ISO8859-1"));
			httpResponse.setContentType("application/msexcel");
			wb.write(os);  
			os.close();
		
	}
	
}
