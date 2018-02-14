package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import org.pcsoft.app.jimix.core.model.JimixLevel;
import org.pcsoft.app.jimix.core.plugin.type.JimixEffectHolder;
import org.pcsoft.app.jimix.core.plugin.type.JimixPixelReaderImpl;
import org.pcsoft.app.jimix.core.plugin.type.JimixPixelWriterImpl;
import org.pcsoft.app.jimix.plugins.api.type.JimixApplySource;

import java.net.URL;
import java.util.ResourceBundle;

public class PictureEditorPaneView implements FxmlView<PictureEditorPaneViewModel>, Initializable {
    @FXML
    private ImageView imgPicture;
    @FXML
    private ImageView imgMask;
    
    @InjectViewModel
    private PictureEditorPaneViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgMask.fitWidthProperty().bind(imgPicture.fitWidthProperty());
        imgMask.fitHeightProperty().bind(imgPicture.fitHeightProperty());
        //imgMask.visibleProperty().bind(viewModel.maskProperty().isNotNull());

        imgPicture.imageProperty().bind(viewModel.resultPictureProperty());
        //imgMask.imageProperty().bind(viewModel.maskProperty());
    }

    void applyPictureEffect(final JimixEffectHolder effectHolder) {
        final JimixLevel jimixLevel = viewModel.getLevelList().get(0);

        final JimixPixelReaderImpl pixelReader = new JimixPixelReaderImpl(jimixLevel.getPicture().getPixelReader(), (int) jimixLevel.getPicture().getWidth(), (int) jimixLevel.getPicture().getHeight());
        final JimixPixelWriterImpl pixelWriter = new JimixPixelWriterImpl((int) jimixLevel.getPicture().getWidth(), (int) jimixLevel.getPicture().getHeight());
        try {
            effectHolder.apply(pixelReader, pixelWriter, effectHolder.getConfigurationClass().newInstance(), JimixApplySource.Picture);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        jimixLevel.setPicture(pixelWriter.buildImage());
    }
}
