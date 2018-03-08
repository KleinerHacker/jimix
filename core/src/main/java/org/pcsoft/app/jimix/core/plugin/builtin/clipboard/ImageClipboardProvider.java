package org.pcsoft.app.jimix.core.plugin.builtin.clipboard;

import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import org.pcsoft.app.jimix.core.plugin.builtin.model.ImagePluginElement;
import org.pcsoft.app.jimix.plugin.system.api.JimixClipboardProvider;
import org.pcsoft.app.jimix.plugin.system.api.annotation.JimixClipboardProviderDescriptor;

@JimixClipboardProviderDescriptor(name = "Paste as image", description = "Create an image element and paste it in active layer")
public class ImageClipboardProvider implements JimixClipboardProvider<ImagePluginElement> {
    @Override
    public boolean acceptClipboardContent(Clipboard clipboard) {
        return clipboard.hasImage();
    }

    @Override
    public ImagePluginElement createElementFromClipboard(Clipboard clipboard) {
        final Image image = clipboard.getImage();
        return new ImagePluginElement(image);
    }
}
