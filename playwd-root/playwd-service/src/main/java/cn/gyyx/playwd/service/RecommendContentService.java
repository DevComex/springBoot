package cn.gyyx.playwd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean.State;
import cn.gyyx.playwd.beans.playwd.RecommendContentBean;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.bll.RecommendContentBll;
import cn.gyyx.playwd.utils.StringTools;

/**
 * 推荐区域信息服务类
 * @author yangteng
 *
 */
@Component
public class RecommendContentService {

	@Autowired
	public RecommendContentBll recommendContentBll;
	
	@Autowired
	public ArticleBll articleBll;
	
	/**
	 * 编辑信息
	 * @return
	 */
	public ResultBean<String> update(RecommendContentBean model) {
		ResultBean<String> result = new ResultBean<String>();

		String url = model.getUrl();
		
		if (url != null) {
			// 当前编辑的url如果是文章，必须为审核通过或推荐状态才能显示
			String str = url.substring(url.lastIndexOf("/")+1);
			if(StringTools.isNumeric(str)){
				model.setContentId(Integer.parseInt(str)); 
				ArticleBean article = articleBll.getArticleById(model.getContentId());
				if(article==null){
					model.setContentId(0);
				}
				else if ((article.getStatus().equals(State.unreviewd.toString())
						|| article.getStatus().equals(State.failed.toString())
						|| article.getStatus().equals(State.hidden.toString()))) {
					result.setProperties(false, "该文章未被推荐,请重新选择", null);
					return result;
				}
			}else {
				model.setContentId(0);
			}
		}

		int row = recommendContentBll.update(model);
		if (row <= 0) {
			result.setProperties(false, "更新失败", null);
			return result;
		}

		result.setProperties(true, "更新成功", null);
		return result;
	}
	
	/**
	 * 推荐内容向上移动或向下移动
	 * @param code
	 * @param type 向上移动up,向下移动down
	 * @return
	 */
	public ResultBean<String> move(Integer code, String type) {

		ResultBean<String> result = new ResultBean<String>();

		// 查询推荐区域信息
		RecommendContentBean contentBean = recommendContentBll.getInfo(code);
		if (contentBean == null) {
			result.setMessage("推荐区域信息不存在");
			return result;
		}
		
		Integer displayOrder = contentBean.getDisplayOrder();
		if (type.equals("moveUp") || type.equals("moveTop")) {
			
			if (displayOrder <= 1) {
				result.setMessage(type.equals("moveUp") ? "推荐区域信息不能向上移动"
						: "推荐区域信息不能置顶");
				return result;
			}

			if (type.equals("moveUp")) {
				return move(contentBean, displayOrder - 1, displayOrder);
			}

			return moveTop(contentBean, displayOrder);

		} else if (type.equals("moveDown") || type.equals("moveBottom")) {
			// 查询最大的排序
			Integer maxDisplayOrder = recommendContentBll
					.getDisplayOrder(contentBean.getRecommendSlotId());
			if (displayOrder >= maxDisplayOrder) {
				result.setMessage(type.equals("moveDown") ? "推荐区域信息不能向下移动"
						: "推荐区域信息不能置底");
				return result;
			}

			if (type.equals("moveDown")) {
				return move(contentBean, displayOrder + 1, displayOrder);
			}

			return moveBottom(contentBean, maxDisplayOrder, displayOrder);
		}

		return new ResultBean<String>(false, "参数type不正确", null);
	}
	
	/**
	 * 向上移动,向下移动
	 * @param sourceContentBean 推荐区域信息
	 * @param sourceDisplayOrder 原位置
	 * @param targetDisplayOrder 目标位置
	 * @return
	 */
	@Transactional
	public ResultBean<String> move(RecommendContentBean sourceContentBean,Integer sourceDisplayOrder,
			Integer targetDisplayOrder) {

		RecommendContentBean targetContentBean = recommendContentBll.getInfo(
				sourceContentBean.getRecommendSlotId(), sourceDisplayOrder);

		if (targetContentBean != null) {
			targetContentBean.setDisplayOrder(targetDisplayOrder);
			recommendContentBll.updateDisplayOrder(targetContentBean);
		}

		sourceContentBean.setDisplayOrder(sourceDisplayOrder);
		recommendContentBll.updateDisplayOrder(sourceContentBean);

		return new ResultBean<String>(true,"更新成功",null);
	}
	
	/**
	 * 置顶
	 * @param sourceContentBean
	 * @param displayOrder
	 */
	@Transactional
	public ResultBean<String> moveTop(RecommendContentBean sourceContentBean,Integer displayOrder){
		
		//大于当前位置的所有记录向下移动一个位置
		recommendContentBll.moveTop(displayOrder,sourceContentBean.getRecommendSlotId());

		//移动到第一条
		sourceContentBean.setDisplayOrder(1);
		recommendContentBll.updateDisplayOrder(sourceContentBean);
		
		return new ResultBean<String>(true,"更新成功",null);
	}
	
	/**
	 * 置底
	 * @param sourceContentBean
	 * @param contentList
	 * @param maxDisplayOrder
	 * @param displayOrder
	 */
	@Transactional
	public ResultBean<String> moveBottom(RecommendContentBean sourceContentBean,Integer maxDisplayOrder,Integer displayOrder){
		
		//小于当前位置的所有记录向上移动一个位置
		recommendContentBll.moveBottom(displayOrder,sourceContentBean.getRecommendSlotId());
		
		//移动到最后一条
		sourceContentBean.setDisplayOrder(maxDisplayOrder);
		recommendContentBll.updateDisplayOrder(sourceContentBean);
		
		return new ResultBean<String>(true,"更新成功",null);
	}

	/**
	 * 更新显示状态
	 * @param model
	 * @return
	 */
	public ResultBean<String> updateDisplay(RecommendContentBean model) {
		Integer number=recommendContentBll.updateDisplay(model);
		if(number<=0){
			return new ResultBean<String>(false,"更新失败",null);
		}
		return new ResultBean<String>(true,"更新成功",null);
	}
}
