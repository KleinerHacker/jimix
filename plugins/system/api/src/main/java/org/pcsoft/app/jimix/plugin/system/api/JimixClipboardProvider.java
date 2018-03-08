package org.pcsoft.app.jimix.plugin.system.api;

import javafx.scene.input.Clipboard;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPluginElement;

/**
 * Provider to convert clipboard content to viewable elements, see {@link JimixPluginElement}
 */
public interface JimixClipboardProvider<T extends JimixPluginElement> {
    boolean acceptClipboardContent(Clipboard clipboard);

    T createElementFromClipboard(Clipboard clipboard);
}
