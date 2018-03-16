package org.pcsoft.app.jimix.app.ui.component.cell.tree;

import javafx.geometry.Insets;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import org.pcsoft.app.jimix.app.item.tree.*;
import org.pcsoft.app.jimix.app.ui.component.cell.pane.ElementTreeCellPane;
import org.pcsoft.app.jimix.app.ui.component.cell.pane.LayerTreeCellPane;
import org.pcsoft.app.jimix.core.project.JimixElement;
import org.pcsoft.app.jimix.core.project.JimixLayer;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixEffectInstance;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixFilterInstance;

public class ProjectTreeCell extends TreeCell<Object> {
    private static final Color[] BACKGROUND_COLORS = new Color[]{
            Color.web("#D0D0D0"), Color.web("#D8D8D8"), Color.web("#E0E0E0"), Color.web("#E8E8E8"), Color.web("#F0F0F0"), Color.web("#F8F8F8")
    };

    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);
        setGraphic(null);
        setStyle(null);
        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        if (!empty) {
            if (getTreeItem() instanceof LayerTreeItem && item instanceof JimixLayer) {
                setGraphic(new LayerTreeCellPane((JimixLayer) item));
            } else if ((getTreeItem() instanceof PictureElementTreeItem || getTreeItem() instanceof MaskElementTreeItem) && item instanceof JimixElement) {
                setGraphic(new ElementTreeCellPane((JimixElement) item));
            } else if (getTreeItem() instanceof PictureElementRootTreeItem || getTreeItem() instanceof MaskElementRootTreeItem) {
                setText("Elements");
                setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/ic_elements24.png"))));
                setStyle("-fx-font-weight: bold");
            } else if (getTreeItem() instanceof FilterTreeItem && item instanceof JimixFilterInstance) {
                setText(((JimixFilterInstance) item).getPlugin().getName());//TODO
                setGraphic(new ImageView(((JimixFilterInstance) item).getPlugin().getIcon()));
            } else if (getTreeItem() instanceof FilterRootTreeItem) {
                setText("Filters");
                setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/ic_filter24.png"))));
            } else if (getTreeItem() instanceof EffectRootTreeItem) {
                setText("Effects");
                setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/ic_effect24.png"))));
            } else if (getTreeItem() instanceof EffectTreeItem) {
                setText(((JimixEffectInstance) item).getPlugin().getName());
                setGraphic(new ImageView(((JimixEffectInstance) item).getPlugin().getIcon()));
            } else if (getTreeItem() instanceof PictureRootTreeItem) {
                setText("Picture");
                setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/ic_picture24.png"))));
                setStyle("-fx-font-weight: bold");
            } else if (getTreeItem() instanceof MaskRootTreeItem) {
                setText("Mask");
                setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/ic_mask24.png"))));
                setStyle("-fx-font-weight: bold");
            }

            buildBackground();
        }
    }

    @Override
    public void updateSelected(boolean selected) {
        super.updateSelected(selected);

        
        if (getTreeItem() == null || isEmpty()) {
            return;
        }

        if (!selected) {
            buildBackground();
        } else {
            setBackground(new Background(new BackgroundFill(Color.SKYBLUE.brighter(), null, null), new BackgroundFill(Color.DEEPSKYBLUE, null, new Insets(2.5d))));
        }
    }

    private void buildBackground() {
        final int parentCount = getParentCount(getTreeItem()) - 1; //Ignore invisible root
        if (parentCount < BACKGROUND_COLORS.length) {
            setBackground(new Background(new BackgroundFill(BACKGROUND_COLORS[parentCount], null, null)));
        } else {
            setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        }
    }

    private static int getParentCount(TreeItem treeItem) {
        TreeItem current = treeItem;
        int counter = -1;

        while (current != null) {
            counter++;
            current = current.getParent();
        }

        return counter;
    }
}
