package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import utils.ErrorLog;
import utils.PropertiesRead;

public class Start extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu jmUser;
	private JMenu jmFavorites;
	private JMenu jmStyle;
	private ClosableTabbedPane tabFavorites;
	
	public Start() {
//		读取配置文件中的窗体风格及图标并设置
		setStyle();
//		设置主窗体JFrmae的属性
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		setBounds(screenSize.width / 2 - 512, screenSize.height / 2 - 360, 1024, 720);
		setResizable(false);
		setVisible(true);
		setTitle("相册管理器");
//		设置菜单栏
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
//		设置中间面板
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
//		设置选项卡面板
		tabFavorites = new ClosableTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabFavorites, BorderLayout.CENTER);
//		用户操作菜单
		jmUser = new UserMenu();
		menuBar.add(jmUser);
//		收藏夹操作菜单
		jmFavorites = new FavoritesMenu(tabFavorites);
		menuBar.add(jmFavorites);
//		窗口风格选择菜单
		jmStyle = new StyleSelectionMenu();
		menuBar.add(jmStyle);
	}

	public void setStyle() {
		PropertiesRead pr = new PropertiesRead();
		String style = "com.jtattoo.plaf.hifi.HiFiLookAndFeel";
		if(pr.containsKey("style"))
			style = pr.getProperty("style");
		pr.close();
		try {
			UIManager.setLookAndFeel(style);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			ErrorLog.log(e);
		}
		ImageIcon imageIcon=new ImageIcon("./images/photos.png");
		setIconImage(imageIcon.getImage());
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
