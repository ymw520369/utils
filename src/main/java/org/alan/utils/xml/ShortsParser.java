/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.xml;

/**
 * 类说明：短整数数组分析器
 *
 * @author zminleo <zmin@seasky.cn>
 * @version 1.0
 */

public final class ShortsParser extends NormalParser {

	/* methods */

    /**
     * 一般分析方法
     */
    public Object normalParse(Element el, XmlContext context, Object arg) {
        String str = el.getFirstText();
        if (str == null)
            return null;
        String[] strs = str.split(",", -1);
        short[] array = new short[strs.length];
        for (int i = 0; i < strs.length; i++)
            array[i] = Short.parseShort(strs[i]);
        return array;
    }

}