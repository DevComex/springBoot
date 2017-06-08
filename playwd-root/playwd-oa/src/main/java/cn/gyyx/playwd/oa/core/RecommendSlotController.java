 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月7日上午10:56:01
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.oa.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.RecommendSlotBean;
import cn.gyyx.playwd.service.RecommendSlotService;

/**
 * 推荐位功能
 * @author lidudi
 *
 */
@Controller
@RequestMapping("/slot")
public class RecommendSlotController {
	
	private static final Logger logger = GYYXLoggerFactory.getLogger(RecommendSlotController.class);
	
	/**
	 *推荐位管理 
	 */
	@Autowired
	public RecommendSlotService recommendSlotService;
	
	/**
	 * 获取推荐位置列表
	 * @param type
	 * @return
	 */
	@GetMapping("/list")
	@ResponseBody
	public ResultBean<List<Object>> getRecommendSlotList(String contentType) {
		try {
			if(StringUtils.isEmpty(contentType)){
				return new ResultBean<List<Object>>("invalid_contentType", "推荐位置类型不能为空", null);
			}	
			
			List<RecommendSlotBean>recommendList=recommendSlotService.getRecommendSlotList(contentType);
			List<Object>resultList=new ArrayList<Object>();						
			for (RecommendSlotBean recommendSlotBean : recommendList) {
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("code", recommendSlotBean.getCode());
				map.put("slotGroup", recommendSlotBean.getSlotGroup());
				map.put("slot", recommendSlotBean.getSlot());
				map.put("list", recommendSlotBean.getList());
				resultList.add(map);
				}	
			
			return new ResultBean<List<Object>>("success", "成功", resultList);
			
		} catch (Exception e) {
			logger.error("获取推荐位置列表异常", e);
			return new ResultBean<List<Object>>("unknown_error", "获取推荐位置列表异常", null);
		}
	}
}
