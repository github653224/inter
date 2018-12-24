package com.testingedu.restful;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import com.testingedu.common.Users;

import net.sf.json.JSONObject;

@Path("/user")
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
	@POST
	@Path("/{param}")
	@Produces("text/plain;charset=UTF-8")
	public String register(@PathParam("param") String params) {
		// System.out.println(params);
		JSONObject jsonParams = null;
		try {
			jsonParams = JSONObject.fromObject(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		if(jsonParams==null) {
			return "{\"status\":401,\"msg\":\"参数错误\"}";
		}
			
		String t = request.getHeader("token");
		Users user = new Users();
		return user.Register(t, jsonParams.getString("name"), jsonParams.getString("pwd"),
				jsonParams.getString("nickname"), jsonParams.getString("describe"));
	}
}