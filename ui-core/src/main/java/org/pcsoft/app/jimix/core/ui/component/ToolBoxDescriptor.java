package org.pcsoft.app.jimix.core.ui.component;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ToggleButton;

import java.util.concurrent.atomic.AtomicBoolean;

public final class ToolBoxDescriptor implements Comparable<ToolBoxDescriptor> {
    private final IntegerProperty orderNumber = new SimpleIntegerProperty();
    private final ObjectProperty<Node> content = new SimpleObjectProperty<>();
    private final ReadOnlyObjectProperty<ToggleButton> toolButton = new ReadOnlyObjectWrapper<>(new ToggleButton());

    private MultipleSelectionModel<ToolBoxDescriptor> selectionModel = null;
    private ToolBox toolBox = null;

    private final AtomicBoolean ignoreUpdate = new AtomicBoolean(false);

    public ToolBoxDescriptor(final int orderNumber, final String title, final Node content) {
        this(orderNumber, title, null, content);
    }

    public ToolBoxDescriptor(final int orderNumber, final String title, final Node graphic, final Node content) {
        this();
        this.orderNumber.set(orderNumber);
        this.toolButton.get().setText(title);
        this.toolButton.get().setGraphic(graphic);
        this.toolButton.get().setId("tool-button-" + orderNumber);
        this.content.set(content);
    }

    public ToolBoxDescriptor() {
        toolButton.get().getStyleClass().setAll("tool-button");
        toolButton.get().getStylesheets().setAll("css/tool-button.css");
        toolButton.get().selectedProperty().addListener((v, o, n) -> {
            if (ignoreUpdate.get())
                return;
            if (toolBox == null || selectionModel == null)
                return;

            final int index = toolBox.getSortedItems().indexOf(this);
            if (index < 0)
                return;

            ignoreUpdate.set(true);
            try {
                if (n) {
                    selectionModel.select(index);
                } else {
                    selectionModel.clearSelection(index);
                }
            } finally {
                ignoreUpdate.set(false);
            }
        });
        toolButton.get().disabledProperty().addListener((v, o, n) -> {
            if (n) {
                toolButton.get().setSelected(false);
            }
        });
    }

    public int getOrderNumber() {
        return orderNumber.get();
    }

    public IntegerProperty orderNumberProperty() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber.set(orderNumber);
    }

    public String getTitle() {
        return toolButton.get().getText();
    }

    public StringProperty titleProperty() {
        return toolButton.get().textProperty();
    }

    public void setTitle(String title) {
        toolButton.get().setText(title);
    }

    public Node getGraphic() {
        return toolButton.get().getGraphic();
    }

    public ObjectProperty<Node> graphicProperty() {
        return toolButton.get().graphicProperty();
    }

    public void setGraphic(Node graphic) {
        this.toolButton.get().setGraphic(graphic);
    }

    public Node getContent() {
        return content.get();
    }

    public ObjectProperty<Node> contentProperty() {
        return content;
    }

    public void setContent(Node content) {
        this.content.set(content);
    }

    public boolean isDisable() {
        return toolButton.get().isDisable();
    }

    public BooleanProperty disableProperty() {
        return toolButton.get().disableProperty();
    }

    public void setDisable(boolean disable) {
        toolButton.get().setDisable(disable);
    }

    public boolean isSelected() {
        return toolButton.get().isSelected();
    }

    public BooleanBinding selectedProperty() {
        return Bindings.createBooleanBinding(() -> toolButton.get().isSelected(), toolButton.get().selectedProperty());
    }

    void setSelected(final boolean selected) {
        toolButton.get().setSelected(selected);
    }

    ToggleButton getToolButton() {
        return toolButton.get();
    }

    ReadOnlyObjectProperty<ToggleButton> toolButtonProperty() {
        return toolButton;
    }

    MultipleSelectionModel<ToolBoxDescriptor> getSelectionModel() {
        return selectionModel;
    }

    void setSelectionModel(MultipleSelectionModel<ToolBoxDescriptor> selectionModel) {
        this.selectionModel = selectionModel;
    }

    ToolBox getToolBox() {
        return toolBox;
    }

    void setToolBox(ToolBox toolBox) {
        this.toolBox = toolBox;
    }

    DoubleProperty widthProperty() {
        return toolButton.get().prefWidthProperty();
    }

    @Override
    public int compareTo(ToolBoxDescriptor o) {
        return Integer.compare(orderNumber.get(), o.getOrderNumber());
    }
}
