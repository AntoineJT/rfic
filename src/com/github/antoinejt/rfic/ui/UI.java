package com.github.antoinejt.rfic.ui;

import java.io.File;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.github.antoinejt.rfic.FloppyDiskException;
import com.github.antoinejt.rfic.RawFloppyImage;

public class UI {
	private static final String TITLE = "Raw Floppy Image Creator v1.0.0";
	private static final ImageIcon icon = new ImageIcon(UI.class.getResource("rfic_logo.png"));
	
	public UI() {
		final String[] modes = {"Automatic", "Semi-Manual", "Manual"};
		int sctrsz = -1;
		int nbrsct = -1;
		int choice = ExtendedJOptionPane.showJComboBox(TITLE,icon.getImage(),"Choose the mode you want to use",modes);
		if (choice==0) { // Automatic
			sctrsz = 512;
			nbrsct = 2880;
		} else if (choice==1) { // Semi-Manual
			final String[] sizes = {"512 bits", "1024 bits", "2048 bits", "4096 bits"};
			sctrsz = Double.valueOf(Math.pow(2,ExtendedJOptionPane.showJComboBox(TITLE,icon.getImage(),"Sector Size",sizes)+1)*256).intValue();
			final String[] type = {"5,25\" 160Kio", "5,25\" 180Kio", "5,25\" 320Kio", "5,25\" 360Kio", "3,5\" 720Kio", "5,25\" 1200Kio", "3,5\" 1440Kio", "3,5\" 2880Kio"};
			choice = ExtendedJOptionPane.showJComboBox(TITLE,icon.getImage(),"Choose your Floppy Disk Type",type);
			int kio = -1;
			switch(choice) {
				case 0 : 
					kio = 160;
					break;
				case 1 :
					kio = 180;
					break;
				case 2 :
					kio = 320;
					break;
				case 3 : 
					kio = 360;
					break;
				case 4 : 
					kio = 720;
					break;
				case 5 :
					kio = 1200;
					break;
				case 6 :
					kio = 1440;
					break;
				case 7 :
					kio = 2880;
					break;
			}
			nbrsct = kio*1024/sctrsz;
		} else if (choice==2) { // Manual
			sctrsz = Integer.parseInt((String)JOptionPane.showInputDialog(null,"Sector Size (Power of 512)",TITLE,JOptionPane.QUESTION_MESSAGE,icon,null,512));
			nbrsct = Integer.parseInt((String)JOptionPane.showInputDialog(null,"Number of Sectors",TITLE,JOptionPane.QUESTION_MESSAGE,icon,null,2880));
		}
		final RawFloppyImage rfi = new RawFloppyImage(sctrsz,nbrsct);
		final JFileChooser chooser = new JFileChooser();
		final JFrame jf=new JFrame(); // this thing is here just to put an icon on the jchooser thing
		jf.setIconImage(icon.getImage());
		jf.setEnabled(false);
		chooser.setCurrentDirectory(Paths.get("").toAbsolutePath().toFile());
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(true);
		if (chooser.showOpenDialog(jf)==JFileChooser.APPROVE_OPTION) {
			final File[] in = chooser.getSelectedFiles();
			chooser.setMultiSelectionEnabled(false);
			if (chooser.showSaveDialog(jf)==JFileChooser.APPROVE_OPTION) {
				final File out = chooser.getSelectedFile();
				try {
					rfi.createFloppy(in, out);
				} catch (FloppyDiskException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
