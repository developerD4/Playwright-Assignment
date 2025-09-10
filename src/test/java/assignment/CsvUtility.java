package assignment;

import java.io.FileWriter;
import java.io.IOException;

public class CsvUtility {
	private static FileWriter writer;
	
	public static void init(String fileName) throws IOException {
		if(writer == null) {
			writer = new FileWriter(fileName);
			writer.append("TestClass,TestCase,Status,Message\r\n"); 
			//\r\n	CR + LF → moves cursor to beginning and down to the next line (Windows-style new line)
			writer.flush();
		}
	}
	public static void logResult(String testClass, String testCase, boolean status, String message) throws IOException{
		writer.append(testClass).append(",")
			.append(testCase).append(",")
			.append(status ? "Passed":"Failed").append(",")
			.append(message.replace(",", ";")).append("\r\n");
		writer.flush();
	}
	public static void close() throws IOException{
		if(writer!=null) {
			writer.flush();
			writer.close();
		}
	}
	
}
