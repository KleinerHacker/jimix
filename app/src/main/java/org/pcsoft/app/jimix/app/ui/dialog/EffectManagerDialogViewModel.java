package org.pcsoft.app.jimix.app.ui.dialog;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.Observable;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.commons.type.TransparentSnapshotParams;
import org.pcsoft.app.jimix.plugin.mani.manager.type.JimixEffectInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EffectManagerDialogViewModel implements ViewModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(EffectManagerDialogViewModel.class);

    private final ObjectProperty<Image> sourceImage = new SimpleObjectProperty<>();
    private final ObjectProperty<JimixEffectInstance> selectedEffect = new SimpleObjectProperty<>();

    private final ReadOnlyObjectWrapper<Image> resultImageWrapper = new ReadOnlyObjectWrapper<>();
    private final ReadOnlyObjectProperty<Image> resultImage = resultImageWrapper.getReadOnlyProperty();

    private final BooleanBinding allowSubmit;

    public EffectManagerDialogViewModel() {
        sourceImage.addListener(o -> refreshResultImage());
        selectedEffect.addListener((v, o, n) -> {
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

        allowSubmit = selectedEffect.isNotNull();
    }

    private void onInvalidate(final Observable o) {
        refreshResultImage();
    }

    private void refreshResultImage() {
        if (sourceImage.get() == null) {
            resultImageWrapper.set(null);
        } else if (selectedEffect.get() == null) {
            resultImageWrapper.set(sourceImage.get());
        } else {
            try {
                final Rectangle rectangle = new Rectangle(0, 0, sourceImage.get().getWidth(), sourceImage.get().getHeight());
                rectangle.setFill(new ImagePattern(sourceImage.get()));
                final Node node = selectedEffect.get().apply(rectangle, 0, 0, (int) sourceImage.get().getWidth(), (int) sourceImage.get().getHeight());
                resultImageWrapper.set(node.snapshot(new TransparentSnapshotParams(), null));
            } catch (JimixPluginExecutionException e) {
                LOGGER.error("Unable to run filter " + selectedEffect.get().getPlugin().getIdentifier(), e);
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

    public JimixEffectInstance getSelectedEffect() {
        return selectedEffect.get();
    }

    public ObjectProperty<JimixEffectInstance> selectedEffectProperty() {
        return selectedEffect;
    }

    public void setSelectedEffect(JimixEffectInstance selectedEffect) {
        this.selectedEffect.set(selectedEffect);
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
