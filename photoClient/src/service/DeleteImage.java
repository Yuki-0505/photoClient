package service;
/*
 * 按组编号和时间戳删除图片
 * 返回状态
 */
public class DeleteImage extends PassData {

	public DeleteImage() {
		super();
	}

	public int work(String uid, int gid, long timestamp) {
		json.put("uid", uid);
		json.put("gid", gid);
		json.put("timestamp", timestamp);
		jsonArrIO();
		return jsonArr.getJSONObject(0).getIntValue("status");
	}

	public static void main(String[] args) {
		System.out.println(new DeleteImage().work("0c0352b1559d4b9db79f5c04e69f5901", 2, 15983310300826L));
	}

}
