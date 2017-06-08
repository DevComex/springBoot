package cn.gyyx.action.bll.noviceoa;
/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/2/25 18:29
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */

import cn.gyyx.action.beans.noviceoa.NoviceServerBean;
import cn.gyyx.action.dao.noviceoa.NoviceServerDao;

import java.util.List;

/**
 * <p>
 * 描述:
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class NoviceServerBll {
    NoviceServerDao noviceServerDao = new NoviceServerDao();

    public int insert(NoviceServerBean bean) {
        return noviceServerDao.insert(bean);
    }

    public boolean updateByCode(NoviceServerBean bean) {
        return noviceServerDao.updateByCode(bean);
    }

    public NoviceServerBean selectByCode(int code) {
        return noviceServerDao.selectByCode(code);
    }

    public List<NoviceServerBean> selectByBatchId(int batchId) {
        return noviceServerDao.selectByBatchId(batchId);
    }

    public NoviceServerBean selectByServerId(int serverId, int gameId) {
        return noviceServerDao.selectByServerId(serverId, gameId);
    }

    public NoviceServerBean selectMaxServer(int gameId) {
        return noviceServerDao.selectMaxServer(gameId);
    }

    public List<Integer> selectServerIdsByGameId(int gameId){
        return noviceServerDao.selectServerIdsByGameId(gameId);
    }
}
