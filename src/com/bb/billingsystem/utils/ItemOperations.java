package com.bb.billingsystem.utils;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.bb.billingsystem.dto.*;

public class ItemOperations {
	public static ArrayList<Item> fillItems() throws SQLException {
		ArrayList<Item>  items = new ArrayList<>();
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
			pstmt = con.prepareStatement("Select id, name, price from product");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int id1 = rs.getInt("id");
			    String name = rs.getString("name");
			    int price1 = rs.getInt("price");
			    Item item = new Item(id1,name,price1);
			    items.add(item);
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
		return items;
	}
	
	public static ArrayList<Item> fillCartItems() throws SQLException {
		ArrayList<Item>  items = new ArrayList<>();
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
			pstmt = con.prepareStatement("Select id, name, price from cart");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int id1 = rs.getInt("id");
			    String name = rs.getString("name");
			    int price1 = rs.getInt("price");
			    Item item = new Item(id1,name,price1);
			    items.add(item);
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
		return items;
	}
}
