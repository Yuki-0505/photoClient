package utils;

public class GetFileSuffix {
	
	public static String get(String fileName) {
		String[] split = fileName.split("\\.");
		if(split.length > 1)
			return split[split.length - 1];
		return null;
	}

}
