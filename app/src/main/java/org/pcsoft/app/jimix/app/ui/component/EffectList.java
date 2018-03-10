package org.pcsoft.app.jimix.app.ui.component;

import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugin.manipulation.manager.ManipulationPluginManager;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.Jimix2DEffectInstance;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.Jimix2DEffectPlugin;
import org.pcsoft.framework.jfex.ui.component.data.ListViewEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EffectList extends ListViewEx<Jimix2DEffectInstance, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EffectList.class);

    public EffectList() {
        setHeaderKeyExtractor(instance -> instance.getPlugin().getGroup() == null ?
                LanguageResources.getText("common.plugin.group.default") : instance.getPlugin().getGroup());
        setHeaderComparator(String::compareTo);
        setHeaderCellRendererCallback(this::drawHeader);
        setValueComparator(Comparator.comparing(o -> o.getPlugin().getName()));
        setValueCellRendererCallback(this::drawValue);
        getItems().setAll(extractEffects());
    }

    private List<Jimix2DEffectInstance> extractEffects() {
        final List<Jimix2DEffectInstance> instanceList = new ArrayList<>();
        for (final Jimix2DEffectPlugin plugin : ManipulationPluginManager.getInstance().getAll2DEffects()) {
            try {
                final Jimix2DEffectInstance instance = plugin.createInstance();
                instanceList.add(instance);
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to create instance for effect " + plugin.getIdentifier() + ", skip", e);
                continue;
            }
        }
        return instanceList;
    }

    private void drawValue(ListCell cell, Jimix2DEffectInstance item, boolean empty) {
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
