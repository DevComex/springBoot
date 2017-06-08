/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/2/25 18:53
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.service.noviceoa;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.activity.ActivityConfigBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.noviceoa.Constant;
import cn.gyyx.action.beans.noviceoa.NoviceBatchBean;
import cn.gyyx.action.beans.noviceoa.NoviceBatchTypeBean;
import cn.gyyx.action.beans.noviceoa.NoviceServerBean;
import cn.gyyx.action.bll.activity.ActivityConfigBll;
import cn.gyyx.action.bll.noviceoa.NoviceBatchBll;
import cn.gyyx.action.bll.noviceoa.NoviceBatchTypeBll;
import cn.gyyx.action.bll.noviceoa.NoviceCooperateServerBll;
import cn.gyyx.action.bll.noviceoa.NoviceServerBll;
import cn.gyyx.action.service.agent.CallWebApiAgent;

import java.util.*;

/**
 * <p>
 * 描述:新手卡OA网络层
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class NoviceService {
    private NoviceBatchBll noviceBatchBll = new NoviceBatchBll();
    private NoviceServerBll noviceServerBll = new NoviceServerBll();
    private NoviceCooperateServerBll noviceCooperateServerBll = new NoviceCooperateServerBll();
    private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
    private NoviceBatchTypeBll noviceBatchTypeBll = new NoviceBatchTypeBll();
    private CallWebApiAgent callWebApiAgent = new CallWebApiAgent();

    /**
     * 添加批次信息
     *
     * @param bean           批次信息实体
     * @param activityConfig 批次活动配置信息
     * @param serverIds      服务器编号列表
     * @param serverNames    服务器名列表
     * @return
     */
    public ResultBean<String> addBatch(NoviceBatchBean bean, ActivityConfigBean activityConfig, String[] serverIds, String[] serverNames) {
        ResultBean<String> resultBean = new ResultBean<>();
        //检测批次名是否唯一
        NoviceBatchBean checkBatch = noviceBatchBll.selectBeanByName(bean.getName());
        if (checkBatch != null) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("已存在相同批次名称");
            return resultBean;
        }

        //检测批次类型是否存在
        NoviceBatchTypeBean noviceBatchTypeBean = noviceBatchTypeBll.selectBeanByBatchType(bean.getBatchType());
        if (noviceBatchTypeBean == null) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("批次类别不存在");
            return resultBean;
        }

        //添加批次信息
        int batchId = noviceBatchBll.insert(bean);

        //添加活动配置信息
        String activityCode = Constant.ACTIVITY_KEY + batchId;
        activityConfig.setActivityCode(activityCode);
        activityConfig.setNote(bean.getBatchType());
        int activityId = activityConfigBll.insert(activityConfig);
        if (activityId <= 0) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("添加活动配置信息失败");
            return resultBean;
        }

        //服务器数组转map
        if (serverIds != null) {
            Map<String, String> mapServer = new HashMap<>();

            for (int i = 0; i < serverIds.length; i++) {
                mapServer.put(serverIds[i], serverNames[i]);
            }
            //筛选绑定服务器
            resultBean = updateNoviceServer(bean.getCode(), bean.getBatchType(), mapServer, bean.getGameId());
            if (!resultBean.getIsSuccess()) {
                return resultBean;
            }
        }
        resultBean.setIsSuccess(true);
        resultBean.setMessage("业务组添加成功，请联系相关人员创建该业务组下的【新手卡号表】");
        return resultBean;
    }

    /**
     * g更新批次信息
     *
     * @param bean
     * @param activityConfig
     * @param serverIds
     * @param serverNames
     * @return
     */
    public ResultBean<String> updateBatch(NoviceBatchBean bean, ActivityConfigBean activityConfig, String[] serverIds, String[] serverNames) {
        ResultBean<String> resultBean = new ResultBean<>();
        //检测批次名是否唯一
        NoviceBatchBean checkBatch = noviceBatchBll.selectBeanByName(bean.getName());
        if (checkBatch != null && checkBatch.getCode().intValue() != bean.getCode().intValue()) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("已存在相同批次名称");
            return resultBean;
        }

        //更新批次信息
        boolean updateBatchResult = noviceBatchBll.updateByBatchId(bean);

        if (!updateBatchResult) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("更新批次信息失败");
            return resultBean;
        }

        //更新批次配置信息
        activityConfig.setActivityCode(Constant.ACTIVITY_KEY + bean.getCode());
        boolean updateActivityResult = activityConfigBll.updateActivityConfig(activityConfig);

        if (!updateActivityResult) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("更新活动配置信息失败");
            return resultBean;
        }

        Map<String, String> mapServer = new HashMap<>();

        if (serverIds != null) {
            for (int i = 0; i < serverIds.length; i++) {
                mapServer.put(serverIds[i], serverNames[i]);
            }
        }

        //更新绑定服务器状态
        resultBean = updateNoviceServer(bean.getCode(), bean.getBatchType(), mapServer, bean.getGameId());

        if (!resultBean.getIsSuccess()) {
            return resultBean;
        }
        resultBean.setIsSuccess(true);
        resultBean.setMessage("更新成功");
        return resultBean;
    }

    public ResultBean<List<ServerInfoBean.Value>> getServerList(int gameId, String batchType) {
        ResultBean<List<ServerInfoBean.Value>> resultBean = new ResultBean<>();
        List<ServerInfoBean.Value> resultServerList = new ArrayList<>();

        //获取全区服
        ResultBean<List<ServerInfoBean.Value>> serverList = CallWebApiAgent.getWdServers(gameId);

        if (serverList.getData() == null || serverList.getData().isEmpty()) {
            resultBean.setIsSuccess(false);
            resultBean.setMessage("获取服务器信息列表失败");
            return resultBean;
        }

        List<ServerInfoBean.Value> requestServerList = serverList.getData();

        if (batchType.equals(Constant.NoviceBatchType)) {
            //新手卡类型批次 筛选去除已绑定的服务器
            List<Integer> noviceServerList = noviceServerBll.selectServerIdsByGameId(gameId);

            if (noviceServerList == null || noviceServerList.isEmpty()) {
                resultBean.setData(requestServerList);
                resultBean.setIsSuccess(true);
                return resultBean;
            }
            Integer minServerId = noviceServerList.get(0);

            //获取新服服务器列表
            for (ServerInfoBean.Value temp :
                    requestServerList) {
                if (temp.getCode() > minServerId) {
                    if (!noviceServerList.contains(temp.getCode())) {
                        resultServerList.add(temp);
                    }
                }
            }
        } else {
            //非新手卡类型 加载全区服
            resultServerList = requestServerList;
        }

        resultBean.setIsSuccess(true);
        resultBean.setData(resultServerList);

        return resultBean;
    }

    /**
     * 更新服务器绑定信息
     *
     * @param batchId
     * @param servers
     * @return
     */
    private ResultBean<String> updateNoviceServer(int batchId, String batchType, Map<String, String> servers, int gameId) {
        ResultBean<String> resultBean = new ResultBean<>();

        List<NoviceServerBean> noviceServerList = null;

        //获取批次已绑定服务器信息
        if (batchType.equals(Constant.NoviceBatchType)) {
            noviceServerList = noviceServerBll.selectByBatchId(batchId);
        } else {
            //非 新手卡类型批次 不互斥批次服务器绑定状态，可添加全区服
            noviceServerList = noviceCooperateServerBll.selectByBatchId(batchId);
        }

        if (noviceServerList == null || noviceServerList.isEmpty()) {
            resultBean = addServer(batchId, batchType, servers, gameId);
            return resultBean;
        } else {
            for (NoviceServerBean tempServer :
                    noviceServerList) {
                if (servers.keySet().contains(tempServer.getServerId() + "")) {
                    //已绑定服务器 从待操作服务器中删除
                    servers.remove(tempServer.getServerId() + "");
                    continue;
                } else {
                    //解除绑定的服务器 更新删除状态
                    NoviceServerBean serverBean = new NoviceServerBean();
                    serverBean.setCode(tempServer.getCode());
                    serverBean.setIsDelete(true);
                    if (batchType.equals(Constant.NoviceBatchType)) {
                        noviceServerBll.updateByCode(serverBean);
                    } else {
                        noviceCooperateServerBll.updateByCode(serverBean);
                    }
                    //从待操作服务器中删除
                    servers.remove(tempServer.getServerId() + "");
                }
            }
            if (!servers.isEmpty()) {
                return addServer(batchId, batchType, servers, gameId);
            }
        }

        resultBean.setIsSuccess(true);
        return resultBean;
    }

    /**
     * 添加服务器
     *
     * @param batchId
     * @param servers
     * @return
     */
    private ResultBean<String> addServer(int batchId, String batchType, Map<String, String> servers, int gameId) {
        ResultBean<String> resultBean = new ResultBean<>();
        for (String key : servers.keySet()
                ) {
            if (key.isEmpty()) {
                continue;
            }
            int serverId = Integer.parseInt(key);
            NoviceServerBean serverCheck = null;

            //获取绑定表中服务器信息
            if (batchType.equals(Constant.NoviceBatchType)) {
                serverCheck = noviceServerBll.selectByServerId(serverId, gameId);
            } else {
                serverCheck = noviceCooperateServerBll.selectByServerId(batchId, serverId);
            }

            if (serverCheck == null) {
                //无绑定信息则直接构建服务器对象
                NoviceServerBean serverBean = new NoviceServerBean(null, batchId, gameId, serverId, servers.get(key), null, false, new Date());

                if (batchType.equals(Constant.NoviceBatchType) || serverId != Constant.AllGameServerIdFlag.intValue()) {
                    //获取服务器的大区编号，存入数据库，在前台使用时不再调用接口获取
                    ServerInfoBean serverInfoBean = callWebApiAgent.getServerStatusFromWebApi(serverId);
                    if (serverInfoBean != null) {
                        serverBean.setNetTypeCode(serverInfoBean.getValue().getNetTypeCode());
                    }
                } else {
                    //全区服标识的服务器信息 大区编号设为0
                    serverBean.setNetTypeCode(0);
                }
                //添加绑定服务器信息
                if (batchType.equals(Constant.NoviceBatchType)) {
                    noviceServerBll.insert(serverBean);
                } else {
                    noviceCooperateServerBll.insert(serverBean);
                }
                continue;
            }

            //已绑定其他批次的服务器 不再进行添加
            if (serverCheck.getIsDelete() == false && serverCheck.getBatchId() != batchId) {
                resultBean.setIsSuccess(false);
                resultBean.setMessage("服务器:" + servers.get(key) + "已经参与到其他批次新手卡活动中");
                return resultBean;
            }

            //已删除且 未绑定其他批次的服务器 更新绑定状态和绑定批次
            NoviceServerBean serverBean = new NoviceServerBean();
            serverBean.setCode(serverCheck.getCode());
            serverBean.setBatchId(batchId);
            serverBean.setIsDelete(false);

            if (batchType.equals(Constant.NoviceBatchType)) {
                noviceServerBll.updateByCode(serverBean);
            } else {
                noviceCooperateServerBll.updateByCode(serverBean);
            }
        }
        resultBean.setIsSuccess(true);
        return resultBean;
    }
}
