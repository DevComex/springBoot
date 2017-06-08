package cn.gyyx.action.service.address;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.enums.ActivityTypeEnums;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.bll.lottery.IActionPrizesAddressBLL;
import cn.gyyx.action.bll.lottery.impl.ActionPrizesAddressBLLImpl;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.distribute.lock.DistributedLock;

public abstract class ActionPrizesAddressService implements IActionPrizesAddressService {

	protected IActionPrizesAddressBLL actionPrizesAddressBLL = new ActionPrizesAddressBLLImpl();
	
	// 保存地址
	@Override
	public ResultBean<String> post(ActionPrizesAddressPO params) {
		ResultBean<String> result = new ResultBean<String>();
		result.setIsSuccess(false);
		result.setMessage("保存失败！");
		
		if (null == params) {
			result.setMessage("参数为空！");
			return result;
		}
		if (null == params.getActivityId()) {
			result.setMessage("活动ID不能为空！");
			return result;
		}
		if (null == params.getUserId()) {
			result.setMessage("用户账号不能为空！");
			return result;
		}
		// 默认为抽奖地址
		if (null == params.getActivityType()) params.setActivityType(ActivityTypeEnums.Lottery.toString());
		
		// 防止用户提交
		try(DistributedLock lock = new DistributedLock(params.getActivityId() + "-" + params.getUserId() + "-address-service")) {
			lock.weakLock(30, 0);
			
			SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();
			try {
				if (actionPrizesAddressBLL.post(params, session)) {
					session.commit();
					result.setIsSuccess(true);
					result.setMessage("保存成功！");
				}
			}
			catch(Exception e) {
				if (session != null) session.rollback();
				logger.error("ActionPrizesAddressServiceImpl->post->sql->Cause:" + e.getCause());
				logger.error("ActionPrizesAddressServiceImpl->post->sql->Message:" + e.getMessage());
				logger.error("ActionPrizesAddressServiceImpl->post->sql->StackTrace:" + e.getStackTrace());
			}
			finally {
				if (session != null) session.close();
			}
		}
		catch(Exception e) {
			logger.error("ActionPrizesAddressServiceImpl->post->Cause:" + e.getCause());
			logger.error("ActionPrizesAddressServiceImpl->post->Message:" + e.getMessage());
			logger.error("ActionPrizesAddressServiceImpl->post->StackTrace:" + e.getStackTrace());
		}
		
		return result;
	}
}
