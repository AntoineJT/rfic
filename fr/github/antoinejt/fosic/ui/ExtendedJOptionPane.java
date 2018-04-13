package fr.github.antoinejt.fosic.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ExtendedJOptionPane extends JOptionPane {
	private static int ret=-1;
	public static int showJComboBox(String title, String message, String[] values) {
		ret=-1;
		JDialog jd = new JDialog();
		JOptionPane jop = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.DEFAULT_OPTION);
		JComboBox mode = new JComboBox(values);
		jop.remove(1);
		jop.add(mode);
		KeyListener kl = new KeyListener() {
			public void keyPressed(KeyEvent ke) {}
			public void keyReleased(KeyEvent ke) {
				if (ke.getKeyCode()==KeyEvent.VK_ENTER)	ret=mode.getSelectedIndex();
			}
			public void keyTyped(KeyEvent ke) {}
		};
		jop.addKeyListener(kl);
		jd.addKeyListener(kl);
		mode.addKeyListener(kl);
		if (title!=null)
			jd.setTitle(title);
		jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jd.getContentPane().add(jop);
		jd.pack();
		jd.setLocationRelativeTo(null);
		jd.setVisible(true);
		while(ret==-1&&jd.isDisplayable()) {
			try {
				Thread.sleep(250L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		jd.dispose();
		return ret;
	}
}
