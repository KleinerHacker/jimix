package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import org.pcsoft.app.jimix.core.project.JimixLevel;
import org.pcsoft.app.jimix.core.project.JimixLevelModel;
import org.pcsoft.app.jimix.core.plugin.type.JimixEffectInstance;
import org.pcsoft.app.jimix.core.plugin.type.JimixPixelReaderImpl;
import org.pcsoft.app.jimix.core.plugin.type.JimixPixelWriterImpl;
import org.pcsoft.app.jimix.plugins.api.type.JimixApplySource;

import java.net.URL;
import java.util.ResourceBundle;

public class PictureEditorPaneView implements FxmlView<PictureEditorPaneViewModel>, Initializable {
    @FXML
    private ListView<JimixLevel> lstLevel;
    @FXML
    private ImageView imgPicture;
    @FXML
    private ImageView imgMask;

    @InjectViewModel
    private PictureEditorPaneViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Bindings.bindContent(lstLevel.getItems(), viewModel.levelListProperty());
        viewModel.selectedLevelProperty().bind(lstLevel.getSelectionModel().selectedItemProperty());

        imgMask.fitWidthProperty().bind(imgPicture.fitWidthProperty());
        imgMask.fitHeightProperty().bind(imgPicture.fitHeightProperty());
        imgMask.visibleProperty().bind(Bindings.createBooleanBinding(
                () -> viewModel.getSelectedLevel() != null && viewModel.getSelectedLevel().getModel().getMask() != null,
                viewModel.selectedLevelProperty()
        ));
        
        imgPicture.imageProperty().bind(viewModel.resultPictureProperty());
        imgMask.imageProperty().bind(Bindings.createObjectBinding(
                () -> viewModel.getSelectedLevel() == null ? null : viewModel.getSelectedLevel().getModel().getMask(),
                viewModel.selectedLevelProperty()
        ));
    }
}
