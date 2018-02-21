package org.pcsoft.app.jimix.app.ui.component.cell.pane;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import org.pcsoft.app.jimix.app.ex.InvertBooleanProperty;

import java.net.URL;
import java.util.ResourceBundle;

public class LayerTreeCellPaneView implements FxmlView<LayerTreeCellPaneViewModel>, Initializable {
    @FXML
    private ImageView imgPreview;
    @FXML
    private ToggleButton btnVisibility;
    @FXML
    private Label lblName;

    @InjectViewModel
    private LayerTreeCellPaneViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblName.textProperty().bind(viewModel.nameProperty());
        btnVisibility.selectedProperty().bindBidirectional(new InvertBooleanProperty(viewModel.visibilityProperty()));
        imgPreview.imageProperty().bind(viewModel.previewProperty());
    }
}
