/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.field;

/**
 * 类说明：短整数域
 * 
 * @version 1.0
 * @author zminleo <zmin@seasky.cn>
 */

public final class ShortField extends FieldObject implements Comparable {

	/* fields */
	/** 值 */
	public short value;

	/* methods */
	/** 获得值 */
	public Object getValue() {
		return new Short(value);
	}

	/** 比较方法 */
	public int compareTo(Object obj) {
		if (!(obj instanceof ShortField))
			return COMP_GRTR;
		short v = ((ShortField) obj).value;
		if (value > v)
			return COMP_GRTR;
		if (value < v)
			return COMP_LESS;
		return COMP_EQUAL;
	}

}