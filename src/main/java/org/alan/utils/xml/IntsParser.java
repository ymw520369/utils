
package org.alan.utils.xml;


/**
 * 类说明：整数数组分析器
 *
 * @author zminleo <zmin@seasky.cn>
 * @version 1.0
 */

public final class IntsParser extends NormalParser {

	/* static fields */
    /**
     * 零长度的数组
     */
    public static final int[] EMPTY_ARRAY = {};

	/* methods */

    /**
     * 一般分析方法
     */
    public Object normalParse(Element el, XmlContext context, Object arg) {
        String str = el.getFirstText();
        if (str == null)
            return null;
        if (str.length() <= 0)
            return EMPTY_ARRAY;
        return parseIntArray(str);
    }

    private int[] parseIntArray(String str) {
        String[] strArray = str.split(",", -1);
        int[] intArray = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        return intArray;
    }

}