package org.pcsoft.app.jimix.app.ui.dialog;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import org.controlsfx.control.PropertySheet;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.app.type.PropertySheetCallback;
import org.pcsoft.app.jimix.app.util.PropertyUtils;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.core.plugin.PluginManager;
import org.pcsoft.app.jimix.core.plugin.type.JimixFilterInstance;
import org.pcsoft.app.jimix.core.plugin.type.JimixFilterPlugin;
import org.pcsoft.framework.jfex.data.ListViewEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class FilterManagerDialogView implements FxmlView<FilterManagerDialogViewModel>, Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterManagerDialogView.class);

    @FXML
    private ListViewEx<JimixFilterInstance, String> lstFilter;
    @FXML
    private ImageView imgPreview;
    @FXML
    private PropertySheet propSheet;

    @InjectViewModel
    private FilterManagerDialogViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lstFilter.setHeaderKeyExtractor(instance -> instance.getPlugin().getGroup() == null ?
                LanguageResources.getText("common.plugin.group.default") : instance.getPlugin().getGroup());
        lstFilter.setHeaderComparator(String::compareTo);
        lstFilter.setHeaderCellRendererCallback((cell, item, empty) -> {
            cell.setText(null);
            if (item != null && !empty) {
                cell.setText(item);
            }
        });
        lstFilter.setValueComparator(Comparator.comparing(o -> o.getPlugin().getName()));
        lstFilter.setValueCellRendererCallback((cell, item, empty) -> {
            cell.setText(null);
            cell.setGraphic(null);
            if (item != null && !empty) {
                cell.setText(item.getPlugin().getName());
                cell.setGraphic(new ImageView(item.getPlugin().getIcon()));
            }
        });

        lstFilter.setItemLoader(() -> {
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

        imgPreview.imageProperty().bind(viewModel.resultImageProperty());
        viewModel.selectedFilterProperty().bind(lstFilter.getSelectionModel().selectedItemProperty());
        viewModel.selectedFilterProperty().addListener(o -> {
            propSheet.getItems().clear();

            if (viewModel.getSelectedFilter() == null)
                return;

            PropertyUtils.addProperties(propSheet, viewModel.getSelectedFilter().getConfiguration());
        });

        propSheet.setPropertyEditorFactory(new PropertySheetCallback(propSheet.getPropertyEditorFactory()));
    }
}
