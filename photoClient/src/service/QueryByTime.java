package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class QueryByTime extends Tools {
	
	public QueryByTime() {
		super();
	}

	public JSONArray work(String uid, long timestamp) {
		json.put("uid", uid);
		json.put("timestamp", timestamp);
		jsonArrIO();
		return jsonArr;
	}

	public static void main(String[] args) throws IOException {
		JSONArray jsonArr =  new QueryByTime().work("a17dbbc0bb2641e19754e41ed3a63d68", 1593134915591L);
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject json = jsonArr.getJSONObject(i);
			FileOutputStream fos = new FileOutputStream(new File("./images",json.getString("timestamp")+"_"+json.getString("name")));
			fos.write(json.getBytes("image"));
			fos.close();
		}
	}

}
