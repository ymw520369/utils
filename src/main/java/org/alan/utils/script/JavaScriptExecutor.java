/**
 * Coyyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * 类说明：JavaScript脚本执行器
 * 
 * @version 1.0
 */

public class JavaScriptExecutor implements ScriptExecutor {

	/* fields */
	/** JavaScript的脚本环境 */
	Scriptable scope = new ImporterTopLevel(Context.enter());

	/* properties */
	/** 获得变量方法 */
	public Object get(String name) {
		if (scope == null)
			return null;
		try {
			return scope.get(name, scope);
		} catch (Throwable t) {
			throw new ScriptException(t);
		}
	}

	/** 设置变量方法 */
	public void set(String name, Object value) {
		if (scope == null)
			return;
		try {
			ScriptableObject.putProperty(scope, name,
					Context.javaToJS(value, scope));
		} catch (Throwable t) {
			throw new ScriptException(t);
		}
	}

	/** 移除变量方法 */
	public Object remove(String name) {
		if (scope == null)
			return null;
		try {
			Object value = scope.get(name, scope);
			if (value != null)
				scope.delete(name);
			return value;
		} catch (Throwable t) {
			throw new ScriptException(t);
		}
	}

	/* methods */
	/** 执行脚本，code为脚本代码，src为源地址，lineNumber为行号 */
	public void execute(String code, String src, int lineNumber) {
		if (scope == null)
			return;
		try {
			Context.enter().evaluateString(scope, code, src, lineNumber, null);
		} catch (Throwable t) {
			throw new ScriptException(t);
		} finally {
			Context.exit();
		}
	}

	public void exit() {
		scope = null;
	}

}