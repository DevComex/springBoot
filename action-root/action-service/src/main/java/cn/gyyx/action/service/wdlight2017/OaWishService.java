package cn.gyyx.action.service.wdlight2017;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.gyyx.action.beans.lottery.ResultBean;
import cn.gyyx.action.beans.wd10yearcoser.ResultBeanWithPage;
import cn.gyyx.action.beans.wdlight2017.LightOaBean;
import cn.gyyx.action.beans.wdlight2017.ValidWishBean;
import cn.gyyx.action.bll.wdlight2017.WishBll;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class OaWishService {
	private static final GYYXLogger LOG = GYYXLoggerFactory.getLogger(OaWishService.class);
	WishBll wishBll = new WishBll();

	public ResultBeanWithPage<List<ValidWishBean>> getValidWish(int pageNum, int page, int status) {
		if (StringUtils.isEmpty(pageNum + "") || StringUtils.isEmpty(page + "") || StringUtils.isEmpty(status + "")) {
			LOG.info("获取许愿审核列表失败");
			return new ResultBeanWithPage<>(false, "参数不能为空", null, 0);
		}
		if (status == -1) {
			LOG.info("获取许愿审核列表失败");
			return new ResultBeanWithPage<>(false, "请选择许愿状态", null, 0);
		}
		List<ValidWishBean> wishList = wishBll.getValidWishBean(pageNum, page, status);
		int count=wishBll.getWishCount(status);
        LOG.debug("获取许愿列表wishList.size():{}", wishList.size());
	    return new ResultBeanWithPage<>(true, "获取许愿列表成功", wishList, count);
	
	}

	public ResultBean<Object> modifyWishStatusByCode(int status, int code) {
		ResultBean<Object> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "修改许愿状态失败", null);
		if (StringUtils.isEmpty(code + "") || StringUtils.isEmpty(status + "")) {
			LOG.info("修改许愿状态失败");
			resultBean.setProperties(false, "参数不能为空", null);
			return resultBean;
		}
		int result = wishBll.modifyWishStatusByCode(status, code);
		LOG.debug("修改许愿状态影响行数:{}", result);
		if (result > 0) {
			resultBean.setProperties(true, "修改许愿状态成功", null);
		}
		return resultBean;
	}
	
	public ResultBean<Object> modifyLightLimitNum(int level, int limitNum) {
		ResultBean<Object> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "修改人数失败", null);
		if(limitNum%2!=0){
			LOG.info("修改人数必须是偶数:{}",limitNum );
			resultBean.setProperties(false, "修改人数必须是偶数", null);
			return resultBean;
		}
		//获取该层信息
		LightOaBean lightBean=getLight(level);
		if(lightBean==null){
			LOG.warn("获取{}层信息失败:", level);
			resultBean.setProperties(false, "获取许愿层失败", null);
			return resultBean;
		}
		if(lightBean.getLightType()>=3){
			LOG.info("该层已经是点亮状态，不能修改人数:{}", lightBean.getLightType());
			resultBean.setProperties(false, "该层已经是点亮状态，不能修改人数", null);
			return resultBean;
		}
		
		if(lightBean.getPeopleNum()>=limitNum){
			LOG.info("修改人数小于或等于该层原有人数:oldNum{},newNum{}",lightBean.getPeopleNum(), limitNum);
			resultBean.setProperties(false, "修改人数小于或等于该层原有人数,不能修改人数", null);
			return resultBean;
		}
		int result = wishBll.modifyLightLimitNum(limitNum, level);
		LOG.debug("修改人数影响行数:{}", result);
		if (result > 0) {
			resultBean.setProperties(true, "修改人数成功", null);
		}
		return resultBean;
	}
	
	public LightOaBean getLight(int level) {
		List<LightOaBean> lightBeans = wishBll.getLightLevel();
		if(lightBeans==null||lightBeans.isEmpty()||lightBeans.size()<=0){
			return null;
		}		
		LOG.debug("获取总共层size:{}", lightBeans.size());
		LightOaBean bean=new LightOaBean();
		for (LightOaBean lightBean : lightBeans) {
			if(level==lightBean.getLevel()){
				bean=lightBean;
			}
		}
		return bean;
	}
	
	public ResultBean<Object> getLightLevel() {
		ResultBean<Object> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "获取许愿层信息失败", null);
		List<LightOaBean> result = wishBll.getLightLevel();
		if(result==null||result.isEmpty()||result.size()<=0){
			LOG.warn("获取许愿层信息失败");
			return resultBean;
		}	
		LOG.info("获取许愿层信息成功");
		resultBean.setProperties(true, "获取许愿层信息成功", result);
		return resultBean;
	}
	

}
