/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月17日下午1:26:52
 * 版本号：v1.0
 * 本类主要用途叙述：中奖信息信息配置的业务层
 */

package cn.gyyx.action.bll.wdno1pet;

import cn.gyyx.action.beans.wdno1pet.LotteryConfigBean;
import cn.gyyx.action.dao.wdno1pet.ILotter;
import cn.gyyx.action.dao.wdno1pet.LotterDAO;

public class LotteryConfigBLL {
	/**
	 * 插入中奖配置信息
	 * 
	 * @param lConfigBean
	 */
	public void setLotteryConfig(LotteryConfigBean lConfigBean) {
		ILotter iLotter = new LotterDAO();
		iLotter.insertLotter(lConfigBean);
	}

	/**
	 * 配置获奖规则
	 * 
	 * @return 顺序和奖品编号
	 */
	public String[] RuleLotter(int num) {
		/**
		 * 奖品的数量
		 */
		int npillow = 2;// IPhone的数量
		int ngiftPackage = 2;// IpadAir的数量
		int nMi = 6; // 小米的数量
		int nHaier = 8; // 海尔音响的数量
		int nPacket = 10; // 游戏钱包的数量
		int ngold = 10; // 金手链的数量
		int nMouse = 20;// 极致鼠标的数量
		int ngoldIngot = 600;// 元宝的数量
		// 奖品的中奖间隔
		int iPadair = num / ngiftPackage;// IPad的间隔
		int IPhone5s = num / npillow;// Iphone的间隔
		int Mi = num / nMi;// 小米的间隔
		int haiEr = num / nHaier; // 海尔音响的间隔
		int packet = num / nPacket;// 游戏钱包的数量
		int gold = num / ngold;// 金手链的间隔
		int mouse = num / nMouse; // 鼠标的平均分配
		int goldIngot;
		if(num<1200){
		 goldIngot = num / ngoldIngot+1;
		}
		else{
			goldIngot = num / ngoldIngot;
		}// 金元宝的平均分配
		int count=0;
		/**
		 * 分配奖品的最小底线
		 */
		if (num <= 1000) {
			num = 1000;
		}//

		int top = 1;// 中奖的开始位置
		String[] rule = new String[9999999];
		// 中IPhne的位置
		rule[top] = "pillow";
		rule[top + IPhone5s] = "pillow";
		top = top + 1;
		// 中iPad air 的位置

		rule[top] = "giftPackage";
		rule[top + iPadair] = "giftPackage";
		top = top + 1;
		// 中小米的位置

		for (int i = top; i < num; i = i + Mi) {
			// 如果当前位置没有奖品
			if (rule[i] == null) {
				rule[i] = "Mi";
			}
			// 当当前位置应经有奖
			else {
				while (rule[i] != null) {
					i = i + 1;
				}
				rule[i] = "Mi";
			}

		}
		top = top + 1;
		/**
		 * 海尔音响中奖的配置
		 */
		for (int i = top; i < num; i = i + haiEr) {
			// 如果当前位置没有奖品
			if (rule[i] == null) {
				rule[i] = "Haier";
			}
			// 当当前位置应经有奖
			else {
				while (rule[i] != null) {
					i = i + 1;
				}
				rule[i] = "Haier";
			}

		}
		top = top + 1;
		/**
		 * 周边钱包中奖位置
		 */
		for (int i = top; i < num; i = i + packet) {
			// 如果当前位置没有奖品
			if (rule[i] == null) {
				rule[i] = "purse";
			}
			// 当当前位置应经有奖
			else {
				while (rule[i] != null) {
					i = i + 1;
				}
				rule[i] = "purse";
			}

		}
		top = top + 1;
		/**
		 * 金手链中奖位置
		 */
		for (int i = top; i < num; i = i + gold) {
			// 如果当前位置没有奖品
			if (rule[i] == null) {
				rule[i] = "bracelet";
			}
			// 当当前位置应经有奖
			else {
				while (rule[i] != null) {
					i = i + 1;
				}
				rule[i] = "bracelet";
			}

		}
		top = top + 1;
		/**
		 * 金手链中奖位置
		 */
		for (int i = top; i < num; i = i + mouse) {
			// 如果当前位置没有奖品
			if (rule[i] == null) {
				rule[i] = "mouse";
			}
			// 当当前位置应经有奖
			else {
				while (rule[i] != null) {
					i = i + 1;
				}
				rule[i] = "mouse";
			}

		}
		top = top + 1;
		/**
		 * 元宝的中奖位置
		 */
		for (int i = top; i < num; i = i + goldIngot) {
			// 如果当前位置没有奖品
			if (rule[i] == null) {
				rule[i] = "10000gold";
				count=count+1;
			}
			// 当当前位置应经有奖
			else {
				while (rule[i] != null) {
					i = i + 1;
				}
				rule[i] = "10000gold";
				count=count+1;
			}

		}
		/**
		 * 谢谢惠顾
		 */
		for (int i = 1; i <= num; i++) {
			if (rule[i] == null) {
			if(count<600){
				rule[i]="10000gold";
				count=count+1;
			}
			else{
			
				break;
			}
			}
		}

		return rule;

	}
	/**
	 * 判断配置表的记录是否是空
	 * @return boolean 
	 */
	public boolean isEmpty(){
		boolean is=false;
		ILotter iLotter=new LotterDAO();
		int n=iLotter.selectConutLottery();
		if(n==0){
			is=true;
		}
		return is;
	}
	/**
	 * 清空配置的值
	 */
	public void cleanLottery(){
		ILotter iLotter=new LotterDAO();
		iLotter.deleteLotteryConfig();
	}
	/**
	 * 给句名次查找到当前名次的奖品
	 * @param order
	 * @return lotteryConfigBean 该名次的中奖配置信息
	 */
	public LotteryConfigBean getLotteryConfigBeanByOrder(int order){
		ILotter iLotter=new LotterDAO();
		LotteryConfigBean lotteryConfigBean=iLotter.selectLotteryConfigByOrder(order);
		return lotteryConfigBean;
		
	}
	

}
