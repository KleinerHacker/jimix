package org.pcsoft.app.jimix.plugin.common.api.type;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.image.Image;

public interface JimixPluginElement {
    Observable[] getObservables();

    Image getPreview();
    ReadOnlyObjectProperty<Image> previewProperty();

    JimixElementType getType();
}
