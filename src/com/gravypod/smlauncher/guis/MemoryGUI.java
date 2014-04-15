package com.gravypod.smlauncher.guis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.gravypod.smlauncher.Settings;

public class MemoryGUI {
	
	private JFrame frame;
	
	private JTextField maxMemory;
	
	private JTextField minMemory;
	
	private JButton btnSave;
	
	private JButton btnCancle;
	
	/**
	 * Create the application.
	 */
	public MemoryGUI() {
	
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		frame = new JFrame();
		frame.setBounds(100, 100, 381, 108);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel maxMemLable = new JLabel("Max Memory");
		maxMemLable.setBounds(10, 11, 86, 14);
		frame.getContentPane().add(maxMemLable);
		
		JLabel minMemLable = new JLabel("Min Memory");
		minMemLable.setBounds(10, 41, 86, 14);
		frame.getContentPane().add(minMemLable);
		
		maxMemory = new JTextField();
		maxMemory.setBounds(90, 8, 86, 20);
		frame.getContentPane().add(maxMemory);
		maxMemory.setColumns(10);
		
		minMemory = new JTextField();
		minMemory.setColumns(10);
		minMemory.setBounds(90, 39, 86, 20);
		frame.getContentPane().add(minMemory);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				try {
					Settings.setMax(maxMemory.getText());
					Settings.setMin(minMemory.getText());
					Settings.save();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Unable to save");
					e1.printStackTrace();
				}
				unshow();
			}
		});
		btnSave.setBounds(242, 7, 89, 23);
		frame.getContentPane().add(btnSave);
		
		btnCancle = new JButton("Cancel");
		btnCancle.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				unshow();
			}
		});
		btnCancle.setBounds(242, 37, 89, 23);
		frame.getContentPane().add(btnCancle);
	}
	
	public void unshow() {
	
		frame.setVisible(false);
	}
	
	public void show() {
	
		maxMemory.setText(Settings.getMax());
		minMemory.setText(Settings.getMin());
		frame.setVisible(true);
	}
}
