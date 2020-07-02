package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesWrite extends Properties {
	private BufferedOutputStream bos;
	
	public PropertiesWrite() {
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream("user.properties"));
			load(bis);
			bis.close();
			bos = new BufferedOutputStream(new FileOutputStream("user.properties"));
		} catch (Exception e) {
			ErrorLog.log(e);
		}
	}
	
	public void close() {
		try {
			store(bos, null);
			bos.close();
		} catch (IOException e) {
			ErrorLog.log(e);
		}
	}

}
