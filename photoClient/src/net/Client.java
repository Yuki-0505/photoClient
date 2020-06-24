package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Client {

	private Socket socket;
	private BufferedReader in;
	private PrintStream out;

	public Client() {
		try {
			this.socket = new Socket("localhost", 8000);
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new PrintStream(socket.getOutputStream());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// 发送json数据
		public void sendJson(JSONArray json) {
			out.println(json.toString());
		}

//		接收json数据
		public JSONArray receiveJson() {
			try {
				return JSONArray.parseArray(in.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

//	关闭所有接口
	public void closeAll() {
		try {
			socket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

	}

}
