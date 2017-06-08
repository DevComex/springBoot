package cn.gyyx.playwd.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.RoleBean;
import cn.gyyx.playwd.beans.playwd.UserBean;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.bll.RoleBll;
import cn.gyyx.playwd.bll.UserBll;
import cn.gyyx.playwd.bll.UserTitleBll;

@Service
public class UserService {
	
	/**
	 * 获取用户信息
	 */
	@Autowired
	private  UserBll userBll  ;
	
	/**
	 * 用户称号
	 */
	@Autowired
	private  UserTitleBll userTitleBll;
	
	@Autowired
	private ArticleBll articleBll;
	@Autowired
	private RoleBll roleBll;

	
	/**
	 * 获取作者名片信息 包含排名和数据
	 * @param userId
	 * @return
	 * @throws Exception
	 */
    public UserBean getUserInfoAll(int userId) throws Exception {
        // 根据code查询记者信息
        UserBean memberInfoBean = userBll.getByUserId(userId);
        if (memberInfoBean == null) {
            memberInfoBean = new UserBean();
        }
        // 获取默认角色信息
        String picture = "";
        RoleBean roleBean = roleBll.getDefaultRole(userId);
        if (roleBean != null) {
            picture = roleBean.getPicture();
        }
        memberInfoBean.setIcon(picture);
        // 获取用户财富（排名和累计奖励）
        userBll.getRewardInfoRank(memberInfoBean);
        // 获取当前用户的称号
        String title = userTitleBll.getUserCurrentTitle(userId);
        memberInfoBean.setTitle(title);
        // 获取发表作品数
        int articleCount = articleBll.getPassedArtitleCountByUserId(userId);
        memberInfoBean.setArticleCount(articleCount);
        return memberInfoBean;
    }
	
	/**
	 * 财富排行榜
	 * @param topNumber
	 * @return
	 * @throws Exception 
	 */
	public ResultBean<Object> getWealthRankingList(Integer topNumber) throws Exception {
		 List<LinkedHashMap<String,String>>hashMaps= userBll.getRewardRankTopN(topNumber);
         if(hashMaps.size()==0){
         	return new ResultBean<Object>("success", "成功", null);
         }
         
         List<Map<String, Object>>maps=FluentIterable.from(hashMaps).
         		transform(new Function<Map<String,String>, Map<String,Object>>() {

						@Override
						public Map<String, Object> apply(Map<String,String> input) {
							Map<String, Object> map=new HashMap<String, Object>();
							Integer userId=Integer.parseInt(input.get("userId"));
							ArticleBean articleBean=articleBll.getServerNameAndAuth(userId);
							map.put("serverName", articleBean.getServerName());
							map.put("auth", articleBean.getAuthor());
							map.put("userRank", input.get("userRank"));
							map.put("rmb", input.get("rmb").toString().split("\\.")[0]+"元");
							map.put("silverCoin", input.get("silverCoin"));
							return map;
						}
					}).toList();
         return new ResultBean<Object>("success", "成功", maps);
	}

    public UserBll getUserBll() {
        return userBll;
    }

    public void setUserBll(UserBll userBll) {
        this.userBll = userBll;
    }

    public UserTitleBll getUserTitleBll() {
        return userTitleBll;
    }

    public void setUserTitleBll(UserTitleBll userTitleBll) {
        this.userTitleBll = userTitleBll;
    }

    public ArticleBll getArticleBll() {
        return articleBll;
    }

    public void setArticleBll(ArticleBll articleBll) {
        this.articleBll = articleBll;
    }

    public RoleBll getRoleBll() {
        return roleBll;
    }

    public void setRoleBll(RoleBll roleBll) {
        this.roleBll = roleBll;
    }
	
}
