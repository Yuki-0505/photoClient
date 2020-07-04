package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.Client;
import service.Register;
import utils.PropertiesWrite;

public class RegisterView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lblUser;
	private JLabel lblPassword;
	private JLabel lblRepeatPw;
	private JTextField txtUser;
	private JPasswordField txtPassword;
	private JPasswordField txtRepeatPw;
	private JButton btnOk;
	private JButton btnClear;
	/**
	 * Create the frame.
	 */
	public RegisterView() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		setBounds(screenSize.width / 2 - 160, screenSize.height / 2 - 115, 450, 300);
		setSize(320, 210);
		setResizable(false);
		setLayout(null);
		setTitle("---注册---");

		lblUser = new JLabel("User:");
		lblPassword = new JLabel("Password:");
		lblRepeatPw = new JLabel("Repeat:");
		txtUser = new JTextField();
		txtPassword = new JPasswordField();
		txtRepeatPw = new JPasswordField();
		btnOk = new JButton("确定");
		btnClear = new JButton("清除");

		lblUser.setBounds(50, 20, 100, 20);
		add(lblUser);
		txtUser.setBounds(160, 20, 100, 20);
		add(txtUser);
		lblPassword.setBounds(50, 60, 100, 20);
		add(lblPassword);
		txtPassword.setBounds(160, 60, 100, 20);
		add(txtPassword);
		lblRepeatPw.setBounds(50, 100, 100, 20);
		add(lblRepeatPw);
		txtRepeatPw.setBounds(160, 100, 100, 20);
		add(txtRepeatPw);
		btnOk.setBounds(50, 140, 80, 20);
		add(btnOk);
		btnClear.setBounds(180, 140, 80, 20);
		add(btnClear);

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
				txtRepeatPw.setText("");
			}
		});
		txtRepeatPw.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
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
		String repeatPw = new String(txtRepeatPw.getPassword());
		if (name.equals("")) {
			JOptionPane.showMessageDialog(null, "用户名不能为空！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		if (!check(password)) {
			JOptionPane.showMessageDialog(null, "密码必须由字母、数字组成，区分大小写\n密码长度为8-18位", "提示信息", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		if (!password.equals(repeatPw)) {
			JOptionPane.showMessageDialog(null, "两次输入的密码不相同！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		if(Client.netCheck() == false) {
			JOptionPane.showMessageDialog(null, "网络异常！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		int status = new Register().work(name, password);
		if (status == 0) {
			JOptionPane.showMessageDialog(null, "用户名已存在！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(null, "注册成功！", "提示信息", JOptionPane.PLAIN_MESSAGE);
//		clearUserProperties();
//		new LoginView();
		setVisible(false);
	}

	public boolean check(String password) {
		return password.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]{8,18}$");
	}
	
//	public void clearUserProperties() {
//		PropertiesWrite pw = new PropertiesWrite();
//		pw.clear();
//		pw.close();
//	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
//		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//				| UnsupportedLookAndFeelException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					new RegisterView();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}
}
