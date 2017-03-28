/**
 * 
 */
package undo.impl;

import java.util.LinkedList;

import undo.Change;
import undo.Document;
import undo.UndoManager;

/**
 * The implementation for {@link UndoManager} that provide undo and redo operations to {@link Document}s, based on
 * {@link Change} objects.
 * 
 * @author <a href="mailto:mikhail.m.mina@gmail.com">Mina Mansour</a>
 * 
 * @version 1.0
 *
 * @since Mar 24, 2017
 * 
 */
public class UndoManagerImpl implements UndoManager {

	private Document document;

	private LinkedList<Change> changeList;

	private int bufferSize;

	private int undoLevel;

	private boolean canRedo = false;

	public UndoManagerImpl(Document doc, int bufferSize) {
		this.document = doc;
		this.bufferSize = bufferSize;
		this.undoLevel = bufferSize;
		this.changeList = new LinkedList<Change>();
	}

	/**
	 * Override the default constructor and call the constructor with the parameter to avoid creating instance from the
	 * default one.
	 */
	public UndoManagerImpl() {
		this(null, 0);
	}

	/*
	 * @see undo.UndoManager#registerChange(undo.Change)
	 */
	@Override
	public void registerChange(Change change) {
		// Adding only changes when the size is not exhausted. Else removing the change at the end of the list and
		// adding a new on the top of the list.
		if (getChangeList().size() < getBufferSize()) {
			getChangeList().addFirst(change);
		} else {
			getChangeList().removeLast();
			getChangeList().addFirst(change);
		}

		// If there is a new change registered then the change level is reset to the newest value on the list, so that
		// an undo action will result in the latest changes being undone
		setUndoLevel(0);
		canRedo = false;

	}

	/*
	 * @see undo.UndoManager#canUndo()
	 */
	@Override
	public boolean canUndo() {
		// If no change is registered then Undo cannot be performed
		return (getChangeList().size() > 0) && !(getUndoLevel() == getChangeList().size());
	}

	/*
	 * @see undo.UndoManager#undo()
	 */
	@Override
	public void undo() {
		if (!canUndo()) {
			throw new IllegalStateException("Nothing to Undo");
		}

		// Get the change from the list based on the undo level and revert this change from the document.
		Change undoChange = getChangeList().get(getUndoLevel());
		undoChange.revert(getDocument());

		// If undo is called before a new change is registered then this will lead to the next change in the list to be
		// undone. Increment the undo level
		setUndoLevel(getUndoLevel() + 1);

		// Update the canRedo with true after successfully undo.
		canRedo = true;

	}

	/*
	 * @see undo.UndoManager#canRedo()
	 */
	@Override
	public boolean canRedo() {
		return canRedo && (getUndoLevel() > 0);
	}

	/*
	 * @see undo.UndoManager#redo()
	 */
	@Override
	public void redo() {
		if (!canRedo()) {
			throw new IllegalStateException("Nothing to Redo");
		}

		setUndoLevel(getUndoLevel() - 1);

		// Get the change from the list based on the undo level and apply the change to the document.
		Change redoChange = getChangeList().get(getUndoLevel());
		redoChange.apply(getDocument());
	}

	protected LinkedList<Change> getChangeList() {
		return this.changeList;
	}

	protected Document getDocument() {
		return this.document;
	}

	protected int getBufferSize() {
		return this.bufferSize;
	}

	protected int getUndoLevel() {
		return undoLevel;
	}

	protected void setUndoLevel(int undoLevel) {
		this.undoLevel = undoLevel;
	}

}
