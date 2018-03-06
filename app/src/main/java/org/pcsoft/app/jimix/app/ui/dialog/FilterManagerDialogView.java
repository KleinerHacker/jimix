package org.pcsoft.app.jimix.app.ui.dialog;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.pcsoft.app.jimix.app.ui.component.FilterList;
import org.pcsoft.app.jimix.app.ui.component.VariantComboBox;
import org.pcsoft.app.jimix.app.ui.component.prop_sheet.JimixPropertySheet;
import org.pcsoft.app.jimix.app.util.PropertyUtils;
import org.pcsoft.app.jimix.plugin.mani.api.config.JimixFilterConfiguration;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixFilterVariant;
import org.pcsoft.app.jimix.plugin.mani.manager.ManipulationPluginVariantManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
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

    private final AtomicBoolean ignoreConfigurationUpdate = new AtomicBoolean(false);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAdd.disableProperty().bind(cmbVariants.valueProperty().isNotNull());
        btnRemove.disableProperty().bind(Bindings.createBooleanBinding(
                () -> cmbVariants.getValue() == null || cmbVariants.getValue().isBuiltin(),
                cmbVariants.valueProperty()
        ));
        pnlPreview.disableProperty().bind(viewModel.selectedFilterProperty().isNull());

        imgPreview.imageProperty().bind(viewModel.resultImageProperty());
        viewModel.selectedFilterProperty().bind(lstFilter.getSelectionModel().selectedItemProperty());
        //Update variants and properties
        viewModel.selectedFilterProperty().addListener(o -> {
            propSheet.getItems().clear();
            cmbVariants.setValue(null);
            cmbVariants.getItems().clear();

            if (viewModel.getSelectedFilter() == null)
                return;

            cmbVariants.getItems().setAll(extractFilterVariants());
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
        ManipulationPluginVariantManager.filterVariantListProperty().addListener(
                (ListChangeListener<JimixFilterVariant>) c -> cmbVariants.getItems().setAll(extractFilterVariants()));
        //Copy configuration values from variant into instance configuration
        cmbVariants.valueProperty().addListener((v, o, n) -> {
            if (n == null)
                return;

            //Setup configuration from combo box, so ignore update to stop reset to null
            ignoreConfigurationUpdate.set(true);
            try {
                viewModel.getSelectedFilter().getConfiguration().update(n.getConfiguration());
            } finally {
                ignoreConfigurationUpdate.set(false);
            }
        });
    }

    private List<JimixFilterVariant> extractFilterVariants() {
        return ManipulationPluginVariantManager.getFilterVariantList().stream()
                .filter(item -> item.getConfiguration().getClass() == viewModel.getSelectedFilter().getConfiguration().getClass())
                .collect(Collectors.toList());
    }

    @FXML
    private void onActionAddVariant(ActionEvent actionEvent) {
        final Optional<String> result = new TextInputDialog() {{
            setTitle("New Filter Variant");
            setContentText("Enter name:");
        }}.showAndWait();
        if (result.isPresent()) {
            ManipulationPluginVariantManager.getFilterVariantList().add(
                    JimixFilterVariant.createCustom(result.get(), (JimixFilterConfiguration) viewModel.getSelectedFilter().getConfiguration().copy())
            );
        }
    }

    @FXML
    private void onActionRemoveVariant(ActionEvent actionEvent) {
        final Optional<ButtonType> result = new Alert(Alert.AlertType.WARNING, "You are sure to remove filter variant?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            ManipulationPluginVariantManager.getFilterVariantList().remove(cmbVariants.getValue());
        }
    }

    private void onConfigurationInvalidated(Observable obs) {
        if (ignoreConfigurationUpdate.get())
            return;

        cmbVariants.setValue(null);
    }
}
