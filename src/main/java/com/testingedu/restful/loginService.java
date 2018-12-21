package com.testingedu.restful;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.testingedu.common.Login;
import com.testingedu.common.Users;

@Path("/login")
public class loginService {
	@Context
	HttpServletRequest request;

	/**
	 * 注销接口 方法用来注销当前token用户登录态
	 * 
	 * @param 无
	 * @return json
	 */
	@POST
	@Produces("text/plain;charset=UTF-8")
	public String logout() {
		String t = request.getHeader("token");
		Login login = new Login();
		return login.logout(t);
	}

	/**
	 * 登录接口 方法用来通过用户名密码登录
	 * 
	 * @param 用户名
	 *            密码
	 * @return json
	 */
	@POST
	@Path("/{name}/{pwd}")
	@Produces("text/plain;charset=UTF-8")
	public String login(@PathParam("name") String name, @PathParam("pwd") String pwd) {
		String t = request.getHeader("token");
		Login login = new Login();
		return login.login(name, pwd, t);
	}

	/**
	 * 获取用户信息接口 方法用来获取用户id的用户信息
	 * 
	 * @param 用户id
	 * @return json
	 */
	@POST
	@Path("/{id}")
	@Produces("text/plain;charset=UTF-8")
	public String getUserInfo(@PathParam("id") String id) {
		String t = request.getHeader("token");
		Users user = new Users();
		return user.getUserInfo(t, id);
	}
}