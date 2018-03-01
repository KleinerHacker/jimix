package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectInfoPaneView implements FxmlView<ProjectInfoPaneViewModel>, Initializable {
    @FXML
    private Label lblDimension;

    @InjectViewModel
    private ProjectInfoPaneViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblDimension.textProperty().bind(viewModel.dimensionProperty());
    }
}
