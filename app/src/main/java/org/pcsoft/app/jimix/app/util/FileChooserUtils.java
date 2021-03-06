package org.pcsoft.app.jimix.app.util;

import javafx.stage.FileChooser;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.plugin.system.manager.SystemPluginManager;
import org.pcsoft.app.jimix.plugin.system.manager.type.JimixImageFileTypeProviderPlugin;
import org.pcsoft.app.jimix.plugin.system.manager.type.JimixProjectFileTypeProviderPlugin;
import org.pcsoft.framework.jfex.util.FXChooserUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class FileChooserUtils {
    public static List<File> showOpenPicturesFileChooser() {
        return showOpenPicturesFileChooser(true);
    }

    public static List<File> showOpenPicturesFileChooser(boolean allowProjects) {
        return FXChooserUtils.showFileOpenMultipleChooser(LanguageResources.getText("dlg.project.open.title"),
                "dlg.picture.open", buildExtensionFilter(true, allowProjects)).orElse(null);
    }

    public static File showOpenPictureFileChooser() {
        return showOpenPictureFileChooser(true);
    }

    public static File showOpenPictureFileChooser(boolean allowProjects) {
        return FXChooserUtils.showFileOpenChooser(LanguageResources.getText("dlg.project.open.title"),
                "dlg.picture.open", buildExtensionFilter(true, allowProjects)).orElse(null);
    }

    public static File showSavePictureFileChooser() {
        return showSavePictureFileChooser(true);
    }

    public static File showSavePictureFileChooser(boolean allowProjects) {
        return FXChooserUtils.showFileSaveChooser(LanguageResources.getText("dlg.project.save.title"),
                "dlg.picture.save", buildExtensionFilter(false, allowProjects)).orElse(null);
    }

    private static List<FileChooser.ExtensionFilter> buildExtensionFilter(boolean allowAllFilter, boolean allowProjects) {
        final JimixImageFileTypeProviderPlugin[] imageFileTypeProviderPlugins = SystemPluginManager.getInstance().getAllImageFileTypeProviders();
        final JimixProjectFileTypeProviderPlugin[] projectFileTypeProviderPlugins = SystemPluginManager.getInstance().getAllProjectFileTypeProviders();

        final ArrayList<FileChooser.ExtensionFilter> filters = new ArrayList<>();
        for (final JimixImageFileTypeProviderPlugin imageFileTypeProviderPlugin : imageFileTypeProviderPlugins) {
            filters.add(new FileChooser.ExtensionFilter(imageFileTypeProviderPlugin.getDescription(), imageFileTypeProviderPlugin.getExtensions()));
        }
        if (allowProjects) {
            for (final JimixProjectFileTypeProviderPlugin projectFileTypeProviderPlugin : projectFileTypeProviderPlugins) {
                filters.add(new FileChooser.ExtensionFilter(projectFileTypeProviderPlugin.getDescription(), projectFileTypeProviderPlugin.getExtensions()));
            }
        }

        if (allowAllFilter) {
            final List<String> extensions = new ArrayList<>();
            for (final JimixImageFileTypeProviderPlugin imageFileTypeProviderPlugin : imageFileTypeProviderPlugins) {
                extensions.addAll(Arrays.asList(imageFileTypeProviderPlugin.getExtensions()));
            }
            for (final JimixProjectFileTypeProviderPlugin projectFileTypeProviderPlugin : projectFileTypeProviderPlugins) {
                extensions.addAll(Arrays.asList(projectFileTypeProviderPlugin.getExtensions()));
            }
            filters.add(0, new FileChooser.ExtensionFilter(LanguageResources.getText("dlg.project.extension.all"), extensions));
        }

        return filters;
    }

    private FileChooserUtils() {
    }
}
