package org.pcsoft.app.jimix.core.tooling;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import org.pcsoft.app.jimix.project.JimixProjectModel;

public final class UndoRedoManager {
    private static final UndoRedoManager instance = new UndoRedoManager();

    public static UndoRedoManager getInstance() {
        return instance;
    }

    private final ReadOnlyListProperty<JimixProjectModel> modelHistoryList =
            new ReadOnlyListWrapper<JimixProjectModel>(FXCollections.observableArrayList()).getReadOnlyProperty();
    private final IntegerProperty index = new SimpleIntegerProperty(0);

    private final BooleanBinding canUndo, canRedo;

    private UndoRedoManager() {
        canUndo = index.lessThan(modelHistoryList.sizeProperty()).and(modelHistoryList.emptyProperty().not());
        canRedo = index.greaterThan(0).and(modelHistoryList.emptyProperty().not());
    }

    public Boolean getCanUndo() {
        return canUndo.get();
    }

    public BooleanBinding canUndoProperty() {
        return canUndo;
    }

    public Boolean getCanRedo() {
        return canRedo.get();
    }

    public BooleanBinding canRedoProperty() {
        return canRedo;
    }

    public void addHistoryModel(JimixProjectModel currentModel) {
        modelHistoryList.add(0, currentModel.copy());
        index.set(0);
    }

    public JimixProjectModel undoAndGet() {
        if (modelHistoryList.isEmpty() || index.get() >= modelHistoryList.size())
            return null;

        final JimixProjectModel projectModel = modelHistoryList.get(index.get());
        index.set(index.get() + 1);

        return projectModel;
    }

    public JimixProjectModel redoAndGet() {
        if (modelHistoryList.isEmpty() || index.get() <= 0)
            return null;

        index.set(index.get() - 1);
        final JimixProjectModel projectModel = modelHistoryList.get(index.get());

        return projectModel;
    }
}
