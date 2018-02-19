package org.pcsoft.app.jimix.core.ui.util;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.List;

public final class FXListUtils extends FXSelectionModelUtils {

    public static <T> void reselect(TableView<T> cmb) {
        reselect(cmb.getSelectionModel());
    }

    public static <T> void reselect(ListView<T> cmb) {
        reselect(cmb.getSelectionModel());
    }

    public static <T> void reselect(ChoiceBox<T> cmb) {
        reselect(cmb.getSelectionModel());
    }

    public static <T> void reselect(ComboBox<T> cmb) {
        reselect(cmb.getSelectionModel());
    }

    public static <T> void moveBottom(ListView<T> listView, List<T> modelList) {
        moveDown(listView.getSelectionModel(), modelList, -1);
    }

    public static <T> void moveBottom(TableView<T> tableView, List<T> modelList) {
        moveDown(tableView.getSelectionModel(), modelList, -1);
    }

    public static <T> void moveDown(ListView<T> listView, List<T> modelList) {
        moveDown(listView.getSelectionModel(), modelList, 1);
    }

    public static <T> void moveDown(TableView<T> tableView, List<T> modelList) {
        moveDown(tableView.getSelectionModel(), modelList, 1);
    }

    public static <T> void moveDown(ListView<T> listView, List<T> modelList, int shiftCount) {
        moveDown(listView.getSelectionModel(), modelList, shiftCount);
    }

    public static <T> void moveDown(TableView<T> tableView, List<T> modelList, int shiftCount) {
        moveDown(tableView.getSelectionModel(), modelList, shiftCount);
    }

    public static <T> void moveTop(ListView<T> listView, List<T> modelList) {
        moveUp(listView.getSelectionModel(), modelList, -1);
    }

    public static <T> void moveTop(TableView<T> tableView, List<T> modelList) {
        moveUp(tableView.getSelectionModel(), modelList, -1);
    }

    public static <T> void moveUp(ListView<T> listView, List<T> modelList) {
        moveUp(listView.getSelectionModel(), modelList, 1);
    }

    public static <T> void moveUp(TableView<T> tableView, List<T> modelList) {
        moveUp(tableView.getSelectionModel(), modelList, 1);
    }

    public static <T> void moveUp(ListView<T> listView, List<T> modelList, int shiftCount) {
        moveUp(listView.getSelectionModel(), modelList, shiftCount);
    }

    public static <T> void moveUp(TableView<T> tableView, List<T> modelList, int shiftCount) {
        moveUp(tableView.getSelectionModel(), modelList, shiftCount);
    }

    private static <T> void moveUp(final SelectionModel<T> selectionModel, final List<T> modelList, final int shiftCount) {
        final int selectedIndex = selectionModel.getSelectedIndex();
        final T selectedItem = selectionModel.getSelectedItem();

        modelList.remove(selectedItem);

        if (shiftCount < 0) {
            modelList.add(0, selectedItem);
            selectionModel.select(0);
        } else {
            modelList.add(selectedIndex - shiftCount, selectedItem);
            selectionModel.select(selectedIndex - shiftCount);
        }
    }

    private static <T> void moveDown(final SelectionModel<T> selectionModel, final List<T> modelList, final int shiftCount) {
        final int selectedIndex = selectionModel.getSelectedIndex();
        final T selectedItem = selectionModel.getSelectedItem();

        modelList.remove(selectedItem);

        if (shiftCount < 0) {
            modelList.add(selectedItem);
            selectionModel.select(modelList.size() - 1);
        } else {
            modelList.add(selectedIndex + shiftCount, selectedItem);
            selectionModel.select(selectedIndex + shiftCount);
        }
    }

    public static <T> BooleanBinding buildMoveUpDisabledBinding(final ListView<T> listView, final int shiftCount) {
        return buildMoveUpDisabledBinding(listView.getSelectionModel(), shiftCount);
    }

    public static <T> BooleanBinding buildMoveUpDisabledBinding(final TableView<T> listView, final int shiftCount) {
        return buildMoveUpDisabledBinding(listView.getSelectionModel(), shiftCount);
    }

    public static <T> BooleanBinding buildMoveUpDisabledBinding(final ListView<T> listView) {
        return buildMoveUpDisabledBinding(listView.getSelectionModel(), 1);
    }

    public static <T> BooleanBinding buildMoveUpDisabledBinding(final TableView<T> listView) {
        return buildMoveUpDisabledBinding(listView.getSelectionModel(), 1);
    }

    public static <T> BooleanBinding buildMoveTopBinding(final ListView<T> listView) {
        return buildMoveUpDisabledBinding(listView.getSelectionModel(), -1);
    }

    public static <T> BooleanBinding buildMoveTopBinding(final TableView<T> listView) {
        return buildMoveUpDisabledBinding(listView.getSelectionModel(), -1);
    }

    public static <T> BooleanBinding buildMoveDownDisabledBinding(final ListView<T> listView, final int shiftCount) {
        return buildMoveDownDisabledBinding(listView.getSelectionModel(), listView.getItems(), shiftCount);
    }

    public static <T> BooleanBinding buildMoveDownDisabledBinding(final TableView<T> listView, final int shiftCount) {
        return buildMoveDownDisabledBinding(listView.getSelectionModel(), listView.getItems(), shiftCount);
    }

    public static <T> BooleanBinding buildMoveDownDisabledBinding(final ListView<T> listView) {
        return buildMoveDownDisabledBinding(listView.getSelectionModel(), listView.getItems(), 1);
    }

    public static <T> BooleanBinding buildMoveDownDisabledBinding(final TableView<T> listView) {
        return buildMoveDownDisabledBinding(listView.getSelectionModel(), listView.getItems(), 1);
    }

    public static <T> BooleanBinding buildMoveBottomBinding(final ListView<T> listView) {
        return buildMoveDownDisabledBinding(listView.getSelectionModel(), listView.getItems(), -1);
    }

    public static <T> BooleanBinding buildMoveBottomBinding(final TableView<T> listView) {
        return buildMoveDownDisabledBinding(listView.getSelectionModel(), listView.getItems(), -1);
    }

    private static <T> BooleanBinding buildMoveUpDisabledBinding(final MultipleSelectionModel<T> selectionModel, final int shiftCount) {
        if (shiftCount < 0) 
            return selectionModel.selectedItemProperty().isNull().or(selectionModel.selectedIndexProperty().lessThan(1)); 
            
        return selectionModel.selectedItemProperty().isNull().or(selectionModel.selectedIndexProperty().lessThan(shiftCount));
    }

    private static <T> BooleanBinding buildMoveDownDisabledBinding(final SelectionModel<T> selectionModel, final ObservableList<T> list, final int shiftCount) {
        if (shiftCount < 0)
            return selectionModel.selectedItemProperty().isNull().or(selectionModel.selectedIndexProperty().greaterThanOrEqualTo(
                    new SimpleListProperty<>(list).sizeProperty().subtract(1)));

        return selectionModel.selectedItemProperty().isNull().or(selectionModel.selectedIndexProperty().greaterThanOrEqualTo(
                new SimpleListProperty<>(list).sizeProperty().subtract(shiftCount)));
    }

    private FXListUtils() {
    }
}
