/**
 * 
 */
package undo.impl.util;

/**
 *
 * @author <a href="mailto:mikhail.m.mina@gmail.com">Mina Mansour</a>
 * 
 * @version 1.0
 *
 * @since Mar 24, 2017
 */

public class UndoAssert {

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notValid(int value, String message) {
		if (value <= 0) {
			throw new IllegalArgumentException(message);
		}
	}

}
