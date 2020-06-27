package service;
/*
 * 修改用户名和密码
 * 返回状态
 */
public class updateUserName extends PassData {
	
	public updateUserName() {
		super();
	}

	public int work(String uid, String name, String password) {
		json.put("uid", uid);
		json.put("name", name);
		json.put("password", password);
		jsonArrIO();
		return jsonArr.getJSONObject(0).getIntValue("status");
	}

	public static void main(String[] args) {
		System.out.println(new updateUserName().work("a17dbbc0bb2641e19754e41ed3a63d68","lisa","123456"));

	}

}
