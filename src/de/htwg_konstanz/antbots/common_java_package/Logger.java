
package de.htwg_konstanz.antbots.common_java_package;
import java.io.*;
import java.io.File;

public class Logger {

	protected PrintStream logStream = null;
	
	public Logger(String logFileName) {
		try {
			logStream = new PrintStream(new File(logFileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void log(String log) {
		logStream.println(log);
	}
	public void logWithoutLineEnding(String s) {
		logStream.print(s);
	}
}