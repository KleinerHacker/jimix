package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.pcsoft.app.jimix.app.item.tree.*;
import org.pcsoft.app.jimix.app.ui.component.cell.tree.ProjectTreeCell;
import org.pcsoft.app.jimix.core.plugin.PluginManager;
import org.pcsoft.app.jimix.core.plugin.type.JimixFilterInstance;
import org.pcsoft.app.jimix.core.project.JimixElement;
import org.pcsoft.app.jimix.core.project.JimixLayer;
import org.pcsoft.framework.jfex.util.FXTreeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class LayerListView implements FxmlView<LayerListViewModel>, Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(LayerListView.class);

    @FXML
    private TreeView<Object> tvLayer;

    @InjectViewModel
    private LayerListViewModel viewModel;

    private final AtomicBoolean ignoreSelectionUpdate = new AtomicBoolean(false);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvLayer.setCellFactory(param -> new ProjectTreeCell());
        //Layer selection
        tvLayer.getSelectionModel().selectedItemProperty().addListener((v, o, n) -> {
            if (ignoreSelectionUpdate.get())
                return;

            viewModel.setSelectedLayer(null);
            viewModel.setSelectedElement(null);
            viewModel.setSelectedTopLayer(null);

            if (n == null)
                return;

            if (n instanceof LayerTreeItem) {
                viewModel.setSelectedLayer(((LayerTreeItem) n).getLayer());
            } else if (n instanceof ElementTreeItem) {
                viewModel.setSelectedElement(((ElementTreeItem) n).getElement());
            }

            //Find top layer
            TreeItem current = n;
            while (current != null && !(current instanceof LayerTreeItem)) {
                current = current.getParent();
            }
            viewModel.setSelectedTopLayer(current == null ? null : ((LayerTreeItem) current).getLayer());
        });

        viewModel.layerListProperty().addListener((ListChangeListener<JimixLayer>) c -> refreshTree(false));
        refreshTree(true);
    }

    private void refreshTree(final boolean firstRefresh) {
        //Ignore selection update to stop property sheet lost focus effect
        if (!firstRefresh) {
            ignoreSelectionUpdate.set(true);
        }
        try {
            //Save state for tree
            final FXTreeUtils.TreeStateInfo treeState = FXTreeUtils.getTreeState(tvLayer);

            tvLayer.setRoot(null);
            if (viewModel.getLayerList() == null || viewModel.getLayerList().isEmpty())
                return;

            final ProjectTreeItem projectTreeItem = new ProjectTreeItem();
            for (final JimixLayer layer : viewModel.getLayerList()) {
                final LayerTreeItem layerTreeItem = new LayerTreeItem(layer);

                //1. Elements
                final ElementRootTreeItem elementRootTreeItem = new ElementRootTreeItem();
                for (final JimixElement element : layer.getElementList()) {
                    final ElementTreeItem elementTreeItem = new ElementTreeItem(element);
                    elementRootTreeItem.getChildren().add(elementTreeItem);
                }
                layerTreeItem.getChildren().add(elementRootTreeItem);

                //2. Processing
                final FilterRootTreeItem filterRootTreeItem = new FilterRootTreeItem();
                for (final String filterClassName : layer.getModel().getFilterList()) {
                    JimixFilterInstance instance = PluginManager.getInstance().getFilter(filterClassName);
                    if (instance == null) {
                        LOGGER.warn("Unable to find filter " + filterClassName + ", ignore");
                        continue;
                    }

                    final FilterTreeItem filterTreeItem = new FilterTreeItem(instance);
                    filterRootTreeItem.getChildren().add(filterTreeItem);
                }
                layerTreeItem.getChildren().add(filterRootTreeItem);

                projectTreeItem.getChildren().add(layerTreeItem);
            }

            tvLayer.setRoot(projectTreeItem);

            //Restore state for tree
            FXTreeUtils.setTreeState(tvLayer, treeState);
        } finally {
            if (!firstRefresh) {
                ignoreSelectionUpdate.set(false);
            }
        }
    }
}
