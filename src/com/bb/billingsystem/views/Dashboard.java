package com.bb.billingsystem.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.bb.billingsystem.dao.CartDAO;
import com.bb.billingsystem.dao.ProductDAO;
import com.bb.billingsystem.dto.Item;
import com.bb.billingsystem.dto.ProductDTO;
import com.bb.billingsystem.utils.BundleReader;
import com.bb.billingsystem.utils.CommonConstants;
import com.bb.billingsystem.utils.Email;
import com.bb.billingsystem.utils.ExcelReader;
import com.bb.billingsystem.utils.ItemOperations;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class Dashboard extends JFrame implements CommonConstants {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnNewButton;
	private JButton btnRefresh;
	private JButton btn1NewButton;
	private JButton btn2Refresh;
	private JPanel panel; 
	private JPanel panel2;
	private JPanel panel1;
	private JLabel lLabel;
	private JLabel lLabel1;
	private Boolean added=false;
	private Boolean firstTime = true;
	private JMenu mnOrder;

	private void uploadXLS(){
		firstTime = false;
		panel.setVisible(false);
		panel2.setVisible(false);
		btn1NewButton.setVisible(false);
		lLabel.setVisible(false);
		btn2Refresh.setVisible(false);
		scrollPane.setVisible(false);
		table.setVisible(false);
		btnNewButton.setVisible(false);
		btnRefresh.setVisible(false);
		JFileChooser openDialog = new JFileChooser("C:\\Users\\");
		openDialog.showOpenDialog(this);
		File currentFile = openDialog.getSelectedFile();
		System.out.println("File "+currentFile.getAbsolutePath());
			try {
				ArrayList<ProductDTO> products = ExcelReader.readExcel(currentFile);
				ProductDAO productDAO  = new ProductDAO();
				String message = productDAO.bulkAdd(products)?"Bulk Uploaded SuccessFully ":"Not Uploaded Some Error";
				if(message.contains("SuccessFully")) {
					JOptionPane.showMessageDialog(this, message);
					Email.sendMail();
				}
				else {
				JOptionPane.showMessageDialog(this, message);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
	
	private void downloadXLS(){
		firstTime = false;
		panel.setVisible(false);
		panel2.setVisible(false);
		btn1NewButton.setVisible(false);
		lLabel.setVisible(false);
		btn2Refresh.setVisible(false);
		scrollPane.setVisible(false);
		table.setVisible(false);
		btnNewButton.setVisible(false);
		btnRefresh.setVisible(false);
		File file = new File(BundleReader.getValue(XLS_FORMAT_KEY));
		if(file.exists()){
			try {
				FileInputStream fs = new FileInputStream(file);
				FileOutputStream fo = 
						new FileOutputStream(BundleReader.getValue(DOWNLOAD_KEY));
				int singleByte = fs.read();
				while(singleByte!=EOF){
					fo.write(singleByte);
					System.out.println((char)singleByte);
					singleByte = fs.read();
				}
				JOptionPane.showMessageDialog(this, 
						"Download Completed at:  "+BundleReader.getValue(DOWNLOAD_KEY));
				fs.close();
				fo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void showProduct() throws SQLException {
		firstTime = false;
		panel2.setVisible(false);
		panel.setVisible(false);
		btn1NewButton.setVisible(false);
		lLabel.setVisible(false);
		btn2Refresh.setVisible(false);
		scrollPane.setVisible(true);
		table.setVisible(true);
		btnNewButton.setVisible(true);
		btnRefresh.setVisible(true);
		table.setModel(new MyTableModel());
	}
	
	private void placeOrder() throws SQLException, ClassNotFoundException {
		if(firstTime==true) {
			ArrayList<Item> items;
			CartDAO c = new CartDAO();
			items =ItemOperations.fillItems();
			added = false;
			panel.setVisible(true);
			btn1NewButton.setVisible(true);
			btn2Refresh.setVisible(true);
			lLabel.setVisible(true);
			c.deleteAll();
			for(Item item:items) {
				panel1 = new JPanel();
				panel1.setPreferredSize(new Dimension(400,100));
				panel1.setLayout(new GridLayout(0,3,5,2));
				JLabel label = new JLabel(item.getName());
				JLabel l = new JLabel("Rs."+Double.toString(item.getPrice()));
				JButton but = new JButton("Add to Cart");
				but.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(added==false) {
							but.setBackground(Color.PINK);
							added=true;
							try {
								c.addToCart(item.getId(),item.getName(),item.getPrice());
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else{
							but.setBackground(null);
							added=false;
							try {
								c.deleteFromCart(item.getId());
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				});
				panel1.setBorder(new LineBorder(Color.BLACK,1));
				panel1.add(label);
				panel1.add(l);
				panel1.add(but);
				panel.add(panel1);
			}
			firstTime=false;
		}
		else if(firstTime==false) {
			panel.setVisible(true);
			btn1NewButton.setVisible(true);
			btn2Refresh.setVisible(true);
			lLabel.setVisible(true);
		}
		panel2.setVisible(false);
		scrollPane.setVisible(false);
		table.setVisible(false);
		btnNewButton.setVisible(false);
		btnRefresh.setVisible(false);
	}
	
	private void payOrder() throws SQLException {
		double total=0;
		mnOrder.setEnabled(false);
		panel.setVisible(false);
		btn1NewButton.setVisible(false);
		lLabel.setVisible(false);
		lLabel1.setVisible(true);
		btn2Refresh.setVisible(false);
		scrollPane.setVisible(false);
		table.setVisible(false);
		btnNewButton.setVisible(false);
		btnRefresh.setVisible(false);
		ArrayList<Item> items;
		items =ItemOperations.fillCartItems();
		panel2.setVisible(true);
		for(Item item:items) {
			panel1 = new JPanel();
			panel1.setPreferredSize(new Dimension(400,100));
			panel1.setLayout(new GridLayout(0,2,5,2));
			JLabel label = new JLabel(item.getName());
			label.setHorizontalAlignment(SwingConstants.LEFT);
			JLabel l = new JLabel("Rs."+Double.toString(item.getPrice()));
			l.setHorizontalAlignment(SwingConstants.CENTER);
			total = total + item.getPrice();
			panel1.add(label);
			panel1.add(l);
			panel2.add(panel1);
		}	
		JLabel totall = new JLabel("TOTAL AMOUNT: Rs."+ total);
		totall.setBounds(640,450,300,50);
		totall.setFont(new Font("Arial", Font.BOLD, 20));
		contentPane.add(totall);
		JButton btn = new JButton("Confirm Order");
		btn.setBounds(680,500,170,40);
		btn.setFont(new Font("Tahoma", Font.BOLD, 14));
		final double t = total;
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Email.confirmMail(t);
				JOptionPane.showMessageDialog(null, "Order Confirmed", "Congratulations!!", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		contentPane.add(btn);
	}
	
	public Dashboard(String userid){
		setTitle("Welcome "+Character.toString(userid.charAt(0)).toUpperCase()+userid.substring(1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1077, 736);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lLabel = new JLabel("Products List");
		lLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lLabel.setFont(new Font("Arial", Font.BOLD, 25));
		lLabel.setBounds(605,56, 300, 40);
		lLabel.setVisible(false);
		contentPane.add(lLabel);
		panel = new JPanel();
		panel.setLayout(new GridLayout(0,1,20,5));
		panel.setBounds(350, 100, 800, 600);
		panel.setVisible(false);
		contentPane.add(panel);
		btn1NewButton = new JButton("PLACE ORDER");
		btn1NewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn1NewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					payOrder();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
		});
		btn1NewButton.setBounds(600, 705, 145, 44);
		btn1NewButton.setVisible(false);
		contentPane.add(btn1NewButton);
		
		btn2Refresh = new JButton("MINIMIZE");
		btn2Refresh.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn2Refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstTime = false;
				panel.setVisible(false);
				btn1NewButton.setVisible(false);
				lLabel.setVisible(false);
				btn2Refresh.setVisible(false);
			}
		});
		btn2Refresh.setBounds(750, 705, 150, 44);
		btn2Refresh.setVisible(false);
		contentPane.add(btn2Refresh);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(443, 128, 630, 310);
		contentPane.add(scrollPane);
		scrollPane.setVisible(false);
		table = new JTable();
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		scrollPane.setColumnHeaderView(table);
		scrollPane.setViewportView(table);
		
		btnNewButton = new JButton("MINIMIZE");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setVisible(false);
				table.setVisible(false);
				btnNewButton.setVisible(false);
				btnRefresh.setVisible(false);
			}
		});
		btnNewButton.setBounds(622, 489, 145, 44);
		btnNewButton.setVisible(false);
		contentPane.add(btnNewButton);
		
		btnRefresh = new JButton("REFRESH");
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					table.setModel(new MyTableModel());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRefresh.setBounds(791, 489, 100, 44);
		btnRefresh.setVisible(false);
		contentPane.add(btnRefresh);
		table.setVisible(false);
		
		lLabel1 = new JLabel("FINAL BILL");
		lLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		lLabel1.setFont(new Font("Arial", Font.BOLD, 27));
		lLabel1.setBounds(615,156, 300, 40);
		lLabel1.setVisible(false);
		contentPane.add(lLabel1);
		panel2 = new JPanel();
		panel2.setLayout(new GridLayout(0,1,20,5));
		panel2.setBounds(550, 225, 450, 200);
		panel2.setVisible(false);
		panel2.setBorder(new LineBorder(Color.BLACK,1));
		contentPane.add(panel2);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Arial", Font.PLAIN, 12));
		this.setJMenuBar(menuBar);
		
		JMenu mnAdmin = new JMenu("Admin");
		mnAdmin.setFont(new Font("Segoe UI Symbol", Font.BOLD, 16));
		menuBar.add(mnAdmin);
		
		JMenu mnProducts = new JMenu("Products");
		mnProducts.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnAdmin.add(mnProducts);
		
		JMenuItem mntmAddProduct = new JMenuItem("Download XLS");
		mntmAddProduct.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mntmAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				downloadXLS();
			}
		});

		mnProducts.add(mntmAddProduct);
		
		JMenuItem mntmUpdateProduct = new JMenuItem("Upload XLS");
		mntmUpdateProduct.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mntmUpdateProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uploadXLS();
			}
		});
		mnProducts.add(mntmUpdateProduct);
		
		JMenuItem mntmDeleteProduct = new JMenuItem("Product List");
		mntmDeleteProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					showProduct();
				}catch(Exception e1) {
					System.out.println(e1);
				}

			}
		});
		mntmDeleteProduct.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnProducts.add(mntmDeleteProduct);
		
		JMenuItem mntmCustomerReport = new JMenuItem("Customer Report");
		mntmCustomerReport.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnAdmin.add(mntmCustomerReport);
		
		mnOrder = new JMenu("Customer");
		mnOrder.setFont(new Font("Segoe UI Symbol", Font.BOLD, 16));
		menuBar.add(mnOrder);
		
		JMenuItem mntmOrderPlace = new JMenuItem("Products List");
		mntmOrderPlace.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mntmOrderPlace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					placeOrder();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmOrderPlace.setHorizontalAlignment(SwingConstants.RIGHT);
		mnOrder.add(mntmOrderPlace);
	}
}
