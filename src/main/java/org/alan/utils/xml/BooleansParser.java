
package org.alan.utils.xml;


/**
 * 类说明：布尔数组分析器
 *
 * @version 1.0
 */

public final class BooleansParser extends NormalParser {

    /**
     * 一般分析方法
     */
    public Object normalParse(Element el, XmlContext context, Object arg) {
        String str = el.getFirstText();
        if (str == null)
            return null;
        String[] strs = str.split(",", -1);
        boolean[] array = new boolean[strs.length];
        for (int i = 0; i < strs.length; i++)
            array[i] = Boolean.parseBoolean(strs[i]);
        return array;
    }

}