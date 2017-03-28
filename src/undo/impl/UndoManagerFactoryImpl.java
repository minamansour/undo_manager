/**
 * 
 */
package undo.impl;

import undo.Document;
import undo.UndoManager;
import undo.UndoManagerFactory;
import undo.impl.util.UndoAssert;

/**
 * <p>
 * Title: UndoManagerFactoryImpl.java
 * </p>
 * 
 * <p>
 * Description: A factory implementation for {@link UndoManagerFactory}.
 * </p>
 * 
 * @author <a href="mailto:mikhail.m.mina@gmail.com">Mina Mansour</a>
 * @version 1.0
 *
 * @since Mar 26, 2017
 */

public class UndoManagerFactoryImpl implements UndoManagerFactory {

	/*
	 * @see undo.UndoManagerFactory#createUndoManager(undo.Document, int)
	 */

	@Override
	public UndoManager createUndoManager(Document doc, int bufferSize) {
		UndoAssert.notNull(doc, "Document cannot be null.");
		UndoAssert.notValid(bufferSize, "Buffer size cannot be less than zero.");
		return new UndoManagerImpl(doc, bufferSize);
	}

}
