package view;

import java.io.BufferedReader;
import java.io.FileReader;

public class Photos {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("config.properties"));
		Class<?> clazz = Class.forName(br.readLine());
		
		Object obj = (Object) clazz.newInstance();
		System.out.println(obj.toString());
		
		br.close();
		
	}

}
