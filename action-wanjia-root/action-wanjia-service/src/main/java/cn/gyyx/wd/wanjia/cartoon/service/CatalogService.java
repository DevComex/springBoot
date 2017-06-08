package cn.gyyx.wd.wanjia.cartoon.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.wd.wanjia.ResultBean;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCollect;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdCollectMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaMapper;

@Service
public class CatalogService {
	@Autowired
	private WanwdManhuaMapper manhuaMapper;
	@Autowired
	private WanwdCollectMapper collectMapper;

	public List<Map<String, Object>>   collectinfo(Integer userId, int pageNumber, int pageSize) {
		int start = (pageNumber - 1) * pageSize + 1;
        int end = pageNumber * pageSize;
        Map<String, Integer> map =new HashMap<String, Integer>();
        map.put("userId", userId);
        map.put("start", start);
        map.put("end", end);
		 List<Map<String,Object>> list = manhuaMapper.selectByUserId(map);
		 return list;
		 
	}

	public int selectCountByUid(Integer userId) {
		return manhuaMapper.selectCountByUid(userId);
	}

	public List<Map<String, Object>> bodylist(int categoryCode, int pageNumber, int pageSize, Integer type) {
		//int pageSize =12;
		int start = (pageNumber - 1) * pageSize + 1;
        int end = pageNumber * pageSize;
        
        Map<String, Integer> map =new HashMap<String, Integer>();
        if(type ==0){
        	map.put("time", type);
        }else{
        	map.put("num", type);
        }
        if(categoryCode==0){
        	map.put("categoryCode", null);
        }else{
        	map.put("categoryCode", categoryCode);
        }
        map.put("start", start);
        map.put("end", end);
        
		return manhuaMapper.selectManhuaList(map);
	}

	public int bodylistCount(Integer categoryCode) {
		 if(categoryCode==0){
			 categoryCode =null;
	       } 
		return manhuaMapper.selectManhuaListCount(categoryCode);
	}

	public List<Map<String, Object>> userCatalogList(Integer userId) {
		
		return collectMapper.userCatalogList(userId);
	}

	public ResultBean catalogbook(Integer manhuaCode, Integer userId,String userName) {
		ResultBean resultBean = new ResultBean<>(false,"fail");
		WanwdCollect wanCollect=collectMapper.selectByManhuaCode(manhuaCode,userId);
		
		if(wanCollect==null){//添加收藏信息
			WanwdCollect wc = new WanwdCollect();
			wc.setCreaterTime(new Date());
			wc.setIsDelete(false);
			wc.setSourcesType(4);
			wc.setSourcesCode(manhuaCode);
			wc.setUserId(userId);
			wc.setUserName(userName);
			collectMapper.insertSelective(wc);
			
			resultBean.setMessage("收藏成功");
			resultBean.setSuccess(true);
			return resultBean;
		}else{
			 
			wanCollect.setIsDelete(true);
			collectMapper.updateByPrimaryKeySelective(wanCollect);
			resultBean.setMessage("已取消收藏！");
			resultBean.setSuccess(true);
			return resultBean;
		}
		
		
	}

	public List<Map<String, Object>> bodyRightList(String location) {
		// TODO Auto-generated method stub
		return manhuaMapper.selectRightList(location);
	}

 
	
}
