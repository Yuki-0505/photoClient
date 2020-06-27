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
		System.out.println(new DeleteImage().work("a17dbbc0bb2641e19754e41ed3a63d68", 1, 1593132983112L));
	}

}
