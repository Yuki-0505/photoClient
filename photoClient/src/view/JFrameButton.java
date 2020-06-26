package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class JFrameButton extends JFrame implements ActionListener {
	JButton windLook = new JButton("Windows 窗口");
	JButton unixLook = new JButton("Unix 窗口");
	JButton javaLook = new JButton("Java 窗口");
	JButton macLook = new JButton("Mac 窗口");
	JButton gtkLook = new JButton("GTK 窗口");
	JLabel label = new JLabel("选择界面的风格");

	public JFrameButton() {
		this.setLayout(new FlowLayout());
		this.add(label);
		this.add(windLook);
		windLook.addActionListener(this);
		this.add(unixLook);
		unixLook.addActionListener(this);
		this.add(javaLook);
		javaLook.addActionListener(this);
		this.add(macLook);
		macLook.addActionListener(this);
		this.add(gtkLook);
		gtkLook.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e) {
		String look = "javax.swing.plaf.metal.MetalLookAndFeel";
		if (e.getSource() == javaLook)
			look = "javax.swing.plaf.metal.MetalLookAndFeel";
		else if (e.getSource() == windLook)
			look = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		else if (e.getSource() == unixLook)
			look = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
		else if (e.getSource() == macLook)
			look = "com.sun.java.swing.plaf.mac.MacLookAndFeel";
		else if (e.getSource() == gtkLook)
			look = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";

		try {
			UIManager.setLookAndFeel(look);
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception ex) {
			System.out.println("Exception:" + ex);
		}
	}

	public static void demo01() {
		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");// Nimbus风格，jdk6
																							// update10版本以后的才会出现
//			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//当前系统风格
//			 UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");//Motif风格，是蓝黑
//			 UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());//跨平台的Java风格
//			 UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//windows风格
//			 UIManager.setLookAndFeel("javax.swing.plaf.windows.WindowsLookAndFeel");//windows风格
//			 UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");//java风格
//			 UIManager.setLookAndFeel("com.apple.mrj.swing.MacLookAndFeel");//待考察，

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		demo01();
		JFrameButton jwb = new JFrameButton();
		jwb.setTitle("界面风格设置");
		jwb.setLocationRelativeTo(null);// 窗口居中显示
		jwb.setResizable(false);
		jwb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jwb.setSize(600, 100);
		jwb.setVisible(true);
	}
}