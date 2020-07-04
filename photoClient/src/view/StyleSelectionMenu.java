package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import utils.PropertiesWrite;

public class StyleSelectionMenu extends JMenu {

	public StyleSelectionMenu() {
		setText("窗口风格");
		HashMap<String, String> style = new HashMap<String, String>();
		style.put("Noire", "com.jtattoo.plaf.noire.NoireLookAndFeel");
		style.put("Smart", "com.jtattoo.plaf.smart.SmartLookAndFeel");
		style.put("Mint", "com.jtattoo.plaf.mint.MintLookAndFeel");
		style.put("McWin", "com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		style.put("Luna", "com.jtattoo.plaf.luna.LunaLookAndFeel");
		style.put("HiFi", "com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		style.put("Fast", "com.jtattoo.plaf.fast.FastLookAndFeel");
		style.put("Bernstein", "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
		style.put("Aero", "com.jtattoo.plaf.aero.AeroLookAndFeel");
		style.put("Graphite", "com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		for (String key : (Set<String>) style.keySet()) {
			JMenuItem jmi = new JMenuItem(key);
			add(jmi);
			jmi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String lookAndFeel = (String) style.get(jmi.getText());
					PropertiesWrite pw = new PropertiesWrite();
					pw.put("style", lookAndFeel);
					pw.close();
					JOptionPane.showMessageDialog(null, "程序将关闭，请重启以使设置生效！", "提示信息", JOptionPane.PLAIN_MESSAGE);
					System.exit(0);
				}
			});
		}
	}
	
}
