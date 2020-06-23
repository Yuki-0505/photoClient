package service;

import java.sql.ResultSet;

import com.alibaba.fastjson.JSONObject;

import net.Client;

public class Login {
	
	public ResultSet work(String name, String password) {
		JSONObject json = new JSONObject();
		json.put("clazz", this.getClass().getName());
		json.put("name", name);
		json.put("password", password);
		Client client = new Client();
		client.sendJson(json);
		json = client.receiveJson();
		client.closeAll();
		return (ResultSet)json.get("rs");
	}

	public static void main(String[] args) {
		System.out.println(new Login().work("yuki", "123456"));
		
	}

}
