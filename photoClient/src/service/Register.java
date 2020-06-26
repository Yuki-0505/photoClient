package service;

public class Register extends Tools {
	
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
