/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/2/23 16:19
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.noviceoa;

import cn.gyyx.action.beans.noviceoa.NoviceBatchBean;
import cn.gyyx.action.dao.noviceoa.NoviceBatchDao;

import java.util.List;

/**
 * <p>
 * 描述:新手卡批次管理业务逻辑层
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class NoviceBatchBll {
    private NoviceBatchDao noviceBatchDao = new NoviceBatchDao();

    /**
     * 添加批次信息
     *
     * @param record
     * @return
     */
    public int insert(NoviceBatchBean record) {
        return noviceBatchDao.insert(record);
    }

    /**
     * 根据批次号更新批次信息
     *
     * @param record
     * @return
     */
    public boolean updateByBatchId(NoviceBatchBean record) {
        return noviceBatchDao.update(record);
    }

    /**
     * 根据批次号获取批次信息
     *
     * @param code
     * @return
     */
    public NoviceBatchBean selectBeanByCode(Integer code) {
        return noviceBatchDao.selectBeanByCode(code);
    }

    /**
     * 根据批次名获取批次信息
     *
     * @param batchName
     * @return
     */
    public NoviceBatchBean selectBeanByName(String batchName) {
        return noviceBatchDao.selectBeanByName(batchName);
    }

    /**
     * 获取批次数目
     *
     * @return
     */
    public int selectTotalCount(String batchType) {
        return noviceBatchDao.selectTotalCount(batchType);
    }

    /**
     * 获取分页批次列表
     *
     * @param gameId
     * @param index
     * @param pageCount
     * @return
     */
    public List<NoviceBatchBean> selectPageList(int gameId, int index, int pageCount, String batchType) {
        return noviceBatchDao.selectPageList(gameId, index, pageCount, batchType);
    }

    /**
     * 获取批次列表
     *
     * @param gameId
     * @return
     */
    public List<NoviceBatchBean> selectBatchList(int gameId, String batchType) {
        return noviceBatchDao.selectBatchList(gameId, batchType);
    }
}
