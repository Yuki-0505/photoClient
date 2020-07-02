package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.Client;
import service.CreateGroup;
import utils.PropertiesRead;

public class CreateGroupView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JLabel lblName;
	private JTextField txtName;
	private JButton btnOk;
	private JButton btnClear;

	/**
	 * Create the frame.
	 */
	public CreateGroupView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		setBounds(screenSize.width / 2 - 160, screenSize.height / 2 - 65, 320, 130);
		setResizable(false);
		setLayout(null);
		setTitle("---新建收藏夹---");

		lblName = new JLabel("Name:");
		txtName = new JTextField(20);
		btnOk = new JButton("确定");
		btnClear = new JButton("清空");

		lblName.setBounds(50, 20, 100, 20);
		add(lblName);
		txtName.setBounds(160, 20, 100, 20);
		add(txtName);
		btnOk.setBounds(50, 60, 80, 20);
		add(btnOk);
		btnClear.setBounds(180, 60, 80, 20);
		add(btnClear);
		
		listen();
		setVisible(true);
	}
	
	public void listen() {
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				okAction();
			}
		});
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtName.setText("");
			}
		});
		txtName.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					okAction();
				}
			}
		});
	}
	
	private void okAction() {
		String name = txtName.getText();
		if("".equals(name) == true) {
			JOptionPane.showMessageDialog(null, "收藏夹名不能为空！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		if (Client.netCheck() == false) {
			JOptionPane.showMessageDialog(null, "网络异常！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		PropertiesRead pr = new PropertiesRead();
		if(!pr.containsKey("uid")) {
			JOptionPane.showMessageDialog(null, "请先登录！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		int status = new CreateGroup().work(pr.getProperty("uid"), name);
		pr.close();
		if(status == 1) {
			JOptionPane.showMessageDialog(null, "收藏夹创建成功！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, "该名称已存在！", "提示信息", JOptionPane.PLAIN_MESSAGE);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateGroupView frame = new CreateGroupView();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
