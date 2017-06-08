package cn.gyyx.playwd.bll;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.ArticleBean.State;
import cn.gyyx.playwd.beans.playwd.CategoryBean.CategoryType;
import cn.gyyx.playwd.beans.playwd.ReviewLogBean;
import cn.gyyx.playwd.dao.playwd.ReviewLogDao;

/**
 * 状态日志
 * @author lidudi
 *
 */
@Component
public class ReviewLogBll {
	@Autowired
	ReviewLogDao reviewLogDao;

	/**
	 * 图文状态转换日志
	 * @param contentId
	 * @param contentType
	 * @param FromState
	 * @param operator
	 */
	public boolean insert(int contentId,CategoryType contentType,State fromState,State toState,String operator){
		ReviewLogBean log = new ReviewLogBean();
		log.setContentId(contentId);
		log.setContentType(contentType.Value());
		log.setCreateTime(new Date());
		log.setFromStatus(fromState.Value());
		log.setToStatus(toState.Value());
		log.setOperator(operator);
		return reviewLogDao.insert(log)>0;
	}
}