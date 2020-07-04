package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.Client;
import service.UpdateAvatar;
import service.UpdateSignature;
import service.UpdateUserInfo;
import utils.ImageBytesToFile;
import utils.ErrorLog;
import utils.GetFileSuffix;
import utils.ImageFileToBytes;
import utils.MyImageFilter;
import utils.PropertiesRead;
import utils.PropertiesWrite;

public class UpdateUserView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel lblUser;
	private JLabel lblPassword;
	private JLabel lblSignature;
	private JLabel lblAvatar;
	private JLabel lblInfo;
	private JTextField txtUser;
	private JPasswordField txtPassword;
	private JTextField txtSignature;
	private JTextField txtAvatar;
	private JButton btnSelect;
	private JButton btnOk;
	private JButton btnClear;

	/**
	 * Create the frame.
	 */
	public UpdateUserView() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		setBounds(screenSize.width / 2 - 160, screenSize.height / 2 - 175, 360, 300);
		setResizable(false);
		setLayout(null);
		setTitle("---修改信息---");

		lblUser = new JLabel("用户名:");
		lblPassword = new JLabel("密码:");
		lblSignature = new JLabel("个性签名:");
		lblAvatar = new JLabel("头像:");
		txtUser = new JTextField(20);
		txtPassword = new JPasswordField(20);
		txtSignature = new JTextField(255);
		txtAvatar = new JTextField(255);
		lblInfo = new JLabel("*不填的选项则表示不修改*");
		btnSelect = new JButton("选择");
		btnOk = new JButton("确定");
		btnClear = new JButton("清空");

		lblUser.setBounds(50, 20, 100, 20);
		add(lblUser);
		txtUser.setBounds(160, 20, 140, 20);
		add(txtUser);
		lblPassword.setBounds(50, 60, 100, 20);
		add(lblPassword);
		txtPassword.setBounds(160, 60, 140, 20);
		add(txtPassword);
		lblSignature.setBounds(50, 100, 100, 20);
		add(lblSignature);
		txtSignature.setBounds(160, 100, 140, 20);
		add(txtSignature);
		lblAvatar.setBounds(50, 140, 100, 20);
		add(lblAvatar);
		txtAvatar.setBounds(160, 140, 80, 20);
		add(txtAvatar);
		btnSelect.setBounds(240, 140, 60, 20);
		add(btnSelect);
		lblInfo.setBounds(50, 180, 200, 20);
		add(lblInfo);
		btnOk.setBounds(50, 220, 80, 20);
		add(btnOk);
		btnClear.setBounds(220, 220, 80, 20);
		add(btnClear);

		listen();

		setVisible(true);
	}

	public void listen() {
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					okAction();
				} catch (IOException e1) {
					ErrorLog.log(e1);
				}
			}
		});

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUser.setText("");
				txtPassword.setText("");
				txtSignature.setText("");
				txtAvatar.setText("");
			}
		});
		btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setFileFilter(new MyImageFilter());
				chooser.setMultiSelectionEnabled(false);
				chooser.showDialog(new JLabel(), "选择");
				File file = chooser.getSelectedFile();
				if(file == null) return;
				txtAvatar.setText(file.getAbsolutePath());
			}
		});
	}

	public void okAction() throws IOException {
		String name = txtUser.getText();
		String password = new String(txtPassword.getPassword());
		String signature = txtSignature.getText();
		String imagePath = txtAvatar.getText();
		if (Client.netCheck() == false) {
			JOptionPane.showMessageDialog(null, "网络异常！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		PropertiesRead pr = new PropertiesRead();
		String uid = pr.getProperty("uid");
		if("".equals(name)) name = pr.getProperty("name");
		if("".equals(password)) password = pr.getProperty("password");
		int status = new UpdateUserInfo().work(uid, name, password);
		pr.close();
		PropertiesWrite pw = new PropertiesWrite();
		if (status == 0) {
			JOptionPane.showMessageDialog(null, "用户名已存在！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		writeUser(name, password);
		if(!"".equals(signature)) {
			status = new UpdateSignature().work(uid, signature);
			pw.put("signature", signature);
		}
		if(!"".equals(imagePath)) {
			File file = new File(imagePath);
			byte[] image = ImageFileToBytes.getBytes(file);
			status = new UpdateAvatar().work(uid, file.getName(), image);
			String avatarPath = "./images/" + "user." + GetFileSuffix.get(file.getName());
			ImageBytesToFile.save(new File(avatarPath), image);
			pw.setProperty("avatarPath", avatarPath);
		}
		pw.close();
		JOptionPane.showMessageDialog(null, "修改成功！", "提示信息", JOptionPane.PLAIN_MESSAGE);
		setVisible(false);
	}

//	用户信息保存至配置文件
	public void writeUser(String name, String password) {
		PropertiesWrite proio = new PropertiesWrite();
		proio.setProperty("name", name);
		proio.setProperty("password", password);
		proio.close();
	}

	public static void main(String[] args) {
		new UpdateUserView();
	}

}
