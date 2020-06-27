package service;
/*
 * 修改图片所属分组
 * 返回状态
 */
public class UpdateImageGroup extends PassData {

	public UpdateImageGroup() {
		super();
	}

	public int work(String uid, int gid, long timestamp, int newgid) {
		json.put("uid", uid);
		json.put("gid", gid);
		json.put("timestamp", timestamp);
		json.put("newgid", newgid);
		jsonArrIO();
		return jsonArr.getJSONObject(0).getIntValue("status");
	}

	public static void main(String[] args) {
		System.out.println(new UpdateImageGroup().work("a17dbbc0bb2641e19754e41ed3a63d68", 3, 1593132983112L, 2));
	}

}
