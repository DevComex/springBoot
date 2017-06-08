/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/2/25 20:26
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.activity;

import cn.gyyx.action.beans.activity.ActivityConfigBean;
import cn.gyyx.action.dao.activity.ActivityConfigDao;

import java.util.Date;

/**
 * <p>
 * 描述:activity活动业务逻辑层
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class ActivityConfigBll {
    private ActivityConfigDao activityConfigDao = new ActivityConfigDao();

    public int insert(ActivityConfigBean bean) {
        return activityConfigDao.insert(bean);
    }

    public boolean updateIsOpenStatus(String activityCode, boolean isOpen) {
        ActivityConfigBean activityConfigBean = new ActivityConfigBean();
        activityConfigBean.setActivityCode(activityCode);
        activityConfigBean.setIsOpen(isOpen);

        return activityConfigDao.updateByActivityCode(activityConfigBean);
    }

    public boolean updateActivityConfig(ActivityConfigBean bean) {
        return activityConfigDao.updateByActivityCode(bean);
    }

    public ActivityConfigBean selectBeanByActivityCode(String activityCode) {
        return activityConfigDao.selectBeanByActivityCode(activityCode);
    }
}
