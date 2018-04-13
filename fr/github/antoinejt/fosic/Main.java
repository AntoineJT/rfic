package fr.github.antoinejt.fosic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.github.antoinejt.fosic.ui.UI;

public class Main {
	public static void main(String[] args) {
		new UI();
	}
	
	byte[] readSmallBinaryFile(String aFileName) throws IOException {
		Path path = Paths.get(aFileName);
    	return Files.readAllBytes(path);
	}
	
	void writeSmallBinaryFile(byte[] aBytes, String aFileName) throws IOException {
		Path path = Paths.get(aFileName);
		Files.write(path, aBytes); //creates, overwrites
 	}
}
