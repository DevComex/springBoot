package cn.gyyx.wd.wanjia.cartoon.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import cn.gyyx.wd.wanjia.ResultBean;
import cn.gyyx.wd.wanjia.cartoon.beans.Constans;
import cn.gyyx.wd.wanjia.cartoon.beans.ManhuaInfoBean;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdRecommend;
import cn.gyyx.wd.wanjia.cartoon.beans.tools.ExcelTools;
import cn.gyyx.wd.wanjia.cartoon.beans.tools.MessageEnum;
import cn.gyyx.wd.wanjia.cartoon.beans.tools.ReCommendEnum;
import cn.gyyx.wd.wanjia.cartoon.bll.NewPageByCodeBll;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdRecommendMapper;
@Service
public class RecommendService {
	@Autowired
	private WanwdRecommendMapper recommendMapper;
	@Autowired
	private WanwdManhuaMapper manhuaMapper;
	@Autowired
	private NewPageByCodeBll newPageByCodeBll ;
	@Autowired
	private MessageService messageService;
	public ResultBean<Object> saveFields(List<Integer> list, Integer manhuaCode, Integer rewarLevel) {
		ResultBean<Object> resultBean = new ResultBean<>(false, "fail");
		String message = "";
		WanwdManhua wanwdManhua = manhuaMapper.selectByPrimaryKey(manhuaCode);
		if(wanwdManhua==null){
			resultBean.setProperties(false, "该漫画查询不到",null);
			return resultBean;
		}
		try {
			for (Integer localtion : list) {
				int displayOrder=recommendMapper.selectByLocaltion(localtion);
				//该漫画是否已推送
				WanwdRecommend wanwdRecommend=recommendMapper.selectByLocaltionAndManhuaCode(localtion,wanwdManhua.getCode());
				if(wanwdRecommend!=null){
					message+=" 该漫画在"+ReCommendEnum.getName(localtion)+"推荐位已推荐过  !";
					continue;
				}
				//道姐推荐栏
				if(localtion==ReCommendEnum.RECOMMEND_DAOJIE_ING.getIndex()){
					if(wanwdManhua.getIsClosed()!=0){
						message+=" 该漫画已完结,不可推送到"+ReCommendEnum.getName(localtion)+" ;";;
						continue;
					}
				}else if(localtion==ReCommendEnum.RECOMMEND_DAOJIE_OVER.getIndex()){
					if(wanwdManhua.getIsClosed()!=1){
						message+=" 该漫画未完结,不可推送到"+ReCommendEnum.getName(localtion)+" ;";;
						continue;
					}
				};
				
				WanwdRecommend recommend = new WanwdRecommend();
				Date date = new Date();
				
				recommend.setContentId(manhuaCode);
				recommend.setContentType(Constans.MANHUA_CONTENT_TYPE);
				recommend.setDisplayOrder(displayOrder+1);
				recommend.setIsDelete(false);
				recommend.setIsDisplay(true);
				recommend.setLocationId(localtion);
				recommend.setTitle(wanwdManhua.getTitle());
				recommend.setUpdateTime(date);
				recommend.setCreateTime(date);
				recommend.setUrl(MessageFormat.format(Constans.RECOMMEND_URL, manhuaCode));
				if(rewarLevel!=null){
					recommend.setThumbnail(String.valueOf(rewarLevel));
				}
				int i = recommendMapper.insertSelective(recommend);
				message+=" 该漫画成功推送到"+ReCommendEnum.getName(localtion)+" ;";
				//添加消息
				boolean b = saveMessage(wanwdManhua, MessageEnum.REWAR_LEVEL, rewarLevel);
				
			}
		} catch (Exception e) {
			resultBean.setProperties(false, "内部错误", null);
			e.printStackTrace();
			return resultBean;
		}
		resultBean.setProperties(true, message, null);
		return resultBean;
		
	}
	private boolean saveMessage(WanwdManhua manhua, MessageEnum messageEnum,Integer rewarLevel) {
		String context = MessageFormat.format(messageEnum.getName(),manhua.getTitle(),rewarLevel);
		return messageService.saveMessage(manhua.getAuthorId(), manhua.getTitle(), manhua.getCode(), messageEnum.getIndex(),
				context);
	}
	public List<WanwdRecommend> getManhuaFields(Integer manhuaCode) {
		
		return recommendMapper.getManhuaFields(manhuaCode);
	}
	public List<WanwdRecommend> getFieldsListByIndex(Integer location, Integer leavel) {
		Map<String, Object> map = new HashMap<>();
		if(!location.equals(-1)){
			map.put("location", location);
		}
		if(!leavel.equals(-1)){
			map.put("leavel", leavel);
		}
		List<WanwdRecommend> list = recommendMapper.getFieldsListByLocaltionId(map);
		for (WanwdRecommend wanwdRecommend : list) {
			ManhuaInfoBean infoBean = newPageByCodeBll.findNewPageByCode(wanwdRecommend.getContentId());
			if(infoBean!=null){
				wanwdRecommend.setInfoBean(infoBean);
			}
		}
		
		return list;
	}
	public ResultBean<Object> fieldsDisplay(Integer code) {
		ResultBean<Object> resultBean = new ResultBean<>(false, "fail");
		WanwdRecommend recommend = recommendMapper.selectByPrimaryKey(code);
		if(recommend==null){
			resultBean.setMessage("该数据不存在");
			return resultBean;
		}
		if(recommend.getIsDisplay()){
			recommend.setIsDisplay(false);
			recommendMapper.updateByPrimaryKey(recommend);
		}else{
			recommend.setIsDisplay(true);
			recommendMapper.updateByPrimaryKey(recommend);
			
		}
		resultBean.setMessage("修改展示状态成功");
		resultBean.setSuccess(true);
		return resultBean;
	}
	public ResultBean<Object> fieldsDelete(Integer code) {
		ResultBean<Object> resultBean = new ResultBean<>(false, "fail");
		WanwdRecommend recommend = recommendMapper.selectByPrimaryKey(code);
		if(recommend==null){
			resultBean.setMessage("该数据不存在");
			return resultBean;
		}
			recommend.setIsDelete(true);
			recommendMapper.updateByPrimaryKey(recommend);
		resultBean.setMessage("删除成功");
		resultBean.setSuccess(true);
		return resultBean;
	}
	public ResultBean<Object> fieldsMove(Integer code, String type) {
		ResultBean<Object> resultBean = new ResultBean<>(false, "fail");
		WanwdRecommend recommend = recommendMapper.selectByPrimaryKey(code);
		if(recommend==null){
			resultBean.setMessage("该数据不存在!");
			return resultBean;
		}
		Integer order = recommend.getDisplayOrder();
		Integer location = recommend.getLocationId();
		switch (type) {
		case "UP"://上移
			WanwdRecommend upRecommend=recommendMapper.findByLocaltionAndOrderId(location,order-1);
			if(order==1){
				resultBean.setMessage("该数据已经在顶部!");
				return resultBean;
			}
			recommend.setDisplayOrder(order-1);
			recommendMapper.updateByPrimaryKeySelective(recommend);//本数据上移一位
			
			if(upRecommend!=null){
				upRecommend.setDisplayOrder(order);
				recommendMapper.updateByPrimaryKeySelective(upRecommend);//上一条数据 下移
			}
			resultBean.setMessage("该数据上移成功!");
			resultBean.setSuccess(true);
			break;
		case "DOWN":
			WanwdRecommend downRecommend=recommendMapper.findByLocaltionAndOrderId(location,order+1);//获取下一位置数据
			if(downRecommend==null){
				resultBean.setMessage("该数据已经在底部!");
				return resultBean;
			}else{
				recommend.setDisplayOrder(order+1);
				recommendMapper.updateByPrimaryKeySelective(recommend);//本数据上移一位
				downRecommend.setDisplayOrder(order);
				recommendMapper.updateByPrimaryKeySelective(downRecommend);//上一条数据 下移
			}
			resultBean.setMessage("该数据下移成功!");
			resultBean.setSuccess(true);
			break;
		case "TOP":
			if(order==1){
				resultBean.setMessage("该数据已经在最顶部!");
				return resultBean;
			}
			LinkedList<WanwdRecommend> topList = recommendMapper.getLinkListByLocaltionId(location);
			 for (Iterator iterator = topList.iterator(); iterator.hasNext();) {
				WanwdRecommend wanwdRecommend = (WanwdRecommend) iterator.next();
				if(wanwdRecommend.getDisplayOrder()==order){
					iterator.remove();
				}
			}
			topList.addFirst(recommend);
			for (int i = 1; i <= topList.size(); i++) {
				WanwdRecommend topRecommend = topList.get(i-1);
				topRecommend.setDisplayOrder(i);
				recommendMapper.updateByPrimaryKeySelective(topRecommend);
			} 
			 
			resultBean.setMessage("该数据置顶成功!");
			resultBean.setSuccess(true);
			break;
		case "BOTTOM":
			LinkedList<WanwdRecommend> bottomList = recommendMapper.getLinkListByLocaltionId(location);
			if(bottomList.size()==order){
				resultBean.setMessage("该数据已经在最底部!");
				return resultBean;
			}
			 for (Iterator iterator2 = bottomList.iterator(); iterator2.hasNext();) {
				WanwdRecommend wanwdRecommend = (WanwdRecommend) iterator2.next();
				if(wanwdRecommend.getDisplayOrder()==order){
					iterator2.remove();
				}
			}
			bottomList.addLast(recommend);
			for (int i = 1; i <= bottomList.size(); i++) {
				WanwdRecommend topRecommend = bottomList.get(i-1);
				topRecommend.setDisplayOrder(i);
				recommendMapper.updateByPrimaryKeySelective(topRecommend);
			} 
			 
			resultBean.setMessage("该数据置底成功!");
			resultBean.setSuccess(true);
			break;
		case "DELETE":
			LinkedList<WanwdRecommend> deleteList = recommendMapper.getLinkListByLocaltionId(location);
			if(recommend.getIsDisplay()){
				resultBean.setMessage("数据状态为已展示,不能删除!");
				return resultBean;
			}
			 for (Iterator iterator2 = deleteList.iterator(); iterator2.hasNext();) {
				WanwdRecommend wanwdRecommend = (WanwdRecommend) iterator2.next();
				if(wanwdRecommend.getDisplayOrder()==order){
					wanwdRecommend.setIsDelete(true);
					wanwdRecommend.setDisplayOrder(-1);
					recommendMapper.updateByPrimaryKeySelective(wanwdRecommend);
					iterator2.remove();
				}
			}
			for (int i = 1; i <= deleteList.size(); i++) {
				WanwdRecommend deleteRecommend = deleteList.get(i-1);
				deleteRecommend.setDisplayOrder(i);
				recommendMapper.updateByPrimaryKeySelective(deleteRecommend);
			} 
			 
			resultBean.setMessage("该数据删除成功!");
			resultBean.setSuccess(true);
			break;
		default:	
			resultBean.setMessage("type值只能为:UP DOWN TOP BOTTOM DELETE");
			resultBean.setSuccess(false);
		}
		return resultBean;
	}
 
	public List<Map<String, Object>>   recommendHistory(Integer pageIndex, Integer pageSize,Integer location, int isClosed, int leavel, String account,
			String title) {
		Map<String, Object> map = new HashMap<>();
		if(pageIndex !=null){
			int start = (pageIndex - 1) * pageSize + 1;
	        int end = pageIndex * pageSize;
			map.put("start", start);
			map.put("end", end);
		}
		if(location!=null){
			map.put("location", location);
		}else{
			map.put("locationList", getIndexList());
		}
		 
		if(isClosed!=-1){
			map.put("isClosed", isClosed);
		}
		if(leavel!=-1){
			map.put("leavel", leavel);
		}
		if(!account.equals("")){
			map.put("account", account);
		}
		if(!title.equals("")){
			map.put("title", title);
		}
		List<Map<String, Object>> list = recommendMapper.selectRecommendHistory(map);
		for (Map<String, Object> map2 : list) {
			if(map2.get("location_id")!=null){
				int object = (int)map2.get("location_id");
				map2.put("location", ReCommendEnum.getName(object));
			}
		}
		return list;
	}
	public ResultBean<Object> editFields(List<Integer> list, Integer manhuaCode, Integer rewarLevel) {
		ResultBean<Object> resultBean = new ResultBean<>(false, "fail");
		String message = "";
		//查询原有漫画推荐位 并删除
		WanwdManhua wanwdManhua = manhuaMapper.selectByPrimaryKey(manhuaCode);
		if(wanwdManhua==null||wanwdManhua.getCode()==null){
			resultBean.setMessage("该漫画无数据");
			return resultBean;
		}
		try {
			recommendMapper.deleteByManhuaCode(manhuaCode);
			for (Integer localtion : list) {
				//最大排序
				int displayOrder=recommendMapper.selectByLocaltion(localtion);
				//道姐推荐栏
				if(localtion==ReCommendEnum.RECOMMEND_DAOJIE_ING.getIndex()){
					if(wanwdManhua.getIsClosed()!=0){
						message+=" 该漫画已完结,不可推送到"+ReCommendEnum.getName(localtion)+" ;";;
						continue;
					}
				}else if(localtion==ReCommendEnum.RECOMMEND_DAOJIE_OVER.getIndex()){
					if(wanwdManhua.getIsClosed()!=1){
						message+=" 该漫画未完结,不可推送到"+ReCommendEnum.getName(localtion)+" ;";;
						continue;
					}
				};
				
				WanwdRecommend recommend = new WanwdRecommend();
				Date date = new Date();
				
				recommend.setContentId(manhuaCode);
				recommend.setContentType(Constans.MANHUA_CONTENT_TYPE);
				recommend.setDisplayOrder(displayOrder+1);
				recommend.setIsDelete(false);
				recommend.setIsDisplay(true);
				recommend.setLocationId(localtion);
				recommend.setTitle(wanwdManhua.getTitle());
				recommend.setUpdateTime(date);
				recommend.setCreateTime(date);
				recommend.setUrl(MessageFormat.format(Constans.RECOMMEND_URL, manhuaCode));
				if(rewarLevel!=null){
					recommend.setThumbnail(String.valueOf(rewarLevel));
				}
				recommendMapper.insertSelective(recommend);
				message+="该漫画推送到"+ReCommendEnum.getName(localtion)+" ;";;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setMessage("内部错误");
			return resultBean;
		}
		resultBean.setProperties(true, message,null);
		return resultBean;
	}
	
	public void editRemark(Integer code, String remark) {
		WanwdRecommend recommend = new WanwdRecommend();
		recommend.setCode(code);
		recommend.setRemark(remark);
		
		recommendMapper.updateByPrimaryKeySelective(recommend);
		
	}
	private String getIndexList() {
		String str ="";
		str+=(ReCommendEnum.RECOMMEND_NEW.getIndex())+",";
		 
		str+=(ReCommendEnum.RECOMMEND_LIULAN.getIndex())+",";
		str+=(ReCommendEnum.RECOMMEND_DAOJIE_OVER.getIndex())+",";
		str+=(ReCommendEnum.RECOMMEND_DAOJIE_ING.getIndex())+",";
		str+=(ReCommendEnum.RECOMMEND_LOOK.getIndex());
		return str;
	}
	
	/**
	 * 导出结果Excel
	 * @param title 
	 * @param account 
	 * @param leavel 
	 * @param isClosed 
	 */
	public Workbook exportPerformanceResultExcel(HttpServletRequest request, Integer isClosed, Integer leavel, String account, String title) throws Exception{
		//获取文件
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/WEB-INF/template/performance_template.xlsx");
		//logger.info("文件path路径：{}",path);
		System.out.println("文件path路径：{}"+path);
		File f = new File(path);
		if(f == null || !f.exists()){
			throw new Exception("导出绩效结果模板文件未找到!");
		}
		InputStream inputStream = new FileInputStream(f);
		Workbook workbook = WorkbookFactory.create(inputStream);
		int j=2;//从第二行开始
		int cols = 9;//默认9列
		List<Map<String,Object>> history = this.recommendHistory(null,null,null, isClosed, leavel, account, title);
		 
		Sheet sheet = workbook.getSheetAt(0);////获取第一个sheet 
		for (int i = 0; i < history.size(); i++) {
			
			
			Row row = sheet.getRow(j);//从第二行开始
			if(row == null){
				row = sheet.createRow(j);
			}
			if(j!=2){
				Row rowPre = sheet.getRow(j-1);
				row.setHeight(rowPre.getHeight());
				//复制上一行
				ExcelTools.copyRow(workbook, rowPre, row, false);
			}
			//设置值
			for(int m=0;m<cols;m++){
				Cell cell = row.getCell(m);
				Object value = null;
				switch (m) {
					case 0:
						value = history.get(i).get("title");
						break;
					case 1:
						value = history.get(i).get("create_time");
						break;
					case 2:
						value =  history.get(i).get("author_account");
						break;
					case 3:
						value =history.get(i).get("read_count");
						break;
					case 4:
						String is_Closed=history.get(i).get("is_closed").equals("1")?"已完结":"未完结";
						value = is_Closed;
						break;
					case 5:
						value = history.get(i).get("url");
						break;
					case 6:
						value = "等级 "+history.get(i).get("thumbnail");
						break;
					case 7:
						int locationId = (int) history.get(i).get("location_id");
						value = ReCommendEnum.getName(locationId);
						break;
					case 8:
						value = history.get(i).get("remark");
						break;
					default:
						break;
				}
				ExcelTools.setCellValue(cell, value);
			}
			j++;
			
		}
		
		return workbook;
	}
	public int recommendHistoryCount(Integer location, Integer isClosed, Integer leavel, String account, String title) {
		Map<String, Object> map = new HashMap<>();
		map.put("start", 1);
		map.put("end", 20);
		if(location!=null){
			map.put("location", location);
		}else{
			map.put("locationList", getIndexList());
		}
		 
		if(isClosed!=-1){
			map.put("isClosed", isClosed);
		}
		if(leavel!=-1){
			map.put("leavel", leavel);
		}
		if(!account.equals("")){
			map.put("account", account);
		}
		if(!title.equals("")){
			map.put("title", title);
		}
		return recommendMapper.selectRecommendHistoryCount(map);
		
	}
	
}
