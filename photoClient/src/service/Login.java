package service;

import java.sql.SQLException;

import com.alibaba.fastjson.JSONObject;
/*
 * 登录验证
 * 返回状态及用户信息
 */
public class Login extends PassData {
	
	public Login() {
		super();
	}

	public JSONObject work(String name, String password) {
		json.put("name", name);
		json.put("password", password);
		jsonArrIO();
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
