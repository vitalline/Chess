package com.syntech.chess.graphic;

import javax.swing.*;
import java.io.File;

public class FileChooser extends Thread {

    private String filePath;
    private Integer status;
    private boolean save;

    public FileChooser(boolean save) {
        filePath = null;
        status = null;
        this.save = save;
    }

    @Override
    public void run() {
        JFileChooser fileopen = new JFileChooser();
        if (save) {
            status = fileopen.showSaveDialog(null);
        } else {
            status = fileopen.showOpenDialog(null);
        }
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
