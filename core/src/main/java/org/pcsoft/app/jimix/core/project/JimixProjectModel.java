package org.pcsoft.app.jimix.core.project;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.apache.commons.io.FileUtils;
import org.pcsoft.app.jimix.commons.exception.JimixProjectException;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public final class JimixProjectModel {
    private final ObjectProperty<File> file = new SimpleObjectProperty<>();
    private final ReadOnlyListProperty<JimixLevelModel> levelList =
            new ReadOnlyListWrapper<JimixLevelModel>(FXCollections.observableArrayList(param -> new Observable[] {param.elementListProperty()}))
                    .getReadOnlyProperty();
    private final IntegerProperty width = new SimpleIntegerProperty(), height = new SimpleIntegerProperty();

    JimixProjectModel(final int width, final int height) {
        this.width.set(width);
        this.height.set(height);

        levelList.add(new JimixLevelModel());
    }

    ObservableList<JimixLevelModel> getLevelList() {
        return levelList.get();
    }

    ReadOnlyListProperty<JimixLevelModel> levelListProperty() {
        return levelList;
    }

    public File getFile() {
        return file.get();
    }

    public ObjectProperty<File> fileProperty() {
        return file;
    }

    public void setFile(File file) {
        this.file.set(file);
    }

    public int getWidth() {
        return width.get();
    }

    public IntegerProperty widthProperty() {
        return width;
    }

    public void setWidth(int width) {
        this.width.set(width);
    }

    public int getHeight() {
        return height.get();
    }

    public IntegerProperty heightProperty() {
        return height;
    }

    public void setHeight(int height) {
        this.height.set(height);
    }

    @Override
    public String toString() {
        return "JimixProjectModel{" +
                "file=" + file +
                '}';
    }
}
