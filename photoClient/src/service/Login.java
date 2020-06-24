package service;

import java.sql.SQLException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.Client;

public class Login extends Tools {
	
	public JSONObject work(String name, String password) {
		clazz.put("clazz", this.getClass().getName());
		json.put("name", name);
		json.put("password", password);
		jsonArr.add(clazz);
		jsonArr.add(json);
		Client client = new Client();
		client.sendJson(jsonArr);
		jsonArr = client.receiveJson();
		client.closeAll();
		if(jsonArr.size() == 0) {
			return null;
		}
		return jsonArr.getJSONObject(0);
	}

	public static void main(String[] args) throws SQLException {
		JSONObject json = new Login().work("yuki", "123456");
		
		System.out.println(json);
		
		
	}

}
