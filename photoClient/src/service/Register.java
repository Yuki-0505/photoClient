package service;
/*
 * 注册用户
 * 返回状态
 */
public class Register extends PassData {
	
	public int work(String name, String password) {
		json.put("name", name);
		json.put("password", password);
		jsonArrIO();
		return jsonArr.getJSONObject(0).getIntValue("status");
	}

	public static void main(String[] args) {
		System.out.println(new Register().work("yuki", "123456"));
//		System.out.println(new Register().work("mafu", "123456"));
	}

}
