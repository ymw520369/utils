
package org.alan.utils.xml;

/**
 * 类说明：元素分析器，返回自己第一个子元素。
 * 
 * @version 1.0
 */

public class ElementParser extends NormalParser {

	/* methods */
	/** 分析方法 */
	public Object normalParse(Element el, XmlContext context, Object arg) {
		return getFirstElement(el, context);
	}

}