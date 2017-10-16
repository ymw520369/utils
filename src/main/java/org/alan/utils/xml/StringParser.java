/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.xml;

/**
 * 类说明：字符串分析器
 * 
 * @version 1.0
 * @author zminleo <zmin@seasky.cn>
 */

public final class StringParser extends NormalParser {

	/* methods */
	/** 一般分析方法 */
	public Object normalParse(Element el, XmlContext context, Object arg) {
		return el.getFirstText();
	}

}