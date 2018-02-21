package org.pcsoft.app.jimix.app.ui.component.cell.pane;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.scene.image.Image;

public class LayerTreeCellPaneViewModel implements ViewModel {
    private final StringProperty name = new SimpleStringProperty();
    private final BooleanProperty visibility = new SimpleBooleanProperty();
    private final ObjectProperty<Image> preview = new SimpleObjectProperty<>();

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public boolean isVisibility() {
        return visibility.get();
    }

    public BooleanProperty visibilityProperty() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility.set(visibility);
    }

    public Image getPreview() {
        return preview.get();
    }

    public ObjectProperty<Image> previewProperty() {
        return preview;
    }

    public void setPreview(Image preview) {
        this.preview.set(preview);
    }
}
