package cn.gyyx.action.bll.config.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.config.po.ActionConfigPO;
import cn.gyyx.action.beans.config.vo.ActionConfigVO;
import cn.gyyx.action.beans.enums.ActivityStatus;
import cn.gyyx.action.beans.enums.ErrorCodeEnums;
import cn.gyyx.action.bll.config.IHdConfigBLL;
import cn.gyyx.action.dao.config.IHdConfigDAO;
import cn.gyyx.action.dao.config.Impl.HdConfigDAOImpl;

/**
  * <p>
  *   获取活动的配置信息
  * </p>
  *  
  * @author GYYX_DEV
  * @since 0.0.1
  */
public class DefaultHdConfigBLL implements IHdConfigBLL {

    private static final Logger logger = LoggerFactory
            .getLogger(DefaultHdConfigBLL.class);
    
    private String message;
    private IHdConfigDAO hdConfigDAO = new HdConfigDAOImpl();

    
    /* (non-Javadoc)
     * @see cn.gyyx.action.bll.config.IHdConfigBLL#getState(int)
     */
    public int getState(int activityId) {
        
        ActionConfigPO actionPo = hdConfigDAO.getData(activityId);
        if (actionPo == null) {
            message = ErrorCodeEnums.ActivityIsInvalid.toString();
            return ErrorCodeEnums.ActivityIsInvalid.getCode();
        }

        Date now = Calendar.getInstance().getTime();
        if (actionPo.getHdStart() != null
                && actionPo.getHdStart().getTime() > now.getTime()) {
            message = ErrorCodeEnums.ActivityHasNotStart.toString();
            return ErrorCodeEnums.ActivityHasNotStart.getCode();
        }

        if (actionPo.getHdEnd() != null
                && actionPo.getHdEnd().getTime() < now.getTime()) {
            message = ErrorCodeEnums.ActivityIsOver.toString();
            return ErrorCodeEnums.ActivityIsOver.getCode();
        }

        return 1;
    }
    
    
    
    /* (non-Javadoc)
     * @see cn.gyyx.action.bll.config.IHdConfigBLL#getStatus(int)
     */
    @Override
    public ActivityStatus getStatus(int activityId){
        ActionConfigPO actionPo = hdConfigDAO.getData(activityId);
        logger.debug("Get hd config info:{}",actionPo);
        // 无效的活动
        if (actionPo == null || actionPo.getIsDelete()) {
            return ActivityStatus.IS_INVALID;
        }

        return getStatus(actionPo.getHdStart(),actionPo.getHdEnd());
    }
    
    /* (non-Javadoc)
     * @see cn.gyyx.action.bll.config.IHdConfigBLL#getStatus(int)
     */
    @Override
    public ActivityStatus getStatus(Date hdStart,Date hdEnd){
        Date now = Calendar.getInstance().getTime();
        if (hdStart != null
                && hdStart.getTime() > now.getTime()) {
            return ActivityStatus.HAS_NOT_START;
        }

        if (hdEnd != null
                && hdEnd.getTime() < now.getTime()) {
            return ActivityStatus.IS_OVER;
        }
        return ActivityStatus.IS_NORMAL;
    }


    /* (non-Javadoc)
     * @see cn.gyyx.action.bll.config.IHdConfigBLL#getConfigEntity(int)
     */
    @Override
    public ActionConfigPO getConfigEntity(int activityId) {
        return hdConfigDAO.getData(activityId);
    }



    /* (non-Javadoc)
     * @see cn.gyyx.action.bll.config.IHdConfigBLL#getConfigParams(int)
     */
    @Override
    public <T> T getConfigParams(int activityId, Class<T> type) {
        // 获得活动配置
        ActionConfigPO actionPo = hdConfigDAO.getData(activityId);
        if (actionPo == null || null == actionPo.getParas()
                || StringUtils.isEmpty(actionPo.getParas())) {
            return null;
        }
        logger.debug("Get hd config params info:{}",actionPo.getParas());
        T configParams = null;
        try {
            configParams = JSON.parseObject(actionPo.getParas(), type);
        } catch (Exception e) {
            logger.warn("invalid hd config params: {}.", actionPo.getParas());
        }

        return configParams;
    }



    /* (non-Javadoc)
     * @see cn.gyyx.action.bll.config.IHdConfigBLL#getConfigParamsByKey(int, java.lang.String)
     */
    @Override
    public String getConfigParamsByKey(int activityId, String configKey) {
        // 获得活动配置
        ActionConfigPO actionPo = hdConfigDAO.getData(activityId);
        if (actionPo == null || null == actionPo.getParas()
                || StringUtils.isEmpty(actionPo.getParas())) {
            return null;
        }
        logger.debug("Get hd config params info:{}",actionPo.getParas());
        
        // 获取参数配置
        Map<String, String> configParams = null;
        try {
            configParams = JSON.parseObject(actionPo.getParas(), Map.class);
        } catch (Exception e) {
            logger.warn("invalid hd config params: {}.", actionPo.getParas());
        }
        
        // 获取具体参数 
        if (configParams != null && configParams.containsKey(configKey)) {
            return configParams.get(configKey);
        }

        return null;
    }
    
    /* (non-Javadoc)
     * @see cn.gyyx.action.bll.config.IHdConfigBLL#getLotteryScore(int)
     */
    @Override
    public int getLotteryScore(int activityId) {
        // 获得活动配置
        ActionConfigPO actionPo = hdConfigDAO.getData(activityId);
        if (actionPo == null) {
            message = "无效活动！";
            return -1;
        }

        // 获得活动配置参数
        if (null == actionPo.getParas()
                || StringUtils.isEmpty(actionPo.getParas())) {
            message = "无效活动！";
            return -1;
        }

        ActionConfigVO configVO = JSON.parseObject(actionPo.getParas(),
            ActionConfigVO.class);
        if (null == configVO) {
            message = "活动配置参数无效！";
            return -1;
        }
        if (null == configVO.getLotteryScore()) {
            message = "活动配置积分参数无效！";
            return -1;
        }

        // 获得抽奖积分
        if (null != configVO.getLotteryScore()
                && configVO.getLotteryScore() > 0) {
            return configVO.getLotteryScore();
        }

        return -1;
    }

    
    /* (non-Javadoc)
     * @see cn.gyyx.action.bll.config.IHdConfigBLL#getConfigByKey(int, java.lang.String)
     */
    @Override
    public Object getConfigByKey(int activityId, String key) {
        // 获得活动配置
        ActionConfigPO actionPo = hdConfigDAO.getData(activityId);
        if (actionPo == null) {
            message = "无效活动！";
            return null;
        }

        // 获得活动配置参数
        if (null == actionPo.getParas()
                || StringUtils.isEmpty(actionPo.getParas())) {
            message = "无效活动！";
            return null;
        }
        
        Map<String, Object> params = JSON.parseObject(actionPo.getParas());
        if (null == params) {
            message = "活动配置参数无效！";
            return null;
        }

        if (params.containsKey(key)) {
            return params.get(key);
        }

        return null;
    }

    public String getMessage() {
        return message;
    }
}
