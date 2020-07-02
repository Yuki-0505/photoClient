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

	public JSONArray work(String uid, int gid, int offset) {
		json.put("uid", uid);
		json.put("gid", gid);
		json.put("offset", offset);
		jsonArrIO();
		return jsonArr;
	}
	

	public static void main(String[] args) throws IOException {
		new QueryByTyte().work("0e878240e0ec488a93333b918f1a0398", 1, 0);
	}

}

