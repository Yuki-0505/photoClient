package view;

import javax.swing.JPanel;

import com.alibaba.fastjson.JSONArray;

public class OnePagePanel extends JPanel {

	public OnePagePanel(JSONArray jsonArr) {
		setLayout(null);
		setBounds(0, 0, 900, 620);
//		TODO 多线程
		Flag: for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				if (4 * i + j >= jsonArr.size())
					break Flag;
				final int x = j;
				final int y = i;
				new Thread() {
					public void run() {
						add(new OneImageButton(jsonArr.getJSONObject(4 * y + x), x, y));
					}
				}.start();
			}
		}
	}

}
