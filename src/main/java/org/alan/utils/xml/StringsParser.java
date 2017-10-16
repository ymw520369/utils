/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.xml;

/**
 * 类说明：字符串数组分析器
 *
 * @author zminleo <zmin@seasky.cn>
 * @version 1.0
 */

public final class StringsParser extends NormalParser {

	/* static fields */
    /**
     * 分隔字符串常量
     */
    public static final String SEPARATOR = "|";

	/* methods */

    /**
     * 一般分析方法
     */
    public Object normalParse(Element el, XmlContext context, Object arg) {
        String str = el.getFirstText();
        if (str == null)
            return null;
        String separator = el.getAttribute("separator");
        if (separator == null)
            separator = SEPARATOR;
        return str.split(separator, -1);
    }

}