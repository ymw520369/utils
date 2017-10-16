
package org.alan.utils.xml;

/**
 * 类说明：绑定元素分析器
 * 
 * @version 1.0
 */

public final class BindParser extends NormalParser {

	/* methods */
	/** 一般分析方法 */
	public Object normalParse(Element el, XmlContext context, Object arg) {
		Element child = NormalParser.getFirstElement(el, context);
		if (child == null)
			return null;
		Parser p = context.getParser(child.getName());
		if (p == null)
			return null;
		return p.parse(child, context, arg);
	}

}