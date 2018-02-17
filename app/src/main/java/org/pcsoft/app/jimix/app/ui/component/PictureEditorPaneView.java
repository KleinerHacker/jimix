package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import org.pcsoft.app.jimix.core.ui.component.ToolBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PictureEditorPaneView implements FxmlView<PictureEditorPaneViewModel>, Initializable {
    @FXML
    private ToolBox toolBoxRight;

    @FXML
    private LayerList lstLayer;
    @FXML
    private ImageView imgPicture;
    @FXML
    private ImageView imgMask;

    @InjectViewModel
    private PictureEditorPaneViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Bindings.bindContent(lstLayer.getLayerList(), viewModel.layerListProperty());
        viewModel.selectedLayerProperty().bind(lstLayer.selectedLayerProperty());

        imgMask.fitWidthProperty().bind(imgPicture.fitWidthProperty());
        imgMask.fitHeightProperty().bind(imgPicture.fitHeightProperty());
        imgMask.visibleProperty().bind(Bindings.createBooleanBinding(
                () -> viewModel.getSelectedLayer() != null && viewModel.getSelectedLayer().getModel().getMask() != null,
                viewModel.selectedLayerProperty()
        ));
        
        imgPicture.imageProperty().bind(viewModel.resultPictureProperty());
        imgMask.imageProperty().bind(Bindings.createObjectBinding(
                () -> viewModel.getSelectedLayer() == null ? null : viewModel.getSelectedLayer().getModel().getMask(),
                viewModel.selectedLayerProperty()
        ));

        toolBoxRight.getSelectionModel().selectAll();
    }
}
