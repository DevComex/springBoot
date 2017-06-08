 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李鹄
    * @联系方式：lihu@gyyx.cn
    * @创建时间：2017年4月19日下午2:31:27
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.CommentBean;
import cn.gyyx.playwd.beans.playwd.UserBean;
import cn.gyyx.playwd.bll.CommentBll;
import cn.gyyx.playwd.bll.UserBll;
import cn.gyyx.playwd.service.CommentService;

/**
 * 举报管理
 * @author lihu
 *
 */
public class CommentServiceTest {

        private CommentBll commentBll;

     
        private UserBll memberInfoBll;
	
	private CommentService commentService;  
	
	@BeforeClass
	public void setMockup() {
	        commentService=new CommentService();
		commentBll=mock(CommentBll.class);
		memberInfoBll=mock(UserBll.class);
		commentService.setCommentBll(commentBll);
		commentService.setMemberInfoBll(memberInfoBll);
	}
	@Test(description="获取浏览详情页评论列表,当参数正确,返回数据")
	public void getBrowsePageCommentList_whenparm_thendata() throws Exception {
	        int startSize = 1;
	        int endSize = 5;
	        int contentId=1;
	        int userId=1;
	        String contentType ="article";
	        List<CommentBean> list = new ArrayList<>();
	        list.add(getComment("1"));
	        list.add(getComment("2"));
	        UserBean user = new UserBean();
	        user.setCode(1);
	        user.setIcon("ceshi");
		when(commentBll.getBrowsePageCommentList(contentType, contentId, startSize, endSize)).thenReturn(list);
		when(memberInfoBll.getByUserId(1)).thenReturn(user);
		when(memberInfoBll.getByUserId(2)).thenReturn(user);
		//比较返回值是否相等
		ResultBean<List<Map<String,String>>> resultBean = commentService.getBrowsePageCommentList(contentType, contentId, startSize, endSize);
                assertEquals(resultBean.getIsSuccess(),true);
                assertEquals(resultBean.getMessage(),"获取成功");
                assertEquals(resultBean.getData(),list);
		 
	}
	
	@Test(description=" 根据评论id,获取回复列表,返回数据")
        public void getBrowsePageReplyList_whenCommentId_thendata() throws Exception {
               
                int commentId=1;
                List<CommentBean> list = new ArrayList<>();
                list.add(getComment("1"));
                list.add(getComment("2"));
               
                when(commentBll.getBrowsePageReplyList(commentId)).thenReturn(list);
                //比较返回值是否相等
                ResultBean<List<Map<String,String>>> resultBean = commentService.getBrowsePageReplyList(commentId);
                assertEquals(resultBean.getIsSuccess(),true);
                assertEquals(resultBean.getMessage(),"获取成功");
                assertEquals(resultBean.getData(),list);
                 
        }
	
	@Test(description=" 根据评论id, 获取详情 ")
	public void viewDetails_whenCommentId_thendata() throws Exception {
	    String parent ="child";
	    CommentBean bean = getComment("1");
	    List<Map<String, Object>> list = new ArrayList<>();
            list.add(getMap("1"));
            list.add(getMap("2"));
	    
	    when(commentBll.get(bean.getCode())).thenReturn(bean);
	    when(commentBll.viewDetailsByCode(bean.getCode(),parent)).thenReturn(list);
	    //比较返回值是否相等
	   ResultBean resultBean = commentService.viewDetails(1);
	    assertEquals(resultBean.getIsSuccess(),true);
	    assertEquals(resultBean.getMessage(),"获取列表成功");
	    assertEquals(resultBean.getData(),list);
	    
	}
	@Test(description="修改评论状态 ")
	public void auditing_whenCommentId_thendata() throws Exception {
	    Integer code =1;
	    
	   // when(commentBll.changeCommentIsShow(code, true)).thenReturn(null);
	    //比较返回值是否相等
	    ResultBean resultBean = commentService.auditing(code, "show") ;
	    assertEquals(resultBean.getIsSuccess(),true);
	    assertEquals(resultBean.getMessage(),"展示修改成功");
	    
	}
	
	@Test(description="添加回复或评论")
	public void comment_whenCommentId_thendata() throws Exception {
	    
	    int commentId=1;
	    CommentBean bean = getComment("1");
	    
	    when(commentBll.get(bean.getCode())).thenReturn(bean);
	    when(commentBll.getCount(bean.getContentType(),
	            bean.getContentId())).thenReturn(2);
	    Mockito.doAnswer(new Answer<Object>() {
	            public Object answer(InvocationOnMock invocation) {
	                Object[] args = invocation.getArguments();
	                System.out.println("%%%%%%%%%%%%%%%%%"+args);
	                return "called with arguments: " + args;
	            }
	        }).when(commentBll).insert(bean);
	    //when(commentBll.insert(bean)).thenReturn(2);
	    ResultBean<Object> resultBean = commentService.comment(bean);
	    
	    //比较返回值是否相等
	    assertEquals(resultBean.getIsSuccess(),false);
	    assertEquals(resultBean.getMessage(),"获取成功");
	    
	}
	
	@Test(description=" 根据不同参数,获取评论列表,返回数据")
	public void getCommentList_whenParm_thendata() throws Exception {
	    Integer pageIndex=1; 
	    Integer pageSize=5;
            String content="content";
            String account="account";
            String contentType="contentType";
            String title="title";
            Integer isShow=1;
            String startTime="2017-05-25";
            String endTime="2017-06-25";
	    int commentId=1;
	    List<Map<String, Object>> list = new ArrayList<>();
	    list.add(getMap("1"));
	    list.add(getMap("2"));
	   PageBean<Map<String,Object>> page = PageBean.createPage(true, 2, pageIndex, pageSize, list, "获取列表成功");
	   System.out.println("commentBll++++++++"+commentBll);
	    when(commentBll.selectCommentList(pageIndex,
	            pageSize, content, account, contentType, title, isShow, startTime,
	            endTime)).thenReturn(list);
	    when(commentBll.selectCommentListCount(pageIndex,
	            pageSize, content, account, contentType, title, isShow, startTime,
	            endTime)).thenReturn(2);
	    //比较返回值是否相等
	    PageBean pageBean = commentService.getCommentList(pageIndex, pageSize, content, account, contentType, title, isShow, startTime, endTime);
	    assertEquals(pageBean.getIsSuccess(),true);
	    assertEquals(pageBean.getMessage(),"获取列表成功");
	   assertEquals(pageBean.getData(),list);
	    
	}
	
	 private Map<String, Object> getMap(String string) {
	     Map<String, Object> map =new HashMap<String, Object>();
	     map.put(string, string);
             return map;
    }
    private CommentBean getComment(String value){
	     CommentBean bean =new CommentBean();
	     bean.setCode(Integer.valueOf(value) );
	     bean.setContent(value);
	     bean.setContentId(2);
	     bean.setContentType("ceshi");
	     bean.setParentCode(2);
	     bean.setFromUserId(Integer.valueOf(value));
	     bean.setFromUserAccount(value);
	     return bean;
	 }
	 
}
