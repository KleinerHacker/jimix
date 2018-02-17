package org.pcsoft.app.jimix.app.ui.component.cell.pane;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.HBox;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.core.project.JimixLayer;

public class LayerTreeCellPane extends HBox {

    private final LayerTreeCellPaneView controller;
    private final LayerTreeCellPaneViewModel viewModel;

    private final ObjectProperty<JimixLayer> value = new SimpleObjectProperty<>();

    public LayerTreeCellPane() {
        final ViewTuple<LayerTreeCellPaneView, LayerTreeCellPaneViewModel> viewTuple =
                FluentViewLoader.fxmlView(LayerTreeCellPaneView.class).resourceBundle(LanguageResources.getBundle()).root(this).load();
        controller = viewTuple.getCodeBehind();
        viewModel = viewTuple.getViewModel();

        value.addListener((v, o, n) -> {
            if (n == null)
                return;

            viewModel.setName(n.getModel().getName());
        });
    }

    public LayerTreeCellPane(final JimixLayer value) {
        this();
        setValue(value);
    }

    public JimixLayer getValue() {
        return value.get();
    }

    public ObjectProperty<JimixLayer> valueProperty() {
        return value;
    }

    public void setValue(JimixLayer value) {
        this.value.set(value);
    }
}
