package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import utils.ErrorLog;
import utils.ImageFileToBytes;
import utils.PropertiesRead;

public class HomepageView extends JFrame {

	private JButton btnAvatar;
	private JLabel lblName;
	private JLabel lblUid;
	private JLabel lblSignature;
	private JTextField txtName;
	private JTextField txtUid;
	private JTextField txtSignature;
	
	public HomepageView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		setBounds(screenSize.width / 2 - 180, screenSize.height / 2 - 155, 450, 250);
		setResizable(false);
		setLayout(null);
		setTitle("---个人主页---");
		
		PropertiesRead pr = new PropertiesRead();
		String uid = pr.getProperty("uid");
		String name = pr.getProperty("name");
		String signature = pr.getProperty("signature");
		String avatarPath = "./images/notimage.jpg";
		if(pr.containsKey("avatarPath")) {
			avatarPath = pr.getProperty("avatarPath");
		}
		byte[] avatar = ImageFileToBytes.getBytes(new File(avatarPath));
		
		btnAvatar = new JButton();
		setImage(new ImageIcon(avatar));
		lblName = new JLabel("用户名:");
		lblUid = new JLabel("用户ID:" );
		lblSignature = new JLabel("个性签名:");
		txtName = new JTextField(name);
		txtUid = new JTextField(uid);
		txtSignature = new JTextField(signature);
		lblName.setBounds(210, 20, 220, 25);
		txtName.setBounds(210, 45, 220, 30);
		lblUid.setBounds(210, 75, 220, 25);
		txtUid.setBounds(210, 100, 220, 30);
		lblSignature.setBounds(210, 130, 220, 25);
		txtSignature.setBounds(210, 155, 220, 30);
		add(lblName);
		add(lblUid);
		add(lblSignature);
		add(txtName);
		add(txtUid);
		add(txtSignature);
		
		setVisible(true);
	}
	
	public void setImage(ImageIcon image) {
		int hight = image.getIconHeight();
		int width = image.getIconWidth();
		if (hight > width) {
			hight = 180 * hight / width;
			width = 180;
		} else {
			width = 180 * width / hight;
			hight = 180;
		}
		Image img = image.getImage().getScaledInstance(width, hight, Image.SCALE_AREA_AVERAGING);
		ImageIcon icon = new ImageIcon(img);
		btnAvatar.setIcon(icon);
		btnAvatar.setBounds(20, 20, 180, 180);
		add(btnAvatar);
		btnAvatar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new OneImageView(image);
			}
		});
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			ErrorLog.log(e);
		}
		new HomepageView();
	}

}
