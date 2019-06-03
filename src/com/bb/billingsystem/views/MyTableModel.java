package com.bb.billingsystem.views;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.bb.billingsystem.dto.Item;
import com.bb.billingsystem.utils.*;

public class MyTableModel extends AbstractTableModel  {
	
	private static final long serialVersionUID = 1L;
	ArrayList<Item> items;
	String names [] = {"ID","NAME","PRICE"};
	public MyTableModel() throws SQLException {
		this.items =ItemOperations.fillItems();
	}
	
	 public String getColumnName(int column) {
		 return names[column];
	 }
	 
	 public int getRowCount() {
		 return items.size();
	 }
	 
	 public int getColumnCount() {
		 return names.length;
	 }  
   
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Item item = this.items.get(rowIndex);
		if(columnIndex ==0) {
			return item.getId();
		}
		else
		if(columnIndex ==1) {	
			return item.getName();
		}
		else
		if(columnIndex==2) {	
			return item.getPrice();
		}
		return null;
	}
}
