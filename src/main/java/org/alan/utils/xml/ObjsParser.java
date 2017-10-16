
package org.alan.utils.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;

/**
 * 类说明：对象数组分析器
 *
 * @version 1.0
 */

public class ObjsParser extends NormalParser {

	/* static fields */
    /**
     * 日志记录
     */
    private static final Logger log = LoggerFactory.getLogger(ObjsParser.class);

	/* methods */

    /**
     * 一般分析方法
     */
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
            c = Class.forName(className, true, loader);
        } catch (Exception e) {
            if (log.isWarnEnabled())
                log.warn("normalParse error, class not found, " + el, e);
            return null;
        }
        int length = -1;
        String str = getAttribute(el, context, "length");
        if (str != null)
            length = Integer.parseInt(str);
        if (length < 0)
            length = getSubElementCount(el, context, 0);
        Object array;
        try {
            array = Array.newInstance(c, length);
        } catch (Exception e) {
            if (log.isWarnEnabled())
                log.warn("normalParse error, new instance, " + el, e);
            return null;
        }
        if (length == 0)
            return array;
        int count = 0;
        for (int i = 0, n = el.getContentCount(); i < n; i++) {
            if (el.getType(i) != XmlReader.ELEMENT)
                continue;
            count = parseMacro(el, context, array, count,
                    (Element) el.getContent(i));
        }
        return array;
    }

    /**
     * 分析宏引用
     */
    public int parseMacro(Element el, XmlContext context, Object array,
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
                index = parseMacro(el, context, array, index,
                        (Element) e.getContent(i));
            }
            return index;
        }
        Parser p = context.getParser(child.getName());
        if (p == null)
            return index;
        Array.set(array, index, p.parse(child, context, null));
        return index + 1;
    }

}