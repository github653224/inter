package com.testingedu.common;

public class Login {

	public String auth(String token) {
		String t = "null";
		// token不存在则创建
		if (token == null || Token.getToken(token) == null) {
			t = Token.newToken();
			return "{\"status\":200,\"msg\":\"success\",\"token\":\"" + t + "\"}";
		}
		Token.updateToken(token);
		// token存在，未登录则直接返回
		if (Token.getToken(token).equals("null")) {
			return "{\"status\":201,\"msg\":\"success\"}";
		} else {
			// token存在，未失效则直接返回
			if (Token.updateToken(token)) {
				return "{\"status\":202,\"msg\":\"success\"}";
			} else {
				// token失效则创建
				t = Token.newToken();
				return "{\"status\":200,\"msg\":\"success\",\"token\":\"" + t + "\"}";
			}
		}
	}

	public String login(String name, String pwd, String t) {
		if (t == null || Token.getToken(t) == null) {
			return "{\"status\":405,\"msg\":\"非法请求\"}";
		}
		if (Token.checkUser(name)) {
			return "{\"status\":406,\"msg\":\"用户已经在别处登录\"}";
		}
		if (checkParam(name, 2, 17) && checkParam(pwd, 2, 17)) {
			String res = "{";
			String id = Token.mysql.PLogin(name, pwd);
			if (id != null) {
				Token.setToken(id, name, t);
				res += "\"status\":200,\"msg\":\"恭喜您，登录成功\",\"userid\":\"" + id + "\"}";
			} else {
				res += "\"status\":401,\"msg\":\"用户名密码错误\"}";
			}
			return res;
		} else {
			return "{\"status\":402,\"msg\":\"参数错误\"}";
		}

	}

	public String logout(String token) {
		if (token == null || Token.getToken(token) == null) {
			return "{\"status\":405,\"msg\":\"非法请求\"}";
		}
		Token.delToken(token);
		return "{\"status\":200,\"msg\":\"用户已退出登录\"}";
	}

	private boolean checkParam(String str, int l, int r) {

		if (str != null && str.length() > l && str.length() < r) {
			return true;
		}

		return false;
	}
}
