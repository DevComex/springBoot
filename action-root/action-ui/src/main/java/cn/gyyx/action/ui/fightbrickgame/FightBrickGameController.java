package cn.gyyx.action.ui.fightbrickgame;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.fightbrickgame.Constants;
import cn.gyyx.action.beans.fightbrickgame.FightBrickGameBean;
import cn.gyyx.action.beans.lottery.vo.LotteryPrizesVO;
import cn.gyyx.action.bll.fightbrickgame.FightBrickGameBLL;
import cn.gyyx.action.common.beans.ResultBean;
import cn.gyyx.action.service.BaseController;
import cn.gyyx.action.service.fightbrickgame.FightBrickGameService;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

import com.google.common.base.Throwables;

/**
 * 微信h5小游戏接口
 * <p>
 * FightBrickGameController描述
 * </p>
 * 
 * @author yangteng
 * @since 0.0.1
 */
@Controller
@RequestMapping("/fightbrickgame")
public class FightBrickGameController extends BaseController {

    private static final Logger logger = GYYXLoggerFactory
            .getLogger(FightBrickGameController.class);

    private FightBrickGameBLL fightBrickGameBll = new FightBrickGameBLL();

    private FightBrickGameService fightBrickGameService = new FightBrickGameService();

    /**
     * 记录游戏得分
     * 
     * @return
     */
    @RequestMapping(value = "/record/score", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<String> addScore(HttpServletRequest request) {

        ResultBean<String> result = new ResultBean<String>();

        try {
            ResultBean<Map<String, String>> verifyResult = addScoreParamVerify(request);
            if (!verifyResult.getIsSuccess()) {
                return result;
            }

            Map<String, String> map = verifyResult.getData();
            String encrypt = map.get("encrypt");
            String score = map.get("score");
            String openid = map.get("openid");
            String key = null;

            Cookie[] cookies = request.getCookies();
            for (Cookie item : cookies) {
                if ("guid".equals(item.getName())) {
                    key = item.getValue();
                    break;
                }
            }

            // 记录分数
            return fightBrickGameService.addScore(openid, encrypt,
                Integer.parseInt(score), key);

        } catch (Exception ex) {
            logger.error("h5小游戏记录游戏得分异常{}", ex);
            result.setMessage("记录游戏得分异常");
            return result;
        }
    }

    /**
     * h5小游戏生成唯一标识 yangteng
     * 
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/identify", method = { RequestMethod.GET })
    @ResponseBody
    public ResultBean<String> getIdentify(HttpServletRequest request,
            HttpServletResponse response) {
        ResultBean<String> result = new ResultBean<String>();

        try {
            ResultBean<String> verifyResult = commonVerify(request);
            if (!verifyResult.getIsSuccess()) {
                result.setMessage(verifyResult.getMessage());
                return result;
            }

            // 生成标识
            List<String> list = fightBrickGameService.getIdentify();

            Cookie cookie = new Cookie("guid", list.get(0));
            cookie.setMaxAge(Constants.COOKIE_EXPIRE_MINUTE);
            response.addCookie(cookie);

            result.setMessage("查询成功");
            result.setIsSuccess(true);
            result.setData(list.get(1));
            return result;

        } catch (Exception ex) {
            logger.error("h5小游戏生成唯一标识异常,错误堆栈:{}",
                Throwables.getStackTraceAsString(ex));
            result.setMessage("获取失败");
            return result;
        }
    }

    /**
     * 查询排行
     * 
     * @param request
     */
    @RequestMapping(value = "/rank", method = { RequestMethod.GET })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getRank(
            HttpServletRequest request) {
        ResultBean<List<Map<String, Object>>> result = new ResultBean<List<Map<String, Object>>>();

        try {
            ResultBean<String> verifyResult = commonVerify(request);
            if (!verifyResult.getIsSuccess()) {
                result.setMessage(verifyResult.getMessage());
                return result;
            }

            List<FightBrickGameBean> list = fightBrickGameBll.getRank();
            if (list.isEmpty()) {
                result.setMessage("暂无数据");
                return result;
            }

            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            for (FightBrickGameBean item : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("nickName", item.getNickName());
                map.put("rank", item.getRank());
                map.put("score", item.getHighScore());
                mapList.add(map);
            }

            result.setIsSuccess(true);
            result.setMessage("查询成功");
            result.setData(mapList);
            return result;

        } catch (Exception ex) {
            logger.error("h5小游戏查询排行异常,错误堆栈:{}",
                Throwables.getStackTraceAsString(ex));
            result.setMessage("查询排行失败");
            return result;
        }
    }

    /**
     * 查询中奖列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/award/list", method = { RequestMethod.GET })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getAwardList(
            HttpServletRequest request) {

        ResultBean<List<Map<String, Object>>> result = new ResultBean<List<Map<String, Object>>>();
        try {
            ResultBean<String> verifyResult = commonVerify(request);
            if (!verifyResult.getIsSuccess()) {
                result.setMessage(verifyResult.getMessage());
                return result;
            }

            //查询活动配置信息
            ResultBean<Map<String,Object>> hdResult=fightBrickGameService.getHdConfigInfo(Constants.ACTION_CODE);
            if(!hdResult.getIsSuccess()){
                result.setMessage("查询活动配置信息失败");
                return result;
            }
            
            Map<String,Object> hdMap=hdResult.getData();
            Date hdEnd=(Date) hdMap.get("hdEnd");
            Date now=new Date();
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List<FightBrickGameBean> giftList = null;
            if (hdEnd.getTime() < now.getTime()) {
                giftList = fightBrickGameBll.getGiftList(Constants.ACTION_CODE, sdf.format(hdEnd));
            }

            //查询活动时间内中奖列表
            List<FightBrickGameBean> list = fightBrickGameBll.getAwardList(Constants.ACTION_CODE, sdf.format(hdEnd));            
            
            List<FightBrickGameBean> newList = new ArrayList<FightBrickGameBean>();
            boolean isGiftEmpty=giftList == null || giftList.isEmpty();
            
            if (isGiftEmpty && list.isEmpty()) {
                result.setMessage("暂无数据");
                return result;
            } else if (isGiftEmpty && !list.isEmpty()) {
                newList = list;
            } else if (!isGiftEmpty && list.isEmpty()) {
                newList = giftList;
            } else {
                for (FightBrickGameBean item : list) {
                    giftList.add(item);
                }
                newList = giftList;
            }           

            List<Map<String, Object>> awards = new ArrayList<Map<String, Object>>();
            for (FightBrickGameBean item : newList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("nickName", item.getNickName());
                map.put("drawTime",
                    item.getCreateTime().substring(5).replace("-", "."));
                map.put("prizeName", item.getPrizeName());
                awards.add(map);
            }

            result.setIsSuccess(true);
            result.setMessage("查询成功");
            result.setData(awards);
            return result;

        } catch (Exception ex) {
            logger.error("查询中奖列表异常,错误消息:{}",
                Throwables.getStackTraceAsString(ex));
            result.setMessage("查询中奖列表失败");
            return result;
        }
    }

    /**
     * 游戏结束查询最新得分，当天最高得分，历史最高得分，当天排行
     * 
     * @param resquest
     */
    @RequestMapping(value = "/stat", method = { RequestMethod.GET })
    @ResponseBody
    public ResultBean<Map<String, Object>> getStatInfo(
            HttpServletRequest request) {
        ResultBean<Map<String, Object>> result = new ResultBean<Map<String, Object>>();

        try {
            ResultBean<String> verifyResult = commonVerify(request);
            if (!verifyResult.getIsSuccess()) {
                result.setMessage(verifyResult.getMessage());
                return result;
            }

            String openid = verifyResult.getData();
            return fightBrickGameService.getStatInfo(openid,
                Constants.ACTION_CODE);
        } catch (Exception ex) {
            logger.error("查询玩家得分信息失败,错误消息:{}",
                Throwables.getStackTraceAsString(ex));
            result.setMessage("查询玩家得分信息失败");
            return result;
        }
    }

    /**
     * 查询个人奖励
     * 
     * @param request
     */
    @RequestMapping(value = "/award", method = { RequestMethod.GET })
    @ResponseBody
    public ResultBean<Map<String, Object>> getAward(HttpServletRequest request) {
        ResultBean<Map<String, Object>> result = new ResultBean<Map<String, Object>>();

        try {
            ResultBean<String> verifyResult = commonVerify(request);
            if (!verifyResult.getIsSuccess()) {
                result.setMessage(verifyResult.getMessage());
                return result;
            }

            String openid = verifyResult.getData();
            // 查询个人奖励
            return fightBrickGameService
                    .getAward(openid, Constants.ACTION_CODE);
        } catch (Exception ex) {
            logger.error("查询玩家得分信息失败,错误消息:{}",
                Throwables.getStackTraceAsString(ex));
            result.setMessage("查询玩家得分信息失败");
            return result;
        }
    }

    /**
     * 查询游戏服务器列表
     * 
     * @param request
     */
    @RequestMapping(value = "/server/list", method = { RequestMethod.GET })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> getServerList(
            HttpServletRequest request) {
        ResultBean<List<Map<String, Object>>> result = new ResultBean<List<Map<String, Object>>>();

        try {
            ResultBean<String> verifyResult = commonVerify(request);
            if (!verifyResult.getIsSuccess()) {
                result.setMessage(verifyResult.getMessage());
                return result;
            }

            String netType = request.getParameter("netType");
            if (netType == null || "".equals(netType)) {
                result.setMessage("缺少参数netType");
                return result;
            }

            if (!netType.matches("^[1-3]$")) {
                result.setMessage("参数netType格式不正确");
                return result;
            }

            // 查询游戏服务器列表
            return fightBrickGameService.getServerList(netType,
                Constants.ACTION_CODE);
        } catch (Exception ex) {
            logger.error("查询游戏服务器列表失败,错误消息:{}",
                Throwables.getStackTraceAsString(ex));
            result.setMessage("查询游戏服务器列表失败");
            return result;
        }
    }

    /**
     * 领取虚拟物品奖励
     * 
     * @param request
     */
    @RequestMapping(value = "/present/virtual/receive",
        method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<String> receiveVirtualPresent(HttpServletRequest request) {
        ResultBean<String> result = new ResultBean<String>();

        try {
            ResultBean<Map<String, String>> verifyResult = virtualPresentParamVerify(request);
            if (!verifyResult.getIsSuccess()) {
                result.setMessage(verifyResult.getMessage());
                return result;
            }

            Map<String, String> map = verifyResult.getData();
            String netType = map.get("netType");
            String serverId = map.get("serverId");
            String ip = getIp(request);
            String openid = map.get("openid");
            String account = map.get("account");

            // 领取虚拟物品奖励
            return fightBrickGameService.receiveVirtualPresent(
                Constants.ACTION_CODE, account, openid, netType,
                Integer.parseInt(serverId), ip);
        } catch (Exception ex) {
            logger.error("领取虚拟物品失败,错误消息:{}",
                Throwables.getStackTraceAsString(ex));
            result.setMessage("领取虚拟物品失败");
            return result;
        }
    }

    /**
     * 领取实物奖品
     * 
     * @param request
     */
    @RequestMapping(value = "/present/real/receive",
        method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<String> receiveRealPresent(HttpServletRequest request) {
        ResultBean<String> result = new ResultBean<String>();

        try {
            ResultBean<Map<String, String>> verifyResult = realPresentParamVerify(request);
            if (!verifyResult.getIsSuccess()) {
                result.setMessage(verifyResult.getMessage());
                return result;
            }

            Map<String, String> map = verifyResult.getData();
            String netType = map.get("netType");
            String serverId = map.get("serverId");
            String name = map.get("name");
            String location = map.get("location");
            String phone = map.get("phone");
            String account = map.get("account");
            String openid = map.get("openid");

            LotteryPrizesVO vo = new LotteryPrizesVO();
            vo.setActivityId(Constants.ACTION_CODE);
            vo.setUserName(name);
            vo.setUserPhone(phone);
            vo.setUserAddress(location);
            vo.setUserId(account);

            String ip = getIp(request);

            // 领取虚拟物品奖励
            return fightBrickGameService.receiveRealPresent(
                Constants.ACTION_CODE, account, openid, netType,
                Integer.parseInt(serverId), ip, vo);
        } catch (Exception ex) {
            logger.error("领取虚拟物品失败,错误消息:{}",
                Throwables.getStackTraceAsString(ex));
            result.setMessage("领取虚拟物品失败");
            return result;
        }
    }

    /**
     * 查询玩家的昵称，最高得分，最佳排名（分享页显示）
     * 
     * @param request
     */
    @RequestMapping(value = "/share", method = { RequestMethod.GET })
    @ResponseBody
    public ResultBean<Map<String, Object>> getShare(HttpServletRequest request) {
        ResultBean<Map<String, Object>> result = new ResultBean<Map<String, Object>>();

        try {
            ResultBean<String> commonResult = commonVerify(request);
            if (!commonResult.getIsSuccess()) {
                result.setMessage(commonResult.getMessage());
                return result;
            }

            String openid = commonResult.getData();

            // 查询玩家的微信昵称，最高得分，最佳排名
            return fightBrickGameService.getShare(openid);
        } catch (Exception ex) {
            logger.error("查询分享页显示信息失败,错误消息:{}",
                Throwables.getStackTraceAsString(ex));
            result.setMessage("查询分享页显示信息失败");
            return result;
        }
    }

    /**
     * 
      * <p>
      *    h5小游戏的配置信息
      * </p>
      *
      * @action
      *    yangteng 2017年3月29日 下午2:40:53 描述
      *
      * @return String
     */
    @RequestMapping(value="/game/config",method={RequestMethod.GET})
    @ResponseBody
    public String getH5GameConfig(){
        String str="{\"showFPS\": false, \"frameRate\": 60, \"project_type\": \"javascript\", \"debugMode\": 1, \"renderMode\": 0, \"id\": \"gameCanvas\"}";
        return str;
    }
    
    /**
     * 
      * <p>
      *    记录分享日志
      * </p>
      *
      * @action
      *    yangteng 2017年4月7日 下午1:41:55 描述
      *
      * @param request
      * @return Object
     */
    @RequestMapping(value="/share/add", method={RequestMethod.POST})
    @ResponseBody
    public ResultBean<String> addShareLog(HttpServletRequest request){
        ResultBean<String> result=new ResultBean<String>();
        
        try {
            ResultBean<String> commonResult = commonVerify(request);
            if (!commonResult.getIsSuccess()) {
                result.setMessage(commonResult.getMessage());
                return result;
            }

            String openid = commonResult.getData();
            String ip=getIp(request);
            
            // 记录分享日志
            return fightBrickGameService.addShareLog(Constants.ACTION_CODE, openid, ip);
        } catch (Exception ex) {
            logger.error("记录分享日志异常,错误消息:{}",
                Throwables.getStackTraceAsString(ex));
            result.setMessage("记录分享日志异常");
            return result;
        }
    }
    
    /**
     * 
     * <p>
     * 领取实物奖品的参数验证
     * </p>
     *
     * @action yangteng 2017年3月24日 下午6:54:22 描述
     *
     * @param userInfo
     * @param netType
     * @param serverId
     * @param name
     * @param address
     * @param phone
     * @param request
     * @return
     * @throws Exception
     *             ResultBean<String>
     */
    private ResultBean<Map<String, String>> realPresentParamVerify(
            HttpServletRequest request) throws Exception {
        ResultBean<Map<String, String>> result = new ResultBean<Map<String, String>>();

        ResultBean<Map<String, String>> commonResult = virtualPresentParamVerify(request);
        if (!commonResult.getIsSuccess()) {
            result.setMessage(commonResult.getMessage());
            return result;
        }

        String name = request.getParameter("name");
        if (name == null || "".equals(name)) {
            result.setMessage("请填写姓名");
            return result;
        }

        if (name.getBytes("GBK").length > 10) {
            result.setMessage("姓名的长度不能大于5个汉字");
            return result;
        }

        String location = request.getParameter("address");
        if (location == null || "".equals(location)) {
            result.setMessage("请填写地址");
            return result;
        }

        if (location.getBytes("GBK").length > 200) {
            result.setMessage("地址的长度不能大于100个汉字");
            return result;
        }

        String phone = request.getParameter("phone");
        if (phone == null || "".equals(phone)) {
            result.setMessage("请填写手机号");
            return result;
        }

        if (phone.getBytes().length > 11) {
            result.setMessage("手机号的长度不能大于11个字符");
            return result;
        }

        if (!phone.matches("^1[3|4|5|8|7][0-9]\\d{8}$")) {
            result.setMessage("手机号的格式不正确");
            return result;
        }

        Map<String, String> map = commonResult.getData();
        map.put("name", name);
        map.put("location", location);
        map.put("phone", phone);

        result.setData(map);
        result.setIsSuccess(true);
        return result;
    }

    /**
     * 
     * <p>
     * 领取虚拟物品的参数验证
     * </p>
     *
     * @action yangteng 2017年3月24日 下午6:42:22 描述
     *
     * @param netType
     *            大区
     * @param serverId
     *            服务器ID
     * @param request
     * @return
     * @throws Exception
     *             ResultBean<String>
     */
    private ResultBean<Map<String, String>> virtualPresentParamVerify(
            HttpServletRequest request) throws Exception {
        ResultBean<Map<String, String>> result = new ResultBean<Map<String, String>>();

        ResultBean<String> commonResult = commonVerify(request);
        if (!commonResult.getIsSuccess()) {
            result.setMessage(commonResult.getMessage());
            return result;
        }

        // 验证用户是否登录
        UserInfo userInfo = SignedUser.getUserInfo(request);
        if (null == userInfo) {
            result.setIsSuccess(false);
            result.setMessage("您没有登录！");
            return result;
        }        
        
        String netType = request.getParameter("netType");
        if (netType == null || "".equals(netType)) {
            result.setMessage("缺少参数netType");
            return result;
        }

        if (!netType.matches("^[1-3]$")) {
            result.setMessage("参数netType格式不正确");
            return result;
        }

        String serverId = request.getParameter("serverId");
        if (serverId == null || "".equals(serverId)) {
            result.setMessage("缺少参数serverId");
            return result;
        }

        if (!serverId.matches("^[1-9]\\d*$")) {
            result.setMessage("参数serverId格式不正确");
            return result;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("openid", commonResult.getData());
        map.put("account", userInfo.getAccount());
        map.put("netType", netType);
        map.put("serverId", serverId);

        result.setData(map);
        result.setIsSuccess(true);
        return result;
    }

    /**
     * 
     * <p>
     * 记录分数时参数验证
     * </p>
     *
     * @action yangteng 2017年3月24日 下午5:59:26 描述
     *
     * @return ResultBean<Object>
     */
    private ResultBean<Map<String, String>> addScoreParamVerify(
            HttpServletRequest request) throws Exception {
        ResultBean<Map<String, String>> result = new ResultBean<Map<String, String>>();

        ResultBean<String> commonResult = commonVerify(request);
        if (!commonResult.getIsSuccess()) {
            result.setMessage(commonResult.getMessage());
            return result;
        }

        String encrypt = request.getParameter("encrypt");
        if (encrypt == null || "".equals(encrypt)) {
            result.setMessage("缺少参数encrypt");
            return result;
        }

        String score = request.getParameter("score");
        if (score == null || "".equals(score)) {
            result.setMessage("缺少参数score");
            return result;
        }

        if (!score.matches("^\\d+$")) {
            result.setMessage("参数score格式不正确");
            return result;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("openid", commonResult.getData().toString());
        map.put("encrypt", encrypt);
        map.put("score", score);

        result.setData(map);
        result.setIsSuccess(true);
        return result;
    }

    /**
     * 请求接口时通用验证方法
     * <p>
     * 方法说明
     * </p>
     *
     * @action Administrator 2017年3月24日 下午5:49:48 描述
     *
     * @param request
     * @return
     * @throws Exception
     *             ResultBean<Object>
     */
    private ResultBean<String> commonVerify(HttpServletRequest request)
            throws Exception {
        ResultBean<String> result = new ResultBean<String>();

        // 是否是微信浏览器
        if (!checkIsWeiXin(request)) {
            result.setMessage("在微信客户端打开链接");
            return result;
        }

        // 验证签名
        String openid = valideURLSign(request);
        if (openid == null || "".equals(openid)) {
            result.setMessage("此链接地址无效!");
            return result;
        }

        result.setData(openid);
        result.setIsSuccess(true);
        return result;
    }

    /***
     * 微信请求url 验签名
     */
    private String valideURLSign(HttpServletRequest request) throws Exception {

        String time = request.getParameter("time");

        String sign = request.getParameter("sign");

        String openId = request.getParameter("OpenId");

        String url = "OpenId=" + openId + "&time=" + time;

        logger.info("h5小游戏MD5编码" + MD5.encode(url + Constants.WX_SIGN_KEY));

        if (MD5.encode(url + Constants.WX_SIGN_KEY).equals(sign)) {
            return openId;
        }

        return null;
    }

    /**
     * 验证微信浏览器
     * <p>
     * 方法说明
     * </p>
     *
     * @action yangteng 2017年3月24日 下午5:42:09 描述
     *
     * @param request
     * @return
     * @throws Exception
     *             boolean
     */
    private boolean checkIsWeiXin(HttpServletRequest request) throws Exception {
        try {
            String ua = request.getHeader("user-agent").toLowerCase();
            System.out.println(ua);
            if (ua.indexOf("micromessenger") == -1
                    || ua.indexOf("windows") > -1) {// 不是微信浏览器
                return false;
            }
        } catch (Exception e) {
            logger.error("检查是否是微信浏览器失败,错误堆栈:{}",
                Throwables.getStackTraceAsString(e));
        }
        return true;
    }
}