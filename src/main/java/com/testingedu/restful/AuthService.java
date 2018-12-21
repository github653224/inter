package com.testingedu.restful;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.testingedu.common.Login;

@Path("/auth")
public class AuthService {
	@Context
	HttpServletRequest request;

	/**
	 * 权限校验和刷新接口 方法用来校验并初始化当前请求
	 * 
	 * @param 无
	 * @return json
	 */
	@POST
	@Produces("text/plain;charset=UTF-8")
	public String auth() {
		// System.out.println(request.getHeader("token"));
		String token = request.getHeader("token");
		Login login = new Login();
		return login.auth(token);
	}
}