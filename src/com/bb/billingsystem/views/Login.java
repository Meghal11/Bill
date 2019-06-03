package com.bb.billingsystem.views;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bb.billingsystem.dao.UserDAO;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.border.EmptyBorder;

public class Login extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	

	/**
	 * Launch the application.
	 */
	private void checkLogin() {
		String userid = textField.getText();
		String password = String.valueOf(passwordField.getPassword());
		UserDAO common = new UserDAO();
		try {
		boolean isUserexist = common.doLogin(userid, password);
		String message = isUserexist?"Welcome "+Character.toString(userid.charAt(0)).toUpperCase()+userid.substring(1):"Invalid Userid or Password";
		JOptionPane.showMessageDialog(this, message);
		if(isUserexist) {
			if(userid.equals("admin")== true) {
				Registration reg = new Registration(userid);
				reg.setExtendedState(JFrame.MAXIMIZED_BOTH);
				reg.setVisible(true);
				this.setVisible(false);
				this.dispose();
			}
			else {
				Dashboard dashBoard = new Dashboard(userid);
				common.getDetails(userid);
				dashBoard.setExtendedState(JFrame.MAXIMIZED_BOTH);
				dashBoard.setVisible(true);
				this.setVisible(false);
				this.dispose();
			}
		}
		}
		catch(SQLException e) {
			JOptionPane.showMessageDialog(this, "Some DataBase Issue Occur Contact to System Admin");
		}
		catch(ClassNotFoundException e) {
			JOptionPane.showMessageDialog(this, "DB Driver Missing, Contact to System Admin");
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setResizable(false);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUserid = new JLabel(" Userid :");
		lblUserid.setFont(new Font("Bookman Old Style", Font.BOLD, 19));
		lblUserid.setBounds(572, 253, 133, 56);
		contentPane.add(lblUserid);
		
		JLabel lblNewLabel = new JLabel("Password :");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 19));
		lblNewLabel.setBounds(572, 319, 133, 56);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					passwordField.requestFocus(true);
				}
			}
		});
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setBounds(705, 265, 300, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					checkLogin();
				}
			}
		});
		passwordField.setEchoChar('*');
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		passwordField.setBounds(705, 332, 300, 30);
		contentPane.add(passwordField);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkLogin();
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnLogin.setBounds(637, 417, 150, 40);
		contentPane.add(btnLogin);
		
		JButton btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				passwordField.setText("");
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnReset.setBounds(813, 417, 150, 40);
		contentPane.add(btnReset);		
	}
}
