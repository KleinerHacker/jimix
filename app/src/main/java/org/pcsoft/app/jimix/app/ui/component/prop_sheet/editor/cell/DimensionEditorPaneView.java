package org.pcsoft.app.jimix.app.ui.component.prop_sheet.editor.cell;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import org.pcsoft.framework.jfex.property.NumberStringProperty;
import org.pcsoft.framework.jfex.util.EventHandlerUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class DimensionEditorPaneView implements FxmlView<DimensionEditorPaneViewModel>, Initializable {
    @FXML
    private ImageView imgProportional;
    @FXML
    private TextField txtWidth;
    @FXML
    private TextField txtHeight;
    @FXML
    private ToggleButton btnProportional;

    @InjectViewModel
    private DimensionEditorPaneViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtWidth.addEventHandler(KeyEvent.KEY_TYPED, EventHandlerUtils.TextFieldHandlers.createNumericIntegerInputRestrictionHandler());
        txtHeight.addEventHandler(KeyEvent.KEY_TYPED, EventHandlerUtils.TextFieldHandlers.createNumericIntegerInputRestrictionHandler());

        imgProportional.imageProperty().bind(
                Bindings.when(btnProportional.selectedProperty())
                .then(new Image(getClass().getResourceAsStream("/icons/ic_prop_act16.png")))
                .otherwise(new Image(getClass().getResourceAsStream("/icons/ic_prop_deact16.png")))
        );

        txtWidth.textProperty().bindBidirectional(new NumberStringProperty<>(viewModel.widthProperty(), Integer.class));
        txtHeight.textProperty().bindBidirectional(new NumberStringProperty<>(viewModel.heightProperty(), Integer.class));
        viewModel.proportionalProperty().bind(btnProportional.selectedProperty());
    }
}
