/*------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
* 作者：Chen 
* 联系方式：chenpeng03@gyyx.cn 
* 创建时间： 2014年12月17日 下午4:57:25
* 版本号：v1.0 
* 本类主要用途描述： 
*  
-------------------------------------------------------------------------*/
package cn.gyyx.action.beans.wdno1pet;


public enum PetType {
	
	/**
	 * 普通宠物
	 */
	Puchong(0),
	/**
	 * 元灵
	 */
	Yuanling(1),
	/**
	 * 神兽
	 */
	Shenshou(2),
	/**
	 * 变异
	 */
	Bianyi(3),
	/**
	 * 坐骑
	 */
	Zuoqi(4);
	private int code;
	private PetType(int code){
		this.code = code;
	}
	@Override
	public String toString(){
		return code+"";
	}
	
	/**
	 * 验证是否存在传入类型
	 * @param type
	 * @return
	 */
	public static boolean isType(String type) {
		boolean flag = false;
		for (PetType p : PetType.values()) {
			if (type.equals(p.name())) {
				flag = true;
			}
		}
		return flag;
	}
	public static String getCodeByPetType(String petType){
		return PetType.valueOf(petType).toString();
	}
}
