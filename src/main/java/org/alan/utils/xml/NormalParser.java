
package org.alan.utils.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类说明：一般分析器，实现了设置对象的id
 * 
 * @version 1.0
 */

public abstract class NormalParser implements Parser {

	/* static fields */
	/** 引用、加载方式、类名、引用名、宏的属性名称 */
	public static final String ID = "id", LOAD = "load", CLASS = "class",
			REF = "ref", MACRO = "macro", MACRO_REF = "macro";
	/** 名称、公开类型的属性名称 */
	public static final String NAME = "name", DECLARED = "declared";
	/** 空对象 */
	public static final Object NONE = new Object();

	/** 日志记录 */
	private static final Logger log = LoggerFactory.getLogger(NormalParser.class);

	/* static methods */
	/**
	 * 获得指定元素的属性， 如果该元素的属性有宏引用， 则本地元素没有的属性将取宏元素的属性
	 */
	public static String getAttribute(Element el, XmlContext context,
			String name) {
		String str;
		Element parent;
		do {
			str = el.getAttribute(name);
			if (str != null)
				return str;
			if (el.getSource() == NONE)
				return null;
			parent = (Element) el.getSource();
			if (parent == null) {
				str = el.getAttribute(MACRO);
				if (str != null) {
					parent = (Element) context.get(str);
					if (parent != null)
						el.setSource(parent);
					else
						el.setSource(NONE);
				} else
					el.setSource(NONE);
			}
			el = parent;
		} while (el != null);
		return null;
	}

	/** 计算指定元素的子元素的总数量，如果有宏引用，将递归计算宏的子元素 */
	public static int getSubElementCount(Element el, XmlContext context,
			int count) {
		Element child;
		for (int i = 0, n = el.getContentCount(); i < n; i++) {
			if (el.getType(i) != XmlReader.ELEMENT)
				continue;
			child = (Element) el.getContent(i);
			if (MACRO_REF.equalsIgnoreCase(child.getName())) {
				String id = child.getAttribute(ID);
				if (id == null)
					continue;
				Element e = (Element) context.get(id);
				if (e == null)
					continue;
				count = getSubElementCount(e, context, count);
			} else
				count++;
		}
		return count;
	}

	/** 获得指定元素中的第一个子元素， 如果为宏引用，则返回宏的第一个子元素 */
	public static Element getFirstElement(Element el, XmlContext context) {
		Element child = el.getFirstElement();
		if (child == null)
			return null;
		if (!MACRO_REF.equalsIgnoreCase(child.getName()))
			return child;
		String id = child.getAttribute(ID);
		if (id != null)
			return null;
		el = (Element) context.get(id);
		if (el == null)
			return null;
		return el.getFirstElement();
	}

	/** 获得指定元素中的第一个文本内容 */
	public static String getFirstText(Element el, XmlContext context) {
		return el.getFirstText();
	}

	/* methods */
	/** 分析方法，调用一般分析方法获得对象，然后设置对象的id */
	public Object parse(Element el, XmlContext context, Object arg) {
		Object obj;
		try {
			obj = normalParse(el, context, arg);
		} catch (Throwable t) {
			if (log.isWarnEnabled())
				log.warn("parse error, " + el, t);
			return null;
		}
		String id = el.getAttribute(ID);
		// 元素有id属性且采用标准加载方式，则设置对象的id
		if (id != null && arg != context) {
			Object old = context.set(id, obj);
			if (old != null && context.isWarnSameId() && log.isWarnEnabled())
				log.warn("parse error, same id, id=" + id + " " + el);
		}
		return obj;
	}

	/** 一般分析方法 */
	public abstract Object normalParse(Element el, XmlContext context,
			Object arg);

}