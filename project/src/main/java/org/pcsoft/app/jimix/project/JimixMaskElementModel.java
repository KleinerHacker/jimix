package org.pcsoft.app.jimix.project;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPluginElement;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixEffectInstance;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixFilterInstance;

import java.util.stream.Collectors;

/**
 * Represent a element model. This is the abstract base for all custom elements to show in image.
 */
public final class JimixMaskElementModel extends JimixElementModel<JimixMaskElementModel> {
    private final ReadOnlyListProperty<JimixEffectInstance> effectList =
            new ReadOnlyListWrapper<JimixEffectInstance>(FXCollections.observableArrayList(param -> param.getConfiguration().getObservables())).getReadOnlyProperty();
    private final ReadOnlyListProperty<JimixFilterInstance> filterList =
            new ReadOnlyListWrapper<JimixFilterInstance>(FXCollections.observableArrayList(param -> param.getConfiguration().getObservables())).getReadOnlyProperty();

    public JimixMaskElementModel(final JimixPluginElement pluginElement) {
        super(pluginElement);
    }

    public ObservableList<JimixEffectInstance> getEffectList() {
        return effectList.get();
    }

    /**
     * List of effects to apply for this element
     *
     * @return
     */
    public ReadOnlyListProperty<JimixEffectInstance> effectListProperty() {
        return effectList;
    }

    public ObservableList<JimixFilterInstance> getFilterList() {
        return filterList.get();
    }

    public ReadOnlyListProperty<JimixFilterInstance> filterListProperty() {
        return filterList;
    }

    @Override
    protected JimixMaskElementModel _copy(final JimixPluginElement pluginElement) {
        final JimixMaskElementModel elementModel = new JimixMaskElementModel(pluginElement.copy());

        elementModel.effectList.addAll(
                this.effectList.stream()
                        .map(item -> (JimixEffectInstance<?, ?>) item.copy())
                        .collect(Collectors.toList())
        );

        elementModel.filterList.addAll(
                this.filterList.stream()
                        .map(JimixFilterInstance::copy)
                        .collect(Collectors.toList())
        );

        return elementModel;
    }

    @Override
    protected Observable[] _getObservables() {
        return new Observable[]{
                effectList, filterList
        };
    }
}
