package org.pcsoft.app.jimix.plugins.api;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugins.api.model.JimixElementModel;

public interface JimixElementDrawer<T extends JimixElementModel> {
    Image draw(final T elementModel);
}
