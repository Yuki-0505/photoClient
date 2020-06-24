package service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class SaveImage extends Tools {

	public SaveImage() {
		super();
	}
	
	public int work(String uid, int gid, byte[] image) {
		json.put("uid", uid);
		json.put("gid", gid);
		json.put("image", image);
		jsonArr.add(json);
		getJsonArr();
		return jsonArr.getJSONObject(0).getIntValue("status");
	}

	public static void main(String[] args) throws IOException {
		FileInputStream fis = new FileInputStream("./images/yui.jpg");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int b;
		while((b = fis.read()) != -1) {
			baos.write(b);
		}
		byte[] image = baos.toByteArray();
		System.out.println(new SaveImage().work("99d0555fbbbb4700ac0ede33b5202660", 1, image));
	}

}
