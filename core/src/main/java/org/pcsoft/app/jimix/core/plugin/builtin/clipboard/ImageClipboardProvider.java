package org.pcsoft.app.jimix.core.plugin.builtin.clipboard;

import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import org.pcsoft.app.jimix.core.plugin.builtin.model.JimixImageElementModel;
import org.pcsoft.app.jimix.plugins.api.JimixClipboardProvider;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixClipboardProviderDescriptor;

@JimixClipboardProviderDescriptor(name = "Paste as image", description = "Create an image element and paste it in active layer")
public class ImageClipboardProvider implements JimixClipboardProvider<JimixImageElementModel> {
    @Override
    public boolean acceptClipboardContent(Clipboard clipboard) {
        return clipboard.hasImage();
    }

    @Override
    public JimixImageElementModel createElementFromClipboard(Clipboard clipboard) {
        final Image image = clipboard.getImage();
        return new JimixImageElementModel(image);
    }
}
