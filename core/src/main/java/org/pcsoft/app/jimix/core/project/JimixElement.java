package org.pcsoft.app.jimix.core.project;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import org.pcsoft.app.jimix.project.JimixElementModel;
import org.pcsoft.app.jimix.project.JimixPictureElementModel;

import java.util.Objects;
import java.util.UUID;

/**
 * Element holder for {@link JimixPictureElementModel}, with additional app internal information
 */
public abstract class JimixElement<T extends JimixElementModel> implements JimixWrapper {
    //Temporary identifier only
    private final ReadOnlyObjectProperty<UUID> uuid = new ReadOnlyObjectWrapper<>(UUID.randomUUID()).getReadOnlyProperty();
    private final ReadOnlyObjectProperty<T> model;
    private final BooleanProperty visible = new SimpleBooleanProperty(true);

    private final ReadOnlyObjectProperty<JimixProject> project;
    private final ReadOnlyObjectProperty<JimixLayer> layer;

    public JimixElement(final JimixProject project, final JimixLayer layer, final T model) {
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

    public T getModel() {
        return model.get();
    }

    public ReadOnlyObjectProperty<T> modelProperty() {
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

    public boolean getVisible() {
        return visible.get();
    }

    public BooleanProperty visibleProperty() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible.set(visible);
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[] {
                visible
        };
    }

    //<editor-fold desc="Equals / Hashcode / ToString">
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
    //</editor-fold>
}
