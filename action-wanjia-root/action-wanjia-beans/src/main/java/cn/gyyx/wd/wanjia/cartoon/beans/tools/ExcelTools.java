/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.wd.wanjia.cartoon.beans.tools;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年6月6日 下午2:15:20
 * 描        述： Excel工具类
 */
public class ExcelTools {
	public static String getCellStringValue(Cell cell){
		String cellValue = null;
		// 类型转换;
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {// 对字符串的处理
				cellValue = cell.getRichStringCellValue()
						.getString();
			} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {// 对布尔值的处理
				cellValue = String.valueOf(cell
						.getBooleanCellValue());
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {// 对数字的处理
				if (DateUtil.isCellDateFormatted(cell)) {
					Date d = cell.getDateCellValue(); // 对日期处理
					DateFormat formater = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					cellValue = formater.format(d);
				} else {// 其余按照数字处理
						// 防止科学计数法
					DecimalFormat df = new DecimalFormat("0.000");
					double acno = cell.getNumericCellValue();
					String acnoStr = df.format(acno);
					if (acnoStr.indexOf(".") > -1) {
						acnoStr = acnoStr.replaceAll("0+?$", "");// 去掉多余的0
						cellValue = acnoStr.replaceAll("[.]$", "");// 如最后一位是.则去掉
					}
				}
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {// 空的处理
				cellValue = "";
			} else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {// ERROR的处理
				cellValue = "";
			} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {// 公式的处理
				cellValue = cell.getCellFormula() + "";
			} else {
				cellValue = "";
			}
		} else {
			cellValue = "";
		}
		return cellValue;
	}
	
	/**
	 * 复制整行单元格
	 * @param wb
	 * @param srcCell
	 * @param distCell
	 * @param copyValueFlag
	 * @param value
	 */
	public static void copyCell(Workbook wb,Cell srcCell, Cell distCell,  
            boolean copyValueFlag,Object value) {  
        CellStyle newstyle=wb.createCellStyle();  
        copyCellStyle(srcCell.getCellStyle(), newstyle);  
        
        //获得传入的excel的字体的样式 
        Font eFont = wb.getFontAt(srcCell.getCellStyle().getFontIndex()); 
        //生成新的字体 
        Font fonts = wb.createFont(); 
         
        fonts.setFontHeightInPoints(eFont.getFontHeightInPoints()); //设置字体大小 
        fonts.setColor(eFont.getColor()); //设置字体颜色 
        fonts.setFontName(eFont.getFontName()); //设置子是什么字体（如宋体） 
        fonts.setBoldweight(eFont.getBoldweight()); //设置粗体  
         
        newstyle.setFont(fonts);//将字体样式设置给单元格 

        //distCell.setEncoding(srcCell.get);  
        //样式  
        distCell.setCellStyle(newstyle);  
        //评论  
        if (srcCell.getCellComment() != null) {  
            distCell.setCellComment(srcCell.getCellComment());  
        }  
        // 不同数据类型处理  
        int srcCellType = srcCell.getCellType();  
        distCell.setCellType(srcCellType);  
        
        if (copyValueFlag) {  
            if (srcCellType == Cell.CELL_TYPE_NUMERIC) {  
            	if (HSSFDateUtil.isCellDateFormatted(srcCell)) {  
                    distCell.setCellValue(srcCell.getDateCellValue());  
                } else {  
                    distCell.setCellValue(srcCell.getNumericCellValue());  
                }  
            } else if (srcCellType == HSSFCell.CELL_TYPE_BOOLEAN) {  
                distCell.setCellValue(srcCell.getBooleanCellValue());  
            } else if (srcCellType == Cell.CELL_TYPE_STRING) {  
                distCell.setCellValue(srcCell.getRichStringCellValue());  
            } else if (srcCellType == Cell.CELL_TYPE_BLANK) {  
                // nothing21  
            } else { // nothing29  
            }  
        }else{
//        	 if(value instanceof String){
//             	distCell.setCellValue((String)value);  
//             }else if(value instanceof Integer){
//            	distCell.setCellValue((Integer)value);  
//             }else if(value instanceof Double){
//             	distCell.setCellValue((Double)value);  
//              }
//        	 
        }
    }  
	
	/** 
     * 复制一个单元格样式到目的单元格样式 
     * @param fromStyle 
     * @param toStyle 
     */  
    public static void copyCellStyle(CellStyle fromStyle,  
            CellStyle toStyle) {  
        toStyle.setAlignment(fromStyle.getAlignment());  
        //边框和边框颜色  
        toStyle.setBorderBottom(fromStyle.getBorderBottom());  
        toStyle.setBorderLeft(fromStyle.getBorderLeft());  
        toStyle.setBorderRight(fromStyle.getBorderRight());  
        toStyle.setBorderTop(fromStyle.getBorderTop());  
        toStyle.setTopBorderColor(fromStyle.getTopBorderColor());  
        toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());  
        toStyle.setRightBorderColor(fromStyle.getRightBorderColor());  
        toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());  
          
        //背景和前景  
        toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());  
        toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());  
          
        toStyle.setDataFormat(fromStyle.getDataFormat());  
        toStyle.setFillPattern(fromStyle.getFillPattern());  
//      toStyle.setFont(fromStyle.getFont(null));  
        toStyle.setHidden(fromStyle.getHidden());  
        toStyle.setIndention(fromStyle.getIndention());//首行缩进  
        toStyle.setLocked(fromStyle.getLocked());  
        toStyle.setRotation(fromStyle.getRotation());//旋转  
        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());  
        toStyle.setWrapText(fromStyle.getWrapText());  
          
    } 
    
    /** 
     * 行复制功能 
     * @param fromRow 
     * @param toRow 
     */  
    public static void copyRow(Workbook wb,Row fromRow,Row toRow,boolean copyValueFlag){  
        for (Iterator cellIt = fromRow.cellIterator(); cellIt.hasNext();) {  
            Cell tmpCell = (Cell) cellIt.next();  
            Cell newCell = toRow.createCell(tmpCell.getColumnIndex());  
            copyCell(wb,tmpCell, newCell, copyValueFlag,null);  
        }  
    }  
    
    public static void setCellValue(Cell cell,  
            Object value) {  
    	if(cell == null || value == null){
    		return;
    	}
    	if(value instanceof String){
    		cell.setCellValue((String)value);
    	}else if(value instanceof Double){
    		cell.setCellValue((Double)value);
    	}else if(value instanceof Integer){
    		cell.setCellValue((Integer)value);
    	}
    }
    
    /**    
     * 获取合并单元格的值    
     * @param sheet    
     * @param row    
     * @param column    
     * @return    
     */     
     public static String getMergedRegionValue(Sheet sheet ,int row , int column){     
         int sheetMergeCount = sheet.getNumMergedRegions();     
              
         for(int i = 0 ; i < sheetMergeCount ; i++){     
             CellRangeAddress ca = sheet.getMergedRegion(i);     
             int firstColumn = ca.getFirstColumn();     
             int lastColumn = ca.getLastColumn();     
             int firstRow = ca.getFirstRow();     
             int lastRow = ca.getLastRow();     
             if(row >= firstRow && row <= lastRow){     
                  if(column >= firstColumn && column <= lastColumn){     
                     Row fRow = sheet.getRow(firstRow); 
                     Cell fCell = fRow.getCell(firstColumn); 
                     return getCellStringValue(fCell) ; 
                 }     
             }     
         }     
         return null ;     
     }   
	
	
}
