package cn.gyyx.action.dao.supporter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.gyyx.action.common.beans.InnerTransmissionData;
import cn.gyyx.action.common.chain.TransmissionChainNode;
import cn.gyyx.action.common.chain.TransmissionChainNode.DataArea;
import cn.gyyx.action.common.chain.app.ContinuousDateChainNode;
import cn.gyyx.action.common.chain.app.UserLoginCheckChainNode;
import cn.gyyx.action.common.chain.transimpl.ConditionChainNode;
import cn.gyyx.action.common.chain.transimpl.CrossChainNode;
import cn.gyyx.action.common.chain.transimpl.CrossChainNode.CrossChainConfig;
import cn.gyyx.action.common.chain.transimpl.DataComparatorChainNode;
import cn.gyyx.action.common.chain.transimpl.DataComparatorChainNode.Validate;
import cn.gyyx.action.common.chain.transimpl.DataMoverChainNode;
import cn.gyyx.action.common.chain.transimpl.DataSetterChainNode;
import cn.gyyx.action.common.chain.transimpl.DateComparatorChainNode;
import cn.gyyx.action.common.chain.transimpl.GoalMethodChainNode;
import cn.gyyx.action.common.chain.transimpl.JSONDecompressionChainNode;
import cn.gyyx.action.common.chain.transimpl.NullCheckChainNode;
import cn.gyyx.action.common.dictionary.DBColum;
import cn.gyyx.action.common.dictionary.DBTable;
import cn.gyyx.action.common.dictionary.Dictionary;
import cn.gyyx.action.common.dictionary.Dictionary.FormType;
import cn.gyyx.action.common.dictionary.DictionaryContainer;
import cn.gyyx.action.common.dictionary.GoalMethod;
import cn.gyyx.action.common.dictionary.QueryType;
import cn.gyyx.action.common.dictionary.QueryType.QueryStg;
import cn.gyyx.action.common.dictionary.UpdateType;
import cn.gyyx.action.common.dictionary.UpdateType.UpdateOp;
import cn.gyyx.action.common.dictionary.UploadType;
import cn.gyyx.action.common.manager.ActionManager;
import cn.gyyx.action.common.manager.ManagerContainer;

/** 
* 类:PreJPADemo<br />
* 叙述:序列化/反序列化功能完成前 暂时此地编码<br />
* 注释日期:2015年11月3日<br />
* @author lizepu
*/
public class PreJPADemo {
	
	public static void lhzsInvestigate(){
		try{
			Dictionary dic = new Dictionary(284);
			ActionManager manager = new ActionManager(284);
			DictionaryContainer.addDictionary(dic);
			ManagerContainer.addManager(manager);
			
			GoalMethod uploadForm = new GoalMethod("cn.gyyx.action.dao.supporter.CommonSupportDAO", "uploadForm", InnerTransmissionData.class);
			TransmissionChainNode bugUpload = new GoalMethodChainNode(uploadForm);
			manager.baseChain("lhzsInvestigate", bugUpload);
			
			UploadType uType = new UploadType("lhzsInvestigate",FormType.MAIN_FORM);
			uType.mappingName("sex","sex","性别");
			uType.mappingName("birthday","birthday","出生年月日");
			uType.mappingName("played","played","是否玩过灵魂战神");
			uType.mappingName("province","province","省份");
			uType.mappingName("city","city","市");
			uType.mappingName("channel","channel","通过什么渠道了解游戏");
			uType.mappingName("quality","quality","看中游戏哪方面品质");
			uType.mappingName("playTime","playTime","每天游戏时间");
			uType.mappingName("overRes","overRes","游戏流失原因");
			uType.mappingName("gameName","gameName","最近三个月玩过的游戏");
			uType.mappingName("userName",DBColum.S1,"玩家姓名");
			uType.mappingName("userPhone",DBColum.S2,"手机号");
			uType.mappingName("userEmail",DBColum.S3,"邮箱");
			uType.mappingName("userQQ",DBColum.S4,"邮箱");
			uType.enableUpload();
			dic.addUploadType(uType);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public static void lhzs() {
		try{
			//定义字典及管理器
			Dictionary dic = new Dictionary(282);
			ActionManager manager = new ActionManager(282);
			DictionaryContainer.addDictionary(dic);
			ManagerContainer.addManager(manager);
			
			GoalMethod uploadForm = new GoalMethod("cn.gyyx.action.dao.supporter.CommonSupportDAO", "uploadForm", InnerTransmissionData.class);
			GoalMethod checkCaptcha = new GoalMethod("cn.gyyx.action.service.CaptchaTransmissionAdapter", "validate", InnerTransmissionData.class);
			TransmissionChainNode bugUpload = new DataMoverChainNode(DataArea.T_COOKIE, DataArea.T_DATA, "GYYX_CHECKCODE_VJ", "cookieKey");
			bugUpload.error("无法读取Cookie");
			bugUpload
			.setNext(new JSONDecompressionChainNode(DataArea.T_DATA,DataArea.T_EXTRA))
			.setNext(new GoalMethodChainNode(checkCaptcha).error("验证方法调用失败"))
			.setNext(new DataComparatorChainNode(Validate.NUM_EQUAL, 0).error("验证码错误"))
			.setNext(new GoalMethodChainNode(uploadForm).error("表单提交失败"));
			manager.baseChain("bug", bugUpload);
			
			UploadType uType = new UploadType("bug",FormType.MAIN_FORM);
			uType.mappingName("titnle",DBColum.S1,"标题");
			uType.mappingName("area","area","区组");
			uType.mappingName("content","content","内容");
			uType.mappingName("captcha","captcha","验证码");
			uType.mappingName("account","account","账号");
			uType.mappingName("role","role","角色");
			uType.enableUpload();
			dic.addUploadType(uType);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void wdAlone() {
		try{
			int actionCode = 283;
			Dictionary dic = new Dictionary(actionCode);
			ActionManager manager = new ActionManager(actionCode);
			DictionaryContainer.addDictionary(dic);
			ManagerContainer.addManager(manager);
			GoalMethod uploadForm = new GoalMethod("cn.gyyx.action.dao.supporter.CommonSupportDAO", "uploadForm", InnerTransmissionData.class);
			GoalMethod queryForm = new GoalMethod("cn.gyyx.action.dao.supporter.CommonSupportDAO", "queryForm", InnerTransmissionData.class);
			GoalMethod queryFormNum = new GoalMethod("cn.gyyx.action.dao.supporter.CommonSupportDAO", "queryFormNum", InnerTransmissionData.class);
			GoalMethod updateForm = new GoalMethod("cn.gyyx.action.dao.supporter.CommonSupportDAO", "updateForm", InnerTransmissionData.class);
			GoalMethod lottery = new GoalMethod("cn.gyyx.action.service.lottery.LotterySupporterAdapter", "lotteryByDataBase", InnerTransmissionData.class);
			//通用操作链条头
			TransmissionChainNode commonQueryNumChain = new UserLoginCheckChainNode();
			commonQueryNumChain.setNext(new GoalMethodChainNode(queryFormNum).error("数据库数量查询错误"));
			TransmissionChainNode commonQueryChain = new UserLoginCheckChainNode();
			commonQueryChain.setNext(new GoalMethodChainNode(queryForm).error("数据库实体查询错误"));
			TransmissionChainNode commonUploadChain = new UserLoginCheckChainNode();
			commonUploadChain.setNext(new GoalMethodChainNode(uploadForm).error("数据库插入错误"));
			TransmissionChainNode commonUpdateChain = new UserLoginCheckChainNode();
			commonUpdateChain.setNext(new GoalMethodChainNode(updateForm).error("数据库更新错误"));
			TransmissionChainNode commonLottery = new UserLoginCheckChainNode();
			commonLottery.setNext(new GoalMethodChainNode(lottery).error("抽奖过程调用失败"));
			//定义数据类型（内部）
			//定义数据类型（外部）
			//用户数据
			UploadType userMain = new UploadType("userMain",FormType.MAIN_FORM);
			userMain.mappingName("isAnswered", DBColum.S1, "no", "是否答题 提交值将被忽略 初始固定为no");
			userMain.mappingName("prize", DBColum.S3, "no", "抽到的奖品中文名称 提交值将被忽略 初始固定为no");
			userMain.mappingName("address", DBColum.S4, "无", "用户地址信息 提交值将被忽略 初始为无，中实物奖品后会进行提交");
			userMain.mappingName("answerSequence", DBColum.S5, "", "答题序列,初始为空串");
			userMain.enableUpload();
			dic.addUploadType(userMain);
			//定义查询类型（内部）
			//获得可抽奖用户信息
			//获得用户的抽奖信息
			QueryType getPrizeUser = new QueryType("getPrizeUser");
			getPrizeUser.and(DBColum.S1,QueryStg.CHAR_EQUAL,"yes");
			getPrizeUser.and(DBColum.S3,QueryStg.CHAR_EQUAL,"no");
			getPrizeUser.limitUser();
			getPrizeUser.setUploadType(userMain);
			//定义查询类型（外部）
			//获得用户信息
			QueryType getUserMain = new QueryType("getUserMain");
			getUserMain.limitUser();
			userMain.addQueryType(getUserMain);
			//定义查询级联
			//定义更新类型（内部）
			//定义更新类型（外部）
			//更新答题状态
			UpdateType updateAnswer = new UpdateType("updateAnswer",DBTable.SEARCH_TB);
			updateAnswer.updateSet(DBColum.S1, UpdateOp.CHAR_EQUAL , "yes");
			updateAnswer.updateSet("answers",DBColum.S5, UpdateOp.CHAR_EQUAL ,"提交的答题序列");
			updateAnswer.limitUser();
			userMain.addUpdateType(updateAnswer);
			//更新奖品
			UpdateType updatePrize = new UpdateType("updatePrize",DBTable.SEARCH_TB);
			updatePrize.limitUser();
			updatePrize.updateSet("prize", DBColum.S3,UpdateOp.CHAR_EQUAL,"奖品 无需填写，将自动进行抽奖并更新");
			userMain.addUpdateType(updatePrize);
			//更新地址
			UpdateType updateAddress = new UpdateType("updateAddress",DBTable.SEARCH_TB);
			updateAddress.limitUser();
			updateAddress.updateSet("address", DBColum.S4,UpdateOp.CHAR_EQUAL,"地址信息 请拼合为一个字符串");
			userMain.addUpdateType(updateAddress);
			//定义操作链表（内部）
			//查询当前用户数量
			CrossChainConfig userMainNumGetter = new CrossChainConfig();
			userMainNumGetter.setCloneQuery(getUserMain);
			userMainNumGetter.setCrossNode(commonQueryNumChain);
			userMainNumGetter.setSaveName("userNum");
			//查询可抽奖用户信息
			CrossChainConfig prizeUserMainGetter = new CrossChainConfig();
			prizeUserMainGetter.setCloneQuery(getPrizeUser);
			prizeUserMainGetter.setCrossNode(commonQueryChain);
			//抽奖
			CrossChainConfig prizeSetter = new CrossChainConfig();
			prizeSetter.setCrossNode(commonLottery);
			prizeSetter.setResultType(DataArea.T_EXTRA);
			prizeSetter.setSaveName(DBColum.JSON_STR);
			//定义操作链表（外部）
			////----基础类型
			//查询当前用户信息
			CrossChainConfig userMainGetter = new CrossChainConfig();
			userMainGetter.setCloneQuery(getUserMain);
			userMainGetter.setCrossNode(commonQueryChain);
			manager.baseChain("getUserMain", commonQueryChain);
			////----Cross类型
			//插入用户信息
			TransmissionChainNode uploadUserMainChain = new UserLoginCheckChainNode();
			uploadUserMainChain
			.setNext(new CrossChainNode(userMainNumGetter).error("查询用户信息失败"))
			.setNext(new DataComparatorChainNode(Validate.NUM_EQUAL,"userNum",0).error("用户已存在"))
			.setNext(new GoalMethodChainNode(uploadForm).error("插入数据失败"));
			manager.baseChain("userMain", uploadUserMainChain);
			//更新答题状态
			TransmissionChainNode updateAnswerChain = new UserLoginCheckChainNode();
			updateAnswerChain
			.setNext(new CrossChainNode(userMainGetter).error("查询用户信息失败"))
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,DBColum.CODE))
			.setNext(new GoalMethodChainNode(updateForm).error("更新数据失败"));
			manager.baseChain("updateAnswer", updateAnswerChain);
			//抽奖
			TransmissionChainNode updatePrizeChain = new UserLoginCheckChainNode();//1检测登陆
			updatePrizeChain
			.setNext(new CrossChainNode(prizeUserMainGetter).error("查询抽奖资格失败"))//2检测是否有资格
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,DBColum.CODE).error("您没有抽奖资格"))
			.setNext(new DataSetterChainNode(DataArea.T_EXTRA,"NO_PRIZE_CHINESE","礼包"))
			.setNext(new DataSetterChainNode(DataArea.T_EXTRA,"NO_PRIZE_ENGLISH","Gift"))
			.setNext(new DataSetterChainNode(DataArea.T_EXTRA,"NO_PRIZE_REAL","card"))
			.setNext(new DataSetterChainNode(DataArea.T_EXTRA,"NO_PRIZE_NUMBER",3))
			.setNext(new CrossChainNode(prizeSetter).error("抽奖过程调用失败"))//3进行抽奖
			.setNext(new JSONDecompressionChainNode(DataArea.T_EXTRA,DataArea.T_EXTRA))
			.setNext(new DataMoverChainNode(DataArea.T_EXTRA,DataArea.T_DATA,"prizeChinese",DBColum.S3))
			.setNext(new GoalMethodChainNode(updateForm).error("记录中奖信息失败"))//4记录得到的奖品
			.setNext(new JSONDecompressionChainNode(DataArea.T_EXTRA,DataArea.T_RESULTBEAN));
			manager.baseChain("updatePrize", updatePrizeChain);
			//更新用户地址
			TransmissionChainNode updateAddressChain = new UserLoginCheckChainNode();
			updateAddressChain
			.setNext(new CrossChainNode(userMainGetter).error("查询用户信息失败"))
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,DBColum.CODE).error("无法确认您的信息"))
			.setNext(new GoalMethodChainNode(updateForm).error("地址更新失败"));
			manager.baseChain("updateAddress", updateAddressChain);
		} catch(Exception e) {}
	}
	public static void fireJuge() {
		//TODO 之后需要做反序列化
		try {
			//定义字典及管理器
			Dictionary dic = new Dictionary(279);
			ActionManager manager = new ActionManager(279);
			DictionaryContainer.addDictionary(dic);
			ManagerContainer.addManager(manager);
			GoalMethod uploadForm = new GoalMethod("cn.gyyx.action.dao.supporter.CommonSupportDAO", "uploadForm", InnerTransmissionData.class);
			GoalMethod queryForm = new GoalMethod("cn.gyyx.action.dao.supporter.CommonSupportDAO", "queryForm", InnerTransmissionData.class);
			GoalMethod queryFormNum = new GoalMethod("cn.gyyx.action.dao.supporter.CommonSupportDAO", "queryFormNum", InnerTransmissionData.class);
			GoalMethod updateForm = new GoalMethod("cn.gyyx.action.dao.supporter.CommonSupportDAO", "updateForm", InnerTransmissionData.class);
			GoalMethod lottery = new GoalMethod("cn.gyyx.action.service.lottery.LotterySupporterAdapter", "lotteryByDataBase", InnerTransmissionData.class);
			//通用操作链条头
			TransmissionChainNode commonQueryNumChain = new UserLoginCheckChainNode();
			commonQueryNumChain.setNext(new GoalMethodChainNode(queryFormNum).error("数据库数量查询错误"));
			TransmissionChainNode commonQueryChain = new UserLoginCheckChainNode();
			commonQueryChain.setNext(new GoalMethodChainNode(queryForm).error("数据库实体查询错误"));
			TransmissionChainNode commonUploadChain = new UserLoginCheckChainNode();
			commonUploadChain.setNext(new GoalMethodChainNode(uploadForm).error("数据库插入错误"));
			TransmissionChainNode commonUpdateChain = new UserLoginCheckChainNode();
			commonUpdateChain.setNext(new GoalMethodChainNode(updateForm).error("数据库更新错误"));
			TransmissionChainNode commonLottery = new UserLoginCheckChainNode();
			commonLottery.setNext(new GoalMethodChainNode(lottery).error("抽奖过程调用失败"));
			//定义数据类型（内部）
			//定义数据类型(外部)
			UploadType userMain = new UploadType("userMain",FormType.MAIN_FORM);//用户积分数据
			userMain.mappingName("leftScore",DBColum.I1,0,"剩余积分-提交值将被忽略-初始时固定为0");
			userMain.mappingName("usedScore",DBColum.I2,0,"已用积分-提交值将被忽略-初始时固定为0");
			userMain.mappingName("phoneNumber",DBColum.S1,"无","手机号-提交值将被忽略-初始时固定为“无”");
			userMain.mappingName("address",DBColum.S2,"无","收获地址-提交值将被忽略-初始时固定为“无”");
			userMain.mappingName("activation",DBColum.S3,"无","激活码-提交值将被忽略-初始时固定为“无”");
			userMain.mappingName("gift",DBColum.S4,"no","是否领取武道赠礼-提交值将被忽略-初始固定为“no”");
			userMain.mappingName("weixin",DBColum.S5,"no","是否关注过微信-提交值将被忽略-初始时固定为“no”");
			userMain.mappingName("weibo",DBColum.S6,"no","是否关注过微博-提交值将被忽略-初始时固定为“no”");
			userMain.mappingName("tieba",DBColum.S7,"no","是否关注过贴吧-提交值将被忽略-初始时固定为“no”");
			userMain.enableUpload();
			dic.addUploadType(userMain);
			
			UploadType userQuize = new UploadType("userQuize",FormType.EVENT_LOG);//每日用户答题数据
			dic.addUploadType(userQuize);
			
			UploadType userSign = new UploadType("userSign",FormType.EVENT_LOG);//用户签到数据
			userSign.enableUpload();
			dic.addUploadType(userSign);
			
			UploadType idCard = new UploadType("idCard",FormType.ALONE_FORM);
			idCard.mappingName("idCard","idCard","激活码-无需提交，该值将由内部抽取写入");
			dic.addUploadType(idCard);
			
			UploadType question = new UploadType("question",FormType.ALONE_FORM);
			question.mappingName("isSingle", "isSingle","是否为单选");
			question.mappingName("question", "question","问题内容");
			question.mappingName("answer1", "answer1","选项1");
			question.mappingName("answer2", "answer2","选项2");
			question.mappingName("answer3", "answer3","选项3");
			question.mappingName("answer4", "answer4","选项4");
			question.mappingName("trueAnswer","trueAnswer","正确答案");
			//question.enableUpload();//TODO 记得删了
			dic.addUploadType(question);
			
			UploadType prize = new UploadType("prize",FormType.ALONE_FORM);
			prize.enableUpload();
			dic.addUploadType(prize);
			//定义查询类型（内部）
			QueryType getUserMainNum = new QueryType("getUserMainNum");//查询本人信息数量
			getUserMainNum.setUploadType(userMain);
			getUserMainNum.limitUser();
			
			QueryType phoneSeted = new QueryType("phoneSeted");//查询本人是否设置过手机号
			phoneSeted.limitUser();
			phoneSeted.and(DBColum.S1, QueryStg.CHAR_EQUAL,"无");
			phoneSeted.setUploadType(userMain);
			
			QueryType addressSeted = new QueryType("addressSeted");//查询本人是否设置过地址
			addressSeted.limitUser();
			addressSeted.and(DBColum.S2, QueryStg.CHAR_EQUAL,"无");
			addressSeted.setUploadType(userMain);
			
			QueryType activited = new QueryType("activited");//查询本人是否领取了测试码
			activited.limitUser();
			activited.and(DBColum.S3,QueryStg.CHAR_EQUAL,"无");
			activited.setUploadType(userMain);
			
			QueryType gifted = new QueryType("gifted");//查询本人是否领取了武道礼包
			gifted.limitUser();
			gifted.and(DBColum.S4,QueryStg.CHAR_EQUAL,"no");
			gifted.setUploadType(userMain);
			
			QueryType weixined = new QueryType("weixined");//查询本人是否关注了微信
			weixined.limitUser();
			weixined.and(DBColum.S5,QueryStg.CHAR_EQUAL,"no");
			weixined.setUploadType(userMain);
			
			QueryType weiboed = new QueryType("weiboed");//查询本人是否关注了微博
			weiboed.limitUser();
			weiboed.and(DBColum.S6,QueryStg.CHAR_EQUAL,"no");
			weiboed.setUploadType(userMain);
			
			QueryType tiebaed = new QueryType("tiebaed");//查询本人是否关注了贴吧
			tiebaed.limitUser();
			tiebaed.and(DBColum.S7,QueryStg.CHAR_EQUAL,"no");
			tiebaed.setUploadType(userMain);
			
			QueryType getIdCard = new QueryType("getIdCard");//获取一张激活码
			getIdCard.and(DBColum.DISPLAY,QueryStg.CHAR_EQUAL,"no");
			getIdCard.setUseTop(1);
			getIdCard.setUploadType(idCard);
			
			QueryType prizeAgain = new QueryType("prizeAgain");//查询是否重复中
			prizeAgain.limitUser();
			prizeAgain.and("prizeEnglish",DBColum.JSON_STR,QueryStg.CHAR_LIKE);
			prizeAgain.setUploadType(prize);
			//定义查询类型(外部)
			QueryType getUserMain = new QueryType("getUserMain");//查询本人信息
			getUserMain.limitUser();
			getUserMain.addExtraOutput(DBColum.ACCOUNT);
			userMain.addQueryType(getUserMain);
			
			QueryType quizeToday = new QueryType("quizeToday");//查询本人今日是否答过题
			quizeToday.limitSqlDate();
			quizeToday.limitUser();
			userQuize.addQueryType(quizeToday);
			
			QueryType signToday = new QueryType("signToday");//查询本人今日是否签到
			signToday.limitSqlDate();
			signToday.limitUser();
			userSign.addQueryType(signToday);
			
			QueryType getUserSign = new QueryType("getUserSign");//查询本人连续签到天数
			getUserSign.addExtraOutput(DBColum.UPLOAD_SQLDATE);
			getUserSign.limitUser();
			userSign.addQueryType(getUserSign);
			
			QueryType getUsedIdCardNum = new QueryType("getUsedIdCardNum");//查询已使用激活码
			getUsedIdCardNum.and(DBColum.DISPLAY,QueryStg.CHAR_EQUAL,"yes");
			idCard.addQueryType(getUsedIdCardNum);
			
			QueryType getAllIdCardNum = new QueryType("getAllIdCardNum");//查询已使用激活码
			idCard.addQueryType(getAllIdCardNum);
			
			QueryType getRandomQuestion = new QueryType("getRandomQuestion");//随机查询5道题
			getRandomQuestion.randomize();
			getRandomQuestion.setUseTop(5);
			question.addQueryType(getRandomQuestion);
			
			QueryType getUserPrize = new QueryType("getUserPrize");
			getUserPrize.limitUser();
			prize.addQueryType(getUserPrize);
			//定义查询级联
			////定义更新类型（内部）
			UpdateType addFiveScore = new UpdateType("addFiveScore",DBTable.SEARCH_TB);//加五分
			addFiveScore.updateSet(DBColum.I1,UpdateOp.NUM_ADD,5);
			addFiveScore.limitUser();
			addFiveScore.setUploadType(userMain);
			
			UpdateType addTwScore = new UpdateType("addTwScore",DBTable.SEARCH_TB);//加20分
			addTwScore.updateSet(DBColum.I1,UpdateOp.NUM_ADD,20);
			addTwScore.limitUser();
			addTwScore.setUploadType(userMain);
			
			UpdateType redFtScore = new UpdateType("redFtScore",DBTable.SEARCH_TB);//-50分
			redFtScore.updateSet(DBColum.I1,UpdateOp.NUM_RED,50);
			redFtScore.updateSet(DBColum.I2,UpdateOp.NUM_ADD,50);
			redFtScore.limitUser();
			redFtScore.setUploadType(userMain);
			
			UpdateType redTHScore = new UpdateType("redTHScore",DBTable.SEARCH_TB);//-200分
			redTHScore.updateSet(DBColum.I1,UpdateOp.NUM_RED,200);
			redTHScore.updateSet(DBColum.I2,UpdateOp.NUM_ADD,200);
			redTHScore.limitUser();
			redTHScore.setUploadType(userMain);
			
			UpdateType checkIdCard = new UpdateType("checkIdCard",DBTable.ALONE_TB);
			checkIdCard.updateSet(DBColum.DISPLAY,UpdateOp.CHAR_EQUAL,"yes");
			checkIdCard.setUploadType(idCard);
			//定义更新类型（外部）
			////修改电话号码
			UpdateType updatePhoneNumber = new UpdateType("updatePhoneNumber",DBTable.SEARCH_TB);
			updatePhoneNumber.updateSet("phoneNumber", DBColum.S1, UpdateOp.CHAR_EQUAL,"修改电话号码");
			updatePhoneNumber.limitUser();
			userMain.addUpdateType(updatePhoneNumber);
			////绑定电话号码
			UpdateType setPhoneNumber = new UpdateType("setPhoneNumber",DBTable.SEARCH_TB);
			setPhoneNumber.updateSet("phoneNumber", DBColum.S1, UpdateOp.CHAR_EQUAL,"绑定电话号码");
			setPhoneNumber.limitUser();
			userMain.addUpdateType(setPhoneNumber);
			////修改地址
			UpdateType updateAddress = new UpdateType("updateAddress",DBTable.SEARCH_TB);
			updateAddress.updateSet("address", DBColum.S2, UpdateOp.CHAR_EQUAL,"修改收货地址");
			updateAddress.limitUser();
			userMain.addUpdateType(updateAddress);
			////绑定地址
			UpdateType setAddress = new UpdateType("setAddress",DBTable.SEARCH_TB);
			setAddress.updateSet("address", DBColum.S2, UpdateOp.CHAR_EQUAL,"绑定收货地址");
			setAddress.limitUser();
			userMain.addUpdateType(setAddress);
			////上传答题分数
			UpdateType updateQuize = new UpdateType("updateQuize",DBTable.SEARCH_TB);
			updateQuize.updateSet("quizeScore",DBColum.I1,UpdateOp.NUM_ADD,"答题分数");
			updateQuize.limitUser();
			userMain.addUpdateType(updateQuize);
			////领取武道赠礼
			UpdateType getGift = new UpdateType("getGift",DBTable.SEARCH_TB);
			getGift.updateSet(DBColum.S4,UpdateOp.CHAR_EQUAL,"yes");
			getGift.limitUser();
			userMain.addUpdateType(getGift);
			////领取微信赠礼
			UpdateType getWeixin = new UpdateType("getWeixin",DBTable.SEARCH_TB);
			getWeixin.updateSet(DBColum.S5,UpdateOp.CHAR_EQUAL,"yes");
			getWeixin.limitUser();
			userMain.addUpdateType(getWeixin);
			////领取微博赠礼
			UpdateType getWeibo = new UpdateType("getWeibo",DBTable.SEARCH_TB);
			getWeibo.updateSet(DBColum.S6,UpdateOp.CHAR_EQUAL,"yes");
			getWeibo.limitUser();
			userMain.addUpdateType(getWeibo);
			////领取贴吧赠礼
			UpdateType getTieba = new UpdateType("getTieba",DBTable.SEARCH_TB);
			getTieba.updateSet(DBColum.S7,UpdateOp.CHAR_EQUAL,"yes");
			getTieba.limitUser();
			userMain.addUpdateType(getTieba);
			////领取激活码
			UpdateType getActiviteCode = new UpdateType("getActiviteCode",DBTable.SEARCH_TB);
			getActiviteCode.updateSet("idCard",DBColum.S3,UpdateOp.CHAR_EQUAL,"激活码-填写值将被忽略");
			getActiviteCode.limitUser();
			userMain.addUpdateType(getActiviteCode);
			//定义操作链表（内部）
			////查询本人信息数量
			CrossChainConfig userMainNumberGetter = new CrossChainConfig();
			userMainNumberGetter.setCloneQuery(getUserMainNum);
			userMainNumberGetter.setCrossNode(commonQueryNumChain);
			userMainNumberGetter.setSaveName("mainNumber");
			////加5分
			CrossChainConfig scoreFiveAdder = new CrossChainConfig();
			scoreFiveAdder.setCloneUpdate(addFiveScore);
			scoreFiveAdder.setCrossNode(commonUpdateChain);
			scoreFiveAdder.setSaveName("addScore");
			////加20分
			CrossChainConfig scoreTwAdder = new CrossChainConfig();
			scoreTwAdder.setCloneUpdate(addTwScore);
			scoreTwAdder.setCrossNode(commonUpdateChain);
			scoreTwAdder.setSaveName("addScore");
			////-50分
			CrossChainConfig scoreFtReder = new CrossChainConfig();
			scoreFtReder.setCloneUpdate(redFtScore);
			scoreFtReder.setCrossNode(commonUpdateChain);
			scoreFtReder.setSaveName("redScore");
			////-200分
			CrossChainConfig scoreTHReder = new CrossChainConfig();
			scoreTHReder.setCloneUpdate(redTHScore);
			scoreTHReder.setCrossNode(commonUpdateChain);
			scoreTHReder.setSaveName("redScore");
			////设置今日答题状态
			CrossChainConfig quizeTodaySetter = new CrossChainConfig();
			quizeTodaySetter.setCloneUpload(userQuize);
			quizeTodaySetter.setCrossNode(commonUploadChain);
			quizeTodaySetter.setResultType(DataArea.T_RESULTBEAN);
			////查询本人是否设置过手机
			CrossChainConfig phoneSetedGetter = new CrossChainConfig();
			phoneSetedGetter.setCloneQuery(phoneSeted);
			phoneSetedGetter.setCrossNode(commonQueryNumChain);
			phoneSetedGetter.setSaveName("phoneSeted");
			////查询本人是否设置过地址
			CrossChainConfig addressSetedGetter = new CrossChainConfig();
			addressSetedGetter.setCloneQuery(addressSeted);
			addressSetedGetter.setCrossNode(commonQueryNumChain);
			addressSetedGetter.setSaveName("addressSeted");
			////查询本人是否领取过激活码
			CrossChainConfig activitedGetter = new CrossChainConfig();
			activitedGetter.setCloneQuery(activited);
			activitedGetter.setCrossNode(commonQueryNumChain);
			activitedGetter.setSaveName("activited");
			////查询本人是否领取过武道赠礼
			CrossChainConfig giftedGetter = new CrossChainConfig();
			giftedGetter.setCloneQuery(gifted);
			giftedGetter.setCrossNode(commonQueryNumChain);
			giftedGetter.setSaveName("gifted");
			////是否关注了微信
			CrossChainConfig weixinedGetter = new CrossChainConfig();
			weixinedGetter.setCloneQuery(weixined);
			weixinedGetter.setCrossNode(commonQueryNumChain);
			weixinedGetter.setSaveName("weixined");
			////是否关注了微博
			CrossChainConfig weiboedGetter = new CrossChainConfig();
			weiboedGetter.setCloneQuery(weiboed);
			weiboedGetter.setCrossNode(commonQueryNumChain);
			weiboedGetter.setSaveName("weiboed");
			////是否关注了贴吧
			CrossChainConfig teibaedGetter = new CrossChainConfig();
			teibaedGetter.setCloneQuery(tiebaed);
			teibaedGetter.setCrossNode(commonQueryNumChain);
			teibaedGetter.setSaveName("tiebaed");
			////获得一个激活码
			CrossChainConfig idCardGetter = new CrossChainConfig();
			idCardGetter.setCloneQuery(getIdCard);
			idCardGetter.setCrossNode(commonQueryChain);
			////注销一个激活码（标记为已分发）
			CrossChainConfig idCardChecker = new CrossChainConfig();
			idCardChecker.setCloneUpdate(checkIdCard);
			idCardChecker.setCrossNode(commonUpdateChain);
			////检测抽奖重复性
			CrossChainConfig prizeAgainSetter = new CrossChainConfig();
			prizeAgainSetter.setCloneQuery(prizeAgain);
			prizeAgainSetter.setCrossNode(commonQueryNumChain);
			prizeAgainSetter.setSaveName("prizeNumber");
			////进行抽奖
			CrossChainConfig prizeSetter = new CrossChainConfig();
			prizeSetter.setCrossNode(commonLottery);
			prizeSetter.setResultType(DataArea.T_DATA);
			prizeSetter.setSaveName(DBColum.JSON_STR);
			//定义操作链表（外部）
			////----基础类型
			////查询本人信息
			CrossChainConfig mainInfoGetter = new CrossChainConfig();
			mainInfoGetter.setCloneQuery(getUserMain);
			mainInfoGetter.setCrossNode(commonQueryChain);
			manager.baseChain("getUserMain",commonQueryChain);
			////查询本人签到全部信息
			TransmissionChainNode signNumQueryChain = new UserLoginCheckChainNode();
			signNumQueryChain
			.setNext(new GoalMethodChainNode(queryForm).error("数据库实体查询错误"))
			.setNext(new ContinuousDateChainNode().error("未能找到连续性日期参数"));
			manager.baseChain("getUserSign",signNumQueryChain);
			////查询本人今日是否答题过
			CrossChainConfig quizeTodayGetter = new CrossChainConfig();
			quizeTodayGetter.setCloneQuery(quizeToday);
			quizeTodayGetter.setCrossNode(commonQueryNumChain);
			quizeTodayGetter.setSaveName("todayQuizeNumber");
			manager.baseChain("quizeToday",commonQueryNumChain);
			////查询本人今日是否签到
			CrossChainConfig signTodayGetter = new CrossChainConfig();
			signTodayGetter.setCloneQuery(signToday);
			signTodayGetter.setCrossNode(commonQueryNumChain);
			signTodayGetter.setSaveName("todaySignNumber");
			manager.baseChain("signToday",commonQueryNumChain);
			////查询全部激活码数量
			manager.baseChain("getAllIdCardNum", commonQueryNumChain);
			////查询已用激活码数量
			manager.baseChain("getUsedIdCardNum", commonQueryNumChain);
			////上传问题
			//manager.baseChain("question",commonUploadChain);//TODO 之后删掉
			////查询随机问题
			manager.baseChain("getRandomQuestion",commonQueryChain);
			////查询中奖信息
			manager.baseChain("getUserPrize",commonQueryChain);
			////----CROSS类型
			////增加用户信息
			TransmissionChainNode addUserMainChain = new UserLoginCheckChainNode();
			addUserMainChain
			.setNext(new CrossChainNode(userMainNumberGetter).error("查询用户数量信息失败"))
			.setNext(new DataComparatorChainNode(Validate.NUM_SMALLER,"mainNumber",1).error("信息已存在"))
			.setNext(new GoalMethodChainNode(uploadForm).error("插入信息失败"));
			manager.baseChain("userMain",addUserMainChain);
			////用户签到
			TransmissionChainNode signTodayChain = new UserLoginCheckChainNode();
			signTodayChain
			.setNext(new CrossChainNode(mainInfoGetter).error("查询用户信息失败"))
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,Dictionary.getJNameByColumnName(DBColum.CODE)).error("数据库中没有您的信息"))
			.setNext(new CrossChainNode(signTodayGetter).error("查询今日签到信息失败"))
			.setNext(new DataComparatorChainNode(Validate.NUM_EQUAL,"todaySignNumber",0).error("您今日已经签到过了"))
			.setNext(new GoalMethodChainNode(uploadForm).error("签到信息插入失败"))
			.setNext(new CrossChainNode(scoreFiveAdder).error("更新积分失败"))
			.setNext(new DataComparatorChainNode(Validate.NUM_EQUAL,"addScore",1).error("更新积分失败，请勿频繁操作"));
			manager.baseChain("userSign",signTodayChain);
			////绑定电话(首次设置电话)
			TransmissionChainNode setPhoneNumberChain = new UserLoginCheckChainNode();
			setPhoneNumberChain
			.setNext(new CrossChainNode(mainInfoGetter).error("查询用户信息失败"))
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,DBColum.CODE).error("数据库中没有您的信息"))
			.setNext(new CrossChainNode(phoneSetedGetter).error("获取电话绑定状态失败"))
			.setNext(new DataComparatorChainNode(Validate.NUM_EQUAL,"phoneSeted",1).error("您已经设置过电话，请使用修改功能修改"))
			.setNext(new GoalMethodChainNode(updateForm).error("更新数据库电话号码失败"))
			.setNext(new CrossChainNode(scoreTwAdder).error("更新积分失败"));
			manager.baseChain("setPhoneNumber",setPhoneNumberChain);
			////更新电话
			TransmissionChainNode updatePhoneNumberChain = new UserLoginCheckChainNode();
			updatePhoneNumberChain
			.setNext(new CrossChainNode(mainInfoGetter).error("查询用户信息失败"))
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,DBColum.CODE).error("数据库中没有您的信息"))
			.setNext(new GoalMethodChainNode(updateForm).error("更新数据库电话号码失败"));
			manager.baseChain("updatePhoneNumber",updatePhoneNumberChain);
			////绑定地址(首次设置地址)
			TransmissionChainNode setAddressChain = new UserLoginCheckChainNode();
			setAddressChain
			.setNext(new CrossChainNode(mainInfoGetter).error("查询用户信息失败"))
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,DBColum.CODE).error("数据库中没有您的信息"))
			.setNext(new CrossChainNode(addressSetedGetter).error("获取地址设置信息失败"))
			.setNext(new DataComparatorChainNode(Validate.NUM_EQUAL,"addressSeted",1).error("您已经设置过地址，请使用修改功能修改"))
			.setNext(new GoalMethodChainNode(updateForm).error("更新数据库地址失败"))
			.setNext(new CrossChainNode(scoreTwAdder).error("更新积分失败"));
			manager.baseChain("setAddress",setAddressChain);
			////更新收货地址
			TransmissionChainNode updateAddressChain = new UserLoginCheckChainNode();
			updateAddressChain
			.setNext(new CrossChainNode(mainInfoGetter).error("查询用户信息失败"))
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,DBColum.CODE).error("数据库中没有您的信息"))
			.setNext(new GoalMethodChainNode(updateForm).error("更新数据库收获地址失败"));
			manager.baseChain("updateAddress",updateAddressChain);
			////用户答题
			TransmissionChainNode updateQuizeChain = new UserLoginCheckChainNode();
			updateQuizeChain
			.setNext(new CrossChainNode(mainInfoGetter).error("查询用户信息失败"))
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,DBColum.CODE).error("数据库中没有您的信息"))
			.setNext(new DataComparatorChainNode(DataArea.T_DATA,Validate.NUM_SMALLER,DBColum.I1,51).error("分数提交错误，应<=50"))
			.setNext(new CrossChainNode(quizeTodayGetter).error("查询今日答题状态失败"))
			.setNext(new DataComparatorChainNode(Validate.NUM_SMALLER,"todayQuizeNumber",1).error("您今日已经答过题了"))
			.setNext(new CrossChainNode(quizeTodaySetter).error("更新今日答题状态失败"))
			.setNext(new GoalMethodChainNode(updateForm).error("更新分数失败"));
			manager.baseChain("updateQuize",updateQuizeChain);
			////领取武道赠礼
			TransmissionChainNode giftedChain = new UserLoginCheckChainNode();
			giftedChain
			.setNext(new CrossChainNode(mainInfoGetter).error("查询用户信息失败"))
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,DBColum.CODE).error("数据库中没有您的信息"))
			.setNext(new CrossChainNode(giftedGetter).error("查询武道奖励领取状态失败"))
			.setNext(new DataComparatorChainNode(Validate.NUM_EQUAL,"gifted",1).error("您已经领取过武道奖励"))
			.setNext(new GoalMethodChainNode(updateForm).error("更新武道奖励失败"))
			.setNext(new CrossChainNode(scoreTwAdder).error("更新分数失败"));
			manager.baseChain("getGift",giftedChain);
			////领取微信赠礼
			TransmissionChainNode weixinedChain = new UserLoginCheckChainNode();
			weixinedChain
			.setNext(new CrossChainNode(mainInfoGetter).error("查询用户信息失败"))
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,DBColum.CODE).error("数据库中没有您的信息"))
			.setNext(new CrossChainNode(weixinedGetter).error("查询微信关注状态失败"))
			.setNext(new DataComparatorChainNode(Validate.NUM_EQUAL,"weixined",1).error("您已经领取过微信关注奖励了"))
			.setNext(new GoalMethodChainNode(updateForm).error("更新微信关注状态失败"))
			.setNext(new CrossChainNode(scoreTwAdder).error("更新奖励分数失败"));
			manager.baseChain("getWeixin",weixinedChain);
			
			////领取微博赠礼
			TransmissionChainNode weiboedChain = new UserLoginCheckChainNode();
			weiboedChain
			.setNext(new CrossChainNode(mainInfoGetter).error("查询用户信息失败"))
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,DBColum.CODE).error("数据库中没有您的信息"))
			.setNext(new CrossChainNode(weiboedGetter).error("获取微博关注状态失败"))
			.setNext(new DataComparatorChainNode(Validate.NUM_EQUAL,"weiboed",1).error("您已经领取过贴吧微博奖励"))
			.setNext(new GoalMethodChainNode(updateForm).error("更新微博关注状态失败"))
			.setNext(new CrossChainNode(scoreTwAdder).error("更新分数失败"));
			manager.baseChain("getWeibo",weiboedChain);
			
			////领取贴吧赠礼
			TransmissionChainNode tiebaedChain = new UserLoginCheckChainNode();
			tiebaedChain
			.setNext(new CrossChainNode(mainInfoGetter).error("查询用户信息失败"))
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,DBColum.CODE).error("数据库中没有您的信息"))
			.setNext(new CrossChainNode(teibaedGetter).error("获取贴吧关注状态失败"))
			.setNext(new DataComparatorChainNode(Validate.NUM_EQUAL,"tiebaed",1).error("您已经领取过贴吧关注奖励"))
			.setNext(new GoalMethodChainNode(updateForm).error("更新贴吧关注状态失败"))
			.setNext(new CrossChainNode(scoreTwAdder).error("更新分数失败"));
			manager.baseChain("getTieba",tiebaedChain);
			
			////兑换激活码
			TransmissionChainNode getIdCardChain = new UserLoginCheckChainNode();//1.检测登陆
			getIdCardChain
			.setNext(new CrossChainNode(mainInfoGetter).error("查询用户信息失败"))//2.检测信息存在
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,DBColum.CODE).error("数据库中没有您的信息"))
			.setNext(new CrossChainNode(activitedGetter).error("获取激活码状态失败"))
			.setNext(new DataComparatorChainNode(Validate.NUM_EQUAL,"activited",1).error("您已经领取过激活码"))//3.确认未领取激活码
			.setNext(new DataComparatorChainNode(Validate.NUM_BE,"leftScore",50).error("兑换激活码最少需要50积分"))//4.确认分数足够
			.setNext(new DataMoverChainNode(DataArea.T_EXTRA,DataArea.T_EXTRA,DBColum.CODE,"user_code_reg"))
			.setNext(new CrossChainNode(idCardGetter).error("获取激活码信息失败"))//5.找激活卡
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,"idCard").error("对不起，激活码已发完"))//6.确认有卡
			.setNext(new DataMoverChainNode(DataArea.T_EXTRA,DataArea.T_DATA,"idCard",DBColum.S3))
			.setNext(new CrossChainNode(idCardChecker).error("激活码销卡失败"))//7.销卡
			.setNext(new DataMoverChainNode(DataArea.T_EXTRA,DataArea.T_EXTRA,"user_code_reg",DBColum.CODE))
			.setNext(new CrossChainNode(scoreFtReder).error("分数更新操作失败"))//8.扣分
			.setNext(new DataComparatorChainNode(Validate.NUM_EQUAL,"redScore",1).error("扣分失败，请不要频繁访问本地址"))
			.setNext(new GoalMethodChainNode(updateForm).error("记录激活码失败"))//9.更新领取状态，插入激活码
			.setNext(new DataMoverChainNode(DataArea.T_EXTRA,DataArea.T_RESULTBEAN,"idCard",""));
			manager.baseChain("getActiviteCode",getIdCardChain);
			
			////记录中奖信息
			TransmissionChainNode prizeSaveChain = new GoalMethodChainNode(uploadForm);
			prizeSaveChain.error("抽奖记录失败");
			prizeSaveChain.setNext(new JSONDecompressionChainNode(DataArea.T_DATA,DataArea.T_RESULTBEAN));
			////若为谢谢则不记录
			TransmissionChainNode saveCheckChain = new JSONDecompressionChainNode(DataArea.T_DATA,DataArea.T_RESULTBEAN);
			saveCheckChain
			.setNext(new ConditionChainNode(new DataComparatorChainNode(Validate.STR_NOTEQUAL,"prizeEnglish","nameplate")))
			.setNext(prizeSaveChain);
			////给予谢谢参与
			TransmissionChainNode thanks = new DataSetterChainNode("{\"cardCode\":\"\",\"configCode\":4,\"isReal\":\"noRealPrize\",\"prizeChinese\":\"谢谢参与\",\"prizeCode\":0,\"prizeEnglish\":\"nameplate\",\"userCode\":0}");
			thanks.setNext(new JSONDecompressionChainNode(DataArea.T_RESULTBEAN,DataArea.T_RESULTBEAN));
			////条件节点
			ConditionChainNode cond = new ConditionChainNode(new DataComparatorChainNode(Validate.NUM_EQUAL,"prizeNumber",0));
			cond.setNext(saveCheckChain);
			cond.setElse(thanks);
			////抽奖
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date start = format.parse("2015-10-01");
			Date end = format.parse("2015-10-02");
			TransmissionChainNode prizeChain = new UserLoginCheckChainNode();//1.检测登陆
			prizeChain
			.setNext(new DateComparatorChainNode(start,end).error("活动已结束"))
			.setNext(new CrossChainNode(mainInfoGetter).error("查询用户信息失败"))//2.检测信息存在
			.setNext(new NullCheckChainNode(DataArea.T_EXTRA,DBColum.CODE).error("数据库中没有您的信息"))
			.setNext(new DataComparatorChainNode(Validate.NUM_BE,"leftScore",200).error("抽奖最少需要200积分"))//4.确认分数足够
			.setNext(new CrossChainNode(scoreTHReder).error("分数更新操作失败"))//8.扣分
			.setNext(new DataComparatorChainNode(Validate.NUM_EQUAL,"redScore",1).error("扣分失败，请不要频繁访问本地址"))
			.setNext(new CrossChainNode(prizeSetter).error("抽奖失败!"))
			.setNext(new JSONDecompressionChainNode(DataArea.T_DATA,DataArea.T_EXTRA))
			.setNext(new DataMoverChainNode(DataArea.T_EXTRA,DataArea.T_EXTRA,"prizeEnglish",DBColum.JSON_STR))
			.setNext(new CrossChainNode(prizeAgainSetter).error("确认中奖纪录失败"))
			.setNext(cond);
			
//			.setNext(new GoalMethodChainNode(uploadForm).error("抽奖记录失败"))
//			.setNext(new DataMoverChainNode(DataArea.T_DATA,DataArea.T_RESULTBEAN,DBColum.JSON_STR,"prize"))
//			.setNext(new MapResultChainNode());
			manager.baseChain("prize", prizeChain);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
