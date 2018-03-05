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
import org.pcsoft.app.jimix.app.ui.component.prop_sheet.JimixPropertySheet;
import org.pcsoft.app.jimix.app.util.PropertyUtils;
import org.pcsoft.app.jimix.core.project.JimixElement;
import org.pcsoft.app.jimix.core.project.JimixLayer;
import org.pcsoft.app.jimix.plugins.manager.type.JimixEffectInstance;
import org.pcsoft.app.jimix.plugins.manager.type.JimixFilterInstance;
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
    @FXML
    private JimixPropertySheet propSheet;

    @InjectViewModel
    private PictureEditorPaneViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lstLayer.layerListProperty().bind(viewModel.layerListProperty());
        viewModel.selectedTopLayerProperty().bind(lstLayer.selectedTopLayerProperty());

        imgMask.fitWidthProperty().bind(imgPicture.fitWidthProperty());
        imgMask.fitHeightProperty().bind(imgPicture.fitHeightProperty());
        imgMask.visibleProperty().bind(Bindings.createBooleanBinding(
                () -> viewModel.getSelectedTopLayer() != null && viewModel.getSelectedTopLayer().getModel().getMask() != null,
                viewModel.selectedTopLayerProperty()
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
                () -> viewModel.getSelectedTopLayer() == null ? null : viewModel.getSelectedTopLayer().getModel().getMask(),
                viewModel.selectedTopLayerProperty()
        ));

        toolBoxRight.getSelectionModel().selectAll();
        toolBoxLeft.getSelectionModel().selectAll();

        lstLayer.selectedItemProperty().addListener((v, o, n) -> refreshProperties());
        viewModel.selectedItemProperty().bind(lstLayer.selectedItemProperty());
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

    private void refreshProperties() {
        propSheet.getItems().clear();

        if (lstLayer.getSelectedItem() instanceof JimixLayer) {
            PropertyUtils.addProperties(propSheet, ((JimixLayer)lstLayer.getSelectedItem()).getModel());
        } else if (lstLayer.getSelectedItem() instanceof JimixElement) {
            PropertyUtils.addProperties(propSheet, ((JimixElement) lstLayer.getSelectedItem()).getModel());
        } else if (lstLayer.getSelectedItem() instanceof JimixFilterInstance) {
            PropertyUtils.addProperties(propSheet, ((JimixFilterInstance) lstLayer.getSelectedItem()).getConfiguration());
        } else if (lstLayer.getSelectedItem() instanceof JimixEffectInstance) {
            PropertyUtils.addProperties(propSheet, ((JimixEffectInstance) lstLayer.getSelectedItem()).getConfiguration());
        }
    }
}
