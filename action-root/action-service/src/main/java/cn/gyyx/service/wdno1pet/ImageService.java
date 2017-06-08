package cn.gyyx.service.wdno1pet;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdno1pet.ImageBean;
import cn.gyyx.action.bll.wdno1pet.ImageBLL;
import cn.gyyx.action.bll.wdno1pet.Validation;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

public class ImageService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ImageService.class);
	private static final ImageBLL imageBLL = new ImageBLL();

	public ResultBean<Integer> uploadImg(HttpServletRequest request,
			ImageBean img) {
		img.setImgStatus("uncheck");
		logger.debug("ImageBean", img);
		ResultBean<Integer> msg = new ResultBean<Integer>();
		String check = Validation.checkBean(img);
		if (check.equals(Validation.BEAN_CHECK_SUCCESS)) {
			UserInfo userInfo;
			try {
				userInfo = SignedUser.getUserInfo(request);
				logger.debug("" + userInfo.getUserId());
				img.setUserCode(userInfo.getUserId());
				int res = imageBLL.uploadImage(img);
				msg.setIsSuccess(true);
				msg.setData(res);
				msg.setMessage("成功");
			} catch (NullPointerException e) {
				logger.warn("[uploadImage]NullPointerException in geting userInfo");
				msg.setIsSuccess(false);
				msg.setMessage("请先登录！");
			}
		} else {
			msg.setIsSuccess(false);
			msg.setMessage(check);
		}
		logger.debug("msg", msg);
		return msg;
	}
}
