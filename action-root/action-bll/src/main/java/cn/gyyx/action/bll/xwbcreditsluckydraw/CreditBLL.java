/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月10日 上午10:58:35
 * @版本号：
 * @本类主要用途描述：积分逻辑类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.CreditsBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.SumCreditBean;
import cn.gyyx.action.dao.xwbcreditsluckydraw.CreditsDAO;
import cn.gyyx.action.dao.xwbcreditsluckydraw.SumCreditDAO;

public class CreditBLL {

	private SumCreditDAO sumCreditDao = new SumCreditDAO();
	private CreditsDAO creditsDao = new CreditsDAO();
	
	/**
	 * 签到得积分（）
	 * @param credit
	 */
	public void addSignCredit(CreditsBean credit){
		int sumCredit = getSumCredit(credit.getAccount());
	}
	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: addSumCredit
	 * @Description: TODO 添加总积分表新用户
	 * @param credit
	 * @return int
	 */
	public int addSumCredit(SumCreditBean credit) {
		return sumCreditDao.addSumCredit(credit);
	}

	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: setSumCredit
	 * @Description: TODO 修改用户总积分
	 * @param credit
	 * @return int
	 */
	public int setSumCredit(SumCreditBean credit) {
		return sumCreditDao.setSumCredit(credit);
	}
	public SumCreditBean getCreditByAccount(String account){
		return sumCreditDao.getCreditByAccount(account);
		
	}
	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: addCredits
	 * @Description: TODO 增加用户积分记录
	 * @param credit
	 * @return int
	 */
	public int addCredits(CreditsBean credit) {
		return creditsDao.addCredits(credit);
	}

	/**
	 * @日期：2015年7月10日
	 * @Title: getSumCredit
	 * @Description: TODO 根据用户账户获取总积分
	 * @param account
	 * @return int
	 */
	public int getSumCredit(String account) {
		return sumCreditDao.getSumCredit(account);
	}

	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: getCreditsNum
	 * @Description: TODO
	 * @return int
	 */
	public int getCreditsNum(CreditsBean credit) {
		return creditsDao.getCreditsNum(credit);
	}

	public List<CreditsBean> getCredits(CreditsBean creditBean) {
		List<CreditsBean> creditList = creditsDao.getCredits(creditBean);
		if (creditList != null) {
			for (CreditsBean credit : creditList) {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				credit.setEnterTimeStr(format.format(credit.getEnterTime()));
			}
		}
		return creditList;
	}

	/**
	 * 
	 * @日期：2015年7月10日
	 * @Title: judge
	 * @Description: TODO 判断是否为新用户添加或者修改总积分
	 * @param credit
	 */
	public void judge(CreditsBean credit) {
		int result = this.getSumCredit(credit.getAccount());
		if (result == -1) {
			SumCreditBean sumCredit = new SumCreditBean(credit.getAccount(), 0);
			this.addSumCredit(sumCredit);
			if (credit.getCredits() > 0) {
				sumCredit.setSumCredit(credit.getCredits());
				this.setSumCredit(sumCredit);
			}
		} else {
			SumCreditBean sumCredit = new SumCreditBean(credit.getAccount(),
					result + credit.getCredits());
			this.setSumCredit(sumCredit);
		}
		this.addCredits(credit);
	}

}
