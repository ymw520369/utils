/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.xml;

/**
 * 类说明：宏定义元素分析器
 * 
 * @version 1.0
 * @author zminleo <zmin@seasky.cn>
 */

public class DefineParser extends NormalParser {

	/* methods */
	/** 一般分析方法 */
	public Object normalParse(Element el, XmlContext context, Object arg) {
		return el;
	}

}