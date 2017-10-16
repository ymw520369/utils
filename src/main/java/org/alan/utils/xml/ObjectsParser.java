
package org.alan.utils.xml;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类说明：根对象分析器
 *
 * @version 1.0
 */

public class ObjectsParser implements Parser {

	/* static fields */
    /**
     * 加载方式的值，惰性或工厂加载
     */
    public static final String LAZY = "lazy", FACTORY = "factory";
    /**
     * 警告同名ID的定义常量
     */
    public static final String WARN_SAME_ID = "warnSameId";

    /**
     * 日志记录
     */
    private static final Logger log = LoggerFactory.getLogger(ObjectsParser.class);

	/* methods */

    /**
     * 分析方法
     */
    public Object parse(Element el, XmlContext context, Object arg) {
        String str = el.getAttribute(WARN_SAME_ID);
        if (str != null)
            context.setWarnSameId(Boolean.parseBoolean(str));
        for (int i = 0, n = el.getContentCount(); i < n; i++) {
            if (el.getType(i) != XmlReader.ELEMENT)
                continue;
            parseMacro(el, context, arg, (Element) el.getContent(i));
        }
        return null;
    }

    /**
     * 分析宏引用
     */
    public void parseMacro(Element el, XmlContext context, Object arg,
                           Element child) {
        if (NormalParser.MACRO_REF.equalsIgnoreCase(child.getName())) {
            String id = child.getAttribute(NormalParser.ID);
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
        parseLoad(el, context, arg, child);
    }

    /**
     * 分析加载方法
     */
    public void parseLoad(Element el, XmlContext context, Object arg,
                          Element child) {
        String id = child.getAttribute(NormalParser.ID);
        if (id != null) {
            // 惰性或工厂加载
            String load = child.getAttribute(NormalParser.LOAD);
            if (load != null) {
                if (LAZY.equalsIgnoreCase(load)) {
                    Object old = context.set(id, child, XmlContext.LAZY);
                    if (old != null && context.isWarnSameId()
                            && log.isWarnEnabled())
                        log.warn("parse error, same id, id=" + id + " " + el
                        );
                    return;
                }
                if (FACTORY.equalsIgnoreCase(load)) {
                    Object old = context.set(id, child, XmlContext.FACTORY);
                    if (old != null && context.isWarnSameId()
                            && log.isWarnEnabled())
                        log.warn("parse error, same id, id=" + id + " " + el
                        );
                    return;
                }
                if (log.isWarnEnabled())
                    log.warn(
                            "parseLoad error, invalid load:" + load + " " + el
                    );
            }
        }
        Parser p = context.getParser(child.getName());
        if (p != null)
            p.parse(child, context, arg);
    }

}