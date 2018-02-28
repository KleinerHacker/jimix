package org.pcsoft.app.jimix.app.ui.component;

import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.core.plugin.PluginManager;
import org.pcsoft.app.jimix.core.plugin.type.JimixFilterInstance;
import org.pcsoft.app.jimix.core.plugin.type.JimixFilterPlugin;
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
        setHeaderCellRendererCallback((cell, item, empty) -> {
            cell.setText(null);
            cell.setStyle("-fx-font-weight: bold");
            if (item != null && !empty) {
                cell.setText(item);
            }
        });
        setValueComparator(Comparator.comparing(o -> o.getPlugin().getName()));
        setValueCellRendererCallback((cell, item, empty) -> {
            cell.setText(null);
            cell.setGraphic(null);
            if (item != null && !empty) {
                cell.setText(item.getPlugin().getName());
                cell.setGraphic(new ImageView(item.getPlugin().getIcon()));
                cell.setPadding(new Insets(0, 0, 0, 20));
            }
        });

        setItemLoader(() -> {
            final List<JimixFilterInstance> instanceList = new ArrayList<>();
            for (final JimixFilterPlugin plugin : PluginManager.getInstance().getAllFilters()) {
                try {
                    final JimixFilterInstance instance = plugin.createInstance();
                    instanceList.add(instance);
                } catch (JimixPluginException e) {
                    LOGGER.error("Unable to create instance for filter " + plugin.getIdentifier() + ", skip", e);
                    continue;
                }
            }
            return instanceList;
        });
    }
}
