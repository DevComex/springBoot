/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：WdPopularActivity
 * @作者：范佳琪
 * @联系方式：fanjiaqig@gyyx.cn
 * @创建时间： 2016年03月14日
 * @版本号：
 * @本类主要用途描述：投票信息数据控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.wdpopularactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/popularactivity")
public class VoteController {
	
	private int actionCode = 344;
	private String actionName = "问道官网人气活动";
	
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(VoteController.class);
	
	@RequestMapping("/index")
	public String index(Model model) {
		return "wdVoteActivity/index";
	}
	
	@RequestMapping(value = "/queryActivityTime")
	@ResponseBody
	public ResultBean<String> queryActivityTime(){
	    return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}

	/**
	 * @Title addVoteInfo 
	 * @Description 投票 
	 * @author fanjiaqi
	 * @param request activityName
	 * @return
	 */
	@RequestMapping(value = "/addVoteInfo")
	@ResponseBody
	public ResultBean<Integer> addVoteInfo(HttpServletRequest request,String activityName){
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}
	
	/**
	 * @Title getAllVoteResult
	 * @Description 获取全部活动的投票结果
	 * @author fanjiaqi
	 * @return
	 */
	@RequestMapping(value = "/getAllVoteResult")
	@ResponseBody
	public ResultBean<List<Map<String,Object>>> getAllVoteResult(){
		ResultBean<List<Map<String,Object>>> result = new ResultBean<>();
		//活动下线+假数据
		List<Map<String,Object>> list=new ArrayList<>();
		
		Map<String,Object> map =new HashMap<>();
		map.put("activityName", "萝卜桃子");
		map.put("code", 1001);
		map.put("description", "玩家需要变身成猴子或是兔子才能参加活动,变身成猴子，可以在桃柳林获得桃子；如果变身成兔子，可以在官道北获得萝卜。");
		map.put("url", "http://static.cn114.cn/action/wdVoteActivity/img/萝卜桃子.jpg");
		map.put("voteNum", 35500);
		list.add(map); 
		
		map =new HashMap<>();
                map.put("activityName", "仙界宠物大逃亡");
                map.put("code", 1007);
                map.put("description", "众兽将天上女娲补天之处挖了一个大洞。神兽们带着自己的子孙从洞中都逃了出来。玉帝派遣了所有天兵天将去捉拿这些神兽，目前神兽们被天兵天将们围堵到蝴蝶谷。玉帝邀请各位豪侠帮忙捕捉这些神兽。");
                map.put("url", "http://static.cn114.cn/action/wdVoteActivity/img/仙界宠物大逃亡.jpg");
                map.put("voteNum", 33641);
                list.add(map); 
                
                map =new HashMap<>();
                map.put("activityName", "夜游百鬼地");
                map.put("code", 1008);
                map.put("description", "百鬼地内阴阳门封印不稳，新一轮的百鬼地动荡又开始了，天心道人正在百鬼地内守卫人间界入口，望各位道友速速前往支援！");
                map.put("url", "http://static.cn114.cn/action/wdVoteActivity/img/新夜游百鬼地.jpg");
                map.put("voteNum", 32608);
                list.add(map); 
                
                map =new HashMap<>();
                map.put("activityName", "七星拱月");
                map.put("code", 1002);
                map.put("description", "北斗七星君乃七位星神。道友每天最多只能挑战三位星君，依次挑战完七位星君后即可挑战太阴星君，每次成功挑战都能得到丰厚的奖励，战胜太阴星君后有更多的机会得到大奖。");
                map.put("url", "http://static.cn114.cn/action/wdVoteActivity/img/七星拱月.jpg");
                map.put("voteNum", 28764);
                list.add(map); 
                
                map =new HashMap<>();
                map.put("activityName", "撕名牌大战");
                map.put("code", 1004);
                map.put("description", "玩家进入场地被分为两个阵营进行PK，将敌人击倒后会刷新出铭牌元魄，捕捉它可以获得多倍奖励和道具奖励。");
                map.put("url", "http://static.cn114.cn/action/wdVoteActivity/img/撕名牌大战.jpg");
                map.put("voteNum", 21695);
                list.add(map); 
                
                map =new HashMap<>();
                map.put("activityName", "大兴土木");
                map.put("code", 1000);
                map.put("description", "以打怪获得任务物品来获得全服双倍时间以及怪物攻城活动的开启");
                map.put("url", "http://static.cn114.cn/action/wdVoteActivity/img/大兴土木.jpg");
                map.put("voteNum", 8229);
                list.add(map); 
                
                map =new HashMap<>();
                map.put("activityName", "娃娃促销");
                map.put("code", 1005);
                map.put("description", "提交道具套餐卡获得娘娘赐福，令娃娃在接下来的30天内进入战斗和使用娃娃技能不消耗饱食度和心情度。");
                map.put("url", "http://static.cn114.cn/action/wdVoteActivity/img/娃娃促销.jpg");
                map.put("voteNum", 5766);
                list.add(map); 
                
                map =new HashMap<>();
                map.put("activityName", "争分夺秒");
                map.put("code", 1009);
                map.put("description", "上次比试中兔大哥睡了个懒觉、小肥猪吃东西太多撑得跑不动、小牛牛顺路去打了个酱油，结果小龟获得了最后的胜利。现在大家要求重赛，看谁能最先跑到天墉城拍卖师旁的门楼处，你也来猜猜比赛结果吧，猜对了有奖哦。");
                map.put("url", "http://static.cn114.cn/action/wdVoteActivity/img/争分夺秒.jpg");
                map.put("voteNum", 3280);
                list.add(map); 
                
                map =new HashMap<>();
                map.put("activityName", "群魔乱舞");
                map.put("code", 1003);
                map.put("description", "玩家可通过天墉城NPC神算子传送至魔舞镇中，挑战地图内的心魔。");
                map.put("url", "http://static.cn114.cn/action/wdVoteActivity/img/群魔乱舞.jpg");
                map.put("voteNum", 1841);
                list.add(map); 
                
                map =new HashMap<>();
                map.put("activityName", "五行争霸");
                map.put("code", 1006);
                map.put("description", "九州之内，哪一门派才是真正的强者，今日五行争霸之门打开，各位道友可在活动指定线路找百晓通进入场内为各自门派一展身手。");
                map.put("url", "http://static.cn114.cn/action/wdVoteActivity/img/五行争霸.jpg");
                map.put("voteNum", 1480);
                
		list.add(map); 
		result.setProperties(true, "获取活动列表成功", list);
		return result ;
		        
	}
}