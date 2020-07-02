package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import service.DeleteGroup;

public class DeleteGroupView extends JFrame {

	private String uid;
	private int gid;
	private JRadioButton jrbYes;
	private JButton btn;

	public DeleteGroupView(String uid, int gid) {
		super();
		this.uid = uid;
		this.gid = gid;
		
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		setBounds(screenSize.width / 2 - 150, screenSize.height / 2 - 50, 400, 150);
		setLayout(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		jrbYes = new JRadioButton("同时删除该收藏夹下所有图片(不勾选则转移到默认收藏夹下)");
		jrbYes.setSelected(true);
		jrbYes.setBounds(10, 10, 480, 50);
		add(jrbYes);

		btn = new JButton("确定");
		btn.setBounds(170, 70, 60, 20);
		add(btn);
		
		listen();
		
	}
	
	public void listen() {
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int flag = jrbYes.isSelected() ? 1 : 0;
				new DeleteGroup().work(uid, gid, flag);
				dispose();
				JOptionPane.showMessageDialog(null, "删除成功！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			}
		});
	}

}
