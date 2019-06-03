package com.bb.billingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CartDAO {
	public void addToCart(int id, String name,double d) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = CommonDAO.getConnection();
		pstmt = con.prepareStatement("Insert into cart VALUES(?,?,?) ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, price = EXCLUDED.price");
		pstmt.setInt(1,id);
		pstmt.setString(2, name);
		pstmt.setDouble(3, d);
		pstmt.executeUpdate();
		pstmt.close();
		con.close();
	}
	
	public void deleteFromCart(int id) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = CommonDAO.getConnection();
		pstmt = con.prepareStatement("delete from cart where id=?");
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
		pstmt.close();
		con.close();
	}
	
	public void deleteAll() throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = CommonDAO.getConnection();
		pstmt = con.prepareStatement("delete from cart");
		pstmt.executeUpdate();
		pstmt.close();
		con.close();
	}
	
}
