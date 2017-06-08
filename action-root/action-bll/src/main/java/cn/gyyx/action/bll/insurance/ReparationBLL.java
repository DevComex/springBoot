/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——理赔业务处理类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.bll.insurance;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import cn.gyyx.action.beans.insurance.MyOrderParameterBean;
import cn.gyyx.action.beans.insurance.ReparationBean;
import cn.gyyx.action.dao.insurance.OrderDAO;
import cn.gyyx.action.dao.insurance.ReparationDAO;

public class ReparationBLL {

	private ReparationDAO reparetionDAO = new ReparationDAO();
	private OrderDAO orderDAO = new OrderDAO();
	/**
	 * 计算理赔金额总额
	 * @return
	 */
	public Float selectReparationSUM(MyOrderParameterBean myOrderParameterBean){
		return reparetionDAO.selectReparationSUM(myOrderParameterBean);
	}
	/**
	 * 查询分页总条数
	 * @param myOrderParameterBean
	 * @return
	 */
	public Integer selectPageCount(MyOrderParameterBean myOrderParameterBean, Integer num){
		Integer count = reparetionDAO.selectPageCount(myOrderParameterBean);
		return count%num == 0 ? count/num : count/num+1;
	}
	/**
	 * 分页查询
	 * @param myOrderParameterBean
	 * @return
	 */
	public List<ReparationBean> selectPage(MyOrderParameterBean myOrderParameterBean){
		 List<ReparationBean> list = reparetionDAO.selectPage(myOrderParameterBean);
		 //将查出的理赔表信息，数据逐条加入订单表中对应的账号
		 for(ReparationBean bean : list){
			 bean.setAccount(orderDAO.selectByOrderNum(bean.getOrderNum()).getAccount());
		 }
		 return list;
	}
	/**
	 * 条件查询
	 * @param myOrderParameterBean
	 * @return
	 */
	public List<ReparationBean> selectExcel(MyOrderParameterBean myOrderParameterBean){
		 List<ReparationBean> list = reparetionDAO.selectExcel(myOrderParameterBean);
		 //将查出的理赔表信息，数据逐条加入订单表中对应的账号
		 for(ReparationBean bean : list){
			 bean.setAccount(orderDAO.selectByOrderNum(bean.getOrderNum()).getAccount());
		 }
		 return list;
	}
	
	/**
	 * 插入理赔表
	 * @param reparationBean
	 */
	public boolean addReparation(ReparationBean reparationBean){
		try{
			reparetionDAO.insertReparation(reparationBean);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * 根据订单号更改理赔说明
	 * @param orderNum
	 */
	public boolean setReparationOrderNum(String orderNum, String reparationRestult){
		try{
			reparetionDAO.updateReparationOrderNum(orderNum, reparationRestult);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * 根据订单号更改理赔金额
	 * @param orderNum
	 * @param reparationNum
	 */
	public void setReparation(String orderNum, Float reparationNum) {
		reparetionDAO.setReparation(orderNum, reparationNum);	
	}
	/**
	 * 根据订单号查询理赔信息
	 * @param OrderNum
	 * @return
	 */
	public ReparationBean selectReparationBeanByOrderNum(String OrderNum){
		return reparetionDAO.selectReparationBeanByOrderNum(OrderNum);
	}
	/**
	 * @throws IOException 
	 * @作者：王雷
	 * @日期：2015年7月17日
	 * @描述：导出保险理赔订单的Excel表格
	 * @参数： HttpServletRequest request 
	 */
	public void exportReparationExcel(HttpServletResponse httpResponse,
			List<ReparationBean> list) throws IOException {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("保险理赔订单信息");

		sheet.setColumnWidth(0, 16 * 256);
		sheet.setColumnWidth(1, 16 * 256);
		sheet.setColumnWidth(2, 16 * 256);
		sheet.setColumnWidth(3, 16 * 256);
		sheet.setColumnWidth(4, 16 * 256);
		sheet.setColumnWidth(5, 16 * 256);
		sheet.setColumnWidth(6, 16 * 256);
		sheet.setColumnWidth(7, 16 * 256);
		
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
		cellList.add("账号");
		cellList.add("区组");
		cellList.add("订单类型");
		cellList.add("保费");
		cellList.add("保单周期");
		cellList.add("理赔金额");
		cellList.add("理赔说明");
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
			ReparationBean info = list.get(i);

			// 第四步，创建单元格，并设置值
			row.createCell(0).setCellValue(info.getOrderNum());
			row.createCell(1).setCellValue(info.getAccount());
			row.createCell(2).setCellValue(info.getServerGroup());
			row.createCell(3).setCellValue(info.getType());
			row.createCell(4).setCellValue(info.getProtection());
			row.createCell(5).setCellValue(info.getCircle());
			row.createCell(6).setCellValue(info.getReparation());
			row.createCell(7).setCellValue(info.getReparationResult());
		
		}
		// 第六步，将文件存到指定位置
		
			String fileName = "WDInsuranceReparationOrderInfoTable.xls";
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
