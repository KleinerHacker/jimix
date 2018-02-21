package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.pcsoft.framework.jfex.toolbox.ToolBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PictureEditorPaneView implements FxmlView<PictureEditorPaneViewModel>, Initializable {
    @FXML
    private ToolBox toolBoxLeft;
    @FXML
    private ToolBox toolBoxRight;

    @FXML
    private LayerList lstLayer;
    @FXML
    private ImageView imgPicture;
    @FXML
    private ImageView imgMask;
    @FXML
    private Canvas canvasGround;

    @InjectViewModel
    private PictureEditorPaneViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lstLayer.layerListProperty().bind(viewModel.layerListProperty());
        viewModel.selectedLayerProperty().bind(lstLayer.selectedLayerProperty());

        imgMask.fitWidthProperty().bind(imgPicture.fitWidthProperty());
        imgMask.fitHeightProperty().bind(imgPicture.fitHeightProperty());
        imgMask.visibleProperty().bind(Bindings.createBooleanBinding(
                () -> viewModel.getSelectedLayer() != null && viewModel.getSelectedLayer().getModel().getMask() != null,
                viewModel.selectedLayerProperty()
        ));

        canvasGround.widthProperty().bind(Bindings.createDoubleBinding(
                () -> viewModel.getResultPicture() == null ? 0 : viewModel.getResultPicture().getWidth(),
                viewModel.resultPictureProperty()
        ));
        canvasGround.heightProperty().bind(Bindings.createDoubleBinding(
                () -> viewModel.getResultPicture() == null ? 0 : viewModel.getResultPicture().getHeight(),
                viewModel.resultPictureProperty()
        ));
        canvasGround.widthProperty().addListener(o -> refreshTransparentGround());
        canvasGround.heightProperty().addListener(o -> refreshTransparentGround());
        refreshTransparentGround();

        imgPicture.imageProperty().bind(viewModel.resultPictureProperty());
        imgMask.imageProperty().bind(Bindings.createObjectBinding(
                () -> viewModel.getSelectedLayer() == null ? null : viewModel.getSelectedLayer().getModel().getMask(),
                viewModel.selectedLayerProperty()
        ));

        toolBoxRight.getSelectionModel().selectAll();
        toolBoxLeft.getSelectionModel().selectAll();
    }

    private void refreshTransparentGround() {
        final GraphicsContext gc = canvasGround.getGraphicsContext2D();
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0, canvasGround.getWidth(), canvasGround.getHeight());

        gc.setFill(Color.BLACK);
        for (int y = 0; y < canvasGround.getHeight() / 10; y++) {
            for (int x = 0; x < canvasGround.getWidth() / 10; x++) {
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) {
                    gc.fillRect(x * 10, y * 10, 10, 10);
                }
            }
        }
    }
}
