/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.xml;


import org.alan.utils.script.ScriptExecutor;

/**
 * 类说明：xml应用环境
 * 
 * @version 1.0
 */

public interface XmlContext extends Context {

	/* static fields */
	/** 加载方式的类型 */
	public static final int LAZY = 1, FACTORY = 2;

	/* methods */
	/** 获得父环境 */
	public XmlContext getXmlParent();

	/** 设置加载方式所加载的对象描述 */
	public Object set(String name, Element el, int load);

	/** 获得类加载器 */
	public ClassLoader getClassLoader();

	/** 设置类加载器 */
	public void setClassLoader(ClassLoader loader);

	/** 获得指定元素的分析器 */
	public Parser getParser(String name);

	/** 设置指定元素的分析器 */
	public void setParser(String name, Parser parser);

	/** 检查是否存在指定元素的分析器 */
	public Parser checkParser(String name);

	/** 获得指定语言的脚本执行器 */
	public ScriptExecutor getExecutor(String language);

	/** 判断是否忽略大小写 */
	public boolean isIgnoreCase();

	/** 设置是否忽略大小写 */
	public void setIgnoreCase(boolean b);

	/** 判断是否警告同名ID */
	public boolean isWarnSameId();

	/** 设置是否警告同名ID */
	public void setWarnSameId(boolean b);

	/** 分析指定uri的xml文档 */
	public void parse(String uri);

	/** 分析xml文档 */
	public void parse(Document doc);

	/** 分析xml元素 */
	public void parse(Element e);

}