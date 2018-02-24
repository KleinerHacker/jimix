package org.pcsoft.app.jimix.app.util;

import javafx.stage.FileChooser;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.framework.jfex.util.FXChooserUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class FileChooserUtils {
    public static List<File> showOpenPictureFileChooser() {
        return FXChooserUtils.showFileOpenMultipleChooser(LanguageResources.getText("dlg.project.open.title"),
                "dlg.picture.open", buildExtensionFilter(true)).orElse(null);
    }

    public static File showSavePictureFileChooser() {
        return FXChooserUtils.showFileSaveChooser(LanguageResources.getText("dlg.project.save.title"),
                "dlg.picture.save", buildExtensionFilter(false)).orElse(null);
    }

    private static List<FileChooser.ExtensionFilter> buildExtensionFilter(boolean allowAllFilter) {
        final ArrayList<FileChooser.ExtensionFilter> filters = new ArrayList<>(Arrays.asList(
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
        ));
        if (allowAllFilter) {
            filters.add(0, new FileChooser.ExtensionFilter(
                    LanguageResources.getText("dlg.project.extension.all"), "*.bmp", "*.jpg", "*.jpeg", "*.png", "*.jmx"
            ));
        }

        return filters;
    }

    private FileChooserUtils() {
    }
}
