package cn.gyyx.action.service.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.common.ActionCommonImageBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.wdno1pet.ImgCheckBean;
import cn.gyyx.core.DataFormat;

public class OaCommonService {
	public ResultBean<String> passImg(ActionCommonImageBean bean) {
		ResultBean<String> res = new ResultBean<String>();
		String oldServicUrl = "http://interface.up.gyyx.cn/Image/SaveToRealFromTempByProportional.ashx?group="
				+ "wd1"
				+ "&width="
				+ bean.getImgWidth()
				+ "&height="
				+ bean.getImgHeight()
				+ "&Code="
				+ bean.getImgFeature();
		try {
			String realUrl = backServiceUrlInfo(oldServicUrl);
			ImgCheckBean img = DataFormat.jsonToObj(realUrl, ImgCheckBean.class);
			if(img.getUrl() != null) {
				res.setIsSuccess(true);
				bean.setRealUrl(img.getUrl());
				res.setData(img.getUrl());
			} else {
				res.setIsSuccess(false);
				res.setMessage("图片服务器认证错误");
			}
		} catch (Exception e) {
			res.setIsSuccess(false);
			res.setData(e.getMessage());
		}
		return res;
	}

	private String backServiceUrlInfo(String oldServicUrl) throws IOException {
		URL url;
		url = new URL(oldServicUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "utf-8"));
		String temp;
		StringBuffer realUrl = new StringBuffer();
		while ((temp = reader.readLine()) != null) {
			realUrl.append(temp);
		}
		reader.close();
		connection.disconnect();
		return realUrl.toString();
	}
	public void ExtendExcel(HttpServletResponse httpResponse,List<ActionCommonImageBean> list
    		) throws IOException {
		SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
    	// 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook(); 
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("审核");

        sheet.setColumnWidth(0, 15 * 256);
        sheet.setColumnWidth(1, 10 * 256);
        sheet.setColumnWidth(2, 12 * 256);
        sheet.setColumnWidth(3, 20* 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 20 * 256);
        sheet.setColumnWidth(7, 20 * 256);
        sheet.setColumnWidth(8, 20 * 256);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        ArrayList<String> cellList = new ArrayList<String>();
//        cellList.add("");
        cellList.add("编号");
        cellList.add("账号");
        cellList.add("区组");
        cellList.add("角色名");
        cellList.add("作品名");
        cellList.add("上传时间");
        cellList.add("作品详情");
        cellList.add("点赞数");
        cellList.add("状态");
        

        HSSFCell cell = row.createCell(0);
        for (int i = 0; i < cellList.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(cellList.get(i));
            cell.setCellStyle(style);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        String GroupName = "";
        int j=0;
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow((int) i + 1);
            ActionCommonImageBean info = (ActionCommonImageBean) list.get(i);


            // 第四步，创建单元格，并设置值
            row.createCell(0).setCellValue(info.getCode());
            row.createCell(1).setCellValue(info.getWdDatingPet().getAccount());
            row.createCell(2).setCellValue(info.getWdDatingPet().getServer());
            row.createCell(3).setCellValue(info.getWdDatingPet().getCharacter());
            row.createCell(4).setCellValue(info.getWdDatingPet().getPetName());
            if(info.getWdDatingPet().getUploadDate()!=null){
            	
            row.createCell(5).setCellValue(sdf.format(info.getWdDatingPet().getUploadDate()));
            }
            row.createCell(6).setCellValue(info.getTempUrl());
            row.createCell(7).setCellValue(info.getWdDatingPet().getAdmired());
            row.createCell(8).setCellValue(info.getStatus());
            j++;
 
        }
       

        // 第六步，将文件存到指定位置

        String fileName = "check.xls";
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
