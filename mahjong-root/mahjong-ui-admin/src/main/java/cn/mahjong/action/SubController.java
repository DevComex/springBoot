package cn.mahjong.action;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/sub")
@Controller
public class SubController extends AbstractBaseController {

  @RequestMapping(value = {"/", "/index"})
  public String indexView() {
    return "/sub/index";
  }

  @RequestMapping("/edit")
  public String editView() {
    return "/sub/edit";
  }

  @RequestMapping(value = "/block", method = RequestMethod.POST)
  public ResponseEntity<Object> block(int userId, String reason) {
    return null;
  }

  @RequestMapping(value = "/unblock", method = RequestMethod.POST)
  public ResponseEntity<Object> unblock(int userId) {
    return null;
  }

  @RequestMapping("/block/history")
  public ResponseEntity<Object> blockHistory(int userId) {
    return null;
  }

  @RequestMapping("/list")
  public ResponseEntity<Object> list(String account, int pageSize, int pageIndex) {
    return null;
  }
}
