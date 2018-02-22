package org.pcsoft.app.jimix.app.ui.component.cell.pane;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.HBox;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.core.project.JimixElement;
import org.pcsoft.app.jimix.core.plugin.builtin.model.JimixImageElementModel;

public class ElementTreeCellPane extends HBox {
    private final ElementTreeCellPaneView controller;
    private final ElementTreeCellPaneViewModel viewModel;

    private final ObjectProperty<JimixElement> value = new SimpleObjectProperty<>();

    public ElementTreeCellPane() {
        final ViewTuple<ElementTreeCellPaneView, ElementTreeCellPaneViewModel> viewTuple =
                FluentViewLoader.fxmlView(ElementTreeCellPaneView.class).resourceBundle(LanguageResources.getBundle()).root(this).load();
        controller = viewTuple.getCodeBehind();
        viewModel = viewTuple.getViewModel();

        value.addListener((v, o, n) -> {
            if (o != null) {
                viewModel.visibilityProperty().unbindBidirectional(o.getModel().visibilityProperty());
            }

            if (n != null) {
                viewModel.setTitle(n.getModel() instanceof JimixImageElementModel ? "Image" : "");//TODO
                viewModel.visibilityProperty().bindBidirectional(n.getModel().visibilityProperty());
                if (n.getModel() instanceof JimixImageElementModel) {
                    viewModel.previewProperty().bind(((JimixImageElementModel) n.getModel()).valueProperty());
                }
            }
        });
    }

    public ElementTreeCellPane(final JimixElement value) {
        this();
        setValue(value);
    }

    public JimixElement getValue() {
        return value.get();
    }

    public ObjectProperty<JimixElement> valueProperty() {
        return value;
    }

    public void setValue(JimixElement value) {
        this.value.set(value);
    }
}
