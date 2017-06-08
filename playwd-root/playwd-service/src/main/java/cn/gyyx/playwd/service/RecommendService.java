/**
* -------------------------------------------------------------------------
* (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
* @版权所有：北京光宇在线科技有限责任公司
* @项目名称：玩家天地
* @作者：李杜迪
* @联系方式：lidudi@gyyx.cn
* @创建时间：2017年3月3日上午9:14:14
* @版本号：1.0.0
*-------------------------------------------------------------------------
*/
package cn.gyyx.playwd.service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean.State;
import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.beans.playwd.CategoryBean.CategoryType;
import cn.gyyx.playwd.beans.playwd.MessageBean;
import cn.gyyx.playwd.beans.playwd.NovelBean;
import cn.gyyx.playwd.beans.playwd.PrizeBean;
import cn.gyyx.playwd.beans.playwd.PrizeLogBean;
import cn.gyyx.playwd.beans.playwd.RecommendContentBean;
import cn.gyyx.playwd.beans.playwd.RecommendSlotBean;
import cn.gyyx.playwd.beans.playwd.RmbOrderBean.RmbOrderState;
import cn.gyyx.playwd.beans.playwd.UserBean;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.bll.CategoryBll;
import cn.gyyx.playwd.bll.ItemBll;
import cn.gyyx.playwd.bll.MessageBll;
import cn.gyyx.playwd.bll.NovelBll;
import cn.gyyx.playwd.bll.PrizeBll;
import cn.gyyx.playwd.bll.PrizeLogBll;
import cn.gyyx.playwd.bll.RecommendBll;
import cn.gyyx.playwd.bll.RecommendContentBll;
import cn.gyyx.playwd.bll.RecommendSlotBll;
import cn.gyyx.playwd.bll.ReviewLogBll;
import cn.gyyx.playwd.bll.RmbOrderBll;
import cn.gyyx.playwd.bll.UserBll;
import cn.gyyx.playwd.bll.UserTitleBll;
import cn.gyyx.playwd.bll.WdgiftOrderBll;
import cn.gyyx.playwd.utils.StringTools;

/**
 * 推荐功能
 * 
 * @author lidudi
 *
 */
@Service
public class RecommendService {

    /**
     * 推荐位管理类
     */
    private RecommendSlotBll recommendSlotBll;

    /**
     * 推荐功能
     */
    private RecommendBll recommendBll;

    /**
     * 奖励物品类
     */
    private PrizeBll prizeBll;

    /**
     * 图文类
     */
    private ArticleBll articleBll;

    /**
     * 状态日志
     */
    private ReviewLogBll reviewLogBll;

    /**
     * 虚拟物品订单
     */
    private WdgiftOrderBll wdgiftOrderBll;

    /**
     * 推荐区域信息
     */
    private RecommendContentBll recommendContentBll;

    /**
     * 用户中心消息
     */
    private MessageBll messageBll;

    /**
     * 分类管理
     */
    private CategoryBll categoryBll;

    /**
     * 现金订单
     */
    private RmbOrderBll rmbOrderBll;

    /**
     * 用户称号信息
     */
    private UserTitleBll userTitleBll;

    /**
     * 奖品信息
     */
    private ItemBll itemBll;

    /**
     * 用户信息
     */
    private UserBll userBll;
    /**
     * 小说信息
     */
    private NovelBll novelBll;
    /**
     * 发奖流水
     */
    private PrizeLogBll prizeLogBll;

    @Value("${recommendUrl}")
    public String recommendUrl;
    @Value("${novelUrl}")
    public String novelUrl;

    /**
     * 图文推荐
     * 
     * @param contentId
     *            内容id
     * @param recommendSlotId
     *            推荐位id
     * @param prizeId
     *            奖励id
     * @param prizeType
     *            图文分类4星级和5星级选择人民币或者银元宝（ 可为空）
     * @return
     */
    public ResultBean<String> articleRecommend(Integer contentId,
            String recommendSlotId, Integer prizeId, String prizeType,
            String operator) {
        // 验证内容id的状态
        ArticleBean articleBean = articleBll.getArticleById(contentId);
        if (articleBean == null) {
            return new ResultBean<String>("incorrect-articleInfo", "图文不存在",
                    null);
        }

        // 获取奖品信息
        PrizeBean prizeBean = prizeBll.getPrizeByCode(prizeId);
        if (prizeBean == null) {
            return new ResultBean<String>("incorrect-prize", "奖品信息不存在", null);
        }

        // 图文分类4星级和5星级选择人民币或者银元宝
        if ((prizeBean.getName().equals("4星级")
                || prizeBean.getName().equals("5星级"))
                && StringUtils.isEmpty(prizeType)) {
            return new ResultBean<String>("incorrect-prizeType",
                    "4星级和5星级需选择人民币或者银元宝", null);
        }

        // 增加推荐信息
        return ((RecommendService) AopContext.currentProxy())
                .addArticleRecommend(recommendSlotId, articleBean, prizeBean,
                    operator, prizeType);
    }

    /**
     * 增加推荐信息
     * 
     * @param recommendSlotId
     * @param articleBean
     * @param prizeBean
     * @param operator
     * @param prizeType
     *            图文分类4星级和5星级选择人民币或者银元宝（ 可为空）
     * @return
     */
    @Transactional
    public ResultBean<String> addArticleRecommend(String recommendSlotId,
            ArticleBean articleBean, PrizeBean prizeBean, String operator,
            String prizeType) {
        // 作品未展示
        if (!articleBean.getStatus().equals(ArticleBean.State.passed.Value())) {
            return new ResultBean<String>("incorrect-articleStatus",
                    "图文状态不是审核通过并且展示", null);
        }

        // 获取推荐位置
        String[] recommendSlot = recommendSlotId.split(",");
        List<String> recommendSlotNameString = new ArrayList<String>();
        for (int i = 0; i < recommendSlot.length; i++) {
            int slotId = Integer.parseInt(recommendSlot[i]);
            // 验证推荐位不存在
            RecommendSlotBean recommendSlotBean = recommendSlotBll
                    .getRecommendSlotByCode(slotId);
            if (recommendSlotBean == null) {
                return new ResultBean<String>("incorrect-slot", "推荐位不存在", null);
            }
            // 推荐位名称用户中心消息内容
            recommendSlotNameString.add(recommendSlotBean.getSlotGroup());

            // 增加推荐数据
            recommendBll.addRecommend(CategoryBean.CategoryType.article.Value(),
                articleBean.getCode(), slotId, articleBean.getTitle(),
                articleBean.getCover(), prizeBean.getCode());

            // 增加区域数据
            // 前台文章链接
            String contentUrl = recommendUrl + articleBean.getCode();
            recommendContentBll.add(new RecommendContentBean(slotId,
                    articleBean.getTitle(), contentUrl, articleBean.getCover(),
                    articleBean.getCode()));
        }

        // 修改图文信息
        articleBll.editArticleStatusById(articleBean.getCode(),
            ArticleBean.State.recommended.Value(),
            prizeBll.getPrizeInfo(prizeBean, prizeType), prizeBean.getCode());

        // 增加状态日志
        reviewLogBll.insert(articleBean.getCode(),
            CategoryBean.CategoryType.article,
            State.valueOf(articleBean.getStatus()),
            ArticleBean.State.recommended, operator);

        // 增加奖励订单
        return articlePrize(articleBean, prizeBean,
            StringTools.removeRepeatedChar(recommendSlotNameString), prizeType,operator);
    }

    /**
     * 增加奖励订单
     * 
     * @param articleBean
     * @param prizeBean
     * @param recommendSlotNameString
     * @param prizeType
     *            图文分类4星级和5星级选择人民币或者银元宝（ 可为空）
     * @return
     */
    public ResultBean<String> articlePrize(ArticleBean articleBean,
            PrizeBean prizeBean, String recommendSlotNameString,
            String prizeType,String operator) {
        PrizeLogBean prizeLogBean = new PrizeLogBean(prizeBean.getCode(),operator, "article", articleBean.getCode(), articleBean.getUserId(), recommendUrl + articleBean.getCode(), new Date());
        prizeLogBll.insertPrizeLog(prizeLogBean);
        prizeLogBean.setYyb(0);
        prizeLogBean.setRmb(0);
        prizeLogBean.setAlias("");
        // 用户中心消息内容
        String message = MessageFormat.format(
            MessageBean.MessageType_Recommend_Template,
            categoryBll.getCategroyByCode(articleBean.getParentId()).getTitle(),
            articleBean.getTitle(), prizeBean.getName().subSequence(0, 1),
            recommendSlotNameString, prizeBean.getItemList().get(0).getName(),
            MessageBean.MessageType_RecommendLowPrize_Template);
        String prizeName = "";

        // 一个月获得推荐列表
        List<ArticleBean> articleBeans = articleBll
                .getListByMonthUserId(articleBean.getUserId());
        int articleCountByPrize = getArticleCountByPrize(articleBeans, 1);

        // 增加或修改财富信息使用
        BigDecimal rmb = new BigDecimal(0);
        int silverCoin = 0;

        switch (prizeBean.getName()) {
            case "1星级":
                // 增加虚拟物品订单
                wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(),
                    articleBean.getCode(), prizeBean.getCode(),
                    articleBean.getGameId(), articleBean.getServerId(),
                    articleBean.getServerName(),
                    prizeBean.getItemList().get(0).getItemCode(),
                    CategoryType.article.Value(), articleBean.getUserId());
                    
                // 增加或者修改用户财富信息
                addOrChangeMemberInfo(articleBean.getUserId(), rmb, Integer
                        .parseInt(prizeBean.getItemList().get(0).getValue()));
                //发奖流水日志
                prizeLogBean.setYyb(Integer.parseInt(prizeBean.getItemList().get(0).getValue()));
                prizeLogBean.setRmb(0);
                // 一个月内有六篇稿件获得一星级推荐可获得妙语连珠称号
                if (articleCountByPrize == 6) {
                    prizeLogBean.setAlias("妙语连珠称号");
                    // 增加称号信息
                    addTitle(articleBean, prizeBean.getCode(), "妙语连珠称号",
                        itemBll.getItemByName("妙语连珠称号").getItemCode(), "",
                        articleBean.getUserId());
                }
                // 一个月内有四篇稿件获得一星级推荐可获得丹青妙笔称号
                if (articleCountByPrize == 4) {
                    prizeLogBean.setAlias("丹青妙笔称号");
                    // 增加称号信息
                    addTitle(articleBean, prizeBean.getCode(), "丹青妙笔称号",
                        itemBll.getItemByName("丹青妙笔称号").getItemCode(), "",
                        articleBean.getUserId());
                }
                
                // 用户中心增加消息
                messageBll.add(message,
                    MessageBean.MessageType.recommend.Value(),
                    articleBean.getCode(),
                    CategoryBean.CategoryType.article.Value(),
                    articleBean.getUserId(), articleBean.getTitle());
                break;
            case "2星级":
            case "3星级":
                // 增加虚拟物品订单
                for (int i = 0; i < prizeBean.getItemList().size(); i++) {
                    prizeLogBean.setRmb(0);
                    if (prizeBean.getItemList().get(i).getName()
                            .indexOf("称号") >= 0) {
                        // 增加称号信息
                        prizeLogBean.setAlias(prizeBean.getItemList().get(i).getValue());
                        addTitle(articleBean, prizeBean.getCode(),
                            prizeBean.getItemList().get(i).getName(),
                            prizeBean.getItemList().get(i).getItemCode(),
                            CategoryType.article.Value(),
                            articleBean.getUserId());
                    }
                    if (prizeBean.getItemList().get(i).getName()
                            .indexOf("银元宝") >= 0) {
                        wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(),
                            articleBean.getCode(), prizeBean.getCode(),
                            articleBean.getGameId(), articleBean.getServerId(),
                            articleBean.getServerName(),
                            prizeBean.getItemList().get(i).getItemCode(),
                            CategoryType.article.Value(),
                            articleBean.getUserId());
                        prizeName = prizeBean.getItemList().get(i).getName();
                        prizeLogBean.setYyb(Integer.parseInt(prizeBean.getItemList().get(0).getValue()));
                        // 增加或者修改用户财富信息
                        addOrChangeMemberInfo(articleBean.getUserId(), rmb,
                            Integer.parseInt(
                                prizeBean.getItemList().get(0).getValue()));
                    }
                }
                if (prizeBean.getName().equals("2星级")) {
                    // 一个月内有六篇稿件获得二星级推荐可获得点石成金称号
                    articleCountByPrize = getArticleCountByPrize(articleBeans,
                        2);
                    if (articleCountByPrize == 6) {
                        // 增加称号信息
                        addTitle(articleBean, prizeBean.getCode(), "点石成金称号",
                            itemBll.getItemByName("点石成金称号").getItemCode(), "",
                            articleBean.getUserId());
                    }
                    // 一个月内有四篇稿件获得二星级推荐可获得妙语连珠称号
                    if (articleCountByPrize == 4) {
                        // 增加称号信息
                        addTitle(articleBean, prizeBean.getCode(), "妙语连珠称号",
                            itemBll.getItemByName("妙语连珠称号").getItemCode(), "",
                            articleBean.getUserId());
                    }
                }
                if (prizeBean.getName().equals("3星级")) {
                    articleCountByPrize = getArticleCountByPrize(articleBeans,
                        3);
                    // 一个月内有四篇稿件获得三星级推荐可获得新闻记者称号
                    if (articleCountByPrize == 4) {
                        // 增加称号信息
                        addTitle(articleBean, prizeBean.getCode(), "新闻记者称号",
                            itemBll.getItemByName("新闻记者称号").getItemCode(), "",
                            articleBean.getUserId());
                    }
                }
                // 用户中心增加消息
                message = MessageFormat.format(
                    MessageBean.MessageType_Recommend_Template,
                    categoryBll.getCategroyByCode(articleBean.getParentId())
                            .getTitle(),
                    articleBean.getTitle(),
                    prizeBean.getName().subSequence(0, 1),
                    recommendSlotNameString, prizeName,
                    MessageBean.MessageType_RecommendMediumPrize_Template);
                messageBll.add(message,
                    MessageBean.MessageType.recommend.Value(),
                    articleBean.getCode(),
                    CategoryBean.CategoryType.article.Value(),
                    articleBean.getUserId(), articleBean.getTitle());
                break;
            case "4星级":
            case "5星级":
                for (int i = 0; i < prizeBean.getItemList().size(); i++) {
                    if (prizeBean.getItemList().get(i).getName()
                            .indexOf("称号") >= 0) {
                        prizeLogBean.setAlias(prizeBean.getItemList().get(i).getValue());
                        // 增加称号信息
                        addTitle(articleBean, prizeBean.getCode(),
                            prizeBean.getItemList().get(i).getName(),
                            prizeBean.getItemList().get(i).getItemCode(),
                            CategoryType.article.Value(),
                            articleBean.getUserId());
                    }
                    if (prizeBean.getItemList().get(i).getName()
                            .indexOf("银元宝") >= 0
                            && prizeType.equals("silverCoins")) {
                        prizeLogBean.setYyb(Integer.parseInt(prizeBean.getItemList().get(i).getValue()));
                        wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(),
                            articleBean.getCode(), prizeBean.getCode(),
                            articleBean.getGameId(), articleBean.getServerId(),
                            articleBean.getServerName(),
                            prizeBean.getItemList().get(i).getItemCode(),
                            CategoryType.article.Value(),
                            articleBean.getUserId());
                        prizeName = prizeBean.getItemList().get(i).getName();

                        addOrChangeMemberInfo(articleBean.getUserId(), rmb,
                            Integer.parseInt(
                                prizeBean.getItemList().get(0).getValue()));
                    }
                    // 增加现金奖励订单
                    if (prizeBean.getItemList().get(i).getName()
                            .indexOf("现金") >= 0 && prizeType.equals("rmb")) {
                        prizeLogBean.setRmb(Integer.parseInt(prizeBean.getItemList().get(i).getValue()));
                        rmbOrderBll.addRmbOrder(articleBean.getUserId(),
                            articleBean.getAccount(), articleBean.getCode(),
                            prizeBean.getCode(),
                            Integer.parseInt(
                                prizeBean.getItemList().get(i).getValue()),
                            CategoryType.article.Value(),
                            RmbOrderState.manual.Value());
                        prizeName = prizeBean.getItemList().get(i).getName();

                        addOrChangeMemberInfo(articleBean.getUserId(),
                            new BigDecimal(
                                    prizeBean.getItemList().get(i).getValue()),
                            silverCoin);
                    }
                }
                // 用户中心增加消息
                message = MessageFormat.format(
                    MessageBean.MessageType_Recommend_Template,
                    categoryBll.getCategroyByCode(articleBean.getParentId())
                            .getTitle(),
                    articleBean.getTitle(),
                    prizeBean.getName().subSequence(0, 1),
                    recommendSlotNameString, prizeName,
                    MessageBean.MessageType_RecommendHighPrize_Template);
                messageBll.add(message,
                    MessageBean.MessageType.recommend.Value(),
                    articleBean.getCode(),
                    CategoryBean.CategoryType.article.Value(),
                    articleBean.getUserId(), articleBean.getTitle());
                break;
            default:
                break;
        }
        //发奖流水日志
        prizeLogBll.updatePrizeLog(prizeLogBean);
        return new ResultBean<String>("success", "成功", null);
    }

    /**
     * 增加称号信息
     * 
     * @param articleBean
     * @param prizeId
     * @param itemName
     *            称号名称
     * @param itemCode
     *            称号游戏内代码
     */
    private void addTitle(ArticleBean articleBean, int prizeId, String itemName,
            String itemCode, String contentType, int userId) {
        // 增加称号订单
        wdgiftOrderBll.addWdgiftOrder(articleBean.getAccount(),
            articleBean.getCode(), prizeId, articleBean.getGameId(),
            articleBean.getServerId(), articleBean.getServerName(), itemCode,
            contentType, userId);
        // 增加用户中心称号
        userTitleBll.addUserTitle(contentType, articleBean.getCode(),
            articleBean.getUserId(), prizeId, itemName);
    }

    /**
     * 每月按等级图文数量
     * 
     * @param userTitleBeans
     * @param level
     * @return
     */
    public int getArticleCountByPrize(List<ArticleBean> articleBeans,
            int level) {
        // 根据groupId分组, 如果groupId为null, 则放到默认为0的group下
        Function<ArticleBean, Integer> rule = new Function<ArticleBean, Integer>() {
            @Override
            public Integer apply(ArticleBean bean) {
                if (bean.getPrizeId() == null) {
                    return 0;
                }
                return bean.getPrizeId();
            }
        };
        Multimap<Integer, ArticleBean> userTitleBeansByGroup = Multimaps
                .index(articleBeans, rule);
        Map<Integer, Collection<ArticleBean>> map = userTitleBeansByGroup
                .asMap();
        for (Map.Entry<Integer, Collection<ArticleBean>> entry : map
                .entrySet()) {
            if (entry.getKey().equals(level)) {
                return entry.getValue().size();
            }
        }
        return 0;
    }

    /**
     * 增加或者修改用户财富信息
     * 
     * @param userId
     * @param rmb
     * @param silverCoin
     */
    public boolean addOrChangeMemberInfo(int userId, BigDecimal rmb,
            int silverCoin) {
        UserBean userBean = userBll.getByUserId(userId);
        if (userBean == null) {
            return userBll.addRecord(userId, rmb, silverCoin) > 0;
        } else {
            return userBll.changeRmbAndSilverCoin(userId, rmb, silverCoin) > 0;
        }
    }

    /**
     * 获取推荐管理列表
     * 
     * @param slotId
     * @param prizeId
     * @param authorType
     * @param pageSize
     * @param currentPage
     * @return
     */
    public PageBean<Map<String, Object>> getArticleManagementList(int slotId,
            int prizeId, String authorType, int pageSize, int currentPage) {
        // 获取数量
        int count = articleBll.getRecommendManagementCount(slotId, prizeId,
            authorType);
        if (count <= 0) {
            return PageBean.createPage("success", 0, currentPage, pageSize,
                null, "成功");
        }

        // 获取列表
        List<ArticleBean> list = articleBll.getRecommendManagementList(slotId,
            prizeId, authorType, pageSize, currentPage);

        List<Map<String, Object>> ResultBeanList = FluentIterable.from(list)
                .transform(new Function<ArticleBean, Map<String, Object>>() {

                    @Override
                    public Map<String, Object> apply(ArticleBean info) {

                        SimpleDateFormat format = new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss");
                        String strDate = "无";
                        if (info.getRecommmendTime() != null) {
                            strDate = format.format(info.getRecommmendTime());
                        }

                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("code", info.getCode());
                        map.put("title", info.getTitle());
                        map.put("authorType",
                            info.getAuthorType().equals("player") ? "玩家"
                                    : "官方");
                        map.put("cover", info.getCover());
                        map.put("name", info.getPrizeName() == null ? "无"
                                : info.getPrizeName());
                        map.put("recommmendTime", strDate);
                        return map;
                    }
                }).toList();

        return PageBean.createPage("success", count, currentPage, pageSize,
            ResultBeanList, "成功");

    }

    /**
     * 获取文章对应的推荐位
     * 
     * @param contentId
     * @return
     */
    public List<Map<String, Object>> getSlotsByContentId(int contentId) {

        List<RecommendSlotBean> recommendList = recommendSlotBll
                .getRecommendSlotList(CategoryType.article.Value());
        List<RecommendSlotBean> recommendListNoContentType = recommendSlotBll
                .getRecommendSlotList("");
        List<RecommendSlotBean> recommendListWebsite = recommendSlotBll
                .getRecommendSlotList("website");
        // 将首页推荐位和图文推荐位合并
        recommendList.addAll(recommendListNoContentType);
        recommendList.addAll(recommendListWebsite);

        List<Integer> getSlots = recommendBll.getSlotsByContentId(contentId,
            CategoryType.article.Value());

        List<Map<String, Object>> resultList = FluentIterable
                .from(recommendList).transform(
                    new Function<RecommendSlotBean, Map<String, Object>>() {

                        @Override
                        public Map<String, Object> apply(
                                RecommendSlotBean info) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("code", info.getCode());
                            map.put("slotGroup", info.getSlotGroup());
                            map.put("slot", info.getSlot());
                            map.put("select",
                                getSlots.contains(info.getCode()));
                            return map;
                        }
                    })
                .toList();

        return resultList;
    }

    /**
     * 获取文章对应的推荐位
     * 
     * @param contentId
     * @return
     */
    @Transactional
    public ResultBean<Object> changeSlotsByContentId(int contentId,
            String recommendSlotId) {
        // 验证内容id的状态
        ArticleBean articleBean = articleBll.getArticleById(contentId);
        if (articleBean == null) {
            return new ResultBean<Object>("incorrect-articleInfo", "图文不存在",
                    null);
        }

        // 作品未展示
        if (!articleBean.getStatus()
                .equals(ArticleBean.State.recommended.Value())) {
            return new ResultBean<Object>("incorrect-articleStatus",
                    "图文状态不是推荐状态", null);
        }

        List<Integer> slotsOld = recommendBll.getSlotsByContentId(contentId,
            CategoryType.article.Value());

        // 现有推荐位不包含新推荐位置则增加
        String[] slotsNew = recommendSlotId.split(",");
        for (int i = 0; i < slotsNew.length; i++) {
            int slotId = Integer.parseInt(slotsNew[i]);
            // 验证推荐位不存在
            RecommendSlotBean recommendSlotBean = recommendSlotBll
                    .getRecommendSlotByCode(slotId);
            if (recommendSlotBean == null) {
                return new ResultBean<Object>("incorrect-slot", "推荐位不存在", null);
            }
            // 现有推荐位不包含新推荐位置则增加
            if (!slotsOld.contains(slotId)) {
                recommendBll.addRecommend(
                    CategoryBean.CategoryType.article.Value(),
                    articleBean.getCode(), slotId, articleBean.getTitle(),
                    articleBean.getCover(), articleBean.getPrizeId());

                // 增加区域数据
                // 前台文章链接
                String contentUrl = recommendUrl + articleBean.getCode();
                recommendContentBll.add(new RecommendContentBean(slotId,
                        articleBean.getTitle(), contentUrl,
                        articleBean.getCover(), articleBean.getCode()));
            }
        }

        // 新推荐位置不包含现有推荐位置则删除
        for (Integer slotOld : slotsOld) {
            // 删除推荐位置信息和区域信息
            if (!Arrays.asList(slotsNew).contains(slotOld.toString())) {
                recommendBll.changeIsDeleteByContentIdAndSlotId(
                    articleBean.getCode(), slotOld);
                recommendContentBll.changeIsDeleteBySlotIdAndContentId(
                    articleBean.getCode(), slotOld);
            }
        }

        return new ResultBean<Object>("success", "成功", null);
    }

    /**
     * 获取推荐记录列表
     * 
     * @param locationId
     * @param prizeId
     * @param authorType
     * @param pageSize
     * @param currentPage
     * @return
     */
    public PageBean<Map<String, Object>> getArticleRecordList(int prizeId,
            Date startDate, Date endDate, int pageSize, int currentPage) {
        // 获取数量
        int count = articleBll.getRecommendRecordCount(prizeId, startDate,
            endDate);
        if (count <= 0) {
            return PageBean.createPage("success", 0, currentPage, pageSize,
                new ArrayList<Map<String, Object>>(), "成功");
        }

        // 获取列表
        List<ArticleBean> list = articleBll.getRecommendRecordList(prizeId,
            startDate, endDate, pageSize, currentPage);

        List<Map<String, Object>> ResultBeanList = FluentIterable.from(list)
                .transform(new Function<ArticleBean, Map<String, Object>>() {

                    @Override
                    public Map<String, Object> apply(ArticleBean info) {

                        SimpleDateFormat format = new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss");
                        String strDate = "无";
                        if (info.getRecommmendTime() != null) {
                            strDate = format.format(info.getRecommmendTime());
                        }

                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("code", info.getCode());
                        map.put("title", info.getTitle());
                        map.put("serverName", info.getServerName());
                        map.put("account", info.getAccount());
                        map.put("author", info.getAuthor());
                        map.put("viewCount", info.getViewCount());
                        map.put("prizeName", info.getPrizeName() == null ? "无"
                                : info.getPrizeName());
                        map.put("url", recommendUrl + info.getCode());
                        map.put("recommmendTime", strDate);
                        map.put("remark",
                            info.getRemark() == null ? "" : info.getRemark());
                        return map;
                    }
                }).toList();

        return PageBean.createPage("success", count, currentPage, pageSize,
            ResultBeanList, "成功");

    }

    @Autowired
    public void setReviewLogBll(ReviewLogBll reviewLogBll) {
        this.reviewLogBll = reviewLogBll;
    }

    @Autowired
    public void setRecommendSlotBll(RecommendSlotBll recommendSlotBll) {
        this.recommendSlotBll = recommendSlotBll;
    }

    @Autowired
    public void setRecommendBll(RecommendBll recommendBll) {
        this.recommendBll = recommendBll;
    }

    @Autowired
    public void setPrizeBll(PrizeBll prizeBll) {
        this.prizeBll = prizeBll;
    }

    @Autowired
    public void setArticleBll(ArticleBll articleBll) {
        this.articleBll = articleBll;
    }

    @Autowired
    public void setWdgiftOrderBll(WdgiftOrderBll wdgiftOrderBll) {
        this.wdgiftOrderBll = wdgiftOrderBll;
    }

    @Autowired
    public void setRecommendContentBll(
            RecommendContentBll recommendContentBll) {
        this.recommendContentBll = recommendContentBll;
    }

    @Autowired
    public void setMessageBll(MessageBll messageBll) {
        this.messageBll = messageBll;
    }

    @Autowired
    public void setCategoryBll(CategoryBll categoryBll) {
        this.categoryBll = categoryBll;
    }

    @Autowired
    public void setRmbOrderBll(RmbOrderBll rmbOrderBll) {
        this.rmbOrderBll = rmbOrderBll;
    }

    @Autowired
    public void setUserTitleBll(UserTitleBll userTitleBll) {
        this.userTitleBll = userTitleBll;
    }

    @Autowired
    public void setItemBll(ItemBll itemBll) {
        this.itemBll = itemBll;
    }

    @Autowired
    public void setUserBll(UserBll userBll) {
        this.userBll = userBll;
    }

    @Autowired
    public void setNovelBll(NovelBll novelBll) {
        this.novelBll = novelBll;
    }
    @Autowired
    public void setPrizeLogBll(PrizeLogBll prizeLogBll) {
      this.prizeLogBll = prizeLogBll;
    }

    /**
     * 
     * <p>
     * 小说推荐
     * </p>
     *
     * @action lihu 2017年5月23日 上午10:55:38 描述
     *
     * @param recommendSlotId
     * @param novelId
     *            void
     */
    public ResultBean<String> novelRecommend(Integer recommendSlotId,
            Integer novelId) {
        NovelBean novelBean = novelBll.get(novelId);
        if (novelBean == null) {
            return new ResultBean<String>("novel-no", "小说不存在", null);
        }
        RecommendSlotBean recommendSlotBean = recommendSlotBll
                .getRecommendSlotByCode(recommendSlotId);
        if (recommendSlotBean == null) {
            return new ResultBean<String>("recommendSlot-no", "推荐位不存在", null);
        }
        List<Integer> list = recommendBll.getSlotsByContentId(novelId,CategoryType.novel.Value());
        if (list.size() > 0) {
            return new ResultBean<String>("recommendSlot-yes", "该小说已推荐", null);
        }
        // 添加数据到 推荐区域
        recommendBll.addRecommend(CategoryBean.CategoryType.novel.Value(),
            novelId, recommendSlotId, novelBean.getName(), novelBean.getCover(),
            0);
        // 添加数据到 推荐位
        recommendContentBll.add(new RecommendContentBean(recommendSlotId,
                novelBean.getName(), novelUrl + novelBean.getCode(),
                novelBean.getCover(), novelId));

        return new ResultBean<String>(true, "推荐位添加成功");
    }

    /**
     * 
     * <p>
     * 获取小说推荐列表
     * </p>
     *
     * @action lihu 2017年5月23日 下午4:39:26 描述
     *
     * @param recommendSlotId
     * @param Index
     * @param pageSize
     * @return PageBean<Map<String,Object>>
     */
    public PageBean<Map<String, Object>> getNovelManagementList(
            Integer recommendSlotId, Integer pageSize, Integer pageIndex) {
        int startSize = (pageIndex - 1) * pageSize;
        int endSize = pageSize;
        int count = novelBll.getNovelManagementCount(recommendSlotId);
        List<Map<String, Object>> list = novelBll.getNovelManagementList(recommendSlotId, startSize, endSize);

        return PageBean.createPage(true, count, pageIndex, pageSize, list,
            "查询小说推荐成功");
    }

    /**
     * 
     * <p>
     * 获取小说推荐列表
     * </p>
     *
     * @action lihu 2017年5月24日 上午10:00:14 描述
     *
     * @param contentId
     * @return Object
     */
    public Object getNovelSlotsByContentId(Integer contentId) {
        List<Integer> list = recommendBll.getSlotsByContentId(contentId,
            CategoryType.novel.Value());
        List<RecommendSlotBean> recommendSlotList = recommendSlotBll.getRecommendSlotList("novel");
        List<Map<String, Object>> resultList = FluentIterable
                .from(recommendSlotList).transform(
                    new Function<RecommendSlotBean, Map<String, Object>>() {
                        @Override
                        public Map<String, Object> apply(
                                RecommendSlotBean info) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("code", info.getCode());
                            map.put("slotGroup", info.getSlotGroup());
                            map.put("slot", info.getSlot());
                            map.put("select", list.contains(info.getCode()));
                            return map;
                        }
                    })
                .toList();

        return resultList;
    }

    public ResultBean<Object> changeNovelSlotsByContentId(Integer contentId,
            String recommendSlotId) {
        NovelBean novelBean = novelBll.get(contentId);
        if (novelBean == null) {
            return new ResultBean<Object>("novel-no", "小说不存在", null);
        }
        List<Integer> slotsOld = recommendBll.getSlotsByContentId(contentId,CategoryType.novel.Value());

        // 小说未推荐
        if (slotsOld == null || slotsOld.isEmpty()) {
            return new ResultBean<Object>("incorrect-novelStatus", "小说状态不是推荐状态",
                    null);
        }

        // 现有推荐位不包含新推荐位置则增加
        String[] slotsNew = recommendSlotId.split(",");
        for (int i = 0; i < slotsNew.length; i++) {
            int slotId = Integer.parseInt(slotsNew[i]);
            // 验证推荐位不存在
            RecommendSlotBean recommendSlotBean = recommendSlotBll.getRecommendSlotByCode(slotId);
            if (recommendSlotBean == null) {
                return new ResultBean<Object>("incorrect-slot", "推荐位不存在", null);
            }
            // 现有推荐位不包含新推荐位置则增加
            if (!slotsOld.contains(slotId)) {
                recommendBll.addRecommend(
                    CategoryBean.CategoryType.novel.Value(),
                    novelBean.getCode(), slotId, novelBean.getName(),
                    novelBean.getCover(), 0);

                // 增加区域数据
                // 前台文章链接
                String contentUrl = novelUrl + novelBean.getCode();
                recommendContentBll.add(new RecommendContentBean(slotId,
                        novelBean.getName(), contentUrl, novelBean.getCover(),
                        novelBean.getCode()));
            }
        }

        // 新推荐位置不包含现有推荐位置则删除
        for (Integer slotOld : slotsOld) {
            // 删除推荐位置信息和区域信息
            if (!Arrays.asList(slotsNew).contains(slotOld.toString())) {
                recommendBll.changeIsDeleteByContentIdAndSlotId(novelBean.getCode(), slotOld);
                recommendContentBll.changeIsDeleteBySlotIdAndContentId(novelBean.getCode(), slotOld);
            }
        }

        return new ResultBean<Object>("success", "成功", null);
    }

}
