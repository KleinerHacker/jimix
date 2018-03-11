package org.pcsoft.app.jimix.app.ui.dialog;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixBlenderPlugin;

public class LayerCreateSimpleDialogViewModel implements ViewModel {
    private final ObjectProperty<Paint> paint = new SimpleObjectProperty<>(Color.WHITE);
    private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty<JimixBlenderPlugin> blender = new SimpleObjectProperty<>();
    private final BooleanBinding allowOK;

    public LayerCreateSimpleDialogViewModel() {
        allowOK = paint.isNotNull().and(name.isNotNull().and(name.isNotEmpty())).and(blender.isNotNull());
    }

    public Paint getPaint() {
        return paint.get();
    }

    public ObjectProperty<Paint> paintProperty() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint.set(paint);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public JimixBlenderPlugin getBlender() {
        return blender.get();
    }

    public ObjectProperty<JimixBlenderPlugin> blenderProperty() {
        return blender;
    }

    public void setBlender(JimixBlenderPlugin blender) {
        this.blender.set(blender);
    }

    public Boolean getAllowOK() {
        return allowOK.get();
    }

    public BooleanBinding allowOKProperty() {
        return allowOK;
    }
}
