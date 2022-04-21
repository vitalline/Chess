package com.syntech.chess.graphic;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A wrapper for {@link javax.swing.JFileChooser}. Can be used to open the
 * file choosing dialog, choose a file in it, and then pass over the file path.
 * Used in this app for saving and loading Portable Game Notation (.pgn) files,
 * hence the forced .pgn extension.
 */

public class FileChooser extends Thread {

    /**
     * The path to chosen file (if any).
     */
    private String filePath;

    /**
     * The status of the JFileChooser.
     */
    private Integer status;

    /**
     * Whether the menu is used for saving or opening a file.
     */
    private final boolean save;

    /**
     * Constructs a file chooser with the specified mode ("open" by default, "save" if specified).
     */
    public FileChooser(boolean save) {
        filePath = null;
        status = null;
        this.save = save;
    }

    /**
     * Opens the file choosing screen and stores the results in class variables.
     */
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

    /**
     * Returns the status of the JFileChooser.
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Returns the path to chosen file (if any).
     */
    public String getFilePath() {
        return filePath;
    }
}
