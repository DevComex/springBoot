package cn.gyyx.action.service.mobile.website;

import java.math.BigInteger;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.TreeSet;

import com.google.common.collect.Sets;

public class GetSign {

	private static String sign(String url, String key) {
		URI uri = URI.create(url);

		String timestamp = "timestamp=" + timestamp();
		TreeSet<String> set = Sets.newTreeSet();
		set.add(timestamp);
		
		boolean hasQuery = uri.getQuery() != null && uri.getQuery().length() > 0;
		if (hasQuery) {
			set.addAll(Arrays.asList(uri.getQuery().split("&")));
		}

		boolean first = true;
		StringBuilder builder = new StringBuilder(uri.getPath());
		for (String pair : set) {
			builder.append(first?"?":"&").append(pair);
			first = false;
		}
		builder.append(key);
		
		return url +(hasQuery?"&":"?")+ timestamp + "&sign_type=MD5&sign="	+ md5(builder.toString());
	}

	private static long timestamp() {
		return new Date().getTime() / 1000;
	}

	private static String md5(String text) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(StandardCharsets.UTF_8.encode(text));
			return String.format("%032x", new BigInteger(1, md5.digest()));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
