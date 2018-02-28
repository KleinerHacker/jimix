package org.pcsoft.app.jimix.app.ui.component.prop_sheet.editor.cell;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.GridPane;
import org.pcsoft.app.jimix.app.language.LanguageResources;

import java.awt.*;

public class DimensionEditorPane extends GridPane {

    private final DimensionEditorPaneView controller;
    private final DimensionEditorPaneViewModel viewModel;

    private final ObjectProperty<Dimension> value = new SimpleObjectProperty<>();

    public DimensionEditorPane() {
        final ViewTuple<DimensionEditorPaneView, DimensionEditorPaneViewModel> viewTuple =
                FluentViewLoader.fxmlView(DimensionEditorPaneView.class).root(this).resourceBundle(LanguageResources.getBundle()).load();
        controller = viewTuple.getCodeBehind();
        viewModel = viewTuple.getViewModel();

        viewModel.dimensionProperty().bindBidirectional(value);
    }

    public Dimension getValue() {
        return value.get();
    }

    public ObjectProperty<Dimension> valueProperty() {
        return value;
    }

    public void setValue(Dimension value) {
        this.value.set(value);
    }
}
