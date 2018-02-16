package org.pcsoft.app.jimix.core.model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;
import org.pcsoft.app.jimix.commons.exception.JimixProjectException;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public final class JimixProject {
    private final String uuid = UUID.randomUUID().toString();
    private final ObjectProperty<File> file = new SimpleObjectProperty<>();
    private final ReadOnlyListProperty<JimixLevel> levelList =
            new ReadOnlyListWrapper<JimixLevel>(FXCollections.observableArrayList(param -> new Observable[] {param.maskProperty(), param.pictureProperty()}))
                    .getReadOnlyProperty();

    public JimixProject(Image baseLevelPicture) {
        levelList.add(new JimixLevel(baseLevelPicture));
    }

    public JimixProject(File file) throws JimixProjectException {
        try {
            levelList.add(new JimixLevel(new Image(FileUtils.openInputStream(file))));
            this.file.set(file);
        } catch (IOException e) {
            throw new JimixProjectException("unable to load project from file " + file.getAbsolutePath(), e);
        }
    }

    public ObservableList<JimixLevel> getLevelList() {
        return levelList.get();
    }

    public ReadOnlyListProperty<JimixLevel> levelListProperty() {
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

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JimixProject that = (JimixProject) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "JimixProject{" +
                "uuid='" + uuid + '\'' +
                ", file=" + file +
                '}';
    }
}
