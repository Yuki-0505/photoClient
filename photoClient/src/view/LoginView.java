package view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.alibaba.fastjson.JSONObject;

import net.Client;
import service.Login;
import utils.GetFileSuffix;
import utils.ImageBytesToFile;
import utils.PropertiesRead;
import utils.PropertiesWrite;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lblUser;
	private JLabel lblPassword;
	private JTextField txtUser;
	private JPasswordField txtPassword;
	private JButton btnOk;
	private JButton btnClear;

	/**
	 * Create the frame.
	 */
	public LoginView() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		setBounds(screenSize.width / 2 - 160, screenSize.height / 2 - 95, 320, 170);
		setResizable(false);
		setLayout(null);
		setTitle("---登录---");

		lblUser = new JLabel("User:");
		lblPassword = new JLabel("Password:");
		txtUser = new JTextField(20);
		txtPassword = new JPasswordField(20);
		btnOk = new JButton("确定");
		btnClear = new JButton("清空");

		lblUser.setBounds(50, 20, 100, 20);
		add(lblUser);
		txtUser.setBounds(160, 20, 100, 20);
		add(txtUser);
		lblPassword.setBounds(50, 60, 100, 20);
		add(lblPassword);
		txtPassword.setBounds(160, 60, 100, 20);
		add(txtPassword);
		btnOk.setBounds(50, 100, 80, 20);
		add(btnOk);
		btnClear.setBounds(180, 100, 80, 20);
		add(btnClear);

		readUser();
		listen();

		setVisible(true);
	}

	public void listen() {
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okAction();
			}
		});

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUser.setText("");
				txtPassword.setText("");
			}
		});
		txtPassword.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					okAction();
				}
			}
		});
	}

	public void okAction() {
		String name = txtUser.getText();
		String password = new String(txtPassword.getPassword());
		if (name.equals("") || password.equals("")) {
			JOptionPane.showMessageDialog(null, "用户名或密码不能为空！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		if (Client.netCheck() == false) {
			JOptionPane.showMessageDialog(null, "网络异常！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		JSONObject json = new Login().work(name, password);
		if (json == null) {
			JOptionPane.showMessageDialog(null, "用户名或密码不正确！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		writeUser(json);
		JOptionPane.showMessageDialog(null, "登录成功！", "提示信息", JOptionPane.PLAIN_MESSAGE);
		setVisible(false);
	}

//	从配置文件读取用户信息并显示到登录界面
	public void readUser() {
		PropertiesRead pr = new PropertiesRead();
		txtUser.setText(pr.getProperty("name"));
		txtPassword.setText(pr.getProperty("password"));
		pr.close();
	}

//	用户信息保存至配置文件
	public void writeUser(JSONObject json) {
		PropertiesWrite pw = new PropertiesWrite();
		pw.setProperty("uid", json.getString("uid"));
		pw.setProperty("name", json.getString("name"));
		pw.setProperty("password", json.getString("password"));

		if (json.containsKey("signature"))
			pw.setProperty("signature", json.getString("signature"));
		else
			pw.remove("signature");
		if (json.containsKey("avatar")) {
			String avatarPath = "./images/" + "user." + GetFileSuffix.get(json.getString("avaname"));
			ImageBytesToFile.save(new File(avatarPath), json.getBytes("avatar"));
			pw.setProperty("avatarPath", avatarPath);
		} else
			pw.remove("avatarPath");
		pw.close();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new LoginView();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
