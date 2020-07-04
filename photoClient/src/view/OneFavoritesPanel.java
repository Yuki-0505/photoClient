package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.alibaba.fastjson.JSONArray;

import service.QueryByTyte;
import service.SaveImage;
import utils.ImageFileToBytes;
import utils.MyImageFilter;
import utils.PropertiesRead;

public class OneFavoritesPanel extends JPanel {

	private String uid;
	private int gid;
	private JPanel jpPage;
	private JButton btnPrevious;
	private JButton btnNext;
	private JButton btnUpload;
	private JButton btnRefresh;
	private JSONArray jsonArr;
	private int offset;
	private int count;

	public OneFavoritesPanel(int gid) {
		this.gid = gid;
		PropertiesRead pr = new PropertiesRead();
		this.uid = pr.getProperty("uid");
		pr.close();
		this.offset = 0;
//		设置样式
		setLayout(null);
//		添加按钮
		btnPrevious = new JButton("上一页");
		btnPrevious.setBounds(900, 60, 70, 30);
		add(btnPrevious);
		btnNext = new JButton("下一页");
		btnNext.setBounds(900, 210, 70, 30);
		add(btnNext);
		btnUpload = new JButton("上传");
		btnUpload.setBounds(900, 360, 70, 30);
		add(btnUpload);
		btnRefresh = new JButton("刷新");
		btnRefresh.setBounds(900, 510, 70, 30);
		add(btnRefresh);
//		设置单页图片区域面板
		jsonArr = new QueryByTyte().work(uid, gid, offset);
		updatePanel();

		btnPrevious.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setUid();
				if (offset - 12 >= 0) {
					offset -= 12;
					jsonArr = new QueryByTyte().work(uid, gid, offset);
					remove(jpPage);
					updatePanel();
				} else {
					JOptionPane.showMessageDialog(null, "已经是第一页了哦QWQ！", "提示信息", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setUid();
				jsonArr = new QueryByTyte().work(uid, gid, offset + 12);
				if (jsonArr.size() == 0) {
					JOptionPane.showMessageDialog(null, "已经是最后一页了哦QWQ！", "提示信息", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				offset += 12;
				remove(jpPage);
				updatePanel();
			}
		});
		btnUpload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (setUid() == false) {
					JOptionPane.showMessageDialog(null, "请先登录哦QWQ！", "提示信息", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setFileFilter(new MyImageFilter());
				chooser.setMultiSelectionEnabled(true);
				chooser.showDialog(new JLabel(), "选择");
				File[] files = chooser.getSelectedFiles();
				if (files.length == 0)
					return;
				ProgressBarView pgs = new ProgressBarView();
				new Thread(pgs).start();
				new Thread() {
					public void run() {
						for (int i = 0; i < files.length; i++) {
							byte[] image = ImageFileToBytes.getBytes(files[i]);
							new SaveImage().work(uid, gid, files[i].getName(), image);
							int value = (int) (100 * (i + 1) / files.length);
							pgs.setValue(value);
							if (value == 100) {
								jsonArr = new QueryByTyte().work(uid, gid, offset);
								remove(jpPage);
								updatePanel();
							}
						}
					}
				}.start();
			}
		});
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setUid();
				jsonArr = new QueryByTyte().work(uid, gid, offset);
				remove(jpPage);
				updatePanel();
			}
		});
	}

	public void updatePanel() {
		jpPage = new OnePagePanel(jsonArr);
		add(jpPage);

		count = 0;
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				update(getGraphics());
				count++;
				if (count >= 12) {
					timer.cancel();
				}
			}
		};
		timer.scheduleAtFixedRate(task, 0, 500);
	}

	public boolean setUid() {
		PropertiesRead pr = new PropertiesRead();
		if (pr.containsKey("uid") == false) {
			this.uid = null;
			return false;
		}
		this.uid = pr.getProperty("uid");
		pr.close();
		return true;
	}
}
