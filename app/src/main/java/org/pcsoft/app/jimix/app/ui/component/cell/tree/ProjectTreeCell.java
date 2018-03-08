package org.pcsoft.app.jimix.app.ui.component.cell.tree;

import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;
import org.pcsoft.app.jimix.app.item.tree.*;
import org.pcsoft.app.jimix.app.ui.component.cell.pane.ElementTreeCellPane;
import org.pcsoft.app.jimix.app.ui.component.cell.pane.LayerTreeCellPane;
import org.pcsoft.app.jimix.core.project.JimixElement;
import org.pcsoft.app.jimix.core.project.JimixLayer;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.Jimix2DEffectInstance;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixEffectInstance;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixFilterInstance;

public class ProjectTreeCell extends TreeCell<Object> {
    private final LayerTreeCellPane layerPane = new LayerTreeCellPane();
    private final ElementTreeCellPane elementPane = new ElementTreeCellPane();

    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);
        setGraphic(null);
        if (!empty) {
            if (getTreeItem() instanceof LayerTreeItem && item instanceof JimixLayer) {
                layerPane.setValue((JimixLayer) item);
                setGraphic(layerPane);
            } else if (getTreeItem() instanceof ElementTreeItem && item instanceof JimixElement) {
                elementPane.setValue((JimixElement) item);
                setGraphic(elementPane);
            } else if (getTreeItem() instanceof ElementRootTreeItem) {
                setText("Elements");
            } else if (getTreeItem() instanceof FilterTreeItem && item instanceof JimixFilterInstance) {
                setText(((JimixFilterInstance) item).getPlugin().getName());//TODO
                setGraphic(new ImageView(((JimixFilterInstance) item).getPlugin().getIcon()));
            } else if (getTreeItem() instanceof FilterRootTreeItem) {
                setText("Filters");
            } else if (getTreeItem() instanceof EffectRootTreeItem) {
                setText("Effects");
            } else if (getTreeItem() instanceof EffectTreeItem) {
                setText(((JimixEffectInstance) item).getPlugin().getName());
                setGraphic(new ImageView(((JimixEffectInstance) item).getPlugin().getIcon()));
            }
        }
    }
}
