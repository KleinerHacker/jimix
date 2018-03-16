package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.pcsoft.app.jimix.app.ui.component.prop_sheet.JimixPropertySheet;
import org.pcsoft.app.jimix.app.util.PropertyUtils;
import org.pcsoft.app.jimix.core.project.JimixElement;
import org.pcsoft.app.jimix.core.project.JimixLayer;
import org.pcsoft.app.jimix.core.project.ProjectManager;
import org.pcsoft.app.jimix.core.util.CheckerBoardPatternUtils;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPluginElement;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixEffectInstance;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixFilterInstance;
import org.pcsoft.framework.jfex.property.ExtendedWrapperProperty;
import org.pcsoft.framework.jfex.ui.component.toolbox.ToolBox;
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
        imgMask.visibleProperty().bind(viewModel.showMaskProperty());
        imgMask.imageProperty().bind(new MaskProperty());

        elementSelector.disableProperty().bind(lstLayer.selectedTopLayerProperty().isNull());

        toolBoxRight.getSelectionModel().selectAll();
        toolBoxLeft.getSelectionModel().selectAll();

        lstLayer.selectedItemProperty().addListener((v, o, n) -> refreshProperties());
        viewModel.selectedItemProperty().bind(lstLayer.selectedItemProperty());
    }

    private void refreshTransparentGround() {
        final GraphicsContext gc = canvasGround.getGraphicsContext2D();
        CheckerBoardPatternUtils.buildPattern(gc, canvasGround.getWidth(), canvasGround.getHeight());
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
        } else if (lstLayer.getSelectedItem() instanceof JimixEffectInstance) {
            PropertyUtils.addProperties(propSheet, ((JimixEffectInstance) lstLayer.getSelectedItem()).getConfiguration());
        }
    }

    void selectEffect(JimixEffectInstance instance) {
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
        if (lstLayer.getSelectedTopLayer() == null || lstLayer.getSelectedTopLayerType() == null)
            return;
        if (elementSelector.getSelectedElementBuilder() == null)
            return;

        try {
            final JimixPluginElement pluginElement = (JimixPluginElement) elementSelector.getSelectedElementBuilder().getElementModelClass().newInstance();
            switch (lstLayer.getSelectedTopLayerType()) {
                case Picture:
                    ProjectManager.getInstance().createPictureElementForLayer(lstLayer.getSelectedTopLayer(), pluginElement, (int) e.getX(), (int) e.getY());
                    break;
                case Mask:
                    ProjectManager.getInstance().createMaskElementForLayer(lstLayer.getSelectedTopLayer(), pluginElement, (int) e.getX(), (int) e.getY());
                    break;
                default:
                    throw new RuntimeException();
            }
        } catch (InstantiationException | IllegalAccessException e1) {
            LOGGER.error("Unable to create element " + elementSelector.getSelectedElementBuilder().getElementModelClass().getName(), e1);
            new Alert(Alert.AlertType.ERROR, "Unable to create element: " + e1.getMessage(), ButtonType.OK).showAndWait();
        } finally {
            if (!e.isShiftDown()) {
                elementSelector.setSelectedElementBuilder(null);
            }
        }
    }

    @FXML
    private void onMaskMouseClicked(MouseEvent e) {
        onPictureMouseClicked(e);
    }

    //<editor-fold desc="Helper Classes">
    private final class MaskProperty extends ExtendedWrapperProperty<Image> {
        public MaskProperty() {
            super(viewModel.selectedTopLayerProperty());

            viewModel.selectedTopLayerProperty().addListener((v, o, n) -> {
                if (o != null) {
                    o.resultMaskProperty().addListener(this::invalidated);
                }
                if (n != null) {
                    n.resultMaskProperty().addListener(this::invalidated);
                }
            });
        }

        @Override
        protected Image getPseudoValue() {
            if (viewModel.getSelectedTopLayer() == null)
                return null;
            if (viewModel.getSelectedTopLayer().getResultMask() == null)
                return null;

            return viewModel.getSelectedTopLayer().getResultMask();
        }

        @Override
        protected void setPseudoValue(Image image) {
            throw new IllegalStateException("Not supported");
        }

        private void invalidated(Observable obs) {
            fireValueChangedEvent();
        }
    }
    //</editor-fold>
}
