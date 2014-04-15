package com.gravypod.smlauncher;

import java.io.File;
import java.io.IOException;

public class DirectoryManagement {
	
	private final static String DIR_NAME = "starmade";
	
	private final static String JAR_NAME = "StarMade.jar";
	
	public static enum OS {
		WINDOWS("windows"), LINUX("linux"), MAC("macosx"), OTHER("other");
		
		String name;
		
		private OS(String name) {
		
			this.name = name;
		}
		
		private String getName() {
		
			return name;
		}
	}
	
	public static File getDirectory() {
	
		return getDirectory(getOS());
	}
	
	public static String getCommand() {
	
		return getCommand(getOS());
	}
	
	public static String getCommand(OS os) {
	
		switch (os) {
		//	case WINDOWS:
			//	return "javaw";
			default:
				return "java";
		}
	}
	
	public static OS getOS() {
	
		String osName = System.getProperty("os.name").toLowerCase();
		
		if (osName.contains("win"))
			return OS.WINDOWS;
		if (osName.contains("mac"))
			return OS.MAC;
		if (osName.contains("linux"))
			return OS.LINUX;
		if (osName.contains("unix"))
			return OS.LINUX;
		
		return OS.OTHER;
	}
	
	public static String getStarmadePath() {
	
		return getStarmadeFile().getAbsolutePath();
	}
	
	public static File getStarmadeFile() {
	
		return new File(getDirectory(), JAR_NAME);
	}
	
	public static boolean is64Bit() {
	
		return System.getProperty("os.arch").contains("64");
	}
	
	public static File getNativesDirectory() {
	
		File osNatives = new File(new File(getDirectory(), "native"), getOS().getName());
		
		if (is64Bit()) {
			return new File(osNatives, "x64");
		}
		return osNatives;
	}
	
	public static File getDirectory(OS type) {
	
		String homeFile = System.getProperty("user.home", ".");
		File installDirectory;
		switch (type) {
			case LINUX:
				installDirectory = new File(homeFile, '.' + DIR_NAME + '/');
				break;
			case WINDOWS:
				String appdataFile = System.getenv("APPDATA");
				String choosenFile = appdataFile != null ? appdataFile : homeFile;
				installDirectory = new File(choosenFile, '.' + DIR_NAME + '/');
				break;
			case MAC:
				installDirectory = new File(homeFile, "Library/Application Support/" + DIR_NAME);
				break;
			default:
				installDirectory = new File(homeFile, DIR_NAME + '/');
		}
		if (!installDirectory.exists() || !installDirectory.isDirectory()) {
			installDirectory.mkdirs();
		}
		return installDirectory;
	}
	
	public static ProcessBuilder launch() throws IOException {
	
		ProcessBuilder pb = new ProcessBuilder();
		pb.directory(getDirectory());
		String command = getCommand() + " -Djava.library.path=\"" + getNativesDirectory().getAbsolutePath() + "\" " +  Settings.getLaunchArgs() + " -jar " + new File(getDirectory(), JAR_NAME).getAbsolutePath() + " -force";
		pb.command(command.split(" "));
		System.out.println(command);
		return pb;
	}
	
}
