package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.alibaba.fastjson.JSONArray;

import net.Client;
import service.QueryAllGroup;
import utils.PropertiesRead;

public class FavoritesMenu extends JMenu {
	private JMenuItem jmCreate;
	private JMenuItem jmOpen;
	private JMenuItem jmDelete;
	private JTabbedPane tabFavorites;
	private HashMap<Integer, JPanel> openFavoritesMap;

	public FavoritesMenu(JTabbedPane tabFavorites) {
		this.tabFavorites = tabFavorites;
		this.openFavoritesMap = new HashMap<Integer, JPanel>();
		setFirstLevelMenu();
		
		PropertiesRead pr = new PropertiesRead();
		if(pr.containsKey("uid") == false) {
			pr.close();
			return;
		}
		pr.close();
		JPanel jpanel = new OneFavoritesPanel(1);
		tabFavorites.addTab("默认",jpanel);
		openFavoritesMap.put(1, jpanel);
	}

	public void setFirstLevelMenu() {
		setText("收藏夹");
//		设置一级菜单
		jmCreate = new JMenuItem("新建收藏夹");
		add(jmCreate);
		jmOpen = new JMenu("打开收藏夹");
		add(jmOpen);
		jmDelete = new JMenu("删除收藏夹");
		add(jmDelete);
//		创建收藏夹菜单的监听事件
		jmCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PropertiesRead pr = new PropertiesRead();
				boolean exist = pr.containsKey("uid");
				pr.close();
				if (!exist) {
					JOptionPane.showMessageDialog(null, "您还未登录！", "提示信息", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				JFrame f = new CreateGroupView();
				new Thread() {
					public void run() {
						while (f.isVisible()) {
							System.out.print("");
						}
						f.dispose();
						updateMenu();
					}
				}.start();
			}
		});
		setSecondLevelMenu();
	}

	public void setSecondLevelMenu() {
//		检查网络和用户状态并获取uid
		if (Client.netCheck() == false) 
			return;
		PropertiesRead pr = new PropertiesRead();
		if (pr.containsKey("uid") == false)
			return;
		String uid = pr.getProperty("uid");
		pr.close();
//		打开及删除收藏夹的监听事件
		JSONArray jsonArr = new QueryAllGroup().work(uid);
		for (int i = 0; i < jsonArr.size(); i++) {
//			从json中获取收藏夹名gname和编号gid
			String gname = jsonArr.getJSONObject(i).getString("gname");
			int gid = jsonArr.getJSONObject(i).getIntValue("gid");
//			设置二级菜单
			JMenuItem jmiOpen = new JMenuItem(gname);
			jmOpen.add(jmiOpen);
			JMenuItem jmiDelete = new JMenuItem(gname);
			jmDelete.add(jmiDelete);
//			二级菜单 打开、删除收藏夹 监听事件
			jmiOpen.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (openFavoritesMap.containsKey(gid)) {
						return;
					}
					JPanel jpanel = new OneFavoritesPanel(gid);
					tabFavorites.addTab(gname, jpanel);
					openFavoritesMap.put(gid, jpanel);
					updateMenu();
				}
			});
			jmiDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (gid == 1) {
						JOptionPane.showMessageDialog(null, "默认收藏夹不可被删除哦QWQ！", "提示信息", JOptionPane.PLAIN_MESSAGE);
						return;
					}
					JFrame f = new DeleteGroupView(uid, gid);
					new Thread() {
						public void run() {
							while (f.isVisible()) {
								System.out.print("");
							}
							f.dispose();
							if(openFavoritesMap.containsKey(gid)) {
								tabFavorites.remove(openFavoritesMap.get(gid));
								openFavoritesMap.remove(gid);
							}
							updateMenu();
						}
					}.start();
				}
			});
		}
		update(getGraphics());
	}

	public void updateMenu() {
		removeAll();
		setFirstLevelMenu();
	}
}
