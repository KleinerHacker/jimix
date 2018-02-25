package org.pcsoft.app.jimix.core.tooling;

public final class UndoRedoManager {
    private static final UndoRedoManager instance = new UndoRedoManager();

    public static UndoRedoManager getInstance() {
        return instance;
    }

    private UndoRedoManager() {
    }
}
