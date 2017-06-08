/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月4日下午5:58:06
 * 版本号：v1.0
 * 本类主要用途叙述：用户中奖的信息的配置
 */



package cn.gyyx.action.dao.lottery;

import java.util.HashMap;

public interface IUserLotteryByOrder {

    /**
     * 更新并得到用户抽奖的名词
     * @param map 存储过程所用到的信息
     */
    public void getOrderByUserCode(HashMap<String, Integer> map);
    
    
}
