package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.Client;

public class PassData {
	
	public JSONArray jsonArr = new JSONArray();
	public JSONObject clazz = new JSONObject();
	public JSONObject json = new JSONObject();
	
	public PassData() {
		super();
		clazz.put("clazz", this.getClass().getName());
		jsonArr.add(clazz);
	}
	
	public void jsonArrIO() {
		jsonArr.add(json);
		Client client = new Client();
		client.sendJson(jsonArr);
		jsonArr = client.receiveJson();
		client.closeAll();
	}
}
