package org.pcsoft.app.jimix.plugin.system.api;

import javafx.scene.input.Clipboard;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;

/**
 * Provider to convert clipboard content to viewable elements, see {@link JimixPlugin2DElement}
 */
public interface JimixClipboardProvider<T extends JimixPlugin2DElement> {
    boolean acceptClipboardContent(Clipboard clipboard);

    T createElementFromClipboard(Clipboard clipboard);
}
