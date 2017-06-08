package cn.gyyx.action.oa.back.info;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BackServiceInfo {
	public static String backServiceUrlInfo(String oldServicUrl)
			throws IOException {
		try {
			URL url;
			url = new URL(oldServicUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));
			String temp;
			StringBuffer realUrl = new StringBuffer();
			while ((temp = reader.readLine()) != null) {
				realUrl.append(temp);
			}
			reader.close();
			connection.disconnect();
			return realUrl.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "获取真正地址的时候出错";
		}
	}
}
