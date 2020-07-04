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
	private JMenu jmFavorites;
	private JMenu jmStyle;
	private JTabbedPane tabFavorites;
	
	public Start() {
//		读取配置文件中的窗体风格并设置
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
		tabFavorites = new JTabbedPane(JTabbedPane.TOP);
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
	}
}
