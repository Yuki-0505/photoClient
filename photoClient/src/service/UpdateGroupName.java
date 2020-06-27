package service;
/*
 * 修改分组名
 * 返回状态
 */
public class UpdateGroupName extends PassData {

	public UpdateGroupName() {
		super();
	}

	public int work(String uid, int gid, String newName) {
		json.put("uid", uid);
		json.put("gid", gid);
		json.put("newName", newName);
		jsonArrIO();
		return jsonArr.getJSONObject(0).getIntValue("status");
	}

	public static void main(String[] args) {
		System.out.println(new UpdateGroupName().work("a17dbbc0bb2641e19754e41ed3a63d68", 2, "goodbye"));
	}

}
