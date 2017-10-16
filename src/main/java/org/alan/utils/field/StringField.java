/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.field;

/**
 * 类说明：字符串域
 * 
 * @version 1.0
 * @author zminleo <zmin@seasky.cn>
 */

public final class StringField extends FieldObject implements Comparable {

	/* fields */
	/** 值 */
	public String value;

	/* methods */
	/** 获得值 */
	public Object getValue() {
		return value;
	}

	/** 比较方法 */
	public int compareTo(Object obj) {
		if (!(obj instanceof StringField))
			return COMP_GRTR;
		String v = ((StringField) obj).value;
		if (value == null)
			return v != null ? COMP_LESS : COMP_EQUAL;
		return value.compareTo(v);
	}

}