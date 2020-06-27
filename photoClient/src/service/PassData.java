package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.Client;
/*
 * 将各种事物处理所需数据及返回结果
 * 封装为JSONArray
 * 方便统一数据传输
 * 抽取公共属性和代码块减少冗余
 */
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
