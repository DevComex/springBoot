package cn.mahjong.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.mahjong.beans.common.WebUtils;
import cn.mahjong.bll.CaptchaBll;

@Controller
@RequestMapping("/")
public class CaptchaController extends AbstractBaseController {

  /**
   * 日志记录
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaController.class);
  
  @Autowired
  private CaptchaBll captchaBll;

  /**
   * @param response 响应
   * @param request 请求
   * @return void
   * @Title: index
   * @Description: 生成验证码
   */
  @RequestMapping(value = "/captcha/create")
  public void index(@RequestParam(required = false) String bid, HttpServletResponse response,
      HttpServletRequest request) throws IOException {
    LOGGER.info("Capacha Request From {},bid:{}", WebUtils.getIp(request), bid);
    captchaBll.create(bid, response);
  }
}
