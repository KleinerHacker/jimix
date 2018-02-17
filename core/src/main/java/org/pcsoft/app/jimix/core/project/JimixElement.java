package org.pcsoft.app.jimix.core.project;

import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.Objects;
import java.util.UUID;

/**
 * Level holder for {@link JimixElementModel}, with additional app internal information
 */
public final class JimixElement {
    private final ReadOnlyObjectProperty<UUID> uuid = new ReadOnlyObjectWrapper<>(UUID.randomUUID()).getReadOnlyProperty();
    private final ReadOnlyObjectProperty<JimixElementModel> model;


    private final ReadOnlyObjectProperty<JimixProject> project;
    private final ReadOnlyObjectProperty<JimixLevel> level;

    public JimixElement(final JimixProject project, final JimixLevel level, final JimixElementModel model) {
        this.project = new ReadOnlyObjectWrapper<>(project).getReadOnlyProperty();
        this.level = new ReadOnlyObjectWrapper<>(level).getReadOnlyProperty();
        this.model = new ReadOnlyObjectWrapper<>(model).getReadOnlyProperty();
    }

    public UUID getUuid() {
        return uuid.get();
    }

    public ReadOnlyObjectProperty<UUID> uuidProperty() {
        return uuid;
    }

    public JimixElementModel getModel() {
        return model.get();
    }

    public ReadOnlyObjectProperty<JimixElementModel> modelProperty() {
        return model;
    }

    public JimixProject getProject() {
        return project.get();
    }

    public ReadOnlyObjectProperty<JimixProject> projectProperty() {
        return project;
    }

    public JimixLevel getLevel() {
        return level.get();
    }

    public ReadOnlyObjectProperty<JimixLevel> levelProperty() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JimixElement that = (JimixElement) o;
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
