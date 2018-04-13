package fr.github.antoinejt.fosic.ui;

import java.io.File;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import fr.github.antoinejt.fosic.FOSIC;
import fr.github.antoinejt.fosic.FloppyDiskException;

public class UI {
	private static String TITLE = "Floppy OS Image Creator v1.0.5";
	
	public UI() {
		JOptionPane.showMessageDialog(null, "Copyright © 2018, Antoine James Tournepiche\n\nThe Software is provided “as is”, without warranty of any kind, express or implied, including but not limited to the warranties\nof merchantability, fitness for a particular purpose and noninfringement. In no event shall the authors or copyright holders\nbe liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising\nfrom, out of or in connection with the software or the use or other dealings in the Software.", TITLE+" - (c) 2018, Antoine James Tournepiche", JOptionPane.INFORMATION_MESSAGE);
		String[] modes = {"Automatic","Semi-Manual","Manual"};
		int choice=ExtendedJOptionPane.showJComboBox(TITLE,"Choose the mode you want to use",modes);
		int sctrsz=-1,nbrsct=-1;
		if (choice==0) { //Automatic
			sctrsz=512;
			nbrsct=2880;
		} else if (choice==1) { //Semi-Manual
			String[] sizes={"512 bits","1024 bits","2048 bits","4096 bits"};
			sctrsz=Double.valueOf(Math.pow(2,ExtendedJOptionPane.showJComboBox(TITLE,"Sector Size",sizes)+1)*256).intValue();
			String[] type={"5,25\" 160Kio","5,25\" 180Kio","5,25\" 320Kio","5,25\" 360Kio","3,5\" 720Kio","5,25\" 1200Kio","3,5\" 1440Kio","3,5\" 2880Kio"};
			choice=ExtendedJOptionPane.showJComboBox(TITLE,"Choose your Floppy Disk Type",type);
			int kio=-1;
			switch(choice) {
				case 0 : 
					kio=160;
					break;
				case 1 :
					kio=180;
					break;
				case 2 :
					kio=320;
					break;
				case 3 : 
					kio=360;
					break;
				case 4 : 
					kio=720;
					break;
				case 5 :
					kio=1200;
					break;
				case 6 :
					kio=1440;
					break;
				case 7 :
					kio=2880;
					break;
			}
			nbrsct=kio*1024/sctrsz;
		} else if (choice==2) { //Manual
			sctrsz=Integer.parseInt(JOptionPane.showInputDialog("Sector Size (Power of 512)", 512));
			nbrsct=Integer.parseInt(JOptionPane.showInputDialog("Number of Sectors", 2880));
		}
		FOSIC fosic = new FOSIC(sctrsz,nbrsct);
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(Paths.get("").toAbsolutePath().toFile());
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(true);
		if (chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
			File[] in = chooser.getSelectedFiles();
			chooser.setMultiSelectionEnabled(false);
			if (chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
				File out = chooser.getSelectedFile();
				try {
					fosic.createFloppy(in, out);
				} catch (FloppyDiskException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
