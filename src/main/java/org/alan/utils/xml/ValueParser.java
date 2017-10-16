
package org.alan.utils.xml;


import org.alan.utils.field.FieldValue;

/**
 * 类说明：值对象分析器
 *
 * @version 1.0
 */

public class ValueParser extends NormalParser {

	/* methods */

    /**
     * 一般分析方法
     */
    public Object normalParse(Element el, XmlContext context, Object arg) {
        String str = el.getFirstText();
        if (str == null)
            return null;
        String type = el.getAttribute("type");
        if (type == null)
            return new FieldValue(null, str);
        if (str.length() <= 0)
            return null;
        if ("int".equalsIgnoreCase(type))
            return new FieldValue(int.class, Integer.parseInt(str));
        if ("long".equalsIgnoreCase(type))
            return new FieldValue(long.class, Long.parseLong(str));
        if ("boolean".equalsIgnoreCase(type))
            return new FieldValue(boolean.class, Boolean.parseBoolean(str));
        if ("byte".equalsIgnoreCase(type))
            return new FieldValue(byte.class, Byte.parseByte(str));
        if ("short".equalsIgnoreCase(type))
            return new FieldValue(short.class, Short.parseShort(str));
        if ("char".equalsIgnoreCase(type))
            return new FieldValue(char.class, str.charAt(0));
        if ("float".equalsIgnoreCase(type))
            return new FieldValue(float.class, new Float(str));
        if ("double".equalsIgnoreCase(type))
            return new FieldValue(double.class, new Double(str));
        return null;
    }

}