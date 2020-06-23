package service;

import com.alibaba.fastjson.JSONObject;

import net.Client;

public class Register {
	
	public int work(String name, String password) {
		JSONObject json = new JSONObject();
		json.put("clazz" ,this.getClass().getName());
		json.put("name", name);
		json.put("password", password);
		Client client = new Client();
		client.sendJson(json);
		json = client.receiveJson();
		client.closeAll();
		return json.getIntValue("status");
	}

	public static void main(String[] args) {
		System.out.println(new Register().work("yuki", "123456"));
	}

}
