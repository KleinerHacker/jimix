package org.pcsoft.app.jimix.app.util;

import javafx.stage.FileChooser;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.framework.jfex.util.FXChooserUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public final class FileChooserUtils {
    public static List<File> showOpenPictureFileChooser() {
        return FXChooserUtils.showFileOpenMultipleChooser(LanguageResources.getText("dlg.project.open.title"),
                "dlg.picture.open", buildExtensionFilter()).orElse(null);
    }

    public static File showSavePictureFileChooser() {
        return FXChooserUtils.showFileSaveChooser(LanguageResources.getText("dlg.project.save.title"),
                "dlg.picture.save", buildExtensionFilter()).orElse(null);
    }

    private static List<FileChooser.ExtensionFilter> buildExtensionFilter() {
        return Arrays.asList(
                new FileChooser.ExtensionFilter(
                        LanguageResources.getText("dlg.project.extension.all"), "*.bmp", "*.jpg", "*.jpeg", "*.png", "*.jmx"
                ),
                new FileChooser.ExtensionFilter(
                        LanguageResources.getText("dlg.project.extension.bmp"), "*.bmp"
                ),
                new FileChooser.ExtensionFilter(
                        LanguageResources.getText("dlg.project.extension.jpg"), "*.jpg", "*.jpeg"
                ),
                new FileChooser.ExtensionFilter(
                        LanguageResources.getText("dlg.project.extension.png"), "*.png"
                ),
                new FileChooser.ExtensionFilter(
                        LanguageResources.getText("dlg.project.extension.jxp"), "*.jxp"
                )
        );
    }

    private FileChooserUtils() {
    }
}
