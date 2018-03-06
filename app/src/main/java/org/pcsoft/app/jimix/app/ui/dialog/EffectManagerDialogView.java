package org.pcsoft.app.jimix.app.ui.dialog;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.pcsoft.app.jimix.app.ui.component.EffectList;
import org.pcsoft.app.jimix.app.ui.component.VariantComboBox;
import org.pcsoft.app.jimix.app.ui.component.prop_sheet.JimixPropertySheet;
import org.pcsoft.app.jimix.app.util.PropertyUtils;
import org.pcsoft.app.jimix.plugin.mani.api.config.JimixEffectConfiguration;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixEffectVariant;
import org.pcsoft.app.jimix.plugin.mani.manager.ManipulationPluginVariantManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class EffectManagerDialogView implements FxmlView<EffectManagerDialogViewModel>, Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(EffectManagerDialogView.class);

    @FXML
    private EffectList lstEffect;
    @FXML
    private ImageView imgPreview;
    @FXML
    private JimixPropertySheet propSheet;
    @FXML
    private VariantComboBox<JimixEffectVariant> cmbVariants;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private Slider sldZoom;
    @FXML
    private VBox pnlPreview;

    @InjectViewModel
    private EffectManagerDialogViewModel viewModel;

    private final AtomicBoolean ignoreConfigUpdate = new AtomicBoolean(false);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAdd.disableProperty().bind(cmbVariants.valueProperty().isNotNull());
        btnRemove.disableProperty().bind(Bindings.createBooleanBinding(
                () -> cmbVariants.getValue() == null || cmbVariants.getValue().isBuiltin(),
                cmbVariants.valueProperty()
        ));
        pnlPreview.disableProperty().bind(viewModel.selectedEffectProperty().isNull());

        imgPreview.imageProperty().bind(viewModel.resultImageProperty());
        viewModel.selectedEffectProperty().bind(lstEffect.getSelectionModel().selectedItemProperty());
        //Update variants and properties
        viewModel.selectedEffectProperty().addListener(o -> {
            propSheet.getItems().clear();
            cmbVariants.setValue(null);
            cmbVariants.getItems().clear();

            if (viewModel.getSelectedEffect() == null)
                return;

            cmbVariants.getItems().setAll(extractEffectVariants());
            PropertyUtils.addProperties(propSheet, viewModel.getSelectedEffect().getConfiguration());
        });
        //Reset variants selection in case of changing settings
        viewModel.selectedEffectProperty().addListener((v, o, n) -> {
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
        ManipulationPluginVariantManager.effectVariantListProperty().addListener(
                (ListChangeListener<JimixEffectVariant>) c -> cmbVariants.getItems().setAll(extractEffectVariants()));
        //Copy configuration values from variant into instance configuration
        cmbVariants.valueProperty().addListener((v, o, n) -> {
            if (n == null)
                return;

            //Setup configuration from combo box, so ignore update to stop reset to null
            ignoreConfigUpdate.set(true);
            try {
                viewModel.getSelectedEffect().getConfiguration().update(n.getConfiguration());
            } finally {
                ignoreConfigUpdate.set(false);
            }
        });
        viewModel.zoomProperty().bind(sldZoom.valueProperty());
    }

    private List<JimixEffectVariant> extractEffectVariants() {
        return ManipulationPluginVariantManager.getEffectVariantList().stream()
                .filter(item -> item.getConfiguration().getClass() == viewModel.getSelectedEffect().getConfiguration().getClass())
                .collect(Collectors.toList());
    }

    @FXML
    private void onActionAddVariant(ActionEvent actionEvent) {
        final Optional<String> result = new TextInputDialog() {{
            setTitle("New Effect Variant");
            setContentText("Enter name:");
        }}.showAndWait();
        if (result.isPresent()) {
            ManipulationPluginVariantManager.getEffectVariantList().add(
                    JimixEffectVariant.createCustom(result.get(), (JimixEffectConfiguration) viewModel.getSelectedEffect().getConfiguration().copy())
            );
        }
    }

    @FXML
    private void onActionRemoveVariant(ActionEvent actionEvent) {
        final Optional<ButtonType> result = new Alert(Alert.AlertType.WARNING, "You are sure to remove effect variant?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            ManipulationPluginVariantManager.getEffectVariantList().remove(cmbVariants.getValue());
        }
    }

    private void onConfigurationInvalidated(Observable obs) {
        if (ignoreConfigUpdate.get())
            return;

        cmbVariants.setValue(null);
    }
}
