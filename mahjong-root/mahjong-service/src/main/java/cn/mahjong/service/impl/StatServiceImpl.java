package cn.mahjong.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.RechargeLogBll;
import cn.mahjong.service.StatService;

@Service
public class StatServiceImpl implements StatService {
  @Autowired
  private RechargeLogBll rechargeLogBll;
  
  public void setRechargeLogBll(RechargeLogBll rechargeLogBll) {
    this.rechargeLogBll = rechargeLogBll;
  }
  /*
   * (non-Javadoc)
   * 
   * @see cn.mahjong.service.StatService#personSaleSum(java.lang.String, java.lang.String,
   * java.lang.Integer, java.lang.Integer, int)
   */
  @Override
  public ResultBean<Map<String, Object>> personSaleSum(String start, String end,
      Integer pageIndex, Integer pageSize, int userId) {
    List<Map<String, Object>> list=rechargeLogBll.selectListByOperatorId(start,end, pageIndex,pageSize,userId);
    Map<String, Object> sum=rechargeLogBll.selectCountByOperatorId(start,end,userId);
    Map<String, Object> result = new HashMap<>();
    result.put("list", list);
    result.put("count", sum);
    return new ResultBean<>(true, "查询个人销售记录成功", result);
  }
  /* (non-Javadoc)
   * @see cn.mahjong.service.StatService#personPurchaseSum(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, int)
   */
  @Override
  public ResultBean<Map<String, Object>> personPurchaseSum(String start, String end,
      Integer pageIndex, Integer pageSize, int userId) {
    List<Map<String, Object>> list=rechargeLogBll.selectListByUserId(start,end, pageIndex,pageSize,userId);
    Map<String, Object> sum=rechargeLogBll.selectCountByUserId(start,end,userId);
    Map<String, Object> result = new HashMap<>();
    result.put("list", list);
    result.put("count", sum);
    return new ResultBean<>(true, "查询个人进货记录成功", result);
  }

}
