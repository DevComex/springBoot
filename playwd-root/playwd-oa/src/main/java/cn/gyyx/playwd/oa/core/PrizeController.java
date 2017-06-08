 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月7日上午10:59:27
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.oa.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.oa.stage.model.OAUserInfoModel;
import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.PrizeBean;
import cn.gyyx.playwd.bll.PrizeBll;
import cn.gyyx.playwd.oa.common.web.BaseController;
import cn.gyyx.playwd.service.PrizeService;

/**
 * 奖励管理
 * @author lidudi
 *
 */
@Controller
@RequestMapping("/prize")
public class PrizeController extends BaseController{
	
	private static final Logger logger = GYYXLoggerFactory.getLogger(PrizeController.class);
	
	/**
	 *奖励物品 
	 */
	@Autowired
	public PrizeBll prizeBll;
	
	@Autowired
	public PrizeService prizeService;
	@GetMapping(value={"/record"})
	public String record(){
	  return "prize/prizeRecord";
	}
	/**
	 * 获取奖励物品列表
	 * @param contentType 分类
	 * @return
	 */
	@GetMapping("/list")
	@ResponseBody
	public ResultBean<List<Object>> getPrizeListByContentType(String contentType) {
		try {
			if(StringUtils.isEmpty(contentType)){
				return new ResultBean<List<Object>>("faild", "分类不能为空", null);
			}
			List<PrizeBean>prizeList=prizeBll.getPrizeListByContentType(contentType);
			List<Object> ResultBeanList=new ArrayList<Object>();
			if(prizeList!=null&&prizeList.size()>0){		
				for (PrizeBean prizeBean : prizeList) {
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("code", prizeBean.getCode());
					map.put("name", prizeBean.getName());
					map.put("rmb", 0);
					map.put("silverCoins", 0);
					map.put("title", "无");
					for (int i = 0; i < prizeBean.getItemList().size(); i++) {
						if(prizeBean.getItemList().get(i).getName().indexOf("现金")>=0){
							map.put("rmb", prizeBean.getItemList().get(i).getName());
						}
						if(prizeBean.getItemList().get(i).getName().indexOf("银元宝")>=0){
							map.put("silverCoins", prizeBean.getItemList().get(i).getName());
						}
						if(prizeBean.getItemList().get(i).getName().indexOf("称号")>=0){
							map.put("title", prizeBean.getItemList().get(i).getName());
						}					
					}
					ResultBeanList.add(map);
				}
				return new ResultBean<List<Object>>("success", "成功", ResultBeanList);
			}
			return new ResultBean<List<Object>>("success", "无数据", null);
		} catch (Exception e) {
			logger.error("获取奖励物品列表异常", e);
			return new ResultBean<List<Object>>("unknown_error", "获取奖励物品列表异常", null);
		}
	}
	
	/**
     * 发奖历史记录
     */
    @GetMapping(value = "/history")
    @ResponseBody
    public PageBean<Object> history(String contentType,String searchType,String searchValue,String startTime,String endTime,
        Integer pageIndex,Integer pageSize,HttpServletRequest request,HttpServletResponse response) {
            logger.info("发奖历史记录接口:contentType:{},searchType:{} ,pageIndex:{},pageSize:{}",contentType,searchType,pageIndex,pageSize);
            try {
            //验证登录
              OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
              if (userInfoModel == null) {
                  return PageBean.createPage("incorrect-login", 0, 1, 10, null, "用户没有登录");
              }
              
              logger.info("获取推荐管理列表开始：账号:{}", userInfoModel.getAccount());
              
                pageIndex = (pageIndex == null || pageIndex <=0 ) ? 1 : pageIndex; 
                pageSize = pageSize == null ? 10 : pageSize; 
                searchValue = searchValue==null?"":searchValue.trim();
                return prizeService.getPrizeLogList(contentType,searchType,searchValue.trim(),startTime,endTime,pageIndex,pageSize);
            } catch (Exception e) {
                logger.error("发奖历史记录接口异常",e);
                return PageBean.createPage("interface-error", 0, 1, 10, null, "接口异常");
            }
    }

}
