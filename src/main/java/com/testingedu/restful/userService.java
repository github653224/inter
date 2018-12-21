package com.testingedu.restful;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import com.testingedu.common.Users;

import net.sf.json.JSONObject;

@Path("/user/register")
public class userService {
	@Context
	HttpServletRequest request;

	/**
	 * 注册接口 方法用来获取请求的参数，并处理，然后注册
	 * 
	 * @param 用户名
	 *            密码 昵称 描述
	 * @return json
	 */
	@SuppressWarnings("deprecation")
	@POST
	@Produces("text/plain;charset=UTF-8")
	public String register() {
		String params = request.getQueryString();
		JSONObject jsonParams = null;
		try {
			params = URLDecoder.decode(params);
			// System.out.println(params);
			jsonParams = JSONObject.fromObject(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		if (jsonParams == null) {
			return "{\"status\":401,\"msg\":\"参数错误\"}";
		}
		String username = null, pwd = null, nickname = null, describe = null;
		try {
			username = jsonParams.getString("username");
			pwd = jsonParams.getString("pwd");
			nickname = jsonParams.getString("nickname");
			describe = jsonParams.getString("describe");
		} catch (Exception e) {
		}
		String t = request.getHeader("token");
		Users user = new Users();
		return user.Register(t, username, pwd, nickname, describe);
	}
}