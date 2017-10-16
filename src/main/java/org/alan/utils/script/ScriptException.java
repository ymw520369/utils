/**
 * Coyyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.script;

/**
 * 类说明：脚本异常
 * 
 * @version 1.0
 */

public class ScriptException extends RuntimeException {

	/* static fields */
	/** serialVersionUID的注释 */
	private static final long serialVersionUID = 3103044521625325571L;

	/* constructors */
	/** 构造一个新的ScriptException对象 */
	public ScriptException() {
		super();
	}

	/** 用给定的错误信息构造一个的ScriptException对象 */
	public ScriptException(String message) {
		super(message);
	}

	/** 用给定的异常或错误对象，构造一个的ScriptException对象 */
	public ScriptException(Throwable cause) {
		super(cause);
	}

	/** 用给定的错误信息和异常或错误对象，构造一个的ScriptException对象 */
	public ScriptException(String message, Throwable cause) {
		super(message, cause);
	}

}