package view;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import utils.PropertiesRead;
import utils.PropertiesWrite;

public class UserMenu extends JMenu {

	public UserMenu() {
		checkUserStatus();
	}
	
	public void checkUserStatus() {
		PropertiesRead pr = new PropertiesRead();
		if (!pr.containsKey("uid")) {
			notLoginedIn();
		} else {
			logined();
		}
		pr.close();
	}

	public void notLoginedIn() {
//		设置图标和标题
		ImageIcon image = new ImageIcon("./images/not_image.jpg");
		Image img = image.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);// 第三个值可以去查api是图片转化的方式
		ImageIcon icon = new ImageIcon(img);
		setIcon(icon);
		setText("用户");
//		设置一级菜单
		JMenuItem jmiLogin = new JMenuItem("登录");
		add(jmiLogin);
		JMenuItem jmiRegister = new JMenuItem("注册");
		add(jmiRegister);

		jmiLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame f = new LoginView();
				new Thread() {
					public void run() {
						while(f.isVisible()) {
							System.out.print("");
						}
						f.dispose();
						removeAll();
						checkUserStatus();
					}
				}.start();
			}
		});
		jmiRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame f = new RegisterView();
				new Thread() {
					public void run() {
						while(f.isVisible()) {
							System.out.print("");
						}
						f.dispose();
						removeAll();
						checkUserStatus();
					}
				}.start();
			}
		});
		update(getGraphics());
	}

	public void logined() {
//		读取用户配置文件设置图标和标题
		PropertiesRead pr = new PropertiesRead();
		String avatarPath = "./images/not_image.jpg";
		if (pr.containsKey("avatarPath"))
			avatarPath = pr.getProperty("avatarPath");
		ImageIcon image = new ImageIcon(avatarPath);
		Image img = image.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);// 第三个值可以去查api是图片转化的方式
		ImageIcon icon = new ImageIcon(img);
		String name = pr.getProperty("name");
		pr.close();
		setIcon(icon);
		setText(name);
//		设置一级菜单
		JMenuItem jmiHomepage = new JMenuItem("个人主页");
		add(jmiHomepage);
		JMenuItem jmiUpdateUser = new JMenuItem("修改信息");
		add(jmiUpdateUser);
		JMenuItem jmiSignOut = new JMenuItem("退出登录");
		add(jmiSignOut);
		
		jmiHomepage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new HomepageView();
			}
		});
		jmiUpdateUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame f = new UpdateUserView();
				new Thread() {
					public void run() {
						while(f.isVisible()) {
							System.out.print("");
						}
						f.dispose();
						removeAll();
						checkUserStatus();
					}
				}.start();
			}
		});
		jmiSignOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PropertiesWrite pw = new PropertiesWrite();
				pw.clear();
				pw.close();
				removeAll();
				checkUserStatus();
			}
		});
		update(getGraphics());
	}

}
