package de.htwg_konstanz.antbots.common_java_package;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class AntBot {

	public static void main(String[] args) throws IOException{

		/* Rechtsklick auf AntBotsProject -> Properties -> Builders -> neuen Ant Builder
		 * fuer build.xml hinzufuegen oder importieren.
		 * Danach in dieser Datei den absoluten Pfad ändern*/
		
		File program = new File("C:\\Users\\Felix\\Documents\\TeamProjekt\\AntBotsProject\\attackbot.cmd");
	    if (!program.exists()) {
	      System.err.println("File not found! " + program);
	      return;
	    }
	    ProcessBuilder builder = new ProcessBuilder("cmd", "/c", "start", program.toString());
	    builder.redirectErrorStream(true);
	    System.out.println("Starting process...");
	    Process process = builder.start();
	    InputStream stream = process.getInputStream();
	    System.out.println("Reading output:");
	    int i;
	    while ((i = stream.read()) > -1) {
	      System.out.print((char) i);
	    }
	    System.out.println("\r\nProcess exited with code " + process.exitValue());
	    
	}

}
