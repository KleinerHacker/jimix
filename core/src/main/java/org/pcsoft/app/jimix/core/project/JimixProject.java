package org.pcsoft.app.jimix.core.project;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.core.image.ImageBuilder;

import java.util.Objects;
import java.util.UUID;

/**
 * Project host for the {@link JimixProjectModel} self, with additional app internal information
 */
public final class JimixProject {
    private final ReadOnlyObjectProperty<UUID> uuid = new ReadOnlyObjectWrapper<>(UUID.randomUUID()).getReadOnlyProperty();
    private final ReadOnlyObjectProperty<JimixProjectModel> model;

    private final ReadOnlyMapProperty<UUID, JimixLevel> levelMap =
            new ReadOnlyMapWrapper<UUID, JimixLevel>(FXCollections.observableHashMap()).getReadOnlyProperty();
    private final ReadOnlyListProperty<JimixLevel> levelList =
            new ReadOnlyListWrapper<JimixLevel>(FXCollections.observableArrayList()).getReadOnlyProperty();

    private final ObjectBinding<Image> resultImage;

    JimixProject(final JimixProjectModel model) {
        this.model = new ReadOnlyObjectWrapper<>(model).getReadOnlyProperty();
        //List Updater
        levelMap.addListener((MapChangeListener<UUID, JimixLevel>) c -> {
            if (c.wasAdded()) {
                levelList.add(c.getValueAdded());
            }
            if (c.wasRemoved()) {
                levelList.remove(c.getValueRemoved());
            }
        });

        resultImage = Bindings.createObjectBinding(
                () -> ImageBuilder.getInstance().buildProjectImage(this),
                levelList
        );
    }

    public UUID getUuid() {
        return uuid.get();
    }

    public ReadOnlyObjectProperty<UUID> uuidProperty() {
        return uuid;
    }

    public JimixProjectModel getModel() {
        return model.get();
    }

    public ReadOnlyObjectProperty<JimixProjectModel> modelProperty() {
        return model;
    }

    ObservableMap<UUID, JimixLevel> getLevelMap() {
        return levelMap.get();
    }

    ReadOnlyMapProperty<UUID, JimixLevel> levelMapProperty() {
        return levelMap;
    }

    public ObservableList<JimixLevel> getLevelList() {
        return levelList.get();
    }

    public ReadOnlyListProperty<JimixLevel> levelListProperty() {
        return levelList;
    }

    public JimixLevel getLevel(final UUID levelUUID) {
        return levelMap.get(levelUUID);
    }

    public Image getResultImage() {
        return resultImage.get();
    }

    public ObjectBinding<Image> resultImageProperty() {
        return resultImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JimixProject that = (JimixProject) o;
        return Objects.equals(uuid.get(), that.uuid.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid.get());
    }

    @Override
    public String toString() {
        return model.get().toString();
    }
}
