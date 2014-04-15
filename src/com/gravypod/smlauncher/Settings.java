package com.gravypod.smlauncher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {
	
	private static final File settingsFile = new File(DirectoryManagement.getDirectory(), "launcherSettings.ini");
	
	private static final Properties props = new Properties();
	
	static {
		
		if (settingsFile.exists()) {
			try {
				props.load(new FileInputStream(settingsFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				defaultSettings();
			} catch (IOException e) {
				e.printStackTrace();
				defaultSettings();
			}
		} else {
			defaultSettings();
		}
		
	}
	
	public static void save() {
	
		try {
			props.store(new FileOutputStream(settingsFile), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void defaultSettings() {
	
		props.setProperty("minMem", "512M");
		props.setProperty("maxMem", "2G");
		props.setProperty("backup", "false");
	}
	
	public static void setBackup(boolean backup) {
	
		props.setProperty("boolean", Boolean.toString(backup));
	}
	
	public static boolean getBackup() {
	
		return Boolean.parseBoolean(props.getProperty("backup"));
	}
	
	public static String getMin() {
	
		return props.getProperty("minMem");
	}
	
	public static void setMin(String min) {
	
		props.setProperty("minMem", min);
	}
	
	public static String getMax() {
	
		return props.getProperty("maxMem");
	}
	
	public static void setMax(String max) {
	
		props.setProperty("maxMem", max);
	}
	
	public static String getLaunchArgs() {
		return "-Xms" + getMin() + " -Xmx" + getMax();
	}
}
