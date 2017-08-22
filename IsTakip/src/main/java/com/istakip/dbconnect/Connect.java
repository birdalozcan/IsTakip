package com.istakip.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
	public static Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//Connection connection = DriverManager.getConnection("jdbc:mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/istakip?useUnicode=true&characterEncoding=utf-8", "adminalbibxJ", "vLXE4CaD-YS_");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/istakip?useUnicode=true&characterEncoding=utf-8", "root", "asdf");
			return connection;
		} catch (Exception ex) {
			System.out.println("Db Connect Exception :" + ex.getMessage());
			return null;
		}
	}

	public static boolean close(Connection c) {
		try {
			c.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
