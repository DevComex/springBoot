/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：范佳琪 
 * 联系方式：fanjiaqi@gyyx.cn 
 * 创建时间：2015年12月17日
 * 版本号：v1.0
 * 本类主要用途叙述：小说连载控制器
 */
package cn.gyyx.action.ui.chapter;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.chapter.CatalogBean;
import cn.gyyx.action.beans.chapter.ChapterBean;
import cn.gyyx.action.beans.chapter.ContentInfoBean;
import cn.gyyx.action.service.chapter.ChapterService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: ChapterHandler
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author fanjiaqi fanjiaqi@gyyx.cn
 * @date 2015年12月14日 下午6:29:55
 *
 */
@Controller
@RequestMapping("/chapter")
public class ChapterController {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ChapterController.class);
	private ChapterService chapterService = new ChapterService();
	
	/**
	 * @Title: index
	 * @Description: TODO 目录页
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	@RequestMapping("index")
	public String index(){
		return "zjjsNovel/index";
	}
	
	/**
	 * 
	 * @Title: info
	 * @Description: TODO 章节信息页
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	@RequestMapping("info")
	public String info(){
		return "zjjsNovel/info";
	}
	
	/**
	 * @Title: insertChapter
	 * @Description: TODO 添加全部章节信息
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	/*public void insertChapter(){
		try {
			ChapterBean firstChapterBean = new ChapterBean();
			firstChapterBean = chapterService.getFirstChapterBeanFromWebApi();
			chapterService.insertChapter(firstChapterBean);
			int chapterCode = firstChapterBean.getChapterCode();
			while(true){
				List<ChapterBean> chapterBeans = chapterService.getChapterBeanFromWebApi(chapterCode);
				for (ChapterBean chapterBean : chapterBeans) {
					chapterService.insertChapter(chapterBean);
					if(chapterBean.equals(chapterBeans.get(chapterBeans.size()-1))){
						chapterCode = chapterBean.getChapterCode();
					}
				}
				if(chapterBeans.size() < 10){
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	/**
 	 * @Title: getAllChapterForPage
	 * @Description: TODO 分页获取章节列表
	 * @param @param pageSize
	 * @param @param currentPage
	 * @param @return    
	 * @return ResultBean<CatalogBean>    
	 * @throws
	 */
	@RequestMapping("/getAllChapterForPage")
	@ResponseBody
	public ResultBean<CatalogBean> getAllChapterForPage(
			@RequestParam("pageSize") Integer pageSize,
			@RequestParam("currentPage") Integer currentPage){
		logger.debug("pageSize",pageSize);
		logger.debug("currentPage",currentPage);
		ResultBean<CatalogBean> result = new ResultBean<CatalogBean>(false,"章节获取失败",null);
		List<ChapterBean> chapterBeans = chapterService.getAllChapterForPage
				(pageSize, currentPage);
		logger.debug("chapterBeans",chapterBeans);
		int count = chapterService.getCount();
		logger.debug("count",count);
		if(null != chapterBeans && 0 != count){
			CatalogBean catalogBean = new CatalogBean(chapterBeans, count);
			result.setProperties(true, "章节获取成功", catalogBean);
		}
		return result;
	}
	
	/**
	 * @Title: getChapterInfo
	 * @Description: TODO 获取章节信息
	 * @param @param chapterCode
	 * @param @return    
	 * @return ResultBean<ContentInfoBean>    
	 * @throws
	 */
	@RequestMapping("/getChapterInfo")
	@ResponseBody
	public ResultBean<ContentInfoBean> getChapterInfo(@RequestParam("chapterCode") int chapterCode){
		logger.debug("chapterCode",chapterCode);
		ResultBean<ContentInfoBean> result = new ResultBean<ContentInfoBean>(false,"章节信息获取失败",null);
		ChapterBean chapterBean = chapterService.getChapterByCode(chapterCode);
		logger.debug("chapterBean", chapterBean);
		if(null != chapterBean){
			int orderNum = chapterBean.getOrderNum();
			logger.debug("orderNum",orderNum);
			int lastChapterCode = chapterService.getChapterCode(orderNum - 1);
			int nextChapterCode = chapterService.getChapterCode(orderNum + 1);
			logger.debug("lastChapterCode",lastChapterCode);
			logger.debug("nextChapterCode",nextChapterCode);
			ContentInfoBean contentInfoBean = new ContentInfoBean(chapterBean.getContent(),orderNum,
					lastChapterCode, nextChapterCode);
			result.setProperties(true, "章节信息获取成功", contentInfoBean);
		}
		return result;
	}
}
