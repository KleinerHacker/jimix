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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ElementSelectorViewModel implements ViewModel {
    private final ReadOnlyMapProperty<String, List<Jimix2DElementBuilderPlugin>> builderMap =
            new ReadOnlyMapWrapper<String, List<Jimix2DElementBuilderPlugin>>(FXCollections.observableHashMap()).getReadOnlyProperty();

    private final ObjectProperty<Jimix2DElementBuilderPlugin> selectedElementBuilder = new SimpleObjectProperty<>();

    public ElementSelectorViewModel() {
        builderMap.clear();
        builderMap.putAll(
                Stream.of(ManipulationPluginManager.getInstance().getAll2DElementBuilders())
                        .filter(Jimix2DElementBuilderPlugin::manualAddable)
                        .collect(Collectors.groupingBy(Jimix2DElementBuilderPlugin::getGroup))
        );
    }

    public ObservableMap<String, List<Jimix2DElementBuilderPlugin>> getBuilderMap() {
        return builderMap.get();
    }

    public ReadOnlyMapProperty<String, List<Jimix2DElementBuilderPlugin>> builderMapProperty() {
        return builderMap;
    }

    public Jimix2DElementBuilderPlugin getSelectedElementBuilder() {
        return selectedElementBuilder.get();
    }

    public ObjectProperty<Jimix2DElementBuilderPlugin> selectedElementBuilderProperty() {
        return selectedElementBuilder;
    }

    public void setSelectedElementBuilder(Jimix2DElementBuilderPlugin selectedElementBuilder) {
        this.selectedElementBuilder.set(selectedElementBuilder);
    }
}
