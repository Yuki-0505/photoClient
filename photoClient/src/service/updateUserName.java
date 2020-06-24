package service;

import net.Client;

public class updateUserName extends Tools {
	
	public int work(String uid, String name, String password) {
		clazz.put("clazz", this.getClass().getName());
		json.put("uid", uid);
		json.put("name", name);
		json.put("password", password);
		jsonArr.add(clazz);
		jsonArr.add(json);
		Client client = new Client();
		client.sendJson(jsonArr);
		jsonArr = client.receiveJson();
		client.closeAll();
		return jsonArr.getJSONObject(0).getIntValue("status");
	}

	public static void main(String[] args) {
		System.out.println(new updateUserName().work("2bc0ba6447614e7f93f00a9d474f3d03","lisa","123456"));

	}

}
