package org.pcsoft.app.jimix.project;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

public final class JimixProjectModel implements JimixModel {
    private final IntegerProperty width = new SimpleIntegerProperty(), height = new SimpleIntegerProperty();

    private final ReadOnlyListProperty<JimixLayerModel> layerList =
            new ReadOnlyListWrapper<>(FXCollections.observableArrayList(new JimixLayerObserverCallback())).getReadOnlyProperty();

    public JimixProjectModel(final int width, final int height) {
        this.width.set(width);
        this.height.set(height);
    }

    public ObservableList<JimixLayerModel> getLayerList() {
        return layerList.get();
    }

    public ReadOnlyListProperty<JimixLayerModel> layerListProperty() {
        return layerList;
    }

    public int getWidth() {
        return width.get();
    }

    public IntegerProperty widthProperty() {
        return width;
    }

    public void setWidth(int width) {
        this.width.set(width);
    }

    public int getHeight() {
        return height.get();
    }

    public IntegerProperty heightProperty() {
        return height;
    }

    public void setHeight(int height) {
        this.height.set(height);
    }

    @Override
    public Observable[] getObservableValues() {
        return new Observable[] {
                layerList, width, height
        };
    }

    private static final class JimixLayerObserverCallback implements Callback<JimixLayerModel, Observable[]> {
        @Override
        public Observable[] call(JimixLayerModel param) {
            return new Observable[]{
                    param.nameProperty(), param.backgroundProperty(), param.opacityProperty(),
                    param.blenderProperty(), param.maskProperty(), param.elementListProperty(),
                    param.filterListProperty()
            };
        }
    }
}
