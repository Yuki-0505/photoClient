package service;
/*
 * 修改用户个性签名
 * 返回状态
 */
public class UpdateSignature extends PassData {

	public UpdateSignature() {
		super();
	}

	public int work(String uid, String signature) {
		json.put("uid", uid);
		json.put("signature", signature);
		jsonArrIO();
		return jsonArr.getJSONObject(0).getIntValue("status");
	}

	public static void main(String[] args) {
		System.out.println(new UpdateSignature().work("a17dbbc0bb2641e19754e41ed3a63d68", "这个人懒死了什么都没写>_<"));
	}

}
