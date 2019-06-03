package com.bb.billingsystem.views;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

public class IntroScreen extends JWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(IntroScreen.class);

	private JPanel contentPane;
	Timer timer;
	int counter = 1;
	private void animation() {
		logger.debug("Animation Start With Counter Value "+counter);
		timer = new Timer(50,(e)->{
			progressBar.setValue(counter);
			counter++;
			if(counter>=100) {
				this.setVisible(false);
				this.dispose();
				timer.stop();
				Login login = new Login();
				login.setVisible(true);
			}
		});
		timer.start();
		logger.debug("Animation Code Ends with Counter value "+counter);
	}
	public static void main(String[] args) {
					logger.debug("Inside Main");	
					IntroScreen frame = new IntroScreen();
					
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.animation();
					logger.debug("Main Ends");
	}

	/**
	 * Create the frame.
	 */
	JProgressBar progressBar = new JProgressBar();
	public IntroScreen() {
		
		setBounds(100, 100, 790, 511);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel imagelbl = new JLabel("");
		imagelbl.setIcon(new ImageIcon(IntroScreen.class.getResource("/com/bb/billingsystem/views/intro.jpeg")));
		imagelbl.setBounds(227, 96, 379, 266);
		contentPane.add(imagelbl);
		
		
		progressBar.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		progressBar.setStringPainted(true);
		progressBar.setBounds(4, 354, 778, 45);
		contentPane.add(progressBar);
	}
}