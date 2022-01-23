package com.kikichante.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Convert {
    public static byte[] fileToByteArray(String path) {
        byte[] musicByte = null;
        try {
            musicByte = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return musicByte;
    }

    public static void byteArrayToFile(byte[] bytesMusic, String path) {
        File file = new File(path);
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(bytesMusic);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
