package org.pcsoft.app.jimix.app.ui.dialog;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.Observable;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.commons.type.TransparentSnapshotParams;
import org.pcsoft.app.jimix.plugin.mani.manager.type.JimixEffectInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EffectManagerDialogViewModel implements ViewModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(EffectManagerDialogViewModel.class);

    private final ObjectProperty<Image> sourceImage = new SimpleObjectProperty<>();
    private final ObjectProperty<JimixEffectInstance> selectedEffect = new SimpleObjectProperty<>();
    private final DoubleProperty zoom = new SimpleDoubleProperty(2d);

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
        zoom.addListener(o -> refreshResultImage());

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
                //Calculate new width / height with zoom
                final int width = (int) (sourceImage.get().getWidth() * zoom.get());
                final int height = (int) (sourceImage.get().getHeight() * zoom.get());
                final Canvas canvas = new Canvas(width, height);
                final GraphicsContext gc = canvas.getGraphicsContext2D();
                //Calculate middle position for image
                final int x = (int) (width / 2 - sourceImage.get().getWidth() / 2);
                final int y = (int) (height / 2 - sourceImage.get().getHeight() / 2);
                gc.drawImage(sourceImage.get(), x, y);
                selectedEffect.get().apply(sourceImage.get(), x, y, gc);
                resultImageWrapper.set(canvas.snapshot(new TransparentSnapshotParams(), null));
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

    public double getZoom() {
        return zoom.get();
    }

    public DoubleProperty zoomProperty() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom.set(zoom);
    }
}
