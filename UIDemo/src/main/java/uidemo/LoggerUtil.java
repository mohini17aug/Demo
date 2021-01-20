package uidemo;

import java.io.FileWriter;
import java.io.IOException;

public class LoggerUtil {

	static FileWriter fileWriter;
	public static void initialize() {
		String logFilePath = System.getProperty("user.dir")+"//src/test/resources/log.log";
		try {
			FileUtil.deleteFile(logFilePath);
			
		} catch (IOException e) {
		}
		try {
			FileUtil.createTextFile(logFilePath, "");
			fileWriter = new FileWriter(logFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void startTestCase(String testCaseName, String testCaseDescription) {
		try {
			fileWriter.write(">>>>>>>>>>>>>>>>>>Starting Test CASE: "+testCaseName+" \t Description "+testCaseDescription+"\n");
			System.out.println(">>>>>>>>>>>>>>>>>>Starting Test CASE: "+testCaseName+" \t Description "+testCaseDescription);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void endTestCase() {
		try {
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n\n");
			fileWriter.write("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void logPass(String message) {
		try {
			System.out.println("PASS \t"+message+"\n");
			fileWriter.write("PASS \t"+message+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void logFail(String message) {
		try {
			System.out.println("FAIL \t"+message+"\n");
			fileWriter.write("FAIL \t"+message+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void logInfo(String message) {
		try {
			System.out.println("INFP \t"+message+"\n");
			fileWriter.write("INFP \t"+message+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void close() {
		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
