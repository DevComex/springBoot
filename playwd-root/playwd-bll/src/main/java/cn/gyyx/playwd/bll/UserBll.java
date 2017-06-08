package cn.gyyx.playwd.bll;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.playwd.beans.playwd.UserBean;
import cn.gyyx.playwd.dao.playwd.UserDao;

@Component
public class UserBll {
	@Autowired
	UserDao userDao;
	
	private static XMemcachedClientAdapter xMemcachedClientAdapter = new XMemcachedClientAdapter(
			"Action-ListData");
	
	/**
	 * 插入
	 * @param record
	 * @return
	 */
	public int addRecord(int userId,BigDecimal rmb,int silverCoin) {
		UserBean record=new UserBean();
		record.setUserId(userId);
		record.setIcon("");
		record.setRmb(rmb);
		record.setSilverCoin(silverCoin);
		return userDao.insert(record);
	}

    /**
	 * 根据code获取
	 * @param record
	 * @return
	 */
	public UserBean getByUserId(Integer userId) {
		UserBean bean = userDao.getByUserId(userId);
		if(bean == null){
			this.addRecord(userId,new BigDecimal(0),0);
			bean = userDao.getByUserId(userId);
		}
		return bean;
	}

    /**
     * 更新ByCode
     * @param record
     * @return
     */
	public int updateByCode(UserBean record) {
		return userDao.updateByCode(record);
	}
	
	/**
     * 更新ByUserId
     * @param record
     * @return
     */
	public int updateByUserId(UserBean record) {
		return userDao.updateByUserId(record);
	}

	/**
	 * 计算累计财富 累计财富 换算成元宝 (1块钱等于100银元宝)
	 * @param memberInfoBean
	 * @return
	 */
	public double getSumReward(UserBean memberInfoBean) {
		double sumMoney = 0d;
		if(memberInfoBean.getRmb() != null){
			sumMoney += memberInfoBean.getRmb().doubleValue() * 100;
		}
		if(memberInfoBean.getSilverCoin() != null){
			sumMoney += memberInfoBean.getSilverCoin();
		}
		return sumMoney;
	}

	/**
	 * 获取累计财富排名 超过1000名 暂不排名
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	public void getRewardInfoRank(UserBean memberInfoBean) throws Exception {
		memberInfoBean.setRankNum(-1);
		memberInfoBean.setRewardSum(0d);
		List<LinkedHashMap<String,String>> list = this.getRewardRankTopN(1000);
		if(list != null && list.size() > 0){
			for(Map<String,String> m : list){
				int userId = Integer.parseInt(m.get("userId"));
				int userRank = Integer.parseInt(m.get("userRank"));
				float rmb = Float.parseFloat(m.get("rmb"));
				int silverCoin = Integer.parseInt(m.get("silverCoin"));
				if(userId == memberInfoBean.getUserId()){
					memberInfoBean.setRankNum(userRank);
					memberInfoBean.setRmb(BigDecimal.valueOf(rmb));
					memberInfoBean.setSilverCoin(silverCoin);
					memberInfoBean.setRewardSum(this.getSumReward(memberInfoBean));
					return;
				}
			}
		}
	}
	
	/**
	 * 奖励排名信息 eg: userId:1,userRank:1,rmb:1,silverSoin:1
	 * @param topN
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LinkedHashMap<String,String>> getRewardRankTopN(int topN) throws Exception{
		List<LinkedHashMap<String,String>> list = null;
		String key = "playwd_rewardrank_top_"+topN;
		//查看缓存是否有值,有则返回
		list = xMemcachedClientAdapter.get(key, List.class);
		if(list == null){
			list = new ArrayList<>();
			//查看前1000名放入缓存
			List<UserBean> mList = userDao.getTopNOrderByUserReward(topN);
			this.sortUserByReward(mList);
			if(mList.size() > 0){
				for(UserBean m : mList){
					LinkedHashMap<String,String> map = new LinkedHashMap<>();
					map.put("userId", m.getUserId()+"");
					map.put("userRank", m.getRankNum()+"");
					map.put("rmb", (m.getRmb() != null ? m.getRmb().floatValue() : 0)+"");
					map.put("silverCoin", m.getSilverCoin()+"");
					list.add(map);
				}
				xMemcachedClientAdapter.set(key, 60, list);
			}
		}
		return list;
	}

	/**
	 * 更新财富 
	 * @param userId
	 * @param rmb
	 * @param silverCoin
	 * @return
	 */
	public int changeRmbAndSilverCoin(int userId,BigDecimal rmb,int silverCoin){
		return userDao.updateRmbAndSilverCoin(rmb, silverCoin, userId);
	}
	
	/**
	 * 相同奖品排序一样
	 * @param mList
	 */
	private void sortUserByReward(List<UserBean> mList) {
		for(int i=0;i<mList.size();i++){
			UserBean b = mList.get(i);
			b.setRewardSum(this.getSumReward(b));
			if(i!=0){
				UserBean c = mList.get(i-1);
				
				if(c.getRewardSum() == b.getRewardSum()){
					b.setRankNum(c.getRankNum());
				}else{
					b.setRankNum(i+1);
				}
			}else{
				b.setRankNum(1);
			}
		}
	}
	
	/**
	 * 加密显示账号
	 * @param account
	 * @return
	 */
	public String accountEncrypt(String account){
    	if(account == null){
			return "";
		}
		if(account.length() > 4 ){
			return account.substring(0,2) + "****" + account.substring(account.length() - 2,account.length());
		}
		return account;
    }
}