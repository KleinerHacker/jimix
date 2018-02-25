package org.pcsoft.app.jimix.core.tooling;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public final class RecentFileManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecentFileManager.class);
    private static final File FILE = new File(".recent");
    private static final int MAX_SIZE = 10;
    private static final RecentFileManager instance = new RecentFileManager();

    public static RecentFileManager getInstance() {
        return instance;
    }

    private final ReadOnlyListProperty<File> fileList =
            new ReadOnlyListWrapper<File>(FXCollections.observableArrayList()).getReadOnlyProperty();

    private RecentFileManager() {
        load();
        save();

        fileList.addListener((ListChangeListener<File>) c -> save());
    }

    private void load() {
        if (!FILE.exists() || FILE.isDirectory()) {
            LOGGER.warn("Unable to load file: not found: " + FILE.getAbsolutePath());
            return;
        }

        try (final InputStream in = new FileInputStream(FILE)) {
            try (final Scanner scanner = new Scanner(in, "UTF-8")) {
                while (scanner.hasNextLine()) {
                    final String filename = scanner.nextLine();
                    if (filename.trim().isEmpty())
                        continue;

                    final File file = new File(filename);
                    if (!file.exists() || file.isDirectory())
                        continue;

                    fileList.add(file);
                }
            }
        } catch (IOException e) {
            LOGGER.warn("Unable to load recent files", e);
        }
    }

    private void save() {
        try (final FileWriter writer = new FileWriter(FILE)) {
            for (final File file : fileList) {
                writer.write(file.getAbsolutePath() + SystemUtils.LINE_SEPARATOR);
            }
        } catch (IOException e) {
            LOGGER.warn("Unable to store recent files", e);
        }
    }

    public File[] getFiles() {
        return fileList.get().toArray(new File[fileList.get().size()]);
    }

    public void addFile(final File file) {
        if (fileList.get().contains(file)) {
            fileList.get().remove(file);
        }
        if (fileList.get().size() > MAX_SIZE) {
            fileList.get().remove(fileList.get().size() - MAX_SIZE, fileList.get().size());
        }

        fileList.add(0, file);
    }

    public void addFiles(final List<File> files) {
        for (final File file : files) {
            addFile(file);
        }
    }

    public void removeFile(final File file) {
        fileList.get().remove(file);
    }

    public void addListener(ListChangeListener<? super File> listener) {
        fileList.addListener(listener);
    }

    public void removeListener(ListChangeListener<? super File> listener) {
        fileList.removeListener(listener);
    }
}
