package com.gravypod.smlauncher.guis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import com.gravypod.smlauncher.DirectoryManagement;
import com.gravypod.smlauncher.Settings;
import com.gravypod.smlauncher.VersionManager;

public class LauncherGUI {
	
	private static final String NEWS = "http://star-made.org/news";
	
	private final JFrame frame;
	
	private final WebsiteTab newsBox;
	
	private final JButton updateButton, launchButton;
	
	private final JCheckBox runBackup;
	
	private final JLabel latestBuild, latestVersion;
	
	private final MemoryGUI memoryGUI = new MemoryGUI();
	
	/**
	 * Create the application.
	 * 
	 * @param versionManager
	 */
	public LauncherGUI(final VersionManager versionManager) {
	
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 656, 473);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		updateButton = new JButton("Update");
		updateButton.setBounds(10, 411, 89, 23);
		updateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				launchButton.setVisible(false);
				new Thread() {
					
					@Override
					public void run() {
					
						try {
							versionManager.downloadUpdate(Settings.getBackup(), frame);
						} catch (IOException e) {
							e.printStackTrace();
						}
						SwingUtilities.invokeLater(new Runnable() {
							
							public void run() {
							
								setNeedsUpdate(false);
								launchButton.setVisible(true);
							}
						});
					}
				}.start();
			}
		});
		
		frame.getContentPane().add(updateButton);
		
		launchButton = new JButton("Launch");
		launchButton.setBounds(551, 411, 89, 23);
		launchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				try {
					Process proc = DirectoryManagement.launch().start();
					hide();
					proc.waitFor();
					show();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(launchButton);
		
		JLabel latestVersionLabel = new JLabel("Current Version:");
		latestVersionLabel.setBounds(10, 360, 150, 14);
		frame.getContentPane().add(latestVersionLabel);
		
		latestVersion = new JLabel("Version");
		latestVersion.setBounds(109, 360, 106, 14);
		frame.getContentPane().add(latestVersion);
		
		JLabel latestBuildLabel = new JLabel("Latest Build:");
		latestBuildLabel.setBounds(10, 335, 89, 14);
		frame.getContentPane().add(latestBuildLabel);
		
		latestBuild = new JLabel("Version");
		latestBuild.setBounds(109, 335, 106, 14);
		frame.getContentPane().add(latestBuild);
		
		newsBox = new WebsiteTab();
		newsBox.setBounds(10, 32, 630, 292);
		newsBox.setPage(NEWS);
		frame.getContentPane().add(newsBox);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 650, 21);
		frame.getContentPane().add(menuBar);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JMenuItem mntmOptions = new JMenuItem("Launcher Options");
		mntmOptions.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			
				memoryGUI.show();
			}
		});
		mnSettings.add(mntmOptions);
		
		runBackup = new JCheckBox("Backup");
		runBackup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				Settings.setBackup(!Settings.getBackup());
				Settings.save();
			}
		});
		runBackup.setBounds(10, 381, 97, 23);
		runBackup.setSelected(Settings.getBackup());
		frame.getContentPane().add(runBackup);
		
		setNeedsUpdate(versionManager.needsUpdate());
		setLatestBuild(versionManager.latestBuild());
		setCurrentVersion(versionManager.getBuild());
		
	}
	
	public void setNeedsUpdate(boolean needsUpdate) {
	
		updateButton.setVisible(needsUpdate);
		runBackup.setVisible(needsUpdate);
	}
	
	public void setLatestBuild(String version) {
	
		latestBuild.setText(version);
	}
	
	public void setCurrentVersion(String version) {
	
		latestVersion.setText(version);
	}
	
	public void show() {
	
		frame.setVisible(true);
	}
	
	public void hide() {
	
		frame.setVisible(false);
		
	}
}
