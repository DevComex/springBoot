package cn.gyyx.action.ui.wdfinalchallenge;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.ImmutableMap;

import cn.gyyx.action.beans.wdfinalchallenge.LiveResultBean;
import cn.gyyx.action.beans.wdfinalchallenge.RoomBean;
import cn.gyyx.action.service.wdfinalchallenge.WdFinalChallengeService;

@Controller
@RequestMapping("/wdfinalchallenge")
public class WdFinalChallengeController {
	
	private WdFinalChallengeService service = new WdFinalChallengeService();

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(@CookieValue(value = "VUID", required = false)String vUID, Model model, HttpServletResponse response){
		
		if(vUID == null){
			vUID = UUID.randomUUID().toString();
		}
		
		response.addCookie(new Cookie("VUID", vUID));
		
		LiveResultBean<RoomBean> result = service.getRoomBean(1);
		
		if(result == null ){
			model.addAttribute("error", "获取直播间信息失败");
		} else if(!result.isSuccess()){
			model.addAttribute("error", msg(result.getStatus()));
		}else{
			RoomBean room = result.getData();
			model.addAttribute("roomId", room.getCode());
			model.addAttribute("onAir", room.isOnAir());
			model.addAttribute("onlineCount", room.getOnlineCount());
			if(room.isOnAir()){
				model.addAllAttributes(room.getLivePullUrls());
			}
			if(room.getLiveStartTime() != null){
				String startTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(new DateTime(room.getLiveStartTime()));
				model.addAttribute("startTime", startTime);
			}
		}
		
		return "wdfinalchallenge/index";
	}

	private String msg(String status) {
		// TODO Auto-generated method stub
		return status;
	}

	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> feedback(String contact, String text){
		Map<String, ? extends Object> result;
		
		if(contact == null || contact.trim().isEmpty()) {
			result = ImmutableMap.of("result", false, "message", "联系方式为必填项");
		} else if(text == null || text.trim().isEmpty()) {
			result = ImmutableMap.of("result", false, "message", "反馈内容为必填项");
		} else {
			result = service.feedback(contact, text);
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
