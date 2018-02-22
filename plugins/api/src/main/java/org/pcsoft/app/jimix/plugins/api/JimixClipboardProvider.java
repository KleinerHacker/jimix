package org.pcsoft.app.jimix.plugins.api;

import javafx.scene.input.Clipboard;
import org.pcsoft.app.jimix.plugins.api.model.JimixElementModel;

/**
 * Provider to convert clipboard content to viewable elements, see {@link org.pcsoft.app.jimix.plugins.api.model.JimixElementModel}
 */
public interface JimixClipboardProvider<T extends JimixElementModel> {
    boolean acceptClipboardContent(Clipboard clipboard);

    T createElementFromClipboard(Clipboard clipboard);
}
