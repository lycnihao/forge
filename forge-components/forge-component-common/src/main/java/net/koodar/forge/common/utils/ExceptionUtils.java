package net.koodar.forge.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exception utilities.
 * <p>Part from apache commons lang3 project.</p>
 *
 * @author liyc
 * @see "org.apache.commons.lang3.exception.ExceptionUtils"
 */
public class ExceptionUtils {

	/**
	 * <p>Gets the stack trace from a Throwable as a String.</p>
	 *
	 * <p>The result of this method vary by JDK version as this method
	 * uses {@link Throwable#printStackTrace(PrintWriter)}.
	 * On JDK1.3 and earlier, the cause exception will not be shown
	 * unless the specified throwable alters printStackTrace.</p>
	 *
	 * @param throwable the <code>Throwable</code> to be examined
	 * @return the stack trace as generated by the exception's
	 * <code>printStackTrace(PrintWriter)</code> method
	 */
	public static String getStackTrace(final Throwable throwable) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}

}
