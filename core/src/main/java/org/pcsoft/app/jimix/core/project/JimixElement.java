package org.pcsoft.app.jimix.core.project;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.Objects;
import java.util.UUID;

/**
 * Element holder for {@link JimixElementModel}, with additional app internal information
 */
public final class JimixElement {
    private final ReadOnlyObjectProperty<UUID> uuid = new ReadOnlyObjectWrapper<>(UUID.randomUUID()).getReadOnlyProperty();
    private final ReadOnlyObjectProperty<JimixElementModel> model;


    private final ReadOnlyObjectProperty<JimixProject> project;
    private final ReadOnlyObjectProperty<JimixLayer> layer;

    public JimixElement(final JimixProject project, final JimixLayer layer, final JimixElementModel model) {
        this.project = new ReadOnlyObjectWrapper<>(project).getReadOnlyProperty();
        this.layer = new ReadOnlyObjectWrapper<>(layer).getReadOnlyProperty();
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

    public JimixLayer getLayer() {
        return layer.get();
    }

    public ReadOnlyObjectProperty<JimixLayer> layerProperty() {
        return layer;
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
