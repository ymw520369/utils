/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.xml;

/**
 * 类说明：分析器接口
 * 
 * @version 1.0
 * @author zminleo <zmin@seasky.cn>
 */

public interface Parser {

	/** 分析方法 */
	public Object parse(Element el, XmlContext context, Object arg);

}