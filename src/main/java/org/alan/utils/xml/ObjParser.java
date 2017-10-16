
package org.alan.utils.xml;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 类说明：对象分析器
 * 
 * @version 1.0
 */

public class ObjParser extends NormalParser {

	/* static fields */
	/** 日志记录 */
	private static final Logger log = LoggerFactory.getLogger(ObjParser.class);

	/* methods */
	/** 一般分析方法 */
	public Object normalParse(Element el, XmlContext context, Object arg) {
		String className = getAttribute(el, context, CLASS);
		if (className == null) {
			if (log.isWarnEnabled())
				log.warn("normalParse error, null className, " + el);
			return null;
		}
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
		Object obj = loadObject(el, c, context);
		if (obj == null)
			return null;
		for (int i = 0, n = el.getContentCount(); i < n; i++) {
			if (el.getType(i) != XmlReader.ELEMENT)
				continue;
			parseMacro(el, context, obj, (Element) el.getContent(i));
		}
		return obj;
	}

	/** 加载指定的对象 */
	public Object loadObject(Element el, Class c, XmlContext context) {
		try {
			return c.newInstance();
		} catch (Exception e) {
			if (log.isWarnEnabled())
				log.warn("loadObject error, new instance, " + el, e);
			return null;
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