package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/*
 * 按时间戳查询图片
 * 返回图片信息
 */
public class QueryByTime extends PassData {
	
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
		JSONArray jsonArr =  new QueryByTime().work("0c0352b1559d4b9db79f5c04e69f5901", 15983310300826L);
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject json = jsonArr.getJSONObject(i);
			FileOutputStream fos = new FileOutputStream(new File("./images",json.getString("timestamp")+"_"+json.getString("name")));
			fos.write(json.getBytes("image"));
			fos.close();
		}
	}

}
