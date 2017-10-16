/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.field;


/**
 * 类说明：字符域
 * 
 * @version 1.0
 * @author zminleo <zmin@seasky.cn>
 */

public final class CharField extends FieldObject implements Comparable {

	/* fields */
	/** 值 */
	public char value;

	/* methods */
	/** 获得值 */
	public Object getValue() {
		return new Character(value);
	}

	/** 比较方法 */
	public int compareTo(Object obj) {
		if (!(obj instanceof CharField))
			return COMP_GRTR;
		char v = ((CharField) obj).value;
		if (value > v)
			return COMP_GRTR;
		if (value < v)
			return COMP_LESS;
		return COMP_EQUAL;
	}

}