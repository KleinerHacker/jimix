package org.pcsoft.app.jimix.plugins.api.type;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.image.Image;

import java.awt.*;

public interface JimixPluginElement {
    Observable[] getObservables();

    Dimension getPreferedSize();
    String getName();

    Image getPreview();
    ReadOnlyObjectProperty<Image> previewProperty();

}
