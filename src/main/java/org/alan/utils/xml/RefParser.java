/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.xml;

/**
 * 类说明：引用分析器
 * 
 * @version 1.0
 * @author zminleo <zmin@seasky.cn>
 */

public final class RefParser implements Parser {

	/** 分析方法 */
	public Object parse(Element el, XmlContext context, Object arg) {
		String id = el.getAttribute(NormalParser.ID);
		if (id == null)
			return null;
		Element child = NormalParser.getFirstElement(el, context);
		if (child != null) {
			Parser p = context.getParser(child.getName());
			if (p == null)
				return context.get(id, child);
			return context.get(id, p.parse(child, context, arg));
		}
		return context.get(id);
	}

}