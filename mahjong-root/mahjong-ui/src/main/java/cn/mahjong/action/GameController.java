package cn.mahjong.action;

import cn.mahjong.beans.GameUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.service.GameService;

@Controller
@RequestMapping("/game")
public class GameController extends AbstractBaseController {

  @Autowired
  private GameService gameService;

  @RequestMapping("/userInfo")
  public ResponseEntity<Object> userInfo(int gameUserId) {
    if (gameUserId <= 0) {
      return new ResponseEntity<>(new ResultBean<>(false, "参数错误", null), HttpStatus.OK);
    }
    ResultBean<GameUserInfo> resultBean = gameService.getUserInfo(gameUserId);
    return new ResponseEntity<>(resultBean, HttpStatus.OK);
  }
}
