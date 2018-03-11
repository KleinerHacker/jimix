package org.pcsoft.app.jimix.app.ui.component;

import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.plugin.manipulation.manager.ManipulationPluginManager;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixBlenderPlugin;
import org.pcsoft.framework.jfex.ui.component.data.ComboBoxEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BlenderComboBox extends ComboBoxEx<JimixBlenderPlugin, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlenderComboBox.class);

    public BlenderComboBox() {
        setHeaderKeyExtractor(instance -> instance.getGroup() == null ?
                LanguageResources.getText("common.plugin.group.default") : instance.getGroup());
        setHeaderComparator(String::compareTo);
        setHeaderCellRendererCallback(this::drawHeader);
        setValueComparator(Comparator.comparing(JimixBlenderPlugin::getName));
        setValueCellRendererCallback(this::drawValue);
        setValueButtonCellRendererCallback(this::drawValue);

        getItems().setAll(extractBlenders());
    }

    private List<JimixBlenderPlugin> extractBlenders() {
        return Arrays.asList(ManipulationPluginManager.getInstance().getAllBlenders());
    }

    private void drawValue(ListCell cell, JimixBlenderPlugin item, boolean empty) {
        cell.setText(null);
        cell.setGraphic(null);
        cell.setStyle("");
        if (item != null && !empty) {
            cell.setPadding(new Insets(cell.getPadding().getTop(), cell.getPadding().getRight(), cell.getPadding().getBottom(), 20));
            cell.setText(item.getName());
            cell.setGraphic(new ImageView(item.getIcon()));
        }
    }

    private void drawHeader(ListCell cell, String item, boolean empty) {
        cell.setText(null);
        cell.setGraphic(null);
        cell.setStyle("");
        if (item != null && !empty) {
            cell.setStyle("-fx-font-weight: bold");
            cell.setText(item);
        }
    }
}
