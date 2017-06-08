/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：李泽溥
 * 联系方式：lizepu@gyyx.cn 
 * 创建时间：2014年12月15日13:39:00
 * 版本号：v1.0 
 * 本类主要用途描述：对活动相关数值进行验证
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.bll.wdno1pet;

import org.slf4j.Logger;

import cn.gyyx.action.beans.wd9year.WishBean;
import cn.gyyx.action.beans.wdno1pet.CommentBean;
import cn.gyyx.action.beans.wdno1pet.ImageBean;
import cn.gyyx.action.beans.wdno1pet.WDNo1PetInfoBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.Text;

/**
 * @ClassName: Validation
 * @Description: TODO 对活动相关数值进行验证
 * @author lizepu
 * @date 2014年12月15日13:39:00
 * 
 */
public class Validation {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(Validation.class);
	public static final String BEAN_CHECK_SUCCESS = "SUCCESS!";
	private static final String NUM_REGX = "^[0-9]\\d*$";

	/**
	 * 验证所给字符串是否为非负整数 本次项目中用来检测输入非负整数的部分
	 * 
	 * @param str
	 *            输入的字符串
	 * @return 对应结果的boolean值
	 */
	public static boolean isNonNegativeInteger(String str) {
		logger.debug("str",str);
		if (str != null) {
			
			return str.matches(NUM_REGX);
		}
	
		return false;
	}

	/**
	 * 宠物作品名称，小于16字(第一次需求变更)
	 * 
	 * @param str
	 *            表示作品的名称
	 * @return 对应结果的boolean值
	 */
	public static boolean isPetWorksName(String str) {
		logger.debug("str",str);
		if (str != null) {
			return str.length() <= 16 && str.length() > 0;
		}
		return false;
	}

	/**
	 * 游戏大区名称，小于50字(数据库字段限制)
	 * 
	 * @param str
	 *            表示大区的名称
	 * @return 对应结果的boolean值
	 */
	public static boolean isChannel(String str) {
		logger.debug("str",str);
		if (str != null) {
			return str.length() <= 50;
		}
		return false;
	}

	/**
	 * 角色名称，小于8字(第一次需求变化)
	 * 
	 * @param str
	 *            表示角色的名称
	 * @return 对应结果的boolean值
	 */
	public static boolean isCharacter(String str) {
		if (str != null) {
			return str.length() <= 8 && str.length() > 0;
		}
		return false;
	}

	/**
	 * 宠物介绍，200字以内(数据库字段限制)
	 * 
	 * @param str
	 *            表示昵称的字符串
	 * @return 对应结果的boolean值
	 */
	public static boolean isPetInfo(String str) {
		if (str != null) {
			return str.length() <= 200 && str.length() > 0;
		}
		return false;
	}

	/**
	 * 评论昵称，8字以内(需求限制)
	 * 
	 * @param str
	 *            表示昵称的字符串
	 * @return 对应结果的boolean值
	 */
	public static boolean isNickName(String str) {
		if (str != null) {
			return str.length() <= 8 && str.length() > 0;
		}
		return false;
	}

	/**
	 * 评论内容，2~50字以内(需求限制)
	 * 
	 * @param str
	 *            表示昵称的字符串
	 * @return 对应结果的boolean值
	 */
	public static boolean isComment(String str) {
		if (str != null) {
			return str.length() >= 2 && str.length() <= 20;
		}
		return false;
	}

	/**
	 * 对代表着用户上传作品实体WDNo1PetInfo进行检测
	 * 
	 * @param petInfo
	 *            用户上传的参赛作品实体
	 * @return 代表检测结果的字符串，通过时返回静态引用：BEAN_CHECK_SUCCESS
	 */
	public static String checkBean(WDNo1PetInfoBean petInfo) {
		if (!isChannel(petInfo.getChannel())) {
			return "非法的游戏大区";
		}
		if (!isCharacter(petInfo.getCharacterName())) {
			return "非法的角色名";
		}
		if (!isPetWorksName(petInfo.getPetName())) {
			return "非法的作品名称";
		}
		if (!isPetInfo(petInfo.getPetInfo())) {
			return "非法的宠物介绍";
		}
		if (!isPetType(petInfo.getPetType())) {
			return "非法的宠物类别";
		}
		return BEAN_CHECK_SUCCESS;
	}

	/**
	 * 对代表着用户上传作品实体WishBean进行检测
	 * 
	 * @param WishBean
	 *            用户上传的祝福实体
	 * @return 代表检测结果的字符串，通过时返回静态引用：BEAN_CHECK_SUCCESS
	 */
	public static String checkBean(WishBean wishBean) {
		if (Text.isNullOrEmpty(wishBean.getContent())) {
			return "-2";
		} else if (wishBean.getContent().length() > 10) {
			return "-3";
		}
		if (Text.isNullOrEmpty(wishBean.getPictureURL())) {
			return "-4";
		}
		return BEAN_CHECK_SUCCESS;
	}

	/**
	 * 图片审核状态检测
	 * 
	 * @param str
	 *            审核状态
	 * @return 代表审核状态的布尔值
	 */
	public static boolean isImgStatus(String str) {
		return "uncheck".equals(str);
	}

	/**
	 * 图片类别检测，本次的类别固定为“wd1”
	 * 
	 * @param str
	 *            代表类别的字符串
	 * @return 对应结果的布尔值
	 */
	public static boolean isImgType(String str) {
		return "wd1".equals(str);
	}

	/**
	 * 图片临时Url路径监测，数据库字段限制为200
	 * 
	 * @param str
	 *            代表路径的字符串
	 * @return 对应结果的布尔值
	 */
	public static boolean isImgTempUrl(String str) {
		if (str != null) {
			return str.length() <= 200;
		}
		return false;
	}

	/**
	 * 图片特征码检测，受数据库字段限制为50
	 * 
	 * @param str
	 *            代表图片特征码的字符串
	 * @return 对应结果的布尔值
	 */
	public static boolean isImgFeature(String str) {
		if (str != null) {
			return str.length() <= 50;
		}
		return false;
	}

	/**
	 * 对代表着用户上传图片实体ImageBean进行检测
	 * 
	 * @param img
	 *            用户上传的图片实体
	 * @return 代表检测结果的字符串，通过时返回静态引用：BEAN_CHECK_SUCCESS
	 */
	public static String checkBean(ImageBean img) {
		if (!isImgStatus(img.getImgStatus())) {
			return "非法的审核状态";
		}
		if (!isImgType(img.getImgType())) {
			return "非法的图片类别";
		}
		if (!isImgFeature(img.getImgFeature())) {
			return "非法的图片特征码";
		}
		if (!isImgTempUrl(img.getTempUrl())) {
			return "非法临时路径";
		}
		return BEAN_CHECK_SUCCESS;
	}

	public static String checkBean(CommentBean comment) {
		if (!isNickName(comment.getNickName())) {
			return "非法的昵称";
		}
		if (!isComment(comment.getCommentContent())) {
			return "非法的评论内容";
		}
		return BEAN_CHECK_SUCCESS;
	}

	private static String[] types = { "0", "1", "2", "3", "4" };

	/**
	 * 判断是不是宠物类型 是：返回 true；否返回 false。
	 * 
	 * @param petType
	 *            宠物类型:普通，变异，神兽，元灵，坐骑
	 * @return
	 */
	public static Boolean isPetType(String petType) {
		for (String string : types) {
			if (string.equals(petType)) {
				return true;
			}
		}
		return false;
	}

	private static String[] qualityNames = { "pet_growth", "pet_blood",
			"pet_speed", "pet_magic", "pet_attack" };

	/**
	 * 判断是不是宠物属性名称 是：返回 true；否返回 false。
	 * 
	 * @param petType
	 *            属性名称：pet_growth,pet_blood,pet_speed,pet_magic,pet_attack
	 * @return
	 */
	public static Boolean isPetQualityName(String qualityName) {
		for (String string : qualityNames) {
			if (string.equals(qualityName)) {
				return true;
			}
		}
		return false;
	}
}
