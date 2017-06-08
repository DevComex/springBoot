/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wd11thyearscommentwall
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月9日 上午10:38:47
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.bll.wd11thyearscommentwall;

import java.util.Date;
import java.util.List;

import cn.gyyx.action.beans.wd11thyearscommentwall.CommentWallBean;

/**
 * <p>
 * CommentWallBll描述
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
public interface CommentWallBll {
    /**
     * 
     * <p>
     * 获取前k条的留言的list，by createTime desc。
     * </p>
     *
     * @action caishuai 2017年3月9日 上午10:49:35 描述
     *
     * @param size请求数据大小
     * @return List<CommentBean>
     */
    public List<CommentWallBean> getTopCommentsList(int size);

    /**
     * 
     * <p>
     * 添加留言
     * </p>
     *
     * @action caishuai 2017年3月10日 下午2:11:09 描述
     *
     * @param comment留言实体
     * @return boolean
     */
    public boolean addComments(CommentWallBean comment);

    /**
     * 
     * <p>
     * 获取留言的一页数据，请求时间之前。
     * actionCode对应的记录，actionCode==null时默认全部
     * </p>
     *
     * @action caishuai 2017年3月9日 下午5:13:56 描述
     * @action caishuai 2017年3月15日 11:42:24 
     *          添加actionCode字段,增加获取某个活动单独的留言功能
     * 
     * @param start开始位置
     * @param end结束位置
     * @param endTime数据截止时间
     * @param actionCode
     *            活动code，为null时默认获取全部
     * @return List<CommentBean>
     */
    public List<CommentWallBean> getCommentsByPage(int start, int end,
            Date endTime, Integer actionCode);

    /**
     * 
     * <p>
     * 获取留言的数据总数，请求时间之前。
     * actionCode对应的记录，actionCode==null时默认全部
     * </p>
     *
     * @action caishuai 2017年3月9日 下午5:13:56 描述
     * @action caishuai 2017年3月15日 11:42:24 
     *          添加actionCode字段,增加获取某个活动单独的留言功能
     *
     * @param endTime数据截止时间
     * @param actionCode
     *            活动code，为null时默认获取全部
     * @return int
     */
    public int getCommentsCount(Date endTime, Integer actionCode);

}
