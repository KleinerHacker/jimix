package org.pcsoft.app.jimix.app.ui.component.cell.pane;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.HBox;
import org.apache.commons.lang.StringUtils;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.core.project.JimixElement;
import org.pcsoft.app.jimix.plugin.manipulation.manager.ManipulationPluginManager;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixElementBuilderPlugin;

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
                viewModel.visibilityProperty().unbindBidirectional(o.visibleProperty());
            }

            if (n != null) {
                final JimixElementBuilderPlugin elementBuilder = ManipulationPluginManager.getInstance().getElementBuilder(n.getModel().getPluginElement().getClass());
                viewModel.setTitle(elementBuilder == null ? "<unknown>" : elementBuilder.getName());
                viewModel.setGroup(elementBuilder == null ? "" : StringUtils.isEmpty(elementBuilder.getGroup()) ? LanguageResources.getText("common.plugin.group.default") : elementBuilder.getGroup());
                viewModel.visibilityProperty().bindBidirectional(n.visibleProperty());
                viewModel.previewProperty().bind(n.getModel().getPluginElement().previewProperty());
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
