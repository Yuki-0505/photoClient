package utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class ImageFileToBytes {
	
	public static byte[] getBytes(File file) {
		byte[] image = null;
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024*4];
			int len;
			while((len = bis.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			image = baos.toByteArray();
			bis.close();
			baos.close();
			return image;
		} catch (Exception e) {
			ErrorLog.log(e);
		}
		
		return null;
	}
	
}
