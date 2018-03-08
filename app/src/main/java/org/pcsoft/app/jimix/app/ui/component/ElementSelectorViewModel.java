package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import org.pcsoft.app.jimix.plugin.manipulation.manager.ManipulationPluginManager;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.Jimix2DElementBuilderPlugin;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixElementBuilderPlugin;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ElementSelectorViewModel implements ViewModel {
    private final ReadOnlyMapProperty<String, List<JimixElementBuilderPlugin>> builderMap =
            new ReadOnlyMapWrapper<String, List<JimixElementBuilderPlugin>>(FXCollections.observableHashMap()).getReadOnlyProperty();

    private final ObjectProperty<JimixElementBuilderPlugin> selectedElementBuilder = new SimpleObjectProperty<>();

    public ElementSelectorViewModel() {
        builderMap.clear();
        builderMap.putAll(
                Stream.of(ManipulationPluginManager.getInstance().getAllElementBuilders())
                        .filter(JimixElementBuilderPlugin::manualAddable)
                        .collect(Collectors.groupingBy(JimixElementBuilderPlugin::getGroup))
        );
    }

    public ObservableMap<String, List<JimixElementBuilderPlugin>> getBuilderMap() {
        return builderMap.get();
    }

    public ReadOnlyMapProperty<String, List<JimixElementBuilderPlugin>> builderMapProperty() {
        return builderMap;
    }

    public JimixElementBuilderPlugin getSelectedElementBuilder() {
        return selectedElementBuilder.get();
    }

    public ObjectProperty<JimixElementBuilderPlugin> selectedElementBuilderProperty() {
        return selectedElementBuilder;
    }

    public void setSelectedElementBuilder(JimixElementBuilderPlugin selectedElementBuilder) {
        this.selectedElementBuilder.set(selectedElementBuilder);
    }
}
