package com.testingedu.mysql;

import java.sql.*;

//用来连接mysql的
public class Mysql {
	// 可以用来操作数据
	public Connection ct;

	public Mysql() { // Mysql地址配置
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 创建一个连接
			new property();
			System.out.println(property.mysqlPath);
			ct = DriverManager.getConnection(property.mysqlPath, property.mysqlu, property.mysqlp);
			// 设置超时时间
			DriverManager.setLoginTimeout(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
