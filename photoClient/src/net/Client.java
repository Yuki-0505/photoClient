package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import utils.ErrorLog;
/*
 * 提供
 * 发送和接收JSONArray的方法
 * 检测网络的方法
 */
public class Client {

	private Socket socket;
	private BufferedReader in;
	private PrintStream out;

	public Client() {
		try {
			this.socket = new Socket("localhost", 8000);
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new PrintStream(socket.getOutputStream());
		} catch (Exception e) {
			ErrorLog.log(e);
		}
	}

// 发送json数据
	public void sendJson(JSONArray json) {
		out.println(json.toString());
	}

//	接收json数据
	public JSONArray receiveJson() {
		try {
			return JSONArray.parseArray(in.readLine());
		} catch (IOException e) {
			ErrorLog.log(e);
		}
		return null;
	}
	
//	检查网络
	public static boolean netCheck() {
		boolean flag = true;
		try {
			Socket socket = new Socket("localhost", 8000);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream out = new PrintStream(socket.getOutputStream());
			out.println("network check");
			if(!"network check".equals(in.readLine())) 
				flag = false;
			socket.close();
		} catch (IOException e) {
			flag =false;
		} 
		return flag;
	}

//	关闭所有接口
	public void closeAll() {
		try {
			socket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			ErrorLog.log(e);
		}
	}
	
	

	public static void main(String[] args) {
		
	}

}
