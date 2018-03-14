package org.pcsoft.app.jimix.core.plugin.builtin.model;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.pcsoft.app.jimix.commons.type.JimixSnapshotParams;
import org.pcsoft.app.jimix.core.util.TextPluginElementUtils;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyFontRestriction;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;

public final class TextPluginElement extends JimixPlugin2DElement<TextPluginElement> {
    @JimixProperty(fieldType = String.class, name = "Font Family", description = "Name of font to use", category = "Font")
    @JimixPropertyFontRestriction
    private final StringProperty fontFamilyName = new SimpleStringProperty("Arial");
    @JimixProperty(fieldType = Double.class, name = "Font Size", description = "Size of font to use", category = "Font")
    private final DoubleProperty fontSize = new SimpleDoubleProperty(12d);
    @JimixProperty(fieldType = FontWeight.class, name = "Font Weight", description = "Weight of font to use", category = "Font")
    private final ObjectProperty<FontWeight> fontWeight = new SimpleObjectProperty<>(FontWeight.NORMAL);
    @JimixProperty(fieldType = FontPosture.class, name = "Font Posture", description = "Posture of font to use", category = "Font")
    private final ObjectProperty<FontPosture> fontPosture = new SimpleObjectProperty<>(FontPosture.REGULAR);
    @JimixProperty(fieldType = String.class, name = "Text", description = "Text of element")
    private final StringProperty text = new SimpleStringProperty("text");
    @JimixProperty(fieldType = Boolean.class, name = "Strikeout", description = "Strikeout", category = "Font")
    private final BooleanProperty strikeout = new SimpleBooleanProperty(false);
    @JimixProperty(fieldType = Boolean.class, name = "Underline", description = "Underline", category = "Font")
    private final BooleanProperty underline = new SimpleBooleanProperty(false);

    private final ObjectBinding<Image> preview;

    public TextPluginElement() {
        preview = Bindings.createObjectBinding(
                () -> {
                    if (getFill() == null)
                        return null;

                    return TextPluginElementUtils.buildShape(0, 0, "Ab",
                            Font.font(fontFamilyName.get(), fontWeight.get(), fontPosture.get(), fontSize.get()), underline.get(), strikeout.get(),
                            getFill(), getStroke())
                            .snapshot(new JimixSnapshotParams(), null);
                }, fillProperty(), fontFamilyName, fontSize, fontWeight, fontPosture, underline, strikeout
        );
    }

    public String getFontFamilyName() {
        return fontFamilyName.get();
    }

    public StringProperty fontFamilyNameProperty() {
        return fontFamilyName;
    }

    public void setFontFamilyName(String fontFamilyName) {
        this.fontFamilyName.set(fontFamilyName);
    }

    public double getFontSize() {
        return fontSize.get();
    }

    public DoubleProperty fontSizeProperty() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize.set(fontSize);
    }

    public FontWeight getFontWeight() {
        return fontWeight.get();
    }

    public ObjectProperty<FontWeight> fontWeightProperty() {
        return fontWeight;
    }

    public void setFontWeight(FontWeight fontWeight) {
        this.fontWeight.set(fontWeight);
    }

    public FontPosture getFontPosture() {
        return fontPosture.get();
    }

    public ObjectProperty<FontPosture> fontPostureProperty() {
        return fontPosture;
    }

    public void setFontPosture(FontPosture fontPosture) {
        this.fontPosture.set(fontPosture);
    }

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public boolean isStrikeout() {
        return strikeout.get();
    }

    public BooleanProperty strikeoutProperty() {
        return strikeout;
    }

    public void setStrikeout(boolean strikeout) {
        this.strikeout.set(strikeout);
    }

    public boolean isUnderline() {
        return underline.get();
    }

    public BooleanProperty underlineProperty() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline.set(underline);
    }

    @Override
    public Image getPreview() {
        return preview.get();
    }

    @Override
    public ObjectBinding<Image> previewProperty() {
        return preview;
    }

    @Override
    protected Observable[] _getObservables() {
        return new Observable[]{
                text, fontFamilyName, fontSize, fontWeight, fontPosture, underline, strikeout
        };
    }

    @Override
    protected TextPluginElement _copy() {
        final TextPluginElement pluginElement = new TextPluginElement();
        pluginElement.setFontFamilyName(this.fontFamilyName.get());
        pluginElement.setFontSize(this.fontSize.get());
        pluginElement.setFontWeight(this.fontWeight.get());
        pluginElement.setFontPosture(this.fontPosture.get());
        pluginElement.setText(this.text.get());
        pluginElement.setUnderline(this.underline.get());
        pluginElement.setStrikeout(this.strikeout.get());

        return pluginElement;
    }
}
