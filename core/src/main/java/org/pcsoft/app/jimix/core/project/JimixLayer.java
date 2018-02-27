package org.pcsoft.app.jimix.core.project;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import javafx.util.Callback;
import org.pcsoft.app.jimix.core.util.ImageBuilder;
import org.pcsoft.app.jimix.plugins.api.model.JimixElementModel;

import java.util.*;

/**
 * Layer holder for {@link JimixLayerModel}, with additional app internal information
 */
public final class JimixLayer {
    private final ReadOnlyObjectProperty<UUID> uuid = new ReadOnlyObjectWrapper<>(UUID.randomUUID()).getReadOnlyProperty();
    private final ReadOnlyObjectProperty<JimixLayerModel> model;
    private final ReadOnlyMapProperty<UUID, JimixElement> elementMap =
            new ReadOnlyMapWrapper<UUID, JimixElement>(FXCollections.observableHashMap()).getReadOnlyProperty();
    private final ReadOnlyListProperty<JimixElement> elementList =
            new ReadOnlyListWrapper<>(FXCollections.observableArrayList(new JimixElementObserverCallback())).getReadOnlyProperty();

    private final ReadOnlyObjectProperty<JimixProject> project;

    private final ObjectBinding<Image> resultImage;

    public JimixLayer(final JimixProject project, final JimixLayerModel model) {
        this.project = new ReadOnlyObjectWrapper<>(project).getReadOnlyProperty();
        this.model = new ReadOnlyObjectWrapper<>(model).getReadOnlyProperty();

        for (final JimixElementModel elementModel : model.getElementList()) {
            final JimixElement element = new JimixElement(project, this, elementModel);
            elementMap.put(element.getUuid(), element);
        }

        //List Updater
        elementMap.addListener((MapChangeListener<UUID, JimixElement>) c -> {
            if (c.wasAdded()) {
                elementList.add(c.getValueAdded());
            }
            if (c.wasRemoved()) {
                elementList.remove(c.getValueRemoved());
            }
        });

        //Rebuild cached image if sub elements has changed
        resultImage = Bindings.createObjectBinding(
                () -> ImageBuilder.getInstance().buildLayerImage(this),
                elementList, model.filterListProperty()
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

    public JimixLayerModel getModel() {
        return model.get();
    }

    public ReadOnlyObjectProperty<JimixLayerModel> modelProperty() {
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

    //<editor-fold desc="Equals / Hashcode / String">
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JimixLayer that = (JimixLayer) o;
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

    //<editor-fold desc="Helper Classes">
    private static final class JimixElementObserverCallback implements Callback<JimixElement, Observable[]> {
        @Override
        public Observable[] call(JimixElement param) {
            final List<Observable> list = new ArrayList<>();
            list.addAll(Arrays.asList(param.getModel().getObservableValues()));

            return list.toArray(new Observable[list.size()]);
        }
    }
    //</editor-fold>
}
