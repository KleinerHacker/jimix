package org.pcsoft.app.jimix.app.ui.component.cell.pane;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ElementTreeCellPaneView implements FxmlView<ElementTreeCellPaneViewModel>, Initializable {
    @FXML
    private Label lblTitle;

    @InjectViewModel
    private ElementTreeCellPaneViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblTitle.textProperty().bind(viewModel.titleProperty());
    }
}
