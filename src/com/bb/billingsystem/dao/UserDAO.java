package com.bb.billingsystem.dao;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.bb.billingsystem.utils.AdminConstants;


public class UserDAO {

	public boolean doLogin(String userid, String password) throws ClassNotFoundException, SQLException {
		boolean isUserExist = false;
		
		Connection con = null;  
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResourceBundle rb = ResourceBundle.getBundle("db");
		String dbClass = rb.getString("dbtype");
		try {
			try {
			Object object = Class.forName(dbClass.trim()).getDeclaredConstructor().newInstance();
			Method method = object.getClass().getMethod(rb.getString("dbmethod").trim(), null);
			con = (Connection)method.invoke(object, null);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		if (con!=null) {
			pstmt = con.prepareStatement("select userid, password from UserRecords where userid=? and password=?");
			pstmt.setString(1, userid); 
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				isUserExist = true;
				System.out.println("Welcome "+userid);
					}
			else {
				System.out.println("Invalid userid or password");
				}
			}
		}
		finally {
			if(rs!=null) {
			rs.close();
			}
			if(pstmt!=null) {
			pstmt.close();
			}
			if(con!=null) {
			con.close();
			}
		}
		return isUserExist;
	}
	
	public boolean doRegister(String userid, String password, String email) throws ClassNotFoundException, SQLException {
		boolean isUserExist = false;
		Connection con = null;  
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResourceBundle rb = ResourceBundle.getBundle("db");
		String dbClass = rb.getString("dbtype");
		try {
			try {
			Object object = Class.forName(dbClass.trim()).getDeclaredConstructor().newInstance();
			Method method = object.getClass().getMethod(rb.getString("dbmethod").trim(), null);
			con = (Connection)method.invoke(object, null);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		if (con!=null) {
			pstmt = con.prepareStatement("insert into UserRecords VALUES(?,?,?,'N')");
			pstmt.setString(1, userid); 
			pstmt.setString(2, password);
			pstmt.setString(3, email);
			pstmt.executeUpdate();
			isUserExist=true;
			}
		}
		finally {
			if(rs!=null) {
			rs.close();
			}
			if(pstmt!=null) {
			pstmt.close();
			}
			if(con!=null) {
			con.close();
			}
		}
		return isUserExist;
	}

	public void getDetails(String userid) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = null;  
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResourceBundle rb = ResourceBundle.getBundle("db");
		String dbClass = rb.getString("dbtype");
		try {
			try {
			Object object = Class.forName(dbClass.trim()).getDeclaredConstructor().newInstance();
			Method method = object.getClass().getMethod(rb.getString("dbmethod").trim(), null);
			con = (Connection)method.invoke(object, null);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		if (con!=null) {
			pstmt = con.prepareStatement("Select userid, password, email from UserRecords where userid = ?");
			pstmt.setString(1,userid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String username = rs.getString("userid");
		        String pwd = rs.getString("password");
		        String mail = rs.getString("email");
				System.out.println("Welcome "+userid);
				AdminConstants ad =new AdminConstants();
				ad.setUserid(username);
				ad.setPassword(pwd);
				ad.setEmail(mail);
				}
			else {
				System.out.println("Invalid userid or password");
				}
			}
		}
		finally {
			if(rs!=null) {
			rs.close();
			}
			if(pstmt!=null) {
			pstmt.close();
			}
			if(con!=null) {
			con.close();
			}
		}
	}
}
