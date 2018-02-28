package org.pcsoft.app.jimix.app.ui.dialog;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import org.pcsoft.app.jimix.app.ui.component.FilterList;
import org.pcsoft.app.jimix.app.ui.component.prop_sheet.JimixPropertySheet;
import org.pcsoft.app.jimix.app.util.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class FilterManagerDialogView implements FxmlView<FilterManagerDialogViewModel>, Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterManagerDialogView.class);

    @FXML
    private FilterList lstFilter;
    @FXML
    private ImageView imgPreview;
    @FXML
    private JimixPropertySheet propSheet;

    @InjectViewModel
    private FilterManagerDialogViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgPreview.imageProperty().bind(viewModel.resultImageProperty());
        viewModel.selectedFilterProperty().bind(lstFilter.getSelectionModel().selectedItemProperty());
        viewModel.selectedFilterProperty().addListener(o -> {
            propSheet.getItems().clear();

            if (viewModel.getSelectedFilter() == null)
                return;

            PropertyUtils.addProperties(propSheet, viewModel.getSelectedFilter().getConfiguration());
        });
    }
}
