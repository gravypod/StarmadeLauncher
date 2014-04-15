package com.gravypod.smlauncher;

import java.awt.EventQueue;

import com.gravypod.smlauncher.guis.LauncherGUI;

public class Launcher {
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
			
				try {
					VersionManager versionManager = new VersionManager(DirectoryManagement.getDirectory());
					LauncherGUI gui = new LauncherGUI(versionManager);
					gui.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
