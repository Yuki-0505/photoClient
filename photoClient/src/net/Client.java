package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public Client() {
		try {
			this.socket = new Socket("localhost", 8000);
			this.oos = new ObjectOutputStream(socket.getOutputStream());
			this.ois = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

//	获取输出流
	public ObjectOutputStream getOutputString() {
		return oos;
	}

//	获取输入流
	public ObjectInputStream getInputStream() {
		return ois;
	}

//	关闭所有接口
	public void closeAll() {
		try {
			socket.close();
			oos.close();
			ois.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

	}

}
