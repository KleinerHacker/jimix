package org.pcsoft.app.jimix.app.ui.component;

import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugin.mani.manager.ManipulationPluginManager;
import org.pcsoft.app.jimix.plugin.mani.manager.type.JimixFilterInstance;
import org.pcsoft.app.jimix.plugin.mani.manager.type.JimixFilterPlugin;
import org.pcsoft.framework.jfex.data.ListViewEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FilterList extends ListViewEx<JimixFilterInstance, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterList.class);

    public FilterList() {
        setHeaderKeyExtractor(instance -> instance.getPlugin().getGroup() == null ?
                LanguageResources.getText("common.plugin.group.default") : instance.getPlugin().getGroup());
        setHeaderComparator(String::compareTo);
        setHeaderCellRendererCallback(this::drawHeader);
        setValueComparator(Comparator.comparing(o -> o.getPlugin().getName()));
        setValueCellRendererCallback(this::drawValue);
        getItems().setAll(extractFilters());
    }

    private List<JimixFilterInstance> extractFilters() {
        final List<JimixFilterInstance> instanceList = new ArrayList<>();
        for (final JimixFilterPlugin plugin : ManipulationPluginManager.getInstance().getAllFilters()) {
            try {
                final JimixFilterInstance instance = plugin.createInstance();
                instanceList.add(instance);
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to create instance for filter " + plugin.getIdentifier() + ", skip", e);
                continue;
            }
        }
        return instanceList;
    }

    private void drawValue(ListCell cell, JimixFilterInstance item, boolean empty) {
        cell.setText(null);
        cell.setGraphic(null);
        cell.setStyle("");
        if (item != null && !empty) {
            cell.setText(item.getPlugin().getName());
            cell.setGraphic(new ImageView(item.getPlugin().getIcon()));
            cell.setPadding(new Insets(cell.getPadding().getTop(), cell.getPadding().getRight(), cell.getPadding().getBottom(), 20));
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
