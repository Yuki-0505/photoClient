package service;

import com.alibaba.fastjson.JSONArray;

public class QueryAllGroup extends PassData {
	
	public JSONArray work(String uid) {
		json.put("uid", uid);
		jsonArrIO();
		return jsonArr;
	}

	public static void main(String[] args) {
		System.out.println(new QueryAllGroup().work("0e878240e0ec488a93333b918f1a0398"));
	}

}
