/**
 * Coyyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.script;

import bsh.Interpreter;

/**
 * 类说明：BeanShell脚本执行器
 * 
 * @version 1.0
 */

public class BeanShellExecutor implements ScriptExecutor {

	/* fields */
	/** BeanShell的脚本解释器 */
	Interpreter bsh = new Interpreter();

	/* properties */
	/** 获得变量方法 */
	public Object get(String name) {
		try {
			return bsh.get(name);
		} catch (Throwable t) {
			throw new ScriptException(t);
		}
	}

	/** 设置变量方法 */
	public void set(String name, Object value) {
		try {
			bsh.set(name, value);
		} catch (Throwable t) {
			throw new ScriptException(t);
		}
	}

	/** 移除变量方法 */
	public Object remove(String name) {
		try {
			Object value = bsh.get(name);
			if (value != null)
				bsh.unset(name);
			return value;
		} catch (Throwable t) {
			throw new ScriptException(t);
		}
	}

	/* methods */
	/** 执行脚本，code为脚本代码，src为源地址，lineNumber为行号 */
	public void execute(String code, String src, int lineNumber) {
		try {
			bsh.eval(code);
		} catch (Throwable t) {
			throw new ScriptException(t);
		}
	}

	/** 退出脚本环境 */
	public void exit() {
		bsh.getNameSpace().clear();
	}

}