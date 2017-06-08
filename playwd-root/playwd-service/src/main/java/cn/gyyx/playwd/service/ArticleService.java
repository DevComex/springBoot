package cn.gyyx.playwd.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gyyx.oa.stage.model.OAUserInfoModel;
import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean.State;
import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.beans.playwd.CategoryBean.CategoryType;
import cn.gyyx.playwd.beans.playwd.MessageBean;
import cn.gyyx.playwd.beans.playwd.MessageBean.MessageType;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.bll.CategoryBll;
import cn.gyyx.playwd.bll.MessageBll;
import cn.gyyx.playwd.bll.ReviewLogBll;
import cn.gyyx.playwd.utils.DateTools;

@Service
public class ArticleService {

	private ArticleBll articleBll;

	private CategoryBll categoryBll;
	
	/**
	 * 后台审核日志
	 */
	private ReviewLogBll reviewLogBll;

	/**
	 * 消息
	 */
	private MessageBll messageBll;

	/**
	 * 批量展示
	 * @param bean
	 * @param operator
	 * @return
	 */
	public boolean show(ArticleBean bean, String operator) {
		if (bean.getStatus().equals(ArticleBean.State.hidden.Value())) {
			articleBll.editArticleStatusById(bean.getCode(),
			    ArticleBean.State.passed.Value());
			reviewLogBll.insert(bean.getCode(),CategoryBean.CategoryType.article,
					ArticleBean.State.hidden,ArticleBean.State.passed,operator);
			return true;
		}
		return false;
	}

	/**
	 * 批量隐藏
	 * @param bean
	 * @param operator
	 * @return
	 */
	public boolean hide(ArticleBean bean, String operator) {
		if (bean.getStatus().equals(ArticleBean.State.passed.Value())) {
			articleBll.editArticleStatusById(bean.getCode(),
					ArticleBean.State.hidden.Value());
			reviewLogBll.insert(bean.getCode(),CategoryBean.CategoryType.article,
					ArticleBean.State.passed,ArticleBean.State.hidden,operator);
			return true;
		}
		return false;
	}

	/**
	 * 批量展示和不展示
	 * 
	 * @param type
	 *            展示还是不展示 1：展示 2：隐藏
	 * @param ids
	 *            选择操作的记录
	 * @param userInfoModel
	 *            操作用户
	 */
	@Transactional
	public ResultBean<String> batchShowOrHide(String type, int[] ids,
			OAUserInfoModel userInfoModel) {
		for (int id : ids) {
			ArticleBean bean = articleBll.getArticleById(id);
			if (bean == null) {
				continue;
			}

			if ("show".equals(type)) {
				show(bean, userInfoModel.getRealName());
			} else {
				hide(bean, userInfoModel.getRealName());
			}
		}
		return new ResultBean<>(true, "操作成功", "");
	}

	/**
	 * 审核文章
	 * 
	 * @param type
	 *            通过还是不通过 pass：通过 fail：不通过
	 * @param id
	 *            操作的记录
	 * @param userInfoModel
	 *            操作用户
	 */
	@Transactional
	public ResultBean<String> reviewArticle(String type, int id, OAUserInfoModel userInfoModel) {
		ArticleBean bean = articleBll.getArticleById(id);
		if (bean == null) {
			return new ResultBean<>(false, "文章不存在", "");
		}
		if (!bean.getStatus().equals(ArticleBean.State.unreviewd.Value())) {
			return new ResultBean<>(false, "请选择待审核的文章进行操作", "");
		}
		State status ;
		String messageType = "";
		String message = "";
		if (type.equals("fail")) {
			status = ArticleBean.State.failed;
			messageType = MessageBean.MessageType.fail.Value();
			message = MessageFormat.format(MessageBean.MessageType_Fail_Template, 
					categoryBll.getParentCategoryBySubCode(bean.getCategoryId()).getTitle(), 
					bean.getTitle());
		}else{
			status = ArticleBean.State.passed;
			messageType = MessageBean.MessageType.pass.Value();
			message = MessageFormat.format(MessageBean.MessageType_Pass_Template, 
					categoryBll.getParentCategoryBySubCode(bean.getCategoryId()).getTitle(), 
					bean.getTitle(),
					"图文驿站");
		}
		articleBll.editArticleStatusById(id, status.Value());
		// 插入通知用户消息记录
		messageBll.add(message, messageType, bean.getCode(), CategoryBean.CategoryType.article.Value(), bean.getUserId(),bean.getTitle());
		// 插入日志
		reviewLogBll.insert(bean.getCode(),CategoryBean.CategoryType.article,
				ArticleBean.State.unreviewd,status,userInfoModel.getRealName());
		return new ResultBean<>(true, "操作成功", "");
	}

	/**
	 * 
	 * <p>
	 * 添加/编辑文章
	 * </p>
	 *
	 * @action lihu 2017年3月1日 下午6:37:57 描述
	 *
	 * @param articleBean
	 * @param resultBean
	 * @param oaCode 
	 * @param account 
	 */
	@Transactional
	public ResultBean<Object> saveArticle(ArticleBean articleBean, String account, Integer oaCode) {
		if (articleBean.getCode() == null) { // 添加
			boolean articleCount = articleBll.findCountByTitle(articleBean.getTitle());
			if (articleCount) {
				return new ResultBean<>(false, "文章标题已存在", null);
			}
			articleBean.setCreateTime(new Date());
			articleBean.setStatus(ArticleBean.State.recommended.name());
			articleBean.setServerId(0);
			articleBean.setServerName("官方");
			if(articleBean.getAuthorType().equals("official")){
			    articleBean.setAuthor("官方");
			}
			if(articleBean.getNetId()==null){
			    articleBean.setNetId(1);
			}
			articleBean.setUserId(oaCode);
			articleBean.setAccount(account);
			
			articleBll.insertArticleOA(articleBean);
			return new ResultBean<>(true, "添加文章成功", null);
		} else {// 修改		        
			articleBll.editArticle(articleBean);
			return new ResultBean<>(true, "修改文章成功", null);
		}
	}

	/**
	 * 
	 * @param type
	 *            文章标题 title,上传账号 account,昵称 author
	 * @param key
	 *            搜索内容
	 * @param displayStatus
	 *            展示状态 全部 -1,隐藏 0,展示 1
	 * @param firstCategoryId
	 *            一级分类
	 * @param secondCategoryId
	 *            二级分类
	 * @param authorType
	 *            发布人 玩家player,官方official,全部all
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param status
	 *            审核状态 待审核unreviewd,审核通过(展示)passed,审核不通过failed,通过但不显示hidden,
	 *            通过且推荐recommended
	 * @return
	 */
	public PageBean<Map<String, String>> getList(String type, String key,int displayStatus, int firstCategoryId, int secondCategoryId,
			String authorType, String startTime, String endTime, String status,int pageIndex, int pageSize){
		// 查询数据列表
		List<ArticleBean> articleList = articleBll.getList(type, key, displayStatus, firstCategoryId,
				secondCategoryId, authorType, startTime, endTime, status,
				pageIndex, pageSize);
		if (articleList==null||articleList.size()==0) {
			return PageBean.createPage("", 0, 1, 10, null, "暂无数据");
		}
		
		// 设置返回结果
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (ArticleBean item : articleList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("code", item.getCode() + "");
			map.put("title", item.getTitle());
			map.put("firstName", item.getFirstCategoryName());
			map.put("secondName", item.getSecondCategoryName());
			map.put("serverName", item.getServerName());
			map.put("account", item.getAccount());
			map.put("author", item.getAuthor());
			map.put("displayStatus", getDisplayStatusName(item.getStatus()));
			map.put("statusName", getStatusName(item.getStatus()));
			map.put("createTime", DateTools.formatDate(item.getCreateTime(),
					"yyyy-MM-dd HH:mm:ss"));
			map.put("status", item.getStatus());
			map.put("userId", item.getUserId()+"");
			list.add(map);
		}
		
		int count = articleBll.getCount(type, key, displayStatus, firstCategoryId,secondCategoryId, authorType, startTime, endTime, status);
		
		return PageBean.createPage(true, count, pageIndex, pageSize, list, "查询成功");
	}

	/**
	  * <p>
	  *    获取用户上传的文章
	  * </p>
	  *
	  * @action
	  *    lihu 2017年3月8日 下午6:37:05 描述
	  *
	  * @param userId
	  * @param state 
	 * @param pageSize 
	 * @param pageIndex 
	  * @return List<ArticleBean>
	 */
    public PageBean<ArticleBean> getArticleByUserId(Integer userId, String state, int pageIndex, int pageSize) {
        List<ArticleBean> list = articleBll.getArticleByUserId(userId,state,pageIndex,pageSize);
        for (ArticleBean articleBean : list) {
            if(articleBean.getStatus().equals(ArticleBean.State.unreviewd.Value())){
                articleBean.setStatus("待审核");
            }
            if(articleBean.getStatus().equals(ArticleBean.State.failed.Value())){
                articleBean.setStatus("审核不通过");
            }
            if(articleBean.getStatus().equals(ArticleBean.State.hidden.Value())){
                articleBean.setStatus("通过但不展示");
            }
            if(articleBean.getStatus().equals(ArticleBean.State.passed.Value())){
                articleBean.setStatus("审核通过");
            }
            if(articleBean.getStatus().equals(ArticleBean.State.recommended.Value())){
                articleBean.setStatus("通过且推荐");
            }
        }
        
        int count=articleBll.getCountByUserId(userId,state);
        
        return PageBean.createPage(true, count, pageIndex, pageSize, list, "获取用户上传文章成功");
    }

    /**
     * 
      * <p>
      *    图文list
      * </p>
      *
      * @action
      *    lihu 2017年3月20日 下午3:15:33 描述
      *
      * @param categoryId
      * @param pageIndex
      * @param pageSize
      * @param sortType
      * @param numberType void
     * @return 
     */
    public PageBean<ArticleBean> findList(Integer categoryId, Integer pageIndex,
            Integer pageSize, String sortType ) {
            //检测是否为一级分类
            String categoryType =null;

            //获取所有一级分类
            List<CategoryBean> listCategory = categoryBll.getParentCategory(CategoryBean.CategoryType.article.Value());
            boolean isParent=false;
            for (CategoryBean categoryBean : listCategory) {
                if(categoryBean.getCode().equals(categoryId)){
                	isParent=true;
                }
            }

            if(isParent){
                categoryType ="parent";
            }else{
                categoryType="child";
            }
            if(categoryId==0){
                categoryType=null;
            }
            int startSize = (pageIndex - 1) * pageSize;
            int endSize =   pageSize;
            List<ArticleBean> list = articleBll.getAtricleList(categoryId, startSize, endSize, sortType,categoryType );
            int count = articleBll.getAtricleCount(categoryId, sortType,categoryType );
            
            return PageBean.createPage(true, count, pageIndex, pageSize, list, "获取图文列表成功");
    }
    
    /**
     * 预览
     * @param articleId
     * @return
     * @throws UnsupportedEncodingException 
     */
    public ResultBean<ArticleBean> preview(int articleCode) throws UnsupportedEncodingException {
    	//根据code查询图文信息
		ArticleBean article = articleBll.getArticle(articleCode);
		if(article == null){
			return new ResultBean<ArticleBean>("incorrect-notExist", "不存在", null);
		}
		
		//设置发布时间 前10位
		if(article.getPublishTimeStr() != null && article.getPublishTimeStr().length() > 10){
			article.setPublishTimeStr(article.getPublishTimeStr().substring(0,10));
		}
		
		article.setContent(URLDecoder.decode(article.getContent(),"utf-8"));
		article.setSecondCategoryName(categoryBll.getCategroyByCode(article.getCategoryId()).getTitle());
		article.setFirstCategoryName(categoryBll.getParentCategoryBySubCode(article.getCategoryId()).getTitle());
		
		//获取编辑回复信息
		MessageBean messageBean= messageBll.getMessage(CategoryType.article.Value(), articleCode, MessageType.editor.Value());
		if(messageBean==null){
			article.setEditorMessage("");
		}
		else {
			article.setEditorMessage(messageBean.getMessage());
		}
		return new ResultBean<ArticleBean>("success", "成功", article);
	}

	/**
	 * 设置显示状态 yangteng	 
	 * @param status
	 * @return
	 */
	private String getDisplayStatusName(String status) {
		if (status.equals("passed")||status.equals("recommended")) {
			return "已展示";
		} else if (status.equals("hidden")) {
			return "未展示";
		} else {
			return null;
		}
	}
	
	/**
	 * 设置状态名称 yangteng	 
	 * @param status
	 *            状态
	 * @return
	 */
	private String getStatusName(String status) {
		if (status.equals("unreviewd")) {
			return "未审核";
		} else if (status.equals("passed") || status.equals("hidden")) {
			return "审核通过";
		} else if (status.equals("failed")) {
			return "审核不通过";
		} else if (status.equals("recommended")) {
			return "编辑推荐";
		} else {
			return null;
		}
	}
	
	public ArticleBll getArticleBll() {
		return articleBll;
	}

	@Autowired
	public void setArticleBll(ArticleBll articleBll) {
		this.articleBll = articleBll;
	}
	
	public CategoryBll getCategoryBll() {
		return categoryBll;
	}

	@Autowired
	public void setCategoryBll(CategoryBll categoryBll) {
		this.categoryBll = categoryBll;
	}

	public MessageBll getMessageBll() {
		return messageBll;
	}

	@Autowired
	public void setMessageBll(MessageBll messageBll) {
		this.messageBll = messageBll;
	}

	public ReviewLogBll getReviewLogBll() {
		return reviewLogBll;
	}

	@Autowired
	public void setReviewLogBll(ReviewLogBll reviewLogBll) {
		this.reviewLogBll = reviewLogBll;
	}
}
