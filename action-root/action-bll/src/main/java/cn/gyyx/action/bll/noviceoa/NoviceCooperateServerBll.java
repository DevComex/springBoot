/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/3/28 11:02
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.noviceoa;

import cn.gyyx.action.beans.noviceoa.NoviceServerBean;
import cn.gyyx.action.dao.noviceoa.NoviceCooperateServerDao;

import java.util.List;

/**
 * <p>
 * 描述:异业合作 业务层
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class NoviceCooperateServerBll {
    private NoviceCooperateServerDao noviceCooperateServerDao = new NoviceCooperateServerDao();

    public int insert(NoviceServerBean bean) {
        return noviceCooperateServerDao.insert(bean);
    }

    public NoviceServerBean selectByCode(int code) {
        return noviceCooperateServerDao.selectByCode(code);
    }

    public boolean updateByCode(NoviceServerBean bean) {
        return noviceCooperateServerDao.updateByCode(bean);
    }

    public List<NoviceServerBean> selectByBatchId(int batchId) {
        return noviceCooperateServerDao.selectByBatchId(batchId);
    }

    public NoviceServerBean selectByServerId(int batchId,int serverId) {
        return noviceCooperateServerDao.selectByServerId(batchId, serverId);
    }

    public List<Integer> selectServerIdsByBatchIdGameId(int batchId, int gameId) {
        return noviceCooperateServerDao.selectServerIdsByBatchIdGameId(batchId, gameId);
    }
}
