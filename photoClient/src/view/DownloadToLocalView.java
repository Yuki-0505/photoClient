package view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import utils.ImageBytesToFile;
import utils.MyImageFilter;

public class DownloadToLocalView extends JFileChooser {

	public DownloadToLocalView(String name, byte[] image) {
		setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		setMultiSelectionEnabled(true);
		setCurrentDirectory(new File("/home/yuki/Pictures/download"));
		setSelectedFile(new File(name));
		setFileFilter(new MyImageFilter());
		showDialog(new JLabel(), "选择");
		File file = getSelectedFile();
		ImageBytesToFile.save(file, image);
		JOptionPane.showMessageDialog(null, "下载完成！", "提示信息", JOptionPane.PLAIN_MESSAGE);
	}

}
