package cn.gyyx.action.bll.config;

import java.util.Date;

import cn.gyyx.action.beans.config.po.ActionConfigPO;
import cn.gyyx.action.beans.enums.ActivityStatus;

/**
  * <p>
  *   IHdConfigBLL活动配置读取的基本操作
  * </p>
  *  
  * @author GYYX_DEV
  * @since 0.0.1
  */
public interface IHdConfigBLL {
	
	/**
	  * <p>
	  *    活动状态的获取： 活动无效、活动已经结束、活动尚未开始
	  * </p>
	  *
	  * @action
	  *    bozhencheng add 2017年3月4日 下午4:56:20 添加
	  *
	  * @param activityId 活动的id
	  * @return int 返回 1 为活动正常
	  */

        @Deprecated
	int getState(int activityId);
	
        /**
         * <p>
         *    获取活动当前的状态
         * </p>
         * 
         * <p>
         *    活动的状态用枚举类{@code ActivityStatus}的元素来标识状态
         * </p>
         *
         * @action
         *    bozhencheng add 2017年3月4日 下午5:05:09 描述
         *
         * @param activityId
         * @return ActivityStatus
         */
	ActivityStatus getStatus(int activityId);
	
	/**
         * <p>
         *    获取活动当前的状态
         * </p>
         * 
         * <p>
         *    活动的状态用枚举类{@code ActivityStatus}的元素来标识状态
         * </p>
         *
         * @action
         *    bozhencheng add 2017年3月4日 下午5:05:09 描述
         *
         * @param hdStart
         * @param hdEnd
         * @return ActivityStatus
         */
        ActivityStatus getStatus(Date hdStart,Date hdEnd);
	
	
	/**
	  * <p>
	  *    获取活动的所有配置，并返回对应的实体
	  * </p>
	  *
	  * @action
	  *    bozhencheng add 2017年3月4日 下午5:20:41 添加新方法
	  *
	  * @param activityId
	  * @return ActionConfigPO  为空返回null
	  */
	ActionConfigPO getConfigEntity(int activityId);
	
	
	/**
	  * <p>
	  *    获取活动的params自定参数配置，并返回自定义类型
	  * </p>
	  *
	  * @action
	  *    bozhencheng add 2017年3月4日 下午5:25:00 描述
	  *
	  * @param activityId
	  * @return T  为空返回null
	  */
	 <T> T getConfigParams(int activityId, Class<T> type);
	
	/**
	  * <p>
	  *    根据configKey获取params自定义参数配置中具体的内容， 返回自定义类型
	  * </p>
	  *
	  * @action
	  *    bozhencheng add 2017年3月4日 下午5:23:42 描述
	  *
	  * @param activityId
	  * @param configKey
	  * @return T  为空返回null
	  */
	String getConfigParamsByKey(int activityId, String configKey);
	
	/**
	  * <p>
	  *    不在使用
	  * </p>
	  *
	  * @action
	  *    bozhencheng add 2017年3月4日 下午5:15:57 描述
	  *
	  * @param activityId
	  * @return int
	  */
	int getLotteryScore(int activityId);
	
	/**
	  * <p>
          *    不在使用
	  * </p>
	  *
	  * @action
	  *    bozhencheng add 2017年3月4日 下午5:18:39 描述
	  *
	  * @param activityId
	  * @param key
	  * @return Object
	  */
	Object getConfigByKey(int activityId, String key);
	
	String getMessage();
}
