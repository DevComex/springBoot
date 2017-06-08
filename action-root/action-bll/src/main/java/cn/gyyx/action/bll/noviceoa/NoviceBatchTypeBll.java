/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/3/23 16:35
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.noviceoa;

import cn.gyyx.action.beans.noviceoa.NoviceBatchTypeBean;
import cn.gyyx.action.dao.noviceoa.NoviceBatchTypeDao;

import java.util.List;

/**
 * <p>
 * 描述:新手卡批次类型业务层
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class NoviceBatchTypeBll {
    NoviceBatchTypeDao noviceBatchTypeDao = new NoviceBatchTypeDao();

    public int insert(NoviceBatchTypeBean bean) {
        return noviceBatchTypeDao.insert(bean);
    }

    public NoviceBatchTypeBean selectByCode(int code) {
        return noviceBatchTypeDao.selectByCode(code);
    }

    public NoviceBatchTypeBean selectBeanByBatchType(String batchType) {
        return noviceBatchTypeDao.selectBeanByBatchType(batchType);
    }

    public List<NoviceBatchTypeBean> selectList() {
        return noviceBatchTypeDao.selectList();
    }
}
