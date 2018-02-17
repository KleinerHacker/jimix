package org.pcsoft.app.jimix.core.ui.component;

import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;

import java.util.*;

/**
 * Created by pfeifchr on 09.11.2016.
 */
public class ToolBox extends BorderPane {
    private final class ContentUpdater implements ListChangeListener<ToolBoxDescriptor> {
        @Override
        public void onChanged(Change<? extends ToolBoxDescriptor> c) {
            pnlContent.getItems().clear();
            c.getList().forEach(item -> pnlContent.getItems().add(item.getContent()));
            final long selectionCount = c.getList().size();
            if (selectionCount > 0) {
                for (int i = 0; i < selectionCount - 1; i++) {
                    pnlContent.getDividers().get(i).setPosition((1d / (double) selectionCount) * (i + 1));
                }
            }
        }
    }

    private final ObjectProperty<ToolBoxOrientation> orientation = new SimpleObjectProperty<>(ToolBoxOrientation.LEFT);
    private final ReadOnlyListProperty<ToolBoxDescriptor> items =
            new ReadOnlyListWrapper<ToolBoxDescriptor>(FXCollections.observableArrayList()).getReadOnlyProperty();
    private final ObjectProperty<MultipleSelectionModel<ToolBoxDescriptor>> selectionModel = new SimpleObjectProperty<>(new ToolBoxSelectionModel(this));
    private final DoubleProperty buttonWidth = new SimpleDoubleProperty(150d);

    private final SplitPane pnlContent = new SplitPane();
    private Pane pnlButtonBar = new VBox();

    private final ContentUpdater contentUpdater = new ContentUpdater();

    public ToolBox(ToolBoxDescriptor... toolBoxDescriptors) {
        this(Arrays.asList(toolBoxDescriptors));
    }

    public ToolBox(Collection<ToolBoxDescriptor> toolBoxDescriptors) {
        this();
        items.setAll(toolBoxDescriptors);
    }

    public ToolBox() {
        setCenter(pnlContent);
        updateLayout();

        orientation.addListener(o -> updateLayout());
        items.addListener((ListChangeListener<ToolBoxDescriptor>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (final ToolBoxDescriptor descriptor : c.getAddedSubList()) {
                        descriptor.setSelectionModel(selectionModel.get());
                        descriptor.setToolBox(this);
                        descriptor.widthProperty().bind(buttonWidth);
                    }
                }
                if (c.wasRemoved()) {
                    for (final ToolBoxDescriptor descriptor : c.getRemoved()) {
                        descriptor.setSelectionModel(null);
                        descriptor.setToolBox(null);
                        descriptor.widthProperty().unbind();
                    }
                }
            }

            updateLayout();
        });
        items.addListener((InvalidationListener) o -> updateLayout());

        selectionModel.addListener((v, o, n) -> {
            if (o != null) {
                o.getSelectedItems().removeListener(contentUpdater);
            }
            if (n != null) {
                n.getSelectedItems().addListener(contentUpdater);
            }
        });
        selectionModel.get().getSelectedItems().addListener(contentUpdater);
    }

    private void updateSelectionModelListener(final MultipleSelectionModel<ToolBoxDescriptor> selectionModel) {
        selectionModel.getSelectedIndices().addListener((ListChangeListener<Integer>) c -> {

        });
    }

    public ToolBoxOrientation getOrientation() {
        return orientation.get();
    }

    public ObjectProperty<ToolBoxOrientation> orientationProperty() {
        return orientation;
    }

    public void setOrientation(ToolBoxOrientation orientation) {
        this.orientation.set(orientation);
    }

    public ObservableList<ToolBoxDescriptor> getItems() {
        return items.get();
    }

    List<ToolBoxDescriptor> getSortedItems() {
        final List<ToolBoxDescriptor> list = new ArrayList<>(items);
        Collections.sort(list);

        return Collections.unmodifiableList(list);
    }

    public ReadOnlyListProperty<ToolBoxDescriptor> itemsProperty() {
        return items;
    }

    public SelectionMode getSelectionMode() {
        return selectionModel.get().getSelectionMode();
    }

    public ObjectProperty<SelectionMode> selectionModeProperty() {
        return selectionModel.get().selectionModeProperty();
    }

    public void setSelectionMode(SelectionMode selectionMode) {
        selectionModel.get().setSelectionMode(selectionMode);
    }

    public MultipleSelectionModel<ToolBoxDescriptor> getSelectionModel() {
        return selectionModel.get();
    }

    public ObjectProperty<MultipleSelectionModel<ToolBoxDescriptor>> selectionModelProperty() {
        return selectionModel;
    }

    public void setSelectionModel(MultipleSelectionModel<ToolBoxDescriptor> selectionModel) {
        this.selectionModel.set(selectionModel);
    }

    public double getButtonWidth() {
        return buttonWidth.get();
    }

    public DoubleProperty buttonWidthProperty() {
        return buttonWidth;
    }

    public void setButtonWidth(double buttonWidth) {
        this.buttonWidth.set(buttonWidth);
    }

    private void updateLayout() {
        //Cleanup
        pnlButtonBar.getChildren().clear();

        //Build up
        final double rotation;
        switch (orientation.get()) {
            case LEFT:
                pnlButtonBar = new VBox();
                setLeft(pnlButtonBar);
                pnlContent.setOrientation(Orientation.VERTICAL);
                rotation = -90d;
                break;
            case RIGHT:
                pnlButtonBar = new VBox();
                setRight(pnlButtonBar);
                pnlContent.setOrientation(Orientation.VERTICAL);
                rotation = 90d;
                break;
            case TOP:
                pnlButtonBar = new HBox();
                setTop(pnlButtonBar);
                pnlContent.setOrientation(Orientation.HORIZONTAL);
                rotation = 0;
                break;
            case BOTTOM:
                pnlButtonBar = new HBox();
                setBottom(pnlButtonBar);
                pnlContent.setOrientation(Orientation.HORIZONTAL);
                rotation = 0;
                break;
            default:
                throw new RuntimeException();
        }

        for (final ToolBoxDescriptor toolBoxDescriptor : getSortedItems()) {
            final ToggleButton toolButton = toolBoxDescriptor.getToolButton();
            toolButton.getTransforms().setAll(new Rotate(rotation));
            pnlButtonBar.getChildren().add(new Group(toolButton));
        }
    }
}
