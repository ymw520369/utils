
package org.alan.utils.xml;

/**
 * 类说明：双浮点数数组分析器
 *
 * @version 1.0
 */

public final class DoublesParser extends NormalParser {

	/* methods */

    /**
     * 一般分析方法
     */
    public Object normalParse(Element el, XmlContext context, Object arg) {
        String str = el.getFirstText();
        if (str == null)
            return null;
        String[] strs = str.split(",", -1);
        double[] array = new double[strs.length];
        for (int i = 0; i < strs.length; i++)
            array[i] = Double.parseDouble(strs[i]);
        return array;
    }

}