package cn.gyyx.action.service.lottery.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.vo.LotteryPrizesVO;
import cn.gyyx.action.bll.lottery.IActionPrizesAddressBLL;
import cn.gyyx.action.bll.lottery.ILotteryPrizesBLL;
import cn.gyyx.action.bll.lottery.impl.ActionPrizesAddressBLLImpl;
import cn.gyyx.action.bll.lottery.impl.LotteryPrizesBLLImpl;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.lottery.ILotteryPrizesService;

public class LotteryPrizesService implements ILotteryPrizesService {
	
	private ILotteryPrizesBLL lotteryPrizesBLL = new LotteryPrizesBLLImpl();
	private IActionPrizesAddressBLL actionPrizesAddressBLL = new ActionPrizesAddressBLLImpl();
	
	@Override
	public List<LotteryPrizesVO> getPrizesInfomations(int activityId, String userId) {
		return this.getPrizesInfomations(activityId, 0, userId);
	}
	
	@Override
	public List<LotteryPrizesVO> getPrizesInfomations(int activityId, int userCode, String userId) {
		// 获得奖品信息
		List<LotteryPrizesVO> prizesList = lotteryPrizesBLL.getPrizesInfomations(activityId, userId);
		
		// 获得地址信息
		LotteryPrizesVO address = actionPrizesAddressBLL.getAddress(activityId, userCode, userId);
		
		if (prizesList != null && prizesList.size() > 0 && address != null) {
			for (LotteryPrizesVO item : prizesList) {
				item.setUserName(address.getUserName());
				item.setUserPhone(address.getUserPhone());
				item.setUserAddress(address.getUserAddress());
			}
		}
		
		return prizesList;
	}
	
        @Override
        public ResultBean<String> putAddress(LotteryPrizesVO vo) {
            ResultBean<String> result = new ResultBean<String>();
            result.setIsSuccess(false);
            result.setMessage("保存失败！");
    
            SqlSession session = MyBatisConnectionFactory
                    .getSqlActionDBV2SessionFactory().openSession();
    
            try {
                if (actionPrizesAddressBLL.putAddress(vo, session)) {
                    session.commit();
                    result.setIsSuccess(true);
                    result.setMessage("保存成功！");
                }
            } catch (Exception e) {
                if (session != null)
                    session.rollback();
            }
            finally{
                if(null!=session)
                    session.close();
            }
            return result;
        }
}
