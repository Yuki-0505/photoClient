package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ShowOneImageView extends JFrame {

	private ImageIcon image;
	private ImageIcon icon;
	private int width;
	private int height;
	private JLabel lblImg;
	private int x;
	private int y;
	private double factor;

	public ShowOneImageView(ImageIcon image) {
		this.image = image;
		factor = 1.0;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		width = image.getIconWidth();
		height = image.getIconHeight();
		int showWidth = width, showHeight = height;
		if (width >= 1440) {
			showHeight = 1440 * height / width;
			showWidth = 1440;
		}
		if (showHeight >= 810) {
			showWidth = 810 * showWidth / showHeight;
			showHeight = 810;
		}
		width = showWidth;
		height = showHeight;
		setBounds(screenSize.width / 2 - showWidth / 2, screenSize.height / 2 - showHeight / 2, showWidth + 10,
				showHeight + 40);

		showImage(showWidth, showHeight);

		listen();
		setVisible(true);
	}

	public void listen() {

		lblImg.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}
		});
		lblImg.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				lblImg = (JLabel) e.getSource();
				lblImg.setLocation(lblImg.getX() + e.getX() - x, lblImg.getY() + e.getY() - y);
			}
		});
		lblImg.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				double zoom = e.getWheelRotation() < 0 ? 1.1 : 1 / 1.1;
				factor *= zoom;
				lblImg = (JLabel) e.getSource();
				int localX = (int) (lblImg.getX() - e.getPoint().x * zoom + e.getPoint().x);
				int localY = (int) (lblImg.getY() - e.getPoint().y * zoom + e.getPoint().y);
				int showWidth = (int) (width * factor);
				int showHeight = (int) (height * factor);
				Image img = image.getImage().getScaledInstance(showWidth, showHeight, Image.SCALE_AREA_AVERAGING);
				icon = new ImageIcon(img);
				lblImg.setIcon(icon);
				lblImg.setBounds(localX, localY, showWidth, showHeight);
				img = null;
				icon = null;
			}
		});
	}

	public void showImage(int showWidth, int showHeight) {
		Image img = image.getImage().getScaledInstance(showWidth, showHeight, Image.SCALE_AREA_AVERAGING);
		icon = new ImageIcon(img);
		lblImg = new JLabel();
		lblImg.setIcon(icon);
		lblImg.setBounds(0, 0, showWidth, showHeight);
		add(lblImg);
		img = null;
		icon = null;
	}
}
