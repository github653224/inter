package com.testingedu.common;

import java.util.Map;

/**
 * Title: Users Description: 用来处理数据库结果并供接口调用的类 主要是用户信息查询和注册的功能
 * 
 * @author Will
 */

public class Users {

	public String Register(String t, String name, String pwd, String nickname, String describe) {
		if (t == null || Token.getToken(t) == null) {
			return "{\"status\":405,\"msg\":\"非法请求\"}";
		}
		if (Token.getToken(t) == "null") {
			if (describe == null) {
				describe = "";
			}
			if (checkParam(name, 3, 17) && checkParam(pwd, 3, 17) && checkParam(nickname, 3, 37)
					&& checkParam(describe, 0, 128)) {

				if (Token.mysql.checkUser(name)) {
					return "{\"status\":401,\"msg\":\"用户已被注册\"}";
				}

				if (Token.mysql.PRegister(name, pwd, nickname, describe) == 0)
					return "{\"status\":400,\"msg\":\"参数错误\"}";
				else
					return "{\"status\":200,\"msg\":\"注册成功\"}";
			} else {
				return "{\"status\":402,\"msg\":\"参数错误\"}";
			}
		} else {
			return "{\"status\":406,\"msg\":\"登录状态，不能注册\"}";
		}
	}

	public String getUserInfo(String token, String id) {
		if (token == null || Token.getToken(token) == null) {
			return "{\"status\":405,\"msg\":\"非法请求\"}";
		}
		Token.updateToken(token);
		if (Token.getToken(token) == "null") {
			return "{\"status\":401,\"msg\":\"您还未登录\"}";
		}
		if (!checkParam(id, 1, 11)) {
			return "{\"status\":402,\"msg\":\"参数错误！\"}";
		} else {
			int i = 0;
			try {
				i = Integer.parseInt(id);
			} catch (Exception e) {
			}
			if (i == 0) {
				return "{\"status\":402,\"msg\":\"参数错误！\"}";
			}
		}
		if (Token.updateToken(token)) {
			if (!id.equals(Token.getToken(token))) {
				return "{\"status\":404,\"msg\":\"非法查询\"}";
			}
			Map<String, String> map = Token.mysql.getUserInfo(id);
			String res = "";
			if (map != null) {
				res = "{\"status\":200,\"msg\":\"查询成功\"";
				for (String key : map.keySet()) {
					res += ",\"" + key + "\":\"" + map.get(key) + "\"";
				}
			} else {
				// 理论上不会出现
				res = "{\"status\":402,\"msg\":\"参数错误\"";
			}
			return res + "}";
		} else {
			return "{\"status\":406,\"msg\":\"token已失效\"}";
		}
	}

	private boolean checkParam(String str, int l, int r) {

		if (str != null && str.length() >= l && str.length() < r) {
			return true;
		}

		return false;
	}
}
