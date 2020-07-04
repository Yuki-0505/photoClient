package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.Client;
import service.DeleteImage;
import service.QueryAllGroup;
import service.UpdateImageGroup;

public class ImageRightClickMenu extends JPopupMenu {

	private JButton btnImage;
	private JMenuItem jmiDel;
	private JMenuItem jmiDownload;
	private JMenuItem jmiMove;
	private JSONObject json;
	private String uid;
	private int gid;
	private long timestamp;
	private String name;
	private byte[] imageBytes;

	public ImageRightClickMenu(JButton btnImage, JSONObject json) {
		this.btnImage = btnImage;
		this.json = json;
//		获取图片属性
		this.uid = json.getString("uid");
		this.gid = json.getIntValue("gid");
		this.timestamp = json.getLongValue("timestamp");
		this.name = json.getString("name");
		this.imageBytes = json.getBytes("image");
//		添加一级菜单
		JMenuItem miDel = new JMenuItem("删除");
		add(miDel);
		JMenuItem miDown = new JMenuItem("下载");
		add(miDown);
		JMenu miMove = new JMenu("移动至");
		add(miMove);
//		监听删除菜单
		miDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Client.netCheck() == false) {
					JOptionPane.showMessageDialog(null, "网络异常！", "提示信息", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				int status = new DeleteImage().work(uid, gid, timestamp);
				if (status == 1) {
					btnImage.setIcon(null);
					JOptionPane.showMessageDialog(null, "删除成功！", "提示信息", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "删除失败！", "提示信息", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
//		监听下载菜单
		miDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DownloadToLocalView(name, imageBytes);
			}
		});
//		监听移动图片事件
		JSONArray jsonArr = new QueryAllGroup().work(uid);
		for (int i = 0; i < jsonArr.size(); i++) {
			int newgid = jsonArr.getJSONObject(i).getIntValue("gid");
//			创建二级菜单
			if (gid == newgid)
				continue;
			JMenuItem mi = new JMenuItem(jsonArr.getJSONObject(i).getString("gname"));
			miMove.add(mi);
//			监听二级菜单
			mi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (Client.netCheck() == false) {
						JOptionPane.showMessageDialog(null, "网络异常！", "提示信息", JOptionPane.PLAIN_MESSAGE);
						return;
					}
					int status = new UpdateImageGroup().work(uid, gid, timestamp, newgid);
					if (status == 1) {
						btnImage.setIcon(null);
						JOptionPane.showMessageDialog(null, "移动成功,请刷新！", "提示信息", JOptionPane.PLAIN_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "移动失败！", "提示信息", JOptionPane.PLAIN_MESSAGE);
					}

				}
			});
		}
	}

}
