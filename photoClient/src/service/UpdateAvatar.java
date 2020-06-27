package service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
/*
 * 修改用户头像
 * 返回状态
 */
public class UpdateAvatar extends PassData {

	public UpdateAvatar() {
		super();
	}

	public int work(String uid, String avaname, byte[] image) {
		json.put("uid", uid);
		json.put("avaname", avaname);
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
		System.out.println(new UpdateAvatar().work("a17dbbc0bb2641e19754e41ed3a63d68", "yui.jpg", image));
		fis.close();
	}

}
