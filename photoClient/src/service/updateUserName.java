package service;

public class updateUserName extends Tools {
	
	public updateUserName() {
		super();
	}

	public int work(String uid, String name, String password) {
		json.put("uid", uid);
		json.put("name", name);
		json.put("password", password);
		jsonArr.add(json);
		getJsonArr();
		return jsonArr.getJSONObject(0).getIntValue("status");
	}

	public static void main(String[] args) {
		System.out.println(new updateUserName().work("99d0555fbbbb4700ac0ede33b5202660","lisa","123456"));

	}

}
