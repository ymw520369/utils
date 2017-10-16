
package org.alan.utils.xml;

import org.alan.utils.field.FieldKit;
import org.alan.utils.field.FieldValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类说明：域分析器
 * 
 * @version 1.0
 */

public class FieldParser extends NormalParser {

	/* static fields */
	/** 日志记录 */
	private static final Logger log = LoggerFactory.getLogger(FieldParser.class);

	/* static methods */
	/** 分析域变量 */
	public static FieldValue parseFieldValue(Element el, XmlContext context) {
		FieldValue f;
		Element child = getFirstElement(el, context);
		if (child != null) {
			Parser p = context.getParser(child.getName());
			if (p == null)
				return null;
			Object r = p.parse(child, context, null);
			if (!(r instanceof FieldValue))
				f = new FieldValue(r.getClass(), r);
			else
				f = (FieldValue) r;
		}
		// 模糊类型
		else {
			String text = getFirstText(el, context);
			if (text == null)
				return null;
			f = new FieldValue(null, text);
		}
		return f;
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
		// 类变量
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
			FieldValue value = parseFieldValue(el, context);
			if (value == null) {
				try {
					value = declared
							? FieldKit.getDeclaredField(c, null, name)
							: FieldKit.getField(c, null, name);
					return (value.type.isPrimitive()) ? value : value.value;
				} catch (Exception e) {
					if (log.isWarnEnabled())
						log.warn("normalParse error, get static field, " + el,
								e);
					return null;
				}
			}
			try {
				if (declared)
					FieldKit.setDeclaredField(c, null, name, value);
				else
					FieldKit.setField(c, null, name, value);
				return (value.type.isPrimitive()) ? value : value.value;
			} catch (Exception e) {
				if (log.isWarnEnabled())
					log.warn("normalParse error, set static field, " + el, e);
				return null;
			}
		}
		// 实例变量
		String ref = getAttribute(el, context, REF);
		if (ref != null)
			arg = context.get(ref);
		if (arg == null) {
			if (log.isWarnEnabled())
				log.warn("normalParse error, null specify object, " + el);
			return null;
		}
		FieldValue value = parseFieldValue(el, context);
		if (value == null) {
			try {
				value = declared ? FieldKit.getDeclaredField(arg.getClass(),
						arg, name) : FieldKit.getField(arg.getClass(), arg,
						name);
				return (value.type.isPrimitive()) ? value : value.value;
			} catch (Exception e) {
				if (log.isWarnEnabled())
					log.warn("normalParse error, get field, " + el, e);
				return null;
			}
		}
		try {
			if (declared)
				FieldKit.setDeclaredField(arg.getClass(), arg, name, value);
			else
				FieldKit.setField(arg.getClass(), arg, name, value);
			return (value.type.isPrimitive()) ? value : value.value;
		} catch (Exception e) {
			if (log.isWarnEnabled())
				log.warn("normalParse error, set field, " + el, e);
			return null;
		}
	}

}