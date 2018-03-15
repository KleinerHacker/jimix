package org.pcsoft.app.jimix.plugin.common.api.type;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import org.apache.commons.lang.ArrayUtils;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;

public abstract class JimixPlugin3DElement<T extends JimixPlugin3DElement<T>> implements JimixPluginElement<T> {
    @JimixProperty(fieldType = DrawMode.class, name = "Draw Mode", description = "Draw mode of object", category = "Style")
    private final ObjectProperty<DrawMode> drawMode = new SimpleObjectProperty<>(DrawMode.FILL);
    @JimixProperty(fieldType = CullFace.class, name = "Cull Face", description = "Cull face of object", category = "Style")
    private final ObjectProperty<CullFace> cullFace = new SimpleObjectProperty<>(CullFace.BACK);
    @JimixProperty(fieldType = Color.class, name = "Diffuse Color", description = "Diffuse Color", category = "Style")
    private final ObjectProperty<Color> diffuseColor = new SimpleObjectProperty<>(Color.WHITE);
    @JimixProperty(fieldType = Image.class, name = "Diffuse Image", description = "Diffuse Image", category = "Style")
    private final ObjectProperty<Image> diffuseImage = new SimpleObjectProperty<>(null);

    public DrawMode getDrawMode() {
        return drawMode.get();
    }

    public ObjectProperty<DrawMode> drawModeProperty() {
        return drawMode;
    }

    public void setDrawMode(DrawMode drawMode) {
        this.drawMode.set(drawMode);
    }

    public CullFace getCullFace() {
        return cullFace.get();
    }

    public ObjectProperty<CullFace> cullFaceProperty() {
        return cullFace;
    }

    public void setCullFace(CullFace cullFace) {
        this.cullFace.set(cullFace);
    }

    public Color getDiffuseColor() {
        return diffuseColor.get();
    }

    public ObjectProperty<Color> diffuseColorProperty() {
        return diffuseColor;
    }

    public void setDiffuseColor(Color diffuseColor) {
        this.diffuseColor.set(diffuseColor);
    }

    public Image getDiffuseImage() {
        return diffuseImage.get();
    }

    public ObjectProperty<Image> diffuseImageProperty() {
        return diffuseImage;
    }

    public void setDiffuseImage(Image diffuseImage) {
        this.diffuseImage.set(diffuseImage);
    }

    @Override
    public final Observable[] getObservables() {
        return (Observable[]) ArrayUtils.addAll(
                new Observable[] {
                        drawMode, cullFace, diffuseColor, diffuseImage
                },
                _getObservables()
        );
    }

    protected abstract Observable[] _getObservables();

    @Override
    public final T copy() {
        final T copy = _copy();
        copy.setDrawMode(this.drawMode.get());
        copy.setCullFace(this.cullFace.get());
        copy.setDiffuseColor(this.diffuseColor.get());
        copy.setDiffuseImage(this.diffuseImage.get());

        return copy;
    }

    protected abstract T _copy();

    @Override
    public final JimixElementType getType() {
        return JimixElementType.Element3D;
    }
}
