package org.pcsoft.app.jimix.core.ui.component;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;

import java.util.Collections;

/**
 * Created by pfeifchr on 09.11.2016.
 */
public class ToolBoxSelectionModel extends MultipleSelectionModel<ToolBoxDescriptor> {
    private final ReadOnlyObjectProperty<ToolBox> toolBox;
    private final ObservableList<Integer> selectedIndices = FXCollections.observableArrayList();
    private final ObservableList<ToolBoxDescriptor> selectedItems = FXCollections.observableArrayList(param -> new Observable[]{
            param.selectedProperty(), param.disableProperty(), param.titleProperty(), param.graphicProperty()
    });

    public ToolBoxSelectionModel(final ToolBox toolBox) {
        this.toolBox = new ReadOnlyObjectWrapper<>(toolBox).getReadOnlyProperty();
    }

    @Override
    public void clearAndSelect(int index) {
        if (toolBox.get().getSortedItems().get(index).isDisable())
            return;

        clearSelection();
        select(index);
    }

    @Override
    public void select(int index) {
        final ToolBoxDescriptor toolBoxDescriptor = toolBox.get().getSortedItems().get(index);

        if (toolBoxDescriptor.isDisable())
            return;

        switch (getSelectionMode()) {
            case SINGLE:
                clearSelection();
            case MULTIPLE:
                toolBoxDescriptor.setSelected(true);
                if (!selectedIndices.contains(index)) {
                    selectedIndices.add(index);
                    Collections.sort(selectedIndices);
                }
                if (!selectedItems.contains(toolBoxDescriptor)) {
                    selectedItems.add(toolBoxDescriptor);
                    Collections.sort(selectedItems);
                }
                setSelectedIndex(index);
                setSelectedItem(toolBoxDescriptor);
                break;
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public void select(ToolBoxDescriptor obj) {
        if (!toolBox.get().getSortedItems().contains(obj))
            return;
        if (obj.isDisable())
            return;

        select(toolBox.get().getSortedItems().indexOf(obj));
    }

    @Override
    public void clearSelection(int index) {
        final ToolBoxDescriptor toolBoxDescriptor = toolBox.get().getSortedItems().get(index);

        toolBoxDescriptor.setSelected(false);
        selectedIndices.remove((Integer)index);
        selectedItems.remove(toolBoxDescriptor);
        if (isEmpty()) {
            setSelectedIndex(-1);
            setSelectedItem(null);
        }
    }

    @Override
    public void clearSelection() {
        toolBox.get().getSortedItems().stream()
                .filter(ToolBoxDescriptor::isSelected)
                .forEach(item -> item.setSelected(false));
        selectedIndices.clear();
        selectedItems.clear();
        setSelectedIndex(-1);
        setSelectedItem(null);
    }

    @Override
    public boolean isSelected(int index) {
        return toolBox.get().getSortedItems().get(index).isSelected();
    }

    @Override
    public boolean isEmpty() {
        return toolBox.get().getSortedItems().stream()
                .allMatch((toolBoxDescriptor) -> !toolBoxDescriptor.isSelected());
    }

    @Override
    public void selectPrevious() {
        if (getSelectionMode() != SelectionMode.SINGLE)
            return;

        for (int i = 0; i < toolBox.get().getSortedItems().size(); i++) {
            if (toolBox.get().getSortedItems().get(i).isSelected()) {
                int counter = i;
                while (counter > 0) {
                    if (!toolBox.get().getSortedItems().get(counter).isDisable()) {
                        clearSelection();
                        select(counter);
                        break;
                    }
                    counter--;
                }
                break;
            }
        }
    }

    @Override
    public void selectNext() {
        if (getSelectionMode() != SelectionMode.SINGLE)
            return;

        for (int i = 0; i < toolBox.get().getSortedItems().size(); i++) {
            if (toolBox.get().getSortedItems().get(i).isSelected()) {
                int counter = i;
                while (counter < toolBox.get().getSortedItems().size() - 1) {
                    if (!toolBox.get().getSortedItems().get(counter).isDisable()) {
                        clearSelection();
                        select(counter);
                        break;
                    }
                    counter++;
                }
                break;
            }
        }
    }

    @Override
    public void selectFirst() {
        int counter = 0;
        while (counter < toolBox.get().getSortedItems().size()) {
            final ToolBoxDescriptor toolBoxDescriptor = toolBox.get().getSortedItems().get(counter);

            if (!toolBoxDescriptor.isDisable()) {
                select(counter);
                break;
            }
            counter++;
        }
    }

    @Override
    public void selectLast() {
        int counter = toolBox.get().getSortedItems().size() - 1;
        while (counter >= 0) {
            final ToolBoxDescriptor toolBoxDescriptor = toolBox.get().getSortedItems().get(counter);

            if (!toolBoxDescriptor.isDisable()) {
                select(counter);
                break;
            }
            counter--;
        }
    }

    @Override
    public ObservableList<Integer> getSelectedIndices() {
        return selectedIndices;
    }

    @Override
    public ObservableList<ToolBoxDescriptor> getSelectedItems() {
        return selectedItems;
    }

    @Override
    public void selectIndices(int index, int... indices) {
        select(index);

        if (getSelectionMode() == SelectionMode.SINGLE)
            return;

        for (final int i : indices) {
            select(i);
        }
    }

    @Override
    public void selectAll() {
        if (getSelectionMode() == SelectionMode.SINGLE)
            return;

        for (int i=0; i<toolBox.get().getSortedItems().size(); i++) {
            select(i);
        }
    }

    public ToolBox getToolBox() {
        return toolBox.get();
    }

    public ReadOnlyObjectProperty<ToolBox> toolBoxProperty() {
        return toolBox;
    }
}
