 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月9日下午2:23:23
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.bll;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.TimeLineBean;
import cn.gyyx.playwd.dao.playwd.TimeLineDao;

/**
 * 用户称号信息
 * @author lidudi
 *
 */
@Component
public class TimeLineBll {

	@Autowired
	public TimeLineDao timeLineDao;
	
	/**
	 * 插入
	 * @param operateId
	 * @param toUserId
	 * @param contentType
	 * @param contentId
	 * @param operateType
	 * @param createTime
	 * @param description
	 * @return
	 */
	public int insert(Integer operateId, Integer toUserId,
			String contentType, Integer contentId, String operateType,
			Date createTime, String description) {
		TimeLineBean bean = new TimeLineBean();
		bean.setFromUserId(operateId);
		bean.setToUserId(toUserId);
		bean.setContentType(contentType);
		bean.setContentId(contentId);
		bean.setOperateType(operateType);
		bean.setCreateTime(new Date());
		bean.setDescription(description);
		return timeLineDao.insert(bean);
	}

	/**
	 * 获取数量
	 * @param contentType
	 * @param contentId
	 * @param userId
	 * @return
	 */
	public int getCount(String contentType, int contentId,
			Integer userId,String operatorType) {
		TimeLineBean bean = new TimeLineBean();
		bean.setContentId(contentId);
		bean.setContentType(contentType);
		bean.setFromUserId(userId);
		bean.setOperateType(operatorType);
		return timeLineDao.getCount(bean);
	}

}
