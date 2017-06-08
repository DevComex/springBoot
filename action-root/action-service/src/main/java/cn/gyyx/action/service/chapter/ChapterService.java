/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：范佳琪 
 * 联系方式：fanjiaqi@gyyx.cn 
 * 创建时间：2015年12月17日
 * 版本号：v1.0
 * 本类主要用途叙述：章节的service
 */
package cn.gyyx.action.service.chapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import cn.gyyx.action.beans.chapter.ChapterBean;
import cn.gyyx.action.bll.chapter.ChapterBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.security.MD5;

public class ChapterService {

	private ChapterBLL chapterBLL = new ChapterBLL();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ChapterBLL.class);

	/**
	 * @Title: insertChapter
	 * @Description: TODO 添加章节信息
	 * @param @param chapterBean    
	 * @return void    
	 * @throws
	 */
	public void insertChapter(ChapterBean chapterBean) {
		logger.debug("chapterBean", chapterBean);
		chapterBLL.insertChapter(chapterBean);
	}

	/**
	 * @Title: getChapterBeanFromWebApi
	 * @Description: TODO 调用外部接口获取全部章节信息
	 * @param @param chapterCode
	 * @param @return
	 * @param @throws Exception    
	 * @return List<ChapterBean>    
	 * @throws
	 */
	public List<ChapterBean> getChapterBeanFromWebApi(int chapterCode)
			throws Exception {
		logger.debug("chapterCode", chapterCode);
		String sign = getMD5ByChapterCode(chapterCode);
		logger.debug("sign", sign);
		String url = "http://api.zongheng.com/commonrest?method=chapter.list&book_id=347511&"
				+ "api_key=wqpt7w8h93&sig="
				+ sign
				+ "&cursor="
				+ chapterCode
				+ "&pageSize=10";
		logger.debug("url", url);
		RestClient client = new RestClient();
		try {
			org.apache.wink.client.Resource resource = client.resource(url);
			ClientResponse response = (resource).get();
			// 接收返回响应体
			String result = response.getEntity(String.class);
			JSONObject jsonObject = JSONObject.fromObject(result);
			List<ChapterBean> chapterBeans = new ArrayList<ChapterBean>();
			for (int i = 0; i < jsonObject.getJSONArray("result").size(); i++) {
				JSONObject resultObj = (JSONObject) jsonObject.getJSONArray(
						"result").get(i);
				ChapterBean chapterBean = new ChapterBean();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String createTime = resultObj.getString("createTimeStr");
				String updateTime = resultObj.getString("updateTimeStr");
				chapterBean.setChapterCode(resultObj.getInt("chapterId"));
				chapterBean.setChapterName(resultObj.getString("chapterName"));
				chapterBean.setContent(resultObj.getString("content"));
				chapterBean.setWordNum(resultObj.getInt("wordNum"));
				chapterBean.setCreateTimeStr(dateFormat.parse(createTime));
				chapterBean.setUpdateTimeStr(dateFormat.parse(updateTime));
				chapterBean.setStatus(resultObj.getString("status"));
				chapterBeans.add(chapterBean);
			}
			return chapterBeans;

		} catch (Exception e) {
			logger.warn(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * @Title: getMD5ByChapterCode
	 * @Description: TODO 通过章节Code得到签名，用于获取全部章节信息
	 * @param @param chapterCode
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	private String getMD5ByChapterCode(int chapterCode) {
		logger.debug("chapterCode", chapterCode);
		String integtationURL = "rfuomg424xbdik2gapi_keywqpt7w8h93book_id347511cursor"
				+ chapterCode + "methodchapter.listpageSize10rfuomg424xbdik2g";
		logger.debug("integtationURL", integtationURL);
		return MD5.encode(integtationURL);
	}
	
	/**
	 * @Title: getFirstChapterBeanFromWebApi
	 * @Description: TODO 调用外部接口获取第一章节信息
	 * @param @return
	 * @param @throws Exception    
	 * @return ChapterBean    
	 * @throws
	 */
	public ChapterBean getFirstChapterBeanFromWebApi() throws Exception {
		String sign = getMD5();
		logger.debug("sign", sign);
		String url = "http://api.zongheng.com/commonrest?method=chapter.first&"
				+ "book_id=347511&api_key=wqpt7w8h93&sig=" + sign;
		logger.debug("url", url);
		RestClient client = new RestClient();
		try {
			org.apache.wink.client.Resource resource = client.resource(url);
			ClientResponse response = (resource).get();
			// 接收返回响应体
			String result = response.getEntity(String.class);
			JSONObject jsonObject = JSONObject.fromObject(result);
			JSONObject resultObj = (JSONObject) jsonObject.getJSONObject("result");
			ChapterBean chapterBean = new ChapterBean();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String createTime = resultObj.getString("createTimeStr");
			String updateTime = resultObj.getString("updateTimeStr");
			chapterBean.setChapterCode(resultObj.getInt("chapterId"));
			chapterBean.setChapterName(resultObj.getString("chapterName"));
			chapterBean.setContent(resultObj.getString("content"));
			chapterBean.setWordNum(resultObj.getInt("wordNum"));
			chapterBean.setCreateTimeStr(dateFormat.parse(createTime));
			chapterBean.setUpdateTimeStr(dateFormat.parse(updateTime));
			chapterBean.setStatus(resultObj.getString("status"));
			return chapterBean;

		} catch (Exception e) {
			logger.warn(e.getMessage());
			throw e;
		}

	}
	
	/**
	 * @Title: getMD5
	 * @Description: TODO 获得签名，用于获取第一章节信息
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	private String getMD5() {
		String integtationURL = "rfuomg424xbdik2gapi_keywqpt7w8h93book_id347511"
				+ "methodchapter.firstrfuomg424xbdik2g";
		logger.debug("integtationURL", integtationURL);
		return MD5.encode(integtationURL);
	}
	
	/**
	 * @Title: getAllChapterForPage
	 * @Description: TODO 分页获取目录信息
	 * @param @param pageSize
	 * @param @param currentPage
	 * @param @return    
	 * @return List<ChapterBean>    
	 * @throws
	 */
	public List<ChapterBean> getAllChapterForPage(Integer pageSize,Integer currentPage){
		logger.debug("pageSize",pageSize);
		logger.debug("currentPage", currentPage);
		return chapterBLL.getAllChapterForPage(pageSize, currentPage);
	}
	
	/**
	 * @Title: getCount
	 * @Description: TODO 获取表中记录总数
	 * @param @return    
	 * @return int    
	 * @throws
	 */
	public int getCount(){
		return chapterBLL.getCount();
	}
	
	/**
	 * @Title: getChapterByCode
	 * @Description: TODO 通过主键查找章节信息
	 * @param @param chapterCode
	 * @param @return    
	 * @return ChapterBean    
	 * @throws
	 */
	public ChapterBean getChapterByCode(int chapterCode){
		logger.debug("chapterCode", chapterCode);
		return chapterBLL.getChapterByCode(chapterCode);
	}
	
	/**
	 * 
	 * @Title: getChapterCode
	 * @Description: TODO 根据orderNum获取章节Code
	 * @param @param orderNum
	 * @param @return    
	 * @return int    
	 * @throws
	 */
	public int getChapterCode(int orderNum){
		logger.debug("orderNum",orderNum);
		return chapterBLL.getChapterCode(orderNum);
	}
}
