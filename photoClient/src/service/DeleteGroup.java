package service;
/*
 * 删除分组
 * 返回状态
 */
public class DeleteGroup extends PassData {

	public DeleteGroup() {
		super();
	}
	
	public int work(String uid, int gid, int allFlag) {
		json.put("uid", uid);
		json.put("gid", gid);
		json.put("allFlag", allFlag);
		jsonArrIO();
		return jsonArr.getJSONObject(0).getIntValue("status");
	}

	public static void main(String[] args) {
		System.out.println(new DeleteGroup().work("0c0352b1559d4b9db79f5c04e69f5901", 2, 0));
	}

}
