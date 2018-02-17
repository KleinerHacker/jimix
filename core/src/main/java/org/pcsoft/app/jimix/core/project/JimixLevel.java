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
 * Level holder for {@link JimixLevelModel}, with additional app internal information
 */
public final class JimixLevel {
    private final ReadOnlyObjectProperty<UUID> uuid = new ReadOnlyObjectWrapper<>(UUID.randomUUID()).getReadOnlyProperty();
    private final ReadOnlyObjectProperty<JimixLevelModel> model;
    private final ReadOnlyMapProperty<UUID, JimixElement> elementMap =
            new ReadOnlyMapWrapper<UUID, JimixElement>(FXCollections.observableHashMap()).getReadOnlyProperty();
    private final ReadOnlyListProperty<JimixElement> elementList =
            new ReadOnlyListWrapper<JimixElement>(FXCollections.observableArrayList()).getReadOnlyProperty();

    private final ReadOnlyObjectProperty<JimixProject> project;

    private final ObjectBinding<Image> resultImage;

    public JimixLevel(final JimixProject project, final JimixLevelModel model) {
        this.project = new ReadOnlyObjectWrapper<>(project).getReadOnlyProperty();
        this.model = new ReadOnlyObjectWrapper<>(model).getReadOnlyProperty();
        //List Updater
        elementMap.addListener((MapChangeListener<UUID, JimixElement>) c -> {
            if (c.wasAdded()) {
                elementList.add(c.getValueAdded());
            }
            if (c.wasRemoved()) {
                elementList.remove(c.getValueRemoved());
            }
        });

        resultImage = Bindings.createObjectBinding(
                () -> ImageBuilder.getInstance().buildLevelImage(this),
                elementList
        );
    }

    public UUID getUuid() {
        return uuid.get();
    }

    public ReadOnlyObjectProperty<UUID> uuidProperty() {
        return uuid;
    }

    public JimixProject getProject() {
        return project.get();
    }

    public ReadOnlyObjectProperty<JimixProject> projectProperty() {
        return project;
    }

    public JimixLevelModel getModel() {
        return model.get();
    }

    public ReadOnlyObjectProperty<JimixLevelModel> modelProperty() {
        return model;
    }

    ObservableMap<UUID, JimixElement> getElementMap() {
        return elementMap.get();
    }

    ReadOnlyMapProperty<UUID, JimixElement> elementMapProperty() {
        return elementMap;
    }

    public ObservableList<JimixElement> getElementList() {
        return elementList.get();
    }

    public ReadOnlyListProperty<JimixElement> elementListProperty() {
        return elementList;
    }

    public JimixElement getElement(final UUID elementUUID) {
        return elementMap.get(elementUUID);
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
        JimixLevel that = (JimixLevel) o;
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
