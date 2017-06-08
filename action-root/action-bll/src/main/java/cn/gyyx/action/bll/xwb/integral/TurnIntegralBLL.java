package cn.gyyx.action.bll.xwb.integral;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.dao.xwb.integral.TurnIntegralDAO;

public class TurnIntegralBLL {
	private static final Logger LOG = LoggerFactory
			.getLogger(TurnIntegralBLL.class);
	private TurnIntegralDAO turnIntegralDAO = new TurnIntegralDAO();
	public ResultBean<String> computeTurn(String orderNumber,String account,float amount,int integral,String servicId,String dateTime) {
		ResultBean<String> result = new ResultBean<String>();
		Map<String,String> para = new HashMap<String,String>();
		para.put("orderNum", orderNumber);
		para.put("account", account);
		para.put("integral", String.valueOf(integral));
		para.put("amount", String.valueOf(amount));
		para.put("serviceId", servicId);
		para.put("datetime", dateTime);
		para.put("result", "");
		para.put("error", "");
		if(turnIntegralDAO.computeTurn(para)){
			if(Integer.parseInt(para.get("error"))<1){
				if(Integer.parseInt(para.get("result"))<1){
					result.setProperties(true, "请求成功！", "true");
					LOG.debug(orderNumber+"--"+account+"--"+"请求成功！");
				}else{
					result.setProperties(true, "已提交过该订单，请不要重复提交！", "wrong");
					LOG.debug(orderNumber+"--"+account+"--"+"已提交过该订单，请不要重复提交！");
				}
			}else{
				result.setProperties(false, "存储过程执行异常，以回滚事务，请重新连接！", "error");
				LOG.debug(orderNumber+"--"+account+"--"+"存储过程执行异常，以回滚事务，请重新连接！");
			}
		}else{
			result.setProperties(false, "连接数据库异常！", "error");
			LOG.debug(orderNumber+"--"+account+"--"+"连接数据库异常！");
		}
		return result;
	}
}
