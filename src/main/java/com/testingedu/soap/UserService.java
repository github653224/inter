package com.testingedu.soap;

import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.testingedu.common.Login;
import com.testingedu.common.Token;
import com.testingedu.common.Users;
import com.testingedu.mysql.Mysql;
import com.testingedu.mysql.useMysql;

/**
 * Title: UserService Description: 基于jdk1.6以上的javax.jws 发布webservice接口
 * 
 * @WebService － 它是一个注解，用在类上指定将此类发布成一个ws。 Endpoint –
 *             此类为端点服务类，它的方法publish用于将一个已经添加了@WebService注解 对象绑定到一个地址的端口上。
 *             Version:1.0.0
 * @author Will
 */

@WebService
public class UserService {
	@Resource
	WebServiceContext wsctx;

	/**
	 * 构造函数 启动接口服务前初始化数据库、和token缓存
	 * 
	 * @param 无
	 * @return 无
	 */
	public UserService() {
		Mysql m = new Mysql();
		new Token();
		useMysql mysql = new useMysql(m.ct);
		Token.mysql = mysql;
		System.out.println("/*****特斯汀学院******/");
		System.out.println("      接口测试项目                  ");
		System.out.println("      发布成功!        ");
		System.out.println("/*****特斯汀学院******/");
	}

	/**
	 * 权限校验和刷新接口 方法用来校验并初始化当前请求
	 * 
	 * @param 无
	 * @return json
	 */
	public String auth() {
		String token = getHeader();
		Login login = new Login();
		return login.auth(token);
	}

	/**
	 * 登录接口 方法用来通过用户名密码登录
	 * 
	 * @param 用户名
	 *            密码
	 * @return json
	 */
	public String login(String name, String pwd) {
		String t = getHeader();
		Login login = new Login();
		return login.login(name, pwd, t);
	}

	/**
	 * 注销接口 方法用来注销当前token用户登录态
	 * 
	 * @param 无
	 * @return json
	 */
	public String logout() {
		String t = getHeader();
		Login login = new Login();
		return login.logout(t);
	}

	/**
	 * 获取用户信息接口 方法用来获取用户id的用户信息
	 * 
	 * @param 用户id
	 * @return json
	 */
	public String getUserInfo(String id) {
		String t = getHeader();
		Users user = new Users();
		return user.getUserInfo(t, id);
	}

	/**
	 * 注册接口 方法用来获取请求的参数，并处理，然后注册
	 * 
	 * @param 用户名
	 *            密码 昵称 描述
	 * @return json
	 */
	public String register(String name, String pwd, String nickname, String describe) {
		String t = getHeader();
		Users user = new Users();
		if (describe == null)
			describe = "";
		return user.Register(t, name, pwd, nickname, describe);
	}

	/**
	 * 方法上加@WebMentod(exclude=true)后，此方法不被发布； 方法用来获取头里面的token
	 * 
	 * @param 无
	 * @return token
	 */
	@WebMethod(exclude = true)
	public String getHeader() {
		String token = null;
		try {
			MessageContext mctx = wsctx.getMessageContext();
			Map<?, ?> http_headers = (Map<?, ?>) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
			// System.out.println(http_headers.get("token"));
			token = http_headers.get("token").toString();
			token = token.replace("[", "");
			token = token.replace("]", "");
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return token;
	}

	/**
	 * 静态方法不会被发布
	 * 
	 * @param name
	 * @return
	 */
	public static String getString(String name) {
		return "再见！" + name;
	}

	// 通过EndPoint(端点服务)发布一个WebService
	// public static void main(String[] args) {
	// /*
	// * 参数:1,本地的服务地址; 2,提供服务的类;
	// */
	// Mysql m = new Mysql();
	// Token token = new Token();
	// useMysql mysql = new useMysql(m.ct);
	// UserService service = new UserService();
	//
	// Endpoint.publish("http://192.168.2.191:8080/Service/ServiceHello", service);
	// System.out.println("发布成功!");
	// }
}