package org.pcsoft.app.jimix.app.ui.component.prop_sheet.editor.cell;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.pcsoft.framework.jfex.property.ExtendedWrapperProperty;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DimensionEditorPaneViewModel implements ViewModel {
    private final ObjectProperty<Dimension> dimension;
    private final ObjectProperty<Integer> width = new SimpleObjectProperty<>();
    private final ObjectProperty<Integer> height =new SimpleObjectProperty<>();
    private final BooleanProperty proportional = new SimpleBooleanProperty();

    private final AtomicBoolean ignoreDimensionUpdate = new AtomicBoolean(false);

    public DimensionEditorPaneViewModel() {
        dimension = new ExtendedWrapperProperty<Dimension>(width, height) {
            @Override
            protected Dimension getPseudoValue() {
                return new Dimension(width.get(), height.get());
            }

            @Override
            protected void setPseudoValue(Dimension dimension) {
                ignoreDimensionUpdate.set(true);
                try {
                    if (dimension == null) {
                        width.set(0);
                        height.set(0);
                    }else {
                        width.set(dimension.width);
                        height.set(dimension.height);
                    }
                } finally {
                    ignoreDimensionUpdate.set(false);
                }
            }
        };

        width.addListener((v, o, n) -> {
            if (!proportional.get())
                return;
            if (ignoreDimensionUpdate.get())
                return;
            if (o == 0)
                return;

            ignoreDimensionUpdate.set(true);
            try {
                height.set(n * height.get() / o);
            } finally {
                ignoreDimensionUpdate.set(false);
            }
        });
        height.addListener((v, o, n) -> {
            if (!proportional.get())
                return;
            if (ignoreDimensionUpdate.get())
                return;
            if (o == null)
                return;

            ignoreDimensionUpdate.set(true);
            try {
                width.set(n * width.get() / o);
            } finally {
                ignoreDimensionUpdate.set(false);
            }
        });
    }

    public boolean isProportional() {
        return proportional.get();
    }

    public BooleanProperty proportionalProperty() {
        return proportional;
    }

    public void setProportional(boolean proportional) {
        this.proportional.set(proportional);
    }

    public Dimension getDimension() {
        return dimension.get();
    }

    public ObjectProperty<Dimension> dimensionProperty() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension.set(dimension);
    }

    public Integer getWidth() {
        return width.get();
    }

    public ObjectProperty<Integer> widthProperty() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width.set(width);
    }

    public Integer getHeight() {
        return height.get();
    }

    public ObjectProperty<Integer> heightProperty() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height.set(height);
    }
}
