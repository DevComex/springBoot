/**------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：humingyuan
 * 联系方式：humingyuan@gyyx.cn 
 * 创建时间： 2016/12/21
 * 版本号：v1.0 
 * 本类主要用途描述：问道百万网红投票活动后台控制器
-------------------------------------------------------------------------*/

package cn.gyyx.action.oa.whback;

import java.util.List;

import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 问道百万网红投票活动后台控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/whBack")
public class WhVoteBackController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WhVoteBackController.class);

	/**
	 * @Title getVoteListByWhCode
	 * @Description 获取百万活动投票统计
	 * @author humingyuan
	 * @return
	 */
	@RequestMapping(value = "/getVoteList")
	public String getVoteListByWhCode(Model model){
		JSONArray array = JSONArray.fromObject("[{\"code\":1000,\"whCode\":\"NO.001\",\"whName\":\"诅咒\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1001,\"whCode\":\"NO.002\",\"whName\":\"冷燕\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1002,\"whCode\":\"NO.003\",\"whName\":\"云飞\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1003,\"whCode\":\"NO.004\",\"whName\":\"明镜\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1004,\"whCode\":\"NO.005\",\"whName\":\"深海\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1005,\"whCode\":\"NO.006\",\"whName\":\"白兔\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1006,\"whCode\":\"NO.007\",\"whName\":\"王者\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1007,\"whCode\":\"NO.008\",\"whName\":\"黑羽\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1008,\"whCode\":\"NO.009\",\"whName\":\"李兰\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1009,\"whCode\":\"NO.010\",\"whName\":\"贾贾贾政井\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1010,\"whCode\":\"NO.011\",\"whName\":\"土豆\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1011,\"whCode\":\"NO.012\",\"whName\":\"兮兮\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1012,\"whCode\":\"NO.013\",\"whName\":\"赵小蕾\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1013,\"whCode\":\"NO.014\",\"whName\":\"张阳阳\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1014,\"whCode\":\"NO.015\",\"whName\":\"谱哥哥\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1015,\"whCode\":\"NO.016\",\"whName\":\"琬辰\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1016,\"whCode\":\"NO.017\",\"whName\":\"陈琦\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1017,\"whCode\":\"NO.018\",\"whName\":\"懒爷\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1018,\"whCode\":\"NO.019\",\"whName\":\"李晨明月\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1019,\"whCode\":\"NO.020\",\"whName\":\"怡妞子\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1020,\"whCode\":\"NO.021\",\"whName\":\"小月亮\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1021,\"whCode\":\"NO.022\",\"whName\":\"白洛琳宝宝\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1022,\"whCode\":\"NO.023\",\"whName\":\"世界第一小妹\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1023,\"whCode\":\"NO.024\",\"whName\":\"回忆的旧时光\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1024,\"whCode\":\"NO.025\",\"whName\":\"Eight丶捌\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1025,\"whCode\":\"NO.026\",\"whName\":\"情久の雷雨晴\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1026,\"whCode\":\"NO.027\",\"whName\":\"菲儿丶\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1027,\"whCode\":\"NO.028\",\"whName\":\"海宝的玫瑰\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1028,\"whCode\":\"NO.029\",\"whName\":\"爱琳sama\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1029,\"whCode\":\"NO.030\",\"whName\":\"萌哒哒biu萌萌\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1030,\"whCode\":\"NO.031\",\"whName\":\"洛宝宝要努力做个萌妹子\",\"realVoteNum\":1,\"modifyVoteNum\":16},{\"code\":1031,\"whCode\":\"NO.032\",\"whName\":\"尐魔舞\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1032,\"whCode\":\"NO.033\",\"whName\":\"可爱小乖乖\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1033,\"whCode\":\"NO.034\",\"whName\":\"千千小寻\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1034,\"whCode\":\"NO.035\",\"whName\":\"梦幻环环\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1035,\"whCode\":\"NO.036\",\"whName\":\"大妞er\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1036,\"whCode\":\"NO.037\",\"whName\":\"皇族丶小野喵\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1037,\"whCode\":\"NO.038\",\"whName\":\"皇族沫颜\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1038,\"whCode\":\"NO.039\",\"whName\":\"沐小呆丶\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1039,\"whCode\":\"NO.040\",\"whName\":\"皇族风儿\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1040,\"whCode\":\"NO.041\",\"whName\":\"皇家丶花花\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1041,\"whCode\":\"NO.042\",\"whName\":\"半夏\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1042,\"whCode\":\"NO.043\",\"whName\":\"陈小艾\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1043,\"whCode\":\"NO.044\",\"whName\":\"南曦\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1044,\"whCode\":\"NO.045\",\"whName\":\"喜宝\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1045,\"whCode\":\"NO.046\",\"whName\":\"花花呀花花呀\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1046,\"whCode\":\"NO.047\",\"whName\":\"babie\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1047,\"whCode\":\"NO.048\",\"whName\":\"郁雅洁\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1048,\"whCode\":\"NO.049\",\"whName\":\"sea\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1049,\"whCode\":\"NO.050\",\"whName\":\"薯条姐姐\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1050,\"whCode\":\"NO.051\",\"whName\":\"苏以寒\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1051,\"whCode\":\"NO.052\",\"whName\":\"赵友蕊\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1052,\"whCode\":\"NO.053\",\"whName\":\"刘迎曼\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1053,\"whCode\":\"NO.054\",\"whName\":\"谢小凡\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1054,\"whCode\":\"NO.055\",\"whName\":\"赵思菱\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1055,\"whCode\":\"NO.056\",\"whName\":\"赵海\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1056,\"whCode\":\"NO.057\",\"whName\":\"孙初彤\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1057,\"whCode\":\"NO.058\",\"whName\":\"李水瑶\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1058,\"whCode\":\"NO.059\",\"whName\":\"方易真\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1059,\"whCode\":\"NO.060\",\"whName\":\"孙海瑶\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1060,\"whCode\":\"NO.061\",\"whName\":\"朱安露\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1061,\"whCode\":\"NO.062\",\"whName\":\"秦静枫\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1062,\"whCode\":\"NO.063\",\"whName\":\"周曼寒\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1063,\"whCode\":\"NO.064\",\"whName\":\"欧阳碧琴\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1064,\"whCode\":\"NO.065\",\"whName\":\"王靖菲\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1065,\"whCode\":\"NO.066\",\"whName\":\"陈晓诺\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1066,\"whCode\":\"NO.067\",\"whName\":\"许雨言\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1067,\"whCode\":\"NO.068\",\"whName\":\"唐婉\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1068,\"whCode\":\"NO.069\",\"whName\":\"曾晨\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1069,\"whCode\":\"NO.070\",\"whName\":\"佟佳月\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1070,\"whCode\":\"NO.071\",\"whName\":\"王宇萌\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1071,\"whCode\":\"NO.072\",\"whName\":\"程勇\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1072,\"whCode\":\"NO.073\",\"whName\":\"王琦\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1073,\"whCode\":\"NO.074\",\"whName\":\"陈明\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1074,\"whCode\":\"NO.075\",\"whName\":\"许晴\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1075,\"whCode\":\"NO.076\",\"whName\":\"方元\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1076,\"whCode\":\"NO.077\",\"whName\":\"董紫安\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1077,\"whCode\":\"NO.078\",\"whName\":\"李佳\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1078,\"whCode\":\"NO.079\",\"whName\":\"刘悦\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1079,\"whCode\":\"NO.080\",\"whName\":\"何何\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1080,\"whCode\":\"NO.081\",\"whName\":\"张蔚婷\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1081,\"whCode\":\"NO.082\",\"whName\":\"于凌薇\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1082,\"whCode\":\"NO.083\",\"whName\":\"肖芸熙\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1083,\"whCode\":\"NO.084\",\"whName\":\"韩伯康\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1084,\"whCode\":\"NO.085\",\"whName\":\"汪玲\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1085,\"whCode\":\"NO.086\",\"whName\":\"赵茜茜\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1086,\"whCode\":\"NO.087\",\"whName\":\"徐叶\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1087,\"whCode\":\"NO.088\",\"whName\":\"徐雨嘉\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1088,\"whCode\":\"NO.089\",\"whName\":\"马金星\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1089,\"whCode\":\"NO.090\",\"whName\":\"张铭璐\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1090,\"whCode\":\"NO.091\",\"whName\":\"王骅杨\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1091,\"whCode\":\"NO.092\",\"whName\":\"陈熙雯\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1092,\"whCode\":\"NO.093\",\"whName\":\"乔婕珍\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1093,\"whCode\":\"NO.094\",\"whName\":\"艾佳琦\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1094,\"whCode\":\"NO.095\",\"whName\":\"成雨婷\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1095,\"whCode\":\"NO.096\",\"whName\":\"向若蕊\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1096,\"whCode\":\"NO.097\",\"whName\":\"马从露\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1097,\"whCode\":\"NO.098\",\"whName\":\"武芷文\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1098,\"whCode\":\"NO.099\",\"whName\":\"侍雅彤\",\"realVoteNum\":0,\"modifyVoteNum\":0},{\"code\":1099,\"whCode\":\"NO.100\",\"whName\":\"白巧曼\",\"realVoteNum\":0,\"modifyVoteNum\":0}]");
		model.addAttribute("voteCountBeans", array.toArray());
		return "wdwhback/whvote";
	}
	
	/**
	 * 后台更新投票数
	 * @param model
	 * @param code 编号
	 * @param modifyVoteNum 输入的修改投票数
	 * @author humingyuan
	 * @return
	 */
	@RequestMapping(value="/updateVoteNum")
	@ResponseBody
	public ResultBean<String> updateVoteNum(Model model,
				@RequestParam("code") int code,
				@RequestParam("modifyVoteNum") int modifyVoteNum){
		logger.info("问道百万网红投票活动后台-WhVoteBackController-updateVoteNum,参数code:" 
				+ code + ",票数modifyVoteNum:" + modifyVoteNum);
		return new ResultBean<String>(true, "票数修改成功", "票数修改成功");
	}
}
