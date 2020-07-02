package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressBarView extends JFrame implements Runnable {

	private JProgressBar progressBar;
	private JButton btn;
	private int value = 0;

	public ProgressBarView() {
		this.progressBar = new JProgressBar();
		this.btn = new JButton("确定");
        Container container=getContentPane();
        container.setLayout(new GridLayout(2,1));
        JPanel panel2=new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel3=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel2.add(progressBar);    //添加进度条
        panel3.add(btn);    //添加按钮
        container.add(panel2);
        container.add(panel3);
        progressBar.setStringPainted(true);
		btn.setEnabled(false);
        Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		setBounds(screenSize.width / 2 - 150, screenSize.height / 2 - 50, 300, 100);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        
        btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public void run() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				progressBar.setValue(value);
				progressBar.setString("上传中..." + value + "%");
				if (value == 100) {
					timer.cancel();
					progressBar.setIndeterminate(false);
					progressBar.setString("上传完成！");
					btn.setEnabled(true);
					return;
				}
			}
		};
		timer.scheduleAtFixedRate(task, 0, 1000);
	}

	public static void main(String[] args) throws InterruptedException {
		ProgressBarView pr = new ProgressBarView();
		new Thread(pr).start();
		for(int i = 0; i <= 100; i += 10) {
			pr.setValue(i);
			Thread.sleep(1000);
		}
	}

}
