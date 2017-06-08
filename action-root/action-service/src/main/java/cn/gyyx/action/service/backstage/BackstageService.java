package cn.gyyx.action.service.backstage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;

import cn.gyyx.action.beans.lottery.QualificationBean;
import cn.gyyx.action.bll.wdninestory.QualificationBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class BackstageService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(BackstageService.class);
	private QualificationBLL qualificationBLL = new QualificationBLL();
	public List<QualificationBean> readXls(InputStream is,int actionCode) {
		HSSFWorkbook hssfWorkbook;
		QualificationBean qualificationBean = null;
		List<QualificationBean> list = new ArrayList<QualificationBean>();
		try {
			hssfWorkbook = new HSSFWorkbook(is);
			// 循环工作表Sheet
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow != null) {
						qualificationBean = new QualificationBean();
						
						HSSFCell userCode = hssfRow.getCell(0);
						HSSFCell lotteryTime = hssfRow.getCell(1);
						
						int userCode1 =  (int) userCode.getNumericCellValue();
						int lotteryTime1 =   (int) lotteryTime.getNumericCellValue();
						
						qualificationBean.setUserCode(userCode1);
						qualificationBean.setLotteryTime(lotteryTime1);
						qualificationBean.setActionCode(actionCode);
					
						list.add(qualificationBean);
					}
				}
				if(list.size()!=0){
					for(QualificationBean qualificationBean1:list){
						QualificationBean qualificationBean2 = qualificationBLL.selectByUserAndAction(String.valueOf(qualificationBean1.getUserCode()), String.valueOf(qualificationBean1.getActionCode()));
						if(qualificationBean2==null){
							qualificationBLL.addQualification(qualificationBean1);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
		return list;
	}
	@SuppressWarnings("static-access")
	private String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			if (DateUtil.isCellDateFormatted(hssfCell)) {
				return String.valueOf(hssfCell.getDateCellValue());
			} else {
				return String.valueOf(hssfCell.getNumericCellValue());
			}
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_FORMULA) {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getCellFormula());
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}
}
