package org.pcsoft.app.jimix.app.ui.dialog;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.Observable;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixSource;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixFilterInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterManagerDialogViewModel implements ViewModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterManagerDialogViewModel.class);

    private final ObjectProperty<Image> sourceImage = new SimpleObjectProperty<>();
    private final ObjectProperty<JimixFilterInstance> selectedFilter = new SimpleObjectProperty<>();

    private final ReadOnlyObjectWrapper<Image> resultImageWrapper = new ReadOnlyObjectWrapper<>();
    private final ReadOnlyObjectProperty<Image> resultImage = resultImageWrapper.getReadOnlyProperty();

    private final BooleanBinding allowSubmit;

    public FilterManagerDialogViewModel() {
        sourceImage.addListener(o -> refreshResultImage());
        selectedFilter.addListener((v, o, n) -> {
            if (o != null) {
                for (final Observable observable : o.getConfiguration().getObservables()) {
                    observable.removeListener(this::onInvalidate);
                }
            }
            if (n != null) {
                for (final Observable observable : n.getConfiguration().getObservables()) {
                    observable.addListener(this::onInvalidate);
                }
            }

            refreshResultImage();
        });

        allowSubmit = selectedFilter.isNotNull();
    }

    private void onInvalidate(final Observable o) {
        refreshResultImage();
    }

    private void refreshResultImage() {
        if (sourceImage.get() == null) {
            resultImageWrapper.set(null);
        } else if (selectedFilter.get() == null) {
            resultImageWrapper.set(sourceImage.get());
        } else {
            try {
                resultImageWrapper.set(selectedFilter.get().apply(sourceImage.get(), JimixSource.Picture));
            } catch (JimixPluginExecutionException e) {
                LOGGER.error("Unable to run filter " + selectedFilter.get().getPlugin().getIdentifier(), e);
            }
        }
    }

    public Image getSourceImage() {
        return sourceImage.get();
    }

    public ObjectProperty<Image> sourceImageProperty() {
        return sourceImage;
    }

    public void setSourceImage(Image sourceImage) {
        this.sourceImage.set(sourceImage);
    }

    public JimixFilterInstance getSelectedFilter() {
        return selectedFilter.get();
    }

    public ObjectProperty<JimixFilterInstance> selectedFilterProperty() {
        return selectedFilter;
    }

    public void setSelectedFilter(JimixFilterInstance selectedFilter) {
        this.selectedFilter.set(selectedFilter);
    }

    public Image getResultImage() {
        return resultImage.get();
    }

    public ReadOnlyObjectProperty<Image> resultImageProperty() {
        return resultImage;
    }

    public Boolean getAllowSubmit() {
        return allowSubmit.get();
    }

    public BooleanBinding allowSubmitProperty() {
        return allowSubmit;
    }
}
