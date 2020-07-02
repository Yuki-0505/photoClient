package service;
/*
 * 修改用户名和密码
 * 返回状态
 */
public class UpdateUserInfo extends PassData {
	
	public UpdateUserInfo() {
		super();
	}

	public int work(String uid, String name, String password) {
		json.put("uid", uid);
		json.put("name", name);
		json.put("password", password);
		jsonArrIO();
		return jsonArr.getJSONObject(0).getInteger("status");
	}

	public static void main(String[] args) {
		System.out.println(new UpdateUserInfo().work("a17dbbc0bb2641e19754e41ed3a63d68","lisa","123456"));
	}

}
