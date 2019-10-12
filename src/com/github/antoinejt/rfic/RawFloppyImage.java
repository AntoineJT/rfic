package com.github.antoinejt.rfic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RawFloppyImage {
    private int sectorSize;
    private int numberOfSectors;

    public RawFloppyImage(final int sectorSize, final int numberOfSectors) {
        if (sectorSize < 0) {
			throw new IllegalArgumentException("Sector size can't be negative!");
		}
		// TODO This is probably stupid, replace it with a Math.floor(sctrsz / 512) will probably be better, and more make a method for it
        if (sectorSize / 512 * 512 != sectorSize) {
			throw new IllegalArgumentException("Sector size must be power OF 512!");
		}
        if (numberOfSectors < 0) {
			throw new IllegalArgumentException("Number of sectors can't be negative!");
		}

        this.sectorSize = sectorSize;
        this.numberOfSectors = numberOfSectors;
    }

    private long getFileArrayByteLength(File[] files){
    	long length = 0;

    	for (File currentFile : files){
    		length += currentFile.length();
		}
		return length;
	}

    public void createFloppy(File[] in, File out) throws FloppyDiskException {
        List<Byte> bytes = new ArrayList<>();
        long fileArrayByteLength = getFileArrayByteLength(in);

        if (fileArrayByteLength > (sectorSize * numberOfSectors)) {
			throw new FloppyDiskException("Your floppy disk can't store so much data!");
		}
        for (File currentFile : in) {
			try {
				final byte[] allBytes = Files.readAllBytes(Paths.get(currentFile.getCanonicalPath()));
				for (byte b : allBytes) {
					bytes.add(b);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        while (bytes.size() < (sectorSize * numberOfSectors)) {
			bytes.add((byte) 0);
		}

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for (byte by : bytes) {
			baos.write(Byte.valueOf(by).intValue());
		}
        try {
            Files.write(Paths.get(out.getCanonicalPath()), baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
