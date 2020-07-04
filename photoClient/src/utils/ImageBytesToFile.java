package utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageBytesToFile {
	
	public static void save(File file, byte[] image) {
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(image);
			bos.close();
		} catch (IOException e) {
			ErrorLog.log(e);
		}
	}

}
