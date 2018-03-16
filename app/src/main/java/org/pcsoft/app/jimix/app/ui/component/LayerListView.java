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
import org.pcsoft.app.jimix.core.project.JimixElement;
import org.pcsoft.app.jimix.core.project.JimixLayer;
import org.pcsoft.app.jimix.core.project.JimixMaskElement;
import org.pcsoft.app.jimix.core.project.JimixPictureElement;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixEffectInstance;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixFilterInstance;
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

            viewModel.setSelectedItem(null);
            viewModel.setSelectedTopLayer(null);

            if (n == null)
                return;

            if (n instanceof LayerTreeItem) {
                viewModel.setSelectedItem(((LayerTreeItem) n).getLayer());
            } else if (n instanceof PictureElementTreeItem) {
                viewModel.setSelectedItem(((PictureElementTreeItem) n).getElement());
            } else if (n instanceof MaskElementTreeItem) {
                viewModel.setSelectedItem(((MaskElementTreeItem) n).getElement());
            } else if (n instanceof FilterTreeItem) {
                viewModel.setSelectedItem(((FilterTreeItem) n).getInstance());
            } else if (n instanceof EffectTreeItem) {
                viewModel.setSelectedItem(((EffectTreeItem) n).getInstance());
            }

            //Find top layer
            TreeItem current = n;
            while (current != null && !(current instanceof LayerTreeItem)) {
                if (current instanceof PictureRootTreeItem) {
                    viewModel.setSelectedTopLayerType(LayerList.LayerSubType.Picture);
                } else if (current instanceof MaskRootTreeItem) {
                    viewModel.setSelectedTopLayerType(LayerList.LayerSubType.Mask);
                }
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

                //Picture
                final PictureRootTreeItem pictureRootTreeItem = new PictureRootTreeItem();
                {
                    //1. Elements
                    final PictureElementRootTreeItem pictureElementRootTreeItem = new PictureElementRootTreeItem();
                    for (final JimixPictureElement pictureElement : layer.getPictureElementList()) {
                        final PictureElementTreeItem pictureElementTreeItem = new PictureElementTreeItem(pictureElement);
                        pictureElementRootTreeItem.getChildren().add(pictureElementTreeItem);

                        final EffectRootTreeItem effectRootTreeItem = new EffectRootTreeItem();
                        //1.1 Effects
                        for (final JimixEffectInstance effectInstance : pictureElement.getModel().getEffectList()) {
                            final EffectTreeItem effectTreeItem = new EffectTreeItem(effectInstance);
                            effectRootTreeItem.getChildren().add(effectTreeItem);
                        }
                        pictureElementTreeItem.getChildren().add(effectRootTreeItem);
                    }
                    pictureRootTreeItem.getChildren().add(pictureElementRootTreeItem);

                    //2. Filters
                    final FilterRootTreeItem filterRootTreeItem = new FilterRootTreeItem();
                    for (final JimixFilterInstance filterInstance : layer.getModel().getFilterList()) {
                        final FilterTreeItem filterTreeItem = new FilterTreeItem(filterInstance);
                        filterRootTreeItem.getChildren().add(filterTreeItem);
                    }
                    pictureRootTreeItem.getChildren().add(filterRootTreeItem);
                }
                layerTreeItem.getChildren().add(pictureRootTreeItem);

                //Mask
                final MaskRootTreeItem maskRootTreeItem = new MaskRootTreeItem();
                {
                    //1. Elements
                    final MaskElementRootTreeItem maskElementRootTreeItem = new MaskElementRootTreeItem();
                    for (final JimixMaskElement maskElement : layer.getMaskElementList()) {
                        final MaskElementTreeItem maskElementTreeItem = new MaskElementTreeItem(maskElement);
                        maskElementRootTreeItem.getChildren().add(maskElementTreeItem);

                        //1.1. Effects
                        final EffectRootTreeItem effectRootTreeItem = new EffectRootTreeItem();
                        for (final JimixEffectInstance effectInstance : maskElement.getModel().getEffectList()) {
                            final EffectTreeItem effectTreeItem = new EffectTreeItem(effectInstance);
                            effectRootTreeItem.getChildren().add(effectTreeItem);
                        }
                        maskElementTreeItem.getChildren().add(effectRootTreeItem);

                        //1.2. Filters
                        final FilterRootTreeItem filterRootTreeItem = new FilterRootTreeItem();
                        for (final JimixFilterInstance filterInstance : maskElement.getModel().getFilterList()) {
                            final FilterTreeItem filterTreeItem = new FilterTreeItem(filterInstance);
                            filterRootTreeItem.getChildren().add(filterTreeItem);
                        }
                        maskElementTreeItem.getChildren().add(filterRootTreeItem);
                    }
                    maskRootTreeItem.getChildren().add(maskElementRootTreeItem);
                }
                layerTreeItem.getChildren().add(maskRootTreeItem);

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

    void selectEffect(final JimixEffectInstance instance) {
        FXTreeUtils.selectTreeItem(tvLayer, instance);
    }

    void selectFilter(final JimixFilterInstance instance) {
        FXTreeUtils.selectTreeItem(tvLayer, instance);
    }

    void selectElement(final JimixElement element) {
        FXTreeUtils.selectTreeItem(tvLayer, element);
    }

    void selectLayer(final JimixLayer layer) {
        FXTreeUtils.selectTreeItem(tvLayer, layer);
    }
}
