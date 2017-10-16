
package org.alan.utils.xml;

import org.alan.utils.field.FieldKit;
import org.alan.utils.field.FieldValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类说明：方法分析器
 * 
 * @version 1.0
 */

public class MethodParser extends NormalParser {

	/* static fields */
	/** 日志记录 */
	private static final Logger log = LoggerFactory.getLogger(MethodParser.class);

	/* static methods */
	/** 分析方法参数 */
	public static FieldValue[] parseMethodArgs(Element el, XmlContext context) {
		int n = el.getContentCount();
		if (n <= 0)
			return new FieldValue[0];
		int count = getSubElementCount(el, context, 0);
		if (count == 0) {
			String text = getFirstText(el, context);
			if (text == null)
				return null;
			return new FieldValue[]{new FieldValue(null, text)};
		}
		FieldValue[] args = new FieldValue[count];
		count = 0;
		for (int i = 0; i < n; i++) {
			if (el.getType(i) != XmlReader.ELEMENT)
				continue;
			count = parseMacro(el, context, args, count,
					(Element) el.getContent(i));
		}
		if (count >= args.length)
			return args;
		FieldValue[] temp = new FieldValue[count];
		System.arraycopy(args, 0, temp, 0, count);
		return temp;
	}

	/** 分析宏引用 */
	static int parseMacro(Element el, XmlContext context, FieldValue[] args,
			int index, Element child) {
		if (MACRO_REF.equalsIgnoreCase(child.getName())) {
			String id = child.getAttribute(ID);
			if (id == null)
				return index;
			Element e = (Element) context.get(id);
			if (e == null)
				return index;
			for (int i = 0, n = e.getContentCount(); i < n; i++) {
				if (e.getType(i) != XmlReader.ELEMENT)
					continue;
				index = parseMacro(el, context, args, index,
						(Element) e.getContent(i));
			}
			return index;
		}
		Parser p = context.getParser(child.getName());
		if (p == null)
			return index;
		Object r = p.parse(child, context, null);
		if (r == null)
			return index + 1;
		FieldValue f;
		if (!(r instanceof FieldValue))
			f = new FieldValue(r.getClass(), r);
		else
			f = (FieldValue) r;
		args[index] = f;
		return index + 1;
	}

	/* methods */
	/** 一般分析方法 */
	public Object normalParse(Element el, XmlContext context, Object arg) {
		String name = getAttribute(el, context, NAME);
		if (name == null) {
			if (log.isWarnEnabled())
				log.warn("normalParse error, null name, " + el);
			return null;
		}
		String str = getAttribute(el, context, DECLARED);
		boolean declared = (str != null) && Boolean.parseBoolean(str);
		String className = getAttribute(el, context, CLASS);
		// 类方法调用
		if (className != null) {
			ClassLoader loader = context.getClassLoader();
			Class c;
			try {
				c = (loader != null) ? loader.loadClass(className) : Class
						.forName(className);
			} catch (Exception e) {
				if (log.isWarnEnabled())
					log.warn("normalParse error, class not found, " + el, e);
				return null;
			}
			FieldValue[] args = parseMethodArgs(el, context);
			if (args == null) {
				if (log.isWarnEnabled())
					log.warn("normalParse error, null args static method, "
							+ el);
				return null;
			}
			try {
				FieldValue value = declared ? FieldKit.invokeDeclared(c, null,
						name, args) : FieldKit.invoke(c, null, name, args);
				return (value.type.isPrimitive()) ? value : value.value;
			} catch (Exception e) {
				if (log.isWarnEnabled())
					log.warn("normalParse error, static method invoke, " + el,
							e);
				return null;
			}
		}
		// 实例方法调用
		String ref = getAttribute(el, context, REF);
		if (ref != null)
			arg = context.get(ref);
		if (arg == null) {
			if (log.isWarnEnabled())
				log.warn("normalParse error, null specify object, " + el);
			return null;
		}
		FieldValue[] args = parseMethodArgs(el, context);
		if (args == null) {
			if (log.isWarnEnabled())
				log.warn("normalParse error, null args method, " + el);
			return null;
		}
		try {
			FieldValue value = declared ? FieldKit.invokeDeclared(
					arg.getClass(), arg, name, args) : FieldKit.invoke(
					arg.getClass(), arg, name, args);
			return (value.type.isPrimitive()) ? value : value.value;
		} catch (Exception e) {
			if (log.isWarnEnabled())
				log.warn("normalParse error, method invoke, " + el, e);
			return null;
		}
	}

}