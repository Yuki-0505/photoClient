package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/*
 * 按类型编号查询图片
 * 返回图片信息
 */
public class QueryByTyte extends PassData {

	public QueryByTyte() {
		super();
	}

	public JSONArray work(String uid, int gid) {
		json.put("uid", uid);
		json.put("gid", gid);
		jsonArrIO();
		return jsonArr;
	}

	public static void main(String[] args) throws IOException {
		JSONArray jsonArr =  new QueryByTyte().work("a17dbbc0bb2641e19754e41ed3a63d68", 1);
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject json = jsonArr.getJSONObject(i);
			FileOutputStream fos = new FileOutputStream(new File("./images",json.getString("timestamp")+json.getString("name")));
			fos.write(json.getBytes("image"));
			fos.close();
		}
		
	}

}
