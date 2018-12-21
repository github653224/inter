package com.testingedu.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.testingedu.mysql.useMysql;

/**
 * Title: Token Description: 基于时间戳生成和超时验证 主要是生成和管理token的类 Version:1.0.0
 * 
 * @author Will
 */

public class Token {
	private static Map<String, String> userMap = new HashMap<String, String>();
	private static Map<String, String> tokenuMap = new HashMap<String, String>();
	private static Map<String, String> idMap = new HashMap<String, String>();
	private static Map<String, Long> timeMap = new HashMap<String, Long>();
	private static Map<String, String> tokenMap = new HashMap<String, String>();
	public static useMysql mysql;

	/**
	 * 4 新建token方法 首次访问，生成token
	 * 
	 * @param 无
	 * @return token
	 */
	public static String newToken() {
		String token = UUID.randomUUID().toString().replace("-", "");
		tokenuMap.put(token, "null");
		tokenMap.put(token, "null");
		return token;
	}

	/**
	 * 获取token状态 null为无效token，需要更新；"null"为未登录有效token；用户名为已登录token
	 * 
	 * @param token
	 * @return token对应值
	 */
	public static String getToken(String token) {
		return tokenMap.get(token);
	}

	/**
	 * 登录时设置token 会将token:name；name:token；token:time三组数据放入缓存
	 * 
	 * @param username，token
	 * @return token
	 */
	public static String setToken(String userid, String name, String token) {
		// 保存token的用户信息到缓存
		idMap.put(userid, token);
		tokenMap.put(token, userid);
		userMap.put(name, token);
		tokenuMap.put(token, name);

		// System.out.println(getToken(token));
		long times = System.currentTimeMillis();
		// 记录token的开始时间
		timeMap.put(token, times);
		return token;
	}

	/**
	 * 校验已登录token是否超时 校验超时，超时时间为30分钟；如果已经超时，会删除token对应缓存
	 * 
	 * @param token
	 * @return Boolean 是否超时
	 */
	public static boolean updateToken(String token) {
		long times = System.currentTimeMillis();
		// 判断token是否存在并且没有过期
		if (timeMap.get(token) != null && (times - timeMap.get(token)) < 1800000) {
			timeMap.put(token, times);
			return true;
		} else {
			// 超时则更新
			if (timeMap.get(token) != null && (times - timeMap.get(token)) >= 1800000)
				delToken(token);
		}
		return false;
	}

	/**
	 * 使token失效 当token失效时，删除token对应所有缓存
	 * 
	 * @param token
	 * @return 无
	 */
	public static void delToken(String token) {
		if (tokenMap.get(token) != null) {
			idMap.remove(tokenMap.get(token));
			userMap.remove(tokenuMap.get(token));
			tokenMap.remove(token);
			tokenuMap.remove(token);
			timeMap.remove(token);
		}
	}

	/**
	 * 2 校验当前用户是否有在别处登录 看当前用户是否有对应的token存在
	 * 
	 * @param username
	 * @return Boolean 是否登录
	 */
	public static boolean checkUser(String username) {
		if (userMap.get(username) == null) {
			return false;
		} else {
			return updateUserToken(username);
		}
	}

	public static boolean updateUserToken(String username) {
		long times = System.currentTimeMillis();
		// 判断当前登录的用户是否存在并且没有过期
		if (timeMap.get(userMap.get(username)) != null && (times - timeMap.get(userMap.get(username))) < 1800000) {
			return true;
		} else {
			if (timeMap.get(userMap.get(username)) != null && (times - timeMap.get(userMap.get(username)) >= 1800000))
				delToken(userMap.get(username));
		}
		return false;

	}
}
