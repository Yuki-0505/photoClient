package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.Client;
import service.DeleteImage;
import service.QueryAllGroup;
import service.QueryByTyte;
import service.SaveImage;
import service.UpdateImageGroup;
import utils.ErrorLog;
import utils.ImageFileToBytes;
import utils.MyImageFilter;
import utils.PropertiesRead;
import utils.PropertiesWrite;

public class Start extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu jmUser;
	private JMenu jmStyle;
	private JMenu jmGroup;
	private JTabbedPane favorites;
	private JPanel jpImage;

	private int offset;

	/**
	 * Create the frame.
	 */
	public Start() {
//		读取配置文件中的窗体风格并设置
		PropertiesRead pr = new PropertiesRead();
		String LookAndFeel = pr.getProperty("style");
		pr.close();
		try {
			UIManager.setLookAndFeel(LookAndFeel);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			ErrorLog.log(e);
		}
//		设置主窗体JFrmae的属性
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		setBounds(screenSize.width / 2 - 512, screenSize.height / 2 - 360, 1024, 720);
		setTitle("相册管理器");
//		设置菜单栏
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
//		设置中间面板
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
//		用户操作菜单
		jmUser = new JMenu();
		menuBar.add(jmUser);
//		收藏夹操作菜单
		jmGroup = new JMenu("收藏夹");
		menuBar.add(jmGroup);
		setGroup();
//		窗口风格选择菜单
		jmStyle = new JMenu("窗口风格");
		menuBar.add(jmStyle);
		setStyleSelectionMenu();

		TimerTask task = new TimerTask() {
			public void run() {
				checkLoginStatus();
				setGroup();
			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 2000);

		favorites = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(favorites, BorderLayout.CENTER);
		offset = 0;
		setOpenFavorites(1, "默认");

		setResizable(false);
		setVisible(true);
	}

	public void setOpenFavorites(int gid, String name) {
		PropertiesRead pr = new PropertiesRead();
		if (!pr.containsKey("uid"))
			return;
		String uid = pr.getProperty("uid");
		pr.close();

		favorites.removeAll();
		JPanel jp = new JPanel();
		jp.setLayout(null);
		jpImage = new JPanel();
		jpImage.setLayout(null);
		jpImage.setBounds(0, 0, 900, 620);
		jp.add(jpImage);
		JButton btnPrevious = new JButton("上一页");
		btnPrevious.setBounds(900, 60, 70, 30);
		jp.add(btnPrevious);
		JButton btnNext = new JButton("下一页");
		btnNext.setBounds(900, 210, 70, 30);
		jp.add(btnNext);
		JButton btnUpload = new JButton("上传");
		btnUpload.setBounds(900, 360, 70, 30);
		jp.add(btnUpload);
		JButton btnRefresh = new JButton("刷新");
		btnRefresh.setBounds(900, 510, 70, 30);
		jp.add(btnRefresh);
		showImage(new QueryByTyte().work(uid, gid, offset));

		btnPrevious.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (offset - 12 >= 0) {
					offset -= 12;
					JSONArray jsonArr = new QueryByTyte().work(uid, gid, offset);
					showImage(jsonArr);
					jsonArr = null;
				} else {
					JOptionPane.showMessageDialog(null, "已经是第一页了哦QWQ！", "提示信息", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JSONArray jsonArr = new QueryByTyte().work(uid, gid, offset + 12);
				if (jsonArr.size() == 0) {
					JOptionPane.showMessageDialog(null, "已经是最后一页了哦QWQ！", "提示信息", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				offset += 12;
				showImage(jsonArr);
				jsonArr = null;
			}
		});
		btnUpload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setFileFilter(new MyImageFilter());
				chooser.setMultiSelectionEnabled(true);
				chooser.showDialog(new JLabel(), "选择");
				File[] files = chooser.getSelectedFiles();
				ProgressBarView pgs = new ProgressBarView();
				new Thread(pgs).start();
				new Thread() {
					public void run() {
						for (int i = 0; i < files.length; i++) {
							byte[] image = ImageFileToBytes.getBytes(files[i]);
							new SaveImage().work(uid, gid, files[i].getName(), image);
							int value = (int) (100 * (i + 1) / files.length);
							pgs.setValue(value);
							if(value == 100) {
								favorites.remove(jp);
								setOpenFavorites(gid, name);
							}
						}
					}
				}.start();
			}
		});
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				favorites.remove(jp);
				setOpenFavorites(gid, name);
			}
		});
		favorites.addTab(name, null, jp, null);

		System.gc();
	}

//	展示从制定序号开始的24张图片
	public void showImage(JSONArray jsonArr) {
		jpImage.removeAll();
		Flag: for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				if (4 * i + j >= jsonArr.size())
					break Flag;
				final JSONObject json = jsonArr.getJSONObject(4 * i + j);

				ImageIcon image = new ImageIcon(json.getBytes("image"));
				int hight = image.getIconHeight();
				int width = image.getIconWidth();
				if (hight > width) {
					hight = 180 * hight / width;
					width = 180;
				} else {
					width = 180 * width / hight;
					hight = 180;
				}
				JButton btnImg = new JButton();
				jpImage.add(btnImg);
				Image img = image.getImage().getScaledInstance(width, hight, Image.SCALE_AREA_AVERAGING);
				ImageIcon icon = new ImageIcon(img);
				btnImg.setIcon(icon);
				btnImg.setBounds(40 + 210 * j, 15 + 200 * i, 180, 180);
				btnImg.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (e.getButton() == e.BUTTON1) {
							new ShowOneImageView(image);
						} else if (e.getButton() == e.BUTTON3) {
							addRightClickMenu(btnImg, e, json);
						}
					}
				});
			}
		}
		this.update(this.getGraphics());
	}

	public void addRightClickMenu(JButton btnImg, MouseEvent e, JSONObject json) {
		long timestamp = json.getLongValue("timestamp");
		String uid = json.getString("uid");
		int gid = json.getIntValue("gid");
		String name = json.getString("name");
		byte[] imgBytes = json.getBytes("image");
		JPopupMenu ppm = new JPopupMenu();
		JMenuItem miDel = new JMenuItem("删除");
		JMenuItem miDown = new JMenuItem("下载");
		JMenu miMove = new JMenu("移动至");
		JSONArray jsonArr = new QueryAllGroup().work(uid);
		for (int i = 0; i < jsonArr.size(); i++) {
			JMenuItem mi = new JMenuItem(jsonArr.getJSONObject(i).getString("gname"));
			int newgid = jsonArr.getJSONObject(i).getIntValue("gid");
			miMove.add(mi);
			mi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (Client.netCheck() == false) {
						JOptionPane.showMessageDialog(null, "网络异常！", "提示信息", JOptionPane.PLAIN_MESSAGE);
						return;
					}
					int status = new UpdateImageGroup().work(uid, gid, timestamp, newgid);
					if (status == 1) {
						new Thread() {
							public void run() {
								showImage(new QueryByTyte().work(uid, gid, offset));
							}
						}.start();
						JOptionPane.showMessageDialog(null, "移动成功！", "提示信息", JOptionPane.PLAIN_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "移动失败！", "提示信息", JOptionPane.PLAIN_MESSAGE);
					}
					
				}
			});
		}
		ppm.add(miDel);
		ppm.add(miDown);
		ppm.add(miMove);
		btnImg.add(ppm);
		ppm.show(btnImg, e.getX(), e.getY());
		miDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Client.netCheck() == false) {
					JOptionPane.showMessageDialog(null, "网络异常！", "提示信息", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				int status = new DeleteImage().work(uid, gid, timestamp);
				if (status == 1) {
					new Thread() {
						public void run() {
							showImage(new QueryByTyte().work(uid, gid, offset));
						}
					}.start();
					JOptionPane.showMessageDialog(null, "删除成功！", "提示信息", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "删除失败！", "提示信息", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		miDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				downloadToLocal(name, imgBytes);
			}
		});
	}

	public void downloadToLocal(String name, byte[] imgBytes) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setMultiSelectionEnabled(true);
		chooser.setCurrentDirectory(new File("/home/yuki/Pictures/image"));
		chooser.setSelectedFile(new File(name));
		chooser.setFileFilter(new MyImageFilter());
		chooser.showDialog(new JLabel(), "选择");
		File file = chooser.getSelectedFile();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(imgBytes);
			fos.close();
		} catch (Exception e1) {
			ErrorLog.log(e1);
		}
	}

//	设置对收藏夹操作的菜单
	public void setGroup() {
		jmGroup.removeAll();
		JMenuItem jmCreate = new JMenuItem("新建收藏夹");
		jmGroup.add(jmCreate);
		JMenu jmOpen = new JMenu("打开收藏夹");
		jmGroup.add(jmOpen);
		JMenu jmDelete = new JMenu("删除收藏夹");
		jmGroup.add(jmDelete);

		PropertiesRead pr = new PropertiesRead();
		if (Client.netCheck() == false) {
			JOptionPane.showMessageDialog(null, "网络异常！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
			return;
		}
		if (!pr.containsKey("uid")) {
			JOptionPane.showMessageDialog(null, "您还未登录！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		String uid = pr.getProperty("uid");
		pr.close();

		jmCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CreateGroupView();
			}
		});
		JSONArray jsonArr = new QueryAllGroup().work(uid);
		for (int i = 0; i < jsonArr.size(); i++) {
			String gname = jsonArr.getJSONObject(i).getString("gname");
			int gid = jsonArr.getJSONObject(i).getIntValue("gid");
			JMenuItem jmiOpen = new JMenuItem(gname);
			jmOpen.add(jmiOpen);
			JMenuItem jmiDelete = new JMenuItem(gname);
			jmDelete.add(jmiDelete);
			jmiOpen.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					offset = 0;
					setOpenFavorites(gid, gname);
				}
			});
			jmiDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new DeleteGroupView(uid, gid);
				}
			});
		}
		revalidate();
	}

//	设置选择窗体风格的菜单
	public void setStyleSelectionMenu() {
		HashMap<String, String> style = new HashMap<String, String>();
		style.put("Noire", "com.jtattoo.plaf.noire.NoireLookAndFeel");
		style.put("Smart", "com.jtattoo.plaf.smart.SmartLookAndFeel");
		style.put("Mint", "com.jtattoo.plaf.mint.MintLookAndFeel");
		style.put("McWin", "com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		style.put("Luna", "com.jtattoo.plaf.luna.LunaLookAndFeel");
		style.put("HiFi", "com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		style.put("Fast", "com.jtattoo.plaf.fast.FastLookAndFeel");
		style.put("Bernstein", "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
		style.put("Aero", "com.jtattoo.plaf.aero.AeroLookAndFeel");
		style.put("Graphite", "com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		for (String key : (Set<String>) style.keySet()) {
			JMenuItem jmi = new JMenuItem(key);
			jmStyle.add(jmi);
			jmi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String lookAndFeel = (String) style.get(jmi.getText());
					PropertiesWrite pw = new PropertiesWrite();
					pw.put("style", lookAndFeel);
					pw.close();
					JOptionPane.showMessageDialog(null, "程序将关闭，请重启以使设置生效！", "提示信息", JOptionPane.PLAIN_MESSAGE);
					System.exit(0);
				}
			});
		}
	}

	public void updateGUI() {
		this.update(this.getGraphics());
	}

//	设置未登录时的菜单
	public void setNotLogin() {
		ImageIcon image = new ImageIcon("./images/not_image.jpg");
		Image img = image.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);// 第三个值可以去查api是图片转化的方式
		ImageIcon icon = new ImageIcon(img);
		jmUser.setText("用户");
		jmUser.removeAll();

		JMenuItem jmiLogin = new JMenuItem("登录");
		jmUser.add(jmiLogin);

		JMenuItem jmiRegister = new JMenuItem("注册");
		jmUser.add(jmiRegister);

		jmUser.setIcon(icon);

		revalidate();

		jmiLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoginView();
			}
		});
		jmiRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RegisterView();
			}
		});
	}

//	设置登录后的界面
	public void setLogin() {
		ImageIcon icon = null;
		String name = null;
		try {
			PropertiesRead pr = new PropertiesRead();
			String imgPath = "./images/not_image.jpg";
			if (pr.containsKey("imgPath"))
				imgPath = pr.getProperty("imgPath");
			ImageIcon image = new ImageIcon(imgPath);
			Image img = image.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);// 第三个值可以去查api是图片转化的方式
			icon = new ImageIcon(img);
			name = pr.getProperty("name");
			pr.close();
		} catch (Exception e) {
			ErrorLog.log(e);
		}
		jmUser.setText(name);
		jmUser.removeAll();

		JMenuItem jmiUpdateUser = new JMenuItem("修改信息");
		jmUser.add(jmiUpdateUser);

		JMenuItem jmiSignOut = new JMenuItem("退出登录");
		jmUser.add(jmiSignOut);
		jmUser.setIcon(icon);
		revalidate();

		jmiUpdateUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UpdateUserView();
			}
		});
		jmiSignOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PropertiesWrite pw = new PropertiesWrite();
				pw.clear();
				pw.close();
			}
		});
	}

//	检测并设置登录状态
	public void checkLoginStatus() {
		try {
			PropertiesRead pr = new PropertiesRead();
			if (!pr.containsKey("uid")) {
				setNotLogin();
			} else {
				setLogin();
			}
			pr.close();
		} catch (Exception e) {
			ErrorLog.log(e);
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start frame = new Start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
