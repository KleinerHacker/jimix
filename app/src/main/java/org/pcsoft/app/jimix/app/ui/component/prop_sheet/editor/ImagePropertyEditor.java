package org.pcsoft.app.jimix.app.ui.component.prop_sheet.editor;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.pcsoft.app.jimix.app.util.FileChooserUtils;
import org.pcsoft.framework.jfex.ui.component.paint.PaintImagePane;

import java.io.File;
import java.io.IOException;

public class ImagePropertyEditor extends AbstractPropertyEditor<Image, PaintImagePane> {
    public ImagePropertyEditor(PropertySheet.Item property) {
        super(property, new PaintImagePane());
        getEditor().setImageChooserCallback(() -> {
            final File file = FileChooserUtils.showOpenPictureFileChooser(false);
            if (file != null)
                try {
                    return new Image(FileUtils.openInputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            return null;
        });
    }

    @Override
    protected ObservableValue<Image> getObservableValue() {
        return Bindings.createObjectBinding(
                () -> getEditor().getImagePattern() == null ? null : getEditor().getImagePattern().getImage(),
                getEditor().imagePatternProperty()
        );
    }

    @Override
    public void setValue(Image value) {
        getEditor().setImagePattern(null);
    }
}
