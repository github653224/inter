package com.testingedu.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class useMysql {
	private Connection ct;

	// 初始化这个数据库连接对象
	public useMysql(Connection ct1) {
		ct = ct1;
	}

	/**
	 * 1 获取用户信息方法 通过查询语句获取用户信息
	 * 
	 * @param userid
	 * @return map结果集合
	 */
	public Map<String, String> getUserInfo(String id) {
		String sql = "SELECT * FROM userinfo where id='" + id + "';";
		// System.out.println(sql);
		// 保存结果集
		ResultSet rs = null;
		// 创建执行sql的操作状态
		Statement sm;
		try {
			sm = ct.createStatement();
			// 执行查询
			rs = sm.executeQuery(sql);

			// 执行更新，删除，插入操作
			// int rs1 = sm.executeUpdate(sql);
			// rs表示有多少行的内容
			if (rs != null && rs.next()) {
				// 这个集合，表示一行的内容
				ResultSetMetaData rsmd = rs.getMetaData();
				HashMap<String, String> map = new HashMap<String, String>();
				// index从1开始
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					if (!(rsmd.getColumnName(i).equals("pwd") || rsmd.getColumnName(i).equals("username")))
						map.put(rsmd.getColumnName(i), rs.getString(i));
				}
				// 这个功能在登录并没有用到，我只是教同学们怎么使用查询结果
				// System.out.println(map.toString());
				// 如果查询结果不为空，就返回登录成功
				return map;
			}
			sm.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 3 登录方法 通过查询语句校验用户登录信息
	 * 
	 * @param username
	 *            password
	 * @return Boolean 是否登录成功
	 */
	public String Login(String name, String pwd) {
		String sql = "SELECT * FROM userinfo where username='" + name + "' AND pwd='" + pwd + "';";
		// System.out.println(sql);
		// 保存结果集
		ResultSet rs = null;
		// 创建执行sql的操作状态
		Statement sm;
		try {
			sm = ct.createStatement();
			// 执行查询
			rs = sm.executeQuery(sql);

			// 执行更新，删除，插入操作
			// int rs1 = sm.executeUpdate(sql);
			// rs表示有多少行的内容
			// 处理执行结果
			if (rs != null && rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				HashMap<String, String> map = new HashMap<String, String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					map.put(rsmd.getColumnName(i), rs.getString(i));
				}
				// System.out.println(map.toString());
				return map.get("id");
			}
			sm.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 登录方法 通过存储过程校验用户登录信息
	 * 
	 * @param username
	 *            password
	 * @return String userid
	 */
	public String PLogin(String name, String pwd) {
		// 创建变量保存返回结果
		try {
			// 创建调用存储过程的状态
			CallableStatement cm = ct.prepareCall("{call login(?,?)}");
			// 给存储过程添加参数
			cm.setString(1, name);
			// cm.setInt(1, 1);
			cm.setString(2, pwd);
			// 获取执行结果
			ResultSet rs = cm.executeQuery();
			// 处理执行结果
			if (rs != null && rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				HashMap<String, String> map = new HashMap<String, String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					map.put(rsmd.getColumnName(i), rs.getString(i));
				}
				// System.out.println(map.toString());
				return map.get("id");
			}
			cm.close();
			rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 注册前查询用户名是否存在
	 * 
	 * @param userid
	 * @return map结果集合
	 */
	public boolean checkUser(String name) {
		String sql = "SELECT * FROM userinfo where username='" + name + "';";
		// System.out.println(sql);
		// 保存结果集
		ResultSet rs = null;
		// 创建执行sql的操作状态
		Statement sm;
		try {
			sm = ct.createStatement();
			// 执行查询
			rs = sm.executeQuery(sql);

			// 执行更新，删除，插入操作
			// int rs1 = sm.executeUpdate(sql);
			// rs表示有多少行的内容
			if (rs != null && rs.next()) {
				return true;
			}
			sm.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 注册方法 通过存储过程，更新用户注册信息
	 * 
	 * @param username
	 *            password nickname describe
	 * @return int 更新成功的条数
	 */
	public int PRegister(String name, String pwd, String nickname, String describe) {
		// 创建变量保存返回结果,0表示更新失败，1表示更新成功
		int rs = 0;
		try {
			// 创建调用存储过程的状态
			CallableStatement cm = ct.prepareCall("{call register(?,?,?,?)}");
			// 给存储过程添加参数
			cm.setString(1, name);
			// cm.setInt(1, 1);
			cm.setString(2, pwd);
			cm.setString(3, nickname);
			cm.setString(4, describe);
			// 获取执行结果
			rs = cm.executeUpdate();
			// 处理执行结果
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
}
