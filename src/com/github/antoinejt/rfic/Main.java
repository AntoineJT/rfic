package com.github.antoinejt.rfic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.antoinejt.rfic.ui.UI;

public class Main {
    public static void main(String[] args) {
        new UI();
    }

    byte[] readSmallBinaryFile(final String aFileName) throws IOException {
        final Path path = Paths.get(aFileName);
        return Files.readAllBytes(path);
    }

    void writeSmallBinaryFile(final byte[] aBytes, final String aFileName) throws IOException {
        final Path path = Paths.get(aFileName);
        Files.write(path, aBytes); // creates, overwrites
    }
}
