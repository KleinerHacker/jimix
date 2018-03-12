package org.pcsoft.app.jimix.core.tooling;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Manages a Queue of UndoRedoManager to perform undo and/or redo operations. Clients can add implementations of the Changeable
 * <p>
 * class to this class, and it will manage undo/redo as a Queue.
 *
 * @author Greg Cope
 */

public class UndoRedoManager {

    private final UndoRedoListener listener;
    //the current index node
    private final ObjectProperty<Node> currentIndex = new SimpleObjectProperty<>(null);
    //the parent node far left node.
    private final ObjectProperty<Node> parentNode = new SimpleObjectProperty<>(new Node());

    private final BooleanBinding canUndo, canRedo;


    /**
     * Creates a new UndoRedoManager object which is initially empty.
     */
    public UndoRedoManager(final UndoRedoListener listener) {
        this.currentIndex.set(parentNode.get());
        this.listener = listener;

        canUndo = currentIndex.isNotEqualTo(parentNode);
        canRedo = Bindings.createBooleanBinding(() -> currentIndex.get().right != null, currentIndex);
    }

    /**
     * Creates a new UndoRedoManager which is a duplicate of the parameter in both contents and current index.
     *
     * @param manager
     */
    public UndoRedoManager(UndoRedoManager manager) {
        this(manager.listener);
        this.currentIndex.set(manager.currentIndex.get());
    }

    /**
     * Clears all Changables contained in this manager.
     */
    public void clear() {
        currentIndex.set(parentNode.get());
    }

    /**
     * Adds a Changeable to manage.
     *
     * @param changeable
     */
    public void addChangeable(Object changeable) {
        final Node node = new Node(changeable);
        currentIndex.get().right = node;
        node.left = currentIndex.get();
        currentIndex.set(node);
    }

    /**
     * Determines if an undo can be performed.
     *
     * @return
     */
    public boolean canUndo() {
        return canUndo.get();
    }

    public BooleanBinding canUndoProperty() {
        return canUndo;
    }

    /**
     * Determines if a redo can be performed.
     *
     * @return
     */
    public boolean canRedo() {
        return canRedo.get();
    }

    public BooleanBinding canRedoProperty() {
        return canRedo;
    }

    /**
     * Undoes the Changeable at the current index.
     *
     * @throws IllegalStateException if canUndo returns false.
     */
    public void undo() {
        //validate
        if (!canUndo()) {
            throw new IllegalStateException("Cannot undo. Index is out of range.");
        }

        //undo
        listener.onUndo(currentIndex.get().changeable);
        //set index
        moveLeft();
    }

    /**
     * Moves the internal pointer of the backed linked list to the left.
     *
     * @throws IllegalStateException If the left index is null.
     */
    private void moveLeft() {
        if (currentIndex.get().left == null) {
            throw new IllegalStateException("Internal index set to null.");
        }

        currentIndex.set(currentIndex.get().left);
    }


    /**
     * Moves the internal pointer of the backed linked list to the right.
     *
     * @throws IllegalStateException If the right index is null.
     */

    private void moveRight() {
        if (currentIndex.get().right == null) {
            throw new IllegalStateException("Internal index set to null.");
        }

        currentIndex.set(currentIndex.get().right);
    }


    /**
     * Redoes the Changable at the current index.
     *
     * @throws IllegalStateException if canRedo returns false.
     */

    public void redo() {
        //validate
        if (!canRedo()) {
            throw new IllegalStateException("Cannot redo. Index is out of range.");
        }

        //reset index
        moveRight();
        //redo
        listener.onRedo(currentIndex.get().changeable);
    }


    /**
     * Inner class to implement a doubly linked list for our queue of Changeables.
     *
     * @author Greg Cope
     */
    private class Node {
        private Node left = null;
        private Node right = null;

        private final Object changeable;


        public Node(Object c) {
            changeable = c;
        }

        public Node() {
            changeable = null;
        }

    }

    public interface UndoRedoListener {
        void onUndo(Object changeable);

        void onRedo(Object changeable);
    }
}
