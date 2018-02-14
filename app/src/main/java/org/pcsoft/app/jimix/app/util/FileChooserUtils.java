package org.pcsoft.app.jimix.app.util;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.List;

public final class FileChooserUtils {
    public static List<File> showOpenPictureFileChooser(final Window owner) {
        final FileChooser fileChooser = buildFileChooser();
        fileChooser.setTitle("Open Picture(s)");
        return fileChooser.showOpenMultipleDialog(owner);
    }

    public static File showSavePictureFileChooser(final Window owner) {
        final FileChooser fileChooser = buildFileChooser();
        fileChooser.setTitle("Save Picture");
        return fileChooser.showSaveDialog(owner);
    }

    private static FileChooser buildFileChooser() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter("All supported pictures", "*.bmp", "*.jpg", "*.jpeg", "*.png", "*.jmx"),
                new FileChooser.ExtensionFilter("Bitmap", "*.bmp"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("Portable Network Graphic", "*.png"),
                new FileChooser.ExtensionFilter("Jimix Picture File", "*.jmx")
        );

        return fileChooser;
    }

    private FileChooserUtils() {
    }
}
