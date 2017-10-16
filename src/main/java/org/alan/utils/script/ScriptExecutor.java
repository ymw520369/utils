/**
 * Coyyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.script;

/**
 * 类说明：脚本执行接口
 * 
 * @version 1.0
 */

public interface ScriptExecutor {

	/** 获得变量 */
	public Object get(String name);

	/** 设置变量 */
	public void set(String name, Object value);

	/** 移除变量 */
	public Object remove(String name);

	/** 执行脚本，code为脚本代码，src为源地址，lineNumber为行号 */
	public void execute(String code, String src, int lineNumber);

	/** 退出脚本环境 */
	public void exit();

}