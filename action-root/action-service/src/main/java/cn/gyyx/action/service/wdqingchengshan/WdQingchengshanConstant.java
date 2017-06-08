package cn.gyyx.action.service.wdqingchengshan;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.gyyx.action.beans.PageBean;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.QualificationBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.bll.lottery.impl.ActionPrizesAddressBLLImpl;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年7月12日 下午7:49:24
 * 描        述：活动常量
 */
public class WdQingchengshanConstant {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WdQingchengshanConstant.class);
	
	public static final String HD_NAME = "问道青城山活动";
	
	public static final String MEM_KEY_PREFIX = "wd_qingchengshan_active";
	
	private ActionPrizesAddressBLLImpl actionPrizesAddressBLL = new ActionPrizesAddressBLLImpl();
	

	/**
	 * 用户名 * 表示
	 */
	public static String userNameEncrypt(String account) {
		if(account == null){
			return "";
		}
		if(account.length() > 4 ){
			return account.substring(0,2) + account.substring(2,account.length() - 2).replaceAll(".", "*") + account.substring(account.length() - 2,account.length());
		}
		return account;
	}
	
	/**
	 * 分页获取全部用户地址信息
	 * @param currentPage
	 * @return
	 */
	public ResultBean<PageBean<ActionPrizesAddressPO>> getAddressList(int currentPage){
		logger.warn("WdQingchengshanConstant-getAddressList,参数currentPage："
				+ currentPage);
		ResultBean<PageBean<ActionPrizesAddressPO>> result = new ResultBean<>(false, null, null);
		if(currentPage < 1 ){
			currentPage = 1;
		}
		try {
			List<ActionPrizesAddressPO> addressPO = actionPrizesAddressBLL.getUserAddress(10, currentPage);
			if(addressPO.isEmpty()){
				result.setMessage("没有数据");
				return result;
			}
			
			for(ActionPrizesAddressPO a : addressPO){
				if("exchange".equals(a.getActivityType())){
					a.setActivityType("兑换");
				}
				if("lottery".equals(a.getActivityType())){
					a.setActivityType("抽奖");
				}
			}
			
			int count = actionPrizesAddressBLL.getCount();
			PageBean<ActionPrizesAddressPO> page = PageBean.createPage( count,currentPage,addressPO);
			result.setData(page);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("WdQingchengshanConstant出错",e);
			result.setMessage("请求失败");
		}
		return result;
	}
	
	//导出表格
	@Autowired
	public void extendExcel(HttpServletResponse response,List<ActionPrizesAddressPO> logBeans) throws IOException{
		// 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook(); 
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("邀请函填写信息");
        
        sheet.setColumnWidth(0, 15 * 256);
        sheet.setColumnWidth(1, 15 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 30 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 20 * 256);
        sheet.setColumnWidth(7, 30 * 256);
        sheet.setColumnWidth(8, 15 * 256);
        
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        ArrayList<String> cellList = new ArrayList<String>();
        cellList.add("用户名");
        cellList.add("姓名");
        cellList.add("电话");
        cellList.add("邮箱");
        cellList.add("QQ");
        cellList.add("省市");
        cellList.add("兑换/中奖时间");
        cellList.add("邀请函获取途径（兑换/抽奖）");
        cellList.add("来源");
        
        HSSFCell cell = row.createCell(0);
        for (int i = 0; i < cellList.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(cellList.get(i));
            cell.setCellStyle(style);
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到
        for (int i = 0; i < logBeans.size(); i++) {
        	row = sheet.createRow((int) i + 1);
        	ActionPrizesAddressPO logBean = logBeans.get(i);
      
        	
        	
        	row.setHeight((short) (15 * 20));
        	
        	if("exchange".equals(logBean.getActivityType())){
        		logBean.setActivityType("兑换");
			}
			if("lottery".equals(logBean.getActivityType())){
				logBean.setActivityType("抽奖");
			}
        	
        	// 第四步，创建单元格，并设置值
			HSSFCell cell0 =row.createCell(0);
			HSSFCell cell1 =row.createCell(1);
			HSSFCell cell2 =row.createCell(2);
			HSSFCell cell3 =row.createCell(3);
			HSSFCell cell4 =row.createCell(4);
			HSSFCell cell5 =row.createCell(5);
			HSSFCell cell6 =row.createCell(6);
			HSSFCell cell7 =row.createCell(7);
			HSSFCell cell8 =row.createCell(8);
			
			cell0.setCellStyle(style);
			cell0.setCellValue(logBean.getUserId());
			
			cell1.setCellStyle(style);
			cell1.setCellValue(logBean.getUserName());
			
			cell2.setCellStyle(style);
			cell2.setCellValue(logBean.getUserPhone());
        	
			cell3.setCellStyle(style);
			cell3.setCellValue(logBean.getUserEmail());
			
			cell4.setCellStyle(style);
			cell4.setCellValue(logBean.getQq());
			
			cell5.setCellStyle(style);
			cell5.setCellValue(logBean.getUserAddress());
			
			cell6.setCellStyle(style);
			cell6.setCellValue(sdf.format(logBean.getWinningAt()));
			
			cell7.setCellStyle(style);
			cell7.setCellValue(logBean.getActivityType());
			
			cell8.setCellStyle(style);
			cell8.setCellValue(logBean.getSource());
			
        }
        
        // 第六步，将文件存到指定位置
        String fileName = "邀请函填写信息.xls";
        // 取得输出流
        OutputStream os = response.getOutputStream();
        // 清空输出流
        response.reset();
        response.setHeader("Content-disposition","attachment; filename="
                        + new String(fileName.getBytes("GB2312"),"ISO8859-1"));
        response.setContentType("application/msexcel");
        wb.write(os);
        os.close();
	}

}
