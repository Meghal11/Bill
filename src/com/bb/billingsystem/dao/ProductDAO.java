package com.bb.billingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bb.billingsystem.dto.ProductDTO;

public class ProductDAO {
	public boolean bulkAdd(ArrayList<ProductDTO> products) throws ClassNotFoundException, SQLException{
		Connection con = null;
		boolean isSuccess = false;
		PreparedStatement pstmt = null;
		con = CommonDAO.getConnection();
		con.setAutoCommit(false);
		
		pstmt = con.prepareStatement(" Insert into product VALUES(?,?,?,?) ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, price = EXCLUDED.price, quantity = EXCLUDED.quantity;");
		try{
			for(ProductDTO product : products){
			pstmt.setInt(1, product.getId());
			pstmt.setString(2, product.getName());
			pstmt.setInt(3, product.getPrice());
			pstmt.setInt(4, product.getQuantity());
			pstmt.addBatch();
		}
		pstmt.executeBatch();
		con.commit();
		isSuccess = true;
		}
		catch(SQLException e){
			con.rollback();
			isSuccess = false;
		}
		return isSuccess;
	}
}
