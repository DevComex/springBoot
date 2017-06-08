package cn.gyyx.action.oa.fightbrickgame;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.fightbrickgame.FightBrickGameBean;
import cn.gyyx.action.bll.fightbrickgame.FightBrickGameBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 
  * <p>
  *   微信h5小游戏的后台
  * </p>
  *  
  * @author Administrator
  * @since 0.0.1
 */
@Controller
@RequestMapping("/fightbrickgame")
public class FightBrickGameController {
	
	private static final Logger logger = GYYXLoggerFactory.getLogger(FightBrickGameController.class);
	
	private FightBrickGameBLL fightBrickGameBll=new FightBrickGameBLL();	
	
	/**
	 * H5小游戏首页
	 * @return
	 */
	@RequestMapping(value="/index",method={RequestMethod.GET})
	public String index(){
		return "fightbrickgame/index";
	}
	
	/**
	 * 根据查询条件查询列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list",method={RequestMethod.POST})
	@ResponseBody
	public ResultBean<Object> getList(HttpServletRequest request){		
		
		try{
		
			String keyWord=request.getParameter("keyWord");
			String beginTime=request.getParameter("beginTime");
			String endTime=request.getParameter("endTime");
			String pageIndexStr=request.getParameter("pageIndex");
			String pageSizeStr=request.getParameter("pageSize");			
			
			beginTime=beginTime==null?"":beginTime.trim();
			endTime=endTime==null?"":endTime.trim();
			keyWord=keyWord==null?"":keyWord.trim();			
			
			int pageIndex=pageIndexStr==null?1:Integer.parseInt(pageIndexStr);			
			int pageSize=pageSizeStr==null?10:Integer.parseInt(pageSizeStr);
			
			if(!endTime.equals("")){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				endTime=sdf.format(new Date(sdf.parse(endTime).getTime()+24*60*60*1000));
			}
			
			ResultBean<Object> result=new ResultBean<Object>();
			List<FightBrickGameBean> list=fightBrickGameBll.getList(keyWord,beginTime,endTime,pageIndex,pageSize);
			if(list.size()==0){
				result.setMessage("暂无数据");
				return result;
			}
			
			SimpleDateFormat sdf2=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			
			List<Object> mapList=new ArrayList<Object>();
			for(FightBrickGameBean item : list){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("code", item.getCode());
				map.put("openid", item.getOpenid());
				map.put("nickName", item.getNickName());
				map.put("latestScore", item.getLatestScore());
				map.put("highScore", item.getHighScore());
				map.put("rank", item.getRank());
				map.put("historyScore", item.getHistoryScore());
				map.put("updateTime", sdf2.format(item.getUpdateTime()));
				mapList.add(map);
			}
			
			Integer total=fightBrickGameBll.getCount(keyWord, beginTime, endTime);
			
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("rows", mapList);
			resultMap.put("total", total);
			
			result.setMessage("查询成功");
			result.setIsSuccess(true);
			result.setTotal(total);
			result.setRows(mapList);
			return result;
		}catch(Exception ex){
			logger.error("查询失败",ex);
			return new ResultBean<Object>(false, "查询失败", null);
		}		
	}
}
