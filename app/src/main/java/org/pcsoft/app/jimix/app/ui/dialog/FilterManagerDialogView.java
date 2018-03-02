package org.pcsoft.app.jimix.app.ui.dialog;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.pcsoft.app.jimix.app.ui.component.FilterList;
import org.pcsoft.app.jimix.app.ui.component.FilterVariantComboBox;
import org.pcsoft.app.jimix.app.ui.component.prop_sheet.JimixPropertySheet;
import org.pcsoft.app.jimix.app.util.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class FilterManagerDialogView implements FxmlView<FilterManagerDialogViewModel>, Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterManagerDialogView.class);

    @FXML
    private FilterList lstFilter;
    @FXML
    private ImageView imgPreview;
    @FXML
    private JimixPropertySheet propSheet;
    @FXML
    private FilterVariantComboBox cmbVariants;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;

    @InjectViewModel
    private FilterManagerDialogViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAdd.disableProperty().bind(cmbVariants.valueProperty().isNotNull());
        btnRemove.disableProperty().bind(Bindings.createBooleanBinding(
                () -> cmbVariants.getValue() == null || cmbVariants.getValue().isBuiltin(),
                cmbVariants.valueProperty()
        ));

        cmbVariants.setItemLoader(() -> viewModel.getSelectedFilter() == null ? new ArrayList<>() : Arrays.asList(viewModel.getSelectedFilter().getPlugin().getVariants()));

        imgPreview.imageProperty().bind(viewModel.resultImageProperty());
        viewModel.selectedFilterProperty().bind(lstFilter.getSelectionModel().selectedItemProperty());
        viewModel.selectedFilterProperty().addListener(o -> {
            propSheet.getItems().clear();
            cmbVariants.setValue(null);
            cmbVariants.refresh();

            if (viewModel.getSelectedFilter() == null)
                return;

            PropertyUtils.addProperties(propSheet, viewModel.getSelectedFilter().getConfiguration());
        });

        cmbVariants.valueProperty().addListener((v, o, n) -> {
            if (n == null)
                return;

            viewModel.getSelectedFilter().getConfiguration().update(n.getConfiguration());
        });
    }

    @FXML
    private void onActionAddVariant(ActionEvent actionEvent) {

    }

    @FXML
    private void onActionRemoveVariant(ActionEvent actionEvent) {

    }
}
