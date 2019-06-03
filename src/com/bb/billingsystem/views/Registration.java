package com.bb.billingsystem.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.bb.billingsystem.dao.UserDAO;
import com.bb.billingsystem.utils.AdminConstants;
import com.bb.billingsystem.utils.Email;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Registration extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField textField_2;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 **/
	private void setDetails() {
		String userid = txtUsername.getText();
		String password = String.valueOf(passwordField.getPassword());
		String email = textField_2.getText();
		AdminConstants add = new AdminConstants();
		add.setUserid(userid);
		add.setPassword(password);
		add.setEmail(email);
		UserDAO common = new UserDAO();
		try {
		boolean isUserexist = common.doRegister(userid, password,email);
		String message = isUserexist?"Welcome "+Character.toString(userid.charAt(0)).toUpperCase()+userid.substring(1):"Invalid Userid or Password";
		JOptionPane.showMessageDialog(this, message);
		if(isUserexist) {
			Dashboard dashBoard = new Dashboard(userid);
			dashBoard.setExtendedState(JFrame.MAXIMIZED_BOTH);
			dashBoard.setVisible(true);
			this.setVisible(false);
			this.dispose();
			Email.registerMail(email);
			}
		}
		catch(SQLException e) {
			JOptionPane.showMessageDialog(this, "Some DataBase Issue Occur Contact to System Admin");
		}
		catch(ClassNotFoundException e) {
			JOptionPane.showMessageDialog(this, "DB Driver Missing, Contact to System Admin");
		}
	}

	/**
	 * Create the frame.
	 */
	public Registration(String userid) {
		setTitle("Welcome "+Character.toString(userid.charAt(0)).toUpperCase()+userid.substring(1));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1083, 765);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Registration Form");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 25));
		lblNewLabel.setBounds(605, 156, 300, 40);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Enter Username :");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(540, 263, 170, 40);
		contentPane.add(lblNewLabel_1);
		
		txtUsername = new JTextField();
		txtUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					passwordField.requestFocus(true);
				}
			}
		});
		txtUsername.setFont(new Font("Arial", Font.PLAIN, 16));
		txtUsername.setBounds(710, 270, 250, 27);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblEnterPassword = new JLabel("Enter Password :");
		lblEnterPassword.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEnterPassword.setBounds(540, 328, 170, 40);
		contentPane.add(lblEnterPassword);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					textField_2.requestFocus(true);
				}
			}
		});
		passwordField.setBounds(710, 335, 250, 27);
		contentPane.add(passwordField);
		
		JLabel lblEnterEmail = new JLabel("Enter Email Id :");
		lblEnterEmail.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEnterEmail.setBounds(540, 401, 170, 40);
		contentPane.add(lblEnterEmail);
		
		textField_2 = new JTextField();
		textField_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					setDetails();
				}
			}
		});
		textField_2.setFont(new Font("Arial", Font.PLAIN, 16));
		textField_2.setColumns(10);
		textField_2.setBounds(710, 408, 250, 27);
		contentPane.add(textField_2);
		
		JButton btnNewButton = new JButton("SET DETAILS");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDetails();
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 15));
		btnNewButton.setBounds(600, 500, 150, 40);
		contentPane.add(btnNewButton);
		
		JButton btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_2.setText("");
				passwordField.setText("");
				txtUsername.setText("");
			}
		});
		btnReset.setFont(new Font("Arial", Font.BOLD, 15));
		btnReset.setBounds(756, 500, 150, 40);
		contentPane.add(btnReset);
		
	}
}
