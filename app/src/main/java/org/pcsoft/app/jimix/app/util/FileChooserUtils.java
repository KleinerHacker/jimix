package org.pcsoft.app.jimix.app.util;

import javafx.stage.FileChooser;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.plugins.manager.PluginManager;
import org.pcsoft.app.jimix.plugins.manager.type.JimixFileTypeProviderPlugin;
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
        final JimixFileTypeProviderPlugin[] fileTypeProviders = PluginManager.getInstance().getAllFileTypeProviders();
        final ArrayList<FileChooser.ExtensionFilter> filters = new ArrayList<>();

        for (final JimixFileTypeProviderPlugin fileTypeProvider : fileTypeProviders) {
            filters.add(new FileChooser.ExtensionFilter(fileTypeProvider.getDescription(), fileTypeProvider.getExtensions()));
        }

        if (allowAllFilter) {
            final List<String> extensions = new ArrayList<>();
            for (final JimixFileTypeProviderPlugin fileTypeProvider : fileTypeProviders) {
                extensions.addAll(Arrays.asList(fileTypeProvider.getExtensions()));
            }

            filters.add(0, new FileChooser.ExtensionFilter(LanguageResources.getText("dlg.project.extension.all"), extensions));
        }

        return filters;
    }

    private FileChooserUtils() {
    }
}
