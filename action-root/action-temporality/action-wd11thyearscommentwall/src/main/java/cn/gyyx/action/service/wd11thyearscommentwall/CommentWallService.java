/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wd11thyearscommentwall
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月9日 上午10:57:52
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.service.wd11thyearscommentwall;

import java.util.Date;
import java.util.Map;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wd11thyearscommentwall.CommentWallBean;

/**
 * <p>
 * CommentWallService描述
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
public interface CommentWallService {
    /**
     * 
     * <p>
     * 添加留言
     * </p>
     *
     * @action caishuai 2017年3月9日 下午9:51:48 描述
     *
     * @param comment
     *            留言实体
     * @param limit
     *            每日留言限制
     * @return ResultBean<Object>
     */

    public ResultBean<Object> addComment(CommentWallBean comment, int limit);

    /**
     * 
     * <p>
     * 获取留言的一页数据，请求时间之前
     * </p>
     *
     * @action caishuai 2017年3月9日 下午5:13:56 描述
     * @action caishuai 2017年3月15日 11:42:24 
     *          添加actionCode字段,增加获取某个活动单独的留言功能
     *
     * @param pageSize页大小
     * @param pageIndex页码
     * @param endTime数据截止时间
     * @param cacheTime缓存时间
     * @param actionCode
     *            活动code，为null时默认获取全部
     * @return  Map<String, Object>
     */
    public  Map<String, Object> getCommentsByPage(int pageSize, int pageIndex,
            Date endTime, int cacheTime,Integer actionCode);
}
