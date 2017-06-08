package cn.gyyx.action.service.wdfinalchallenge;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import cn.gyyx.action.beans.wdfinalchallenge.LiveResultBean;
import cn.gyyx.action.beans.wdfinalchallenge.RoomBean;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

public class WdFinalChallengeService {

	public LiveResultBean<RoomBean> getRoomBean(int code) {
		
		try {
			
			HttpResponse response = Request.Get("http://interface.live.gyyx.cn/room/" + code).execute().returnResponse();
			
			if(response.getStatusLine().getStatusCode() == 200){
				ObjectMapper mapper = new ObjectMapper();
				JavaType type = mapper.getTypeFactory().constructParametricType(LiveResultBean.class, RoomBean.class);
				LiveResultBean<RoomBean> roomResult = mapper.readValue(response.getEntity().getContent(), type);
				return roomResult;
			}
			
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		
		return null;
	}

	public Map<String, ? extends Object> feedback(String contact, String text) {
		try {
			HttpResponse response = Request.Post("http://interface.live.gyyx.cn/feedback/")
										.bodyForm(Form.form().add("contact", contact).add("text", text).build())
										.execute().returnResponse();
			if(response.getStatusLine().getStatusCode() == 200){
				return ImmutableMap.of("result", true, "message", "谢谢您的反馈");
			} else {
				return ImmutableMap.of("result", false, "message", "发生错误请重试");
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
			
		return ImmutableMap.of("result", true, "message", "谢谢您的反馈");
	}

}
