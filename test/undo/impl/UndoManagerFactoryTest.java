/**
 * 
 */
package undo.impl;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import undo.Change;
import undo.Document;
import undo.UndoManager;
import undo.UndoManagerFactory;

/**
 * <p>
 * Title: "undo_manager" - UndoManagerFactoryTest.java
 * </p>

 * <p>
 * Description: 
 * </p>

 * @author <a href="mailto:mikhail.m.mina@gmail.com">Mina Mansour</a>
 * @version 1.0
 *
 * @since Mar 26, 2017
 */
/**
 * @author mansoum
 *
 */
public class UndoManagerFactoryTest {

	private UndoManagerFactory undoManagerFactory;
	private UndoManager undoManager;
	private Change change;
	private Document document;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		undoManagerFactory = new UndoManagerFactoryImpl();
		document = EasyMock.createMock(Document.class);
		change = EasyMock.createMock(Change.class);
		undoManager = undoManagerFactory.createUndoManager(document, 1);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateUndoManagerNullDocument() throws Exception {
		undoManagerFactory.createUndoManager(null, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateUndoManagerZeroBuffer() throws Exception {
		undoManagerFactory.createUndoManager(document, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateUndoManagerLessThanZeroBuffer() throws Exception {
		undoManagerFactory.createUndoManager(document, -1);
	}

	@Test(expected = IllegalStateException.class)
	public void testBufferSize() throws Exception {
		undoManagerFactory.createUndoManager(document, 1);
		undoManager.registerChange(change);
		undoManager.undo();
		undoManager.undo();
	}

}
