package com.syntech.chess.graphic;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter pgn = new FileNameExtensionFilter("Portable Game Notation files", "pgn");
        File currentPath = Paths.get("").toFile();
        Path pgnPath = Paths.get(currentPath.getAbsoluteFile().getParent() + "/pgn");
        fileChooser.setCurrentDirectory(pgnPath.toFile());
        fileChooser.setFileFilter(pgn);
        fileChooser.addChoosableFileFilter(pgn);
        if (save) {
            status = fileChooser.showSaveDialog(null);
        } else {
            status = fileChooser.showOpenDialog(null);
        }
        if (status.equals(JFileChooser.APPROVE_OPTION)) {
            File file = fileChooser.getSelectedFile();
            filePath = file.getAbsolutePath();
            if (save && !filePath.endsWith(".pgn")) {
                filePath += ".pgn";
            }
        }
    }

    public Integer getStatus() {
        return status;
    }

    public String getFilePath() {
        return filePath;
    }
}
