package org.pcsoft.app.jimix.core.tooling;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class UndoRedoManagerTest {

    @Test
    public void test() {
        final Object[] objects = {"abc", 123, 1.23d};
        final AtomicInteger index = new AtomicInteger(3);
        final UndoRedoManager undoRedoManager = new UndoRedoManager(new UndoRedoManager.UndoRedoListener() {
            @Override
            public void onUndo(Object changeable) {
                Assert.assertEquals(objects[index.get()], changeable);
            }

            @Override
            public void onRedo(Object changeable) {
                Assert.assertEquals(objects[index.get()], changeable);
            }
        });

        Assert.assertFalse(undoRedoManager.canUndo());
        Assert.assertFalse(undoRedoManager.canRedo());

        undoRedoManager.addChangeable(objects[0]);
        undoRedoManager.addChangeable(objects[1]);
        undoRedoManager.addChangeable(objects[2]);

        Assert.assertTrue(undoRedoManager.canUndo());
        Assert.assertFalse(undoRedoManager.canRedo());

        index.decrementAndGet();
        undoRedoManager.undo();

        Assert.assertTrue(undoRedoManager.canUndo());
        Assert.assertTrue(undoRedoManager.canRedo());

        undoRedoManager.redo();
        index.incrementAndGet();

        Assert.assertTrue(undoRedoManager.canUndo());
        Assert.assertFalse(undoRedoManager.canRedo());

        index.decrementAndGet();
        undoRedoManager.undo();
        undoRedoManager.addChangeable(objects[0]);

        Assert.assertTrue(undoRedoManager.canUndo());
        Assert.assertFalse(undoRedoManager.canRedo());

        index.set(0);
        undoRedoManager.undo();

        Assert.assertTrue(undoRedoManager.canUndo());
        Assert.assertTrue(undoRedoManager.canRedo());

        undoRedoManager.clear();

        Assert.assertFalse(undoRedoManager.canUndo());
        Assert.assertTrue(undoRedoManager.canRedo());

        undoRedoManager.addChangeable(objects[0]);
        undoRedoManager.addChangeable(objects[1]);
        undoRedoManager.addChangeable(objects[2]);

        Assert.assertTrue(undoRedoManager.canUndo());
        Assert.assertFalse(undoRedoManager.canRedo());

        index.set(3);
        index.decrementAndGet();
        undoRedoManager.undo();
        index.decrementAndGet();
        undoRedoManager.undo();
        index.decrementAndGet();
        undoRedoManager.undo();

        Assert.assertFalse(undoRedoManager.canUndo());
        Assert.assertTrue(undoRedoManager.canRedo());

        undoRedoManager.redo();
        index.incrementAndGet();
        undoRedoManager.redo();
        index.incrementAndGet();
        undoRedoManager.redo();
        index.incrementAndGet();

        Assert.assertTrue(undoRedoManager.canUndo());
        Assert.assertFalse(undoRedoManager.canRedo());
    }
}