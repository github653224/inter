package com.testingedu.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testingedu.common.Users;

/**
 * Servlet implementation class login
 */
@WebServlet("/HTTP/register")
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public register() {
		super();
		// TODO Auto-generated constructor stub
	}

	// /**
	// * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	// response)
	// */
	// protected void doGet(HttpServletRequest request, HttpServletResponse
	// response) throws ServletException, IOException {
	// // TODO Auto-generated method stub
	//// super.doGet(request, response);
	// doPost(request,response);
	//// response.getWriter().append("Served at:
	// ").append(request.getContextPath());
	// }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 返回值编码
		response.setContentType("text/html;charset=UTF-8");
		// 收到的参数编码
		request.setCharacterEncoding("UTF-8");
		// 获取接口传入的用户名和密码
		String name = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		String nickname = request.getParameter("nickname");
		String describe = request.getParameter("describe");
		String token = request.getHeader("token");
		String res = "";
		Users user = new Users();
		res = user.Register(token, name, pwd, nickname, describe);
		response.getWriter().append(res);
	}

	public static boolean checkParam(String str, int l, int r) {

		if (str != null && str.length() > l && str.length() < r) {
			return true;
		}

		return false;
	}

}
