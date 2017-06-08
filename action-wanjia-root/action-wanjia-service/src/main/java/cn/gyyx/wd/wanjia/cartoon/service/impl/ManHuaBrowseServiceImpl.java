/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-12
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，漫画浏览的Service接口实现类
 */
package cn.gyyx.wd.wanjia.cartoon.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCategory;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCollect;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaGood;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaPage;
import cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL;
import cn.gyyx.wd.wanjia.cartoon.service.ManHuaBrowseService;

@Service
@Transactional
public class ManHuaBrowseServiceImpl implements ManHuaBrowseService {
        private static final Logger logger = GYYXLoggerFactory.getLogger(ManHuaBrowseService.class);
        @Autowired
        private ManHuaBrowseBLL manHuaBrowseBLL;

        /*
         * (non-Javadoc)
         * 
         * @see
         * cn.gyyx.wd.wanjia.cartoon.service.ManHuaBrowseService#getManHuaInfo(java.
         * lang.Integer, cn.gyyx.core.auth.UserInfo)
         */
        @Override
        public Map<String, Object> getManHuaInfo(Integer code, UserInfo userInfo) {
                // 获取漫画的基本信息
                Map<String, Object> map = getManHuaByCode(code);
                // 判定漫画存在
                if (map == null) {
                        return map;
                }
                // 获取阅读，点赞，收藏状态
                map.putAll(getStatus(code, userInfo));
                return map;
        }

        /**
         * private method 获取目录页漫画的部分信息，包括 漫画名称，漫画简介，作者名，漫画封面，点赞数
         * 
         * @param code
         * @return
         */
        private Map<String, Object> getManHuaByCode(Integer code) {
                Map<String, Object> info = manHuaBrowseBLL.selectManhuaInfo(code);
                if (info==null) {
                        logger.debug("漫画信息不存在或未展示");
                        return null;
                }
                // 获取点赞数
                int goodCount = manHuaBrowseBLL.getManHuaGoodCount(code);
                logger.debug("获取漫画点赞数：manHuaBrowseBLL.getManHuaGoodCount=" + goodCount);
                info.put("goodCount", goodCount);
                return info;
        }

        /**
         * private method 获取目录页漫画的部分信息,阅读状态，点赞状态，收藏状态
         * 
         * @param manhuaCode
         * @param userInfo
         * @return
         */
        private Map<String, Object> getStatus(Integer manhuaCode, UserInfo userInfo) {
                Map<String, Object> map = new HashMap<>();
                logger.debug("判定用户登录状态");// 判断是否登录
                if (userInfo == null) {// 未登录，设置其他信息为未置状态
                        map.put("goodStatus", false);
                        map.put("collectStatus", false);
                        map.put("readStatus", false);
                        logger.debug("未登录，点赞，收藏，阅读状态置为未状态");
                        return map;
                }
                // 获取点赞状态
                WanwdManhuaGood manhuaGood = new WanwdManhuaGood();
                manhuaGood.setUserId(userInfo.getUserId());
                manhuaGood.setManhuaCode(manhuaCode);
                int goodStatus = manHuaBrowseBLL.getGoodStatus(manhuaGood);
                if (goodStatus <= 0) {// 没有点赞记录
                        map.put("goodStatus", false);
                } else {
                        map.put("goodStatus", true);
                }
                logger.debug("登录，获取点赞状态，判定" + map.get("goodStatus") + "。manHuaBrowseBLL.getGoodStatus=" + goodStatus);
                // 获取收藏和阅读历史状态
                WanwdCollect collect = new WanwdCollect();
                collect.setUserId(userInfo.getUserId());
                collect.setSourcesCode(manhuaCode);
                WanwdCollect collectionStatus = manHuaBrowseBLL.selectCollectionStatus(collect);
                if (collectionStatus == null || collectionStatus.getCode() == null) {
                        // 没有收藏记录，也就没有阅读记录（收藏后才有阅读记录）
                        map.put("collectStatus", false);
                        map.put("readStatus", false);
                } else {
                        map.put("collectStatus", true);
                        if (collectionStatus.getReadLogCode() != null &&collectionStatus.getReadLogCode() > 0) {// 阅读记录不存在
                                map.put("readStatus", true);
                        } else {
                                map.put("readStatus", false);
                        }
                }
                logger.debug("登录，获取收藏阅读状态，判定collectStatus：" + map.get("collectStatus") + "readStatus：" + map.get("readStatus")
                                + "。manHuaBrowseBLL.selectCollectionStatus=" + collectionStatus);
                return map;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * cn.gyyx.wd.wanjia.cartoon.service.ManHuaBrowseService#addGoodStatus(cn.
         * gyyx.core.auth.UserInfo, java.lang.Integer)
         */
        @Override
        public boolean addGoodStatus(UserInfo userInfo, Integer manhuaCode) {
                WanwdManhuaGood good = new WanwdManhuaGood();
                good.setUserId(userInfo.getUserId());
                good.setManhuaCode(manhuaCode);
                int goodStatus = manHuaBrowseBLL.getGoodStatus(good);
                logger.debug("获取点赞状态：manHuaBrowseBLL.getGoodStatus=" + goodStatus);
                if (goodStatus <= 0) {
                        // 没有点赞记录
                        good.setCreateTime(new Date());
                        good.setUserAccount(userInfo.getAccount());
                        int status = manHuaBrowseBLL.addGoodStatus(good);
                        logger.debug("没有点赞过，添加点赞，manHuaBrowseBLL.addGoodStatus=" + status);
                } else {
                        logger.debug("已有点赞记录，不能点赞");
                        return false;
                }
                return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * cn.gyyx.wd.wanjia.cartoon.service.ManHuaBrowseService#manhuaBrowse(cn.
         * gyyx.core.auth.UserInfo, java.lang.Integer, java.lang.Integer,
         * java.lang.Integer)
         */

        @Override
        public Map<String, Object> manhuaBrowse(UserInfo userInfo, Integer manhuaCode, Integer bookNum, Integer pageNum) {
                Map<String, Object> map = new HashMap<>();
                Map<String, Object> result = new HashMap<>();
                map.put("manhuaCode", manhuaCode);
                // 获取用户收藏状态和阅读历史
                WanwdCollect collectStatus = getUserCollectStatusForManhua(userInfo, manhuaCode);
                logger.debug("判断是请求开始阅读还是请求具体章节");
                if (bookNum == null || pageNum == null) {
                        Integer minBookNum = manHuaBrowseBLL.getMinBookNum(manhuaCode);
                        map.put("bookNum", minBookNum);
                        map.put("pictureNum", 1);
                        logger.debug("请求开始阅读，获取漫画最小章节并置为最小章节第一页，manHuaBrowseBLL.getMinBookNum=" + minBookNum);
                        logger.debug("判断用户收藏状态和阅读历史");
                        if (collectStatus != null && collectStatus.getCode() != null && collectStatus.getReadLogCode() != null&&collectStatus.getReadLogCode() > 0) {
                                // 有收藏有阅读记录，查询对应记录的信息
                                WanwdManhuaPage manhuaPage = manHuaBrowseBLL.selectPageByPrimaryKey(collectStatus.getReadLogCode());
                                logger.debug("有收藏有阅读记录，获取漫画对应页的信息，manHuaBrowseBLL.selectPageByPrimaryKey" + manhuaPage);
                                WanwdManhuaBook manhuaBook = manHuaBrowseBLL.selectBookByPrimaryKey(manhuaPage.getBookCode());
                                logger.debug("有收藏有阅读记录，获取漫画对应章的信息，manHuaBrowseBLL.selectBookByPrimaryKey" + manhuaBook);
                                // 判定阅读记录是否被编辑删了，是否还存在,未展示
                                if (manhuaBook.getIsDelete() != 1) {
                                        map.put("bookNum", manhuaBook.getBookNum());
                                        map.put("pictureNum", manhuaPage.getPagePictureNum());
                                        logger.debug("漫画章节是展示状态：" + manhuaBook.getIsDelete() + "，漫画置为阅读记录对应的章节和页数");
                                }
                        }
                } else {// 为直接请求某一页
                        map.put("bookNum", bookNum);
                        map.put("pictureNum", pageNum);
                        logger.debug("置为请求具体章节");
                }
                result = manHuaBrowseBLL.getManHuaBrowseInfo(map); // 查信息
                logger.debug("获取对应漫画某章节某页的信息，manHuaBrowseBLL.getManHuaBrowseInfo=" + result);
                if (result == null) {// 数据不存在
                        logger.debug("对应漫画某章节某页的信息不存在");
                        return null;
                } else {
                        // 判断用户收藏状态
                        if (collectStatus != null && collectStatus.getCode() != null) {
                                int writeReadlog = manHuaBrowseBLL.writeReadlog(collectStatus.getCode(), (Integer) result.get("code"));
                                result.put("collect", true);
                                logger.debug("用户收藏了漫画，保存阅读记录.manHuaBrowseBLL.writeReadlog=" + writeReadlog);
                        } else {
                                result.put("collect", false);
                                logger.debug("用户没有收藏或未登录，不保存阅读记录");
                        }
                        return result;
                }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * cn.gyyx.wd.wanjia.cartoon.service.ManHuaBrowseService#getNewestBook(cn.
         * gyyx.core.auth.UserInfo, java.lang.Integer)
         */
        @Override
        public Map<String, Object> getNewestBook(UserInfo userInfo, Integer manhuaCode) {
                int maxBookNum = manHuaBrowseBLL.getManHuaMaxBookNum(manhuaCode);
                logger.debug("获取最大章节数，manHuaBrowseBLL.getManHuaMaxBookNum=" + maxBookNum);
                Map<String, Object> map = new HashMap<>();
                map.put("manhuaCode", manhuaCode);
                map.put("bookNum", maxBookNum);
                map.put("pictureNum", 1);
                Map<String, Object> result = manHuaBrowseBLL.getManHuaBrowseInfo(map);
                logger.debug("获取漫画信息，manHuaBrowseBLL.getManHuaBrowseInfo=" + result);
                // 判断用户收藏状态和阅读历史
                WanwdCollect collectStatus = getUserCollectStatusForManhua(userInfo, manhuaCode);
                if (result == null) {
                        logger.debug("数据不存在");// 数据不存在
                        return null;
                } else {
                        logger.debug("判断用户收藏状态");// 判断用户收藏状态
                        if (collectStatus != null && collectStatus.getCode() != null) {
                                // 收藏，保存阅读记录
                                int writeReadlog = manHuaBrowseBLL.writeReadlog(collectStatus.getCode(), (Integer) result.get("code"));
                                result.put("collect", true);
                                logger.debug("用户收藏了漫画，保存阅读记录.manHuaBrowseBLL.writeReadlog=" + writeReadlog);
                        } else {
                                result.put("collect", false);
                                logger.debug("用户没有收藏或未登录，不保存阅读记录");
                        }
                }
                return result;
        }

        /**
         * 私有方法 ，获取用户收藏状态和阅读历史
         * 
         * @param userInfo
         * @param manhuaCode
         * @return
         */
        private WanwdCollect getUserCollectStatusForManhua(UserInfo userInfo, Integer manhuaCode) {
                WanwdCollect collectionStatus = null;
                logger.debug("判定用户登录");
                if (userInfo != null) {
                        // 调取collect表中是否收藏，收藏了是否有阅读记录，返回页码获取收藏和阅读历史状态
                        WanwdCollect collect = new WanwdCollect();
                        collect.setUserId(userInfo.getUserId());
                        collect.setSourcesCode(manhuaCode);
                        collectionStatus = manHuaBrowseBLL.selectCollectionStatus(collect);
                        logger.debug("用户登录，查询收藏状态，manHuaBrowseBLL.selectCollectionStatus=" + collectionStatus);
                }
                return collectionStatus;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * cn.gyyx.wd.wanjia.cartoon.service.ManHuaBrowseService#readCountPlus(java.
         * lang.Integer)
         */
        @Override
        public boolean readCountPlus(Integer manhuaCode) {
                int readCountPlusOne = manHuaBrowseBLL.readCountPlusOne(manhuaCode);
                if (readCountPlusOne == 1) {
                        return true;
                }
                return false;
        }
}
