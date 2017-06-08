/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-bll
 * @作者：guoyonggang
 * @联系方式：guoyonggang@gyyx.cn
 * @创建时间：2017年2月27日
 * @版本号：1.0
 * @本类主要用途描述：  
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.wechatcharge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.wechatcharge.QueryConditionBean;
import cn.gyyx.action.beans.wechatcharge.WechatChargeBean;
import cn.gyyx.action.beans.wechatcharge.WechatChargePaginationBean;
import cn.gyyx.action.dao.wechatcharge.WechatChargeDAO;

/**
 * <p>
 * 微信兑换信息BLL层操作
 * </p>
 * 
 * @author guoyonggang
 * @since 0.0.1
 */
public class WechatChargeBLL {

    private WechatChargeDAO dao = new WechatChargeDAO();

    private static final Logger logger = LoggerFactory
            .getLogger(WechatChargeBLL.class);

    /**
     * 
     * <p>
     * 添加兑换信息
     * </p>
     *
     * @action 后台添加兑换奖品信息
     *
     * @param record
     * @return boolean
     */
    public boolean addWechatChargeInfo(WechatChargeBean record) {
        return dao.addWechatChargeInfo(record);
    }

    /**
     * 
     * <p>
     * 删除兑换信息
     * </p>
     *
     * @action 后台添加兑换奖品信息
     *
     * @param record
     * @return boolean
     */
    public boolean delete(Integer code) {
        return dao.delete(code);
    }

    /**
     * 
     * <p>
     * 获取兑换信息
     * </p>
     *
     * @action 后台获取兑换奖品信息
     *
     * @param record
     * @return WechatChargeBean
     */
    public WechatChargeBean getWechatChargeInfo(Integer code) {
        return dao.getWechatChargeInfo(code);
    }

    /**
     * 
     * <p>
     * 后台更新兑换信息
     * </p>
     *
     * @action guoyonggang 2017年2月28日 下午8:02:44 描述
     *
     * @param record
     * @return boolean
     */
    public boolean update(WechatChargeBean record) {
        return dao.update(record);
    }

    /**
     * <p>
     * 分页查询兑换信息
     * </p>
     *
     * @action guoyonggang 2017年2月28日 下午1:19:39 描述
     *
     * @param queryCondition
     * @return List<WechatChargeBean>
     */
    public List<WechatChargeBean> queryWechatChargeList(
            QueryConditionBean queryCondition) {
        logger.info("queryWechatChargeList:condition{}", queryCondition);
        WechatChargePaginationBean condition = new WechatChargePaginationBean();
        condition.setStrWhere(getWhereSql(queryCondition));
        int pageIndex = queryCondition.getPageIndex();
        condition.setPageIndex(pageIndex);
        int pageSize = queryCondition.getPageSize();
        condition.setPageSize(pageSize);
        condition.setForwardPage((pageIndex - 1) * pageSize);
        return dao.queryWechatChargeList(condition);
    }

    /**
     * <p>
     * 分页查询兑换信息总数
     * </p>
     *
     * @action guoyonggang 2017年2月28日 下午1:20:04 描述
     *
     * @param queryCondition
     * @return int
     */
    public int queryWechatChargeCount(QueryConditionBean queryCondition) {
        logger.info("queryWechatChargeCount:condition{}", queryCondition);
        WechatChargePaginationBean condition = new WechatChargePaginationBean();
        condition.setStrWhere(getWhereSql(queryCondition));
        int pageIndex = queryCondition.getPageIndex();
        condition.setPageIndex(pageIndex);
        int pageSize = queryCondition.getPageSize();
        condition.setPageSize(pageSize);
        return dao.queryWechatChargeCount(condition);
    }

    /**
     * <p>
     * 查询要导出数据
     * </p>
     *
     * @action guoyonggang 2017年2月28日 下午1:13:16 描述
     *
     * @param strWhere
     * @return List<WechatChargeBean>
     */
    public List<WechatChargeBean> queryWechatChargeListForExport(
            QueryConditionBean queryCondition) {

        return dao.queryWechatChargeListForExport(getWhereSql(queryCondition));
    }

    /**
     * <p>
     * 查询兑换记录
     * </p>
     *
     * @action guoyonggang 2017年2月28日 下午1:13:16 描述
     *
     * @param openId
     * @return List<ChargeResultBean>
     */
    public List<Map<String,Object>> queryWechatChargeListByOpenId(String openId) {
        List<Map<String,Object>> resultMap=new ArrayList<Map<String,Object>>();
        List<WechatChargeBean> list = dao.queryWechatChargeListByOpenId(openId);
        for (WechatChargeBean item : list) { 
            Map<String,Object> map=new HashMap<>();
            map.put("nickName", item.getNickName());
            map.put("prizeType", item.getPrizeType());
            map.put("prizeName", item.getPrizeName());
            map.put("chargeTime", item.getChargeTime());
            map.put("cardNo", item.getCardNo());
            map.put("cardPwd", item.getCardPwd());
            map.put("expressNumber", item.getExpressNumber());
            resultMap.add(map);  
        }
        return resultMap;
        
    }

    /**
     * <p>
     * 根据兑换码查询兑换信息
     * </p>
     *
     * @action guoyonggang 2017年2月28日 下午1:13:16 描述
     *
     * @param chargeCode
     * @return ChargeResultBean
     */
    public WechatChargeBean queryWechatChargeByChargeCode(String chargeCode) {
        return dao.queryWechatChargeByChargeCode(chargeCode);
    }

    /**
     * 
     * <p>
     * 生成whereSql查询条件语句
     * </p>
     *
     * @action guoyonggang 2017年3月1日 下午1:04:14 描述
     *
     * @param queryCondition
     * @return String
     */
    private String getWhereSql(QueryConditionBean queryCondition) {
        String strWhere = "is_delete=0";
        try {
            String channelName = queryCondition.getChannelName();
            if (!channelName.equals("All")) {
                strWhere = strWhere.concat(" and channel_name='")
                        .concat(channelName)
                        .concat("'");
            }
            String timeType = queryCondition.getTimeType();
            String beginTime = queryCondition.getBeginTime();
            String endTime = queryCondition.getEndTime();
            if (timeType.equals("awardTime")) {
                strWhere = concatConditionSql(strWhere, beginTime," and award_time>='");
                strWhere = concatConditionSql(strWhere, endTime," and award_time<='");
            } else if (timeType.equals("chargeTime")) {
                strWhere = concatConditionSql(strWhere, beginTime," and charge_time>='");
                strWhere = concatConditionSql(strWhere, endTime," and charge_time<='");           
            } else if (timeType.equals("chargeEndTime")) {
                strWhere = concatConditionSql(strWhere, beginTime," and charge_end_time>='");
                strWhere = concatConditionSql(strWhere, endTime," and charge_end_time<='");
            }
            String conditionType = queryCondition.getConditionType();
            String conditionValue = queryCondition.getConditionValue();
            if (conditionType.equals("actionName")) {
                strWhere = concatConditionSql(strWhere, conditionValue," and action_name='");
            } else if (conditionType.equals("prizeName")) {
                strWhere = concatConditionSql(strWhere, conditionValue," and prize_name='");
            } else if (conditionType.equals("chargeCode")) {
                strWhere = concatConditionSql(strWhere, conditionValue," and charge_code='");
            }
        } catch (Exception ex) {
            logger.error("create strWhereSql error:{}",
                Throwables.getStackTraceAsString(ex));
        }
        return strWhere;
    }

    /**
      * <p>
      *    根据查询值生成查询条件
      * </p>
      *
      * @action
      *    guoyonggang 2017年3月1日 下午4:56:28 描述
      *
      * @param strWhere
      * @param conditionValue
      * @return String
      */
    private String concatConditionSql(String strWhere, String conditionValue,String partSql) {
        if (conditionValue != null
                && !conditionValue.trim().equals("")) {
            strWhere = strWhere.concat(partSql)
                    .concat(conditionValue.trim()).concat("'");
        }
        return strWhere;
    }
}
