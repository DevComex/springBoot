/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：马文斌
 * 联系方式：mawenbin@gyyx.cn 
 * 创建时间：2015年3月11日下午5:52
 * 版本号：v1.0
 * 本类主要用途叙述：祝福Service
 */
package cn.gyyx.service.wd9year;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import cn.gyyx.action.beans.wd9year.WishBean;
import cn.gyyx.action.beans.wdno1pet.ResultBean;
import cn.gyyx.action.bll.wd9year.RandomUtil;
import cn.gyyx.action.bll.wd9year.WishBll;
import cn.gyyx.action.bll.wdno1pet.Validation;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

/**
 * 祝福Service
 * */
public class WishService {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WishService.class);
	private WishBll wishBll = new WishBll();

	/**
	 * 实现上传祝福功能
	 * */
	public ResultBean<Integer> uploadWish(HttpServletRequest request,
			WishBean wishBean) {
		logger.debug("WishBean", wishBean);
		ResultBean<Integer> msg = new ResultBean<Integer>();
		String checkResult = Validation.checkBean(wishBean);
		logger.debug("checkResult", checkResult);
		if (Validation.BEAN_CHECK_SUCCESS.equals(checkResult)) {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			logger.debug("UserInfo", userInfo);
			if (userInfo == null) {
				msg.setIsSuccess(false);
				msg.setMessage("请先登录！");
				msg.setData(-1);
				return msg;
			}
			// 加入UserId
			wishBean.setUserId(userInfo.getUserId());
			// 上传至服务器
			wishBll.uploadWish(wishBean);
			// 修改返回消息
			msg.setIsSuccess(true);
			msg.setMessage("您已经成功提交，祝福在审核后会展示在前台!");
			msg.setData(1);
		} else if (checkResult.equals("-2")) {
			msg.setIsSuccess(false);
			msg.setMessage("祝福语不能为空！");
			msg.setData(-2);
		} else if (checkResult.equals("-3")) {
			msg.setIsSuccess(false);
			msg.setMessage("祝福语超过10个字！");
			msg.setData(-3);
		} else if (checkResult.equals("-4")) {
			msg.setIsSuccess(false);
			msg.setMessage("图片不能为空！");
			msg.setData(-4);
		}
		logger.debug("msg", msg);
		return msg;
	}

	/**
	 * 分页查询wendao_nineyear_wish_tb表
	 * 
	 * @日期：2015年3月13日
	 * @Title: selectByPage
	 * @param page
	 *            页数
	 * @param checkStatus
	 *            状态
	 * @return 对应的WishBean集合列表，默认未审查的第一页数据 List<WishBean>
	 */
	public List<WishBean> selectByPage(Integer page, String checkStatus) {
		logger.debug("page", page);
		logger.debug("checkStatus", checkStatus);
		// 设置相关参数查询page对应页的信息列表
		Map<String, String> map = new HashMap<String, String>();
		if (page != null) {
			map.put("page", (page - 1) + "");
		} else {
			map.put("page", "0");
		}
		map.put("checkStatus", checkStatus);
		logger.debug("map", map);
		List<WishBean> wishBeanList = wishBll.selectByPage(map);
		logger.debug("wishBeanList", wishBeanList);
		// 转换时间格式
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd mm:hh:ss");
		for (int i = 0; wishBeanList != null && i < wishBeanList.size(); i++) {
			String wishTimeStr = dateFormat.format(wishBeanList.get(i)
					.getWishTime());
			logger.debug("wishTimeStr", wishTimeStr);
			wishBeanList.get(i).setStrWishTime(wishTimeStr);
		}
		logger.debug("wishBeanList", wishBeanList);
		return wishBeanList;
	}

	/**
	 * 根据code修改审核状态
	 * 
	 * @日期：2015年3月13日
	 * @Title: updateWishStatus
	 * @param code
	 *            审核code
	 * @param checkStatus
	 *            审核状态 void
	 */
	public void updateWishStatus(Integer code, String checkStatus) {
		logger.debug("code", code);
		logger.debug("checkStatus", checkStatus);
		WishBean wishBean = new WishBean();
		if (code == null || checkStatus == null) {
			return;
		}
		wishBean.setCode(code);
		wishBean.setCheckStatus(checkStatus);
		wishBll.updateWishStatus(wishBean);
	}

	/**
	 * 查询最新20条数据
	 * 
	 * @日期：2015年3月13日
	 * @Title: selectWishByNew
	 * @return 最新20条数据集合 List<WishBean>
	 */
	public List<WishBean> selectWishByNew() {
		List<WishBean> wishBeanList = wishBll.selectWishByNew();
		logger.debug("wishBeanList", wishBeanList);
		// 未满20条数据可以重复显示
		if (wishBeanList != null && wishBeanList.size() < 20) {
			int index = 0;
			for (int i = wishBeanList.size(); i < 20; i++) {
				wishBeanList.add(wishBeanList.get(index));
			}
		}
		logger.debug("wishBeanList", wishBeanList);
		return wishBeanList;
	}

	/**
	 * 随机查询wish
	 * 
	 * @return List<WishBean>
	 * */
	public List<WishBean> searchRandomWish() {
		// 初始化wishList
		List<WishBean> wishList = new ArrayList<WishBean>();
		// 随机数的上限范围（通过得到祝福的最大code值）
		int randomUpLimit = wishBll.getMaxWishCode();
		logger.debug("randomUpLimit", randomUpLimit);
		int num;
		if (wishBll.getWishCount() <= 20) {
			num = wishBll.getWishCount();
		} else {
			num = 20;
		}
		logger.debug("num", num);
		// 声明一个wish的Set集合
		RandomUtil rand = new RandomUtil(randomUpLimit);
		logger.debug("rand", rand);
		String url = "";
		while (wishList.size() < num) {
			// 得到wishBean
			int tempCode = rand.randomCode(randomUpLimit);
			WishBean temp = wishBll.getWishByCode(tempCode);
			logger.debug("tempCode", tempCode);
			logger.debug("temp", temp);
			// 如果temp不等于空则加入集合中
			if (temp != null) {
				url = temp.getPictureURL();
				if (url.lastIndexOf(".") != -1) {
					int pointIndex = url.lastIndexOf(".");
					url = url.substring(0, pointIndex) + "-small"
							+ url.substring(pointIndex, url.length());
					temp.setSmallPictureURL(url);
				}
				wishList.add(temp);
			}
		}

		logger.debug("wishList", wishList);
		// 将Set中的元素迭代到List中
		return wishList;
	}

	/**
	 * 查询20张最新
	 * 
	 * @日期：2015年3月13日
	 * @Title: searchNewomWish
	 * @return 20张最新 List<WishBean>
	 */
	public List<WishBean> searchNewomWish() {
		// 获得WishList
		List<WishBean> wishList = wishBll.selectWishByNew();
		logger.debug("wishList", wishList);
		// 最终返回结果
		List<WishBean> wishResult = new ArrayList<WishBean>();
		logger.debug("wishResult", wishResult);
		String url = "";
		// 加上小图片的路径
		for (WishBean temp : wishList) {
			url = temp.getPictureURL();
			if (url.lastIndexOf(".") > 0) {
				int pointIndex = url.lastIndexOf(".");
				url = url.substring(0, pointIndex) + "-small"
						+ url.substring(pointIndex, url.length());
				temp.setSmallPictureURL(url);
			}
			wishResult.add(temp);
		}
		logger.debug("wishResult", wishResult);
		return wishResult;
	}
}
