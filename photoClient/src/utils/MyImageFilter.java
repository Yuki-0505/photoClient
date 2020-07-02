package utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class MyImageFilter extends FileFilter {

	@Override
	public String getDescription() {
		return "图片文件(*.jpg, *.jpeg, *.gif, *.png)";
	}

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		String[] split = f.getName().split("\\.");
		if (split.length < 2)
			return false;
		String extension = split[1];
		if ("gif".equals(extension) || "jpeg".equals(extension) || "jpg".equals(extension)
				|| "png".equals(extension)) {
			return true;
		} else {
			return false;
		}
	}

}
