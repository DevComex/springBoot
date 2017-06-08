/*************************************************
       Copyright ©, 2015, GY Game
       Author: 柳佳琦
       Created: 2016年3月24日
       Note：问道名人争霸赛评论业务拼装层.
************************************************/
package cn.gyyx.action.service.wdchampionship;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import cn.gyyx.action.beans.PageBean;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdchampionship.ChampionshipCommenttInfoBean;
import cn.gyyx.action.bll.wdchampionship.ChampionshipCommentInfoBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: ChampionshipCommentInfoService
 * @Description: TODO 问道名人争霸赛评论业务拼装层.
 * @author 柳佳琦 liujiaqi@gyyx.cn
 * @date 2016年3月24日 下午4:47:10
 */
public class ChampionshipCommentInfoService {
	private ChampionshipCommentInfoBLL commentBLL = new ChampionshipCommentInfoBLL();
	private static final Logger LOG = GYYXLoggerFactory.getLogger(ChampionshipCommentInfoService.class);
	
	/**
	* @Title: addComment
	* @Description: TODO 添加评论
	* @date 2016年3月24日 下午4:58:59
	* @param comment   
	* @return ResultBean<String>
	 */
	public ResultBean<String> addComment(ChampionshipCommenttInfoBean comment) {
		ResultBean<String> result = new ResultBean<String>(false, null, null);
		Date endTime;
		try {
			endTime = new SimpleDateFormat("yyyy-MM-dd").parse("2016-05-30");
		} catch (ParseException e) {
			LOG.info(e.toString());
			result.setMessage("请求失败");
			return result;
		}
		if(endTime.before(new Date())){
			result.setMessage("活动已经结束");
			return result;
		}
		// 参数校验
		if(StringUtils.isEmpty(comment.getNickName())){
			result.setMessage("请输入昵称");
			return result;
		}
		if(comment.getNickName().length() > 8){
			result.setMessage("昵称不符合规定");
			return result;
		}
		if(StringUtils.isEmpty(comment.getCommentContent())){
			result.setMessage("请输入评论内容");
			return result;
		}
		if(comment.getCommentContent().length() > 15){
			result.setMessage("评论内容最大长度是15个字符");
			return result;
		}
		LOG.info("addComment parameter verification pass ,comment: " + comment);
		if(commentBLL.addComment(comment)){
			result.setIsSuccess(true);
			result.setMessage("评论提交成功,正在审核...");
		}else{
			result.setIsSuccess(true);
			result.setMessage("评论提交失败");
		}
		LOG.info("addComment function invoke finished... Result :"  + result);
		return result;
	}
	
	
	/**
	* @Title: selectComment
	* @Description: TODO 分页查询评论信息
	* @date 2016年3月24日 下午5:22:01
	* @param @param isDel
	* @param @param typeOfYear
	* @param @param currentPage
	* @param @return    
	* @return ResultBean<PageBean<ChampionshipCommenttInfoBean>>
	 */
	public ResultBean<PageBean<ChampionshipCommenttInfoBean>> selectComment(
			boolean isDel,int typeOfYear,int currentPage) {
		ResultBean<PageBean<ChampionshipCommenttInfoBean>> result = 
				new ResultBean<PageBean<ChampionshipCommenttInfoBean>>(false, null, null);
		if(typeOfYear < 1){//参数校验
			result.setMessage("届数不合法");
			return result;
		}
		int pageSize = 8;
		LOG.info("selectComment invoke get result of null;parameter :"+ isDel + ", " + typeOfYear
					+ ", " + currentPage + ", " + pageSize);
		List<ChampionshipCommenttInfoBean> list = commentBLL
				.selectComment(isDel, typeOfYear, currentPage, pageSize);
		if(CollectionUtils.isEmpty(list)){
			result.setMessage("没有符合条件的数据");
			LOG.info("selectComment invoke get a result of null...");
		}else{
			int count = commentBLL.selectCommentCount(isDel, typeOfYear);
			LOG.info("selectCommentCount invoke get the result is: " + count);
			PageBean<ChampionshipCommenttInfoBean> page = PageBean.createPage(count, currentPage, pageSize, list);
			result.setData(page);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	* @Title: selectTopComment
	* @Description: TODO 查询最新的前99条审核通过的评论
	* @date 2016年3月24日 下午5:29:19
	* @param @param typeOfYear
	* @param @return    
	* @return ResultBean<List<ChampionshipCommenttInfoBean>>
	 */
	public ResultBean<List<ChampionshipCommenttInfoBean>> selectTopComment(int typeOfYear) {
		ResultBean<List<ChampionshipCommenttInfoBean>> result = 
				new ResultBean<List<ChampionshipCommenttInfoBean>>(false, null, null);
		if(typeOfYear < 1){//参数校验
			result.setMessage("届数不合法");
			return result;
		}
		LOG.info("parameter verification pass;parameter :" +  typeOfYear);
		List<ChampionshipCommenttInfoBean> list = commentBLL.selectTopComment(typeOfYear);
		if(CollectionUtils.isEmpty(list)){
			LOG.info("selectTopComment invoke get result of null;parameter :" +  typeOfYear);
			result.setMessage("没有符合条件的数据");
			return result;
		}
		result.setData(list);
		result.setIsSuccess(true);
		return result;
	}
}
