package utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesRead extends Properties {
	private BufferedInputStream bis;

	public PropertiesRead() {
		try {
			bis = new BufferedInputStream(new FileInputStream("user.properties"));
			load(bis);
		} catch (Exception e) {
			ErrorLog.log(e);
		}
	}

	public void close() {
		try {
			bis.close();
		} catch (IOException e) {
			ErrorLog.log(e);
		}
	}
	
	public static void main(String[] args) {
		PropertiesRead pr = new PropertiesRead();
		pr.close();
		System.out.println(pr.getProperty("name"));
	}

}
