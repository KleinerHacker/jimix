package org.pcsoft.app.jimix.project;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPluginElement;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixEffectInstance;

import java.util.stream.Collectors;

/**
 * Represent a element model. This is the abstract base for all custom elements to show in image.
 */
public final class JimixPictureElementModel extends JimixElementModel<JimixPictureElementModel> {
    private final ReadOnlyListProperty<JimixEffectInstance> effectList =
            new ReadOnlyListWrapper<JimixEffectInstance>(FXCollections.observableArrayList(param -> param.getConfiguration().getObservables())).getReadOnlyProperty();

    public JimixPictureElementModel(final JimixPluginElement pluginElement) {
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

    @Override
    protected JimixPictureElementModel _copy(JimixPluginElement pluginElement) {
        final JimixPictureElementModel elementModel = new JimixPictureElementModel(pluginElement.copy());

        elementModel.effectList.addAll(
                this.effectList.stream()
                        .map(item -> (JimixEffectInstance<?, ?>) item.copy())
                        .collect(Collectors.toList())
        );

        return elementModel;
    }

    @Override
    protected Observable[] _getObservables() {
        return new Observable[]{
                effectList
        };
    }
}
