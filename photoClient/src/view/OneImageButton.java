package view;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

import com.alibaba.fastjson.JSONObject;

public class OneImageButton extends JButton {
	
	private JSONObject json;
	private int x;
	private int y;
	
	public OneImageButton(JSONObject json, int x, int y) {
		this.json = json;
		this.x = x;
		this.y = y;
		
		ImageIcon image = new ImageIcon(json.getBytes("image"));
		int hight = image.getIconHeight();
		int width = image.getIconWidth();
		if (hight > width) {
			hight = 180 * hight / width;
			width = 180;
		} else {
			width = 180 * width / hight;
			hight = 180;
		}
		Image img = image.getImage().getScaledInstance(width, hight, Image.SCALE_AREA_AVERAGING);
		ImageIcon icon = new ImageIcon(img);
		setIcon(icon);
		setBounds(40 + 210 * x, 15 + 200 * y, 180, 180);
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					new OneImageView(image);
				} else if (e.getButton() == e.BUTTON3) {
					showRightClickMenu(e);
				}
			}
		});
	}
	
	public void showRightClickMenu(MouseEvent e) {
		JPopupMenu jpm = new ImageRightClickMenu(this, json);
		add(jpm);
		jpm.show(this, e.getX(), e.getY());
	}
	
}
