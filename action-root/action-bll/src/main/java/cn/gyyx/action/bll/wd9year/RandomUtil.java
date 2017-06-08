/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：马文斌
 * 联系方式：mawenbin@gyyx.cn 
 * 创建时间：2015年3月13日下午5:52
 * 版本号：v1.0
 * 本类主要用途叙述：随机算法
 */
package cn.gyyx.action.bll.wd9year;

import java.util.Random;
/**
 * 随机算法
 * */
public class RandomUtil {
	private int[] codeArray;
	private final int CANUSE = 1;
	private final int UNUSE = -1;
	
	public int[] getCodeArray() {
		return codeArray;
	}
	public void setCodeArray(int[] codeArray) {
		this.codeArray = codeArray;
	}
	public RandomUtil(int numCount){
		codeArray = new int[numCount];
		for(int i = 0;i < codeArray.length;i ++){
			codeArray[i] = CANUSE;
		}
	}
	/**
	 * 给一个上限值随机产生该范围内的整数
	 * */
	public int randomCode(int upLimit){
		Random rand =new Random();
		int result = rand.nextInt(upLimit);
		//循环找出不重复的数字
		while(codeArray[result] == UNUSE){
			result ++;
		}
		return result;
	}
}
