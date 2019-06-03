package com.bb.billingsystem.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CommonDAO {
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		ResourceBundle rb = ResourceBundle.getBundle("db");
		Connection con = null;
		Class.forName(rb.getString("driver"));
		System.out.println("Driver Loaded..");
		// Create Connection to Database
		/*
		 * jdbc:postgresql://hostname:port/dbname
		 * jdbc:mysql://localhost:3306/javabase
		 * jdbc:oracle:thin:@localhost:1521:SID
		 */
		
		con = DriverManager.getConnection(rb.getString("url"),rb.getString("userid"),rb.getString("password"));
		return con;
	}
}
