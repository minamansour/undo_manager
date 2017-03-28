/**
 * 
 */
package undo.impl;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import undo.Change;
import undo.Document;
import undo.UndoManager;
import undo.UndoManagerFactory;
import undo.impl.UndoManagerFactoryImpl;

/**
 * <p>
 * Title: "undo_manager" - UndoManagerTest.java
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author <a href="mailto:mikhail.m.mina@gmail.com">Mina Mansour</a>
 * @version 1.0
 *
 * @since Mar 26, 2017
 */

public class UndoManagerTest {

	private UndoManagerFactory undoManagerFactory;

	private Change change;

	private Document document;

	private UndoManager undoManager;

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
		undoManagerFactory = null;
		document = null;
		undoManager = null;
		change = null;
	}

	@Test
	public void testRegisterChange() throws Exception {

		undoManager.registerChange(change);

		// Assert that can undo after register a change to the document.
		Assert.assertTrue(undoManager.canUndo());
		// Assert that can't redo after register a change to the document.
		Assert.assertFalse(undoManager.canRedo());

		// Assert that can't undo after register two changes to the document and undo both.
		undoManager = undoManagerFactory.createUndoManager(document, 2);
		undoManager.registerChange(change);
		undoManager.registerChange(change);
		Assert.assertTrue(undoManager.canUndo());
		undoManager.undo();
		Assert.assertTrue(undoManager.canUndo());
		undoManager.undo();
		Assert.assertFalse(undoManager.canUndo());
	}

	@Test
	public void testCanUndo() throws Exception {
		// Assert for the first use that can't be undo.
		Assert.assertFalse(undoManager.canUndo());

		// Assert that can undo after successfully insert to the document.
		undoManager.registerChange(change);
		Assert.assertTrue(undoManager.canUndo());

		// Assert that can undo after successfully delete from the document.
		undoManager.registerChange(change);
		Assert.assertTrue(undoManager.canUndo());

		// Assert that can't undo after successfully undo to the current document.
		undoManager.registerChange(change);
		undoManager.undo();
		Assert.assertFalse(undoManager.canUndo());

		// Assert that can undo after successfully redo to the current document.
		undoManager.registerChange(change);
		undoManager.undo();
		undoManager.redo();
		Assert.assertTrue(undoManager.canUndo());
	}

	@Test
	public void testCanRedo() throws Exception {
		// Assert for the first use that can't be redo
		Assert.assertFalse(undoManager.canRedo());

		// Assert that can't redo after successfully insert to the document.
		undoManager.registerChange(change);
		Assert.assertFalse(undoManager.canRedo());

		// Assert that can't redo after successfully delete from the document.
		undoManager.registerChange(change);
		Assert.assertFalse(undoManager.canRedo());

		// Assert that can redo after successfully undo to the current document.
		undoManager.registerChange(change);
		undoManager.undo();
		Assert.assertTrue(undoManager.canRedo());
	}

	@Test
	public void testUndo() throws Exception {

		// Assert before register any change to the document.
		Assert.assertFalse(undoManager.canUndo());
		Assert.assertFalse(undoManager.canRedo());

		undoManager.registerChange(change);

		// Assert that can undo before register any change to the document.
		Assert.assertTrue(undoManager.canUndo());

		undoManager.undo();

		// Assert that can't undo after undo the changes registered to the document but can redo this change.
		Assert.assertFalse(undoManager.canUndo());
		Assert.assertTrue(undoManager.canRedo());
	}

	@Test
	public void testRedo() throws Exception {
		// Assert before register any change to the document.
		Assert.assertFalse(undoManager.canUndo());
		Assert.assertFalse(undoManager.canRedo());

		undoManager.registerChange(change);
		undoManager.undo();
		undoManager.redo();

		// Assert that can't Redo after Redo the changes registered to the document but can undo this change.
		Assert.assertTrue(undoManager.canUndo());
		Assert.assertFalse(undoManager.canRedo());
	}



}
