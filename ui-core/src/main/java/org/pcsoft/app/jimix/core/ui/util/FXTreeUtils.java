package org.pcsoft.app.jimix.core.ui.util;

import javafx.scene.control.SelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;

import java.util.HashMap;
import java.util.Map;

public final class FXTreeUtils extends FXSelectionModelUtils {

    public static final class TreeStateInfo {
        private final Map<Integer, Boolean> expendedMap = new HashMap<>();
        private Integer selected;

        private TreeStateInfo() {
        }
    }

    //region Get Tree State
    public static TreeStateInfo getTreeState(final TreeTableView tree) {
        if (tree.getRoot() == null)
            return new TreeStateInfo();

        final TreeStateInfo treeState = getTreeState(tree.getRoot());
        treeState.selected = tree.getSelectionModel().getSelectedItem() == null ? null : tree.getSelectionModel().getSelectedItem().hashCode();

        return treeState;
    }

    public static TreeStateInfo getTreeState(final TreeView tree) {
        if (tree.getRoot() == null)
            return new TreeStateInfo();

        final TreeStateInfo treeState = getTreeState(tree.getRoot());
        treeState.selected = tree.getSelectionModel().getSelectedItem() == null ? null : tree.getSelectionModel().getSelectedItem().hashCode();

        return treeState;
    }

    private static TreeStateInfo getTreeState(final TreeItem rootItem) {
        final TreeStateInfo treeStateInfo = new TreeStateInfo();
        updateTreeState(treeStateInfo, rootItem);

        return treeStateInfo;
    }

    private static void updateTreeState(final TreeStateInfo treeStateInfo, final TreeItem<?> rootItem) {
        treeStateInfo.expendedMap.put(rootItem.hashCode(), rootItem.isExpanded());
        for (final TreeItem childItem : rootItem.getChildren()) {
            updateTreeState(treeStateInfo, childItem);
        }
    }
    //endregion

    //region Set Tree State
    public static void setTreeState(final TreeTableView tree, final TreeStateInfo treeState) {
        if (tree.getRoot() == null)
            return;

        final Map<Integer, TreeItem> treeItemMap = buildTreeItemMap(tree.getRoot());
        setTreeState(treeItemMap, treeState);
        if (treeState.selected != null) {
            if (treeItemMap.containsKey(treeState.selected)) {
                tree.getSelectionModel().select(
                        tree.getRow(treeItemMap.get(treeState.selected))
                );
            }
        }
    }

    public static void setTreeState(final TreeView tree, final TreeStateInfo treeState) {
        if (tree.getRoot() == null)
            return;

        final Map<Integer, TreeItem> treeItemMap = buildTreeItemMap(tree.getRoot());
        setTreeState(treeItemMap, treeState);
        if (treeState.selected != null) {
            if (treeItemMap.containsKey(treeState.selected)) {
                tree.getSelectionModel().select(
                        tree.getRow(treeItemMap.get(treeState.selected))
                );
            }
        }
    }

    private static void setTreeState(final Map<Integer, TreeItem> treeItemMap, final TreeStateInfo treeState) {
        treeItemMap.keySet().stream()
                .filter(treeState.expendedMap::containsKey)
                .forEach(key -> treeItemMap.get(key).setExpanded(treeState.expendedMap.get(key)));
    }

    private static Map<Integer, TreeItem> buildTreeItemMap(final TreeItem rootItem) {
        final Map<Integer, TreeItem> map = new HashMap<>();
        updateTreeItemMap(map, rootItem);

        return map;
    }

    private static void updateTreeItemMap(final Map<Integer, TreeItem> map, final TreeItem<?> rootItem) {
        map.put(rootItem.hashCode(), rootItem);
        for (final TreeItem childItem : rootItem.getChildren()) {
            updateTreeItemMap(map, childItem);
        }
    }
    //endregion

    //region Select Tree Item by value
    public static <T>boolean selectTreeItem(final TreeTableView<T> tree, T value) {
        if (value == null)
            return false;
        if (tree.getRoot() == null)
            return false;

        return searchAndSelectTreeItem(tree.getSelectionModel(), tree.getRoot(), value);
    }

    public static <T>boolean selectTreeItem(final TreeView<T> tree, T value) {
        if (value == null)
            return false;
        if (tree.getRoot() == null)
            return false;

        return searchAndSelectTreeItem(tree.getSelectionModel(), tree.getRoot(), value);
    }

    private static <T>boolean searchAndSelectTreeItem(final SelectionModel<TreeItem<T>> selectionModel, final TreeItem<T> rootItem, T value) {
        if (value.equals(rootItem.getValue())) {
            TreeItem<T> currentItem = rootItem.getParent();
            while (currentItem != null) {
                currentItem.setExpanded(true);
                currentItem = currentItem.getParent();
            }

            selectionModel.select(rootItem);
            return true;
        }

        for (final TreeItem<T> childItem : rootItem.getChildren()) {
            if (searchAndSelectTreeItem(selectionModel, childItem, value))
                return true;
        }

        return false;
    }
    //endregion

    public static <T> void reselect(TreeView<T> cmb) {
        reselect(cmb.getSelectionModel());
    }

    public static <T> void reselect(TreeTableView<T> cmb) {
        reselect(cmb.getSelectionModel());
    }

    private FXTreeUtils() {
    }
}
