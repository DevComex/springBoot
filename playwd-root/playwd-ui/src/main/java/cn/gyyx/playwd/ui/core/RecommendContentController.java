 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月20日上午10:48:24
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.ui.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.RecommendContentBean;
import cn.gyyx.playwd.bll.RecommendContentBll;

/**
 * 推荐区域信息管理
 * @author lidudi
 *
 */
@RequestMapping("/content")
@Controller
public class RecommendContentController {

	private static final Logger logger = GYYXLoggerFactory.getLogger(RecommendContentController.class);
	
	@Autowired
	public RecommendContentBll recommendContentBll;
	
	/**
	 * 图文推荐区域列表
	 * @param slotId
	 * @param topNumber
	 * @return
	 */
	@GetMapping
	@ResponseBody
	public ResultBean<Object> getSlotContentList(Integer slotId, Integer topNumber) {
		try {
			//默认首页轮播
            slotId=(slotId==null||slotId.intValue()<=0)?2:slotId;
            //默认8条数据
            topNumber=(topNumber==null||topNumber.intValue()<=0)?8:topNumber;
            
            List<RecommendContentBean>recommendContentBeans= recommendContentBll.getSlotContentList(slotId, topNumber);
            if(recommendContentBeans.size()==0){
            	return new ResultBean<Object>("success", "成功", null);
            }
            
            List<Map<String, Object>>maps=FluentIterable.from(recommendContentBeans).
            		transform(new Function<RecommendContentBean, Map<String,Object>>() {

						@Override
						public Map<String, Object> apply(RecommendContentBean input) {
							Map<String, Object> map=new HashMap<String, Object>();
							map.put("code", input.getCode());
							map.put("title", input.getTitle());
							map.put("cover", input.getCover());
							map.put("url", input.getUrl());
							if(input.getArticleBean()==null){
								map.put("author", "官方");
								map.put("serverName", "");
							}else {
								map.put("author", input.getArticleBean().getAuthor());
								map.put("serverName", input.getArticleBean().getServerName().equals("官方")
										?"":input.getArticleBean().getServerName());
							}
							return map;
						}
					}).toList();
            
    		return new ResultBean<Object>("success", "成功", maps); 		
		} catch (Exception e) {
			logger.error("获取图文推荐区域信息异常", e);
			return new ResultBean<Object>("unknown_error", "获取图文推荐区域信息异常", null);
		}
	}
}
