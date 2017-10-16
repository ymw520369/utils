/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.xml;

import org.alan.utils.field.FieldKit;
import org.alan.utils.field.FieldValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类说明：构造对象分析器
 *
 * @author zminleo <zmin@seasky.cn>
 * @version 1.0
 */

public class ConstructorParser extends NormalParser {

	/* static fields */
    /**
     * 日志记录
     */
    private static final Logger log = LoggerFactory
            .getLogger(ConstructorParser.class);

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
        String str = getAttribute(el, context, DECLARED);
        boolean declared = (str != null) && Boolean.parseBoolean(str);
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
        FieldValue[] args = MethodParser.parseMethodArgs(el, context);
        if (args == null) {
            if (log.isWarnEnabled())
                log.warn("normalParse error, null args, " + el);
            return null;
        }
        try {
            return declared ? FieldKit.constructDeclared(c, args) : FieldKit
                    .construct(c, args);
        } catch (Exception e) {
            if (log.isWarnEnabled())
                log.warn("normalParse error, construct, " + el, e);
            return null;
        }
    }

}