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
		System.out.println(new CreateGroup().work("a17dbbc0bb2641e19754e41ed3a63d68", "hello"));
	}

}
