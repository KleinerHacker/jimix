package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.VBox;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.Jimix2DElementBuilderPlugin;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixElementBuilderPlugin;

public class ElementSelector extends VBox {

    private final ElementSelectorViewModel viewModel;
    private final ElementSelectorView controller;

    public ElementSelector() {
        final ViewTuple<ElementSelectorView, ElementSelectorViewModel> viewTuple =
                FluentViewLoader.fxmlView(ElementSelectorView.class).resourceBundle(LanguageResources.getBundle()).root(this).load();
        viewModel = viewTuple.getViewModel();
        controller = viewTuple.getCodeBehind();
    }

    public JimixElementBuilderPlugin getSelectedElementBuilder() {
        return viewModel.getSelectedElementBuilder();
    }

    public ObjectProperty<JimixElementBuilderPlugin> selectedElementBuilderProperty() {
        return viewModel.selectedElementBuilderProperty();
    }

    public void setSelectedElementBuilder(JimixElementBuilderPlugin selectedElementBuilder) {
        viewModel.setSelectedElementBuilder(selectedElementBuilder);
    }
}
