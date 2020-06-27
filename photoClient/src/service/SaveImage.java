package service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
/*
 * 上传图片及信息
 * 返回状态
 */
public class SaveImage extends PassData {

	public SaveImage() {
		super();
	}
	
	public int work(String uid, int gid, String name,byte[] image) {
		json.put("uid", uid);
		json.put("gid", gid);
		json.put("name", name);
		json.put("image", image);
		jsonArrIO();
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
		System.out.println(new SaveImage().work("a17dbbc0bb2641e19754e41ed3a63d68", 1, "yui.jpg", image));
		fis.close();
	}

}
