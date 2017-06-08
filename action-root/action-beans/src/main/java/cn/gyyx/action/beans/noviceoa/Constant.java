/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/3/27 15:23
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.noviceoa;

/**
 * <p>
 * 描述:新手卡OA后台常量
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class Constant {
    /**
     * 新手卡 批次类型
     */
    public static final String NoviceBatchType = "novice";

    /**
     * 全区服服务器标识 （以-1位serverId标识全区服）
     */
    public static final Integer AllGameServerIdFlag = -1;

    public static final String ACTIVITY_KEY = "novice-";

    /**
     * 每次批量插入新手卡数目  新手卡 批量插入 MySql默认sql语句长度上限1M 10000条经记录为700多K，批次插入数暂设置为10000
     */
    public static final int Insert_Novice_Card_Batch_Count=10000;

    /**
     * 写入Excel的数据在内存中保持100行，超过100行将被刷新到磁盘
     */
    public static final int Flush_Excel_To_Disk_When_Count = 1000;

    /**
     * 批次类型缩写正则
     */
    public static final String Batch_Type_Regex="^[a-zA-Z]*";
}
