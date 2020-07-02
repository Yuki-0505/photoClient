package service;
/*
 * 新建分组
 * 返回状态
 */
public class CreateGroup extends PassData {
	
	public CreateGroup() {
		super();
	}

	public int work(String uid, String groupName) {
		json.put("uid", uid);
		json.put("groupName", groupName);
		jsonArrIO();
		return jsonArr.getJSONObject(0).getIntValue("status");
	}

	public static void main(String[] args) {
		System.out.println(new CreateGroup().work("0c0352b1559d4b9db79f5c04e69f5901", "hello"));
	}

}
