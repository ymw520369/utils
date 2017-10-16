
package org.alan.utils.xml;

/**
 * 类说明：字符数组分析器
 * 
 * @version 1.0
 */

public final class CharsParser extends NormalParser {

	/* methods */
	/** 一般分析方法 */
	public Object normalParse(Element el, XmlContext context, Object arg) {
		String hex = el.getAttribute("hex");
		String str = el.getFirstText();
		if (hex == null)
			return (str != null) ? str.toCharArray() : null;
		String[] strs = str.split(",", -1);
		char[] array = new char[strs.length];
		for (int i = 0; i < strs.length; i++)
			array[i] = (char) Integer.parseInt(strs[i], 16);
		return array;
	}

}