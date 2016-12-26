package com.example;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JComboBox;

import javafx.scene.media.Media;

public class Mp3ErrorRecordFile {

    public static void main(String[] args) {
        File folder = new File("kugou");
        System.out.println(folder.getPath());
        String[] files = folder.list();
        JComboBox bc;
        try {
            for (String path : files) {
                File file = new File(path);
                Media media = new Media(path);
                if (media.getDuration().toMinutes() <= 0) {
                    recordFile(file);
                } else {
                    System.out.println(file.getPath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void recordFile(File file) throws IOException {
        String content = file.getName() + "\n";
        System.out.println(file.getPath());
        File recordFile = new File("record.txt");
        if (!recordFile.exists()) {
            recordFile.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(recordFile, true);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bos.write(content.getBytes(), 0, content.length());
        bos.flush();
        bos.close();
        fos.close();
    }
}
