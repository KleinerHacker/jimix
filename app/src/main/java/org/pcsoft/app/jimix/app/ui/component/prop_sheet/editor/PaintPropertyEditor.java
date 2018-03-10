package org.pcsoft.app.jimix.app.ui.component.prop_sheet.editor;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import org.apache.commons.io.FileUtils;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.pcsoft.app.jimix.app.util.FileChooserUtils;
import org.pcsoft.framework.jfex.ui.component.paint.PaintPane;
import org.pcsoft.framework.jfex.util.FXChooserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PaintPropertyEditor extends AbstractPropertyEditor<Paint, PaintPane> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaintPropertyEditor.class);

    public PaintPropertyEditor(PropertySheet.Item property) {
        super(property, build());
    }

    @Override
    protected ObservableValue<Paint> getObservableValue() {
        return getEditor().selectedPaintProperty();
    }

    @Override
    public void setValue(Paint value) {
        getEditor().setSelectedPaint(value);
    }

    private static PaintPane build() {
        final PaintPane paintPane = new PaintPane();
        paintPane.setImageChooserCallback(() -> {
            final File file = FileChooserUtils.showOpenPictureFileChooser(false);
            if (file != null) {
                try (final InputStream in = FileUtils.openInputStream(file)) {
                    return new Image(in);
                } catch (IOException e) {
                    LOGGER.error("Unable to load image", e);
                    new Alert(Alert.AlertType.ERROR, "Unable to, load image: " + e.getMessage(), ButtonType.OK).showAndWait();
                }
            }

            return null;
        });

        return paintPane;
    }
}
