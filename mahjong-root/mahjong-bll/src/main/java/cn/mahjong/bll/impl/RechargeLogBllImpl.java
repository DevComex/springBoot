package cn.mahjong.bll.impl;

import cn.mahjong.beans.RechargeLog;
import cn.mahjong.beans.RechargeSummary;
import cn.mahjong.bll.RechargeLogBll;
import cn.mahjong.dao.RechargeLogMapper;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RechargeLogBllImpl implements RechargeLogBll {

  @Autowired
  private RechargeLogMapper mapper;

  @Override
  public List<RechargeLog> get(int userId, Date start, Date end, int pageIndex) {
    int take = 10;
    int skip = take * (pageIndex - 1);
    return mapper.selectByUserId(userId, start, end, skip, take);
  }

  @Override
  public int count(int userId, Date start, Date end) {
    return mapper.countByUserId(userId, start, end);
  }

  @Override
  public RechargeSummary getSummary(int userId, Date start, Date end) {
    return mapper.selectSummaryByUserId(userId, start, end);
  }

  /* (non-Javadoc)
   * @see cn.mahjong.bll.RechargeLogBll#selectListByOperatorId(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, int)
   */
  @Override
  public List<Map<String, Object>> selectListByOperatorId(String start, String end,
      Integer pageIndex, Integer pageSize, int userId) {
    int startSize = (pageIndex - 1) * pageSize;
    int endSize =   pageSize;
    
    return mapper.selectListByOperatorId(start,end, startSize,endSize,userId);
  }

  /* (non-Javadoc)
   * @see cn.mahjong.bll.RechargeLogBll#selectCountByOperatorId(java.lang.String, java.lang.String, int)
   */
  @Override
  public Map<String, Object> selectCountByOperatorId(String start, String end, int userId) {
    return mapper.selectCountByOperatorId(start,end,userId);
  }
  @Override
  public void insert(RechargeLog log) {
    mapper.insert(log);
  }

  /* (non-Javadoc)
   * @see cn.mahjong.bll.RechargeLogBll#selectListByUserId(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, int)
   */
  @Override
  public List<Map<String, Object>> selectListByUserId(String start, String end, Integer pageIndex,
      Integer pageSize, int userId) {
    int startSize = (pageIndex - 1) * pageSize;
    int endSize =   pageSize;
    return mapper.selectListByUserId(start,end, startSize,endSize,userId);
  }

  /* (non-Javadoc)
   * @see cn.mahjong.bll.RechargeLogBll#selectCountByUserId(java.lang.String, java.lang.String, int)
   */
  @Override
  public Map<String, Object> selectCountByUserId(String start, String end, int userId) {
    return mapper.selectCountByUserId(start,end,userId);
  }
}
