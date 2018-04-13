package fr.github.antoinejt.fosic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FOSIC {
	private int sctrsz, nbrsct;
	public FOSIC(int sctrsz, int nbrsct) {
		if (sctrsz<0)
			throw new IllegalArgumentException("Sector Size CAN'T be NEGATIVE!!!");
		else if ((Float.valueOf(sctrsz/512).intValue()*512)!=sctrsz)
			throw new IllegalArgumentException("Sector Size MUST be POWERED OF 512!!!");
		else
			this.sctrsz=sctrsz;
		if (nbrsct<0)
			throw new IllegalArgumentException("Number of Sector CAN'T be NEGATIVE!!!");
		else
			this.nbrsct=nbrsct;
	}
	
	public void createFloppy(File[] in, File out) throws FloppyDiskException {
		ArrayList<Byte> bytes = new ArrayList<Byte>();
		long in_length=0;
		for (File f : in) {
			in_length+=f.length();
		}
		if (in_length>(sctrsz*nbrsct))
			throw new FloppyDiskException("Your floppy disk can't store so much data !");
		for (File f : in) {
			try {
				byte[] b=Files.readAllBytes(Paths.get(f.getCanonicalPath()));
				for (byte by : b)
					bytes.add(Byte.valueOf(by));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		while(bytes.size()<(sctrsz*nbrsct))
			bytes.add((byte)0);
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		for (byte by : bytes) {
			baos.write(Byte.valueOf(by).intValue());
		}
		try {
			Files.write(Paths.get(out.getCanonicalPath()),baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
