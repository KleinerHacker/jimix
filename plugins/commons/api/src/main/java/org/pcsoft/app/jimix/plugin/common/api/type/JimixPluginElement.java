package org.pcsoft.app.jimix.plugin.common.api.type;

import javafx.beans.Observable;
import javafx.beans.binding.ObjectBinding;
import javafx.scene.image.Image;

public interface JimixPluginElement<T extends JimixPluginElement<T>> {
    Observable[] getObservables();

    Image getPreview();
    ObjectBinding<Image> previewProperty();

    JimixElementType getType();

    T copy();
}
