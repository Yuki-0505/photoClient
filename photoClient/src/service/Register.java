package service;

import net.Client;

public class Register extends Tools {
	
	public int work(String name, String password) {
		clazz.put("clazz", this.getClass().getName());
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
		System.out.println(new Register().work("mafu", "123456"));
	}

}
