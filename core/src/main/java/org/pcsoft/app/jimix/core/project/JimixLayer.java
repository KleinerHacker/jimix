package org.pcsoft.app.jimix.core.project;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.transform.Transform;
import javafx.util.Callback;
import org.apache.commons.lang.ArrayUtils;
import org.pcsoft.app.jimix.core.util.ImageBuilder;
import org.pcsoft.app.jimix.project.JimixLayerModel;
import org.pcsoft.app.jimix.project.JimixMaskElementModel;
import org.pcsoft.app.jimix.project.JimixPictureElementModel;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Layer holder for {@link JimixLayerModel}, with additional app internal information
 */
public final class JimixLayer implements JimixWrapper {
    //Temporary identifier only
    private final ReadOnlyObjectProperty<UUID> uuid = new ReadOnlyObjectWrapper<>(UUID.randomUUID()).getReadOnlyProperty();
    private final ReadOnlyObjectProperty<JimixLayerModel> model;
    private final BooleanProperty visible = new SimpleBooleanProperty(true);

    private final ReadOnlyMapProperty<UUID, JimixPictureElement> pictureElementMap =
            new ReadOnlyMapWrapper<UUID, JimixPictureElement>(FXCollections.observableHashMap()).getReadOnlyProperty();
    private final ReadOnlyListProperty<JimixPictureElement> pictureElementList =
            new ReadOnlyListWrapper<JimixPictureElement>(FXCollections.observableArrayList(new JimixElementObserverCallback<>())).getReadOnlyProperty();

    private final ReadOnlyMapProperty<UUID, JimixMaskElement> maskElementMap =
            new ReadOnlyMapWrapper<UUID, JimixMaskElement>(FXCollections.observableHashMap()).getReadOnlyProperty();
    private final ReadOnlyListProperty<JimixMaskElement> maskElementList =
            new ReadOnlyListWrapper<JimixMaskElement>(FXCollections.observableArrayList(new JimixElementObserverCallback<>())).getReadOnlyProperty();

    private final ReadOnlyObjectProperty<JimixProject> project;

    private final ObjectBinding<Image> resultPicture, resultMask;

    public JimixLayer(final JimixProject project, final JimixLayerModel model) {
        this.project = new ReadOnlyObjectWrapper<>(project).getReadOnlyProperty();
        this.model = new ReadOnlyObjectWrapper<>(model).getReadOnlyProperty();

        //List Updater
        pictureElementMap.addListener((MapChangeListener<UUID, JimixPictureElement>) c -> {
            if (c.wasAdded()) {
                pictureElementList.add(c.getValueAdded());
            }
            if (c.wasRemoved()) {
                pictureElementList.remove(c.getValueRemoved());
            }
        });
        //Sync element list from model
        for (final JimixPictureElementModel elementModel : model.getPictureElementList()) {
            final JimixPictureElement element = new JimixPictureElement(project, this, elementModel);
            pictureElementMap.put(element.getUuid(), element);
        }
        // Sync with model
        pictureElementList.addListener((ListChangeListener<JimixPictureElement>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    model.getPictureElementList().addAll(
                            c.getAddedSubList().stream()
                                    .map(JimixElement::getModel)
                                    .collect(Collectors.toList())
                    );
                }
                if (c.wasRemoved()) {
                    model.getPictureElementList().removeAll(
                            c.getRemoved().stream()
                                    .map(JimixElement::getModel)
                                    .collect(Collectors.toList())
                    );
                }
            }
        });

        //List Updater
        maskElementMap.addListener((MapChangeListener<UUID, JimixMaskElement>) c -> {
            if (c.wasAdded()) {
                maskElementList.add(c.getValueAdded());
            }
            if (c.wasRemoved()) {
                maskElementList.remove(c.getValueRemoved());
            }
        });
        //Sync element list from model
        for (final JimixMaskElementModel elementModel : model.getMaskElementList()) {
            final JimixMaskElement element = new JimixMaskElement(project, this, elementModel);
            maskElementMap.put(element.getUuid(), element);
        }
        // Sync with model
        maskElementList.addListener((ListChangeListener<JimixMaskElement>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    model.getMaskElementList().addAll(
                            c.getAddedSubList().stream()
                                    .map(JimixElement::getModel)
                                    .collect(Collectors.toList())
                    );
                }
                if (c.wasRemoved()) {
                    model.getMaskElementList().removeAll(
                            c.getRemoved().stream()
                                    .map(JimixElement::getModel)
                                    .collect(Collectors.toList())
                    );
                }
            }
        });

        //Rebuild cached image if sub elements has changed
        resultPicture = Bindings.createObjectBinding(
                () -> ImageBuilder.getInstance().buildLayerPictureImage(this),
                //TODO: Optimize
                (Observable[]) ArrayUtils.addAll(model.getObservables(), new Observable[]{pictureElementList, visible})
        );
        resultMask = Bindings.createObjectBinding(
                () -> ImageBuilder.getInstance().buildLayerMaskImage(this),
                //TODO: Optimize
                (Observable[]) ArrayUtils.addAll(model.getObservables(), new Observable[]{maskElementList, visible})
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

    ObservableMap<UUID, JimixPictureElement> getPictureElementMap() {
        return pictureElementMap.get();
    }

    ReadOnlyMapProperty<UUID, JimixPictureElement> pictureElementMapProperty() {
        return pictureElementMap;
    }

    public ObservableList<JimixPictureElement> getPictureElementList() {
        return pictureElementList.get();
    }

    public ReadOnlyListProperty<JimixPictureElement> pictureElementListProperty() {
        return pictureElementList;
    }

    ObservableMap<UUID, JimixMaskElement> getMaskElementMap() {
        return maskElementMap.get();
    }

    ReadOnlyMapProperty<UUID, JimixMaskElement> maskElementMapProperty() {
        return maskElementMap;
    }

    public ObservableList<JimixMaskElement> getMaskElementList() {
        return maskElementList.get();
    }

    public ReadOnlyListProperty<JimixMaskElement> maskElementListProperty() {
        return maskElementList;
    }

    public JimixPictureElement getElement(final UUID elementUUID) {
        return pictureElementMap.get(elementUUID);
    }

    public Image getResultPicture() {
        return resultPicture.get();
    }

    public ObjectBinding<Image> resultPictureProperty() {
        return resultPicture;
    }

    public Image getResultMask() {
        return resultMask.get();
    }

    public ObjectBinding<Image> resultMaskProperty() {
        return resultMask;
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

    /**
     * Turn all elements on layer left and recalculate its position
     */
    public void turnLeft() {
        for (final JimixElement element : pictureElementList) {
            element.getModel().setRotation(element.getModel().getRotation() - 90);

            calculateRotation(element, -90);
        }
    }

    /**
     * Turn all elements on layer right and recalculate its position
     */
    public void turnRight() {
        for (final JimixElement element : pictureElementList) {
            element.getModel().setRotation(element.getModel().getRotation() + 90);

            calculateRotation(element, 90);
        }
    }

    private void calculateRotation(JimixElement element, double rotation) {
        final Transform rotate = Transform.rotate(rotation, project.get().getModel().getWidth() / 2, project.get().getModel().getHeight() / 2);
        final Point2D transform = rotate.transform(element.getModel().getX(), element.getModel().getY());
        element.getModel().setX((int) transform.getX());
        element.getModel().setY((int) transform.getY());
    }

    /**
     * Mirror all elements on layer horizontal and recalculate its position
     */
    public void mirrorHorizontal() {
        for (final JimixElement element : pictureElementList) {
            element.getModel().setMirrorHorizontal(!element.getModel().isMirrorHorizontal());
            //TODO
            //element.getModel().setX(project.get().getModel().getWidth() - (element.getModel().getX() + element.getModel().getWidth()));
        }
    }

    /**
     * Mirror all elements on layer vertical and recalculate its position
     */
    public void mirrorVertical() {
        for (final JimixElement element : pictureElementList) {
            element.getModel().setMirrorVertical(!element.getModel().isMirrorVertical());
            //TODO
            //element.getModel().setY(project.get().getModel().getHeight() - (element.getModel().getY() + element.getModel().getHeight()));
        }
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[] {
                visible, resultPicture
        };
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
    private static final class JimixElementObserverCallback<T extends JimixElement> implements Callback<T, Observable[]> {
        @Override
        public Observable[] call(T param) {
            final List<Observable> list = new ArrayList<>();
            //TODO: Optimize
            list.addAll(Arrays.asList(param.getModel().getObservables()));
            list.addAll(Arrays.asList(param.getObservables()));

            return list.toArray(new Observable[list.size()]);
        }
    }
    //</editor-fold>
}
