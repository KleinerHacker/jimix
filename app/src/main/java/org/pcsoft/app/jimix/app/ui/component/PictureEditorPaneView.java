package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.pcsoft.app.jimix.app.ui.component.prop_sheet.JimixPropertySheet;
import org.pcsoft.app.jimix.app.util.PropertyUtils;
import org.pcsoft.app.jimix.core.project.JimixElement;
import org.pcsoft.app.jimix.core.project.JimixLayer;
import org.pcsoft.app.jimix.core.project.ProjectManager;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.Jimix2DEffectInstance;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixFilterInstance;
import org.pcsoft.framework.jfex.toolbox.ToolBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class PictureEditorPaneView implements FxmlView<PictureEditorPaneViewModel>, Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PictureEditorPaneView.class);

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
    @FXML
    private ElementSelector elementSelector;

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

        elementSelector.disableProperty().bind(lstLayer.selectedTopLayerProperty().isNull());

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
            PropertyUtils.addProperties(propSheet, ((JimixLayer) lstLayer.getSelectedItem()).getModel());
        } else if (lstLayer.getSelectedItem() instanceof JimixElement) {
            PropertyUtils.addProperties(propSheet, ((JimixElement) lstLayer.getSelectedItem()).getModel());
            PropertyUtils.addProperties(propSheet, (((JimixElement) lstLayer.getSelectedItem()).getModel()).getPluginElement());
        } else if (lstLayer.getSelectedItem() instanceof JimixFilterInstance) {
            PropertyUtils.addProperties(propSheet, ((JimixFilterInstance) lstLayer.getSelectedItem()).getConfiguration());
        } else if (lstLayer.getSelectedItem() instanceof Jimix2DEffectInstance) {
            PropertyUtils.addProperties(propSheet, ((Jimix2DEffectInstance) lstLayer.getSelectedItem()).getConfiguration());
        }
    }

    void selectEffect(Jimix2DEffectInstance instance) {
        lstLayer.selectEffect(instance);
    }

    void selectFilter(JimixFilterInstance instance) {
        lstLayer.selectFilter(instance);
    }

    void selectElement(JimixElement element) {
        lstLayer.selectElement(element);
    }

    void selectLayer(JimixLayer layer) {
        lstLayer.selectLayer(layer);
    }

    @FXML
    private void onPictureMouseClicked(MouseEvent e) {
        if (e.getButton() != MouseButton.PRIMARY && e.getClickCount() != 1)
            return;
        if (lstLayer.getSelectedTopLayer() == null)
            return;
        if (elementSelector.getSelectedElementBuilder() == null)
            return;

        try {
            final JimixPlugin2DElement pluginElement = elementSelector.getSelectedElementBuilder().getElementModelClass().newInstance();
            ProjectManager.getInstance().createElementForLayer(lstLayer.getSelectedTopLayer(), pluginElement);
        } catch (InstantiationException | IllegalAccessException e1) {
            LOGGER.error("Unable to create element " + elementSelector.getSelectedElementBuilder().getElementModelClass().getName(), e1);
            new Alert(Alert.AlertType.ERROR, "Unable to create element: " + e1.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    @FXML
    private void onMaskMouseClicked(MouseEvent e) {

    }
}
