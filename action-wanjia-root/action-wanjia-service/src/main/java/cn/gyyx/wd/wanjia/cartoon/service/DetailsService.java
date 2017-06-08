package cn.gyyx.wd.wanjia.cartoon.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaComment;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaBookMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaCommentMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaMapper;
@Service
public class DetailsService {
	@Autowired
	private WanwdManhuaCommentMapper commentMapper;
	@Autowired
	private WanwdManhuaMapper manhuaMapper;
	@Autowired
	private WanwdManhuaBookMapper bookMapper;
	
	
	public Map<String, Object>  findDetailsList(Integer pageIndex, Integer pageSize, String type, Integer manhuaCode) {
		int start = (pageIndex - 1) * pageSize + 1;
        int end = pageIndex * pageSize;
        Map<String, Object> map =new HashMap<String, Object>();
        Map<String, Object> resultMap=new HashMap<>(); 
        map.put("type", type);
        map.put("manhuaCode", manhuaCode);
        int findDetailsParentListCount = commentMapper.findDetailsParentListCount(map);
        map.put("start", start);
        map.put("end", end);
        List<WanwdManhuaComment> parentList= commentMapper.findDetailsParentList(map);
        for (WanwdManhuaComment Comment : parentList) {
        	List<WanwdManhuaComment> childList=commentMapper.findDetailsChildList(Comment.getCode());
        	Comment.setList(childList);
		}
        resultMap.put("list", parentList);
        resultMap.put("total", findDetailsParentListCount);
		return resultMap;
	}
	public void saveParentComment(String type, Integer manhuaCode, String context, Integer userId, String account) {
		WanwdManhuaComment comment = new WanwdManhuaComment();
		 comment.setType(type);
		 comment.setContext(context);
		 comment.setCreateTime(new Date());
		 comment.setManhuaCode(manhuaCode);
		 comment.setSourcesAccount(account);
		 comment.setSourcesId(userId);
		 commentMapper.insertSelective(comment);
	}
	public void saveChildComment(String type, Integer manhuaCode, String context, Integer userId, String account,
			Integer parentCode) {
		WanwdManhuaComment parentComment = commentMapper.selectByPrimaryKey(parentCode);
		
		WanwdManhuaComment comment = new WanwdManhuaComment();
		 comment.setType(type);
		 comment.setContext(context);
		 comment.setCreateTime(new Date());
		 comment.setManhuaCode(manhuaCode);
		 comment.setSourcesAccount(account);
		 comment.setSourcesId(userId);
		 comment.setTargetAccount(parentComment.getSourcesAccount());
		 comment.setTargetId(parentComment.getSourcesId());
		 comment.setParentCode(parentCode);
		 commentMapper.insertSelective(comment);
	}
	public WanwdManhua selectManhuaByCode(Integer manhuaCode) {
		WanwdManhua wanwdManhua = manhuaMapper.selectByPrimaryKey(manhuaCode);
		
		return wanwdManhua;
	}
	public List<WanwdManhuaBook> getBookList(Integer manhuaCode) {
		List<WanwdManhuaBook> list=bookMapper.getBookList(manhuaCode);
		for (WanwdManhuaBook wanwdManhuaBook : list) {
			//4天之后
			Date date=new Date(wanwdManhuaBook.getCreateTime().getTime() + 4*24*60*60*1000);
			int i = date.compareTo(new Date());
			if(i>=0){
				wanwdManhuaBook.setNewly("NEW");
			}
			
		}
		return list;
	}
	public int getBookListCount(Integer manhuaCode) {
		return bookMapper.getBookListCount(manhuaCode);
	}
	public List<WanwdManhuaBook> getbookGroupList(Integer manhuaCode, Integer groupKey) {
		Map<String, Object> map =new HashMap<String, Object>();
		int start = (groupKey - 1) * 12 + 1;
        int end = groupKey * 12;
        map.put("manhuaCode", manhuaCode);
        map.put("start", start);
        map.put("end", end);
        List<WanwdManhuaBook> list = bookMapper.getbookGroupList(map);
        for (WanwdManhuaBook wanwdManhuaBook : list) {
			//4天之后
			Date date=new Date(wanwdManhuaBook.getCreateTime().getTime() + 4*24*60*60*1000);
			int i = date.compareTo(new Date());
			if(i>=0){
				wanwdManhuaBook.setNewly("NEW");
			}
			
		}
		return list;
	}

}
