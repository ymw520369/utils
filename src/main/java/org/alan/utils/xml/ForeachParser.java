
package org.alan.utils.xml;

import org.alan.utils.field.FieldValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

/**
 * 类说明：数组或集合的循环分析器
 * 
 * @version 1.0
 */

public class ForeachParser extends NormalParser {

	/* static fields */
	/** 子元素定义的元素名称 */
	public static final String SUB = "sub";
	/** 日志记录 */
	private static final Logger log = LoggerFactory.getLogger(ForeachParser.class);

	/* methods */
	/** 一般分析方法 */
	public Object normalParse(Element el, XmlContext context, Object arg) {
		String ref = getAttribute(el, context, REF);
		if (ref == null) {
			if (log.isWarnEnabled())
				log.warn("normalParse error, null ref, " + el);
			return null;
		}
		Object array = context.get(ref);
		if (array == null) {
			if (log.isWarnEnabled())
				log.warn("normalParse error, null specify array, " + el);
			return null;
		}
		boolean skip = true;
		String str = getAttribute(el, context, "skip");
		if (str != null)
			skip = Boolean.parseBoolean(str);
		String sub = getAttribute(el, context, SUB);
		if (sub == null) {
			if (log.isWarnEnabled())
				log.warn("normalParse error, null sub, " + el);
			return null;
		}
		if (array instanceof Collection) {
			Object obj;
			Iterator i = ((Collection) array).iterator();
			while (i.hasNext()) {
				obj = i.next();
				if (obj == null && skip)
					continue;
				context.set(sub, obj);
				parseSubElements(el, context, arg);
			}
		} else if (array instanceof int[]) {
			int[] temp = (int[]) array;
			for (int i = 0, n = temp.length; i < n; i++) {
				context.set(sub,
						new FieldValue(int.class, temp[i]));
				parseSubElements(el, context, arg);
			}
		} else if (array instanceof long[]) {
			long[] temp = (long[]) array;
			for (int i = 0, n = temp.length; i < n; i++) {
				context.set(sub, new FieldValue(long.class, temp[i]));
				parseSubElements(el, context, arg);
			}
		} else if (array instanceof boolean[]) {
			boolean[] temp = (boolean[]) array;
			for (int i = 0, n = temp.length; i < n; i++) {
				context.set(sub, new FieldValue(boolean.class, temp[i]));
				parseSubElements(el, context, arg);
			}
		} else if (array instanceof byte[]) {
			byte[] temp = (byte[]) array;
			for (int i = 0, n = temp.length; i < n; i++) {
				context.set(sub, new FieldValue(byte.class, temp[i]));
				parseSubElements(el, context, arg);
			}
		} else if (array instanceof short[]) {
			short[] temp = (short[]) array;
			for (int i = 0, n = temp.length; i < n; i++) {
				context.set(sub,
						new FieldValue(short.class, temp[i]));
				parseSubElements(el, context, arg);
			}
		} else if (array instanceof char[]) {
			char[] temp = (char[]) array;
			for (int i = 0, n = temp.length; i < n; i++) {
				context.set(sub, new FieldValue(char.class, temp[i]));
				parseSubElements(el, context, arg);
			}
		} else if (array instanceof float[]) {
			float[] temp = (float[]) array;
			for (int i = 0, n = temp.length; i < n; i++) {
				context.set(sub,
						new FieldValue(float.class, temp[i]));
				parseSubElements(el, context, arg);
			}
		} else if (array instanceof double[]) {
			double[] temp = (double[]) array;
			for (int i = 0, n = temp.length; i < n; i++) {
				context.set(sub, new FieldValue(double.class, temp[i]));
				parseSubElements(el, context, arg);
			}
		} else {
			Object obj;
			for (int i = 0, n = Array.getLength(array); i < n; i++) {
				obj = Array.get(array, i);
				if (obj != null) {
					context.set(sub, obj);
					parseSubElements(el, context, arg);
				} else if (!skip) {
					context.set(sub, obj);
					parseSubElements(el, context, arg);
					obj = context.get(sub);
					if (obj != null)
						Array.set(array, i, obj);
				}
			}
		}
		return null;
	}

	/** 分析子元素 */
	public void parseSubElements(Element el, XmlContext context, Object arg) {
		for (int i = 0, n = el.getContentCount(); i < n; i++) {
			if (el.getType(i) != XmlReader.ELEMENT)
				continue;
			parseMacro(el, context, arg, (Element) el.getContent(i));
		}
	}

	/** 分析宏引用 */
	public void parseMacro(Element el, XmlContext context, Object arg,
			Element child) {
		if (MACRO_REF.equalsIgnoreCase(child.getName())) {
			String id = child.getAttribute(ID);
			if (id == null)
				return;
			Element e = (Element) context.get(id);
			if (e == null)
				return;
			for (int i = 0, n = e.getContentCount(); i < n; i++) {
				if (e.getType(i) != XmlReader.ELEMENT)
					continue;
				parseMacro(el, context, arg, (Element) e.getContent(i));
			}
			return;
		}
		Parser p = context.getParser(child.getName());
		if (p != null)
			p.parse(child, context, arg);
	}

}