package org.pcsoft.app.jimix.app.ui.dialog;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.pcsoft.app.jimix.app.ui.component.FilterList;
import org.pcsoft.app.jimix.app.ui.component.VariantComboBox;
import org.pcsoft.app.jimix.app.ui.component.prop_sheet.JimixPropertySheet;
import org.pcsoft.app.jimix.app.util.PropertyUtils;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixEffectVariant;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixFilterVariant;
import org.pcsoft.app.jimix.plugin.mani.manager.ManipulationPluginVariantManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FilterManagerDialogView implements FxmlView<FilterManagerDialogViewModel>, Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterManagerDialogView.class);

    @FXML
    private FilterList lstFilter;
    @FXML
    private ImageView imgPreview;
    @FXML
    private JimixPropertySheet propSheet;
    @FXML
    private VariantComboBox<JimixFilterVariant> cmbVariants;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private VBox pnlPreview;

    @InjectViewModel
    private FilterManagerDialogViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAdd.disableProperty().bind(cmbVariants.valueProperty().isNotNull());
        btnRemove.disableProperty().bind(Bindings.createBooleanBinding(
                () -> cmbVariants.getValue() == null || cmbVariants.getValue().isBuiltin(),
                cmbVariants.valueProperty()
        ));
        pnlPreview.disableProperty().bind(viewModel.selectedFilterProperty().isNull());

        cmbVariants.setItemLoader(() -> viewModel.getSelectedFilter() == null ? new ArrayList<>() : extractFilterVariants());

        imgPreview.imageProperty().bind(viewModel.resultImageProperty());
        viewModel.selectedFilterProperty().bind(lstFilter.getSelectionModel().selectedItemProperty());
        //Update variants and properties
        viewModel.selectedFilterProperty().addListener(o -> {
            propSheet.getItems().clear();
            cmbVariants.setValue(null);
            cmbVariants.refresh();

            if (viewModel.getSelectedFilter() == null)
                return;

            PropertyUtils.addProperties(propSheet, viewModel.getSelectedFilter().getConfiguration());
        });
        //Reset variants selection in case of changing settings
        viewModel.selectedFilterProperty().addListener((v, o, n) -> {
            if (o != null) {
                for (final Observable observable : o.getConfiguration().getObservables()) {
                    observable.removeListener(this::onConfigurationInvalidated);
                }
            }
            if (n != null) {
                for (final Observable observable : n.getConfiguration().getObservables()) {
                    observable.addListener(this::onConfigurationInvalidated);
                }
            }
        });
        ManipulationPluginVariantManager.effectVariantListProperty().addListener((ListChangeListener<JimixEffectVariant>) c -> cmbVariants.refresh());
        //Copy configuration values from variant into instance configuration
        cmbVariants.valueProperty().addListener((v, o, n) -> {
            if (n == null)
                return;

            viewModel.getSelectedFilter().getConfiguration().update(n.getConfiguration());
        });
    }

    private List<JimixFilterVariant> extractFilterVariants() {
        return ManipulationPluginVariantManager.getFilterVariantList().stream()
                .filter(item -> item.getConfiguration().getClass() == viewModel.getSelectedFilter().getConfiguration().getClass())
                .collect(Collectors.toList());
    }

    @FXML
    private void onActionAddVariant(ActionEvent actionEvent) {

    }

    @FXML
    private void onActionRemoveVariant(ActionEvent actionEvent) {

    }

    private void onConfigurationInvalidated(Observable obs) {
        cmbVariants.setValue(null);
    }
}
