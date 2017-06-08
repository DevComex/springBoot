package cn.mahjong.action;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.mahjong.action.form.GameLoginForm;
import cn.mahjong.action.form.GameNewRoomForm;
import cn.mahjong.action.form.GameRegForm;
import cn.mahjong.beans.GameLoginBean;
import cn.mahjong.beans.GameNewRoomBean;
import cn.mahjong.beans.GameRegBean;
import cn.mahjong.beans.common.Result;
import cn.mahjong.bll.GameLoginBll;
import cn.mahjong.bll.GameNewRoomBll;
import cn.mahjong.bll.GameRegBll;

/**
 * <p>
 * StatsController描述
 * </p>
 * 
 * @author
 * @since 0.0.1
 */
@RestController
@RequestMapping("/stats")
public class StatsController extends AbstractBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatsController.class);
    
    @Autowired
    private GameRegBll gameRegBll;
    
    @Autowired
    private GameLoginBll gameLoginBll;
    
    @Autowired
    private GameNewRoomBll gameNewRoomBll;

    @RequestMapping(value = "/whmj/newuser", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewUser(@Valid GameRegForm gameRegForm,BindingResult bindResult,
            HttpServletRequest request,HttpServletResponse response) {
        LOGGER.info("addNewUser start,gameRegForm:{}", gameRegForm.toString());
        try {
            
            String errorMsg = validateData(bindResult);
            if (!StringUtils.isEmpty(errorMsg)) {
              LOGGER.info("params error:{}", errorMsg);
              return new ResponseEntity<>(new Result<>("invalid-params", errorMsg, null), HttpStatus.BAD_REQUEST);
            }
            
            String regex = "^\\d{8}$";
            Matcher matcher = Pattern.compile(regex).matcher(String.valueOf(gameRegForm.getUserId()));
            if (!matcher.matches()) {
              return new ResponseEntity<>(new Result<>("invalid-userId", "用户标识格式不正确", null), HttpStatus.BAD_REQUEST);
            }
            
            GameRegBean gameRegBean = new GameRegBean();
            BeanUtils.copyProperties(gameRegForm, gameRegBean);
            gameRegBean.setCreateTime(new Date());
            gameRegBean.setGameId(1);
            gameRegBean.setRegTime(new Date(gameRegForm.getCreateTime()* 1000));

            if(gameRegBll.add(gameRegBean)){
                return new ResponseEntity<>(new Result<>("success", "成功", null),HttpStatus.OK);
            }
            return new ResponseEntity<>(new Result<>("success", "成功", null),HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("newUser Exception:{}", e);
            return new ResponseEntity<>(new Result<>("failed", "服务器错误", null),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "/whmj/login", method = RequestMethod.POST)
    public ResponseEntity<Object> addLogin(@Valid GameLoginForm gameLoginForm,BindingResult bindResult,
            HttpServletRequest request,HttpServletResponse response) {
        LOGGER.info("addLogin start,gameLoginForm:{}", gameLoginForm.toString());
        try {
            
            String errorMsg = validateData(bindResult);
            if (!StringUtils.isEmpty(errorMsg)) {
              LOGGER.info("params error:{}", errorMsg);
              return new ResponseEntity<>(new Result<>("invalid-params", errorMsg, null), HttpStatus.BAD_REQUEST);
            }
            
            String regex = "^\\d{8}$";
            Matcher matcher = Pattern.compile(regex).matcher(String.valueOf(gameLoginForm.getUserId()));
            if (!matcher.matches()) {
              return new ResponseEntity<>(new Result<>("invalid-userId", "用户标识格式不正确", null), HttpStatus.BAD_REQUEST);
            }
            
            GameLoginBean gameLoginBean = new GameLoginBean();
            BeanUtils.copyProperties(gameLoginForm, gameLoginBean);
            gameLoginBean.setCreateTime(new Date());
            gameLoginBean.setGameId(1);
            gameLoginBean.setLoginTime(new Date(gameLoginForm.getLoginTime()* 1000));

            if(gameLoginBll.add(gameLoginBean)){
                return new ResponseEntity<>(new Result<>("success", "成功", null),HttpStatus.OK);
            }
            return new ResponseEntity<>(new Result<>("success", "成功", null),HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("newUser Exception:{}", e);
            return new ResponseEntity<>(new Result<>("failed", "服务器错误", null),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "/whmj/newroom", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewRoom(@Valid GameNewRoomForm gameNewRoomForm,BindingResult bindResult,
            HttpServletRequest request,HttpServletResponse response) {
        LOGGER.info("addNewRoom start,gameNewRoomForm:{}", gameNewRoomForm.toString());
        try {
            
            String errorMsg = validateData(bindResult);
            if (!StringUtils.isEmpty(errorMsg)) {
              LOGGER.info("params error:{}", errorMsg);
              return new ResponseEntity<>(new Result<>("invalid-params", errorMsg, null), HttpStatus.BAD_REQUEST);
            }
            
            String regex = "^\\d{8}$";
            Matcher matcher = Pattern.compile(regex).matcher(String.valueOf(gameNewRoomForm.getUserId()));
            if (!matcher.matches()) {
              return new ResponseEntity<>(new Result<>("invalid-userId", "用户标识格式不正确", null), HttpStatus.BAD_REQUEST);
            }
            
            GameNewRoomBean gameNewRoomBean = new GameNewRoomBean();
            BeanUtils.copyProperties(gameNewRoomForm, gameNewRoomBean);
            gameNewRoomBean.setCreateTime(new Date());
            gameNewRoomBean.setGameId(1);
            gameNewRoomBean.setNewRoomTime(new Date(gameNewRoomForm.getCreateTime()* 1000));

            if(gameNewRoomBll.add(gameNewRoomBean)){
                return new ResponseEntity<>(new Result<>("success", "成功", null),HttpStatus.OK);
            }
            return new ResponseEntity<>(new Result<>("success", "成功", null),HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("newUser Exception:{}", e);
            return new ResponseEntity<>(new Result<>("failed", "服务器错误", null),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
