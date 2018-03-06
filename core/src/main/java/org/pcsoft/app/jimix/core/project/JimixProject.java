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
import org.apache.commons.lang.ArrayUtils;
import org.pcsoft.app.jimix.core.util.ImageBuilder;
import org.pcsoft.app.jimix.project.JimixLayerModel;
import org.pcsoft.app.jimix.project.JimixProjectModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Project host for the {@link JimixProjectModel} self, with additional app internal information
 */
public final class JimixProject {
    //Temporary identifier only
    private final ReadOnlyObjectProperty<UUID> uuid = new ReadOnlyObjectWrapper<>(UUID.randomUUID()).getReadOnlyProperty();
    private final ReadOnlyObjectProperty<JimixProjectModel> model;
    private final ObjectProperty<File> file = new SimpleObjectProperty<>();

    private final ReadOnlyMapProperty<UUID, JimixLayer> layerMap =
            new ReadOnlyMapWrapper<UUID, JimixLayer>(FXCollections.observableHashMap()).getReadOnlyProperty();
    private final ReadOnlyListProperty<JimixLayer> layerList =
            new ReadOnlyListWrapper<>(FXCollections.observableArrayList(new JimixLayerObserverCallback())).getReadOnlyProperty();

    private final ObjectBinding<Image> resultImage;

    JimixProject(final JimixProjectModel model) {
        this.model = new ReadOnlyObjectWrapper<>(model).getReadOnlyProperty();

        for (final JimixLayerModel layerModel : model.getLayerList()) {
            final JimixLayer layer = new JimixLayer(this, layerModel);
            layerMap.put(layer.getUuid(), layer);
        }

        //List Updater
        layerMap.addListener((MapChangeListener<UUID, JimixLayer>) c -> {
            if (c.wasAdded()) {
                layerList.add(c.getValueAdded());
            }
            if (c.wasRemoved()) {
                layerList.remove(c.getValueRemoved());
            }
        });

        resultImage = Bindings.createObjectBinding(
                () -> ImageBuilder.getInstance().buildProjectImage(this),
                //TODO: Optimize
                (Observable[]) ArrayUtils.add(model.getObservableValues(), layerList)
        );
    }

    public UUID getUuid() {
        return uuid.get();
    }

    public ReadOnlyObjectProperty<UUID> uuidProperty() {
        return uuid;
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

    public JimixProjectModel getModel() {
        return model.get();
    }

    public ReadOnlyObjectProperty<JimixProjectModel> modelProperty() {
        return model;
    }

    ObservableMap<UUID, JimixLayer> getLayerMap() {
        return layerMap.get();
    }

    ReadOnlyMapProperty<UUID, JimixLayer> layerMapProperty() {
        return layerMap;
    }

    public ObservableList<JimixLayer> getLayerList() {
        return layerList.get();
    }

    public ReadOnlyListProperty<JimixLayer> layerListProperty() {
        return layerList;
    }

    public JimixLayer getLayer(final UUID layerUUID) {
        return layerMap.get(layerUUID);
    }

    public Image getResultImage() {
        return resultImage.get();
    }

    public ObjectBinding<Image> resultImageProperty() {
        return resultImage;
    }

    /**
     * Turn all layers on project left and recalculate its position
     */
    public void turnLeft() {
        for (final JimixLayer layer : layerList) {
            layer.turnLeft();
        }

        turnAroundWidthAndHeight();
    }

    /**
     * Turn all layers on project right and recalculate its position
     */
    public void turnRight() {
        for (final JimixLayer layer : layerList) {
            layer.turnRight();
        }

        turnAroundWidthAndHeight();
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private void turnAroundWidthAndHeight() {
        final int width = model.get().getWidth();
        final int height = model.get().getHeight();
        model.get().setWidth(height);
        model.get().setHeight(width);
    }

    /**
     * Mirror all layers on project horizontal and recalculate its position
     */
    public void mirrorHorizontal() {
        for (final JimixLayer layer : layerList) {
            layer.mirrorHorizontal();
        }
    }

    /**
     * Mirror all layers on project vertical and recalculate its position
     */
    public void mirrorVertical() {
        for (final JimixLayer layer : layerList) {
            layer.mirrorVertical();
        }
    }

    //<editor-fold desc="Equals / Hashcode / ToString">
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
    //</editor-fold>

    //<editor-fold desc="Helper Classes">
    private static final class JimixLayerObserverCallback implements Callback<JimixLayer, Observable[]> {
        @Override
        public Observable[] call(JimixLayer param) {
            final List<Observable> list = new ArrayList<>();
            list.add(param.getModel().nameProperty());
            list.add(param.getModel().opacityProperty());
            list.add(param.getModel().visibilityProperty());
            list.add(param.getModel().filterListProperty());
            list.add(param.elementListProperty());

            return list.toArray(new Observable[list.size()]);
        }
    }
    //</editor-fold>
}
