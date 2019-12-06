package com.syntech.chess.graphic;

import javax.swing.*;
import java.io.File;

public class FileChooser extends Thread {

    private String filePath;
    private Integer status;

    public FileChooser() {
        filePath = null;
        status = null;
    }

    @Override
    public void run() {
        JFileChooser fileopen = new JFileChooser();
        status = fileopen.showDialog(null, "Открыть файл");
        if (status.equals(JFileChooser.APPROVE_OPTION)) {
            File file = fileopen.getSelectedFile();
            filePath = file.getAbsolutePath();
        }
    }

    public Integer getStatus() {
        return status;
    }

    public String getFilePath() {
        return filePath;
    }
}
