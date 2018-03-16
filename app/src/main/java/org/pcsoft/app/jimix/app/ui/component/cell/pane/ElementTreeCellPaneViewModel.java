package org.pcsoft.app.jimix.app.ui.component.cell.pane;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.scene.image.Image;

public class ElementTreeCellPaneViewModel implements ViewModel {
    private final StringProperty title = new SimpleStringProperty(), group = new SimpleStringProperty();
    private final BooleanProperty visibility = new SimpleBooleanProperty();
    private final ObjectProperty<Image> preview = new SimpleObjectProperty<>();

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getGroup() {
        return group.get();
    }

    public StringProperty groupProperty() {
        return group;
    }

    public void setGroup(String group) {
        this.group.set(group);
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
