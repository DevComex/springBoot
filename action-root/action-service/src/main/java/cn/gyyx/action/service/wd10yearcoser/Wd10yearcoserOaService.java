package cn.gyyx.action.service.wd10yearcoser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.challenger.OperationLogBean;
import cn.gyyx.action.beans.challenger.SameDataBean;
import cn.gyyx.action.beans.lottery.LuckyDrawLogBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.wd10yearcoser.Constants;
import cn.gyyx.action.beans.wd10yearcoser.CoserNovice;
import cn.gyyx.action.beans.wd10yearcoser.CoserOfficialVideo;
import cn.gyyx.action.beans.wd10yearcoser.ResourceBean;
import cn.gyyx.action.bll.challenger.OperationLogBll;
import cn.gyyx.action.bll.challenger.SameDataBll;
import cn.gyyx.action.bll.lottery.LuckyDrawLogBll;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.bll.wd10yearcoser.CoserNoviceBll;
import cn.gyyx.action.bll.wd10yearcoser.CoserVideoBll;
import cn.gyyx.action.bll.wd10yearcoser.ResourceBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.challenger.LotteryService;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年9月8日 下午10:32:49
 * 描        述：
 */
public class Wd10yearcoserOaService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(Wd10yearcoserOaService.class);
	
	private int actionCode = 389;
	private int checkPassGiveLotteryChanceCount = 10;
	private SameDataBll sameDataBll = new SameDataBll();
	private CoserNoviceBll coserNoviceBll = new CoserNoviceBll();
	private CoserVideoBll coserVideoBll = new CoserVideoBll();
	private OperationLogBll operationLogBll = new OperationLogBll();
	private ResourceBll resourceBll = new ResourceBll();
	LuckyDrawLogBll luckyDrawLogBll = new LuckyDrawLogBll();
	
	public static String MEM_KEY_PREFIX = "wd10yearcoser_";
	
	private MemcacheUtil memcacheUtil = new MemcacheUtil();
	private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
			.getMemcache();
	
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	public ResultBean<String> save(Integer staffCode,
			String realName,String picUrl, String imgUrl) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if(StringUtils.isBlank(picUrl)){
			resultBean.setProperties(false, "图片链接地址不允许为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(imgUrl)){
			resultBean.setProperties(false, "图片地址不允许为空", "");
			return resultBean;
		}
		
		SqlSession session = getSession();
		String key = "wd10yearcoser_" + "update_banner";
		try {
		//try (DistributedLock lock = new DistributedLock(key)) {
			//lock.weakLock(30, 11);
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("picUrl",picUrl);
			map.put("imgUrl",imgUrl);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			String value = mapper.writeValueAsString(map);
			sameDataBll.resetSameDate(
					"banner", value,
					actionCode);
			memcachedClientAdapter.set(key, getUntilDayEndSeconds(), value);

			// 加入操作日志
			try{
				OperationLogBean operateBean = new OperationLogBean();
				operateBean.setUserName(realName);
				operateBean.setUserid(staffCode);
				operateBean.setType("updateBanner");
				operateBean.setTid(0);
				operateBean.setActionCode(actionCode);
				operateBean.setDescription("修改banner信息");
				operationLogBll.addOperationLog(operateBean, session);
				session.commit(true);
			}catch(Exception e){
				logger.error("操作日志记录失败" ,e);
			}
			
			return new ResultBean<String>(true, "保存成功", null);
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("save error" ,e);
			return new ResultBean<>(false, "操作失败", "");
		} finally{
			if(session != null) session.close();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public ResultBean<Map<String,String>> getBanner() {
		String key = "wd10yearcoser_" + "update_banner";
		try {
			String t = memcachedClientAdapter.get(key, String.class);
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtils.isEmpty(t)){
				SameDataBean bean = sameDataBll.getSameDataBean("banner", actionCode);
				
				if(bean != null){
					ObjectMapper mapper = new ObjectMapper();
					map = mapper.readValue(bean.getContent(), Map.class);
					map.put("picUrl",map.get("picUrl"));
					map.put("imgUrl",map.get("imgUrl"));
					memcachedClientAdapter.set(key, getUntilDayEndSeconds(),
							bean.getContent());
				}
			}else{
				ObjectMapper mapper = new ObjectMapper();
				map = mapper.readValue(t, Map.class);
			}
				
			return new ResultBean<>(true, "保存成功", map);
		} catch (Exception e) {
			logger.error("getBanner error" ,e);
			return new ResultBean<>(false, "操作失败", null);
		} 
		
	}
	
	/**
	 * 得到距离一天结束的好友多少秒
	 * 
	 * @return
	 * @throws ParseException
	 */
	private Integer getUntilDayEndSeconds() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date nowDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nowDate);
			calendar.add(Calendar.DATE, 1);
			Date tempDate;
			tempDate = sdf.parse(sdf.format(calendar.getTime()));
			return (int) ((tempDate.getTime() - nowDate.getTime()) / 1000);
		} catch (Exception e) {
			logger.warn("getUntilDayEndSeconds" + e);
			return 60 * 60 * 24;
		}
	}

	public ResultBean<String> saveImgNotice(Integer staffCode, String realName,
			String code, String title, String url, String picUrl) {
		ResultBean<String> resultBean = new ResultBean<String>();
		try{
			code = Integer.parseInt(code) + "";
		}catch(Exception e){
			logger.error("error" ,e);
		}
		if(StringUtils.isBlank(code)){
			resultBean.setProperties(false, "编号不允许为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(title)){
			resultBean.setProperties(false, "公告内容不允许为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(url)){
			resultBean.setProperties(false, "图片链接地址不允许为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(picUrl)){
			resultBean.setProperties(false, "图片不允许为空", "");
			return resultBean;
		}
		
		SqlSession session = getSession();
		String key = "wd10yearcoser_" + "update_novice_img_" + code;
		try {
		//try (DistributedLock lock = new DistributedLock(key)) {
			//lock.weakLock(30, 11);
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("code",code);
			map.put("title",title);
			map.put("url",url);
			map.put("picUrl",picUrl);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			String value = mapper.writeValueAsString(map);
			sameDataBll.resetSameDate(
					"noviceimg"+code, value,
					actionCode);
			memcachedClientAdapter.set(key, getUntilDayEndSeconds(), value);

			// 加入操作日志
			try{
				OperationLogBean operateBean = new OperationLogBean();
				operateBean.setUserName(realName);
				operateBean.setUserid(staffCode);
				operateBean.setType("updateNoviceImg"+code);
				operateBean.setTid(0);
				operateBean.setActionCode(actionCode);
				operateBean.setDescription("修改图片轮播信息"+code);
				operationLogBll.addOperationLog(operateBean, session);
				session.commit(true);
			}catch(Exception e){
				logger.error("操作日志记录失败" ,e);
			}
			
			return new ResultBean<String>(true, "保存成功", null);
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("save error" ,e);
			return new ResultBean<>(false, "操作失败", "");
		} finally{
			if(session != null) session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public ResultBean<List<Map<String,String>>> getImgNotice() {
		String key = "wd10yearcoser_" + "update_novice_img_";
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			for(int i=1;i<=4;i++){
				String t = memcachedClientAdapter.get(key+i, String.class);
				Map<String,String> map = new HashMap<String,String>();
				if(StringUtils.isEmpty(t)){
					SameDataBean bean = sameDataBll.getSameDataBean("noviceimg"+i, actionCode);
					
					if(bean != null){
						ObjectMapper mapper = new ObjectMapper();
						map = mapper.readValue(bean.getContent(), Map.class);
						map.put("code",map.get("code"));
						map.put("title",map.get("title"));
						map.put("url",map.get("url"));
						map.put("picUrl",map.get("picUrl"));
						memcachedClientAdapter.set(key, getUntilDayEndSeconds(),
								bean.getContent());
					}
				}else{
					ObjectMapper mapper = new ObjectMapper();
					map = mapper.readValue(t, Map.class);
				}
				if(map != null && map.size() > 0){
					list.add(map);
				}
			}
			
				
			return new ResultBean<>(true, "保存成功", list);
		} catch (Exception e) {
			logger.error("getBanner error" ,e);
			return new ResultBean<>(false, "操作失败", null);
		} 
		
	}

	@SuppressWarnings("unchecked")
	public ResultBean<Map<String, String>> getImgNotice(String code) {
		String key = "wd10yearcoser_" + "update_novice_img_";
		ResultBean<Map<String, String>> resultBean = new ResultBean<>();
		try {
			try{
				code = Integer.parseInt(code) + "";
			}catch(Exception e){
				logger.error("error" ,e);
			}
			if(StringUtils.isBlank(code)){
				resultBean.setProperties(false, "编号不允许为空", null);
				return resultBean;
			}
			
			String t = memcachedClientAdapter.get(key+code, String.class);
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtils.isEmpty(t)){
				SameDataBean bean = sameDataBll.getSameDataBean("noviceimg"+code, actionCode);
				
				if(bean != null){
					ObjectMapper mapper = new ObjectMapper();
					map = mapper.readValue(bean.getContent(), Map.class);
					map.put("code",map.get("code"));
					map.put("title",map.get("title"));
					map.put("url",map.get("url"));
					map.put("picUrl",map.get("picUrl"));
					memcachedClientAdapter.set(key, getUntilDayEndSeconds(),
							bean.getContent());
				}
			}else{
				ObjectMapper mapper = new ObjectMapper();
				map = mapper.readValue(t, Map.class);
			}
			if(map.size() == 0){
				return new ResultBean<>(true, "操作成功", null);
			}
				
			return new ResultBean<>(true, "操作成功", map);
		} catch (Exception e) {
			logger.error("getBanner error" ,e);
			return new ResultBean<>(false, "操作失败", null);
		} 
	}
	
	/**
	 * 根据id获取
	 */
	public ResultBean<CoserNovice> getNovice(
			int code) {
		ResultBean<CoserNovice> resultBean = new ResultBean<CoserNovice>();
		try {
			if(code == 0){
				resultBean.setProperties(false, "公告编号不能为空", null);
				return resultBean;
			}
			
			CoserNovice data = coserNoviceBll
					.getCoserNovice(code);
			return new ResultBean<CoserNovice>(true, "获取成功", data);
		} catch (Exception e) {
			logger.error("getChallenterLivenoviceListData" ,e);
			return new ResultBean<CoserNovice>(false, "获取失败", null);
		}
	}
	
	/**
	 * 分页显示后台
	 */
	public ResultBeanWithPage<List<CoserNovice>> noviceDataList(
			CoserNovice bean) {
		try {
			List<CoserNovice> list = coserNoviceBll
					.getNoviceListPaging(bean);
			int count = coserNoviceBll
					.getNoviceCount(bean);
			return new ResultBeanWithPage<>(true, "获取成功", list, count);
		} catch (Exception e) {
			logger.error("noticeDataList" ,e);
			return new ResultBeanWithPage<>(false, "获取失败", null, 0);
		}
	}
	
	
	public ResultBean<String> updateNovice(CoserNovice bean,int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if(bean.getCode() == 0){
			resultBean.setProperties(false, "公告编号不能为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(bean.getTitle())){
			resultBean.setProperties(false, "公告标题不允许为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(bean.getContent())){
			resultBean.setProperties(false, "公告内容不允许为空", "");
			return resultBean;
		}
			
		SqlSession session = getSession();
		try{
			coserNoviceBll.update(bean, session);

			// 加入操作日志 先不加 重要操作才加
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(realName);
			operateBean.setUserid(staffCode);
			operateBean.setType("noviceUpdate");
			operateBean.setTid(bean.getCode());
			operateBean.setActionCode(actionCode);
			operateBean.setDescription("更新公告");
			operationLogBll.addOperationLog(operateBean, session);
			session.commit(true);
		}catch(Exception e){
			if(session != null) session.rollback();
			logger.error("oa----livenovice--update",e);
			return new ResultBean<>(true, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
		return new ResultBean<>(true, "操作成功", "");
	}
	
	public ResultBean<String> insertNovice(CoserNovice bean,int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if(StringUtils.isBlank(bean.getTitle())){
			resultBean.setProperties(false, "公告标题不允许为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(bean.getContent())){
			resultBean.setProperties(false, "公告内容不允许为空", "");
			return resultBean;
		}
			
		SqlSession session = getSession();
		try{
			bean.setCreateTime(new Date());
			bean.setIsPub("N");
			int code =  coserNoviceBll.insert(bean, session);

			// 加入操作日志
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(realName);
			operateBean.setUserid(staffCode);
			operateBean.setType("noviceCreate");
			operateBean.setTid(code);
			operateBean.setActionCode(actionCode);
			operateBean.setDescription("创建公告");
			operationLogBll.addOperationLog(operateBean, session);
			session.commit(true);
		}catch(Exception e){
			if(session != null) session.rollback();
			logger.error("oa----insert--update",e);
			return new ResultBean<>(true, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
		return new ResultBean<>(true, "操作成功", "");
	}
	
	public ResultBean<String> deleteNovice(int code, int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		SqlSession session = getSession();
		try (DistributedLock lock = new DistributedLock(
				MEM_KEY_PREFIX + "noviceDelOperator_")) {
			lock.weakLock(30, 11);
			CoserNovice t = coserNoviceBll.getCoserNovice(code);
			if(t == null){
				resultBean.setProperties(false, "公告不存在", null);
				return resultBean;
			}
			
			coserNoviceBll.delete(t, session);

			// 加入操作日志
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(realName);
			operateBean.setUserid(staffCode);
			operateBean.setType("noviceDel");
			operateBean.setTid(code);
			operateBean.setActionCode(actionCode);
			operateBean.setDescription("删除公告");
			operationLogBll.addOperationLog(operateBean, session);
			session.commit(true);
			
			return new ResultBean<>(true, "删除成功", "");
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("deletenovice error" ,e);
			return new ResultBean<>(false, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
	}

	public ResultBean<String> pubNovice(int code, String state,int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if(state == null || (!"Y".toString().equals(state))){
			resultBean.setProperties(false, "状态不正确", null);
			return resultBean;
		}
		
		SqlSession session = getSession();
		
		try {
			CoserNovice t = coserNoviceBll.getCoserNovice(code);
			if(t == null){
				resultBean.setProperties(false, "公告不存在", null);
				return resultBean;
			}
			
			if("Y".toString().equals(state)){
				if("Y".equals(t.getIsPub())){
					resultBean.setProperties(false, "已经发布,不允许再次操作", null);
					return resultBean;
				}
			}
			t.setIsPub(state);
			t.setPubTime(new Date());
			coserNoviceBll.update(t, session);

			// 加入操作日志
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(realName);
			operateBean.setUserid(staffCode);
			operateBean.setType("novicePub");
			operateBean.setTid(code);
			operateBean.setActionCode(actionCode);
			operateBean.setDescription("Y".toString().equals(state)?
					"发布公告":"取消发布");
			operationLogBll.addOperationLog(operateBean, session);
			session.commit(true);
		
			return new ResultBean<>(true, "Y".toString().equals(state)
					?"发布公告成功":"取消发布成功", "");
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("topOperator error" ,e);
			return new ResultBean<>(false, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
	}
	
	//-----------------------官方视频------------------------------
	
	/**
	 * 根据id获取
	 */
	public ResultBean<CoserOfficialVideo> getVideo(
			int code) {
		ResultBean<CoserOfficialVideo> resultBean = new ResultBean<CoserOfficialVideo>();
		try {
			if(code == 0){
				resultBean.setProperties(false, "作品编号不能为空", null);
				return resultBean;
			}
			
			CoserOfficialVideo data = coserVideoBll
					.getCoserVideo(code);
			return new ResultBean<CoserOfficialVideo>(true, "获取成功", data);
		} catch (Exception e) {
			logger.error("getVideo" ,e);
			return new ResultBean<CoserOfficialVideo>(false, "获取失败", null);
		}
	}
	
	/**
	 * 分页显示后台
	 */
	public ResultBeanWithPage<List<CoserOfficialVideo>> videoDataList(
			CoserOfficialVideo bean) {
		try {
			List<CoserOfficialVideo> list = coserVideoBll
					.getVideoListPaging(bean);
			int count = coserVideoBll
					.getVideoCount(bean);
			return new ResultBeanWithPage<>(true, "获取成功", list, count);
		} catch (Exception e) {
			logger.error("noticeDataList" ,e);
			return new ResultBeanWithPage<>(false, "获取失败", null, 0);
		}
	}
	
	
	public ResultBean<String> updateVideo(CoserOfficialVideo bean,int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if(bean.getCode() == 0){
			resultBean.setProperties(false, "作品编号不能为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(bean.getWorksName())){
			resultBean.setProperties(false, "作品名称不允许为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(bean.getWorksPic())){
			resultBean.setProperties(false, "作品封面不允许为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(bean.getWorksUrl())){
			resultBean.setProperties(false, "作品外链不允许为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(bean.getContent())){
			resultBean.setProperties(false, "作品简介不允许为空", "");
			return resultBean;
		}
			
		SqlSession session = getSession();
		try{
			coserVideoBll.update(bean, session);

			// 加入操作日志 先不加 重要操作才加
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(realName);
			operateBean.setUserid(staffCode);
			operateBean.setType("videoUpdate");
			operateBean.setTid(bean.getCode());
			operateBean.setActionCode(actionCode);
			operateBean.setDescription("更新视频");
			operationLogBll.addOperationLog(operateBean, session);
			session.commit(true);
		}catch(Exception e){
			if(session != null) session.rollback();
			logger.error("oa----video--update",e);
			return new ResultBean<>(true, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
		return new ResultBean<>(true, "操作成功", "");
	}
	
	public ResultBean<String> insertVideo(CoserOfficialVideo bean,int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if(StringUtils.isBlank(bean.getWorksName())){
			resultBean.setProperties(false, "作品名称不允许为空", "");
			return resultBean;
		}
//		if(StringUtils.isBlank(bean.getWorksPic())){
//			resultBean.setProperties(false, "作品封面不允许为空", "");
//			return resultBean;
//		}
		if(StringUtils.isBlank(bean.getWorksUrl())){
			resultBean.setProperties(false, "作品外链不允许为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(bean.getContent())){
			resultBean.setProperties(false, "作品简介不允许为空", "");
			return resultBean;
		}
			
		SqlSession session = getSession();
		try{
			bean.setCreateTime(new Date());
			bean.setIsTop("N");
			int code =  coserVideoBll.insert(bean, session);

			// 加入操作日志
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(realName);
			operateBean.setUserid(staffCode);
			operateBean.setType("videoCreate");
			operateBean.setTid(code);
			operateBean.setActionCode(actionCode);
			operateBean.setDescription("创建视频");
			operationLogBll.addOperationLog(operateBean, session);
			session.commit(true);
		}catch(Exception e){
			if(session != null) session.rollback();
			logger.error("oa----insert--update",e);
			return new ResultBean<>(true, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
		return new ResultBean<>(true, "操作成功", "");
	}
	
	public ResultBean<String> deleteVideo(int code, int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		SqlSession session = getSession();
		try (DistributedLock lock = new DistributedLock(
				MEM_KEY_PREFIX + "videoDelOperator_")) {
			lock.weakLock(30, 11);
			CoserOfficialVideo t = coserVideoBll.getCoserVideo(code);
			if(t == null){
				resultBean.setProperties(false, "作品不存在", null);
				return resultBean;
			}
			
			coserVideoBll.delete(t, session);

			// 加入操作日志
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(realName);
			operateBean.setUserid(staffCode);
			operateBean.setType("videoDel");
			operateBean.setTid(code);
			operateBean.setActionCode(actionCode);
			operateBean.setDescription("删除视频");
			operationLogBll.addOperationLog(operateBean, session);
			session.commit(true);
			
			return new ResultBean<>(true, "删除成功", "");
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("deletevideo error" ,e);
			return new ResultBean<>(false, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
	}

	public ResultBean<String> topVideo(int code, String state,int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if(state == null || (!"Y".toString().equals(state))){
			resultBean.setProperties(false, "状态不正确", null);
			return resultBean;
		}
		
		SqlSession session = getSession();
		
		try {
			CoserOfficialVideo t = coserVideoBll.getCoserVideo(code);
			if(t == null){
				resultBean.setProperties(false, "视频不存在", null);
				return resultBean;
			}
			
			if("Y".toString().equals(state)){
				if("Y".equals(t.getIsTop())){
					resultBean.setProperties(false, "已经推荐,不允许再次操作", null);
					return resultBean;
				}
			}
			t.setIsTop(state);
			t.setCommendTime(new Date());
			coserVideoBll.update(t, session);
			//取消最晚的第三条的推荐
			coserVideoBll.cancleLastTop(session);
			// 加入操作日志
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(realName);
			operateBean.setUserid(staffCode);
			operateBean.setType("videoPub");
			operateBean.setTid(code);
			operateBean.setActionCode(actionCode);
			operateBean.setDescription("Y".toString().equals(state)?
					"推荐":"取消推荐");
			operationLogBll.addOperationLog(operateBean, session);
			session.commit(true);
		
			return new ResultBean<>(true, "Y".toString().equals(state)
					?"推荐成功":"取消推荐成功", "");
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("topOperator error" ,e);
			return new ResultBean<>(false, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
	}

	public ResultBeanWithPage<List<ResourceBean>> worksDataList(
			ResourceBean bean) {
		try {
			if("CHECKEDAndTop".equals(bean.getCheckType())){
				bean.setCheckType("CHECKED");
				bean.setIsShowStr("1");
			}else if("CHECKEDAndUnTop".equals(bean.getCheckType())){
				bean.setCheckType("CHECKED");
				bean.setIsShowStr("0");
			}
			List<ResourceBean> list = resourceBll.getBackResourceListPaging(bean);
			int count = resourceBll.getBackResourceCount(bean);
			return new ResultBeanWithPage<>(true, "获取成功", list, count);
		} catch (Exception e) {
			logger.error("worksDataList" ,e);
			return new ResultBeanWithPage<>(false, "获取失败", null, 0);
		}
	}
	
	public ResultBean<String> checkWorks(int code, String state,int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if(state == null || ((!"CHECKED".equals(state))&& (!"UNCHECK".equals(state)))){
			resultBean.setProperties(false, "状态不正确", null);
			return resultBean;
		}
		SqlSession session = getSession();
		try (DistributedLock lock = new DistributedLock(
				MEM_KEY_PREFIX + "workscheck_")) {
			lock.weakLock(30, 11);
			ResourceBean t = resourceBll.findResourceByCode(code);
			if(t == null){
				resultBean.setProperties(false, "作品不存在", null);
				return resultBean;
			}
			
			if("CHECKED".equals(state)){
				if("CHECKED".equals(t.getCheckType())){
					resultBean.setProperties(false, "已经通过,不允许再次操作", null);
					return resultBean;
				}
			}
			if("UNCHECK".equals(state)){
				if("UNCHECK".equals(t.getCheckType())){
					resultBean.setProperties(false, "已经拒绝,不允许再次操作", null);
					return resultBean;
				}
				//推荐中的不能再次拒绝
				if(t.getIsShow()){
					resultBean.setProperties(false, "作品推荐中,不允许拒绝操作", null);
					return resultBean;
				}
			}
			
			if("CHECKED".equals(state)){
				checkGiveLotteryChance(session, t);
			}
			
			
			t.setCheckType(state);
			t.setUpdateTime(new Date());
			resourceBll.updateResourceByCode(t,session);
			
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(realName);
			operateBean.setUserid(staffCode);
			operateBean.setType("CHECKED".toString().equals(state)?
					"worksPassCheck":"worksUnPassCheck");
			operateBean.setTid(code);
			operateBean.setActionCode(actionCode);
			operateBean.setAccount(t.getUsername());
			operateBean.setDescription("CHECKED".toString().equals(state)?
					"审核通过":"审核拒绝");
			operationLogBll.addOperationLog(operateBean, session);
			
			session.commit(true);
			return new ResultBean<>(true, "操作成功", "");
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("topOperator error" ,e);
			return new ResultBean<>(false, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
	}

	public void checkGiveLotteryChance(SqlSession session, ResourceBean t) {
		//查询次数是否==10, 是的话，抽奖次数+1   0918 改 每10次增加一次机会
		int num = operationLogBll.getCoserWorksCheckCount(actionCode,"worksPassCheck",t.getUsername(), session);
		//int giveChanceCount = luckyDrawLogBll.getCountByAccountAndSourceInAction(t.getUsername(),actionCode,"worksPass", session);
		//if(num >= checkPassGiveLotteryChanceCount && giveChanceCount == 0){
		if(((num+1) % checkPassGiveLotteryChanceCount) == 0){
			//增加抽奖次数和抽奖次数添加日志
			LuckyDrawLogBean logBean = new LuckyDrawLogBean();
			logBean.setAccount(t.getUsername());
			logBean.setActionCode(actionCode);
			logBean.setDrawCount(1);
			logBean.setSource("worksPass");
			logBean.setUserId(t.getUserCode());
			// 这就得加抽奖次数
			LotteryService.setLotteryTimesSqlSession(logBean, session);
		}
	}
	
	public ResultBean<String> batchCheckWorks(String codesStr, String state,int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if(state == null || ((!"CHECKED".equals(state))&& (!"UNCHECK".equals(state)))){
			resultBean.setProperties(false, "状态不正确", null);
			return resultBean;
		}
		if(codesStr == null || codesStr.split(",").length == 0){
			resultBean.setProperties(false, "参数不正确", null);
			return resultBean;
		}
		SqlSession session = getSession();
		try (DistributedLock lock = new DistributedLock(
				MEM_KEY_PREFIX + "workscheck_")) {
			lock.weakLock(30, 11);
//			List<Integer> ids = new ArrayList<Integer>();
			
//			for(String id : codesStr.split(",")){
//				ids.add(Integer.parseInt(id));
//			}
//			
//			resourceBll.batchCheckWorks(ids,state);
			
			//不同作品通过10次增加一次抽奖计划
			for(String id : codesStr.split(",")){
				ResourceBean t = resourceBll.findResourceByCode(Integer.parseInt(id));
				if(t == null){
					logger.info("check--作品不存在:"+id);
					continue;
				}
				if("CHECKED".equals(state)){
					if("CHECKED".equals(t.getCheckType())){
						continue;
					}
					checkGiveLotteryChance(session,t);
				}else{
					//推荐中的不能拒绝
					if(t.getIsShow()){
						continue;
					}
					if("UNCHECK".equals(t.getCheckType())){
						continue;
					}
				}
				
				t.setCheckType(state);
				t.setUpdateTime(new Date());
				resourceBll.updateResourceByCode(t,session);
				
				OperationLogBean operateBean = new OperationLogBean();
				operateBean.setUserName(realName);
				operateBean.setUserid(staffCode);
				operateBean.setType("CHECKED".toString().equals(state)?
						"worksPassCheck":"worksUnPassCheck");
				operateBean.setTid(Integer.parseInt(id));
				operateBean.setActionCode(actionCode);
				operateBean.setAccount(t.getUsername());
				operateBean.setDescription("CHECKED".toString().equals(state)?
						"审核通过":"审核拒绝");
				operationLogBll.addOperationLog(operateBean, session);
			}
			
			
			session.commit(true);
		
			return new ResultBean<>(true, "操作成功", "");
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("topOperator error" ,e);
			return new ResultBean<>(false, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
	}
	
	public ResultBean<String> topWorks(int code, String state,int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if(state == null || (!"Y".equals(state) && !"N".equals(state))){
			resultBean.setProperties(false, "状态不正确", null);
			return resultBean;
		}
		
		SqlSession session = getSession();
		try {
			ResourceBean t = resourceBll.findResourceByCode(code);
			if(t == null){
				resultBean.setProperties(false, "作品不存在", null);
				return resultBean;
			}
			
			t.setIsShow("Y".equals(state)?true:false);
			t.setUpdateTime(new Date());
			t.setShowTime("Y".equals(state)?new Date():null);
			int num = Constants.getTopNumByWorksType(t.getType());
			
			if("N".equals(state)){
				ResourceBean qb = new ResourceBean();
				qb.setType(t.getType());
				qb.setIsShowStr("1");
				int c = resourceBll.getBackResourceCount(qb);
				
				//检查数量
				if(c <=num ){
					resultBean.setProperties(false, "推荐个数少于"+num+"个,不允许操作", null);
					return resultBean;
				}
			}
			
			resourceBll.updateResourceByCode(t,session);
			//取消最晚的第N条的推荐
			//resourceBll.cancleLastTopWorksByType(num,t.getType(),session);
			
			// 加入操作日志
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(realName);
			operateBean.setUserid(staffCode);
			operateBean.setType("worksPub");
			operateBean.setTid(code);
			operateBean.setActionCode(actionCode);
			operateBean.setDescription("Y".toString().equals(state)?
					"推荐":"取消推荐");
			operationLogBll.addOperationLog(operateBean, session);
			session.commit(true);
		
			return new ResultBean<>(true, "Y".toString().equals(state)
					?"推荐成功":"取消推荐成功", "");
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("topOperator error" ,e);
			return new ResultBean<>(false, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
	}
	
	/*--------------前台调用--------------------*/
	
	//首页大图 使用：getBanner()
	
	//首页轮播图片公告  使用：getImgNotice()
	
	//首页文字公告  最新1条公告
	public ResultBean<CoserNovice> noviceNew(
			CoserNovice bean) {
		try {
			CoserNovice res = coserNoviceBll
					.getNoviceNew();
			return new ResultBean<>(true, "获取成功", res);
		} catch (Exception e) {
			logger.error("noticeDataList" ,e);
			return new ResultBean<>(false, "获取失败", null);
		}
	}
	
	//首页文字公告  显示前N条
	public ResultBean<List<CoserNovice>> noviceFontDataList(
			CoserNovice bean) {
		try {
			List<CoserNovice> list = coserNoviceBll
					.getNoviceFontListPaging(bean);
			return new ResultBean<>(true, "获取成功", list);
		} catch (Exception e) {
			logger.error("noticeDataList" ,e);
			return new ResultBean<>(false, "获取失败", null);
		}
	}
	
	//首页文字公告 按照ID查询  使用：getNovice()
	
	//官方视频 推荐
	public ResultBean<List<CoserOfficialVideo>> videoFrontTopList(
			CoserOfficialVideo bean) {
		try {
			List<CoserOfficialVideo> list = coserVideoBll
					.videoFrontTopList(bean);
			return new ResultBean<>(true, "获取成功", list);
		} catch (Exception e) {
			logger.error("videoFrontDataList" ,e);
			return new ResultBean<>(false, "获取失败", null);
		}
	}
	
	//官方视频 列表
	public ResultBeanWithPage<List<CoserOfficialVideo>> videoFrontDataList(
			CoserOfficialVideo bean) {
		try {
			List<CoserOfficialVideo> list = coserVideoBll
					.getVideoFrontListPaging(bean);
			int count = coserVideoBll
					.getVideoCount(bean);
			return new ResultBeanWithPage<>(true, "获取成功", list, count);
		} catch (Exception e) {
			logger.error("videoFrontDataList" ,e);
			return new ResultBeanWithPage<>(false, "获取失败", null, 0);
		}
	}

}
