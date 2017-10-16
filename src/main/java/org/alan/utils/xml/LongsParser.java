
package org.alan.utils.xml;


/**
 * 类说明：长整数数组分析器
 *
 * @version 1.0
 */

public final class LongsParser extends NormalParser {

	/* methods */

    /**
     * 一般分析方法
     */
    public Object normalParse(Element el, XmlContext context, Object arg) {
        String str = el.getFirstText();
        if (str == null)
            return null;
        String[] strs = str.split(",", -1);
        long[] array = new long[strs.length];
        for (int i = 0; i < strs.length; i++)
            array[i] = Long.parseLong(strs[i]);
        return array;
    }

}