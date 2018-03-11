package org.pcsoft.app.jimix.app.ui.dialog;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.pcsoft.app.jimix.app.ui.component.BlenderComboBox;
import org.pcsoft.framework.jfex.ui.component.paint.PaintPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LayerCreateSimpleDialogView implements FxmlView<LayerCreateSimpleDialogViewModel>, Initializable {
    @FXML
    private BlenderComboBox cmbBelnder;
    @FXML
    private TextField txtName;
    @FXML
    private PaintPane paintPane;

    @InjectViewModel
    private LayerCreateSimpleDialogViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paintPane.selectedPaintProperty().bindBidirectional(viewModel.paintProperty());
        txtName.textProperty().bindBidirectional(viewModel.nameProperty());
        cmbBelnder.valueProperty().bindBidirectional(viewModel.blenderProperty());
    }
}
