package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorLog {
	
	public static void errorLog(Exception e) {
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        PrintStream ps = null;
		try {
			ps = new PrintStream(new FileOutputStream("error.log", true));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        ps.println(sw.toString());
        ps.close();
	}

	public static void main(String[] args) {
		
	}

}
