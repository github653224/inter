package com.testingedu.mysql;

import java.io.FileInputStream;
import java.util.Properties;

public class property {

	public static String path = "";
	public static String url = "";
	private static Properties config = null;
	public static String mysqlPath = "";
	public static String mysqlu = "";
	public static String mysqlp = "";

	public property() {
		config = new Properties();
		url = property.class.getResource("").getPath().replaceAll("%20", " ");
		url = url.substring(1, url.indexOf("classes") + 8);
		path = url + "inter.properties";
		System.out.println("配置文件：" + path);
		mysqlPath = property.readRcErpURL("jdbcUrl");
		mysqlu = property.readRcErpURL("mysqlu");
		mysqlp = property.readRcErpURL("mysqlp");
	}

	public static String readRcErpURL(String name) {
		try {
			config.load(new FileInputStream(path));
			return config.getProperty(name);
		} catch (Exception e1) {
			path = "/" + path;
			try {
				config.load(new FileInputStream(path));
				return config.getProperty(name);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		return null;
	}

}
