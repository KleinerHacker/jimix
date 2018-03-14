package org.pcsoft.app.jimix.core.plugin.builtin.clipboard;

import javafx.scene.input.Clipboard;
import javafx.scene.paint.Color;
import org.pcsoft.app.jimix.core.plugin.builtin.model.TextPluginElement;
import org.pcsoft.app.jimix.plugin.system.api.JimixClipboardProvider;
import org.pcsoft.app.jimix.plugin.system.api.annotation.JimixClipboardProviderDescriptor;

@JimixClipboardProviderDescriptor(name = "Paste as text", description = "Create a text element and paste it in active layer")
public class TextClipboardProvider implements JimixClipboardProvider<TextPluginElement> {
    @Override
    public boolean acceptClipboardContent(Clipboard clipboard) {
        return clipboard.hasString();
    }

    @Override
    public TextPluginElement createElementFromClipboard(Clipboard clipboard) {
        final String text = clipboard.getString();
        final TextPluginElement pluginElement = new TextPluginElement();
        pluginElement.setText(text);
        pluginElement.setFill(Color.BLACK);

        return pluginElement;
    }
}
